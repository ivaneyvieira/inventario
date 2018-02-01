package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.ICl;

public class Cl
  implements ICl
{
  private static final long serialVersionUID = -1484236137L;
  private Long id;
  private Integer clno;
  private String departamento;
  private String grupo;
  private String secao;
  private Integer version;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public Integer getClno()
  {
    return this.clno;
  }
  
  public void setClno(Integer paramInteger)
  {
    this.clno = paramInteger;
  }
  
  public String getDepartamento()
  {
    return this.departamento;
  }
  
  public void setDepartamento(String paramString)
  {
    this.departamento = paramString;
  }
  
  public String getGrupo()
  {
    return this.grupo;
  }
  
  public void setGrupo(String paramString)
  {
    this.grupo = paramString;
  }
  
  public String getSecao()
  {
    return this.secao;
  }
  
  public void setSecao(String paramString)
  {
    this.secao = paramString;
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


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Cl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */