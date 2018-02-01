package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.FornecedorRecord;
import java.util.Arrays;
import java.util.List;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Fornecedor
  extends UpdatableTableImpl<FornecedorRecord>
{
  private static final long serialVersionUID = 2031100728L;
  public static final Fornecedor FORNECEDOR = new Fornecedor();
  public final TableField<FornecedorRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<FornecedorRecord, Integer> CODIGO = createField("codigo", SQLDataType.INTEGER, this);
  public final TableField<FornecedorRecord, String> FANTAZIA = createField("fantazia", SQLDataType.VARCHAR, this);
  public final TableField<FornecedorRecord, String> RAZAO = createField("razao", SQLDataType.VARCHAR, this);
  public final TableField<FornecedorRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  
  public Class<FornecedorRecord> getRecordType()
  {
    return FornecedorRecord.class;
  }
  
  public Fornecedor()
  {
    super("fornecedor", Coletor.COLETOR);
  }
  
  public Fornecedor(String paramString)
  {
    super(paramString, Coletor.COLETOR, FORNECEDOR);
  }
  
  public Identity<FornecedorRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_FORNECEDOR;
  }
  
  public UniqueKey<FornecedorRecord> getMainKey()
  {
    return Keys.KEY_FORNECEDOR_PRIMARY;
  }
  
  public List<UniqueKey<FornecedorRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_FORNECEDOR_PRIMARY, Keys.KEY_FORNECEDOR_CODIGO });
  }
  
  public Fornecedor as(String paramString)
  {
    return new Fornecedor(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Fornecedor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */