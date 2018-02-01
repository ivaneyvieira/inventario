package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.LoteBo;
import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.fx.controls.ProviderModel.ESemaforo;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.pojos.Viewlotes;
import java.math.BigInteger;
import java.util.List;

public class ModeloLotes
  extends ProviderModel<Viewlotes>
{
  private final Inventario inv;
  private List<Viewlotes> dados;
  
  public ModeloLotes(Inventario paramInventario)
  {
    super("Lotes", Viewlotes.class);
    this.inv = paramInventario;
    addColuna("numlote", "Número", 6, "000");
    addColuna("desclote", "Descricao");
    addColuna("ultleitura", "Num Divergencia", 15);
    addColuna("divergencias", "Qt Divergencia", 15);
    addColunaSTATUS();
  }
  
  public List<Viewlotes> dadosNovos()
  {
    this.dados = Facade.lote.getLotes(this.inv);
    return this.dados;
  }
  
  public ProviderModel.ESemaforo getStatus(Viewlotes paramViewlotes)
  {
    BigInteger localBigInteger = paramViewlotes.getDivergencias();
    if (localBigInteger.intValue() == 0) {
      return ProviderModel.ESemaforo.verde;
    }
    return ProviderModel.ESemaforo.vermelho;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/ModeloLotes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */