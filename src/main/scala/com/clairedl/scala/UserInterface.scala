package com.clairedl.scala.phonebook.userinterface

import com.colofabrix.scala.figlet4s.unsafe._
import scala.io.StdIn.readLine
import com.clairedl.scala.phonebook.phonebookoperations._
import com.clairedl.scala.phonebook.contactoperations._
import com.clairedl.scala.phonebook.phoneentry._

class UserInterface {
  // Using Figlet4s to render title
  private val builder = Figlet4s.builder()
  private val title = s"Phonebook!"
  private val renderTitle = builder.render(title)
  renderTitle.print()

  val phonebookOperationsText = s"Choose one of the following options (type the corresponding letter): \n" +
    s"\n" +
    s"O: open an existing phonebook \n" +
    s"C: create a new phonebook \n" +
    s"P: show contacts in an existing phonebook \n" +
    s"S: save the phonebook \n" +
    s"Q: save and quit the program \n" +
    s"W: quit witout saving changes \n"

  def menuPhonebook(): Unit = {
    Thread.sleep(2000)
    val phonebookOperator = new PhonebookOperations

    println(phonebookOperationsText)

    val choice = readLine(s"Your choice -> ")

    choice match {
      case "O" | "o"  => {
        val textfile = readLine("What is the textfile name? -> ")
        val originalPhonebook = phonebookOperator.validateAndLoadTextfile(textfile)
        originalPhonebook match {
          case Some(x) => menuContact(x)
          case None    => {
            println(s"File not found/Incorrect name")
            menuPhonebook()
          }
        }
      }
      case "C" | "c"  => {
        menuContact(List[PhoneEntry]())
      }
      case "P" | "p" => {
        val textfile = readLine("What is the textfile name? -> ")
        val phonebook = phonebookOperator.validateAndLoadTextfile(textfile)
        println(s"\n")
        phonebook match {
          case Some(x) => {
            x.foreach(println)
            println(s"\n")
            Thread.sleep(2000)
            menuPhonebook()
          }
          case None    => {
            println(s"File not found/Incorrect name")
            menuPhonebook()
          }
        }
      }
      case "S" | "s"  => {
        // Add save/write
        println("Save and close the current phonebook \n")
        menuPhonebook()
      }
      case "Q" | "q"  => {
        // Add save/write
        println("The phonebook was saved. Goodbye! \n")
      }
      case "W" | "w"  => println("No change was saved. Goodbye! \n")
      case _          => {
        println("This instruction does not exist. \n")
        menuPhonebook()
      }
    }
  }

  val contactOperationsText = s"Choose one of the following actions: \n" +
    s"\n" +
    s"A: add a contact \n" +
    s"F: find a contact \n" +
    s"D: delete a contact \n" +
    s"P: show all the contacts in the phonebook \n" +
    s"S: save the last changes to the Phonebook \n" +
    s"E: exit Phonebook without save the last changes \n"

  def menuContact(tempPhonebook: List[PhoneEntry]): Unit = {
    Thread.sleep(2000)
    val contactOperator = new ContactOperations

    println(contactOperationsText)
    val choice = readLine()

    choice match {
      case "A" | "a"  => {
        val newName = readLine("Name of the contact? -> ")
        val newNumber = readLine("Phone number? Format has to be: +441234567890 -> " )
        // Checks that formatting of new contact is correct
        val checkNewContact = contactOperator.checkNewContact(newName, newNumber, tempPhonebook)
         checkNewContact match {
          case Some(x)  => {
            // Checks for duplicates in phonebook
            val duplicates = contactOperator.findContact(checkNewContact.get, tempPhonebook)
            // No duplicate: new contact is added to the phonebook
            if (duplicates.isEmpty) {
              val updatedPhonebook = contactOperator.addContact(PhoneEntry(newName, newNumber), tempPhonebook)
              println(s"\n")
              println("New contact added! \n")
              menuContact(updatedPhonebook)
            }
            // Duplicate: prints the duplicate
            else {
              println(s"\n")
              println(s"We found similar contacts. Make sure there are no duplicates. \n")
              println(s"This is the contact we found: \n")
              checkNewContact.foreach(println)
              menuContact(tempPhonebook)
            }
          }
          case None    => {
            println("The phone number is incorrectly formatted. Format has to be: +441234567890 \n")
            menuContact(tempPhonebook)
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
      case "P" | "p"  => {
        println("Showing all the contacts")
        tempPhonebook.foreach(println)
        menuContact(tempPhonebook)
      }
      case "S" | "s"  => {
        tempPhonebook.foreach(println)
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
}
