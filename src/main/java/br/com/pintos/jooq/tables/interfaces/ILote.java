package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;

public abstract interface ILote
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setDescricao(String paramString);
  
  public abstract String getDescricao();
  
  public abstract void setLoteavulso(Byte paramByte);
  
  public abstract Byte getLoteavulso();
  
  public abstract void setNumero(Integer paramInteger);
  
  public abstract Integer getNumero();
  
  public abstract void setLojaId(Long paramLong);
  
  public abstract Long getLojaId();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/ILote.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */