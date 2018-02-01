package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.fx.bean.FInventario;
import br.com.pintos.framework.fx.controls.ActionFX;
import br.com.pintos.framework.fx.controls.ControlCombo;
import br.com.pintos.framework.fx.controls.ControlDate;
import br.com.pintos.framework.fx.forms.FiltroFX;
import br.com.pintos.jooq.tables.pojos.Loja;

public class FiltroInventario
  extends FiltroFX<FInventario>
{
  private final BrowserInventarioFX browser;
  private final ControlDate dtInicial = new ControlDate("Data Inicial");
  private final ControlDate dtFinal = new ControlDate("Data Final");
  private final ControlCombo<Loja> cmbLoja = new ControlCombo("Loja", new ModeloLoja());
  
  public FiltroInventario(BrowserInventarioFX paramBrowserInventarioFX, FInventario paramFInventario)
  {
    super(paramBrowserInventarioFX, paramFInventario);
    this.browser = paramBrowserInventarioFX;
  }
  
  private void addButtonLocalizar()
  {
    layout(new ActionFX("Pequisar", "localizar.png", this.browser.localizar()));
  }
  
  public FInventario getBean()
  {
    ((FInventario)this.bean).setDataInicial(this.dtInicial.getValue());
    ((FInventario)this.bean).setDataFinal(this.dtFinal.getValue());
    ((FInventario)this.bean).setLoja((Loja)this.cmbLoja.getValue());
    return (FInventario)this.bean;
  }
  
  public void initControls()
  {
    setBean((FInventario)this.bean);
    layout(this.dtInicial);
    layout(this.dtFinal);
    layout(this.cmbLoja);
    addButtonLocalizar();
  }
  
  public void setBean(FInventario paramFInventario)
  {
    this.dtInicial.setValue(paramFInventario.getDataInicial());
    this.dtFinal.setValue(paramFInventario.getDataFinal());
    this.cmbLoja.setValue(paramFInventario.getLoja());
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/FiltroInventario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */