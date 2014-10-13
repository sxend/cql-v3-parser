package test.arimitsu.sf.cql.v3

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import java.util.ResourceBundle

import arimitsu.sf.cql.v3.Frame.Header
import arimitsu.sf.cql.v3.compressor.NoneCompressor
import arimitsu.sf.cql.v3.message.Message
import arimitsu.sf.cql.v3.message.request.Startup
import arimitsu.sf.cql.v3.{CQLParser, CQLVersion, Compression, Flags, Frame, Opcode, Version}

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

class Client(channel: SocketChannel) {
  val parser = new CQLParser().withCompressor(new NoneCompressor())
  var counter: Short = 0.toShort

  private[v3] def increment: Short = {
    val count = counter
    counter = (counter + 1).toShort
    count
  }

  private[v3] def request(opcode: Opcode, body: Array[Byte]): Message = {
    val streamId = this.increment
    val frame = new Frame(new Header(Version.REQUEST, Flags.NONE, streamId, opcode, body.length), body)
    val writeBuffer = parser.frameToByteBuffer(frame)
    this.channel.write(writeBuffer)
    val readBuffer = ByteBuffer.allocate(4096)
    this.channel.read(readBuffer)
    readBuffer.flip()
    val resultFrame = parser.byteBufferToFrame(readBuffer)
    val message = parser.frameToMessage(resultFrame)
    message
  }

  private[v3] def startup(): Client = {
    val streamId = this.increment
    val parser = new CQLParser().withCompressor(new NoneCompressor())
    val startup = new Startup(CQLVersion.CURRENT, Compression.NONE)
    val body = startup.toBody
    val frame = new Frame(new Header(Version.REQUEST, Flags.NONE, streamId, Opcode.STARTUP, body.length), body)
    val startupBuffer = parser.frameToByteBuffer(frame)
    this.channel.write(startupBuffer)
    val readBuffer = ByteBuffer.allocate(4096)
    this.channel.read(readBuffer)
    readBuffer.flip()
    val resultFrame = parser.byteBufferToFrame(readBuffer)
    val message = parser.frameToMessage(resultFrame)
    this
  }

  private[v3] def close(): Unit = {
    channel.close()
  }
}

