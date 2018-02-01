package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Viewcontagensmodel;
import br.com.pintos.jooq.tables.interfaces.IViewcontagensmodel;
import java.math.BigInteger;
import org.jooq.impl.TableRecordImpl;

public class ViewcontagensmodelRecord
  extends TableRecordImpl<ViewcontagensmodelRecord>
  implements IViewcontagensmodel
{
  private static final long serialVersionUID = -1645454619L;
  
  public void setInventarioId(Long paramLong)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.INVENTARIO_ID, paramLong);
  }
  
  public Long getInventarioId()
  {
    return (Long)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.INVENTARIO_ID);
  }
  
  public void setLoteId(Long paramLong)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.LOTE_ID, paramLong);
  }
  
  public Long getLoteId()
  {
    return (Long)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.LOTE_ID);
  }
  
  public void setProdutoId(Long paramLong)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.PRODUTO_ID, paramLong);
  }
  
  public Long getProdutoId()
  {
    return (Long)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.PRODUTO_ID);
  }
  
  public void setUltleitura(Integer paramInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.ULTLEITURA, paramInteger);
  }
  
  public Integer getUltleitura()
  {
    return (Integer)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.ULTLEITURA);
  }
  
  public void setCodigo(String paramString)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.CODIGO, paramString);
  }
  
  public String getCodigo()
  {
    return (String)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.CODIGO);
  }
  
  public void setDescricao(String paramString)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.DESCRICAO, paramString);
  }
  
  public String getDescricao()
  {
    return (String)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.DESCRICAO);
  }
  
  public void setGrade(String paramString)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.GRADE, paramString);
  }
  
  public String getGrade()
  {
    return (String)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.GRADE);
  }
  
  public void setNumlote(Integer paramInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.NUMLOTE, paramInteger);
  }
  
  public Integer getNumlote()
  {
    return (Integer)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.NUMLOTE);
  }
  
  public void setDesclote(String paramString)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.DESCLOTE, paramString);
  }
  
  public String getDesclote()
  {
    return (String)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.DESCLOTE);
  }
  
  public void setQuant(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.QUANT, paramBigInteger);
  }
  
  public BigInteger getQuant()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.QUANT);
  }
  
  public void setL1(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L1, paramBigInteger);
  }
  
  public BigInteger getL1()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L1);
  }
  
  public void setL2(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L2, paramBigInteger);
  }
  
  public BigInteger getL2()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L2);
  }
  
  public void setL3(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L3, paramBigInteger);
  }
  
  public BigInteger getL3()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L3);
  }
  
  public void setL4(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L4, paramBigInteger);
  }
  
  public BigInteger getL4()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L4);
  }
  
  public void setL5(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L5, paramBigInteger);
  }
  
  public BigInteger getL5()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L5);
  }
  
  public void setL6(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L6, paramBigInteger);
  }
  
  public BigInteger getL6()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L6);
  }
  
  public void setL7(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L7, paramBigInteger);
  }
  
  public BigInteger getL7()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L7);
  }
  
  public void setL8(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L8, paramBigInteger);
  }
  
  public BigInteger getL8()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L8);
  }
  
  public void setL9(BigInteger paramBigInteger)
  {
    setValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L9, paramBigInteger);
  }
  
  public BigInteger getL9()
  {
    return (BigInteger)getValue(Viewcontagensmodel.VIEWCONTAGENSMODEL.L9);
  }
  
  public ViewcontagensmodelRecord()
  {
    super(Viewcontagensmodel.VIEWCONTAGENSMODEL);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/ViewcontagensmodelRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */