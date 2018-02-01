package jfxtras.labs.internal.scene.control.skin;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.SimpleRadialGaugeBehavior;
import jfxtras.labs.scene.control.gauge.Gauge.RadialRange;
import jfxtras.labs.scene.control.gauge.Gauge.ThresholdColor;
import jfxtras.labs.scene.control.gauge.Section;
import jfxtras.labs.scene.control.gauge.SimpleRadialGauge;

public class SimpleRadialGaugeSkin
  extends GaugeSkinBase<SimpleRadialGauge, SimpleRadialGaugeBehavior>
{
  private static final double INSETS = 4.0D;
  private SimpleRadialGauge control;
  private boolean isDirty;
  private boolean initialized;
  private Canvas canvas;
  private GraphicsContext ctx;
  private Pane gauge;
  private Point2D center;
  private Arc bar;
  private Text valueText;
  private NumberFormat valueFormat;
  private Text unitText;
  private Timeline timeline;
  private DoubleProperty gaugeValue;
  private double size;
  private Text minLabel;
  private Text maxLabel;
  private Stop[] barGradientStops;
  private double xy;
  private double wh;
  private double startAngle;
  private double length;
  private Canvas alertIndicator;
  
  public SimpleRadialGaugeSkin(SimpleRadialGauge paramSimpleRadialGauge)
  {
    super(paramSimpleRadialGauge, new SimpleRadialGaugeBehavior(paramSimpleRadialGauge));
    this.control = paramSimpleRadialGauge;
    this.initialized = false;
    this.isDirty = false;
    this.canvas = new Canvas();
    this.ctx = this.canvas.getGraphicsContext2D();
    this.gauge = new Pane();
    this.center = new Point2D(100.0D, 100.0D);
    this.bar = new Arc();
    this.valueText = new Text(Double.toString(this.control.getValue()));
    this.valueFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    this.unitText = new Text(this.control.getUnit());
    this.timeline = new Timeline();
    this.gaugeValue = new SimpleDoubleProperty(this.control.getValue());
    this.size = 200.0D;
    this.minLabel = new Text(this.valueFormat.format(this.control.getMinValue()));
    this.maxLabel = new Text(this.valueFormat.format(this.control.getMaxValue()));
    this.xy = ((this.control.getBarWidth() + 2.0D) / 2.0D + 4.0D);
    this.wh = (this.size - 8.0D - this.control.getBarWidth() - 2.0D);
    this.startAngle = (-(150.0D - (360.0D - this.control.getRadialRange().ANGLE_RANGE) / 2.0D));
    this.length = this.control.getRadialRange().ANGLE_RANGE;
    this.barGradientStops = new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(0.8D, this.control.getBarColor().darker()), new Stop(1.0D, this.control.getBarColor().brighter()) };
    this.alertIndicator = createAlertIndicatorCanvas(this.control.getPrefWidth() * 0.165D, this.control.getPrefHeight() * 0.135D, this.control.getThresholdColor().COLOR);
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(200.0D, 200.0D);
    }
    this.center = new Point2D(this.control.getPrefWidth() / 2.0D, this.control.getPrefHeight() / 2.0D);
    registerChangeListener(this.control.widthProperty(), "WIDTH");
    registerChangeListener(this.control.heightProperty(), "HEIGHT");
    registerChangeListener(this.control.prefWidthProperty(), "WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "HEIGHT");
    registerChangeListener(this.control.minWidthProperty(), "WIDTH");
    registerChangeListener(this.control.minHeightProperty(), "HEIGHT");
    registerChangeListener(this.control.maxWidthProperty(), "WIDTH");
    registerChangeListener(this.control.maxHeightProperty(), "HEIGHT");
    registerChangeListener(this.control.minValueProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.maxValueProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.gaugeModelProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.barFrameColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.barBackgroundColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.valueLabelColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.unitLabelColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.unitProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.barColorProperty(), "BAR");
    registerChangeListener(this.control.barWidthProperty(), "BAR");
    registerChangeListener(this.control.roundedBarProperty(), "BAR");
    registerChangeListener(this.control.valueLabelFontSizeProperty(), "LABEL");
    registerChangeListener(this.control.noOfDecimalsProperty(), "LABEL");
    registerChangeListener(this.control.minLabelColorProperty(), "MIN_MAX_LABEL");
    registerChangeListener(this.control.maxLabelColorProperty(), "MIN_MAX_LABEL");
    registerChangeListener(this.control.minMaxLabelFontSizeProperty(), "MIN_MAX_LABEL");
    registerChangeListener(this.control.valueProperty(), "VALUE");
    registerChangeListener(this.control.minLabelVisibleProperty(), "MIN_LABEL_VISIBLE");
    registerChangeListener(this.gaugeValue, "GAUGE_VALUE");
    this.valueText.setFill(this.control.getValueLabelColor());
    this.unitText.setFill(this.control.getUnitLabelColor());
    this.minLabel.setFill(this.control.getMinLabelColor());
    this.maxLabel.setFill(this.control.getMaxLabelColor());
    addBindings();
    updateNumberFormat();
    if (this.control.isCanvasMode())
    {
      Iterator localIterator = this.control.getSections().iterator();
      while (localIterator.hasNext())
      {
        Section localSection = (Section)localIterator.next();
        if ((this.gaugeValue.get() - this.control.getMinValue() > localSection.getStart()) && (this.gaugeValue.get() - this.control.getMinValue() < localSection.getStop()))
        {
          this.barGradientStops = new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(0.8D, localSection.getColor().darker()), new Stop(1.0D, localSection.getColor().brighter()) };
          break;
        }
        this.barGradientStops = new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(0.8D, this.control.getBarColor().darker()), new Stop(1.0D, this.control.getBarColor().brighter()) };
      }
    }
    this.initialized = true;
    repaint();
  }
  
  private void addBindings()
  {
    if (this.bar.visibleProperty().isBound()) {
      this.bar.visibleProperty().unbind();
    }
    this.bar.visibleProperty().bind(this.gaugeValue.greaterThan(this.control.minValueProperty()));
    if (this.minLabel.visibleProperty().isBound()) {
      this.minLabel.visibleProperty().unbind();
    }
    this.minLabel.visibleProperty().bind(this.control.minLabelVisibleProperty());
    if (this.maxLabel.visibleProperty().isBound()) {
      this.maxLabel.visibleProperty().unbind();
    }
    this.maxLabel.visibleProperty().bind(this.control.maxLabelVisibleProperty());
    if (this.valueText.visibleProperty().isBound()) {
      this.valueText.visibleProperty().unbind();
    }
    this.valueText.visibleProperty().bind(this.control.valueLabelVisibleProperty());
    if (this.unitText.visibleProperty().isBound()) {
      this.unitText.visibleProperty().unbind();
    }
    this.unitText.visibleProperty().bind(this.control.unitLabelVisibleProperty());
    if (this.valueText.fillProperty().isBound()) {
      this.valueText.fillProperty().unbind();
    }
    this.valueText.fillProperty().bind(this.control.valueLabelColorProperty());
    if (this.unitText.fillProperty().isBound()) {
      this.unitText.fillProperty().unbind();
    }
    this.unitText.fillProperty().bind(this.control.unitLabelColorProperty());
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("WIDTH".equals(paramString))
    {
      recalcParameters();
      this.valueText.setLayoutX((this.size - this.valueText.getLayoutBounds().getWidth()) / 2.0D);
      this.valueText.setLayoutY((this.size - this.valueText.getLayoutBounds().getHeight()) / 2.0D + this.control.getValueLabelFontSize());
      this.canvas.setWidth(this.size);
      this.canvas.setHeight(this.size);
      this.alertIndicator = createAlertIndicatorCanvas(this.control.getPrefWidth() * 0.165D, this.control.getPrefHeight() * 0.135D, this.control.getThresholdColor().COLOR);
      repaint();
    }
    else if ("HEIGHT".equals(paramString))
    {
      recalcParameters();
      this.valueText.setLayoutX((this.size - this.valueText.getLayoutBounds().getWidth()) / 2.0D);
      this.valueText.setLayoutY((this.size - this.valueText.getLayoutBounds().getHeight()) / 2.0D + this.control.getValueLabelFontSize());
      this.canvas.setWidth(this.size);
      this.canvas.setHeight(this.size);
      this.alertIndicator = createAlertIndicatorCanvas(this.control.getPrefWidth() * 0.165D, this.control.getPrefHeight() * 0.135D, this.control.getThresholdColor().COLOR);
      repaint();
    }
    else if ("FULL_REPAINT".equals(paramString))
    {
      this.alertIndicator = createAlertIndicatorCanvas(this.control.getPrefWidth() * 0.165D, this.control.getPrefHeight() * 0.135D, this.control.getThresholdColor().COLOR);
      repaint();
    }
    else
    {
      Object localObject1;
      Object localObject2;
      if ("VALUE".equals(paramString))
      {
        if (this.control.isValueAnimationEnabled())
        {
          localObject1 = new KeyValue(this.gaugeValue, Double.valueOf(this.control.getValue()), Interpolator.EASE_BOTH);
          localObject2 = new KeyFrame(Duration.millis(this.control.getTimeToValueInMs()), new KeyValue[] { localObject1 });
          this.timeline.setOnFinished(new EventHandler()
          {
            public void handle(ActionEvent paramAnonymousActionEvent)
            {
              SimpleRadialGaugeSkin.this.gaugeValue.set(SimpleRadialGaugeSkin.this.control.getValue());
            }
          });
          this.timeline = new Timeline();
          this.timeline.getKeyFrames().add(localObject2);
          this.timeline.play();
        }
        else
        {
          this.gaugeValue.set(this.control.getValue());
        }
      }
      else if ("BAR".equals(paramString))
      {
        if (this.control.isCanvasMode())
        {
          this.barGradientStops = new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(0.8D, this.control.getBarColor().darker()), new Stop(1.0D, this.control.getBarColor().brighter()) };
          drawCanvasGauge(this.ctx);
        }
        else
        {
          drawNodeGauge();
        }
      }
      else if ("LABEL".equals(paramString))
      {
        updateNumberFormat();
        repaint();
      }
      else if ("MIN_MAX_LABEL".equals(paramString))
      {
        this.minLabel.setFill(this.control.getMinLabelColor());
        this.minLabel.setFont(Font.font("Verdana", this.control.getMinMaxLabelFontSize()));
        this.maxLabel.setFill(this.control.getMaxLabelColor());
        this.maxLabel.setFont(Font.font("Verdana", this.control.getMinMaxLabelFontSize()));
        repaint();
      }
      else if ("GAUGE_VALUE".equals(paramString))
      {
        if (!this.control.getSections().isEmpty())
        {
          localObject1 = this.control.getSections().iterator();
          while (((Iterator)localObject1).hasNext())
          {
            localObject2 = (Section)((Iterator)localObject1).next();
            if ((this.gaugeValue.get() - this.control.getMinValue() > ((Section)localObject2).getStart()) && (this.gaugeValue.get() - this.control.getMinValue() < ((Section)localObject2).getStop()))
            {
              updateBarColor(((Section)localObject2).getColor());
              break;
            }
            updateBarColor(this.control.getBarColor());
          }
        }
        this.valueText.setText(this.valueFormat.format(this.gaugeValue.get()));
        this.valueText.setLayoutX((this.size - this.valueText.getLayoutBounds().getWidth()) / 2.0D);
        this.valueText.setLayoutY((this.size - this.valueText.getLayoutBounds().getHeight()) / 2.0D + this.control.getValueLabelFontSize());
        this.bar.setLength(-this.gaugeValue.get() * this.control.getAngleStep());
        if ((this.control.isThresholdBehaviorInverted()) && (this.gaugeValue.doubleValue() < this.control.getThreshold())) {
          this.control.setThresholdExceeded(true);
        } else if ((!this.control.isThresholdBehaviorInverted()) && (this.gaugeValue.doubleValue() > this.control.getThreshold())) {
          this.control.setThresholdExceeded(true);
        } else {
          this.control.setThresholdExceeded(false);
        }
        this.alertIndicator.setVisible(this.control.isThresholdExceeded());
        if (this.control.isCanvasMode())
        {
          localObject1 = this.control.getSections().iterator();
          while (((Iterator)localObject1).hasNext())
          {
            localObject2 = (Section)((Iterator)localObject1).next();
            if ((this.gaugeValue.get() - this.control.getMinValue() > ((Section)localObject2).getStart()) && (this.gaugeValue.get() - this.control.getMinValue() < ((Section)localObject2).getStop()))
            {
              this.barGradientStops = new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(0.8D, ((Section)localObject2).getColor().darker()), new Stop(1.0D, ((Section)localObject2).getColor().brighter()) };
              break;
            }
            this.barGradientStops = new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(0.8D, this.control.getBarColor().darker()), new Stop(1.0D, this.control.getBarColor().brighter()) };
          }
          drawCanvasGauge(this.ctx);
        }
      }
    }
  }
  
  public final void repaint()
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
    if (this.control.isCanvasMode())
    {
      this.alertIndicator.setLayoutX((this.size - this.alertIndicator.getWidth()) * 0.5D);
      this.alertIndicator.setLayoutY(this.size * 0.6D);
      drawCanvasGauge(this.ctx);
      getChildren().setAll(new Node[] { this.canvas, this.alertIndicator });
    }
    else
    {
      drawNodeGauge();
      getChildren().setAll(new Node[] { this.gauge });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final SimpleRadialGauge getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 200.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 200.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefHeight(d);
  }
  
  protected double computeMinWidth(double paramDouble)
  {
    return super.computeMinWidth(Math.max(50.0D, paramDouble - getInsets().getLeft() - getInsets().getRight()));
  }
  
  protected double computeMinHeight(double paramDouble)
  {
    return super.computeMinHeight(Math.max(50.0D, paramDouble - getInsets().getTop() - getInsets().getBottom()));
  }
  
  protected double computeMaxWidth(double paramDouble)
  {
    return super.computeMaxWidth(Math.max(200.0D, paramDouble - getInsets().getLeft() - getInsets().getRight()));
  }
  
  protected double computeMaxHeight(double paramDouble)
  {
    return super.computeMaxHeight(Math.max(200.0D, paramDouble - getInsets().getTop() - getInsets().getBottom()));
  }
  
  private void updateNumberFormat()
  {
    StringBuilder localStringBuilder = new StringBuilder(5);
    if (this.control.getNoOfDecimals() == 0)
    {
      localStringBuilder.append("0");
    }
    else
    {
      localStringBuilder.append("0.");
      for (int i = 0; i < this.control.getNoOfDecimals(); i++) {
        localStringBuilder.append("0");
      }
    }
    this.valueFormat = new DecimalFormat(localStringBuilder.toString(), new DecimalFormatSymbols(Locale.US));
  }
  
  private void updateBarColor(Color paramColor)
  {
    double d = this.size / 2.0D - 4.0D;
    this.bar.setStroke(new RadialGradient(0.0D, 0.0D, this.center.getX(), this.center.getY(), d, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getBarColor()), new Stop((d - this.control.getBarWidth()) / d, Color.TRANSPARENT), new Stop((d - this.control.getBarWidth() + 1.0D) / d, paramColor.darker()), new Stop((d - this.control.getBarWidth() / 2.0D) / d, paramColor), new Stop(1.0D, paramColor.deriveColor(0.85D, 0.85D, 0.85D, 1.0D)) }));
  }
  
  private void recalcParameters()
  {
    this.size = (this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight());
    this.xy = ((this.control.getBarWidth() + 2.0D) / 2.0D + 4.0D);
    this.wh = (this.size - 8.0D - this.control.getBarWidth() - 2.0D);
    this.length = this.control.getRadialRange().ANGLE_RANGE;
    this.startAngle = (-(150.0D - (360.0D - this.length) / 2.0D));
  }
  
  private void drawNodeGauge()
  {
    this.size = (this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight());
    double d1 = this.size / 2.0D - 4.0D;
    double d2 = d1 - this.control.getBarWidth() / 2.0D;
    this.center = new Point2D(this.size / 2.0D, this.size / 2.0D);
    this.gauge.getChildren().clear();
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setRadius(0.01D * this.size);
    localInnerShadow1.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow1.setColor(Color.rgb(0, 0, 0, 0.65D));
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setRadius(0.005D * this.size);
    localInnerShadow2.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow2.setOffsetY(-1.0D);
    localInnerShadow2.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.65D));
    Arc localArc = new Arc();
    localArc.setCenterX(this.center.getX());
    localArc.setCenterY(this.center.getY());
    localArc.setRadiusX(d2);
    localArc.setRadiusY(d2);
    localArc.setStartAngle(-(90.0D - (360.0D - this.control.getRadialRange().ANGLE_RANGE) / 2.0D));
    localArc.setLength(this.control.getRadialRange().ANGLE_RANGE);
    localArc.setType(ArcType.OPEN);
    localArc.setFill(null);
    localArc.setSmooth(true);
    localArc.setStroke(this.control.getBarBackgroundColor());
    localArc.setStrokeWidth(this.control.getBarWidth());
    if (this.control.isRoundedBar()) {
      localArc.setStrokeLineCap(StrokeLineCap.ROUND);
    } else {
      localArc.setStrokeLineCap(StrokeLineCap.BUTT);
    }
    localArc.setEffect(localInnerShadow1);
    this.bar.setCenterX(this.center.getX());
    this.bar.setCenterY(this.center.getY());
    this.bar.setRadiusX(localArc.getRadiusX());
    this.bar.setRadiusY(localArc.getRadiusY());
    this.bar.setStartAngle(this.control.getRadialRange().ANGLE_RANGE - (90.0D - (360.0D - this.control.getRadialRange().ANGLE_RANGE) / 2.0D));
    this.bar.setLength(-this.gaugeValue.get() * this.control.getAngleStep());
    this.bar.setType(ArcType.OPEN);
    this.bar.setFill(null);
    this.bar.setSmooth(true);
    if (!this.control.getSections().isEmpty())
    {
      Iterator localIterator = this.control.getSections().iterator();
      while (localIterator.hasNext())
      {
        Section localSection = (Section)localIterator.next();
        if ((this.gaugeValue.get() - this.control.getMinValue() > localSection.getStart()) && (this.gaugeValue.get() - this.control.getMinValue() < localSection.getStop()))
        {
          updateBarColor(localSection.getColor());
          break;
        }
        updateBarColor(this.control.getBarColor());
      }
    }
    if (this.control.isRoundedBar()) {
      this.bar.setStrokeLineCap(StrokeLineCap.ROUND);
    } else {
      this.bar.setStrokeLineCap(StrokeLineCap.BUTT);
    }
    this.bar.setStrokeWidth(this.control.getBarWidth());
    this.bar.setEffect(localInnerShadow1);
    this.valueText.setFont(Font.font("Verdana", FontWeight.BOLD, this.control.getValueLabelFontSize()));
    this.valueText.setTextAlignment(TextAlignment.CENTER);
    this.valueText.setTextOrigin(VPos.BASELINE);
    this.valueText.setText(this.valueFormat.format(this.gaugeValue.get()));
    this.valueText.setLayoutX((this.size - this.valueText.getLayoutBounds().getWidth()) / 2.0D);
    this.valueText.setLayoutY((this.size - this.valueText.getLayoutBounds().getHeight()) / 2.0D + this.control.getValueLabelFontSize());
    this.valueText.setEffect(localInnerShadow2);
    this.unitText.setFont(Font.font("Verdana", FontWeight.BOLD, this.control.getUnitLabelFontSize()));
    this.unitText.setTextAlignment(TextAlignment.CENTER);
    this.unitText.setTextOrigin(VPos.BOTTOM);
    this.unitText.setText(this.control.getUnit());
    this.unitText.setLayoutX((this.size - this.unitText.getLayoutBounds().getWidth()) / 2.0D);
    this.unitText.setLayoutY(this.size - this.unitText.getLayoutBounds().getHeight());
    this.unitText.setEffect(localInnerShadow2);
    this.minLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, this.control.getMinMaxLabelFontSize()));
    this.minLabel.setFill(this.control.getMinLabelColor());
    this.minLabel.setTextAlignment(TextAlignment.CENTER);
    this.minLabel.setTextOrigin(VPos.BOTTOM);
    this.minLabel.setText(this.valueFormat.format(this.control.getMinValue()));
    this.minLabel.setLayoutX(this.size * 0.025D);
    this.minLabel.setLayoutY(this.size - this.minLabel.getLayoutBounds().getHeight() - this.size * 0.025D);
    this.maxLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, this.control.getMinMaxLabelFontSize()));
    this.maxLabel.setFill(this.control.getMaxLabelColor());
    this.maxLabel.setTextAlignment(TextAlignment.CENTER);
    this.maxLabel.setTextOrigin(VPos.BOTTOM);
    this.maxLabel.setText(this.valueFormat.format(this.control.getMaxValue()));
    this.maxLabel.setLayoutX(this.size - this.maxLabel.getLayoutBounds().getWidth() - this.size * 0.025D);
    this.maxLabel.setLayoutY(this.size - this.maxLabel.getLayoutBounds().getHeight() - this.size * 0.025D);
    this.alertIndicator.setLayoutX((this.size - this.alertIndicator.getWidth()) * 0.5D);
    this.alertIndicator.setLayoutY(this.size * 0.6D);
    this.gauge.getChildren().addAll(new Node[] { localArc, this.bar, this.valueText, this.unitText, this.minLabel, this.maxLabel, this.alertIndicator });
    this.gauge.setCache(true);
    this.gauge.setCacheHint(CacheHint.QUALITY);
  }
  
  private void drawCanvasGauge(GraphicsContext paramGraphicsContext)
  {
    paramGraphicsContext.clearRect(0.0D, 0.0D, this.size, this.size);
    paramGraphicsContext.setStroke(this.control.getBarFrameColor());
    paramGraphicsContext.setLineWidth(this.control.getBarWidth() + 2.0D);
    if (this.control.isRoundedBar()) {
      paramGraphicsContext.setLineCap(StrokeLineCap.ROUND);
    } else {
      paramGraphicsContext.setLineCap(StrokeLineCap.BUTT);
    }
    paramGraphicsContext.strokeArc(this.xy, this.xy, this.wh, this.wh, this.startAngle, -this.length, ArcType.OPEN);
    paramGraphicsContext.setStroke(this.control.getBarBackgroundColor());
    paramGraphicsContext.setLineWidth(this.control.getBarWidth());
    if (this.control.isRoundedBar()) {
      paramGraphicsContext.setLineCap(StrokeLineCap.ROUND);
    } else {
      paramGraphicsContext.setLineCap(StrokeLineCap.BUTT);
    }
    paramGraphicsContext.strokeArc(this.xy, this.xy, this.wh, this.wh, this.startAngle, -this.length, ArcType.OPEN);
    paramGraphicsContext.setStroke(new RadialGradient(0.0D, 0.0D, 0.5D * this.size, 0.5D * this.size, 0.5D * this.wh, false, CycleMethod.NO_CYCLE, this.barGradientStops));
    paramGraphicsContext.setLineWidth(this.control.getBarWidth());
    if (this.control.isRoundedBar()) {
      paramGraphicsContext.setLineCap(StrokeLineCap.ROUND);
    } else {
      paramGraphicsContext.setLineCap(StrokeLineCap.BUTT);
    }
    paramGraphicsContext.strokeArc(this.xy, this.xy, this.wh, this.wh, this.startAngle, -this.gaugeValue.doubleValue() * this.control.getAngleStep(), ArcType.OPEN);
    if (this.control.isValueLabelVisible())
    {
      paramGraphicsContext.setFont(Font.font("Verdana", FontWeight.BOLD, this.control.getValueLabelFontSize()));
      paramGraphicsContext.setFill(this.control.getValueLabelColor());
      paramGraphicsContext.setTextAlign(TextAlignment.CENTER);
      paramGraphicsContext.setTextBaseline(VPos.CENTER);
      paramGraphicsContext.fillText(this.valueFormat.format(this.gaugeValue.doubleValue()), this.size / 2.0D, this.size / 2.0D);
    }
    if ((this.control.isUnitLabelVisible()) && (!this.control.getUnit().isEmpty()))
    {
      paramGraphicsContext.setFont(Font.font("Verdana", FontWeight.BOLD, this.control.getUnitLabelFontSize()));
      paramGraphicsContext.setFill(this.control.getUnitLabelColor());
      paramGraphicsContext.setTextAlign(TextAlignment.CENTER);
      paramGraphicsContext.setTextBaseline(VPos.BOTTOM);
      paramGraphicsContext.fillText(this.control.getUnit(), this.size / 2.0D, this.size - this.control.getUnitLabelFontSize());
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/SimpleRadialGaugeSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */