package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;

public class LinearScaleBuilder<B extends LinearScaleBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<LinearScale>
{
  private Map<String, Property> properties = new HashMap();
  
  public static LinearScaleBuilder create()
  {
    return new LinearScaleBuilder();
  }
  
  public final LinearScaleBuilder minValue(double paramDouble)
  {
    this.properties.put("minValue", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final LinearScaleBuilder maxValue(double paramDouble)
  {
    this.properties.put("maxValue", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final LinearScaleBuilder niceScaling(boolean paramBoolean)
  {
    this.properties.put("niceScaling", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LinearScaleBuilder maxNoOfMajorTicks(int paramInt)
  {
    this.properties.put("maxNoOfMajorTicks", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final LinearScaleBuilder maxNoOfMinorTicks(int paramInt)
  {
    this.properties.put("maxNoOfMinorTicks", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final LinearScaleBuilder majorTickSpacing(double paramDouble)
  {
    this.properties.put("majorTickSpacing", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final LinearScaleBuilder minorTickSpacing(double paramDouble)
  {
    this.properties.put("minorTickSpacing", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final LinearScaleBuilder tightScale(boolean paramBoolean)
  {
    this.properties.put("tightScale", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LinearScaleBuilder largeNumberScale(boolean paramBoolean)
  {
    this.properties.put("largeNumberScale", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public LinearScale build()
  {
    LinearScale localLinearScale = new LinearScale();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("minValue".equals(str)) {
        localLinearScale.setMinValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("maxValue".equals(str)) {
        localLinearScale.setMaxValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("niceScaling".equals(str)) {
        localLinearScale.setNiceScaling(((BooleanProperty)this.properties.get(str)).get());
      } else if ("maxNoOfMajorTicks".equals(str)) {
        localLinearScale.setMaxNoOfMajorTicks(((IntegerProperty)this.properties.get(str)).get());
      } else if ("maxNoOfMinorTicks".equals(str)) {
        localLinearScale.setMaxNoOfMinorTicks(((IntegerProperty)this.properties.get(str)).get());
      } else if ("tightScale".equals(str)) {
        localLinearScale.setTightScale(((BooleanProperty)this.properties.get(str)).get());
      } else if ("largeNumberScale".equals(str)) {
        localLinearScale.setLargeNumberScale(((BooleanProperty)this.properties.get(str)).get());
      }
    }
    return localLinearScale;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/LinearScaleBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */