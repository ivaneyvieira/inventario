package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jfxtras.labs.util.Util;

public class StyleModel
{
  private BooleanProperty bargraph = new SimpleBooleanProperty(false);
  private BooleanProperty minMeasuredValueVisible = new SimpleBooleanProperty(false);
  private BooleanProperty maxMeasuredValueVisible = new SimpleBooleanProperty(false);
  private BooleanProperty thresholdVisible = new SimpleBooleanProperty(false);
  private ObjectProperty<Gauge.ThresholdColor> thresholdColor = new SimpleObjectProperty(Gauge.ThresholdColor.RED);
  private ObjectProperty<Gauge.FrameDesign> frameDesign = new SimpleObjectProperty(Gauge.FrameDesign.METAL);
  private ObjectProperty<Color> frameBaseColor = new SimpleObjectProperty(Color.rgb(160, 160, 160));
  private BooleanProperty frameVisible = new SimpleBooleanProperty(true);
  private ObjectProperty<Gauge.BackgroundDesign> backgroundDesign = new SimpleObjectProperty(Gauge.BackgroundDesign.DARK_GRAY);
  private BooleanProperty backgroundVisible = new SimpleBooleanProperty(true);
  private ObjectProperty<Gauge.KnobDesign> knobDesign = new SimpleObjectProperty(Gauge.KnobDesign.STANDARD);
  private ObjectProperty<Gauge.KnobColor> knobColor = new SimpleObjectProperty(Gauge.KnobColor.SILVER);
  private BooleanProperty knobsVisible = new SimpleBooleanProperty(true);
  private ObjectProperty<Gauge.PointerType> pointerType = new SimpleObjectProperty(Gauge.PointerType.TYPE1);
  private BooleanProperty pointerShadowEnabled = new SimpleBooleanProperty(true);
  private BooleanProperty pointerGlowEnabled = new SimpleBooleanProperty(false);
  private ObjectProperty<ColorDef> valueColor = new SimpleObjectProperty(ColorDef.RED);
  private BooleanProperty ledVisible = new SimpleBooleanProperty(true);
  private ObjectProperty<LedColor> ledColor = new SimpleObjectProperty(LedColor.RED);
  private BooleanProperty ledBlinking = new SimpleBooleanProperty(false);
  private BooleanProperty userLedVisible = new SimpleBooleanProperty(false);
  private BooleanProperty userLedOn = new SimpleBooleanProperty(false);
  private ObjectProperty<LedColor> userLedColor = new SimpleObjectProperty(LedColor.BLUE);
  private BooleanProperty userLedBlinking = new SimpleBooleanProperty(false);
  private StringProperty titleFont = new SimpleStringProperty("Verdana");
  private StringProperty unitFont = new SimpleStringProperty("Verdana");
  private ObjectProperty<Radial.ForegroundType> foregroundType = new SimpleObjectProperty(Radial.ForegroundType.TYPE1);
  private BooleanProperty foregroundVisible = new SimpleBooleanProperty(true);
  private BooleanProperty lcdThresholdVisible = new SimpleBooleanProperty(false);
  private ObjectProperty<LcdDesign> lcdDesign = new SimpleObjectProperty(LcdDesign.STANDARD_GREEN);
  private BooleanProperty lcdVisible = new SimpleBooleanProperty(true);
  private BooleanProperty lcdUnitVisible = new SimpleBooleanProperty(false);
  private StringProperty lcdUnitFont = new SimpleStringProperty("Verdana");
  private StringProperty lcdTitleFont = new SimpleStringProperty("Verdana");
  private ObjectProperty<Gauge.LcdFont> lcdValueFont = new SimpleObjectProperty(Gauge.LcdFont.STANDARD);
  private IntegerProperty lcdDecimals = new SimpleIntegerProperty(0);
  private BooleanProperty lcdNumberSystemVisible = new SimpleBooleanProperty(false);
  private BooleanProperty lcdBlinking = new SimpleBooleanProperty(false);
  private BooleanProperty lcdBackgroundVisible = new SimpleBooleanProperty(true);
  private BooleanProperty glowVisible = new SimpleBooleanProperty(false);
  private ObjectProperty<Color> glowColor = new SimpleObjectProperty(Color.rgb(51, 255, 255));
  private BooleanProperty glowOn = new SimpleBooleanProperty(false);
  private BooleanProperty pulsatingGlow = new SimpleBooleanProperty(false);
  private BooleanProperty tickmarksVisible = new SimpleBooleanProperty(true);
  private BooleanProperty majorTicksVisible = new SimpleBooleanProperty(true);
  private ObjectProperty<Gauge.TickmarkType> majorTickmarkType = new SimpleObjectProperty(Gauge.TickmarkType.LINE);
  private ObjectProperty<Color> majorTickmarkColor = new SimpleObjectProperty(Color.WHITE);
  private BooleanProperty majorTickmarkColorEnabled = new SimpleBooleanProperty(false);
  private BooleanProperty minorTicksVisible = new SimpleBooleanProperty(true);
  private ObjectProperty<Color> minorTickmarkColor = new SimpleObjectProperty(Color.WHITE);
  private BooleanProperty minorTickmarkColorEnabled = new SimpleBooleanProperty(false);
  private BooleanProperty tickLabelsVisible = new SimpleBooleanProperty(true);
  private ObjectProperty<Gauge.TicklabelOrientation> tickLabelOrientation = new SimpleObjectProperty(Gauge.TicklabelOrientation.NORMAL);
  private ObjectProperty<Gauge.NumberFormat> tickLabelNumberFormat = new SimpleObjectProperty(Gauge.NumberFormat.AUTO);
  private ObjectProperty<Point2D> tickmarksOffset = new SimpleObjectProperty(new Point2D(0.0D, 0.0D));
  private BooleanProperty tickmarkGlowEnabled = new SimpleBooleanProperty(false);
  private ObjectProperty<Color> tickmarkGlowColor = new SimpleObjectProperty(Color.color(0.5D, 0.7D, 0.9D, 0.8D));
  private BooleanProperty sectionsVisible = new SimpleBooleanProperty(false);
  private BooleanProperty expandedSections = new SimpleBooleanProperty(false);
  private BooleanProperty sectionsHighlighting = new SimpleBooleanProperty(false);
  private BooleanProperty showSectionTickmarksOnly = new SimpleBooleanProperty(false);
  private BooleanProperty areasVisible = new SimpleBooleanProperty(false);
  private BooleanProperty areasHighlighting = new SimpleBooleanProperty(false);
  private BooleanProperty markersVisible = new SimpleBooleanProperty(false);
  private ObjectProperty<Color> textureColor = new SimpleObjectProperty(Color.rgb(35, 35, 35));
  private ObjectProperty<Color> simpleGradientBaseColor = new SimpleObjectProperty(Color.rgb(213, 0, 0));
  private BooleanProperty titleVisible = new SimpleBooleanProperty(true);
  private BooleanProperty unitVisible = new SimpleBooleanProperty(true);
  private BooleanProperty trendVisible = new SimpleBooleanProperty(false);
  private ObjectProperty<Color> trendUpColor = new SimpleObjectProperty(Color.LIME);
  private ObjectProperty<Color> trendRisingColor = new SimpleObjectProperty(Color.YELLOWGREEN);
  private ObjectProperty<Color> trendSteadyColor = new SimpleObjectProperty(Color.YELLOW);
  private ObjectProperty<Color> trendFallingColor = new SimpleObjectProperty(Color.ORANGE);
  private ObjectProperty<Color> trendDownColor = new SimpleObjectProperty(Color.RED);
  private ObjectProperty<EventHandler<StyleModelEvent>> onStyleModelEvent = new SimpleObjectProperty();
  
  public final ObjectProperty<EventHandler<StyleModelEvent>> onStyleModelEventProperty()
  {
    return this.onStyleModelEvent;
  }
  
  public final void setOnStyleModelEvent(EventHandler<StyleModelEvent> paramEventHandler)
  {
    onStyleModelEventProperty().set(paramEventHandler);
  }
  
  public final EventHandler<StyleModelEvent> getOnStyleModelEvent()
  {
    return (EventHandler)onStyleModelEventProperty().get();
  }
  
  public void fireStyleModelEvent()
  {
    EventHandler localEventHandler = getOnStyleModelEvent();
    if (localEventHandler != null)
    {
      StyleModelEvent localStyleModelEvent = new StyleModelEvent();
      localEventHandler.handle(localStyleModelEvent);
    }
  }
  
  public final boolean isBargraph()
  {
    return this.bargraph.get();
  }
  
  public final void setBargraph(boolean paramBoolean)
  {
    this.bargraph.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty bargraphProperty()
  {
    return this.bargraph;
  }
  
  public final boolean isMinMeasuredValueVisible()
  {
    return this.minMeasuredValueVisible.get();
  }
  
  public final void setMinMeasuredValueVisible(boolean paramBoolean)
  {
    this.minMeasuredValueVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty minMeasuredValueVisibleProperty()
  {
    return this.minMeasuredValueVisible;
  }
  
  public final boolean isMaxMeasuredValueVisible()
  {
    return this.maxMeasuredValueVisible.get();
  }
  
  public final void setMaxMeasuredValueVisible(boolean paramBoolean)
  {
    this.maxMeasuredValueVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty maxMeasuredValueVisibleProperty()
  {
    return this.maxMeasuredValueVisible;
  }
  
  public final boolean isThresholdVisible()
  {
    return this.thresholdVisible.get();
  }
  
  public final void setThresholdVisible(boolean paramBoolean)
  {
    this.thresholdVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty thresholdVisibleProperty()
  {
    return this.thresholdVisible;
  }
  
  public final Gauge.ThresholdColor getThresholdColor()
  {
    return (Gauge.ThresholdColor)this.thresholdColor.get();
  }
  
  public final void setThresholdColor(Gauge.ThresholdColor paramThresholdColor)
  {
    this.thresholdColor.set(paramThresholdColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.ThresholdColor> thresholdColorProperty()
  {
    return this.thresholdColor;
  }
  
  public final Gauge.FrameDesign getFrameDesign()
  {
    return (Gauge.FrameDesign)this.frameDesign.get();
  }
  
  public final void setFrameDesign(Gauge.FrameDesign paramFrameDesign)
  {
    this.frameDesign.set(paramFrameDesign);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.FrameDesign> frameDesignProperty()
  {
    return this.frameDesign;
  }
  
  public final Color getFrameBaseColor()
  {
    return (Color)this.frameBaseColor.get();
  }
  
  public final void setFrameBaseColor(Color paramColor)
  {
    this.frameBaseColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> frameBaseColorProperty()
  {
    return this.frameBaseColor;
  }
  
  public final boolean isFrameVisible()
  {
    return this.frameVisible.get();
  }
  
  public final void setFrameVisible(boolean paramBoolean)
  {
    this.frameVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty frameVisibleProperty()
  {
    return this.frameVisible;
  }
  
  public final Gauge.BackgroundDesign getBackgroundDesign()
  {
    return (Gauge.BackgroundDesign)this.backgroundDesign.get();
  }
  
  public final void setBackgroundDesign(Gauge.BackgroundDesign paramBackgroundDesign)
  {
    this.backgroundDesign.set(paramBackgroundDesign);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.BackgroundDesign> backgroundDesignProperty()
  {
    return this.backgroundDesign;
  }
  
  public final boolean isBackgroundVisible()
  {
    return this.backgroundVisible.get();
  }
  
  public final void setBackgroundVisible(boolean paramBoolean)
  {
    this.backgroundVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty backgroundVisibleProperty()
  {
    return this.backgroundVisible;
  }
  
  public final Gauge.KnobDesign getKnobDesign()
  {
    return (Gauge.KnobDesign)this.knobDesign.get();
  }
  
  public final void setKnobDesign(Gauge.KnobDesign paramKnobDesign)
  {
    this.knobDesign.set(paramKnobDesign);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.KnobDesign> knobDesignProperty()
  {
    return this.knobDesign;
  }
  
  public final Gauge.KnobColor getKnobColor()
  {
    return (Gauge.KnobColor)this.knobColor.get();
  }
  
  public final void setKnobColor(Gauge.KnobColor paramKnobColor)
  {
    this.knobColor.set(paramKnobColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.KnobColor> knobColorProperty()
  {
    return this.knobColor;
  }
  
  public final boolean getKnobsVisible()
  {
    return this.knobsVisible.get();
  }
  
  public final void setKnobsVisible(boolean paramBoolean)
  {
    this.knobsVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty knobsVisibleProperty()
  {
    return this.knobsVisible;
  }
  
  public final Gauge.PointerType getPointerType()
  {
    return (Gauge.PointerType)this.pointerType.get();
  }
  
  public final void setPointerType(Gauge.PointerType paramPointerType)
  {
    this.pointerType.set(paramPointerType);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.PointerType> pointerTypeProperty()
  {
    return this.pointerType;
  }
  
  public final ColorDef getValueColor()
  {
    return (ColorDef)this.valueColor.get();
  }
  
  public final void setValueColor(ColorDef paramColorDef)
  {
    this.valueColor.set(paramColorDef);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<ColorDef> valueColorProperty()
  {
    return this.valueColor;
  }
  
  public final boolean isPointerGlowEnabled()
  {
    return this.pointerGlowEnabled.get();
  }
  
  public final void setPointerGlowEnabled(boolean paramBoolean)
  {
    this.pointerGlowEnabled.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty pointerGlowEnabledProperty()
  {
    return this.pointerGlowEnabled;
  }
  
  public final boolean isPointerShadowEnabled()
  {
    return this.pointerShadowEnabled.get();
  }
  
  public final void setPointerShadowEnabled(boolean paramBoolean)
  {
    this.pointerShadowEnabled.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty pointerShadowEnabledProperty()
  {
    return this.pointerShadowEnabled;
  }
  
  public final boolean isLedVisible()
  {
    return this.ledVisible.get();
  }
  
  public final void setLedVisible(boolean paramBoolean)
  {
    this.ledVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty ledVisibleProperty()
  {
    return this.ledVisible;
  }
  
  public final LedColor getLedColor()
  {
    return (LedColor)this.ledColor.get();
  }
  
  public final void setLedColor(LedColor paramLedColor)
  {
    this.ledColor.set(paramLedColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<LedColor> ledColorProperty()
  {
    return this.ledColor;
  }
  
  public final boolean isLedBlinking()
  {
    return this.ledBlinking.get();
  }
  
  public final void setLedBlinking(boolean paramBoolean)
  {
    this.ledBlinking.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty ledBlinkingProperty()
  {
    return this.ledBlinking;
  }
  
  public final boolean isUserLedVisible()
  {
    return this.userLedVisible.get();
  }
  
  public final void setUserLedVisible(boolean paramBoolean)
  {
    this.userLedVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty userLedVisibleProperty()
  {
    return this.userLedVisible;
  }
  
  public final LedColor getUserLedColor()
  {
    return (LedColor)this.userLedColor.get();
  }
  
  public final void setUserLedColor(LedColor paramLedColor)
  {
    this.userLedColor.set(paramLedColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<LedColor> userLedColorProperty()
  {
    return this.userLedColor;
  }
  
  public final boolean isUserLedOn()
  {
    return this.userLedOn.get();
  }
  
  public final void setUserLedOn(boolean paramBoolean)
  {
    this.userLedOn.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty userLedOnProperty()
  {
    return this.userLedOn;
  }
  
  public final boolean isUserLedBlinking()
  {
    return this.userLedBlinking.get();
  }
  
  public final void setUserLedBlinking(boolean paramBoolean)
  {
    this.userLedBlinking.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty userLedBlinkingProperty()
  {
    return this.userLedBlinking;
  }
  
  public final String getTitleFont()
  {
    return (String)this.titleFont.get();
  }
  
  public final void setTitleFont(String paramString)
  {
    this.titleFont.set(paramString);
    fireStyleModelEvent();
  }
  
  public final StringProperty titleFontProperty()
  {
    return this.titleFont;
  }
  
  public final String getUnitFont()
  {
    return (String)this.unitFont.get();
  }
  
  public final void setUnitFont(String paramString)
  {
    this.unitFont.set(paramString);
    fireStyleModelEvent();
  }
  
  public final StringProperty unitFontProperty()
  {
    return this.unitFont;
  }
  
  public final Radial.ForegroundType getForegroundType()
  {
    return (Radial.ForegroundType)this.foregroundType.get();
  }
  
  public final void setForegroundType(Radial.ForegroundType paramForegroundType)
  {
    this.foregroundType.set(paramForegroundType);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Radial.ForegroundType> foregroundTypeProperty()
  {
    return this.foregroundType;
  }
  
  public final boolean isForegroundVisible()
  {
    return this.foregroundVisible.get();
  }
  
  public final void setForegroundVisible(boolean paramBoolean)
  {
    this.foregroundVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty foregroundVisibleProperty()
  {
    return this.foregroundVisible;
  }
  
  public final boolean isLcdThresholdVisible()
  {
    return this.lcdThresholdVisible.get();
  }
  
  public final void setLcdThresholdVisible(boolean paramBoolean)
  {
    this.lcdThresholdVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty lcdThresholdVisibleProperty()
  {
    return this.lcdThresholdVisible;
  }
  
  public final LcdDesign getLcdDesign()
  {
    return (LcdDesign)this.lcdDesign.get();
  }
  
  public final void setLcdDesign(LcdDesign paramLcdDesign)
  {
    this.lcdDesign.set(paramLcdDesign);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty lcdDesignProperty()
  {
    return this.lcdDesign;
  }
  
  public final boolean isLcdVisible()
  {
    return this.lcdVisible.get();
  }
  
  public final void setLcdVisible(boolean paramBoolean)
  {
    this.lcdVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty lcdVisibleProperty()
  {
    return this.lcdVisible;
  }
  
  public final boolean getLcdUnitVisible()
  {
    return this.lcdUnitVisible.get();
  }
  
  public final void setLcdUnitVisible(boolean paramBoolean)
  {
    this.lcdUnitVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty lcdUnitVisibleProperty()
  {
    return this.lcdUnitVisible;
  }
  
  public final String getLcdUnitFont()
  {
    return (String)this.lcdUnitFont.get();
  }
  
  public final void setLcdUnitFont(String paramString)
  {
    this.lcdUnitFont.set(paramString);
    fireStyleModelEvent();
  }
  
  public final StringProperty lcdUnitFontProperty()
  {
    return this.lcdUnitFont;
  }
  
  public final String getLcdTitleFont()
  {
    return (String)this.lcdTitleFont.get();
  }
  
  public final void setLcdTitleFont(String paramString)
  {
    this.lcdTitleFont.set(paramString);
    fireStyleModelEvent();
  }
  
  public final StringProperty lcdTitleFontProperty()
  {
    return this.lcdTitleFont;
  }
  
  public final Gauge.LcdFont getLcdValueFont()
  {
    return (Gauge.LcdFont)this.lcdValueFont.get();
  }
  
  public final void setLcdValueFont(Gauge.LcdFont paramLcdFont)
  {
    this.lcdValueFont.set(paramLcdFont);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.LcdFont> lcdValueFontProperty()
  {
    return this.lcdValueFont;
  }
  
  public final int getLcdDecimals()
  {
    return this.lcdDecimals.get();
  }
  
  public final void setLcdDecimals(int paramInt)
  {
    int i = paramInt < 0 ? 0 : paramInt > 5 ? 5 : paramInt;
    this.lcdDecimals.set(i);
    fireStyleModelEvent();
  }
  
  public final IntegerProperty lcdDecimalsProperty()
  {
    return this.lcdDecimals;
  }
  
  public final boolean isLcdNumberSystemVisible()
  {
    return this.lcdNumberSystemVisible.get();
  }
  
  public final void setLcdNumberSystemVisible(boolean paramBoolean)
  {
    this.lcdNumberSystemVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty lcdNumberSystemVisibleProperty()
  {
    return this.lcdNumberSystemVisible;
  }
  
  public final boolean isLcdBlinking()
  {
    return this.lcdBlinking.get();
  }
  
  public final void setLcdBlinking(boolean paramBoolean)
  {
    this.lcdBlinking.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty lcdBlinkingProperty()
  {
    return this.lcdBlinking;
  }
  
  public final boolean isLcdBackgroundVisible()
  {
    return this.lcdBackgroundVisible.get();
  }
  
  public final void setLcdBackgroundVisible(boolean paramBoolean)
  {
    this.lcdBackgroundVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty lcdBackgroundVisibleProperty()
  {
    return this.lcdBackgroundVisible;
  }
  
  public final boolean isGlowVisible()
  {
    return this.glowVisible.get();
  }
  
  public final void setGlowVisible(boolean paramBoolean)
  {
    this.glowVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty glowVisibleProperty()
  {
    return this.glowVisible;
  }
  
  public final Color getGlowColor()
  {
    return (Color)this.glowColor.get();
  }
  
  public final void setGlowColor(Color paramColor)
  {
    this.glowColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> glowColorProperty()
  {
    return this.glowColor;
  }
  
  public final boolean isGlowOn()
  {
    return this.glowOn.get();
  }
  
  public final void setGlowOn(boolean paramBoolean)
  {
    this.glowOn.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty glowOnProperty()
  {
    return this.glowOn;
  }
  
  public final boolean isPulsatingGlow()
  {
    return this.pulsatingGlow.get();
  }
  
  public final void setPulsatingGlow(boolean paramBoolean)
  {
    this.pulsatingGlow.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty pulsatingGlowProperty()
  {
    return this.pulsatingGlow;
  }
  
  public final boolean isTickmarksVisible()
  {
    return this.tickmarksVisible.get();
  }
  
  public final void setTickmarksVisible(boolean paramBoolean)
  {
    this.tickmarksVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty tickmarksVisibleProperty()
  {
    return this.tickmarksVisible;
  }
  
  public final boolean isMajorTicksVisible()
  {
    return this.majorTicksVisible.get();
  }
  
  public final void setMajorTicksVisible(boolean paramBoolean)
  {
    this.majorTicksVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty majorTicksVisibleProperty()
  {
    return this.majorTicksVisible;
  }
  
  public final Gauge.TickmarkType getMajorTickmarkType()
  {
    return (Gauge.TickmarkType)this.majorTickmarkType.get();
  }
  
  public final void setMajorTickmarkType(Gauge.TickmarkType paramTickmarkType)
  {
    this.majorTickmarkType.set(paramTickmarkType);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.TickmarkType> majorTickmarkTypeProperty()
  {
    return this.majorTickmarkType;
  }
  
  public final Color getMajorTickmarkColor()
  {
    return (Color)this.majorTickmarkColor.get();
  }
  
  public final void setMajorTickmarkColor(Color paramColor)
  {
    this.majorTickmarkColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> majorTickmarkColorProperty()
  {
    return this.majorTickmarkColor;
  }
  
  public final boolean isMajorTickmarkColorEnabled()
  {
    return this.majorTickmarkColorEnabled.get();
  }
  
  public final void setMajorTickmarkColorEnabled(boolean paramBoolean)
  {
    this.majorTickmarkColorEnabled.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty majorTickmarkColorEnabledProperty()
  {
    return this.majorTickmarkColorEnabled;
  }
  
  public final boolean isMinorTicksVisible()
  {
    return this.minorTicksVisible.get();
  }
  
  public final void setMinorTicksVisible(boolean paramBoolean)
  {
    this.minorTicksVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty minorTicksVisibleProperty()
  {
    return this.minorTicksVisible;
  }
  
  public final Color getMinorTickmarkColor()
  {
    return (Color)this.minorTickmarkColor.get();
  }
  
  public final void setMinorTickmarkColor(Color paramColor)
  {
    this.minorTickmarkColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> minorTickmarkColorProperty()
  {
    return this.minorTickmarkColor;
  }
  
  public final boolean isMinorTickmarkColorEnabled()
  {
    return this.minorTickmarkColorEnabled.get();
  }
  
  public final void setMinorTickmarkColorEnabled(boolean paramBoolean)
  {
    this.minorTickmarkColorEnabled.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty minorTickmarkColorEnabledProperty()
  {
    return this.minorTickmarkColorEnabled;
  }
  
  public final boolean isTickLabelsVisible()
  {
    return this.tickLabelsVisible.get();
  }
  
  public final void setTickLabelsVisible(boolean paramBoolean)
  {
    this.tickLabelsVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty ticklabelsVisibleProperty()
  {
    return this.tickLabelsVisible;
  }
  
  public final Gauge.TicklabelOrientation getTickLabelOrientation()
  {
    return (Gauge.TicklabelOrientation)this.tickLabelOrientation.get();
  }
  
  public final void setTickLabelOrientation(Gauge.TicklabelOrientation paramTicklabelOrientation)
  {
    this.tickLabelOrientation.set(paramTicklabelOrientation);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.TicklabelOrientation> tickLabelOrientationProperty()
  {
    return this.tickLabelOrientation;
  }
  
  public final Gauge.NumberFormat getTickLabelNumberFormat()
  {
    return (Gauge.NumberFormat)this.tickLabelNumberFormat.get();
  }
  
  public final void setTickLabelNumberFormat(Gauge.NumberFormat paramNumberFormat)
  {
    this.tickLabelNumberFormat.set(paramNumberFormat);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Gauge.NumberFormat> tickLabelNumberFormatProperty()
  {
    return this.tickLabelNumberFormat;
  }
  
  public final Point2D getTickmarksOffset()
  {
    return (Point2D)this.tickmarksOffset.get();
  }
  
  public final void setTickmarksOffset(Point2D paramPoint2D)
  {
    this.tickmarksOffset.set(paramPoint2D);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Point2D> tickmarksOffsetProperty()
  {
    return this.tickmarksOffset;
  }
  
  public final boolean isTickmarkGlowEnabled()
  {
    return this.tickmarkGlowEnabled.get();
  }
  
  public final void setTickmarkGlowEnabled(boolean paramBoolean)
  {
    this.tickmarkGlowEnabled.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty tickmarkGlowEnabledProperty()
  {
    return this.tickmarkGlowEnabled;
  }
  
  public final Color getTickmarkGlowColor()
  {
    return (Color)this.tickmarkGlowColor.get();
  }
  
  public final void setTickmarkGlowColor(Color paramColor)
  {
    this.tickmarkGlowColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> tickmarkGlowColorProperty()
  {
    return this.tickmarkGlowColor;
  }
  
  public final boolean isSectionsVisible()
  {
    return this.sectionsVisible.get();
  }
  
  public final void setSectionsVisible(boolean paramBoolean)
  {
    this.sectionsVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty sectionsVisibleProperty()
  {
    return this.sectionsVisible;
  }
  
  public final boolean isExpandedSections()
  {
    return this.expandedSections.get();
  }
  
  public final void setExpandedSections(boolean paramBoolean)
  {
    this.expandedSections.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty expandedSectionsProperty()
  {
    return this.expandedSections;
  }
  
  public final boolean isSectionsHighlighting()
  {
    return this.sectionsHighlighting.get();
  }
  
  public final void setSectionsHighlighting(boolean paramBoolean)
  {
    this.sectionsHighlighting.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty sectionsHighlightingProperty()
  {
    return this.sectionsHighlighting;
  }
  
  public final boolean isShowSectionTickmarksOnly()
  {
    return this.showSectionTickmarksOnly.get();
  }
  
  public final void setShowSectionTickmarksOnly(boolean paramBoolean)
  {
    this.showSectionTickmarksOnly.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty showSectionTickmarksOnlyProperty()
  {
    return this.showSectionTickmarksOnly;
  }
  
  public final boolean isAreasVisible()
  {
    return this.areasVisible.get();
  }
  
  public final void setAreasVisible(boolean paramBoolean)
  {
    this.areasVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty areasVisibleProperty()
  {
    return this.areasVisible;
  }
  
  public final boolean isAreasHighlighting()
  {
    return this.areasHighlighting.get();
  }
  
  public final void setAreasHighlighting(boolean paramBoolean)
  {
    this.areasHighlighting.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty areasHighlightingProperty()
  {
    return this.areasHighlighting;
  }
  
  public final boolean isMarkersVisible()
  {
    return this.markersVisible.get();
  }
  
  public final void setMarkersVisible(boolean paramBoolean)
  {
    this.markersVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty markersVisibleProperty()
  {
    return this.markersVisible;
  }
  
  public final Color getTextureColor()
  {
    return (Color)this.textureColor.get();
  }
  
  public final String getTextureColorString()
  {
    StringBuilder localStringBuilder = new StringBuilder(30);
    localStringBuilder.append("-fx-texture: ");
    localStringBuilder.append(Util.createCssColor(getTextureColor()));
    return localStringBuilder.toString();
  }
  
  public final void setTextureColor(Color paramColor)
  {
    this.textureColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> textureColorProperty()
  {
    return this.textureColor;
  }
  
  public final Color getSimpleGradientBaseColor()
  {
    return (Color)this.simpleGradientBaseColor.get();
  }
  
  public final String getSimpleGradientBaseColorString()
  {
    StringBuilder localStringBuilder = new StringBuilder(30);
    localStringBuilder.append("-fx-simplegradient-base: ");
    localStringBuilder.append(Util.createCssColor(getSimpleGradientBaseColor()));
    return localStringBuilder.toString();
  }
  
  public final void setSimpleGradientBaseColor(Color paramColor)
  {
    this.simpleGradientBaseColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> simpleGradientBaseColorProperty()
  {
    return this.simpleGradientBaseColor;
  }
  
  public final boolean isTitleVisible()
  {
    return this.titleVisible.get();
  }
  
  public final void setTitleVisible(boolean paramBoolean)
  {
    this.titleVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty titleVisibleProperty()
  {
    return this.titleVisible;
  }
  
  public final boolean isUnitVisible()
  {
    return this.unitVisible.get();
  }
  
  public final void setUnitVisible(boolean paramBoolean)
  {
    this.unitVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty unitVisibleProperty()
  {
    return this.unitVisible;
  }
  
  public final boolean isTrendVisible()
  {
    return this.trendVisible.get();
  }
  
  public final void setTrendVisible(boolean paramBoolean)
  {
    this.trendVisible.set(paramBoolean);
    fireStyleModelEvent();
  }
  
  public final BooleanProperty trendVisibleProperty()
  {
    return this.trendVisible;
  }
  
  public final Color getTrendUpColor()
  {
    return (Color)this.trendUpColor.get();
  }
  
  public final void setTrendUpColor(Color paramColor)
  {
    this.trendUpColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> trendUpColorProperty()
  {
    return this.trendUpColor;
  }
  
  public final Color getTrendRisingColor()
  {
    return (Color)this.trendRisingColor.get();
  }
  
  public final void setTrendRisingColor(Color paramColor)
  {
    this.trendRisingColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> trendRisingColorProperty()
  {
    return this.trendRisingColor;
  }
  
  public final Color getTrendSteadyColor()
  {
    return (Color)this.trendSteadyColor.get();
  }
  
  public final void setTrendSteadyColor(Color paramColor)
  {
    this.trendSteadyColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> trendSteadyColorProperty()
  {
    return this.trendSteadyColor;
  }
  
  public final Color getTrendFallingColor()
  {
    return (Color)this.trendFallingColor.get();
  }
  
  public final void setTrendFallingColor(Color paramColor)
  {
    this.trendFallingColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> trendFallingColorProperty()
  {
    return this.trendFallingColor;
  }
  
  public final Color getTrendDownColor()
  {
    return (Color)this.trendDownColor.get();
  }
  
  public final void setTrendDownColor(Color paramColor)
  {
    this.trendDownColor.set(paramColor);
    fireStyleModelEvent();
  }
  
  public final ObjectProperty<Color> trendDownColorProperty()
  {
    return this.trendDownColor;
  }
  
  public class StyleModelEvent
    extends Event
  {
    public StyleModelEvent()
    {
      super();
    }
    
    public StyleModelEvent(Object paramObject, EventTarget paramEventTarget)
    {
      super(paramEventTarget, new EventType());
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/StyleModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */