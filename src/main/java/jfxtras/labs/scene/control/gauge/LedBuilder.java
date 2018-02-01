package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class LedBuilder<B extends LedBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<Led>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final LedBuilder create()
  {
    return new LedBuilder();
  }
  
  public final LedBuilder color(Color paramColor)
  {
    this.properties.put("color", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final LedBuilder type(Led.Type paramType)
  {
    this.properties.put("type", new SimpleObjectProperty(paramType));
    return this;
  }
  
  public final LedBuilder on(boolean paramBoolean)
  {
    this.properties.put("on", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LedBuilder frameVisible(boolean paramBoolean)
  {
    this.properties.put("frameVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LedBuilder blinking(boolean paramBoolean)
  {
    this.properties.put("blinking", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final B prefWidth(double paramDouble)
  {
    this.properties.put("prefWidth", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B prefHeight(double paramDouble)
  {
    this.properties.put("prefHeight", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutX(double paramDouble)
  {
    this.properties.put("layoutX", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutY(double paramDouble)
  {
    this.properties.put("layoutY", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final Led build()
  {
    Led localLed = new Led();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("color".equals(str)) {
        localLed.setColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("type".equals(str)) {
        localLed.setType((Led.Type)((ObjectProperty)this.properties.get(str)).get());
      } else if ("on".equals(str)) {
        localLed.setOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("blinking".equals(str)) {
        localLed.setBlinking(((BooleanProperty)this.properties.get(str)).get());
      } else if ("frameVisible".equals(str)) {
        localLed.setFrameVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localLed.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localLed.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localLed.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localLed.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localLed;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/LedBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */