package arimitsu.sf.akka.cqlclient

import akka.actor.Actor
import java.util.concurrent.atomic.AtomicReference
import arimitsu.sf.akka.cqlclient.message._
import akka.io.{Tcp, IO}
import akka.io.Tcp._
import arimitsu.sf.cql.v3._
import akka.util.ByteString
import akka.io.Tcp.Connected
import akka.io.Tcp.Received
import akka.io.Tcp.Register
import akka.io.Tcp.Connect
import akka.io.Tcp.CommandFailed
import java.nio.ByteBuffer
import scala.collection.JavaConversions._
import arimitsu.sf.akka.cqlclient.message.Startup
import akka.io.Tcp.Connected
import arimitsu.sf.akka.cqlclient.NodeConfiguration
import akka.io.Tcp.Received
import akka.io.Tcp.Register
import akka.io.Tcp.Connect
import arimitsu.sf.akka.cqlclient.message.Message
import akka.io.Tcp.CommandFailed
import arimitsu.sf.akka.cqlclient.message.Options

/**
 * Created by sxend on 2014/06/06.
 */
class CqlActor(nodeConfig: NodeConfiguration, eventHandler: EventHandler) extends Actor {

  import context.system

  IO(Tcp) ! Connect(nodeConfig.nodeAddress)

  override def receive = {
    case CommandFailed(c: Connect) =>
      sender() ! "connect failed"
      context stop self
    case c@Connected(remote, local) =>
      val connection = sender()
      connection ! Register(self)
      val streamReference: AtomicReference[Short] = new AtomicReference[Short](1.toShort)
      val compression: Compression = Compression.valueOf(nodeConfig.compression.getBytes)
      val operationMap = scala.collection.mutable.HashMap[Short, Message[_]]()
      def sendMessage(message: Message[_], process: (Short) => ByteBuffer) = {
        val streamId: Short = streamReference.get()
        val result = streamReference.compareAndSet(streamId, (streamId + 1).toShort)
        if (result) {
          operationMap.put(streamId, message)
          connection ! Write(ByteString(process(streamId)))
        } else {
          self ! message
        }
      }
      context.become {
        case option: Options =>
          sendMessage(option, (streamId) =>
            new arimitsu.sf.cql.v3.messages.Options(streamId, nodeConfig.flags).toFrame.toByteBuffer(compression.compressor)
          )
        case startup: Startup =>
          sendMessage(startup, (streamId) =>
            new arimitsu.sf.cql.v3.messages.Startup(streamId, nodeConfig.flags, startup.options).toFrame.toByteBuffer(Compression.NONE.compressor)
          )
        case query: Query =>
          sendMessage(query, (streamId) =>
            new arimitsu.sf.cql.v3.messages.Query(streamId,nodeConfig.flags,query.string,query.parameter).toFrame.toByteBuffer(compression.compressor)
          )
        case Received(data) =>
          val frame = new Frame(data.toByteBuffer, compression.compressor)
          import Opcode._
          frame.header.opcode match {
            case ERROR =>
              frame.header.streamId match {
                case 0 => println(new String(frame.body))
                case _ =>
                  val op = operationMap.remove(frame.header.streamId)
                  op.get.error(arimitsu.sf.cql.v3.messages.Error.ErrorParser.parse(ByteBuffer.wrap(frame.body)))
              }
            //            case AUTHENTICATE =>
            //              val op = operationMap.remove(frame.header.streamId)
            //              op.get.process(frame)
            //            case READY =>
            //              val op = operationMap.remove(frame.header.streamId)
            //              op.get.process(frame)
            //            case RESULT =>
            //              val op = operationMap.remove(frame.header.streamId)
            //              op.get.process(frame)
            //            case SUPPORTED =>
            //              val op = operationMap.remove(frame.header.streamId)
            //              op.get.process(frame)
            //            case AUTH_CHALLENGE =>
            //              val op = operationMap.remove(frame.header.streamId)
            //              op.get.process(frame)
            //            case AUTH_SUCCESS =>
            //              val op =
            //              op.get.process(frame)
            case EVENT =>
              if (frame.header.streamId >= 0) throw new RuntimeException("protocol error.")
            //              eventHandler.handle(new Event(frame))
            case _ =>
              operationMap.remove(frame.header.streamId).get.process(frame)
          }
      }
    case message: Message[_] => self ! message
  }

}
