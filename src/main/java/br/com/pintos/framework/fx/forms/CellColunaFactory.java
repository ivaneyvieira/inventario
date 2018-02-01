package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.fx.controls.Coluna;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CellColunaFactory<B, T>
  implements Callback<TableColumn<B, T>, TableCell<B, T>>
{
  private final Coluna coluna;
  
  public CellColunaFactory(Coluna paramColuna)
  {
    this.coluna = paramColuna;
  }
  
  private String alinhamento()
  {
    switch (this.coluna.getAlinhamento())
    {
    case right: 
      return "-fx-alignment: top-right";
    case center: 
      return "-fx-alignment: top-center";
    case left: 
      return "-fx-alignment: top-left";
    }
    return "-fx-alignment: top-left";
  }
  
  public TableCell<B, T> call(TableColumn<B, T> paramTableColumn)
  {
    TableCell local1 = new TableCell()
    {
      private String getString()
      {
        return getItem() == null ? "" : getItem().toString();
      }
      
      public void updateItem(T paramAnonymousT, boolean paramAnonymousBoolean)
      {
        super.updateItem(paramAnonymousT, paramAnonymousBoolean);
        setText(paramAnonymousBoolean ? null : getString());
        setGraphic(null);
      }
    };
    local1.setStyle(alinhamento());
    return local1;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/CellColunaFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */