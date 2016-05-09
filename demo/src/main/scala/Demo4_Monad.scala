package monadDemo

import cats.Monad

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Book(id:Int, title:String, links: List[Book] = Nil) {
  def link(other: Book) = Book(id, title, other :: links)
}

object Catalog{
  private val books =
    Map(
      10 -> Book(10, "book-10"),
      20 -> Book(20, "book-10"),
      30 -> Book(30, "book-10"),
      40 -> Book(40, "book-10")
  )

  def getBookAsync(id:Int) : Future[Option[Book]] =
    Future(getBook(id))

  def getBook(id:Int) : Option[Book] =
    books.get(id)
}



object Demo4_Monad extends App {

  val book = for {
    a <- Catalog.getBook(10)
    b <- Catalog.getBook(20)
  } yield a.link(b)

  println(book)

  val book2 = for {
    a <- Catalog.getBook(1)
    b <- Catalog.getBook(10)
  } yield a.link(b)

  println(book2)

  val book3 = for {
    a <- Catalog.getBook(10)
    b <- Catalog.getBook(11)
  } yield a.link(b)

  println(book3)

  val myMonad = new Monad[List] {
    override def flatMap[A, B](fa: List[A])(f: (A) => List[B]): List[B] = {
      fa.flatMap(a => f(a) ++ f(a))
    }

    override def pure[A](x: A): List[A] = List(x, x)
  }

  val f = myMonad.lift((x:Int) => x.toString)

  val res = f(List(2, 3))
  println(res)

}
