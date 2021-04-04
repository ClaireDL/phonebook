package com.clairedl.scala.phonebook

import scala.util.matching.Regex

object InputValidation {
  def validate(input: String): String = {
    val phonePattern: Regex = "(\\+\\d{1,3})[0-9]{11}".r
    phonePattern.findFirstIn(input).getOrElse("Invalid Format")
  }
}
