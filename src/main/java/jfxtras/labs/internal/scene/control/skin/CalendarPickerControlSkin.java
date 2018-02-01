package jfxtras.labs.internal.scene.control.skin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
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
import jfxtras.labs.scene.control.CalendarPicker;
import jfxtras.labs.scene.control.CalendarPicker.Mode;
import jfxtras.labs.scene.control.CalendarTimePicker;
import jfxtras.labs.scene.control.ListSpinner;
import jfxtras.labs.scene.control.ListSpinner.CycleEvent;
import jfxtras.labs.scene.control.ListSpinnerIntegerList;

public class CalendarPickerControlSkin
  extends CalendarPickerMonthlySkinAbstract<CalendarPickerControlSkin>
{
  private ListSpinner<String> monthListSpinner = null;
  private ListSpinner<Integer> yearListSpinner = null;
  private final List<Label> weekdayLabels = new ArrayList();
  private final List<Label> weeknumberLabels = new ArrayList();
  private final List<ToggleButton> dayButtons = new ArrayList();
  private final CalendarTimePicker timePicker = new CalendarTimePicker();
  private final Map<BooleanProperty, ToggleButton> booleanPropertyToDayToggleButtonMap = new WeakHashMap();
  private final ChangeListener<Boolean> toggleButtonSelectedPropertyChangeListener = new ChangeListener()
  {
    public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
    {
      CalendarPickerControlSkin.this.refreshDayButtonToggleState();
    }
  };
  private final EventHandler<MouseEvent> toggleButtonMouseReleasedPropertyEventHandler = new EventHandler()
  {
    public void handle(MouseEvent paramAnonymousMouseEvent)
    {
      ToggleButton localToggleButton = (ToggleButton)paramAnonymousMouseEvent.getSource();
      CalendarPickerControlSkin.this.toggle(localToggleButton, paramAnonymousMouseEvent.isShiftDown());
    }
  };
  private final EventHandler<KeyEvent> toggleButtonKeyReleasedPropertyEventHandler = new EventHandler()
  {
    public void handle(KeyEvent paramAnonymousKeyEvent)
    {
      ToggleButton localToggleButton = (ToggleButton)paramAnonymousKeyEvent.getSource();
      if (" ".equals(paramAnonymousKeyEvent.getText())) {
        CalendarPickerControlSkin.this.toggle(localToggleButton, paramAnonymousKeyEvent.isShiftDown());
      }
    }
  };
  private final EventHandler<MouseEvent> weekdayLabelMouseClickedPropertyEventHandler = new EventHandler()
  {
    public void handle(MouseEvent paramAnonymousMouseEvent)
    {
      if (((CalendarPicker)CalendarPickerControlSkin.this.getSkinnable()).getMode() == Mode.SINGLE) {
        return;
      }
      if (((CalendarPicker)CalendarPickerControlSkin.this.getSkinnable()).getMode() == Mode.RANGE) {
        return;
      }
      int i = ((Integer)((Label)paramAnonymousMouseEvent.getSource()).getUserData()).intValue();
      for (int j = 0; j < 6; j++)
      {
        int k = j * 7 + i;
        ToggleButton localToggleButton = (ToggleButton)CalendarPickerControlSkin.this.dayButtons.get(k);
        if (localToggleButton.isVisible() == true) {
          CalendarPickerControlSkin.this.toggle(localToggleButton, false);
        }
      }
    }
  };
  private final EventHandler<MouseEvent> weeknumerLabelMouseClickedPropertyEventHandler = new EventHandler()
  {
    public void handle(MouseEvent paramAnonymousMouseEvent)
    {
      if (((CalendarPicker)CalendarPickerControlSkin.this.getSkinnable()).getMode() == Mode.SINGLE) {
        return;
      }
      if (((CalendarPicker)CalendarPickerControlSkin.this.getSkinnable()).getMode() == Mode.RANGE) {
        ((CalendarPicker)CalendarPickerControlSkin.this.getSkinnable()).calendars().clear();
      }
      int i = ((Integer)((Label)paramAnonymousMouseEvent.getSource()).getUserData()).intValue();
      for (int j = i * 7; j < i * 7 + 7; j++)
      {
        ToggleButton localToggleButton = (ToggleButton)CalendarPickerControlSkin.this.dayButtons.get(j);
        if (((CalendarPicker)CalendarPickerControlSkin.this.getSkinnable()).getMode() == Mode.RANGE) {
          ((CalendarPicker)CalendarPickerControlSkin.this.getSkinnable()).calendars().add(CalendarPickerControlSkin.this.calendarForToggleButton(localToggleButton));
        } else {
          CalendarPickerControlSkin.this.toggle(localToggleButton, false);
        }
      }
    }
  };
  private Calendar iLastSelected = null;
  private final AtomicInteger iRefreshingSelection = new AtomicInteger(0);
  
  public CalendarPickerControlSkin(CalendarPicker paramCalendarPicker)
  {
    super(paramCalendarPicker);
    construct();
  }
  
  private void construct()
  {
    createNodes();
    ((CalendarPicker)getSkinnable()).calendarProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        if (((CalendarPicker)CalendarPickerControlSkin.this.getSkinnable()).getCalendar() != null) {
          CalendarPickerControlSkin.this.setDisplayedCalendar(((CalendarPicker)CalendarPickerControlSkin.this.getSkinnable()).getCalendar());
        }
      }
    });
    if (((CalendarPicker)getSkinnable()).getCalendar() != null) {
      setDisplayedCalendar(((CalendarPicker)getSkinnable()).getCalendar());
    }
    ((CalendarPicker)getSkinnable()).calendars().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends Calendar> paramAnonymousChange)
      {
        CalendarPickerControlSkin.this.refreshDayButtonToggleState();
      }
    });
    displayedCalendar().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        CalendarPickerControlSkin.this.refresh();
      }
    });
    refresh();
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
    this.monthListSpinner = new ListSpinner(localList).withIndex(Integer.valueOf(Calendar.getInstance().get(2))).withCyclic(Boolean.TRUE);
    this.monthListSpinner.setOnCycle(new EventHandler()
    {
      public void handle(CycleEvent paramAnonymousCycleEvent)
      {
        if (paramAnonymousCycleEvent.cycledDown()) {
          CalendarPickerControlSkin.this.yearListSpinner.increment();
        } else {
          CalendarPickerControlSkin.this.yearListSpinner.decrement();
        }
      }
    });
    this.monthListSpinner.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2)
      {
        CalendarPickerControlSkin.this.setDisplayedCalendarFromSpinners();
      }
    });
    localGridPane.add(this.monthListSpinner, 1, 0, 4, 1);
    this.yearListSpinner = new ListSpinner(new ListSpinnerIntegerList()).withValue(Integer.valueOf(Calendar.getInstance().get(1)));
    this.yearListSpinner.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue paramAnonymousObservableValue, Integer paramAnonymousInteger1, Integer paramAnonymousInteger2)
      {
        CalendarPickerControlSkin.this.setDisplayedCalendarFromSpinners();
      }
    });
    localGridPane.add(this.yearListSpinner, 5, 0, 3, 1);
    Label localLabel = new Label("   ");
    localLabel.onMouseClickedProperty().set(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (paramAnonymousMouseEvent.getClickCount() < 1) {
          return;
        }
        CalendarPickerControlSkin.this.setDisplayedCalendarToToday();
      }
    });
    localGridPane.add(localLabel, 0, 1);
    Object localObject;
    for (int i = 0; i < 7; i++)
    {
      localObject = new Label("" + i);
      ((Label)localObject).getStyleClass().add("weekday-label");
      ((Label)localObject).setMaxWidth(2.147483647E9D);
      localGridPane.add((Node)localObject, i + 1, 1);
      ((Label)localObject).setUserData(Integer.valueOf(i));
      ((Label)localObject).onMouseClickedProperty().set(this.weekdayLabelMouseClickedPropertyEventHandler);
      this.weekdayLabels.add(localObject);
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
      ((ToggleButton)localObject).getStyleClass().add("day-button");
      ((ToggleButton)localObject).selectedProperty().addListener(this.toggleButtonSelectedPropertyChangeListener);
      ((ToggleButton)localObject).onMouseReleasedProperty().set(this.toggleButtonMouseReleasedPropertyEventHandler);
      ((ToggleButton)localObject).onKeyReleasedProperty().set(this.toggleButtonKeyReleasedPropertyEventHandler);
      this.booleanPropertyToDayToggleButtonMap.put(((ToggleButton)localObject).selectedProperty(), localObject);
      localGridPane.add((Node)localObject, i % 7 + 1, i / 7 + 2);
      ((ToggleButton)localObject).setMaxWidth(Double.MAX_VALUE);
      ((ToggleButton)localObject).setAlignment(Pos.BASELINE_CENTER);
      this.dayButtons.add(localObject);
    }
    if ((((CalendarPicker)getSkinnable()).getMode().equals(Mode.SINGLE)) && (((Boolean)((CalendarPicker)getSkinnable()).showTimeProperty().get()).booleanValue() == true))
    {
      localGridPane.add(this.timePicker, 1, 8, 7, 1);
      Bindings.bindBidirectional(this.timePicker.calendarProperty(), ((CalendarPicker)getSkinnable()).calendarProperty());
    }
    getStyleClass().add(getClass().getSimpleName());
    getChildren().add(localGridPane);
  }
  
  private Calendar calendarForToggleButton(ToggleButton paramToggleButton)
  {
    int i = this.dayButtons.indexOf(paramToggleButton);
    int j = determineFirstOfMonthDayOfWeek();
    int k = i - j + 1;
    Calendar localCalendar = (Calendar)getDisplayedCalendar().clone();
    localCalendar.set(1, getDisplayedCalendar().get(1));
    localCalendar.set(2, getDisplayedCalendar().get(2));
    localCalendar.set(5, k);
    if ((this.timePicker.isVisible()) && (this.timePicker.getCalendar() != null))
    {
      localCalendar.set(11, this.timePicker.getCalendar().get(11));
      localCalendar.set(12, this.timePicker.getCalendar().get(12));
    }
    return localCalendar;
  }
  
  private void toggle(ToggleButton paramToggleButton, boolean paramBoolean)
  {
    Object localObject1 = calendarForToggleButton(paramToggleButton);
    ObservableList localObservableList = ((CalendarPicker)getSkinnable()).calendars();
    int i = !contains(localObservableList, (Calendar)localObject1) ? 1 : 0;
    if (i != 0)
    {
      localObservableList.add(localObject1);
      while ((((CalendarPicker)getSkinnable()).getMode() == Mode.SINGLE) && (localObservableList.size() > 1)) {
        localObservableList.remove(0);
      }
      while ((((CalendarPicker)getSkinnable()).getMode() == Mode.SINGLE) && (localObservableList.size() > 1)) {
        localObservableList.remove(0);
      }
      while ((((CalendarPicker)getSkinnable()).getMode() == Mode.RANGE) && (!paramBoolean) && (localObservableList.size() > 1)) {
        localObservableList.remove(0);
      }
      if (((((CalendarPicker)getSkinnable()).getMode() == Mode.MULTIPLE) || (((CalendarPicker)getSkinnable()).getMode() == Mode.RANGE)) && (paramBoolean == true) && (this.iLastSelected != null))
      {
        Object localObject2 = this.iLastSelected;
        if (((Calendar)localObject2).after(localObject1))
        {
          localObject3 = localObject2;
          localObject2 = localObject1;
          localObject1 = localObject3;
        }
        Object localObject3 = (Calendar)((Calendar)localObject2).clone();
        ((Calendar)localObject3).add(5, 1);
        while (((Calendar)localObject3).before(localObject1))
        {
          localObservableList.add((Calendar)((Calendar)localObject3).clone());
          ((Calendar)localObject3).add(5, 1);
        }
        Collections.sort(localObservableList);
      }
      this.iLastSelected = ((Calendar)((Calendar)localObject1).clone());
    }
    else
    {
      localObservableList.remove(localObject1);
      this.iLastSelected = null;
    }
    refreshDayButtonToggleState();
  }
  
  private void setDisplayedCalendarFromSpinners()
  {
    int i = ((Integer)this.yearListSpinner.getValue()).intValue();
    int j = this.monthListSpinner.getIndex().intValue();
    Calendar localCalendar = (Calendar)getDisplayedCalendar().clone();
    localCalendar.set(1, i);
    localCalendar.set(2, j);
    setDisplayedCalendar(localCalendar);
  }
  
  private void setDisplayedCalendarToToday()
  {
    Calendar localCalendar1 = Calendar.getInstance();
    Calendar localCalendar2 = (Calendar)getDisplayedCalendar().clone();
    localCalendar2.set(1, localCalendar1.get(1));
    localCalendar2.set(2, localCalendar1.get(2));
    setDisplayedCalendar(localCalendar2);
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
    Calendar localCalendar = getDisplayedCalendar();
    List localList = getMonthLabels();
    String str = (String)localList.get(localCalendar.get(2));
    this.monthListSpinner.setValue(str);
    this.yearListSpinner.setValue(Integer.valueOf(localCalendar.get(1)));
  }
  
  private void refreshWeekdayLabels()
  {
    List localList = getWeekdayLabels();
    for (int i = 0; i < this.weekdayLabels.size(); i++)
    {
      Label localLabel = (Label)this.weekdayLabels.get(i);
      localLabel.setText((String)localList.get(i));
      localLabel.getStyleClass().removeAll(new String[] { "weekend", "weekday" });
      localLabel.getStyleClass().add(isWeekdayWeekend(i) ? "weekend" : "weekday");
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
    Calendar localCalendar = (Calendar)getDisplayedCalendar().clone();
    for (int k = 1; k <= j; k++)
    {
      localCalendar.set(5, k);
      int m = i + k - 1;
      ToggleButton localToggleButton = (ToggleButton)this.dayButtons.get(m);
      localToggleButton.setVisible(true);
      localToggleButton.setText("" + k);
      localToggleButton.getStyleClass().remove("weekend");
      localToggleButton.getStyleClass().remove("weekday");
      localToggleButton.getStyleClass().add(isWeekdayWeekend(m % 7) ? "weekend" : "weekday");
      ((Label)this.weeknumberLabels.get(m / 7)).setVisible(true);
      if (isToday(localCalendar)) {
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
      ObservableList localObservableList = ((CalendarPicker)getSkinnable()).calendars();
      int j = determineDaysInMonth();
      Calendar localCalendar = (Calendar)getDisplayedCalendar().clone();
      for (int k = 1; k <= j; k++)
      {
        localCalendar.set(5, k);
        int m = i + k - 1;
        boolean bool = contains(localObservableList, localCalendar);
        ((ToggleButton)this.dayButtons.get(m)).setSelected(bool);
      }
    }
    finally
    {
      this.iRefreshingSelection.addAndGet(-1);
    }
  }
  
  private boolean contains(List<Calendar> paramList, Calendar paramCalendar)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Calendar localCalendar = (Calendar)localIterator.next();
      if ((localCalendar.get(1) == paramCalendar.get(1)) && (localCalendar.get(2) == paramCalendar.get(2)) && (localCalendar.get(5) == paramCalendar.get(5))) {
        return true;
      }
    }
    return false;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/CalendarPickerControlSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */