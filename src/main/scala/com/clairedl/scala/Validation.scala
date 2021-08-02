package com.clairedl.scala.phonebook.validation

import java.io.File

abstract class Validated {
  val input: String
}

class Valid(validInput: String) extends Validated {
  val input = validInput
}

class Invalid extends Validated {
  val input = ""
}

trait Validation{
  def validate(input: String): Validated
}

object TextFileNameValidation extends Validation {
  def validate(textFileName: String): Validated = {
    val pattern = "[a-zA-Z0-9].txt\\Z".r
    pattern.findFirstIn(textFileName) match {
      case Some(x)  => new Valid(textFileName)
      case None     => new Invalid
    }
  }
}

object FilePathValidation extends Validation {
  def validate(filePath: String): Validated = {
    val found = new java.io.File(filePath).isFile
    if (found) new Valid(filePath)
    else new Invalid
  }
}

object PhoneNumberValidation extends Validation {
  def validate(phoneNumber: String): Validated = {
    val pattern = "(\\+\\d{1,3})[0-9]{11}".r
    pattern.findFirstIn(phoneNumber) match {
      case Some(x)  => new Valid(phoneNumber)
      case None     => new Invalid
    }
  }
}
