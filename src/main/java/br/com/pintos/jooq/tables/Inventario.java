package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.InventarioRecord;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Inventario
  extends UpdatableTableImpl<InventarioRecord>
{
  private static final long serialVersionUID = -1390565465L;
  public static final Inventario INVENTARIO = new Inventario();
  public final TableField<InventarioRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<InventarioRecord, Integer> NUMERO = createField("numero", SQLDataType.INTEGER, this);
  public final TableField<InventarioRecord, Date> DATA = createField("data", SQLDataType.DATE, this);
  public final TableField<InventarioRecord, String> OBSERVACAO = createField("observacao", SQLDataType.CLOB, this);
  public final TableField<InventarioRecord, String> TIPOINVENTARIO = createField("tipoInventario", SQLDataType.VARCHAR, this);
  public final TableField<InventarioRecord, String> STATUSINVENTARIO = createField("statusInventario", SQLDataType.VARCHAR, this);
  public final TableField<InventarioRecord, Long> LOJA_ID = createField("loja_id", SQLDataType.BIGINT, this);
  public final TableField<InventarioRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  public final TableField<InventarioRecord, Long> FORNECEDOR_ID = createField("fornecedor_id", SQLDataType.BIGINT, this);
  public final TableField<InventarioRecord, Long> CL_ID = createField("cl_id", SQLDataType.BIGINT, this);
  
  public Class<InventarioRecord> getRecordType()
  {
    return InventarioRecord.class;
  }
  
  public Inventario()
  {
    super("inventario", Coletor.COLETOR);
  }
  
  public Inventario(String paramString)
  {
    super(paramString, Coletor.COLETOR, INVENTARIO);
  }
  
  public Identity<InventarioRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_INVENTARIO;
  }
  
  public UniqueKey<InventarioRecord> getMainKey()
  {
    return Keys.KEY_INVENTARIO_PRIMARY;
  }
  
  public List<UniqueKey<InventarioRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_INVENTARIO_PRIMARY, Keys.KEY_INVENTARIO_I2 });
  }
  
  public List<ForeignKey<InventarioRecord, ?>> getReferences()
  {
    return Arrays.asList(new ForeignKey[] { Keys.FK_INVENTARIO_LOJA_ID, Keys.FK_INVENTARIO_FORNECEDOR1, Keys.FK_INVENTARIO_CL1 });
  }
  
  public Inventario as(String paramString)
  {
    return new Inventario(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Inventario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */