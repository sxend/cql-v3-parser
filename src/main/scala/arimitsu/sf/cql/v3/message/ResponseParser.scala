package arimitsu.sf.cql.v3.message

import java.nio.ByteBuffer


/**
 * Created by sxend on 2014/06/05.
 */
trait ResponseParser[R] {
  def parse(body: ByteBuffer): R
}
