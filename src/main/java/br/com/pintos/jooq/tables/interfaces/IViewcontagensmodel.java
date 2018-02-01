package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;
import java.math.BigInteger;

public abstract interface IViewcontagensmodel
  extends Serializable
{
  public abstract void setInventarioId(Long paramLong);
  
  public abstract Long getInventarioId();
  
  public abstract void setLoteId(Long paramLong);
  
  public abstract Long getLoteId();
  
  public abstract void setProdutoId(Long paramLong);
  
  public abstract Long getProdutoId();
  
  public abstract void setUltleitura(Integer paramInteger);
  
  public abstract Integer getUltleitura();
  
  public abstract void setCodigo(String paramString);
  
  public abstract String getCodigo();
  
  public abstract void setDescricao(String paramString);
  
  public abstract String getDescricao();
  
  public abstract void setGrade(String paramString);
  
  public abstract String getGrade();
  
  public abstract void setNumlote(Integer paramInteger);
  
  public abstract Integer getNumlote();
  
  public abstract void setDesclote(String paramString);
  
  public abstract String getDesclote();
  
  public abstract void setQuant(BigInteger paramBigInteger);
  
  public abstract BigInteger getQuant();
  
  public abstract void setL1(BigInteger paramBigInteger);
  
  public abstract BigInteger getL1();
  
  public abstract void setL2(BigInteger paramBigInteger);
  
  public abstract BigInteger getL2();
  
  public abstract void setL3(BigInteger paramBigInteger);
  
  public abstract BigInteger getL3();
  
  public abstract void setL4(BigInteger paramBigInteger);
  
  public abstract BigInteger getL4();
  
  public abstract void setL5(BigInteger paramBigInteger);
  
  public abstract BigInteger getL5();
  
  public abstract void setL6(BigInteger paramBigInteger);
  
  public abstract BigInteger getL6();
  
  public abstract void setL7(BigInteger paramBigInteger);
  
  public abstract BigInteger getL7();
  
  public abstract void setL8(BigInteger paramBigInteger);
  
  public abstract BigInteger getL8();
  
  public abstract void setL9(BigInteger paramBigInteger);
  
  public abstract BigInteger getL9();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/IViewcontagensmodel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */