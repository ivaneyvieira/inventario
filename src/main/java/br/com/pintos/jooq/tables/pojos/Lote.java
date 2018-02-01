package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.ILote;

public class Lote
  implements ILote
{
  private static final long serialVersionUID = 161106881L;
  private Long id;
  private String descricao;
  private Byte loteavulso;
  private Integer numero;
  private Long lojaId;
  private Integer version;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getDescricao()
  {
    return this.descricao;
  }
  
  public void setDescricao(String paramString)
  {
    this.descricao = paramString;
  }
  
  public Byte getLoteavulso()
  {
    return this.loteavulso;
  }
  
  public void setLoteavulso(Byte paramByte)
  {
    this.loteavulso = paramByte;
  }
  
  public Integer getNumero()
  {
    return this.numero;
  }
  
  public void setNumero(Integer paramInteger)
  {
    this.numero = paramInteger;
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
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Lote.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */