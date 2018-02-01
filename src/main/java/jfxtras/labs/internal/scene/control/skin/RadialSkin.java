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
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
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
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotParametersBuilder;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.Line;
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
import jfxtras.labs.internal.scene.control.behavior.RadialBehavior;
import jfxtras.labs.scene.control.gauge.ColorDef;
import jfxtras.labs.scene.control.gauge.Gauge.BackgroundDesign;
import jfxtras.labs.scene.control.gauge.Gauge.PointerType;
import jfxtras.labs.scene.control.gauge.Gauge.RadialRange;
import jfxtras.labs.scene.control.gauge.Gauge.ThresholdColor;
import jfxtras.labs.scene.control.gauge.LcdDesign;
import jfxtras.labs.scene.control.gauge.Radial;
import jfxtras.labs.scene.control.gauge.Section;

public class RadialSkin
  extends GaugeSkinBase<Radial, RadialBehavior>
{
  private static final Rectangle MIN_SIZE = new Rectangle(25.0D, 25.0D);
  private static final Rectangle PREF_SIZE = new Rectangle(200.0D, 200.0D);
  private static final Rectangle MAX_SIZE = new Rectangle(1024.0D, 1024.0D);
  private final SnapshotParameters SNAPSHOT_PARAMETER = SnapshotParametersBuilder.create().fill(Color.TRANSPARENT).build();
  private Radial control;
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
  private Group bargraphOff;
  private Group bargraphOn;
  private Group ledOff;
  private Group ledOn;
  private Group userLedOff;
  private Group userLedOn;
  private Group foreground;
  private Timeline rotationAngleTimeline;
  private DoubleProperty gaugeValue;
  private double negativeOffset;
  private Point2D center;
  private int noOfLeds;
  private ArrayList<Shape> ledsOff;
  private ArrayList<Shape> ledsOn;
  private DoubleProperty currentValue;
  private DoubleProperty formerValue;
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
  private Group histogram;
  private ImageView histogramImage;
  private WritableImage img;
  private long histogramInterval;
  private long lastHistogramFadingTimerCall;
  private AnimationTimer histogramFadingTimer;
  private boolean isDirty;
  private boolean initialized;
  
  public RadialSkin(Radial paramRadial)
  {
    super(paramRadial, new RadialBehavior(paramRadial));
    this.control = paramRadial;
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
    this.bargraphOff = new Group();
    this.bargraphOn = new Group();
    this.ledOff = new Group();
    this.ledOn = new Group();
    this.userLedOff = new Group();
    this.userLedOn = new Group();
    this.foreground = new Group();
    this.rotationAngleTimeline = new Timeline();
    this.gaugeValue = new SimpleDoubleProperty(0.0D);
    this.negativeOffset = 0.0D;
    this.noOfLeds = 60;
    this.ledsOff = new ArrayList(this.noOfLeds);
    this.ledsOn = new ArrayList(this.noOfLeds);
    this.currentValue = new SimpleDoubleProperty(0.0D);
    this.formerValue = new SimpleDoubleProperty(0.0D);
    this.lcdValue = new SimpleDoubleProperty(0.0D);
    this.currentLcdValue = new SimpleDoubleProperty(0.0D);
    this.glowPulse = new FadeTransition(Duration.millis(800.0D), this.glowOn);
    this.pointerRotation = new Rotate();
    this.isDirty = false;
    this.ledTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > RadialSkin.this.lastLedTimerCall + RadialSkin.this.getBlinkInterval())
        {
          RadialSkin.access$180(RadialSkin.this, 1);
          if (RadialSkin.this.ledOnVisible) {
            RadialSkin.this.ledOn.setOpacity(1.0D);
          } else {
            RadialSkin.this.ledOn.setOpacity(0.0D);
          }
          RadialSkin.this.lastLedTimerCall = paramAnonymousLong;
        }
      }
    };
    this.lastLedTimerCall = 0L;
    this.ledOnVisible = false;
    this.userLedTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > RadialSkin.this.lastUserLedTimerCall + RadialSkin.this.getBlinkInterval())
        {
          RadialSkin.access$480(RadialSkin.this, 1);
          if (RadialSkin.this.userLedOnVisible) {
            RadialSkin.this.userLedOn.setOpacity(1.0D);
          } else {
            RadialSkin.this.userLedOn.setOpacity(0.0D);
          }
          RadialSkin.this.lastUserLedTimerCall = paramAnonymousLong;
        }
      }
    };
    this.lastUserLedTimerCall = 0L;
    this.userLedOnVisible = false;
    this.histogram = new Group();
    this.histogramImage = new ImageView();
    this.histogramImage.setSmooth(true);
    this.histogramInterval = (this.control.histogramDataPeriodInMinutesProperty().get() * 60000000000L / 100L);
    this.lastHistogramFadingTimerCall = 0L;
    this.histogramFadingTimer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        long l = System.nanoTime();
        if (l > RadialSkin.this.lastHistogramFadingTimerCall + RadialSkin.this.histogramInterval)
        {
          RadialSkin.this.reduceHistogramOpacity();
          RadialSkin.this.lastHistogramFadingTimerCall = l;
        }
      }
    };
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
    if (this.gaugeValue.get() < this.control.getMinValue()) {
      this.gaugeValue.set(this.control.getMinValue());
    } else if (this.gaugeValue.get() > this.control.getMaxValue()) {
      this.gaugeValue.set(this.control.getMaxValue());
    }
    this.control.recalcRange();
    this.control.setMinMeasuredValue(this.control.getMaxValue());
    this.control.setMaxMeasuredValue(this.control.getMinValue());
    if (this.control.getMinValue() < 0.0D) {
      this.negativeOffset = (this.control.getMinValue() * this.control.getAngleStep());
    } else {
      this.negativeOffset = 0.0D;
    }
    addBindings();
    addListeners();
    registerChangeListener(this.control.histogramCreationEnabledProperty(), "HISTOGRAM_CREATION");
    registerChangeListener(this.control.histogramDataPeriodInMinutesProperty(), "HISTOGRAM_PERIOD");
    if (this.control.isHistogramCreationEnabled()) {
      this.histogramFadingTimer.start();
    }
    this.pointerRotation.angleProperty().bind(this.gaugeValue.subtract(this.control.minValueProperty()).multiply(this.control.angleStepProperty()).add(this.control.getRadialRange().ROTATION_OFFSET));
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
    if (this.histogram.visibleProperty().isBound()) {
      this.histogram.visibleProperty().unbind();
    }
    this.histogram.visibleProperty().bind(this.control.histogramVisibleProperty());
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
    if (this.lcdThresholdIndicator.visibleProperty().isBound()) {
      this.lcdThresholdIndicator.visibleProperty().unbind();
    }
    if ((this.control.isLcdThresholdVisible()) && (this.control.isLcdValueCoupled())) {
      this.lcdThresholdIndicator.visibleProperty().bind(this.control.thresholdVisibleProperty());
    }
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
        RadialSkin.this.updateAreas();
        RadialSkin.this.drawCircularAreas(RadialSkin.this.control, RadialSkin.this.areas, RadialSkin.this.gaugeBounds);
      }
    });
    this.control.getSections().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        RadialSkin.this.updateSections();
        RadialSkin.this.drawCircularSections(RadialSkin.this.control, RadialSkin.this.sections, RadialSkin.this.gaugeBounds);
      }
    });
    this.control.getMarkers().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        RadialSkin.this.drawCircularIndicators(RadialSkin.this.control, RadialSkin.this.markers, RadialSkin.this.center, RadialSkin.this.gaugeBounds);
      }
    });
    this.control.realValueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        RadialSkin.this.formerValue.set(paramAnonymousNumber1.doubleValue());
        if (RadialSkin.this.rotationAngleTimeline.getStatus() != Animation.Status.STOPPED) {
          RadialSkin.this.rotationAngleTimeline.stop();
        }
        if (RadialSkin.this.control.isHistogramCreationEnabled()) {
          RadialSkin.this.addValueToHistogram(paramAnonymousNumber1.doubleValue());
        }
        if ((paramAnonymousNumber2.doubleValue() > paramAnonymousNumber1.doubleValue() - RadialSkin.this.control.getRedrawToleranceValue()) && (paramAnonymousNumber2.doubleValue() < paramAnonymousNumber1.doubleValue() + RadialSkin.this.control.getRedrawToleranceValue())) {
          return;
        }
        Object localObject1;
        Object localObject2;
        if (RadialSkin.this.control.isValueAnimationEnabled())
        {
          localObject1 = new KeyValue(RadialSkin.this.gaugeValue, paramAnonymousNumber2, Interpolator.SPLINE(0.5D, 0.4D, 0.4D, 1.0D));
          localObject2 = new KeyFrame(Duration.millis(RadialSkin.this.control.getAnimationDuration()), new KeyValue[] { localObject1 });
          RadialSkin.this.rotationAngleTimeline = new Timeline();
          RadialSkin.this.rotationAngleTimeline.getKeyFrames().add(localObject2);
          RadialSkin.this.rotationAngleTimeline.play();
        }
        else
        {
          RadialSkin.this.gaugeValue.set(paramAnonymousNumber2.doubleValue());
          RadialSkin.this.pointerRotation.setPivotX(RadialSkin.this.center.getX());
          RadialSkin.this.pointerRotation.setPivotY(RadialSkin.this.center.getY());
          RadialSkin.this.pointer.getTransforms().clear();
          RadialSkin.this.pointer.getTransforms().add(RadialSkin.this.pointerRotation);
        }
        RadialSkin.this.checkMarkers(RadialSkin.this.control, paramAnonymousNumber1.doubleValue(), paramAnonymousNumber2.doubleValue());
        Iterator localIterator;
        Section localSection;
        Shape localShape;
        if (RadialSkin.this.control.isSectionsHighlighting())
        {
          localObject1 = new InnerShadow();
          ((InnerShadow)localObject1).setBlurType(BlurType.GAUSSIAN);
          localObject2 = new DropShadow();
          ((DropShadow)localObject2).setWidth(0.05D * RadialSkin.this.gaugeBounds.getWidth());
          ((DropShadow)localObject2).setHeight(0.05D * RadialSkin.this.gaugeBounds.getHeight());
          ((DropShadow)localObject2).setBlurType(BlurType.GAUSSIAN);
          localIterator = RadialSkin.this.control.getSections().iterator();
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
        if (RadialSkin.this.control.isAreasHighlighting())
        {
          localObject1 = new InnerShadow();
          ((InnerShadow)localObject1).setBlurType(BlurType.GAUSSIAN);
          localObject2 = new DropShadow();
          ((DropShadow)localObject2).setWidth(0.05D * RadialSkin.this.gaugeBounds.getWidth());
          ((DropShadow)localObject2).setHeight(0.05D * RadialSkin.this.gaugeBounds.getHeight());
          ((DropShadow)localObject2).setBlurType(BlurType.GAUSSIAN);
          localIterator = RadialSkin.this.control.getAreas().iterator();
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
        if (RadialSkin.this.bargraphOff.isVisible())
        {
          int i = RadialSkin.this.noOfLeds - 1 - (int)((paramAnonymousNumber2.doubleValue() - RadialSkin.this.control.getMinValue()) * RadialSkin.this.control.getAngleStep() / 5.0D);
          int j = RadialSkin.this.noOfLeds - 1 - (int)((paramAnonymousNumber1.doubleValue() - RadialSkin.this.control.getMinValue()) * RadialSkin.this.control.getAngleStep() / 5.0D);
          int k = i > RadialSkin.this.noOfLeds ? RadialSkin.this.noOfLeds : i < 0 ? 0 : i;
          int m = j > RadialSkin.this.noOfLeds ? RadialSkin.this.noOfLeds : j < 0 ? 0 : j;
          int n = RadialSkin.this.noOfLeds - 1 - (int)((RadialSkin.this.control.getThreshold() - RadialSkin.this.control.getMinValue()) * RadialSkin.this.control.getAngleStep() / 5.0D);
          int i1;
          if (Double.compare(RadialSkin.this.control.getValue(), RadialSkin.this.formerValue.doubleValue()) >= 0) {
            for (i1 = k; i1 <= m; i1++) {
              ((Shape)RadialSkin.this.ledsOn.get(i1)).setVisible(true);
            }
          } else {
            for (i1 = k; i1 >= m; i1--) {
              ((Shape)RadialSkin.this.ledsOn.get(i1)).setVisible(false);
            }
          }
          if (RadialSkin.this.control.isThresholdVisible())
          {
            ((Shape)RadialSkin.this.ledsOn.get(n)).setVisible(true);
            ((Shape)RadialSkin.this.ledsOn.get(n)).getStyleClass().clear();
            ((Shape)RadialSkin.this.ledsOn.get(n)).getStyleClass().add("bargraph-threshold");
          }
        }
        else
        {
          RadialSkin.this.pointer.getTransforms().clear();
          RadialSkin.this.pointerRotation.setPivotX(RadialSkin.this.center.getX());
          RadialSkin.this.pointerRotation.setPivotY(RadialSkin.this.center.getY());
          RadialSkin.this.pointer.getTransforms().add(RadialSkin.this.pointerRotation);
        }
        RadialSkin.this.currentValue.set(paramAnonymousNumber2.doubleValue());
        RadialSkin.this.currentLcdValue.set(RadialSkin.this.control.isLcdValueCoupled() ? paramAnonymousNumber2.doubleValue() : RadialSkin.this.control.getLcdValue());
        if (Double.compare(RadialSkin.this.currentValue.get(), RadialSkin.this.control.getMinMeasuredValue()) < 0) {
          RadialSkin.this.control.setMinMeasuredValue(RadialSkin.this.currentValue.get());
        } else if (Double.compare(RadialSkin.this.currentValue.get(), RadialSkin.this.control.getMaxMeasuredValue()) > 0) {
          RadialSkin.this.control.setMaxMeasuredValue(RadialSkin.this.currentValue.get());
        }
        if (RadialSkin.this.control.isThresholdBehaviorInverted()) {
          RadialSkin.this.control.setThresholdExceeded(RadialSkin.this.currentValue.doubleValue() < RadialSkin.this.control.getThreshold());
        } else {
          RadialSkin.this.control.setThresholdExceeded(RadialSkin.this.currentValue.doubleValue() > RadialSkin.this.control.getThreshold());
        }
        if (!RadialSkin.this.control.isThresholdExceeded()) {
          RadialSkin.this.ledOn.setOpacity(0.0D);
        }
        if (RadialSkin.this.control.isLcdVisible()) {
          RadialSkin.this.drawLcdContent();
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
        this.noOfLeds = ((int)(this.control.getRadialRange().ANGLE_RANGE / 5.0D));
        this.isDirty = true;
        if (this.pointerRotation.angleProperty().isBound()) {
          this.pointerRotation.angleProperty().unbind();
        }
        if (this.control.getRadialRange() == Gauge.RadialRange.RADIAL_360) {
          this.pointerRotation.angleProperty().bind(this.gaugeValue.multiply(this.control.angleStepProperty()).add(this.control.getRadialRange().ROTATION_OFFSET));
        } else {
          this.pointerRotation.angleProperty().bind(this.gaugeValue.subtract(this.control.minValueProperty()).multiply(this.control.angleStepProperty()).add(this.control.getRadialRange().ROTATION_OFFSET));
        }
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
      else if ("THRESHOLD_COLOR".equals(paramString))
      {
        int i = this.noOfLeds - 1 - (int)((this.control.getThreshold() - this.control.getMinValue()) * this.control.getAngleStep() / 5.0D);
        ((Shape)this.ledsOn.get(i)).getStyleClass().clear();
        ((Shape)this.ledsOn.get(i)).getStyleClass().add("bargraph-threshold");
        drawThreshold();
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
      else if ("SIMPLE_GRADIENT_BASE".equals(paramString))
      {
        repaint();
      }
      else if ("TICKMARKS".equals(paramString))
      {
        if (this.control.getMinValue() < 0.0D) {
          this.negativeOffset = (this.control.getMinValue() * this.control.getAngleStep());
        } else {
          this.negativeOffset = 0.0D;
        }
        drawCircularTickmarks(this.control, this.tickmarks, this.center, this.gaugeBounds);
      }
      else if ("POINTER_GLOW".equals(paramString))
      {
        drawPointer();
      }
      else if ("POINTER_SHADOW".equals(paramString))
      {
        drawPointer();
      }
      else if ("HISTOGRAM_CREATION".equals(paramString))
      {
        if (this.control.isHistogramCreationEnabled()) {
          this.histogramFadingTimer.start();
        } else {
          this.histogramFadingTimer.stop();
        }
      }
      else if ("HISTOGRAM_PERIOD".equals(paramString))
      {
        this.histogramInterval = (this.control.histogramDataPeriodInMinutesProperty().get() * 60000000000L / 100L);
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
      else
      {
        double d;
        if ("PREF_WIDTH".equals(paramString))
        {
          d = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
          if (d > 0.0D) {
            this.img = new WritableImage((int)d, (int)d);
          }
          calcGaugeBounds();
          repaint();
        }
        else if ("PREF_HEIGHT".equals(paramString))
        {
          d = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
          if (d > 0.0D) {
            this.img = new WritableImage((int)d, (int)d);
          }
          calcGaugeBounds();
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
      this.center = new Point2D(this.gaugeBounds.getWidth() * 0.5D, this.gaugeBounds.getHeight() * 0.5D);
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
      drawCircularIndicators(this.control, this.markers, this.center, this.gaugeBounds);
      drawCircularLcd(this.control, this.lcd, this.gaugeBounds);
      drawLcdContent();
      drawPointer();
      drawCircularBargraph(this.control, this.bargraphOff, this.noOfLeds, this.ledsOff, false, true, this.center, this.gaugeBounds);
      drawCircularBargraph(this.control, this.bargraphOn, this.noOfLeds, this.ledsOn, true, false, this.center, this.gaugeBounds);
      drawCircularKnobs(this.control, this.knobs, this.center, this.gaugeBounds);
      drawCircularForeground(this.control, this.foreground, this.gaugeBounds);
      if ((this.control.isPointerShadowEnabled()) && (!this.control.isPointerGlowEnabled())) {
        addDropShadow(this.control, new Node[] { this.knobs, this.pointerShadow });
      }
      getChildren().setAll(new Node[] { this.frame, this.background, this.histogram, this.trend, this.sections, this.areas, this.ledOff, this.ledOn, this.userLedOff, this.userLedOn, this.titleAndUnit, this.tickmarks, this.threshold, this.glowOff, this.glowOn, this.lcd, this.lcdContent, this.pointerShadow, this.bargraphOff, this.bargraphOn, this.minMeasured, this.maxMeasured, this.markers, this.knobsShadow, this.foreground });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public Radial getSkinnable()
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
    return super.computePrefHeight(d);
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
    double d1 = this.control.getPrefWidth() * 0.38D;
    double d2 = this.control.isExpandedSections() ? d1 - this.control.getPrefWidth() * 0.12D : d1 - this.control.getPrefWidth() * 0.04D;
    Circle localCircle = new Circle(this.center.getX(), this.center.getY(), d2);
    Iterator localIterator = this.control.getSections().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      double d3 = localSection.getStart() < this.control.getMinValue() ? this.control.getMinValue() : localSection.getStart();
      double d4 = localSection.getStop() > this.control.getMaxValue() ? this.control.getMaxValue() : localSection.getStop();
      double d5 = this.control.getRadialRange().SECTIONS_OFFSET - d3 * this.control.getAngleStep() + this.control.getMinValue() * this.control.getAngleStep();
      double d6 = -(d4 - d3) * this.control.getAngleStep();
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
      double d6 = this.control.getRadialRange().SECTIONS_OFFSET - d4 * this.control.getAngleStep() + this.control.getMinValue() * this.control.getAngleStep();
      double d7 = -(d5 - d4) * this.control.getAngleStep();
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
  
  private void addValueToHistogram(double paramDouble)
  {
    double d = this.control.getPrefWidth() <= this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    if (this.img == null) {
      this.img = new WritableImage((int)d, (int)d);
    }
    this.histogramImage.setImage(this.histogram.snapshot(this.SNAPSHOT_PARAMETER, this.img));
    this.histogramImage.setClip(new Circle(0.5D * d, 0.5D * d, 0.4158878504672897D * d));
    this.histogramImage.setOpacity(1.0D);
    this.histogram.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d, d);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    Line localLine = new Line(this.center.getX(), this.center.getY() - d * 0.22D, this.center.getX(), this.center.getY() - d * 0.25D);
    localLine.setStrokeWidth(this.control.getHistogramLineWidth());
    localLine.setStroke(this.control.getHistogramColor());
    localLine.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET + (paramDouble - this.control.getMinValue()) * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    localLine.setBlendMode(BlendMode.HARD_LIGHT);
    this.histogram.getChildren().addAll(new Node[] { localRectangle, this.histogramImage, localLine });
  }
  
  private void reduceHistogramOpacity()
  {
    if (Double.compare(this.histogramImage.getOpacity(), 0.0D) > 0) {
      this.histogramImage.setOpacity(this.histogramImage.getOpacity() - this.control.histogramDataPeriodInMinutesProperty().doubleValue() / 100.0D);
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
    localText1.setY(0.3D * d + localText1.getLayoutBounds().getHeight());
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
    this.titleAndUnit.setCache(true);
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
    localPath.getStyleClass().add(this.control.getThresholdColor().CSS);
    localPath.getStyleClass().add("threshold-gradient");
    localPath.setTranslateX(0.485D * d2);
    localPath.setTranslateY(0.14D * d3);
    this.threshold.getChildren().addAll(new Node[] { localPath });
    this.threshold.getTransforms().clear();
    this.threshold.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getY()));
    this.threshold.getTransforms().add(Transform.rotate(-this.control.getMinValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    this.threshold.getTransforms().add(Transform.rotate(this.control.getThreshold() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    this.threshold.setCache(true);
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
    localPath.setTranslateY(0.1D * d3);
    this.minMeasured.getChildren().add(localPath);
    this.minMeasured.getTransforms().clear();
    this.minMeasured.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getY()));
    this.minMeasured.getTransforms().add(Transform.rotate(-this.control.getMinValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    this.minMeasured.getTransforms().add(Transform.rotate(this.control.getMinMeasuredValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    this.minMeasured.setCache(true);
    this.minMeasured.setCacheHint(CacheHint.ROTATE);
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
    localPath.setTranslateY(0.1D * d3);
    this.maxMeasured.getChildren().add(localPath);
    this.maxMeasured.getTransforms().clear();
    this.maxMeasured.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET, this.center.getX(), this.center.getY()));
    this.maxMeasured.getTransforms().add(Transform.rotate(-this.control.getMinValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    this.maxMeasured.getTransforms().add(Transform.rotate(this.control.getMaxMeasuredValue() * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
    this.maxMeasured.setCache(true);
    this.maxMeasured.setCacheHint(CacheHint.ROTATE);
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
      localPath1.setStyle("-fx-value: " + this.control.getValueColor().CSS);
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
    double d4 = this.control.getValue() < this.control.getMinValue() ? this.control.getMinValue() : this.control.getValue();
    this.pointer.getTransforms().add(Transform.rotate(this.control.getRadialRange().ROTATION_OFFSET + (d4 - this.control.getMinValue()) * this.control.getAngleStep(), this.center.getX(), this.center.getY()));
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
    if (this.lcdUnitString.visibleProperty().isBound()) {
      this.lcdUnitString.visibleProperty().unbind();
    }
    this.lcdUnitString.visibleProperty().bind(this.control.lcdUnitVisibleProperty());
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


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/RadialSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */