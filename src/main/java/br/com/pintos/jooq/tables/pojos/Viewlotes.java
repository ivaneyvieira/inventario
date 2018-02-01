package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.IViewlotes;
import java.math.BigInteger;

public class Viewlotes
  implements IViewlotes
{
  private static final long serialVersionUID = -501694950L;
  private Long inventarioId;
  private Long loteId;
  private Integer numlote;
  private String desclote;
  private Integer ultleitura;
  private BigInteger divergencias;
  
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
  
  public Integer getNumlote()
  {
    return this.numlote;
  }
  
  public void setNumlote(Integer paramInteger)
  {
    this.numlote = paramInteger;
  }
  
  public String getDesclote()
  {
    return this.desclote;
  }
  
  public void setDesclote(String paramString)
  {
    this.desclote = paramString;
  }
  
  public Integer getUltleitura()
  {
    return this.ultleitura;
  }
  
  public void setUltleitura(Integer paramInteger)
  {
    this.ultleitura = paramInteger;
  }
  
  public BigInteger getDivergencias()
  {
    return this.divergencias;
  }
  
  public void setDivergencias(BigInteger paramBigInteger)
  {
    this.divergencias = paramBigInteger;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Viewlotes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */