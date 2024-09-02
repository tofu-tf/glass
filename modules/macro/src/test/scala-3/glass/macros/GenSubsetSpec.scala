package glass.macros

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.shouldBe

class GenSubsetSpec extends AnyFunSuite:
  test("Subset narrow") {
    val subset = GenSubset[A, B.type]

    val _ = subset.narrow(C) shouldBe Left(C)
    subset.narrow(B) shouldBe Right(B)
  }
