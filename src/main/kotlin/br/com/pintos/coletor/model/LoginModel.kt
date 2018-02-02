package br.com.pintos.coletor.model

import br.com.pintos.coletor.db.EStatusInv
import br.com.pintos.coletor.db.Inventario
import br.com.pintos.coletor.db.Inventarios
import br.com.pintos.coletor.db.Usuario
import br.com.pintos.coletor.db.Usuarios
import org.jetbrains.exposed.sql.transactions.transaction

object LoginModel {
  var usuario: Usuario? = null
  var coletor: String? = null
  var inventariosAberto = transaction {
    Inventario.find { Inventarios.statusInventario eq EStatusInv.Aberto }.toList()
  }

  val msgInventario = when {
    inventariosAberto.count() == 0 -> "Nao ha inventario\naberto"
    inventariosAberto.count() > 1  -> "Mais de um\ninventario aberto"
    else                           -> null
  }

  val inventario = when {
    inventariosAberto.count() == 0 -> null
    inventariosAberto.count() > 1  -> null
    else                           -> inventariosAberto.first()
  }

  val loja = transaction { inventario?.loja }

  fun validaLogin(matricula: String): Boolean {
    return transaction {
      usuario  = Usuario.find { Usuarios.matricula eq matricula }.firstOrNull()
      usuario  != null
    }
  }
}