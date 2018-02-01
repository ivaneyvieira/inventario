package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.TrafficLightBehavior;
import jfxtras.labs.scene.control.gauge.TrafficLight;

public class TrafficLightSkin
  extends SkinBase<TrafficLight, TrafficLightBehavior>
{
  public static final long BLINK_INTERVAL = 500000000L;
  private TrafficLight control;
  private boolean isDirty;
  private boolean initialized;
  private Group trafficlight;
  private Shape redOn;
  private Shape highlightRedOn;
  private Shape yellowOn;
  private Shape highlightYellowOn;
  private Shape greenOn;
  private Shape highlightGreenOn;
  private boolean on;
  private AnimationTimer timer;
  private long lastTimerCall;
  
  public TrafficLightSkin(TrafficLight paramTrafficLight)
  {
    super(paramTrafficLight, new TrafficLightBehavior(paramTrafficLight));
    this.control = paramTrafficLight;
    this.initialized = false;
    this.isDirty = false;
    this.redOn = new Circle();
    this.highlightRedOn = new Ellipse();
    this.yellowOn = new Circle();
    this.highlightYellowOn = new Ellipse();
    this.greenOn = new Circle();
    this.highlightGreenOn = new Ellipse();
    this.trafficlight = new Group();
    this.timer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        long l = System.nanoTime();
        if (l > TrafficLightSkin.this.lastTimerCall + 500000000L)
        {
          TrafficLightSkin.access$180(TrafficLightSkin.this, 1);
          if (TrafficLightSkin.this.control.isRedBlinking())
          {
            TrafficLightSkin.this.redOn.setVisible(TrafficLightSkin.this.on);
            TrafficLightSkin.this.highlightRedOn.setVisible(TrafficLightSkin.this.on);
          }
          if (TrafficLightSkin.this.control.isYellowBlinking())
          {
            TrafficLightSkin.this.yellowOn.setVisible(TrafficLightSkin.this.on);
            TrafficLightSkin.this.highlightYellowOn.setVisible(TrafficLightSkin.this.on);
          }
          if (TrafficLightSkin.this.control.isGreenBlinking())
          {
            TrafficLightSkin.this.greenOn.setVisible(TrafficLightSkin.this.on);
            TrafficLightSkin.this.highlightGreenOn.setVisible(TrafficLightSkin.this.on);
          }
          TrafficLightSkin.this.lastTimerCall = l;
        }
      }
    };
    this.lastTimerCall = 0L;
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(80.0D, 200.0D);
    }
    registerChangeListener(this.control.redOnProperty(), "RED");
    registerChangeListener(this.control.redBlinkingProperty(), "RED_BLINKING");
    registerChangeListener(this.control.yellowOnProperty(), "YELLOW");
    registerChangeListener(this.control.yellowBlinkingProperty(), "YELLOW_BLINKING");
    registerChangeListener(this.control.greenOnProperty(), "GREEN");
    registerChangeListener(this.control.greenBlinkingProperty(), "GREEN_BLINKING");
    registerChangeListener(this.control.darkBackgroundProperty(), "DARK_BACKGROUND");
    this.timer.start();
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("RED".equals(paramString))
    {
      this.redOn.setVisible(this.control.isRedOn());
      this.highlightRedOn.setVisible(this.control.isRedOn());
    }
    else if (!"RED_BLINKING".equals(paramString))
    {
      if ("YELLOW".equals(paramString))
      {
        this.yellowOn.setVisible(this.control.isYellowOn());
        this.highlightYellowOn.setVisible(this.control.isYellowOn());
      }
      else if (!"YELLOW_BLINKING".equals(paramString))
      {
        if ("GREEN".equals(paramString))
        {
          this.greenOn.setVisible(this.control.isGreenOn());
          this.highlightGreenOn.setVisible(this.control.isGreenOn());
        }
        else if (!"GREEN_BLINKING".equals(paramString))
        {
          if ("DARK_BACKGROUND".equals(paramString)) {
            repaint();
          } else if ("PREF_WIDTH".equals(paramString)) {
            repaint();
          } else if ("PREF_HEIGHT".equals(paramString)) {
            repaint();
          }
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
    if (this.control.getScene() != null)
    {
      drawTrafficLight();
      getChildren().setAll(new Node[] { this.trafficlight });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final TrafficLight getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 80.0D;
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
    return super.computePrefWidth(d);
  }
  
  public final void drawTrafficLight()
  {
    double d1 = this.control.getPrefWidth();
    double d2 = this.control.getPrefHeight();
    this.trafficlight.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.125D * d1, 0.055D * d2, 0.75D * d1, 0.9D * d2);
    localRectangle.setArcWidth(0.75D * d1);
    localRectangle.setArcHeight(0.3D * d2);
    Color localColor1 = this.control.isDarkBackground() ? Color.color(0.2D, 0.2D, 0.2D, 0.6D) : Color.color(0.8D, 0.8D, 0.8D, 0.6D);
    localRectangle.setFill(localColor1);
    localRectangle.setStroke(null);
    Path localPath = new Path();
    localPath.setFillRule(FillRule.EVEN_ODD);
    localPath.getElements().add(new MoveTo(0.125D * d1, 0.205D * d2));
    localPath.getElements().add(new CubicCurveTo(0.125D * d1, 0.12D * d2, 0.2875D * d1, 0.055D * d2, 0.5D * d1, 0.055D * d2));
    localPath.getElements().add(new CubicCurveTo(0.7125D * d1, 0.055D * d2, 0.875D * d1, 0.12D * d2, 0.875D * d1, 0.205D * d2));
    localPath.getElements().add(new CubicCurveTo(0.875D * d1, 0.205D * d2, 0.875D * d1, 0.805D * d2, 0.875D * d1, 0.805D * d2));
    localPath.getElements().add(new CubicCurveTo(0.875D * d1, 0.89D * d2, 0.7125D * d1, 0.955D * d2, 0.5D * d1, 0.955D * d2));
    localPath.getElements().add(new CubicCurveTo(0.2875D * d1, 0.955D * d2, 0.125D * d1, 0.89D * d2, 0.125D * d1, 0.805D * d2));
    localPath.getElements().add(new CubicCurveTo(0.125D * d1, 0.805D * d2, 0.125D * d1, 0.205D * d2, 0.125D * d1, 0.205D * d2));
    localPath.getElements().add(new ClosePath());
    localPath.getElements().add(new MoveTo(0.0D, 0.2D * d2));
    localPath.getElements().add(new CubicCurveTo(0.0D, 0.2D * d2, 0.0D, 0.8D * d2, 0.0D, 0.8D * d2));
    localPath.getElements().add(new CubicCurveTo(0.0D, 0.91D * d2, 0.225D * d1, d2, 0.5D * d1, d2));
    localPath.getElements().add(new CubicCurveTo(0.775D * d1, d2, d1, 0.91D * d2, d1, 0.8D * d2));
    localPath.getElements().add(new CubicCurveTo(d1, 0.8D * d2, d1, 0.2D * d2, d1, 0.2D * d2));
    localPath.getElements().add(new CubicCurveTo(d1, 0.09D * d2, 0.775D * d1, 0.0D, 0.5D * d1, 0.0D));
    localPath.getElements().add(new CubicCurveTo(0.225D * d1, 0.0D, 0.0D, 0.09D * d2, 0.0D, 0.2D * d2));
    localPath.getElements().add(new ClosePath());
    Color localColor2 = this.control.isDarkBackground() ? Color.color(0.8D, 0.8D, 0.8D, 0.6D) : Color.color(0.2D, 0.2D, 0.2D, 0.6D);
    localPath.setFill(localColor2);
    localPath.setStroke(null);
    this.trafficlight.getChildren().addAll(new Node[] { localRectangle, localPath });
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.140625D * d1);
    localInnerShadow.setHeight(0.140625D * d1);
    localInnerShadow.setRadius(0.140625D * d1);
    localInnerShadow.setColor(Color.BLACK);
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    DropShadow localDropShadow1 = new DropShadow();
    localDropShadow1.setWidth(0.25D * d1);
    localDropShadow1.setHeight(0.25D * d1);
    localDropShadow1.setRadius(0.18D * d1);
    localDropShadow1.setColor(Color.RED);
    localDropShadow1.setBlurType(BlurType.GAUSSIAN);
    localDropShadow1.inputProperty().set(localInnerShadow);
    DropShadow localDropShadow2 = new DropShadow();
    localDropShadow2.setWidth(0.25D * d1);
    localDropShadow2.setHeight(0.25D * d1);
    localDropShadow2.setRadius(0.18D * d1);
    localDropShadow2.setColor(Color.YELLOW);
    localDropShadow2.setBlurType(BlurType.GAUSSIAN);
    localDropShadow2.inputProperty().set(localInnerShadow);
    DropShadow localDropShadow3 = new DropShadow();
    localDropShadow3.setWidth(0.25D * d1);
    localDropShadow3.setHeight(0.25D * d1);
    localDropShadow3.setRadius(0.18D * d1);
    localDropShadow3.setColor(Color.LIME);
    localDropShadow3.setBlurType(BlurType.GAUSSIAN);
    localDropShadow3.inputProperty().set(localInnerShadow);
    Circle localCircle1 = new Circle(0.5D * d1, 0.2D * d2, 0.3125D * d1);
    RadialGradient localRadialGradient1 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.285D * d2, 0.59375D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.3019607843D, 0.0D, 0.0D, 1.0D)), new Stop(0.98D, Color.color(0.0039215686D, 0.0D, 0.0D, 1.0D)), new Stop(0.99D, Color.BLACK), new Stop(1.0D, Color.BLACK) });
    localCircle1.setFill(localRadialGradient1);
    localCircle1.setStroke(null);
    localCircle1.setEffect(localInnerShadow);
    Ellipse localEllipse1 = new Ellipse(0.49375D * d1, 0.13D * d2, 0.23125D * d1, 0.05D * d2);
    RadialGradient localRadialGradient2 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.105D * d2, 0.2125D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2235294118D)), new Stop(0.98D, Color.color(1.0D, 1.0D, 1.0D, 0.0274509804D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0274509804D)) });
    localEllipse1.setFill(localRadialGradient2);
    localEllipse1.setStroke(null);
    this.redOn = new Circle(0.5D * d1, 0.2D * d2, 0.3125D * d1);
    RadialGradient localRadialGradient3 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.285D * d2, 0.59375D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.RED), new Stop(0.98D, Color.color(0.2549019608D, 0.0D, 0.0D, 1.0D)), new Stop(0.99D, Color.color(0.2470588235D, 0.0D, 0.0D, 1.0D)), new Stop(1.0D, Color.color(0.2470588235D, 0.0D, 0.0D, 1.0D)) });
    this.redOn.setFill(localRadialGradient3);
    this.redOn.setStroke(null);
    this.redOn.setEffect(localDropShadow1);
    this.redOn.setVisible(this.control.isRedOn());
    this.highlightRedOn = new Ellipse(0.49375D * d1, 0.13D * d2, 0.23125D * d1, 0.05D * d2);
    RadialGradient localRadialGradient4 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.105D * d2, 0.2125D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.6745098039D)), new Stop(0.98D, Color.color(1.0D, 1.0D, 1.0D, 0.0862745098D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0862745098D)) });
    this.highlightRedOn.setFill(localRadialGradient4);
    this.highlightRedOn.setStroke(null);
    this.highlightRedOn.setVisible(this.control.isRedOn());
    this.trafficlight.getChildren().addAll(new Node[] { localCircle1, localEllipse1, this.redOn, this.highlightRedOn });
    Circle localCircle2 = new Circle(0.5D * d1, 0.5D * d2, 0.3125D * d1);
    RadialGradient localRadialGradient5 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.585D * d2, 0.59375D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.3254901961D, 0.3333333333D, 0.0D, 1.0D)), new Stop(0.98D, Color.color(0.0039215686D, 0.0039215686D, 0.0D, 1.0D)), new Stop(0.99D, Color.BLACK), new Stop(1.0D, Color.BLACK) });
    localCircle2.setFill(localRadialGradient5);
    localCircle2.setStroke(null);
    localCircle2.setEffect(localInnerShadow);
    Ellipse localEllipse2 = new Ellipse(0.49375D * d1, 0.43D * d2, 0.23125D * d1, 0.05D * d2);
    RadialGradient localRadialGradient6 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.405D * d2, 0.2125D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2235294118D)), new Stop(0.98D, Color.color(1.0D, 1.0D, 1.0D, 0.0274509804D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0274509804D)) });
    localEllipse2.setFill(localRadialGradient6);
    localEllipse2.setStroke(null);
    this.yellowOn = new Circle(0.5D * d1, 0.5D * d2, 0.3125D * d1);
    RadialGradient localRadialGradient7 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.585D * d2, 0.59375D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.YELLOW), new Stop(0.98D, Color.color(0.3333333333D, 0.3411764706D, 0.0D, 1.0D)), new Stop(0.99D, Color.color(0.3254901961D, 0.3333333333D, 0.0D, 1.0D)), new Stop(1.0D, Color.color(0.3254901961D, 0.3333333333D, 0.0D, 1.0D)) });
    this.yellowOn.setFill(localRadialGradient7);
    this.yellowOn.setStroke(null);
    this.yellowOn.setEffect(localDropShadow2);
    this.yellowOn.setVisible(this.control.isYellowOn());
    this.highlightYellowOn = new Ellipse(0.49375D * d1, 0.43D * d2, 0.23125D * d1, 0.05D * d2);
    RadialGradient localRadialGradient8 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.405D * d2, 0.2125D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.6745098039D)), new Stop(0.98D, Color.color(1.0D, 1.0D, 1.0D, 0.0862745098D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0862745098D)) });
    this.highlightYellowOn.setFill(localRadialGradient8);
    this.highlightYellowOn.setStroke(null);
    this.highlightYellowOn.setVisible(this.control.isYellowOn());
    this.trafficlight.getChildren().addAll(new Node[] { localCircle2, localEllipse2, this.yellowOn, this.highlightYellowOn });
    Circle localCircle3 = new Circle(0.5D * d1, 0.8D * d2, 0.3125D * d1);
    RadialGradient localRadialGradient9 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.885D * d2, 0.59375D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0980392157D, 0.337254902D, 0.0D, 1.0D)), new Stop(0.98D, Color.color(0.0D, 0.0039215686D, 0.0D, 1.0D)), new Stop(0.99D, Color.BLACK), new Stop(1.0D, Color.BLACK) });
    localCircle3.setFill(localRadialGradient9);
    localCircle3.setStroke(null);
    localCircle3.setEffect(localInnerShadow);
    Ellipse localEllipse3 = new Ellipse(0.49375D * d1, 0.73D * d2, 0.23125D * d1, 0.05D * d2);
    RadialGradient localRadialGradient10 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.705D * d2, 0.2125D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2235294118D)), new Stop(0.98D, Color.color(1.0D, 1.0D, 1.0D, 0.0274509804D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0274509804D)) });
    localEllipse3.setFill(localRadialGradient10);
    localEllipse3.setStroke(null);
    this.greenOn = new Circle(0.5D * d1, 0.8D * d2, 0.3125D * d1);
    RadialGradient localRadialGradient11 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.885D * d2, 0.59375D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.LIME), new Stop(0.98D, Color.color(0.1254901961D, 0.2784313725D, 0.1411764706D, 1.0D)), new Stop(0.99D, Color.color(0.1254901961D, 0.2705882353D, 0.1411764706D, 1.0D)), new Stop(1.0D, Color.color(0.1254901961D, 0.2705882353D, 0.1411764706D, 1.0D)) });
    this.greenOn.setFill(localRadialGradient11);
    this.greenOn.setStroke(null);
    this.greenOn.setEffect(localDropShadow3);
    this.greenOn.setVisible(this.control.isGreenOn());
    this.highlightGreenOn = new Ellipse(0.49375D * d1, 0.73D * d2, 0.23125D * d1, 0.05D * d2);
    RadialGradient localRadialGradient12 = new RadialGradient(0.0D, 0.0D, 0.5D * d1, 0.705D * d2, 0.2125D * d1, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.6745098039D)), new Stop(0.98D, Color.color(1.0D, 1.0D, 1.0D, 0.0862745098D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0862745098D)) });
    this.highlightGreenOn.setFill(localRadialGradient12);
    this.highlightGreenOn.setStroke(null);
    this.highlightGreenOn.setVisible(this.control.isGreenOn());
    this.trafficlight.getChildren().addAll(new Node[] { localCircle3, localEllipse3, this.greenOn, this.highlightGreenOn });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/TrafficLightSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */