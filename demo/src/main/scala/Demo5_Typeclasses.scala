

sealed trait PrintMe[T] {
  def print(value: T): Unit
}


case class Person(name: String)

case class Job(title: String)


object Printer {
  def print[A](a: A)(implicit printMe: PrintMe[A]) = {
    printMe.print(a)
  }
}

object Person {
  implicit val personPrinter = new PrintMe[Person] {
    override def print(value: Person): Unit = {
      println(s"I am a person. My name is ${value.name}")
    }
  }
}

object Job {
  implicit val jobPrinter = new PrintMe[Job] {
    override def print(value: Job): Unit = {
      println(s"This is a job with title ${value.title}")
    }
  }
}


object PrinterSyntax {
  implicit class PrinterOps[A](value: A) {
    def print()(implicit printer: PrintMe[A]): Unit = {
      printer.print(value)
    }
  }
}


object Demo5_Typeclasses extends App {
  val person = Person("Donald")
  val job = Job("Putin's Aid")

  import Person._
  import Job._


  Printer.print(person)
  Printer.print(job)

  import PrinterSyntax._

  person.print()
  job.print()
}
