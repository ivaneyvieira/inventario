package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.IInventario;
import java.sql.Date;

public class Inventario
  implements IInventario
{
  private static final long serialVersionUID = -163244383L;
  private Long id;
  private Integer numero;
  private Date data;
  private String observacao;
  private String tipoinventario;
  private String statusinventario;
  private Long lojaId;
  private Integer version;
  private Long fornecedorId;
  private Long clId;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public Integer getNumero()
  {
    return this.numero;
  }
  
  public void setNumero(Integer paramInteger)
  {
    this.numero = paramInteger;
  }
  
  public Date getData()
  {
    return this.data;
  }
  
  public void setData(Date paramDate)
  {
    this.data = paramDate;
  }
  
  public String getObservacao()
  {
    return this.observacao;
  }
  
  public void setObservacao(String paramString)
  {
    this.observacao = paramString;
  }
  
  public String getTipoinventario()
  {
    return this.tipoinventario;
  }
  
  public void setTipoinventario(String paramString)
  {
    this.tipoinventario = paramString;
  }
  
  public String getStatusinventario()
  {
    return this.statusinventario;
  }
  
  public void setStatusinventario(String paramString)
  {
    this.statusinventario = paramString;
  }
  
  public Long getLojaId()
  {
    return this.lojaId;
  }
  
  public void setLojaId(Long paramLong)
  {
    this.lojaId = paramLong;
  }
  
  public Integer getVersion()
  {
    return this.version;
  }
  
  public void setVersion(Integer paramInteger)
  {
    this.version = paramInteger;
  }
  
  public Long getFornecedorId()
  {
    return this.fornecedorId;
  }
  
  public void setFornecedorId(Long paramLong)
  {
    this.fornecedorId = paramLong;
  }
  
  public Long getClId()
  {
    return this.clId;
  }
  
  public void setClId(Long paramLong)
  {
    this.clId = paramLong;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Inventario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */