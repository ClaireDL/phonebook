package com.clairedl.scala.phonebook.phoneentry

import scala.util.matching.Regex

case class PhoneEntry(firstName: String, phoneNumber: String) {
  override def toString(): String = s"Name: $firstName; Phone Number: $phoneNumber"
}
