package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.LeituraBo;
import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.jooq.tables.pojos.Viewcontagensmodel;
import java.util.List;

public class ModeloDivergencia
  extends ProviderModel<Viewcontagensmodel>
{
  private List<Viewcontagensmodel> dados;
  private final Long inventarioId;
  private final Long loteId;
  
  public ModeloDivergencia(Long paramLong1, Long paramLong2)
  {
    super("Divergencias", Viewcontagensmodel.class);
    this.inventarioId = paramLong1;
    this.loteId = paramLong2;
    addColuna("codigo", "Código");
    addColuna("descricao", "Descrição");
    addColuna("grade", "Grade");
    addColuna("numlote", "Lote", "000");
    addColuna("quant", "Quant");
    addColuna("l1", "L1");
    addColuna("l2", "L2");
    addColuna("l3", "L3");
    addColuna("l4", "L4");
    addColuna("l5", "L5");
    addColuna("l6", "L6");
    addColunaSTATUS();
  }
  
  public List<Viewcontagensmodel> dadosNovos()
  {
    this.dados = Facade.leitura.getDados(this.inventarioId, this.loteId);
    return this.dados;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/ModeloDivergencia.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */