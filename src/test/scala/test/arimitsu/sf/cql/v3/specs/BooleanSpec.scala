package test.arimitsu.sf.cql.v3.specs

import java.nio.ByteBuffer

import arimitsu.sf.cql.v3.{Consistency, Opcode}
import arimitsu.sf.cql.v3.columntype.ColumnTypes
import arimitsu.sf.cql.v3.message.request.{Execute, QueryFlags, QueryParameters, Prepare}
import arimitsu.sf.cql.v3.message.request.QueryParameters.ListValues
import arimitsu.sf.cql.v3.message.response.result.{Rows, Prepared}
import org.scalatest.{OneInstancePerTest, BeforeAndAfter, Matchers, FunSuite}
import test.arimitsu.sf.cql.v3.ClientManager

/**
 * Created by sxend on 14/10/13.
 */
class BooleanSpec extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest {
  val BOOLEAN_COLUMN_NAME = "boolean_column"
  val TEST_DATA = Math.random() > 0.5
  test("boolean insert, select and delete") {
    val client = ClientManager.getInstance.startup()
    val insertQuery = s"INSERT INTO test.test_table1 (id, ${BOOLEAN_COLUMN_NAME}) VALUES(?,?)"
    val insertPreparedId = {
      val message = client.request(Opcode.PREPARE, new Prepare(insertQuery).toBody)
      message match {
        case error: arimitsu.sf.cql.v3.message.response.Error =>
          fail(error.message)
        case _ =>
      }
      message shouldBe a[Prepared]
      message.asInstanceOf[Prepared].id
    }

    val insertResult = {
      val param = {
        val list = new ListValues()
        list.put(ColumnTypes.INT.builder.build(), 3)
        list.put(ColumnTypes.BOOLEAN.builder.build(), TEST_DATA)
        new QueryParameters.Builder()
          .setConsistency(Consistency.ALL)
          .setFlags(QueryFlags.VALUES.mask)
          .setValues(list)
          .build()
      }
      val message = client.request(Opcode.EXECUTE, new Execute(insertPreparedId, param).toBody)
      message match {
        case error: arimitsu.sf.cql.v3.message.response.Error =>
          fail(error.message)
        case _ =>
      }
      message shouldBe a[arimitsu.sf.cql.v3.message.response.result.Void]
    }
    val selectQuery = s"SELECT ${BOOLEAN_COLUMN_NAME} FROM test.test_table1 where id = ?"
    val selectPreparedId = {
      val message = client.request(Opcode.PREPARE, new Prepare(selectQuery).toBody)
      message shouldBe a[Prepared]
      message.asInstanceOf[Prepared].id
    }
    val selectResult = {
      val param = {
        val list = new ListValues()
        list.put(ColumnTypes.INT.builder.build(), 3)
        new QueryParameters.Builder()
          .setConsistency(Consistency.ALL)
          .setFlags(QueryFlags.VALUES.mask)
          .setValues(list)
          .build()
      }
      val message = client.request(Opcode.EXECUTE, new Execute(selectPreparedId, param).toBody)
      message shouldBe a[Rows]
      val rows = message.asInstanceOf[Rows]
      rows.rowsCount should be(1)
      val data = rows.rowsContent.get(0).get(0)
      data.name should be(BOOLEAN_COLUMN_NAME)
      data.value.isInstanceOf[Boolean] should be(true)
      data.value.asInstanceOf[Boolean] should be(TEST_DATA)
    }

    val deleteQuery = s"DELETE ${BOOLEAN_COLUMN_NAME} FROM test.test_table1 where id = ?"
    val deletePreparedId = {
      val message = client.request(Opcode.PREPARE, new Prepare(deleteQuery).toBody)
      message shouldBe a[Prepared]
      message.asInstanceOf[Prepared].id
    }
    val deleteResult = {
      val param = {
        val list = new ListValues()
        list.put(ColumnTypes.INT.builder.build(), 3)
        new QueryParameters.Builder()
          .setConsistency(Consistency.ALL)
          .setFlags(QueryFlags.VALUES.mask)
          .setValues(list)
          .build()
      }
      val message = client.request(Opcode.EXECUTE, new Execute(deletePreparedId, param).toBody)
      message match {
        case error: arimitsu.sf.cql.v3.message.response.Error =>
          fail(error.message)
        case _ =>
      }
      message shouldBe a[arimitsu.sf.cql.v3.message.response.result.Void]
    }
    client.close()
  }

}
