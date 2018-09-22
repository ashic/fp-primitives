import cats.data.{ValidatedNel}

case class Customer(name:String, age: Int)



object Demo6_Applicative extends App {

  import cats.syntax.validated._
  import cats.syntax.apply._

  type ValidationResult[A] = ValidatedNel[String, A]

  def validateName(name:String): ValidationResult[String] =
    if(name.length > 1) name.validNel
    else "Name has to be more than one character".invalidNel

  def validateAge(age:Int):ValidationResult[Int] =
    if(age > 16) age.validNel
    else "Age has to be more than 16".invalidNel


  def validatePerson(name:String, age: Int) : ValidationResult[Customer] =
    (validateName(name), validateAge(age)).mapN(Customer)


  println(validatePerson("John", 21))
  println(validatePerson("J", 1))

}


