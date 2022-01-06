package com.clairedl.scala.phonebook.contact

case class Contact(name: String, phoneNumber: String) {
  override def toString(): String = s"Name: $name; Phone Number: $phoneNumber"
}
