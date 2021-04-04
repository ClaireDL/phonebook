package com.clairedl.scala.phonebook

import com.clairedl.scala.phonebook.phonebookoperations._
import com.clairedl.scala.phonebook.phonebookwriter._
import com.clairedl.scala.phonebook.phonebookloader._
import com.clairedl.scala.phonebook.phoneentry._

object Main extends App {
  val phoneBook = PhoneBookOperations.open(new TextLoader("PhoneBookClaire.txt"))
  phoneBook.foreach(println)

  val newFile: List[PhoneEntry] = List(
    PhoneEntry("Fab", "07498358467"),
    PhoneEntry("Cl", "+447489367152")
  )

  PhoneBookOperations.save(new TextWriter("test2.txt", newFile))
}
