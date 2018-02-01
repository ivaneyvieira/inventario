package jfxtras.labs.animation;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class Timer
{
  private final Runnable runnable;
  private final java.util.Timer timer;
  private final ObjectProperty<Duration> delayObjectProperty = new SimpleObjectProperty(this, "delay", Duration.millis(0.0D));
  private final ObjectProperty<Duration> cycleDurationObjectProperty = new SimpleObjectProperty(this, "cycleDuration", Duration.millis(1000.0D));
  private final ObjectProperty<Boolean> repeatsObjectProperty = new SimpleObjectProperty(this, "repeats", Boolean.TRUE);
  private final AtomicReference<TimerTask> timerTaskAtomicReference = new AtomicReference(null);
  
  public Timer(Runnable paramRunnable)
  {
    this(true, paramRunnable);
  }
  
  public Timer(boolean paramBoolean, Runnable paramRunnable)
  {
    this.runnable = paramRunnable;
    this.timer = new java.util.Timer(paramBoolean);
  }
  
  public ObjectProperty<Duration> delayProperty()
  {
    return this.delayObjectProperty;
  }
  
  public Duration getDelay()
  {
    return (Duration)this.delayObjectProperty.getValue();
  }
  
  public void setDelay(Duration paramDuration)
  {
    this.delayObjectProperty.setValue(paramDuration);
  }
  
  public Timer withDelay(Duration paramDuration)
  {
    setDelay(paramDuration);
    return this;
  }
  
  public ObjectProperty<Duration> cycleDurationProperty()
  {
    return this.cycleDurationObjectProperty;
  }
  
  public Duration getCycleDuration()
  {
    return (Duration)this.cycleDurationObjectProperty.getValue();
  }
  
  public void setCycleDuration(Duration paramDuration)
  {
    this.cycleDurationObjectProperty.setValue(paramDuration);
  }
  
  public Timer withCycleDuration(Duration paramDuration)
  {
    setCycleDuration(paramDuration);
    return this;
  }
  
  public ObjectProperty<Boolean> repeatsProperty()
  {
    return this.repeatsObjectProperty;
  }
  
  public boolean getRepeats()
  {
    return ((Boolean)this.repeatsObjectProperty.getValue()).booleanValue();
  }
  
  public void setRepeats(boolean paramBoolean)
  {
    this.repeatsObjectProperty.setValue(Boolean.valueOf(paramBoolean));
  }
  
  public Timer withRepeats(boolean paramBoolean)
  {
    setRepeats(paramBoolean);
    return this;
  }
  
  public synchronized Timer start()
  {
    if (this.timerTaskAtomicReference.get() != null) {
      throw new IllegalStateException("Timer already started");
    }
    TimerTask local1 = new TimerTask()
    {
      public void run()
      {
        Platform.runLater(Timer.this.runnable);
        if (!((Boolean)Timer.this.repeatsObjectProperty.getValue()).booleanValue()) {
          Timer.this.stop();
        }
      }
    };
    this.timer.schedule(local1, ((Duration)this.delayObjectProperty.getValue()).toMillis(), ((Duration)this.cycleDurationObjectProperty.getValue()).toMillis());
    this.timerTaskAtomicReference.set(local1);
    return this;
  }
  
  public Timer stop()
  {
    TimerTask localTimerTask = (TimerTask)this.timerTaskAtomicReference.getAndSet(null);
    if (localTimerTask != null) {
      localTimerTask.cancel();
    }
    return this;
  }
  
  public Timer restart()
  {
    stop();
    start();
    return this;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/animation/Timer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */