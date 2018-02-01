package br.com.pintos.framework.fx.controls;

import br.com.pintos.framework.fx.view.Icon;
import br.com.pintos.framework.util.Command;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class ActionFX
  extends Button
{
  private final Command command;
  
  public ActionFX(String paramString1, String paramString2, Command paramCommand)
  {
    this.command = paramCommand;
    setGraphic(Icon.icon(paramString2, Integer.valueOf(32)));
    setOnAction(new Action());
    setTooltip(new Tooltip(paramString1));
  }
  
  public class Action
    implements EventHandler<ActionEvent>
  {
    public Action() {}
    
    public void handle(ActionEvent paramActionEvent)
    {
      ActionFX.this.command.execute();
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ActionFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */