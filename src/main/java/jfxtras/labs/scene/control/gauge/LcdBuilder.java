package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;

public class LcdBuilder<B extends LcdBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<Lcd>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final LcdBuilder create()
  {
    return new LcdBuilder();
  }
  
  public final LcdBuilder styleModel(StyleModel paramStyleModel)
  {
    this.properties.put("styleModel", new SimpleObjectProperty(paramStyleModel));
    return this;
  }
  
  public final LcdBuilder design(LcdDesign paramLcdDesign)
  {
    this.properties.put("design", new SimpleObjectProperty(paramLcdDesign));
    return this;
  }
  
  public final LcdBuilder value(double paramDouble)
  {
    this.properties.put("value", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final LcdBuilder valueAnimationEnabled(boolean paramBoolean)
  {
    this.properties.put("valueAnimationEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder threshold(double paramDouble)
  {
    this.properties.put("threshold", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final LcdBuilder decimals(int paramInt)
  {
    this.properties.put("decimals", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final LcdBuilder backgroundVisible(boolean paramBoolean)
  {
    this.properties.put("backgroundVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder minMeasuredValueVisible(boolean paramBoolean)
  {
    this.properties.put("minMeasuredValueVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder minMeasuredValueDecimals(int paramInt)
  {
    this.properties.put("minMeasuredValueDecimals", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final LcdBuilder maxMeasuredValueVisible(boolean paramBoolean)
  {
    this.properties.put("maxMeasuredValueVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder maxMeasuredValueDecimals(int paramInt)
  {
    this.properties.put("maxMeasuredValueDecimals", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final LcdBuilder formerValueVisible(boolean paramBoolean)
  {
    this.properties.put("formerValueVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder bargraphVisible(boolean paramBoolean)
  {
    this.properties.put("bargraphVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder title(String paramString)
  {
    this.properties.put("title", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final LcdBuilder titleVisible(boolean paramBoolean)
  {
    this.properties.put("titleVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder unit(String paramString)
  {
    this.properties.put("unit", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final LcdBuilder unitVisible(boolean paramBoolean)
  {
    this.properties.put("unitVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder trendVisible(boolean paramBoolean)
  {
    this.properties.put("trendVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder thresholdVisible(boolean paramBoolean)
  {
    this.properties.put("thresholdVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder thresholdBehaviorInverted(boolean paramBoolean)
  {
    this.properties.put("thresholdBehaviorInverted", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder numberSystem(Gauge.NumberSystem paramNumberSystem)
  {
    this.properties.put("numberSystem", new SimpleObjectProperty(paramNumberSystem));
    return this;
  }
  
  public final LcdBuilder numberSystemVisible(boolean paramBoolean)
  {
    this.properties.put("numberSystemVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder titleFont(String paramString)
  {
    this.properties.put("titleFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final LcdBuilder unitFont(String paramString)
  {
    this.properties.put("unitFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final LcdBuilder valueFont(Gauge.LcdFont paramLcdFont)
  {
    this.properties.put("valueFont", new SimpleObjectProperty(paramLcdFont));
    return this;
  }
  
  public final LcdBuilder clockMode(boolean paramBoolean)
  {
    this.properties.put("clockMode", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final LcdBuilder clockSecondsVisible(boolean paramBoolean)
  {
    this.properties.put("clockSecondsVisible", new SimpleBooleanProperty(paramBoolean));
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
  
  public final Lcd build()
  {
    Lcd localLcd = new Lcd();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("styleModel".equals(str))
      {
        localLcd.setStyleModel((StyleModel)((ObjectProperty)this.properties.get(str)).get());
      }
      else if ("design".equals(str))
      {
        localLcd.setLcdDesign((LcdDesign)((ObjectProperty)this.properties.get(str)).get());
      }
      else if ("value".equals(str))
      {
        localLcd.setValue(((DoubleProperty)this.properties.get(str)).get());
        localLcd.setLcdValue(((DoubleProperty)this.properties.get(str)).get());
      }
      else if ("valueAnimationEnabled".equals(str))
      {
        localLcd.setValueAnimationEnabled(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("threshold".equals(str))
      {
        localLcd.setThreshold(((DoubleProperty)this.properties.get(str)).get());
        localLcd.setLcdThreshold(((DoubleProperty)this.properties.get(str)).get());
      }
      else if ("decimals".equals(str))
      {
        localLcd.setLcdDecimals(((IntegerProperty)this.properties.get(str)).get());
      }
      else if ("backgroundVisible".equals(str))
      {
        localLcd.setLcdBackgroundVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("minMeasuredValueVisible".equals(str))
      {
        localLcd.setLcdMinMeasuredValueVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("minMeasuredValueDecimals".equals(str))
      {
        localLcd.setLcdMinMeasuredValueDecimals(((IntegerProperty)this.properties.get(str)).get());
      }
      else if ("maxMeasuredValueVisible".equals(str))
      {
        localLcd.setLcdMaxMeasuredValueVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("maxMeasuredValueDecimals".equals(str))
      {
        localLcd.setLcdMaxMeasuredValueDecimals(((IntegerProperty)this.properties.get(str)).get());
      }
      else if ("formerValueVisible".equals(str))
      {
        localLcd.setLcdFormerValueVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("bargraphVisible".equals(str))
      {
        localLcd.setBargraphVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("title".equals(str))
      {
        localLcd.setTitle((String)((StringProperty)this.properties.get(str)).get());
      }
      else if ("titleVisible".equals(str))
      {
        localLcd.setTitleVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("unit".equals(str))
      {
        localLcd.setUnit((String)((StringProperty)this.properties.get(str)).get());
        localLcd.setLcdUnit((String)((StringProperty)this.properties.get(str)).get());
      }
      else if ("unitVisible".equals(str))
      {
        localLcd.setUnitVisible(((BooleanProperty)this.properties.get(str)).get());
        localLcd.setLcdUnitVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("trendVisible".equals(str))
      {
        localLcd.setTrendVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("thresholdVisible".equals(str))
      {
        localLcd.setThresholdVisible(((BooleanProperty)this.properties.get(str)).get());
        localLcd.setLcdThresholdVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("thresholdBehaviorInverted".equals(str))
      {
        localLcd.setThresholdBehaviorInverted(((BooleanProperty)this.properties.get(str)).get());
        localLcd.setLcdThresholdBehaviorInverted(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("numberSystem".equals(str))
      {
        localLcd.setLcdNumberSystem((Gauge.NumberSystem)((ObjectProperty)this.properties.get(str)).get());
      }
      else if ("numberSystemVisible".equals(str))
      {
        localLcd.setLcdNumberSystemVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("unitFont".equals(str))
      {
        localLcd.setUnitFont((String)((StringProperty)this.properties.get(str)).get());
        localLcd.setLcdUnitFont((String)((StringProperty)this.properties.get(str)).get());
      }
      else if ("titleFont".equals(str))
      {
        localLcd.setTitleFont((String)((StringProperty)this.properties.get(str)).get());
        localLcd.setLcdTitleFont((String)((StringProperty)this.properties.get(str)).get());
      }
      else if ("valueFont".equals(str))
      {
        localLcd.setLcdValueFont((Gauge.LcdFont)((ObjectProperty)this.properties.get(str)).get());
      }
      else if ("clockMode".equals(str))
      {
        localLcd.setClockMode(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("clockSecondsVisible".equals(str))
      {
        localLcd.setClockSecondsVisible(((BooleanProperty)this.properties.get(str)).get());
      }
      else if ("prefWidth".equals(str))
      {
        localLcd.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      }
      else if ("prefHeight".equals(str))
      {
        localLcd.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      }
      else if ("layoutX".equals(str))
      {
        localLcd.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      }
      else if ("layoutY".equals(str))
      {
        localLcd.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localLcd;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/LcdBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */