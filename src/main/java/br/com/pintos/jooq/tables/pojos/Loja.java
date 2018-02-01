package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.ILoja;

public class Loja
  implements ILoja
{
  private static final long serialVersionUID = 1572860093L;
  private Long id;
  private String endereco;
  private String nome;
  private String sigla;
  private Integer storeno;
  private Integer version;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getEndereco()
  {
    return this.endereco;
  }
  
  public void setEndereco(String paramString)
  {
    this.endereco = paramString;
  }
  
  public String getNome()
  {
    return this.nome;
  }
  
  public void setNome(String paramString)
  {
    this.nome = paramString;
  }
  
  public String getSigla()
  {
    return this.sigla;
  }
  
  public void setSigla(String paramString)
  {
    this.sigla = paramString;
  }
  
  public Integer getStoreno()
  {
    return this.storeno;
  }
  
  public void setStoreno(Integer paramInteger)
  {
    this.storeno = paramInteger;
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


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Loja.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */