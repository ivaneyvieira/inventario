package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;

public class TrafficLightBuilder<B extends TrafficLightBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<TrafficLight>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final TrafficLightBuilder create()
  {
    return new TrafficLightBuilder();
  }
  
  public final TrafficLightBuilder redOn(boolean paramBoolean)
  {
    this.properties.put("redOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final TrafficLightBuilder redBlinking(boolean paramBoolean)
  {
    this.properties.put("redBlinking", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final TrafficLightBuilder yellowOn(boolean paramBoolean)
  {
    this.properties.put("yellowOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final TrafficLightBuilder yellowBlinking(boolean paramBoolean)
  {
    this.properties.put("yellowBlinking", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final TrafficLightBuilder greenOn(boolean paramBoolean)
  {
    this.properties.put("greenOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final TrafficLightBuilder greenBlinking(boolean paramBoolean)
  {
    this.properties.put("greenBlinking", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final TrafficLightBuilder darkBackground(boolean paramBoolean)
  {
    this.properties.put("darkBackground", new SimpleBooleanProperty(paramBoolean));
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
  
  public final TrafficLight build()
  {
    TrafficLight localTrafficLight = new TrafficLight();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("redOn".equals(str)) {
        localTrafficLight.setRedOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("redBlinking".equals(str)) {
        localTrafficLight.setRedBlinking(((BooleanProperty)this.properties.get(str)).get());
      } else if ("yellowOn".equals(str)) {
        localTrafficLight.setYellowOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("yellowBlinking".equals(str)) {
        localTrafficLight.setYellowBlinking(((BooleanProperty)this.properties.get(str)).get());
      } else if ("greenOn".equals(str)) {
        localTrafficLight.setGreenOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("greenBlinking".equals(str)) {
        localTrafficLight.setGreenBlinking(((BooleanProperty)this.properties.get(str)).get());
      } else if ("darkBackground".equals(str)) {
        localTrafficLight.setDarkBackground(((BooleanProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localTrafficLight.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localTrafficLight.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localTrafficLight.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localTrafficLight.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localTrafficLight;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/TrafficLightBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */