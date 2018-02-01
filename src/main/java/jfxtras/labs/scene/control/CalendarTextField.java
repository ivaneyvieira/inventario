package jfxtras.labs.scene.control;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;

public class CalendarTextField
  extends Control
{
  private final ObjectProperty<Calendar> valueObjectProperty = new SimpleObjectProperty(this, "value", null);
  private final DateFormat dateFormat = SimpleDateFormat.getDateInstance();
  private final DateFormat dateTimeFormat = SimpleDateFormat.getDateTimeInstance();
  private final ObjectProperty<DateFormat> dateFormatObjectProperty = new SimpleObjectProperty(this, "dateFormat", this.dateFormat);
  private final ObjectProperty<Locale> localeObjectProperty = new SimpleObjectProperty(Locale.getDefault());
  private final ObjectProperty<String> promptTextObjectProperty = new SimpleObjectProperty(this, "promptText", null);
  private volatile ObjectProperty<Boolean> showTimeObjectProperty = new SimpleObjectProperty(this, "showTime", Boolean.valueOf(false));
  
  public CalendarTextField()
  {
    construct();
  }
  
  private void construct()
  {
    getStyleClass().add(getClass().getSimpleName());
    setFocusTraversable(false);
    constructShowTimeProperty();
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + getClass().getSimpleName() + ".css").toString();
  }
  
  public ObjectProperty<Calendar> valueProperty()
  {
    return this.valueObjectProperty;
  }
  
  public Calendar getValue()
  {
    return (Calendar)this.valueObjectProperty.getValue();
  }
  
  public void setValue(Calendar paramCalendar)
  {
    this.valueObjectProperty.setValue(paramCalendar);
  }
  
  public CalendarTextField withValue(Calendar paramCalendar)
  {
    setValue(paramCalendar);
    return this;
  }
  
  public ObjectProperty<DateFormat> dateFormatProperty()
  {
    return this.dateFormatObjectProperty;
  }
  
  public DateFormat getDateFormat()
  {
    return (DateFormat)this.dateFormatObjectProperty.getValue();
  }
  
  public void setDateFormat(DateFormat paramDateFormat)
  {
    this.dateFormatObjectProperty.setValue(paramDateFormat);
  }
  
  public CalendarTextField withDateFormat(DateFormat paramDateFormat)
  {
    setDateFormat(paramDateFormat);
    return this;
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
  
  public CalendarTextField withLocale(Locale paramLocale)
  {
    setLocale(paramLocale);
    return this;
  }
  
  public ObjectProperty<String> promptTextProperty()
  {
    return this.promptTextObjectProperty;
  }
  
  public String getPromptText()
  {
    return (String)this.promptTextObjectProperty.get();
  }
  
  public void setPromptText(String paramString)
  {
    this.promptTextObjectProperty.set(paramString);
  }
  
  public CalendarTextField withPromptText(String paramString)
  {
    setPromptText(paramString);
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
  
  public CalendarTextField withShowTime(Boolean paramBoolean)
  {
    setShowTime(paramBoolean);
    return this;
  }
  
  private void constructShowTimeProperty()
  {
    this.showTimeObjectProperty.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        if ((paramAnonymousBoolean2.booleanValue() == true) && (CalendarTextField.this.getDateFormat() == CalendarTextField.this.dateFormat)) {
          CalendarTextField.this.setDateFormat(CalendarTextField.this.dateTimeFormat);
        }
        if ((!paramAnonymousBoolean2.booleanValue()) && (CalendarTextField.this.getDateFormat() == CalendarTextField.this.dateTimeFormat)) {
          CalendarTextField.this.setDateFormat(CalendarTextField.this.dateFormat);
        }
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/CalendarTextField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */