package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Viewcontagens;
import br.com.pintos.jooq.tables.interfaces.IViewcontagens;
import java.math.BigInteger;
import org.jooq.impl.TableRecordImpl;

public class ViewcontagensRecord
  extends TableRecordImpl<ViewcontagensRecord>
  implements IViewcontagens
{
  private static final long serialVersionUID = -1967619200L;
  
  public void setInventarioId(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.INVENTARIO_ID, paramLong);
  }
  
  public Long getInventarioId()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.INVENTARIO_ID);
  }
  
  public void setLoteId(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.LOTE_ID, paramLong);
  }
  
  public Long getLoteId()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.LOTE_ID);
  }
  
  public void setProdutoId(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.PRODUTO_ID, paramLong);
  }
  
  public Long getProdutoId()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.PRODUTO_ID);
  }
  
  public void setUltleitura(Integer paramInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.ULTLEITURA, paramInteger);
  }
  
  public Integer getUltleitura()
  {
    return (Integer)getValue(Viewcontagens.VIEWCONTAGENS.ULTLEITURA);
  }
  
  public void setStatuscoleta(String paramString)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.STATUSCOLETA, paramString);
  }
  
  public String getStatuscoleta()
  {
    return (String)getValue(Viewcontagens.VIEWCONTAGENS.STATUSCOLETA);
  }
  
  public void setL1(BigInteger paramBigInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.L1, paramBigInteger);
  }
  
  public BigInteger getL1()
  {
    return (BigInteger)getValue(Viewcontagens.VIEWCONTAGENS.L1);
  }
  
  public void setL2(BigInteger paramBigInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.L2, paramBigInteger);
  }
  
  public BigInteger getL2()
  {
    return (BigInteger)getValue(Viewcontagens.VIEWCONTAGENS.L2);
  }
  
  public void setL3(BigInteger paramBigInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.L3, paramBigInteger);
  }
  
  public BigInteger getL3()
  {
    return (BigInteger)getValue(Viewcontagens.VIEWCONTAGENS.L3);
  }
  
  public void setL4(BigInteger paramBigInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.L4, paramBigInteger);
  }
  
  public BigInteger getL4()
  {
    return (BigInteger)getValue(Viewcontagens.VIEWCONTAGENS.L4);
  }
  
  public void setL5(BigInteger paramBigInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.L5, paramBigInteger);
  }
  
  public BigInteger getL5()
  {
    return (BigInteger)getValue(Viewcontagens.VIEWCONTAGENS.L5);
  }
  
  public void setL6(BigInteger paramBigInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.L6, paramBigInteger);
  }
  
  public BigInteger getL6()
  {
    return (BigInteger)getValue(Viewcontagens.VIEWCONTAGENS.L6);
  }
  
  public void setL7(BigInteger paramBigInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.L7, paramBigInteger);
  }
  
  public BigInteger getL7()
  {
    return (BigInteger)getValue(Viewcontagens.VIEWCONTAGENS.L7);
  }
  
  public void setL8(BigInteger paramBigInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.L8, paramBigInteger);
  }
  
  public BigInteger getL8()
  {
    return (BigInteger)getValue(Viewcontagens.VIEWCONTAGENS.L8);
  }
  
  public void setL9(BigInteger paramBigInteger)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.L9, paramBigInteger);
  }
  
  public BigInteger getL9()
  {
    return (BigInteger)getValue(Viewcontagens.VIEWCONTAGENS.L9);
  }
  
  public void setC1(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.C1, paramLong);
  }
  
  public Long getC1()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.C1);
  }
  
  public void setC2(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.C2, paramLong);
  }
  
  public Long getC2()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.C2);
  }
  
  public void setC3(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.C3, paramLong);
  }
  
  public Long getC3()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.C3);
  }
  
  public void setC4(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.C4, paramLong);
  }
  
  public Long getC4()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.C4);
  }
  
  public void setC5(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.C5, paramLong);
  }
  
  public Long getC5()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.C5);
  }
  
  public void setC6(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.C6, paramLong);
  }
  
  public Long getC6()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.C6);
  }
  
  public void setC7(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.C7, paramLong);
  }
  
  public Long getC7()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.C7);
  }
  
  public void setC8(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.C8, paramLong);
  }
  
  public Long getC8()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.C8);
  }
  
  public void setC9(Long paramLong)
  {
    setValue(Viewcontagens.VIEWCONTAGENS.C9, paramLong);
  }
  
  public Long getC9()
  {
    return (Long)getValue(Viewcontagens.VIEWCONTAGENS.C9);
  }
  
  public ViewcontagensRecord()
  {
    super(Viewcontagens.VIEWCONTAGENS);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/ViewcontagensRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */