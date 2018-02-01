package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;

public abstract interface IColeta
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setNumleitura(Integer paramInteger);
  
  public abstract Integer getNumleitura();
  
  public abstract void setInventarioId(Long paramLong);
  
  public abstract Long getInventarioId();
  
  public abstract void setLoteId(Long paramLong);
  
  public abstract Long getLoteId();
  
  public abstract void setUsuarioId(Long paramLong);
  
  public abstract Long getUsuarioId();
  
  public abstract void setColetor(Integer paramInteger);
  
  public abstract Integer getColetor();
  
  public abstract void setStatus(String paramString);
  
  public abstract String getStatus();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/IColeta.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */