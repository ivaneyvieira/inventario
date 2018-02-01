package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.fx.controls.ProviderModel.ESemaforo;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PropertyStatusFactory<B>
  implements Callback<TableColumn.CellDataFeatures<B, ProviderModel.ESemaforo>, ObservableValue<ProviderModel.ESemaforo>>
{
  private final ProviderModel<B> model;
  
  public PropertyStatusFactory(ProviderModel<B> paramProviderModel)
  {
    this.model = paramProviderModel;
  }
  
  public ObservableValue<ProviderModel.ESemaforo> call(TableColumn.CellDataFeatures<B, ProviderModel.ESemaforo> paramCellDataFeatures)
  {
    Object localObject = paramCellDataFeatures.getValue();
    ProviderModel.ESemaforo localESemaforo = this.model.getStatus(localObject);
    return new ReadOnlyObjectWrapper(localESemaforo);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/PropertyStatusFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */