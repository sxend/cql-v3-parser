package test.arimitsu.sf.cql.v3.specs

import arimitsu.sf.cql.v3.message.request.Options
import arimitsu.sf.cql.v3.message.response.Supported
import arimitsu.sf.cql.v3.{Compression, Opcode}
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers, OneInstancePerTest}
import test.arimitsu.sf.cql.v3.ClientManager

/**
 * Created by sxend on 14/10/13.
 */
class OptionsSpec extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest {
  test("Options Request and Supported feature check") {
    val client = ClientManager.getInstance
    val message = client.request(Opcode.OPTIONS, new Options().toBody)
    message shouldBe a[Supported]
    val supported = message.asInstanceOf[Supported]
    import scala.collection.JavaConversions._
    supported.stringMultiMap.containsKey(Supported.COMPRESSION) should be(right = true)
    supported.stringMultiMap.containsKey(Supported.CQL_VERSION) should be(right = true)
    supported.stringMultiMap.get(Supported.COMPRESSION).forall {
      compression => compression == Compression.LZ4.name || compression == Compression.SNAPPY.name
    } should be(true)
    client.close()
  }
}
