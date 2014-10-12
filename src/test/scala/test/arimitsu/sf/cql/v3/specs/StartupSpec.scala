package test.arimitsu.sf.cql.v3.specs

import java.nio.ByteBuffer

import arimitsu.sf.cql.v3.Frame.Header
import arimitsu.sf.cql.v3.message.response.{Ready, Supported}
import arimitsu.sf.cql.v3.{Opcode, Flags, Version, Frame, Compression, CQLVersion, CQLParser}
import arimitsu.sf.cql.v3.compressor.NoneCompressor
import arimitsu.sf.cql.v3.message.request.Startup
import org.scalatest.{OneInstancePerTest, BeforeAndAfter, Matchers, FunSuite}
import test.arimitsu.sf.cql.v3.ClientManager

/**
 * Created by sxend on 14/10/13.
 */
class StartupSpec extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest{
  test("startup"){
    val client = ClientManager.getInstance
    val parser = new CQLParser().withCompressor(new NoneCompressor())
    val startupBuffer = {
      val startup = new Startup(CQLVersion.CURRENT,Compression.NONE)
      val body = startup.toBody
      val frame = new Frame(new Header(Version.REQUEST, Flags.NONE, client.streamId, Opcode.STARTUP, body.length), body)
      parser.frameToByteBuffer(frame)
    }
    client.channel.write(startupBuffer)
    val readBuffer = ByteBuffer.allocate(4096)
    client.channel.read(readBuffer)
    readBuffer.flip()
    val resultFrame = parser.byteBufferToFrame(readBuffer)
    val message = parser.frameToMessage(resultFrame)
    message shouldBe a [Ready]
  }
}
