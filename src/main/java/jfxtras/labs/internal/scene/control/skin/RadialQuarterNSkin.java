package jfxtras.labs.internal.scene.control.skin;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javafx.animation.Animation.Status;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.RadialQuarterNBehavior;
import jfxtras.labs.scene.control.gauge.ColorDef;
import jfxtras.labs.scene.control.gauge.Gauge.BackgroundDesign;
import jfxtras.labs.scene.control.gauge.Gauge.PointerType;
import jfxtras.labs.scene.control.gauge.Gauge.RadialRange;
import jfxtras.labs.scene.control.gauge.Gauge.ThresholdColor;
import jfxtras.labs.scene.control.gauge.LcdDesign;
import jfxtras.labs.scene.control.gauge.RadialQuarterN;
import jfxtras.labs.scene.control.gauge.Section;

public class RadialQuarterNSkin
  extends GaugeSkinBase<RadialQuarterN, RadialQuarterNBehavior>
{
  private static final Rectangle MIN_SIZE = new Rectangle(25.0D, 25.0D);
  private static final Rectangle PREF_SIZE = new Rectangle(200.0D, 200.0D);
  private static final Rectangle MAX_SIZE = new Rectangle(1024.0D, 1024.0D);
  private RadialQuarterN control;
  private Rectangle gaugeBounds;
  private Point2D framelessOffset;
  private Group frame;
  private Group background;
  private Group trend;
  private Group sections;
  private Group areas;
  private Group markers;
  private Group titleAndUnit;
  private Group tickmarks;
  private Group glowOff;
  private Group glowOn;
  private ArrayList<Color> glowColors;
  private Group lcd;
  private Group lcdContent;
  private Font lcdUnitFont;
  private Font lcdValueFont;
  private Text lcdValueString;
  private Text lcdUnitString;
  private Group lcdThresholdIndicator;
  private Group knobs;
  private Group knobsShadow;
  private Group threshold;
  private Group minMeasured;
  private Group maxMeasured;
  private Group pointer;
  private Group pointerShadow;
  private Group ledOff;
  private Group ledOn;
  private Group userLedOff;
  private Group userLedOn;
  private Group foreground;
  private Point2D center;
  private Timeline rotationAngleTimeline;
  private DoubleProperty gaugeValue;
  private DoubleProperty currentValue;
  private DoubleProperty lcdValue;
  private DoubleProperty currentLcdValue;
  private FadeTransition glowPulse;
  private Rotate pointerRotation;
  private AnimationTimer ledTimer;
  private boolean ledOnVisible;
  private long lastLedTimerCall;
  private AnimationTimer userLedTimer;
  private boolean userLedOnVisible;
  private long lastUserLedTimerCall;
  private boolean isDirty;
  private boolean initialized;
  
  public RadialQuarterNSkin(RadialQuarterN paramRadialQuarterN)
  {
    super(paramRadialQuarterN, new RadialQuarterNBehavior(paramRadialQuarterN));
    this.control = paramRadialQuarterN;
    this.gaugeBounds = new Rectangle(200.0D, 200.0D);
    this.framelessOffset = new Point2D(0.0D, 0.0D);
    this.center = new Point2D(0.0D, 0.0D);
    this.frame = new Group();
    this.background = new Group();
    this.trend = new Group();
    this.sections = new Group();
    this.areas = new Group();
    this.markers = new Group();
    this.titleAndUnit = new Group();
    this.tickmarks = new Group();
    this.glowOff = new Group();
    this.glowOn = new Group();
    this.glowColors = new ArrayList(4);
    this.lcd = new Group();
    this.lcdContent = new Group();
    this.lcdValueString = new Text();
    this.lcdUnitString = new Text();
    this.lcdThresholdIndicator = new Group();
    this.knobs = new Group();
    this.knobsShadow = new Group(new Node[] { this.knobs });
    this.threshold = new Group();
    this.minMeasured = new Group();
    this.maxMeasured = new Group();
    this.pointer = new Group();
    this.pointerShadow = new Group(new Node[] { this.pointer });
    this.ledOff = new Group();
    this.ledOn = new Group();
    this.userLedOff = new Group();
    this.userLedOn = new Group();
    this.foreground = new Group();
    this.rotationAngleTimeline = new Timeline();
    this.gaugeValue = new SimpleDoubleProperty(0.0D);
    this.currentValue = new SimpleDoubleProperty(0.0D);
    this.lcdValue = new SimpleDoubleProperty(0.0D);
    this.currentLcdValue = new SimpleDoubleProperty(0.0D);
    this.glowPulse = new FadeTransition(Duration.millis(800.0D), this.glowOn);
    this.pointerRotation = new Rotate();
    this.isDirty = false;
    this.ledTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > RadialQuarterNSkin.this.lastLedTimerCall + RadialQuarterNSkin.this.getBlinkInterval())
        {
          RadialQuarterNSkin.access$180(RadialQuarterNSkin.this, 1);
          if (RadialQuarterNSkin.this.ledOnVisible) {
            RadialQuarterNSkin.this.ledOn.setOpacity(1.0D);
          } else {
            RadialQuarterNSkin.this.ledOn.setOpacity(0.0D);
          }
          RadialQuarterNSkin.this.lastLedTimerCall = paramAnonymousLong;
        }
      }
    };
    this.lastLedTimerCall = 0L;
    this.ledOnVisible = false;
    this.userLedTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > RadialQuarterNSkin.this.lastUserLedTimerCall + RadialQuarterNSkin.this.getBlinkInterval())
        {
          RadialQuarterNSkin.access$480(RadialQuarterNSkin.this, 1);
          if (RadialQuarterNSkin.this.userLedOnVisible) {
            RadialQuarterNSkin.this.userLedOn.setOpacity(1.0D);
          } else {
            RadialQuarterNSkin.this.userLedOn.setOpacity(0.0D);
          }
          RadialQuarterNSkin.this.lastUserLedTimerCall = paramAnonymousLong;
        }
      }
    };
    this.lastUserLedTimerCall = 0L;
    this.userLedOnVisible = false;
    this.initialized = false;
    init();
  }
  
  private void init()
  {
    if ((this.control.getPrefWidth() < 0.0D) || (this.control.getPrefHeight() < 0.0D)) {
      this.control.setPrefSize(PREF_SIZE.getWidth(), PREF_SIZE.getHeight());
    }
    this.control.recalcRange();
    this.glowColors.clear();
    Color localColor = this.control.getGlowColor();
    this.glowColors.add(Color.hsb(localColor.getHue(), 0.46D, 0.96D, 0.0D));
    this.glowColors.add(Color.hsb(localColor.getHue(), 0.67D, 0.9D, 1.0D));
    this.glowColors.add(Color.hsb(localColor.getHue(), 1.0D, 1.0D, 1.0D));
    this.glowColors.add(Color.hsb(localColor.getHue(), 0.67D, 0.9D, 1.0D));
    this.glowPulse.setFromValue(0.1D);
    this.glowPulse.setToValue(1.0D);
    this.glowPulse.setInterpolator(Interpolator.SPLINE(0.0D, 0.0D, 0.4D, 1.0D));
    this.glowPulse.setInterpolator(Interpolator.EASE_OUT);
    this.glowPulse.setCycleCount(-1);
    this.glowPulse.setAutoReverse(true);
    if ((this.control.isPulsatingGlow()) && (this.control.isGlowVisible()))
    {
      if (!this.glowOn.isVisible()) {
        this.glowOn.setVisible(true);
      }
      if (this.glowOn.getOpacity() < 1.0D) {
        this.glowOn.setOpacity(1.0D);
      }
      this.glowPulse.play();
    }
    else
    {
      this.glowPulse.stop();
      this.glowOn.setOpacity(0.0D);
    }
    if (this.control.isGlowVisible())
    {
      this.glowOff.setVisible(true);
      if (this.control.isGlowOn()) {
        this.glowOn.setOpacity(1.0D);
      } else {
        this.glowOn.setOpacity(0.0D);
      }
    }
    else
    {
      this.glowOff.setVisible(false);
      this.glowOn.setOpacity(0.0D);
    }
    this.ledOn.setOpacity(0.0D);
    if (this.control.isUserLedOn()) {
      this.userLedOn.setOpacity(1.0D);
    } else {
      this.userLedOn.setOpacity(0.0D);
    }
    if (this.control.isUserLedBlinking()) {
      this.userLedTimer.start();
    }
    if (!this.control.getSections().isEmpty()) {
      updateSections();
    }
    if (!this.control.getAreas().isEmpty()) {
      updateAreas();
    }
    if (this.gaugeValue.get() < this.control.getMinValue()) {
      this.gaugeValue.set(this.control.getMinValue());
    } else if (this.gaugeValue.get() > this.control.getMaxValue()) {
      this.gaugeValue.set(this.control.getMaxValue());
    }
    this.control.recalcRange();
    this.control.setMinMeasuredValue(this.control.getMaxValue());
    this.control.setMaxMeasuredValue(this.control.getMinValue());
    addBindings();
    addListeners();
    calcGaugeBounds();
    this.lcdUnitString.getStyleClass().clear();
    this.lcdUnitString.getStyleClass().add("lcd");
    this.lcdUnitString.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.lcdUnitString.getStyleClass().add("lcd-text");
    this.lcdValueString.getStyleClass().clear();
    this.lcdValueString.getStyleClass().add("lcd");
    this.lcdValueString.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.lcdValueString.getStyleClass().add("lcd-text");
    this.initialized = true;
    repaint();
  }
  
  private void addBindings()
  {
    if (this.frame.visibleProperty().isBound()) {
      this.frame.visibleProperty().unbind();
    }
    this.frame.visibleProperty().bind(this.control.frameVisibleProperty());
    if (this.background.visibleProperty().isBound()) {
      this.background.visibleProperty().unbind();
    }
    this.background.visibleProperty().bind(this.control.backgroundVisibleProperty());
    if (this.sections.visibleProperty().isBound()) {
      this.sections.visibleProperty().unbind();
    }
    this.sections.visibleProperty().bind(this.control.sectionsVisibleProperty());
    if (this.areas.visibleProperty().isBound()) {
      this.areas.visibleProperty().unbind();
    }
    this.areas.visibleProperty().bind(this.control.areasVisibleProperty());
    if (this.markers.visibleProperty().isBound()) {
      this.markers.visibleProperty().unbind();
    }
    this.markers.visibleProperty().bind(this.control.markersVisibleProperty());
    if (this.ledOff.visibleProperty().isBound()) {
      this.ledOff.visibleProperty().unbind();
    }
    this.ledOff.visibleProperty().bind(this.control.ledVisibleProperty());
    if (this.ledOn.visibleProperty().isBound()) {
      this.ledOn.visibleProperty().unbind();
    }
    this.ledOn.visibleProperty().bind(this.control.ledVisibleProperty());
    if (this.userLedOff.visibleProperty().isBound()) {
      this.userLedOff.visibleProperty().unbind();
    }
    this.userLedOff.visibleProperty().bind(this.control.userLedVisibleProperty());
    if (this.userLedOn.visibleProperty().isBound()) {
      this.userLedOn.visibleProperty().unbind();
    }
    this.userLedOn.visibleProperty().bind(this.control.userLedVisibleProperty());
    if (this.threshold.visibleProperty().isBound()) {
      this.threshold.visibleProperty().unbind();
    }
    this.threshold.visibleProperty().bind(this.control.thresholdVisibleProperty());
    if (this.minMeasured.visibleProperty().isBound()) {
      this.minMeasured.visibleProperty().unbind();
    }
    this.minMeasured.visibleProperty().bind(this.control.minMeasuredValueVisibleProperty());
    if (this.maxMeasured.visibleProperty().isBound()) {
      this.maxMeasured.visibleProperty().unbind();
    }
    this.maxMeasured.visibleProperty().bind(this.control.maxMeasuredValueVisibleProperty());
    if (this.lcdValue.isBound()) {
      this.lcdValue.unbind();
    }
    this.lcdValue.bind(this.control.valueProperty());
    if (this.foreground.visibleProperty().isBound()) {
      this.foreground.visibleProperty().unbind();
    }
    this.foreground.visibleProperty().bind(this.control.foregroundVisibleProperty());
    if (this.trend.visibleProperty().isBound()) {
      this.trend.visibleProperty().unbind();
    }
    this.trend.visibleProperty().bind(this.control.trendVisibleProperty());
  }
  
  private void addListeners()
  {
    this.control.getAreas().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        RadialQuarterNSkin.this.updateAreas();
        RadialQuarterNSkin.this.drawCircularAreas(RadialQuarterNSkin.this.control, RadialQuarterNSkin.this.areas, RadialQuarterNSkin.this.gaugeBounds);
      }
    });
    this.control.getSections().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        RadialQuarterNSkin.this.updateSections();
        RadialQuarterNSkin.this.drawCircularSections(RadialQuarterNSkin.this.control, RadialQuarterNSkin.this.sections, RadialQuarterNSkin.this.gaugeBounds);
      }
    });
    this.control.getMarkers().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        RadialQuarterNSkin.this.drawCircularIndicators(RadialQuarterNSkin.this.control, RadialQuarterNSkin.this.markers, RadialQuarterNSkin.this.center, RadialQuarterNSkin.this.gaugeBounds);
      }
    });
    this.control.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        if (RadialQuarterNSkin.this.rotationAngleTimeline.getStatus() != Animation.Status.STOPPED) {
          RadialQuarterNSkin.this.rotationAngleTimeline.stop();
        }
        if ((paramAnonymousNumber2.doubleValue() > paramAnonymousNumber1.doubleValue() - RadialQuarterNSkin.this.control.getRedrawToleranceValue()) && (paramAnonymousNumber2.doubleValue() < paramAnonymousNumber1.doubleValue() + RadialQuarterNSkin.this.control.getRedrawToleranceValue())) {
          return;
        }
        Object localObject1;
        Object localObject2;
        if (RadialQuarterNSkin.this.control.isValueAnimationEnabled())
        {
          localObject1 = new KeyValue(RadialQuarterNSkin.this.gaugeValue, paramAnonymousNumber2, Interpolator.SPLINE(0.5D, 0.4D, 0.4D, 1.0D));
          localObject2 = new KeyFrame(Duration.millis(RadialQuarterNSkin.this.control.getAnimationDuration()), new KeyValue[] { localObject1 });
          RadialQuarterNSkin.this.rotationAngleTimeline = new Timeline();
          RadialQuarterNSkin.this.rotationAngleTimeline.getKeyFrames().add(localObject2);
          RadialQuarterNSkin.this.rotationAngleTimeline.play();
        }
        else
        {
          RadialQuarterNSkin.this.pointerRotation.setPivotX(RadialQuarterNSkin.this.center.getX());
          RadialQuarterNSkin.this.pointerRotation.setPivotY(RadialQuarterNSkin.this.center.getY());
          RadialQuarterNSkin.this.pointerRotation.setAngle((paramAnonymousNumber2.doubleValue() - RadialQuarterNSkin.this.control.getMinValue()) * RadialQuarterNSkin.this.control.getAngleStep());
          RadialQuarterNSkin.this.pointer.getTransforms().add(Transform.rotate(RadialQuarterNSkin.this.control.getRadialRange().ROTATION_OFFSET, RadialQuarterNSkin.this.center.getX(), RadialQuarterNSkin.this.center.getY()));
          RadialQuarterNSkin.this.pointer.getTransforms().add(RadialQuarterNSkin.this.pointerRotation);
        }
        RadialQuarterNSkin.this.checkMarkers(RadialQuarterNSkin.this.control, paramAnonymousNumber1.doubleValue(), paramAnonymousNumber2.doubleValue());
        Iterator localIterator;
        Section localSection;
        Shape localShape;
        if (RadialQuarterNSkin.this.control.isSectionsHighlighting())
        {
          localObject1 = new InnerShadow();
          ((InnerShadow)localObject1).setBlurType(BlurType.GAUSSIAN);
          localObject2 = new DropShadow();
          ((DropShadow)localObject2).setWidth(0.05D * RadialQuarterNSkin.this.gaugeBounds.getWidth());
          ((DropShadow)localObject2).setHeight(0.05D * RadialQuarterNSkin.this.gaugeBounds.getHeight());
          ((DropShadow)localObject2).setBlurType(BlurType.GAUSSIAN);
          localIterator = RadialQuarterNSkin.this.control.getSections().iterator();
          while (localIterator.hasNext())
          {
            localSection = (Section)localIterator.next();
            localShape = localSection.getSectionArea();
            if (localSection.contains(paramAnonymousNumber2.doubleValue()))
            {
              ((InnerShadow)localObject1).setColor(localSection.getColor().darker());
              ((DropShadow)localObject2).setInput((Effect)localObject1);
              ((DropShadow)localObject2).setColor(localSection.getColor().brighter());
              localShape.setEffect((Effect)localObject2);
            }
            else
            {
              localShape.setEffect(null);
            }
          }
        }
        if (RadialQuarterNSkin.this.control.isAreasHighlighting())
        {
          localObject1 = new InnerShadow();
          ((InnerShadow)localObject1).setBlurType(BlurType.GAUSSIAN);
          localObject2 = new DropShadow();
          ((DropShadow)localObject2).setWidth(0.05D * RadialQuarterNSkin.this.gaugeBounds.getWidth());
          ((DropShadow)localObject2).setHeight(0.05D * RadialQuarterNSkin.this.gaugeBounds.getHeight());
          ((DropShadow)localObject2).setBlurType(BlurType.GAUSSIAN);
          localIterator = RadialQuarterNSkin.this.control.getAreas().iterator();
          while (localIterator.hasNext())
          {
            localSection = (Section)localIterator.next();
            localShape = localSection.getFilledArea();
            if (localSection.contains(paramAnonymousNumber2.doubleValue()))
            {
              ((InnerShadow)localObject1).setColor(localSection.getColor().darker());
              ((DropShadow)localObject2).setInput((Effect)localObject1);
              ((DropShadow)localObject2).setColor(localSection.getColor().brighter());
              localShape.setEffect((Effect)localObject2);
            }
            else
            {
              localShape.setEffect(null);
            }
          }
        }
      }
    });
    this.gaugeValue.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        RadialQuarterNSkin.this.pointer.getTransforms().clear();
        RadialQuarterNSkin.this.pointerRotation.setPivotX(RadialQuarterNSkin.this.center.getX());
        RadialQuarterNSkin.this.pointerRotation.setPivotY(RadialQuarterNSkin.this.center.getY());
        RadialQuarterNSkin.this.pointerRotation.setAngle((paramAnonymousNumber2.doubleValue() - RadialQuarterNSkin.this.control.getMinValue()) * RadialQuarterNSkin.this.control.getAngleStep());
        RadialQuarterNSkin.this.pointer.getTransforms().add(Transform.rotate(RadialQuarterNSkin.this.control.getRadialRange().ROTATION_OFFSET, RadialQuarterNSkin.this.center.getX(), RadialQuarterNSkin.this.center.getY()));
        RadialQuarterNSkin.this.pointer.getTransforms().add(RadialQuarterNSkin.this.pointerRotation);
        RadialQuarterNSkin.this.currentValue.set(paramAnonymousNumber2.doubleValue());
        RadialQuarterNSkin.this.currentLcdValue.set(RadialQuarterNSkin.this.control.isLcdValueCoupled() ? RadialQuarterNSkin.this.currentValue.get() : RadialQuarterNSkin.this.control.getLcdValue());
        if (Double.compare(RadialQuarterNSkin.this.currentValue.get(), RadialQuarterNSkin.this.control.getMinMeasuredValue()) < 0) {
          RadialQuarterNSkin.this.control.setMinMeasuredValue(RadialQuarterNSkin.this.currentValue.get());
        } else if (Double.compare(RadialQuarterNSkin.this.currentValue.get(), RadialQuarterNSkin.this.control.getMaxMeasuredValue()) > 0) {
          RadialQuarterNSkin.this.control.setMaxMeasuredValue(RadialQuarterNSkin.this.currentValue.get());
        }
        if (RadialQuarterNSkin.this.control.isThresholdBehaviorInverted()) {
          RadialQuarterNSkin.this.control.setThresholdExceeded(RadialQuarterNSkin.this.currentValue.get() < RadialQuarterNSkin.this.control.getThreshold());
        } else {
          RadialQuarterNSkin.this.control.setThresholdExceeded(RadialQuarterNSkin.this.currentValue.get() > RadialQuarterNSkin.this.control.getThreshold());
        }
        if (!RadialQuarterNSkin.this.control.isThresholdExceeded()) {
          RadialQuarterNSkin.this.ledOn.setOpacity(0.0D);
        }
        if (RadialQuarterNSkin.this.control.isLcdVisible()) {
          RadialQuarterNSkin.this.drawLcdContent();
        }
      }
    });
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if (!"ANIMATION_DURATION".equals(paramString)) {
      if ("RADIAL_RANGE".equals(paramString))
      {
        this.isDirty = true;
      }
      else if ("FRAME_DESIGN".equals(paramString))
      {
        drawCircularFrame(this.control, this.frame, this.gaugeBounds);
      }
      else if ("BACKGROUND_DESIGN".equals(paramString))
      {
        drawCircularBackground(this.control, this.background, this.gaugeBounds);
        drawCircularTickmarks(this.control, this.tickmarks, this.center, this.gaugeBounds);
      }
      else if ("KNOB_DESIGN".equals(paramString))
      {
        drawCircularKnobs(this.control, this.knobs, this.center, this.gaugeBounds);
      }
      else if ("KNOB_COLOR".equals(paramString))
      {
        drawCircularKnobs(this.control, this.knobs, this.center, this.gaugeBounds);
      }
      else if ("POINTER_TYPE".equals(paramString))
      {
        drawPointer();
      }
      else if ("VALUE_COLOR".equals(paramString))
      {
        drawPointer();
      }
      else if ("FOREGROUND_TYPE".equals(paramString))
      {
        drawCircularForeground(this.control, this.foreground, this.gaugeBounds);
      }
      else if ("LCD".equals(paramString))
      {
        drawCircularLcd(this.control, this.lcd, this.gaugeBounds);
        this.lcdUnitString.getStyleClass().clear();
        this.lcdUnitString.getStyleClass().add("lcd");
        this.lcdUnitString.getStyleClass().add(this.control.getLcdDesign().CSS);
        this.lcdUnitString.getStyleClass().add("lcd-text");
        this.lcdValueString.getStyleClass().clear();
        this.lcdValueString.getStyleClass().add("lcd");
        this.lcdValueString.getStyleClass().add(this.control.getLcdDesign().CSS);
        this.lcdValueString.getStyleClass().add("lcd-text");
        drawLcdContent();
      }
      else if ("USER_LED_BLINKING".equals(paramString))
      {
        if ((this.userLedOff.isVisible()) && (this.userLedOn.isVisible())) {
          if (this.control.isUserLedBlinking())
          {
            this.userLedTimer.start();
          }
          else
          {
            this.userLedTimer.stop();
            this.userLedOn.setOpacity(0.0D);
          }
        }
      }
      else if ("LED_BLINKING".equals(paramString))
      {
        if ((this.ledOff.isVisible()) && (this.ledOn.isVisible())) {
          if (this.control.isLedBlinking())
          {
            this.ledTimer.start();
          }
          else
          {
            this.ledTimer.stop();
            this.ledOn.setOpacity(0.0D);
          }
        }
      }
      else if ("GLOW_COLOR".equals(paramString))
      {
        this.glowColors.clear();
        Color localColor = this.control.getGlowColor();
        this.glowColors.add(Color.hsb(localColor.getHue(), 0.46D, 0.96D, 0.0D));
        this.glowColors.add(Color.hsb(localColor.getHue(), 0.67D, 0.9D, 1.0D));
        this.glowColors.add(Color.hsb(localColor.getHue(), 1.0D, 1.0D, 1.0D));
        this.glowColors.add(Color.hsb(localColor.getHue(), 0.67D, 0.9D, 1.0D));
        drawCircularGlowOn(this.control, this.glowOn, this.glowColors, this.gaugeBounds);
      }
      else if ("GLOW_VISIBILITY".equals(paramString))
      {
        this.glowOff.setVisible(this.control.isGlowVisible());
        if (!this.control.isGlowVisible()) {
          this.glowOn.setOpacity(0.0D);
        }
      }
      else if ("GLOW_ON".equals(paramString))
      {
        if ((this.glowOff.isVisible()) && (this.control.isGlowOn()))
        {
          this.glowOn.setOpacity(1.0D);
          this.glowOff.setVisible(true);
        }
        else
        {
          this.glowOff.setVisible(true);
          this.glowOn.setOpacity(0.0D);
        }
      }
      else if ("PULSATING_GLOW".equals(paramString))
      {
        if ((this.control.isPulsatingGlow()) && (this.control.isGlowVisible()))
        {
          if (!this.glowOn.isVisible()) {
            this.glowOn.setVisible(true);
          }
          if (this.glowOn.getOpacity() < 1.0D) {
            this.glowOn.setOpacity(1.0D);
          }
          this.glowPulse.play();
        }
        else
        {
          this.glowPulse.stop();
          this.glowOn.setOpacity(0.0D);
        }
      }
      else if ("TICKMARKS".equals(paramString))
      {
        drawCircularTickmarks(this.control, this.tickmarks, this.center, this.gaugeBounds);
      }
      else if ("MIN_MEASURED_VALUE".equals(paramString))
      {
        this.minMeasured.getTransforms().clear();
        this.minMeasured.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getY()));
        this.minMeasured.getTransforms().add(Transform.rotate(-this.control.getMinValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
        this.minMeasured.getTransforms().add(Transform.rotate(this.control.getMinMeasuredValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
      }
      else if ("MAX_MEASURED_VALUE".equals(paramString))
      {
        this.maxMeasured.getTransforms().clear();
        this.maxMeasured.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getY()));
        this.maxMeasured.getTransforms().add(Transform.rotate(-this.control.getMinValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
        this.maxMeasured.getTransforms().add(Transform.rotate(this.control.getMaxMeasuredValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
      }
      else if ("TREND".equals(paramString))
      {
        drawCircularTrend(this.control, this.trend, this.gaugeBounds);
      }
      else if ("GAUGE_MODEL".equals(paramString))
      {
        addBindings();
        repaint();
      }
      else if ("STYLE_MODEL".equals(paramString))
      {
        addBindings();
        repaint();
      }
      else if ("THRESHOLD_EXCEEDED".equals(paramString))
      {
        if (this.control.isThresholdExceeded()) {
          this.ledTimer.start();
        } else {
          this.ledTimer.stop();
        }
      }
      else if ("PREF_WIDTH".equals(paramString))
      {
        repaint();
      }
      else if ("PREF_HEIGHT".equals(paramString))
      {
        repaint();
      }
      else if ("AREAS".equals(paramString))
      {
        updateAreas();
        drawCircularAreas(this.control, this.areas, this.gaugeBounds);
      }
      else if ("SECTIONS".equals(paramString))
      {
        updateSections();
        drawCircularSections(this.control, this.sections, this.gaugeBounds);
      }
      else if ("MARKERS".equals(paramString))
      {
        drawCircularIndicators(this.control, this.markers, this.center, this.gaugeBounds);
      }
    }
  }
  
  public void repaint()
  {
    this.isDirty = true;
    requestLayout();
  }
  
  public void layoutChildren()
  {
    if (!this.isDirty) {
      return;
    }
    adjustLcdFont();
    if (!this.initialized) {
      init();
    }
    if (this.control.getScene() != null)
    {
      calcGaugeBounds();
      setTranslateX(this.framelessOffset.getX());
      setTranslateY(this.framelessOffset.getY());
      this.center = new Point2D(this.gaugeBounds.getWidth() * 0.5D, this.gaugeBounds.getHeight() * 0.735D);
      drawCircularFrame(this.control, this.frame, this.gaugeBounds);
      drawCircularBackground(this.control, this.background, this.gaugeBounds);
      drawCircularTrend(this.control, this.trend, this.gaugeBounds);
      updateSections();
      drawCircularSections(this.control, this.sections, this.gaugeBounds);
      updateAreas();
      drawCircularAreas(this.control, this.areas, this.gaugeBounds);
      drawTitleAndUnit();
      drawCircularTickmarks(this.control, this.tickmarks, this.center, this.gaugeBounds);
      drawCircularLed(this.control, this.ledOff, this.ledOn, this.gaugeBounds);
      drawCircularUserLed(this.control, this.userLedOff, this.userLedOn, this.gaugeBounds);
      drawThreshold();
      drawCircularGlowOff(this.glowOff, this.gaugeBounds);
      drawCircularGlowOn(this.control, this.glowOn, this.glowColors, this.gaugeBounds);
      drawMinMeasuredIndicator();
      drawMaxMeasuredIndicator();
      drawCircularLcd(this.control, this.lcd, this.gaugeBounds);
      drawLcdContent();
      drawPointer();
      drawCircularKnobs(this.control, this.knobs, this.center, this.gaugeBounds);
      drawCircularForeground(this.control, this.foreground, this.gaugeBounds);
      if ((this.control.isPointerShadowEnabled()) && (!this.control.isPointerGlowEnabled())) {
        addDropShadow(this.control, new Node[] { this.knobs, this.pointerShadow });
      }
      getChildren().setAll(new Node[] { this.frame, this.background, this.trend, this.sections, this.areas, this.ledOff, this.ledOn, this.userLedOff, this.userLedOn, this.titleAndUnit, this.tickmarks, this.threshold, this.glowOff, this.glowOn, this.minMeasured, this.maxMeasured, this.markers, this.lcd, this.lcdContent, this.pointerShadow, this.knobsShadow, this.foreground });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public RadialQuarterN getSkinnable()
  {
    return this.control;
  }
  
  public void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = PREF_SIZE.getWidth();
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = PREF_SIZE.getHeight();
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computeMinWidth(double paramDouble)
  {
    return super.computeMinWidth(Math.max(MIN_SIZE.getWidth(), paramDouble - getInsets().getLeft() - getInsets().getRight()));
  }
  
  protected double computeMinHeight(double paramDouble)
  {
    return super.computeMinHeight(Math.max(MIN_SIZE.getHeight(), paramDouble - getInsets().getTop() - getInsets().getBottom()));
  }
  
  protected double computeMaxWidth(double paramDouble)
  {
    return super.computeMaxWidth(Math.max(MAX_SIZE.getWidth(), paramDouble - getInsets().getLeft() - getInsets().getRight()));
  }
  
  protected double computeMaxHeight(double paramDouble)
  {
    return super.computeMaxHeight(Math.max(MAX_SIZE.getHeight(), paramDouble - getInsets().getTop() - getInsets().getBottom()));
  }
  
  private String formatLcdValue(double paramDouble)
  {
    StringBuilder localStringBuilder = new StringBuilder(16);
    localStringBuilder.append("0");
    int i = this.control.getLcdDecimals();
    if (i > 0) {
      localStringBuilder.append(".");
    }
    for (int j = 0; j < i; j++) {
      localStringBuilder.append("0");
    }
    localStringBuilder.trimToSize();
    DecimalFormat localDecimalFormat = new DecimalFormat(localStringBuilder.toString(), new DecimalFormatSymbols(Locale.US));
    return localDecimalFormat.format(paramDouble);
  }
  
  private void calcGaugeBounds()
  {
    if (this.control.isFrameVisible())
    {
      this.gaugeBounds.setWidth(this.control.getPrefWidth());
      this.gaugeBounds.setHeight(this.control.getPrefHeight());
      this.framelessOffset = new Point2D(0.0D, 0.0D);
    }
    else
    {
      this.gaugeBounds.setWidth(this.control.getPrefWidth() * 1.202247191D);
      this.gaugeBounds.setHeight(this.control.getPrefHeight() * 1.202247191D);
      this.framelessOffset = new Point2D(-this.gaugeBounds.getWidth() * 0.0841121495D, -this.gaugeBounds.getWidth() * 0.0841121495D);
    }
  }
  
  private void updateSections()
  {
    double d1 = this.control.getPrefWidth() * 0.5D;
    double d2 = this.control.isExpandedSections() ? d1 - this.control.getPrefWidth() * 0.12D : d1 - this.control.getPrefWidth() * 0.04D;
    Circle localCircle = new Circle(this.center.getX(), this.center.getY(), d2);
    double d3 = this.control.getMinValue() * this.control.getAngleStep() + this.control.getRadialRange().ROTATION_OFFSET;
    double d4 = -45.0D - this.control.getMinValue() * this.control.getAngleStep();
    Iterator localIterator = this.control.getSections().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      double d5 = localSection.getStart() < this.control.getMinValue() ? this.control.getMinValue() : localSection.getStart();
      double d6 = localSection.getStop() > this.control.getMaxValue() ? this.control.getMaxValue() : localSection.getStop();
      double d7 = d4 + this.control.getRadialRange().SECTIONS_OFFSET - d5 * this.control.getAngleStep() + this.control.getMinValue() * this.control.getAngleStep();
      double d8 = -(d6 - d5) * this.control.getAngleStep();
      Arc localArc = new Arc();
      localArc.setType(ArcType.ROUND);
      localArc.setCenterX(this.center.getX());
      localArc.setCenterY(this.center.getY());
      localArc.setRadiusX(d1);
      localArc.setRadiusY(d1);
      localArc.setStartAngle(d3 + d7);
      localArc.setLength(d8);
      Shape localShape = Shape.subtract(localArc, localCircle);
      localSection.setSectionArea(localShape);
    }
  }
  
  private void updateAreas()
  {
    double d1 = this.control.getPrefWidth() * 0.5D;
    double d2 = this.control.isExpandedSections() ? this.control.getPrefWidth() * 0.12D : this.control.getPrefWidth() * 0.04D;
    double d3 = d1 - d2;
    double d4 = this.control.getMinValue() * this.control.getAngleStep() + this.control.getRadialRange().ROTATION_OFFSET;
    Rectangle localRectangle = new Rectangle(0.0D, this.control.getRadialRange().LCD_FACTORS.getY() * this.control.getPrefHeight(), this.control.getPrefWidth(), this.control.getPrefHeight() / 2.0D);
    double d5 = -45.0D - this.control.getMinValue() * this.control.getAngleStep();
    Iterator localIterator = this.control.getAreas().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      double d6 = localSection.getStart() < this.control.getMinValue() ? this.control.getMinValue() : localSection.getStart();
      double d7 = localSection.getStop() > this.control.getMaxValue() ? this.control.getMaxValue() : localSection.getStop();
      double d8 = d5 + this.control.getRadialRange().SECTIONS_OFFSET - d6 * this.control.getAngleStep() + this.control.getMinValue() * this.control.getAngleStep();
      double d9 = -(d7 - d6) * this.control.getAngleStep();
      Arc localArc = new Arc();
      localArc.setType(ArcType.ROUND);
      localArc.setCenterX(this.center.getX());
      localArc.setCenterY(this.center.getY());
      localArc.setRadiusX(d3);
      localArc.setRadiusY(d3);
      localArc.setStartAngle(d4 + d8);
      localArc.setLength(d9);
      Shape localShape = Shape.subtract(localArc, localRectangle);
      localSection.setFilledArea(localShape);
    }
  }
  
  private void adjustLcdFont()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = d1 * this.control.getRadialRange().LCD_FACTORS.getHeight();
    this.lcdUnitFont = Font.font(this.control.getLcdUnitFont(), FontWeight.NORMAL, 0.4D * d2);
    switch (this.control.getLcdValueFont())
    {
    case LCD: 
      this.lcdValueFont = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/digital.ttf"), 0.75D * d2);
      break;
    case BUS: 
      this.lcdValueFont = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/bus.otf"), 0.6D * d2);
      break;
    case PIXEL: 
      this.lcdValueFont = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/pixel.ttf"), 0.6D * d2);
      break;
    case PHONE_LCD: 
      this.lcdValueFont = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/phonelcd.ttf"), 0.6D * d2);
      break;
    case STANDARD: 
    default: 
      this.lcdValueFont = Font.font("Verdana", FontWeight.NORMAL, 0.6D * d2);
    }
  }
  
  public void drawTitleAndUnit()
  {
    double d = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    this.titleAndUnit.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d, d);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.titleAndUnit.getChildren().add(localRectangle);
    Font localFont1 = Font.font(this.control.getTitleFont(), FontWeight.NORMAL, 0.046728972D * d);
    Text localText1 = new Text();
    localText1.setTextOrigin(VPos.BOTTOM);
    localText1.setFont(localFont1);
    localText1.setText(this.control.getTitle());
    localText1.setX((d - localText1.getLayoutBounds().getWidth()) / 2.0D);
    localText1.setY(0.16D * d + localText1.getLayoutBounds().getHeight());
    localText1.getStyleClass().add(this.control.getBackgroundDesign().CSS_TEXT);
    Font localFont2 = Font.font(this.control.getUnitFont(), FontWeight.NORMAL, 0.046728972D * d);
    Text localText2 = new Text();
    localText2.setTextOrigin(VPos.BOTTOM);
    localText2.setFont(localFont2);
    localText2.setText(this.control.getUnit());
    localText2.setX((d - localText2.getLayoutBounds().getWidth()) / 2.0D);
    localText2.setY(0.365D * d + localText2.getLayoutBounds().getHeight());
    localText2.getStyleClass().add(this.control.getBackgroundDesign().CSS_TEXT);
    this.titleAndUnit.getChildren().addAll(new Node[] { localText1, localText2 });
  }
  
  public void drawThreshold()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = d1;
    double d3 = d1;
    this.threshold.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.threshold.getChildren().add(localRectangle);
    Path localPath = createTriangleShape(0.03D * d2, 0.03D * d3, false);
    localPath.setStrokeType(StrokeType.CENTERED);
    localPath.setStrokeLineCap(StrokeLineCap.ROUND);
    localPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
    localPath.setStrokeWidth(0.002D * d3);
    localPath.getStyleClass().add("root");
    localPath.setStyle(this.control.getThresholdColor().CSS);
    localPath.getStyleClass().add("threshold-gradient");
    localPath.setTranslateX(0.485D * d2);
    localPath.setTranslateY(0.25D * d3);
    this.threshold.getChildren().addAll(new Node[] { localPath });
    this.threshold.getTransforms().clear();
    this.threshold.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getY()));
    this.threshold.getTransforms().add(Transform.rotate(-this.control.getMinValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    this.threshold.getTransforms().add(Transform.rotate(this.control.getThreshold() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
  }
  
  public void drawMinMeasuredIndicator()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = d1;
    double d3 = d1;
    this.minMeasured.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.minMeasured.getChildren().add(localRectangle);
    Path localPath = createTriangleShape(0.03D * d2, 0.035D * d3, true);
    localPath.setFill(Color.color(0.0D, 0.0D, 0.8D));
    localPath.setStroke(null);
    localPath.setTranslateX(0.485D * d2);
    localPath.setTranslateY(0.21D * d3);
    this.minMeasured.getChildren().add(localPath);
    this.minMeasured.getTransforms().clear();
    this.minMeasured.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getY()));
    this.minMeasured.getTransforms().add(Transform.rotate(-this.control.getMinValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    this.minMeasured.getTransforms().add(Transform.rotate(this.control.getMinMeasuredValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
  }
  
  public void drawMaxMeasuredIndicator()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = d1;
    double d3 = d1;
    this.maxMeasured.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.maxMeasured.getChildren().add(localRectangle);
    Path localPath = createTriangleShape(0.03D * d2, 0.035D * d3, true);
    localPath.setFill(Color.color(0.8D, 0.0D, 0.0D));
    localPath.setStroke(null);
    localPath.setTranslateX(0.485D * d2);
    localPath.setTranslateY(0.21D * d3);
    this.maxMeasured.getChildren().add(localPath);
    this.maxMeasured.getTransforms().clear();
    this.maxMeasured.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getY()));
    this.maxMeasured.getTransforms().add(Transform.rotate(-this.control.getMinValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    this.maxMeasured.getTransforms().add(Transform.rotate(this.control.getMaxMeasuredValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
  }
  
  public void drawPointer()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = d1;
    double d3 = d1;
    this.pointer.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.pointer.getChildren().addAll(new Node[] { localRectangle });
    Path localPath1 = new Path();
    Path localPath2 = new Path();
    localPath1.setSmooth(true);
    localPath1.getStyleClass().add("root");
    localPath1.setStyle("-fx-value: " + this.control.getValueColor().CSS);
    switch (this.control.getPointerType())
    {
    case TYPE2: 
      localPath1.getStyleClass().add("pointer2-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.52D * d2, 0.71D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.695D * d3));
      localPath1.getElements().add(new LineTo(0.51D * d2, 0.545D * d3));
      localPath1.getElements().add(new LineTo(0.505D * d2, 0.25D * d3));
      localPath1.getElements().add(new LineTo(0.495D * d2, 0.25D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.545D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.695D * d3));
      localPath1.getElements().add(new LineTo(0.48D * d2, 0.71D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.48D * d2, 0.71D * d3, 0.465D * d2, 0.72D * d3, 0.465D * d2, 0.735D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.465D * d2, 0.75D * d3, 0.48D * d2, 0.765D * d3, 0.5D * d2, 0.765D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.515D * d2, 0.765D * d3, 0.535D * d2, 0.75D * d3, 0.535D * d2, 0.735D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.535D * d2, 0.72D * d3, 0.52D * d2, 0.71D * d3, 0.52D * d2, 0.71D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE3: 
      localPath1.getStyleClass().add("pointer3-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.4953271028037383D * d2, 0.1308411214953271D * d3));
      localPath1.getElements().add(new LineTo(0.5046728971962616D * d2, 0.1308411214953271D * d3));
      localPath1.getElements().add(new LineTo(0.5046728971962616D * d2, 0.5046728971962616D * d3));
      localPath1.getElements().add(new LineTo(0.4953271028037383D * d2, 0.5046728971962616D * d3));
      localPath1.getElements().add(new LineTo(0.4953271028037383D * d2, 0.1308411214953271D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE4: 
      localPath1.getStyleClass().add("pointer4-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.255D * d3));
      localPath1.getElements().add(new LineTo(0.51D * d2, 0.265D * d3));
      localPath1.getElements().add(new LineTo(0.535D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.525D * d2, 0.855D * d3));
      localPath1.getElements().add(new LineTo(0.475D * d2, 0.855D * d3));
      localPath1.getElements().add(new LineTo(0.465D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.265D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.255D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE5: 
      localPath1.getStyleClass().add("pointer5-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.525D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.245D * d3));
      localPath1.getElements().add(new LineTo(0.475D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.735D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE6: 
      localPath1.getStyleClass().add("pointer6-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.475D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.475D * d2, 0.595D * d3));
      localPath1.getElements().add(new LineTo(0.48D * d2, 0.49D * d3));
      localPath1.getElements().add(new LineTo(0.495D * d2, 0.255D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.495D * d2, 0.255D * d3, 0.495D * d2, 0.25D * d3, 0.5D * d2, 0.25D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.505D * d2, 0.25D * d3, 0.505D * d2, 0.255D * d3, 0.505D * d2, 0.255D * d3));
      localPath1.getElements().add(new LineTo(0.52D * d2, 0.49D * d3));
      localPath1.getElements().add(new LineTo(0.525D * d2, 0.595D * d3));
      localPath1.getElements().add(new LineTo(0.525D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.505D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.505D * d2, 0.595D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.505D * d2, 0.595D * d3, 0.505D * d2, 0.495D * d3, 0.5D * d2, 0.495D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.495D * d2, 0.495D * d3, 0.495D * d2, 0.595D * d3, 0.495D * d2, 0.595D * d3));
      localPath1.getElements().add(new LineTo(0.495D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.475D * d2, 0.735D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE7: 
      localPath1.getStyleClass().add("pointer7-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.49D * d2, 0.255D * d3));
      localPath1.getElements().add(new LineTo(0.47D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.525D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.51D * d2, 0.255D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.255D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE8: 
      localPath1.getStyleClass().add("pointer8-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.765D * d3));
      localPath1.getElements().add(new LineTo(0.535D * d2, 0.735D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.535D * d2, 0.735D * d3, 0.505D * d2, 0.65D * d3, 0.5D * d2, 0.255D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.495D * d2, 0.65D * d3, 0.465D * d2, 0.735D * d3, 0.465D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.765D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE9: 
      localPath1.getStyleClass().add("pointer9-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.495D * d2, 0.37D * d3));
      localPath1.getElements().add(new LineTo(0.505D * d2, 0.37D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.66D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.66D * d3));
      localPath1.getElements().add(new LineTo(0.495D * d2, 0.37D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.getElements().add(new MoveTo(0.49D * d2, 0.25D * d3));
      localPath1.getElements().add(new LineTo(0.475D * d2, 0.705D * d3));
      localPath1.getElements().add(new LineTo(0.475D * d2, 0.765D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.475D * d2, 0.765D * d3, 0.475D * d2, 0.855D * d3, 0.475D * d2, 0.855D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.475D * d2, 0.855D * d3, 0.48D * d2, 0.86D * d3, 0.5D * d2, 0.86D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.52D * d2, 0.86D * d3, 0.525D * d2, 0.855D * d3, 0.525D * d2, 0.855D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.525D * d2, 0.855D * d3, 0.525D * d2, 0.765D * d3, 0.525D * d2, 0.765D * d3));
      localPath1.getElements().add(new LineTo(0.525D * d2, 0.705D * d3));
      localPath1.getElements().add(new LineTo(0.51D * d2, 0.25D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.25D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      localPath2.getStyleClass().add("root");
      localPath2.setStyle("-fx-value: " + this.control.getValueColor().CSS);
      localPath2.getStyleClass().add("pointer9-box");
      localPath2.setFillRule(FillRule.EVEN_ODD);
      localPath2.getElements().add(new MoveTo(0.495D * d2, 0.355D * d3));
      localPath2.getElements().add(new LineTo(0.505D * d2, 0.355D * d3));
      localPath2.getElements().add(new LineTo(0.505D * d2, 0.255D * d3));
      localPath2.getElements().add(new LineTo(0.495D * d2, 0.255D * d3));
      localPath2.getElements().add(new LineTo(0.495D * d2, 0.355D * d3));
      localPath2.getElements().add(new ClosePath());
      localPath2.setStroke(null);
      break;
    case TYPE10: 
      localPath1.getStyleClass().add("pointer10-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.25D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.5D * d2, 0.25D * d3, 0.435D * d2, 0.715D * d3, 0.435D * d2, 0.725D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.435D * d2, 0.765D * d3, 0.465D * d2, 0.795D * d3, 0.5D * d2, 0.795D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.535D * d2, 0.795D * d3, 0.565D * d2, 0.765D * d3, 0.565D * d2, 0.725D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.565D * d2, 0.715D * d3, 0.5D * d2, 0.25D * d3, 0.5D * d2, 0.25D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE11: 
      localPath1.getStyleClass().add("pointer11-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.25D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.735D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.485D * d2, 0.735D * d3, 0.485D * d2, 0.81D * d3, 0.5D * d2, 0.81D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.515D * d2, 0.81D * d3, 0.515D * d2, 0.735D * d3, 0.515D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.25D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE12: 
      localPath1.getStyleClass().add("pointer12-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.25D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.75D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.735D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.25D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE13: 
      localPath1.getStyleClass().add("pointer13-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.485D * d2, 0.275D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.245D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.275D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.745D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.745D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.275D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE14: 
      localPath1.getStyleClass().add("pointer14-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.485D * d2, 0.275D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.245D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.275D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.745D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.745D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.275D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE15: 
      localPath1.getStyleClass().add("pointer15-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.485D * d2, 0.745D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.37D * d3));
      localPath1.getElements().add(new LineTo(0.46D * d2, 0.37D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.245D * d3));
      localPath1.getElements().add(new LineTo(0.535D * d2, 0.37D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.37D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.745D * d3));
      localPath1.getElements().add(new LineTo(0.485D * d2, 0.745D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE16: 
      localPath1.getStyleClass().add("pointer16-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.86D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.52D * d2, 0.86D * d3, 0.54D * d2, 0.845D * d3, 0.54D * d2, 0.845D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.53D * d2, 0.835D * d3, 0.51D * d2, 0.765D * d3, 0.51D * d2, 0.765D * d3));
      localPath1.getElements().add(new LineTo(0.51D * d2, 0.705D * d3));
      localPath1.getElements().add(new LineTo(0.51D * d2, 0.295D * d3));
      localPath1.getElements().add(new LineTo(0.505D * d2, 0.29D * d3));
      localPath1.getElements().add(new LineTo(0.505D * d2, 0.24D * d3));
      localPath1.getElements().add(new LineTo(0.495D * d2, 0.24D * d3));
      localPath1.getElements().add(new LineTo(0.495D * d2, 0.29D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.295D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.705D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.765D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.49D * d2, 0.765D * d3, 0.47D * d2, 0.835D * d3, 0.46D * d2, 0.845D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.46D * d2, 0.845D * d3, 0.475D * d2, 0.86D * d3, 0.5D * d2, 0.86D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE1: 
    default: 
      localPath1.setStyle("-fx-pointer: " + this.control.getValueColor().CSS);
      localPath1.getStyleClass().add("pointer1-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.515D * d2, 0.705D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.515D * d2, 0.69D * d3, 0.51D * d2, 0.63D * d3, 0.51D * d2, 0.615D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.505D * d2, 0.6D * d3, 0.5D * d2, 0.25D * d3, 0.5D * d2, 0.25D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.5D * d2, 0.25D * d3, 0.49D * d2, 0.595D * d3, 0.49D * d2, 0.61D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.49D * d2, 0.63D * d3, 0.485D * d2, 0.69D * d3, 0.485D * d2, 0.705D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.475D * d2, 0.715D * d3, 0.465D * d2, 0.725D * d3, 0.465D * d2, 0.735D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.465D * d2, 0.75D * d3, 0.48D * d2, 0.765D * d3, 0.5D * d2, 0.765D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.52D * d2, 0.765D * d3, 0.535D * d2, 0.75D * d3, 0.535D * d2, 0.735D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.535D * d2, 0.725D * d3, 0.525D * d2, 0.715D * d3, 0.515D * d2, 0.705D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
    }
    if (this.control.isPointerGlowEnabled())
    {
      DropShadow localDropShadow = new DropShadow();
      localDropShadow.setWidth(0.04D * d1);
      localDropShadow.setHeight(0.04D * d1);
      localDropShadow.setOffsetX(0.0D);
      localDropShadow.setOffsetY(0.0D);
      localDropShadow.setRadius(0.04D * d1);
      localDropShadow.setColor(this.control.getValueColor().COLOR);
      localDropShadow.setBlurType(BlurType.GAUSSIAN);
      if (this.control.getPointerType() == Gauge.PointerType.TYPE9) {
        localPath2.setEffect(localDropShadow);
      } else {
        localPath1.setEffect(localDropShadow);
      }
    }
    this.pointer.getChildren().addAll(new Node[] { localPath1 });
    if (this.control.getPointerType() == Gauge.PointerType.TYPE9) {
      this.pointer.getChildren().add(localPath2);
    }
    this.pointer.getTransforms().clear();
    this.pointer.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getY()));
    this.pointer.setCache(true);
    this.pointer.setCacheHint(CacheHint.ROTATE);
  }
  
  public void drawLcdContent()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    this.lcdContent.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d1);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    this.lcdContent.getChildren().add(localRectangle1);
    Rectangle localRectangle2 = new Rectangle((d1 - d1 * this.control.getRadialRange().LCD_FACTORS.getX()) / 2.0D, d1 * this.control.getRadialRange().LCD_FACTORS.getY(), d1 * this.control.getRadialRange().LCD_FACTORS.getWidth(), d1 * this.control.getRadialRange().LCD_FACTORS.getHeight());
    double d2;
    switch (this.control.getLcdValueFont())
    {
    case LCD: 
      d2 = 1.5D;
      break;
    case BUS: 
      d2 = 2.0D;
      break;
    case PIXEL: 
      d2 = 2.0D;
      break;
    case PHONE_LCD: 
      d2 = 2.0D;
      break;
    case STANDARD: 
    default: 
      d2 = 2.0D;
    }
    if (this.lcdValueFont == null) {
      adjustLcdFont();
    }
    this.lcdValueString.setFont(this.lcdValueFont);
    this.lcdUnitString.setFont(this.lcdUnitFont);
    this.lcdUnitString.setText(this.control.isLcdValueCoupled() ? this.control.getUnit() : this.control.getLcdUnit());
    this.lcdUnitString.setTextOrigin(VPos.BOTTOM);
    this.lcdUnitString.setTextAlignment(TextAlignment.RIGHT);
    if (!this.lcdUnitString.visibleProperty().isBound()) {
      this.lcdUnitString.visibleProperty().bind(this.control.lcdUnitVisibleProperty());
    }
    if (this.control.isLcdUnitVisible())
    {
      this.lcdUnitString.setX(localRectangle2.getX() + (localRectangle2.getWidth() - this.lcdUnitString.getLayoutBounds().getWidth()) - localRectangle2.getHeight() * 0.0833333333D);
      this.lcdUnitString.setY(localRectangle2.getY() + (localRectangle2.getHeight() + this.lcdValueString.getLayoutBounds().getHeight()) / d2 - this.lcdValueString.getLayoutBounds().getHeight() * 0.05D);
    }
    this.lcdUnitString.setStroke(null);
    switch (this.control.getLcdNumberSystem())
    {
    case HEXADECIMAL: 
      this.lcdValueString.setText(Integer.toHexString((int)this.currentLcdValue.get()).toUpperCase());
      break;
    case OCTAL: 
      this.lcdValueString.setText(Integer.toOctalString((int)this.currentLcdValue.get()).toUpperCase());
      break;
    case DECIMAL: 
    default: 
      this.lcdValueString.setText(formatLcdValue(this.currentLcdValue.get()));
    }
    if (this.control.isLcdUnitVisible()) {
      this.lcdValueString.setX(localRectangle2.getX() + (localRectangle2.getWidth() - this.lcdUnitString.getLayoutBounds().getWidth() - this.lcdValueString.getLayoutBounds().getWidth()) - localRectangle2.getHeight() * 0.0833333333D);
    } else {
      this.lcdValueString.setX(localRectangle2.getX() + (localRectangle2.getWidth() - this.lcdValueString.getLayoutBounds().getWidth()) - localRectangle2.getHeight() * 0.0833333333D);
    }
    this.lcdValueString.setY(localRectangle2.getY() + (localRectangle2.getHeight() + this.lcdValueString.getLayoutBounds().getHeight()) / 2.0D);
    this.lcdValueString.setTextOrigin(VPos.BOTTOM);
    this.lcdValueString.setTextAlignment(TextAlignment.RIGHT);
    this.lcdValueString.setStroke(null);
    this.lcdContent.getChildren().addAll(new Node[] { this.lcdUnitString, this.lcdValueString });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/RadialQuarterNSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */