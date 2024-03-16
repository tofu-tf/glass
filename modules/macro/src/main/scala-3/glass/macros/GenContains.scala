package glass.macros

import scala.quoted.*

import glass.Contains
import glass.macros.internal.*

object GenContains:
  def apply[A]: MkContains[A] = new MkContains[A]

class MkContains[A]:
  inline def apply[B](inline expr: A => B): Contains[A, B] = ${ ContainsMacro.mkContainsImpl('expr) }
