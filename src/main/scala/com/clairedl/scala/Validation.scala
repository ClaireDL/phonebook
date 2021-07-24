package com.clairedl.scala.phonebook.validation

import java.io.File

abstract class Validated

class Valid(input: String) extends Validated
class Invalid(input: String) extends Validated

trait Validation{
  def validate(input: String): Validated
}

object TextFileNameValidation extends Validation {
  def validate(input: String): Validated = {
    val pattern = "[a-zA-Z0-9].txt\\Z".r
    pattern.findFirstIn(input) match {
      case Some(x)  => new Valid(input)
      case None     => new Invalid(input)
    }
  }
}

object FilePathValidation extends Validation {
  def validate(input: String): Validated = {
    val found = new java.io.File(input).isFile
    if (found) new Valid(input)
    else new Invalid(input)
  }
}

object PhoneNumberValidation extends Validation {
  def validate(input: String): Validated = {
    val pattern = "(\\+\\d{1,3})[0-9]{11}".r
    pattern.findFirstIn(input) match {
      case Some(x)  => new Valid(input)
      case None     => new Invalid(input)
    }
  }
}
