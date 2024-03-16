package glass.macros

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.shouldBe

class GenContainsSpec extends AnyFunSuite:
  test("Get") {
    val data = Bar(42)

    val sut = Bar.i.get(data)

    sut shouldBe 42
  }

  test("Set") {
    val data = Bar(42)

    val sut = Bar.i.update(data, _ + 1)

    sut.i shouldBe 43
  }
