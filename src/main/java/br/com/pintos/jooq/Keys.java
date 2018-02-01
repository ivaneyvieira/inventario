package br.com.pintos.jooq;

import br.com.pintos.jooq.tables.Cl;
import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.Estoque;
import br.com.pintos.jooq.tables.Fornecedor;
import br.com.pintos.jooq.tables.Inventario;
import br.com.pintos.jooq.tables.Leitura;
import br.com.pintos.jooq.tables.Loja;
import br.com.pintos.jooq.tables.Lote;
import br.com.pintos.jooq.tables.Produto;
import br.com.pintos.jooq.tables.Usuario;
import br.com.pintos.jooq.tables.records.ClRecord;
import br.com.pintos.jooq.tables.records.ColetaRecord;
import br.com.pintos.jooq.tables.records.EstoqueRecord;
import br.com.pintos.jooq.tables.records.FornecedorRecord;
import br.com.pintos.jooq.tables.records.InventarioRecord;
import br.com.pintos.jooq.tables.records.LeituraRecord;
import br.com.pintos.jooq.tables.records.LojaRecord;
import br.com.pintos.jooq.tables.records.LoteRecord;
import br.com.pintos.jooq.tables.records.ProdutoRecord;
import br.com.pintos.jooq.tables.records.UsuarioRecord;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;

public class Keys
{
  public static final Identity<ClRecord, Long> IDENTITY_CL = Identities0.IDENTITY_CL;
  public static final Identity<ColetaRecord, Long> IDENTITY_COLETA = Identities0.IDENTITY_COLETA;
  public static final Identity<EstoqueRecord, Long> IDENTITY_ESTOQUE = Identities0.IDENTITY_ESTOQUE;
  public static final Identity<FornecedorRecord, Long> IDENTITY_FORNECEDOR = Identities0.IDENTITY_FORNECEDOR;
  public static final Identity<InventarioRecord, Long> IDENTITY_INVENTARIO = Identities0.IDENTITY_INVENTARIO;
  public static final Identity<LeituraRecord, Long> IDENTITY_LEITURA = Identities0.IDENTITY_LEITURA;
  public static final Identity<LojaRecord, Long> IDENTITY_LOJA = Identities0.IDENTITY_LOJA;
  public static final Identity<LoteRecord, Long> IDENTITY_LOTE = Identities0.IDENTITY_LOTE;
  public static final Identity<ProdutoRecord, Long> IDENTITY_PRODUTO = Identities0.IDENTITY_PRODUTO;
  public static final Identity<UsuarioRecord, Long> IDENTITY_USUARIO = Identities0.IDENTITY_USUARIO;
  public static final UniqueKey<ClRecord> KEY_CL_PRIMARY = UniqueKeys0.KEY_CL_PRIMARY;
  public static final UniqueKey<ClRecord> KEY_CL_CLNO = UniqueKeys0.KEY_CL_CLNO;
  public static final UniqueKey<ColetaRecord> KEY_COLETA_PRIMARY = UniqueKeys0.KEY_COLETA_PRIMARY;
  public static final UniqueKey<EstoqueRecord> KEY_ESTOQUE_PRIMARY = UniqueKeys0.KEY_ESTOQUE_PRIMARY;
  public static final UniqueKey<EstoqueRecord> KEY_ESTOQUE_I1 = UniqueKeys0.KEY_ESTOQUE_I1;
  public static final UniqueKey<FornecedorRecord> KEY_FORNECEDOR_PRIMARY = UniqueKeys0.KEY_FORNECEDOR_PRIMARY;
  public static final UniqueKey<FornecedorRecord> KEY_FORNECEDOR_CODIGO = UniqueKeys0.KEY_FORNECEDOR_CODIGO;
  public static final UniqueKey<InventarioRecord> KEY_INVENTARIO_PRIMARY = UniqueKeys0.KEY_INVENTARIO_PRIMARY;
  public static final UniqueKey<InventarioRecord> KEY_INVENTARIO_I2 = UniqueKeys0.KEY_INVENTARIO_I2;
  public static final UniqueKey<LeituraRecord> KEY_LEITURA_PRIMARY = UniqueKeys0.KEY_LEITURA_PRIMARY;
  public static final UniqueKey<LojaRecord> KEY_LOJA_PRIMARY = UniqueKeys0.KEY_LOJA_PRIMARY;
  public static final UniqueKey<LojaRecord> KEY_LOJA_STORENO = UniqueKeys0.KEY_LOJA_STORENO;
  public static final UniqueKey<LoteRecord> KEY_LOTE_PRIMARY = UniqueKeys0.KEY_LOTE_PRIMARY;
  public static final UniqueKey<LoteRecord> KEY_LOTE_I1 = UniqueKeys0.KEY_LOTE_I1;
  public static final UniqueKey<ProdutoRecord> KEY_PRODUTO_PRIMARY = UniqueKeys0.KEY_PRODUTO_PRIMARY;
  public static final UniqueKey<ProdutoRecord> KEY_PRODUTO_BARCODE = UniqueKeys0.KEY_PRODUTO_BARCODE;
  public static final UniqueKey<ProdutoRecord> KEY_PRODUTO_I1 = UniqueKeys0.KEY_PRODUTO_I1;
  public static final UniqueKey<UsuarioRecord> KEY_USUARIO_PRIMARY = UniqueKeys0.KEY_USUARIO_PRIMARY;
  public static final UniqueKey<UsuarioRecord> KEY_USUARIO_MATRICULA = UniqueKeys0.KEY_USUARIO_MATRICULA;
  public static final ForeignKey<ColetaRecord, InventarioRecord> FK_COLETAS_INVENTARIO_ID = ForeignKeys0.FK_COLETAS_INVENTARIO_ID;
  public static final ForeignKey<ColetaRecord, LoteRecord> FK_COLETAS_LOTE_ID = ForeignKeys0.FK_COLETAS_LOTE_ID;
  public static final ForeignKey<ColetaRecord, UsuarioRecord> FK_COLETAS_USUARIO_ID = ForeignKeys0.FK_COLETAS_USUARIO_ID;
  public static final ForeignKey<EstoqueRecord, InventarioRecord> FK_ESTOQUE_INVENTARIO_ID = ForeignKeys0.FK_ESTOQUE_INVENTARIO_ID;
  public static final ForeignKey<EstoqueRecord, ProdutoRecord> FK_ESTOQUE_PRODUTO_ID = ForeignKeys0.FK_ESTOQUE_PRODUTO_ID;
  public static final ForeignKey<InventarioRecord, LojaRecord> FK_INVENTARIO_LOJA_ID = ForeignKeys0.FK_INVENTARIO_LOJA_ID;
  public static final ForeignKey<InventarioRecord, FornecedorRecord> FK_INVENTARIO_FORNECEDOR1 = ForeignKeys0.FK_INVENTARIO_FORNECEDOR1;
  public static final ForeignKey<InventarioRecord, ClRecord> FK_INVENTARIO_CL1 = ForeignKeys0.FK_INVENTARIO_CL1;
  public static final ForeignKey<LeituraRecord, ColetaRecord> FK_LEITURA_COLETA_ID = ForeignKeys0.FK_LEITURA_COLETA_ID;
  public static final ForeignKey<LeituraRecord, ProdutoRecord> FK_LEITURA_PRODUTO_ID = ForeignKeys0.FK_LEITURA_PRODUTO_ID;
  public static final ForeignKey<LoteRecord, LojaRecord> FK_LOTES_LOJA_ID = ForeignKeys0.FK_LOTES_LOJA_ID;
  public static final ForeignKey<ProdutoRecord, ClRecord> FK_PRODUTOS_CL_ID = ForeignKeys0.FK_PRODUTOS_CL_ID;
  public static final ForeignKey<ProdutoRecord, FornecedorRecord> FK_PRODUTOS_FORNECEDOR_ID = ForeignKeys0.FK_PRODUTOS_FORNECEDOR_ID;
  
  private static class ForeignKeys0
    extends AbstractKeys
  {
    public static final ForeignKey<ColetaRecord, InventarioRecord> FK_COLETAS_INVENTARIO_ID = createForeignKey(Keys.KEY_INVENTARIO_PRIMARY, Coleta.COLETA, new TableField[] { Coleta.COLETA.INVENTARIO_ID });
    public static final ForeignKey<ColetaRecord, LoteRecord> FK_COLETAS_LOTE_ID = createForeignKey(Keys.KEY_LOTE_PRIMARY, Coleta.COLETA, new TableField[] { Coleta.COLETA.LOTE_ID });
    public static final ForeignKey<ColetaRecord, UsuarioRecord> FK_COLETAS_USUARIO_ID = createForeignKey(Keys.KEY_USUARIO_PRIMARY, Coleta.COLETA, new TableField[] { Coleta.COLETA.USUARIO_ID });
    public static final ForeignKey<EstoqueRecord, InventarioRecord> FK_ESTOQUE_INVENTARIO_ID = createForeignKey(Keys.KEY_INVENTARIO_PRIMARY, Estoque.ESTOQUE, new TableField[] { Estoque.ESTOQUE.INVENTARIO_ID });
    public static final ForeignKey<EstoqueRecord, ProdutoRecord> FK_ESTOQUE_PRODUTO_ID = createForeignKey(Keys.KEY_PRODUTO_PRIMARY, Estoque.ESTOQUE, new TableField[] { Estoque.ESTOQUE.PRODUTO_ID });
    public static final ForeignKey<InventarioRecord, LojaRecord> FK_INVENTARIO_LOJA_ID = createForeignKey(Keys.KEY_LOJA_PRIMARY, Inventario.INVENTARIO, new TableField[] { Inventario.INVENTARIO.LOJA_ID });
    public static final ForeignKey<InventarioRecord, FornecedorRecord> FK_INVENTARIO_FORNECEDOR1 = createForeignKey(Keys.KEY_FORNECEDOR_PRIMARY, Inventario.INVENTARIO, new TableField[] { Inventario.INVENTARIO.FORNECEDOR_ID });
    public static final ForeignKey<InventarioRecord, ClRecord> FK_INVENTARIO_CL1 = createForeignKey(Keys.KEY_CL_PRIMARY, Inventario.INVENTARIO, new TableField[] { Inventario.INVENTARIO.CL_ID });
    public static final ForeignKey<LeituraRecord, ColetaRecord> FK_LEITURA_COLETA_ID = createForeignKey(Keys.KEY_COLETA_PRIMARY, Leitura.LEITURA, new TableField[] { Leitura.LEITURA.COLETA_ID });
    public static final ForeignKey<LeituraRecord, ProdutoRecord> FK_LEITURA_PRODUTO_ID = createForeignKey(Keys.KEY_PRODUTO_PRIMARY, Leitura.LEITURA, new TableField[] { Leitura.LEITURA.PRODUTO_ID });
    public static final ForeignKey<LoteRecord, LojaRecord> FK_LOTES_LOJA_ID = createForeignKey(Keys.KEY_LOJA_PRIMARY, Lote.LOTE, new TableField[] { Lote.LOTE.LOJA_ID });
    public static final ForeignKey<ProdutoRecord, ClRecord> FK_PRODUTOS_CL_ID = createForeignKey(Keys.KEY_CL_PRIMARY, Produto.PRODUTO, new TableField[] { Produto.PRODUTO.CL_ID });
    public static final ForeignKey<ProdutoRecord, FornecedorRecord> FK_PRODUTOS_FORNECEDOR_ID = createForeignKey(Keys.KEY_FORNECEDOR_PRIMARY, Produto.PRODUTO, new TableField[] { Produto.PRODUTO.FORNECEDOR_ID });
  }
  
  private static class UniqueKeys0
    extends AbstractKeys
  {
    public static final UniqueKey<ClRecord> KEY_CL_PRIMARY = createUniqueKey(Cl.CL, new TableField[] { Cl.CL.ID });
    public static final UniqueKey<ClRecord> KEY_CL_CLNO = createUniqueKey(Cl.CL, new TableField[] { Cl.CL.CLNO });
    public static final UniqueKey<ColetaRecord> KEY_COLETA_PRIMARY = createUniqueKey(Coleta.COLETA, new TableField[] { Coleta.COLETA.ID });
    public static final UniqueKey<EstoqueRecord> KEY_ESTOQUE_PRIMARY = createUniqueKey(Estoque.ESTOQUE, new TableField[] { Estoque.ESTOQUE.ID });
    public static final UniqueKey<EstoqueRecord> KEY_ESTOQUE_I1 = createUniqueKey(Estoque.ESTOQUE, new TableField[] { Estoque.ESTOQUE.INVENTARIO_ID, Estoque.ESTOQUE.PRODUTO_ID });
    public static final UniqueKey<FornecedorRecord> KEY_FORNECEDOR_PRIMARY = createUniqueKey(Fornecedor.FORNECEDOR, new TableField[] { Fornecedor.FORNECEDOR.ID });
    public static final UniqueKey<FornecedorRecord> KEY_FORNECEDOR_CODIGO = createUniqueKey(Fornecedor.FORNECEDOR, new TableField[] { Fornecedor.FORNECEDOR.CODIGO });
    public static final UniqueKey<InventarioRecord> KEY_INVENTARIO_PRIMARY = createUniqueKey(Inventario.INVENTARIO, new TableField[] { Inventario.INVENTARIO.ID });
    public static final UniqueKey<InventarioRecord> KEY_INVENTARIO_I2 = createUniqueKey(Inventario.INVENTARIO, new TableField[] { Inventario.INVENTARIO.NUMERO });
    public static final UniqueKey<LeituraRecord> KEY_LEITURA_PRIMARY = createUniqueKey(Leitura.LEITURA, new TableField[] { Leitura.LEITURA.ID });
    public static final UniqueKey<LojaRecord> KEY_LOJA_PRIMARY = createUniqueKey(Loja.LOJA, new TableField[] { Loja.LOJA.ID });
    public static final UniqueKey<LojaRecord> KEY_LOJA_STORENO = createUniqueKey(Loja.LOJA, new TableField[] { Loja.LOJA.STORENO });
    public static final UniqueKey<LoteRecord> KEY_LOTE_PRIMARY = createUniqueKey(Lote.LOTE, new TableField[] { Lote.LOTE.ID });
    public static final UniqueKey<LoteRecord> KEY_LOTE_I1 = createUniqueKey(Lote.LOTE, new TableField[] { Lote.LOTE.LOJA_ID, Lote.LOTE.LOTEAVULSO, Lote.LOTE.NUMERO });
    public static final UniqueKey<ProdutoRecord> KEY_PRODUTO_PRIMARY = createUniqueKey(Produto.PRODUTO, new TableField[] { Produto.PRODUTO.ID });
    public static final UniqueKey<ProdutoRecord> KEY_PRODUTO_BARCODE = createUniqueKey(Produto.PRODUTO, new TableField[] { Produto.PRODUTO.BARCODE });
    public static final UniqueKey<ProdutoRecord> KEY_PRODUTO_I1 = createUniqueKey(Produto.PRODUTO, new TableField[] { Produto.PRODUTO.CODIGO, Produto.PRODUTO.GRADE });
    public static final UniqueKey<UsuarioRecord> KEY_USUARIO_PRIMARY = createUniqueKey(Usuario.USUARIO, new TableField[] { Usuario.USUARIO.ID });
    public static final UniqueKey<UsuarioRecord> KEY_USUARIO_MATRICULA = createUniqueKey(Usuario.USUARIO, new TableField[] { Usuario.USUARIO.MATRICULA });
  }
  
  private static class Identities0
    extends AbstractKeys
  {
    public static Identity<ClRecord, Long> IDENTITY_CL = createIdentity(Cl.CL, Cl.CL.ID);
    public static Identity<ColetaRecord, Long> IDENTITY_COLETA = createIdentity(Coleta.COLETA, Coleta.COLETA.ID);
    public static Identity<EstoqueRecord, Long> IDENTITY_ESTOQUE = createIdentity(Estoque.ESTOQUE, Estoque.ESTOQUE.ID);
    public static Identity<FornecedorRecord, Long> IDENTITY_FORNECEDOR = createIdentity(Fornecedor.FORNECEDOR, Fornecedor.FORNECEDOR.ID);
    public static Identity<InventarioRecord, Long> IDENTITY_INVENTARIO = createIdentity(Inventario.INVENTARIO, Inventario.INVENTARIO.ID);
    public static Identity<LeituraRecord, Long> IDENTITY_LEITURA = createIdentity(Leitura.LEITURA, Leitura.LEITURA.ID);
    public static Identity<LojaRecord, Long> IDENTITY_LOJA = createIdentity(Loja.LOJA, Loja.LOJA.ID);
    public static Identity<LoteRecord, Long> IDENTITY_LOTE = createIdentity(Lote.LOTE, Lote.LOTE.ID);
    public static Identity<ProdutoRecord, Long> IDENTITY_PRODUTO = createIdentity(Produto.PRODUTO, Produto.PRODUTO.ID);
    public static Identity<UsuarioRecord, Long> IDENTITY_USUARIO = createIdentity(Usuario.USUARIO, Usuario.USUARIO.ID);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/Keys.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */