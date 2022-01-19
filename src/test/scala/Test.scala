package com.clairedl.scala.phonebook.test

import org.scalatest._
import flatspec._
import matchers._
import com.clairedl.scala.phonebook.contact._
import com.clairedl.scala.phonebook.phonebook._

class TestPhonebook extends AnyFlatSpec with should.Matchers {
  val contactList = List(
    Contact("Claire", "+447123456789"),
    Contact("Fabrizio", "+447987654321"),
    Contact("Marchampt", "+33412345678")
  )

  val phonebook = new PhoneBook("Test", contactList)
  val newContact = Contact("Curious", "+447456123789")

  "Adding a contact" should "add it at the top of the phonebook" in {
    val computed = phonebook.add(newContact)
    val expected = newContact :: contactList
    assert(computed === expected)
  }

  "Adding a contact and sorting the phonebook" should "put the new contact list in alphabetical order" in {
    val computed = phonebook.addAndSort(newContact)
    val expected = (newContact :: contactList).sortBy(_.name)
    assert(computed === expected)
  }

  "Looking for a contact's name" should "return the contact's details" in {
    val computed = phonebook.findByName("Claire")
    val expected = Contact("Claire", "+447123456789")
    assert(computed === expected)
  }
}
