package br.com.pintos.framework.fx.view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabPaneApplication
  extends TabPane
{
  public Tab addNode(String paramString, Node paramNode)
  {
    Tab localTab = new Tab();
    localTab.setClosable(false);
    localTab.setText(paramString);
    localTab.setContent(paramNode);
    getTabs().add(localTab);
    return localTab;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/view/TabPaneApplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */