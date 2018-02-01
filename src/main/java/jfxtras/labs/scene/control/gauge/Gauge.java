package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Gauge
  extends Control
{
  private ObjectProperty<GaugeModel> gaugeModelProperty;
  private ObjectProperty<StyleModel> styleModelProperty;
  private GaugeModel gaugeModel;
  private StyleModel styleModel;
  private ObjectProperty<RadialRange> radialRange;
  private DoubleProperty angleStep;
  private final ObjectProperty<EventHandler<GaugeModel.GaugeModelEvent>> onGaugeModelEvent = new SimpleObjectProperty();
  private final ObjectProperty<EventHandler<StyleModel.StyleModelEvent>> onStyleModelEvent = new SimpleObjectProperty();
  
  protected Gauge()
  {
    this(new GaugeModel(), new StyleModel());
  }
  
  protected Gauge(GaugeModel paramGaugeModel)
  {
    this(paramGaugeModel, new StyleModel());
  }
  
  protected Gauge(StyleModel paramStyleModel)
  {
    this(new GaugeModel(), paramStyleModel);
  }
  
  protected Gauge(GaugeModel paramGaugeModel, StyleModel paramStyleModel)
  {
    this.gaugeModelProperty = new SimpleObjectProperty(paramGaugeModel);
    this.styleModelProperty = new SimpleObjectProperty(paramStyleModel);
    this.gaugeModel = ((GaugeModel)this.gaugeModelProperty.get());
    this.styleModel = ((StyleModel)this.styleModelProperty.get());
    this.radialRange = new SimpleObjectProperty(RadialRange.RADIAL_300);
    this.angleStep = new SimpleDoubleProperty(((RadialRange)this.radialRange.get()).ANGLE_RANGE / this.gaugeModel.getRange());
    ledBlinkingProperty().bind(thresholdExceededProperty());
    addGaugeModelListener();
    addStyleModelListener();
  }
  
  private final void addGaugeModelListener()
  {
    this.gaugeModel.setOnGaugeModelEvent(new EventHandler()
    {
      public void handle(GaugeModel.GaugeModelEvent paramAnonymousGaugeModelEvent)
      {
        Gauge.this.forwardModelEvent(paramAnonymousGaugeModelEvent);
      }
    });
  }
  
  public final ObjectProperty<EventHandler<GaugeModel.GaugeModelEvent>> onGaugeModelEventProperty()
  {
    return this.onGaugeModelEvent;
  }
  
  public final void setOnGaugeModelEvent(EventHandler<GaugeModel.GaugeModelEvent> paramEventHandler)
  {
    onGaugeModelEventProperty().set(paramEventHandler);
  }
  
  public final EventHandler<GaugeModel.GaugeModelEvent> getOnGaugeModelEvent()
  {
    return (EventHandler)onGaugeModelEventProperty().get();
  }
  
  public void forwardModelEvent(GaugeModel.GaugeModelEvent paramGaugeModelEvent)
  {
    EventHandler localEventHandler = getOnGaugeModelEvent();
    if (localEventHandler != null) {
      localEventHandler.handle(paramGaugeModelEvent);
    }
  }
  
  private final void addStyleModelListener()
  {
    this.styleModel.setOnStyleModelEvent(new EventHandler()
    {
      public void handle(StyleModel.StyleModelEvent paramAnonymousStyleModelEvent)
      {
        Gauge.this.forwardStyleModelEvent(paramAnonymousStyleModelEvent);
      }
    });
  }
  
  public final ObjectProperty<EventHandler<StyleModel.StyleModelEvent>> onStyleModelEventProperty()
  {
    return this.onStyleModelEvent;
  }
  
  public final void setOnStyleModelEvent(EventHandler<StyleModel.StyleModelEvent> paramEventHandler)
  {
    onStyleModelEventProperty().set(paramEventHandler);
  }
  
  public final EventHandler<StyleModel.StyleModelEvent> getOnStyleModelEvent()
  {
    return (EventHandler)onStyleModelEventProperty().get();
  }
  
  public void forwardStyleModelEvent(StyleModel.StyleModelEvent paramStyleModelEvent)
  {
    EventHandler localEventHandler = getOnStyleModelEvent();
    if (localEventHandler != null) {
      localEventHandler.handle(paramStyleModelEvent);
    }
  }
  
  public String getUserAgentStylesheet()
  {
    return getClass().getResource("steelseries.css").toExternalForm();
  }
  
  public final StyleModel getStyleModel()
  {
    return (StyleModel)this.styleModelProperty.get();
  }
  
  public final void setStyleModel(StyleModel paramStyleModel)
  {
    this.styleModelProperty.set(paramStyleModel);
    this.styleModel = ((StyleModel)styleModelProperty().get());
    addStyleModelListener();
  }
  
  public final ReadOnlyObjectProperty<StyleModel> styleModelProperty()
  {
    return this.styleModelProperty;
  }
  
  public final GaugeModel getGaugeModel()
  {
    return (GaugeModel)this.gaugeModelProperty.get();
  }
  
  public final void setGaugeModel(GaugeModel paramGaugeModel)
  {
    this.gaugeModelProperty.set(paramGaugeModel);
    this.gaugeModel = ((GaugeModel)this.gaugeModelProperty.get());
    addGaugeModelListener();
  }
  
  public final ReadOnlyObjectProperty<GaugeModel> gaugeModelProperty()
  {
    return this.gaugeModelProperty;
  }
  
  public RadialRange getRadialRange()
  {
    return (RadialRange)this.radialRange.get();
  }
  
  public void setRadialRange(RadialRange paramRadialRange)
  {
    this.radialRange.set(paramRadialRange);
    this.gaugeModel.calcRange();
    this.angleStep.set(((RadialRange)this.radialRange.get()).ANGLE_RANGE / this.gaugeModel.getRange());
    if (paramRadialRange == RadialRange.RADIAL_360)
    {
      setKnobsVisible(false);
      setEndlessMode(true);
    }
    else
    {
      setEndlessMode(false);
    }
  }
  
  public final ObjectProperty<RadialRange> radialRangeProperty()
  {
    return this.radialRange;
  }
  
  public final double getAngleStep()
  {
    return this.angleStep.get();
  }
  
  public final void recalcRange()
  {
    if (getMinValue() < getMaxValue()) {
      this.gaugeModel.calcRange();
    }
    this.angleStep.set(((RadialRange)this.radialRange.get()).ANGLE_RANGE / this.gaugeModel.getRange());
  }
  
  public final ReadOnlyDoubleProperty angleStepProperty()
  {
    return this.angleStep;
  }
  
  public final Point2D getLedPosition()
  {
    return ((RadialRange)this.radialRange.get()).LED_POSITION;
  }
  
  public final Point2D getUserLedPosition()
  {
    return ((RadialRange)this.radialRange.get()).USER_LED_POSITION;
  }
  
  public final double getValue()
  {
    return this.gaugeModel.getValue();
  }
  
  public final void setValue(double paramDouble)
  {
    this.gaugeModel.setValue(paramDouble);
  }
  
  public final DoubleProperty valueProperty()
  {
    return this.gaugeModel.valueProperty();
  }
  
  public final double getRealValue()
  {
    return this.gaugeModel.getRealValue();
  }
  
  public final ReadOnlyDoubleProperty realValueProperty()
  {
    return this.gaugeModel.realValueProperty();
  }
  
  public final boolean isValueAnimationEnabled()
  {
    return this.gaugeModel.isValueAnimationEnabled();
  }
  
  public final void setValueAnimationEnabled(boolean paramBoolean)
  {
    this.gaugeModel.setValueAnimationEnabled(paramBoolean);
  }
  
  public final BooleanProperty valueAnimationEnabledProperty()
  {
    return this.gaugeModel.valueAnimationEnabledProperty();
  }
  
  public final double getAnimationDuration()
  {
    return this.gaugeModel.getAnimationDuration();
  }
  
  public final void setAnimationDuration(double paramDouble)
  {
    this.gaugeModel.setAnimationDuration(paramDouble);
  }
  
  public final DoubleProperty animationDurationProperty()
  {
    return this.gaugeModel.animationDurationProperty();
  }
  
  public final double getRedrawTolerance()
  {
    return this.gaugeModel.getRedrawTolerance();
  }
  
  public final void setRedrawTolerance(double paramDouble)
  {
    this.gaugeModel.setRedrawTolerance(paramDouble);
  }
  
  public final DoubleProperty redrawToleranceProperty()
  {
    return this.gaugeModel.redrawToleranceProperty();
  }
  
  public final double getRedrawToleranceValue()
  {
    return this.gaugeModel.getRedrawToleranceValue();
  }
  
  public final double getMinValue()
  {
    return this.gaugeModel.getMinValue();
  }
  
  public final void setMinValue(double paramDouble)
  {
    this.gaugeModel.setMinValue(paramDouble);
    this.gaugeModel.calcRange();
    this.angleStep.set(((RadialRange)this.radialRange.get()).ANGLE_RANGE / this.gaugeModel.getRange());
  }
  
  public final ReadOnlyDoubleProperty minValueProperty()
  {
    return this.gaugeModel.minValueProperty();
  }
  
  public final double getUncorrectedMinValue()
  {
    return this.gaugeModel.getUncorrectedMinValue();
  }
  
  public final double getMaxValue()
  {
    return this.gaugeModel.getMaxValue();
  }
  
  public final void setMaxValue(double paramDouble)
  {
    this.gaugeModel.setMaxValue(paramDouble);
    this.gaugeModel.calcRange();
    this.angleStep.set(((RadialRange)this.radialRange.get()).ANGLE_RANGE / this.gaugeModel.getRange());
  }
  
  public final ReadOnlyDoubleProperty maxValueProperty()
  {
    return this.gaugeModel.maxValueProperty();
  }
  
  public final double getUncorrectedMaxValue()
  {
    return this.gaugeModel.getUncorrectedMaxValue();
  }
  
  public final double getRange()
  {
    return this.gaugeModel.getRange();
  }
  
  public final ReadOnlyDoubleProperty rangeProperty()
  {
    return this.gaugeModel.rangeProperty();
  }
  
  public final double getMinMeasuredValue()
  {
    return this.gaugeModel.getMinMeasuredValue();
  }
  
  public final void setMinMeasuredValue(double paramDouble)
  {
    this.gaugeModel.setMinMeasuredValue(paramDouble);
  }
  
  public final DoubleProperty minMeasuredValueProperty()
  {
    return this.gaugeModel.minMeasuredValueProperty();
  }
  
  public final boolean isBargraph()
  {
    return this.styleModel.isBargraph();
  }
  
  public final void setBargraph(boolean paramBoolean)
  {
    this.styleModel.setBargraph(paramBoolean);
  }
  
  public final BooleanProperty bargraphProperty()
  {
    return this.styleModel.bargraphProperty();
  }
  
  public final boolean isMinMeasuredValueVisible()
  {
    return this.styleModel.isMinMeasuredValueVisible();
  }
  
  public final void setMinMeasuredValueVisible(boolean paramBoolean)
  {
    this.styleModel.setMinMeasuredValueVisible(paramBoolean);
  }
  
  public final BooleanProperty minMeasuredValueVisibleProperty()
  {
    return this.styleModel.minMeasuredValueVisibleProperty();
  }
  
  public final void resetMinMeasuredValue()
  {
    this.gaugeModel.resetMinMeasuredValue();
  }
  
  public final double getMaxMeasuredValue()
  {
    return this.gaugeModel.getMaxMeasuredValue();
  }
  
  public final void setMaxMeasuredValue(double paramDouble)
  {
    this.gaugeModel.setMaxMeasuredValue(paramDouble);
  }
  
  public final DoubleProperty maxMeasuredValueProperty()
  {
    return this.gaugeModel.maxMeasuredValueProperty();
  }
  
  public final boolean isMaxMeasuredValueVisible()
  {
    return this.styleModel.isMaxMeasuredValueVisible();
  }
  
  public final void setMaxMeasuredValueVisible(boolean paramBoolean)
  {
    this.styleModel.setMaxMeasuredValueVisible(paramBoolean);
  }
  
  public final BooleanProperty maxMeasuredValueVisibleProperty()
  {
    return this.styleModel.maxMeasuredValueVisibleProperty();
  }
  
  public final void resetMaxMeasuredValue()
  {
    this.gaugeModel.resetMaxMeasuredValue();
  }
  
  public final void resetMinMaxMeasuredValue()
  {
    this.gaugeModel.resetMinMaxMeasuredValue();
  }
  
  public final double getThreshold()
  {
    return this.gaugeModel.getThreshold();
  }
  
  public final void setThreshold(double paramDouble)
  {
    this.gaugeModel.setThreshold(paramDouble);
  }
  
  public final DoubleProperty thresholdProperty()
  {
    return this.gaugeModel.thresholdProperty();
  }
  
  public final boolean isThresholdBehaviorInverted()
  {
    return this.gaugeModel.isThresholdBehaviorInverted();
  }
  
  public final void setThresholdBehaviorInverted(boolean paramBoolean)
  {
    this.gaugeModel.setThresholdBehaviorInverted(paramBoolean);
  }
  
  public final BooleanProperty thresholdBehaviorInvertedProperty()
  {
    return this.gaugeModel.thresholdBehaviorInvertedProperty();
  }
  
  public final boolean isThresholdExceeded()
  {
    return this.gaugeModel.isThresholdExceeded();
  }
  
  public final void setThresholdExceeded(boolean paramBoolean)
  {
    this.gaugeModel.setThresholdExceeded(paramBoolean);
  }
  
  public final BooleanProperty thresholdExceededProperty()
  {
    return this.gaugeModel.thresholdExceededProperty();
  }
  
  public final boolean isThresholdVisible()
  {
    return this.styleModel.isThresholdVisible();
  }
  
  public final void setThresholdVisible(boolean paramBoolean)
  {
    this.styleModel.setThresholdVisible(paramBoolean);
  }
  
  public final BooleanProperty thresholdVisibleProperty()
  {
    return this.styleModel.thresholdVisibleProperty();
  }
  
  public final ThresholdColor getThresholdColor()
  {
    return this.styleModel.getThresholdColor();
  }
  
  public final void setThresholdColor(ThresholdColor paramThresholdColor)
  {
    this.styleModel.setThresholdColor(paramThresholdColor);
  }
  
  public final ObjectProperty<ThresholdColor> thresholdColorProperty()
  {
    return this.styleModel.thresholdColorProperty();
  }
  
  public final String getTitle()
  {
    return this.gaugeModel.getTitle();
  }
  
  public final void setTitle(String paramString)
  {
    this.gaugeModel.setTitle(paramString);
  }
  
  public final StringProperty titleProperty()
  {
    return this.gaugeModel.titleProperty();
  }
  
  public final String getUnit()
  {
    return this.gaugeModel.getUnit();
  }
  
  public final void setUnit(String paramString)
  {
    this.gaugeModel.setUnit(paramString);
  }
  
  public final StringProperty unitProperty()
  {
    return this.gaugeModel.unitProperty();
  }
  
  public final FrameDesign getFrameDesign()
  {
    return this.styleModel.getFrameDesign();
  }
  
  public final void setFrameDesign(FrameDesign paramFrameDesign)
  {
    this.styleModel.setFrameDesign(paramFrameDesign);
  }
  
  public final ObjectProperty<FrameDesign> frameDesignProperty()
  {
    return this.styleModel.frameDesignProperty();
  }
  
  public final Color getFrameBaseColor()
  {
    return this.styleModel.getFrameBaseColor();
  }
  
  public final void setFrameBaseColor(Color paramColor)
  {
    this.styleModel.setFrameBaseColor(paramColor);
  }
  
  public final ObjectProperty<Color> frameBaseColorProperty()
  {
    return this.styleModel.frameBaseColorProperty();
  }
  
  public final boolean isFrameVisible()
  {
    return this.styleModel.isFrameVisible();
  }
  
  public final void setFrameVisible(boolean paramBoolean)
  {
    this.styleModel.setFrameVisible(paramBoolean);
  }
  
  public final BooleanProperty frameVisibleProperty()
  {
    return this.styleModel.frameVisibleProperty();
  }
  
  public final BackgroundDesign getBackgroundDesign()
  {
    return this.styleModel.getBackgroundDesign();
  }
  
  public final void setBackgroundDesign(BackgroundDesign paramBackgroundDesign)
  {
    this.styleModel.setBackgroundDesign(paramBackgroundDesign);
  }
  
  public final ObjectProperty<BackgroundDesign> backgroundDesignProperty()
  {
    return this.styleModel.backgroundDesignProperty();
  }
  
  public final boolean isBackgroundVisible()
  {
    return this.styleModel.isBackgroundVisible();
  }
  
  public final void setBackgroundVisible(boolean paramBoolean)
  {
    this.styleModel.setBackgroundVisible(paramBoolean);
  }
  
  public final BooleanProperty backgroundVisibleProperty()
  {
    return this.styleModel.backgroundVisibleProperty();
  }
  
  public final KnobDesign getKnobDesign()
  {
    return this.styleModel.getKnobDesign();
  }
  
  public final void setKnobDesign(KnobDesign paramKnobDesign)
  {
    this.styleModel.setKnobDesign(paramKnobDesign);
  }
  
  public final ObjectProperty<KnobDesign> knobDesignProperty()
  {
    return this.styleModel.knobDesignProperty();
  }
  
  public final KnobColor getKnobColor()
  {
    return this.styleModel.getKnobColor();
  }
  
  public final void setKnobColor(KnobColor paramKnobColor)
  {
    this.styleModel.setKnobColor(paramKnobColor);
  }
  
  public final ObjectProperty<KnobColor> knobColorProperty()
  {
    return this.styleModel.knobColorProperty();
  }
  
  public final boolean isKnobsVisible()
  {
    return this.styleModel.getKnobsVisible();
  }
  
  public final void setKnobsVisible(boolean paramBoolean)
  {
    this.styleModel.setKnobsVisible(paramBoolean);
  }
  
  public final BooleanProperty knobsVisibleProperty()
  {
    return this.styleModel.knobsVisibleProperty();
  }
  
  public final PointerType getPointerType()
  {
    return this.styleModel.getPointerType();
  }
  
  public final void setPointerType(PointerType paramPointerType)
  {
    this.styleModel.setPointerType(paramPointerType);
  }
  
  public final ObjectProperty<PointerType> pointerTypeProperty()
  {
    return this.styleModel.pointerTypeProperty();
  }
  
  public final ColorDef getValueColor()
  {
    return this.styleModel.getValueColor();
  }
  
  public final void setValueColor(ColorDef paramColorDef)
  {
    this.styleModel.setValueColor(paramColorDef);
  }
  
  public final ObjectProperty<ColorDef> valueColorProperty()
  {
    return this.styleModel.valueColorProperty();
  }
  
  public final boolean isPointerGlowEnabled()
  {
    return this.styleModel.isPointerGlowEnabled();
  }
  
  public final void setPointerGlowEnabled(boolean paramBoolean)
  {
    this.styleModel.setPointerGlowEnabled(paramBoolean);
  }
  
  public final BooleanProperty pointerGlowEnabledProperty()
  {
    return this.styleModel.pointerGlowEnabledProperty();
  }
  
  public final boolean isPointerShadowEnabled()
  {
    return this.styleModel.isPointerShadowEnabled();
  }
  
  public final void setPointerShadowEnabled(boolean paramBoolean)
  {
    this.styleModel.setPointerShadowEnabled(paramBoolean);
  }
  
  public final BooleanProperty pointerShadowEnabledProperty()
  {
    return this.styleModel.pointerShadowEnabledProperty();
  }
  
  public final boolean isLedVisible()
  {
    return this.styleModel.isLedVisible();
  }
  
  public final void setLedVisible(boolean paramBoolean)
  {
    this.styleModel.setLedVisible(paramBoolean);
  }
  
  public final BooleanProperty ledVisibleProperty()
  {
    return this.styleModel.ledVisibleProperty();
  }
  
  public final LedColor getLedColor()
  {
    return this.styleModel.getLedColor();
  }
  
  public final void setLedColor(LedColor paramLedColor)
  {
    this.styleModel.setLedColor(paramLedColor);
  }
  
  public final ObjectProperty<LedColor> ledColorProperty()
  {
    return this.styleModel.ledColorProperty();
  }
  
  public final boolean isLedBlinking()
  {
    return this.styleModel.isLedBlinking();
  }
  
  public final void setLedBlinking(boolean paramBoolean)
  {
    this.styleModel.setLedBlinking(paramBoolean);
  }
  
  public final BooleanProperty ledBlinkingProperty()
  {
    return this.styleModel.ledBlinkingProperty();
  }
  
  public final boolean isUserLedVisible()
  {
    return this.styleModel.isUserLedVisible();
  }
  
  public final void setUserLedVisible(boolean paramBoolean)
  {
    this.styleModel.setUserLedVisible(paramBoolean);
  }
  
  public final BooleanProperty userLedVisibleProperty()
  {
    return this.styleModel.userLedVisibleProperty();
  }
  
  public final LedColor getUserLedColor()
  {
    return this.styleModel.getUserLedColor();
  }
  
  public final void setUserLedColor(LedColor paramLedColor)
  {
    this.styleModel.setUserLedColor(paramLedColor);
  }
  
  public final ObjectProperty<LedColor> userLedColorProperty()
  {
    return this.styleModel.userLedColorProperty();
  }
  
  public final boolean isUserLedOn()
  {
    return this.styleModel.isUserLedOn();
  }
  
  public final void setUserLedOn(boolean paramBoolean)
  {
    this.styleModel.setUserLedOn(paramBoolean);
  }
  
  public final BooleanProperty userLedOnProperty()
  {
    return this.styleModel.userLedOnProperty();
  }
  
  public final boolean isUserLedBlinking()
  {
    return this.styleModel.isUserLedBlinking();
  }
  
  public final void setUserLedBlinking(boolean paramBoolean)
  {
    this.styleModel.setUserLedBlinking(paramBoolean);
  }
  
  public final BooleanProperty userLedBlinkingProperty()
  {
    return this.styleModel.userLedBlinkingProperty();
  }
  
  public final String getTitleFont()
  {
    return this.styleModel.getTitleFont();
  }
  
  public final void setTitleFont(String paramString)
  {
    this.styleModel.setTitleFont(paramString);
  }
  
  public final StringProperty titleFontProperty()
  {
    return this.styleModel.titleFontProperty();
  }
  
  public final String getUnitFont()
  {
    return this.styleModel.getUnitFont();
  }
  
  public final void setUnitFont(String paramString)
  {
    this.styleModel.setUnitFont(paramString);
  }
  
  public final StringProperty unitFontProperty()
  {
    return this.styleModel.unitFontProperty();
  }
  
  public final Radial.ForegroundType getForegroundType()
  {
    return this.styleModel.getForegroundType();
  }
  
  public final void setForegroundType(Radial.ForegroundType paramForegroundType)
  {
    this.styleModel.setForegroundType(paramForegroundType);
  }
  
  public final ObjectProperty<Radial.ForegroundType> foregroundTypeProperty()
  {
    return this.styleModel.foregroundTypeProperty();
  }
  
  public final boolean isForegroundVisible()
  {
    return this.styleModel.isForegroundVisible();
  }
  
  public final void setForegroundVisible(boolean paramBoolean)
  {
    this.styleModel.setForegroundVisible(paramBoolean);
  }
  
  public final BooleanProperty foregroundVisibleProperty()
  {
    return this.styleModel.foregroundVisibleProperty();
  }
  
  public final double getLcdValue()
  {
    return this.gaugeModel.getLcdValue();
  }
  
  public final void setLcdValue(double paramDouble)
  {
    this.gaugeModel.setLcdValue(paramDouble);
  }
  
  public final DoubleProperty lcdValueProperty()
  {
    return this.gaugeModel.lcdValueProperty();
  }
  
  public final boolean isLcdValueCoupled()
  {
    return this.gaugeModel.isLcdValueCoupled();
  }
  
  public final void setLcdValueCoupled(boolean paramBoolean)
  {
    this.gaugeModel.setLcdValueCoupled(paramBoolean);
  }
  
  public final BooleanProperty lcdValueCoupledProperty()
  {
    return this.gaugeModel.lcdValueCoupledProperty();
  }
  
  public final double getLcdThreshold()
  {
    return this.gaugeModel.getLcdThreshold();
  }
  
  public final void setLcdThreshold(double paramDouble)
  {
    this.gaugeModel.setLcdThreshold(paramDouble);
  }
  
  public final DoubleProperty lcdThresholdProperty()
  {
    return this.gaugeModel.lcdThresholdProperty();
  }
  
  public final boolean isLcdThresholdBehaviorInverted()
  {
    return this.gaugeModel.isLcdThresholdBehaviorInverted();
  }
  
  public final void setLcdThresholdBehaviorInverted(boolean paramBoolean)
  {
    this.gaugeModel.setLcdThresholdBehaviorInverted(paramBoolean);
  }
  
  public final BooleanProperty lcdThresholdBehaviorInvertedProperty()
  {
    return this.gaugeModel.lcdThresholdBehaviorInvertedProperty();
  }
  
  public final boolean isLcdThresholdVisible()
  {
    return this.styleModel.isLcdThresholdVisible();
  }
  
  public final void setLcdThresholdVisible(boolean paramBoolean)
  {
    this.styleModel.setLcdThresholdVisible(paramBoolean);
  }
  
  public final BooleanProperty lcdThresholdVisibleProperty()
  {
    return this.styleModel.lcdThresholdVisibleProperty();
  }
  
  public final LcdDesign getLcdDesign()
  {
    return this.styleModel.getLcdDesign();
  }
  
  public final void setLcdDesign(LcdDesign paramLcdDesign)
  {
    this.styleModel.setLcdDesign(paramLcdDesign);
  }
  
  public final ObjectProperty lcdDesignProperty()
  {
    return this.styleModel.lcdDesignProperty();
  }
  
  public final boolean isLcdVisible()
  {
    return this.styleModel.isLcdVisible();
  }
  
  public final void setLcdVisible(boolean paramBoolean)
  {
    this.styleModel.setLcdVisible(paramBoolean);
  }
  
  public final BooleanProperty lcdVisibleProperty()
  {
    return this.styleModel.lcdVisibleProperty();
  }
  
  public final String getLcdUnit()
  {
    return this.gaugeModel.getLcdUnit();
  }
  
  public final void setLcdUnit(String paramString)
  {
    this.gaugeModel.setLcdUnit(paramString);
  }
  
  public final StringProperty lcdUnitProperty()
  {
    return this.gaugeModel.lcdUnitProperty();
  }
  
  public final boolean isLcdUnitVisible()
  {
    return this.styleModel.getLcdUnitVisible();
  }
  
  public final void setLcdUnitVisible(boolean paramBoolean)
  {
    this.styleModel.setLcdUnitVisible(paramBoolean);
  }
  
  public final BooleanProperty lcdUnitVisibleProperty()
  {
    return this.styleModel.lcdUnitVisibleProperty();
  }
  
  public final String getLcdUnitFont()
  {
    return this.styleModel.getLcdUnitFont();
  }
  
  public final void setLcdUnitFont(String paramString)
  {
    this.styleModel.setLcdUnitFont(paramString);
  }
  
  public final StringProperty lcdUnitFontProperty()
  {
    return this.styleModel.lcdUnitFontProperty();
  }
  
  public final String getLcdTitleFont()
  {
    return this.styleModel.getLcdTitleFont();
  }
  
  public final void setLcdTitleFont(String paramString)
  {
    this.styleModel.setLcdTitleFont(paramString);
  }
  
  public final StringProperty lcdTitleFontProperty()
  {
    return this.styleModel.lcdTitleFontProperty();
  }
  
  public final LcdFont getLcdValueFont()
  {
    return this.styleModel.getLcdValueFont();
  }
  
  public final void setLcdValueFont(LcdFont paramLcdFont)
  {
    this.styleModel.setLcdValueFont(paramLcdFont);
  }
  
  public final ObjectProperty<LcdFont> lcdValueFontProperty()
  {
    return this.styleModel.lcdValueFontProperty();
  }
  
  public final NumberSystem getLcdNumberSystem()
  {
    return this.gaugeModel.getLcdNumberSystem();
  }
  
  public final void setLcdNumberSystem(NumberSystem paramNumberSystem)
  {
    this.gaugeModel.setLcdNumberSystem(paramNumberSystem);
  }
  
  public final ObjectProperty lcdNumberSystemProperty()
  {
    return this.gaugeModel.lcdNumberSystemProperty();
  }
  
  public final boolean isLcdNumberSystemVisible()
  {
    return this.styleModel.isLcdNumberSystemVisible();
  }
  
  public final void setLcdNumberSystemVisible(boolean paramBoolean)
  {
    this.styleModel.setLcdNumberSystemVisible(paramBoolean);
  }
  
  public final BooleanProperty lcdNumberSystemVisibleProperty()
  {
    return this.styleModel.lcdNumberSystemVisibleProperty();
  }
  
  public final int getLcdDecimals()
  {
    return this.styleModel.getLcdDecimals();
  }
  
  public final void setLcdDecimals(int paramInt)
  {
    this.styleModel.setLcdDecimals(paramInt);
  }
  
  public final IntegerProperty lcdDecimalsProperty()
  {
    return this.styleModel.lcdDecimalsProperty();
  }
  
  public final boolean isLcdBlinking()
  {
    return this.styleModel.isLcdBlinking();
  }
  
  public final void setLcdBlinking(boolean paramBoolean)
  {
    this.styleModel.setLcdBlinking(paramBoolean);
  }
  
  public final BooleanProperty lcdBlinkingProperty()
  {
    return this.styleModel.lcdBlinkingProperty();
  }
  
  public final boolean isLcdBackgroundVisible()
  {
    return this.styleModel.isLcdBackgroundVisible();
  }
  
  public final void setLcdBackgroundVisible(boolean paramBoolean)
  {
    this.styleModel.setLcdBackgroundVisible(paramBoolean);
  }
  
  public final BooleanProperty lcdBackgroundVisibleProperty()
  {
    return this.styleModel.lcdBackgroundVisibleProperty();
  }
  
  public final boolean isGlowVisible()
  {
    return this.styleModel.isGlowVisible();
  }
  
  public final void setGlowVisible(boolean paramBoolean)
  {
    this.styleModel.setGlowVisible(paramBoolean);
  }
  
  public final BooleanProperty glowVisibleProperty()
  {
    return this.styleModel.glowVisibleProperty();
  }
  
  public final boolean isGlowOn()
  {
    return this.styleModel.isGlowOn();
  }
  
  public final void setGlowOn(boolean paramBoolean)
  {
    this.styleModel.setGlowOn(paramBoolean);
  }
  
  public final BooleanProperty glowOnProperty()
  {
    return this.styleModel.glowOnProperty();
  }
  
  public final boolean isPulsatingGlow()
  {
    return this.styleModel.isPulsatingGlow();
  }
  
  public final void setPulsatingGlow(boolean paramBoolean)
  {
    this.styleModel.setPulsatingGlow(paramBoolean);
  }
  
  public final BooleanProperty pulsatingGlowProperty()
  {
    return this.styleModel.pulsatingGlowProperty();
  }
  
  public final Color getGlowColor()
  {
    return this.styleModel.getGlowColor();
  }
  
  public final void setGlowColor(Color paramColor)
  {
    this.styleModel.setGlowColor(paramColor);
  }
  
  public final ObjectProperty<Color> glowColorProperty()
  {
    return this.styleModel.glowColorProperty();
  }
  
  public final boolean isTickmarksVisible()
  {
    return this.styleModel.isTickmarksVisible();
  }
  
  public final void setTickmarksVisible(boolean paramBoolean)
  {
    this.styleModel.setTickmarksVisible(paramBoolean);
  }
  
  public final BooleanProperty tickmarksVisibleProperty()
  {
    return this.styleModel.tickmarksVisibleProperty();
  }
  
  public final boolean isMajorTicksVisible()
  {
    return this.styleModel.isMajorTicksVisible();
  }
  
  public final void setMajorTicksVisible(boolean paramBoolean)
  {
    this.styleModel.setMajorTicksVisible(paramBoolean);
  }
  
  public final BooleanProperty majorTicksVisibleProperty()
  {
    return this.styleModel.majorTicksVisibleProperty();
  }
  
  public final TickmarkType getMajorTickmarkType()
  {
    return this.styleModel.getMajorTickmarkType();
  }
  
  public final void setMajorTickmarkType(TickmarkType paramTickmarkType)
  {
    this.styleModel.setMajorTickmarkType(paramTickmarkType);
  }
  
  public final ObjectProperty<TickmarkType> majorTickmarkTypeProperty()
  {
    return this.styleModel.majorTickmarkTypeProperty();
  }
  
  public final Color getMajorTickmarkColor()
  {
    return this.styleModel.getMajorTickmarkColor();
  }
  
  public final void setMajorTickmarkColor(Color paramColor)
  {
    this.styleModel.setMajorTickmarkColor(paramColor);
  }
  
  public final ObjectProperty<Color> majorTickmarkColorProperty()
  {
    return this.styleModel.majorTickmarkColorProperty();
  }
  
  public final boolean isMajorTickmarkColorEnabled()
  {
    return this.styleModel.isMajorTickmarkColorEnabled();
  }
  
  public final void setMajorTickmarkColorEnabled(boolean paramBoolean)
  {
    this.styleModel.setMajorTickmarkColorEnabled(paramBoolean);
  }
  
  public final BooleanProperty majorTickmarkColorEnabledProperty()
  {
    return this.styleModel.majorTickmarkColorEnabledProperty();
  }
  
  public final boolean isMinorTicksVisible()
  {
    return this.styleModel.isMinorTicksVisible();
  }
  
  public final void setMinorTicksVisible(boolean paramBoolean)
  {
    this.styleModel.setMinorTicksVisible(paramBoolean);
  }
  
  public final BooleanProperty minorTicksVisibleProperty()
  {
    return this.styleModel.minorTicksVisibleProperty();
  }
  
  public final Color getMinorTickmarkColor()
  {
    return this.styleModel.getMinorTickmarkColor();
  }
  
  public final void setMinorTickmarkColor(Color paramColor)
  {
    this.styleModel.setMinorTickmarkColor(paramColor);
  }
  
  public final ObjectProperty<Color> minorTickmarkColorProperty()
  {
    return this.styleModel.minorTickmarkColorProperty();
  }
  
  public final boolean isMinorTickmarkColorEnabled()
  {
    return this.styleModel.isMinorTickmarkColorEnabled();
  }
  
  public final void setMinorTickmarkColorEnabled(boolean paramBoolean)
  {
    this.styleModel.setMinorTickmarkColorEnabled(paramBoolean);
  }
  
  public final BooleanProperty minorTickmarkColorEnabledProperty()
  {
    return this.styleModel.minorTickmarkColorEnabledProperty();
  }
  
  public final boolean isTickLabelsVisible()
  {
    return this.styleModel.isTickLabelsVisible();
  }
  
  public final void setTickLabelsVisible(boolean paramBoolean)
  {
    this.styleModel.setTickLabelsVisible(paramBoolean);
  }
  
  public final BooleanProperty ticklabelsVisibleProperty()
  {
    return this.styleModel.ticklabelsVisibleProperty();
  }
  
  public final TicklabelOrientation getTickLabelOrientation()
  {
    return this.styleModel.getTickLabelOrientation();
  }
  
  public final void setTickLabelOrientation(TicklabelOrientation paramTicklabelOrientation)
  {
    this.styleModel.setTickLabelOrientation(paramTicklabelOrientation);
  }
  
  public final ObjectProperty<TicklabelOrientation> tickLabelOrientationProperty()
  {
    return this.styleModel.tickLabelOrientationProperty();
  }
  
  public final NumberFormat getTickLabelNumberFormat()
  {
    return this.styleModel.getTickLabelNumberFormat();
  }
  
  public final void setTickLabelNumberFormat(NumberFormat paramNumberFormat)
  {
    this.styleModel.setTickLabelNumberFormat(paramNumberFormat);
  }
  
  public final ObjectProperty<NumberFormat> tickLabelNumberFormatProperty()
  {
    return this.styleModel.tickLabelNumberFormatProperty();
  }
  
  public final Point2D getTickmarksOffset()
  {
    return this.styleModel.getTickmarksOffset();
  }
  
  public final void setTickmarksOffset(Point2D paramPoint2D)
  {
    this.styleModel.setTickmarksOffset(paramPoint2D);
  }
  
  public final ObjectProperty<Point2D> tickmarksOffsetProperty()
  {
    return this.styleModel.tickmarksOffsetProperty();
  }
  
  public final boolean isTickmarkGlowEnabled()
  {
    return this.styleModel.isTickmarkGlowEnabled();
  }
  
  public final void setTickmarkGlowEnabled(boolean paramBoolean)
  {
    this.styleModel.setTickmarkGlowEnabled(paramBoolean);
  }
  
  public final BooleanProperty tickmarkGlowEnabledProperty()
  {
    return this.styleModel.tickmarkGlowEnabledProperty();
  }
  
  public final Color getTickmarkGlowColor()
  {
    return this.styleModel.getTickmarkGlowColor();
  }
  
  public final void setTickmarkGlowColor(Color paramColor)
  {
    this.styleModel.setTickmarkGlowColor(paramColor);
  }
  
  public final ObjectProperty<Color> tickmarkGlowProperty()
  {
    return this.styleModel.tickmarkGlowColorProperty();
  }
  
  public final int getMaxNoOfMajorTicks()
  {
    return this.gaugeModel.getMaxNoOfMajorTicks();
  }
  
  public final void setMaxNoOfMajorTicks(int paramInt)
  {
    this.gaugeModel.setMaxNoOfMajorTicks(paramInt);
  }
  
  public final IntegerProperty maxNoOfMajorTicksProperty()
  {
    return this.gaugeModel.maxNoOfMajorTicksProperty();
  }
  
  public final int getMaxNoOfMinorTicks()
  {
    return this.gaugeModel.getMaxNoOfMinorTicks();
  }
  
  public final void setMaxNoOfMinorTicks(int paramInt)
  {
    this.gaugeModel.setMaxNoOfMinorTicks(paramInt);
  }
  
  public final IntegerProperty maxNoOfMinorTicksProperty()
  {
    return this.gaugeModel.maxNoOfMinorTicksProperty();
  }
  
  public final double getMajorTickSpacing()
  {
    return this.gaugeModel.getMajorTickSpacing();
  }
  
  public final void setMajorTickSpacing(double paramDouble)
  {
    this.gaugeModel.setMajorTickSpacing(paramDouble);
  }
  
  public final DoubleProperty majorTickSpacingProperty()
  {
    return this.gaugeModel.majorTickSpacingProperty();
  }
  
  public final double getMinorTickSpacing()
  {
    return this.gaugeModel.getMinorTickSpacing();
  }
  
  public final void setMinorTickSpacing(double paramDouble)
  {
    this.gaugeModel.setMinorTickSpacing(paramDouble);
  }
  
  public final DoubleProperty minorTickSpacingProperty()
  {
    return this.gaugeModel.minorTickSpacingProperty();
  }
  
  public final boolean isNiceScaling()
  {
    return this.gaugeModel.isNiceScaling();
  }
  
  public final void setNiceScaling(boolean paramBoolean)
  {
    this.gaugeModel.setNiceScaling(paramBoolean);
    recalcRange();
  }
  
  public final BooleanProperty niceScalingProperty()
  {
    return this.gaugeModel.niceScalingProperty();
  }
  
  public final boolean isTightScale()
  {
    return this.gaugeModel.isTightScale();
  }
  
  public final void setTightScale(boolean paramBoolean)
  {
    this.gaugeModel.setTightScale(paramBoolean);
  }
  
  public final BooleanProperty tightScaleProperty()
  {
    return this.gaugeModel.tightScaleProperty();
  }
  
  public final double getTightScaleOffset()
  {
    return this.gaugeModel.getTightScaleOffset();
  }
  
  public final boolean isLargeNumberScale()
  {
    return this.gaugeModel.isLargeNumberScale();
  }
  
  public final void setLargeNumberScale(boolean paramBoolean)
  {
    this.gaugeModel.setLargeNumberScale(paramBoolean);
  }
  
  public final BooleanProperty largeNumberScaleProperty()
  {
    return this.gaugeModel.largeNumberScaleProperty();
  }
  
  public final boolean isLastLabelVisible()
  {
    return this.gaugeModel.isLastLabelVisible();
  }
  
  public final void setLastLabelVisible(boolean paramBoolean)
  {
    this.gaugeModel.setLastLabelVisible(paramBoolean);
  }
  
  public final BooleanProperty lastLabelVisibleProperty()
  {
    return this.gaugeModel.lastLabelVisibleProperty();
  }
  
  public final ObservableList<Section> getSections()
  {
    return this.gaugeModel.getSections();
  }
  
  public final void addSection(Section paramSection)
  {
    this.gaugeModel.addSection(paramSection);
  }
  
  public final void addAllSections(Section... paramVarArgs)
  {
    for (Section localSection : paramVarArgs) {
      this.gaugeModel.addSection(localSection);
    }
  }
  
  public final void addAllSections(List<Section> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      this.gaugeModel.addSection(localSection);
    }
  }
  
  public final void removeSection(Section paramSection)
  {
    this.gaugeModel.removeSection(paramSection);
  }
  
  public final void resetSections()
  {
    this.gaugeModel.resetSections();
  }
  
  public final boolean isSectionsVisible()
  {
    return this.styleModel.isSectionsVisible();
  }
  
  public final void setSectionsVisible(boolean paramBoolean)
  {
    this.styleModel.setSectionsVisible(paramBoolean);
  }
  
  public final BooleanProperty sectionsVisibleProperty()
  {
    return this.styleModel.sectionsVisibleProperty();
  }
  
  public final boolean isExpandedSections()
  {
    return this.styleModel.isExpandedSections();
  }
  
  public final void setExpandedSections(boolean paramBoolean)
  {
    this.styleModel.setExpandedSections(paramBoolean);
  }
  
  public final BooleanProperty expandedSectionsProperty()
  {
    return this.styleModel.expandedSectionsProperty();
  }
  
  public final boolean isSectionsHighlighting()
  {
    return this.styleModel.isSectionsHighlighting();
  }
  
  public final void setSectionsHighlighting(boolean paramBoolean)
  {
    this.styleModel.setSectionsHighlighting(paramBoolean);
  }
  
  public final BooleanProperty sectionsHighlightingProperty()
  {
    return this.styleModel.sectionsHighlightingProperty();
  }
  
  public final boolean isShowSectionTickmarksOnly()
  {
    return this.styleModel.isShowSectionTickmarksOnly();
  }
  
  public final void setShowSectionTickmarksOnly(boolean paramBoolean)
  {
    this.styleModel.setShowSectionTickmarksOnly(paramBoolean);
  }
  
  public final BooleanProperty showSectionTickmarksOnlyProperty()
  {
    return this.styleModel.showSectionTickmarksOnlyProperty();
  }
  
  public final ObservableList<Section> getAreas()
  {
    return this.gaugeModel.getAreas();
  }
  
  public final void addArea(Section paramSection)
  {
    this.gaugeModel.addArea(paramSection);
  }
  
  public final void addAllAreas(Section... paramVarArgs)
  {
    for (Section localSection : paramVarArgs) {
      this.gaugeModel.addArea(localSection);
    }
  }
  
  public final void addAllAreas(List<Section> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      this.gaugeModel.addArea(localSection);
    }
  }
  
  public final void removeArea(Section paramSection)
  {
    this.gaugeModel.removeArea(paramSection);
  }
  
  public final void resetAreas()
  {
    this.gaugeModel.resetAreas();
  }
  
  public final boolean isAreasVisible()
  {
    return this.styleModel.isAreasVisible();
  }
  
  public final void setAreasVisible(boolean paramBoolean)
  {
    this.styleModel.setAreasVisible(paramBoolean);
  }
  
  public final BooleanProperty areasVisibleProperty()
  {
    return this.styleModel.areasVisibleProperty();
  }
  
  public final boolean isAreasHighlighting()
  {
    return this.styleModel.isAreasHighlighting();
  }
  
  public final void setAreasHighlighting(boolean paramBoolean)
  {
    this.styleModel.setAreasHighlighting(paramBoolean);
  }
  
  public final BooleanProperty areasHighlightingProperty()
  {
    return this.styleModel.areasHighlightingProperty();
  }
  
  public final ObservableList<Section> getTickMarkSections()
  {
    return this.gaugeModel.getTickMarkSections();
  }
  
  public final void addTickMarkSection(Section paramSection)
  {
    this.gaugeModel.addTickMarkSection(paramSection);
  }
  
  public final void removeTickMarkSection(Section paramSection)
  {
    this.gaugeModel.removeTickMarkSection(paramSection);
  }
  
  public final void resetTickMarkSections()
  {
    this.gaugeModel.resetTickMarkSections();
  }
  
  public final ObservableList<Marker> getMarkers()
  {
    return this.gaugeModel.getMarkers();
  }
  
  public final void addMarker(Marker paramMarker)
  {
    this.gaugeModel.addMarker(paramMarker);
  }
  
  public final void removeMarker(Marker paramMarker)
  {
    this.gaugeModel.removeMarker(paramMarker);
  }
  
  public final void resetMarkers()
  {
    this.gaugeModel.resetMarkers();
  }
  
  public final boolean isMarkersVisible()
  {
    return this.styleModel.isMarkersVisible();
  }
  
  public final void setMarkersVisible(boolean paramBoolean)
  {
    this.styleModel.setMarkersVisible(paramBoolean);
  }
  
  public final BooleanProperty markersVisibleProperty()
  {
    return this.styleModel.markersVisibleProperty();
  }
  
  public final boolean isEndlessMode()
  {
    return this.gaugeModel.isEndlessMode();
  }
  
  public final void setEndlessMode(boolean paramBoolean)
  {
    if (getRadialRange() == RadialRange.RADIAL_360) {
      this.gaugeModel.setEndlessMode(paramBoolean);
    }
  }
  
  public final Color getTextureColor()
  {
    return this.styleModel.getTextureColor();
  }
  
  public final String getTextureColorString()
  {
    return this.styleModel.getTextureColorString();
  }
  
  public final void setTextureColor(Color paramColor)
  {
    this.styleModel.setTextureColor(paramColor);
  }
  
  public final ObjectProperty<Color> textureColorProperty()
  {
    return this.styleModel.textureColorProperty();
  }
  
  public final Color getSimpleGradientBaseColor()
  {
    return this.styleModel.getSimpleGradientBaseColor();
  }
  
  public final String getSimpleGradientBaseColorString()
  {
    return this.styleModel.getSimpleGradientBaseColorString();
  }
  
  public final void setSimpleGradientBaseColor(Color paramColor)
  {
    this.styleModel.setSimpleGradientBaseColor(paramColor);
  }
  
  public final ObjectProperty<Color> simpleGradientBaseColorProperty()
  {
    return this.styleModel.simpleGradientBaseColorProperty();
  }
  
  public final boolean isTitleVisible()
  {
    return this.styleModel.isTitleVisible();
  }
  
  public final void setTitleVisible(boolean paramBoolean)
  {
    this.styleModel.setTitleVisible(paramBoolean);
  }
  
  public final BooleanProperty titleVisibleProperty()
  {
    return this.styleModel.titleVisibleProperty();
  }
  
  public final boolean isUnitVisible()
  {
    return this.styleModel.isUnitVisible();
  }
  
  public final void setUnitVisible(boolean paramBoolean)
  {
    this.styleModel.setUnitVisible(paramBoolean);
  }
  
  public final BooleanProperty unitVisibleProperty()
  {
    return this.styleModel.unitVisibleProperty();
  }
  
  public final Trend getTrend()
  {
    return this.gaugeModel.getTrend();
  }
  
  public final void setTrend(Trend paramTrend)
  {
    this.gaugeModel.setTrend(paramTrend);
  }
  
  public final ObjectProperty<Trend> trendProperty()
  {
    return this.gaugeModel.trendProperty();
  }
  
  public final boolean isTrendVisible()
  {
    return this.styleModel.isTrendVisible();
  }
  
  public final void setTrendVisible(boolean paramBoolean)
  {
    this.styleModel.setTrendVisible(paramBoolean);
  }
  
  public final BooleanProperty trendVisibleProperty()
  {
    return this.styleModel.trendVisibleProperty();
  }
  
  public final Color getTrendUpColor()
  {
    return this.styleModel.getTrendUpColor();
  }
  
  public final void setTrendUpColor(Color paramColor)
  {
    this.styleModel.setTrendUpColor(paramColor);
  }
  
  public final ObjectProperty<Color> trendUpColorProperty()
  {
    return this.styleModel.trendUpColorProperty();
  }
  
  public final Color getTrendRisingColor()
  {
    return this.styleModel.getTrendRisingColor();
  }
  
  public final void setTrendRisingColor(Color paramColor)
  {
    this.styleModel.setTrendRisingColor(paramColor);
  }
  
  public final ObjectProperty<Color> trendRisingColorProperty()
  {
    return this.styleModel.trendRisingColorProperty();
  }
  
  public final Color getTrendSteadyColor()
  {
    return this.styleModel.getTrendSteadyColor();
  }
  
  public final void setTrendSteadyColor(Color paramColor)
  {
    this.styleModel.setTrendSteadyColor(paramColor);
  }
  
  public final ObjectProperty<Color> trendSteadyColorProperty()
  {
    return this.styleModel.trendSteadyColorProperty();
  }
  
  public final Color getTrendFallingColor()
  {
    return this.styleModel.getTrendFallingColor();
  }
  
  public final void setTrendFallingColor(Color paramColor)
  {
    this.styleModel.setTrendFallingColor(paramColor);
  }
  
  public final ObjectProperty<Color> trendFallingColorProperty()
  {
    return this.styleModel.trendFallingColorProperty();
  }
  
  public final Color getTrendDownColor()
  {
    return this.styleModel.getTrendDownColor();
  }
  
  public final void setTrendDownColor(Color paramColor)
  {
    this.styleModel.setTrendDownColor(paramColor);
  }
  
  public final ObjectProperty<Color> trendDownColorProperty()
  {
    return this.styleModel.trendDownColorProperty();
  }
  
  public static enum Trend
  {
    UP,  RISING,  STEADY,  FALLING,  DOWN,  UNKNOWN;
    
    private Trend() {}
  }
  
  public static enum TicklabelOrientation
  {
    NORMAL,  HORIZONTAL,  TANGENT;
    
    private TicklabelOrientation() {}
  }
  
  public static enum TickmarkType
  {
    LINE,  TRIANGLE;
    
    private TickmarkType() {}
  }
  
  public static enum ThresholdColor
  {
    RED("-fx-red;", Color.rgb(213, 0, 0)),  GREEN("-fx-green;", Color.rgb(0, 148, 0)),  BLUE("-fx-blue;", Color.rgb(0, 120, 220)),  ORANGE("-fx-orange;", Color.rgb(248, 142, 0)),  YELLOW("-fx-yellow;", Color.rgb(210, 204, 0)),  CYAN("-fx-cyan;", Color.rgb(0, 159, 215)),  MAGENTA("-fx-magenta;", Color.rgb(223, 42, 125)),  LILA("-fx-lila", Color.rgb(71, 0, 255)),  WHITE("-fx-white;", Color.rgb(245, 245, 245)),  GRAY("-fx-gray;", Color.rgb(102, 102, 102)),  BLACK("-fx-black;", Color.rgb(15, 15, 15)),  RAITH("-fx-raith;", Color.rgb(65, 143, 193)),  GREEN_LCD("-fx-green-lcd;", Color.rgb(24, 220, 183)),  JUG_GREEN("-fx-jug-green;", Color.rgb(90, 183, 0)),  CUSTOM("-fx-custom;", Color.rgb(0, 195, 97));
    
    public final String CSS;
    public final Color COLOR;
    
    private ThresholdColor(String paramString, Color paramColor)
    {
      this.CSS = ("-fx-threshold: " + paramString);
      this.COLOR = paramColor;
    }
  }
  
  public static enum RadialRange
  {
    RADIAL_360(360.0D, 0.0D, 0.0D, new Rectangle(0.4D, 0.56D, 0.4D, 0.12D), 0.0D, new Point2D(0.6D, 0.4D), new Point2D(0.3D, 0.4D), 1.0D, 0.38D),  RADIAL_300(300.0D, -150.0D, 240.0D, new Rectangle(0.4D, 0.56D, 0.4D, 0.12D), 150.0D, new Point2D(0.6D, 0.4D), new Point2D(0.3D, 0.4D), 1.0D, 0.38D),  RADIAL_280(280.0D, -140.0D, 280.0D, new Rectangle(0.4D, 0.56D, 0.4D, 0.12D), 150.0D, new Point2D(0.6D, 0.4D), new Point2D(0.3D, 0.4D), 1.0D, 0.38D),  RADIAL_270(270.0D, -180.0D, 270.0D, new Rectangle(0.4D, 0.56D, 0.4D, 0.12D), 180.0D, new Point2D(0.6D, 0.4D), new Point2D(0.3D, 0.4D), 1.0D, 0.38D),  RADIAL_180(180.0D, -90.0D, 180.0D, new Rectangle(0.55D, 0.56D, 0.55D, 0.12D), 90.0D, new Point2D(0.6D, 0.4D), new Point2D(0.3D, 0.4D), 1.0D, 0.38D),  RADIAL_180N(180.0D, -90.0D, 180.0D, new Rectangle(0.55D, 0.56D, 0.55D, 0.12D), 90.0D, new Point2D(0.6D, 0.35D), new Point2D(0.3D, 0.35D), 1.0D, 0.38D),  RADIAL_180S(180.0D, -90.0D, 180.0D, new Rectangle(0.55D, 0.56D, 0.55D, 0.12D), 0.0D, new Point2D(0.6D, 0.2D), new Point2D(0.3D, 0.2D), -1.0D, 0.38D),  RADIAL_90(90.0D, -90.0D, 180.0D, new Rectangle(0.55D, 0.56D, 0.55D, 0.12D), 91.0D, new Point2D(0.6D, 0.4D), new Point2D(0.3D, 0.4D), 1.0D, 0.38D),  RADIAL_90N(90.0D, 315.0D, 225.0D, new Rectangle(0.55D, 0.52D, 0.55D, 0.12D), 45.0D, new Point2D(0.6D, 0.4D), new Point2D(0.3D, 0.4D), 1.0D, 0.5D),  RADIAL_90W(90.0D, 225.0D, 45.0D, new Rectangle(0.2D, 0.58D, 0.45D, 0.12D), 135.0D, new Point2D(0.12D, 0.35D), new Point2D(0.12D, 0.55D), 1.0D, 0.5D),  RADIAL_90S(90.0D, -135.0D, 45.0D, new Rectangle(0.55D, 0.36D, 0.55D, 0.12D), 225.0D, new Point2D(0.6D, 0.5D), new Point2D(0.3D, 0.5D), -1.0D, 0.5D),  RADIAL_90E(90.0D, 135.0D, 225.0D, new Rectangle(0.2D, 0.58D, 0.45D, 0.12D), -315.0D, new Point2D(0.78D, 0.35D), new Point2D(0.78D, 0.55D), -1.0D, 0.5D);
    
    public final double ANGLE_RANGE;
    public final double ROTATION_OFFSET;
    public final double SECTIONS_OFFSET;
    public final Rectangle LCD_FACTORS;
    public final double TICKLABEL_ORIENATION_CHANGE_ANGLE;
    public final Point2D LED_POSITION;
    public final Point2D USER_LED_POSITION;
    public final double ANGLE_STEP_SIGN;
    public final double RADIUS_FACTOR;
    
    private RadialRange(double paramDouble1, double paramDouble2, double paramDouble3, Rectangle paramRectangle, double paramDouble4, Point2D paramPoint2D1, Point2D paramPoint2D2, double paramDouble5, double paramDouble6)
    {
      this.ANGLE_RANGE = paramDouble1;
      this.ROTATION_OFFSET = paramDouble2;
      this.SECTIONS_OFFSET = paramDouble3;
      this.LCD_FACTORS = paramRectangle;
      this.TICKLABEL_ORIENATION_CHANGE_ANGLE = paramDouble4;
      this.LED_POSITION = paramPoint2D1;
      this.USER_LED_POSITION = paramPoint2D2;
      this.ANGLE_STEP_SIGN = paramDouble5;
      this.RADIUS_FACTOR = paramDouble6;
    }
  }
  
  public static enum PointerType
  {
    TYPE1,  TYPE2,  TYPE3,  TYPE4,  TYPE5,  TYPE6,  TYPE7,  TYPE8,  TYPE9,  TYPE10,  TYPE11,  TYPE12,  TYPE13,  TYPE14,  TYPE15,  TYPE16;
    
    private PointerType() {}
  }
  
  public static enum NumberSystem
  {
    DECIMAL("dec"),  HEXADECIMAL("hex"),  OCTAL("oct");
    
    private String text;
    
    private NumberSystem(String paramString)
    {
      this.text = paramString;
    }
    
    public String toString()
    {
      return this.text;
    }
  }
  
  public static enum NumberFormat
  {
    AUTO("0"),  STANDARD("0"),  FRACTIONAL("0.0#"),  SCIENTIFIC("0.##E0"),  PERCENTAGE("##0.0%");
    
    private final DecimalFormat DF;
    
    private NumberFormat(String paramString)
    {
      Locale.setDefault(new Locale("en", "US"));
      this.DF = new DecimalFormat(paramString);
    }
    
    public String format(Number paramNumber)
    {
      return this.DF.format(paramNumber);
    }
  }
  
  public static enum LcdFont
  {
    STANDARD,  LCD,  BUS,  PIXEL,  PHONE_LCD;
    
    private LcdFont() {}
  }
  
  public static enum KnobDesign
  {
    STANDARD,  PLAIN,  METAL,  BIG;
    
    private KnobDesign() {}
  }
  
  public static enum KnobColor
  {
    BLACK,  BRASS,  SILVER;
    
    private KnobColor() {}
  }
  
  public static enum FrameDesign
  {
    BLACK_METAL("frame-design-blackmetal"),  SHINY_METAL("frame-design-shinymetal"),  CHROME("frame-design-chrome"),  METAL("frame-design-metal"),  GLOSSY_METAL("frame-design-glossymetal"),  DARK_GLOSSY("frame-design-darkglossy"),  BRASS("frame-design-brass"),  STEEL("frame-design-steel"),  GOLD("frame-design-gold"),  ANTHRACITE("frame-design-anthracite"),  TILTED_GRAY("frame-design-tiltedgray"),  TILTED_BLACK("frame-design-tiltedblack"),  CUSTOM("frame-design-custom");
    
    public final String CSS;
    
    private FrameDesign(String paramString)
    {
      this.CSS = paramString;
    }
  }
  
  public static enum BackgroundDesign
  {
    DARK_GRAY("background-design-darkgray"),  SATIN_GRAY("background-design-satingray"),  LIGHT_GRAY("background-design-lightgray"),  WHITE("background-design-white"),  BLACK("background-design-black"),  BEIGE("background-design-beige"),  BROWN("background-design-brown"),  RED("background-design-red"),  GREEN("background-design-green"),  BLUE("background-design-blue"),  ANTHRACITE("background-design-anthracite"),  MUD("background-design-mud"),  CARBON("background-design-carbon"),  STAINLESS("background-design-stainless"),  BRUSHED_METAL("background-design-brushedmetal"),  PUNCHED_SHEET("background-design-punchedsheet"),  NOISY_PLASTIC("backgroundd-design-noisyplastic"),  SIMPLE_GRADIENT("background-design-simplegradient"),  TRANSPARENT("background-design-transparent"),  CUSTOM("background-design-custom");
    
    public final String CSS_BACKGROUND;
    public final String CSS_TEXT;
    
    private BackgroundDesign(String paramString)
    {
      this.CSS_BACKGROUND = paramString;
      this.CSS_TEXT = (paramString + "-text");
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Gauge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */