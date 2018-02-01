package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;
import java.sql.Date;

public abstract interface IInventario
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setNumero(Integer paramInteger);
  
  public abstract Integer getNumero();
  
  public abstract void setData(Date paramDate);
  
  public abstract Date getData();
  
  public abstract void setObservacao(String paramString);
  
  public abstract String getObservacao();
  
  public abstract void setTipoinventario(String paramString);
  
  public abstract String getTipoinventario();
  
  public abstract void setStatusinventario(String paramString);
  
  public abstract String getStatusinventario();
  
  public abstract void setLojaId(Long paramLong);
  
  public abstract Long getLojaId();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
  
  public abstract void setFornecedorId(Long paramLong);
  
  public abstract Long getFornecedorId();
  
  public abstract void setClId(Long paramLong);
  
  public abstract Long getClId();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/IInventario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */