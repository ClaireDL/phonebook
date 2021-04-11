package com.clairedl.scala.phonebook

import com.clairedl.scala.phonebook.phonebookoperations._
import com.clairedl.scala.phonebook.phonebookwriter._
import com.clairedl.scala.phonebook.phonebookloader._
import com.clairedl.scala.phonebook.phoneentry._
import com.clairedl.scala.phonebook.phoneentryoperations._

object Main extends App {
  val phoneBook = PhoneBookOperations.open(new TextLoader("PhoneBookClaire.txt"))
  phoneBook.foreach(println)

  val newFile: List[PhoneEntry] = List(
    PhoneEntry("Fab", "07498358467"),
    PhoneEntry("Cl", "+447489367152")
  )

  val augmented = PhoneBookOperations.add(PhoneEntry("Virginie", "+33478415279"), newFile)

  PhoneBookOperations.save(new TextWriter("test2.txt", augmented))
}

// object Main {
//   val usage: String = """|Phonebook Options
//     | -o | --open         string        Path to existing phonebook to open
//     | -s | --save         string        Path to file to save the phonebook to
//     | """.stripMargin

//   def main(args: Array[String]): Unit = {
//     val arguments = args.toList

//     arguments match {
//         case "-o" :: fileName :: Nil => {
//           val phoneBook = PhoneBookOperations.open(new TextLoader(fileName))
//           phoneBook.foreach(println)
//         }
//         case "-h" :: Nil => println(s"You requested help.")
//         case "-a" :: String :: String => phone number validation!!
//         case _ => println(s"""|You need command line arguments: \n $usage""".stripMargin)
//       }
//   }
// }
