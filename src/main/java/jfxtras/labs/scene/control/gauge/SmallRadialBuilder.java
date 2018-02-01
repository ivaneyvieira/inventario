package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class SmallRadialBuilder<B extends SmallRadialBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<SmallRadial>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final SmallRadialBuilder create()
  {
    return new SmallRadialBuilder();
  }
  
  public final SmallRadialBuilder model(GaugeModel paramGaugeModel)
  {
    this.properties.put("GAUGE_MODEL", new SimpleObjectProperty(paramGaugeModel));
    return this;
  }
  
  public final SmallRadialBuilder title(String paramString)
  {
    this.properties.put("TITLE", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final SmallRadialBuilder threshold(double paramDouble)
  {
    this.properties.put("THRESHOLD", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SmallRadialBuilder minValue(double paramDouble)
  {
    this.properties.put("MIN_VALUE", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SmallRadialBuilder maxValue(double paramDouble)
  {
    this.properties.put("MAX_VALUE", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SmallRadialBuilder value(double paramDouble)
  {
    this.properties.put("VALUE", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SmallRadialBuilder valueAnimationEnabled(boolean paramBoolean)
  {
    this.properties.put("VALUE_ANIMATION_ENABLED", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SmallRadialBuilder noOfDecimals(int paramInt)
  {
    this.properties.put("NO_OF_DECIMALS", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final SmallRadialBuilder sections(Section[] paramArrayOfSection)
  {
    this.properties.put("SECTIONS_ARRAY", new SimpleObjectProperty(paramArrayOfSection));
    return this;
  }
  
  public final SmallRadialBuilder sections(List<Section> paramList)
  {
    this.properties.put("SECTIONS_LIST", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final SmallRadialBuilder frameColor(Color paramColor)
  {
    this.properties.put("FRAME_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SmallRadialBuilder backgroundColor(Color paramColor)
  {
    this.properties.put("BACKGROUND_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SmallRadialBuilder tickMarkColor(Color paramColor)
  {
    this.properties.put("TICK_MARK_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SmallRadialBuilder valueLabelColor(Color paramColor)
  {
    this.properties.put("VALUE_LABEL_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SmallRadialBuilder pointerColor(Color paramColor)
  {
    this.properties.put("POINTER_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SmallRadialBuilder centerKnobColor(Color paramColor)
  {
    this.properties.put("CENTER_KNOB_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SmallRadialBuilder thresholdLedColor(Color paramColor)
  {
    this.properties.put("THRESHOLD_LED_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SmallRadialBuilder pointerShadowVisible(boolean paramBoolean)
  {
    this.properties.put("POINTER_SHADOW_VISIBLE", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SmallRadialBuilder valueLabelVisible(boolean paramBoolean)
  {
    this.properties.put("VALUE_LABEL_VISIBLE", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SmallRadialBuilder timeToValueInMs(double paramDouble)
  {
    this.properties.put("TIME_TO_VALUE_IN_MS", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B prefWidth(double paramDouble)
  {
    this.properties.put("PREF_WIDTH", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B prefHeight(double paramDouble)
  {
    this.properties.put("PREF_HEIGHT", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutX(double paramDouble)
  {
    this.properties.put("LAYOUT_X", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutY(double paramDouble)
  {
    this.properties.put("LAYOUT_Y", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SmallRadial build()
  {
    SmallRadial localSmallRadial = new SmallRadial();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("GAUGE_MODEL".equals(str)) {
        localSmallRadial.setGaugeModel((GaugeModel)((ObjectProperty)this.properties.get(str)).get());
      } else if ("TITLE".equals(str)) {
        localSmallRadial.setTitle((String)((StringProperty)this.properties.get(str)).get());
      } else if ("THRESHOLD".equals(str)) {
        localSmallRadial.setThreshold(((DoubleProperty)this.properties.get(str)).get());
      } else if ("MIN_VALUE".equals(str)) {
        localSmallRadial.setMinValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("MAX_VALUE".equals(str)) {
        localSmallRadial.setMaxValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("VALUE".equals(str)) {
        localSmallRadial.setValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("VALUE_ANIMATION_ENABLED".equals(str)) {
        localSmallRadial.setValueAnimationEnabled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("FRAME_COLOR".equals(str)) {
        localSmallRadial.setFrameColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("BACKGROUND_COLOR".equals(str)) {
        localSmallRadial.setBackgroundColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("TICK_MARK_COLOR".equals(str)) {
        localSmallRadial.setTickMarkColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("POINTER_COLOR".equals(str)) {
        localSmallRadial.setPointerColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("CENTER_KNOB_COLOR".equals(str)) {
        localSmallRadial.setCenterKnobColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("THRESHOLD_LED_COLOR".equals(str)) {
        localSmallRadial.setThresholdLedColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("POINTER_SHADOW_VISIBLE".equals(str)) {
        localSmallRadial.setPointerShadowVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("NO_OF_DECIMALS".equals(str)) {
        localSmallRadial.setNoOfDecimals(((IntegerProperty)this.properties.get(str)).get());
      } else if ("SECTIONS_ARRAY".equals(str)) {
        localSmallRadial.setSections((Section[])((ObjectProperty)this.properties.get(str)).get());
      } else if ("SECTIONS_LIST".equals(str)) {
        localSmallRadial.setSections((List)((ObjectProperty)this.properties.get(str)).get());
      } else if ("VALUE_LABEL_COLOR".equals(str)) {
        localSmallRadial.setValueLabelColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("VALUE_LABEL_VISIBLE".equals(str)) {
        localSmallRadial.setValueLabelVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("TIME_TO_VALUE_IN_MS".equals(str)) {
        localSmallRadial.setTimeToValueInMs(((DoubleProperty)this.properties.get(str)).get());
      } else if ("PREF_WIDTH".equals(str)) {
        localSmallRadial.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("PREF_HEIGHT".equals(str)) {
        localSmallRadial.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("LAYOUT_X".equals(str)) {
        localSmallRadial.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("LAYOUT_Y".equals(str)) {
        localSmallRadial.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localSmallRadial;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SmallRadialBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */