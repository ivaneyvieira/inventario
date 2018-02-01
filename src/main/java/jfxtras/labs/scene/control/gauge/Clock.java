package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Clock
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "clock";
  private StringProperty timeZone = new SimpleStringProperty(Calendar.getInstance().getTimeZone().getDisplayName());
  private BooleanProperty running = new SimpleBooleanProperty(false);
  private BooleanProperty secondPointerVisible = new SimpleBooleanProperty(true);
  private BooleanProperty autoDimEnabled = new SimpleBooleanProperty(false);
  private BooleanProperty daylightSavingTime = new SimpleBooleanProperty(Calendar.getInstance().getTimeZone().inDaylightTime(new Date()));
  private ObjectProperty<Theme> theme = new SimpleObjectProperty(Theme.BRIGHT);
  private ObjectProperty<ClockStyle> clockStyle = new SimpleObjectProperty(ClockStyle.DB);
  private ObjectProperty<Paint> brightBackgroundPaint = new SimpleObjectProperty(Color.WHITE);
  private ObjectProperty<Paint> darkBackgroundPaint = new SimpleObjectProperty(Color.BLACK);
  private ObjectProperty<Paint> brightTickMarkPaint = new SimpleObjectProperty(Color.BLACK);
  private ObjectProperty<Paint> darkTickMarkPaint = new SimpleObjectProperty(Color.WHITE);
  private ObjectProperty<Paint> brightPointerPaint = new SimpleObjectProperty(Color.BLACK);
  private ObjectProperty<Paint> darkPointerPaint = new SimpleObjectProperty(Color.WHITE);
  private ObjectProperty<Paint> secondPointerPaint = new SimpleObjectProperty(Color.rgb(237, 0, 58));
  private IntegerProperty hour = new SimpleIntegerProperty(0);
  private IntegerProperty minute = new SimpleIntegerProperty(0);
  private IntegerProperty second = new SimpleIntegerProperty(0);
  private StringProperty title = new SimpleStringProperty("");
  
  public Clock()
  {
    getStyleClass().add("clock");
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 <= paramDouble2 ? paramDouble1 : paramDouble2;
    super.setPrefSize(d, d);
  }
  
  public final String getTimeZone()
  {
    return (String)this.timeZone.get();
  }
  
  public final void setTimeZone(String paramString)
  {
    this.timeZone.set(paramString);
  }
  
  public final StringProperty timeZoneProperty()
  {
    return this.timeZone;
  }
  
  public final boolean isRunning()
  {
    return this.running.get();
  }
  
  public final void setRunning(boolean paramBoolean)
  {
    this.running.set(paramBoolean);
  }
  
  public final BooleanProperty runningProperty()
  {
    return this.running;
  }
  
  public final boolean isSecondPointerVisible()
  {
    return this.secondPointerVisible.get();
  }
  
  public final void setSecondPointerVisible(boolean paramBoolean)
  {
    this.secondPointerVisible.set(paramBoolean);
  }
  
  public final BooleanProperty secondPointerVisibleProperty()
  {
    return this.secondPointerVisible;
  }
  
  public final boolean isAutoDimEnabled()
  {
    return this.autoDimEnabled.get();
  }
  
  public final void setAutoDimEnabled(boolean paramBoolean)
  {
    this.autoDimEnabled.set(paramBoolean);
  }
  
  public final BooleanProperty autoDimEnabledProperty()
  {
    return this.autoDimEnabled;
  }
  
  public final boolean isDaylightSavingTime()
  {
    return this.daylightSavingTime.get();
  }
  
  public final void setDaylightSavingTime(boolean paramBoolean)
  {
    this.daylightSavingTime.set(paramBoolean);
  }
  
  public final BooleanProperty daylightSavingTimeProperty()
  {
    return this.daylightSavingTime;
  }
  
  public final Theme getTheme()
  {
    return (Theme)this.theme.get();
  }
  
  public final void setTheme(Theme paramTheme)
  {
    this.theme.set(paramTheme);
  }
  
  public final ObjectProperty<Theme> themeProperty()
  {
    return this.theme;
  }
  
  public final ClockStyle getClockStyle()
  {
    return (ClockStyle)this.clockStyle.get();
  }
  
  public final void setClockStyle(ClockStyle paramClockStyle)
  {
    this.clockStyle.set(paramClockStyle);
  }
  
  public final ObjectProperty<ClockStyle> clockStyleProperty()
  {
    return this.clockStyle;
  }
  
  public final Paint getBrightBackgroundPaint()
  {
    return (Paint)this.brightBackgroundPaint.get();
  }
  
  public final void setBrightBackgroundPaint(Paint paramPaint)
  {
    this.brightBackgroundPaint.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> brightBackgroundPaintProperty()
  {
    return this.brightBackgroundPaint;
  }
  
  public final Paint getDarkBackgroundPaint()
  {
    return (Paint)this.darkBackgroundPaint.get();
  }
  
  public final void setDarkBackgroundPaint(Paint paramPaint)
  {
    this.darkBackgroundPaint.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> darkBackgroundPaintProperty()
  {
    return this.darkBackgroundPaint;
  }
  
  public final Paint getBrightTickMarkPaint()
  {
    return (Paint)this.brightTickMarkPaint.get();
  }
  
  public final void setBrightTickMarkPaint(Paint paramPaint)
  {
    this.brightTickMarkPaint.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> brightTickMarkPaintProperty()
  {
    return this.brightTickMarkPaint;
  }
  
  public final Paint getDarkTickMarkPaint()
  {
    return (Paint)this.darkTickMarkPaint.get();
  }
  
  public final void setDarkTickMarkPaint(Paint paramPaint)
  {
    this.darkTickMarkPaint.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> darkTickMarkPaintProperty()
  {
    return this.darkTickMarkPaint;
  }
  
  public final Paint getBrightPointerPaint()
  {
    return (Paint)this.brightPointerPaint.get();
  }
  
  public final void setBrightPointerPaint(Paint paramPaint)
  {
    this.brightPointerPaint.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> brightPointerPaintProperty()
  {
    return this.brightPointerPaint;
  }
  
  public final Paint getDarkPointerPaint()
  {
    return (Paint)this.darkPointerPaint.get();
  }
  
  public final void setDarkPointerPaint(Paint paramPaint)
  {
    this.darkPointerPaint.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> darkPointerPaintProperty()
  {
    return this.darkPointerPaint;
  }
  
  public final Paint getSecondPointerPaint()
  {
    return (Paint)this.secondPointerPaint.get();
  }
  
  public final void setSecondPointerPaint(Paint paramPaint)
  {
    this.secondPointerPaint.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> secondPointerPaintProperty()
  {
    return this.secondPointerPaint;
  }
  
  public final String getTitle()
  {
    return (String)this.title.get();
  }
  
  public final void setTitle(String paramString)
  {
    this.title.set(paramString);
  }
  
  public final StringProperty titleProperty()
  {
    return this.title;
  }
  
  public final int getHour()
  {
    return this.hour.get();
  }
  
  public final void setHour(int paramInt)
  {
    this.hour.set(clamp(0, 23, paramInt));
  }
  
  public final ReadOnlyIntegerProperty hourProperty()
  {
    return this.hour;
  }
  
  public final int getMinute()
  {
    return this.minute.get();
  }
  
  public final void setMinute(int paramInt)
  {
    this.minute.set(clamp(0, 59, paramInt));
  }
  
  public final ReadOnlyIntegerProperty minuteProperty()
  {
    return this.minute;
  }
  
  public final int getSecond()
  {
    return this.second.get();
  }
  
  public final void setSecond(int paramInt)
  {
    this.second.set(clamp(0, 59, paramInt));
  }
  
  public final ReadOnlyIntegerProperty secondProperty()
  {
    return this.second;
  }
  
  private int clamp(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt3 > paramInt2 ? paramInt2 : paramInt3 < paramInt1 ? paramInt1 : paramInt3;
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
  
  public static enum ClockStyle
  {
    DB,  IOS6,  STANDARD;
    
    private ClockStyle() {}
  }
  
  public static enum Theme
  {
    BRIGHT,  DARK;
    
    private Theme() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Clock.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */