package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class LedBargraphBuilder<B extends LedBargraphBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<LedBargraph>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final LedBargraphBuilder create()
  {
    return new LedBargraphBuilder();
  }
  
  public final LedBargraphBuilder noOfLeds(int paramInt)
  {
    this.properties.put("noOfLeds", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final LedBargraphBuilder ledType(Led.Type paramType)
  {
    this.properties.put("ledType", new SimpleObjectProperty(paramType));
    return this;
  }
  
  public final LedBargraphBuilder orientation(Orientation paramOrientation)
  {
    this.properties.put("orientation", new SimpleObjectProperty(paramOrientation));
    return this;
  }
  
  public final LedBargraphBuilder peakValueVisible(boolean paramBoolean)
  {
    this.properties.put("peakValueVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LedBargraphBuilder ledSize(double paramDouble)
  {
    this.properties.put("ledSize", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final LedBargraphBuilder ledColors(LinkedList<Color> paramLinkedList)
  {
    this.properties.put("ledColors", new SimpleObjectProperty(paramLinkedList));
    return this;
  }
  
  public final LedBargraphBuilder ledColor(int paramInt, Color paramColor)
  {
    this.properties.put("ledColorIndex", new SimpleIntegerProperty(paramInt));
    this.properties.put("ledColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final LedBargraphBuilder value(double paramDouble)
  {
    this.properties.put("value", new SimpleDoubleProperty(paramDouble));
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
  
  public final LedBargraph build()
  {
    LedBargraph localLedBargraph = new LedBargraph();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("noOfLeds".equals(str)) {
        localLedBargraph.setNoOfLeds(((IntegerProperty)this.properties.get(str)).get());
      } else if ("ledType".equals(str)) {
        localLedBargraph.setLedType((Led.Type)((ObjectProperty)this.properties.get(str)).get());
      } else if ("orientation".equals(str)) {
        localLedBargraph.setOrientation((Orientation)((ObjectProperty)this.properties.get(str)).get());
      } else if ("peakValueVisible".equals(str)) {
        localLedBargraph.setPeakValueVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("ledSize".equals(str)) {
        localLedBargraph.setLedSize(((DoubleProperty)this.properties.get(str)).get());
      } else if ("ledColors".equals(str)) {
        localLedBargraph.setLedColors((LinkedList)((ObjectProperty)this.properties.get(str)).get());
      } else if ("ledColor".equals(str)) {
        localLedBargraph.setLedColor(((IntegerProperty)this.properties.get("ledColorIndex")).get(), (Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("value".equals(str)) {
        localLedBargraph.setValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localLedBargraph.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localLedBargraph.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localLedBargraph.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localLedBargraph.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localLedBargraph;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/LedBargraphBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */