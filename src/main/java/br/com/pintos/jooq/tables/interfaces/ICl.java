package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;

public abstract interface ICl
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setClno(Integer paramInteger);
  
  public abstract Integer getClno();
  
  public abstract void setDepartamento(String paramString);
  
  public abstract String getDepartamento();
  
  public abstract void setGrupo(String paramString);
  
  public abstract String getGrupo();
  
  public abstract void setSecao(String paramString);
  
  public abstract String getSecao();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/ICl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */