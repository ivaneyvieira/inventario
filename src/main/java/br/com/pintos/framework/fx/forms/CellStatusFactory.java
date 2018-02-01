package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.fx.controls.ProviderModel.ESemaforo;
import br.com.pintos.framework.fx.view.Icon;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class CellStatusFactory<B>
  implements Callback<TableColumn<B, ProviderModel.ESemaforo>, TableCell<B, ProviderModel.ESemaforo>>
{
  public TableCell<B, ProviderModel.ESemaforo> call(TableColumn<B, ProviderModel.ESemaforo> paramTableColumn)
  {
    TableCell local1 = new TableCell()
    {
      protected void updateItem(ProviderModel.ESemaforo paramAnonymousESemaforo, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousESemaforo != null)
        {
          String str = "";
          switch (CellStatusFactory.2.$SwitchMap$br$com$pintos$framework$fx$controls$ProviderModel$ESemaforo[paramAnonymousESemaforo.ordinal()])
          {
          case 1: 
            str = "verde.png";
            break;
          case 2: 
            str = "amarelo.png";
            break;
          case 3: 
            str = "vermelho.png";
          }
          ImageView localImageView = Icon.icon(str, Integer.valueOf(16));
          setGraphic(localImageView);
        }
      }
    };
    local1.setStyle("-fx-alignment: top-center");
    return local1;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/CellStatusFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */