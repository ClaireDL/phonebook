package com.clairedl.scala.phonebook.phoneentry

case class PhoneEntry(firstName: String, phoneNumber: String) {
  override def toString(): String = s"Name: $firstName; Phone Number: $phoneNumber"
}
