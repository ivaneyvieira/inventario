package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;

public abstract interface IFornecedor
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setCodigo(Integer paramInteger);
  
  public abstract Integer getCodigo();
  
  public abstract void setFantazia(String paramString);
  
  public abstract String getFantazia();
  
  public abstract void setRazao(String paramString);
  
  public abstract String getRazao();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/IFornecedor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */