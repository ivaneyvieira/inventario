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

public class GaugeModelBuilder
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final GaugeModelBuilder create()
  {
    return new GaugeModelBuilder();
  }
  
  public final GaugeModelBuilder value(double paramDouble)
  {
    this.properties.put("value", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeModelBuilder valueAnimationEnabled(boolean paramBoolean)
  {
    this.properties.put("valueAnimationEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeModelBuilder animationDuration(double paramDouble)
  {
    this.properties.put("animationDuration", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeModelBuilder redrawTolerance(double paramDouble)
  {
    this.properties.put("redrawTolerance", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeModelBuilder minValue(double paramDouble)
  {
    this.properties.put("minValue", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeModelBuilder maxValue(double paramDouble)
  {
    this.properties.put("maxValue", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeModelBuilder threshold(double paramDouble)
  {
    this.properties.put("threshold", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeModelBuilder thresholdBehaviorInverted(boolean paramBoolean)
  {
    this.properties.put("thresholdBehaviorInverted", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeModelBuilder title(String paramString)
  {
    this.properties.put("title", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeModelBuilder unit(String paramString)
  {
    this.properties.put("unit", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeModelBuilder lcdValueCoupled(boolean paramBoolean)
  {
    this.properties.put("lcdValueCoupled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeModelBuilder lcdThreshold(double paramDouble)
  {
    this.properties.put("lcdThreshold", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeModelBuilder lcdThresholdBehaviorInverted(boolean paramBoolean)
  {
    this.properties.put("lcdThresholdBehaviorInverted", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeModelBuilder lcdUnitString(String paramString)
  {
    this.properties.put("lcdUnitString", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeModelBuilder lcdNumberSystem(Gauge.NumberSystem paramNumberSystem)
  {
    this.properties.put("lcdNumberSystem", new SimpleObjectProperty(paramNumberSystem));
    return this;
  }
  
  public final GaugeModelBuilder maxNoOfMajorTicks(int paramInt)
  {
    this.properties.put("maxNoOfMajorTicks", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final GaugeModelBuilder maxNoOfMinorTicks(int paramInt)
  {
    this.properties.put("maxNoOfMinorTicks", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final GaugeModelBuilder majorTickSpacing(double paramDouble)
  {
    this.properties.put("majorTickSpacing", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeModelBuilder minorTickSpacing(double paramDouble)
  {
    this.properties.put("minorTickSpacing", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeModelBuilder niceScaling(boolean paramBoolean)
  {
    this.properties.put("niceScaling", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeModelBuilder tightScale(boolean paramBoolean)
  {
    this.properties.put("tightScale", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeModelBuilder largeNumberScale(boolean paramBoolean)
  {
    this.properties.put("largeNumberScale", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeModelBuilder lastLabelVisible(boolean paramBoolean)
  {
    this.properties.put("lastLabelVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeModelBuilder trend(Gauge.Trend paramTrend)
  {
    this.properties.put("trend", new SimpleObjectProperty(paramTrend));
    return this;
  }
  
  public final GaugeModelBuilder sections(Section... paramVarArgs)
  {
    this.properties.put("sectionsArray", new SimpleObjectProperty(paramVarArgs));
    return this;
  }
  
  public final GaugeModelBuilder sections(List<Section> paramList)
  {
    this.properties.put("sectionsList", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final GaugeModelBuilder areas(Section... paramVarArgs)
  {
    this.properties.put("areasArray", new SimpleObjectProperty(paramVarArgs));
    return this;
  }
  
  public final GaugeModelBuilder areas(List<Section> paramList)
  {
    this.properties.put("areasList", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final GaugeModelBuilder tickMarkSections(Section... paramVarArgs)
  {
    this.properties.put("tickMarkSectionsArray", new SimpleObjectProperty(paramVarArgs));
    return this;
  }
  
  public final GaugeModelBuilder tickMarkSections(List<Section> paramList)
  {
    this.properties.put("tickMarkSectionsList", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final GaugeModelBuilder markers(Marker... paramVarArgs)
  {
    this.properties.put("markersArray", new SimpleObjectProperty(paramVarArgs));
    return this;
  }
  
  public final GaugeModelBuilder markers(List<Marker> paramList)
  {
    this.properties.put("markersList", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final GaugeModelBuilder endlessMode(boolean paramBoolean)
  {
    this.properties.put("endlessMode", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeModel build()
  {
    GaugeModel localGaugeModel = new GaugeModel();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("value".equals(str)) {
        localGaugeModel.setValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("valueAnimationEnabled".equals(str)) {
        localGaugeModel.setValueAnimationEnabled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("animationDuration".equals(str)) {
        localGaugeModel.setAnimationDuration(((DoubleProperty)this.properties.get(str)).get());
      } else if ("redrawTolerance".equals(str)) {
        localGaugeModel.setRedrawTolerance(((DoubleProperty)this.properties.get(str)).get());
      } else if ("minValue".equals(str)) {
        localGaugeModel.setMinValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("maxValue".equals(str)) {
        localGaugeModel.setMaxValue(((DoubleProperty)this.properties.get(str)).get());
      } else if ("threshold".equals(str)) {
        localGaugeModel.setThreshold(((DoubleProperty)this.properties.get(str)).get());
      } else if ("thresholdBehaviorInverted".equals(str)) {
        localGaugeModel.setThresholdBehaviorInverted(((BooleanProperty)this.properties.get(str)).get());
      } else if ("title".equals(str)) {
        localGaugeModel.setTitle((String)((StringProperty)this.properties.get(str)).get());
      } else if ("unit".equals(str)) {
        localGaugeModel.setUnit((String)((StringProperty)this.properties.get(str)).get());
      } else if ("lcdValueCoupled".equals(str)) {
        localGaugeModel.setLcdValueCoupled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("lcdThreshold".equals(str)) {
        localGaugeModel.setLcdThreshold(((DoubleProperty)this.properties.get(str)).get());
      } else if ("lcdThresholdBehaviorInverted".equals(str)) {
        localGaugeModel.setLcdThresholdBehaviorInverted(((BooleanProperty)this.properties.get(str)).get());
      } else if ("lcdUnitString".equals(str)) {
        localGaugeModel.setLcdUnit((String)((StringProperty)this.properties.get(str)).get());
      } else if ("lcdNumberSystem".equals(str)) {
        localGaugeModel.setLcdNumberSystem((Gauge.NumberSystem)((ObjectProperty)this.properties.get(str)).get());
      } else if ("maxNoOfMajorTicks".equals(str)) {
        localGaugeModel.setMaxNoOfMajorTicks(((IntegerProperty)this.properties.get(str)).get());
      } else if ("maxNoOfMinorTicks".equals(str)) {
        localGaugeModel.setMaxNoOfMinorTicks(((IntegerProperty)this.properties.get(str)).get());
      } else if ("majorTickSpacing".equals(str)) {
        localGaugeModel.setMajorTickSpacing(((DoubleProperty)this.properties.get(str)).get());
      } else if ("minorTickSpacing".equals(str)) {
        localGaugeModel.setMinorTickSpacing(((DoubleProperty)this.properties.get(str)).get());
      } else if ("trend".equals(str)) {
        localGaugeModel.setTrend((Gauge.Trend)((ObjectProperty)this.properties.get(str)).get());
      } else if ("niceScaling".equals(str)) {
        localGaugeModel.setNiceScaling(((BooleanProperty)this.properties.get(str)).get());
      } else if ("tightScale".equals(str)) {
        localGaugeModel.setTightScale(((BooleanProperty)this.properties.get(str)).get());
      } else if ("largeNumberScale".equals(str)) {
        localGaugeModel.setLargeNumberScale(((BooleanProperty)this.properties.get(str)).get());
      } else if ("lastLabelVisible".equals(str)) {
        localGaugeModel.setLastLabelVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("sectionsArray".equals(str)) {
        localGaugeModel.setSections((Section[])((ObjectProperty)this.properties.get(str)).get());
      } else if ("sectionsList".equals(str)) {
        localGaugeModel.setSections((List)((ObjectProperty)this.properties.get(str)).get());
      } else if ("areasArray".equals(str)) {
        localGaugeModel.setAreas((Section[])((ObjectProperty)this.properties.get(str)).get());
      } else if ("areasList".equals(str)) {
        localGaugeModel.setAreas((List)((ObjectProperty)this.properties.get(str)).get());
      } else if ("tickMarkSectionsArray".equals(str)) {
        localGaugeModel.setTickMarkSections((Section[])((ObjectProperty)this.properties.get(str)).get());
      } else if ("tickMarkSectionsList".equals(str)) {
        localGaugeModel.setTickMarkSections((List)((ObjectProperty)this.properties.get(str)).get());
      } else if ("markersArray".equals(str)) {
        localGaugeModel.setMarkers((Marker[])((ObjectProperty)this.properties.get(str)).get());
      } else if ("markersList".equals(str)) {
        localGaugeModel.setMarkers((List)((ObjectProperty)this.properties.get(str)).get());
      } else if ("endlessMode".equals(str)) {
        localGaugeModel.setEndlessMode(((BooleanProperty)this.properties.get(str)).get());
      }
    }
    return localGaugeModel;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/GaugeModelBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */