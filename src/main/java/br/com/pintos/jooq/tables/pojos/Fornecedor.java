package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.IFornecedor;

public class Fornecedor
  implements IFornecedor
{
  private static final long serialVersionUID = 2146464018L;
  private Long id;
  private Integer codigo;
  private String fantazia;
  private String razao;
  private Integer version;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public Integer getCodigo()
  {
    return this.codigo;
  }
  
  public void setCodigo(Integer paramInteger)
  {
    this.codigo = paramInteger;
  }
  
  public String getFantazia()
  {
    return this.fantazia;
  }
  
  public void setFantazia(String paramString)
  {
    this.fantazia = paramString;
  }
  
  public String getRazao()
  {
    return this.razao;
  }
  
  public void setRazao(String paramString)
  {
    this.razao = paramString;
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


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Fornecedor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */