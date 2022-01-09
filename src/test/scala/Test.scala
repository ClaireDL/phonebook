package com.clairedl.scala.phonebook.test

import org.scalatest._
import flatspec._
import matchers._
import com.clairedl.scala.phonebook.contact._
import com.clairedl.scala.phonebook.phonebook._

class TestPhonebook extends AnyFlatSpec with should.Matchers {
  val phonebook = List(
    Contact("Claire", "+447123456789"),
    Contact("Fabrizio", "+447987654321"),
    Contact("Marchampt", "+33412345678")
  )

  val newContact = Contact("Curious", "+447456123789")

  "Adding a contact" should "put new contact at the top" in {
    val computed = new PhoneBook("test", phonebook).add(newContact)
    val expected = newContact :: phonebook
    assert(computed === expected)
  }

  "Adding a contact and sorting the phonebook" should "put the new contact list in alphabetical order" in {
    val computed = new PhoneBook("test", phonebook).addAndSort(newContact)
    val expected = (newContact :: phonebook).sortBy(_.name)
    assert(computed === expected)
  }
}
