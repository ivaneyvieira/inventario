package jfxtras.labs.scene.control;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import jfxtras.labs.internal.scene.control.skin.CalendarTimePickerSkin;

public class CalendarTimePicker
  extends Control
{
  private final ObjectProperty<Calendar> calendarObjectProperty = new SimpleObjectProperty(this, "calendar", Calendar.getInstance())
  {
    public void set(Calendar paramAnonymousCalendar)
    {
      paramAnonymousCalendar = CalendarTimePickerSkin.blockMinutesToStep(paramAnonymousCalendar, CalendarTimePicker.this.getMinuteStep());
      super.set(paramAnonymousCalendar);
    }
  };
  private final SimpleObjectProperty<Integer> minuteStepProperty = new SimpleObjectProperty(this, "minuteStep", Integer.valueOf(1))
  {
    public void set(Integer paramAnonymousInteger)
    {
      super.set(paramAnonymousInteger);
      CalendarTimePicker.this.setCalendar(CalendarTimePickerSkin.blockMinutesToStep(CalendarTimePicker.this.getCalendar(), CalendarTimePicker.this.getMinuteStep()));
    }
  };
  private final SimpleObjectProperty<Boolean> showLabelsProperty = new SimpleObjectProperty(this, "showLabels", Boolean.valueOf(false))
  {
    public void set(Boolean paramAnonymousBoolean)
    {
      if (paramAnonymousBoolean == null) {
        throw new NullPointerException("showLabels cannot be null");
      }
      super.set(paramAnonymousBoolean);
    }
  };
  
  public CalendarTimePicker()
  {
    construct();
  }
  
  private void construct()
  {
    getStyleClass().add(getClass().getSimpleName());
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
  
  public CalendarTimePicker withCalendar(Calendar paramCalendar)
  {
    setCalendar(paramCalendar);
    return this;
  }
  
  public ObjectProperty<Integer> minuteStepProperty()
  {
    return this.minuteStepProperty;
  }
  
  public Integer getMinuteStep()
  {
    return (Integer)this.minuteStepProperty.getValue();
  }
  
  public void setMinuteStep(Integer paramInteger)
  {
    this.minuteStepProperty.setValue(paramInteger);
  }
  
  public CalendarTimePicker withMinuteStep(Integer paramInteger)
  {
    setMinuteStep(paramInteger);
    return this;
  }
  
  public ObjectProperty<Boolean> showLabelsProperty()
  {
    return this.showLabelsProperty;
  }
  
  public Boolean getShowLabels()
  {
    return (Boolean)this.showLabelsProperty.getValue();
  }
  
  public void setShowLabels(Boolean paramBoolean)
  {
    this.showLabelsProperty.setValue(paramBoolean);
  }
  
  public CalendarTimePicker withShowLabels(Boolean paramBoolean)
  {
    setShowLabels(paramBoolean);
    return this;
  }
  
  public static String quickFormatCalendar(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return "null";
    }
    SimpleDateFormat localSimpleDateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(1);
    localSimpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
    return paramCalendar == null ? "null" : localSimpleDateFormat.format(paramCalendar.getTime());
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/CalendarTimePicker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */