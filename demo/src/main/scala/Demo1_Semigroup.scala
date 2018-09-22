package semigroupDemo

import cats.Semigroup

case class Price(amount: Double)

object Price {
  implicit val sumSemigroup = new Semigroup[Price] {
    override def combine(x: Price, y: Price): Price = Price(x.amount + y.amount)
  }
}

object MaybePrice {
  implicit val sumSemigroup = new Semigroup[Price] {
    override def combine(x: Price, y: Price): Price = {
      val p1 = if (x.amount > 0) x.amount else 0
      val p2 = if (y.amount > 0) y.amount else 0
      Price(p1 + p2)
    }
  }
}


object Totalz {
  def calculate(items: List[Price]): Price =
    items.foldLeft(Price(0))((a, b) => Price(a.amount + b.amount))

  import cats.syntax.semigroup._

  def calculate2(items: List[Price])(implicit s: Semigroup[Price]): Price =
    items.foldLeft(Price(0))(_ |+| _)

  def calculate3[T](items: List[T])(point: T)(implicit s: Semigroup[T]): T =
    items.foldLeft(point)(_ |+| _)
}

object Demo1_Semigroup extends App {

  val prices = List(Price(1), Price(5), Price(10))

  val total1 = Totalz.calculate(prices)
  println(total1)

  val total2 = Totalz.calculate2(prices)(Price.sumSemigroup)
  println(total2)

  import Price._

  val total2b = Totalz.calculate2(prices)

  import MaybePrice.sumSemigroup

  val total3 = Totalz.calculate2(prices)
  println(total3)


  val newPrices = List(Price(1), Price(-25), Price(2))
  val total4 = Totalz.calculate2(newPrices)

  println(total4)

  import Price._
  val total5 = Totalz.calculate2(newPrices)
  println(total5)


}
