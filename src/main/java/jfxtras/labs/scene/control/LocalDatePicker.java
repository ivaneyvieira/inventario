package jfxtras.labs.scene.control;

import java.net.URL;
import java.util.Locale;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javax.time.calendar.LocalDate;

public class LocalDatePicker
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = LocalDatePicker.class.getSimpleName();
  private final ObjectProperty<LocalDate> localDateObjectProperty = new SimpleObjectProperty(this, "localDate");
  private final ObservableList<LocalDate> localDates = FXCollections.observableArrayList();
  private volatile ObjectProperty<Locale> iLocaleObjectProperty = new SimpleObjectProperty(this, "locale", Locale.getDefault());
  private final SimpleObjectProperty<Mode> modeObjectProperty = new SimpleObjectProperty(this, "mode", Mode.SINGLE)
  {
    public void invalidated()
    {
      super.invalidated();
      while ((LocalDatePicker.this.modeObjectProperty.getValue() == Mode.SINGLE) && (LocalDatePicker.this.localDates().size() > 1)) {
        LocalDatePicker.this.localDates().remove(LocalDatePicker.this.localDates().size() - 1);
      }
    }
    
    public void set(Mode paramAnonymousMode)
    {
      if (paramAnonymousMode == null) {
        throw new NullPointerException("Null not allowed");
      }
      super.set(paramAnonymousMode);
    }
  };
  
  public LocalDatePicker()
  {
    construct();
  }
  
  public LocalDatePicker(LocalDate paramLocalDate)
  {
    construct();
    setLocalDate(paramLocalDate);
  }
  
  private void construct()
  {
    getStyleClass().add(DEFAULT_STYLE_CLASS);
    constructLocalDate();
    constructLocalDates();
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + DEFAULT_STYLE_CLASS + ".css").toString();
  }
  
  public final ObjectProperty<LocalDate> localDateProperty()
  {
    return this.localDateObjectProperty;
  }
  
  public final LocalDate getLocalDate()
  {
    return (LocalDate)this.localDateObjectProperty.getValue();
  }
  
  public final void setLocalDate(LocalDate paramLocalDate)
  {
    this.localDateObjectProperty.setValue(paramLocalDate);
  }
  
  public LocalDatePicker withLocalDate(LocalDate paramLocalDate)
  {
    setLocalDate(paramLocalDate);
    return this;
  }
  
  private void constructLocalDate()
  {
    localDateProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends LocalDate> paramAnonymousObservableValue, LocalDate paramAnonymousLocalDate1, LocalDate paramAnonymousLocalDate2)
      {
        if ((paramAnonymousLocalDate1 != null) && (paramAnonymousLocalDate2 == null)) {
          LocalDatePicker.this.localDates().remove(paramAnonymousLocalDate1);
        }
        if ((paramAnonymousLocalDate2 != null) && (!LocalDatePicker.this.localDates().contains(paramAnonymousLocalDate2))) {
          LocalDatePicker.this.localDates().add(paramAnonymousLocalDate2);
        }
      }
    });
  }
  
  public final ObservableList<LocalDate> localDates()
  {
    return this.localDates;
  }
  
  private void constructLocalDates()
  {
    localDates().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends LocalDate> paramAnonymousChange)
      {
        if (LocalDatePicker.this.localDates.size() == 0) {
          LocalDatePicker.this.setLocalDate(null);
        } else {
          LocalDatePicker.this.setLocalDate((LocalDate)LocalDatePicker.this.localDates.get(LocalDatePicker.this.localDates.size() - 1));
        }
      }
    });
  }
  
  public ObjectProperty<Locale> localeProperty()
  {
    return this.iLocaleObjectProperty;
  }
  
  public final Locale getLocale()
  {
    return (Locale)this.iLocaleObjectProperty.getValue();
  }
  
  public final void setLocale(Locale paramLocale)
  {
    this.iLocaleObjectProperty.setValue(paramLocale);
  }
  
  public LocalDatePicker withLocale(Locale paramLocale)
  {
    setLocale(paramLocale);
    return this;
  }
  
  public final ObjectProperty<Mode> modeProperty()
  {
    return this.modeObjectProperty;
  }
  
  public final Mode getMode()
  {
    return (Mode)this.modeObjectProperty.getValue();
  }
  
  public final void setMode(Mode paramMode)
  {
    this.modeObjectProperty.setValue(paramMode);
  }
  
  public LocalDatePicker withMode(Mode paramMode)
  {
    setMode(paramMode);
    return this;
  }
  
  public static enum Mode
  {
    SINGLE,  MULTIPLE,  RANGE;
    
    private Mode() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/LocalDatePicker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */