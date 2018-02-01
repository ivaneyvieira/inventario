package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.LeituraRecord;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Leitura
  extends UpdatableTableImpl<LeituraRecord>
{
  private static final long serialVersionUID = -1087211396L;
  public static final Leitura LEITURA = new Leitura();
  public final TableField<LeituraRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<LeituraRecord, Time> HORA = createField("hora", SQLDataType.TIME, this);
  public final TableField<LeituraRecord, String> LEITURA_ = createField("leitura", SQLDataType.VARCHAR, this);
  public final TableField<LeituraRecord, String> OBSERVACAO = createField("observacao", SQLDataType.CLOB, this);
  public final TableField<LeituraRecord, Integer> QUANT = createField("quant", SQLDataType.INTEGER, this);
  public final TableField<LeituraRecord, String> STATUS = createField("status", SQLDataType.VARCHAR, this);
  public final TableField<LeituraRecord, Long> COLETA_ID = createField("coleta_id", SQLDataType.BIGINT, this);
  public final TableField<LeituraRecord, Long> PRODUTO_ID = createField("produto_id", SQLDataType.BIGINT, this);
  public final TableField<LeituraRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  
  public Class<LeituraRecord> getRecordType()
  {
    return LeituraRecord.class;
  }
  
  public Leitura()
  {
    super("leitura", Coletor.COLETOR);
  }
  
  public Leitura(String paramString)
  {
    super(paramString, Coletor.COLETOR, LEITURA);
  }
  
  public Identity<LeituraRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_LEITURA;
  }
  
  public UniqueKey<LeituraRecord> getMainKey()
  {
    return Keys.KEY_LEITURA_PRIMARY;
  }
  
  public List<UniqueKey<LeituraRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_LEITURA_PRIMARY });
  }
  
  public List<ForeignKey<LeituraRecord, ?>> getReferences()
  {
    return Arrays.asList(new ForeignKey[] { Keys.FK_LEITURA_COLETA_ID, Keys.FK_LEITURA_PRODUTO_ID });
  }
  
  public Leitura as(String paramString)
  {
    return new Leitura(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Leitura.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */