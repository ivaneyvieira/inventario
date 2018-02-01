package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.tables.records.ViewcontagensRecord;
import java.math.BigInteger;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Viewcontagens
  extends TableImpl<ViewcontagensRecord>
{
  private static final long serialVersionUID = 12526509L;
  public static final Viewcontagens VIEWCONTAGENS = new Viewcontagens();
  public final TableField<ViewcontagensRecord, Long> INVENTARIO_ID = createField("Inventario_id", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> LOTE_ID = createField("lote_id", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> PRODUTO_ID = createField("produto_id", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Integer> ULTLEITURA = createField("ultLeitura", SQLDataType.INTEGER, this);
  public final TableField<ViewcontagensRecord, String> STATUSCOLETA = createField("statusColeta", SQLDataType.VARCHAR, this);
  public final TableField<ViewcontagensRecord, BigInteger> L1 = createField("l1", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensRecord, BigInteger> L2 = createField("l2", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensRecord, BigInteger> L3 = createField("l3", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensRecord, BigInteger> L4 = createField("l4", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensRecord, BigInteger> L5 = createField("l5", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensRecord, BigInteger> L6 = createField("l6", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensRecord, BigInteger> L7 = createField("l7", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensRecord, BigInteger> L8 = createField("l8", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensRecord, BigInteger> L9 = createField("l9", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensRecord, Long> C1 = createField("c1", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> C2 = createField("c2", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> C3 = createField("c3", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> C4 = createField("c4", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> C5 = createField("c5", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> C6 = createField("c6", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> C7 = createField("c7", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> C8 = createField("c8", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensRecord, Long> C9 = createField("c9", SQLDataType.BIGINT, this);
  
  public Class<ViewcontagensRecord> getRecordType()
  {
    return ViewcontagensRecord.class;
  }
  
  public Viewcontagens()
  {
    super("viewContagens", Coletor.COLETOR);
  }
  
  public Viewcontagens(String paramString)
  {
    super(paramString, Coletor.COLETOR, VIEWCONTAGENS);
  }
  
  public Viewcontagens as(String paramString)
  {
    return new Viewcontagens(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Viewcontagens.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */