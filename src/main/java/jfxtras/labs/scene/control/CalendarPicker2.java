package jfxtras.labs.scene.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javax.time.calendar.LocalDate;
import javax.time.calendar.MonthOfYear;

public class CalendarPicker2
  extends LocalDatePicker
{
  private final ObjectProperty<Calendar> calendarObjectProperty = new SimpleObjectProperty(this, "calendar");
  private final ObservableList<Calendar> calendars = FXCollections.observableArrayList();
  
  public CalendarPicker2()
  {
    construct();
  }
  
  private void construct()
  {
    constructCalendar();
    constructCalendars();
  }
  
  public ObjectProperty<Calendar> calendarProperty()
  {
    return this.calendarObjectProperty;
  }
  
  private void constructCalendar()
  {
    calendarProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Calendar> paramAnonymousObservableValue, Calendar paramAnonymousCalendar1, Calendar paramAnonymousCalendar2)
      {
        CalendarPicker2.this.localDateProperty().set(CalendarPicker2.this.createLocalDateFromCalendar(paramAnonymousCalendar2));
      }
    });
    localDateProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends LocalDate> paramAnonymousObservableValue, LocalDate paramAnonymousLocalDate1, LocalDate paramAnonymousLocalDate2)
      {
        CalendarPicker2.this.calendarObjectProperty.set(paramAnonymousLocalDate2 == null ? null : CalendarPicker2.this.createCalendarFromLocalDate(paramAnonymousLocalDate2));
      }
    });
  }
  
  public Calendar getCalendar()
  {
    return (Calendar)this.calendarObjectProperty.getValue();
  }
  
  public void setCalendar(Calendar paramCalendar)
  {
    this.calendarObjectProperty.setValue(paramCalendar);
  }
  
  public CalendarPicker2 withCalendar(Calendar paramCalendar)
  {
    setCalendar(paramCalendar);
    return this;
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
        while (paramAnonymousChange.next())
        {
          Iterator localIterator = paramAnonymousChange.getRemoved().iterator();
          Calendar localCalendar;
          LocalDate localLocalDate;
          while (localIterator.hasNext())
          {
            localCalendar = (Calendar)localIterator.next();
            localLocalDate = CalendarPicker2.this.createLocalDateFromCalendar(localCalendar);
            if (CalendarPicker2.this.localDates().contains(localLocalDate)) {
              CalendarPicker2.this.localDates().remove(localLocalDate);
            }
          }
          localIterator = paramAnonymousChange.getAddedSubList().iterator();
          while (localIterator.hasNext())
          {
            localCalendar = (Calendar)localIterator.next();
            localLocalDate = CalendarPicker2.this.createLocalDateFromCalendar(localCalendar);
            if (!CalendarPicker2.this.localDates().contains(localLocalDate)) {
              CalendarPicker2.this.localDates().add(localLocalDate);
            }
          }
        }
      }
    });
    localDates().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends LocalDate> paramAnonymousChange)
      {
        while (paramAnonymousChange.next())
        {
          Iterator localIterator = paramAnonymousChange.getRemoved().iterator();
          LocalDate localLocalDate;
          Calendar localCalendar;
          while (localIterator.hasNext())
          {
            localLocalDate = (LocalDate)localIterator.next();
            localCalendar = CalendarPicker2.this.createCalendarFromLocalDate(localLocalDate);
            if (CalendarPicker2.this.calendars().contains(localCalendar)) {
              CalendarPicker2.this.calendars().remove(localCalendar);
            }
          }
          localIterator = paramAnonymousChange.getAddedSubList().iterator();
          while (localIterator.hasNext())
          {
            localLocalDate = (LocalDate)localIterator.next();
            localCalendar = CalendarPicker2.this.createCalendarFromLocalDate(localLocalDate);
            if (!CalendarPicker2.this.calendars().contains(localCalendar)) {
              CalendarPicker2.this.calendars().add(localCalendar);
            }
          }
        }
      }
    });
  }
  
  private Calendar createCalendarFromLocalDate(LocalDate paramLocalDate)
  {
    if (paramLocalDate == null) {
      return null;
    }
    Calendar localCalendar = Calendar.getInstance(getLocale());
    localCalendar.set(1, paramLocalDate.getYear());
    localCalendar.set(2, paramLocalDate.getMonthOfYear().getValue() - 1);
    localCalendar.set(5, paramLocalDate.getDayOfMonth());
    localCalendar.set(11, 0);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    return localCalendar;
  }
  
  private LocalDate createLocalDateFromCalendar(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    LocalDate localLocalDate = LocalDate.of(paramCalendar.get(1), paramCalendar.get(2) + 1, paramCalendar.get(5));
    return localLocalDate;
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
        str = str + ", ";
      }
      str = str + quickFormatCalendar(localCalendar);
    }
    str = str + "]";
    return str;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/CalendarPicker2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */