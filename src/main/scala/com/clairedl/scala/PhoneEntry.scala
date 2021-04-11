package com.clairedl.scala.phonebook.phoneentry

import scala.util.matching.Regex

case class PhoneEntry(firstName: String, phoneNumber: String) {
  override def toString(): String = s"Name: $firstName; Phone Number: $phoneNumber"
}

object PhoneEntry {
  def validateNumber(firstName: String, phoneNumber: String): PhoneEntry = {
    val phonePattern: Regex = "(\\+\\d{1,3})[0-9]{11}".r
    val result = phonePattern.findFirstIn(phoneNumber)
    result match {
      case Some(x) => PhoneEntry(firstName, phoneNumber)
      case None    => PhoneEntry(firstName, "Invalid number")
    }
  }
}
