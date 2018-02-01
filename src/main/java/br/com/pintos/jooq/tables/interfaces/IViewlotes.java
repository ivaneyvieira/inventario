package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;
import java.math.BigInteger;

public abstract interface IViewlotes
  extends Serializable
{
  public abstract void setInventarioId(Long paramLong);
  
  public abstract Long getInventarioId();
  
  public abstract void setLoteId(Long paramLong);
  
  public abstract Long getLoteId();
  
  public abstract void setNumlote(Integer paramInteger);
  
  public abstract Integer getNumlote();
  
  public abstract void setDesclote(String paramString);
  
  public abstract String getDesclote();
  
  public abstract void setUltleitura(Integer paramInteger);
  
  public abstract Integer getUltleitura();
  
  public abstract void setDivergencias(BigInteger paramBigInteger);
  
  public abstract BigInteger getDivergencias();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/IViewlotes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */