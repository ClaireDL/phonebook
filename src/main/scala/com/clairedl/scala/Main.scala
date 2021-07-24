package com.clairedl.scala

import com.clairedl.scala.phonebook.phonebookoperations._
import com.clairedl.scala.phonebook.phonebookwriter._
import com.clairedl.scala.phonebook.phonebookloader._
import com.clairedl.scala.phonebook.phoneentry._
import com.clairedl.scala.phonebook.validation._
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
        val tempPhonebook = phonebookOperator.validateAndLoadTextfile(textfile)
        tempPhonebook match {
          case Some(x) => menuContact(x)
          case None    => {
            println(s"File not found/Incorrect name")
            menuPhonebook()
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
        val newNumber = readLine("Phone number? Format has to be: +441234567890 " )
        contactOperator.validateNewContact(newName, newNumber, tempPhonebook) match {
          case Some(x)  => {
            val updatedPhonebook = contactOperator.add(x,tempPhonebook)
            menuContact(updatedPhonebook)
          }
          case None     => {
            val findDuplicateName = contactOperator.findName(newName, tempPhonebook)
            if (! findDuplicateName.isEmpty) {
              println("The name already exists.")
              findDuplicateName.foreach(println)
              menuContact(tempPhonebook)
            }
            val validateNumber = PhoneNumberValidation.validate(newNumber)
            if (validateNumber.isInstanceOf[Valid]) {
              val findDuplicateNumber = contactOperator.findNumber(validateNumber, tempPhonebook)
              if (! findDuplicateNumber.isEmpty) {
                println("The number already exists.")
                findDuplicateNumber.foreach(println)
                menuContact(tempPhonebook)
              }
            }
            else {
              println("The number is incorrectly formatted. It has to be: +441234567890 ")
              menuContact(tempPhonebook)
            }
          }
        }
      }
      case "F" | "f"  => {
        println("Find a contact")
        // Add find function
        menuContact(tempPhonebook)
      }
      case "D" | "d"  => {
        println("Delete a contact")
        // Add find contact and save new Phonebook without contact
        menuContact(tempPhonebook)
      }
      case "S" | "s"  => {
        println("Save and close phonebook")
        // Add save and write Phonebook

      }
      case "E" | "e"  => {
        println("Close without saving")
        // Add close Phonebook
      }
      case _          => menuContact(tempPhonebook)
    }
  }

  println(s"Welcome to the Phonebook App!\n")
  menuPhonebook()

}
