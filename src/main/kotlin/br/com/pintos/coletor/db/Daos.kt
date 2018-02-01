package br.com.pintos.coletor.db


import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable


object Lotes : LongIdTable("lote"){
  val descricao	=text("descricao")
  val loteavulso = 	bool("loteavulso")
  val numero	= integer("numero")
  val loja = reference("loja_id", Lojas)
  val version	= integer("version").nullable()
}

object Lojas : LongIdTable("loja"){
  val endereco	= varchar("endereco", 50)
  val nome	= varchar("nome", 30)
  val sigla	= varchar("sigla", 2)
  val storeno	= integer("storeno")
  val version	= integer("version").nullable()
}

object Coletas : LongIdTable("coleta"){
  val numleitura	= integer("numleitura")
  val Inventario_id	= long("Inventario_id")
  val lote = reference("lote_id", Lotes)
  val usuario	 = reference("usuario_id", Usuarios)
  val coletor	= integer("coletor")
  val status	= enumerationByName("status", 10,  EStatusColeta::class.java)
  val version	= integer("version").nullable()
}

object Usuarios: LongIdTable("usuario") {
  val matricula	= integer("matricula")
  val nome	= varchar("nome", 50)
  val senha	= varchar("senha",20)
  val apelido	= varchar("apelido",30)
  val version	= integer("version").nullable()
}

object Leituras : LongIdTable("leitura"){
  val hora	= datetime("hora")
  val leitura	= varchar("leitura", 20)
  val observacao	=text("observacao")
  val quant	= integer("quant")
  val status	= enumerationByName("status", 10, EStatusLeitura::class.java)
  val coleta	= reference("coleta_id", Coletas)
  val produto	= reference("produto_id", Produtos)
  val version	= integer("version").nullable()
}

object  Produtos : LongIdTable("produto"){
  val barcode	=varchar("barcode", 20)
  val codigo	= varchar("codigo", 16)
  val descricao	= varchar("descricao", 40)
  val duplicado	= bool("duplicado")
  val foralinha	= bool("foralinha")
  val grade	= varchar("grade", 10)
  val usoconsumo	= bool("usoconsumo")
  val cl_id	= long("cl_id")
  val fornecedor_id	=long("fornecedor_id")
  val version	= integer("version").nullable()
}

class Lote(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Lote>(Lotes)

  var descricao by Lotes.descricao
  var loteavulso by Lotes.loteavulso
  var numero by Lotes.numero
  var loja by Loja referencedOn Lotes.loja
  var version by Lotes.version
}

class Loja(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Loja>(Lojas)

  var endereco by Lojas.endereco
  var nome by Lojas.nome
  var sigla by Lojas.sigla
  var storeno by Lojas.storeno
  var version by Lojas.version
}

class Coleta(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Coleta>(Coletas)

  var numleitura
  var Inventario_id
  var lote_id
  var usuario_id
  var coletor
  var status
  var version
}

class Usuario(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Usuario>(Usuarios)

  var matricula
  var nome
  var senha
  var apelido
  var version
}

class Leitura(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Leitura>(Leituras)

  var hora
  var leitura
  var observacao
  var quant
  var status
  var coleta_id
  var produto_id
  var version
}

class Produto(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Produto>(Produtos)

  var barcode
  var codigo
  var descricao
  var duplicado
  var foralinha
  var grade
  var usoconsumo
  var cl_id
  var fornecedor_id
  var version
}


enum class EStatusColeta {
  FECHADO,
  ABERTO
}

enum class  EStatusLeitura {
  SUCESSO,
  ERRO
}