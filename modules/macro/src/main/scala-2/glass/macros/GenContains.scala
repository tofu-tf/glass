package glass.macros

import glass.Contains
import glass.macros.internal.MacroImpl

class GenContains[A] {

  /** generate a [[Contains]] between a case class `S` and one of its field */
  def apply[B](field: A => B): Contains[A, B] = macro MacroImpl.genContains_impl[A, B]
}

object GenContains {
  def apply[A] = new GenContains[A]
}
