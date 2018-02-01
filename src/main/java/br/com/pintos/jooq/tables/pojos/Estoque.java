package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.IEstoque;

public class Estoque
  implements IEstoque
{
  private static final long serialVersionUID = 748446081L;
  private Long id;
  private Integer quant;
  private Long inventarioId;
  private Long produtoId;
  private Integer version;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public Integer getQuant()
  {
    return this.quant;
  }
  
  public void setQuant(Integer paramInteger)
  {
    this.quant = paramInteger;
  }
  
  public Long getInventarioId()
  {
    return this.inventarioId;
  }
  
  public void setInventarioId(Long paramLong)
  {
    this.inventarioId = paramLong;
  }
  
  public Long getProdutoId()
  {
    return this.produtoId;
  }
  
  public void setProdutoId(Long paramLong)
  {
    this.produtoId = paramLong;
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


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Estoque.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */