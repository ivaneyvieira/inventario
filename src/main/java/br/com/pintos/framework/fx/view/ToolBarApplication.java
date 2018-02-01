package br.com.pintos.framework.fx.view;

import br.com.pintos.framework.fx.viewmodel.VApplication;
import br.com.pintos.framework.fx.viewmodel.VModulo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;

public class ToolBarApplication
  extends ToolBar
{
  private final VModulo modulos;
  private final ApplicationFX application;
  
  public ToolBarApplication(ApplicationFX paramApplicationFX)
  {
    this.modulos = paramApplicationFX.getApplication().getModulos();
    this.application = paramApplicationFX;
    VModulo[] arrayOfVModulo1 = this.modulos.getChildreAction();
    for (VModulo localVModulo : arrayOfVModulo1)
    {
      addButtons(localVModulo);
      getItems().add(new Separator());
    }
  }
  
  public void addButtons(final VModulo paramVModulo)
  {
    Object localObject;
    if (paramVModulo.getFolha().booleanValue() == true)
    {
      if (paramVModulo.getIcon() != null)
      {
        localObject = new Button();
        ((Button)localObject).setGraphic(Icon.icon(paramVModulo.getIcon(), Integer.valueOf(50)));
        ((Button)localObject).setTooltip(new Tooltip(paramVModulo.getDescricao()));
        ((Button)localObject).setOnAction(new EventHandler()
        {
          public void handle(ActionEvent paramAnonymousActionEvent)
          {
            ExecMenu localExecMenu = paramVModulo.makeExec();
            if (localExecMenu != null) {
              ToolBarApplication.this.application.execute(localExecMenu);
            }
          }
        });
        getItems().add(localObject);
      }
    }
    else {
      for (VModulo localVModulo : paramVModulo.getChildreAction()) {
        addButtons(localVModulo);
      }
    }
  }
  
  public VModulo getModulos()
  {
    return this.modulos;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/view/ToolBarApplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */