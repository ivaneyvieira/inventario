package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.fx.controls.ProviderModel;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class RowFactoryTable<B>
  implements Callback<TableView<B>, TableRow<B>>
{
  private final ProviderModel<B> model;
  
  public RowFactoryTable(ProviderModel<B> paramProviderModel)
  {
    this.model = paramProviderModel;
  }
  
  public TableRow<B> call(TableView<B> paramTableView)
  {
    TableRow local1 = new TableRow()
    {
      protected void updateItem(B paramAnonymousB, boolean paramAnonymousBoolean)
      {
        super.updateItem(paramAnonymousB, paramAnonymousBoolean);
        String str = RowFactoryTable.this.model.getStyle(paramAnonymousB);
        if (str != null) {
          setStyle(str);
        }
      }
    };
    return local1;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/RowFactoryTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */