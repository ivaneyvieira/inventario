package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.EstoqueRecord;
import java.util.Arrays;
import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Estoque
  extends UpdatableTableImpl<EstoqueRecord>
{
  private static final long serialVersionUID = 2035317078L;
  public static final Estoque ESTOQUE = new Estoque();
  public final TableField<EstoqueRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<EstoqueRecord, Integer> QUANT = createField("quant", SQLDataType.INTEGER, this);
  public final TableField<EstoqueRecord, Long> INVENTARIO_ID = createField("inventario_id", SQLDataType.BIGINT, this);
  public final TableField<EstoqueRecord, Long> PRODUTO_ID = createField("produto_id", SQLDataType.BIGINT, this);
  public final TableField<EstoqueRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  
  public Class<EstoqueRecord> getRecordType()
  {
    return EstoqueRecord.class;
  }
  
  public Estoque()
  {
    super("estoque", Coletor.COLETOR);
  }
  
  public Estoque(String paramString)
  {
    super(paramString, Coletor.COLETOR, ESTOQUE);
  }
  
  public Identity<EstoqueRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_ESTOQUE;
  }
  
  public UniqueKey<EstoqueRecord> getMainKey()
  {
    return Keys.KEY_ESTOQUE_PRIMARY;
  }
  
  public List<UniqueKey<EstoqueRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_ESTOQUE_PRIMARY, Keys.KEY_ESTOQUE_I1 });
  }
  
  public List<ForeignKey<EstoqueRecord, ?>> getReferences()
  {
    return Arrays.asList(new ForeignKey[] { Keys.FK_ESTOQUE_INVENTARIO_ID, Keys.FK_ESTOQUE_PRODUTO_ID });
  }
  
  public Estoque as(String paramString)
  {
    return new Estoque(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Estoque.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */