package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.tables.records.ViewlotesRecord;
import java.math.BigInteger;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Viewlotes
  extends TableImpl<ViewlotesRecord>
{
  private static final long serialVersionUID = -664730487L;
  public static final Viewlotes VIEWLOTES = new Viewlotes();
  public final TableField<ViewlotesRecord, Long> INVENTARIO_ID = createField("Inventario_id", SQLDataType.BIGINT, this);
  public final TableField<ViewlotesRecord, Long> LOTE_ID = createField("lote_id", SQLDataType.BIGINT, this);
  public final TableField<ViewlotesRecord, Integer> NUMLOTE = createField("numLote", SQLDataType.INTEGER, this);
  public final TableField<ViewlotesRecord, String> DESCLOTE = createField("descLote", SQLDataType.CLOB, this);
  public final TableField<ViewlotesRecord, Integer> ULTLEITURA = createField("ultLeitura", SQLDataType.INTEGER, this);
  public final TableField<ViewlotesRecord, BigInteger> DIVERGENCIAS = createField("divergencias", SQLDataType.DECIMAL_INTEGER, this);
  
  public Class<ViewlotesRecord> getRecordType()
  {
    return ViewlotesRecord.class;
  }
  
  public Viewlotes()
  {
    super("viewLotes", Coletor.COLETOR);
  }
  
  public Viewlotes(String paramString)
  {
    super(paramString, Coletor.COLETOR, VIEWLOTES);
  }
  
  public Viewlotes as(String paramString)
  {
    return new Viewlotes(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Viewlotes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */