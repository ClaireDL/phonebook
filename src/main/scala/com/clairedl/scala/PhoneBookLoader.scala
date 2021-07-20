package com.clairedl.scala.phonebook.phonebookloader

import scala.io.Source
import com.clairedl.scala.phonebook.phoneentry._

trait PhoneBookLoader {
  def load(): List[PhoneEntry]
}

class FileLoader(name: String) extends PhoneBookLoader {
  def load(): List[PhoneEntry] = {
    Source
      .fromFile(name)
      .getLines()
      .map { line =>
        val split = line.split(",")
        PhoneEntry(split(0), split(1))
      }
    .toList
  }
}
