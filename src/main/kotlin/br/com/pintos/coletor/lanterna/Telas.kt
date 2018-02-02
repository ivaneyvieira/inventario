package br.com.pintos.coletor.lanterna

import br.com.pintos.coletor.db.DB
import br.com.pintos.coletor.lanterna.util.ColWindow
import br.com.pintos.coletor.lanterna.util.horizontal
import br.com.pintos.coletor.lanterna.util.label
import br.com.pintos.coletor.lanterna.util.messageDialog
import br.com.pintos.coletor.lanterna.util.open
import br.com.pintos.coletor.lanterna.util.text
import br.com.pintos.coletor.model.LoginModel
import br.com.pintos.coletor.model.LoteModel

fun main(args: Array<String>) {
  DB.open()

  args.firstOrNull()?.let { coletor ->
    LoginModel.msgInventario?.let { msg ->
      val win = messageDialog("ERRO", msg)
      Terminal(win).use {
        LoginModel.coletor = coletor
        it.startScreen()
      }
      System.exit(0)
    }

    Terminal(telaLogin).use {
      LoginModel.coletor = coletor
      it.startScreen()
    }

  }
}

val telaLogin by lazy {
  TelaLogin()
}
val telaLote by lazy {
  TelaLote()
}

class TelaLogin : ColWindow(titulo = "LOGIN") {
  override fun initComp() {
    DB.open()

    val edtLogin = text(9)
    areaPanel.addComponent(
      horizontal(label("Matricula:"), edtLogin)
                          )
    addButton("Login") {
      if (LoginModel.validaLogin(edtLogin.text)) {
        telaLote.open()
      } else {
        val win = messageDialog("ERRO", "Usuario nao\nencontrado")
        Terminal.textGUI.addWindow(win)
        edtLogin.text = ""
        edtLogin.takeFocus()
      }
    }
  }
}

class TelaLote : ColWindow("SELECAO DE LOTE") {
  override fun initComp() {
    DB.open()

    val loja = LoginModel.loja?.nome ?: ""
    val lote = LoteModel.lote?.numero?.toString()?.padStart(3, '0') ?: "---"
    val coleta = LoteModel.coletaAberta()?.numleitura?.toString()?.padStart(2, '0') ?: "--"
    val quant = LoteModel.quantColeta()?.toString()?.padStart(4, ' ') ?: "----"
    val user = LoginModel.usuario?.apelido ?: ""

    areaPanel.addComponent(label("LJ: $loja"))
    areaPanel.addComponent(label("LT: $lote/$coleta  QT: $quant"))
    areaPanel.addComponent(label("US: $user"))
  }
}