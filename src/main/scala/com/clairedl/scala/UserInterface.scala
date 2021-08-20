package com.clairedl.scala.phonebook.userinterface

import com.colofabrix.scala.figlet4s.unsafe._
import scala.io.StdIn.readLine
import com.clairedl.scala.phonebook.phonebookoperations._
import com.clairedl.scala.phonebook.contactoperations._
import com.clairedl.scala.phonebook.phoneentry._
import com.clairedl.scala.phonebook.validation.PhoneNumberValidation

class UserInterface {
  // Using Figlet4s to render title
  private val builder = Figlet4s.builder()
  private val title = s"Phonebook!"
  private val renderTitle = builder.render(title)
  renderTitle.print()

  private def execute2SDelay(function: Unit): Unit = {
    Thread.sleep(2000)
    function
  }

  val phonebookOperationsText = s"Choose one of the following options (type the corresponding letter): \n" +
    s"\n" +
    s"O: open an existing phonebook \n" +
    s"C: create a new phonebook \n" +
    s"P: show contacts in a phonebook \n" +
    s"S: save the changes \n" +
    s"Q: save and quit the program \n" +
    s"W: quit witout saving changes \n" +
    s"D: delete an existing phonebook \n"

  def menuPhonebook(workingPhonebook: List[PhoneEntry], initialPhonebook: List[PhoneEntry], phonebookName: String): Unit = {
    val phonebookOperator = new PhonebookOperations

    println(phonebookOperationsText)

    val choice = readLine(s"Your choice -> ")

    choice match {
      case "O" | "o"  => {
        val textfile = readLine("What is the textfile name? -> ")
        println("\n")
        val originalPhonebook = phonebookOperator.validateAndLoadTextfile(textfile)
        originalPhonebook match {
          case Some(x) => menuContact(x, x, textfile)
          case None    => {
            execute2SDelay(println(s"File not found/Incorrect name"))
            menuPhonebook(List[PhoneEntry](), List[PhoneEntry](), "")
          }
        }
      }
      case "C" | "c"  => {
        menuContact(List[PhoneEntry](), List[PhoneEntry](), "")
      }
      case "P" | "p" => {
        val textfile = readLine("What is the textfile name? -> ")
        val phonebook = phonebookOperator.validateAndLoadTextfile(textfile)
        println(s"\n")
        phonebook match {
          case Some(x) => {
            println(s"Contacts in $textfile: \n")
            x.foreach(println)
            execute2SDelay(println(s"\n"))
            menuPhonebook(x, x, textfile)
          }
          case None    => {
            execute2SDelay(println(s"File not found/Incorrect name \n"))
            menuPhonebook(List[PhoneEntry](), List[PhoneEntry](), "")
          }
        }
      }
      case "S" | "s"  => {
        // Add save/write
        execute2SDelay(println("Not implemented: Save and close the current phonebook \n"))
        menuPhonebook(List[PhoneEntry](), List[PhoneEntry](), "")
      }
      case "Q" | "q"  => {
        // Add save/write
        println("Not implemented: The phonebook was saved. Goodbye! \n")
      }
      case "W" | "w"  => {
        println("Not implemented: No change was saved. Goodbye! \n")
      }
      case "D" | "d"  => {
        // Add deleting a phonebook
        println("Not implemented: The phonebook was deleted.")
      }
      case _          => {
        execute2SDelay(println("This instruction does not exist. \n"))
        menuPhonebook(List[PhoneEntry](), List[PhoneEntry](), "")
      }
    }
  }

  val contactOperationsText = s"Choose one of the following actions: \n" +
    s"\n" +
    s"P: show the contacts \n" +
    s"A: add a contact \n" +
    s"F: find a contact \n" +
    s"D: delete a contact \n" +
    s"B: go back to the phonebook menu \n"

  def menuContact(workingPhonebook: List[PhoneEntry], initialPhonebook: List[PhoneEntry], phonebookName: String): Unit = {
    val contactOperator = new ContactOperations

    println(contactOperationsText)
    val choice = readLine(s"Your choice -> ")

    choice match {
      case "P" | "p"  => {
        workingPhonebook.foreach(println)
        execute2SDelay(menuContact(workingPhonebook, initialPhonebook, phonebookName))
      }
      case "A" | "a"  => {
        val newName = readLine("Name of the contact? -> ")
        val newNumber = readLine("Phone number? Format has to be: +441234567890 -> " )
        // Checks that formatting of new contact is correct
        val checkNewContact = contactOperator.checkNewContact(newName, newNumber, workingPhonebook)
         checkNewContact match {
          case Some(x)  => {
            // Checks for duplicates in phonebook
            val duplicates = contactOperator.findContact(checkNewContact.get, workingPhonebook)
            // No duplicate: new contact is added to the phonebook
            if (duplicates.isEmpty) {
              val updatedPhonebook = contactOperator.addContact(PhoneEntry(newName, newNumber), workingPhonebook)
              println(s"\n")
              execute2SDelay(println("New contact added! \n"))
              menuContact(updatedPhonebook, initialPhonebook, phonebookName)
            }
            // Duplicate: shows the duplicate but does not add to the phonebook
            else {
              println(s"\n")
              println(s"We found similar contacts. Make sure there are no duplicates. \n")
              println(s"This is the contact we found: \n")
              checkNewContact.foreach(println)
              Thread.sleep(2000)
              menuContact(workingPhonebook, initialPhonebook, phonebookName)
            }
          }
          case None    => {
            execute2SDelay(println("The phone number is incorrectly formatted. Format has to be: +441234567890 \n"))
            menuContact(workingPhonebook, initialPhonebook, phonebookName)
          }
        }
      }
      case "F" | "f"  => {
        println(s"You can look by typing the name OR phone number (formatting: +441234567890)")
        val toFind = readLine("Name or phone number? -> ")
        Thread.sleep(2000)
        val checkNumber = contactOperator.checkNumberFormatting(toFind)
        val found = {
          if (checkNumber) contactOperator.findNumber(toFind, workingPhonebook)
          else contactOperator.findName(toFind, workingPhonebook)
        }
        if (found.isEmpty) {
          println(s"\n")
          execute2SDelay(println(s"No contact was found. \n"))
          menuContact(workingPhonebook, initialPhonebook, phonebookName)
        }
        else {
          println(s"\n")
          println(s"We found the following contact(s):")
          found.foreach(println)
          execute2SDelay(println(s"\n"))
          menuContact(workingPhonebook, initialPhonebook, phonebookName)
        }
      }
      case "D" | "d"  => {
        println(s"This is the phonebook content:")
        workingPhonebook.foreach(println)
        execute2SDelay(println(s"\n"))
        val toDelete = readLine("Type the name of the contact you want to remove from the phonebook -> ")
        val contactToDelete = contactOperator.findName(toDelete, workingPhonebook)
        Thread.sleep(2000)
        println(s"We found: $contactToDelete \n")
        val confirmation = readLine(s"Are you sure you want to delete it? (Y/N) -> ")
        confirmation match {
          case "Y" | "y" => {
            val result = contactToDelete.map(x => workingPhonebook.filterNot(entry => entry == x)).flatten
            menuContact(result, initialPhonebook, phonebookName)
          }
          case "N" | "n" => menuContact(workingPhonebook, initialPhonebook, phonebookName)
        }
      }
      case "B" | "b"  => execute2SDelay(menuPhonebook(workingPhonebook, initialPhonebook, phonebookName))

      case _          => execute2SDelay(menuContact(workingPhonebook, initialPhonebook, phonebookName))
    }
  }
}