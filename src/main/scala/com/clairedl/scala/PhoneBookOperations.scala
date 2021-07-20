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

  def add(newEntry: PhoneEntry, phoneBook: List[PhoneEntry]): List[PhoneEntry] = {
    val updated = newEntry :: phoneBook
    updated
  }

  def findName(name: String, phoneBook: List[PhoneEntry]): List[PhoneEntry] = {
    phoneBook.flatMap(entry => if (entry.firstName == name) Some(entry) else None)
  }

  def findNumber(number: Valid, phoneBook: List[PhoneEntry]): List[PhoneEntry] = {
    phoneBook.flatMap(entry => if (entry.phoneNumber == number) Some(entry) else None)
  }

  def findContact(contact: PhoneEntry, phoneBook: List[PhoneEntry]): List[PhoneEntry] = {
    phoneBook.flatMap(entry => if (entry == contact) Some(entry) else None)
  }
}
