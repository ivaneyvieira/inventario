package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Viewultleitura;
import br.com.pintos.jooq.tables.interfaces.IViewultleitura;
import org.jooq.impl.TableRecordImpl;

public class ViewultleituraRecord
  extends TableRecordImpl<ViewultleituraRecord>
  implements IViewultleitura
{
  private static final long serialVersionUID = -1512563551L;
  
  public void setInventarioId(Long paramLong)
  {
    setValue(Viewultleitura.VIEWULTLEITURA.INVENTARIO_ID, paramLong);
  }
  
  public Long getInventarioId()
  {
    return (Long)getValue(Viewultleitura.VIEWULTLEITURA.INVENTARIO_ID);
  }
  
  public void setLoteId(Long paramLong)
  {
    setValue(Viewultleitura.VIEWULTLEITURA.LOTE_ID, paramLong);
  }
  
  public Long getLoteId()
  {
    return (Long)getValue(Viewultleitura.VIEWULTLEITURA.LOTE_ID);
  }
  
  public void setUltleitura(Integer paramInteger)
  {
    setValue(Viewultleitura.VIEWULTLEITURA.ULTLEITURA, paramInteger);
  }
  
  public Integer getUltleitura()
  {
    return (Integer)getValue(Viewultleitura.VIEWULTLEITURA.ULTLEITURA);
  }
  
  public ViewultleituraRecord()
  {
    super(Viewultleitura.VIEWULTLEITURA);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/ViewultleituraRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */