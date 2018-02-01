package br.com.pintos.jooq.tables.pojos;

import br.com.pintos.jooq.tables.interfaces.IProduto;

public class Produto
  implements IProduto
{
  private static final long serialVersionUID = -2063754899L;
  private Long id;
  private String barcode;
  private String codigo;
  private String descricao;
  private Byte duplicado;
  private Byte foralinha;
  private String grade;
  private Byte usoconsumo;
  private Long clId;
  private Long fornecedorId;
  private Integer version;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getBarcode()
  {
    return this.barcode;
  }
  
  public void setBarcode(String paramString)
  {
    this.barcode = paramString;
  }
  
  public String getCodigo()
  {
    return this.codigo;
  }
  
  public void setCodigo(String paramString)
  {
    this.codigo = paramString;
  }
  
  public String getDescricao()
  {
    return this.descricao;
  }
  
  public void setDescricao(String paramString)
  {
    this.descricao = paramString;
  }
  
  public Byte getDuplicado()
  {
    return this.duplicado;
  }
  
  public void setDuplicado(Byte paramByte)
  {
    this.duplicado = paramByte;
  }
  
  public Byte getForalinha()
  {
    return this.foralinha;
  }
  
  public void setForalinha(Byte paramByte)
  {
    this.foralinha = paramByte;
  }
  
  public String getGrade()
  {
    return this.grade;
  }
  
  public void setGrade(String paramString)
  {
    this.grade = paramString;
  }
  
  public Byte getUsoconsumo()
  {
    return this.usoconsumo;
  }
  
  public void setUsoconsumo(Byte paramByte)
  {
    this.usoconsumo = paramByte;
  }
  
  public Long getClId()
  {
    return this.clId;
  }
  
  public void setClId(Long paramLong)
  {
    this.clId = paramLong;
  }
  
  public Long getFornecedorId()
  {
    return this.fornecedorId;
  }
  
  public void setFornecedorId(Long paramLong)
  {
    this.fornecedorId = paramLong;
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


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/pojos/Produto.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */