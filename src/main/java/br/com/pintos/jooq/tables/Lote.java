package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.LoteRecord;
import java.util.Arrays;
import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Lote
  extends UpdatableTableImpl<LoteRecord>
{
  private static final long serialVersionUID = 1436211932L;
  public static final Lote LOTE = new Lote();
  public final TableField<LoteRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<LoteRecord, String> DESCRICAO = createField("descricao", SQLDataType.CLOB, this);
  public final TableField<LoteRecord, Byte> LOTEAVULSO = createField("loteavulso", SQLDataType.TINYINT, this);
  public final TableField<LoteRecord, Integer> NUMERO = createField("numero", SQLDataType.INTEGER, this);
  public final TableField<LoteRecord, Long> LOJA_ID = createField("loja_id", SQLDataType.BIGINT, this);
  public final TableField<LoteRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  
  public Class<LoteRecord> getRecordType()
  {
    return LoteRecord.class;
  }
  
  public Lote()
  {
    super("lote", Coletor.COLETOR);
  }
  
  public Lote(String paramString)
  {
    super(paramString, Coletor.COLETOR, LOTE);
  }
  
  public Identity<LoteRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_LOTE;
  }
  
  public UniqueKey<LoteRecord> getMainKey()
  {
    return Keys.KEY_LOTE_PRIMARY;
  }
  
  public List<UniqueKey<LoteRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_LOTE_PRIMARY, Keys.KEY_LOTE_I1 });
  }
  
  public List<ForeignKey<LoteRecord, ?>> getReferences()
  {
    return Arrays.asList(new ForeignKey[] { Keys.FK_LOTES_LOJA_ID });
  }
  
  public Lote as(String paramString)
  {
    return new Lote(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Lote.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */