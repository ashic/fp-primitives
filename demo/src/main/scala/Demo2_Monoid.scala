package monoidDemo

import cats.{Monoid}


case class Price(amount: Double)

object Price {

  implicit val sumMonoid = new Monoid[Price] {
    override def combine(x: Price, y: Price): Price = Price(x.amount + y.amount)

    override def empty: Price = Price(0)
  }
}

object MaybePrice {
  implicit val sumMonoid = new Monoid[Price] {

    override def combine(x: Price, y: Price): Price = {
      val p1 = if (x.amount > 0) x.amount else 0
      val p2 = if (y.amount > 0) y.amount else 0
      Price(p1 + p2)
    }

    override def empty: Price = Price(0)
  }
}

case class MaybePriceWithBase(private val basePrice: Double) {
  implicit val sumMonoid = new Monoid[Price] {

    override def combine(x: Price, y: Price): Price = {
      val p1 = if (x.amount > basePrice) x.amount else 0
      val p2 = if (y.amount > basePrice) y.amount else 0
      Price(p1 + p2)
    }

    override def empty: Price = Price(0)
  }
}

object Totalz {
  def calculate(items: List[Price]): Price =
    items.foldLeft(Price(0))((a, b) => Price(a.amount + b.amount))


  import cats.syntax.semigroup._

  def calculate2(items: List[Price])(implicit s: Monoid[Price]): Price =
    items.foldLeft(s.empty)(_ |+| _)
}

object Demo2_Monoid extends App {

  val prices = List(Price(1), Price(5), Price(10))
  val total1 = Totalz.calculate(prices)
  println(total1)

  import Price._

  val total2 = Totalz.calculate2(prices)
  println(total2)

  import MaybePrice.sumMonoid

  val total3 = Totalz.calculate2(prices)
  println(total3)

  val maybeWithBase = MaybePriceWithBase(2)
  val total4 = Totalz.calculate2(prices)(maybeWithBase.sumMonoid)
  println(total4)
}
