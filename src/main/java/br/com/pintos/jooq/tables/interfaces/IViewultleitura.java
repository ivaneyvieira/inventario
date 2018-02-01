package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;

public abstract interface IViewultleitura
  extends Serializable
{
  public abstract void setInventarioId(Long paramLong);
  
  public abstract Long getInventarioId();
  
  public abstract void setLoteId(Long paramLong);
  
  public abstract Long getLoteId();
  
  public abstract void setUltleitura(Integer paramInteger);
  
  public abstract Integer getUltleitura();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/IViewultleitura.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */