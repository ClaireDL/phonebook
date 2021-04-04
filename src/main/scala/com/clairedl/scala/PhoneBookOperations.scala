package com.clairedl.scala.phonebook.phonebookoperations

import com.clairedl.scala.phonebook.phonebookwriter._
import com.clairedl.scala.phonebook.phonebookloader._
import com.clairedl.scala.phonebook.phoneentry._

/**
  * List of operations that can be done on the Phonebook:
  * - Load
  * - Create
  * - Export
  * - Save
  */
object PhoneBookOperations {
  def open(source: PhoneBookLoader): List[PhoneEntry] = {
    source.load()
  }

  def save(mode: PhoneBookWriter): Unit = {
    mode.write()
  }
}
