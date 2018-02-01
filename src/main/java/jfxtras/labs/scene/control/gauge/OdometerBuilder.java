package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class OdometerBuilder<B extends OdometerBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<Odometer>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final OdometerBuilder create()
  {
    return new OdometerBuilder();
  }
  
  public final OdometerBuilder rotationPreset(int paramInt)
  {
    this.properties.put("rotationPreset", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final OdometerBuilder color(Color paramColor)
  {
    this.properties.put("color", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final OdometerBuilder decimalColor(Color paramColor)
  {
    this.properties.put("decimalColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final OdometerBuilder numberColor(Color paramColor)
  {
    this.properties.put("numberColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final OdometerBuilder noOfDecimals(int paramInt)
  {
    this.properties.put("noOfDecimals", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final OdometerBuilder noOfDigits(int paramInt)
  {
    this.properties.put("noOfDigits", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final OdometerBuilder interval(long paramLong)
  {
    this.properties.put("interval", new SimpleLongProperty(paramLong));
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
  
  public final Odometer build()
  {
    Odometer localOdometer = new Odometer();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("rotationPreset".equals(str)) {
        localOdometer.setRotationPreset(((IntegerProperty)this.properties.get(str)).get());
      } else if ("color".equals(str)) {
        localOdometer.setColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("decimalColor".equals(str)) {
        localOdometer.setDecimalColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("numberColor".equals(str)) {
        localOdometer.setNumberColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("noOfDecimals".equals(str)) {
        localOdometer.setNoOfDecimals(((IntegerProperty)this.properties.get(str)).get());
      } else if ("noOfDigits".equals(str)) {
        localOdometer.setNoOfDigits(((IntegerProperty)this.properties.get(str)).get());
      } else if ("interval".equals(str)) {
        localOdometer.setInterval(((LongProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localOdometer.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localOdometer.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localOdometer.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localOdometer.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localOdometer;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/OdometerBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */