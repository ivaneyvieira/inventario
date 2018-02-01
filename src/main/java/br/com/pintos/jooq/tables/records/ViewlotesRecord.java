package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Viewlotes;
import br.com.pintos.jooq.tables.interfaces.IViewlotes;
import java.math.BigInteger;
import org.jooq.impl.TableRecordImpl;

public class ViewlotesRecord
  extends TableRecordImpl<ViewlotesRecord>
  implements IViewlotes
{
  private static final long serialVersionUID = -1829755741L;
  
  public void setInventarioId(Long paramLong)
  {
    setValue(Viewlotes.VIEWLOTES.INVENTARIO_ID, paramLong);
  }
  
  public Long getInventarioId()
  {
    return (Long)getValue(Viewlotes.VIEWLOTES.INVENTARIO_ID);
  }
  
  public void setLoteId(Long paramLong)
  {
    setValue(Viewlotes.VIEWLOTES.LOTE_ID, paramLong);
  }
  
  public Long getLoteId()
  {
    return (Long)getValue(Viewlotes.VIEWLOTES.LOTE_ID);
  }
  
  public void setNumlote(Integer paramInteger)
  {
    setValue(Viewlotes.VIEWLOTES.NUMLOTE, paramInteger);
  }
  
  public Integer getNumlote()
  {
    return (Integer)getValue(Viewlotes.VIEWLOTES.NUMLOTE);
  }
  
  public void setDesclote(String paramString)
  {
    setValue(Viewlotes.VIEWLOTES.DESCLOTE, paramString);
  }
  
  public String getDesclote()
  {
    return (String)getValue(Viewlotes.VIEWLOTES.DESCLOTE);
  }
  
  public void setUltleitura(Integer paramInteger)
  {
    setValue(Viewlotes.VIEWLOTES.ULTLEITURA, paramInteger);
  }
  
  public Integer getUltleitura()
  {
    return (Integer)getValue(Viewlotes.VIEWLOTES.ULTLEITURA);
  }
  
  public void setDivergencias(BigInteger paramBigInteger)
  {
    setValue(Viewlotes.VIEWLOTES.DIVERGENCIAS, paramBigInteger);
  }
  
  public BigInteger getDivergencias()
  {
    return (BigInteger)getValue(Viewlotes.VIEWLOTES.DIVERGENCIAS);
  }
  
  public ViewlotesRecord()
  {
    super(Viewlotes.VIEWLOTES);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/ViewlotesRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */