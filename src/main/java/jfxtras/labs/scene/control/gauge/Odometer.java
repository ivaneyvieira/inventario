package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class Odometer
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "odometer";
  private ObjectProperty<Color> color = new SimpleObjectProperty(Color.rgb(240, 240, 240));
  private ObjectProperty<Color> decimalColor = new SimpleObjectProperty(Color.rgb(220, 0, 0));
  private ObjectProperty<Color> numberColor = new SimpleObjectProperty(Color.BLACK);
  private ObjectProperty<Color> numberDecimalColor = new SimpleObjectProperty(Color.WHITE);
  private LongProperty interval;
  private IntegerProperty rotations;
  private IntegerProperty rotationPreset;
  private BooleanProperty countdownMode;
  private IntegerProperty noOfDigits;
  private IntegerProperty noOfDecimals;
  private long lastTimerCall;
  private AnimationTimer timer;
  
  public Odometer()
  {
    this(6);
  }
  
  public Odometer(int paramInt)
  {
    this.noOfDigits = new SimpleIntegerProperty(paramInt < 0 ? 1 : paramInt);
    this.noOfDecimals = new SimpleIntegerProperty(0);
    this.interval = new SimpleLongProperty(1000L);
    this.rotations = new SimpleIntegerProperty(0);
    this.rotationPreset = new SimpleIntegerProperty(0);
    this.countdownMode = new SimpleBooleanProperty(false);
    this.lastTimerCall = System.nanoTime();
    this.timer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        long l = System.nanoTime();
        if (l > Odometer.this.lastTimerCall + Odometer.this.interval.get() * 1000000L)
        {
          Odometer.this.increment();
          Odometer.this.lastTimerCall = l;
        }
      }
    };
    getStyleClass().add("odometer");
  }
  
  public final Color getColor()
  {
    return (Color)this.color.get();
  }
  
  public final void setColor(Color paramColor)
  {
    this.color.set(paramColor);
  }
  
  public final ObjectProperty<Color> colorProperty()
  {
    return this.color;
  }
  
  public final Color getDecimalColor()
  {
    return (Color)this.decimalColor.get();
  }
  
  public final void setDecimalColor(Color paramColor)
  {
    this.decimalColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> decimalColorProperty()
  {
    return this.decimalColor;
  }
  
  public final Color getNumberColor()
  {
    return (Color)this.numberColor.get();
  }
  
  public final void setNumberColor(Color paramColor)
  {
    this.numberColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> numberColorProperty()
  {
    return this.numberColor;
  }
  
  public final Color getNumberDecimalColor()
  {
    return (Color)this.numberDecimalColor.get();
  }
  
  public final void setNumberDecimalColor(Color paramColor)
  {
    this.numberDecimalColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> numberDecimalColorProperty()
  {
    return this.numberDecimalColor;
  }
  
  public final long getInterval()
  {
    return this.interval.get();
  }
  
  public final void setInterval(long paramLong)
  {
    this.interval.set(paramLong > 5000L ? 5000L : paramLong < 100L ? 100L : paramLong);
  }
  
  public final LongProperty intervalProperty()
  {
    return this.interval;
  }
  
  public final int getRotations()
  {
    return this.rotations.get();
  }
  
  public final ReadOnlyIntegerProperty rotationsProperty()
  {
    return this.rotations;
  }
  
  public final int getRotationPreset()
  {
    return this.rotationPreset.get();
  }
  
  public final void setRotationPreset(int paramInt)
  {
    this.rotationPreset.set(paramInt);
  }
  
  public final IntegerProperty rotationPresetProperty()
  {
    return this.rotationPreset;
  }
  
  public final void start()
  {
    this.timer.start();
  }
  
  public final void stop()
  {
    this.timer.stop();
  }
  
  public final void reset()
  {
    this.timer.stop();
    this.rotations.set(0);
  }
  
  public final boolean isCountdownMode()
  {
    return this.countdownMode.get();
  }
  
  public final void setCountdownMode(boolean paramBoolean)
  {
    this.countdownMode.set(paramBoolean);
  }
  
  public final BooleanProperty countdownModeProperty()
  {
    return this.countdownMode;
  }
  
  public final int getNoOfDigits()
  {
    return this.noOfDigits.get();
  }
  
  public final void setNoOfDigits(int paramInt)
  {
    this.noOfDigits.set(paramInt);
  }
  
  public final IntegerProperty noOfDigitsProperty()
  {
    return this.noOfDigits;
  }
  
  public final int getNoOfDecimals()
  {
    return this.noOfDecimals.get();
  }
  
  public final void setNoOfDecimals(int paramInt)
  {
    this.noOfDecimals.set(paramInt);
  }
  
  public final IntegerProperty noOfDecimalsProperty()
  {
    return this.noOfDecimals;
  }
  
  public final void increment()
  {
    this.rotations.set(this.rotations.get() + 1);
    if (this.rotations.get() >= Math.pow(10.0D, this.noOfDigits.get() + this.noOfDecimals.get())) {
      this.rotations.set(0);
    }
  }
  
  public final int getDialPosition(int paramInt)
  {
    double d = Math.pow(10.0D, paramInt);
    return (int)Math.floor(this.rotations.get() % d / (d / 10.0D));
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1 / (getNoOfDigits() + getNoOfDecimals()) * 1.6875D < paramDouble2 * 0.5925925925925926D ? paramDouble1 / (getNoOfDigits() + getNoOfDecimals()) * 1.6875D : paramDouble2;
    double d2 = d1 * 0.5925925925925926D * (getNoOfDigits() + getNoOfDecimals());
    super.setPrefSize(d2, d1);
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Odometer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */