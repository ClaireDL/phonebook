package com.clairedl.scala.phonebook

import com.colofabrix.scala.figlet4s.unsafe._
// import scala.io.StdIn.readLine

object Main extends App {
  // Using Figlet4s to render title
  val builder = Figlet4s.builder()
  val title = s"Phonebook!"
  val renderTitle = builder.render(title)

  // Welcome title
  renderTitle.print()


}
