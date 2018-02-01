package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.fx.controls.Coluna;
import br.com.pintos.framework.util.Beans;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PropertyFormatFactory<B>
  implements Callback<TableColumn.CellDataFeatures<B, String>, ObservableValue<String>>
{
  private final Coluna coluna;
  
  public PropertyFormatFactory(Coluna paramColuna)
  {
    this.coluna = paramColuna;
  }
  
  public ObservableValue<String> call(TableColumn.CellDataFeatures<B, String> paramCellDataFeatures)
  {
    Object localObject1 = paramCellDataFeatures.getValue();
    String str1 = this.coluna.getNome();
    Object localObject2 = Util.bean.get(localObject1, str1);
    String str2 = Util.string.format(localObject2);
    return new ReadOnlyStringWrapper(str2);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/PropertyFormatFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */