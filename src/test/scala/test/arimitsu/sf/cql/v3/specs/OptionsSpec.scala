package test.arimitsu.sf.cql.v3.specs

import java.nio.ByteBuffer

import arimitsu.sf.cql.v3.Frame.Header
import arimitsu.sf.cql.v3._
import arimitsu.sf.cql.v3.message.request.Options
import arimitsu.sf.cql.v3.message.response.Supported
import org.scalatest._
import test.arimitsu.sf.cql.v3.ChannelManager

import scala.collection.JavaConversions._

/**
 * Created by sxend on 2014/10/02.
 */
class OptionsSpec extends FlatSpec with Matchers {
  "Options Response" should "be Supported" in {
    val client = ChannelManager.getInstance
    val options = new Options()
    val parser = new CqlParser()
    parser.configure(Compression.NONE)
    val body = options.toBody
    val frame = new Frame(new Header(Version.REQUEST, Flags.NONE, client.streamId, Opcode.OPTIONS, body.length), body)
    val writeBuffer = parser.frameToByteBuffer(frame)
    client.channel.write(writeBuffer)
    val readBuffer = ByteBuffer.allocate(1000)
    client.channel.read(readBuffer)
    readBuffer.flip()
    val comingFrame = parser.byteBufferToFrame(readBuffer)
    comingFrame.header.opcode should be(Opcode.SUPPORTED)
    val supported = new Supported(ByteBuffer.wrap(comingFrame.body))
    supported.stringMultiMap.get(Supported.CQL_VERSION).head should be("3.2.0")
    supported.stringMultiMap.get(Supported.COMPRESSION).head should be("snappy")
    supported.stringMultiMap.get(Supported.COMPRESSION).last should be("lz4")
  }
}
