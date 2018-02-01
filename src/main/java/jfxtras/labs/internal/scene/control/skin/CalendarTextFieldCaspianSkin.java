package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Popup;
import jfxtras.labs.internal.scene.control.behavior.CalendarTextFieldBehavior;
import jfxtras.labs.scene.control.CalendarPicker;
import jfxtras.labs.scene.control.CalendarPicker.Mode;
import jfxtras.labs.scene.control.CalendarTextField;
import jfxtras.labs.util.NodeUtil;

public class CalendarTextFieldCaspianSkin
  extends SkinBase<CalendarTextField, CalendarTextFieldBehavior>
{
  private CalendarTextField control;
  private TextField textField = null;
  private ImageView imageView = null;
  private GridPane gridPane = null;
  private CalendarPicker calendarPicker = null;
  private Image closeIconImage = null;
  private Popup popup = null;
  
  public CalendarTextFieldCaspianSkin(CalendarTextField paramCalendarTextField)
  {
    super(paramCalendarTextField, new CalendarTextFieldBehavior(paramCalendarTextField));
    this.control = paramCalendarTextField;
    construct();
  }
  
  private void construct()
  {
    createNodes();
    ((CalendarTextField)getSkinnable()).valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Calendar> paramAnonymousObservableValue, Calendar paramAnonymousCalendar1, Calendar paramAnonymousCalendar2)
      {
        CalendarTextFieldCaspianSkin.this.refreshValue();
      }
    });
    refreshValue();
  }
  
  private void refreshValue()
  {
    Calendar localCalendar = ((CalendarTextField)getSkinnable()).getValue();
    String str = localCalendar == null ? "" : ((CalendarTextField)getSkinnable()).getDateFormat().format(localCalendar.getTime());
    this.textField.setText(str);
  }
  
  private void createNodes()
  {
    this.textField = new TextField();
    this.textField.setPrefColumnCount(20);
    this.textField.focusedProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        if (!CalendarTextFieldCaspianSkin.this.textField.isFocused()) {
          CalendarTextFieldCaspianSkin.this.parse();
        }
      }
    });
    this.textField.setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        CalendarTextFieldCaspianSkin.this.parse();
      }
    });
    this.textField.setOnKeyPressed(new EventHandler()
    {
      public void handle(KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getCode() == KeyCode.UP) || (paramAnonymousKeyEvent.getCode() == KeyCode.DOWN))
        {
          CalendarTextFieldCaspianSkin.this.parse();
          Calendar localCalendar = (Calendar)((CalendarTextField)CalendarTextFieldCaspianSkin.this.getSkinnable()).getValue().clone();
          int i = 5;
          if ((!paramAnonymousKeyEvent.isShiftDown()) && (paramAnonymousKeyEvent.isControlDown())) {
            i = 2;
          }
          if ((!paramAnonymousKeyEvent.isShiftDown()) && (paramAnonymousKeyEvent.isAltDown())) {
            i = 1;
          }
          if ((paramAnonymousKeyEvent.isShiftDown() == true) && (paramAnonymousKeyEvent.isControlDown()) && (((Boolean)((CalendarTextField)CalendarTextFieldCaspianSkin.this.getSkinnable()).showTimeProperty().get()).booleanValue())) {
            i = 11;
          }
          if ((paramAnonymousKeyEvent.isShiftDown() == true) && (paramAnonymousKeyEvent.isAltDown()) && (((Boolean)((CalendarTextField)CalendarTextFieldCaspianSkin.this.getSkinnable()).showTimeProperty().get()).booleanValue())) {
            i = 12;
          }
          localCalendar.add(i, paramAnonymousKeyEvent.getCode() == KeyCode.UP ? 1 : -1);
          ((CalendarTextField)CalendarTextFieldCaspianSkin.this.getSkinnable()).setValue(localCalendar);
        }
      }
    });
    this.textField.tooltipProperty().bind(((CalendarTextField)getSkinnable()).tooltipProperty());
    ((CalendarTextField)getSkinnable()).setTooltip(new Tooltip("Type a date or use # for today, or +/-<number>[d|w|m|y] for delta's (for example: -3m for minus 3 months)\nUse cursor up and down plus optional shift (week), ctrl (month) or alt (year) for quick keyboard changes."));
    this.textField.promptTextProperty().bind(((CalendarTextField)getSkinnable()).promptTextProperty());
    Image localImage = new Image(getClass().getResourceAsStream(getClass().getSimpleName() + "Icon.png"));
    this.imageView = new ImageView(localImage);
    this.imageView.setPickOnBounds(true);
    this.imageView.setOnMouseClicked(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (CalendarTextFieldCaspianSkin.this.textField.focusedProperty().get() == true) {
          CalendarTextFieldCaspianSkin.this.parse();
        }
        CalendarTextFieldCaspianSkin.this.showPopup(paramAnonymousMouseEvent);
      }
    });
    this.gridPane = new GridPane();
    this.gridPane.setHgap(3.0D);
    this.gridPane.add(this.textField, 0, 0);
    this.gridPane.add(this.imageView, 1, 0);
    ColumnConstraints localColumnConstraints = new ColumnConstraints(100.0D, 10.0D, Double.MAX_VALUE);
    localColumnConstraints.setHgrow(Priority.ALWAYS);
    this.gridPane.getColumnConstraints().addAll(new ColumnConstraints[] { localColumnConstraints });
    getStyleClass().add(getClass().getSimpleName());
    getChildren().add(this.gridPane);
    focusedProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        if (paramAnonymousBoolean2.booleanValue() == true) {
          CalendarTextFieldCaspianSkin.this.textField.requestFocus();
        }
      }
    });
    this.calendarPicker = new CalendarPicker();
    this.calendarPicker.setMode(Mode.SINGLE);
    this.calendarPicker.showTimeProperty().bind(((CalendarTextField)getSkinnable()).showTimeProperty());
    Bindings.bindBidirectional(this.calendarPicker.localeProperty(), ((CalendarTextField)getSkinnable()).localeProperty());
    Bindings.bindBidirectional(this.calendarPicker.calendarProperty(), ((CalendarTextField)getSkinnable()).valueProperty());
    this.calendarPicker.calendarProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Calendar> paramAnonymousObservableValue, Calendar paramAnonymousCalendar1, Calendar paramAnonymousCalendar2)
      {
        if ((CalendarTextFieldCaspianSkin.this.popup != null) && (!((Boolean)((CalendarTextField)CalendarTextFieldCaspianSkin.this.getSkinnable()).showTimeProperty().get()).booleanValue()))
        {
          CalendarTextFieldCaspianSkin.this.popup.hide();
          CalendarTextFieldCaspianSkin.this.popup = null;
        }
      }
    });
    this.closeIconImage = new Image(getClass().getResourceAsStream(getClass().getSimpleName() + "CloseWindowIcon.png"));
  }
  
  private void parse()
  {
    try
    {
      String str = this.textField.getText();
      str = str.trim();
      if (str.length() == 0)
      {
        ((CalendarTextField)getSkinnable()).setValue(null);
        return;
      }
      if ((str.startsWith("-")) || (str.startsWith("+")))
      {
        if (str.startsWith("+")) {
          str = str.substring(1);
        }
        int i = 5;
        if (str.toLowerCase().endsWith("d"))
        {
          str = str.substring(0, str.length() - 1);
          i = 5;
        }
        if (str.toLowerCase().endsWith("w"))
        {
          str = str.substring(0, str.length() - 1);
          i = 3;
        }
        if (str.toLowerCase().endsWith("m"))
        {
          str = str.substring(0, str.length() - 1);
          i = 2;
        }
        if (str.toLowerCase().endsWith("y"))
        {
          str = str.substring(0, str.length() - 1);
          i = 1;
        }
        int j = Integer.parseInt(str);
        Calendar localCalendar2 = (Calendar)((CalendarTextField)getSkinnable()).getValue().clone();
        localCalendar2.add(i, j);
        ((CalendarTextField)getSkinnable()).setValue(localCalendar2);
      }
      else if (str.equals("#"))
      {
        ((CalendarTextField)getSkinnable()).setValue(Calendar.getInstance());
      }
      else
      {
        Date localDate = ((CalendarTextField)getSkinnable()).getDateFormat().parse(str);
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.setTime(localDate);
        ((CalendarTextField)getSkinnable()).setValue(localCalendar1);
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  private void showPopup(MouseEvent paramMouseEvent)
  {
    if (this.popup == null)
    {
      this.popup = new Popup();
      this.popup.setAutoFix(true);
      this.popup.setAutoHide(true);
      this.popup.setHideOnEscape(true);
      BorderPane localBorderPane = new BorderPane();
      localBorderPane.getStyleClass().add(getClass().getSimpleName() + "_popup");
      localBorderPane.setCenter(this.calendarPicker);
      if (((Boolean)((CalendarTextField)getSkinnable()).showTimeProperty().get()).booleanValue() == true)
      {
        ImageView localImageView = new ImageView(this.closeIconImage);
        localImageView.setPickOnBounds(true);
        localImageView.setOnMouseClicked(new EventHandler()
        {
          public void handle(MouseEvent paramAnonymousMouseEvent)
          {
            CalendarTextFieldCaspianSkin.this.popup.hide();
            CalendarTextFieldCaspianSkin.this.popup = null;
          }
        });
        localBorderPane.rightProperty().set(localImageView);
      }
      this.popup.getContent().add(localBorderPane);
    }
    this.popup.show(this.textField, NodeUtil.screenX(getSkinnable()), NodeUtil.screenY(getSkinnable()) + this.textField.getHeight());
    this.calendarPicker.requestFocus();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/CalendarTextFieldCaspianSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */