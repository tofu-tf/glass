package glass.macros

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.annotation.nowarn

@nowarn("cat=other-pure-statement")
class GenSubsetSpec extends AnyFunSuite with Matchers {
  test("Subset narrow") {
    A.b.narrow(C) shouldBe Left(C)
    A.b.narrow(B) shouldBe Right(B)
    A.b.toString() shouldBe ":B"
  }
}
