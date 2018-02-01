package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.tables.records.ViewultleituraRecord;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Viewultleitura
  extends TableImpl<ViewultleituraRecord>
{
  private static final long serialVersionUID = -1910808940L;
  public static final Viewultleitura VIEWULTLEITURA = new Viewultleitura();
  public final TableField<ViewultleituraRecord, Long> INVENTARIO_ID = createField("Inventario_id", SQLDataType.BIGINT, this);
  public final TableField<ViewultleituraRecord, Long> LOTE_ID = createField("lote_id", SQLDataType.BIGINT, this);
  public final TableField<ViewultleituraRecord, Integer> ULTLEITURA = createField("ultLeitura", SQLDataType.INTEGER, this);
  
  public Class<ViewultleituraRecord> getRecordType()
  {
    return ViewultleituraRecord.class;
  }
  
  public Viewultleitura()
  {
    super("viewUltLeitura", Coletor.COLETOR);
  }
  
  public Viewultleitura(String paramString)
  {
    super(paramString, Coletor.COLETOR, VIEWULTLEITURA);
  }
  
  public Viewultleitura as(String paramString)
  {
    return new Viewultleitura(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Viewultleitura.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */