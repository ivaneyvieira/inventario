package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
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
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.ClockBehavior;
import jfxtras.labs.scene.control.gauge.Clock;
import jfxtras.labs.scene.control.gauge.Clock.ClockStyle;
import jfxtras.labs.scene.control.gauge.Clock.Theme;

public class ClockSkin
  extends SkinBase<Clock, ClockBehavior>
{
  private static final long INTERVAL = 50000000L;
  private static final Calendar CAL = ;
  private Clock control;
  private int dst;
  private Group hourPointer;
  private Group hourPointerShadow;
  private Group minutePointer;
  private Group minutePointerShadow;
  private Group secondPointer;
  private Group secondPointerShadow;
  private Group clock;
  private DoubleProperty hourAngle;
  private DoubleProperty minuteAngle;
  private DoubleProperty secondAngle;
  private int hourOffset;
  private int minuteOffset;
  private int lastHour;
  private DoubleProperty minute;
  private long lastTimerCall;
  private boolean isDay;
  private Timeline timeline;
  private AnimationTimer timer;
  private boolean isDirty;
  private boolean initialized;
  
  public ClockSkin(Clock paramClock)
  {
    super(paramClock, new ClockBehavior(paramClock));
    this.control = paramClock;
    CAL.setTimeZone(TimeZone.getTimeZone(this.control.getTimeZone()));
    this.dst = (this.control.isDaylightSavingTime() ? 1 : 0);
    this.hourPointer = new Group();
    this.hourPointerShadow = new Group(new Node[] { this.hourPointer });
    this.minutePointer = new Group();
    this.minutePointerShadow = new Group(new Node[] { this.minutePointer });
    this.secondPointer = new Group();
    this.secondPointerShadow = new Group(new Node[] { this.secondPointer });
    this.clock = new Group();
    this.hourAngle = new SimpleDoubleProperty(30 * Calendar.getInstance().get(10) + this.dst);
    this.minuteAngle = new SimpleDoubleProperty(6 * Calendar.getInstance().get(12));
    this.secondAngle = new SimpleDoubleProperty(6 * Calendar.getInstance().get(13));
    this.hourOffset = (CAL.get(11) - Calendar.getInstance().get(11));
    this.minuteOffset = (CAL.get(12) - Calendar.getInstance().get(12));
    this.lastHour = CAL.get(11);
    this.minute = new SimpleDoubleProperty(0.0D);
    this.lastTimerCall = 0L;
    this.isDay = true;
    this.timeline = new Timeline();
    this.timer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        long l = System.nanoTime();
        if (l >= ClockSkin.this.lastTimerCall + 50000000L)
        {
          ClockSkin.CAL.setTimeZone(TimeZone.getTimeZone(ClockSkin.this.control.getTimeZone()));
          if (ClockSkin.CAL.get(11) < ClockSkin.this.lastHour)
          {
            ClockSkin.this.checkForNight();
            ClockSkin.this.repaint();
          }
          ClockSkin.this.secondAngle.set(Calendar.getInstance().get(13) * 6 + Calendar.getInstance().get(14) * 0.006D);
          ClockSkin.this.minute.set((ClockSkin.this.minuteOffset + Calendar.getInstance().get(12)) * 6);
          ClockSkin.this.hourAngle.set((ClockSkin.this.hourOffset + Calendar.getInstance().get(10)) * 30 + 0.5D * Calendar.getInstance().get(12));
          ClockSkin.this.lastTimerCall = l;
          ClockSkin.this.lastHour = ClockSkin.CAL.get(11);
          ClockSkin.this.control.setHour(ClockSkin.CAL.get(11));
          ClockSkin.this.control.setMinute(ClockSkin.CAL.get(12));
          ClockSkin.this.control.setSecond(ClockSkin.CAL.get(13));
        }
      }
    };
    this.minute.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        ClockSkin.this.moveMinutePointer(paramAnonymousNumber2.doubleValue());
      }
    });
    this.isDirty = false;
    this.initialized = false;
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(200.0D, 200.0D);
    }
    if (this.control.getBrightBackgroundPaint() == null) {
      this.control.setBrightBackgroundPaint(new RadialGradient(0.0D, 0.0D, getPrefWidth() / 2.0D, getPrefHeight() / 2.0D, getPrefWidth() / 2.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(191, 207, 197)), new Stop(0.7D, Color.rgb(226, 239, 229)), new Stop(1.0D, Color.rgb(199, 216, 206)) }));
    }
    if (this.control.getDarkBackgroundPaint() == null) {
      this.control.setDarkBackgroundPaint(new LinearGradient(0.0D, 0.0D, 0.0D, getPrefHeight(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(0, 0, 0)), new Stop(1.0D, Color.rgb(0, 0, 0)) }));
    }
    if (this.secondPointer.visibleProperty().isBound()) {
      this.secondPointer.visibleProperty().unbind();
    }
    this.secondPointer.visibleProperty().bind(this.control.secondPointerVisibleProperty());
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    registerChangeListener(this.control.runningProperty(), "RUNNING");
    registerChangeListener(this.control.timeZoneProperty(), "TIME_ZONE");
    registerChangeListener(this.secondAngle, "SECOND_ANGLE");
    registerChangeListener(this.minuteAngle, "MINUTE_ANGLE");
    registerChangeListener(this.hourAngle, "HOUR_ANGLE");
    registerChangeListener(this.control.themeProperty(), "THEME");
    registerChangeListener(this.control.clockStyleProperty(), "CLOCK_STYLE");
    registerChangeListener(this.control.brightBackgroundPaintProperty(), "BRIGHT_BACKGROUND_PAINT");
    registerChangeListener(this.control.darkBackgroundPaintProperty(), "DARK_BACKGROUND_PAINT");
    registerChangeListener(this.control.brightPointerPaintProperty(), "BRIGHT_POINTER_PAINT");
    registerChangeListener(this.control.darkPointerPaintProperty(), "DARK_POINTER_PAINT");
    registerChangeListener(this.control.brightTickMarkPaintProperty(), "BRIGHT_TICK_MARK_PAINT");
    registerChangeListener(this.control.darkTickMarkPaintProperty(), "DARK_TICK_MARK_PAINT");
    registerChangeListener(this.control.secondPointerPaintProperty(), "SECOND_POINTER_PAINT");
    registerChangeListener(this.control.titleProperty(), "TITLE");
    registerChangeListener(this.control.hourProperty(), "HOUR");
    registerChangeListener(this.control.minuteProperty(), "MINUTE");
    registerChangeListener(this.control.secondProperty(), "SECOND");
    setTime();
    this.initialized = true;
    checkForNight();
    repaint();
    if (this.control.isRunning()) {
      this.timer.start();
    } else if ((this.control.getHour() != 0) || (this.control.getMinute() != 0) || (this.control.getSecond() != 0)) {
      setTime(this.control.getHour(), this.control.getMinute(), this.control.getSecond());
    }
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("TIME_ZONE".equals(paramString))
    {
      setTime();
    }
    else if ("RUNNING".equals(paramString))
    {
      if (this.control.isRunning())
      {
        setTime();
        this.timer.start();
      }
      else
      {
        this.timer.stop();
      }
    }
    else if ("SECOND_ANGLE".equals(paramString))
    {
      this.secondPointer.setRotate(this.secondAngle.get());
    }
    else if ("MINUTE_ANGLE".equals(paramString))
    {
      this.minutePointer.setRotate(this.minuteAngle.get());
    }
    else if ("HOUR_ANGLE".equals(paramString))
    {
      this.hourPointer.setRotate(this.hourAngle.get());
    }
    else if ("TYPE".equals(paramString))
    {
      checkForNight();
      repaint();
    }
    else if ("THEME".equals(paramString))
    {
      repaint();
    }
    else if ("CLOCK_STYLE".equals(paramString))
    {
      drawSecondPointer();
    }
    else if ("BRIGHT_BACKGROUND_PAINT".equals(paramString))
    {
      repaint();
    }
    else if ("DARK_BACKGROUND_PAINT".equals(paramString))
    {
      repaint();
    }
    else if ("BRIGHT_POINTER_PAINT".equals(paramString))
    {
      repaint();
    }
    else if ("DARK_POINTER_PAINT".equals(paramString))
    {
      repaint();
    }
    else if ("BRIGHT_TICK_MARK_PAINT".equals(paramString))
    {
      repaint();
    }
    else if ("DARK_TICK_MARK_PAINT".equals(paramString))
    {
      repaint();
    }
    else if ("SECOND_POINTER_PAINT".equals(paramString))
    {
      repaint();
    }
    else if ("TITLE".equals(paramString))
    {
      repaint();
    }
    else if ("HOUR".equals(paramString))
    {
      if (!this.control.isRunning()) {
        setTime(this.control.getHour(), this.control.getMinute(), this.control.getSecond());
      }
    }
    else if ("MINUTE".equals(paramString))
    {
      if (!this.control.isRunning()) {
        setTime(this.control.getHour(), this.control.getMinute(), this.control.getSecond());
      }
    }
    else if ("SECOND".equals(paramString))
    {
      if (!this.control.isRunning()) {
        setTime(this.control.getHour(), this.control.getMinute(), this.control.getSecond());
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
      drawClock();
      drawMinutePointer();
      drawHourPointer();
      drawSecondPointer();
      drawShadows();
      getChildren().setAll(new Node[] { this.clock, this.minutePointerShadow, this.hourPointerShadow, this.secondPointerShadow });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final Clock getSkinnable()
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
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 200.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  private void setTime()
  {
    CAL.setTimeZone(TimeZone.getTimeZone(this.control.getTimeZone()));
    this.dst = (this.control.isDaylightSavingTime() ? 1 : 0);
    int i = Calendar.getInstance().getTimeZone().inDaylightTime(new Date()) ? 1 : 0;
    this.hourOffset = (CAL.get(11) + this.dst - Calendar.getInstance().get(11) + i);
    this.minuteOffset = (CAL.get(12) - Calendar.getInstance().get(12));
    this.secondAngle.set(Calendar.getInstance().get(13) * 6 + Calendar.getInstance().get(14) * 0.006D);
    this.minuteAngle.set((this.minuteOffset + Calendar.getInstance().get(12)) * 6);
    this.hourAngle.set((this.hourOffset + Calendar.getInstance().get(10)) * 30 + 0.5D * Calendar.getInstance().get(12));
    checkForNight();
  }
  
  private void setTime(int paramInt1, int paramInt2, int paramInt3)
  {
    this.secondAngle.set(paramInt3 * 6);
    this.minuteAngle.set(paramInt2 * 6);
    this.hourAngle.set(paramInt1 * 30 + 0.5D * paramInt2);
    checkForNight();
  }
  
  private void moveMinutePointer(double paramDouble)
  {
    KeyValue localKeyValue = new KeyValue(this.minuteAngle, Double.valueOf(paramDouble), Interpolator.SPLINE(0.5D, 0.4D, 0.4D, 1.0D));
    KeyFrame localKeyFrame = new KeyFrame(Duration.millis(200.0D), new KeyValue[] { localKeyValue });
    this.timeline = new Timeline();
    this.timeline.getKeyFrames().add(localKeyFrame);
    this.timeline.play();
  }
  
  private void checkForNight()
  {
    if (this.control.isAutoDimEnabled())
    {
      if ((CAL.get(11) > 6) && (CAL.get(11) < 18)) {
        this.isDay = true;
      } else {
        this.isDay = false;
      }
    }
    else {
      this.isDay = (this.control.getTheme() == Theme.BRIGHT);
    }
  }
  
  public void drawClock()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.clock.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    this.clock.getChildren().add(localRectangle1);
    Circle localCircle1 = new Circle(0.5D * d2, 0.5D * d3, 0.5D * d2);
    localCircle1.getStyleClass().clear();
    localCircle1.getStyleClass().add("clock-frame-fill");
    localCircle1.setStroke(null);
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.15D * d1);
    localInnerShadow1.setHeight(0.15D * d1);
    localInnerShadow1.setOffsetY(-0.025D * d1);
    localInnerShadow1.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.65D));
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setInput(localInnerShadow1);
    localInnerShadow2.setWidth(0.15D * d1);
    localInnerShadow2.setHeight(0.15D * d1);
    localInnerShadow2.setOffsetY(0.025D * d1);
    localInnerShadow2.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.1D));
    localCircle1.setEffect(localInnerShadow2);
    Circle localCircle2 = new Circle(0.5D * d2, 0.5D * d3, 0.4921259842519685D * d2);
    if (this.control.isAutoDimEnabled())
    {
      if (this.isDay) {
        localCircle2.setFill(this.control.getBrightBackgroundPaint());
      } else {
        localCircle2.setFill(this.control.getDarkBackgroundPaint());
      }
    }
    else if (this.control.getTheme() == Theme.BRIGHT) {
      localCircle2.setFill(this.control.getBrightBackgroundPaint());
    } else {
      localCircle2.setFill(this.control.getDarkBackgroundPaint());
    }
    localCircle2.setStroke(null);
    Text localText = new Text(this.control.getTitle());
    localText.setFontSmoothingType(FontSmoothingType.LCD);
    localText.setFont(Font.font("Verdana", FontWeight.NORMAL, 0.06D * d1));
    localText.setTextAlignment(TextAlignment.CENTER);
    localText.setX((d1 - localText.getLayoutBounds().getWidth()) / 2.0D);
    localText.setY(0.6D * d1 + localText.getLayoutBounds().getHeight());
    if (this.control.isAutoDimEnabled())
    {
      if (this.isDay) {
        localText.setFill(this.control.getBrightTickMarkPaint());
      } else {
        localText.setFill(this.control.getDarkTickMarkPaint());
      }
    }
    else if (this.control.getTheme() == Theme.BRIGHT) {
      localText.setFill(this.control.getBrightTickMarkPaint());
    } else {
      localText.setFill(this.control.getDarkTickMarkPaint());
    }
    InnerShadow localInnerShadow3 = new InnerShadow();
    localInnerShadow3.setWidth(0.0929889298892989D * localCircle2.getLayoutBounds().getWidth());
    localInnerShadow3.setHeight(0.0929889298892989D * localCircle2.getLayoutBounds().getHeight());
    localInnerShadow3.setOffsetY(0.008856088560885609D * d1);
    localInnerShadow3.setRadius(0.0929889298892989D * localCircle2.getLayoutBounds().getWidth());
    localInnerShadow3.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.6470588235D));
    localInnerShadow3.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow3.inputProperty().set(null);
    localCircle2.setEffect(localInnerShadow3);
    Path localPath = new Path();
    if (this.control.getClockStyle() == ClockStyle.IOS6)
    {
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(0.023622047244094488D * d2, 0.36220472440944884D * d3));
      localPath.getElements().add(new CubicCurveTo(0.08661417322834646D * d2, 0.15748031496062992D * d3, 0.2677165354330709D * d2, 0.007874015748031496D * d3, 0.5039370078740157D * d2, 0.007874015748031496D * d3));
      localPath.getElements().add(new CubicCurveTo(0.7322834645669292D * d2, 0.007874015748031496D * d3, 0.9133858267716536D * d2, 0.15748031496062992D * d3, 0.9763779527559056D * d2, 0.36220472440944884D * d3));
      localPath.getElements().add(new CubicCurveTo(0.984251968503937D * d2, 0.3858267716535433D * d3, 0.7480314960629921D * d2, 0.5039370078740157D * d3, 0.5039370078740157D * d2, 0.5039370078740157D * d3));
      localPath.getElements().add(new CubicCurveTo(0.25196850393700787D * d2, 0.49606299212598426D * d3, 0.015748031496062992D * d2, 0.3858267716535433D * d3, 0.023622047244094488D * d2, 0.36220472440944884D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.setFill(Color.color(1.0D, 1.0D, 1.0D, 0.15D));
      localPath.setStroke(null);
    }
    Group localGroup = new Group();
    for (int i = 0; i < 360; i += 6)
    {
      Rotate localRotate = Transform.rotate(i, d1 / 2.0D, d1 / 2.0D);
      Rectangle localRectangle2;
      if (i % 30 == 0) {
        switch (this.control.getClockStyle())
        {
        case IOS6: 
          if (i % 90 == 0) {
            localRectangle2 = new Rectangle(0.4763779528D * d2, 0.023622047244094488D * d3, 0.0472440945D * d2, 0.110701107D * d3);
          } else {
            localRectangle2 = new Rectangle(0.48031496062992124D * d2, 0.023622047244094488D * d3, 0.03937007874015748D * d2, 0.110701107D * d3);
          }
          break;
        case DB: 
          localRectangle2 = new Rectangle(0.48031496062992124D * d2, 0.023622047244094488D * d3, 0.03937007874015748D * d2, 0.110701107D * d3);
          break;
        default: 
          localRectangle2 = new Rectangle(0.49606299212598426D * d2, 0.031496062992125984D * d3, 0.015748031496062992D * d2, 0.06299212598425197D * d3);
          localRectangle2.setArcWidth(0.0078740157D * d2);
          localRectangle2.setArcHeight(0.0078740157D * d3);
          break;
        }
      } else {
        switch (this.control.getClockStyle())
        {
        case IOS6: 
          localRectangle2 = new Rectangle(0.4960629921D * d2, 0.023622047244094488D * d3, 0.0078740157D * d2, 0.047244094488188976D * d3);
          break;
        case DB: 
          localRectangle2 = new Rectangle(0.4881889763779528D * d2, 0.023622047244094488D * d3, 0.023622047244094488D * d2, 0.047244094488188976D * d3);
          break;
        default: 
          localRectangle2 = new Rectangle(0.49606299212598426D * d2, 0.031496062992125984D * d3, 0.007874015748031496D * d2, 0.03937007874015748D * d3);
          localRectangle2.setArcWidth(0.0039370079D * d2);
          localRectangle2.setArcHeight(0.0039370079D * d3);
        }
      }
      if (this.control.isAutoDimEnabled())
      {
        if (this.isDay) {
          localRectangle2.setFill(this.control.getBrightTickMarkPaint());
        } else {
          localRectangle2.setFill(this.control.getDarkTickMarkPaint());
        }
      }
      else if (this.control.getTheme() == Theme.BRIGHT) {
        localRectangle2.setFill(this.control.getBrightTickMarkPaint());
      } else {
        localRectangle2.setFill(this.control.getDarkTickMarkPaint());
      }
      localRectangle2.setStroke(null);
      localRectangle2.getTransforms().add(localRotate);
      localGroup.getChildren().add(localRectangle2);
    }
    this.clock.getChildren().addAll(new Node[] { localCircle1, localCircle2, localText, localPath, localGroup });
    this.clock.setCache(true);
  }
  
  public void drawMinutePointer()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.minutePointer.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    this.minutePointer.getChildren().add(localRectangle);
    Object localObject1;
    Object localObject2;
    Object localObject3;
    switch (this.control.getClockStyle())
    {
    case IOS6: 
      localObject1 = new Rectangle(0.4783464567D * d2, 0.047244094488188976D * d3, 0.0433070866D * d2, 0.5511811024D * d3);
      break;
    case DB: 
      localObject1 = new Rectangle(0.48031496062992124D * d2, 0.047244094488188976D * d3, 0.03937007874015748D * d2, 0.47244094488188976D * d3);
      break;
    default: 
      localObject2 = new Circle(0.5D * d2, 0.5D * d3, 0.03543307086614173D * d2);
      localObject3 = new Rectangle(0.4881889763779528D * d2, 0.12598425196850394D * d3, 0.023622047244094488D * d2, 0.4881889763779528D * d3);
      ((Rectangle)localObject3).setArcWidth(0.011811023622047244D * d2);
      ((Rectangle)localObject3).setArcHeight(0.011811023622047244D * d3);
      localObject1 = Shape.union((Shape)localObject2, (Shape)localObject3);
    }
    if (this.control.isAutoDimEnabled())
    {
      if (this.isDay) {
        ((Shape)localObject1).setFill(this.control.getBrightPointerPaint());
      } else {
        ((Shape)localObject1).setFill(this.control.getDarkPointerPaint());
      }
    }
    else if (this.control.getTheme() == Theme.BRIGHT) {
      ((Shape)localObject1).setFill(this.control.getBrightPointerPaint());
    } else {
      ((Shape)localObject1).setFill(this.control.getDarkPointerPaint());
    }
    ((Shape)localObject1).setStroke(null);
    this.minutePointer.setRotate(this.minuteAngle.get());
    this.minutePointer.getChildren().add(localObject1);
    if (this.control.getClockStyle() == ClockStyle.STANDARD)
    {
      localObject2 = new Rectangle(0.49606299212598426D * d2, 0.13385826771653545D * d3, 0.007874015748031496D * d2, 0.2992125984251969D * d3);
      ((Rectangle)localObject2).setArcWidth(0.003937007874015748D * d2);
      ((Rectangle)localObject2).setArcHeight(0.003937007874015748D * d3);
      localObject3 = Color.color(0.8274509804D, 0.8666666667D, 0.6156862745D, 1.0D);
      ((Rectangle)localObject2).setFill((Paint)localObject3);
      ((Rectangle)localObject2).setStroke(null);
      this.minutePointer.getChildren().add(localObject2);
    }
    this.minutePointer.setCache(true);
  }
  
  public void drawHourPointer()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.hourPointer.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    this.hourPointer.getChildren().add(localRectangle);
    Object localObject1;
    Object localObject2;
    Object localObject3;
    switch (this.control.getClockStyle())
    {
    case IOS6: 
      localObject1 = new Rectangle(0.4783464567D * d2, 0.2440944882D * d3, 0.0433070866D * d2, 0.3543307087D * d3);
      break;
    case DB: 
      localObject1 = new Rectangle(0.47244094488188976D * d2, 0.2125984251968504D * d3, 0.05511811023622047D * d2, 0.2992125984251969D * d3);
      break;
    default: 
      localObject2 = new Circle(0.5D * d2, 0.5D * d3, 0.051181102362204724D * d2);
      localObject3 = new Rectangle(0.48031496062992124D * d2, 0.2047244094488189D * d3, 0.03937007874015748D * d2, 0.3937007874015748D * d3);
      ((Rectangle)localObject3).setArcWidth(0.01968503937007874D * d2);
      ((Rectangle)localObject3).setArcHeight(0.01968503937007874D * d3);
      localObject1 = Shape.union((Shape)localObject2, (Shape)localObject3);
    }
    if (this.control.isAutoDimEnabled())
    {
      if (this.isDay) {
        ((Shape)localObject1).setFill(this.control.getBrightPointerPaint());
      } else {
        ((Shape)localObject1).setFill(this.control.getDarkPointerPaint());
      }
    }
    else if (this.control.getTheme() == Theme.BRIGHT) {
      ((Shape)localObject1).setFill(this.control.getBrightPointerPaint());
    } else {
      ((Shape)localObject1).setFill(this.control.getDarkPointerPaint());
    }
    ((Shape)localObject1).setStroke(null);
    this.hourPointer.setRotate(this.hourAngle.get());
    this.hourPointer.getChildren().add(localObject1);
    if (this.control.getClockStyle() == ClockStyle.STANDARD)
    {
      localObject2 = new Rectangle(0.4881889763779528D * d2, 0.2204724409448819D * d3, 0.023622047244094488D * d2, 0.2125984251968504D * d3);
      ((Rectangle)localObject2).setArcWidth(0.011811023622047244D * d2);
      ((Rectangle)localObject2).setArcHeight(0.011811023622047244D * d3);
      localObject3 = Color.color(0.8235294118D, 0.8588235294D, 0.5882352941D, 1.0D);
      ((Rectangle)localObject2).setFill((Paint)localObject3);
      ((Rectangle)localObject2).setStroke(null);
      this.hourPointer.getChildren().add(localObject2);
    }
    this.hourPointer.setCache(true);
  }
  
  public void drawSecondPointer()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.secondPointer.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    this.secondPointer.getChildren().add(localRectangle1);
    Object localObject1;
    Object localObject2;
    Path localPath;
    switch (this.control.getClockStyle())
    {
    case IOS6: 
      localObject1 = new Circle(0.5D * d2, 0.20078740157480315D * d3, 0.036900369D * d2);
      localObject2 = new Rectangle(0.4926199262D * d2, 0.2204724409D * d3, 0.0147601476D * d2, 0.4409448819D * d3);
      ((Shape)localObject2).setFill(Color.BLACK);
      Circle localCircle1 = new Circle(0.5D * d2, 0.5D * d3, 0.0236220472D * d2);
      Shape localShape = Path.union((Shape)localObject1, (Shape)localObject2);
      localShape.setFill(Color.BLACK);
      localPath = (Path)Path.union(localShape, localCircle1);
      break;
    case DB: 
      Circle localCircle2 = new Circle(d1 * 0.5D, d1 * 0.190909091D, d1 * 0.0454545454D);
      Circle localCircle3 = new Circle(d1 * 0.5D, d1 * 0.190909091D, d1 * 0.0363636364D);
      localPath = (Path)Path.subtract(localCircle2, localCircle3);
      localPath.getElements().add(new MoveTo(d2 * 0.4863636364D, d1 * 0.5D));
      localPath.getElements().add(new LineTo(d2 * 0.5136363636D, d1 * 0.5D));
      localPath.getElements().add(new LineTo(d2 * 0.5045454545D, d2 * 0.0363636364D));
      localPath.getElements().add(new LineTo(d2 * 0.4954545455D, d2 * 0.0363636364D));
      localPath.getElements().add(new ClosePath());
      localPath = (Path)Path.subtract(localPath, new Circle(d1 * 0.5D, d1 * 0.190909091D, d1 * 0.0363636364D));
      break;
    default: 
      Circle localCircle4 = new Circle(0.5D * d2, 0.5D * d3, 0.027559055118110236D * d2);
      Rectangle localRectangle2 = new Rectangle(0.49606299212598426D * d2, 0.06299212598425197D * d3, 0.007874015748031496D * d2, 0.4251968503937008D * d3);
      localRectangle2.setArcWidth(0.007874015748031496D * d2);
      localRectangle2.setArcHeight(0.007874015748031496D * d3);
      localPath = (Path)Shape.union(localCircle4, localRectangle2);
    }
    localPath.setFill(this.control.getSecondPointerPaint());
    localPath.setStroke(null);
    this.secondPointer.getChildren().add(localPath);
    if (this.control.getClockStyle() == ClockStyle.STANDARD)
    {
      localObject1 = new Rectangle(0.49606299212598426D * d2, 0.06299212598425197D * d3, 0.007874015748031496D * d2, 0.03937007874015748D * d3);
      ((Rectangle)localObject1).setArcWidth(0.007874015748031496D * d2);
      ((Rectangle)localObject1).setArcHeight(0.007874015748031496D * d3);
      localObject2 = Color.color(0.8039215686D, 0.8549019608D, 0.5921568627D, 1.0D);
      ((Rectangle)localObject1).setFill((Paint)localObject2);
      ((Rectangle)localObject1).setStroke(null);
      this.secondPointer.getChildren().add(localObject1);
    }
    this.secondPointer.setRotate(this.secondAngle.get());
    switch (this.control.getClockStyle())
    {
    case DB: 
      localObject1 = new Circle(0.5D * d2, 0.5D * d3, 0.051181102362204724D * d2);
      ((Circle)localObject1).getStyleClass().clear();
      if (this.control.isAutoDimEnabled())
      {
        if (this.isDay) {
          ((Circle)localObject1).setFill(this.control.getBrightPointerPaint());
        } else {
          ((Circle)localObject1).setFill(this.control.getDarkPointerPaint());
        }
      }
      else if (Theme.BRIGHT == this.control.getTheme()) {
        ((Circle)localObject1).setFill(this.control.getBrightPointerPaint());
      } else {
        ((Circle)localObject1).setFill(this.control.getDarkPointerPaint());
      }
      ((Circle)localObject1).setStroke(null);
      break;
    case IOS6: 
      localObject1 = new Circle(0.5D * d2, 0.5D * d3, 0.0078740157D * d2);
      ((Circle)localObject1).setFill(Color.color(0.8745098039D, 0.8745098039D, 0.8156862745D, 1.0D));
      ((Circle)localObject1).setStroke(null);
      break;
    default: 
      localObject1 = new Circle(0.5D * d2, 0.5D * d2, 0.25D * d2);
      ((Circle)localObject1).setFill(Color.TRANSPARENT);
      ((Circle)localObject1).setStroke(Color.TRANSPARENT);
    }
    this.secondPointer.getChildren().add(localObject1);
    this.secondPointer.setCache(true);
  }
  
  private void drawShadows()
  {
    double d = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    Light.Distant localDistant = new Light.Distant();
    localDistant.setAzimuth(270.0D);
    localDistant.setElevation(50.0D);
    Lighting localLighting = new Lighting();
    localLighting.setLight(localDistant);
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setInput(localLighting);
    localDropShadow.setOffsetY(0.015D * d);
    localDropShadow.setRadius(0.015D * d);
    localDropShadow.setBlurType(BlurType.GAUSSIAN);
    localDropShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.55D));
    if (this.control.isAutoDimEnabled())
    {
      if (this.isDay)
      {
        this.minutePointerShadow.setEffect(localDropShadow);
        this.hourPointerShadow.setEffect(localDropShadow);
      }
    }
    else if (this.control.getTheme() == Theme.BRIGHT)
    {
      this.minutePointerShadow.setEffect(localDropShadow);
      this.hourPointerShadow.setEffect(localDropShadow);
    }
    this.secondPointerShadow.setEffect(localDropShadow);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/ClockSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */