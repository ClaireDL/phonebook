package com.clairedl.scala.phonebook.userinterface

import com.colofabrix.scala.figlet4s.unsafe._
import scala.io.StdIn.readLine
import com.clairedl.scala.phonebook.phonebookoperations._
import com.clairedl.scala.phonebook.contactoperations._
import com.clairedl.scala.phonebook.phoneentry._
import com.clairedl.scala.phonebook.validation.PhoneNumberValidation
import com.clairedl.scala.phonebook.validation.TextFileNameValidation
import com.clairedl.scala.phonebook.validation.Valid
import com.clairedl.scala.phonebook.phonebookwriter.TextWriter

class UserInterface {
  // Adds a 2s delay before executing a function (to increase visibility for user).
  def execute2SDelay(function: Unit): Unit = {
    Thread.sleep(2000)
    function
  }

  // Using Figlet4s to render title
  val builder = Figlet4s.builder()
  val title = s"Phonebook!"
  val renderTitle = builder.render(title)
  renderTitle.print()

  val phonebookOperator = new PhonebookOperations

  val phonebookOperationsText = s"Choose one of the following options (type the corresponding letter): \n" +
    s"\n" +
    s"O: open an existing phonebook \n" +
    s"C: create a new phonebook \n" +
    s"P: show contacts in a phonebook \n" +
    s"S: save the changes and quit the program \n" +
    s"W: quit witout saving changes \n" +
    s"D: delete an existing phonebook \n"

  def menuPhonebook(workingPhonebook: List[PhoneEntry], initialPhonebook: List[PhoneEntry], phonebookName: String): Unit = {
    // Checks the file name is valid that the user is happy with it before saving it
    def checkAndSave(title: String): Unit = {
      val checkTitle = TextFileNameValidation.validate(title)
      val finalTitle = {
        if (checkTitle.isInstanceOf[Valid]) title
        else title + ".txt"
      }
      execute2SDelay(println(s"The phonebook will be saved as $finalTitle \n"))
      val confirmation = readLine(s"Are you happy with this title? (Y/N) -> ")
      confirmation match {
        case "Y" | "y" => {
          phonebookOperator.save(new TextWriter(finalTitle, workingPhonebook))
          println(s"The phonebook is saved. Goodbye! \n")
        }
        case "N" | "n" => {
          val updatedTitle = readLine(s"Write the name of the phonebook -> ")
          execute2SDelay(checkAndSave(updatedTitle))
        }
      }
    }

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

      case "C" | "c"  => menuContact(List[PhoneEntry](), List[PhoneEntry](), "")
      
      case "P" | "p" => {
        val textfile = readLine("What is the textfile name? -> ")
        val phonebook = phonebookOperator.validateAndLoadTextfile(textfile)
        println(s"\n")
        phonebook match {
          case Some(x) => {
            println(s"Contacts in $textfile: \n")
            x.foreach(println)
            execute2SDelay(println(s" \n"))
            menuPhonebook(x, x, textfile)
          }

          case None    => {
            execute2SDelay(println(s"File not found/Incorrect name \n"))
            menuPhonebook(List[PhoneEntry](), List[PhoneEntry](), "")
          }
        }
      }

      case "S" | "s"  => {
        phonebookName match {
          case "" => {
            val title = readLine(s"Write the name of the phonebook -> ")
            checkAndSave(title)
          }

          case _ => checkAndSave(phonebookName)
        }
      }
      
      case "W" | "w"  => println("No change was saved. Goodbye! \n")

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
        val newNumber = readLine("Phone number? Format has to be: +441234567890 -> ")
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
              println(s"We found similar contacts; no duplicates allowed. \n")
              execute2SDelay(println(s"Existing contact: \n"))
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
        def showFound(contacts: List[PhoneEntry]): Unit = {
          println(s"We found the following contact(s):")
          contacts.foreach(println)
        }

        println(s"If you want to look by name, type 'n', by phone number type 'p'")

        val choice = readLine("Your choice -> ")

        choice match {
          case "N" | "n"  => {
            val name = readLine("Name to find -> ")
            val found = contactOperator.findName(name, workingPhonebook)
            if (found.isEmpty) {
              println(s"\n")
              execute2SDelay(println(s"No such contact was found.\n"))
              menuContact(workingPhonebook, initialPhonebook, phonebookName)
            }
            else {
              showFound(found)
              menuContact(workingPhonebook, initialPhonebook, phonebookName)
            }
          }
          case "P" | "p"  => {
            val number = readLine("Phone number to find (formatting: +441234567890) -> ")
            val checkNumber = contactOperator.checkNumberFormatting(number)
            if (checkNumber) {
              val found = contactOperator.findNumber(number, workingPhonebook)
              if (found.isEmpty) {
              println(s"\n")
              execute2SDelay(println(s"No contact was found: did you type it correctly? \n"))
              menuContact(workingPhonebook, initialPhonebook, phonebookName)
              }
              else showFound(found)
            }
            else {
              println("Incorrect formatting. \n")
              menuContact(workingPhonebook, initialPhonebook, phonebookName)
            }
          }
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

        val confirmation = readLine(s"Are you sure you want to delete this contact? (Y/N) -> ")
        confirmation match {
          case "Y" | "y" => {
            val result = contactToDelete.map(x => workingPhonebook.filterNot(entry => entry == x)).flatten
            menuContact(result, initialPhonebook, phonebookName)
          }
          case "N" | "n" => menuContact(workingPhonebook, initialPhonebook, phonebookName)
        }
      }

      case "B" | "b"  => execute2SDelay(menuPhonebook(workingPhonebook, initialPhonebook, phonebookName))

      case _          => {
        execute2SDelay(println("This instruction does not exist. \n"))
        execute2SDelay(menuContact(workingPhonebook, initialPhonebook, phonebookName))
      }
    }
  }
}
