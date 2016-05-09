package functorDemo

import cats.Functor

object Demo3_Functor extends App{

  def myFunc(x:Int) = x+5


  import cats.std.list._
  val res = Functor[List].map(List(1,2,3,4)) (myFunc)
  println(res)

  import cats.syntax.option._
  import cats.std.option._
  val res2 = Functor[Option].map(2.some)(myFunc)
  println(res2)

  val listMyFunc = Functor[List].lift(myFunc)
  val res3 = listMyFunc(List(1, 2, 3))
  println(res3)

  val myListFunctor = new Functor[List] {
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] =
      fa.take(5).map(f)
  }

  val listMyFunc2 = myListFunctor.lift(myFunc)
  val res4 = listMyFunc2(List(1, 2, 3, 4, 5, 6, 7, 8))
  println(res4)
}
