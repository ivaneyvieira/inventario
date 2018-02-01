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

public class GaugeBuilder<B extends GaugeBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<Gauge>
{
  private HashMap<String, Property> gaugeProperties = new HashMap();
  private HashMap<String, Property> styleProperties = new HashMap();
  
  public static final GaugeBuilder<?> create()
  {
    return new GaugeBuilder();
  }
  
  public final Gauge build()
  {
    GaugeType localGaugeType = GaugeType.RADIAL;
    double d1 = -1.0D;
    double d2 = -1.0D;
    double d3 = -1.0D;
    double d4 = -1.0D;
    Gauge.RadialRange localRadialRange = Gauge.RadialRange.RADIAL_300;
    GaugeModel localGaugeModel;
    if (this.gaugeProperties.containsKey("gaugeModel")) {
      localGaugeModel = (GaugeModel)((ObjectProperty)this.gaugeProperties.get("gaugeModel")).get();
    } else {
      localGaugeModel = new GaugeModel();
    }
    StyleModel localStyleModel;
    if (this.styleProperties.containsKey("styleModel")) {
      localStyleModel = (StyleModel)((ObjectProperty)this.styleProperties.get("styleModel")).get();
    } else {
      localStyleModel = new StyleModel();
    }
    Iterator localIterator = this.gaugeProperties.keySet().iterator();
    String str;
    while (localIterator.hasNext())
    {
      str = (String)localIterator.next();
      if ("gaugeType".equals(str)) {
        localGaugeType = (GaugeType)((ObjectProperty)this.gaugeProperties.get(str)).get();
      } else if ("value".equals(str)) {
        localGaugeModel.setValue(((DoubleProperty)this.gaugeProperties.get(str)).get());
      } else if ("valueAnimationEnabled".equals(str)) {
        localGaugeModel.setValueAnimationEnabled(((BooleanProperty)this.gaugeProperties.get(str)).get());
      } else if ("animationDuration".equals(str)) {
        localGaugeModel.setAnimationDuration(((DoubleProperty)this.gaugeProperties.get(str)).get());
      } else if ("redrawTolerance".equals(str)) {
        localGaugeModel.setRedrawTolerance(((DoubleProperty)this.gaugeProperties.get(str)).get());
      } else if ("minValue".equals(str)) {
        localGaugeModel.setMinValue(((DoubleProperty)this.gaugeProperties.get(str)).get());
      } else if ("maxValue".equals(str)) {
        localGaugeModel.setMaxValue(((DoubleProperty)this.gaugeProperties.get(str)).get());
      } else if ("threshold".equals(str)) {
        localGaugeModel.setThreshold(((DoubleProperty)this.gaugeProperties.get(str)).get());
      } else if ("thresholdBehaviorInverted".equals(str)) {
        localGaugeModel.setThresholdBehaviorInverted(((BooleanProperty)this.gaugeProperties.get(str)).get());
      } else if ("radialRange".equals(str)) {
        localRadialRange = (Gauge.RadialRange)((ObjectProperty)this.gaugeProperties.get(str)).get();
      } else if ("title".equals(str)) {
        localGaugeModel.setTitle((String)((StringProperty)this.gaugeProperties.get(str)).get());
      } else if ("unit".equals(str)) {
        localGaugeModel.setUnit((String)((StringProperty)this.gaugeProperties.get(str)).get());
      } else if ("lcdValueCoupled".equals(str)) {
        localGaugeModel.setLcdValueCoupled(((BooleanProperty)this.gaugeProperties.get(str)).get());
      } else if ("lcdThreshold".equals(str)) {
        localGaugeModel.setLcdThreshold(((DoubleProperty)this.gaugeProperties.get(str)).get());
      } else if ("lcdThresholdBehaviorInverted".equals(str)) {
        localGaugeModel.setLcdThresholdBehaviorInverted(((BooleanProperty)this.gaugeProperties.get(str)).get());
      } else if ("lcdUnitString".equals(str)) {
        localGaugeModel.setLcdUnit((String)((StringProperty)this.gaugeProperties.get(str)).get());
      } else if ("lcdNumberSystem".equals(str)) {
        localGaugeModel.setLcdNumberSystem((Gauge.NumberSystem)((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("maxNoOfMajorTicks".equals(str)) {
        localGaugeModel.setMaxNoOfMajorTicks(((IntegerProperty)this.gaugeProperties.get(str)).get());
      } else if ("maxNoOfMinorTicks".equals(str)) {
        localGaugeModel.setMaxNoOfMinorTicks(((IntegerProperty)this.gaugeProperties.get(str)).get());
      } else if ("majorTickSpacing".equals(str)) {
        localGaugeModel.setMajorTickSpacing(((DoubleProperty)this.gaugeProperties.get(str)).get());
      } else if ("minorTickSpacing".equals(str)) {
        localGaugeModel.setMinorTickSpacing(((DoubleProperty)this.gaugeProperties.get(str)).get());
      } else if ("trend".equals(str)) {
        localGaugeModel.setTrend((Gauge.Trend)((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("niceScaling".equals(str)) {
        localGaugeModel.setNiceScaling(((BooleanProperty)this.gaugeProperties.get(str)).get());
      } else if ("tightScale".equals(str)) {
        localGaugeModel.setTightScale(((BooleanProperty)this.gaugeProperties.get(str)).get());
      } else if ("largeNumberScale".equals(str)) {
        localGaugeModel.setLargeNumberScale(((BooleanProperty)this.gaugeProperties.get(str)).get());
      } else if ("lastLabelVisible".equals(str)) {
        localGaugeModel.setLastLabelVisible(((BooleanProperty)this.gaugeProperties.get(str)).get());
      } else if ("sectionsArray".equals(str)) {
        localGaugeModel.setSections((Section[])((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("sectionsList".equals(str)) {
        localGaugeModel.setSections((List)((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("areasArray".equals(str)) {
        localGaugeModel.setAreas((Section[])((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("areasList".equals(str)) {
        localGaugeModel.setAreas((List)((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("tickMarkSectionsArray".equals(str)) {
        localGaugeModel.setTickMarkSections((Section[])((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("tickMarkSectionsList".equals(str)) {
        localGaugeModel.setTickMarkSections((List)((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("markersArray".equals(str)) {
        localGaugeModel.setMarkers((Marker[])((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("markersList".equals(str)) {
        localGaugeModel.setMarkers((List)((ObjectProperty)this.gaugeProperties.get(str)).get());
      } else if ("endlessMode".equals(str)) {
        localGaugeModel.setEndlessMode(((BooleanProperty)this.gaugeProperties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        d1 = ((DoubleProperty)this.gaugeProperties.get(str)).get();
      } else if ("prefHeight".equals(str)) {
        d2 = ((DoubleProperty)this.gaugeProperties.get(str)).get();
      } else if ("layoutX".equals(str)) {
        d3 = ((DoubleProperty)this.gaugeProperties.get(str)).get();
      } else if ("layoutY".equals(str)) {
        d4 = ((DoubleProperty)this.gaugeProperties.get(str)).get();
      }
    }
    localIterator = this.styleProperties.keySet().iterator();
    while (localIterator.hasNext())
    {
      str = (String)localIterator.next();
      if ("bargraph".equals(str)) {
        localStyleModel.setBargraph(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("minMeasuredValueVisible".equals(str)) {
        localStyleModel.setMinMeasuredValueVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("maxMeasuredValueVisible".equals(str)) {
        localStyleModel.setMaxMeasuredValueVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("thresholdVisible".equals(str)) {
        localStyleModel.setThresholdVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("thresholdColor".equals(str)) {
        localStyleModel.setThresholdColor((Gauge.ThresholdColor)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("frameDesign".equals(str)) {
        localStyleModel.setFrameDesign((Gauge.FrameDesign)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("frameBaseColor".equals(str)) {
        localStyleModel.setFrameBaseColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("frameVisible".equals(str)) {
        localStyleModel.setFrameVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("backgroundDesign".equals(str)) {
        localStyleModel.setBackgroundDesign((Gauge.BackgroundDesign)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("backgroundVisible".equals(str)) {
        localStyleModel.setBackgroundVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("knobDesign".equals(str)) {
        localStyleModel.setKnobDesign((Gauge.KnobDesign)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("knobColor".equals(str)) {
        localStyleModel.setKnobColor((Gauge.KnobColor)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("knobsVisible".equals(str))
      {
        if (localGaugeModel.isEndlessMode()) {
          localStyleModel.setKnobsVisible(false);
        } else {
          localStyleModel.setKnobsVisible(((BooleanProperty)this.styleProperties.get(str)).get());
        }
      }
      else if ("pointerType".equals(str)) {
        localStyleModel.setPointerType((Gauge.PointerType)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("valueColor".equals(str)) {
        localStyleModel.setValueColor((ColorDef)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("pointerGlowEnabled".equals(str)) {
        localStyleModel.setPointerGlowEnabled(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("pointerShadowEnabled".equals(str)) {
        localStyleModel.setPointerShadowEnabled(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("ledVisible".equals(str)) {
        localStyleModel.setLedVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("ledColor".equals(str)) {
        localStyleModel.setLedColor((LedColor)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("userLedVisible".equals(str)) {
        localStyleModel.setUserLedVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("userLedColor".equals(str)) {
        localStyleModel.setUserLedColor((LedColor)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("userLedOn".equals(str)) {
        localStyleModel.setUserLedOn(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("userLedBlinking".equals(str)) {
        localStyleModel.setUserLedBlinking(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("titleFont".equals(str)) {
        localStyleModel.setTitleFont((String)((StringProperty)this.styleProperties.get(str)).get());
      } else if ("unitFont".equals(str)) {
        localStyleModel.setUnitFont((String)((StringProperty)this.styleProperties.get(str)).get());
      } else if ("foregroundType".equals(str)) {
        localStyleModel.setForegroundType((Radial.ForegroundType)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("foregroundVisible".equals(str)) {
        localStyleModel.setForegroundVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("lcdThresholdVisible".equals(str)) {
        localStyleModel.setLcdThresholdVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("lcdDesign".equals(str)) {
        localStyleModel.setLcdDesign((LcdDesign)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("lcdVisible".equals(str)) {
        localStyleModel.setLcdVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("lcdUnitStringVisible".equals(str)) {
        localStyleModel.setLcdUnitVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("lcdNumberSystemVisible".equals(str)) {
        localStyleModel.setLcdNumberSystemVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("lcdUnitFont".equals(str)) {
        localStyleModel.setLcdUnitFont((String)((StringProperty)this.styleProperties.get(str)).get());
      } else if ("lcdTitleFont".equals(str)) {
        localStyleModel.setLcdTitleFont((String)((StringProperty)this.styleProperties.get(str)).get());
      } else if ("lcdValueFont".equals(str)) {
        localStyleModel.setLcdValueFont((Gauge.LcdFont)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("lcdDecimals".equals(str)) {
        localStyleModel.setLcdDecimals(((IntegerProperty)this.styleProperties.get(str)).get());
      } else if ("lcdBlinking".equals(str)) {
        localStyleModel.setLcdBlinking(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("lcdBackgroundVisible".equals(str)) {
        localStyleModel.setLcdBackgroundVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("glowVisible".equals(str)) {
        localStyleModel.setGlowVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("glowOn".equals(str)) {
        localStyleModel.setGlowOn(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("tickmarksVisible".equals(str)) {
        localStyleModel.setTickmarksVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("majorTickmarksVisible".equals(str)) {
        localStyleModel.setMajorTicksVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("majorTickmarkType".equals(str)) {
        localStyleModel.setMajorTickmarkType((Gauge.TickmarkType)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("majorTickmarkColor".equals(str)) {
        localStyleModel.setMajorTickmarkColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("majorTickmarkColorEnabled".equals(str)) {
        localStyleModel.setMajorTickmarkColorEnabled(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("minorTickmarksVisible".equals(str)) {
        localStyleModel.setMinorTicksVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("minorTickmarkColor".equals(str)) {
        localStyleModel.setMinorTickmarkColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("minorTickmarkColorEnabled".equals(str)) {
        localStyleModel.setMinorTickmarkColorEnabled(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("tickLabelsVisible".equals(str)) {
        localStyleModel.setTickLabelsVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("tickLabelOrientation".equals(str)) {
        localStyleModel.setTickLabelOrientation((Gauge.TicklabelOrientation)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("tickmarkGlowEnabled".equals(str)) {
        localStyleModel.setTickmarkGlowEnabled(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("tickmarkGlowColor".equals(str)) {
        localStyleModel.setTickmarkGlowColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("sectionsVisible".equals(str)) {
        localStyleModel.setSectionsVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("sectionsHighlighting".equals(str)) {
        localStyleModel.setSectionsHighlighting(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("showSectionTickmarksOnly".equals(str)) {
        localStyleModel.setShowSectionTickmarksOnly(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("areasVisible".equals(str)) {
        localStyleModel.setAreasVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("areasHighlighting".equals(str)) {
        localStyleModel.setAreasHighlighting(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("markersVisible".equals(str)) {
        localStyleModel.setMarkersVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("textureColor".equals(str)) {
        localStyleModel.setTextureColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("simpleGradientBaseColor".equals(str)) {
        localStyleModel.setSimpleGradientBaseColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("titleVisible".equals(str)) {
        localStyleModel.setTitleVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("unitVisible".equals(str)) {
        localStyleModel.setUnitVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("trendVisible".equals(str)) {
        localStyleModel.setTrendVisible(((BooleanProperty)this.styleProperties.get(str)).get());
      } else if ("trendUpColor".equals(str)) {
        localStyleModel.setTrendUpColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("trendRisingColor".equals(str)) {
        localStyleModel.setTrendRisingColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("trendSteadyColor".equals(str)) {
        localStyleModel.setTrendSteadyColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("trendFallingColor".equals(str)) {
        localStyleModel.setTrendFallingColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      } else if ("trendDownColor".equals(str)) {
        localStyleModel.setTrendDownColor((Color)((ObjectProperty)this.styleProperties.get(str)).get());
      }
    }
    double d5 = d1 == -1.0D ? 200.0D : d1;
    double d6 = d2 == -1.0D ? 200.0D : d2;
    double d7 = d5 <= d6 ? d5 : d6;
    switch (localGaugeType)
    {
    case LCD: 
      Lcd localLcd = new Lcd(localGaugeModel, localStyleModel);
      if (d1 != -1.0D) {
        localLcd.setPrefWidth(d1);
      }
      if (d2 != -1.0D) {
        localLcd.setPrefHeight(d2);
      }
      super.applyTo(localLcd);
      return localLcd;
    case LINEAR: 
      Linear localLinear = new Linear(localGaugeModel, localStyleModel);
      if (d1 != -1.0D) {
        localLinear.setPrefWidth(d1);
      }
      if (d2 != -1.0D) {
        localLinear.setPrefHeight(d2);
      }
      if (d3 != -1.0D) {
        localLinear.setLayoutX(d3);
      }
      if (d4 != -1.0D) {
        localLinear.setLayoutY(d4);
      }
      super.applyTo(localLinear);
      return localLinear;
    case RADIAL_HALF_N: 
      RadialHalfN localRadialHalfN = new RadialHalfN(localGaugeModel, localStyleModel);
      localRadialHalfN.setPrefSize(d7, d7);
      if (d3 != -1.0D) {
        localRadialHalfN.setLayoutX(d3);
      }
      if (d4 != -1.0D) {
        localRadialHalfN.setLayoutY(d4);
      }
      super.applyTo(localRadialHalfN);
      return localRadialHalfN;
    case RADIAL_HALF_S: 
      RadialHalfS localRadialHalfS = new RadialHalfS(localGaugeModel, localStyleModel);
      localRadialHalfS.setPrefSize(d7, d7);
      if (d3 != -1.0D) {
        localRadialHalfS.setLayoutX(d3);
      }
      if (d4 != -1.0D) {
        localRadialHalfS.setLayoutY(d4);
      }
      super.applyTo(localRadialHalfS);
      return localRadialHalfS;
    case RADIAL_QUARTER_N: 
      RadialQuarterN localRadialQuarterN = new RadialQuarterN(localGaugeModel, localStyleModel);
      localRadialQuarterN.setPrefSize(d7, d7);
      if (d3 != -1.0D) {
        localRadialQuarterN.setLayoutX(d3);
      }
      if (d4 != -1.0D) {
        localRadialQuarterN.setLayoutY(d4);
      }
      super.applyTo(localRadialQuarterN);
      return localRadialQuarterN;
    case RADIAL_QUARTER_E: 
      RadialQuarterE localRadialQuarterE = new RadialQuarterE(localGaugeModel, localStyleModel);
      localRadialQuarterE.setPrefSize(d7, d7);
      if (d3 != -1.0D) {
        localRadialQuarterE.setLayoutX(d3);
      }
      if (d4 != -1.0D) {
        localRadialQuarterE.setLayoutY(d4);
      }
      super.applyTo(localRadialQuarterE);
      return localRadialQuarterE;
    case RADIAL_QUARTER_S: 
      RadialQuarterS localRadialQuarterS = new RadialQuarterS(localGaugeModel, localStyleModel);
      localRadialQuarterS.setPrefSize(d7, d7);
      if (d3 != -1.0D) {
        localRadialQuarterS.setLayoutX(d3);
      }
      if (d4 != -1.0D) {
        localRadialQuarterS.setLayoutY(d4);
      }
      super.applyTo(localRadialQuarterS);
      return localRadialQuarterS;
    case RADIAL_QUARTER_W: 
      RadialQuarterW localRadialQuarterW = new RadialQuarterW(localGaugeModel, localStyleModel);
      localRadialQuarterW.setPrefSize(d7, d7);
      if (d3 != -1.0D) {
        localRadialQuarterW.setLayoutX(d3);
      }
      if (d4 != -1.0D) {
        localRadialQuarterW.setLayoutY(d4);
      }
      super.applyTo(localRadialQuarterW);
      return localRadialQuarterW;
    case SIMPLE_RADIAL_GAUGE: 
      SimpleRadialGauge localSimpleRadialGauge = new SimpleRadialGauge(localGaugeModel);
      localSimpleRadialGauge.setPrefSize(d7, d7);
      localSimpleRadialGauge.setRadialRange(Gauge.RadialRange.RADIAL_300);
      if (d3 != -1.0D) {
        localSimpleRadialGauge.setLayoutX(d3);
      }
      if (d4 != -1.0D) {
        localSimpleRadialGauge.setLayoutY(d4);
      }
      super.applyTo(localSimpleRadialGauge);
      return localSimpleRadialGauge;
    case SIMPLE_LINEAR_GAUGE: 
      SimpleLinearGauge localSimpleLinearGauge = new SimpleLinearGauge();
      if (d1 != -1.0D) {
        localSimpleLinearGauge.setPrefWidth(d1);
      }
      if (d2 != -1.0D) {
        localSimpleLinearGauge.setPrefHeight(d2);
      }
      if (d3 != -1.0D) {
        localSimpleLinearGauge.setLayoutX(d3);
      }
      if (d4 != -1.0D) {
        localSimpleLinearGauge.setLayoutY(d4);
      }
      super.applyTo(localSimpleLinearGauge);
      return localSimpleLinearGauge;
    }
    Radial localRadial = new Radial(localGaugeModel, localStyleModel);
    if ((localRadialRange == Gauge.RadialRange.RADIAL_90) || (localRadialRange == Gauge.RadialRange.RADIAL_180) || (localRadialRange == Gauge.RadialRange.RADIAL_270) || (localRadialRange == Gauge.RadialRange.RADIAL_300) || (localRadialRange == Gauge.RadialRange.RADIAL_360)) {
      localRadial.setRadialRange(localRadialRange);
    }
    localRadial.setPrefSize(d7, d7);
    if (d3 != -1.0D) {
      localRadial.setLayoutX(d3);
    }
    if (d4 != -1.0D) {
      localRadial.setLayoutY(d4);
    }
    super.applyTo(localRadial);
    return localRadial;
  }
  
  public final B gaugeModel(GaugeModel paramGaugeModel)
  {
    this.gaugeProperties.put("gaugeModel", new SimpleObjectProperty(paramGaugeModel));
    return this;
  }
  
  public final B gaugeType(GaugeType paramGaugeType)
  {
    this.gaugeProperties.put("gaugeType", new SimpleObjectProperty(paramGaugeType));
    return this;
  }
  
  public final B value(double paramDouble)
  {
    this.gaugeProperties.put("value", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeBuilder valueAnimationEnabled(boolean paramBoolean)
  {
    this.gaugeProperties.put("valueAnimationEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder animationDuration(double paramDouble)
  {
    this.gaugeProperties.put("animationDuration", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeBuilder redrawTolerance(double paramDouble)
  {
    this.gaugeProperties.put("redrawTolerance", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeBuilder minValue(double paramDouble)
  {
    this.gaugeProperties.put("minValue", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeBuilder maxValue(double paramDouble)
  {
    this.gaugeProperties.put("maxValue", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeBuilder threshold(double paramDouble)
  {
    this.gaugeProperties.put("threshold", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeBuilder thresholdBehaviorInverted(boolean paramBoolean)
  {
    this.gaugeProperties.put("thresholdBehaviorInverted", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder radialRange(Gauge.RadialRange paramRadialRange)
  {
    this.gaugeProperties.put("radialRange", new SimpleObjectProperty(paramRadialRange));
    return this;
  }
  
  public final GaugeBuilder title(String paramString)
  {
    this.gaugeProperties.put("title", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeBuilder unit(String paramString)
  {
    this.gaugeProperties.put("unit", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeBuilder lcdValueCoupled(boolean paramBoolean)
  {
    this.gaugeProperties.put("lcdValueCoupled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder lcdThreshold(double paramDouble)
  {
    this.gaugeProperties.put("lcdThreshold", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeBuilder lcdThresholdBehaviorInverted(boolean paramBoolean)
  {
    this.gaugeProperties.put("lcdThresholdBehaviorInverted", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder lcdUnitString(String paramString)
  {
    this.gaugeProperties.put("lcdUnitString", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeBuilder lcdNumberSystem(Gauge.NumberSystem paramNumberSystem)
  {
    this.gaugeProperties.put("lcdNumberSystem", new SimpleObjectProperty(paramNumberSystem));
    return this;
  }
  
  public final GaugeBuilder maxNoOfMajorTicks(int paramInt)
  {
    this.gaugeProperties.put("maxNoOfMajorTicks", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final GaugeBuilder maxNoOfMinorTicks(int paramInt)
  {
    this.gaugeProperties.put("maxNoOfMinorTicks", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final GaugeBuilder majorTickSpacing(double paramDouble)
  {
    this.gaugeProperties.put("majorTickSpacing", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeBuilder minorTickSpacing(double paramDouble)
  {
    this.gaugeProperties.put("minorTickSpacing", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final GaugeBuilder trend(Gauge.Trend paramTrend)
  {
    this.gaugeProperties.put("trend", new SimpleObjectProperty(paramTrend));
    return this;
  }
  
  public final GaugeBuilder niceScaling(boolean paramBoolean)
  {
    this.gaugeProperties.put("niceScaling", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder tightScale(boolean paramBoolean)
  {
    this.gaugeProperties.put("tightScale", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder largeNumberScale(boolean paramBoolean)
  {
    this.gaugeProperties.put("largeNumberScale", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder lastLabelVisible(boolean paramBoolean)
  {
    this.gaugeProperties.put("lastLabelVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder sections(Section... paramVarArgs)
  {
    this.gaugeProperties.put("sectionsArray", new SimpleObjectProperty(paramVarArgs));
    return this;
  }
  
  public final GaugeBuilder sections(List<Section> paramList)
  {
    this.gaugeProperties.put("sectionsList", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final GaugeBuilder areas(Section... paramVarArgs)
  {
    this.gaugeProperties.put("areasArray", new SimpleObjectProperty(paramVarArgs));
    return this;
  }
  
  public final GaugeBuilder areas(List<Section> paramList)
  {
    this.gaugeProperties.put("areasList", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final GaugeBuilder markers(Marker... paramVarArgs)
  {
    this.gaugeProperties.put("markersArray", new SimpleObjectProperty(paramVarArgs));
    return this;
  }
  
  public final GaugeBuilder markers(List<Marker> paramList)
  {
    this.gaugeProperties.put("markersList", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final GaugeBuilder endlessMode(boolean paramBoolean)
  {
    this.gaugeProperties.put("endlessMode", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final B prefWidth(double paramDouble)
  {
    this.gaugeProperties.put("prefWidth", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B prefHeight(double paramDouble)
  {
    this.gaugeProperties.put("prefHeight", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutX(double paramDouble)
  {
    this.gaugeProperties.put("layoutX", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutY(double paramDouble)
  {
    this.gaugeProperties.put("layoutY", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B styleModel(StyleModel paramStyleModel)
  {
    this.styleProperties.put("styleModel", new SimpleObjectProperty(paramStyleModel));
    return this;
  }
  
  public final GaugeBuilder bargraph(boolean paramBoolean)
  {
    this.styleProperties.put("bargraph", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder minMeasuredValueVisible(boolean paramBoolean)
  {
    this.styleProperties.put("minMeasuredValueVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder maxMeasuredValueVisible(boolean paramBoolean)
  {
    this.styleProperties.put("maxMeasuredValueVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder thresholdVisible(boolean paramBoolean)
  {
    this.styleProperties.put("thresholdVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder thresholdColor(Gauge.ThresholdColor paramThresholdColor)
  {
    this.styleProperties.put("thresholdColor", new SimpleObjectProperty(paramThresholdColor));
    return this;
  }
  
  public final GaugeBuilder frameDesign(Gauge.FrameDesign paramFrameDesign)
  {
    this.styleProperties.put("frameDesign", new SimpleObjectProperty(paramFrameDesign));
    return this;
  }
  
  public final GaugeBuilder frameBaseColor(Color paramColor)
  {
    this.styleProperties.put("frameBaseColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder frameVisible(boolean paramBoolean)
  {
    this.styleProperties.put("frameVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder backgroundDesign(Gauge.BackgroundDesign paramBackgroundDesign)
  {
    this.styleProperties.put("backgroundDesign", new SimpleObjectProperty(paramBackgroundDesign));
    return this;
  }
  
  public final GaugeBuilder backgroundVisible(boolean paramBoolean)
  {
    this.styleProperties.put("backgroundVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder knobDesign(Gauge.KnobDesign paramKnobDesign)
  {
    this.styleProperties.put("knobDesign", new SimpleObjectProperty(paramKnobDesign));
    return this;
  }
  
  public final GaugeBuilder knobColor(Gauge.KnobColor paramKnobColor)
  {
    this.styleProperties.put("knobColor", new SimpleObjectProperty(paramKnobColor));
    return this;
  }
  
  public final GaugeBuilder knobsVisible(boolean paramBoolean)
  {
    this.styleProperties.put("knobsVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder pointerType(Gauge.PointerType paramPointerType)
  {
    this.styleProperties.put("pointerType", new SimpleObjectProperty(paramPointerType));
    return this;
  }
  
  public final GaugeBuilder valueColor(ColorDef paramColorDef)
  {
    this.styleProperties.put("valueColor", new SimpleObjectProperty(paramColorDef));
    return this;
  }
  
  public final GaugeBuilder pointerGlowEnabled(boolean paramBoolean)
  {
    this.styleProperties.put("pointerGlowEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder pointerShadowEnabled(boolean paramBoolean)
  {
    this.styleProperties.put("pointerShadowEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder ledVisible(boolean paramBoolean)
  {
    this.styleProperties.put("ledVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder ledColor(LedColor paramLedColor)
  {
    this.styleProperties.put("ledColor", new SimpleObjectProperty(paramLedColor));
    return this;
  }
  
  public final GaugeBuilder userLedVisible(boolean paramBoolean)
  {
    this.styleProperties.put("userLedVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder userLedColor(LedColor paramLedColor)
  {
    this.styleProperties.put("userLedColor", new SimpleObjectProperty(paramLedColor));
    return this;
  }
  
  public final GaugeBuilder userLedOn(boolean paramBoolean)
  {
    this.styleProperties.put("userLedOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder userLedBlinking(boolean paramBoolean)
  {
    this.styleProperties.put("userLedBlinking", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder titleFont(String paramString)
  {
    this.styleProperties.put("titleFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeBuilder unitfont(String paramString)
  {
    this.styleProperties.put("unitFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeBuilder foregroundType(Radial.ForegroundType paramForegroundType)
  {
    this.styleProperties.put("foregroundType", new SimpleObjectProperty(paramForegroundType));
    return this;
  }
  
  public final GaugeBuilder foregroundVisible(boolean paramBoolean)
  {
    this.styleProperties.put("foregroundVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder lcdThresholdVisible(boolean paramBoolean)
  {
    this.styleProperties.put("lcdThresholdVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder lcdDesign(LcdDesign paramLcdDesign)
  {
    this.styleProperties.put("lcdDesign", new SimpleObjectProperty(paramLcdDesign));
    return this;
  }
  
  public final GaugeBuilder lcdVisible(boolean paramBoolean)
  {
    this.styleProperties.put("lcdVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder lcdUnitStringVisible(boolean paramBoolean)
  {
    this.styleProperties.put("lcdUnitStringVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder lcdNumberSystemVisible(boolean paramBoolean)
  {
    this.styleProperties.put("lcdNumberSystemVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder lcdUnitFont(String paramString)
  {
    this.styleProperties.put("lcdUnitFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeBuilder lcdTitleFont(String paramString)
  {
    this.styleProperties.put("lcdTitleFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final GaugeBuilder lcdValueFont(Gauge.LcdFont paramLcdFont)
  {
    this.styleProperties.put("lcdValueFont", new SimpleObjectProperty(paramLcdFont));
    return this;
  }
  
  public final GaugeBuilder lcdDecimals(int paramInt)
  {
    this.styleProperties.put("lcdDecimals", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final GaugeBuilder lcdBlinking(boolean paramBoolean)
  {
    this.styleProperties.put("lcdBlinking", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder lcdBackgroundVisible(boolean paramBoolean)
  {
    this.styleProperties.put("lcdBackgroundVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder glowVisible(boolean paramBoolean)
  {
    this.styleProperties.put("glowVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder glowOn(boolean paramBoolean)
  {
    this.styleProperties.put("glowOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder pulsatingGlow(boolean paramBoolean)
  {
    this.styleProperties.put("pulsatingGlow", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder glowColor(Color paramColor)
  {
    this.styleProperties.put("glowColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder tickmarksVisible(boolean paramBoolean)
  {
    this.styleProperties.put("tickmarksVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder majorTickmarksVisible(boolean paramBoolean)
  {
    this.styleProperties.put("majorTickmarksVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder majorTickmarkType(Gauge.TickmarkType paramTickmarkType)
  {
    this.styleProperties.put("majorTickmarkType", new SimpleObjectProperty(paramTickmarkType));
    return this;
  }
  
  public final GaugeBuilder majorTickmarkColor(Color paramColor)
  {
    this.styleProperties.put("majorTickmarkColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder majorTickmarkColorEnabled(boolean paramBoolean)
  {
    this.styleProperties.put("majorTickmarkColorEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder minorTickmarksVisible(boolean paramBoolean)
  {
    this.styleProperties.put("minorTickmarksVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder minorTickmarkColor(Color paramColor)
  {
    this.styleProperties.put("minorTickmarkColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder minorTickmarkColorEnabled(boolean paramBoolean)
  {
    this.styleProperties.put("minorTickmarkColorEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder tickLabelsVisible(boolean paramBoolean)
  {
    this.styleProperties.put("tickLablesVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder tickLabelOrientation(Gauge.TicklabelOrientation paramTicklabelOrientation)
  {
    this.styleProperties.put("tickLabelOrientation", new SimpleObjectProperty(paramTicklabelOrientation));
    return this;
  }
  
  public final GaugeBuilder tickLabelNumberFormat(Gauge.NumberFormat paramNumberFormat)
  {
    this.styleProperties.put("tickLabelNumberFormat", new SimpleObjectProperty(paramNumberFormat));
    return this;
  }
  
  public final GaugeBuilder tickmarkGlowEnabled(boolean paramBoolean)
  {
    this.styleProperties.put("tickmarkGlowEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder tickmarkGlowColor(Color paramColor)
  {
    this.styleProperties.put("tickmarkGlowColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder sectionsVisible(boolean paramBoolean)
  {
    this.styleProperties.put("sectionsVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder sectionsHighlighting(boolean paramBoolean)
  {
    this.styleProperties.put("sectionsHighlighting", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder showSectionTickmarksOnly(boolean paramBoolean)
  {
    this.styleProperties.put("showSectionTickmarksOnly", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder areasVisible(boolean paramBoolean)
  {
    this.styleProperties.put("areasVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder areasHighlighting(boolean paramBoolean)
  {
    this.styleProperties.put("areasHighlighting", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder markersVisible(boolean paramBoolean)
  {
    this.styleProperties.put("markersVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder textureColor(Color paramColor)
  {
    this.styleProperties.put("textureColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder simpleGradientBaseColor(Color paramColor)
  {
    this.styleProperties.put("simpleGradientBaseColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder titleVisible(boolean paramBoolean)
  {
    this.styleProperties.put("titleVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder unitVisible(boolean paramBoolean)
  {
    this.styleProperties.put("unitVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder trendVisible(boolean paramBoolean)
  {
    this.styleProperties.put("trendVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final GaugeBuilder trendUpColor(Color paramColor)
  {
    this.styleProperties.put("trendUpColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder trendRisingColor(Color paramColor)
  {
    this.styleProperties.put("trendRisingColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder trendSteadyColor(Color paramColor)
  {
    this.styleProperties.put("trendSteadyColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder trendFallingColor(Color paramColor)
  {
    this.styleProperties.put("trendFallingColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final GaugeBuilder trendDownColor(Color paramColor)
  {
    this.styleProperties.put("trendDownColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public static enum GaugeType
  {
    LCD,  LINEAR,  RADIAL_HALF_N,  RADIAL_HALF_S,  RADIAL_QUARTER_N,  RADIAL_QUARTER_E,  RADIAL_QUARTER_S,  RADIAL_QUARTER_W,  RADIAL,  SIMPLE_RADIAL_GAUGE,  SIMPLE_LINEAR_GAUGE;
    
    private GaugeType() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/GaugeBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */