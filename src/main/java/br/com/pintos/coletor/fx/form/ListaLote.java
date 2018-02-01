package br.com.pintos.coletor.fx.form;

import br.com.pintos.framework.fx.forms.ListaDialog;
import br.com.pintos.framework.fx.forms.TableFX;
import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.ShowDialog;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.pojos.Viewlotes;
import javafx.collections.ObservableList;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

public class ListaLote
  extends ListaDialog<Viewlotes>
{
  public ListaLote(Inventario paramInventario)
  {
    super("Lotes", new ModeloLotes(paramInventario));
  }
  
  private Command actionColetas()
  {
    new Command(this)
    {
      protected void run()
      {
        Viewlotes localViewlotes = (Viewlotes)ListaLote.this.table.selecionado();
        if (localViewlotes != null)
        {
          ListaDialog localListaDialog = new ListaDialog("Divergencias", new ModeloDivergencia(localViewlotes.getInventarioId(), localViewlotes.getLoteId()));
          localListaDialog.run(ListaLote.this.application);
        }
      }
    };
  }
  
  protected ToolBar toolBar()
  {
    ToolBar localToolBar = super.toolBar();
    localToolBar.getItems().add(new Separator());
    localToolBar.getItems().add(createButton("Coletas", "coletas.png", actionColetas()));
    return localToolBar;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/ListaLote.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */