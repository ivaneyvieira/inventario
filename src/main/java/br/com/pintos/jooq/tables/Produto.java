package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.ProdutoRecord;
import java.util.Arrays;
import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Produto
  extends UpdatableTableImpl<ProdutoRecord>
{
  private static final long serialVersionUID = 93616399L;
  public static final Produto PRODUTO = new Produto();
  public final TableField<ProdutoRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<ProdutoRecord, String> BARCODE = createField("barcode", SQLDataType.VARCHAR, this);
  public final TableField<ProdutoRecord, String> CODIGO = createField("codigo", SQLDataType.VARCHAR, this);
  public final TableField<ProdutoRecord, String> DESCRICAO = createField("descricao", SQLDataType.VARCHAR, this);
  public final TableField<ProdutoRecord, Byte> DUPLICADO = createField("duplicado", SQLDataType.TINYINT, this);
  public final TableField<ProdutoRecord, Byte> FORALINHA = createField("foralinha", SQLDataType.TINYINT, this);
  public final TableField<ProdutoRecord, String> GRADE = createField("grade", SQLDataType.VARCHAR, this);
  public final TableField<ProdutoRecord, Byte> USOCONSUMO = createField("usoconsumo", SQLDataType.TINYINT, this);
  public final TableField<ProdutoRecord, Long> CL_ID = createField("cl_id", SQLDataType.BIGINT, this);
  public final TableField<ProdutoRecord, Long> FORNECEDOR_ID = createField("fornecedor_id", SQLDataType.BIGINT, this);
  public final TableField<ProdutoRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  
  public Class<ProdutoRecord> getRecordType()
  {
    return ProdutoRecord.class;
  }
  
  public Produto()
  {
    super("produto", Coletor.COLETOR);
  }
  
  public Produto(String paramString)
  {
    super(paramString, Coletor.COLETOR, PRODUTO);
  }
  
  public Identity<ProdutoRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_PRODUTO;
  }
  
  public UniqueKey<ProdutoRecord> getMainKey()
  {
    return Keys.KEY_PRODUTO_PRIMARY;
  }
  
  public List<UniqueKey<ProdutoRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_PRODUTO_PRIMARY, Keys.KEY_PRODUTO_BARCODE, Keys.KEY_PRODUTO_I1 });
  }
  
  public List<ForeignKey<ProdutoRecord, ?>> getReferences()
  {
    return Arrays.asList(new ForeignKey[] { Keys.FK_PRODUTOS_CL_ID, Keys.FK_PRODUTOS_FORNECEDOR_ID });
  }
  
  public Produto as(String paramString)
  {
    return new Produto(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Produto.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */