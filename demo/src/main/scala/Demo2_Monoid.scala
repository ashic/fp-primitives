package monoidDemo

import algebra.{Monoid}


case class Price(amount: Double)

object Price {
  implicit val sumMonoid = new Monoid[Price] {
    override def combine(x: Price, y: Price): Price = Price(x.amount + y.amount)

    override def empty: Price = Price(0)
  }
}

case class DiscountedPrice(private val percentage: Double) {
  implicit val sumMonoid = new Monoid[Price] {
    override def combine(x: Price, y: Price): Price = Price(x.amount + y.amount * percentage)
    override def empty: Price = Price(0)
  }
}

case class DiscountedPriceWithBase(private val percentage: Double, private val basePrice: Double) {
  implicit val sumMonoid = new Monoid[Price] {
    override def combine(x: Price, y: Price): Price = Price(x.amount + y.amount * percentage)
    override def empty: Price = Price(basePrice)
  }
}

object Totalz {
  def calculate(items: List[Price]) : Price =
    items.foldLeft(Price(0))((a, b) => Price(a.amount + b.amount))


  import cats.syntax.semigroup._

  def calculate2(items:List[Price]) (implicit s:Monoid[Price]) : Price =
    items.foldLeft(s.empty)(_ |+| _)
}
object Demo2_Monoid extends App {

  val prices = List(Price(1), Price(5), Price(10))
  val total1 = Totalz.calculate(prices)
  println(total1)

  import Price._
  val total2 = Totalz.calculate2(prices)
  println(total2)

  val discount = DiscountedPrice(0.5)
  import discount.sumMonoid
  val total3 = Totalz.calculate2(prices)
  println(total3)

  val discountWithBase = DiscountedPriceWithBase(0.7, 2)
  val total4 = Totalz.calculate2(prices)(discountWithBase.sumMonoid)
  println(total4)
}
