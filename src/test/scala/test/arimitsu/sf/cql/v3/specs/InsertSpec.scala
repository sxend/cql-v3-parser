//package test.arimitsu.sf.cql.v3.specs
//
//import java.math.BigInteger
//import java.net.InetAddress
//import java.nio.ByteBuffer
//import java.util
//import java.util.UUID
//
//import arimitsu.sf.cql.v3.columntype.ColumnTypes
//import arimitsu.sf.cql.v3.message.request.QueryParameters.{Builder, ListValues}
//import arimitsu.sf.cql.v3.message.request.{QueryFlags, Execute, Prepare}
//import arimitsu.sf.cql.v3.message.response.result.Prepared
//import arimitsu.sf.cql.v3.{Consistency, Flags, Opcode}
//import org.scalatest.{BeforeAndAfter, FunSuite, Matchers, OneInstancePerTest}
//import test.arimitsu.sf.cql.v3.ClientManager
//
///**
// * Created by sxend on 14/10/13.
// */
//class InsertSpec extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest {
//  val selectQuery = s"select id,ascii_column,decimal_column,int_column,text_column,uuid_column,bigint_column,double_column,list_column,timestamp_column,varchar_column,blob_column,float_column,map_column,timeuuid_column,varint_column,boolean_column,inet_column,set_column,tuple_column from test.test_table1 where id = ?"
//  val id_name = "id"
//  val ascii_column_name = "ascii_column"
//  val decimal_column_name = "decimal_column"
//  val int_column_name = "int_column"
//  val text_column_name = "text_column"
//  val uuid_column_name = "uuid_column"
//  val bigint_column_name = "bigint_column"
//  val double_column_name = "double_column"
//  val list_column_name = "list_column"
//  val timestamp_column_name = "timestamp_column"
//  val varchar_column_name = "varchar_column"
//  val blob_column_name = "blob_column"
//  val float_column_name = "float_column"
//  val map_column_name = "map_column"
//  val timeuuid_column_name = "timeuuid_column"
//  val varint_column_name = "varint_column"
//  val boolean_column_name = "boolean_column"
//  val inet_column_name = "inet_column"
//  val set_column_name = "set_column"
//  val tuple_column_name = "tuple_column"
//  val id_value = 3
//  val ascii_column_value = "Ascii"
//  val decimal_column_value = {
//    new java.math.BigDecimal(new java.math.BigInteger(new Array[Byte](1.toByte)),0)
//  }
//  val int_column_value = 1
//  val text_column_value = "テキスト"
//  val uuid_column_value = UUID.randomUUID()
//  val bigint_column_value = 100000L
//  val double_column_value = 1.01d
//  val list_column_value = {
//    val list = new java.util.ArrayList[String]()
//    list.add("リスト値1")
//    list.add("リスト値2")
//    list
//  }
//  val timestamp_column_value = new java.util.Date()
//  val varchar_column_value = "ｳﾞｧｰキャラカラム"
//  val blob_column_value = {
//    val num = 0x0000000000000001L
//    val buffer = ByteBuffer.allocate(8)
//    buffer.putLong(num)
//    buffer.array()
//  }
//  val float_column_value = 0.1f
//  val map_column_value = {
//    val map = new util.HashMap[String, String]()
//    map.put("キー", "ヴァリュー")
//    map.put("キー2", "ヴァリュー2")
//    map
//  }
//  val timeuuid_column_value = UUID.randomUUID()
//  val varint_column_value = {
//    new java.math.BigInteger(new Array[Byte](1.toByte))
//  }
//  val boolean_column_value = true
//  val inet_column_value = InetAddress.getByName("20.20.20.20")
//  val set_column_value = {
//    val set = new util.HashSet[String]()
//    set.add("セットヴァリュー1")
//    set.add("セットヴァリュー2")
//    set
//  }
//  val tuple_column_value = {
//    val list = new util.ArrayList[Any]()
//    list.add("タプル1")
//    list.add(InetAddress.getByName("20.20.20.20"))
//    list.add(3.14)
//    list
//  }
//  //  val insertQuery =
//  //    s"""|INSERT INTO test_table1 (${id_name},${ascii_column_name},${decimal_column_name},${int_column_name},${text_column_name},${uuid_column_name},${bigint_column_name},${double_column_name},${list_column_name},${timestamp_column_name},${varchar_column_name},${blob_column_name},${float_column_name},${map_column_name},${timeuuid_column_name},${varint_column_name},${boolean_column_name},${inet_column_name},${set_column_name},${tuple_column_name})
//  //      |             VALUES (${id_value},${ascii_column_value},${decimal_column_value},${int_column_value},${text_column_value},${uuid_column_value},${bigint_column_value},${double_column_value},${list_column_value},${timestamp_column_value},${varchar_column_value},${blob_column_value},${float_column_value},${map_column_value},${timeuuid_column_value},${varint_column_value},${boolean_column_value},${inet_column_value},${set_column_value},${tuple_column_value})
//  //    """.stripMargin
//  val insertQuery = s"INSERT INTO test.test_table1 (id,ascii_column,decimal_column,int_column,text_column,uuid_column,bigint_column,double_column,list_column,timestamp_column,varchar_column,blob_column,float_column,map_column,timeuuid_column,varint_column,boolean_column,inet_column,set_column,tuple_column) VALUES (${id_value},?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
//  val deleteQuery = s"DELETE FROM test.test_table1 where id = ?"
//  test("select, insert, select, delete all column") {
//    val client = ClientManager.getInstance.startup()
//
//    val insertId = {
//      val insPre = new Prepare(insertQuery)
//      val message = client.request(Opcode.PREPARE, insPre.toBody)
//      if (message.isInstanceOf[arimitsu.sf.cql.v3.message.response.Error]) {
////        println(message.asInstanceOf[arimitsu.sf.cql.v3.message.response.Error].message)
//      }
//      message shouldBe a[Prepared]
//      message.asInstanceOf[Prepared].id
//    }
//    val insertResult = {
//      val listValues = new ListValues()
//      listValues.put(ColumnTypes.ASCII.builder.build(), ascii_column_value)
//      listValues.put(ColumnTypes.DECIMAL.builder.build(), decimal_column_value)
//      listValues.put(ColumnTypes.INT.builder.build(), int_column_value)
//      listValues.put(ColumnTypes.VARCHAR.builder.build(), text_column_value)
//      listValues.put(ColumnTypes.UUID.builder.build(), uuid_column_value)
//      listValues.put(ColumnTypes.BIGINT.builder.build(), bigint_column_value)
//      listValues.put(ColumnTypes.DOUBLE.builder.build(), double_column_value)
//      listValues.put(ColumnTypes.LIST.builder.build(ColumnTypes.VARCHAR.builder.build()), list_column_value)
//      listValues.put(ColumnTypes.TIMESTAMP.builder.build(), timestamp_column_value)
//      listValues.put(ColumnTypes.VARCHAR.builder.build(), varchar_column_value)
//      listValues.put(ColumnTypes.BLOB.builder.build(), blob_column_value)
//      listValues.put(ColumnTypes.FLOAT.builder.build(), float_column_value)
//      listValues.put(ColumnTypes.MAP.builder.build(ColumnTypes.VARCHAR.builder.build(), ColumnTypes.VARCHAR.builder.build()), map_column_value)
//      listValues.put(ColumnTypes.TIMEUUID.builder.build(), timeuuid_column_value)
//      listValues.put(ColumnTypes.VARINT.builder.build(), varint_column_value)
//      listValues.put(ColumnTypes.BOOLEAN.builder.build(), boolean_column_value)
//      listValues.put(ColumnTypes.INET.builder.build(), inet_column_value)
//      listValues.put(ColumnTypes.SET.builder.build(ColumnTypes.VARCHAR.builder.build()), set_column_value)
//      listValues.put(ColumnTypes.TUPLE.builder.build(ColumnTypes.VARCHAR.builder.build(), ColumnTypes.INET.builder.build(), ColumnTypes.DOUBLE.builder.build()), tuple_column_value)
//
//      val param = new Builder()
//        .setConsistency(Consistency.ALL)
//        .setFlags(QueryFlags.VALUES.mask)
//        .setValues(listValues)
//        .build()
//      val execute = new Execute(insertId, param)
//      client.request(Opcode.EXECUTE, execute.toBody)
//    }
//    if (insertResult.isInstanceOf[arimitsu.sf.cql.v3.message.response.Error]) {
//      println(insertResult.asInstanceOf[arimitsu.sf.cql.v3.message.response.Error].message)
//    }
//    insertResult shouldBe a[Void]
//
//    val deleteId = {
//      val deletePre = new Prepare(deleteQuery)
//      val message = client.request(Opcode.PREPARE, deletePre.toBody)
//      if (message.isInstanceOf[arimitsu.sf.cql.v3.message.response.Error]) {
//        println(message.asInstanceOf[arimitsu.sf.cql.v3.message.response.Error].message)
//      }
//      message shouldBe a[Prepared]
//      message.asInstanceOf[Prepared].id
//    }
//    val deleteResult = {
//      val listValues = new ListValues()
//      listValues.put(ColumnTypes.INT.builder.build(), id_value)
//      val param = new Builder()
//        .setConsistency(Consistency.ALL)
//        .setFlags(Flags.NONE.value)
//        .setValues(listValues)
//        .build()
//      val execute = new Execute(deleteId, param)
//      client.request(Opcode.EXECUTE, execute.toBody)
//    }
//
//    deleteResult shouldBe a[Void]
//
//    client.close()
//  }
//}
