package test.arimitsu.sf.cql.v3.specs

import java.nio.ByteBuffer

import arimitsu.sf.cql.v3.Frame.Header
import arimitsu.sf.cql.v3.compressor.NoneCompressor
import arimitsu.sf.cql.v3.message.request.Options
import arimitsu.sf.cql.v3.message.response.Supported
import arimitsu.sf.cql.v3.{Compression, CQLParser, Flags, Frame, Opcode, Version}
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers, OneInstancePerTest}
import test.arimitsu.sf.cql.v3.ClientManager

/**
 * Created by sxend on 14/10/13.
 */
class OptionsSpec extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest {
  test("option") {
    val client = ClientManager.getInstance
    val parser = new CQLParser().withCompressor(new NoneCompressor())
    val optionsBuffer = {
      val option = new Options()
      val body = option.toBody
      val frame = new Frame(new Header(Version.REQUEST, Flags.NONE, client.streamId, Opcode.OPTIONS, body.length), body)
      parser.frameToByteBuffer(frame)
    }
    client.channel.write(optionsBuffer)

    val readBuffer = ByteBuffer.allocate(4096)
    client.channel.read(readBuffer)
    readBuffer.flip()
    val resultFrame = parser.byteBufferToFrame(readBuffer)
    val message = parser.frameToMessage(resultFrame)
    message shouldBe a [Supported]
    val supported = message.asInstanceOf[Supported]
    import scala.collection.JavaConversions._
    supported.stringMultiMap.containsKey(Supported.COMPRESSION) should be (right = true)
    supported.stringMultiMap.containsKey(Supported.CQL_VERSION) should be (right = true)
    supported.stringMultiMap.get(Supported.COMPRESSION).forall{
      compression => compression == Compression.LZ4.name || compression == Compression.SNAPPY.name
    } should be(true)
  }
}
