package com.clairedl.scala.phonebook.phoneentryoperations

import com.clairedl.scala.phonebook.phoneentry._

/**
  * List of operations that can be done with a phone entry:
  * - create
  * - find
  * - update
  * - delete
  * - add more fields that are not essential
  */
object PhoneEntryOperations {
  def add(firstName: String, phoneNumber: String, phoneBook: List[PhoneEntry]): List[PhoneEntry] = {
    val newEntry: PhoneEntry = PhoneEntry(firstName, phoneNumber)
    val updated: List[PhoneEntry] = newEntry :: phoneBook
    updated
  }

  // def find(element: String): PhoneEntry {

  // }
}
