package jfxtras.labs.scene.control.window;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MinimizeIcon
  extends WindowIcon
{
  public static final String DEFAULT_STYLE_CLASS = "window-minimize-icon";
  
  public MinimizeIcon(final Window paramWindow)
  {
    getStyleClass().setAll(new String[] { "window-minimize-icon" });
    setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        paramWindow.setMinimized(Boolean.valueOf(!paramWindow.isMinimized()));
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/window/MinimizeIcon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */