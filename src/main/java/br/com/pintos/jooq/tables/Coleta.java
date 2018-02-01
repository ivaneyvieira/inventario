package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.ColetaRecord;
import java.util.Arrays;
import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Coleta
  extends UpdatableTableImpl<ColetaRecord>
{
  private static final long serialVersionUID = 1475683166L;
  public static final Coleta COLETA = new Coleta();
  public final TableField<ColetaRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<ColetaRecord, Integer> NUMLEITURA = createField("numleitura", SQLDataType.INTEGER, this);
  public final TableField<ColetaRecord, Long> INVENTARIO_ID = createField("Inventario_id", SQLDataType.BIGINT, this);
  public final TableField<ColetaRecord, Long> LOTE_ID = createField("lote_id", SQLDataType.BIGINT, this);
  public final TableField<ColetaRecord, Long> USUARIO_ID = createField("usuario_id", SQLDataType.BIGINT, this);
  public final TableField<ColetaRecord, Integer> COLETOR = createField("coletor", SQLDataType.INTEGER, this);
  public final TableField<ColetaRecord, String> STATUS = createField("status", SQLDataType.VARCHAR, this);
  public final TableField<ColetaRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  
  public Class<ColetaRecord> getRecordType()
  {
    return ColetaRecord.class;
  }
  
  public Coleta()
  {
    super("coleta", Coletor.COLETOR);
  }
  
  public Coleta(String paramString)
  {
    super(paramString, Coletor.COLETOR, COLETA);
  }
  
  public Identity<ColetaRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_COLETA;
  }
  
  public UniqueKey<ColetaRecord> getMainKey()
  {
    return Keys.KEY_COLETA_PRIMARY;
  }
  
  public List<UniqueKey<ColetaRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_COLETA_PRIMARY });
  }
  
  public List<ForeignKey<ColetaRecord, ?>> getReferences()
  {
    return Arrays.asList(new ForeignKey[] { Keys.FK_COLETAS_INVENTARIO_ID, Keys.FK_COLETAS_LOTE_ID, Keys.FK_COLETAS_USUARIO_ID });
  }
  
  public Coleta as(String paramString)
  {
    return new Coleta(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Coleta.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */