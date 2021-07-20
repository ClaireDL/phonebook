package com.clairedl.scala

import com.clairedl.scala.phonebook.phonebookoperations._
import com.clairedl.scala.phonebook.phonebookwriter._
import com.clairedl.scala.phonebook.phonebookloader._
import com.clairedl.scala.phonebook.phoneentry._
import com.clairedl.scala.phonebook.validation._
import scala.util.matching.Regex
import scala.io.StdIn.readLine

object Main extends App {

  val phonebookOperationsText = s"Choose one of the following options (type the corresponding letter): \n" +
    s"O: open an existing phonebook \n" +
    s"C: create a new phonebook \n" +
    s"S: save the phonebook \n" +
    s"Q: save and quit the program \n" +
    s"W: quit witout saving changes \n"

  def menuPhonebook(): Unit = {
    val phonebookOperator = new PhonebookOperations

    println(phonebookOperationsText)
    val choice = readLine()
    choice match {
      case "O" | "o"  => {
        println("Opening a phonebook")
        val textfile = readLine("What is the textfile name? ")
        val checkTextfile = TextFileNameValidation.validate(textfile)
        if (checkTextfile.isInstanceOf[Invalid]) {
          println("The name you typed is not formatted correctly. \n")
          menuPhonebook()
        }
        else {
          val checkFile = FilePathValidation.validate(textfile)
          if (checkFile.isInstanceOf[Invalid]) {
            println("Could not find the file. \n")
            menuPhonebook()
          }
          else {
            val tempPhonebook = phonebookOperator.load(new FileLoader(textfile))
            menuContact(tempPhonebook)
          }
        }
      }
      case "C" | "c"  => {
        println("Creating a new phonebook")
        menuContact(List[PhoneEntry]())
      }
      case "S" | "s"  => {
        // Add save/write
        println("Save and close the current phonebook")
        menuPhonebook()
      }
      case "Q" | "q"  => {
        // Add save/write
        println("The phonebook was saved. Goodbye!")
      }
      case "W" | "w"  => println("No change was saved. Goodbye!")
      case _          => {
        println("This instruction does not exist")
        menuPhonebook()
      }
    }
  }

  val contactOperationsText = s"Choose one of the following actions: \n" +
    s"A: add a contact \n" +
    s"F: find a contact \n" +
    s"D: delete a contact \n" +
    s"S: save the last changes to the Phonebook \n" +
    s"E: exit Phonebook without save the last changes \n"

  def menuContact(tempPhonebook: List[PhoneEntry]): Unit = {
    val contactOperator = new PhonebookOperations

    println(contactOperationsText)
    val choice = readLine()

    choice match {
      case "A" | "a"  => {
        println("Add a new contact")
        val newName = readLine("Name of the contact? ")
        if (contactOperator.findName(newName,tempPhonebook).isEmpty) {
          println(s"This is a new name! \n")
        }
        else {
          println(s"The name already exists \n")
          menuContact(tempPhonebook)
        }

        val newPhoneNumber = readLine("Phone number? Format: +127123456789 \n")
        val checkNumber = PhoneNumberValidation.validate(newPhoneNumber)
        if (checkNumber.isInstanceOf[Valid]) contactOperator.add(PhoneEntry(newName, newPhoneNumber), tempPhonebook)
        else {
          println(s"The format is incorrect, it should be: +127123456789 \n")
          menuContact(tempPhonebook)
        }

        menuContact(tempPhonebook)
      }
      case "F" | "f"  => {
        println("Find a contact")
        menuContact(tempPhonebook)
      }
      case "D" | "d"  => {
        println("Delete a contact")
        menuContact(tempPhonebook)
      }
      case "S" | "s"  => println("Save and close phonebook")
      case "E" | "e"  => println("Close without saving")
      case _          => menuContact(tempPhonebook)
    }
  }

  println(s"Welcome to the Phonebook App!\n")
  menuPhonebook()

}
