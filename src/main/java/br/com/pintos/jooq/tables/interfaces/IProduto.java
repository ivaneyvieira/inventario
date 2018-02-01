package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;

public abstract interface IProduto
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setBarcode(String paramString);
  
  public abstract String getBarcode();
  
  public abstract void setCodigo(String paramString);
  
  public abstract String getCodigo();
  
  public abstract void setDescricao(String paramString);
  
  public abstract String getDescricao();
  
  public abstract void setDuplicado(Byte paramByte);
  
  public abstract Byte getDuplicado();
  
  public abstract void setForalinha(Byte paramByte);
  
  public abstract Byte getForalinha();
  
  public abstract void setGrade(String paramString);
  
  public abstract String getGrade();
  
  public abstract void setUsoconsumo(Byte paramByte);
  
  public abstract Byte getUsoconsumo();
  
  public abstract void setClId(Long paramLong);
  
  public abstract Long getClId();
  
  public abstract void setFornecedorId(Long paramLong);
  
  public abstract Long getFornecedorId();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/IProduto.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */