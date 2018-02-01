package br.com.pintos.coletor.vos;

import br.com.pintos.jooq.tables.pojos.Lote;
import br.com.pintos.jooq.tables.pojos.Produto;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Divergencia
{
  private final Lote lote;
  private final Produto produto;
  private final List<DivergenciaLeitura> divergenciaLeituras;
  
  public Divergencia(Lote paramLote, Produto paramProduto, List<DivergenciaLeitura> paramList)
  {
    this.lote = paramLote;
    this.produto = paramProduto;
    this.divergenciaLeituras = paramList;
  }
  
  public String getCodigo()
  {
    return this.produto.getCodigo().trim();
  }
  
  public Integer getCount(int paramInt)
  {
    if (leituras().size() >= paramInt) {
      return ((DivergenciaLeitura)leituras().get(paramInt - 1)).getCount();
    }
    return null;
  }
  
  public String getDescricao()
  {
    return this.produto.getDescricao();
  }
  
  public String getGrade()
  {
    return this.produto.getGrade();
  }
  
  public Integer getL1()
  {
    return getCount(1);
  }
  
  public Integer getL2()
  {
    return getCount(2);
  }
  
  public Integer getL3()
  {
    return getCount(3);
  }
  
  public Integer getL4()
  {
    return getCount(4);
  }
  
  public Integer getL5()
  {
    return getCount(5);
  }
  
  public Integer getL6()
  {
    return getCount(6);
  }
  
  public Integer getNumLote()
  {
    return this.lote.getNumero();
  }
  
  public Integer getQuant()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = leituras().iterator();
    while (localIterator.hasNext())
    {
      DivergenciaLeitura localDivergenciaLeitura = (DivergenciaLeitura)localIterator.next();
      Integer localInteger1 = localDivergenciaLeitura.getCount();
      Integer localInteger2 = (Integer)localHashMap.get(localInteger1);
      if (localInteger2 == null)
      {
        localHashMap.put(localInteger1, Integer.valueOf(1));
      }
      else
      {
        localInteger2 = Integer.valueOf(localInteger2.intValue() + 1);
        if (localInteger2.intValue() == 2) {
          return localInteger1;
        }
        localHashMap.put(localInteger1, localInteger2);
      }
    }
    return null;
  }
  
  public String getStatus()
  {
    if (getQuant() == null) {
      return "Aberto";
    }
    return "Fechado";
  }
  
  private List<DivergenciaLeitura> leituras()
  {
    return this.divergenciaLeituras;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/vos/Divergencia.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */