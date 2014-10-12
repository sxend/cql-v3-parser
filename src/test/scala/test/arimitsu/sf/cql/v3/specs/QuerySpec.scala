package test.arimitsu.sf.cql.v3.specs

import java.net.InetAddress
import java.nio.ByteBuffer

import arimitsu.sf.cql.v3.Frame.Header
import arimitsu.sf.cql.v3._
import arimitsu.sf.cql.v3.compressor.NoneCompressor
import arimitsu.sf.cql.v3.message.request.QueryParameters.{Builder, ListValues}
import arimitsu.sf.cql.v3.message.request.{Query, QueryFlags, Startup}
import arimitsu.sf.cql.v3.message.response.Ready
import arimitsu.sf.cql.v3.message.response.result.Rows
import arimitsu.sf.cql.v3.util.Notations
import org.scalatest._
import test.arimitsu.sf.cql.v3.ChannelManager

import scala.collection.JavaConversions._

/**
 * Created by sxend on 2014/10/02.
 */
class QuerySpec extends FlatSpec with Matchers {
  "Options Response" should "be Supported" in {
    var client = ChannelManager.getInstance
    val parser = new CQLParser().withCompressor(new NoneCompressor())

    var body = {
      new Startup(CQLVersion.CURRENT, Compression.NONE).toBody
    }
    var frame = new Frame(new Header(Version.REQUEST, Flags.NONE, client.streamId, Opcode.STARTUP, body.length), body)
    client.channel.write(parser.frameToByteBuffer(frame))
    var resultBuffer = ByteBuffer.allocate(4096)
    client.channel.read(resultBuffer)
    resultBuffer.flip()
    var resultFrame = parser.byteBufferToFrame(resultBuffer)
    var message = parser.frameToMessage(resultFrame)
    message.isInstanceOf[Ready] should be(true)

    client = client.next

    body = {
      val param = new Builder()
        .setConsistency(Consistency.ONE)
        .setFlags(QueryFlags.VALUES.mask)
        .setValues(new ListValues()).build()
      new Query("select id,ascii_column,decimal_column,int_column,text_column,uuid_column,bigint_column,double_column,list_column,timestamp_column,varchar_column,blob_column,float_column,map_column,timeuuid_column,varint_column,boolean_column,inet_column,set_column from test.test_table1 where id = 1", param).toBody
    }
    frame = new Frame(new Header(Version.REQUEST, Flags.NONE, client.streamId, Opcode.QUERY, body.length), body)
    client.channel.write(parser.frameToByteBuffer(frame))
    resultBuffer = ByteBuffer.allocate(4096)
    client.channel.read(resultBuffer)
    resultBuffer.flip()
    resultFrame = parser.byteBufferToFrame(resultBuffer)
    message = parser.frameToMessage(resultFrame)
    message.isInstanceOf[Rows] should be(true)
    var rows = message.asInstanceOf[Rows]
    rows.rowsCount should be(1)
    var record = rows.rowsContent(0)
    record(0).name should be("id")
    record(0).value should be(1)
    record(1).name should be("ascii_column")
    record(1).value should be("Ascii")
    record(2).name should be("decimal_column")
    record(2).value should be(new java.math.BigDecimal(new java.math.BigInteger(Array[Byte](1)), 0))
    record(3).name should be("int_column")
    record(3).value should be(1)
    record(4).name should be("text_column")
    record(4).value should be("テキスト")
    record(5).name should be("uuid_column")
    record(5).value.toString should be("550e8400-e29b-41d4-a716-446655440000")
    record(6).name should be("bigint_column")
    record(6).value should be(100000)
    record(7).name should be("double_column")
    record(7).value should be(1.01)
    record(8).name should be("list_column")
    record(8).value.asInstanceOf[java.util.List[String]].toList should be(List("リスト値1", "リスト値2"))
    record(9).name should be("timestamp_column")
    record(9).value.asInstanceOf[java.util.Date].getTime should be(1402726004000L)
    record(10).name should be("varchar_column")
    record(10).value should be("ｳﾞｧｰキャラカラム")
    record(11).name should be("blob_column")
    record(11).value.asInstanceOf[Array[Byte]] should be(Notations.toLongBytes(0x0000000000000001))
    record(12).name should be("float_column")
    record(12).value should be(0.1.toFloat)
    record(13).name should be("map_column")
    record(13).value.asInstanceOf[java.util.Map[String, String]].toMap should be(Map("キー" -> "ヴァリュー", "キー2" -> "ヴァリュー2"))
    record(14).name should be("timeuuid_column")
    //    record(14).value.toString should be("98157292-4aa1-11e4-8b15-4f0614c20ed3")
    record(15).name should be("varint_column")
    record(15).value should be(new java.math.BigInteger(Array[Byte](1)))
    record(16).name should be("boolean_column")
    record(16).value.asInstanceOf[Boolean] should be(true)
    record(17).name should be("inet_column")
    record(17).value.toString should be(InetAddress.getByName("20.20.20.20").toString)
    record(18).name should be("set_column")
    record(18).value.asInstanceOf[java.util.Set[String]].toSet should be(Set("セットヴァリュー1", "セットヴァリュー2"))
  }
}
