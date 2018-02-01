package jfxtras.labs.scene.control.window;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;

public class WindowIcon
  extends Control
{
  public static final String DEFAULT_STYLE_CLASS = "window-icon";
  private ObjectProperty<EventHandler<ActionEvent>> onActionProperty = new SimpleObjectProperty();
  
  public WindowIcon()
  {
    getStyleClass().setAll(new String[] { "window-icon" });
  }
  
  public ObjectProperty<EventHandler<ActionEvent>> onActionProperty()
  {
    return this.onActionProperty;
  }
  
  public EventHandler<ActionEvent> getOnAction()
  {
    return (EventHandler)this.onActionProperty.get();
  }
  
  public void setOnAction(EventHandler<ActionEvent> paramEventHandler)
  {
    this.onActionProperty.set(paramEventHandler);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/window/WindowIcon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */