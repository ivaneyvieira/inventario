package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class Led
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "led";
  private ObjectProperty<Type> type = new SimpleObjectProperty(Type.ROUND);
  private ObjectProperty<Color> color = new SimpleObjectProperty(Color.RED);
  private BooleanProperty on = new SimpleBooleanProperty(false);
  private BooleanProperty blinking = new SimpleBooleanProperty(false);
  private BooleanProperty frameVisible = new SimpleBooleanProperty(true);
  
  public Led()
  {
    getStyleClass().add("led");
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 <= paramDouble2 ? paramDouble1 : paramDouble2;
    super.setPrefSize(d, d);
  }
  
  public void setMinSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 <= paramDouble2 ? paramDouble1 : paramDouble2;
    super.setMinSize(d, d);
  }
  
  public void setMaxSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 <= paramDouble2 ? paramDouble1 : paramDouble2;
    super.setMaxSize(d, d);
  }
  
  public final Type getType()
  {
    return (Type)this.type.get();
  }
  
  public final void setType(Type paramType)
  {
    this.type.set(paramType);
  }
  
  public final ObjectProperty<Type> typeProperty()
  {
    return this.type;
  }
  
  public final Color getColor()
  {
    return (Color)this.color.get();
  }
  
  public final void setColor(Color paramColor)
  {
    this.color.set(paramColor);
  }
  
  public final ObjectProperty<Color> colorProperty()
  {
    return this.color;
  }
  
  public final boolean isOn()
  {
    return this.on.get();
  }
  
  public final void setOn(boolean paramBoolean)
  {
    this.on.set(paramBoolean);
  }
  
  public final BooleanProperty onProperty()
  {
    return this.on;
  }
  
  public final boolean isBlinking()
  {
    return this.blinking.get();
  }
  
  public final void setBlinking(boolean paramBoolean)
  {
    this.blinking.set(paramBoolean);
  }
  
  public final BooleanProperty blinkingProperty()
  {
    return this.blinking;
  }
  
  public final boolean isFrameVisible()
  {
    return this.frameVisible.get();
  }
  
  public final void setFrameVisible(boolean paramBoolean)
  {
    this.frameVisible.set(paramBoolean);
  }
  
  public final BooleanProperty frameVisibleProperty()
  {
    return this.frameVisible;
  }
  
  public String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
  
  public static enum Type
  {
    ROUND,  SQUARE,  VERTICAL,  HORIZONTAL;
    
    private Type() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Led.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */