package test.akka.cql.client

import akka.actor.ActorSystem
import java.net.InetSocketAddress
import arimitsu.sf.akka.cqlclient.{Configuration, CqlClient}
import scala.util.Success
import scala.util.Failure
import arimitsu.sf.cql.v3.Flags
import arimitsu.sf.cql.v3.messages.Startup

/**
 * Created by sxend on 14/05/31.
 */
object Main {
  def main(args: Array[String]): Unit = {
    implicit val serverSystem = ActorSystem("serverSystem")
    val configuration = Configuration(Seq(new InetSocketAddress("127.0.0.1", 9042)), Flags.COMPRESSION, "lz4", 10)
    val client = CqlClient(configuration)
    val f = client.options()

    import serverSystem.dispatcher
    f.onComplete {
      case Success(s) =>
        println(s.stringMultiMap)
        val startupFuture = client.startup(Map(
          Startup.CQL_VERSION -> Startup.CQL_VERSION_NUMBER,
          Startup.OPTION_COMPRESSION -> configuration.compression))
        startupFuture.onComplete {
          case Success(st) =>
            st match {
              case Left(a) =>
                println(a.className)
              case Right(r) =>
                println(r)
              case _ => println("other")
            }
          case Failure(t) => t.printStackTrace()
        }
      case Failure(t) => t.printStackTrace()
    }
  }
}

