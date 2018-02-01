package jfxtras.labs.scene.control;

import java.net.URL;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradientBuilder;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class SlideLock
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "slide-lock";
  public static final double START_XCOORD = 33.0D;
  public static final double END_XCOORD = 375.0D;
  public static final double BUTTON_YCOORD = 49.0D;
  public static final double PREFERRED_WIDTH = 523.28571D;
  public static final double PREFERRED_HEIGHT = 188.0D;
  private BooleanProperty backgroundVisible;
  private BooleanProperty buttonGlareVisible;
  private BooleanProperty locked;
  private StringProperty text;
  private DoubleProperty startX;
  private DoubleProperty endX;
  private DoubleProperty textOpacity;
  private Timeline snapButtonBackAnim;
  private Timeline unlockAnimation;
  private ObjectProperty<Paint> buttonArrowBackgroundColor;
  private ObjectProperty<Paint> buttonColor;
  
  public SlideLock()
  {
    this("slide to unlock");
  }
  
  public SlideLock(String paramString)
  {
    getStyleClass().setAll(new String[] { "slide-lock" });
    this.backgroundVisible = new SimpleBooleanProperty(false);
    this.buttonGlareVisible = new SimpleBooleanProperty(true);
    this.buttonGlareVisible = new SimpleBooleanProperty(true);
    this.locked = new SimpleBooleanProperty(true);
    this.text = new SimpleStringProperty(paramString);
    this.startX = new SimpleDoubleProperty(33.0D);
    this.endX = new SimpleDoubleProperty(33.0D);
    this.textOpacity = new SimpleDoubleProperty(1.0D);
    this.snapButtonBackAnim = new Timeline();
    this.unlockAnimation = new Timeline();
    this.buttonArrowBackgroundColor = new SimpleObjectProperty();
    this.buttonColor = new SimpleObjectProperty();
    init();
  }
  
  private void init()
  {
    KeyValue localKeyValue1 = new KeyValue(this.endX, Double.valueOf(33.0D));
    KeyFrame localKeyFrame1 = new KeyFrame(Duration.millis(150.0D), new KeyValue[] { localKeyValue1 });
    KeyValue localKeyValue2 = new KeyValue(this.textOpacity, Integer.valueOf(1));
    KeyFrame localKeyFrame2 = new KeyFrame(Duration.millis(150.0D), new KeyValue[] { localKeyValue2 });
    this.snapButtonBackAnim.getKeyFrames().addAll(new KeyFrame[] { localKeyFrame1, localKeyFrame2 });
    KeyValue localKeyValue3 = new KeyValue(this.endX, Double.valueOf(375.0D));
    KeyFrame localKeyFrame3 = new KeyFrame(Duration.millis(1000.0D), new KeyValue[] { localKeyValue3 });
    KeyValue localKeyValue4 = new KeyValue(this.textOpacity, Integer.valueOf(0));
    KeyFrame localKeyFrame4 = new KeyFrame(Duration.millis(1000.0D), new KeyValue[] { localKeyValue4 });
    this.unlockAnimation.getKeyFrames().addAll(new KeyFrame[] { localKeyFrame3, localKeyFrame4 });
    this.unlockAnimation.setOnFinished(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        SlideLock.this.setLocked(false);
      }
    });
    this.buttonArrowBackgroundColor.set(LinearGradientBuilder.create().proportional(true).startX(0.0D).startY(1.0D).endX(0.0D).endY(0.0D).stops(new Stop[] { new Stop(0.0D, Color.web("747474")), new Stop(1.0D, Color.web("e8e8e8")) }).build());
    this.buttonColor.set(LinearGradientBuilder.create().proportional(true).startX(0.0D).startY(1.0D).endX(0.0D).endY(0.0D).stops(new Stop[] { new Stop(0.0D, Color.web("c5c5c5")), new Stop(1.0D, Color.web("f0f0f0")) }).build());
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/scene/control/slidelock.css").toExternalForm();
  }
  
  public final boolean isBackgroundVisible()
  {
    return this.backgroundVisible.get();
  }
  
  public final void setBackgroundVisible(boolean paramBoolean)
  {
    this.backgroundVisible.set(paramBoolean);
  }
  
  public final BooleanProperty backgroundVisibleProperty()
  {
    return this.backgroundVisible;
  }
  
  public final boolean isButtonGlareVisible()
  {
    return this.buttonGlareVisible.get();
  }
  
  public final void setButtonGlareVisible(boolean paramBoolean)
  {
    this.buttonGlareVisible.set(paramBoolean);
  }
  
  public final BooleanProperty buttonGlareVisibleProperty()
  {
    return this.buttonGlareVisible;
  }
  
  public final boolean isLocked()
  {
    return this.locked.get();
  }
  
  public final void setLocked(boolean paramBoolean)
  {
    this.locked.set(paramBoolean);
  }
  
  public final BooleanProperty lockedProperty()
  {
    return this.locked;
  }
  
  public final void autoUnlock()
  {
    this.unlockAnimation.play();
  }
  
  public final String getText()
  {
    return (String)this.text.get();
  }
  
  public final void setText(String paramString)
  {
    this.text.set(paramString);
  }
  
  public final StringProperty textProperty()
  {
    return this.text;
  }
  
  public final double getStartX()
  {
    return this.startX.get();
  }
  
  public final void setStartX(double paramDouble)
  {
    this.startX.set(paramDouble);
  }
  
  public final DoubleProperty startXProperty()
  {
    return this.startX;
  }
  
  public final double getEndX()
  {
    return this.endX.get();
  }
  
  public final void setEndX(double paramDouble)
  {
    this.endX.set(paramDouble);
  }
  
  public final DoubleProperty endXProperty()
  {
    return this.endX;
  }
  
  public final double getTextOpacity()
  {
    return this.textOpacity.get();
  }
  
  public final void setTextOpacity(double paramDouble)
  {
    this.textOpacity.set(paramDouble);
  }
  
  public final DoubleProperty textOpacityProperty()
  {
    return this.textOpacity;
  }
  
  public final Paint getButtonArrowBackgroundColor()
  {
    return (Paint)this.buttonArrowBackgroundColor.get();
  }
  
  public final void setButtonArrowBackgroundColor(Paint paramPaint)
  {
    this.buttonArrowBackgroundColor.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> buttonArrowBackgroundColorProperty()
  {
    return this.buttonArrowBackgroundColor;
  }
  
  public final Paint getButtonColor()
  {
    return (Paint)this.buttonColor.get();
  }
  
  public final void setButtonColor(Paint paramPaint)
  {
    this.buttonColor.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> buttonColorProperty()
  {
    return this.buttonColor;
  }
  
  public Timeline getSnapButtonBackAnim()
  {
    return this.snapButtonBackAnim;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/SlideLock.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */