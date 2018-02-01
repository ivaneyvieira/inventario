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

public class SimpleIndicatorBuilder<B extends SimpleIndicatorBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<SimpleIndicator>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final SimpleIndicatorBuilder create()
  {
    return new SimpleIndicatorBuilder();
  }
  
  public final SimpleIndicatorBuilder innerColor(Color paramColor)
  {
    this.properties.put("innerColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SimpleIndicatorBuilder outerColor(Color paramColor)
  {
    this.properties.put("outerColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SimpleIndicatorBuilder glowVisible(boolean paramBoolean)
  {
    this.properties.put("glowVisible", new SimpleBooleanProperty(paramBoolean));
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
  
  public final SimpleIndicator build()
  {
    SimpleIndicator localSimpleIndicator = new SimpleIndicator();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("innerColor".equals(str)) {
        localSimpleIndicator.setInnerColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("outerColor".equals(str)) {
        localSimpleIndicator.setOuterColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("glowVisible".equals(str)) {
        localSimpleIndicator.setGlowVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localSimpleIndicator.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localSimpleIndicator.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localSimpleIndicator.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localSimpleIndicator.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localSimpleIndicator;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SimpleIndicatorBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */