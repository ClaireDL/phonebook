package com.clairedl.scala.phonebook.phonebookwriter

import com.clairedl.scala.phonebook.phoneentry._
import java.io._
import scala.io.Source

trait PhoneBookWriter {
  def write(): Unit
}

class TextWriter(fileName: String, data: List[PhoneEntry]) extends PhoneBookWriter {
  def write(): Unit = {
    val file = new File(fileName)
    val writer = new BufferedWriter(new FileWriter(file))
    data.map(x => writer.write(s"${x.firstName}, ${x.phoneNumber}\n"))
    writer.close()
  }
}
