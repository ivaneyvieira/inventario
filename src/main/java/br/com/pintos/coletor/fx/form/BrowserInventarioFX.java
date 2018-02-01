package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.InventarioBO;
import br.com.pintos.coletor.bos.InventarioBO.Status;
import br.com.pintos.coletor.bos.InventarioBO.Tipo;
import br.com.pintos.coletor.fx.bean.FInventario;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.fx.forms.BrowserFX;
import br.com.pintos.framework.fx.forms.FiltroFX;
import br.com.pintos.framework.fx.forms.FormBean;
import br.com.pintos.framework.fx.forms.ListaDialog;
import br.com.pintos.framework.fx.forms.TableFX;
import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.ShowDialog;
import br.com.pintos.jooq.tables.pojos.Inventario;
import javafx.collections.ObservableList;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

public class BrowserInventarioFX
  extends BrowserFX<Inventario, FInventario>
{
  public BrowserInventarioFX()
  {
    super("Editor de inventario");
  }
  
  private Command actionColetas()
  {
    new Command(this)
    {
      protected void run()
      {
        Inventario localInventario = (Inventario)BrowserInventarioFX.this.tableBean.selecionado();
        if (localInventario != null)
        {
          ListaDialog localListaDialog = new ListaDialog("Divergencias", new ModeloDivergencia(localInventario.getId(), null));
          localListaDialog.run(BrowserInventarioFX.this.application);
        }
      }
    };
  }
  
  private Command actionLotes()
  {
    new Command(this)
    {
      protected void run()
      {
        Inventario localInventario = (Inventario)BrowserInventarioFX.this.tableBean.selecionado();
        if (localInventario != null)
        {
          ListaLote localListaLote = new ListaLote(localInventario);
          localListaLote.run(BrowserInventarioFX.this.application);
        }
      }
    };
  }
  
  protected void delete(Inventario paramInventario)
    throws BOException
  {
    Facade.inventario.delete(paramInventario);
  }
  
  protected void insert(Inventario paramInventario)
    throws BOException
  {
    Facade.inventario.insert(paramInventario);
  }
  
  public Command localizar()
  {
    return null;
  }
  
  protected Inventario newBean()
  {
    Inventario localInventario = new Inventario();
    localInventario.setNumero(Facade.inventario.proximoNumero());
    localInventario.setData(new java.sql.Date(new java.util.Date().getTime()));
    localInventario.setTipoinventario(InventarioBO.Tipo.SIMPLES.toString());
    localInventario.setStatusinventario(InventarioBO.Status.ABERTO.toString());
    return localInventario;
  }
  
  protected FormBean<Inventario> newFormBean(Inventario paramInventario)
  {
    return new FormInventario(this, paramInventario);
  }
  
  protected FiltroFX<FInventario> newFormFiltro()
  {
    return new FiltroInventario(this, new FInventario());
  }
  
  protected ProviderModel<Inventario> newProviderTable()
  {
    return new ModeloInventario();
  }
  
  protected ToolBar toolBar()
  {
    ToolBar localToolBar = super.toolBar();
    ObservableList localObservableList = localToolBar.getItems();
    localObservableList.add(new Separator());
    localObservableList.add(createButton("Lotes", "lotes.png", actionLotes()));
    localObservableList.add(createButton("Coletas", "coletas.png", actionColetas()));
    return localToolBar;
  }
  
  protected void update(Inventario paramInventario)
    throws BOException
  {
    Facade.inventario.update(paramInventario);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/BrowserInventarioFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */