package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.IViewultleitura;

public class Viewultleitura
  implements IViewultleitura
{
  private static final long serialVersionUID = -1617140096L;
  private Long inventarioId;
  private Long loteId;
  private Integer ultleitura;
  
  public Long getInventarioId()
  {
    return this.inventarioId;
  }
  
  public void setInventarioId(Long paramLong)
  {
    this.inventarioId = paramLong;
  }
  
  public Long getLoteId()
  {
    return this.loteId;
  }
  
  public void setLoteId(Long paramLong)
  {
    this.loteId = paramLong;
  }
  
  public Integer getUltleitura()
  {
    return this.ultleitura;
  }
  
  public void setUltleitura(Integer paramInteger)
  {
    this.ultleitura = paramInteger;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Viewultleitura.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */