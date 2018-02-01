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

public class SimpleGaugeBuilder<B extends SimpleGaugeBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<SimpleGauge>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final SimpleGaugeBuilder create()
  {
    return new SimpleGaugeBuilder();
  }
  
  public final SimpleGaugeBuilder type(GaugeType paramGaugeType)
  {
    this.properties.put("TYPE", new SimpleObjectProperty(paramGaugeType));
    return this;
  }
  
  public final SimpleGaugeBuilder model(GaugeModel paramGaugeModel)
  {
    this.properties.put("MODEL", new SimpleObjectProperty(paramGaugeModel));
    return this;
  }
  
  public final SimpleGaugeBuilder minValue(double paramDouble)
  {
    this.properties.put("MIN_VALUE", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SimpleGaugeBuilder maxValue(double paramDouble)
  {
    this.properties.put("MAX_VALUE", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SimpleGaugeBuilder value(double paramDouble)
  {
    this.properties.put("VALUE", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SimpleGaugeBuilder valueAnimationEnabled(boolean paramBoolean)
  {
    this.properties.put("VALUE_ANIMATION_ENABLED", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SimpleGaugeBuilder barColor(Color paramColor)
  {
    this.properties.put("BAR_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SimpleGaugeBuilder barWidth(double paramDouble)
  {
    this.properties.put("BAR_WIDTH", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SimpleGaugeBuilder valueLabelFontSize(double paramDouble)
  {
    this.properties.put("LABEL_FONT_SIZE", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SimpleGaugeBuilder unitFontSize(double paramDouble)
  {
    this.properties.put("UNIT_FONT_SIZE", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SimpleGaugeBuilder noOfDecimals(int paramInt)
  {
    this.properties.put("NO_OF_DECIMALS", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final SimpleGaugeBuilder sections(Section[] paramArrayOfSection)
  {
    this.properties.put("SECTIONS_ARRAY", new SimpleObjectProperty(paramArrayOfSection));
    return this;
  }
  
  public final SimpleGaugeBuilder sections(List<Section> paramList)
  {
    this.properties.put("SECTIONS_LIST", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final SimpleGaugeBuilder unit(String paramString)
  {
    this.properties.put("UNIT", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final SimpleGaugeBuilder barFrameColor(Color paramColor)
  {
    this.properties.put("BAR_FRAME_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SimpleGaugeBuilder barBackgroundColor(Color paramColor)
  {
    this.properties.put("BAR_BACKGROUND_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SimpleGaugeBuilder valueLabelColor(Color paramColor)
  {
    this.properties.put("VALUE_LABEL_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SimpleGaugeBuilder unitLabelColor(Color paramColor)
  {
    this.properties.put("UNIT_LABEL_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SimpleGaugeBuilder minLabelVisible(boolean paramBoolean)
  {
    this.properties.put("MIN_LABEL_VISIBLE", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SimpleGaugeBuilder maxLabelVisible(boolean paramBoolean)
  {
    this.properties.put("MAX_LABEL_VISIBLE", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SimpleGaugeBuilder minMaxLabelFontSize(double paramDouble)
  {
    this.properties.put("MIN_MAX_LABEL_FONT_SIZE", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SimpleGaugeBuilder minLabelColor(Color paramColor)
  {
    this.properties.put("MIN_LABEL_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SimpleGaugeBuilder maxLabelColor(Color paramColor)
  {
    this.properties.put("MAX_LABEL_COLOR", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SimpleGaugeBuilder roundedBar(boolean paramBoolean)
  {
    this.properties.put("ROUNDED_BAR", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SimpleGaugeBuilder valueLabelVisible(boolean paramBoolean)
  {
    this.properties.put("VALUE_LABEL_VISIBLE", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SimpleGaugeBuilder unitLabelVisible(boolean paramBoolean)
  {
    this.properties.put("UNIT_LABEL_VISIBLE", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SimpleGaugeBuilder timeToValueInMs(double paramDouble)
  {
    this.properties.put("TIME_TO_VALUE_IN_MS", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SimpleGaugeBuilder canvasMode(boolean paramBoolean)
  {
    this.properties.put("CANVAS_MODE", new SimpleBooleanProperty(paramBoolean));
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
  
  public final SimpleGauge build()
  {
    Object localObject;
    if (this.properties.containsKey("TYPE")) {
      switch ((GaugeType)((ObjectProperty)this.properties.get("TYPE")).get())
      {
      case SIMPLE_LINEAR_GAUGE: 
        localObject = new SimpleLinearGauge();
        break;
      case SIMPLE_RADIAL_GAUGE: 
      default: 
        localObject = new SimpleRadialGauge();
        break;
      }
    } else {
      localObject = new SimpleRadialGauge();
    }
    if (this.properties.containsKey("MODEL")) {
      ((SimpleGauge)localObject).setGaugeModel((GaugeModel)((ObjectProperty)this.properties.get("MODEL")).get());
    }
    if ((this.properties.containsKey("PREF_WIDTH")) && (this.properties.containsKey("PREF_HEIGHT"))) {
      ((SimpleGauge)localObject).setPrefSize(((DoubleProperty)this.properties.get("PREF_WIDTH")).get(), ((DoubleProperty)this.properties.get("PREF_HEIGHT")).get());
    }
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("MIN_VALUE".equals(str)) {
        ((SimpleGauge)localObject).setMinValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("MAX_VALUE".equals(str)) {
        ((SimpleGauge)localObject).setMaxValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("VALUE".equals(str)) {
        ((SimpleGauge)localObject).setValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("LAYOUT_X".equals(str)) {
        ((SimpleGauge)localObject).setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("LAYOUT_Y".equals(str)) {
        ((SimpleGauge)localObject).setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      } else if ("VALUE_ANIMATION_ENABLED".equals(str)) {
        ((SimpleGauge)localObject).setValueAnimationEnabled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("BAR_COLOR".equals(str)) {
        ((SimpleGauge)localObject).setValueColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("BAR_WIDTH".equals(str)) {
        ((SimpleGauge)localObject).setBarWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("LABEL_FONT_SIZE".equals(str)) {
        ((SimpleGauge)localObject).setValueLabelFontSize(((DoubleProperty)this.properties.get(str)).get());
      } else if ("NO_OF_DECIMALS".equals(str)) {
        ((SimpleGauge)localObject).setNoOfDecimals(((IntegerProperty)this.properties.get(str)).get());
      } else if ("SECTIONS_ARRAY".equals(str)) {
        ((SimpleGauge)localObject).setSections((Section[])((ObjectProperty)this.properties.get(str)).get());
      } else if ("SECTIONS_LIST".equals(str)) {
        ((SimpleGauge)localObject).setSections((List)((ObjectProperty)this.properties.get(str)).get());
      } else if ("UNIT".equals(str)) {
        ((SimpleGauge)localObject).setUnit((String)((StringProperty)this.properties.get(str)).get());
      } else if ("BAR_FRAME_COLOR".equals(str)) {
        ((SimpleGauge)localObject).setBarFrameColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("BAR_BACKGROUND_COLOR".equals(str)) {
        ((SimpleGauge)localObject).setBarBackgroundColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("VALUE_LABEL_COLOR".equals(str)) {
        ((SimpleGauge)localObject).setValueLabelColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("UNIT_LABEL_COLOR".equals(str)) {
        ((SimpleGauge)localObject).setUnitLabelColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("UNIT_FONT_SIZE".equals(str)) {
        ((SimpleGauge)localObject).setUnitLabelFontSize(((DoubleProperty)this.properties.get(str)).get());
      } else if ("MIN_LABEL_VISIBLE".equals(str)) {
        ((SimpleGauge)localObject).setMinLabelVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("MAX_LABEL_VISIBLE".equals(str)) {
        ((SimpleGauge)localObject).setMaxLabelVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("MIN_MAX_LABEL_FONT_SIZE".equals(str)) {
        ((SimpleGauge)localObject).setMinMaxLabelFontSize(((DoubleProperty)this.properties.get(str)).get());
      } else if ("MIN_LABEL_COLOR".equals(str)) {
        ((SimpleGauge)localObject).setMinLabelColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("MAX_LABEL_COLOR".equals(str)) {
        ((SimpleGauge)localObject).setMaxLabelColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("ROUNDED_BAR".equals(str)) {
        ((SimpleGauge)localObject).setRoundedBar(((BooleanProperty)this.properties.get(str)).get());
      } else if ("VALUE_LABEL_VISIBLE".equals(str)) {
        ((SimpleGauge)localObject).setValueLabelVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("UNIT_LABEL_VISIBLE".equals(str)) {
        ((SimpleGauge)localObject).setUnitLabelVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("TIME_TO_VALUE_IN_MS".equals(str)) {
        ((SimpleGauge)localObject).setTimeToValueInMs(((DoubleProperty)this.properties.get(str)).get());
      } else if ("CANVAS_MODE".equals(str)) {
        ((SimpleGauge)localObject).setCanvasMode(((BooleanProperty)this.properties.get(str)).get());
      }
    }
    return (SimpleGauge)localObject;
  }
  
  public static enum GaugeType
  {
    SIMPLE_RADIAL_GAUGE,  SIMPLE_LINEAR_GAUGE;
    
    private GaugeType() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SimpleGaugeBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */