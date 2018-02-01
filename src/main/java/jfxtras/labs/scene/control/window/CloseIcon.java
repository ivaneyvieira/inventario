package jfxtras.labs.scene.control.window;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CloseIcon
  extends WindowIcon
{
  public static final String DEFAULT_STYLE_CLASS = "window-close-icon";
  
  public CloseIcon(final Window paramWindow)
  {
    getStyleClass().setAll(new String[] { "window-close-icon" });
    setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        paramWindow.close();
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/window/CloseIcon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */