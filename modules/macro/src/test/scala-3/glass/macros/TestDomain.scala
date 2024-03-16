package glass.macros

sealed trait A
case object B extends A
case object C extends A

case class Bar(i: Int)
object Bar extends DeriveContains
