package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class StyleModelBuilder
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final StyleModelBuilder create()
  {
    return new StyleModelBuilder();
  }
  
  public final StyleModelBuilder bargraph(boolean paramBoolean)
  {
    this.properties.put("bargraph", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder minMeasuredValueVisible(boolean paramBoolean)
  {
    this.properties.put("minMeasuredValueVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder maxMeasuredValueVisible(boolean paramBoolean)
  {
    this.properties.put("maxMeasuredValueVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder thresholdVisible(boolean paramBoolean)
  {
    this.properties.put("thresholdVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder thresholdColor(Gauge.ThresholdColor paramThresholdColor)
  {
    this.properties.put("thresholdColor", new SimpleObjectProperty(paramThresholdColor));
    return this;
  }
  
  public final StyleModelBuilder frameDesign(Gauge.FrameDesign paramFrameDesign)
  {
    this.properties.put("frameDesign", new SimpleObjectProperty(paramFrameDesign));
    return this;
  }
  
  public final StyleModelBuilder frameBaseColor(Color paramColor)
  {
    this.properties.put("frameBaseColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder frameVisible(boolean paramBoolean)
  {
    this.properties.put("frameVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder backgroundDesign(Gauge.BackgroundDesign paramBackgroundDesign)
  {
    this.properties.put("backgroundDesign", new SimpleObjectProperty(paramBackgroundDesign));
    return this;
  }
  
  public final StyleModelBuilder backgroundVisible(boolean paramBoolean)
  {
    this.properties.put("backgroundVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder knobDesign(Gauge.KnobDesign paramKnobDesign)
  {
    this.properties.put("knobDesign", new SimpleObjectProperty(paramKnobDesign));
    return this;
  }
  
  public final StyleModelBuilder knobColor(Gauge.KnobColor paramKnobColor)
  {
    this.properties.put("knobColor", new SimpleObjectProperty(paramKnobColor));
    return this;
  }
  
  public final StyleModelBuilder knobsVisible(boolean paramBoolean)
  {
    this.properties.put("knobsVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder pointerType(Gauge.PointerType paramPointerType)
  {
    this.properties.put("pointerType", new SimpleObjectProperty(paramPointerType));
    return this;
  }
  
  public final StyleModelBuilder valueColor(ColorDef paramColorDef)
  {
    this.properties.put("valueColor", new SimpleObjectProperty(paramColorDef));
    return this;
  }
  
  public final StyleModelBuilder pointerGlowEnabled(boolean paramBoolean)
  {
    this.properties.put("pointerGlowEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder pointerShadowEnabled(boolean paramBoolean)
  {
    this.properties.put("pointerShadowEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder ledVisible(boolean paramBoolean)
  {
    this.properties.put("ledVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder ledColor(LedColor paramLedColor)
  {
    this.properties.put("ledColor", new SimpleObjectProperty(paramLedColor));
    return this;
  }
  
  public final StyleModelBuilder userLedVisible(boolean paramBoolean)
  {
    this.properties.put("userLedVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder userLedColor(LedColor paramLedColor)
  {
    this.properties.put("userLedColor", new SimpleObjectProperty(paramLedColor));
    return this;
  }
  
  public final StyleModelBuilder userLedOn(boolean paramBoolean)
  {
    this.properties.put("userLedOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder userLedBlinking(boolean paramBoolean)
  {
    this.properties.put("userLedBlinking", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder titleFont(String paramString)
  {
    this.properties.put("titleFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final StyleModelBuilder unitfont(String paramString)
  {
    this.properties.put("unitFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final StyleModelBuilder foregroundType(Radial.ForegroundType paramForegroundType)
  {
    this.properties.put("foregroundType", new SimpleObjectProperty(paramForegroundType));
    return this;
  }
  
  public final StyleModelBuilder foregroundVisible(boolean paramBoolean)
  {
    this.properties.put("foregroundVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder lcdThresholdVisible(boolean paramBoolean)
  {
    this.properties.put("lcdThresholdVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder lcdDesign(LcdDesign paramLcdDesign)
  {
    this.properties.put("lcdDesign", new SimpleObjectProperty(paramLcdDesign));
    return this;
  }
  
  public final StyleModelBuilder lcdVisible(boolean paramBoolean)
  {
    this.properties.put("lcdVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder lcdUnitStringVisible(boolean paramBoolean)
  {
    this.properties.put("lcdUnitStringVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder lcdNumberSystemVisible(boolean paramBoolean)
  {
    this.properties.put("lcdNumberSystemVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder lcdUnitFont(String paramString)
  {
    this.properties.put("lcdUnitFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final StyleModelBuilder lcdTitleFont(String paramString)
  {
    this.properties.put("lcdTitleFont", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final StyleModelBuilder lcdValueFont(Gauge.LcdFont paramLcdFont)
  {
    this.properties.put("lcdValueFont", new SimpleObjectProperty(paramLcdFont));
    return this;
  }
  
  public final StyleModelBuilder lcdDecimals(int paramInt)
  {
    this.properties.put("lcdDecimals", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final StyleModelBuilder lcdBlinking(boolean paramBoolean)
  {
    this.properties.put("lcdBlinking", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder lcdBackgroundVisible(boolean paramBoolean)
  {
    this.properties.put("lcdBackgroundVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder glowVisible(boolean paramBoolean)
  {
    this.properties.put("glowVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder glowOn(boolean paramBoolean)
  {
    this.properties.put("glowOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder pulsatingGlow(boolean paramBoolean)
  {
    this.properties.put("pulsatingGlow", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder glowColor(Color paramColor)
  {
    this.properties.put("glowColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder tickmarksVisible(boolean paramBoolean)
  {
    this.properties.put("tickmarksVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder majorTickmarksVisible(boolean paramBoolean)
  {
    this.properties.put("majorTickmarksVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder majorTickmarkType(Gauge.TickmarkType paramTickmarkType)
  {
    this.properties.put("majorTickmarkType", new SimpleObjectProperty(paramTickmarkType));
    return this;
  }
  
  public final StyleModelBuilder majorTickmarkColor(Color paramColor)
  {
    this.properties.put("majorTickmarkColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder majorTickmarkColorEnabled(boolean paramBoolean)
  {
    this.properties.put("majorTickmarkColorEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder minorTickmarksVisible(boolean paramBoolean)
  {
    this.properties.put("minorTickmarksVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder minorTickmarkColor(Color paramColor)
  {
    this.properties.put("minorTickmarkColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder minorTickmarkColorEnabled(boolean paramBoolean)
  {
    this.properties.put("minorTickmarkColorEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder tickLabelsVisible(boolean paramBoolean)
  {
    this.properties.put("tickLablesVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder tickLabelOrientation(Gauge.TicklabelOrientation paramTicklabelOrientation)
  {
    this.properties.put("tickLabelOrientation", new SimpleObjectProperty(paramTicklabelOrientation));
    return this;
  }
  
  public final StyleModelBuilder tickLabelNumberFormat(Gauge.NumberFormat paramNumberFormat)
  {
    this.properties.put("tickLabelNumberFormat", new SimpleObjectProperty(paramNumberFormat));
    return this;
  }
  
  public final StyleModelBuilder tickmarkGlowEnabled(boolean paramBoolean)
  {
    this.properties.put("tickmarkGlowEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder tickmarkGlowColor(Color paramColor)
  {
    this.properties.put("tickmarkGlowColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder sectionsVisible(boolean paramBoolean)
  {
    this.properties.put("sectionsVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder sectionsHighlighting(boolean paramBoolean)
  {
    this.properties.put("sectionsHighlighting", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder showSectionTickmarksOnly(boolean paramBoolean)
  {
    this.properties.put("showSectionTickmarksOnly", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder areasVisible(boolean paramBoolean)
  {
    this.properties.put("areasVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder areasHighlighting(boolean paramBoolean)
  {
    this.properties.put("areasHighlighting", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder markersVisible(boolean paramBoolean)
  {
    this.properties.put("markersVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder textureColor(Color paramColor)
  {
    this.properties.put("textureColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder simpleGradientBaseColor(Color paramColor)
  {
    this.properties.put("simpleGradientBaseColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder titleVisible(boolean paramBoolean)
  {
    this.properties.put("titleVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder unitVisible(boolean paramBoolean)
  {
    this.properties.put("unitVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder trendVisible(boolean paramBoolean)
  {
    this.properties.put("trendVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final StyleModelBuilder trendUpColor(Color paramColor)
  {
    this.properties.put("trendUpColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder trendRisingColor(Color paramColor)
  {
    this.properties.put("trendRisingColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder trendSteadyColor(Color paramColor)
  {
    this.properties.put("trendSteadyColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder trendFallingColor(Color paramColor)
  {
    this.properties.put("trendFallingColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModelBuilder trendDownColor(Color paramColor)
  {
    this.properties.put("trendDownColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StyleModel build()
  {
    StyleModel localStyleModel = new StyleModel();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("bargraph".equals(str)) {
        localStyleModel.setBargraph(((BooleanProperty)this.properties.get(str)).get());
      } else if ("minMeasuredValueVisible".equals(str)) {
        localStyleModel.setMinMeasuredValueVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("maxMeasuredValueVisible".equals(str)) {
        localStyleModel.setMaxMeasuredValueVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("thresholdVisible".equals(str)) {
        localStyleModel.setThresholdVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("thresholdColor".equals(str)) {
        localStyleModel.setThresholdColor((Gauge.ThresholdColor)((ObjectProperty)this.properties.get(str)).get());
      } else if ("frameDesign".equals(str)) {
        localStyleModel.setFrameDesign((Gauge.FrameDesign)((ObjectProperty)this.properties.get(str)).get());
      } else if ("frameBaseColor".equals(str)) {
        localStyleModel.setFrameBaseColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("frameVisible".equals(str)) {
        localStyleModel.setFrameVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("backgroundDesign".equals(str)) {
        localStyleModel.setBackgroundDesign((Gauge.BackgroundDesign)((ObjectProperty)this.properties.get(str)).get());
      } else if ("backgroundVisible".equals(str)) {
        localStyleModel.setBackgroundVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("knobDesign".equals(str)) {
        localStyleModel.setKnobDesign((Gauge.KnobDesign)((ObjectProperty)this.properties.get(str)).get());
      } else if ("knobColor".equals(str)) {
        localStyleModel.setKnobColor((Gauge.KnobColor)((ObjectProperty)this.properties.get(str)).get());
      } else if ("knobsVisible".equals(str)) {
        localStyleModel.setKnobsVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("pointerType".equals(str)) {
        localStyleModel.setPointerType((Gauge.PointerType)((ObjectProperty)this.properties.get(str)).get());
      } else if ("valueColor".equals(str)) {
        localStyleModel.setValueColor((ColorDef)((ObjectProperty)this.properties.get(str)).get());
      } else if ("pointerGlowEnabled".equals(str)) {
        localStyleModel.setPointerGlowEnabled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("pointerShadowEnabled".equals(str)) {
        localStyleModel.setPointerShadowEnabled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("ledVisible".equals(str)) {
        localStyleModel.setLedVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("ledColor".equals(str)) {
        localStyleModel.setLedColor((LedColor)((ObjectProperty)this.properties.get(str)).get());
      } else if ("userLedVisible".equals(str)) {
        localStyleModel.setUserLedVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("userLedColor".equals(str)) {
        localStyleModel.setUserLedColor((LedColor)((ObjectProperty)this.properties.get(str)).get());
      } else if ("userLedOn".equals(str)) {
        localStyleModel.setUserLedOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("userLedBlinking".equals(str)) {
        localStyleModel.setUserLedBlinking(((BooleanProperty)this.properties.get(str)).get());
      } else if ("titleFont".equals(str)) {
        localStyleModel.setTitleFont((String)((StringProperty)this.properties.get(str)).get());
      } else if ("unitFont".equals(str)) {
        localStyleModel.setUnitFont((String)((StringProperty)this.properties.get(str)).get());
      } else if ("foregroundType".equals(str)) {
        localStyleModel.setForegroundType((Radial.ForegroundType)((ObjectProperty)this.properties.get(str)).get());
      } else if ("foregroundVisible".equals(str)) {
        localStyleModel.setForegroundVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("lcdThresholdVisible".equals(str)) {
        localStyleModel.setLcdThresholdVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("lcdDesign".equals(str)) {
        localStyleModel.setLcdDesign((LcdDesign)((ObjectProperty)this.properties.get(str)).get());
      } else if ("lcdVisible".equals(str)) {
        localStyleModel.setLcdVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("lcdUnitStringVisible".equals(str)) {
        localStyleModel.setLcdUnitVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("lcdNumberSystemVisible".equals(str)) {
        localStyleModel.setLcdNumberSystemVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("lcdUnitFont".equals(str)) {
        localStyleModel.setLcdUnitFont((String)((StringProperty)this.properties.get(str)).get());
      } else if ("lcdTitleFont".equals(str)) {
        localStyleModel.setLcdTitleFont((String)((StringProperty)this.properties.get(str)).get());
      } else if ("lcdValueFont".equals(str)) {
        localStyleModel.setLcdValueFont((Gauge.LcdFont)((ObjectProperty)this.properties.get(str)).get());
      } else if ("lcdDecimals".equals(str)) {
        localStyleModel.setLcdDecimals(((IntegerProperty)this.properties.get(str)).get());
      } else if ("lcdBlinking".equals(str)) {
        localStyleModel.setLcdBlinking(((BooleanProperty)this.properties.get(str)).get());
      } else if ("lcdBackgroundVisible".equals(str)) {
        localStyleModel.setLcdBackgroundVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("glowVisible".equals(str)) {
        localStyleModel.setGlowVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("glowOn".equals(str)) {
        localStyleModel.setGlowOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("tickmarksVisible".equals(str)) {
        localStyleModel.setTickmarksVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("majorTickmarksVisible".equals(str)) {
        localStyleModel.setMajorTicksVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("majorTickmarkType".equals(str)) {
        localStyleModel.setMajorTickmarkType((Gauge.TickmarkType)((ObjectProperty)this.properties.get(str)).get());
      } else if ("majorTickmarkColor".equals(str)) {
        localStyleModel.setMajorTickmarkColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("majorTickmarkColorEnabled".equals(str)) {
        localStyleModel.setMajorTickmarkColorEnabled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("minorTickmarksVisible".equals(str)) {
        localStyleModel.setMinorTicksVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("minorTickmarkColor".equals(str)) {
        localStyleModel.setMinorTickmarkColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("minorTickmarkColorEnabled".equals(str)) {
        localStyleModel.setMinorTickmarkColorEnabled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("tickLabelsVisible".equals(str)) {
        localStyleModel.setTickLabelsVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("tickLabelOrientation".equals(str)) {
        localStyleModel.setTickLabelOrientation((Gauge.TicklabelOrientation)((ObjectProperty)this.properties.get(str)).get());
      } else if ("tickmarkGlowEnabled".equals(str)) {
        localStyleModel.setTickmarkGlowEnabled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("tickmarkGlowColor".equals(str)) {
        localStyleModel.setTickmarkGlowColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("sectionsVisible".equals(str)) {
        localStyleModel.setSectionsVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("sectionsHighlighting".equals(str)) {
        localStyleModel.setSectionsHighlighting(((BooleanProperty)this.properties.get(str)).get());
      } else if ("showSectionTickmarksOnly".equals(str)) {
        localStyleModel.setShowSectionTickmarksOnly(((BooleanProperty)this.properties.get(str)).get());
      } else if ("areasVisible".equals(str)) {
        localStyleModel.setAreasVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("areasHighlighting".equals(str)) {
        localStyleModel.setAreasHighlighting(((BooleanProperty)this.properties.get(str)).get());
      } else if ("markersVisible".equals(str)) {
        localStyleModel.setMarkersVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("textureColor".equals(str)) {
        localStyleModel.setTextureColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("simpleGradientBaseColor".equals(str)) {
        localStyleModel.setSimpleGradientBaseColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("titleVisible".equals(str)) {
        localStyleModel.setTitleVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("unitVisible".equals(str)) {
        localStyleModel.setUnitVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("trendVisible".equals(str)) {
        localStyleModel.setTrendVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("trendUpColor".equals(str)) {
        localStyleModel.setTrendUpColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("trendRisingColor".equals(str)) {
        localStyleModel.setTrendRisingColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("trendSteadyColor".equals(str)) {
        localStyleModel.setTrendSteadyColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("trendFallingColor".equals(str)) {
        localStyleModel.setTrendFallingColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("trendDownColor".equals(str)) {
        localStyleModel.setTrendDownColor((Color)((ObjectProperty)this.properties.get(str)).get());
      }
    }
    return localStyleModel;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/StyleModelBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */