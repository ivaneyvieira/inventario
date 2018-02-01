package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.Calendar;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jfxtras.labs.internal.scene.control.behavior.CalendarTimePickerBehavior;
import jfxtras.labs.scene.control.CalendarTimePicker;

public class CalendarTimePickerSkin
  extends SkinBase<CalendarTimePicker, CalendarTimePickerBehavior>
{
  private final Slider hourScrollSlider = new Slider();
  private final Slider minuteScrollSlider = new Slider();
  private final Text timeText = new Text("XX:XX");
  final Pane hourLabelsPane = new Pane()
  {
    protected void layoutChildren()
    {
      getChildren().clear();
      double d1 = new Text("88").prefWidth(0.0D);
      double d2 = d1 / 2.0D;
      double d3 = d1 + d2;
      double d4 = 5.0D;
      Text localText1 = new Text("0");
      Rectangle localRectangle = new Rectangle(0.0D, 0.0D, CalendarTimePickerSkin.this.minuteScrollSlider.getWidth(), localText1.prefHeight(0.0D));
      localRectangle.setFill(Color.TRANSPARENT);
      getChildren().add(localRectangle);
      int i = (int)(getWidth() / d3) + 2;
      int j = 24;
      if (i >= 24) {
        j = 1;
      } else if (i >= 12) {
        j = 2;
      } else if (i >= 8) {
        j = 3;
      } else if (i >= 6) {
        j = 4;
      } else if (i > 4) {
        j = 6;
      } else if (i > 2) {
        j = 12;
      }
      int k = 0;
      Text localText2;
      double d5;
      while (k < 24)
      {
        localText2 = new Text("" + k);
        localText2.setY(localText2.prefHeight(0.0D));
        d5 = d4 + (CalendarTimePickerSkin.this.minuteScrollSlider.getWidth() - 2.0D * d4) / 23.0D * k - localText2.prefWidth(0.0D) / (k == 23 ? 1 : 2) * (k == 0 ? 0 : 1);
        localText2.setX(d5);
        getChildren().add(localText2);
        k += j;
      }
      for (k = 0; k < 24; k++)
      {
        localText2 = new Text("0");
        d5 = d4 + (CalendarTimePickerSkin.this.minuteScrollSlider.getWidth() - 2.0D * d4) / 23.0D * k;
        getChildren().add(new Line(d5, localText2.prefHeight(0.0D) + 3.0D, d5, localText2.prefHeight(0.0D) + 3.0D + 3.0D));
      }
    }
  };
  final Pane minuteLabelsPane = new Pane()
  {
    protected void layoutChildren()
    {
      getChildren().clear();
      double d1 = new Text("88").prefWidth(0.0D);
      double d2 = d1 / 2.0D;
      double d3 = d1 + d2;
      double d4 = 5.0D;
      if (((Boolean)((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).showLabelsProperty().get()).booleanValue())
      {
        Text localText1 = new Text("0");
        Rectangle localRectangle = new Rectangle(0.0D, 0.0D, CalendarTimePickerSkin.this.minuteScrollSlider.getWidth(), localText1.prefHeight(0.0D));
        localRectangle.setFill(Color.TRANSPARENT);
        getChildren().add(localRectangle);
      }
      int i = (int)(getWidth() / d3) + 2;
      int j = 60;
      if (i >= 60) {
        j = 1;
      } else if (i >= 30) {
        j = 2;
      } else if (i >= 20) {
        j = 3;
      } else if (i >= 15) {
        j = 4;
      } else if (i >= 12) {
        j = 5;
      } else if (i >= 6) {
        j = 10;
      } else if (i >= 4) {
        j = 15;
      } else if (i >= 2) {
        j = 30;
      }
      if (j < ((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).getMinuteStep().intValue()) {
        j = ((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).getMinuteStep().intValue();
      }
      int k = 0;
      while (k <= 59)
      {
        Text localText2 = new Text("" + k);
        localText2.setY(localText2.prefHeight(0.0D));
        double d6 = d4 + (CalendarTimePickerSkin.this.minuteScrollSlider.getWidth() - 2.0D * d4) / 59.0D * k - localText2.prefWidth(0.0D) / (k == 59 ? 1 : 2) * (k == 0 ? 0 : 1);
        localText2.setX(d6);
        getChildren().add(localText2);
        k += j;
      }
      for (k = 0; k <= 59; k++)
      {
        double d5 = d4 + (CalendarTimePickerSkin.this.minuteScrollSlider.getWidth() - 2.0D * d4) / 59.0D * k;
        getChildren().add(new Line(d5, 0.0D, d5, 3.0D));
      }
    }
  };
  
  public CalendarTimePickerSkin(CalendarTimePicker paramCalendarTimePicker)
  {
    super(paramCalendarTimePicker, new CalendarTimePickerBehavior(paramCalendarTimePicker));
    construct();
  }
  
  private void construct()
  {
    createNodes();
    ((CalendarTimePicker)getSkinnable()).calendarProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        CalendarTimePickerSkin.this.refresh();
      }
    });
    refresh();
    ((CalendarTimePicker)getSkinnable()).minuteStepProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        CalendarTimePickerSkin.this.minuteScrollSlider.setBlockIncrement(((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).getMinuteStep().doubleValue());
      }
    });
    this.minuteScrollSlider.setBlockIncrement(((CalendarTimePicker)getSkinnable()).getMinuteStep().doubleValue());
    ((CalendarTimePicker)getSkinnable()).showLabelsProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        CalendarTimePickerSkin.this.refreshLayout();
      }
    });
  }
  
  private void createNodes()
  {
    this.hourScrollSlider.minProperty().set(0.0D);
    this.hourScrollSlider.maxProperty().set(23.0D);
    this.hourScrollSlider.setMajorTickUnit(12.0D);
    this.hourScrollSlider.setMinorTickCount(3);
    this.minuteScrollSlider.minProperty().set(0.0D);
    this.minuteScrollSlider.maxProperty().set(59.0D);
    this.minuteScrollSlider.setMajorTickUnit(10.0D);
    this.hourScrollSlider.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        Calendar localCalendar = ((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).getCalendar();
        localCalendar = localCalendar == null ? Calendar.getInstance() : (Calendar)localCalendar.clone();
        localCalendar.set(11, paramAnonymousNumber2.intValue());
        ((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).setCalendar(localCalendar);
      }
    });
    this.minuteScrollSlider.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        Calendar localCalendar = ((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).getCalendar();
        localCalendar = localCalendar == null ? Calendar.getInstance() : (Calendar)localCalendar.clone();
        int i = paramAnonymousNumber2.intValue();
        int j = ((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).getMinuteStep().intValue();
        if (j > 1)
        {
          i += ((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).getMinuteStep().intValue() / 2;
          if (i > 59) {
            i -= j;
          }
        }
        localCalendar.set(12, i);
        localCalendar = CalendarTimePickerSkin.blockMinutesToStep(localCalendar, ((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).getMinuteStep());
        ((CalendarTimePicker)CalendarTimePickerSkin.this.getSkinnable()).setCalendar(localCalendar);
      }
    });
    this.timeText.setDisable(true);
    this.timeText.getStyleClass().add("timeLabel");
    refreshLayout();
    getStyleClass().add(getClass().getSimpleName());
  }
  
  private void refreshLayout()
  {
    getChildren().clear();
    VBox localVBox = new VBox(0.0D);
    localVBox.alignmentProperty().set(Pos.CENTER);
    if (((CalendarTimePicker)getSkinnable()).getShowLabels().booleanValue()) {
      localVBox.getChildren().add(this.hourLabelsPane);
    }
    localVBox.getChildren().add(this.hourScrollSlider);
    localVBox.getChildren().add(this.minuteScrollSlider);
    if (((CalendarTimePicker)getSkinnable()).getShowLabels().booleanValue()) {
      localVBox.getChildren().add(this.minuteLabelsPane);
    }
    getChildren().add(localVBox);
    getChildren().add(this.timeText);
  }
  
  private void refresh()
  {
    Calendar localCalendar = ((CalendarTimePicker)getSkinnable()).getCalendar();
    int i = localCalendar == null ? 0 : localCalendar.get(11);
    int j = localCalendar == null ? 0 : localCalendar.get(12);
    this.hourScrollSlider.valueProperty().set(i);
    this.minuteScrollSlider.valueProperty().set(j);
    this.timeText.setText(calendarTimeToText(localCalendar));
  }
  
  public static String calendarTimeToText(Calendar paramCalendar)
  {
    if (paramCalendar == null) {
      return "";
    }
    int i = paramCalendar.get(11);
    int j = paramCalendar.get(12);
    String str = (i < 10 ? "0" : "") + i + ":" + (j < 10 ? "0" : "") + j;
    return str;
  }
  
  public static Calendar blockMinutesToStep(Calendar paramCalendar, Integer paramInteger)
  {
    if ((paramInteger == null) || (paramCalendar == null)) {
      return paramCalendar;
    }
    int i = paramCalendar.get(12);
    if (paramInteger.intValue() == 1) {
      return paramCalendar;
    }
    i /= paramInteger.intValue();
    i *= paramInteger.intValue();
    if (paramCalendar.get(12) != i)
    {
      Calendar localCalendar = (Calendar)paramCalendar.clone();
      localCalendar.set(12, i);
      paramCalendar = localCalendar;
    }
    return paramCalendar;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/CalendarTimePickerSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */