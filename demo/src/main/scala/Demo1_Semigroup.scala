package semigroupDemo

import cats.Semigroup

case class Price(amount: Double)

object Price {
  implicit val sumSemigroup = new Semigroup[Price] {
    override def combine(x: Price, y: Price): Price = Price(x.amount + y.amount)
  }
}

case class DiscountedPrice(private val percentage: Double) {
  implicit val sumSemigroup = new Semigroup[Price] {
    override def combine(x: Price, y: Price): Price = Price(x.amount + y.amount * percentage)
  }
}


object Totalz {
  def calculate(items: List[Price]): Price =
    items.foldLeft(Price(0))((a, b) => Price(a.amount + b.amount))

  import cats.syntax.semigroup._

  def calculate2(items:List[Price]) (implicit s:Semigroup[Price]) : Price =
    items.foldLeft(Price(0))(_ |+| _)

  def calculate3[T](items:List[T])(point:T) (implicit s:Semigroup[T]) : T =
    items.foldLeft(point)(_ |+| _)
}

object Demo1_Semigroup extends App {

  val prices = List(Price(1), Price(5), Price(10))

  val total1 = Totalz.calculate(prices)
  println(total1)

  val total2 = Totalz.calculate2(prices) (Price.sumSemigroup)
  println(total2)

  import Price._
  val total2b = Totalz.calculate2(prices)

  val discount = DiscountedPrice(0.5)
  import discount.sumSemigroup
  val total3 = Totalz.calculate2(prices)
  println(total3)
}
