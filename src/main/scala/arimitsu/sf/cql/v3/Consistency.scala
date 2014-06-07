package arimitsu.sf.cql.v3

;

/**
 * Created by sxend on 14/06/04.
 */
object Consistency {

  sealed abstract class Consistency(val value: Short)

  case object ANY extends Consistency(0x0000)

  case object ONE extends Consistency(0x0001)

  case object TWO extends Consistency(0x0002)

  case object THREE extends Consistency(0x0003)

  case object QUORUM extends Consistency(0x0004)

  case object ALL extends Consistency(0x0005)

  case object LOCAL_QUORUM extends Consistency(0x0006)

  case object EACH_QUORUM extends Consistency(0x0007)

  case object SERIAL extends Consistency(0x0008)

  case object LOCAL_SERIAL extends Consistency(0x0009)

  case object LOCAL_ONE extends Consistency(0x000A)

}
