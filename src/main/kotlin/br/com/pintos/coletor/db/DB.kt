package br.com.pintos.coletor.db

import org.jetbrains.exposed.sql.Database

object DB {
  fun open() {
    Database.connect(
      url = "jdbc:mysql://10.1.10.245/coletor",
      driver = "com.mysql.jdbc.Driver",
      user = "root",
      password = "musabela"
                    )
  }
}