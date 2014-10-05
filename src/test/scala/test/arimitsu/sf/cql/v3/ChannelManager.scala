package test.arimitsu.sf.cql.v3

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import java.util.ResourceBundle

import arimitsu.sf.cql.v3.message.request.Options
import arimitsu.sf.cql.v3.{CqlParser => Parser, Opcode, Compression, Flags}

/**
 * Created by sxend on 2014/09/30.
 */
object ChannelManager {
  private var client = {
    val rb = ResourceBundle.getBundle("test")
    val host = Option(rb.getString("CASSANDRA_HOST"))
    val port = Option(rb.getString("CASSANDRA_PORT")).map(Integer.parseInt)
    val channel = SocketChannel.open()
    channel.connect(new InetSocketAddress(host.getOrElse("127.0.0.1"), port.getOrElse(9042)))
    new Client(channel)
  }
  def getInstance: Client = {
    val old = client
    client = client.next
    old
  }
}
class Client(val channel: SocketChannel, val streamId: Short = 0.toShort) {
  private[v3] def next: Client = new Client(channel, (streamId + 1).toShort)
}

