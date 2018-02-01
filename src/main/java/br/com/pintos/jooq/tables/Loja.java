package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.LojaRecord;
import java.util.Arrays;
import java.util.List;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Loja
  extends UpdatableTableImpl<LojaRecord>
{
  private static final long serialVersionUID = -1829283959L;
  public static final Loja LOJA = new Loja();
  public final TableField<LojaRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<LojaRecord, String> ENDERECO = createField("endereco", SQLDataType.VARCHAR, this);
  public final TableField<LojaRecord, String> NOME = createField("nome", SQLDataType.VARCHAR, this);
  public final TableField<LojaRecord, String> SIGLA = createField("sigla", SQLDataType.VARCHAR, this);
  public final TableField<LojaRecord, Integer> STORENO = createField("storeno", SQLDataType.INTEGER, this);
  public final TableField<LojaRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  
  public Class<LojaRecord> getRecordType()
  {
    return LojaRecord.class;
  }
  
  public Loja()
  {
    super("loja", Coletor.COLETOR);
  }
  
  public Loja(String paramString)
  {
    super(paramString, Coletor.COLETOR, LOJA);
  }
  
  public Identity<LojaRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_LOJA;
  }
  
  public UniqueKey<LojaRecord> getMainKey()
  {
    return Keys.KEY_LOJA_PRIMARY;
  }
  
  public List<UniqueKey<LojaRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_LOJA_PRIMARY, Keys.KEY_LOJA_STORENO });
  }
  
  public Loja as(String paramString)
  {
    return new Loja(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Loja.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */