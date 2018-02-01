package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;

public abstract interface ILoja
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setEndereco(String paramString);
  
  public abstract String getEndereco();
  
  public abstract void setNome(String paramString);
  
  public abstract String getNome();
  
  public abstract void setSigla(String paramString);
  
  public abstract String getSigla();
  
  public abstract void setStoreno(Integer paramInteger);
  
  public abstract Integer getStoreno();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/ILoja.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */