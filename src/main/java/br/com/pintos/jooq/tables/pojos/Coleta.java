package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.IColeta;

public class Coleta
  implements IColeta
{
  private static final long serialVersionUID = -1142019809L;
  private Long id;
  private Integer numleitura;
  private Long inventarioId;
  private Long loteId;
  private Long usuarioId;
  private Integer coletor;
  private String status;
  private Integer version;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public Integer getNumleitura()
  {
    return this.numleitura;
  }
  
  public void setNumleitura(Integer paramInteger)
  {
    this.numleitura = paramInteger;
  }
  
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
  
  public Long getUsuarioId()
  {
    return this.usuarioId;
  }
  
  public void setUsuarioId(Long paramLong)
  {
    this.usuarioId = paramLong;
  }
  
  public Integer getColetor()
  {
    return this.coletor;
  }
  
  public void setColetor(Integer paramInteger)
  {
    this.coletor = paramInteger;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
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


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Coleta.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */