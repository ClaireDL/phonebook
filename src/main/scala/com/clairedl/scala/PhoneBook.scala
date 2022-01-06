package com.clairedl.scala.phonebook.phonebook

import com.clairedl.scala.phonebook.contact._

class PhoneBook(name: String, content: List[Contact]) {
  def add(contact: Contact): List[Contact] = ???

  def delete(contact: Contact): List[Contact] = ???

  def findByName(name: String): Contact = ???

  def findByNumber(number: String): Contact = ???

  def updateName(contact: Contact, newName: String): List[Contact] = ???

  def updateNumber(contact: Contact, newNumber: String): List[Contact] = ???
}
