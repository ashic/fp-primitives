package functorDemo

import cats.Functor

object Demo3_Functor extends App{

  def myFunc(x:Int): Int = x+5  //simple function....works on primitives


  import cats.instances.list._
  val res = Functor[List].map(List(1,2,3,4)) (myFunc) //function now works on lists
  println(res)

  import cats.syntax.option._
  import cats.instances.option._
  val res2 = Functor[Option].map(2.some)(myFunc)  //function now works on options
  println(res2)

  val listMyFunc = Functor[List].lift(myFunc)     //"lift" the function to work on lists
  val res3 = listMyFunc(List(1, 2, 3))
  println(res3)
//
  //our custom functor
  def write[A](x:A) = println(s"writing out $x")
  val myListFunctor = new Functor[List] {
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] =
      fa.map(x => {
        write(x)
        f(x)
      })
  }
//
  val listMyFunc2 = myListFunctor.lift(myFunc)    //function lifted using our own functor
  val res4 = listMyFunc2(List(1, 2, 3, 4, 5, 6, 7, 8))
  println(res4)
//
//
//  // Functors compose!
  val listOpt = Functor[Option] compose Functor[List]
  val flo = listOpt.lift(myFunc)  //function works on Option[List]
  println(flo(Some(List(1,2,3,4))))
//
  val optList = Functor[List] compose Functor[Option]
  val fol = optList.lift (myFunc)  //function works on List[Option]
  println(fol(List(2.some, none[Int], 3.some)))
//
  val listOptList = optList compose Functor[List]
  val flol = listOptList.lift (myFunc)  //function works on List[Option[List]]
  println(flol(
    List(
      Some(List(1,2,3)),
      none[List[Int]],
      Some(List(4,5,6)))))
}


