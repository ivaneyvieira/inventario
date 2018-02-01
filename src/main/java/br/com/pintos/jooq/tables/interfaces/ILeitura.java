package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;
import java.sql.Time;

public abstract interface ILeitura
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setHora(Time paramTime);
  
  public abstract Time getHora();
  
  public abstract void setLeitura(String paramString);
  
  public abstract String getLeitura();
  
  public abstract void setObservacao(String paramString);
  
  public abstract String getObservacao();
  
  public abstract void setQuant(Integer paramInteger);
  
  public abstract Integer getQuant();
  
  public abstract void setStatus(String paramString);
  
  public abstract String getStatus();
  
  public abstract void setColetaId(Long paramLong);
  
  public abstract Long getColetaId();
  
  public abstract void setProdutoId(Long paramLong);
  
  public abstract Long getProdutoId();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/ILeitura.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */