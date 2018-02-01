package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.LedBehavior;
import jfxtras.labs.scene.control.gauge.Led;
import jfxtras.labs.util.Util;

public class LedSkin
  extends SkinBase<Led, LedBehavior>
{
  public static final long BLINK_INTERVAL = 500000000L;
  private Led control;
  private boolean isDirty;
  private boolean initialized;
  private Group led;
  private Shape ledOn;
  private boolean on;
  private AnimationTimer timer;
  private long lastTimerCall;
  
  public LedSkin(Led paramLed)
  {
    super(paramLed, new LedBehavior(paramLed));
    this.control = paramLed;
    this.initialized = false;
    this.isDirty = false;
    this.led = new Group();
    this.timer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        long l = System.nanoTime();
        if (l > LedSkin.this.lastTimerCall + 500000000L)
        {
          LedSkin.access$180(LedSkin.this, 1);
          LedSkin.this.ledOn.setVisible(LedSkin.this.on);
          LedSkin.this.lastTimerCall = l;
        }
      }
    };
    this.lastTimerCall = 0L;
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(16.0D, 16.0D);
    }
    this.led.getStyleClass().setAll(new String[] { "led" });
    registerChangeListener(this.control.onProperty(), "ON");
    registerChangeListener(this.control.blinkingProperty(), "BLINKING");
    registerChangeListener(this.control.colorProperty(), "COLOR");
    registerChangeListener(this.control.typeProperty(), "TYPE");
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    if (this.control.isBlinking()) {
      this.timer.start();
    }
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("ON".equals(paramString)) {
      this.ledOn.setVisible(this.control.isOn());
    } else if ("BLINKING".equals(paramString))
    {
      if (this.control.isBlinking())
      {
        this.timer.start();
      }
      else
      {
        this.timer.stop();
        this.ledOn.setVisible(false);
      }
    }
    else if ("COLOR".equals(paramString)) {
      repaint();
    } else if ("TYPE".equals(paramString)) {
      repaint();
    } else if ("PREF_WIDTH".equals(paramString)) {
      repaint();
    } else if ("PREF_HEIGHT".equals(paramString)) {
      repaint();
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
      drawLed();
      getChildren().setAll(new Node[] { this.led });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final Led getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 20.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 20.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  public final void drawLed()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.led.getChildren().clear();
    this.led.setStyle("-fx-led: " + Util.createCssColor(this.control.getColor()));
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    this.led.getChildren().add(localRectangle);
    Object localObject1;
    Object localObject2;
    Object localObject3;
    switch (this.control.getType())
    {
    case SQUARE: 
      localObject1 = new Rectangle(0.0625D * d2, 0.0625D * d3, 0.875D * d2, 0.875D * d3);
      localObject2 = new Rectangle(0.1875D * d2, 0.1875D * d3, 0.625D * d2, 0.625D * d3);
      this.ledOn = new Rectangle(0.1875D * d2, 0.1875D * d3, 0.625D * d2, 0.625D * d3);
      localObject3 = new Rectangle(0.25D * d2, 0.25D * d3, 0.5D * d2, 0.1875D * d3);
      break;
    case VERTICAL: 
      localObject1 = new Rectangle(0.25D * d2, 0.0625D * d3, 0.5D * d2, 0.875D * d3);
      localObject2 = new Rectangle(0.3125D * d2, 0.125D * d3, 0.375D * d2, 0.75D * d3);
      this.ledOn = new Rectangle(0.3125D * d2, 0.125D * d3, 0.375D * d2, 0.75D * d3);
      localObject3 = new Rectangle(0.3125D * d2, 0.125D * d3, 0.375D * d2, 0.375D * d3);
      break;
    case HORIZONTAL: 
      localObject1 = new Rectangle(0.0625D * d2, 0.25D * d3, 0.875D * d2, 0.5D * d3);
      localObject2 = new Rectangle(0.125D * d2, 0.3125D * d3, 0.75D * d2, 0.375D * d3);
      this.ledOn = new Rectangle(0.125D * d2, 0.3125D * d3, 0.75D * d2, 0.375D * d3);
      localObject3 = new Rectangle(0.125D * d2, 0.3125D * d3, 0.75D * d2, 0.1875D * d3);
      break;
    case ROUND: 
    default: 
      localObject1 = new Circle(0.5D * d2, 0.5D * d3, 0.4375D * d2);
      localObject2 = new Circle(0.5D * d2, 0.5D * d3, 0.3125D * d2);
      this.ledOn = new Circle(0.5D * d2, 0.5D * d3, 0.3125D * d2);
      localObject3 = new Circle(0.5D * d2, 0.5D * d3, 0.2D * d2);
    }
    ((Shape)localObject1).getStyleClass().add("frame");
    ((Shape)localObject2).getStyleClass().clear();
    ((Shape)localObject2).getStyleClass().add("off");
    ((Shape)localObject2).setStyle("-fx-led: " + Util.createCssColor(this.control.getColor()));
    this.ledOn.getStyleClass().clear();
    this.ledOn.getStyleClass().add("on");
    this.ledOn.setStyle("-fx-led: " + Util.createCssColor(this.control.getColor()));
    this.ledOn.setVisible(this.control.isOn());
    ((Shape)localObject3).getStyleClass().add("highlight");
    if (((Shape)localObject1).visibleProperty().isBound()) {
      ((Shape)localObject1).visibleProperty().unbind();
    }
    ((Shape)localObject1).visibleProperty().bind(this.control.frameVisibleProperty());
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.18D * d1);
    localInnerShadow.setHeight(0.18D * d1);
    localInnerShadow.setRadius(0.15D * d1);
    localInnerShadow.setColor(Color.BLACK);
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    ((Shape)localObject2).setEffect(localInnerShadow);
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setSpread(0.35D);
    localDropShadow.setRadius(0.16D * this.ledOn.getLayoutBounds().getWidth());
    localDropShadow.setColor(this.control.getColor());
    localDropShadow.setBlurType(BlurType.GAUSSIAN);
    localDropShadow.setInput(localInnerShadow);
    this.ledOn.setEffect(localDropShadow);
    this.led.getChildren().addAll(new Node[] { localObject1, localObject2, this.ledOn, localObject3 });
    this.led.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/LedSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */