package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javax.time.calendar.DayOfWeek;
import javax.time.calendar.LocalDate;
import javax.time.calendar.MonthOfYear;
import javax.time.calendar.format.DateTimeFormatter;
import javax.time.calendar.format.DateTimeFormatterBuilder;
import javax.time.calendar.format.DateTimeFormatters;
import jfxtras.labs.internal.scene.control.behavior.LocalDatePickerBehavior;
import jfxtras.labs.scene.control.ListSpinner;
import jfxtras.labs.scene.control.ListSpinner.CycleEvent;
import jfxtras.labs.scene.control.ListSpinnerIntegerList;
import jfxtras.labs.scene.control.LocalDatePicker;
import jfxtras.labs.scene.control.LocalDatePicker.Mode;

public class LocalDatePickerControlSkin
  extends SkinBase<LocalDatePicker, LocalDatePickerBehavior>
{
  private volatile ObjectProperty<LocalDate> displayedLocalDateObjectProperty = new SimpleObjectProperty(this, "displayedLocalDate");
  private ListSpinner<String> monthXSpinner = null;
  private ListSpinner<Integer> yearXSpinner = null;
  private final List<Label> weekdayLabels = new ArrayList();
  private final List<Label> weeknumberLabels = new ArrayList();
  private final List<ToggleButton> dayButtons = new ArrayList();
  private final Map<BooleanProperty, ToggleButton> booleanPropertyToDayToggleButtonMap = new WeakHashMap();
  private final ChangeListener<Boolean> toggleButtonSelectedPropertyChangeListener = new ChangeListener()
  {
    public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
    {
      LocalDatePickerControlSkin.this.refreshDayButtonToggleState();
    }
  };
  private final EventHandler<MouseEvent> toggleButtonMouseReleasedPropertyEventHandler = new EventHandler()
  {
    public void handle(MouseEvent paramAnonymousMouseEvent)
    {
      ToggleButton localToggleButton = (ToggleButton)paramAnonymousMouseEvent.getSource();
      LocalDatePickerControlSkin.this.toggle(localToggleButton, paramAnonymousMouseEvent.isShiftDown());
    }
  };
  private final EventHandler<KeyEvent> toggleButtonKeyReleasedPropertyEventHandler = new EventHandler()
  {
    public void handle(KeyEvent paramAnonymousKeyEvent)
    {
      ToggleButton localToggleButton = (ToggleButton)paramAnonymousKeyEvent.getSource();
      if (" ".equals(paramAnonymousKeyEvent.getText())) {
        LocalDatePickerControlSkin.this.toggle(localToggleButton, paramAnonymousKeyEvent.isShiftDown());
      }
    }
  };
  private final EventHandler<MouseEvent> weekdayLabelMouseClickedPropertyEventHandler = new EventHandler()
  {
    public void handle(MouseEvent paramAnonymousMouseEvent)
    {
      if (((LocalDatePicker)LocalDatePickerControlSkin.this.getSkinnable()).getMode() == Mode.SINGLE) {
        return;
      }
      if (((LocalDatePicker)LocalDatePickerControlSkin.this.getSkinnable()).getMode() == Mode.RANGE) {
        return;
      }
      int i = ((Integer)((Label)paramAnonymousMouseEvent.getSource()).getUserData()).intValue();
      for (int j = 0; j < 6; j++)
      {
        int k = j * 7 + i;
        ToggleButton localToggleButton = (ToggleButton)LocalDatePickerControlSkin.this.dayButtons.get(k);
        if (localToggleButton.isVisible() == true) {
          LocalDatePickerControlSkin.this.toggle(localToggleButton, false);
        }
      }
    }
  };
  private final EventHandler<MouseEvent> weeknumerLabelMouseClickedPropertyEventHandler = new EventHandler()
  {
    public void handle(MouseEvent paramAnonymousMouseEvent)
    {
      if (((LocalDatePicker)LocalDatePickerControlSkin.this.getSkinnable()).getMode() == Mode.SINGLE) {
        return;
      }
      if (((LocalDatePicker)LocalDatePickerControlSkin.this.getSkinnable()).getMode() == Mode.RANGE) {
        ((LocalDatePicker)LocalDatePickerControlSkin.this.getSkinnable()).localDates().clear();
      }
      int i = ((Integer)((Label)paramAnonymousMouseEvent.getSource()).getUserData()).intValue();
      for (int j = i * 7; j < i * 7 + 7; j++)
      {
        ToggleButton localToggleButton = (ToggleButton)LocalDatePickerControlSkin.this.dayButtons.get(j);
        if (((LocalDatePicker)LocalDatePickerControlSkin.this.getSkinnable()).getMode() == Mode.RANGE) {
          ((LocalDatePicker)LocalDatePickerControlSkin.this.getSkinnable()).localDates().add(LocalDatePickerControlSkin.this.LocalDateForToggleButton(localToggleButton));
        } else {
          LocalDatePickerControlSkin.this.toggle(localToggleButton, false);
        }
      }
    }
  };
  private LocalDate iLastSelected = null;
  private final AtomicInteger iRefreshingSelection = new AtomicInteger(0);
  
  public LocalDatePickerControlSkin(LocalDatePicker paramLocalDatePicker)
  {
    super(paramLocalDatePicker, new LocalDatePickerBehavior(paramLocalDatePicker));
    construct();
  }
  
  private void construct()
  {
    createNodes();
    setDisplayedLocalDate(LocalDate.now());
    ((LocalDatePicker)getSkinnable()).localeProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        LocalDatePickerControlSkin.this.refreshLocale();
      }
    });
    refreshLocale();
    ((LocalDatePicker)getSkinnable()).localDateProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends LocalDate> paramAnonymousObservableValue, LocalDate paramAnonymousLocalDate1, LocalDate paramAnonymousLocalDate2)
      {
        if (paramAnonymousLocalDate2 != null) {
          LocalDatePickerControlSkin.this.setDisplayedLocalDate(paramAnonymousLocalDate2);
        }
      }
    });
    if (((LocalDatePicker)getSkinnable()).getLocalDate() != null) {
      setDisplayedLocalDate(((LocalDatePicker)getSkinnable()).getLocalDate());
    }
    ((LocalDatePicker)getSkinnable()).localDates().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends LocalDate> paramAnonymousChange)
      {
        LocalDatePickerControlSkin.this.refreshDayButtonToggleState();
      }
    });
    displayedLocalDate().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends LocalDate> paramAnonymousObservableValue, LocalDate paramAnonymousLocalDate1, LocalDate paramAnonymousLocalDate2)
      {
        LocalDatePickerControlSkin.this.refresh();
      }
    });
    refresh();
  }
  
  public LocalDate getDisplayedLocalDate()
  {
    return (LocalDate)this.displayedLocalDateObjectProperty.getValue();
  }
  
  public void setDisplayedLocalDate(LocalDate paramLocalDate)
  {
    if (paramLocalDate.getDayOfMonth() != 1) {
      paramLocalDate = LocalDate.of(paramLocalDate.getYear(), paramLocalDate.getMonthOfYear(), 1);
    }
    this.displayedLocalDateObjectProperty.setValue(paramLocalDate);
  }
  
  public LocalDatePickerControlSkin withDisplayedLocalDate(LocalDate paramLocalDate)
  {
    setDisplayedLocalDate(paramLocalDate);
    return this;
  }
  
  public ObjectProperty<LocalDate> displayedLocalDate()
  {
    return this.displayedLocalDateObjectProperty;
  }
  
  private void refreshLocale()
  {
    setDisplayedLocalDate(getDisplayedLocalDate());
  }
  
  private void createNodes()
  {
    GridPane localGridPane = new GridPane();
    localGridPane.setVgap(2.0D);
    localGridPane.setHgap(2.0D);
    ColumnConstraints localColumnConstraints1 = new ColumnConstraints();
    localColumnConstraints1.setHgrow(Priority.ALWAYS);
    ColumnConstraints localColumnConstraints2 = new ColumnConstraints();
    localColumnConstraints2.setHgrow(Priority.NEVER);
    localGridPane.getColumnConstraints().addAll(new ColumnConstraints[] { localColumnConstraints2, localColumnConstraints1, localColumnConstraints1, localColumnConstraints1, localColumnConstraints1, localColumnConstraints1, localColumnConstraints1, localColumnConstraints1 });
    List localList = getMonthLabels();
    this.monthXSpinner = new ListSpinner(localList).withIndex(Integer.valueOf(LocalDate.now().getMonthOfYear().getValue())).withCyclic(Boolean.TRUE);
    this.monthXSpinner.setOnCycle(new EventHandler()
    {
      public void handle(CycleEvent paramAnonymousCycleEvent)
      {
        if (paramAnonymousCycleEvent.cycledDown()) {
          LocalDatePickerControlSkin.this.yearXSpinner.increment();
        } else {
          LocalDatePickerControlSkin.this.yearXSpinner.decrement();
        }
      }
    });
    this.monthXSpinner.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2)
      {
        LocalDatePickerControlSkin.this.setDisplayedLocalDateFromSpinners();
      }
    });
    localGridPane.add(this.monthXSpinner, 0, 0, 5, 1);
    this.yearXSpinner = new ListSpinner(new ListSpinnerIntegerList()).withValue(Integer.valueOf(LocalDate.now().getYear()));
    this.yearXSpinner.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue paramAnonymousObservableValue, Integer paramAnonymousInteger1, Integer paramAnonymousInteger2)
      {
        LocalDatePickerControlSkin.this.setDisplayedLocalDateFromSpinners();
      }
    });
    localGridPane.add(this.yearXSpinner, 5, 0, 3, 1);
    Label localLabel = new Label("   ");
    localLabel.onMouseClickedProperty().set(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (paramAnonymousMouseEvent.getClickCount() < 1) {
          return;
        }
        LocalDatePickerControlSkin.this.setDisplayedLocalDateToToday();
      }
    });
    localGridPane.add(localLabel, 0, 1);
    Object localObject;
    for (int i = 0; i < 7; i++)
    {
      localObject = new Label("" + i);
      ((Label)localObject).setAlignment(Pos.CENTER);
      localGridPane.add((Node)localObject, i + 1, 1);
      ((Label)localObject).setUserData(Integer.valueOf(i));
      ((Label)localObject).onMouseClickedProperty().set(this.weekdayLabelMouseClickedPropertyEventHandler);
      this.weekdayLabels.add(localObject);
      ((Label)localObject).setAlignment(Pos.BASELINE_CENTER);
    }
    for (i = 0; i < 6; i++)
    {
      localObject = new Label("" + i);
      ((Label)localObject).getStyleClass().add("weeknumber");
      ((Label)localObject).setAlignment(Pos.BASELINE_RIGHT);
      this.weeknumberLabels.add(localObject);
      ((Label)localObject).setUserData(Integer.valueOf(i));
      ((Label)localObject).onMouseClickedProperty().set(this.weeknumerLabelMouseClickedPropertyEventHandler);
      localGridPane.add((Node)this.weeknumberLabels.get(i), 0, i + 2);
    }
    for (i = 0; i < 42; i++)
    {
      localObject = new ToggleButton("" + i);
      ((ToggleButton)localObject).getStyleClass().add("day");
      ((ToggleButton)localObject).selectedProperty().addListener(this.toggleButtonSelectedPropertyChangeListener);
      ((ToggleButton)localObject).onMouseReleasedProperty().set(this.toggleButtonMouseReleasedPropertyEventHandler);
      ((ToggleButton)localObject).onKeyReleasedProperty().set(this.toggleButtonKeyReleasedPropertyEventHandler);
      this.booleanPropertyToDayToggleButtonMap.put(((ToggleButton)localObject).selectedProperty(), localObject);
      localGridPane.add((Node)localObject, i % 7 + 1, i / 7 + 2);
      ((ToggleButton)localObject).setMaxWidth(Double.MAX_VALUE);
      ((ToggleButton)localObject).setAlignment(Pos.BASELINE_CENTER);
      this.dayButtons.add(localObject);
    }
    getStyleClass().add(getClass().getSimpleName());
    getChildren().add(localGridPane);
  }
  
  private LocalDate LocalDateForToggleButton(ToggleButton paramToggleButton)
  {
    int i = this.dayButtons.indexOf(paramToggleButton);
    int j = determineFirstOfMonthDayOfWeek();
    int k = i - j + 1;
    LocalDate localLocalDate = LocalDate.of(getDisplayedLocalDate().getYear(), getDisplayedLocalDate().getMonthOfYear().getValue(), k);
    return localLocalDate;
  }
  
  private void toggle(ToggleButton paramToggleButton, boolean paramBoolean)
  {
    LocalDate localLocalDate1 = LocalDateForToggleButton(paramToggleButton);
    ObservableList localObservableList = ((LocalDatePicker)getSkinnable()).localDates();
    int i = !localObservableList.contains(localLocalDate1) ? 1 : 0;
    if (i != 0)
    {
      localObservableList.add(localLocalDate1);
      while ((((LocalDatePicker)getSkinnable()).getMode() == Mode.SINGLE) && (localObservableList.size() > 1)) {
        localObservableList.remove(0);
      }
      while ((((LocalDatePicker)getSkinnable()).getMode() == Mode.SINGLE) && (localObservableList.size() > 1)) {
        localObservableList.remove(0);
      }
      while ((((LocalDatePicker)getSkinnable()).getMode() == Mode.RANGE) && (!paramBoolean) && (localObservableList.size() > 1)) {
        localObservableList.remove(0);
      }
      if (((((LocalDatePicker)getSkinnable()).getMode() == Mode.MULTIPLE) || (((LocalDatePicker)getSkinnable()).getMode() == Mode.RANGE)) && (paramBoolean == true) && (this.iLastSelected != null))
      {
        LocalDate localLocalDate2 = this.iLastSelected;
        int j = localLocalDate2.isAfter(localLocalDate1) ? -1 : 1;
        LocalDate localLocalDate3 = localLocalDate2;
        for (localLocalDate3 = localLocalDate3.plusDays(j); !localLocalDate3.equals(localLocalDate1); localLocalDate3 = localLocalDate3.plusDays(j)) {
          localObservableList.add(localLocalDate3);
        }
        localObservableList.remove(localLocalDate1);
        localObservableList.add(localLocalDate1);
      }
      this.iLastSelected = localLocalDate1;
    }
    else
    {
      localObservableList.remove(localLocalDate1);
      this.iLastSelected = null;
    }
    refreshDayButtonToggleState();
  }
  
  private void setDisplayedLocalDateFromSpinners()
  {
    int i = ((Integer)this.yearXSpinner.getValue()).intValue();
    int j = this.monthXSpinner.getIndex().intValue();
    LocalDate localLocalDate = LocalDate.of(i, j, 1);
    setDisplayedLocalDate(localLocalDate);
  }
  
  private void setDisplayedLocalDateToToday()
  {
    LocalDate localLocalDate = LocalDate.now();
    setDisplayedLocalDate(localLocalDate);
  }
  
  private void refresh()
  {
    refreshSpinner();
    refreshWeekdayLabels();
    refreshWeeknumberLabels();
    refreshDayButtonsVisibilityAndLabel();
    refreshDayButtonToggleState();
  }
  
  private void refreshSpinner()
  {
    LocalDate localLocalDate = getDisplayedLocalDate();
    List localList = getMonthLabels();
    String str = (String)localList.get(localLocalDate.getMonthOfYear().getValue());
    this.monthXSpinner.setValue(str);
    this.yearXSpinner.setValue(Integer.valueOf(localLocalDate.getYear()));
  }
  
  private void refreshWeekdayLabels()
  {
    List localList = getWeekdayLabels();
    for (int i = 0; i < this.weekdayLabels.size(); i++)
    {
      Label localLabel = (Label)this.weekdayLabels.get(i);
      localLabel.setText((String)localList.get(i));
      localLabel.getStyleClass().remove("weekend");
      localLabel.getStyleClass().remove("weekday");
      localLabel.getStyleClass().add(isWeekend(i) ? "weekend" : "weekday");
    }
  }
  
  private void refreshWeeknumberLabels()
  {
    List localList = getWeeknumbers();
    for (int i = 0; i < localList.size(); i++) {
      ((Label)this.weeknumberLabels.get(i)).setText((((Integer)localList.get(i)).intValue() < 10 ? "0" : "") + ((Integer)localList.get(i)).toString());
    }
  }
  
  private void refreshDayButtonsVisibilityAndLabel()
  {
    int i = determineFirstOfMonthDayOfWeek();
    for (int j = 0; j < i; j++) {
      ((ToggleButton)this.dayButtons.get(j)).setVisible(false);
    }
    for (j = 1; j < 6; j++) {
      ((Label)this.weeknumberLabels.get(j)).setVisible(false);
    }
    j = determineDaysInMonth();
    LocalDate localLocalDate1 = getDisplayedLocalDate();
    for (int k = 1; k <= j; k++)
    {
      LocalDate localLocalDate2 = LocalDate.of(localLocalDate1.getYear(), localLocalDate1.getMonthOfYear(), k);
      int m = i + k - 1;
      ToggleButton localToggleButton = (ToggleButton)this.dayButtons.get(m);
      localToggleButton.setVisible(true);
      localToggleButton.setText("" + k);
      localToggleButton.getStyleClass().remove("weekend");
      localToggleButton.getStyleClass().remove("weekday");
      localToggleButton.getStyleClass().add(isWeekend(m % 7) ? "weekend" : "weekday");
      ((Label)this.weeknumberLabels.get(m / 7)).setVisible(true);
      if (isToday(localLocalDate2)) {
        localToggleButton.getStyleClass().add("today");
      } else {
        localToggleButton.getStyleClass().remove("today");
      }
    }
    for (k = i + j; k < 42; k++) {
      ((ToggleButton)this.dayButtons.get(k)).setVisible(false);
    }
  }
  
  private void refreshDayButtonToggleState()
  {
    this.iRefreshingSelection.addAndGet(1);
    try
    {
      int i = determineFirstOfMonthDayOfWeek();
      ObservableList localObservableList = ((LocalDatePicker)getSkinnable()).localDates();
      int j = determineDaysInMonth();
      LocalDate localLocalDate1 = getDisplayedLocalDate();
      for (int k = 1; k <= j; k++)
      {
        LocalDate localLocalDate2 = LocalDate.of(localLocalDate1.getYear(), localLocalDate1.getMonthOfYear(), k);
        int m = i + k - 1;
        boolean bool = localObservableList.contains(localLocalDate2);
        ((ToggleButton)this.dayButtons.get(m)).setSelected(bool);
      }
    }
    finally
    {
      this.iRefreshingSelection.addAndGet(-1);
    }
  }
  
  protected List<String> getWeekdayLabels()
  {
    ArrayList localArrayList = new ArrayList();
    int i = DayOfWeek.firstDayOfWeekFor(((LocalDatePicker)getSkinnable()).getLocale()).getValue();
    DateTimeFormatter localDateTimeFormatter = new DateTimeFormatterBuilder().append(DateTimeFormatters.pattern("E")).toFormatter();
    for (int j = 0; j < 7; j++)
    {
      LocalDate localLocalDate = LocalDate.of(2009, 7, 12 + i + j);
      localArrayList.add(localDateTimeFormatter.withLocale(((LocalDatePicker)getSkinnable()).getLocale()).print(localLocalDate));
    }
    return localArrayList;
  }
  
  protected List<Integer> getWeeknumbers()
  {
    ArrayList localArrayList = new ArrayList();
    DateTimeFormatter localDateTimeFormatter = new DateTimeFormatterBuilder().append(DateTimeFormatters.pattern("ww")).toFormatter();
    LocalDate localLocalDate = getDisplayedLocalDate();
    for (int i = 0; i <= 5; i++)
    {
      localArrayList.add(Integer.valueOf(localDateTimeFormatter.withLocale(((LocalDatePicker)getSkinnable()).getLocale()).print(localLocalDate)));
      localLocalDate = localLocalDate.plusDays(7L);
    }
    return localArrayList;
  }
  
  protected List<String> getMonthLabels()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("dummy");
    DateTimeFormatter localDateTimeFormatter = new DateTimeFormatterBuilder().append(DateTimeFormatters.pattern("MMMM")).toFormatter();
    for (int i = 0; i < 12; i++)
    {
      LocalDate localLocalDate = LocalDate.of(2009, i + 1, 1);
      localArrayList.add(localDateTimeFormatter.withLocale(((LocalDatePicker)getSkinnable()).getLocale()).print(localLocalDate));
    }
    return localArrayList;
  }
  
  protected boolean isWeekend(int paramInt)
  {
    int i = DayOfWeek.firstDayOfWeekFor(((LocalDatePicker)getSkinnable()).getLocale()).getValue();
    int j = (i + paramInt - 1) % 7;
    return (j == 5) || (j == 6);
  }
  
  protected int determineFirstOfMonthDayOfWeek()
  {
    int i = DayOfWeek.firstDayOfWeekFor(((LocalDatePicker)getSkinnable()).getLocale()).getValue();
    int j = getDisplayedLocalDate().getDayOfWeek().getValue() - i;
    if (j < 0) {
      j += 7;
    }
    return j;
  }
  
  protected int determineDaysInMonth()
  {
    LocalDate localLocalDate = getDisplayedLocalDate();
    localLocalDate = localLocalDate.plusMonths(1L);
    localLocalDate = localLocalDate.plusDays(-1L);
    int i = localLocalDate.getDayOfMonth();
    return i;
  }
  
  protected boolean isToday(LocalDate paramLocalDate)
  {
    LocalDate localLocalDate = LocalDate.now();
    return (localLocalDate.getYear() == paramLocalDate.getYear()) && (localLocalDate.getMonthOfYear().getValue() == paramLocalDate.getMonthOfYear().getValue()) && (localLocalDate.getDayOfMonth() == paramLocalDate.getDayOfMonth());
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/LocalDatePickerControlSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */