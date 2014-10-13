package test.arimitsu.sf.cql.v3.specs

import arimitsu.sf.cql.v3.message.request.Startup
import arimitsu.sf.cql.v3.message.response.Ready
import arimitsu.sf.cql.v3.{CQLVersion, Compression, Opcode}
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers, OneInstancePerTest}
import test.arimitsu.sf.cql.v3.ClientManager

/**
 * Created by sxend on 14/10/13.
 */
class StartupSpec extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest {
  test("startup") {
    val client = ClientManager.getInstance
    val message = client.request(Opcode.STARTUP, new Startup(CQLVersion.CURRENT, Compression.NONE).toBody)
    message shouldBe a[Ready]
    client.close()
  }
}
