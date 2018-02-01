package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class SimpleIndicator
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "simple-indicator";
  private ObjectProperty<Color> innerColor;
  private ObjectProperty<Color> outerColor;
  private BooleanProperty glowVisible;
  
  public SimpleIndicator()
  {
    this(Color.rgb(153, 255, 255), Color.rgb(0, 29, 255), true);
  }
  
  public SimpleIndicator(Color paramColor1, Color paramColor2, boolean paramBoolean)
  {
    this.innerColor = new SimpleObjectProperty(paramColor1);
    this.outerColor = new SimpleObjectProperty(paramColor2);
    this.glowVisible = new SimpleBooleanProperty(paramBoolean);
    getStyleClass().add("simple-indicator");
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 < paramDouble2 * 1.0D ? paramDouble1 * 1.0D : paramDouble2;
    super.setPrefSize(d, d);
  }
  
  public final Color getInnerColor()
  {
    return (Color)this.innerColor.get();
  }
  
  public final void setInnerColor(Color paramColor)
  {
    this.innerColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> innerColorProperty()
  {
    return this.innerColor;
  }
  
  public final Color getOuterColor()
  {
    return (Color)this.outerColor.get();
  }
  
  public final void setOuterColor(Color paramColor)
  {
    this.outerColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> outerColorProperty()
  {
    return this.outerColor;
  }
  
  public final boolean isGlowVisible()
  {
    return this.glowVisible.get();
  }
  
  public final void setGlowVisible(boolean paramBoolean)
  {
    this.glowVisible.set(paramBoolean);
  }
  
  public final BooleanProperty glowVisibleProperty()
  {
    return this.glowVisible;
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SimpleIndicator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */