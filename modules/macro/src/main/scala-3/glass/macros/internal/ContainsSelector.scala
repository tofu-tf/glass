package glass.macros.internal

import glass.*

private[macros] class ContainsSelector[A](lenses: Map[String, PContains[A, A, ?, ?]]) extends Selectable:
  inline def selectDynamic(name: String): PContains[A, A, ?, ?] =
    lenses(name)
