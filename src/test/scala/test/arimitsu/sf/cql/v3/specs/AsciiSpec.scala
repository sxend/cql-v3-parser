package test.arimitsu.sf.cql.v3.specs

import org.scalatest.{BeforeAndAfter, FunSuite, Matchers, OneInstancePerTest}
import test.arimitsu.sf.cql.v3.ClientManager

/**
 * Created by sxend on 14/10/13.
 */
class AsciiSpec extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest {
  test("ascii spec") {
    val client = ClientManager.getInstance.startup()

    client.close()
  }

}
