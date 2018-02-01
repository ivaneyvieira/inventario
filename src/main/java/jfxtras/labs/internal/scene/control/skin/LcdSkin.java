package jfxtras.labs.internal.scene.control.skin;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javafx.animation.Animation.Status;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.LcdBehavior;
import jfxtras.labs.scene.control.gauge.Gauge.LcdFont;
import jfxtras.labs.scene.control.gauge.Gauge.NumberSystem;
import jfxtras.labs.scene.control.gauge.Lcd;
import jfxtras.labs.scene.control.gauge.LcdDesign;
import jfxtras.labs.scene.control.gauge.Section;

public class LcdSkin
  extends GaugeSkinBase<Lcd, LcdBehavior>
{
  private static final Rectangle PREF_SIZE = new Rectangle(132.0D, 48.0D);
  private AnimationTimer lcdBlinkingTimer;
  private AnimationTimer thresholdTimer;
  private Lcd control;
  private boolean valueVisible;
  private boolean thresholdVisible;
  private Group sections;
  private Group glowOn;
  private ArrayList<Color> glowColors;
  private Group lcd;
  private Group lcdContent;
  private Text lcdValueString;
  private Text lcdValueBackgroundString;
  private Text lcdUnitString;
  private double lcdValueOffsetLeft;
  private double lcdValueOffsetRight;
  private Group lcdThresholdIndicator;
  private double lcdDigitalFontSizeFactor;
  private Text lcdTitle;
  private Text lcdNumberSystem;
  private Text lcdMinMeasuredValue;
  private Text lcdMaxMeasuredValue;
  private Text lcdFormerValue;
  private Group minMeasured;
  private Group maxMeasured;
  private DoubleProperty currentValue;
  private double formerValue;
  private DoubleProperty lcdValue;
  private DoubleProperty currentLcdValue;
  private FadeTransition glowPulse;
  private Path trendUp;
  private Path trendSteady;
  private Path trendDown;
  private List<Shape> bargraph;
  private Transition toValueAnimation;
  private boolean isDirty;
  private boolean initialized;
  private long lastLcdTimerCall;
  private long lastThresholdTimerCall;
  private long lastClockTimerCall;
  private AnimationTimer clockTimer;
  private StringProperty lcdClockValue;
  
  public LcdSkin(Lcd paramLcd)
  {
    super(paramLcd, new LcdBehavior(paramLcd));
    this.control = paramLcd;
    this.sections = new Group();
    this.glowOn = new Group();
    this.glowColors = new ArrayList(4);
    this.lcd = new Group();
    this.lcdContent = new Group();
    this.lcdValueString = new Text();
    this.lcdValueBackgroundString = new Text();
    this.lcdUnitString = new Text();
    this.lcdValueOffsetLeft = 0.0D;
    this.lcdValueOffsetRight = 0.0D;
    this.lcdThresholdIndicator = new Group();
    this.lcdDigitalFontSizeFactor = 1.0D;
    this.lcdTitle = new Text();
    this.lcdNumberSystem = new Text();
    this.lcdMinMeasuredValue = new Text();
    this.lcdMaxMeasuredValue = new Text();
    this.lcdFormerValue = new Text();
    this.minMeasured = new Group();
    this.maxMeasured = new Group();
    this.currentValue = new SimpleDoubleProperty(0.0D);
    this.lcdValue = new SimpleDoubleProperty(0.0D);
    this.currentLcdValue = new SimpleDoubleProperty(0.0D);
    this.bargraph = new ArrayList(20);
    this.glowPulse = new FadeTransition(Duration.millis(800.0D), this.glowOn);
    this.toValueAnimation = new Transition()
    {
      protected void interpolate(double paramAnonymousDouble)
      {
        LcdSkin.this.currentValue.set(LcdSkin.this.formerValue + (LcdSkin.this.control.getValue() - LcdSkin.this.formerValue) * paramAnonymousDouble);
      }
    };
    this.isDirty = false;
    this.initialized = false;
    this.lastLcdTimerCall = (System.nanoTime() + getBlinkInterval());
    this.valueVisible = true;
    this.lcdBlinkingTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > LcdSkin.this.lastLcdTimerCall + LcdSkin.this.getBlinkInterval())
        {
          LcdSkin.access$480(LcdSkin.this, 1);
          LcdSkin.this.lcdValueString.setVisible(LcdSkin.this.valueVisible);
          LcdSkin.this.lastLcdTimerCall = paramAnonymousLong;
        }
      }
    };
    this.thresholdVisible = false;
    this.lastThresholdTimerCall = (System.nanoTime() + getBlinkInterval());
    this.thresholdTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if ((paramAnonymousLong > LcdSkin.this.lastThresholdTimerCall + LcdSkin.this.getBlinkInterval()) && (LcdSkin.this.control.isLcdThresholdVisible()))
        {
          LcdSkin.access$780(LcdSkin.this, 1);
          LcdSkin.this.lcdThresholdIndicator.setVisible(LcdSkin.this.thresholdVisible);
          LcdSkin.this.lastThresholdTimerCall = paramAnonymousLong;
        }
      }
    };
    this.lastClockTimerCall = System.nanoTime();
    this.clockTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > LcdSkin.this.lastClockTimerCall + 500000000L) {
          LcdSkin.this.updateLcdClock();
        }
      }
    };
    this.lcdClockValue = new SimpleStringProperty("00:00:00");
    init();
  }
  
  private void init()
  {
    if ((this.control.getPrefWidth() < 0.0D) || (this.control.getPrefHeight() < 0.0D)) {
      this.control.setPrefSize(PREF_SIZE.getWidth(), PREF_SIZE.getHeight());
    }
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
      if (this.control.isGlowOn()) {
        this.glowOn.setOpacity(1.0D);
      } else {
        this.glowOn.setOpacity(0.0D);
      }
    }
    else {
      this.glowOn.setOpacity(0.0D);
    }
    if (this.control.isLcdBlinking()) {
      this.lcdBlinkingTimer.start();
    }
    if (this.control.isClockMode()) {
      this.clockTimer.start();
    }
    addBindings();
    addListeners();
    registerChangeListener(this.control.backgroundVisibleProperty(), "BACKGROUND_VISIBILITY");
    registerChangeListener(this.control.clockModeProperty(), "CLOCK_MODE");
    registerChangeListener(this.lcdClockValue, "CLOCK_VALUE");
    this.currentLcdValue.set(this.control.getLcdValue());
    this.initialized = true;
    repaint();
  }
  
  private void addBindings()
  {
    if (this.sections.visibleProperty().isBound()) {
      this.sections.visibleProperty().unbind();
    }
    this.sections.visibleProperty().bind(this.control.sectionsVisibleProperty());
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
    this.lcdValue.bind(this.control.lcdValueProperty());
    if (this.lcdMinMeasuredValue.visibleProperty().isBound()) {
      this.lcdMinMeasuredValue.visibleProperty().unbind();
    }
    this.lcdMinMeasuredValue.visibleProperty().bind(this.control.lcdMinMeasuredValueVisibleProperty());
    if (this.lcdMaxMeasuredValue.visibleProperty().isBound()) {
      this.lcdMinMeasuredValue.visibleProperty().unbind();
    }
    this.lcdMaxMeasuredValue.visibleProperty().bind(this.control.lcdMaxMeasuredValueVisibleProperty());
    if (this.lcdFormerValue.visibleProperty().isBound()) {
      this.lcdFormerValue.visibleProperty().unbind();
    }
    this.lcdFormerValue.visibleProperty().bind(this.control.lcdFormerValueVisibleProperty());
    if (this.lcdNumberSystem.visibleProperty().isBound()) {
      this.lcdNumberSystem.visibleProperty().unbind();
    }
    this.lcdNumberSystem.visibleProperty().bind(this.control.lcdNumberSystemVisibleProperty());
    if (this.lcdTitle.visibleProperty().isBound()) {
      this.lcdTitle.visibleProperty().unbind();
    }
    this.lcdTitle.visibleProperty().bind(this.control.titleVisibleProperty());
  }
  
  private void addListeners()
  {
    this.control.lcdBlinkingProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        if (paramAnonymousBoolean2.booleanValue())
        {
          LcdSkin.this.lcdBlinkingTimer.start();
        }
        else
        {
          LcdSkin.this.lcdBlinkingTimer.stop();
          LcdSkin.this.lcdValueString.setVisible(true);
        }
      }
    });
    this.control.bargraphVisibleProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        LcdSkin.this.repaint();
      }
    });
    this.control.thresholdExceededProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        if (LcdSkin.this.control.isLcdThresholdVisible()) {
          if (paramAnonymousBoolean2.booleanValue())
          {
            LcdSkin.this.thresholdTimer.start();
          }
          else
          {
            LcdSkin.this.thresholdTimer.stop();
            LcdSkin.this.lcdThresholdIndicator.setVisible(false);
          }
        }
      }
    });
    this.control.lcdValueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        LcdSkin.this.formerValue = paramAnonymousNumber1.doubleValue();
        if (LcdSkin.this.toValueAnimation.getStatus() != Animation.Status.STOPPED) {
          LcdSkin.this.toValueAnimation.stop();
        }
        if (LcdSkin.this.control.isValueAnimationEnabled())
        {
          LcdSkin.this.toValueAnimation.setInterpolator(Interpolator.SPLINE(0.5D, 0.4D, 0.4D, 1.0D));
          LcdSkin.this.toValueAnimation.play();
        }
        else
        {
          LcdSkin.this.currentValue.set(paramAnonymousNumber2.doubleValue());
        }
        LcdSkin.this.checkMarkers(LcdSkin.this.control, paramAnonymousNumber1.doubleValue(), paramAnonymousNumber2.doubleValue());
      }
    });
    this.control.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        LcdSkin.this.formerValue = paramAnonymousNumber1.doubleValue();
        if (LcdSkin.this.toValueAnimation.getStatus() != Animation.Status.STOPPED) {
          LcdSkin.this.toValueAnimation.stop();
        }
        if (LcdSkin.this.control.isValueAnimationEnabled())
        {
          LcdSkin.this.toValueAnimation.setInterpolator(Interpolator.SPLINE(0.5D, 0.4D, 0.4D, 1.0D));
          LcdSkin.this.toValueAnimation.play();
        }
        else
        {
          LcdSkin.this.currentValue.set(paramAnonymousNumber2.doubleValue());
        }
        LcdSkin.this.checkMarkers(LcdSkin.this.control, paramAnonymousNumber1.doubleValue(), paramAnonymousNumber2.doubleValue());
      }
    });
    this.currentValue.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        LcdSkin.this.currentLcdValue.set(LcdSkin.this.control.isLcdValueCoupled() ? LcdSkin.this.currentValue.get() : LcdSkin.this.control.getLcdValue());
        if (Double.compare(LcdSkin.this.currentValue.get(), LcdSkin.this.control.getMinMeasuredValue()) < 0) {
          LcdSkin.this.control.setMinMeasuredValue(LcdSkin.this.currentValue.get());
        }
        if (Double.compare(LcdSkin.this.currentValue.get(), LcdSkin.this.control.getMaxMeasuredValue()) > 0) {
          LcdSkin.this.control.setMaxMeasuredValue(LcdSkin.this.currentValue.get());
        }
        if (LcdSkin.this.control.isThresholdBehaviorInverted()) {
          LcdSkin.this.control.setThresholdExceeded(LcdSkin.this.currentValue.get() < LcdSkin.this.control.getThreshold());
        } else {
          LcdSkin.this.control.setThresholdExceeded(LcdSkin.this.currentValue.get() > LcdSkin.this.control.getThreshold());
        }
        if (LcdSkin.this.control.isLcdThresholdVisible()) {
          LcdSkin.this.lcdThresholdIndicator.setVisible(LcdSkin.this.control.isThresholdExceeded());
        }
        LcdSkin.this.drawLcdContent();
        if (!LcdSkin.this.control.getSections().isEmpty())
        {
          Iterator localIterator = LcdSkin.this.control.getSections().iterator();
          while (localIterator.hasNext())
          {
            Section localSection = (Section)localIterator.next();
            if ((Double.compare(LcdSkin.this.currentValue.get(), localSection.getStart()) >= 0) && (Double.compare(LcdSkin.this.currentValue.get(), localSection.getStop()) <= 0)) {
              break;
            }
          }
          if (LcdSkin.this.control.getWidth() > LcdSkin.this.control.getHeight()) {}
        }
      }
    });
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("LCD".equals(paramString))
    {
      drawLcd();
      drawLcdContent();
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
      if (!this.control.isGlowVisible()) {
        this.glowOn.setOpacity(0.0D);
      }
    }
    else if ("GLOW_ON".equals(paramString))
    {
      if (this.control.isGlowOn()) {
        this.glowOn.setOpacity(1.0D);
      } else {
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
    else if ("TREND".equals(paramString))
    {
      drawLcdContent();
    }
    else if ("BACKGROUND_VISIBILITY".equals(paramString))
    {
      repaint();
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
    else if ("PREF_WIDTH".equals(paramString))
    {
      repaint();
    }
    else if ("PREF_HEIGHT".equals(paramString))
    {
      repaint();
    }
    else if ("CLOCK_MODE".equals(paramString))
    {
      if (this.control.isClockMode()) {
        this.clockTimer.start();
      } else {
        this.clockTimer.stop();
      }
    }
    else if ("CLOCK_VALUE".equals(paramString))
    {
      drawLcdContent();
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
      drawGlowOn();
      drawLcd();
      drawLcdContent();
      getChildren().setAll(new Node[] { this.minMeasured, this.maxMeasured, this.lcd, this.glowOn, this.lcdContent });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public Lcd getSkinnable()
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
  
  private String formatLcdValue(double paramDouble, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder(16);
    localStringBuilder.append("0");
    if (paramInt > 0) {
      localStringBuilder.append(".");
    }
    for (int i = 0; i < paramInt; i++) {
      localStringBuilder.append("0");
    }
    localStringBuilder.trimToSize();
    DecimalFormat localDecimalFormat = new DecimalFormat(localStringBuilder.toString(), new DecimalFormatSymbols(Locale.US));
    return localDecimalFormat.format(paramDouble);
  }
  
  private void prepareLcd()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = this.control.getPrefWidth();
    double d3 = this.control.getPrefHeight();
    Rectangle localRectangle = new Rectangle(1.0D, 1.0D, d2 - 2.0D, d3 - 2.0D);
    Font localFont1;
    switch (this.control.getLcdValueFont())
    {
    case BUS: 
      localFont1 = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/bus.otf"), 0.4583333333D * d1);
      this.lcdDigitalFontSizeFactor = 1.0D;
      break;
    case LCD: 
      localFont1 = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/digital.ttf"), 0.5833333333D * d1);
      this.lcdDigitalFontSizeFactor = 1.9098073909D;
      break;
    case PIXEL: 
      localFont1 = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/pixel.ttf"), 0.5208333333D * d1);
      this.lcdDigitalFontSizeFactor = 1.0D;
      break;
    case PHONE_LCD: 
      localFont1 = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/phonelcd.ttf"), 0.4583333333D * d1);
      this.lcdDigitalFontSizeFactor = 1.0D;
      break;
    case STANDARD: 
    default: 
      localFont1 = Font.font("Verdana", FontWeight.NORMAL, 0.5D * d1);
      this.lcdDigitalFontSizeFactor = 1.0D;
    }
    this.lcdValueString.setFont(localFont1);
    this.lcdValueString.setFontSmoothingType(FontSmoothingType.LCD);
    this.lcdValueString.getStyleClass().clear();
    this.lcdValueString.getStyleClass().add("lcd");
    this.lcdValueString.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.lcdValueString.getStyleClass().add("lcd-text");
    Font localFont2 = Font.font(this.control.getLcdUnitFont(), FontWeight.NORMAL, 0.26D * localRectangle.getLayoutBounds().getHeight());
    this.lcdUnitString.setFont(localFont2);
    this.lcdUnitString.setTextOrigin(VPos.BASELINE);
    this.lcdUnitString.setTextAlignment(TextAlignment.RIGHT);
    this.lcdUnitString.setText(this.control.isLcdValueCoupled() ? this.control.getUnit() : this.control.getLcdUnit());
    if (this.lcdUnitString.visibleProperty().isBound()) {
      this.lcdUnitString.visibleProperty().unbind();
    }
    this.lcdUnitString.visibleProperty().bind(this.control.lcdUnitVisibleProperty());
    this.lcdValueOffsetLeft = (d1 * 0.04D);
    if (this.control.isLcdUnitVisible())
    {
      this.lcdUnitString.setX(d2 - this.lcdUnitString.getLayoutBounds().getWidth() - d1 * 0.04D);
      this.lcdUnitString.setY(d1 - this.lcdValueString.getLayoutBounds().getHeight() * this.lcdDigitalFontSizeFactor / 2.0D);
      this.lcdUnitString.setFontSmoothingType(FontSmoothingType.LCD);
      this.lcdUnitString.getStyleClass().clear();
      this.lcdUnitString.getStyleClass().add("lcd");
      this.lcdUnitString.getStyleClass().add(this.control.getLcdDesign().CSS);
      this.lcdUnitString.getStyleClass().add("lcd-text");
      this.lcdValueOffsetRight = (this.lcdUnitString.getLayoutBounds().getWidth() + d1 * 0.0833333333D);
      this.lcdValueString.setX(localRectangle.getX() + localRectangle.getWidth() - this.lcdValueString.getLayoutBounds().getWidth() - this.lcdValueOffsetRight);
    }
    else
    {
      this.lcdValueOffsetRight = (d1 * 0.0833333333D);
      this.lcdValueString.setX(d2 - this.lcdValueString.getLayoutBounds().getWidth() - this.lcdValueOffsetRight);
    }
    this.lcdValueBackgroundString.setFont(localFont1);
    this.lcdValueBackgroundString.setTextOrigin(VPos.BASELINE);
    this.lcdValueBackgroundString.setTextAlignment(TextAlignment.RIGHT);
    Text localText = new Text("8");
    localText.setFont(localFont1);
    double d4 = localText.getLayoutBounds().getWidth();
    double d5 = this.control.getLcdDecimals() == 0 ? 0.0D : this.control.getLcdDecimals() * d4 + d4;
    double d6 = localRectangle.getWidth() - this.lcdValueOffsetRight - d5;
    int i = (int)Math.floor(d6 / d4);
    StringBuilder localStringBuilder = new StringBuilder();
    for (int j = 0; j < this.control.getLcdDecimals(); j++) {
      localStringBuilder.append("8");
    }
    if (this.control.getLcdDecimals() != 0) {
      localStringBuilder.insert(0, ".");
    }
    for (j = 0; j < i; j++) {
      localStringBuilder.insert(0, "8");
    }
    this.lcdValueBackgroundString.setText(localStringBuilder.toString());
    if (this.control.isLcdUnitVisible()) {
      this.lcdValueBackgroundString.setX(localRectangle.getX() + localRectangle.getWidth() - this.lcdValueBackgroundString.getLayoutBounds().getWidth() - this.lcdValueOffsetRight);
    } else {
      this.lcdValueBackgroundString.setX(d2 - this.lcdValueBackgroundString.getLayoutBounds().getWidth() - this.lcdValueOffsetRight);
    }
    this.lcdValueBackgroundString.setY(d1 - this.lcdValueBackgroundString.getLayoutBounds().getHeight() * this.lcdDigitalFontSizeFactor / 2.0D);
    this.lcdValueBackgroundString.setFontSmoothingType(FontSmoothingType.LCD);
    this.lcdValueBackgroundString.getStyleClass().clear();
    this.lcdValueBackgroundString.getStyleClass().add("lcd");
    this.lcdValueBackgroundString.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.lcdValueBackgroundString.getStyleClass().add("lcd-text-background");
    this.lcdValueBackgroundString.setVisible(Gauge.LcdFont.LCD == this.control.getLcdValueFont());
    Font localFont3 = Font.font(this.control.getLcdTitleFont(), FontWeight.BOLD, 0.1666666667D * d1);
    Font localFont4 = Font.font("Verdana", FontWeight.NORMAL, 0.1666666667D * d1);
    this.lcdTitle.setFont(localFont3);
    this.lcdTitle.setTextOrigin(VPos.BASELINE);
    this.lcdTitle.setTextAlignment(TextAlignment.CENTER);
    this.lcdTitle.setText(this.control.getTitle());
    this.lcdTitle.setX(localRectangle.getLayoutX() + (localRectangle.getLayoutBounds().getWidth() - this.lcdTitle.getLayoutBounds().getWidth()) / 2.0D);
    this.lcdTitle.setY(localRectangle.getLayoutY() + this.lcdTitle.getLayoutBounds().getHeight() + 0.04D * d1);
    this.lcdTitle.setFontSmoothingType(FontSmoothingType.LCD);
    this.lcdTitle.getStyleClass().clear();
    this.lcdTitle.getStyleClass().add("lcd");
    this.lcdTitle.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.lcdTitle.getStyleClass().add("lcd-text");
    this.lcdNumberSystem.setFont(localFont4);
    this.lcdNumberSystem.setTextOrigin(VPos.BASELINE);
    this.lcdNumberSystem.setTextAlignment(TextAlignment.RIGHT);
    this.lcdNumberSystem.setText(this.control.getLcdNumberSystem().toString());
    this.lcdNumberSystem.setX(localRectangle.getLayoutX() + (localRectangle.getLayoutBounds().getWidth() - this.lcdTitle.getLayoutBounds().getWidth()) / 2.0D);
    this.lcdNumberSystem.setY(localRectangle.getLayoutY() + localRectangle.getHeight() - 0.0416666667D * d1);
    this.lcdNumberSystem.setFontSmoothingType(FontSmoothingType.LCD);
    this.lcdNumberSystem.getStyleClass().clear();
    this.lcdNumberSystem.getStyleClass().add("lcd");
    this.lcdNumberSystem.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.lcdNumberSystem.getStyleClass().add("lcd-text");
    this.lcdMinMeasuredValue.setFont(localFont4);
    this.lcdMinMeasuredValue.setTextOrigin(VPos.BASELINE);
    this.lcdMinMeasuredValue.setTextAlignment(TextAlignment.RIGHT);
    this.lcdMinMeasuredValue.setX(localRectangle.getLayoutX() + 0.0416666667D * d1);
    this.lcdMinMeasuredValue.setY(localRectangle.getLayoutY() + this.lcdMinMeasuredValue.getLayoutBounds().getHeight() + 0.04D * d1);
    this.lcdMinMeasuredValue.setFontSmoothingType(FontSmoothingType.LCD);
    this.lcdMinMeasuredValue.getStyleClass().clear();
    this.lcdMinMeasuredValue.getStyleClass().add("lcd");
    this.lcdMinMeasuredValue.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.lcdMinMeasuredValue.getStyleClass().add("lcd-text");
    this.lcdMaxMeasuredValue.setFont(localFont4);
    this.lcdMaxMeasuredValue.setTextOrigin(VPos.BASELINE);
    this.lcdMaxMeasuredValue.setTextAlignment(TextAlignment.RIGHT);
    this.lcdMaxMeasuredValue.setY(localRectangle.getLayoutY() + this.lcdMinMeasuredValue.getLayoutBounds().getHeight() + 0.04D * d1);
    this.lcdMaxMeasuredValue.setFontSmoothingType(FontSmoothingType.LCD);
    this.lcdMaxMeasuredValue.getStyleClass().clear();
    this.lcdMaxMeasuredValue.getStyleClass().add("lcd");
    this.lcdMaxMeasuredValue.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.lcdMaxMeasuredValue.getStyleClass().add("lcd-text");
    this.lcdFormerValue.setFont(localFont4);
    this.lcdFormerValue.setTextOrigin(VPos.BASELINE);
    this.lcdFormerValue.setTextAlignment(TextAlignment.CENTER);
    this.lcdFormerValue.setY(localRectangle.getLayoutY() + localRectangle.getHeight() - 0.0416666667D * d1);
    this.lcdFormerValue.setFontSmoothingType(FontSmoothingType.LCD);
    this.lcdFormerValue.getStyleClass().clear();
    this.lcdFormerValue.getStyleClass().add("lcd");
    this.lcdFormerValue.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.lcdFormerValue.getStyleClass().add("lcd-text");
  }
  
  private boolean isNoOfDigitsValid()
  {
    Rectangle localRectangle = new Rectangle(1.0D, 1.0D, this.control.getPrefWidth() - 2.0D, this.control.getPrefHeight() - 2.0D);
    double d1 = localRectangle.getWidth() - this.lcdValueOffsetLeft - this.lcdValueOffsetRight;
    double d2 = this.lcdValueString.getLayoutBounds().getWidth();
    return Double.compare(d1, d2) >= 0;
  }
  
  public void drawGlowOn()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = this.control.getPrefWidth();
    double d3 = this.control.getPrefHeight();
    this.glowOn.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    double d4 = localRectangle.getWidth() > localRectangle.getHeight() ? localRectangle.getHeight() * 0.15D : localRectangle.getWidth() * 0.15D;
    localRectangle.setArcWidth(d4);
    localRectangle.setArcHeight(d4);
    localRectangle.setFill(Color.color(1.0D, 1.0D, 1.0D, 0.5D));
    localRectangle.setStroke(null);
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.6D * d1);
    localInnerShadow.setHeight(0.6D * d1);
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    if (localInnerShadow.colorProperty().isBound()) {
      localInnerShadow.colorProperty().unbind();
    }
    localInnerShadow.colorProperty().bind(this.control.glowColorProperty());
    localRectangle.effectProperty().set(localInnerShadow);
    this.glowOn.getChildren().addAll(new Node[] { localRectangle });
    this.glowOn.setCache(true);
  }
  
  public void drawLcd()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = this.control.getPrefWidth();
    double d3 = this.control.getPrefHeight();
    this.lcd.getChildren().clear();
    this.lcd.getStyleClass().add("lcd");
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    double d4 = localRectangle1.getWidth() > localRectangle1.getHeight() ? localRectangle1.getHeight() * 0.15D : localRectangle1.getWidth() * 0.15D;
    localRectangle1.setArcWidth(d4);
    localRectangle1.setArcHeight(d4);
    LinearGradient localLinearGradient = new LinearGradient(0.5D * d2, 0.0D, 0.5D * d2, d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.1D, 0.1D, 0.1D, 1.0D)), new Stop(0.1D, Color.color(0.3D, 0.3D, 0.3D, 1.0D)), new Stop(0.93D, Color.color(0.3D, 0.3D, 0.3D, 1.0D)), new Stop(1.0D, Color.color(0.86D, 0.86D, 0.86D, 1.0D)) });
    localRectangle1.setFill(localLinearGradient);
    localRectangle1.setStroke(null);
    Rectangle localRectangle2 = new Rectangle(1.0D, 1.0D, d2 - 2.0D, d3 - 2.0D);
    double d5 = localRectangle1.getArcWidth() - 1.0D;
    localRectangle2.setArcWidth(d5);
    localRectangle2.setArcHeight(d5);
    localRectangle2.getStyleClass().add(this.control.getLcdDesign().CSS);
    localRectangle2.getStyleClass().add("lcd-main");
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.25D * d1);
    localInnerShadow1.setHeight(0.25D * d1);
    localInnerShadow1.setOffsetY(-0.05D * d1);
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.2D));
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setInput(localInnerShadow1);
    localInnerShadow2.setWidth(0.15D * d1);
    localInnerShadow2.setHeight(0.075D * d1);
    localInnerShadow2.setOffsetY(0.025D * d1);
    localInnerShadow2.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.65D));
    localRectangle2.setEffect(localInnerShadow2);
    if (!this.control.isLcdBackgroundVisible())
    {
      localRectangle1.setVisible(false);
      localRectangle2.setVisible(false);
    }
    this.trendUp = new Path();
    this.trendUp.setFillRule(FillRule.EVEN_ODD);
    this.trendUp.getElements().add(new MoveTo(0.18181818181818182D * d2, 0.9375D * d3));
    this.trendUp.getElements().add(new LineTo(0.21212121212121213D * d2, 0.8125D * d3));
    this.trendUp.getElements().add(new LineTo(0.24242424242424243D * d2, 0.9375D * d3));
    this.trendUp.getElements().add(new LineTo(0.18181818181818182D * d2, 0.9375D * d3));
    this.trendUp.getElements().add(new ClosePath());
    this.trendUp.getStyleClass().clear();
    this.trendUp.getStyleClass().add("lcd");
    this.trendUp.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.trendUp.getStyleClass().add("lcd-text");
    this.trendUp.setVisible(false);
    this.trendSteady = new Path();
    this.trendSteady.setFillRule(FillRule.EVEN_ODD);
    this.trendSteady.getElements().add(new MoveTo(0.18181818181818182D * d2, 0.8125D * d3));
    this.trendSteady.getElements().add(new LineTo(0.24242424242424243D * d2, 0.875D * d3));
    this.trendSteady.getElements().add(new LineTo(0.18181818181818182D * d2, 0.9375D * d3));
    this.trendSteady.getElements().add(new LineTo(0.18181818181818182D * d2, 0.8125D * d3));
    this.trendSteady.getElements().add(new ClosePath());
    this.trendSteady.getStyleClass().clear();
    this.trendSteady.getStyleClass().add("lcd");
    this.trendSteady.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.trendSteady.getStyleClass().add("lcd-text");
    this.trendSteady.setVisible(false);
    this.trendDown = new Path();
    this.trendDown.setFillRule(FillRule.EVEN_ODD);
    this.trendDown.getElements().add(new MoveTo(0.18181818181818182D * d2, 0.8125D * d3));
    this.trendDown.getElements().add(new LineTo(0.21212121212121213D * d2, 0.9375D * d3));
    this.trendDown.getElements().add(new LineTo(0.24242424242424243D * d2, 0.8125D * d3));
    this.trendDown.getElements().add(new LineTo(0.18181818181818182D * d2, 0.8125D * d3));
    this.trendDown.getElements().add(new ClosePath());
    this.trendDown.getStyleClass().clear();
    this.trendDown.getStyleClass().add("lcd");
    this.trendDown.getStyleClass().add(this.control.getLcdDesign().CSS);
    this.trendDown.getStyleClass().add("lcd-text");
    this.trendDown.setVisible(false);
    prepareLcd();
    this.lcd.getChildren().addAll(new Node[] { localRectangle1, localRectangle2 });
    if ((this.control.isBargraphVisible()) && (!this.control.isClockMode()))
    {
      Path localPath = new Path();
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(0.9166666666666666D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.9166666666666666D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.946969696969697D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.946969696969697D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.8712121212121212D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.8712121212121212D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.9015151515151515D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.9015151515151515D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.8257575757575758D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.8257575757575758D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.8560606060606061D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.8560606060606061D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.7803030303030303D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.7803030303030303D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.8106060606060606D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.8106060606060606D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.7348484848484849D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.7348484848484849D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.7651515151515151D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.7651515151515151D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.6893939393939394D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.6893939393939394D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.7196969696969697D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.7196969696969697D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.6439393939393939D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.6439393939393939D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.6742424242424242D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.6742424242424242D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.5984848484848485D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.5984848484848485D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.6287878787878788D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.6287878787878788D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.553030303030303D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.553030303030303D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.5833333333333334D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.5833333333333334D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.5075757575757576D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.5075757575757576D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.5378787878787878D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.5378787878787878D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.4621212121212121D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.4621212121212121D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.49242424242424243D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.49242424242424243D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.4166666666666667D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.4166666666666667D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.44696969696969696D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.44696969696969696D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.3712121212121212D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.3712121212121212D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.4015151515151515D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.4015151515151515D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.32575757575757575D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.32575757575757575D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.3560606060606061D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.3560606060606061D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.2803030303030303D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.2803030303030303D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.3106060606060606D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.3106060606060606D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.23484848484848486D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.23484848484848486D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.26515151515151514D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.26515151515151514D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.1893939393939394D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.1893939393939394D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.2196969696969697D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.2196969696969697D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.14393939393939395D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.14393939393939395D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.17424242424242425D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.17424242424242425D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.09848484848484848D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.09848484848484848D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.12878787878787878D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.12878787878787878D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getElements().add(new MoveTo(0.05303030303030303D * d2, 0.74D * d3));
      localPath.getElements().add(new LineTo(0.05303030303030303D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.08333333333333333D * d2, 0.78D * d3));
      localPath.getElements().add(new LineTo(0.08333333333333333D * d2, 0.74D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.getStyleClass().add(this.control.getLcdDesign().CSS);
      localPath.getStyleClass().add("lcd-text-background");
      Rectangle localRectangle3 = new Rectangle(0.05303030303030303D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle3.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle3.getStyleClass().add("lcd-text");
      localRectangle3.setVisible(false);
      Rectangle localRectangle4 = new Rectangle(0.09848484848484848D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle4.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle4.getStyleClass().add("lcd-text");
      localRectangle4.setVisible(false);
      Rectangle localRectangle5 = new Rectangle(0.14393939393939395D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle5.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle5.getStyleClass().add("lcd-text");
      localRectangle5.setVisible(false);
      Rectangle localRectangle6 = new Rectangle(0.1893939393939394D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle6.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle6.getStyleClass().add("lcd-text");
      localRectangle6.setVisible(false);
      Rectangle localRectangle7 = new Rectangle(0.23484848484848486D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle7.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle7.getStyleClass().add("lcd-text");
      localRectangle7.setVisible(false);
      Rectangle localRectangle8 = new Rectangle(0.2803030303030303D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle8.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle8.getStyleClass().add("lcd-text");
      localRectangle8.setVisible(false);
      Rectangle localRectangle9 = new Rectangle(0.32575757575757575D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle9.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle9.getStyleClass().add("lcd-text");
      localRectangle9.setVisible(false);
      Rectangle localRectangle10 = new Rectangle(0.3712121212121212D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle10.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle10.getStyleClass().add("lcd-text");
      localRectangle10.setVisible(false);
      Rectangle localRectangle11 = new Rectangle(0.4166666666666667D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle11.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle11.getStyleClass().add("lcd-text");
      localRectangle11.setVisible(false);
      Rectangle localRectangle12 = new Rectangle(0.4621212121212121D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle12.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle12.getStyleClass().add("lcd-text");
      localRectangle12.setVisible(false);
      Rectangle localRectangle13 = new Rectangle(0.5075757575757576D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle13.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle13.getStyleClass().add("lcd-text");
      localRectangle13.setVisible(false);
      Rectangle localRectangle14 = new Rectangle(0.553030303030303D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle14.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle14.getStyleClass().add("lcd-text");
      localRectangle14.setVisible(false);
      Rectangle localRectangle15 = new Rectangle(0.5984848484848485D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle15.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle15.getStyleClass().add("lcd-text");
      localRectangle15.setVisible(false);
      Rectangle localRectangle16 = new Rectangle(0.6439393939393939D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle16.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle16.getStyleClass().add("lcd-text");
      localRectangle16.setVisible(false);
      Rectangle localRectangle17 = new Rectangle(0.6893939393939394D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle17.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle17.getStyleClass().add("lcd-text");
      localRectangle17.setVisible(false);
      Rectangle localRectangle18 = new Rectangle(0.7348484848484849D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle18.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle18.getStyleClass().add("lcd-text");
      localRectangle18.setVisible(false);
      Rectangle localRectangle19 = new Rectangle(0.7803030303030303D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle19.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle19.getStyleClass().add("lcd-text");
      localRectangle19.setVisible(false);
      Rectangle localRectangle20 = new Rectangle(0.8257575757575758D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle20.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle20.getStyleClass().add("lcd-text");
      localRectangle20.setVisible(false);
      Rectangle localRectangle21 = new Rectangle(0.8712121212121212D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle21.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle21.getStyleClass().add("lcd-text");
      localRectangle21.setVisible(false);
      Rectangle localRectangle22 = new Rectangle(0.9166666666666666D * d2, 0.74D * d3, 0.030303030303030304D * d2, 0.04D * d3);
      localRectangle22.getStyleClass().add(this.control.getLcdDesign().CSS);
      localRectangle22.getStyleClass().add("lcd-text");
      localRectangle22.setVisible(false);
      this.bargraph.clear();
      this.bargraph.add(localRectangle3);
      this.bargraph.add(localRectangle4);
      this.bargraph.add(localRectangle5);
      this.bargraph.add(localRectangle6);
      this.bargraph.add(localRectangle7);
      this.bargraph.add(localRectangle8);
      this.bargraph.add(localRectangle9);
      this.bargraph.add(localRectangle10);
      this.bargraph.add(localRectangle11);
      this.bargraph.add(localRectangle12);
      this.bargraph.add(localRectangle13);
      this.bargraph.add(localRectangle14);
      this.bargraph.add(localRectangle15);
      this.bargraph.add(localRectangle16);
      this.bargraph.add(localRectangle17);
      this.bargraph.add(localRectangle18);
      this.bargraph.add(localRectangle19);
      this.bargraph.add(localRectangle20);
      this.bargraph.add(localRectangle21);
      this.bargraph.add(localRectangle22);
      this.lcd.getChildren().add(localPath);
      this.lcd.getChildren().addAll(this.bargraph);
    }
    this.lcd.getChildren().add(this.lcdTitle);
    this.lcd.getChildren().add(this.lcdNumberSystem);
    this.lcd.getChildren().add(this.lcdUnitString);
    this.lcd.getChildren().add(this.lcdValueBackgroundString);
    this.lcdThresholdIndicator = createLcdThresholdIndicator(d3 * 0.2045454545D, d3 * 0.2045454545D);
    this.lcdThresholdIndicator.setTranslateX(0.04D * d1);
    this.lcdThresholdIndicator.setTranslateY(d3 - this.lcdThresholdIndicator.getLayoutBounds().getHeight() - 0.0416666667D * d1);
    this.lcdThresholdIndicator.setVisible(this.control.isLcdThresholdVisible());
    this.lcd.getChildren().add(this.lcdThresholdIndicator);
    this.lcd.setCache(true);
  }
  
  public void drawLcdContent()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = this.control.getPrefWidth();
    double d3 = this.control.getPrefHeight();
    this.lcdContent.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    this.lcdContent.getChildren().add(localRectangle1);
    Rectangle localRectangle2 = new Rectangle(1.0D, 1.0D, d2 - 2.0D, d3 - 2.0D);
    if (this.control.isClockMode())
    {
      this.lcdValueString.setText((String)this.lcdClockValue.get());
    }
    else
    {
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
        this.lcdValueString.setText(formatLcdValue(this.currentLcdValue.get(), this.control.getLcdDecimals()));
      }
      this.lcdNumberSystem.setText(this.control.getLcdNumberSystem().toString());
      this.lcdNumberSystem.setX(d2 - this.lcdNumberSystem.getLayoutBounds().getWidth() - 0.0416666667D * d1);
      this.lcdNumberSystem.setY(localRectangle2.getLayoutY() + localRectangle2.getHeight() - 0.0416666667D * d1);
      if (!isNoOfDigitsValid()) {
        this.lcdValueString.setText("-E-");
      }
    }
    if ((this.control.isLcdUnitVisible()) && (!this.control.isClockMode())) {
      this.lcdValueString.setX(localRectangle2.getX() + (localRectangle2.getWidth() - this.lcdValueString.getLayoutBounds().getWidth()) - this.lcdValueOffsetRight);
    } else {
      this.lcdValueString.setX(d2 - this.lcdValueString.getLayoutBounds().getWidth() - this.lcdValueOffsetRight);
    }
    this.lcdValueString.setY(d1 - this.lcdValueString.getLayoutBounds().getHeight() * this.lcdDigitalFontSizeFactor / 2.0D);
    if ((this.control.isBargraphVisible()) && (!this.bargraph.isEmpty()))
    {
      int i = (int)((this.currentLcdValue.get() - this.currentLcdValue.get()) * 20.0D);
      for (int j = 0; j < 20; j++) {
        if (j <= i) {
          ((Shape)this.bargraph.get(j)).setVisible(true);
        } else {
          ((Shape)this.bargraph.get(j)).setVisible(false);
        }
      }
    }
    this.lcdTitle.setText(this.control.getTitle());
    this.lcdMinMeasuredValue.setText(formatLcdValue(this.control.getMinMeasuredValue(), this.control.getLcdMinMeasuredValueDecimals()));
    this.lcdMaxMeasuredValue.setText(formatLcdValue(this.control.getMaxMeasuredValue(), this.control.getLcdMaxMeasuredValueDecimals()));
    this.lcdMaxMeasuredValue.setX(d2 - this.lcdMaxMeasuredValue.getLayoutBounds().getWidth() - 0.0416666667D * d1);
    this.lcdFormerValue.setText(formatLcdValue(this.formerValue, this.control.getLcdDecimals()));
    this.lcdFormerValue.setX((d2 - this.lcdFormerValue.getLayoutBounds().getWidth()) / 2.0D);
    this.lcdFormerValue.setFontSmoothingType(FontSmoothingType.LCD);
    if (this.control.isTrendVisible()) {
      switch (this.control.getTrend())
      {
      case UP: 
        this.trendUp.setVisible(true);
        this.trendSteady.setVisible(false);
        this.trendDown.setVisible(false);
        break;
      case STEADY: 
        this.trendUp.setVisible(false);
        this.trendSteady.setVisible(true);
        this.trendDown.setVisible(false);
        break;
      case DOWN: 
        this.trendUp.setVisible(false);
        this.trendSteady.setVisible(false);
        this.trendDown.setVisible(true);
        break;
      default: 
        this.trendUp.setVisible(false);
        this.trendSteady.setVisible(false);
        this.trendDown.setVisible(false);
      }
    }
    if (this.control.isClockMode()) {
      this.lcdContent.getChildren().addAll(new Node[] { this.lcdValueString });
    } else {
      this.lcdContent.getChildren().addAll(new Node[] { this.lcdValueString, this.lcdMinMeasuredValue, this.lcdMaxMeasuredValue, this.lcdFormerValue, this.trendUp, this.trendSteady, this.trendDown });
    }
  }
  
  private void updateLcdClock()
  {
    int i = Calendar.getInstance().get(11);
    int j = Calendar.getInstance().get(12);
    int k = Calendar.getInstance().get(13);
    String str1 = i < 10 ? "0" + Integer.toString(i) : Integer.toString(i);
    String str2 = j < 10 ? "0" + Integer.toString(j) : Integer.toString(j);
    String str3 = k < 10 ? "0" + Integer.toString(k) : Integer.toString(k);
    if (this.control.isClockSecondsVisible()) {
      this.lcdClockValue.set(str1 + ":" + str2 + ":" + str3);
    } else {
      this.lcdClockValue.set(str1 + ":" + str2);
    }
  }
  
  private Group createLcdThresholdIndicator(double paramDouble1, double paramDouble2)
  {
    Group localGroup = new Group();
    localGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, paramDouble1, paramDouble2);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    localGroup.getChildren().add(localRectangle);
    Path localPath = new Path();
    localPath.setFillRule(FillRule.EVEN_ODD);
    localPath.getElements().add(new MoveTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.7777777777777778D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5555555555555556D, paramDouble2 * 0.7777777777777778D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5555555555555556D, paramDouble2 * 0.8888888888888888D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.8888888888888888D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.7777777777777778D));
    localPath.getElements().add(new ClosePath());
    localPath.getElements().add(new MoveTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.3333333333333333D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5555555555555556D, paramDouble2 * 0.3333333333333333D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5555555555555556D, paramDouble2 * 0.7222222222222222D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.7222222222222222D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.3333333333333333D));
    localPath.getElements().add(new ClosePath());
    localPath.getElements().add(new MoveTo(0.0D, paramDouble2));
    localPath.getElements().add(new LineTo(paramDouble1, paramDouble2));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5D, 0.0D));
    localPath.getElements().add(new LineTo(0.0D, paramDouble2));
    localPath.getElements().add(new ClosePath());
    localPath.getStyleClass().add("lcd");
    localPath.getStyleClass().add(this.control.getLcdDesign().CSS);
    localPath.getStyleClass().add("lcd-text");
    localPath.setStroke(null);
    localGroup.getChildren().addAll(new Node[] { localPath });
    return localGroup;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/LcdSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */