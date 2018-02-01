package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import jfxtras.labs.internal.scene.control.behavior.CalendarPickerBehavior;
import jfxtras.labs.scene.control.CalendarPicker;

public abstract class CalendarPickerMonthlySkinAbstract<S>
  extends SkinBase<CalendarPicker, CalendarPickerBehavior>
{
  private volatile ObjectProperty<Calendar> displayedCalendarObjectProperty = new SimpleObjectProperty(this, "displayedCalendar");
  private SimpleDateFormat simpleDateFormat = null;
  private Calendar iToday = Calendar.getInstance();
  private int iTodayYear = this.iToday.get(1);
  private int iTodayMonth = this.iToday.get(2);
  private int iTodayDay = this.iToday.get(5);
  
  public CalendarPickerMonthlySkinAbstract(CalendarPicker paramCalendarPicker)
  {
    super(paramCalendarPicker, new CalendarPickerBehavior(paramCalendarPicker));
    construct();
  }
  
  private void construct()
  {
    setDisplayedCalendar(Calendar.getInstance());
    ((CalendarPicker)getSkinnable()).localeProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        CalendarPickerMonthlySkinAbstract.this.refreshLocale();
      }
    });
    refreshLocale();
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
    Calendar localCalendar = getDisplayedCalendar();
    if ((paramCalendar != null) && (localCalendar != null) && (paramCalendar.get(1) == localCalendar.get(1)) && (paramCalendar.get(2) == localCalendar.get(2)) && (paramCalendar.get(5) == localCalendar.get(5))) {
      return;
    }
    this.displayedCalendarObjectProperty.setValue(derriveDisplayedCalendar(paramCalendar));
  }
  
  public S withDisplayedCalendar(Calendar paramCalendar)
  {
    setDisplayedCalendar(paramCalendar);
    return this;
  }
  
  private Calendar derriveDisplayedCalendar(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return null;
    }
    Calendar localCalendar = Calendar.getInstance(((CalendarPicker)getSkinnable()).getLocale());
    localCalendar.set(5, 1);
    localCalendar.set(2, paramCalendar.get(2));
    localCalendar.set(1, paramCalendar.get(1));
    localCalendar.set(11, 0);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    return localCalendar;
  }
  
  private void refreshLocale()
  {
    this.simpleDateFormat = ((SimpleDateFormat)SimpleDateFormat.getDateInstance(1, ((CalendarPicker)getSkinnable()).getLocale()));
    setDisplayedCalendar(getDisplayedCalendar());
  }
  
  protected List<String> getWeekdayLabels()
  {
    ArrayList localArrayList = new ArrayList();
    this.simpleDateFormat.applyPattern("E");
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(2009, 6, 5);
    for (int i = 0; i < 7; i++)
    {
      localGregorianCalendar.set(5, 4 + getDisplayedCalendar().getFirstDayOfWeek() + i);
      localArrayList.add(this.simpleDateFormat.format(localGregorianCalendar.getTime()));
    }
    return localArrayList;
  }
  
  protected List<Integer> getWeeknumbers()
  {
    ArrayList localArrayList = new ArrayList();
    Calendar localCalendar = (Calendar)getDisplayedCalendar().clone();
    for (int i = 0; i <= 5; i++)
    {
      localArrayList.add(Integer.valueOf(localCalendar.get(3)));
      localCalendar.add(5, 7);
    }
    return localArrayList;
  }
  
  protected List<String> getMonthLabels()
  {
    ArrayList localArrayList = new ArrayList();
    this.simpleDateFormat.applyPattern("MMMM");
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(2009, 0, 1);
    for (int i = 0; i < 12; i++)
    {
      localGregorianCalendar.set(2, i);
      localArrayList.add(this.simpleDateFormat.format(localGregorianCalendar.getTime()));
    }
    return localArrayList;
  }
  
  protected boolean isWeekday(int paramInt1, int paramInt2)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(2009, 6, 4 + getDisplayedCalendar().getFirstDayOfWeek());
    localGregorianCalendar.add(5, paramInt1);
    int i = localGregorianCalendar.get(7);
    return i == paramInt2;
  }
  
  protected boolean isWeekdayWeekend(int paramInt)
  {
    return (isWeekday(paramInt, 7)) || (isWeekday(paramInt, 1));
  }
  
  protected int determineFirstOfMonthDayOfWeek()
  {
    int i = getDisplayedCalendar().getFirstDayOfWeek();
    int j = getDisplayedCalendar().get(7) - i;
    if (j < 0) {
      j += 7;
    }
    return j;
  }
  
  protected int determineDaysInMonth()
  {
    Calendar localCalendar = (Calendar)getDisplayedCalendar().clone();
    localCalendar.add(2, 1);
    localCalendar.set(5, 1);
    localCalendar.add(5, -1);
    return localCalendar.get(5);
  }
  
  protected boolean isToday(Calendar paramCalendar)
  {
    int i = paramCalendar.get(1);
    int j = paramCalendar.get(2);
    int k = paramCalendar.get(5);
    return (i == this.iTodayYear) && (j == this.iTodayMonth) && (k == this.iTodayDay);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/CalendarPickerMonthlySkinAbstract.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */