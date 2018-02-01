package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;

public abstract interface IEstoque
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setQuant(Integer paramInteger);
  
  public abstract Integer getQuant();
  
  public abstract void setInventarioId(Long paramLong);
  
  public abstract Long getInventarioId();
  
  public abstract void setProdutoId(Long paramLong);
  
  public abstract Long getProdutoId();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/IEstoque.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */