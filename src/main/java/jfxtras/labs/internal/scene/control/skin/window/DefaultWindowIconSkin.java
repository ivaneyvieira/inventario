package jfxtras.labs.internal.scene.control.skin.window;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import jfxtras.labs.scene.control.window.WindowIcon;

public class DefaultWindowIconSkin
  extends SkinBase<WindowIcon, BehaviorBase<WindowIcon>>
{
  public DefaultWindowIconSkin(final WindowIcon paramWindowIcon)
  {
    super(paramWindowIcon, new BehaviorBase(paramWindowIcon));
    setCursor(Cursor.DEFAULT);
    onMouseClickedProperty().set(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (paramWindowIcon.getOnAction() != null) {
          paramWindowIcon.getOnAction().handle(new ActionEvent(paramAnonymousMouseEvent, paramWindowIcon));
        }
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/window/DefaultWindowIconSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */