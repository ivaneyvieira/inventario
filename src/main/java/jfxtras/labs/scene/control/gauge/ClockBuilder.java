package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Paint;
import javafx.util.Builder;

public class ClockBuilder<B extends ClockBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<Clock>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final ClockBuilder create()
  {
    return new ClockBuilder();
  }
  
  public final ClockBuilder timeZone(String paramString)
  {
    this.properties.put("timeZone", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final ClockBuilder daylightSavingTime(boolean paramBoolean)
  {
    this.properties.put("daylightSavingTime", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final ClockBuilder secondPointerVisible(boolean paramBoolean)
  {
    this.properties.put("secondPointerVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final ClockBuilder autoDimEnabled(boolean paramBoolean)
  {
    this.properties.put("autoDimEnabled", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final ClockBuilder running(boolean paramBoolean)
  {
    this.properties.put("running", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final ClockBuilder clockStyle(Clock.ClockStyle paramClockStyle)
  {
    this.properties.put("clockStyle", new SimpleObjectProperty(paramClockStyle));
    return this;
  }
  
  public final ClockBuilder theme(Clock.Theme paramTheme)
  {
    this.properties.put("theme", new SimpleObjectProperty(paramTheme));
    return this;
  }
  
  public final ClockBuilder brightBackgroundPaint(Paint paramPaint)
  {
    this.properties.put("brightBackgroundPaint", new SimpleObjectProperty(paramPaint));
    return this;
  }
  
  public final ClockBuilder darkBackgroundPaint(Paint paramPaint)
  {
    this.properties.put("darkBackgroundPaint", new SimpleObjectProperty(paramPaint));
    return this;
  }
  
  public final ClockBuilder brightPointerPaint(Paint paramPaint)
  {
    this.properties.put("brightPointerPaint", new SimpleObjectProperty(paramPaint));
    return this;
  }
  
  public final ClockBuilder darkPointerPaint(Paint paramPaint)
  {
    this.properties.put("darkPointerPaint", new SimpleObjectProperty(paramPaint));
    return this;
  }
  
  public final ClockBuilder brightTickMarkPaint(Paint paramPaint)
  {
    this.properties.put("brightTickMarkPaint", new SimpleObjectProperty(paramPaint));
    return this;
  }
  
  public final ClockBuilder darkTickMarkPaint(Paint paramPaint)
  {
    this.properties.put("darkTickMarkPaint", new SimpleObjectProperty(paramPaint));
    return this;
  }
  
  public final ClockBuilder secondPointerPaint(Paint paramPaint)
  {
    this.properties.put("secondPointerPaint", new SimpleObjectProperty(paramPaint));
    return this;
  }
  
  public final ClockBuilder title(String paramString)
  {
    this.properties.put("title", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final ClockBuilder hour(int paramInt)
  {
    this.properties.put("hour", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final ClockBuilder minute(int paramInt)
  {
    this.properties.put("minute", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final ClockBuilder second(int paramInt)
  {
    this.properties.put("second", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final B prefWidth(double paramDouble)
  {
    this.properties.put("prefWidth", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B prefHeight(double paramDouble)
  {
    this.properties.put("prefHeight", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutX(double paramDouble)
  {
    this.properties.put("layoutX", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutY(double paramDouble)
  {
    this.properties.put("layoutY", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final Clock build()
  {
    Clock localClock = new Clock();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("timeZone".equals(str)) {
        localClock.setTimeZone((String)((StringProperty)this.properties.get(str)).get());
      } else if ("daylightSavingTime".equals(str)) {
        localClock.setDaylightSavingTime(((BooleanProperty)this.properties.get(str)).get());
      } else if ("secondPointerVisible".equals(str)) {
        localClock.setSecondPointerVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("autoDimEnabled".equals(str)) {
        localClock.setAutoDimEnabled(((BooleanProperty)this.properties.get(str)).get());
      } else if ("running".equals(str)) {
        localClock.setRunning(((BooleanProperty)this.properties.get(str)).get());
      } else if ("clockStyle".equals(str)) {
        localClock.setClockStyle((Clock.ClockStyle)((ObjectProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localClock.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localClock.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("theme".equals(str)) {
        localClock.setTheme((Clock.Theme)((ObjectProperty)this.properties.get(str)).get());
      } else if ("brightBackgroundPaint".equals(str)) {
        localClock.setBrightBackgroundPaint((Paint)((ObjectProperty)this.properties.get(str)).get());
      } else if ("darkBackgroundPaint".equals(str)) {
        localClock.setDarkBackgroundPaint((Paint)((ObjectProperty)this.properties.get(str)).get());
      } else if ("brightPointerPaint".equals(str)) {
        localClock.setBrightPointerPaint((Paint)((ObjectProperty)this.properties.get(str)).get());
      } else if ("darkPointerPaint".equals(str)) {
        localClock.setDarkPointerPaint((Paint)((ObjectProperty)this.properties.get(str)).get());
      } else if ("brightTickMarkPaint".equals(str)) {
        localClock.setBrightTickMarkPaint((Paint)((ObjectProperty)this.properties.get(str)).get());
      } else if ("darkTickMarkPaint".equals(str)) {
        localClock.setDarkTickMarkPaint((Paint)((ObjectProperty)this.properties.get(str)).get());
      } else if ("secondPointerPaint".equals(str)) {
        localClock.setSecondPointerPaint((Paint)((ObjectProperty)this.properties.get(str)).get());
      } else if ("title".equals(str)) {
        localClock.setTitle((String)((StringProperty)this.properties.get(str)).get());
      } else if ("hour".equals(str)) {
        localClock.setHour(((IntegerProperty)this.properties.get(str)).get());
      } else if ("minute".equals(str)) {
        localClock.setMinute(((IntegerProperty)this.properties.get(str)).get());
      } else if ("second".equals(str)) {
        localClock.setSecond(((IntegerProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localClock.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localClock.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localClock;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/ClockBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */