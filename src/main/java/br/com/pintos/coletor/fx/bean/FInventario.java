package br.com.pintos.coletor.fx.bean;

import br.com.pintos.jooq.tables.pojos.Loja;
import java.util.Date;

public class FInventario
{
  private Date dataInicial;
  private Date dataFinal;
  private Loja loja;
  
  public Date getDataFinal()
  {
    return this.dataFinal;
  }
  
  public Date getDataInicial()
  {
    return this.dataInicial;
  }
  
  public Loja getLoja()
  {
    return this.loja;
  }
  
  public void setDataFinal(Date paramDate)
  {
    this.dataFinal = paramDate;
  }
  
  public void setDataInicial(Date paramDate)
  {
    this.dataInicial = paramDate;
  }
  
  public void setLoja(Loja paramLoja)
  {
    this.loja = paramLoja;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/bean/FInventario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */