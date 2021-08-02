package com.clairedl.scala.phonebook.contactoperations

import com.clairedl.scala.phonebook.phoneentry._
import com.clairedl.scala.phonebook.validation._

class ContactOperations {
  def addContact(newEntry: PhoneEntry, phoneBook: List[PhoneEntry]): List[PhoneEntry] = {
    val updated = newEntry :: phoneBook
    updated
  }

  def findName(name: String, phoneBook: List[PhoneEntry]): List[PhoneEntry] = {
    phoneBook.flatMap(entry => if (entry.firstName == name) Some(entry) else None)
  }

  def findNumber(number: String, phoneBook: List[PhoneEntry]): List[PhoneEntry] = {
    phoneBook.flatMap(entry => if (entry.phoneNumber == number) Some(entry) else None)
  }

  def findContact(contact: PhoneEntry, phoneBook: List[PhoneEntry]): List[PhoneEntry] = {
    phoneBook.flatMap(entry => if (entry.firstName == contact.firstName || entry.phoneNumber == contact.phoneNumber) Some(entry) else None)
  }

  def checkNewContact(name: String, phoneNumber: String, phoneBook: List[PhoneEntry]): Option[PhoneEntry] = {
    val validatedNumber = PhoneNumberValidation.validate(phoneNumber)

    if (validatedNumber.isInstanceOf[Valid]) Some(PhoneEntry(name, phoneNumber))
    else None
  }

  def checkNumberFormatting(phoneNumber: String): Boolean = {
    val valid = PhoneNumberValidation.validate(phoneNumber)
    valid.isInstanceOf[Valid]
  }
}
