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
import javafx.animation.Transition;
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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
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
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.LinearBehavior;
import jfxtras.labs.scene.control.gauge.ColorDef;
import jfxtras.labs.scene.control.gauge.Gauge.BackgroundDesign;
import jfxtras.labs.scene.control.gauge.Gauge.FrameDesign;
import jfxtras.labs.scene.control.gauge.Gauge.NumberFormat;
import jfxtras.labs.scene.control.gauge.Gauge.ThresholdColor;
import jfxtras.labs.scene.control.gauge.LcdDesign;
import jfxtras.labs.scene.control.gauge.Linear;
import jfxtras.labs.scene.control.gauge.Marker;
import jfxtras.labs.scene.control.gauge.Section;
import jfxtras.labs.util.ConicalGradient;
import jfxtras.labs.util.Util;

public class LinearSkin
  extends GaugeSkinBase<Linear, LinearBehavior>
{
  private static final Rectangle MIN_SIZE = new Rectangle(25.0D, 50.0D);
  private static final Rectangle PREF_SIZE = new Rectangle(170.0D, 340.0D);
  private static final Rectangle MAX_SIZE = new Rectangle(512.0D, 1024.0D);
  private Linear control;
  private Rectangle gaugeBounds;
  private Point2D framelessOffset;
  private Group frame;
  private Group background;
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
  private Text lcdValueString;
  private Text lcdUnitString;
  private Group lcdThresholdIndicator;
  private Group threshold;
  private Group minMeasured;
  private Group maxMeasured;
  private Group bar;
  private Rectangle currentBar;
  private Rectangle currentBarHl;
  private Group ledOff;
  private Group ledOn;
  private Group userLedOff;
  private Group userLedOn;
  private Group foreground;
  private DoubleProperty currentValue;
  private double stepsize;
  private double formerValue;
  private DoubleProperty lcdValue;
  private DoubleProperty currentLcdValue;
  private FadeTransition glowPulse;
  private Transition toValueAnimation;
  private AnimationTimer ledTimer;
  private boolean ledOnVisible;
  private long lastLedTimerCall;
  private AnimationTimer userLedTimer;
  private boolean userLedOnVisible;
  private long lastUserLedTimerCall;
  private boolean isDirty;
  private boolean initialized;
  
  public LinearSkin(Linear paramLinear)
  {
    super(paramLinear, new LinearBehavior(paramLinear));
    this.control = paramLinear;
    this.gaugeBounds = new Rectangle(150.0D, 350.0D);
    this.framelessOffset = new Point2D(0.0D, 0.0D);
    this.frame = new Group();
    this.background = new Group();
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
    this.threshold = new Group();
    this.minMeasured = new Group();
    this.maxMeasured = new Group();
    this.bar = new Group();
    this.ledOff = new Group();
    this.ledOn = new Group();
    this.userLedOff = new Group();
    this.userLedOn = new Group();
    this.foreground = new Group();
    this.currentValue = new SimpleDoubleProperty(0.0D);
    this.lcdValue = new SimpleDoubleProperty(0.0D);
    this.currentLcdValue = new SimpleDoubleProperty(0.0D);
    this.glowPulse = new FadeTransition(Duration.millis(800.0D), this.glowOn);
    this.toValueAnimation = new Transition()
    {
      protected void interpolate(double paramAnonymousDouble)
      {
        LinearSkin.this.currentValue.set(LinearSkin.this.formerValue + (LinearSkin.this.control.getValue() - LinearSkin.this.formerValue) * paramAnonymousDouble);
      }
    };
    this.ledTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > LinearSkin.this.lastLedTimerCall + LinearSkin.this.getBlinkInterval())
        {
          LinearSkin.access$480(LinearSkin.this, 1);
          if (LinearSkin.this.ledOnVisible) {
            LinearSkin.this.ledOn.setOpacity(1.0D);
          } else {
            LinearSkin.this.ledOn.setOpacity(0.0D);
          }
          LinearSkin.this.lastLedTimerCall = paramAnonymousLong;
        }
      }
    };
    this.lastLedTimerCall = 0L;
    this.ledOnVisible = false;
    this.userLedTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > LinearSkin.this.lastUserLedTimerCall + LinearSkin.this.getBlinkInterval())
        {
          LinearSkin.access$780(LinearSkin.this, 1);
          if (LinearSkin.this.userLedOnVisible) {
            LinearSkin.this.userLedOn.setOpacity(1.0D);
          } else {
            LinearSkin.this.userLedOn.setOpacity(0.0D);
          }
          LinearSkin.this.lastUserLedTimerCall = paramAnonymousLong;
        }
      }
    };
    this.lastUserLedTimerCall = 0L;
    this.userLedOnVisible = false;
    this.isDirty = false;
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
    addBindings();
    addListeners();
    this.control.recalcRange();
    this.control.setMinMeasuredValue(this.control.getMaxValue());
    this.control.setMaxMeasuredValue(this.control.getMinValue());
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
    if (this.lcd.visibleProperty().isBound()) {
      this.lcd.visibleProperty().unbind();
    }
    this.lcd.visibleProperty().bind(this.control.lcdVisibleProperty());
    if (this.lcdContent.visibleProperty().isBound()) {
      this.lcdContent.visibleProperty().unbind();
    }
    this.lcdContent.visibleProperty().bind(this.control.lcdVisibleProperty());
    if (this.foreground.visibleProperty().isBound()) {
      this.foreground.visibleProperty().unbind();
    }
    this.foreground.visibleProperty().bind(this.control.foregroundVisibleProperty());
  }
  
  private void addListeners()
  {
    this.control.getMarkers().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        LinearSkin.this.drawIndicators();
      }
    });
    this.control.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        LinearSkin.this.formerValue = paramAnonymousNumber1.doubleValue();
        if (LinearSkin.this.toValueAnimation.getStatus() != Animation.Status.STOPPED) {
          LinearSkin.this.toValueAnimation.stop();
        }
        if ((paramAnonymousNumber2.doubleValue() > paramAnonymousNumber1.doubleValue() - LinearSkin.this.control.getRedrawToleranceValue()) && (paramAnonymousNumber2.doubleValue() < paramAnonymousNumber1.doubleValue() + LinearSkin.this.control.getRedrawToleranceValue())) {
          return;
        }
        if (LinearSkin.this.control.isValueAnimationEnabled())
        {
          LinearSkin.this.toValueAnimation.setInterpolator(Interpolator.SPLINE(0.5D, 0.4D, 0.4D, 1.0D));
          LinearSkin.this.toValueAnimation.play();
        }
        else
        {
          LinearSkin.this.currentValue.set(paramAnonymousNumber2.doubleValue());
          LinearSkin.this.updateBar();
        }
        LinearSkin.this.checkMarkers(LinearSkin.this.control, paramAnonymousNumber1.doubleValue(), paramAnonymousNumber2.doubleValue());
      }
    });
    this.currentValue.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        LinearSkin.this.currentLcdValue.set(LinearSkin.this.control.isLcdValueCoupled() ? LinearSkin.this.currentValue.get() : LinearSkin.this.control.getLcdValue());
        if (Double.compare(LinearSkin.this.currentValue.get(), LinearSkin.this.control.getMinMeasuredValue()) < 0) {
          LinearSkin.this.control.setMinMeasuredValue(LinearSkin.this.currentValue.get());
        }
        if (Double.compare(LinearSkin.this.currentValue.get(), LinearSkin.this.control.getMaxMeasuredValue()) > 0) {
          LinearSkin.this.control.setMaxMeasuredValue(LinearSkin.this.currentValue.get());
        }
        if (LinearSkin.this.control.isThresholdBehaviorInverted()) {
          LinearSkin.this.control.setThresholdExceeded(LinearSkin.this.currentValue.get() < LinearSkin.this.control.getThreshold());
        } else {
          LinearSkin.this.control.setThresholdExceeded(LinearSkin.this.currentValue.get() > LinearSkin.this.control.getThreshold());
        }
        if (!LinearSkin.this.control.isThresholdExceeded()) {
          LinearSkin.this.ledOn.setOpacity(0.0D);
        }
        if (LinearSkin.this.control.isLcdVisible()) {
          LinearSkin.this.drawLcdContent();
        }
        if (!LinearSkin.this.control.getSections().isEmpty())
        {
          Iterator localIterator = LinearSkin.this.control.getSections().iterator();
          while (localIterator.hasNext())
          {
            Section localSection = (Section)localIterator.next();
            if ((Double.compare(LinearSkin.this.currentValue.get(), localSection.getStart()) >= 0) && (Double.compare(LinearSkin.this.currentValue.get(), localSection.getStop()) <= 0))
            {
              LinearSkin.this.currentBar.setStyle("-fx-value: " + localSection.getCssColor());
              break;
            }
            LinearSkin.this.currentBar.setStyle("-fx-value: " + LinearSkin.this.control.getValueColor().CSS);
          }
          if (LinearSkin.this.control.getWidth() <= LinearSkin.this.control.getHeight()) {
            LinearSkin.this.currentBar.getStyleClass().add("bar-vertical-solid");
          } else {
            LinearSkin.this.currentBar.getStyleClass().add("bar-horizontal-solid");
          }
        }
        LinearSkin.this.updateBar();
      }
    });
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("FRAME_DESIGN".equals(paramString))
    {
      drawFrame();
    }
    else if ("BACKGROUND_DESIGN".equals(paramString))
    {
      drawBackground();
      drawTickmarks();
    }
    else if ("VALUE_COLOR".equals(paramString))
    {
      drawBar();
    }
    else if ("FOREGROUND_TYPE".equals(paramString))
    {
      drawForeground();
    }
    else if ("LCD_DESIGN".equals(paramString))
    {
      drawLcd();
      drawLcdContent();
    }
    else if ("LCD_NUMBER_SYSTEM".equals(paramString))
    {
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
      drawTickmarks();
    }
    else if ("MIN_MEASURED_VALUE".equals(paramString))
    {
      if (this.control.getPrefWidth() <= this.control.getPrefHeight()) {
        this.minMeasured.setTranslateY(-(this.control.getMinMeasuredValue() - this.control.getMinValue()) * this.stepsize);
      } else {
        this.minMeasured.setTranslateX(Math.abs((this.control.getMinMeasuredValue() - this.control.getMinValue()) * this.stepsize));
      }
    }
    else if ("MAX_MEASURED_VALUE".equals(paramString))
    {
      if (this.control.getPrefWidth() <= this.control.getPrefHeight()) {
        this.maxMeasured.setTranslateY(-(this.control.getMaxMeasuredValue() - this.control.getMinValue()) * this.stepsize);
      } else {
        this.maxMeasured.setTranslateX(Math.abs((this.control.getMaxMeasuredValue() - this.control.getMinValue()) * this.stepsize));
      }
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
    else if ((!"AREAS".equals(paramString)) && (!"SECTIONS".equals(paramString)))
    {
      if ("MARKERS".equals(paramString)) {
        drawIndicators();
      } else if ("PREF_WIDTH".equals(paramString)) {
        repaint();
      } else if ("PREF_HEIGHT".equals(paramString)) {
        repaint();
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
      drawFrame();
      drawBackground();
      drawTitleAndUnit();
      drawTickmarks();
      drawLed();
      drawUserLed();
      drawThreshold();
      drawGlowOff();
      drawGlowOn();
      drawMinMeasuredIndicator();
      drawMaxMeasuredIndicator();
      drawIndicators();
      drawLcd();
      drawLcdContent();
      drawBar();
      drawForeground();
      getChildren().setAll(new Node[] { this.frame, this.background, this.ledOff, this.ledOn, this.userLedOff, this.userLedOn, this.titleAndUnit, this.tickmarks, this.threshold, this.glowOff, this.glowOn, this.minMeasured, this.maxMeasured, this.markers, this.lcd, this.lcdContent, this.bar, this.foreground });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public Linear getSkinnable()
  {
    return this.control;
  }
  
  public void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d;
    if (paramDouble < getPrefHeight()) {
      d = PREF_SIZE.getWidth();
    } else {
      d = PREF_SIZE.getHeight();
    }
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d;
    if (paramDouble < getPrefHeight()) {
      d = PREF_SIZE.getHeight();
    } else {
      d = PREF_SIZE.getWidth();
    }
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefHeight(d);
  }
  
  protected double computeMinWidth(double paramDouble)
  {
    double d;
    if (getPrefWidth() < getPrefHeight()) {
      d = Math.max(MIN_SIZE.getWidth(), paramDouble);
    } else {
      d = Math.max(MIN_SIZE.getHeight(), paramDouble);
    }
    return super.computeMinWidth(d);
  }
  
  protected double computeMinHeight(double paramDouble)
  {
    double d;
    if (getPrefWidth() < getPrefHeight()) {
      d = Math.max(MIN_SIZE.getHeight(), paramDouble);
    } else {
      d = Math.max(MIN_SIZE.getWidth(), paramDouble);
    }
    return super.computeMinHeight(d);
  }
  
  protected double computeMaxWidth(double paramDouble)
  {
    double d;
    if (getPrefWidth() < getPrefHeight()) {
      d = Math.max(MAX_SIZE.getWidth(), paramDouble);
    } else {
      d = Math.max(MAX_SIZE.getHeight(), paramDouble);
    }
    return super.computeMaxWidth(d);
  }
  
  protected double computeMaxHeight(double paramDouble)
  {
    double d;
    if (getPrefWidth() < getPrefHeight()) {
      d = Math.max(MAX_SIZE.getHeight(), paramDouble);
    } else {
      d = Math.max(MAX_SIZE.getWidth(), paramDouble);
    }
    return super.computeMaxHeight(d);
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
      double d = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
      this.gaugeBounds.setWidth(this.control.getPrefWidth() + d * 0.168224299D + 2.0D);
      this.gaugeBounds.setHeight(this.control.getPrefHeight() + d * 0.168224299D + 2.0D);
      this.framelessOffset = new Point2D(-d * 0.0841121495D - 1.0D, -d * 0.0841121495D - 1.0D);
    }
  }
  
  public void drawFrame()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.frame.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
    localRectangle1.setArcWidth(0.05D * d1);
    localRectangle1.setArcHeight(0.05D * d1);
    Rectangle localRectangle2 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle2.setArcWidth(0.09333333333333334D * d1);
    localRectangle2.setArcHeight(0.09333333333333334D * d1);
    localRectangle2.setFill(Color.color(0.5176470588D, 0.5176470588D, 0.5176470588D, 1.0D));
    localRectangle2.setStroke(null);
    this.frame.getChildren().add(localRectangle2);
    Rectangle localRectangle3 = new Rectangle(1.0D, 1.0D, d2 - 2.0D, d3 - 2.0D);
    localRectangle3.setArcWidth(0.08D * d1);
    localRectangle3.setArcHeight(0.08D * d1);
    localRectangle3.setStroke(null);
    Rectangle localRectangle4 = new Rectangle(0.0841121495D * d1, 0.0841121495D * d1, d2 - 0.168224299D * d1, d3 - 0.168224299D * d1);
    localRectangle4.setArcWidth(0.05D * d1);
    localRectangle4.setArcHeight(0.05D * d1);
    localRectangle4.setFill(Color.color(0.6D, 0.6D, 0.6D, 0.8D));
    localRectangle4.setStroke(null);
    switch (this.control.getFrameDesign())
    {
    case BLACK_METAL: 
      ConicalGradient localConicalGradient1 = new ConicalGradient(new Point2D(localRectangle3.getLayoutBounds().getWidth() / 2.0D, localRectangle3.getLayoutBounds().getHeight() / 2.0D), new Stop[] { new Stop(0.0D, Color.rgb(254, 254, 254)), new Stop(0.125D, Color.rgb(0, 0, 0)), new Stop(0.3472D, Color.rgb(153, 153, 153)), new Stop(0.5D, Color.rgb(0, 0, 0)), new Stop(0.6805D, Color.rgb(153, 153, 153)), new Stop(0.875D, Color.rgb(0, 0, 0)), new Stop(1.0D, Color.rgb(254, 254, 254)) });
      localRectangle3.setFill(localConicalGradient1.apply(localRectangle3));
      localRectangle3.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle4 });
      break;
    case SHINY_METAL: 
      ConicalGradient localConicalGradient2 = new ConicalGradient(new Point2D(localRectangle3.getLayoutBounds().getWidth() / 2.0D, localRectangle3.getLayoutBounds().getHeight() / 2.0D), new Stop[] { new Stop(0.0D, Color.rgb(254, 254, 254)), new Stop(0.125D, Util.darker(this.control.getFrameBaseColor(), 0.15D)), new Stop(0.25D, this.control.getFrameBaseColor().darker()), new Stop(0.3472D, this.control.getFrameBaseColor().brighter()), new Stop(0.5D, this.control.getFrameBaseColor().darker().darker()), new Stop(0.6527D, this.control.getFrameBaseColor().brighter()), new Stop(0.75D, this.control.getFrameBaseColor().darker()), new Stop(0.875D, Util.darker(this.control.getFrameBaseColor(), 0.15D)), new Stop(1.0D, Color.rgb(254, 254, 254)) });
      localRectangle3.setFill(localConicalGradient2.apply(localRectangle3));
      localRectangle3.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle4 });
      break;
    case CHROME: 
      ConicalGradient localConicalGradient3 = new ConicalGradient(new Point2D(localRectangle3.getLayoutBounds().getWidth() / 2.0D, localRectangle3.getLayoutBounds().getHeight() / 2.0D), new Stop[] { new Stop(0.0D, Color.WHITE), new Stop(0.09D, Color.WHITE), new Stop(0.12D, Color.rgb(136, 136, 138)), new Stop(0.16D, Color.rgb(164, 185, 190)), new Stop(0.25D, Color.rgb(158, 179, 182)), new Stop(0.29D, Color.rgb(112, 112, 112)), new Stop(0.33D, Color.rgb(221, 227, 227)), new Stop(0.38D, Color.rgb(155, 176, 179)), new Stop(0.48D, Color.rgb(156, 176, 177)), new Stop(0.52D, Color.rgb(254, 255, 255)), new Stop(0.63D, Color.WHITE), new Stop(0.68D, Color.rgb(156, 180, 180)), new Stop(0.8D, Color.rgb(198, 209, 211)), new Stop(0.83D, Color.rgb(246, 248, 247)), new Stop(0.87D, Color.rgb(204, 216, 216)), new Stop(0.97D, Color.rgb(164, 188, 190)), new Stop(1.0D, Color.WHITE) });
      localRectangle3.setFill(localConicalGradient3.apply(localRectangle3));
      localRectangle3.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle4 });
      break;
    case GLOSSY_METAL: 
      localRectangle3.setFill(new LinearGradient(0.4714285714285714D * d2, 0.014285714285714285D * d3, 0.47142857142857153D * d2, 0.9785714285714285D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.9764705882D, 0.9764705882D, 0.9764705882D, 1.0D)), new Stop(0.1D, Color.color(0.7843137255D, 0.7647058824D, 0.7490196078D, 1.0D)), new Stop(0.26D, Color.WHITE), new Stop(0.73D, Color.color(0.1137254902D, 0.1137254902D, 0.1137254902D, 1.0D)), new Stop(1.0D, Color.color(0.8196078431D, 0.8196078431D, 0.8196078431D, 1.0D)) }));
      Rectangle localRectangle5 = new Rectangle(0.08571428571428572D * d2, 0.08571428571428572D * d3, 0.8285714285714286D * d2, 0.8285714285714286D * d3);
      localRectangle5.setArcWidth(0.05714285714285714D * d1);
      localRectangle5.setArcHeight(0.05714285714285714D * d1);
      localRectangle5.setFill(new LinearGradient(0.0D, localRectangle5.getLayoutBounds().getMinY(), 0.0D, localRectangle5.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.9764705882D, 0.9764705882D, 0.9764705882D, 1.0D)), new Stop(0.23D, Color.color(0.7843137255D, 0.7647058824D, 0.7490196078D, 1.0D)), new Stop(0.36D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(0.59D, Color.color(0.1137254902D, 0.1137254902D, 0.1137254902D, 1.0D)), new Stop(0.76D, Color.color(0.7843137255D, 0.7607843137D, 0.7529411765D, 1.0D)), new Stop(1.0D, Color.color(0.8196078431D, 0.8196078431D, 0.8196078431D, 1.0D)) }));
      Rectangle localRectangle6 = new Rectangle(localRectangle4.getX() - 2.0D, localRectangle4.getY() - 2.0D, localRectangle4.getWidth() + 4.0D, localRectangle4.getHeight() + 4.0D);
      localRectangle6.setArcWidth(localRectangle4.getArcWidth() + 1.0D);
      localRectangle6.setArcHeight(localRectangle4.getArcHeight() + 1.0D);
      localRectangle6.setFill(Color.web("#F6F6F6"));
      Rectangle localRectangle7 = new Rectangle(localRectangle6.getX() + 2.0D, localRectangle6.getY() + 2.0D, localRectangle6.getWidth() - 4.0D, localRectangle6.getHeight() - 4.0D);
      localRectangle7.setArcWidth(localRectangle6.getArcWidth() - 1.0D);
      localRectangle7.setArcHeight(localRectangle6.getArcHeight() - 1.0D);
      localRectangle7.setFill(Color.web("#333333"));
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle5, localRectangle6, localRectangle7 });
      break;
    case DARK_GLOSSY: 
      localRectangle3.setFill(new LinearGradient(0.8551401869158879D * d2, 0.14953271028037382D * d3, 0.15794611761513314D * d2, 0.8467267795811287D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.3254901961D, 0.3254901961D, 0.3254901961D, 1.0D)), new Stop(0.08D, Color.color(0.9960784314D, 0.9960784314D, 1.0D, 1.0D)), new Stop(0.52D, Color.color(0.0D, 0.0D, 0.0D, 1.0D)), new Stop(0.55D, Color.color(0.0196078431D, 0.0235294118D, 0.0196078431D, 1.0D)), new Stop(0.84D, Color.color(0.9725490196D, 0.9803921569D, 0.9764705882D, 1.0D)), new Stop(0.99D, Color.color(0.3254901961D, 0.3254901961D, 0.3254901961D, 1.0D)), new Stop(1.0D, Color.color(0.3254901961D, 0.3254901961D, 0.3254901961D, 1.0D)) }));
      Rectangle localRectangle8 = new Rectangle(0.08571428571428572D * d2, 0.08571428571428572D * d3, 0.8285714285714286D * d2, 0.8285714285714286D * d3);
      localRectangle8.setArcWidth(0.05714285714285714D * d1);
      localRectangle8.setArcHeight(0.05714285714285714D * d1);
      localRectangle8.setFill(new LinearGradient(0.5D * d2, 0.014018691588785047D * d3, 0.5D * d2, 0.985981308411215D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.2588235294D, 0.2588235294D, 0.2588235294D, 1.0D)), new Stop(0.42D, Color.color(0.2588235294D, 0.2588235294D, 0.2588235294D, 1.0D)), new Stop(1.0D, Color.color(0.0509803922D, 0.0509803922D, 0.0509803922D, 1.0D)) }));
      localRectangle8.setStroke(null);
      Rectangle localRectangle9 = new Rectangle(localRectangle3.getX(), localRectangle3.getY(), localRectangle3.getWidth(), localRectangle3.getHeight() * 0.5D);
      localRectangle9.setArcWidth(localRectangle3.getArcWidth());
      localRectangle9.setArcHeight(localRectangle3.getArcHeight());
      localRectangle9.setFill(new LinearGradient(0.5D * d2, 0.014018691588785047D * d3, 0.5D * d2, 0.5280373831775701D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(0.26D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(0.26009998D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
      localRectangle9.setStroke(null);
      Rectangle localRectangle10 = new Rectangle(localRectangle4.getX() - 2.0D, localRectangle4.getY() - 2.0D, localRectangle4.getWidth() + 4.0D, localRectangle4.getHeight() + 4.0D);
      localRectangle10.setArcWidth(localRectangle4.getArcWidth() + 1.0D);
      localRectangle10.setArcHeight(localRectangle4.getArcHeight() + 1.0D);
      localRectangle10.setFill(new LinearGradient(0.8037383177570093D * d2, 0.1822429906542056D * d3, 0.18584594354259637D * d2, 0.8001353648686187D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.6745098039D, 0.6745098039D, 0.6784313725D, 1.0D)), new Stop(0.08D, Color.color(0.9960784314D, 0.9960784314D, 1.0D, 1.0D)), new Stop(0.52D, Color.color(0.0D, 0.0D, 0.0D, 1.0D)), new Stop(0.55D, Color.color(0.0196078431D, 0.0235294118D, 0.0196078431D, 1.0D)), new Stop(0.91D, Color.color(0.9725490196D, 0.9803921569D, 0.9764705882D, 1.0D)), new Stop(0.99D, Color.color(0.6980392157D, 0.6980392157D, 0.6980392157D, 1.0D)), new Stop(1.0D, Color.color(0.6980392157D, 0.6980392157D, 0.6980392157D, 1.0D)) }));
      localRectangle10.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle8, localRectangle9, localRectangle10 });
      break;
    default: 
      ImageView localImageView = new ImageView();
      localImageView.setVisible(false);
      localRectangle3.getStyleClass().add(this.control.getFrameDesign().CSS);
      localRectangle3.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle4 });
    }
    this.frame.setCache(true);
  }
  
  public void drawBackground()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.background.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    this.background.getChildren().add(localRectangle1);
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.2D * d1);
    localInnerShadow.setHeight(0.2D * d1);
    localInnerShadow.setOffsetY(0.03D * d1);
    localInnerShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.7D));
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    LinearGradient localLinearGradient = new LinearGradient(0.0D, 0.0D, d2, 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 0.6D)), new Stop(0.4D, Color.color(0.0D, 0.0D, 0.0D, 0.0D)), new Stop(0.6D, Color.color(0.0D, 0.0D, 0.0D, 0.0D)), new Stop(1.0D, Color.color(0.0D, 0.0D, 0.0D, 0.6D)) });
    Rectangle localRectangle2 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
    localRectangle2.setArcWidth(0.05D * d1);
    localRectangle2.setArcHeight(0.05D * d1);
    localRectangle2.setStroke(null);
    Rectangle localRectangle3 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
    localRectangle3.setArcWidth(0.05D * d1);
    localRectangle3.setArcHeight(0.05D * d1);
    Object localObject1;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    Object localObject5;
    Object localObject6;
    switch (this.control.getBackgroundDesign())
    {
    case STAINLESS: 
      localObject1 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      localObject2 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#E2E2E2").getBrightness());
      localObject3 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#B2B2B4").getBrightness());
      localObject4 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#ACACAE").getBrightness());
      localObject5 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      localObject6 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#6E6E70").getBrightness());
      Color localColor1 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#6E6E70").getBrightness());
      Color localColor2 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      Color localColor3 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#6E6E70").getBrightness());
      Color localColor4 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#6E6E70").getBrightness());
      Color localColor5 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      Color localColor6 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#ACACAE").getBrightness());
      Color localColor7 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#B2B2B4").getBrightness());
      Color localColor8 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#E2E2E2").getBrightness());
      Color localColor9 = Color.hsb(this.control.getTextureColor().getHue(), this.control.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      ConicalGradient localConicalGradient = new ConicalGradient(new Point2D(d1 / 2.0D, d1 / 2.0D), new Stop[] { new Stop(0.0D, (Color)localObject1), new Stop(0.03D, (Color)localObject2), new Stop(0.1D, (Color)localObject3), new Stop(0.14D, (Color)localObject4), new Stop(0.24D, (Color)localObject5), new Stop(0.33D, (Color)localObject6), new Stop(0.38D, localColor1), new Stop(0.5D, localColor2), new Stop(0.62D, localColor3), new Stop(0.67D, localColor4), new Stop(0.76D, localColor5), new Stop(0.81D, localColor6), new Stop(0.85D, localColor7), new Stop(0.97D, localColor8), new Stop(1.0D, localColor9) });
      localRectangle2.setFill(localConicalGradient.apply(localRectangle2));
      localRectangle2.setEffect(localInnerShadow);
      this.background.getChildren().addAll(new Node[] { localRectangle2 });
      break;
    case CARBON: 
      localRectangle2.setFill(Util.createCarbonPattern());
      localRectangle2.setStroke(null);
      Rectangle localRectangle4 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
      localRectangle4.setArcWidth(0.05D * d1);
      localRectangle4.setArcHeight(0.05D * d1);
      localRectangle4.setStroke(null);
      localRectangle4.setFill(new LinearGradient(localRectangle4.getLayoutBounds().getMinX(), 0.0D, localRectangle4.getLayoutBounds().getMaxX(), 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 0.5D)), new Stop(0.4D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.6D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(1.0D, Color.color(0.0D, 0.0D, 0.0D, 0.5D)) }));
      localRectangle4.setStroke(null);
      this.background.getChildren().addAll(new Node[] { localRectangle2, localRectangle4 });
      break;
    case PUNCHED_SHEET: 
      localRectangle2.setFill(Util.createPunchedSheetPattern(this.control.getTextureColor()));
      localRectangle2.setStroke(null);
      Rectangle localRectangle5 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
      localRectangle5.setArcWidth(0.05D * d1);
      localRectangle5.setArcHeight(0.05D * d1);
      localRectangle5.setFill(new LinearGradient(localRectangle5.getLayoutBounds().getMinX(), 0.0D, localRectangle5.getLayoutBounds().getMaxX(), 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 0.5D)), new Stop(0.4D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.6D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(1.0D, Color.color(0.0D, 0.0D, 0.0D, 0.5D)) }));
      localRectangle5.setStroke(null);
      this.background.getChildren().addAll(new Node[] { localRectangle2, localRectangle5 });
      break;
    case NOISY_PLASTIC: 
      Rectangle localRectangle6 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
      localRectangle6.setArcWidth(0.05D * d1);
      localRectangle6.setArcHeight(0.05D * d1);
      localRectangle6.setFill(new LinearGradient(0.0D, localRectangle6.getLayoutY(), 0.0D, localRectangle6.getLayoutBounds().getHeight(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Util.brighter(this.control.getTextureColor(), 0.15D)), new Stop(1.0D, Util.darker(this.control.getTextureColor(), 0.15D)) }));
      localRectangle6.setStroke(null);
      localRectangle6.setEffect(localInnerShadow);
      localRectangle2.setFill(Util.applyNoisyBackground(localRectangle2, this.control.getTextureColor()));
      this.background.getChildren().addAll(new Node[] { localRectangle6, localRectangle2 });
      break;
    case BRUSHED_METAL: 
      localRectangle2.setFill(Util.applyBrushedMetalBackground(localRectangle2, this.control.getTextureColor()));
      localRectangle2.setEffect(localInnerShadow);
      this.background.getChildren().addAll(new Node[] { localRectangle2 });
      break;
    default: 
      localRectangle2.setStyle(this.control.getSimpleGradientBaseColorString());
      localRectangle2.getStyleClass().add(this.control.getBackgroundDesign().CSS_BACKGROUND);
      localRectangle2.setEffect(localInnerShadow);
      this.background.getChildren().addAll(new Node[] { localRectangle2 });
    }
    if (d2 <= d3)
    {
      localObject1 = new Rectangle(0.42D * d2 + 2.0D, 0.1657142857142857D * d3, 0.16D * d2 - 3.0D, 0.6714285714D * d3);
      localObject2 = new LinearGradient(0.5D * d2, 0.1657142857142857D * d3, 0.5D * d2, 0.8371428571428572D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.5D, Color.color(1.0D, 1.0D, 1.0D, 0.1490196078D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)) });
      ((Rectangle)localObject1).setFill((Paint)localObject2);
      ((Rectangle)localObject1).setStroke(null);
      localObject3 = new Rectangle(0.42D * d2, 0.1657142857142857D * d3, 1.0D, 0.6714285714D * d3);
      localObject4 = new LinearGradient(((Rectangle)localObject3).getLayoutBounds().getMinX(), ((Rectangle)localObject3).getLayoutBounds().getMinY(), ((Rectangle)localObject3).getLayoutBounds().getMinX(), ((Rectangle)localObject3).getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2901960784D)), new Stop(0.5D, Color.color(1.0D, 1.0D, 1.0D, 0.3450980392D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)) });
      ((Rectangle)localObject3).setFill((Paint)localObject4);
      ((Rectangle)localObject3).setStroke(null);
      localObject5 = new Rectangle(0.58D * d2, 0.1657142857142857D * d3, 1.0D, 0.6714285714D * d3);
      localObject6 = new LinearGradient(((Rectangle)localObject5).getLayoutBounds().getMinX(), ((Rectangle)localObject5).getLayoutBounds().getMinY(), ((Rectangle)localObject5).getLayoutBounds().getMinX(), ((Rectangle)localObject5).getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2901960784D)), new Stop(0.5D, Color.color(1.0D, 1.0D, 1.0D, 0.3450980392D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)) });
      ((Rectangle)localObject5).setFill((Paint)localObject6);
      ((Rectangle)localObject5).setStroke(null);
      this.background.getChildren().addAll(new Node[] { localObject1, localObject3, localObject5 });
    }
    else
    {
      localObject1 = new Rectangle(0.1657142857142857D * d2, 0.42D * d3 + 2.0D, 0.6714285714D * d2, 0.16D * d3 - 3.0D);
      localObject2 = new LinearGradient(0.1657142857142857D * d2, 0.5D * d3, 0.8371428571428572D * d2, 0.5D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.5D, Color.color(1.0D, 1.0D, 1.0D, 0.1490196078D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)) });
      ((Rectangle)localObject1).setFill((Paint)localObject2);
      ((Rectangle)localObject1).setStroke(null);
      localObject3 = new Rectangle(0.1657142857142857D * d2, 0.42D * d3, 0.6714285714D * d2, 1.0D);
      localObject4 = new LinearGradient(((Rectangle)localObject3).getLayoutBounds().getMinX(), ((Rectangle)localObject3).getLayoutBounds().getMinY(), ((Rectangle)localObject3).getLayoutBounds().getMaxX(), ((Rectangle)localObject3).getLayoutBounds().getMinY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2901960784D)), new Stop(0.5D, Color.color(1.0D, 1.0D, 1.0D, 0.3450980392D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)) });
      ((Rectangle)localObject3).setFill((Paint)localObject4);
      ((Rectangle)localObject3).setStroke(null);
      localObject5 = new Rectangle(0.1657142857142857D * d2, 0.58D * d3, 0.6714285714D * d2, 1.0D);
      localObject6 = new LinearGradient(((Rectangle)localObject5).getLayoutBounds().getMinX(), ((Rectangle)localObject5).getLayoutBounds().getMinY(), ((Rectangle)localObject5).getLayoutBounds().getMaxX(), ((Rectangle)localObject5).getLayoutBounds().getMinY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2901960784D)), new Stop(0.5D, Color.color(1.0D, 1.0D, 1.0D, 0.3450980392D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)) });
      ((Rectangle)localObject5).setFill((Paint)localObject6);
      ((Rectangle)localObject5).setStroke(null);
      this.background.getChildren().addAll(new Node[] { localObject1, localObject3, localObject5 });
    }
    this.background.setCache(true);
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
    Font localFont1 = Font.font(this.control.getTitleFont(), FontWeight.NORMAL, 0.08D * d1);
    Text localText1 = new Text();
    localText1.setTextOrigin(VPos.BOTTOM);
    localText1.setFont(localFont1);
    localText1.setText(this.control.getTitle());
    if (d2 <= d3)
    {
      localText1.setX((d2 - localText1.getLayoutBounds().getWidth()) / 2.0D);
      localText1.setY(0.0657142857D * d3 + localText1.getLayoutBounds().getHeight());
    }
    else
    {
      localText1.setX(0.0628571429D * d2);
      localText1.setY(0.1533333333D * d3 + localText1.getLayoutBounds().getHeight());
    }
    localText1.getStyleClass().add(this.control.getBackgroundDesign().CSS_TEXT);
    Font localFont2 = Font.font(this.control.getUnitFont(), FontWeight.NORMAL, 0.0666666667D * d1);
    Text localText2 = new Text();
    localText2.setTextOrigin(VPos.BOTTOM);
    localText2.setFont(localFont2);
    localText2.setText(this.control.getUnit());
    if (d2 <= d3)
    {
      localText2.setX((d2 - localText2.getLayoutBounds().getWidth()) / 2.0D);
      localText2.setY(0.1085714286D * d3 + localText2.getLayoutBounds().getHeight());
    }
    else
    {
      localText2.setX(0.0628571429D * d2);
      localText2.setY(0.2666666667D * d3 + localText2.getLayoutBounds().getHeight());
    }
    localText2.getStyleClass().add(this.control.getBackgroundDesign().CSS_TEXT);
    this.titleAndUnit.getChildren().addAll(new Node[] { localRectangle, localText1, localText2 });
    this.titleAndUnit.setCache(true);
  }
  
  public void drawGlowOff()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.glowOff.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
    localRectangle1.setArcWidth(0.05D * d1);
    localRectangle1.setArcHeight(0.05D * d1);
    localRectangle1.setStroke(null);
    Rectangle localRectangle2 = new Rectangle(0.11D * d1 + 1.0D, 0.11D * d1 + 1.0D, d2 - 0.22D * d1 - 2.0D, d3 - 0.22D * d1 - 2.0D);
    localRectangle2.setArcWidth(0.045D * d1);
    localRectangle2.setArcHeight(0.045D * d1);
    Shape localShape = Shape.subtract(localRectangle1, localRectangle2);
    LinearGradient localLinearGradient = new LinearGradient(0.5D * d2, 0.08411214953271028D * d3, 0.5D * d2, 0.9112149532710281D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.8D, 0.8D, 0.8D, 0.4D)), new Stop(0.17D, Color.color(0.6D, 0.6D, 0.6D, 0.4D)), new Stop(0.33D, Color.color(0.9882352941D, 0.9882352941D, 0.9882352941D, 0.4D)), new Stop(0.34D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)), new Stop(0.63D, Color.color(0.8D, 0.8D, 0.8D, 0.4D)), new Stop(0.64D, Color.color(0.7960784314D, 0.7960784314D, 0.7960784314D, 0.4D)), new Stop(0.83D, Color.color(0.6D, 0.6D, 0.6D, 0.4D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)) });
    localShape.setFill(localLinearGradient);
    localShape.setStroke(null);
    this.glowOff.getChildren().addAll(new Node[] { localShape });
    this.glowOff.setCache(true);
  }
  
  public void drawGlowOn()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.glowOn.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
    localRectangle1.setArcWidth(0.05D * d1);
    localRectangle1.setArcHeight(0.05D * d1);
    localRectangle1.setStroke(null);
    Rectangle localRectangle2 = new Rectangle(0.11D * d1 + 1.0D, 0.11D * d1 + 1.0D, d2 - 0.22D * d1 - 2.0D, d3 - 0.22D * d1 - 2.0D);
    localRectangle2.setArcWidth(0.045D * d1);
    localRectangle2.setArcHeight(0.045D * d1);
    Shape localShape = Shape.subtract(localRectangle1, localRectangle2);
    localShape.setFill(this.control.getGlowColor());
    localShape.setStroke(null);
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setRadius(0.15D * d1);
    localDropShadow.setBlurType(BlurType.GAUSSIAN);
    if (localDropShadow.colorProperty().isBound()) {
      localDropShadow.colorProperty().unbind();
    }
    localDropShadow.colorProperty().bind(this.control.glowColorProperty());
    localShape.effectProperty().set(localDropShadow);
    this.glowOn.getChildren().addAll(new Node[] { localShape });
    this.glowOn.setCache(true);
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
    this.markers.getTransforms().clear();
    Iterator localIterator = this.control.getMarkers().iterator();
    while (localIterator.hasNext())
    {
      Marker localMarker = (Marker)localIterator.next();
      Group localGroup;
      if (d2 <= d3)
      {
        localGroup = createIndicator(d1, localMarker, new Point2D(0.59D * d2, 0.8345D * d3 - d1 * 0.0210280374D - Math.abs(localMarker.getValue()) * this.stepsize));
        localGroup.setRotate(90.0D);
      }
      else
      {
        localGroup = createIndicator(d1, localMarker, new Point2D(0.1657142857142857D * d2 - d1 * 0.0210280374D + Math.abs(localMarker.getValue()) * this.stepsize, 0.36D * d3));
      }
      this.markers.getChildren().add(localGroup);
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
    Path localPath = new Path();
    localPath.setFillRule(FillRule.EVEN_ODD);
    if (d2 <= d3)
    {
      localPath.getElements().add(new MoveTo(0.3673333333D * d2, 0.8371428571D * d3));
      localPath.getElements().add(new LineTo(0.414D * d2, 0.8471428571D * d3));
      localPath.getElements().add(new LineTo(0.414D * d2, 0.8271428571D * d3));
      localPath.getElements().add(new ClosePath());
      this.threshold.setTranslateY(-Math.abs(this.control.getThreshold() - this.control.getMinValue()) * this.stepsize);
    }
    else
    {
      localPath.getElements().add(new MoveTo(0.1657142857D * d2, 0.645D * d3));
      localPath.getElements().add(new LineTo(0.1757142857D * d2, 0.6D * d3));
      localPath.getElements().add(new LineTo(0.1557142857D * d2, 0.6D * d3));
      localPath.getElements().add(new ClosePath());
      this.threshold.setTranslateX(Math.abs(this.control.getThreshold() - this.control.getMinValue()) * this.stepsize);
    }
    localPath.setStrokeType(StrokeType.CENTERED);
    localPath.setStrokeLineCap(StrokeLineCap.ROUND);
    localPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
    localPath.setStrokeWidth(0.005D * d1);
    localPath.getStyleClass().add("root");
    localPath.setStyle(this.control.getThresholdColor().CSS);
    localPath.getStyleClass().add("threshold-gradient");
    this.threshold.getChildren().addAll(new Node[] { localRectangle, localPath });
  }
  
  public void drawMinMeasuredIndicator()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.minMeasured.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.minMeasured.getChildren().add(localRectangle);
    Path localPath = createTriangleShape(d1 * 0.032D, d1 * 0.038D, true);
    localPath.setFill(Color.color(0.0D, 0.0D, 0.8D, 1.0D));
    localPath.setStroke(null);
    if (d2 <= d3)
    {
      localPath.setRotate(-90.0D);
      localPath.setTranslateX(0.335D * d2);
      localPath.setTranslateY(0.8345D * d3 - d1 * 0.014D);
    }
    else
    {
      localPath.setRotate(180.0D);
      localPath.setTranslateX(0.1657142857142857D * d2 - d1 * 0.016D);
      localPath.setTranslateY(0.65D * d3);
    }
    this.minMeasured.getChildren().add(localPath);
  }
  
  public void drawMaxMeasuredIndicator()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.maxMeasured.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.maxMeasured.getChildren().add(localRectangle);
    Path localPath = createTriangleShape(d1 * 0.032D, d1 * 0.038D, true);
    localPath.setFill(Color.color(0.8D, 0.0D, 0.0D, 1.0D));
    localPath.setStroke(null);
    if (d2 <= d3)
    {
      localPath.setRotate(-90.0D);
      localPath.setTranslateX(0.335D * d2);
      localPath.setTranslateY(0.8345D * d3 - d1 * 0.014D);
    }
    else
    {
      localPath.setRotate(180.0D);
      localPath.setTranslateX(0.1657142857142857D * d2 - d1 * 0.016D);
      localPath.setTranslateY(0.65D * d3);
    }
    this.maxMeasured.getChildren().add(localPath);
  }
  
  public void drawBar()
  {
    double d1 = this.gaugeBounds.getWidth();
    double d2 = this.gaugeBounds.getHeight();
    this.bar.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    this.bar.getChildren().add(localRectangle1);
    Rectangle localRectangle2;
    double d3;
    double d4;
    double d5;
    if (d1 <= d2)
    {
      localRectangle2 = new Rectangle(0.42D * d1 + 3.0D, 0.1657142857142857D * d2, 0.16D * d1 - 5.0D, 0.6714285714D * d2);
      d3 = localRectangle2.getLayoutBounds().getMaxY();
      d4 = localRectangle2.getLayoutBounds().getMinY();
      this.stepsize = Math.abs(localRectangle2.getLayoutBounds().getHeight() / this.control.getRange());
      d5 = this.control.getMaxValue() < 0.0D ? d4 : this.control.getMinValue() > 0.0D ? d3 : d3 + this.control.getMinValue() * this.stepsize;
      this.currentBar = new Rectangle(localRectangle2.getLayoutBounds().getMinX(), d5, localRectangle2.getLayoutBounds().getWidth(), Math.abs(this.currentValue.get()) * this.stepsize);
      if (this.currentValue.get() > 0.0D) {
        this.currentBar.setTranslateY(-Math.abs(this.currentValue.get()) * this.stepsize);
      }
      this.currentBar.getStyleClass().add("root");
      this.currentBar.setStyle("-fx-value: " + this.control.getValueColor().CSS);
      this.currentBar.getStyleClass().add("bar-vertical-solid");
      this.currentBarHl = new Rectangle(this.currentBar.getLayoutBounds().getMinX(), this.currentBar.getLayoutBounds().getMinY(), this.currentBar.getLayoutBounds().getWidth(), this.currentBar.getLayoutBounds().getHeight());
      this.currentBarHl.getStyleClass().add("bar-vertical-highlight");
    }
    else
    {
      localRectangle2 = new Rectangle(0.1657142857142857D * d1, 0.42D * d2 + 3.0D, 0.6714285714D * d1, 0.16D * d2 - 5.0D);
      d3 = localRectangle2.getLayoutBounds().getMinX();
      d4 = localRectangle2.getLayoutBounds().getMaxX();
      this.stepsize = (localRectangle2.getLayoutBounds().getWidth() / this.control.getRange());
      d5 = this.control.getMaxValue() < 0.0D ? d4 : this.control.getMinValue() > 0.0D ? d3 : d3 + Math.abs(this.control.getMinValue() * this.stepsize);
      this.currentBar = new Rectangle(d5, localRectangle2.getLayoutBounds().getMinY(), Math.abs(this.currentValue.get()) * this.stepsize, localRectangle2.getLayoutBounds().getHeight());
      if (this.currentValue.get() < 0.0D) {
        this.currentBar.setTranslateX(this.currentValue.get() * this.stepsize);
      }
      this.currentBar.getStyleClass().add("root");
      this.currentBar.setStyle("-fx-value: " + this.control.getValueColor().CSS);
      this.currentBar.getStyleClass().add("bar-horizontal-solid");
      this.currentBarHl = new Rectangle(this.currentBar.getLayoutBounds().getMinX(), this.currentBar.getLayoutBounds().getMinY(), this.currentBar.getLayoutBounds().getWidth(), this.currentBar.getLayoutBounds().getHeight());
      this.currentBarHl.getStyleClass().add("bar-horizontal-highlight");
    }
    this.currentBar.setStroke(null);
    this.currentBarHl.setStroke(null);
    this.bar.getChildren().addAll(new Node[] { this.currentBar, this.currentBarHl });
  }
  
  public void updateBar()
  {
    double d1 = this.gaugeBounds.getWidth();
    double d2 = this.gaugeBounds.getHeight();
    double d3 = this.currentValue.get();
    if (d1 <= d2)
    {
      this.currentBar.setHeight(Math.abs(d3) * this.stepsize);
      this.currentBarHl.setHeight(Math.abs(d3) * this.stepsize);
      this.currentBar.setTranslateY(0.0D);
      this.currentBarHl.setTranslateY(0.0D);
      if (Double.compare(d3, 0.0D) >= 0)
      {
        this.currentBar.setTranslateY(-d3 * this.stepsize);
        this.currentBarHl.setTranslateY(-d3 * this.stepsize);
      }
    }
    else
    {
      this.currentBar.setWidth(Math.abs(d3) * this.stepsize);
      this.currentBarHl.setWidth(Math.abs(d3) * this.stepsize);
      this.currentBar.setTranslateX(0.0D);
      this.currentBarHl.setTranslateX(0.0D);
      if (Double.compare(d3, 0.0D) <= 0)
      {
        this.currentBar.setTranslateX(-Math.abs(d3) * this.stepsize);
        this.currentBarHl.setTranslateX(-Math.abs(d3) * this.stepsize);
      }
    }
  }
  
  public void drawBarGraph()
  {
    double d1 = this.gaugeBounds.getWidth();
    double d2 = this.gaugeBounds.getHeight();
    double d3;
    double d4;
    if (d1 <= d2)
    {
      d3 = 0.16D * d1 - 5.0D;
      d4 = 0.0085714286D * d2;
    }
    else
    {
      d3 = 0.0085714286D * d1;
      d4 = 0.16D * d2 - 5.0D;
    }
  }
  
  public void drawLed()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.ledOff.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    Group localGroup1 = createLed(d1 * 0.14D, this.control.getLedColor(), false);
    if (d2 <= d3)
    {
      localGroup1.setLayoutX(d2 * 0.68D);
      localGroup1.setLayoutY(d3 * 0.1D);
    }
    else
    {
      localGroup1.setLayoutX(d2 * 0.87D);
      localGroup1.setLayoutY(d3 * 0.5D - localGroup1.getLayoutBounds().getHeight());
    }
    this.ledOff.getChildren().addAll(new Node[] { localRectangle1, localGroup1 });
    this.ledOff.setCache(true);
    this.ledOn.getChildren().clear();
    Rectangle localRectangle2 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle2.setOpacity(0.0D);
    localRectangle2.setStroke(null);
    Group localGroup2 = createLed(d1 * 0.14D, this.control.getLedColor(), true);
    if (d2 <= d3)
    {
      localGroup2.setLayoutX(d2 * 0.68D);
      localGroup2.setLayoutY(d3 * 0.1D);
    }
    else
    {
      localGroup2.setLayoutX(d2 * 0.87D);
      localGroup2.setLayoutY(d3 * 0.5D - localGroup1.getLayoutBounds().getHeight());
    }
    this.ledOn.getChildren().addAll(new Node[] { localRectangle2, localGroup2 });
    this.ledOn.setCache(true);
  }
  
  public void drawUserLed()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.userLedOff.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    Group localGroup1 = createLed(d1 * 0.14D, this.control.getUserLedColor(), false);
    if (d2 <= d3)
    {
      localGroup1.setLayoutX(d2 * 0.1933333333D);
      localGroup1.setLayoutY(d3 * 0.1D);
    }
    else
    {
      localGroup1.setLayoutX(d2 * 0.0828571429D);
      localGroup1.setLayoutY(d3 * 0.5D - localGroup1.getLayoutBounds().getHeight());
    }
    this.userLedOff.getChildren().addAll(new Node[] { localRectangle1, localGroup1 });
    this.userLedOn.getChildren().clear();
    Rectangle localRectangle2 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle2.setOpacity(0.0D);
    localRectangle2.setStroke(null);
    Group localGroup2 = createLed(d1 * 0.14D, this.control.getUserLedColor(), true);
    if (d2 <= d3)
    {
      localGroup2.setLayoutX(d2 * 0.1933333333D);
      localGroup2.setLayoutY(d3 * 0.1D);
    }
    else
    {
      localGroup2.setLayoutX(d2 * 0.0828571429D);
      localGroup2.setLayoutY(d3 * 0.5D - localGroup1.getLayoutBounds().getHeight());
    }
    this.userLedOn.getChildren().addAll(new Node[] { localRectangle2, localGroup2 });
  }
  
  public void drawLcd()
  {
    double d1 = this.gaugeBounds.getWidth();
    double d2 = this.gaugeBounds.getHeight();
    this.lcd.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    this.lcd.getChildren().addAll(new Node[] { localRectangle1 });
    Rectangle localRectangle2;
    if (d1 <= d2) {
      localRectangle2 = new Rectangle(0.16666666666666666D * d1, 0.8571428571428571D * d2, 0.6666666666666666D * d1, 0.07142857142857142D * d2);
    } else {
      localRectangle2 = new Rectangle(0.6457142857142857D * d1, 0.17333333333333334D * d2, 0.28D * d1, 0.15333333333333332D * d2);
    }
    double d3 = localRectangle2.getWidth() > localRectangle2.getHeight() ? localRectangle2.getHeight() * 0.15D : localRectangle2.getWidth() * 0.15D;
    localRectangle2.setArcWidth(d3);
    localRectangle2.setArcHeight(d3);
    LinearGradient localLinearGradient = new LinearGradient(0.0D, localRectangle2.getLayoutBounds().getMinY(), 0.0D, localRectangle2.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.1D, 0.1D, 0.1D, 1.0D)), new Stop(0.1D, Color.color(0.3D, 0.3D, 0.3D, 1.0D)), new Stop(0.93D, Color.color(0.3D, 0.3D, 0.3D, 1.0D)), new Stop(1.0D, Color.color(0.86D, 0.86D, 0.86D, 1.0D)) });
    localRectangle2.setFill(localLinearGradient);
    localRectangle2.setStroke(null);
    Rectangle localRectangle3 = new Rectangle(localRectangle2.getX() + 1.0D, localRectangle2.getY() + 1.0D, localRectangle2.getWidth() - 2.0D, localRectangle2.getHeight() - 2.0D);
    double d4 = localRectangle2.getArcWidth() - 1.0D;
    localRectangle3.setArcWidth(d4);
    localRectangle3.setArcHeight(d4);
    localRectangle3.getStyleClass().add("lcd");
    localRectangle3.getStyleClass().add(this.control.getLcdDesign().CSS);
    localRectangle3.getStyleClass().add("lcd-main");
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.25D * localRectangle2.getHeight());
    localInnerShadow1.setHeight(0.25D * localRectangle2.getHeight());
    localInnerShadow1.setOffsetY(-0.05D * localRectangle2.getHeight());
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.2D));
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setInput(localInnerShadow1);
    localInnerShadow2.setWidth(0.15D * localRectangle2.getHeight());
    localInnerShadow2.setHeight(0.15D * localRectangle2.getHeight());
    localInnerShadow2.setOffsetY(0.025D * localRectangle2.getHeight());
    localInnerShadow2.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.65D));
    localRectangle3.setEffect(localInnerShadow2);
    this.lcd.getChildren().addAll(new Node[] { localRectangle2, localRectangle3 });
    this.lcd.setCache(true);
  }
  
  public void drawLcdContent()
  {
    double d1 = this.gaugeBounds.getWidth();
    double d2 = this.gaugeBounds.getHeight();
    this.lcdContent.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    Rectangle localRectangle2;
    if (d1 <= d2) {
      localRectangle2 = new Rectangle(0.16666666666666666D * d1, 0.8571428571428571D * d2, 0.6666666666666666D * d1, 0.07142857142857142D * d2);
    } else {
      localRectangle2 = new Rectangle(0.6457142857142857D * d1, 0.17333333333333334D * d2, 0.28D * d1, 0.15333333333333332D * d2);
    }
    Font localFont1 = Font.font(this.control.getLcdUnitFont(), FontWeight.NORMAL, 0.4D * localRectangle2.getLayoutBounds().getHeight());
    Font localFont2;
    double d3;
    switch (this.control.getLcdValueFont())
    {
    case LCD: 
      localFont2 = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/digital.ttf"), 0.75D * localRectangle2.getLayoutBounds().getHeight());
      d3 = 1.5D;
      break;
    case BUS: 
      localFont2 = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/bus.otf"), 0.6D * localRectangle2.getLayoutBounds().getHeight());
      d3 = 2.0D;
      break;
    case PIXEL: 
      localFont2 = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/pixel.ttf"), 0.6D * localRectangle2.getLayoutBounds().getHeight());
      d3 = 2.0D;
      break;
    case PHONE_LCD: 
      localFont2 = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/phonelcd.ttf"), 0.6D * localRectangle2.getLayoutBounds().getHeight());
      d3 = 2.0D;
      break;
    case STANDARD: 
    default: 
      localFont2 = Font.font("Verdana", FontWeight.NORMAL, 0.6D * localRectangle2.getLayoutBounds().getHeight());
      d3 = 2.0D;
    }
    this.lcdValueString.setFont(localFont2);
    this.lcdUnitString.setFont(localFont1);
    this.lcdUnitString.setText(this.control.isLcdValueCoupled() ? this.control.getUnit() : this.control.getLcdUnit());
    this.lcdUnitString.setTextOrigin(VPos.BOTTOM);
    this.lcdUnitString.setTextAlignment(TextAlignment.RIGHT);
    if (this.lcdUnitString.visibleProperty().isBound()) {
      this.lcdUnitString.visibleProperty().unbind();
    }
    this.lcdUnitString.visibleProperty().bind(this.control.lcdUnitVisibleProperty());
    if (this.control.isLcdUnitVisible())
    {
      this.lcdUnitString.setX(localRectangle2.getX() + (localRectangle2.getWidth() - this.lcdUnitString.getLayoutBounds().getWidth()) - localRectangle2.getHeight() * 0.0625D);
      this.lcdUnitString.setY(localRectangle2.getY() + (localRectangle2.getHeight() + this.lcdValueString.getLayoutBounds().getHeight()) / d3 - this.lcdValueString.getLayoutBounds().getHeight() * 0.05D);
    }
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
      this.lcdValueString.setX(localRectangle2.getX() + (localRectangle2.getWidth() - this.lcdValueString.getLayoutBounds().getWidth()) - localRectangle2.getHeight() * 0.0625D);
    }
    this.lcdValueString.setY(localRectangle2.getY() + (localRectangle2.getHeight() + this.lcdValueString.getLayoutBounds().getHeight()) / 2.0D);
    this.lcdValueString.setTextOrigin(VPos.BOTTOM);
    this.lcdValueString.setTextAlignment(TextAlignment.RIGHT);
    this.lcdContent.getChildren().addAll(new Node[] { localRectangle1, this.lcdUnitString, this.lcdValueString });
  }
  
  public void drawTickmarks()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.tickmarks.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    this.tickmarks.getChildren().add(localRectangle1);
    Gauge.NumberFormat localNumberFormat;
    if (this.control.getTickLabelNumberFormat() == Gauge.NumberFormat.AUTO)
    {
      if (Math.abs(this.control.getMajorTickSpacing()) > 1000.0D) {
        localNumberFormat = Gauge.NumberFormat.SCIENTIFIC;
      } else if (this.control.getMajorTickSpacing() % 1.0D != 0.0D) {
        localNumberFormat = Gauge.NumberFormat.FRACTIONAL;
      } else {
        localNumberFormat = Gauge.NumberFormat.STANDARD;
      }
    }
    else {
      localNumberFormat = this.control.getTickLabelNumberFormat();
    }
    Font localFont = Font.font("Verdana", FontWeight.NORMAL, 0.06D * d1);
    double d4 = 0.022D * d1;
    double d5 = 0.035D * d1;
    double d6 = 0.05D * d1;
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.setSmooth(true);
    localPath1.strokeTypeProperty().set(StrokeType.CENTERED);
    localPath1.strokeLineCapProperty().set(StrokeLineCap.ROUND);
    localPath1.strokeLineJoinProperty().set(StrokeLineJoin.BEVEL);
    localPath1.strokeWidthProperty().set(0.0045D * d1);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.setSmooth(true);
    localPath2.strokeTypeProperty().set(StrokeType.CENTERED);
    localPath2.strokeLineCapProperty().set(StrokeLineCap.ROUND);
    localPath2.strokeLineJoinProperty().set(StrokeLineJoin.BEVEL);
    localPath2.strokeWidthProperty().set(0.0025D * d1);
    Path localPath3 = new Path();
    localPath3.setFillRule(FillRule.EVEN_ODD);
    localPath3.setSmooth(true);
    localPath3.strokeTypeProperty().set(StrokeType.CENTERED);
    localPath3.strokeLineCapProperty().set(StrokeLineCap.ROUND);
    localPath3.strokeLineJoinProperty().set(StrokeLineJoin.BEVEL);
    localPath3.strokeWidthProperty().set(0.0015D * d1);
    ArrayList localArrayList = new ArrayList();
    localPath1.getStyleClass().add(this.control.getBackgroundDesign().CSS_BACKGROUND);
    localPath2.getStyleClass().add(this.control.getBackgroundDesign().CSS_BACKGROUND);
    localPath3.getStyleClass().add(this.control.getBackgroundDesign().CSS_BACKGROUND);
    int i;
    Rectangle localRectangle2;
    double d7;
    double d8;
    double d9;
    Point2D localPoint2D1;
    if (d2 <= d3)
    {
      i = 0;
      localRectangle2 = new Rectangle(0.42D * d2 + 3.0D, 0.1657142857142857D * d3, 0.16D * d2 - 5.0D, 0.6714285714D * d3);
      d7 = localRectangle2.getLayoutBounds().getMaxY();
      d8 = localRectangle2.getLayoutBounds().getMinY();
      this.stepsize = Math.abs(localRectangle2.getLayoutBounds().getHeight() / this.control.getRange());
      d9 = localRectangle2.getLayoutBounds().getMinX() - d1 * 0.05D;
      localPoint2D1 = new Point2D(d9 - d6 - 5.0D, 0.0D);
    }
    else
    {
      i = 1;
      localRectangle2 = new Rectangle(0.1657142857142857D * d2, 0.42D * d3 + 3.0D, 0.6714285714D * d2, 0.16D * d3 - 5.0D);
      d7 = localRectangle2.getLayoutBounds().getMinX();
      d8 = localRectangle2.getLayoutBounds().getMaxX();
      this.stepsize = (localRectangle2.getLayoutBounds().getWidth() / this.control.getRange());
      d9 = localRectangle2.getLayoutBounds().getMaxY() + d1 * 0.05D;
      localPoint2D1 = new Point2D(0.0D, d9 + d6 + 0.06D * d1 + 5.0D);
    }
    double d10 = i == 0 ? d8 : d7;
    double d11 = i == 0 ? d7 : d8;
    double d12 = i == 0 ? this.control.getMaxValue() : this.control.getMinValue();
    int j = this.control.getMaxNoOfMinorTicks() - 1;
    for (double d13 = d10; Double.compare(d13, d11 + 1.0D) <= 0; d13 += this.stepsize)
    {
      j++;
      Point2D localPoint2D2;
      Point2D localPoint2D3;
      if (j == this.control.getMaxNoOfMinorTicks())
      {
        if (i == 0)
        {
          localPoint2D2 = new Point2D(d9, 0.0D);
          localPoint2D3 = new Point2D(d9 - d6, 0.0D);
          localPath1.getElements().add(new MoveTo(localPoint2D2.getX(), d13));
          localPath1.getElements().add(new LineTo(localPoint2D3.getX(), d13));
        }
        else
        {
          localPoint2D2 = new Point2D(0.0D, d9);
          localPoint2D3 = new Point2D(0.0D, d9 + d6);
          localPath1.getElements().add(new MoveTo(d13, localPoint2D2.getY()));
          localPath1.getElements().add(new LineTo(d13, localPoint2D3.getY()));
        }
        if (this.control.isTickLabelsVisible())
        {
          Text localText = new Text(localNumberFormat.format(Double.valueOf(d12)));
          localText.setSmooth(true);
          localText.setFontSmoothingType(FontSmoothingType.LCD);
          localText.setTextAlignment(TextAlignment.CENTER);
          localText.setTextOrigin(VPos.BOTTOM);
          localText.getStyleClass().add(this.control.getBackgroundDesign().CSS_TEXT);
          localText.setStroke(null);
          localText.setFont(localFont);
          if (i == 0)
          {
            localText.setX(localPoint2D1.getX() - localText.getLayoutBounds().getWidth());
            localText.setY(d13 + localText.getLayoutBounds().getHeight() / 2.0D);
          }
          else
          {
            localText.setX(d13 - localText.getLayoutBounds().getWidth() / 2.0D);
            localText.setY(localPoint2D1.getY());
          }
          localArrayList.add(localText);
          if (i == 0) {
            d12 -= this.control.getMajorTickSpacing();
          } else {
            d12 += this.control.getMajorTickSpacing();
          }
        }
        j = 0;
      }
      else if ((this.control.getMaxNoOfMinorTicks() % 2 == 0) && (j == this.control.getMaxNoOfMinorTicks() / 2))
      {
        if (i == 0)
        {
          localPoint2D2 = new Point2D(d9, 0.0D);
          localPoint2D3 = new Point2D(d9 - d5, 0.0D);
          localPath2.getElements().add(new MoveTo(localPoint2D2.getX(), d13));
          localPath2.getElements().add(new LineTo(localPoint2D3.getX(), d13));
        }
        else
        {
          localPoint2D2 = new Point2D(0.0D, d9);
          localPoint2D3 = new Point2D(0.0D, d9 + d5);
          localPath2.getElements().add(new MoveTo(d13, localPoint2D2.getY()));
          localPath2.getElements().add(new LineTo(d13, localPoint2D3.getY()));
        }
      }
      else if ((this.control.isTickmarksVisible()) && (this.control.isMinorTicksVisible()))
      {
        if (i == 0)
        {
          localPoint2D2 = new Point2D(d9, 0.0D);
          localPoint2D3 = new Point2D(d9 - d4, 0.0D);
          localPath3.getElements().add(new MoveTo(localPoint2D2.getX(), d13));
          localPath3.getElements().add(new LineTo(localPoint2D3.getX(), d13));
        }
        else
        {
          localPoint2D2 = new Point2D(0.0D, d9);
          localPoint2D3 = new Point2D(0.0D, d9 + d4);
          localPath3.getElements().add(new MoveTo(d13, localPoint2D2.getY()));
          localPath3.getElements().add(new LineTo(d13, localPoint2D3.getY()));
        }
      }
    }
    this.tickmarks.getChildren().addAll(new Node[] { localPath1, localPath2, localPath3 });
    this.tickmarks.getChildren().addAll(localArrayList);
    this.tickmarks.setCache(true);
  }
  
  public void drawForeground()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.foreground.getChildren().clear();
    Insets localInsets = new Insets(0.0841121495D * d1 + 2.0D, d2 - 0.0841121495D * d1 - 2.0D, d3 - 0.0841121495D * d1 - 2.0D, 0.0841121495D * d1 + 2.0D);
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.foreground.getChildren().addAll(new Node[] { localRectangle });
    Path localPath = new Path();
    Point2D localPoint2D1;
    Point2D localPoint2D2;
    if (d2 >= d3)
    {
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(localInsets.getLeft(), localInsets.getBottom()));
      localPath.getElements().add(new LineTo(localInsets.getRight(), localInsets.getBottom()));
      localPath.getElements().add(new CubicCurveTo(localInsets.getRight(), localInsets.getBottom(), localInsets.getRight() - 13.0D, 0.7D * d3, localInsets.getRight() - 13.0D, 0.5D * d3));
      localPath.getElements().add(new CubicCurveTo(localInsets.getRight() - 13.0D, 0.3D * d3, localInsets.getRight(), localInsets.getTop(), localInsets.getRight(), localInsets.getTop()));
      localPath.getElements().add(new LineTo(localInsets.getLeft(), localInsets.getTop()));
      localPath.getElements().add(new CubicCurveTo(localInsets.getLeft(), localInsets.getTop(), localInsets.getLeft() + 13.0D, 0.3D * d3, localInsets.getLeft() + 13.0D, 0.5D * d3));
      localPath.getElements().add(new CubicCurveTo(localInsets.getLeft() + 13.0D, 0.7D * d3, localInsets.getLeft(), localInsets.getBottom(), localInsets.getLeft(), localInsets.getBottom()));
      localPath.getElements().add(new ClosePath());
      localPoint2D1 = new Point2D(0.0D, localPath.getLayoutBounds().getMaxY());
      localPoint2D2 = new Point2D(0.0D, localPath.getLayoutBounds().getMinY());
    }
    else
    {
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(localInsets.getLeft(), localInsets.getTop()));
      localPath.getElements().add(new LineTo(localInsets.getLeft(), localInsets.getBottom()));
      localPath.getElements().add(new CubicCurveTo(localInsets.getLeft(), localInsets.getBottom(), 0.3D * d2, localInsets.getBottom() - 13.0D, 0.5D * d2, localInsets.getBottom() - 13.0D));
      localPath.getElements().add(new CubicCurveTo(0.7D * d2, localInsets.getBottom() - 13.0D, localInsets.getRight(), localInsets.getBottom(), localInsets.getRight(), localInsets.getBottom()));
      localPath.getElements().add(new LineTo(localInsets.getRight(), localInsets.getTop()));
      localPath.getElements().add(new CubicCurveTo(localInsets.getRight(), localInsets.getTop(), 0.7D * d2, localInsets.getTop() + 13.0D, 0.5D * d2, localInsets.getTop() + 13.0D));
      localPath.getElements().add(new CubicCurveTo(0.3D * d2, localInsets.getTop() + 13.0D, localInsets.getLeft(), localInsets.getTop(), localInsets.getLeft(), localInsets.getTop()));
      localPath.getElements().add(new ClosePath());
      localPoint2D1 = new Point2D(localPath.getLayoutBounds().getMinX(), 0.0D);
      localPoint2D2 = new Point2D(localPath.getLayoutBounds().getMaxX(), 0.0D);
    }
    LinearGradient localLinearGradient = new LinearGradient(localPoint2D1.getX(), localPoint2D1.getY(), localPoint2D2.getX(), localPoint2D2.getY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.06D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.07D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.12D, Color.color(1.0D, 1.0D, 1.0D, 0.05D)), new Stop(0.17D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.18D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.23D, Color.color(1.0D, 1.0D, 1.0D, 0.02D)), new Stop(0.3D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.8D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.84D, Color.color(1.0D, 1.0D, 1.0D, 0.08D)), new Stop(0.93D, Color.color(1.0D, 1.0D, 1.0D, 0.18D)), new Stop(0.94D, Color.color(1.0D, 1.0D, 1.0D, 0.2D)), new Stop(0.96D, Color.color(1.0D, 1.0D, 1.0D, 0.1D)), new Stop(0.97D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) });
    localPath.setFill(localLinearGradient);
    localPath.setStroke(null);
    this.foreground.getChildren().addAll(new Node[] { localPath });
    this.foreground.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/LinearSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */