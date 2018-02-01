package br.com.pintos.coletor.console;

import br.com.pintos.coletor.bos.ColetaBo;
import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.LoteBo;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.tables.pojos.Coleta;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.pojos.Lote;
import br.com.pintos.jooq.tables.pojos.Usuario;

public class ModeloColetor
{
  public Coleta coleta;
  private final Inventario inventario;
  private final Usuario usuario;
  private final Integer coletor;
  
  public ModeloColetor(Inventario paramInventario, Usuario paramUsuario, Integer paramInteger)
    throws BOException
  {
    if (paramInventario == null) {
      throw new BOException("Nao ha inventario selecionado");
    }
    this.inventario = paramInventario;
    if (paramUsuario == null) {
      throw new BOException("Nao ha usuario selecionado");
    }
    this.usuario = paramUsuario;
    if (paramInteger == null) {
      throw new BOException("Nao ha coletor selecionado");
    }
    this.coletor = paramInteger;
    this.coleta = findColetaAberta();
  }
  
  public boolean checaColetor()
  {
    return this.coleta.getColetor().equals(getColetor());
  }
  
  private int compara(Lote paramLote1, Lote paramLote2)
  {
    Long localLong1;
    if (paramLote1 == null) {
      localLong1 = Long.valueOf(0L);
    } else {
      localLong1 = paramLote1.getId();
    }
    Long localLong2;
    if (paramLote2 == null) {
      localLong2 = Long.valueOf(0L);
    } else {
      localLong2 = paramLote2.getId();
    }
    return localLong1.compareTo(localLong2);
  }
  
  public void fechaColeta()
    throws BOException
  {
    if (this.coleta != null) {
      Facade.coleta.fechaColeta(this.coleta);
    }
    this.coleta = findColetaAberta();
  }
  
  private Coleta findColetaAberta()
  {
    return Facade.coleta.findColeta(this.inventario, this.usuario);
  }
  
  public Coleta getColeta()
  {
    return this.coleta;
  }
  
  public Integer getColetor()
  {
    return this.coletor;
  }
  
  public Inventario getInventario()
  {
    return this.inventario;
  }
  
  public Lote getLote()
  {
    if (this.coleta == null) {
      return null;
    }
    return (Lote)Facade.lote.findById(this.coleta.getLoteId());
  }
  
  public Usuario getUsuario()
  {
    return this.usuario;
  }
  
  public void setLote(Integer paramInteger)
    throws BOException
  {
    Inventario localInventario = getInventario();
    Long localLong = localInventario.getLojaId();
    Lote localLote = Facade.lote.findLote(localLong, paramInteger);
    setLote(localLote);
  }
  
  public void setLote(Lote paramLote)
    throws BOException
  {
    if (compara(paramLote, getLote()) != 0) {
      this.coleta = Facade.coleta.mudaLote(this.coleta, paramLote, this.inventario, this.usuario, this.coletor);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/console/ModeloColetor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */