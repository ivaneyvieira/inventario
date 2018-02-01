package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.effect.InnerShadowBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.SmallRadialBehavior;
import jfxtras.labs.scene.control.gauge.Gauge.RadialRange;
import jfxtras.labs.scene.control.gauge.Section;
import jfxtras.labs.scene.control.gauge.SmallRadial;

public class SmallRadialSkin
  extends SkinBase<SmallRadial, SmallRadialBehavior>
{
  private SmallRadial control;
  private Group smallRadial;
  private Path pointer;
  private Text valueText;
  private Canvas background;
  private GraphicsContext ctxBackground;
  private Canvas ledOn;
  private GraphicsContext ctxLedOn;
  private boolean on;
  private Point2D center;
  private double angleOffset;
  private NumberFormat valueFormat;
  private Timeline timeline;
  private DoubleProperty gaugeValue;
  private double size;
  private Rotate rotate;
  private boolean isDirty;
  private boolean initialized;
  private long lastTimerCall;
  private AnimationTimer timer;
  
  public SmallRadialSkin(SmallRadial paramSmallRadial)
  {
    super(paramSmallRadial, new SmallRadialBehavior(paramSmallRadial));
    this.control = paramSmallRadial;
    this.initialized = false;
    this.isDirty = false;
    this.smallRadial = new Group();
    this.pointer = new Path();
    this.valueFormat = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));
    this.valueText = new Text(this.valueFormat.format(this.control.getValue()));
    this.background = new Canvas();
    this.ctxBackground = this.background.getGraphicsContext2D();
    this.ledOn = new Canvas();
    this.ctxLedOn = this.ledOn.getGraphicsContext2D();
    this.on = false;
    this.center = new Point2D(100.0D, 100.0D);
    this.angleOffset = (-(this.control.getRadialRange().ANGLE_RANGE * 0.5D));
    this.timeline = new Timeline();
    this.gaugeValue = new SimpleDoubleProperty(this.control.getValue());
    this.size = 200.0D;
    this.rotate = RotateBuilder.create().pivotX(100.0D).pivotY(100.0D).angle(this.angleOffset + (this.gaugeValue.get() - this.control.getMinValue()) * this.control.getAngleStep()).build();
    this.lastTimerCall = System.nanoTime();
    this.timer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (paramAnonymousLong > SmallRadialSkin.this.lastTimerCall + 500000000L)
        {
          SmallRadialSkin.access$180(SmallRadialSkin.this, 1);
          SmallRadialSkin.this.ledOn.setVisible(SmallRadialSkin.this.on);
          SmallRadialSkin.this.lastTimerCall = paramAnonymousLong;
        }
      }
    };
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(121.0D, 121.0D);
    }
    this.ledOn.setVisible(false);
    registerChangeListener(this.control.widthProperty(), "WIDTH");
    registerChangeListener(this.control.heightProperty(), "HEIGHT");
    registerChangeListener(this.control.prefWidthProperty(), "WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "HEIGHT");
    registerChangeListener(this.control.minWidthProperty(), "WIDTH");
    registerChangeListener(this.control.minHeightProperty(), "HEIGHT");
    registerChangeListener(this.control.maxWidthProperty(), "WIDTH");
    registerChangeListener(this.control.maxHeightProperty(), "HEIGHT");
    registerChangeListener(this.control.titleProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.ledVisibleProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.minValueProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.maxValueProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.gaugeModelProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.labelColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.frameColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.backgroundColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.tickMarkColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.pointerColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.centerKnobColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.thresholdLedColorProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.pointerShadowVisibleProperty(), "FULL_REPAINT");
    registerChangeListener(this.control.noOfDecimalsProperty(), "LABEL");
    registerChangeListener(this.control.valueProperty(), "VALUE");
    registerChangeListener(this.control.thresholdExceededProperty(), "THRESHOLD_EXCEEDED");
    registerChangeListener(this.gaugeValue, "GAUGE_VALUE");
    addBindings();
    addListeners();
    this.pointer.getTransforms().add(this.rotate);
    if (this.control.getValue() > this.control.getThreshold()) {
      this.control.setThresholdExceeded(true);
    }
    this.initialized = true;
    repaint();
  }
  
  private void addBindings()
  {
    if (this.valueText.visibleProperty().isBound()) {
      this.valueText.visibleProperty().unbind();
    }
    this.valueText.visibleProperty().bind(this.control.valueLabelVisibleProperty());
  }
  
  private void addListeners()
  {
    this.gaugeValue.addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        if (SmallRadialSkin.this.control.isThresholdBehaviorInverted()) {
          SmallRadialSkin.this.control.setThresholdExceeded(SmallRadialSkin.this.gaugeValue.doubleValue() < SmallRadialSkin.this.control.getThreshold());
        } else {
          SmallRadialSkin.this.control.setThresholdExceeded(SmallRadialSkin.this.gaugeValue.doubleValue() > SmallRadialSkin.this.control.getThreshold());
        }
      }
    });
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("WIDTH".equals(paramString))
    {
      reCalcParameters();
      repaint();
    }
    else if ("HEIGHT".equals(paramString))
    {
      reCalcParameters();
      repaint();
    }
    else if ("FULL_REPAINT".equals(paramString))
    {
      repaint();
    }
    else if ("VALUE".equals(paramString))
    {
      if (this.control.isValueAnimationEnabled())
      {
        KeyValue localKeyValue = new KeyValue(this.gaugeValue, Double.valueOf(this.control.getValue()), Interpolator.EASE_BOTH);
        KeyFrame localKeyFrame = new KeyFrame(Duration.millis(this.control.getTimeToValueInMs()), new KeyValue[] { localKeyValue });
        this.timeline.setOnFinished(new EventHandler()
        {
          public void handle(ActionEvent paramAnonymousActionEvent)
          {
            SmallRadialSkin.this.gaugeValue.set(SmallRadialSkin.this.control.getValue());
          }
        });
        this.timeline = new Timeline();
        this.timeline.getKeyFrames().add(localKeyFrame);
        this.timeline.play();
      }
      else
      {
        this.gaugeValue.set(this.control.getValue());
      }
    }
    else if ("LABEL".equals(paramString))
    {
      updateNumberFormat();
      repaint();
    }
    else if ("GAUGE_VALUE".equals(paramString))
    {
      this.valueText.setText(this.valueFormat.format(this.gaugeValue.get()));
      this.valueText.setX((this.size - this.valueText.getLayoutBounds().getWidth()) * 0.5D);
      this.valueText.setY(0.88D * this.size);
      this.rotate.setAngle(this.angleOffset + (this.gaugeValue.get() - this.control.getMinValue()) * this.control.getAngleStep());
    }
    else if ("THRESHOLD_EXCEEDED".equals(paramString))
    {
      if (this.control.isThresholdExceeded())
      {
        this.timer.start();
      }
      else
      {
        this.timer.stop();
        this.on = false;
        this.ledOn.setVisible(this.on);
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
    drawGauge();
    getChildren().setAll(new Node[] { this.smallRadial });
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final SmallRadial getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 121.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 121.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefHeight(d);
  }
  
  protected double computeMinWidth(double paramDouble)
  {
    return super.computeMinWidth(Math.max(121.0D, paramDouble - getInsets().getLeft() - getInsets().getRight()));
  }
  
  protected double computeMinHeight(double paramDouble)
  {
    return super.computeMinHeight(Math.max(121.0D, paramDouble - getInsets().getTop() - getInsets().getBottom()));
  }
  
  protected double computeMaxWidth(double paramDouble)
  {
    return super.computeMaxWidth(Math.max(121.0D, paramDouble - getInsets().getLeft() - getInsets().getRight()));
  }
  
  protected double computeMaxHeight(double paramDouble)
  {
    return super.computeMaxHeight(Math.max(121.0D, paramDouble - getInsets().getTop() - getInsets().getBottom()));
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
  
  private void reCalcParameters()
  {
    this.size = (this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight());
    double d = 0.5D * this.size;
    this.angleOffset = (-(this.control.getRadialRange().ANGLE_RANGE * 0.5D));
    this.center = new Point2D(d, d);
    this.rotate.setPivotX(d);
    this.rotate.setPivotY(d);
    this.valueText.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 0.11570247933884298D * this.size));
    this.valueText.setX((this.size - this.valueText.getLayoutBounds().getWidth()) * 0.5D);
    this.valueText.setY(0.88D * this.size);
    this.background.setWidth(this.size);
    this.background.setHeight(this.size);
    this.ledOn.setWidth(this.size);
    this.ledOn.setHeight(this.size);
    drawGaugeBackground();
    drawLedOn(this.ctxLedOn);
  }
  
  private final void drawGauge()
  {
    this.valueText.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 0.11570247933884298D * this.size));
    this.valueText.setX((this.size - this.valueText.getLayoutBounds().getWidth()) * 0.5D);
    this.valueText.setY(0.88D * this.size);
    this.valueText.setTextOrigin(VPos.BOTTOM);
    this.valueText.setFill(this.control.getValueLabelColor());
    this.pointer.setFillRule(FillRule.EVEN_ODD);
    this.pointer.getElements().add(new MoveTo(0.5D * this.size, 0.12396694214876033D * this.size));
    this.pointer.getElements().add(new CubicCurveTo(0.5D * this.size, 0.12396694214876033D * this.size, 0.48760330578512395D * this.size, 0.371900826446281D * this.size, 0.48760330578512395D * this.size, 0.4380165289256198D * this.size));
    this.pointer.getElements().add(new CubicCurveTo(0.48760330578512395D * this.size, 0.4380165289256198D * this.size, 0.48760330578512395D * this.size, 0.5619834710743802D * this.size, 0.48760330578512395D * this.size, 0.5619834710743802D * this.size));
    this.pointer.getElements().add(new CubicCurveTo(0.48760330578512395D * this.size, 0.5619834710743802D * this.size, 0.48760330578512395D * this.size, 0.6033057851239669D * this.size, 0.5D * this.size, 0.6033057851239669D * this.size));
    this.pointer.getElements().add(new CubicCurveTo(0.512396694214876D * this.size, 0.6033057851239669D * this.size, 0.512396694214876D * this.size, 0.5619834710743802D * this.size, 0.512396694214876D * this.size, 0.5619834710743802D * this.size));
    this.pointer.getElements().add(new CubicCurveTo(0.512396694214876D * this.size, 0.5619834710743802D * this.size, 0.512396694214876D * this.size, 0.4380165289256198D * this.size, 0.512396694214876D * this.size, 0.4380165289256198D * this.size));
    this.pointer.getElements().add(new CubicCurveTo(0.512396694214876D * this.size, 0.371900826446281D * this.size, 0.5D * this.size, 0.12396694214876033D * this.size, 0.5D * this.size, 0.12396694214876033D * this.size));
    this.pointer.getElements().add(new ClosePath());
    Color localColor = Color.color(this.control.getPointerColor().getRed(), this.control.getPointerColor().getGreen(), this.control.getPointerColor().getBlue(), 0.8D);
    this.pointer.setStroke(localColor.darker());
    this.pointer.setFill(localColor);
    if (this.control.isPointerShadowVisible()) {
      this.pointer.setEffect(DropShadowBuilder.create().radius(0.05D * this.size).color(Color.rgb(0, 0, 0, 0.5D)).blurType(BlurType.GAUSSIAN).build());
    }
    Circle localCircle = new Circle(0.5D * this.size, 0.5D * this.size, 0.06198347107438017D * this.size);
    localCircle.setFill(new LinearGradient(0.0D, localCircle.getLayoutBounds().getMinY(), 0.0D, localCircle.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getCenterKnobColor().brighter()), new Stop(1.0D, this.control.getCenterKnobColor().darker()) }));
    localCircle.setStroke(null);
    localCircle.setEffect(InnerShadowBuilder.create().radius(0.0085D * this.size).blurType(BlurType.GAUSSIAN).build());
    this.smallRadial.getChildren().clear();
    this.smallRadial.getChildren().addAll(new Node[] { this.background, this.valueText, this.ledOn });
    this.smallRadial.getChildren().addAll(new Node[] { this.pointer, localCircle });
  }
  
  private void drawTickmarks(GraphicsContext paramGraphicsContext)
  {
    double d3 = 180.0D + this.control.getRadialRange().ANGLE_RANGE * 0.5D;
    double d4 = 0.0D;
    for (double d5 = this.control.getMinValue(); Double.compare(d5, this.control.getMaxValue()) <= 0; d5 += 1.0D)
    {
      double d1 = Math.sin(Math.toRadians(d4 + d3));
      double d2 = Math.cos(Math.toRadians(d4 + d3));
      Point2D localPoint2D1 = new Point2D(this.center.getX() + this.size * 0.36D * d1, this.center.getY() + this.size * 0.36D * d2);
      Point2D localPoint2D2 = new Point2D(this.center.getX() + this.size * 0.41D * d1, this.center.getY() + this.size * 0.41D * d2);
      Point2D localPoint2D3 = new Point2D(this.center.getX() + this.size * 0.32D * d1, this.center.getY() + this.size * 0.32D * d2);
      paramGraphicsContext.setStroke(this.control.getTickMarkColor());
      if (d5 % 10.0D == 0.0D) {
        paramGraphicsContext.strokeLine(localPoint2D1.getX(), localPoint2D1.getY(), localPoint2D2.getX(), localPoint2D2.getY());
      }
      if ((Double.compare(this.control.getMinValue(), d5) == 0) || (Double.compare(this.control.getMaxValue(), d5) == 0))
      {
        paramGraphicsContext.save();
        paramGraphicsContext.setFont(Font.font("Verdana", FontWeight.NORMAL, 0.04D * this.size));
        paramGraphicsContext.setTextAlign(TextAlignment.CENTER);
        paramGraphicsContext.setFill(this.control.getTickMarkColor());
        paramGraphicsContext.fillText(Integer.toString((int)d5), localPoint2D3.getX(), localPoint2D3.getY());
        paramGraphicsContext.restore();
      }
      d4 -= this.control.getAngleStep();
    }
  }
  
  private final void drawSections(GraphicsContext paramGraphicsContext)
  {
    double d1 = (this.size - 0.77D * this.size) * 0.5D;
    double d2 = this.size * 0.77D;
    double d3 = this.angleOffset - 90.0D;
    Iterator localIterator = this.control.getSections().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      double d4 = (localSection.getStart() - this.control.getMinValue()) * this.control.getAngleStep();
      double d5 = (localSection.getStop() - localSection.getStart()) * this.control.getAngleStep();
      paramGraphicsContext.save();
      paramGraphicsContext.setStroke(localSection.getColor());
      paramGraphicsContext.setLineWidth(this.size * 0.07D);
      paramGraphicsContext.setLineCap(StrokeLineCap.BUTT);
      paramGraphicsContext.strokeArc(d1, d1, d2, d2, -(d3 + d4), -d5, ArcType.OPEN);
      paramGraphicsContext.restore();
    }
  }
  
  private final void drawLedOff(GraphicsContext paramGraphicsContext)
  {
    paramGraphicsContext.save();
    paramGraphicsContext.beginPath();
    paramGraphicsContext.arc(0.3677685950413223D * this.size, 0.6074380165289256D * this.size, 0.05371900826446281D * this.size, 0.05371900826446281D * this.size, 0.0D, 360.0D);
    paramGraphicsContext.setFill(new LinearGradient(0.3305785123966942D * this.size, 0.5702479338842975D * this.size, 0.40654866244152993D * this.size, 0.6462180839291333D * this.size, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0784313725D, 0.0784313725D, 0.0784313725D, 0.6470588235D)), new Stop(0.15D, Color.color(0.0784313725D, 0.0784313725D, 0.0784313725D, 0.6470588235D)), new Stop(0.26D, Color.color(0.1607843137D, 0.1607843137D, 0.1607843137D, 0.6470588235D)), new Stop(0.261D, Color.color(0.1607843137D, 0.1607843137D, 0.1607843137D, 0.6431372549D)), new Stop(0.85D, Color.color(0.7843137255D, 0.7843137255D, 0.7843137255D, 0.4039215686D)), new Stop(1.0D, Color.color(0.7843137255D, 0.7843137255D, 0.7843137255D, 0.3450980392D)) }));
    paramGraphicsContext.fill();
    paramGraphicsContext.restore();
    paramGraphicsContext.save();
    paramGraphicsContext.beginPath();
    paramGraphicsContext.arc(0.3677685950413223D * this.size, 0.6074380165289256D * this.size, 0.045454545454545456D * this.size, 0.045454545454545456D * this.size, 0.0D, 360.0D);
    paramGraphicsContext.setFill(new LinearGradient(0.33884297520661155D * this.size, 0.5785123966942148D * this.size, 0.3972815521641775D * this.size, 0.6369509736517808D * this.size, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getThresholdLedColor().darker().darker()), new Stop(0.49D, this.control.getThresholdLedColor().darker().darker().darker()), new Stop(1.0D, this.control.getThresholdLedColor().darker().darker()) }));
    paramGraphicsContext.fill();
    paramGraphicsContext.applyEffect(InnerShadowBuilder.create().offsetX(0.0D * this.size).offsetY(0.0D * this.size).radius(0.0013523666D * this.size).color(Color.color(0.0D, 0.0D, 0.0D, 1.0D)).blurType(BlurType.GAUSSIAN).input(null).build());
    paramGraphicsContext.restore();
    paramGraphicsContext.save();
    paramGraphicsContext.beginPath();
    paramGraphicsContext.arc(0.3677685950413223D * this.size, 0.6074380165289256D * this.size, 0.0371900826446281D * this.size, 0.0371900826446281D * this.size, 0.0D, 360.0D);
    paramGraphicsContext.setFill(new RadialGradient(0.0D, 0.0D, 0.33884297520661155D * this.size, 0.5785123966942148D * this.size, 0.0371900826446281D * this.size, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.7843137255D, 0.7607843137D, 0.8156862745D, 0.6D)), new Stop(1.0D, Color.TRANSPARENT) }));
    paramGraphicsContext.fill();
    paramGraphicsContext.restore();
  }
  
  private final void drawLedOn(GraphicsContext paramGraphicsContext)
  {
    paramGraphicsContext.save();
    paramGraphicsContext.beginPath();
    paramGraphicsContext.arc(0.3677685950413223D * this.size, 0.6074380165289256D * this.size, 0.045454545454545456D * this.size, 0.045454545454545456D * this.size, 0.0D, 360.0D);
    paramGraphicsContext.setFill(new LinearGradient(0.33884297520661155D * this.size, 0.5785123966942148D * this.size, 0.3972815521641775D * this.size, 0.6369509736517808D * this.size, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getThresholdLedColor()), new Stop(0.49D, this.control.getThresholdLedColor().darker()), new Stop(1.0D, this.control.getThresholdLedColor().brighter()) }));
    paramGraphicsContext.fill();
    paramGraphicsContext.applyEffect(DropShadowBuilder.create().offsetX(0.0D * this.size).offsetY(0.0D * this.size).radius(0.05D * this.size).color(this.control.getThresholdLedColor().brighter()).blurType(BlurType.GAUSSIAN).input(InnerShadowBuilder.create().offsetX(0.0D * this.size).offsetY(0.0D * this.size).radius(0.0013523666D * this.size).color(Color.color(0.0D, 0.0D, 0.0D, 1.0D)).blurType(BlurType.GAUSSIAN).build()).build());
    paramGraphicsContext.restore();
    paramGraphicsContext.save();
    paramGraphicsContext.beginPath();
    paramGraphicsContext.arc(0.3677685950413223D * this.size, 0.6074380165289256D * this.size, 0.0371900826446281D * this.size, 0.0371900826446281D * this.size, 0.0D, 360.0D);
    paramGraphicsContext.setFill(new RadialGradient(0.0D, 0.0D, 0.33884297520661155D * this.size, 0.5785123966942148D * this.size, 0.0371900826446281D * this.size, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.7843137255D, 0.7607843137D, 0.8156862745D, 0.6D)), new Stop(1.0D, Color.TRANSPARENT) }));
    paramGraphicsContext.fill();
    paramGraphicsContext.restore();
  }
  
  private final void drawGaugeBackground()
  {
    this.ctxBackground.clearRect(0.0D, 0.0D, this.size, this.size);
    this.ctxBackground.save();
    this.ctxBackground.beginPath();
    this.ctxBackground.arc(0.5D * this.size, 0.5D * this.size, 0.5D * this.size, 0.5D * this.size, 0.0D, 360.0D);
    this.ctxBackground.setFill(new LinearGradient(0.0D, 0.0D, 0.0D, this.size, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getFrameColor().brighter()), new Stop(1.0D, this.control.getFrameColor().darker()) }));
    this.ctxBackground.fill();
    this.ctxBackground.restore();
    this.ctxBackground.save();
    this.ctxBackground.beginPath();
    this.ctxBackground.arc(0.5D * this.size, 0.5D * this.size, 0.43388429752066116D * this.size, 0.43388429752066116D * this.size, 0.0D, 360.0D);
    this.ctxBackground.setFill(new LinearGradient(0.0D, 0.0661157025D * this.size, 0.0D, 0.9338842975D * this.size, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getBackgroundColor().darker()), new Stop(1.0D, this.control.getBackgroundColor().brighter()) }));
    this.ctxBackground.fill();
    this.ctxBackground.arc(0.5D * this.size, 0.5D * this.size, 0.43388429752066116D * this.size, 0.43388429752066116D * this.size, 0.0D, 360.0D);
    this.ctxBackground.setFill(new RadialGradient(0.0D, 0.0D, 0.5D * this.size, 0.57D * this.size, 0.5D * this.size, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(0.9D, Color.TRANSPARENT), new Stop(0.95D, Color.rgb(0, 0, 0, 0.1D)), new Stop(1.0D, Color.rgb(0, 0, 0, 0.3D)) }));
    this.ctxBackground.fill();
    this.ctxBackground.restore();
    drawSections(this.ctxBackground);
    drawTickmarks(this.ctxBackground);
    if (this.control.isLedVisible())
    {
      this.ctxBackground.save();
      drawLedOff(this.ctxBackground);
      this.ctxBackground.restore();
    }
    this.ctxBackground.save();
    this.ctxBackground.setFont(Font.font("Verdana", FontWeight.NORMAL, 0.1D * this.size));
    this.ctxBackground.setTextAlign(TextAlignment.CENTER);
    this.ctxBackground.setFill(this.control.getTickMarkColor());
    this.ctxBackground.fillText(this.control.getTitle(), 0.5D * this.size, 0.35D * this.size);
    this.ctxBackground.restore();
    this.background.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/SmallRadialSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */