package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.IUsuario;

public class Usuario
  implements IUsuario
{
  private static final long serialVersionUID = -1997497461L;
  private Long id;
  private Integer matricula;
  private String nome;
  private String senha;
  private String apelido;
  private Integer version;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public Integer getMatricula()
  {
    return this.matricula;
  }
  
  public void setMatricula(Integer paramInteger)
  {
    this.matricula = paramInteger;
  }
  
  public String getNome()
  {
    return this.nome;
  }
  
  public void setNome(String paramString)
  {
    this.nome = paramString;
  }
  
  public String getSenha()
  {
    return this.senha;
  }
  
  public void setSenha(String paramString)
  {
    this.senha = paramString;
  }
  
  public String getApelido()
  {
    return this.apelido;
  }
  
  public void setApelido(String paramString)
  {
    this.apelido = paramString;
  }
  
  public Integer getVersion()
  {
    return this.version;
  }
  
  public void setVersion(Integer paramInteger)
  {
    this.version = paramInteger;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Usuario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */