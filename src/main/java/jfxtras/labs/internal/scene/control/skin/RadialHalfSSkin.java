package jfxtras.labs.internal.scene.control.skin;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.Animation.Status;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
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
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
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
import javafx.scene.transform.Transform;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.RadialHalfSBehavior;
import jfxtras.labs.scene.control.gauge.ColorDef;
import jfxtras.labs.scene.control.gauge.Gauge.BackgroundDesign;
import jfxtras.labs.scene.control.gauge.Gauge.FrameDesign;
import jfxtras.labs.scene.control.gauge.Gauge.PointerType;
import jfxtras.labs.scene.control.gauge.Gauge.RadialRange;
import jfxtras.labs.scene.control.gauge.Gauge.ThresholdColor;
import jfxtras.labs.scene.control.gauge.Marker;
import jfxtras.labs.scene.control.gauge.RadialHalfS;
import jfxtras.labs.scene.control.gauge.Section;

public class RadialHalfSSkin
  extends GaugeSkinBase<RadialHalfS, RadialHalfSBehavior>
{
  private static final Rectangle MIN_SIZE = new Rectangle(38.0D, 25.0D);
  private static final Rectangle PREF_SIZE = new Rectangle(200.0D, 130.0D);
  private static final Rectangle MAX_SIZE = new Rectangle(1024.0D, 666.0D);
  private RadialHalfS control;
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
  private Group knobs;
  private Group knobsShadow;
  private Group threshold;
  private Group minMeasured;
  private Group maxMeasured;
  private Group pointer;
  private Group pointerShadow;
  private Group bargraphOff;
  private Group bargraphOn;
  private Group ledOff;
  private Group ledOn;
  private Group userLedOff;
  private Group userLedOn;
  private Group foreground;
  private Point2D center;
  private int noOfLeds;
  private ArrayList<Shape> ledsOff;
  private ArrayList<Shape> ledsOn;
  private DoubleProperty currentValue;
  private DoubleProperty formerValue;
  private FadeTransition glowPulse;
  private RotateTransition pointerRotation;
  private AnimationTimer ledTimer;
  private boolean ledOnVisible;
  private long lastLedTimerCall;
  private AnimationTimer userLedTimer;
  private boolean userLedOnVisible;
  private long lastUserLedTimerCall;
  private boolean isDirty;
  private boolean initialized;
  
  public RadialHalfSSkin(RadialHalfS paramRadialHalfS)
  {
    super(paramRadialHalfS, new RadialHalfSBehavior(paramRadialHalfS));
    this.control = paramRadialHalfS;
    this.gaugeBounds = new Rectangle(200.0D, 130.0D);
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
    this.knobs = new Group();
    this.knobsShadow = new Group(new Node[] { this.knobs });
    this.threshold = new Group();
    this.minMeasured = new Group();
    this.maxMeasured = new Group();
    this.pointer = new Group();
    this.pointerShadow = new Group(new Node[] { this.pointer });
    this.bargraphOff = new Group();
    this.bargraphOn = new Group();
    this.ledOff = new Group();
    this.ledOn = new Group();
    this.userLedOff = new Group();
    this.userLedOn = new Group();
    this.foreground = new Group();
    this.noOfLeds = 35;
    this.ledsOff = new ArrayList(this.noOfLeds);
    this.ledsOn = new ArrayList(this.noOfLeds);
    this.currentValue = new SimpleDoubleProperty(0.0D);
    this.formerValue = new SimpleDoubleProperty(0.0D);
    this.glowPulse = new FadeTransition(Duration.millis(800.0D), this.glowOn);
    this.pointerRotation = new RotateTransition(Duration.millis(this.control.getAnimationDuration()), this.pointer);
    this.isDirty = false;
    this.ledTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > RadialHalfSSkin.this.lastLedTimerCall + RadialHalfSSkin.this.getBlinkInterval())
        {
          RadialHalfSSkin.access$180(RadialHalfSSkin.this, 1);
          if (RadialHalfSSkin.this.ledOnVisible) {
            RadialHalfSSkin.this.ledOn.setOpacity(1.0D);
          } else {
            RadialHalfSSkin.this.ledOn.setOpacity(0.0D);
          }
          RadialHalfSSkin.this.lastLedTimerCall = paramAnonymousLong;
        }
      }
    };
    this.lastLedTimerCall = 0L;
    this.ledOnVisible = false;
    this.userLedTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > RadialHalfSSkin.this.lastUserLedTimerCall + RadialHalfSSkin.this.getBlinkInterval())
        {
          RadialHalfSSkin.access$480(RadialHalfSSkin.this, 1);
          if (RadialHalfSSkin.this.userLedOnVisible) {
            RadialHalfSSkin.this.userLedOn.setOpacity(1.0D);
          } else {
            RadialHalfSSkin.this.userLedOn.setOpacity(0.0D);
          }
          RadialHalfSSkin.this.lastUserLedTimerCall = paramAnonymousLong;
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
    this.noOfLeds = ((int)(this.control.getRadialRange().ANGLE_RANGE / 5.0D));
    this.control.recalcRange();
    this.control.setMinMeasuredValue(this.control.getMaxValue());
    this.control.setMaxMeasuredValue(this.control.getMinValue());
    addBindings();
    addListeners();
    calcGaugeBounds();
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
    if (this.bargraphOff.visibleProperty().isBound()) {
      this.bargraphOff.visibleProperty().unbind();
    }
    this.bargraphOff.visibleProperty().bind(this.control.bargraphProperty());
    if (this.bargraphOn.visibleProperty().isBound()) {
      this.bargraphOn.visibleProperty().unbind();
    }
    this.bargraphOn.visibleProperty().bind(this.control.bargraphProperty());
    this.pointer.setVisible(!this.bargraphOff.isVisible());
    this.knobs.setVisible(!this.bargraphOff.isVisible());
    if (this.bargraphOff.isVisible())
    {
      this.areas.setOpacity(0.0D);
      this.sections.setOpacity(0.0D);
    }
    else
    {
      this.areas.setOpacity(1.0D);
      this.sections.setOpacity(1.0D);
    }
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
        RadialHalfSSkin.this.updateAreas();
        RadialHalfSSkin.this.drawCircularAreas(RadialHalfSSkin.this.control, RadialHalfSSkin.this.areas, RadialHalfSSkin.this.gaugeBounds);
      }
    });
    this.control.getSections().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        RadialHalfSSkin.this.updateSections();
        RadialHalfSSkin.this.drawCircularSections(RadialHalfSSkin.this.control, RadialHalfSSkin.this.sections, RadialHalfSSkin.this.gaugeBounds);
      }
    });
    this.control.getMarkers().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        RadialHalfSSkin.this.drawCircularIndicators(RadialHalfSSkin.this.control, RadialHalfSSkin.this.markers, RadialHalfSSkin.this.center, RadialHalfSSkin.this.gaugeBounds);
      }
    });
    this.control.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        RadialHalfSSkin.this.formerValue.set(paramAnonymousNumber1.doubleValue() < RadialHalfSSkin.this.control.getMinValue() ? RadialHalfSSkin.this.control.getMinValue() : paramAnonymousNumber1.doubleValue());
        if (RadialHalfSSkin.this.pointerRotation.getStatus() != Animation.Status.STOPPED) {
          RadialHalfSSkin.this.pointerRotation.stop();
        }
        if ((paramAnonymousNumber2.doubleValue() > paramAnonymousNumber1.doubleValue() - RadialHalfSSkin.this.control.getRedrawToleranceValue()) && (paramAnonymousNumber2.doubleValue() < paramAnonymousNumber1.doubleValue() + RadialHalfSSkin.this.control.getRedrawToleranceValue())) {
          return;
        }
        if (RadialHalfSSkin.this.control.isValueAnimationEnabled())
        {
          RadialHalfSSkin.this.pointerRotation.setFromAngle(-(RadialHalfSSkin.this.formerValue.doubleValue() - RadialHalfSSkin.this.control.getMinValue()) * RadialHalfSSkin.this.control.getAngleStep());
          RadialHalfSSkin.this.pointerRotation.setToAngle(-(paramAnonymousNumber2.doubleValue() - RadialHalfSSkin.this.control.getMinValue()) * RadialHalfSSkin.this.control.getAngleStep());
          RadialHalfSSkin.this.pointerRotation.setInterpolator(Interpolator.SPLINE(0.5D, 0.4D, 0.4D, 1.0D));
          RadialHalfSSkin.this.pointerRotation.play();
        }
        else
        {
          RadialHalfSSkin.this.pointer.setRotate(-(paramAnonymousNumber2.doubleValue() - RadialHalfSSkin.this.control.getMinValue()) * RadialHalfSSkin.this.control.getAngleStep());
        }
        RadialHalfSSkin.this.checkMarkers(RadialHalfSSkin.this.control, paramAnonymousNumber1.doubleValue(), paramAnonymousNumber2.doubleValue());
        InnerShadow localInnerShadow;
        DropShadow localDropShadow;
        Iterator localIterator;
        Section localSection;
        Shape localShape;
        if (RadialHalfSSkin.this.control.isSectionsHighlighting())
        {
          localInnerShadow = new InnerShadow();
          localInnerShadow.setBlurType(BlurType.GAUSSIAN);
          localDropShadow = new DropShadow();
          localDropShadow.setWidth(0.05D * RadialHalfSSkin.this.gaugeBounds.getWidth());
          localDropShadow.setHeight(0.05D * RadialHalfSSkin.this.gaugeBounds.getHeight());
          localDropShadow.setBlurType(BlurType.GAUSSIAN);
          localIterator = RadialHalfSSkin.this.control.getSections().iterator();
          while (localIterator.hasNext())
          {
            localSection = (Section)localIterator.next();
            localShape = localSection.getSectionArea();
            if (localSection.contains(paramAnonymousNumber2.doubleValue()))
            {
              localInnerShadow.setColor(localSection.getColor().darker());
              localDropShadow.setInput(localInnerShadow);
              localDropShadow.setColor(localSection.getColor().brighter());
              localShape.setEffect(localDropShadow);
            }
            else
            {
              localShape.setEffect(null);
            }
          }
        }
        if (RadialHalfSSkin.this.control.isAreasHighlighting())
        {
          localInnerShadow = new InnerShadow();
          localInnerShadow.setBlurType(BlurType.GAUSSIAN);
          localDropShadow = new DropShadow();
          localDropShadow.setWidth(0.05D * RadialHalfSSkin.this.gaugeBounds.getWidth());
          localDropShadow.setHeight(0.05D * RadialHalfSSkin.this.gaugeBounds.getHeight());
          localDropShadow.setBlurType(BlurType.GAUSSIAN);
          localIterator = RadialHalfSSkin.this.control.getAreas().iterator();
          while (localIterator.hasNext())
          {
            localSection = (Section)localIterator.next();
            localShape = localSection.getFilledArea();
            if (localSection.contains(paramAnonymousNumber2.doubleValue()))
            {
              localInnerShadow.setColor(localSection.getColor().darker());
              localDropShadow.setInput(localInnerShadow);
              localDropShadow.setColor(localSection.getColor().brighter());
              localShape.setEffect(localDropShadow);
            }
            else
            {
              localShape.setEffect(null);
            }
          }
        }
      }
    });
    this.pointer.rotateProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        RadialHalfSSkin.this.currentValue.set(-paramAnonymousNumber2.doubleValue() / RadialHalfSSkin.this.control.getAngleStep() + RadialHalfSSkin.this.control.getMinValue());
        if (RadialHalfSSkin.this.bargraphOff.isVisible())
        {
          int i = paramAnonymousNumber2.intValue() / 5 * -1;
          int j = paramAnonymousNumber1.intValue() / 5 * -1;
          int k = i >= RadialHalfSSkin.this.noOfLeds ? RadialHalfSSkin.this.noOfLeds : i < 0 ? 0 : i;
          int m = j >= RadialHalfSSkin.this.noOfLeds ? RadialHalfSSkin.this.noOfLeds - 1 : j < 0 ? 0 : j;
          int n = (int)(RadialHalfSSkin.this.control.getThreshold() * RadialHalfSSkin.this.control.getAngleStep() / 5.0D);
          int i1;
          if (Double.compare(RadialHalfSSkin.this.control.getValue(), RadialHalfSSkin.this.formerValue.doubleValue()) >= 0) {
            for (i1 = m; i1 < k; i1++) {
              ((Shape)RadialHalfSSkin.this.ledsOn.get(i1)).setVisible(true);
            }
          } else {
            for (i1 = m; i1 > k; i1--) {
              ((Shape)RadialHalfSSkin.this.ledsOn.get(i1)).setVisible(false);
            }
          }
          if (RadialHalfSSkin.this.control.isThresholdVisible())
          {
            ((Shape)RadialHalfSSkin.this.ledsOn.get(n)).setVisible(true);
            ((Shape)RadialHalfSSkin.this.ledsOn.get(n)).getStyleClass().clear();
            ((Shape)RadialHalfSSkin.this.ledsOn.get(n)).getStyleClass().add("bargraph-threshold");
          }
        }
        if (Double.compare(RadialHalfSSkin.this.currentValue.get(), RadialHalfSSkin.this.control.getMinMeasuredValue()) < 0) {
          RadialHalfSSkin.this.control.setMinMeasuredValue(RadialHalfSSkin.this.currentValue.get());
        } else if (Double.compare(RadialHalfSSkin.this.currentValue.get(), RadialHalfSSkin.this.control.getMaxMeasuredValue()) > 0) {
          RadialHalfSSkin.this.control.setMaxMeasuredValue(RadialHalfSSkin.this.currentValue.get());
        }
        if (RadialHalfSSkin.this.control.isThresholdBehaviorInverted()) {
          RadialHalfSSkin.this.control.setThresholdExceeded(RadialHalfSSkin.this.currentValue.get() < RadialHalfSSkin.this.control.getThreshold());
        } else {
          RadialHalfSSkin.this.control.setThresholdExceeded(RadialHalfSSkin.this.currentValue.get() > RadialHalfSSkin.this.control.getThreshold());
        }
        if (!RadialHalfSSkin.this.control.isThresholdExceeded()) {
          RadialHalfSSkin.this.ledOn.setOpacity(0.0D);
        }
      }
    });
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("ANIMATION_DURATION".equals(paramString))
    {
      this.pointerRotation.setDuration(Duration.millis(this.control.getAnimationDuration()));
    }
    else if ("RADIAL_RANGE".equals(paramString))
    {
      this.noOfLeds = ((int)(this.control.getRadialRange().ANGLE_RANGE / 5.0D));
      this.isDirty = true;
    }
    else if ("FRAME_DESIGN".equals(paramString))
    {
      drawFrame();
    }
    else if ("BACKGROUND_DESIGN".equals(paramString))
    {
      drawBackground();
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
    else if ("THRESHOLD_COLOR".equals(paramString))
    {
      int i = (int)(this.control.getThreshold() * this.control.getAngleStep() / 5.0D);
      ((Shape)this.ledsOn.get(i)).getStyleClass().clear();
      ((Shape)this.ledsOn.get(i)).getStyleClass().add("bargraph-threshold");
    }
    else if ("FOREGROUND_TYPE".equals(paramString))
    {
      drawForeground();
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
      drawGlowOn();
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
    else
    {
      double d;
      if ("MIN_MEASURED_VALUE".equals(paramString))
      {
        d = -90.0D + this.control.getRadialRange().ROTATION_OFFSET;
        this.minMeasured.setRotate(d - (this.control.getMinMeasuredValue() - this.control.getMinValue()) * this.control.getAngleStep());
      }
      else if ("MAX_MEASURED_VALUE".equals(paramString))
      {
        d = -90.0D + this.control.getRadialRange().ROTATION_OFFSET;
        this.maxMeasured.setRotate(d - (this.control.getMaxMeasuredValue() - this.control.getMinValue()) * this.control.getAngleStep());
      }
      else if ("TREND".equals(paramString))
      {
        drawCircularTrend(this.control, this.trend, this.gaugeBounds);
      }
      else if ("SIMPLE_GRADIENT_BASE".equals(paramString))
      {
        this.isDirty = true;
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
    if (!this.initialized) {
      init();
    }
    if (this.control.getScene() != null)
    {
      calcGaugeBounds();
      setTranslateX(this.framelessOffset.getX());
      setTranslateY(this.framelessOffset.getY());
      this.center = new Point2D(this.gaugeBounds.getWidth() * 0.5D, this.gaugeBounds.getWidth() * 0.15D);
      drawFrame();
      drawBackground();
      drawCircularTrend(this.control, this.trend, this.gaugeBounds);
      updateSections();
      drawSections();
      updateAreas();
      drawAreas();
      drawTitleAndUnit();
      drawCircularTickmarks(this.control, this.tickmarks, this.center, this.gaugeBounds);
      drawCircularLed(this.control, this.ledOff, this.ledOn, this.gaugeBounds);
      drawCircularUserLed(this.control, this.userLedOff, this.userLedOn, this.gaugeBounds);
      drawThreshold();
      drawGlowOff();
      drawGlowOn();
      drawMinMeasuredIndicator();
      drawMaxMeasuredIndicator();
      drawIndicators();
      drawPointer();
      this.bargraphOff.getTransforms().clear();
      this.bargraphOff.setTranslateY(-this.gaugeBounds.getWidth() * 0.35D);
      drawCircularBargraph(this.control, this.bargraphOff, this.noOfLeds, this.ledsOff, false, true, new Point2D(this.center.getX(), this.gaugeBounds.getWidth() * 0.5D), this.gaugeBounds);
      this.bargraphOn.getTransforms().clear();
      this.bargraphOn.setTranslateY(-this.gaugeBounds.getWidth() * 0.35D);
      drawCircularBargraph(this.control, this.bargraphOn, this.noOfLeds, this.ledsOn, true, false, new Point2D(this.center.getX(), this.gaugeBounds.getWidth() * 0.5D), this.gaugeBounds);
      drawCircularKnobs(this.control, this.knobs, this.center, this.gaugeBounds);
      drawForeground();
      if ((this.control.isPointerShadowEnabled()) && (!this.control.isPointerGlowEnabled())) {
        addDropShadow(this.control, new Node[] { this.knobs, this.pointerShadow });
      }
      getChildren().setAll(new Node[] { this.frame, this.background, this.sections, this.areas, this.trend, this.ledOff, this.ledOn, this.userLedOff, this.userLedOn, this.titleAndUnit, this.tickmarks, this.threshold, this.glowOff, this.glowOn, this.pointerShadow, this.bargraphOff, this.bargraphOn, this.minMeasured, this.maxMeasured, this.markers, this.knobsShadow, this.foreground });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public RadialHalfS getSkinnable()
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
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight()) * 1.5384615385D;
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = PREF_SIZE.getHeight();
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom()) / 1.5384615385D;
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
    double d1 = this.control.getPrefWidth() * 0.38D;
    double d2 = this.control.isExpandedSections() ? d1 - this.control.getPrefWidth() * 0.12D : d1 - this.control.getPrefWidth() * 0.04D;
    Circle localCircle = new Circle(this.center.getX(), this.center.getY(), d2);
    Iterator localIterator = this.control.getSections().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      double d3 = localSection.getStart() < this.control.getMinValue() ? this.control.getMinValue() : localSection.getStart();
      double d4 = localSection.getStop() > this.control.getMaxValue() ? this.control.getMaxValue() : localSection.getStop();
      double d5 = this.control.getRadialRange().SECTIONS_OFFSET + d3 * this.control.getAngleStep() - this.control.getMinValue() * this.control.getAngleStep();
      double d6 = (d4 - d3) * this.control.getAngleStep();
      Arc localArc = new Arc();
      localArc.setType(ArcType.ROUND);
      localArc.setCenterX(this.center.getX());
      localArc.setCenterY(this.center.getY());
      localArc.setRadiusX(d1);
      localArc.setRadiusY(d1);
      localArc.setStartAngle(d5);
      localArc.setLength(d6);
      Shape localShape = Shape.subtract(localArc, localCircle);
      localSection.setSectionArea(localShape);
    }
  }
  
  private void updateAreas()
  {
    double d1 = this.control.getPrefWidth() * 0.38D;
    double d2 = this.control.isExpandedSections() ? this.control.getPrefWidth() * 0.12D : this.control.getPrefWidth() * 0.04D;
    double d3 = d1 - d2;
    Iterator localIterator = this.control.getAreas().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      double d4 = localSection.getStart() < this.control.getMinValue() ? this.control.getMinValue() : localSection.getStart();
      double d5 = localSection.getStop() > this.control.getMaxValue() ? this.control.getMaxValue() : localSection.getStop();
      double d6 = this.control.getRadialRange().SECTIONS_OFFSET + d4 * this.control.getAngleStep() - this.control.getMinValue() * this.control.getAngleStep();
      double d7 = (d5 - d4) * this.control.getAngleStep();
      Arc localArc = new Arc();
      localArc.setType(ArcType.ROUND);
      localArc.setCenterX(this.center.getX());
      localArc.setCenterY(this.center.getY());
      localArc.setRadiusX(d3);
      localArc.setRadiusY(d3);
      localArc.setStartAngle(d6);
      localArc.setLength(d7);
      localSection.setFilledArea(localArc);
    }
  }
  
  public void drawFrame()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = this.control.getPrefWidth();
    double d3 = this.control.getPrefHeight();
    this.frame.getChildren().clear();
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(d2, 0.23076923076923078D * d3));
    localPath1.getElements().add(new CubicCurveTo(d2, 0.6538461538461539D * d3, 0.775D * d2, d3, 0.5D * d2, d3));
    localPath1.getElements().add(new CubicCurveTo(0.225D * d2, d3, 0.0D, 0.6538461538461539D * d3, 0.0D, 0.23076923076923078D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.0D, 0.23076923076923078D * d3, 0.0D, 0.0D, 0.0D, 0.0D));
    localPath1.getElements().add(new LineTo(d2, 0.0D));
    localPath1.getElements().add(new CubicCurveTo(d2, 0.0D, d2, 0.23076923076923078D * d3, d2, 0.23076923076923078D * d3));
    localPath1.getElements().add(new ClosePath());
    localPath1.setFill(Color.color(0.5176470588D, 0.5176470588D, 0.5176470588D, 1.0D));
    localPath1.setStroke(null);
    this.frame.getChildren().add(localPath1);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.07D * d2, 0.23846153846153847D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.07D * d2, 0.23846153846153847D * d3, 0.07D * d2, 0.1076923076923077D * d3, 0.07D * d2, 0.1076923076923077D * d3));
    localPath2.getElements().add(new LineTo(0.92D * d2, 0.1076923076923077D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.92D * d2, 0.1076923076923077D * d3, 0.92D * d2, 0.23846153846153847D * d3, 0.92D * d2, 0.23846153846153847D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.92D * d2, 0.6D * d3, 0.73D * d2, 0.8923076923076924D * d3, 0.495D * d2, 0.8923076923076924D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.26D * d2, 0.8923076923076924D * d3, 0.07D * d2, 0.6D * d3, 0.07D * d2, 0.23846153846153847D * d3));
    localPath2.getElements().add(new ClosePath());
    localPath2.setFill(Color.color(0.6D, 0.6D, 0.6D, 0.8D));
    localPath2.setStroke(null);
    Path localPath3 = new Path();
    localPath3.setFillRule(FillRule.EVEN_ODD);
    localPath3.getElements().add(new MoveTo(0.995D * d2, 0.23076923076923078D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.995D * d2, 0.6538461538461539D * d3, 0.775D * d2, 0.9923076923076923D * d3, 0.5D * d2, 0.9923076923076923D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.225D * d2, 0.9923076923076923D * d3, 0.005D * d2, 0.6538461538461539D * d3, 0.005D * d2, 0.23076923076923078D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.005D * d2, 0.23076923076923078D * d3, 0.005D * d2, 0.007692307692307693D * d3, 0.005D * d2, 0.007692307692307693D * d3));
    localPath3.getElements().add(new LineTo(0.995D * d2, 0.007692307692307693D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.995D * d2, 0.007692307692307693D * d3, 0.995D * d2, 0.23076923076923078D * d3, 0.995D * d2, 0.23076923076923078D * d3));
    localPath3.getElements().add(new ClosePath());
    switch (this.control.getFrameDesign())
    {
    case GLOSSY_METAL: 
      RadialGradient localRadialGradient = new RadialGradient(0.0D, 0.0D, 0.5D * d2, 0.9923076923076923D * d3, 0.495D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.8235294118D, 0.8235294118D, 0.8235294118D, 1.0D)), new Stop(0.95D, Color.color(0.8235294118D, 0.8235294118D, 0.8235294118D, 1.0D)), new Stop(1.0D, Color.color(0.9960784314D, 0.9960784314D, 0.9960784314D, 1.0D)) });
      localPath3.setFill(localRadialGradient);
      localPath3.setStroke(null);
      Path localPath4 = new Path();
      localPath4.setFillRule(FillRule.EVEN_ODD);
      localPath4.getElements().add(new MoveTo(0.985D * d2, 0.23076923076923078D * d3));
      localPath4.getElements().add(new CubicCurveTo(0.985D * d2, 0.6153846153846154D * d3, 0.775D * d2, 0.9769230769230769D * d3, 0.5D * d2, 0.9769230769230769D * d3));
      localPath4.getElements().add(new CubicCurveTo(0.225D * d2, 0.9769230769230769D * d3, 0.015D * d2, 0.6153846153846154D * d3, 0.015D * d2, 0.23076923076923078D * d3));
      localPath4.getElements().add(new CubicCurveTo(0.015D * d2, 0.23076923076923078D * d3, 0.015D * d2, 0.023076923076923078D * d3, 0.015D * d2, 0.023076923076923078D * d3));
      localPath4.getElements().add(new LineTo(0.985D * d2, 0.023076923076923078D * d3));
      localPath4.getElements().add(new CubicCurveTo(0.985D * d2, 0.023076923076923078D * d3, 0.985D * d2, 0.23076923076923078D * d3, 0.985D * d2, 0.23076923076923078D * d3));
      localPath4.getElements().add(new ClosePath());
      LinearGradient localLinearGradient1 = new LinearGradient(0.0D, 0.03076923076923077D * d3, 0.0D, 0.9692307692307692D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.9764705882D, 0.9764705882D, 0.9764705882D, 1.0D)), new Stop(0.23D, Color.color(0.7843137255D, 0.7647058824D, 0.7490196078D, 1.0D)), new Stop(0.34D, Color.color(0.8235294118D, 0.8235294118D, 0.8235294118D, 1.0D)), new Stop(0.65D, Color.color(0.1215686275D, 0.1215686275D, 0.1215686275D, 1.0D)), new Stop(0.84D, Color.color(0.7843137255D, 0.7607843137D, 0.7529411765D, 1.0D)), new Stop(1.0D, Color.color(0.7843137255D, 0.7607843137D, 0.7529411765D, 1.0D)) });
      localPath4.setFill(localLinearGradient1);
      localPath4.setStroke(null);
      Path localPath5 = new Path();
      localPath5.setFillRule(FillRule.EVEN_ODD);
      localPath5.getElements().add(new MoveTo(0.935D * d2, 0.23076923076923078D * d3));
      localPath5.getElements().add(new CubicCurveTo(0.935D * d2, 0.5692307692307692D * d3, 0.77D * d2, 0.9153846153846154D * d3, 0.495D * d2, 0.9153846153846154D * d3));
      localPath5.getElements().add(new CubicCurveTo(0.22D * d2, 0.9153846153846154D * d3, 0.055D * d2, 0.5615384615384615D * d3, 0.055D * d2, 0.23076923076923078D * d3));
      localPath5.getElements().add(new CubicCurveTo(0.055D * d2, 0.23076923076923078D * d3, 0.055D * d2, 0.08461538461538462D * d3, 0.055D * d2, 0.08461538461538462D * d3));
      localPath5.getElements().add(new LineTo(0.935D * d2, 0.08461538461538462D * d3));
      localPath5.getElements().add(new CubicCurveTo(0.935D * d2, 0.08461538461538462D * d3, 0.935D * d2, 0.23076923076923078D * d3, 0.935D * d2, 0.23076923076923078D * d3));
      localPath5.getElements().add(new ClosePath());
      Color localColor1 = Color.color(0.9647058824D, 0.9647058824D, 0.9647058824D, 1.0D);
      localPath5.setFill(localColor1);
      localPath5.setStroke(null);
      Path localPath6 = new Path();
      localPath6.setFillRule(FillRule.EVEN_ODD);
      localPath6.getElements().add(new MoveTo(0.065D * d2, 0.23846153846153847D * d3));
      localPath6.getElements().add(new CubicCurveTo(0.065D * d2, 0.23846153846153847D * d3, 0.065D * d2, 0.1D * d3, 0.065D * d2, 0.1D * d3));
      localPath6.getElements().add(new LineTo(0.925D * d2, 0.1D * d3));
      localPath6.getElements().add(new CubicCurveTo(0.925D * d2, 0.1D * d3, 0.925D * d2, 0.23846153846153847D * d3, 0.925D * d2, 0.23846153846153847D * d3));
      localPath6.getElements().add(new CubicCurveTo(0.925D * d2, 0.6076923076923076D * d3, 0.73D * d2, 0.9D * d3, 0.495D * d2, 0.9D * d3));
      localPath6.getElements().add(new CubicCurveTo(0.26D * d2, 0.9D * d3, 0.065D * d2, 0.6076923076923076D * d3, 0.065D * d2, 0.23846153846153847D * d3));
      localPath6.getElements().add(new ClosePath());
      Color localColor2 = Color.color(0.2D, 0.2D, 0.2D, 1.0D);
      localPath6.setFill(localColor2);
      localPath6.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localPath3, localPath4, localPath5, localPath6 });
      break;
    case DARK_GLOSSY: 
      LinearGradient localLinearGradient2 = new LinearGradient(0.855D * d2, 0.0D, 0.1691064222490489D * d2, 1.0552208888476171D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.6745098039D, 0.6745098039D, 0.6784313725D, 1.0D)), new Stop(0.08D, Color.color(0.9960784314D, 0.9960784314D, 1.0D, 1.0D)), new Stop(0.52D, Color.BLACK), new Stop(0.55D, Color.color(0.0196078431D, 0.0235294118D, 0.0196078431D, 1.0D)), new Stop(0.84D, Color.color(0.9725490196D, 0.9803921569D, 0.9764705882D, 1.0D)), new Stop(0.99D, Color.color(0.7058823529D, 0.7058823529D, 0.7058823529D, 1.0D)), new Stop(1.0D, Color.color(0.6980392157D, 0.6980392157D, 0.6980392157D, 1.0D)) });
      localPath3.setFill(localLinearGradient2);
      localPath3.setStroke(null);
      Path localPath7 = new Path();
      localPath7.setFillRule(FillRule.EVEN_ODD);
      localPath7.getElements().add(new MoveTo(0.985D * d2, 0.23076923076923078D * d3));
      localPath7.getElements().add(new CubicCurveTo(0.985D * d2, 0.6153846153846154D * d3, 0.775D * d2, 0.9769230769230769D * d3, 0.5D * d2, 0.9769230769230769D * d3));
      localPath7.getElements().add(new CubicCurveTo(0.225D * d2, 0.9769230769230769D * d3, 0.015D * d2, 0.6153846153846154D * d3, 0.015D * d2, 0.23076923076923078D * d3));
      localPath7.getElements().add(new CubicCurveTo(0.015D * d2, 0.23076923076923078D * d3, 0.015D * d2, 0.023076923076923078D * d3, 0.015D * d2, 0.023076923076923078D * d3));
      localPath7.getElements().add(new LineTo(0.985D * d2, 0.023076923076923078D * d3));
      localPath7.getElements().add(new CubicCurveTo(0.985D * d2, 0.023076923076923078D * d3, 0.985D * d2, 0.23076923076923078D * d3, 0.985D * d2, 0.23076923076923078D * d3));
      localPath7.getElements().add(new ClosePath());
      LinearGradient localLinearGradient3 = new LinearGradient(0.0D, 0.023076923076923078D * d3, 0.0D, 0.9769230769230769D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.9764705882D, 0.9764705882D, 0.9764705882D, 1.0D)), new Stop(1.0E-4D, Color.BLACK), new Stop(0.41D, Color.color(0.2588235294D, 0.2588235294D, 0.2588235294D, 1.0D)), new Stop(1.0D, Color.color(0.0509803922D, 0.0509803922D, 0.0509803922D, 1.0D)) });
      localPath7.setFill(localLinearGradient3);
      localPath7.setStroke(null);
      Path localPath8 = new Path();
      localPath8.setFillRule(FillRule.EVEN_ODD);
      localPath8.getElements().add(new MoveTo(0.985D * d2, 0.26153846153846155D * d3));
      localPath8.getElements().add(new CubicCurveTo(0.985D * d2, 0.26153846153846155D * d3, 0.985D * d2, 0.023076923076923078D * d3, 0.985D * d2, 0.023076923076923078D * d3));
      localPath8.getElements().add(new LineTo(0.015D * d2, 0.023076923076923078D * d3));
      localPath8.getElements().add(new CubicCurveTo(0.015D * d2, 0.023076923076923078D * d3, 0.015D * d2, 0.23076923076923078D * d3, 0.015D * d2, 0.23076923076923078D * d3));
      localPath8.getElements().add(new CubicCurveTo(0.015D * d2, 0.36153846153846153D * d3, 0.04D * d2, 0.49230769230769234D * d3, 0.08D * d2, 0.6D * d3));
      localPath8.getElements().add(new CubicCurveTo(0.175D * d2, 0.4153846153846154D * d3, 0.35D * d2, 0.1076923076923077D * d3, 0.5D * d2, 0.1076923076923077D * d3));
      localPath8.getElements().add(new CubicCurveTo(0.66D * d2, 0.1076923076923077D * d3, 0.83D * d2, 0.4076923076923077D * d3, 0.92D * d2, 0.6D * d3));
      localPath8.getElements().add(new CubicCurveTo(0.96D * d2, 0.49230769230769234D * d3, 0.98D * d2, 0.3769230769230769D * d3, 0.985D * d2, 0.26153846153846155D * d3));
      localPath8.getElements().add(new ClosePath());
      LinearGradient localLinearGradient4 = new LinearGradient(0.0D, 0.03076923076923077D * d3, 0.0D, 0.6D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.9764705882D, 0.9764705882D, 0.9764705882D, 1.0D)), new Stop(0.26D, Color.color(1.0D, 1.0D, 1.0D, 0.737254902D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) });
      localPath8.setFill(localLinearGradient4);
      localPath8.setStroke(null);
      Path localPath9 = new Path();
      localPath9.setFillRule(FillRule.EVEN_ODD);
      localPath9.getElements().add(new MoveTo(0.065D * d2, 0.23846153846153847D * d3));
      localPath9.getElements().add(new CubicCurveTo(0.065D * d2, 0.23846153846153847D * d3, 0.065D * d2, 0.1D * d3, 0.065D * d2, 0.1D * d3));
      localPath9.getElements().add(new LineTo(0.925D * d2, 0.1D * d3));
      localPath9.getElements().add(new CubicCurveTo(0.925D * d2, 0.1D * d3, 0.925D * d2, 0.23846153846153847D * d3, 0.925D * d2, 0.23846153846153847D * d3));
      localPath9.getElements().add(new CubicCurveTo(0.925D * d2, 0.6076923076923076D * d3, 0.73D * d2, 0.9D * d3, 0.495D * d2, 0.9D * d3));
      localPath9.getElements().add(new CubicCurveTo(0.26D * d2, 0.9D * d3, 0.065D * d2, 0.6076923076923076D * d3, 0.065D * d2, 0.23846153846153847D * d3));
      localPath9.getElements().add(new ClosePath());
      LinearGradient localLinearGradient5 = new LinearGradient(0.805D * d2, 0.1D * d3, 0.1755776747036804D * d2, 1.035117037156343D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.6745098039D, 0.6745098039D, 0.6784313725D, 1.0D)), new Stop(0.07D, Color.color(0.9568627451D, 0.9568627451D, 0.9607843137D, 1.0D)), new Stop(0.08D, Color.color(0.9960784314D, 0.9960784314D, 1.0D, 1.0D)), new Stop(0.52D, Color.BLACK), new Stop(0.5201D, Color.BLACK), new Stop(0.55D, Color.color(0.0196078431D, 0.0235294118D, 0.0196078431D, 1.0D)), new Stop(0.56D, Color.color(0.0352941176D, 0.0392156863D, 0.0352941176D, 1.0D)), new Stop(0.9D, Color.color(0.9725490196D, 0.9803921569D, 0.9764705882D, 1.0D)), new Stop(0.92D, Color.color(0.9058823529D, 0.9137254902D, 0.9098039216D, 1.0D)), new Stop(1.0D, Color.color(0.6980392157D, 0.6980392157D, 0.6980392157D, 1.0D)) });
      localPath9.setFill(localLinearGradient5);
      localPath9.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localPath3, localPath7, localPath8, localPath9 });
      break;
    default: 
      ImageView localImageView = new ImageView();
      localImageView.setVisible(false);
      localPath3.getStyleClass().add(this.control.getFrameDesign().CSS);
      localPath3.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localPath3, localPath2 });
    }
  }
  
  public void drawBackground()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.background.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.background.getChildren().add(localRectangle);
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.2D * d1);
    localInnerShadow.setHeight(0.2D * d1);
    localInnerShadow.setOffsetY(0.03D * d1);
    localInnerShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.7D));
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    LinearGradient localLinearGradient = new LinearGradient(0.0D, 0.0D, d1, 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 0.6D)), new Stop(0.4D, Color.color(0.0D, 0.0D, 0.0D, 0.0D)), new Stop(0.6D, Color.color(0.0D, 0.0D, 0.0D, 0.0D)), new Stop(1.0D, Color.color(0.0D, 0.0D, 0.0D, 0.6D)) });
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.075D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.075D * d2, 0.23846153846153847D * d3, 0.075D * d2, 0.11538461538461539D * d3, 0.075D * d2, 0.11538461538461539D * d3));
    localPath1.getElements().add(new LineTo(0.915D * d2, 0.11538461538461539D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.915D * d2, 0.11538461538461539D * d3, 0.915D * d2, 0.23846153846153847D * d3, 0.915D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.915D * d2, 0.5923076923076923D * d3, 0.725D * d2, 0.8846153846153846D * d3, 0.495D * d2, 0.8846153846153846D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.265D * d2, 0.8846153846153846D * d3, 0.075D * d2, 0.5923076923076923D * d3, 0.075D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new ClosePath());
    localPath1.setStroke(null);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.075D * d2, 0.23846153846153847D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.075D * d2, 0.23846153846153847D * d3, 0.075D * d2, 0.11538461538461539D * d3, 0.075D * d2, 0.11538461538461539D * d3));
    localPath2.getElements().add(new LineTo(0.915D * d2, 0.11538461538461539D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.915D * d2, 0.11538461538461539D * d3, 0.915D * d2, 0.23846153846153847D * d3, 0.915D * d2, 0.23846153846153847D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.915D * d2, 0.5923076923076923D * d3, 0.725D * d2, 0.8846153846153846D * d3, 0.495D * d2, 0.8846153846153846D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.265D * d2, 0.8846153846153846D * d3, 0.075D * d2, 0.5923076923076923D * d3, 0.075D * d2, 0.23846153846153847D * d3));
    localPath2.getElements().add(new ClosePath());
    switch (this.control.getBackgroundDesign())
    {
    }
    localPath1.setStyle(this.control.getSimpleGradientBaseColorString());
    localPath1.getStyleClass().add(this.control.getBackgroundDesign().CSS_BACKGROUND);
    localPath1.setEffect(localInnerShadow);
    localPath1.setStroke(null);
    this.background.getChildren().addAll(new Node[] { localPath1 });
  }
  
  public void drawSections()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.sections.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.sections.getChildren().add(localRectangle);
    Iterator localIterator = this.control.getSections().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      Shape localShape = localSection.getSectionArea();
      localShape.setFill(localSection.getTransparentColor());
      localShape.setStroke(null);
      this.sections.getChildren().add(localShape);
    }
  }
  
  public void drawAreas()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.areas.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.areas.getChildren().add(localRectangle);
    Iterator localIterator = this.control.getAreas().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      Shape localShape = localSection.getFilledArea();
      localShape.setFill(localSection.getTransparentColor());
      localShape.setStroke(null);
      this.areas.getChildren().add(localShape);
    }
  }
  
  public void drawTitleAndUnit()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.titleAndUnit.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.titleAndUnit.getChildren().add(localRectangle);
    Font localFont1 = Font.font(this.control.getTitleFont(), FontWeight.NORMAL, 0.046728972D * d2);
    Text localText1 = new Text();
    localText1.setTextOrigin(VPos.BOTTOM);
    localText1.setFont(localFont1);
    localText1.setText(this.control.getTitle());
    localText1.setX((d2 - localText1.getLayoutBounds().getWidth()) / 2.0D);
    localText1.setY(0.3D * d2 + localText1.getLayoutBounds().getHeight());
    localText1.getStyleClass().add(this.control.getBackgroundDesign().CSS_TEXT);
    Font localFont2 = Font.font(this.control.getUnitFont(), FontWeight.NORMAL, 0.046728972D * d2);
    Text localText2 = new Text();
    localText2.setTextOrigin(VPos.BOTTOM);
    localText2.setFont(localFont2);
    localText2.setText(this.control.getUnit());
    localText2.setX((d2 - localText2.getLayoutBounds().getWidth()) / 2.0D);
    localText2.setY(0.25D * d2 + localText2.getLayoutBounds().getHeight());
    localText2.getStyleClass().add(this.control.getBackgroundDesign().CSS_TEXT);
    this.titleAndUnit.getChildren().addAll(new Node[] { localText1, localText2 });
  }
  
  public void drawGlowOff()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.glowOff.getChildren().clear();
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.1D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.1D * d2, 0.5846153846153846D * d3, 0.275D * d2, 0.8461538461538461D * d3, 0.495D * d2, 0.8461538461538461D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.715D * d2, 0.8461538461538461D * d3, 0.89D * d2, 0.5846153846153846D * d3, 0.89D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.89D * d2, 0.2153846153846154D * d3, 0.89D * d2, 0.15384615384615385D * d3, 0.89D * d2, 0.15384615384615385D * d3));
    localPath1.getElements().add(new LineTo(0.1D * d2, 0.15384615384615385D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.1D * d2, 0.15384615384615385D * d3, 0.1D * d2, 0.2153846153846154D * d3, 0.1D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new ClosePath());
    localPath1.getElements().add(new MoveTo(0.075D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.075D * d2, 0.2153846153846154D * d3, 0.075D * d2, 0.15384615384615385D * d3, 0.075D * d2, 0.15384615384615385D * d3));
    localPath1.getElements().add(new LineTo(0.075D * d2, 0.11538461538461539D * d3));
    localPath1.getElements().add(new LineTo(0.915D * d2, 0.11538461538461539D * d3));
    localPath1.getElements().add(new LineTo(0.915D * d2, 0.15384615384615385D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.915D * d2, 0.15384615384615385D * d3, 0.915D * d2, 0.2153846153846154D * d3, 0.915D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.915D * d2, 0.5923076923076923D * d3, 0.725D * d2, 0.8846153846153846D * d3, 0.495D * d2, 0.8846153846153846D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.265D * d2, 0.8846153846153846D * d3, 0.075D * d2, 0.5923076923076923D * d3, 0.075D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new ClosePath());
    LinearGradient localLinearGradient = new LinearGradient(0.495D * d2, 0.11538461538461539D * d3, 0.495D * d2, 0.8846153846153846D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.8D, 0.8D, 0.8D, 0.4D)), new Stop(0.17D, Color.color(0.6D, 0.6D, 0.6D, 0.4D)), new Stop(0.33D, Color.color(0.9882352941D, 0.9882352941D, 0.9882352941D, 0.4D)), new Stop(0.34D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)), new Stop(0.63D, Color.color(0.8D, 0.8D, 0.8D, 0.4D)), new Stop(0.64D, Color.color(0.7960784314D, 0.7960784314D, 0.7960784314D, 0.4D)), new Stop(0.83D, Color.color(0.6D, 0.6D, 0.6D, 0.4D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)) });
    localPath1.setFill(localLinearGradient);
    localPath1.setStroke(null);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.86D * d2, 0.49230769230769234D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.795D * d2, 0.7153846153846154D * d3, 0.66D * d2, 0.8461538461538461D * d3, 0.5D * d2, 0.8461538461538461D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.5D * d2, 0.8461538461538461D * d3, 0.5D * d2, 0.8846153846153846D * d3, 0.5D * d2, 0.8846153846153846D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.665D * d2, 0.8846153846153846D * d3, 0.81D * d2, 0.7384615384615385D * d3, 0.88D * d2, 0.5076923076923077D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.88D * d2, 0.5076923076923077D * d3, 0.86D * d2, 0.49230769230769234D * d3, 0.86D * d2, 0.49230769230769234D * d3));
    localPath2.getElements().add(new ClosePath());
    RadialGradient localRadialGradient = new RadialGradient(0.0D, 0.0D, 0.73D * d2, 0.7615384615384615D * d3, 0.2375D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.5490196078D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) });
    localPath2.setFill(localRadialGradient);
    localPath2.setStroke(null);
    this.glowOff.getChildren().addAll(new Node[] { localPath1, localPath2 });
  }
  
  public void drawGlowOn()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.glowOn.getChildren().clear();
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.1D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.1D * d2, 0.5846153846153846D * d3, 0.275D * d2, 0.8461538461538461D * d3, 0.495D * d2, 0.8461538461538461D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.715D * d2, 0.8461538461538461D * d3, 0.89D * d2, 0.5846153846153846D * d3, 0.89D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.89D * d2, 0.2153846153846154D * d3, 0.89D * d2, 0.15384615384615385D * d3, 0.89D * d2, 0.15384615384615385D * d3));
    localPath1.getElements().add(new LineTo(0.1D * d2, 0.15384615384615385D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.1D * d2, 0.15384615384615385D * d3, 0.1D * d2, 0.2153846153846154D * d3, 0.1D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new ClosePath());
    localPath1.getElements().add(new MoveTo(0.075D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.075D * d2, 0.2153846153846154D * d3, 0.075D * d2, 0.15384615384615385D * d3, 0.075D * d2, 0.15384615384615385D * d3));
    localPath1.getElements().add(new LineTo(0.075D * d2, 0.11538461538461539D * d3));
    localPath1.getElements().add(new LineTo(0.915D * d2, 0.11538461538461539D * d3));
    localPath1.getElements().add(new LineTo(0.915D * d2, 0.15384615384615385D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.915D * d2, 0.15384615384615385D * d3, 0.915D * d2, 0.2153846153846154D * d3, 0.915D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.915D * d2, 0.5923076923076923D * d3, 0.725D * d2, 0.8846153846153846D * d3, 0.495D * d2, 0.8846153846153846D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.265D * d2, 0.8846153846153846D * d3, 0.075D * d2, 0.5923076923076923D * d3, 0.075D * d2, 0.23846153846153847D * d3));
    localPath1.getElements().add(new ClosePath());
    RadialGradient localRadialGradient1 = new RadialGradient(0.0D, 0.0D, 0.5D * d2, 0.5D * d3, 0.4158878504672897D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, (Color)this.glowColors.get(0)), new Stop(0.91D, (Color)this.glowColors.get(1)), new Stop(0.96D, (Color)this.glowColors.get(2)), new Stop(1.0D, (Color)this.glowColors.get(3)) });
    localPath1.setFill(localRadialGradient1);
    localPath1.setStroke(null);
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setRadius(0.15D * d2);
    localDropShadow.setBlurType(BlurType.GAUSSIAN);
    if (localDropShadow.colorProperty().isBound()) {
      localDropShadow.colorProperty().unbind();
    }
    localDropShadow.colorProperty().bind(this.control.glowColorProperty());
    localPath1.setEffect(localDropShadow);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.86D * d2, 0.49230769230769234D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.795D * d2, 0.7153846153846154D * d3, 0.66D * d2, 0.8461538461538461D * d3, 0.5D * d2, 0.8461538461538461D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.5D * d2, 0.8461538461538461D * d3, 0.5D * d2, 0.8846153846153846D * d3, 0.5D * d2, 0.8846153846153846D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.665D * d2, 0.8846153846153846D * d3, 0.81D * d2, 0.7384615384615385D * d3, 0.88D * d2, 0.5076923076923077D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.88D * d2, 0.5076923076923077D * d3, 0.86D * d2, 0.49230769230769234D * d3, 0.86D * d2, 0.49230769230769234D * d3));
    localPath2.getElements().add(new ClosePath());
    RadialGradient localRadialGradient2 = new RadialGradient(0.0D, 0.0D, 0.73D * d2, 0.7615384615384615D * d3, 0.2375D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.5490196078D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) });
    localPath2.setFill(localRadialGradient2);
    localPath2.setStroke(null);
    this.glowOn.getChildren().addAll(new Node[] { localPath1, localPath2 });
  }
  
  public void drawIndicators()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.markers.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.markers.getChildren().add(localRectangle);
    Iterator localIterator = this.control.getMarkers().iterator();
    while (localIterator.hasNext())
    {
      Marker localMarker = (Marker)localIterator.next();
      if ((Double.compare(localMarker.getValue(), this.control.getMinValue()) >= 0) && (Double.compare(localMarker.getValue(), this.control.getMaxValue()) <= 0))
      {
        Group localGroup = createIndicator(d2, localMarker, new Point2D(d2 * 0.4813084112D, d2 * 0.0841121495D));
        localGroup.getTransforms().clear();
        localGroup.setTranslateY(-d2 * 0.35D);
        localGroup.getTransforms().add(Transform.rotate(-this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getX()));
        double d4 = -90.0D + this.control.getRadialRange().ROTATION_OFFSET;
        localGroup.getTransforms().add(Transform.rotate(d4 - (localMarker.getValue() - this.control.getMinValue()) * this.control.getAngleStep(), this.center.getX(), this.center.getX()));
        this.markers.getChildren().add(localGroup);
      }
    }
  }
  
  public void drawThreshold()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.threshold.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.threshold.getChildren().add(localRectangle);
    Path localPath = createTriangleShape(0.03D * d2, 0.03D * d2, false);
    localPath.setStrokeType(StrokeType.CENTERED);
    localPath.setStrokeLineCap(StrokeLineCap.ROUND);
    localPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
    localPath.setStrokeWidth(0.002D * d3);
    localPath.getStyleClass().add("root");
    localPath.setStyle(this.control.getThresholdColor().CSS);
    localPath.getStyleClass().add("threshold-gradient");
    localPath.setTranslateX(0.485D * d2);
    localPath.setTranslateY(0.14D * d2);
    this.threshold.getChildren().addAll(new Node[] { localPath });
    this.threshold.getTransforms().clear();
    this.threshold.setTranslateY(-d2 * 0.35D);
    this.threshold.getTransforms().add(Transform.rotate(-this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getX()));
    double d4 = -90.0D + this.control.getRadialRange().ROTATION_OFFSET;
    this.threshold.getTransforms().add(Transform.rotate(d4 - (this.control.getThreshold() - this.control.getMinValue()) * this.control.getAngleStep(), this.center.getX(), this.center.getX()));
  }
  
  public void drawMinMeasuredIndicator()
  {
    double d1 = this.gaugeBounds.getWidth();
    double d2 = this.gaugeBounds.getWidth();
    this.minMeasured.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.minMeasured.getChildren().add(localRectangle);
    Path localPath = createTriangleShape(0.03D * d1, 0.035D * d1, true);
    localPath.setFill(Color.color(0.0D, 0.0D, 0.8D));
    localPath.setStroke(null);
    localPath.setTranslateX(0.485D * d1);
    localPath.setTranslateY(0.1D * d1);
    this.minMeasured.getChildren().add(localPath);
    this.minMeasured.getTransforms().clear();
    this.minMeasured.setTranslateY(-d1 * 0.35D);
    this.minMeasured.getTransforms().add(Transform.rotate(-this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getX()));
    double d3 = -90.0D + this.control.getRadialRange().ROTATION_OFFSET;
    this.minMeasured.setRotate(d3 - (this.control.getMinMeasuredValue() - this.control.getMinValue()) * this.control.getAngleStep());
  }
  
  public void drawMaxMeasuredIndicator()
  {
    double d1 = this.gaugeBounds.getWidth();
    double d2 = this.gaugeBounds.getWidth();
    this.maxMeasured.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.maxMeasured.getChildren().add(localRectangle);
    Path localPath = createTriangleShape(0.03D * d1, 0.035D * d1, true);
    localPath.setFill(Color.color(0.8D, 0.0D, 0.0D));
    localPath.setStroke(null);
    localPath.setTranslateX(0.485D * d1);
    localPath.setTranslateY(0.1D * d1);
    this.maxMeasured.getChildren().add(localPath);
    this.maxMeasured.getTransforms().clear();
    this.maxMeasured.setTranslateY(-d1 * 0.35D);
    this.maxMeasured.getTransforms().add(Transform.rotate(-this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getX()));
    double d3 = -90.0D + this.control.getRadialRange().ROTATION_OFFSET;
    this.maxMeasured.setRotate(d3 - (this.control.getMaxMeasuredValue() - this.control.getMinValue()) * this.control.getAngleStep());
  }
  
  public void drawPointer()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getWidth();
    this.pointer.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.pointer.getChildren().add(localRectangle);
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
      localPath1.getElements().add(new MoveTo(0.514018691588785D * d2, 0.4719626168224299D * d3));
      localPath1.getElements().add(new LineTo(0.5046728971962616D * d2, 0.46261682242990654D * d3));
      localPath1.getElements().add(new LineTo(0.5046728971962616D * d2, 0.3411214953271028D * d3));
      localPath1.getElements().add(new LineTo(0.5046728971962616D * d2, 0.2336448598130841D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.5046728971962616D * d2, 0.2336448598130841D * d3, 0.5046728971962616D * d2, 0.1308411214953271D * d3, 0.4953271028037383D * d2, 0.1308411214953271D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.49065420560747663D * d2, 0.1308411214953271D * d3, 0.49065420560747663D * d2, 0.2336448598130841D * d3, 0.49065420560747663D * d2, 0.2336448598130841D * d3));
      localPath1.getElements().add(new LineTo(0.49065420560747663D * d2, 0.3411214953271028D * d3));
      localPath1.getElements().add(new LineTo(0.49065420560747663D * d2, 0.46261682242990654D * d3));
      localPath1.getElements().add(new LineTo(0.48130841121495327D * d2, 0.4719626168224299D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.48130841121495327D * d2, 0.4719626168224299D * d3, 0.4672897196261682D * d2, 0.49065420560747663D * d3, 0.4672897196261682D * d2, 0.5D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.4672897196261682D * d2, 0.5186915887850467D * d3, 0.48130841121495327D * d2, 0.5327102803738317D * d3, 0.4953271028037383D * d2, 0.5327102803738317D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.514018691588785D * d2, 0.5327102803738317D * d3, 0.5327102803738317D * d2, 0.5186915887850467D * d3, 0.5327102803738317D * d2, 0.5D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.5327102803738317D * d2, 0.49065420560747663D * d3, 0.514018691588785D * d2, 0.4719626168224299D * d3, 0.514018691588785D * d2, 0.4719626168224299D * d3));
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
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.1261682242990654D * d3));
      localPath1.getElements().add(new LineTo(0.5093457943925234D * d2, 0.13551401869158877D * d3));
      localPath1.getElements().add(new LineTo(0.5327102803738317D * d2, 0.5D * d3));
      localPath1.getElements().add(new LineTo(0.5233644859813084D * d2, 0.602803738317757D * d3));
      localPath1.getElements().add(new LineTo(0.4766355140186916D * d2, 0.602803738317757D * d3));
      localPath1.getElements().add(new LineTo(0.46261682242990654D * d2, 0.5D * d3));
      localPath1.getElements().add(new LineTo(0.48598130841121495D * d2, 0.13551401869158877D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.1261682242990654D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE5: 
      localPath1.getStyleClass().add("pointer5-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(d2 * 0.5D, d3 * 0.4953271028037383D));
      localPath1.getElements().add(new LineTo(d2 * 0.5280373831775701D, d3 * 0.4953271028037383D));
      localPath1.getElements().add(new LineTo(d2 * 0.5D, d3 * 0.14953271028037382D));
      localPath1.getElements().add(new LineTo(d2 * 0.4719626168224299D, d3 * 0.4953271028037383D));
      localPath1.getElements().add(new LineTo(d2 * 0.5D, d3 * 0.4953271028037383D));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE6: 
      localPath1.getStyleClass().add("pointer6-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(d2 * 0.48130841121495327D, d3 * 0.48598130841121495D));
      localPath1.getElements().add(new LineTo(d2 * 0.48130841121495327D, d3 * 0.3925233644859813D));
      localPath1.getElements().add(new LineTo(d2 * 0.48598130841121495D, d3 * 0.3177570093457944D));
      localPath1.getElements().add(new LineTo(d2 * 0.4953271028037383D, d3 * 0.1308411214953271D));
      localPath1.getElements().add(new LineTo(d2 * 0.5046728971962616D, d3 * 0.1308411214953271D));
      localPath1.getElements().add(new LineTo(d2 * 0.514018691588785D, d3 * 0.3177570093457944D));
      localPath1.getElements().add(new LineTo(d2 * 0.5186915887850467D, d3 * 0.3878504672897196D));
      localPath1.getElements().add(new LineTo(d2 * 0.5186915887850467D, d3 * 0.48598130841121495D));
      localPath1.getElements().add(new LineTo(d2 * 0.5046728971962616D, d3 * 0.48598130841121495D));
      localPath1.getElements().add(new LineTo(d2 * 0.5046728971962616D, d3 * 0.3878504672897196D));
      localPath1.getElements().add(new LineTo(d2 * 0.5D, d3 * 0.3177570093457944D));
      localPath1.getElements().add(new LineTo(d2 * 0.4953271028037383D, d3 * 0.3925233644859813D));
      localPath1.getElements().add(new LineTo(d2 * 0.4953271028037383D, d3 * 0.48598130841121495D));
      localPath1.getElements().add(new LineTo(d2 * 0.48130841121495327D, d3 * 0.48598130841121495D));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE7: 
      localPath1.getStyleClass().add("pointer7-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.49065420560747663D * d2, 0.1308411214953271D * d3));
      localPath1.getElements().add(new LineTo(0.4766355140186916D * d2, 0.5D * d3));
      localPath1.getElements().add(new LineTo(0.5186915887850467D * d2, 0.5D * d3));
      localPath1.getElements().add(new LineTo(0.5046728971962616D * d2, 0.1308411214953271D * d3));
      localPath1.getElements().add(new LineTo(0.49065420560747663D * d2, 0.1308411214953271D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE8: 
      localPath1.getStyleClass().add("pointer8-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.4953271028037383D * d2, 0.5327102803738317D * d3));
      localPath1.getElements().add(new LineTo(0.5327102803738317D * d2, 0.5D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.5327102803738317D * d2, 0.5D * d3, 0.5046728971962616D * d2, 0.45794392523364486D * d3, 0.4953271028037383D * d2, 0.14953271028037382D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.49065420560747663D * d2, 0.45794392523364486D * d3, 0.46261682242990654D * d2, 0.5D * d3, 0.46261682242990654D * d2, 0.5D * d3));
      localPath1.getElements().add(new LineTo(0.4953271028037383D * d2, 0.5327102803738317D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE9: 
      localPath1.getStyleClass().add("pointer9-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(d2 * 0.4953271028037383D, d3 * 0.2336448598130841D));
      localPath1.getElements().add(new LineTo(d2 * 0.5046728971962616D, d3 * 0.2336448598130841D));
      localPath1.getElements().add(new LineTo(d2 * 0.514018691588785D, d3 * 0.4392523364485981D));
      localPath1.getElements().add(new LineTo(d2 * 0.48598130841121495D, d3 * 0.4392523364485981D));
      localPath1.getElements().add(new LineTo(d2 * 0.4953271028037383D, d3 * 0.2336448598130841D));
      localPath1.getElements().add(new ClosePath());
      localPath1.getElements().add(new MoveTo(d2 * 0.49065420560747663D, d3 * 0.1308411214953271D));
      localPath1.getElements().add(new LineTo(d2 * 0.4719626168224299D, d3 * 0.4719626168224299D));
      localPath1.getElements().add(new LineTo(d2 * 0.4719626168224299D, d3 * 0.5280373831775701D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.4719626168224299D, d3 * 0.5280373831775701D, d2 * 0.4766355140186916D, d3 * 0.602803738317757D, d2 * 0.4766355140186916D, d3 * 0.602803738317757D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.4766355140186916D, d3 * 0.6074766355140186D, d2 * 0.48130841121495327D, d3 * 0.6074766355140186D, d2 * 0.5D, d3 * 0.6074766355140186D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.5186915887850467D, d3 * 0.6074766355140186D, d2 * 0.5233644859813084D, d3 * 0.6074766355140186D, d2 * 0.5233644859813084D, d3 * 0.602803738317757D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.5233644859813084D, d3 * 0.602803738317757D, d2 * 0.5280373831775701D, d3 * 0.5280373831775701D, d2 * 0.5280373831775701D, d3 * 0.5280373831775701D));
      localPath1.getElements().add(new LineTo(d2 * 0.5280373831775701D, d3 * 0.4719626168224299D));
      localPath1.getElements().add(new LineTo(d2 * 0.5093457943925234D, d3 * 0.1308411214953271D));
      localPath1.getElements().add(new LineTo(d2 * 0.49065420560747663D, d3 * 0.1308411214953271D));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      localPath2.getStyleClass().add("root");
      localPath2.setStyle("-fx-value: " + this.control.getValueColor().CSS);
      localPath2.getStyleClass().add("pointer9-box");
      localPath2.setFillRule(FillRule.EVEN_ODD);
      localPath2.getElements().add(new MoveTo(d2 * 0.4953271028037383D, d3 * 0.21962616822429906D));
      localPath2.getElements().add(new LineTo(d2 * 0.5046728971962616D, d3 * 0.21962616822429906D));
      localPath2.getElements().add(new LineTo(d2 * 0.5046728971962616D, d3 * 0.13551401869158877D));
      localPath2.getElements().add(new LineTo(d2 * 0.4953271028037383D, d3 * 0.13551401869158877D));
      localPath2.getElements().add(new LineTo(d2 * 0.4953271028037383D, d3 * 0.21962616822429906D));
      localPath2.getElements().add(new ClosePath());
      localPath2.setStroke(null);
      break;
    case TYPE10: 
      localPath1.getStyleClass().add("pointer10-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.4953271028037383D * d2, 0.14953271028037382D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.4953271028037383D * d2, 0.14953271028037382D * d3, 0.4439252336448598D * d2, 0.49065420560747663D * d3, 0.4439252336448598D * d2, 0.5D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.4439252336448598D * d2, 0.5327102803738317D * d3, 0.4672897196261682D * d2, 0.5560747663551402D * d3, 0.4953271028037383D * d2, 0.5560747663551402D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.5280373831775701D * d2, 0.5560747663551402D * d3, 0.5560747663551402D * d2, 0.5327102803738317D * d3, 0.5560747663551402D * d2, 0.5D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.5560747663551402D * d2, 0.49065420560747663D * d3, 0.4953271028037383D * d2, 0.14953271028037382D * d3, 0.4953271028037383D * d2, 0.14953271028037382D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE11: 
      localPath1.getStyleClass().add("pointer11-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new LineTo(0.48598130841121495D * d2, 0.5D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.48598130841121495D * d2, 0.5D * d3, 0.48130841121495327D * d2, 0.5841121495327103D * d3, 0.5D * d2, 0.5841121495327103D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.514018691588785D * d2, 0.5841121495327103D * d3, 0.5093457943925234D * d2, 0.5D * d3, 0.5093457943925234D * d2, 0.5D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE12: 
      localPath1.getStyleClass().add("pointer12-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.5D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new LineTo(0.48598130841121495D * d2, 0.5D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.5046728971962616D * d3));
      localPath1.getElements().add(new LineTo(0.5093457943925234D * d2, 0.5D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStrokeType(StrokeType.CENTERED);
      localPath1.setStrokeLineCap(StrokeLineCap.BUTT);
      localPath1.setStrokeLineJoin(StrokeLineJoin.ROUND);
      localPath1.setStrokeWidth(0.002D * d2);
      break;
    case TYPE13: 
      localPath1.getStyleClass().add("pointer13-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.48598130841121495D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.1308411214953271D * d3));
      localPath1.getElements().add(new LineTo(0.5093457943925234D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new LineTo(0.5093457943925234D * d2, 0.5093457943925234D * d3));
      localPath1.getElements().add(new LineTo(0.48598130841121495D * d2, 0.5093457943925234D * d3));
      localPath1.getElements().add(new LineTo(0.48598130841121495D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE14: 
      localPath1.getStyleClass().add("pointer14-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.48598130841121495D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.1308411214953271D * d3));
      localPath1.getElements().add(new LineTo(0.5093457943925234D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new LineTo(0.5093457943925234D * d2, 0.5093457943925234D * d3));
      localPath1.getElements().add(new LineTo(0.48598130841121495D * d2, 0.5093457943925234D * d3));
      localPath1.getElements().add(new LineTo(0.48598130841121495D * d2, 0.16822429906542055D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE15: 
      localPath1.getStyleClass().add("pointer15-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.48D * d2, 0.505D * d3));
      localPath1.getElements().add(new LineTo(0.48D * d2, 0.275D * d3));
      localPath1.getElements().add(new LineTo(0.46D * d2, 0.275D * d3));
      localPath1.getElements().add(new LineTo(0.495D * d2, 0.15D * d3));
      localPath1.getElements().add(new LineTo(0.53D * d2, 0.275D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.275D * d3));
      localPath1.getElements().add(new LineTo(0.515D * d2, 0.505D * d3));
      localPath1.getElements().add(new LineTo(0.48D * d2, 0.505D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE16: 
      localPath1.getStyleClass().add("pointer16-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.495D * d2, 0.625D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.515D * d2, 0.625D * d3, 0.535D * d2, 0.61D * d3, 0.535D * d2, 0.61D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.525D * d2, 0.6D * d3, 0.505D * d2, 0.53D * d3, 0.505D * d2, 0.53D * d3));
      localPath1.getElements().add(new LineTo(0.505D * d2, 0.47D * d3));
      localPath1.getElements().add(new LineTo(0.505D * d2, 0.17D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.165D * d3));
      localPath1.getElements().add(new LineTo(0.5D * d2, 0.13D * d3));
      localPath1.getElements().add(new LineTo(0.495D * d2, 0.13D * d3));
      localPath1.getElements().add(new LineTo(0.495D * d2, 0.165D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.17D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.47D * d3));
      localPath1.getElements().add(new LineTo(0.49D * d2, 0.53D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.49D * d2, 0.53D * d3, 0.47D * d2, 0.6D * d3, 0.465D * d2, 0.61D * d3));
      localPath1.getElements().add(new CubicCurveTo(0.465D * d2, 0.61D * d3, 0.475D * d2, 0.625D * d3, 0.495D * d2, 0.625D * d3));
      localPath1.getElements().add(new ClosePath());
      localPath1.setStroke(null);
      break;
    case TYPE1: 
    default: 
      localPath1.setStyle("-fx-pointer: " + this.control.getValueColor().CSS);
      localPath1.getStyleClass().add("pointer1-gradient");
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(d2 * 0.5186915887850467D, d3 * 0.4719626168224299D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.514018691588785D, d3 * 0.45794392523364486D, d2 * 0.5093457943925234D, d3 * 0.4158878504672897D, d2 * 0.5093457943925234D, d3 * 0.40186915887850466D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.5046728971962616D, d3 * 0.38317757009345793D, d2 * 0.5D, d3 * 0.1308411214953271D, d2 * 0.5D, d3 * 0.1308411214953271D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.5D, d3 * 0.1308411214953271D, d2 * 0.49065420560747663D, d3 * 0.38317757009345793D, d2 * 0.49065420560747663D, d3 * 0.397196261682243D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.49065420560747663D, d3 * 0.4158878504672897D, d2 * 0.48598130841121495D, d3 * 0.45794392523364486D, d2 * 0.48130841121495327D, d3 * 0.4719626168224299D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.4719626168224299D, d3 * 0.48130841121495327D, d2 * 0.4672897196261682D, d3 * 0.49065420560747663D, d2 * 0.4672897196261682D, d3 * 0.5D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.4672897196261682D, d3 * 0.5186915887850467D, d2 * 0.48130841121495327D, d3 * 0.5327102803738317D, d2 * 0.5D, d3 * 0.5327102803738317D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.5186915887850467D, d3 * 0.5327102803738317D, d2 * 0.5327102803738317D, d3 * 0.5186915887850467D, d2 * 0.5327102803738317D, d3 * 0.5D));
      localPath1.getElements().add(new CubicCurveTo(d2 * 0.5327102803738317D, d3 * 0.49065420560747663D, d2 * 0.5280373831775701D, d3 * 0.48130841121495327D, d2 * 0.5186915887850467D, d3 * 0.4719626168224299D));
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
    this.pointer.setTranslateY(-d2 * 0.35D);
    this.pointer.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getX()));
    this.pointer.setCache(true);
    this.pointer.setCacheHint(CacheHint.ROTATE);
  }
  
  public void drawForeground()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.foreground.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.foreground.getChildren().add(localRectangle);
    Path localPath = new Path();
    switch (this.control.getForegroundType())
    {
    case TYPE2: 
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(0.495D * d2, 0.36923076923076925D * d3));
      localPath.getElements().add(new CubicCurveTo(0.65D * d2, 0.49230769230769234D * d3, 0.73D * d2, 0.4846153846153846D * d3, 0.87D * d2, 0.4846153846153846D * d3));
      localPath.getElements().add(new CubicCurveTo(0.765D * d2, 0.8D * d3, 0.52D * d2, 0.9461538461538461D * d3, 0.31D * d2, 0.7769230769230769D * d3));
      localPath.getElements().add(new CubicCurveTo(0.155D * d2, 0.6615384615384615D * d3, 0.075D * d2, 0.4307692307692308D * d3, 0.08D * d2, 0.19230769230769232D * d3));
      localPath.getElements().add(new CubicCurveTo(0.085D * d2, 0.1D * d3, 0.35D * d2, 0.25384615384615383D * d3, 0.495D * d2, 0.36923076923076925D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.setFill(new LinearGradient(0.495D * d2, 0.23076923076923078D * d3, 0.3072113748856438D * d2, 0.7741215956054937D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0980392157D)) }));
      localPath.setStroke(null);
      this.foreground.getChildren().addAll(new Node[] { localPath });
      break;
    case TYPE3: 
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(0.08D * d2, 0.26153846153846155D * d3));
      localPath.getElements().add(new CubicCurveTo(0.09D * d2, 0.6D * d3, 0.27D * d2, 0.8692307692307693D * d3, 0.495D * d2, 0.8692307692307693D * d3));
      localPath.getElements().add(new CubicCurveTo(0.72D * d2, 0.8692307692307693D * d3, 0.9D * d2, 0.6D * d3, 0.91D * d2, 0.26153846153846155D * d3));
      localPath.getElements().add(new CubicCurveTo(0.865D * d2, 0.5538461538461539D * d3, 0.695D * d2, 0.7692307692307693D * d3, 0.495D * d2, 0.7692307692307693D * d3));
      localPath.getElements().add(new CubicCurveTo(0.295D * d2, 0.7692307692307693D * d3, 0.125D * d2, 0.5538461538461539D * d3, 0.08D * d2, 0.26153846153846155D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.setFill(new RadialGradient(0.0D, 0.0D, 0.495D * d2, 0.23076923076923078D * d3, 0.415D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.9D, Color.color(1.0D, 1.0D, 1.0D, 0.2274509804D)), new Stop(0.96D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
      localPath.setStroke(null);
      this.foreground.getChildren().addAll(new Node[] { localPath });
      break;
    case TYPE4: 
    case TYPE5: 
    case TYPE1: 
    default: 
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(0.075D * d2, 0.11538461538461539D * d3));
      localPath.getElements().add(new CubicCurveTo(0.145D * d2, 0.11538461538461539D * d3, 0.855D * d2, 0.11538461538461539D * d3, 0.915D * d2, 0.11538461538461539D * d3));
      localPath.getElements().add(new CubicCurveTo(0.915D * d2, 0.15384615384615385D * d3, 0.735D * d2, 0.2076923076923077D * d3, 0.495D * d2, 0.2076923076923077D * d3));
      localPath.getElements().add(new CubicCurveTo(0.255D * d2, 0.2076923076923077D * d3, 0.075D * d2, 0.15384615384615385D * d3, 0.075D * d2, 0.11538461538461539D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.setFill(new LinearGradient(0.5D * d2, 0.11538461538461539D * d3, 0.5D * d2, 0.2076923076923077D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2470588235D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
      localPath.setStroke(null);
      this.foreground.getChildren().addAll(new Node[] { localPath });
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/RadialHalfSSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */