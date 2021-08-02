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


  // val phoneBook = new PhonebookOperations
  // val loaded = phoneBook.load(new FileLoader("PhoneBookClaire.txt"))

  // val contactOperations = new ContactOperations

  // println("New contact is Fabrizio with phone number +447123456789")
  // val updatedPhonebook = contactOperations.addContact(PhoneEntry("Fabrizio", "+447123456789"), loaded)
  // println("This is the phonebook with the new contact")
  // updatedPhonebook.foreach(println)

  // println("Find the name 'Claire'")
  // println(contactOperations.findName("Claire", updatedPhonebook))

  // println("Saving the updated phonebook")
  // val savedPhonebook = phoneBook.save(new TextWriter("PhoneBookClaireSaved.txt", updatedPhonebook))

  // println("Closing with saving modifications")
  // val notSavedPhonebook = phoneBook.save(new TextWriter("PhoneBookClaireNotSaved.txt", loaded))

  // With user interface
  val interface = new UserInterface
  interface.menuPhonebook()
}
