package glass.macros

import glass.macros.internal.{CompanionClass, ContainsFor}

trait DeriveContains:
  given conversion(using
      cc: CompanionClass[this.type],
      contains: ContainsFor[cc.Type]
  ): Conversion[this.type, contains.Out] =
    _ => contains.contains
