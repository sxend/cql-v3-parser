package test.arimitsu.sf.cql.v3

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import java.util.ResourceBundle

import arimitsu.sf.cql.v3.Frame.Header
import arimitsu.sf.cql.v3.message.response.Ready
import arimitsu.sf.cql.v3.{Opcode, Flags, Version, Frame, Compression, CQLVersion, CQLParser}
import arimitsu.sf.cql.v3.compressor.NoneCompressor
import arimitsu.sf.cql.v3.message.request.Startup

/**
 * Created by sxend on 2014/09/30.
 */
object ClientManager {
  def getInstance: Client = {
    val rb = ResourceBundle.getBundle("test")
    val host = Option(rb.getString("CASSANDRA_HOST"))
    val port = Option(rb.getString("CASSANDRA_PORT")).map(Integer.parseInt)
    val channel = SocketChannel.open()
    channel.connect(new InetSocketAddress(host.getOrElse("127.0.0.1"), port.getOrElse(9042)))
    new Client(channel)
  }
}

class Client(val channel: SocketChannel, val streamId: Short = 0.toShort) {
  private[v3] def next: Client = new Client(channel, (streamId + 1).toShort)

  private[v3] def startup: Client ={
    val parser = new CQLParser().withCompressor(new NoneCompressor())
    val startupBuffer = {
      val startup = new Startup(CQLVersion.CURRENT,Compression.NONE)
      val body = startup.toBody
      val frame = new Frame(new Header(Version.REQUEST, Flags.NONE, this.streamId, Opcode.STARTUP, body.length), body)
      parser.frameToByteBuffer(frame)
    }
    this.channel.write(startupBuffer)
    val readBuffer = ByteBuffer.allocate(4096)
    this.channel.read(readBuffer)
    readBuffer.flip()
    val resultFrame = parser.byteBufferToFrame(readBuffer)
    val message = parser.frameToMessage(resultFrame)
    this.next
  }

  private[v3] def close() = channel.close()
}

