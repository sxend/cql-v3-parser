package test.arimitsu.sf.cql.v3.specs

import arimitsu.sf.cql.v3.columntype.ColumnTypes
import arimitsu.sf.cql.v3.message.request.QueryParameters.{Builder, ListValues}
import arimitsu.sf.cql.v3.message.request.{Execute, Prepare, QueryFlags}
import arimitsu.sf.cql.v3.message.response.result.{Prepared, Rows}
import arimitsu.sf.cql.v3.{Consistency, Opcode}
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers, OneInstancePerTest}
import test.arimitsu.sf.cql.v3.ClientManager

/**
 * Created by sxend on 14/10/13.
 */
class VarcharUpdateSpec extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest {
  test("prepare, update, select check for [varchar]") {
    val client = ClientManager.getInstance.startup()
    val prepareQuery = s"update test.test_table1 set varchar_column = ? where id = ?"
    val updatePrepare = new Prepare(prepareQuery)
    var message = client.request(Opcode.PREPARE, updatePrepare.toBody)
    message shouldBe a[Prepared]
    val updatePrepared = message.asInstanceOf[Prepared]
    val updateQueryId = updatePrepared.id
    val varchar = "ｳﾞｧｰキャラカラム"
    val updateExecute = {
      val param = new Builder()
        .setConsistency(Consistency.ONE)
        .setFlags(QueryFlags.VALUES.mask)
        .setValues({
        val list = new ListValues()
        list.put(ColumnTypes.VARCHAR.builder.build(), varchar)
        list.put(ColumnTypes.INT.builder.build(), 1)
        list
      }).build()
      new Execute(updateQueryId, param)
    }
    message = client.request(Opcode.EXECUTE, updateExecute.toBody)
    message shouldBe a[arimitsu.sf.cql.v3.message.response.result.Void]

    val selectQuery = s"select varchar_column from test.test_table1 where id = ?"
    val selectPrepare = new Prepare(selectQuery)
    message = client.request(Opcode.PREPARE, selectPrepare.toBody)
    message shouldBe a[Prepared]
    val selectPrepared = message.asInstanceOf[Prepared]
    val selectQueryId = selectPrepared.id
    val selectExecute = {
      val param = new Builder()
        .setConsistency(Consistency.ONE)
        .setFlags(QueryFlags.VALUES.mask)
        .setValues({
        val list = new ListValues()
        list.put(ColumnTypes.INT.builder.build(), 1)
        list
      }).build()
      new Execute(selectQueryId, param)
    }
    message = client.request(Opcode.EXECUTE, selectExecute.toBody)
    message shouldBe a[Rows]
    val resultRows = message.asInstanceOf[Rows]
    resultRows.rowsCount should be(1)
    val resultValue = resultRows.rowsContent.get(0).get(0)
    resultValue.name should be("varchar_column")
    resultValue.value should be(varchar)
    client.close()
  }
}
