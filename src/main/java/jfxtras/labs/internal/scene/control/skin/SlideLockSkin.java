package jfxtras.labs.internal.scene.control.skin;

import java.io.PrintStream;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradientBuilder;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.SVGPathBuilder;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.ScaleBuilder;
import javafx.scene.transform.Transform;
import jfxtras.labs.scene.control.SlideLock;

public class SlideLockSkin
  extends Region
  implements Skin<SlideLock>
{
  private final SlideLock CONTROL;
  private Group button;
  private Text text;
  private EventHandler<MouseEvent> mouseHandler;
  private EventHandler<TouchEvent> touchHandler;
  private AnimationTimer currentSpotlightAnim;
  
  public SlideLockSkin(SlideLock paramSlideLock)
  {
    this.CONTROL = paramSlideLock;
    if ((this.CONTROL.getPrefWidth() <= 0.0D) || (this.CONTROL.getPrefHeight() <= 0.0D)) {
      this.CONTROL.setPrefSize(523.28571D, 188.0D);
    }
    this.button = new Group();
    this.text = new Text(this.CONTROL.getText());
    this.mouseHandler = new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (MouseEvent.MOUSE_PRESSED == paramAnonymousMouseEvent.getEventType()) {
          SlideLockSkin.this.buttonPressAction(SlideLockSkin.this.button, paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
        } else if (MouseEvent.MOUSE_DRAGGED == paramAnonymousMouseEvent.getEventType()) {
          SlideLockSkin.this.moveButtonAction(SlideLockSkin.this.button, paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
        } else if (MouseEvent.MOUSE_RELEASED == paramAnonymousMouseEvent.getEventType()) {
          SlideLockSkin.this.buttonSnapBack();
        }
      }
    };
    this.touchHandler = new EventHandler()
    {
      public void handle(TouchEvent paramAnonymousTouchEvent)
      {
        if (TouchEvent.TOUCH_PRESSED == paramAnonymousTouchEvent.getEventType()) {
          SlideLockSkin.this.buttonPressAction(SlideLockSkin.this.button, paramAnonymousTouchEvent.getTouchPoint().getX(), paramAnonymousTouchEvent.getTouchPoint().getY());
        } else if (TouchEvent.TOUCH_MOVED == paramAnonymousTouchEvent.getEventType()) {
          SlideLockSkin.this.moveButtonAction(SlideLockSkin.this.button, paramAnonymousTouchEvent.getTouchPoint().getX(), paramAnonymousTouchEvent.getTouchPoint().getY());
        } else if (TouchEvent.TOUCH_RELEASED == paramAnonymousTouchEvent.getEventType()) {
          SlideLockSkin.this.buttonSnapBack();
        }
      }
    };
    setMaxSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    drawControl();
    addHandlers();
    startAnimations();
  }
  
  private void startAnimations()
  {
    if (this.currentSpotlightAnim != null) {
      this.currentSpotlightAnim.stop();
    }
    this.currentSpotlightAnim = new AnimationTimer()
    {
      long startTime = System.currentTimeMillis();
      long duration = 0L;
      double x = -70.0D;
      
      public void handle(long paramAnonymousLong)
      {
        long l = System.currentTimeMillis() - this.startTime;
        this.duration += l;
        if (this.duration > 150L)
        {
          RadialGradient localRadialGradient = RadialGradientBuilder.create().radius(70.0D).centerX(this.x).centerY(SlideLockSkin.this.text.getY()).proportional(false).stops(new Stop[] { new Stop(0.0D, Color.WHITE), new Stop(0.5D, Color.WHITE), new Stop(1.0D, Color.web("#555555")) }).build();
          this.x += 10.0D;
          if (this.x > SlideLockSkin.this.text.getBoundsInParent().getMaxX() + 70.0D) {
            this.x = -70.0D;
          }
          SlideLockSkin.this.text.setFill(localRadialGradient);
          this.startTime = System.currentTimeMillis();
          this.duration = 0L;
        }
      }
    };
    this.currentSpotlightAnim.start();
  }
  
  public void layoutChildren()
  {
    drawControl();
    startAnimations();
    super.layoutChildren();
  }
  
  public SlideLock getSkinnable()
  {
    return this.CONTROL;
  }
  
  public Node getNode()
  {
    return this;
  }
  
  public void dispose()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  private void addHandlers()
  {
    setOnMousePressed(this.mouseHandler);
    setOnMouseDragged(this.mouseHandler);
    setOnMouseReleased(this.mouseHandler);
    setOnTouchPressed(this.touchHandler);
    setOnTouchMoved(this.touchHandler);
    setOnTouchReleased(this.touchHandler);
  }
  
  private void buttonPressAction(Node paramNode, double paramDouble1, double paramDouble2)
  {
    if (paramNode.getBoundsInParent().contains(paramDouble1, paramDouble2)) {
      this.CONTROL.setStartX(paramDouble1 - this.CONTROL.getEndX());
    }
  }
  
  private void moveButtonAction(Node paramNode, double paramDouble1, double paramDouble2)
  {
    double d1 = this.CONTROL.getPrefWidth();
    double d2 = this.CONTROL.getPrefHeight();
    double d3 = d1 / 523.28571D;
    double d4 = d2 / 188.0D;
    System.out.println(paramDouble1 + ", " + paramDouble2);
    if (paramNode.getBoundsInParent().contains(paramDouble1, paramDouble2))
    {
      this.CONTROL.setEndX(paramDouble1 - this.CONTROL.getStartX());
      if (paramNode.getTranslateX() < 33.0D)
      {
        this.CONTROL.setLocked(true);
        this.CONTROL.setEndX(33.0D);
      }
      else if (paramNode.getTranslateX() > 375.0D)
      {
        this.CONTROL.setLocked(false);
        this.CONTROL.setEndX(375.0D);
      }
      else
      {
        this.CONTROL.setLocked(true);
      }
      double d5 = 1.0D - paramNode.getTranslateX() / 200.0D;
      if (d5 < 0.0D) {
        d5 = 0.0D;
      }
      this.CONTROL.setTextOpacity(d5);
      this.currentSpotlightAnim.stop();
    }
  }
  
  private void buttonSnapBack()
  {
    if (this.CONTROL.isLocked())
    {
      this.CONTROL.getSnapButtonBackAnim().play();
      this.currentSpotlightAnim.start();
    }
  }
  
  private void drawControl()
  {
    double d1 = this.CONTROL.getPrefWidth();
    double d2 = this.CONTROL.getPrefHeight();
    double d3 = d1 / 523.28571D;
    double d4 = d2 / 188.0D;
    Scale localScale = ScaleBuilder.create().x(d1 / 523.28571D).y(d2 / 188.0D).pivotX(0.0D).pivotY(0.0D).build();
    Rectangle localRectangle1 = ((RectangleBuilder)RectangleBuilder.create().id("slide-background")).width(d1).height(d2).build();
    localRectangle1.visibleProperty().bind(this.CONTROL.backgroundVisibleProperty());
    Rectangle localRectangle2 = ((RectangleBuilder)RectangleBuilder.create().id("slide-area")).x(0.0612476117D * d1).y(0.2463297872D * d2).width(0.8829200973D * d1).height(0.5319148936D * d2).arcWidth(0.079787234D * d2).arcHeight(0.079787234D * d2).build();
    SVGPath localSVGPath = ((SVGPathBuilder)((SVGPathBuilder)((SVGPathBuilder)((SVGPathBuilder)SVGPathBuilder.create().fill(LinearGradientBuilder.create().proportional(true).startX(0.0D).startY(0.0D).endX(0.0D).endY(1.0D).stops(new Stop[] { new Stop(0.0D, Color.web("f0f0f0", 1.0D)), new Stop(1.0D, Color.web("f0f0f0", 0.0D)) }).build())).opacity(0.274D)).transforms(new Transform[] { localScale })).content("m 0,0 0,94 32,0 0,-27.218747 C 30.998808,55.222973 37.761737,45.9354 46.156457,45.93665 l 431.687503,0.06427 c 8.39472,0.0013 15.15487,9.290837 15.15315,20.814756 l -0.004,27.218754 30.28125,0 0,-94.0000031 L 0,0 z").id("glare-frame")).build();
    localSVGPath.visibleProperty().bind(this.CONTROL.backgroundVisibleProperty());
    this.text.setText(this.CONTROL.getText());
    this.text.setId("slide-text");
    this.text.getTransforms().clear();
    this.text.getTransforms().add(localScale);
    drawSlideButton();
    this.button.translateXProperty().bind(this.CONTROL.endXProperty().multiply(d3));
    this.button.setTranslateY(49.0D * d4);
    this.text.setTranslateX(33.0D + this.button.getBoundsInParent().getWidth() + 0.1063829787D * d2);
    this.text.setTranslateY(0.5744680851D * d2);
    this.text.opacityProperty().bind(this.CONTROL.textOpacityProperty());
    Rectangle localRectangle3 = ((RectangleBuilder)((RectangleBuilder)((RectangleBuilder)RectangleBuilder.create().id("slide-top-glare")).fill(Color.WHITE)).width(d1).height(0.5D * d2).opacity(0.0627451D)).build();
    localRectangle3.visibleProperty().bind(this.CONTROL.backgroundVisibleProperty());
    getChildren().clear();
    getChildren().addAll(new Node[] { localRectangle1, localRectangle2, localSVGPath, this.text, this.button, localRectangle3 });
  }
  
  private void drawSlideButton()
  {
    double d1 = this.CONTROL.getPrefWidth();
    double d2 = this.CONTROL.getPrefHeight();
    double d3 = d1 / 523.28571D;
    double d4 = d2 / 188.0D;
    Scale localScale = new Scale();
    localScale.setX(d3);
    localScale.setY(d4);
    localScale.setPivotX(0.0D);
    localScale.setPivotY(0.0D);
    this.button.getChildren().clear();
    Rectangle localRectangle = ((RectangleBuilder)((RectangleBuilder)RectangleBuilder.create().x(0.0358943492D * d1).y(0.0649521277D * d2).width(0.156464544D * d1).height(0.3616446809D * d2).arcWidth(0.0929042553D * d2).arcHeight(0.0929042553D * d2).fill(this.CONTROL.getButtonArrowBackgroundColor())).id("button-gradient-rect")).build();
    this.button.getChildren().add(localRectangle);
    SVGPath localSVGPath1 = ((SVGPathBuilder)((SVGPathBuilder)((SVGPathBuilder)((SVGPathBuilder)SVGPathBuilder.create().fill(Color.BLACK)).effect(new GaussianBlur(5.0D))).transforms(new Transform[] { localScale })).content("m 17.40912,2.47162 c -8.27303,0 -14.9375,7.04253 -14.9375,15.78125 l 0,59.9375 c 0,8.73872 6.66447,15.75 14.9375,15.75 l 84.625,0 c 8.27303,0 14.9375,-7.01128 14.9375,-15.75 l 0,-59.9375 c 0,-8.73872 -6.66447,-15.78125 -14.9375,-15.78125 l -84.625,0 z m 45.0625,18.15625 27.5625,27.59375 -27.5625,27.5625 0,-15.5625 -33.0625,0 0,-24 33.0625,0 0,-15.59375 z").id("#button-arrow-blur-shadow")).build();
    this.button.getChildren().add(localSVGPath1);
    SVGPath localSVGPath2 = ((SVGPathBuilder)((SVGPathBuilder)((SVGPathBuilder)SVGPathBuilder.create().content("m 17.40912,0.47162 c -8.27303,0 -14.9375,7.04253 -14.9375,15.78125 l 0,59.9375 c 0,8.73872 6.66447,15.75 14.9375,15.75 l 84.625,0 c 8.27303,0 14.9375,-7.01128 14.9375,-15.75 l 0,-59.9375 c 0,-8.73872 -6.66447,-15.78125 -14.9375,-15.78125 l -84.625,0 z m 45.0625,18.15625 27.5625,27.59375 -27.5625,27.5625 0,-15.5625 -33.0625,0 0,-24 33.0625,0 0,-15.59375 z").fill(this.CONTROL.getButtonColor())).id("#button-arrow-stencil-crisp")).transforms(new Transform[] { localScale })).build();
    this.button.getChildren().add(localSVGPath2);
    SVGPath localSVGPath3 = ((SVGPathBuilder)((SVGPathBuilder)((SVGPathBuilder)SVGPathBuilder.create().content("m 17.83252,1.67757 c -8.27303,0 -14.9375,7.21042 -14.9375,16.15746 l 0,28.31557 114.5,0 0,-28.31557 c 0,-8.94704 -6.66447,-16.15746 -14.9375,-16.15746 l -84.625,0 z").fill(LinearGradientBuilder.create().proportional(true).startX(0.0D).startY(1.0D).endX(0.0D).endY(0.0D).stops(new Stop[] { new Stop(0.0D, Color.web("f4f4f4", 0.6D)), new Stop(1.0D, Color.web("ffffff", 0.2063063D)) }).build())).id("#button-arrow-glare-rect")).transforms(new Transform[] { localScale })).build();
    localSVGPath3.visibleProperty().bind(this.CONTROL.buttonGlareVisibleProperty());
    this.button.getChildren().add(localSVGPath3);
    this.button.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/SlideLockSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */