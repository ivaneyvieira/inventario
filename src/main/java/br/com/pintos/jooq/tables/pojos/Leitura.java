package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.ILeitura;
import java.sql.Time;

public class Leitura
  implements ILeitura
{
  private static final long serialVersionUID = -1286661599L;
  private Long id;
  private Time hora;
  private String leitura;
  private String observacao;
  private Integer quant;
  private String status;
  private Long coletaId;
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
  
  public Time getHora()
  {
    return this.hora;
  }
  
  public void setHora(Time paramTime)
  {
    this.hora = paramTime;
  }
  
  public String getLeitura()
  {
    return this.leitura;
  }
  
  public void setLeitura(String paramString)
  {
    this.leitura = paramString;
  }
  
  public String getObservacao()
  {
    return this.observacao;
  }
  
  public void setObservacao(String paramString)
  {
    this.observacao = paramString;
  }
  
  public Integer getQuant()
  {
    return this.quant;
  }
  
  public void setQuant(Integer paramInteger)
  {
    this.quant = paramInteger;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public Long getColetaId()
  {
    return this.coletaId;
  }
  
  public void setColetaId(Long paramLong)
  {
    this.coletaId = paramLong;
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


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Leitura.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */