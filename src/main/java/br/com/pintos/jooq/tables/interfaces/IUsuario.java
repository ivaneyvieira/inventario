package br.com.pintos.jooq.tables.interfaces;

import java.io.Serializable;

public abstract interface IUsuario
  extends Serializable
{
  public abstract void setId(Long paramLong);
  
  public abstract Long getId();
  
  public abstract void setMatricula(Integer paramInteger);
  
  public abstract Integer getMatricula();
  
  public abstract void setNome(String paramString);
  
  public abstract String getNome();
  
  public abstract void setSenha(String paramString);
  
  public abstract String getSenha();
  
  public abstract void setApelido(String paramString);
  
  public abstract String getApelido();
  
  public abstract void setVersion(Integer paramInteger);
  
  public abstract Integer getVersion();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/interfaces/IUsuario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */