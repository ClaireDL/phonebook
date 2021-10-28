package com.clairedl.scala.phonebook.phonebookoperations

import com.clairedl.scala.phonebook.phonebookwriter._
import com.clairedl.scala.phonebook.phonebookloader._
import com.clairedl.scala.phonebook.phoneentry._
import com.clairedl.scala.phonebook.validation._

/**
  * List of operations that can be done on the Phonebook:
  * - Load
  * - Add
  * - Export
  * - Save
  */
class PhonebookOperations {
  def load(source: PhoneBookLoader): List[PhoneEntry] = source.load()

  def save(mode: PhoneBookWriter): Unit = mode.write()

  def validateAndLoadTextfile(fileName: String): Option[List[PhoneEntry]] = {
    val checkFilePath = FilePathValidation.validate(fileName)
    if (checkFilePath.isInstanceOf[Invalid]) {
      None
    }
    else Some(load(new FileLoader(fileName)))
  }

  def close(source: PhoneBookLoader): Unit = source.closePhonebook()
}
