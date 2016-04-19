package com.example

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.MySQLDriver.api._
import scala.concurrent.duration._

import Tables._

object Hello extends App {

  val db = Database.forConfig("localhost")
  try {

    val all: Future[Seq[UserRow]] = db.run(Tables.User.result)

    all.map { result =>
      println(result)
    }

    Await.result(all, 5000 millis) //Duration.Inf)

  } finally db.close

}
