package glass.macros.internal

import glass.*
import quotidian.*
import quotidian.syntax.*
import quoted.*

private[macros] object ContainsMacro:
  def mkContainsImpl[A: Type, B: Type](expr: Expr[A => B])(using Quotes): Expr[PContains[A, A, B, B]] =
    import quotes.reflect.*
    expr.asTerm.underlyingArgument match
      case Lambda(_, select @ Select(a, b)) =>
        val productMirror = MacroMirror.summonProduct[A]

        val elem = productMirror
          .elemForSymbol(select.symbol)
          .getOrElse(
            report.errorAndAbort(s"Invalid selector ${select.show}, must be a field of ${productMirror.monoType.show}")
          )
          .asElemOf[B]

        '{
          new PContains[A, A, B, B]:
            def extract(a: A)   = ${ elem.get('a) }
            def set(a: A, b: B) = ${ elem.set('a, 'b) }
        }
      case other                            => report.errorAndAbort(s"Expected a selector of the form `s => a`, but got: ${other}")

  implicit transparent inline def makeContainses[S]: Any = ${ makeContainsesImpl[S] }

  def makeContainsesImpl[S: Type](using Quotes): Expr[Any] =
    import quotes.reflect.*

    val productMirror = MacroMirror.summonProduct[S]

    val containsMap = Expr.ofMap(productMirror.elems.map { elem =>
      import elem.asType
      val selector = '{ (s: S) => ${ elem.get('s) } }
      elem.label -> mkContainsImpl(selector)
    })

    val refinedType =
      Refinement.of[ContainsSelector[S]](
        productMirror.elemsWithTypes.map { case (elem, '[a]) =>
          elem.label -> TypeRepr.of[PContains[S, S, a, a]]
        }
      )

    refinedType.asType match
      case '[t] =>
        '{ new ContainsSelector[S]($containsMap).asInstanceOf[t] }

private[macros] trait ContainsFor[A]:
  type Out

  def contains: Out

private[macros] object ContainsFor:
  transparent inline given derived[A]: ContainsFor[A] = ${ containsForImpl[A] }

  private def containsForImpl[A: Type](using Quotes) =
    import quotes.reflect.*
    val lensesExpr = ContainsMacro.makeContainsesImpl[A]
    lensesExpr.asTerm.tpe.asType match
      case '[t] =>
        '{
          new ContainsFor[A]:
            type Out = t

            def contains: t = $lensesExpr.asInstanceOf[t]
        }

private[macros] trait CompanionClass[A]:
  type Type

private[macros] object CompanionClass:
  transparent inline given [A]: CompanionClass[A] = ${ companionImpl[A] }

  private def companionImpl[A: Type](using Quotes) =
    import quotes.reflect.*
    val companionClass = TypeRepr.companionClassOf[A]
    if companionClass.typeSymbol.isNoSymbol then report.errorAndAbort(s"No companion class found for ${Type.show[A]}")

    TypeRepr.companionClassOf[A].asType match
      case '[t] =>
        '{
          new CompanionClass[A]:
            type Type = t
        }
