package test.arimitsu.sf.cql.v3.specs

import java.nio.ByteBuffer

import arimitsu.sf.cql.v3.message.request.Options
import arimitsu.sf.cql.v3.message.response.Supported
import arimitsu.sf.cql.v3.{Compression, CqlParser, Flags, Opcode}
import org.scalatest._
import test.arimitsu.sf.cql.v3.ChannelManager

import scala.collection.JavaConversions._

/**
 * Created by sxend on 2014/10/02.
 */
class OptionsSpec extends FlatSpec with Matchers {
  "Options Response" should "be Supported" in {
    val client = ChannelManager.getInstance
    val options = new Options(client.streamId, Flags.NONE)
    val parser = new CqlParser()
    parser.configure(Compression.NONE)
    options.toFrame
    val writeBuffer = parser.unparse(options.toFrame)
    client.channel.write(writeBuffer)
    val readBuffer = ByteBuffer.allocate(1000)
    client.channel.read(readBuffer)
    readBuffer.flip()
    val comingFrame = parser.parse(readBuffer)
    comingFrame.header.opcode should be(Opcode.SUPPORTED)
    val supported = Supported.fromBuffer(ByteBuffer.wrap(comingFrame.body))
    supported.stringMultiMap.get(Supported.CQL_VERSION).head should be("3.2.0")
    supported.stringMultiMap.get(Supported.COMPRESSION).head should be("snappy")
    supported.stringMultiMap.get(Supported.COMPRESSION).last should be("lz4")
  }
}
