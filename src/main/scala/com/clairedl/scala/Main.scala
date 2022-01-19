package com.clairedl.scala.phonebook

import com.colofabrix.scala.figlet4s.unsafe._
import com.clairedl.scala.phonebook.contact._
import com.clairedl.scala.phonebook.phonebook._
// import scala.io.StdIn.readLine

object Main extends App {
  // Using Figlet4s to render title
  val builder = Figlet4s.builder()
  val title = s"Phonebook!"
  val renderTitle = builder.render(title)

  // Welcome title
  renderTitle.print()

  val phonebook = List(
    Contact("Claire", "+447123456789"),
    Contact("Fabrizio", "+447987654321"),
    Contact("Marchampt", "+33412345678")
  )

  val testing = new PhoneBook("phonebook", phonebook)
  val find = testing.findByName("Claire")
  println(find)
}
