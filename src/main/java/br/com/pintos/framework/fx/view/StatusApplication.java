package br.com.pintos.framework.fx.view;

import br.com.pintos.framework.fx.viewmodel.VModulo;
import java.util.Iterator;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StatusApplication
  extends ToolBar
{
  public void addAtalhos(List<VModulo> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      VModulo localVModulo = (VModulo)localIterator.next();
      if ((localVModulo.getFolha().booleanValue() == true) && (localVModulo.getAtalho() != null))
      {
        Label localLabel1 = new Label();
        localLabel1.setText(localVModulo.getAtalho());
        Font localFont = localLabel1.getFont();
        localFont = Font.font(localFont.getFamily(), FontWeight.BOLD, localFont.getSize());
        localLabel1.setFont(localFont);
        Label localLabel2 = new Label();
        localLabel2.setText(localVModulo.getDescricao());
        getItems().add(localLabel1);
        getItems().add(localLabel2);
        getItems().add(new Separator());
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/view/StatusApplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */