package br.com.pintos.framework.fx.view;

import br.com.pintos.framework.fx.viewmodel.VApplication;
import br.com.pintos.framework.fx.viewmodel.VModulo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

public class MenuBarApplication
  extends MenuBar
{
  private final VModulo modulos;
  private final ApplicationFX application;
  
  public MenuBarApplication(ApplicationFX paramApplicationFX)
  {
    this.modulos = paramApplicationFX.getApplication().getModulos();
    this.application = paramApplicationFX;
    for (VModulo localVModulo : this.modulos.getChildreAction())
    {
      Menu localMenu = new Menu(localVModulo.getDescricao());
      getMenus().add(localMenu);
      addSubMenu(localMenu, localVModulo);
    }
  }
  
  public void addSubMenu(Menu paramMenu, VModulo paramVModulo)
  {
    for (final VModulo localVModulo : paramVModulo.getChildreAction())
    {
      Object localObject;
      if (localVModulo.getFolha().booleanValue() == true)
      {
        localObject = new MenuItem(localVModulo.getDescricao());
        if (localVModulo.getAtalho() != null)
        {
          KeyCombination localKeyCombination = KeyCombination.keyCombination(localVModulo.getAtalho());
          ((MenuItem)localObject).setAccelerator(localKeyCombination);
        }
        ((MenuItem)localObject).setOnAction(new EventHandler()
        {
          public void handle(ActionEvent paramAnonymousActionEvent)
          {
            ExecMenu localExecMenu = localVModulo.makeExec();
            if (localExecMenu != null) {
              MenuBarApplication.this.application.execute(localExecMenu);
            }
          }
        });
        paramMenu.getItems().add(localObject);
        if (localVModulo.getIcon() != null) {
          ((MenuItem)localObject).setGraphic(Icon.icon(localVModulo.getIcon(), Integer.valueOf(16)));
        }
      }
      else
      {
        localObject = new Menu(localVModulo.getDescricao());
        addSubMenu((Menu)localObject, localVModulo);
      }
    }
  }
  
  public VModulo getModulos()
  {
    return this.modulos;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/view/MenuBarApplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */