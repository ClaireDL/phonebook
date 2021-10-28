package com.clairedl.scala

import com.clairedl.scala.phonebook.phonebookoperations._
import com.clairedl.scala.phonebook.contactoperations._
import com.clairedl.scala.phonebook.phonebookwriter._
import com.clairedl.scala.phonebook.phonebookloader._
import com.clairedl.scala.phonebook.phoneentry._
import com.clairedl.scala.phonebook.validation._
import com.clairedl.scala.phonebook.userinterface._
import scala.collection.mutable.ListBuffer

object Main extends App {
  val interface = new UserInterface
  interface.menuPhonebook(List[PhoneEntry](), List[PhoneEntry](), "")
}
