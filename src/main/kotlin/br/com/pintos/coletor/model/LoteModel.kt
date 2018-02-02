package br.com.pintos.coletor.model

import br.com.pintos.coletor.db.Coleta
import br.com.pintos.coletor.db.Loja
import br.com.pintos.coletor.db.Lojas
import br.com.pintos.coletor.db.Lote
import org.jetbrains.exposed.sql.transactions.transaction

object LoteModel {
  var lote: Lote? = null

  fun coletaAberta(): Coleta? {
    return null
  }

  fun quantColeta(): Int? {
    return null
  }
}