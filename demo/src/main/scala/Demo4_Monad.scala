package monadDemo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

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

  def getBook(id:Int) : Option[Book] =
    books.get(id)
}
//
//
//
object Demo4_Monad extends App {

  val book: Option[Book] = for {
    a <- Catalog.getBook(10) // Option[Book]
    b <- Catalog.getBook(20)
  } yield a.link(b)


  println(book)

  val book2 = for {
    a <- Catalog.getBook(1)
    b <- Catalog.getBook(10)
  } yield a.link(b)

  println(book2)

//  val book3 = for {
//    a <- Catalog.getBook(10)
//    b <- Catalog.getBook(11)
//  } yield a.link(b)
//
//  println(book3)
//
  val f1: Future[Int] = for {
    a <- Future(10)
    b <- Future(20)
    c <- Future(30)
  } yield (a + b + c)

//  val f2: Future[Int] = Future(10).flatMap(a => Future(20).flatMap(b => Future(30).map(c => (a + b + c))))
//
//
//  import scala.concurrent.duration._
//  println(Await.result(f1, 2 seconds))
//  println(Await.result(f2, 2 seconds))
//
//  import cats.Monad
//  import cats.instances.all._
//  import cats.syntax.flatMap._
//  val fi: Future[Int] = Monad[Future].pure(20) >>= { x => Future(x + 20)}
//
//  println(Await.result(fi, 2 seconds))
}
