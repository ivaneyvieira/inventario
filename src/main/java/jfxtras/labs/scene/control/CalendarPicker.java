package jfxtras.labs.scene.control;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;

public class CalendarPicker
  extends Control
{
  private final ObjectProperty<Calendar> calendarObjectProperty = new SimpleObjectProperty(this, "calendar");
  private final ObservableList<Calendar> calendars = FXCollections.observableArrayList();
  private volatile ObjectProperty<Locale> localeObjectProperty = new SimpleObjectProperty(this, "locale", Locale.getDefault());
  private final SimpleObjectProperty<Mode> modeObjectProperty = new SimpleObjectProperty(this, "mode", Mode.SINGLE)
  {
    public void set(Mode paramAnonymousMode)
    {
      if (paramAnonymousMode == null) {
        throw new NullPointerException("Null not allowed");
      }
      super.set(paramAnonymousMode);
    }
  };
  private volatile ObjectProperty<Boolean> showTimeObjectProperty = new SimpleObjectProperty(this, "showTime", Boolean.valueOf(false));
  
  public CalendarPicker()
  {
    construct();
  }
  
  private void construct()
  {
    getStyleClass().add(getClass().getSimpleName());
    constructCalendar();
    constructCalendars();
    constructLocale();
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + getClass().getSimpleName() + ".css").toString();
  }
  
  public ObjectProperty<Calendar> calendarProperty()
  {
    return this.calendarObjectProperty;
  }
  
  public Calendar getCalendar()
  {
    return (Calendar)this.calendarObjectProperty.getValue();
  }
  
  public void setCalendar(Calendar paramCalendar)
  {
    this.calendarObjectProperty.setValue(paramCalendar);
  }
  
  public CalendarPicker withCalendar(Calendar paramCalendar)
  {
    setCalendar(paramCalendar);
    return this;
  }
  
  private void constructCalendar()
  {
    calendarProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Calendar> paramAnonymousObservableValue, Calendar paramAnonymousCalendar1, Calendar paramAnonymousCalendar2)
      {
        if ((paramAnonymousCalendar2 != null) && (!CalendarPicker.this.calendars().contains(paramAnonymousCalendar2))) {
          CalendarPicker.this.calendars().add(paramAnonymousCalendar2);
        }
        if (paramAnonymousCalendar1 != null) {
          CalendarPicker.this.calendars().remove(paramAnonymousCalendar1);
        }
      }
    });
  }
  
  public ObservableList<Calendar> calendars()
  {
    return this.calendars;
  }
  
  private void constructCalendars()
  {
    calendars().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends Calendar> paramAnonymousChange)
      {
        if (!CalendarPicker.this.calendars().contains(CalendarPicker.this.getCalendar())) {
          if (CalendarPicker.this.calendars().size() > 0) {
            CalendarPicker.this.setCalendar((Calendar)CalendarPicker.this.calendars().get(0));
          } else {
            CalendarPicker.this.setCalendar(null);
          }
        }
      }
    });
  }
  
  public ObjectProperty<Locale> localeProperty()
  {
    return this.localeObjectProperty;
  }
  
  public Locale getLocale()
  {
    return (Locale)this.localeObjectProperty.getValue();
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.localeObjectProperty.setValue(paramLocale);
  }
  
  public CalendarPicker withLocale(Locale paramLocale)
  {
    setLocale(paramLocale);
    return this;
  }
  
  private void constructLocale()
  {
    localeProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable) {}
    });
  }
  
  public ObjectProperty<Mode> modeProperty()
  {
    return this.modeObjectProperty;
  }
  
  public Mode getMode()
  {
    return (Mode)this.modeObjectProperty.getValue();
  }
  
  public void setMode(Mode paramMode)
  {
    this.modeObjectProperty.setValue(paramMode);
  }
  
  public CalendarPicker withMode(Mode paramMode)
  {
    setMode(paramMode);
    return this;
  }
  
  public ObjectProperty<Boolean> showTimeProperty()
  {
    return this.showTimeObjectProperty;
  }
  
  public Boolean getShowTime()
  {
    return (Boolean)this.showTimeObjectProperty.getValue();
  }
  
  public void setShowTime(Boolean paramBoolean)
  {
    this.showTimeObjectProperty.setValue(paramBoolean);
  }
  
  public CalendarPicker withShowTime(Boolean paramBoolean)
  {
    setShowTime(paramBoolean);
    return this;
  }
  
  public static String quickFormatCalendar(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return "null";
    }
    SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(1);
    localSimpleDateFormat.applyPattern("yyyy-MM-dd");
    return paramCalendar == null ? "null" : localSimpleDateFormat.format(paramCalendar.getTime());
  }
  
  public static String quickFormatCalendar(List<Calendar> paramList)
  {
    if (paramList == null) {
      return "null";
    }
    String str = paramList.size() + "x [";
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Calendar localCalendar = (Calendar)localIterator.next();
      if (!str.endsWith("[")) {
        str = str + ",";
      }
      str = str + quickFormatCalendar(localCalendar);
    }
    str = str + "]";
    return str;
  }
  
  public static enum Mode
  {
    SINGLE,  MULTIPLE,  RANGE;
    
    private Mode() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/CalendarPicker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */