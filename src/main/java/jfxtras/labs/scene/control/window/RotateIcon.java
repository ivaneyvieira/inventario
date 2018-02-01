package jfxtras.labs.scene.control.window;

import javafx.animation.RotateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class RotateIcon
  extends WindowIcon
{
  public static final String DEFAULT_STYLE_CLASS = "window-rotate-icon";
  
  public RotateIcon(final Window paramWindow)
  {
    getStyleClass().setAll(new String[] { "window-rotate-icon" });
    setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        RotateTransition localRotateTransition = new RotateTransition();
        localRotateTransition.setAxis(Rotate.Z_AXIS);
        localRotateTransition.setDuration(Duration.seconds(1.0D));
        localRotateTransition.setByAngle(-360.0D);
        localRotateTransition.setNode(paramWindow);
        localRotateTransition.setCycleCount(1);
        localRotateTransition.play();
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/window/RotateIcon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */