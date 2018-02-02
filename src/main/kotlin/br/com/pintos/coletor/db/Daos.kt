package br.com.pintos.coletor.db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable

object Lotes : LongIdTable("lote") {
  val descricao = text("descricao")
  val loteavulso = bool("loteavulso")
  val numero = integer("numero")
  val loja = reference("loja_id", Lojas)
  val version = integer("version").nullable()
}

object Lojas : LongIdTable("loja") {
  val endereco = varchar("endereco", 50)
  val nome = varchar("nome", 30)
  val sigla = varchar("sigla", 2)
  val storeno = integer("storeno")
  val version = integer("version").nullable()
}

object Coletas : LongIdTable("coleta") {
  val numleitura = integer("numleitura")
  val Inventario_id = long("Inventario_id")
  val lote = reference("lote_id", Lotes)
  val usuario = reference("usuario_id", Usuarios)
  val coletor = integer("coletor")
  val status = enumerationByName("status", 10, EStatusColeta::class.java)
  val version = integer("version").nullable()
}

object Usuarios : LongIdTable("usuario") {
  val matricula = integer("matricula")
  val nome = varchar("nome", 50)
  val senha = varchar("senha", 20)
  val apelido = varchar("apelido", 30)
  val version = integer("version").nullable()
}

object Leituras : LongIdTable("leitura") {
  val hora = datetime("hora")
  val leitura = varchar("leitura", 20)
  val observacao = text("observacao")
  val quant = integer("quant")
  val status = enumerationByName("status", 10, EStatusLeitura::class.java)
  val coleta = reference("coleta_id", Coletas)
  val produto = reference("produto_id", Produtos)
  val version = integer("version").nullable()
}

object Produtos : LongIdTable("produto") {
  val barcode = varchar("barcode", 20)
  val codigo = varchar("codigo", 16)
  val descricao = varchar("descricao", 40)
  val duplicado = bool("duplicado")
  val foralinha = bool("foralinha")
  val grade = varchar("grade", 10)
  val usoconsumo = bool("usoconsumo")
  val cl_id = long("cl_id")
  val fornecedor_id = long("fornecedor_id")
  val version = integer("version").nullable()
}

object Inventarios : LongIdTable("inventario"){
  val numero = integer("numero")
  val data = date("data")
  val observacao = text("observacao")
  val tipoInventario  = varchar("tipoInventario", 20)
  val statusInventario = enumerationByName("statusInventario", 10, EStatusInv::class.java)
  val loja = reference("loja_id", Lojas)
  val version =  integer("version").nullable()
  val fornecedor_id = long("fornecedor_id")
  val cl_id = long("cl_id")
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

  var numleitura by Coletas.numleitura
  var Inventario_id by Coletas.Inventario_id
  var lote by Lote referencedOn Coletas.lote
  var usuario by Usuario referencedOn Coletas.usuario
  var coletor by Coletas.coletor
  var status by Coletas.status
  var version by Coletas.version
}

class Usuario(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Usuario>(Usuarios)

  var matricula by Usuarios.matricula
  var nome by Usuarios.nome
  var senha by Usuarios.senha
  var apelido by Usuarios.apelido
  var version by Usuarios.version
}

class Leitura(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Leitura>(Leituras)

  var hora by Leituras.hora
  var leitura by Leituras.leitura
  var observacao by Leituras.observacao
  var quant by Leituras.quant
  var status by Leituras.status
  var coleta by Coleta referencedOn Leituras.coleta
  var produto by Produto referencedOn Leituras.produto
  var version by Leituras.version
}

class Produto(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Produto>(Produtos)

  var barcode by Produtos.barcode
  var codigo by Produtos.codigo
  var descricao by Produtos.descricao
  var duplicado by Produtos.duplicado
  var foralinha by Produtos.foralinha
  var grade by Produtos.grade
  var usoconsumo by Produtos.usoconsumo
  var cl_id by Produtos.cl_id
  var fornecedor_id by Produtos.fornecedor_id
  var version by Produtos.version
}

class Inventario(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<Inventario>(Inventarios)

  var numero by Inventarios.numero
  var data by Inventarios.data
  var observacao by Inventarios.observacao
  var tipoInventario by Inventarios.tipoInventario
  var statusInventario by Inventarios.statusInventario
  var loja by Loja referencedOn Inventarios.loja
  var version by Inventarios.version
  var fornecedor_id by Inventarios.fornecedor_id
  var cl_id by Inventarios.cl_id
}

enum class EStatusColeta {
  FECHADO,
  ABERTO
}

enum class EStatusLeitura {
  SUCESSO,
  ERRO
}

enum class EStatusInv {
  Fechado,
  Aberto
}