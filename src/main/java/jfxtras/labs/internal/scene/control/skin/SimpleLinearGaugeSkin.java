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
import javafx.geometry.Orientation;
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
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.SimpleLinearGaugeBehavior;
import jfxtras.labs.scene.control.gauge.Section;
import jfxtras.labs.scene.control.gauge.SimpleLinearGauge;

public class SimpleLinearGaugeSkin
  extends GaugeSkinBase<SimpleLinearGauge, SimpleLinearGaugeBehavior>
{
  private SimpleLinearGauge control;
  private boolean isDirty;
  private boolean initialized;
  private Canvas canvas;
  private GraphicsContext ctx;
  private Pane gauge;
  private Point2D center;
  private Line bar;
  private Text valueText;
  private NumberFormat valueFormat;
  private Text unitText;
  private Timeline timeline;
  private DoubleProperty gaugeValue;
  private double width;
  private double height;
  private Text minLabel;
  private Text maxLabel;
  private Orientation orientation;
  private double stepsize;
  private Stop[] barGradientStops;
  
  public SimpleLinearGaugeSkin(SimpleLinearGauge paramSimpleLinearGauge)
  {
    super(paramSimpleLinearGauge, new SimpleLinearGaugeBehavior(paramSimpleLinearGauge));
    this.control = paramSimpleLinearGauge;
    this.initialized = false;
    this.isDirty = false;
    this.canvas = new Canvas();
    this.ctx = this.canvas.getGraphicsContext2D();
    this.gauge = new Pane();
    this.center = new Point2D(100.0D, 50.0D);
    this.bar = new Line();
    this.valueText = new Text();
    this.valueFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    this.unitText = new Text();
    this.timeline = new Timeline();
    this.gaugeValue = new SimpleDoubleProperty(this.control.getValue());
    this.width = 200.0D;
    this.height = 60.0D;
    this.minLabel = new Text(this.valueFormat.format(this.control.getMinValue()));
    this.maxLabel = new Text(this.valueFormat.format(this.control.getMaxValue()));
    this.orientation = Orientation.HORIZONTAL;
    this.stepsize = 1.0D;
    this.barGradientStops = new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(0.8D, this.control.getBarColor().darker()), new Stop(1.0D, this.control.getBarColor().brighter()) };
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(200.0D, 100.0D);
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
      this.width = this.control.getPrefWidth();
      this.height = this.control.getPrefHeight();
      this.orientation = (this.width < this.height ? Orientation.VERTICAL : Orientation.HORIZONTAL);
      this.canvas.setWidth(this.width);
      this.canvas.setHeight(this.height);
      repaint();
    }
    else if ("HEIGHT".equals(paramString))
    {
      this.width = this.control.getPrefWidth();
      this.height = this.control.getPrefHeight();
      this.orientation = (this.width < this.height ? Orientation.VERTICAL : Orientation.HORIZONTAL);
      this.canvas.setWidth(this.width);
      this.canvas.setHeight(this.height);
      repaint();
    }
    else if ("FULL_REPAINT".equals(paramString))
    {
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
          localObject1 = new KeyValue(this.gaugeValue, Double.valueOf(this.control.getValue()), Interpolator.SPLINE(0.5D, 0.4D, 0.4D, 1.0D));
          localObject2 = new KeyFrame(Duration.millis(1500.0D), new KeyValue[] { localObject1 });
          this.timeline.setOnFinished(new EventHandler()
          {
            public void handle(ActionEvent paramAnonymousActionEvent)
            {
              SimpleLinearGaugeSkin.this.gaugeValue.set(SimpleLinearGaugeSkin.this.control.getValue());
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
        if ((this.control.isThresholdBehaviorInverted()) && (this.gaugeValue.doubleValue() < this.control.getThreshold())) {
          this.control.setThresholdExceeded(true);
        } else if ((!this.control.isThresholdBehaviorInverted()) && (this.gaugeValue.doubleValue() > this.control.getThreshold())) {
          this.control.setThresholdExceeded(true);
        } else {
          this.control.setThresholdExceeded(false);
        }
        this.valueText.setText(this.valueFormat.format(this.gaugeValue.get()));
        switch (this.orientation)
        {
        case VERTICAL: 
          this.valueText.setLayoutX((this.width - this.valueText.getLayoutBounds().getWidth()) / 2.0D);
          this.bar.setEndY(this.height - this.control.getBarWidth() - (this.gaugeValue.get() - this.control.getMinValue()) * this.stepsize);
          break;
        case HORIZONTAL: 
        default: 
          this.valueText.setLayoutX((this.width - this.valueText.getLayoutBounds().getWidth()) / 2.0D);
          this.bar.setEndX(this.control.getBarWidth() + (this.gaugeValue.get() - this.control.getMinValue()) * this.stepsize);
        }
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
      drawCanvasGauge(this.ctx);
      getChildren().setAll(new Node[] { this.canvas });
    }
    else
    {
      drawNodeGauge();
      getChildren().setAll(new Node[] { this.gauge });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final SimpleLinearGauge getSkinnable()
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
    double d = 100.0D;
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
    switch (this.orientation)
    {
    case VERTICAL: 
      this.bar.setStroke(new LinearGradient(this.bar.getStartX() - this.control.getBarWidth() / 2.0D, 0.0D, this.bar.getStartX() + this.control.getBarWidth() / 2.0D, 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, paramColor), new Stop(0.5D, paramColor), new Stop(1.0D, paramColor.darker()) }));
      break;
    case HORIZONTAL: 
    default: 
      this.bar.setStroke(new LinearGradient(0.0D, this.bar.getStartY() - this.control.getBarWidth() / 2.0D, 0.0D, this.bar.getStartY() + this.control.getBarWidth() / 2.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, paramColor), new Stop(0.5D, paramColor), new Stop(1.0D, paramColor.darker()) }));
    }
  }
  
  public final void drawNodeGauge()
  {
    this.width = this.control.getPrefWidth();
    this.height = this.control.getPrefHeight();
    this.orientation = (this.width < this.height ? Orientation.VERTICAL : Orientation.HORIZONTAL);
    double d = this.width < this.height ? this.height : this.width;
    this.center = new Point2D(this.width / 2.0D, this.height / 2.0D);
    switch (this.orientation)
    {
    case VERTICAL: 
      this.stepsize = Math.abs((this.height - 2.0D * this.control.getBarWidth() - this.valueText.getLayoutBounds().getHeight() - 5.0D - this.control.getBarWidth() / 2.0D) / this.control.getRange());
      break;
    case HORIZONTAL: 
    default: 
      this.stepsize = Math.abs((this.width - 2.0D * this.control.getBarWidth()) / this.control.getRange());
    }
    this.gauge.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, this.width, this.width);
    localRectangle.setOpacity(0.0D);
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setRadius(0.01D * d);
    localInnerShadow1.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow1.setColor(Color.rgb(0, 0, 0, 0.65D));
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setRadius(0.005D * d);
    localInnerShadow2.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow2.setOffsetY(-1.0D);
    localInnerShadow2.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.65D));
    Line localLine = new Line();
    switch (this.orientation)
    {
    case VERTICAL: 
      localLine.setStartX(this.center.getX());
      localLine.setStartY(this.height - this.control.getBarWidth());
      localLine.setEndX(this.center.getX());
      localLine.setEndY(this.control.getBarWidth() + this.valueText.getLayoutBounds().getHeight() + 5.0D + this.control.getBarWidth() / 2.0D);
      break;
    case HORIZONTAL: 
    default: 
      localLine.setStartX(this.control.getBarWidth());
      localLine.setStartY(this.center.getY());
      localLine.setEndX(this.width - this.control.getBarWidth());
      localLine.setEndY(this.center.getY());
    }
    localLine.setFill(null);
    localLine.setSmooth(true);
    localLine.setStroke(this.control.getBarBackgroundColor());
    localLine.setStrokeWidth(this.control.getBarWidth());
    if (this.control.isRoundedBar()) {
      localLine.setStrokeLineCap(StrokeLineCap.ROUND);
    } else {
      localLine.setStrokeLineCap(StrokeLineCap.BUTT);
    }
    localLine.setEffect(localInnerShadow1);
    switch (this.orientation)
    {
    case VERTICAL: 
      this.bar.setStartX(localLine.getStartX());
      this.bar.setStartY(localLine.getStartY());
      this.bar.setEndX(localLine.getEndX());
      this.bar.setEndY(localLine.getStartY() - (this.gaugeValue.get() - this.control.getMinValue()) * this.stepsize);
      break;
    case HORIZONTAL: 
    default: 
      this.bar.setStartX(localLine.getStartX());
      this.bar.setStartY(localLine.getStartY());
      this.bar.setEndX(this.control.getBarWidth() + (this.gaugeValue.get() - this.control.getMinValue()) * this.stepsize);
      this.bar.setEndY(localLine.getEndY());
    }
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
    this.valueText.setTextOrigin(VPos.BOTTOM);
    this.valueText.setText(this.valueFormat.format(this.gaugeValue.doubleValue()));
    this.valueText.setEffect(localInnerShadow2);
    this.unitText.setFont(Font.font("Verdana", FontWeight.BOLD, this.control.getUnitLabelFontSize()));
    this.unitText.setTextAlignment(TextAlignment.CENTER);
    this.unitText.setTextOrigin(VPos.BOTTOM);
    this.unitText.setText(this.control.getUnit());
    this.unitText.setEffect(localInnerShadow2);
    this.minLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, this.control.getMinMaxLabelFontSize()));
    this.minLabel.setFill(this.control.getMinLabelColor());
    this.minLabel.setTextAlignment(TextAlignment.CENTER);
    this.minLabel.setTextOrigin(VPos.BOTTOM);
    this.minLabel.setText(this.valueFormat.format(this.control.getMinValue()));
    this.maxLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, this.control.getMinMaxLabelFontSize()));
    this.maxLabel.setFill(this.control.getMaxLabelColor());
    this.maxLabel.setTextAlignment(TextAlignment.CENTER);
    this.maxLabel.setTextOrigin(VPos.BOTTOM);
    this.maxLabel.setText(this.valueFormat.format(this.control.getMaxValue()));
    switch (this.orientation)
    {
    case VERTICAL: 
      this.valueText.setLayoutX((this.width - this.valueText.getLayoutBounds().getWidth()) / 2.0D);
      this.valueText.setLayoutY(this.valueText.getLayoutBounds().getHeight());
      this.unitText.setLayoutX(this.center.getX() - this.control.getBarWidth() / 2.0D - this.unitText.getLayoutBounds().getHeight() - 5.0D);
      this.unitText.setLayoutY(localLine.getEndY() + (localLine.getStartY() - localLine.getEndY()) / 2.0D + this.unitText.getLayoutBounds().getHeight() / 2.0D);
      this.minLabel.setLayoutX(this.center.getX() - this.control.getBarWidth() / 2.0D - this.minLabel.getLayoutBounds().getWidth() - 2.0D);
      this.minLabel.setLayoutY(localLine.getStartY());
      this.maxLabel.setLayoutX(this.center.getX() - this.control.getBarWidth() / 2.0D - this.maxLabel.getLayoutBounds().getWidth() - 2.0D);
      this.maxLabel.setLayoutY(localLine.getEndY() + this.maxLabel.getLayoutBounds().getHeight());
      break;
    case HORIZONTAL: 
    default: 
      this.valueText.setLayoutX((this.width - this.valueText.getLayoutBounds().getWidth()) / 2.0D);
      this.valueText.setLayoutY(this.center.getY() - this.control.getBarWidth() / 2.0D - 2.0D);
      this.unitText.setLayoutX((this.width - this.unitText.getLayoutBounds().getWidth()) / 2.0D);
      this.unitText.setLayoutY(this.center.getY() + this.control.getBarWidth() / 2.0D + this.unitText.getLayoutBounds().getHeight() + 2.0D);
      this.minLabel.setLayoutX(localLine.getStartX());
      this.minLabel.setLayoutY(this.center.getY() + this.control.getBarWidth() / 2.0D + this.minLabel.getLayoutBounds().getHeight() + 2.0D);
      this.maxLabel.setLayoutX(localLine.getEndX() - this.maxLabel.getLayoutBounds().getWidth());
      this.maxLabel.setLayoutY(this.center.getY() + this.control.getBarWidth() / 2.0D + this.maxLabel.getLayoutBounds().getHeight() + 2.0D);
    }
    this.gauge.getChildren().addAll(new Node[] { localRectangle, localLine, this.bar, this.valueText, this.unitText, this.minLabel, this.maxLabel });
    this.gauge.setCache(true);
    this.gauge.setCacheHint(CacheHint.QUALITY);
  }
  
  private void drawCanvasGauge(GraphicsContext paramGraphicsContext)
  {
    if (Orientation.VERTICAL == this.orientation) {
      this.stepsize = Math.abs((this.height - 2.0D * this.control.getBarWidth() - this.valueText.getLayoutBounds().getHeight() - 5.0D - this.control.getBarWidth() / 2.0D) / this.control.getRange());
    } else {
      this.stepsize = Math.abs((this.width - 2.0D * this.control.getBarWidth()) / this.control.getRange());
    }
    paramGraphicsContext.clearRect(0.0D, 0.0D, this.width, this.height);
    paramGraphicsContext.setStroke(this.control.getBarFrameColor());
    paramGraphicsContext.setLineWidth(this.control.getBarWidth() + 2.0D);
    if (this.control.isRoundedBar()) {
      paramGraphicsContext.setLineCap(StrokeLineCap.ROUND);
    } else {
      paramGraphicsContext.setLineCap(StrokeLineCap.BUTT);
    }
    if (Orientation.VERTICAL == this.orientation) {
      paramGraphicsContext.strokeLine(this.center.getX(), this.height - this.control.getBarWidth(), this.center.getX(), this.control.getBarWidth() + this.valueText.getLayoutBounds().getHeight() + 5.0D + this.control.getBarWidth() / 2.0D);
    } else {
      paramGraphicsContext.strokeLine(this.control.getBarWidth(), this.center.getY(), this.width - this.control.getBarWidth(), this.center.getY());
    }
    paramGraphicsContext.setStroke(this.control.getBarBackgroundColor());
    paramGraphicsContext.setLineWidth(this.control.getBarWidth());
    if (this.control.isRoundedBar()) {
      paramGraphicsContext.setLineCap(StrokeLineCap.ROUND);
    } else {
      paramGraphicsContext.setLineCap(StrokeLineCap.BUTT);
    }
    if (Orientation.VERTICAL == this.orientation) {
      paramGraphicsContext.strokeLine(this.center.getX(), this.height - this.control.getBarWidth(), this.center.getX(), this.control.getBarWidth() + this.valueText.getLayoutBounds().getHeight() + 5.0D + this.control.getBarWidth() / 2.0D - (this.gaugeValue.get() - this.control.getMinValue()) * this.stepsize);
    } else {
      paramGraphicsContext.strokeLine(this.control.getBarWidth(), this.center.getY(), this.width - this.control.getBarWidth() + (this.gaugeValue.get() - this.control.getMinValue()) * this.stepsize, this.center.getY());
    }
    if (this.control.isValueLabelVisible())
    {
      paramGraphicsContext.setFont(Font.font("Verdana", FontWeight.BOLD, this.control.getValueLabelFontSize()));
      paramGraphicsContext.setFill(this.control.getValueLabelColor());
      paramGraphicsContext.setTextAlign(TextAlignment.CENTER);
      paramGraphicsContext.setTextBaseline(VPos.CENTER);
      if (Orientation.VERTICAL == this.orientation) {
        paramGraphicsContext.fillText(this.valueFormat.format(this.gaugeValue.doubleValue()), this.width / 2.0D, this.control.getValueLabelFontSize());
      } else {
        paramGraphicsContext.fillText(this.valueFormat.format(this.gaugeValue.doubleValue()), this.width / 2.0D, this.control.getValueLabelFontSize());
      }
    }
    if ((this.control.isUnitLabelVisible()) && (!this.control.getUnit().isEmpty()))
    {
      paramGraphicsContext.setFont(Font.font("Verdana", FontWeight.BOLD, this.control.getUnitLabelFontSize()));
      paramGraphicsContext.setFill(this.control.getUnitLabelColor());
      paramGraphicsContext.setTextAlign(TextAlignment.CENTER);
      paramGraphicsContext.setTextBaseline(VPos.BOTTOM);
      if (Orientation.VERTICAL == this.orientation) {
        paramGraphicsContext.fillText(this.control.getUnit(), this.width / 2.0D, this.height - this.control.getUnitLabelFontSize());
      } else {
        paramGraphicsContext.fillText(this.control.getUnit(), this.width / 2.0D, this.height - this.control.getUnitLabelFontSize());
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/SimpleLinearGaugeSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */