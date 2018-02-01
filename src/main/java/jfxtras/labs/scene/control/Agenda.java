package jfxtras.labs.scene.control;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.util.Callback;

public class Agenda
  extends Control
{
  private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
  private final ObservableList<AppointmentGroup> appointmentGroups = FXCollections.observableArrayList();
  private final ObjectProperty<Locale> iLocaleObjectProperty = new SimpleObjectProperty(this, "locale", Locale.getDefault());
  private final ObjectProperty<Calendar> displayedCalendarObjectProperty = new SimpleObjectProperty(this, "displayedCalendar", Calendar.getInstance());
  private final ObservableList<Appointment> selectedAppointments = FXCollections.observableArrayList();
  private final ObjectProperty<Callback<CalendarRange, Void>> calendarRangeCallbackObjectProperty = new SimpleObjectProperty(this, "calendarRangeCallback", null);
  private final ObjectProperty<Callback<CalendarRange, Appointment>> createAppointmentCallbackObjectProperty = new SimpleObjectProperty(this, "createAppointmentCallback", null);
  
  public Agenda()
  {
    construct();
  }
  
  private void construct()
  {
    getStyleClass().add(getClass().getSimpleName());
    constructAppointments();
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + getClass().getSimpleName() + ".css").toString();
  }
  
  public ObservableList<Appointment> appointments()
  {
    return this.appointments;
  }
  
  private void constructAppointments()
  {
    this.appointments.addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends Appointment> paramAnonymousChange)
      {
        while (paramAnonymousChange.next())
        {
          Iterator localIterator = paramAnonymousChange.getRemoved().iterator();
          while (localIterator.hasNext())
          {
            Appointment localAppointment = (Appointment)localIterator.next();
            Agenda.this.selectedAppointments.remove(localAppointment);
          }
        }
      }
    });
  }
  
  public ObservableList<AppointmentGroup> appointmentGroups()
  {
    return this.appointmentGroups;
  }
  
  public ObjectProperty<Locale> localeProperty()
  {
    return this.iLocaleObjectProperty;
  }
  
  public Locale getLocale()
  {
    return (Locale)this.iLocaleObjectProperty.getValue();
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.iLocaleObjectProperty.setValue(paramLocale);
  }
  
  public Agenda withLocale(Locale paramLocale)
  {
    setLocale(paramLocale);
    return this;
  }
  
  public ObjectProperty<Calendar> displayedCalendar()
  {
    return this.displayedCalendarObjectProperty;
  }
  
  public Calendar getDisplayedCalendar()
  {
    return (Calendar)this.displayedCalendarObjectProperty.getValue();
  }
  
  public void setDisplayedCalendar(Calendar paramCalendar)
  {
    this.displayedCalendarObjectProperty.setValue(paramCalendar);
  }
  
  public Agenda withDisplayedCalendar(Calendar paramCalendar)
  {
    setDisplayedCalendar(paramCalendar);
    return this;
  }
  
  public ObservableList<Appointment> selectedAppointments()
  {
    return this.selectedAppointments;
  }
  
  public ObjectProperty<Callback<CalendarRange, Void>> calendarRangeCallbackProperty()
  {
    return this.calendarRangeCallbackObjectProperty;
  }
  
  public Callback<CalendarRange, Void> getCalendarRangeCallback()
  {
    return (Callback)this.calendarRangeCallbackObjectProperty.getValue();
  }
  
  public void setCalendarRangeCallback(Callback<CalendarRange, Void> paramCallback)
  {
    this.calendarRangeCallbackObjectProperty.setValue(paramCallback);
  }
  
  public Agenda withCalendarRangeCallback(Callback<CalendarRange, Void> paramCallback)
  {
    setCalendarRangeCallback(paramCallback);
    return this;
  }
  
  public ObjectProperty<Callback<CalendarRange, Appointment>> createAppointmentCallbackProperty()
  {
    return this.createAppointmentCallbackObjectProperty;
  }
  
  public Callback<CalendarRange, Appointment> getCreateAppointmentCallback()
  {
    return (Callback)this.createAppointmentCallbackObjectProperty.getValue();
  }
  
  public void setCreateAppointmentCallback(Callback<CalendarRange, Appointment> paramCallback)
  {
    this.createAppointmentCallbackObjectProperty.setValue(paramCallback);
  }
  
  public Agenda withCreateAppointmentCallback(Callback<CalendarRange, Appointment> paramCallback)
  {
    setCreateAppointmentCallback(paramCallback);
    return this;
  }
  
  public static String quickFormatCalendar(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return "";
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    return localSimpleDateFormat.format(paramCalendar.getTime());
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
  
  public static class AppointmentGroupImpl
    implements AppointmentGroup
  {
    private final ObjectProperty<String> descriptionObjectProperty = new SimpleObjectProperty(this, "description");
    private final ObjectProperty<String> styleClassObjectProperty = new SimpleObjectProperty(this, "styleClass");
    
    public ObjectProperty<String> descriptionProperty()
    {
      return this.descriptionObjectProperty;
    }
    
    public String getDescription()
    {
      return (String)this.descriptionObjectProperty.getValue();
    }
    
    public void setDescription(String paramString)
    {
      this.descriptionObjectProperty.setValue(paramString);
    }
    
    public AppointmentGroupImpl withDescription(String paramString)
    {
      setDescription(paramString);
      return this;
    }
    
    public ObjectProperty<String> styleClassProperty()
    {
      return this.styleClassObjectProperty;
    }
    
    public String getStyleClass()
    {
      return (String)this.styleClassObjectProperty.getValue();
    }
    
    public void setStyleClass(String paramString)
    {
      this.styleClassObjectProperty.setValue(paramString);
    }
    
    public AppointmentGroupImpl withStyleClass(String paramString)
    {
      setStyleClass(paramString);
      return this;
    }
  }
  
  public static abstract interface AppointmentGroup
  {
    public abstract String getDescription();
    
    public abstract void setDescription(String paramString);
    
    public abstract String getStyleClass();
    
    public abstract void setStyleClass(String paramString);
  }
  
  public static class AppointmentImpl
    implements Appointment
  {
    private final ObjectProperty<Calendar> startTimeObjectProperty = new SimpleObjectProperty(this, "startTime");
    private final ObjectProperty<Calendar> endTimeObjectProperty = new SimpleObjectProperty(this, "endTime");
    private final ObjectProperty<Boolean> wholeDayObjectProperty = new SimpleObjectProperty(this, "wholeDay", Boolean.valueOf(false));
    private final ObjectProperty<String> summaryObjectProperty = new SimpleObjectProperty(this, "summary");
    private final ObjectProperty<String> descriptionObjectProperty = new SimpleObjectProperty(this, "description");
    private final ObjectProperty<String> locationObjectProperty = new SimpleObjectProperty(this, "location");
    private final ObjectProperty<AppointmentGroup> appointmentGroupObjectProperty = new SimpleObjectProperty(this, "appointmentGroup");
    
    public ObjectProperty<Calendar> startTimeProperty()
    {
      return this.startTimeObjectProperty;
    }
    
    public Calendar getStartTime()
    {
      return (Calendar)this.startTimeObjectProperty.getValue();
    }
    
    public void setStartTime(Calendar paramCalendar)
    {
      this.startTimeObjectProperty.setValue(paramCalendar);
    }
    
    public AppointmentImpl withStartTime(Calendar paramCalendar)
    {
      setStartTime(paramCalendar);
      return this;
    }
    
    public ObjectProperty<Calendar> endTimeProperty()
    {
      return this.endTimeObjectProperty;
    }
    
    public Calendar getEndTime()
    {
      return (Calendar)this.endTimeObjectProperty.getValue();
    }
    
    public void setEndTime(Calendar paramCalendar)
    {
      this.endTimeObjectProperty.setValue(paramCalendar);
    }
    
    public AppointmentImpl withEndTime(Calendar paramCalendar)
    {
      setEndTime(paramCalendar);
      return this;
    }
    
    public ObjectProperty<Boolean> wholeDayProperty()
    {
      return this.wholeDayObjectProperty;
    }
    
    public Boolean isWholeDay()
    {
      return (Boolean)this.wholeDayObjectProperty.getValue();
    }
    
    public void setWholeDay(Boolean paramBoolean)
    {
      this.wholeDayObjectProperty.setValue(paramBoolean);
    }
    
    public AppointmentImpl withWholeDay(Boolean paramBoolean)
    {
      setWholeDay(paramBoolean);
      return this;
    }
    
    public ObjectProperty<String> summaryProperty()
    {
      return this.summaryObjectProperty;
    }
    
    public String getSummary()
    {
      return (String)this.summaryObjectProperty.getValue();
    }
    
    public void setSummary(String paramString)
    {
      this.summaryObjectProperty.setValue(paramString);
    }
    
    public AppointmentImpl withSummary(String paramString)
    {
      setSummary(paramString);
      return this;
    }
    
    public ObjectProperty<String> descriptionProperty()
    {
      return this.descriptionObjectProperty;
    }
    
    public String getDescription()
    {
      return (String)this.descriptionObjectProperty.getValue();
    }
    
    public void setDescription(String paramString)
    {
      this.descriptionObjectProperty.setValue(paramString);
    }
    
    public AppointmentImpl withDescription(String paramString)
    {
      setDescription(paramString);
      return this;
    }
    
    public ObjectProperty<String> locationProperty()
    {
      return this.locationObjectProperty;
    }
    
    public String getLocation()
    {
      return (String)this.locationObjectProperty.getValue();
    }
    
    public void setLocation(String paramString)
    {
      this.locationObjectProperty.setValue(paramString);
    }
    
    public AppointmentImpl withLocation(String paramString)
    {
      setLocation(paramString);
      return this;
    }
    
    public ObjectProperty<AppointmentGroup> appointmentGroupProperty()
    {
      return this.appointmentGroupObjectProperty;
    }
    
    public AppointmentGroup getAppointmentGroup()
    {
      return (AppointmentGroup)this.appointmentGroupObjectProperty.getValue();
    }
    
    public void setAppointmentGroup(AppointmentGroup paramAppointmentGroup)
    {
      this.appointmentGroupObjectProperty.setValue(paramAppointmentGroup);
    }
    
    public AppointmentImpl withAppointmentGroup(AppointmentGroup paramAppointmentGroup)
    {
      setAppointmentGroup(paramAppointmentGroup);
      return this;
    }
    
    public String toString()
    {
      return super.toString() + ", " + Agenda.quickFormatCalendar(getStartTime()) + " - " + Agenda.quickFormatCalendar(getEndTime());
    }
  }
  
  public static abstract interface Appointment
  {
    public abstract Calendar getStartTime();
    
    public abstract void setStartTime(Calendar paramCalendar);
    
    public abstract Calendar getEndTime();
    
    public abstract void setEndTime(Calendar paramCalendar);
    
    public abstract Boolean isWholeDay();
    
    public abstract void setWholeDay(Boolean paramBoolean);
    
    public abstract String getSummary();
    
    public abstract void setSummary(String paramString);
    
    public abstract String getDescription();
    
    public abstract void setDescription(String paramString);
    
    public abstract String getLocation();
    
    public abstract void setLocation(String paramString);
    
    public abstract AppointmentGroup getAppointmentGroup();
    
    public abstract void setAppointmentGroup(AppointmentGroup paramAppointmentGroup);
  }
  
  public static class CalendarRange
  {
    Calendar start;
    Calendar end;
    
    public CalendarRange(Calendar paramCalendar1, Calendar paramCalendar2)
    {
      this.start = paramCalendar1;
      this.end = paramCalendar2;
    }
    
    public Calendar getStartCalendar()
    {
      return this.start;
    }
    
    public Calendar getEndCalendar()
    {
      return this.end;
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/Agenda.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */