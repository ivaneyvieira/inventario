package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;

public class XYControlBuilder<B extends XYControlBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<XYControl>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final XYControlBuilder create()
  {
    return new XYControlBuilder();
  }
  
  public final XYControlBuilder xValue(double paramDouble)
  {
    this.properties.put("xValue", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final XYControlBuilder yValue(double paramDouble)
  {
    this.properties.put("yValue", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final XYControlBuilder xAxisLabel(String paramString)
  {
    this.properties.put("xAxisLabel", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final XYControlBuilder yAxisLabel(String paramString)
  {
    this.properties.put("yAxisLabel", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final XYControlBuilder xAxisLabelVisible(boolean paramBoolean)
  {
    this.properties.put("xAxisLabelVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final XYControlBuilder yAxisLabelVisible(boolean paramBoolean)
  {
    this.properties.put("yAxisLabelVisible", new SimpleBooleanProperty(paramBoolean));
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
  
  public final XYControl build()
  {
    XYControl localXYControl = new XYControl();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("xValue".equals(str)) {
        localXYControl.setXValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("yValue".equals(str)) {
        localXYControl.setYValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("xAxisLabel".equals(str)) {
        localXYControl.setXAxisLabel((String)((StringProperty)this.properties.get(str)).get());
      } else if ("yAxisLabel".equals(str)) {
        localXYControl.setYAxisLabel((String)((StringProperty)this.properties.get(str)).get());
      } else if ("xAxisLabelVisible".equals(str)) {
        localXYControl.setXAxisLabelVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("yAxisLabelVisible".equals(str)) {
        localXYControl.setYAxisLabelVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localXYControl.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localXYControl.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localXYControl.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localXYControl.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localXYControl;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/XYControlBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */