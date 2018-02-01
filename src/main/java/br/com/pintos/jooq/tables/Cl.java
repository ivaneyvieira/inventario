package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.ClRecord;
import java.util.Arrays;
import java.util.List;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Cl
  extends UpdatableTableImpl<ClRecord>
{
  private static final long serialVersionUID = -1567967742L;
  public static final Cl CL = new Cl();
  public final TableField<ClRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<ClRecord, Integer> CLNO = createField("clno", SQLDataType.INTEGER, this);
  public final TableField<ClRecord, String> DEPARTAMENTO = createField("departamento", SQLDataType.VARCHAR, this);
  public final TableField<ClRecord, String> GRUPO = createField("grupo", SQLDataType.VARCHAR, this);
  public final TableField<ClRecord, String> SECAO = createField("secao", SQLDataType.VARCHAR, this);
  public final TableField<ClRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  
  public Class<ClRecord> getRecordType()
  {
    return ClRecord.class;
  }
  
  public Cl()
  {
    super("cl", Coletor.COLETOR);
  }
  
  public Cl(String paramString)
  {
    super(paramString, Coletor.COLETOR, CL);
  }
  
  public Identity<ClRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_CL;
  }
  
  public UniqueKey<ClRecord> getMainKey()
  {
    return Keys.KEY_CL_PRIMARY;
  }
  
  public List<UniqueKey<ClRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_CL_PRIMARY, Keys.KEY_CL_CLNO });
  }
  
  public Cl as(String paramString)
  {
    return new Cl(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Cl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */