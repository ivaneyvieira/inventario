package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.Calendar;
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
import jfxtras.labs.internal.scene.control.behavior.CalendarTimeTextFieldBehavior;
import jfxtras.labs.scene.control.CalendarTimePicker;
import jfxtras.labs.scene.control.CalendarTimeTextField;
import jfxtras.labs.util.NodeUtil;

public class CalendarTimeTextFieldCaspianSkin
  extends SkinBase<CalendarTimeTextField, CalendarTimeTextFieldBehavior>
{
  private TextField textField = null;
  private ImageView imageView = null;
  private GridPane gridPane = null;
  private CalendarTimePicker TimePicker = null;
  private Image closeIconImage = null;
  private Popup popup = null;
  
  public CalendarTimeTextFieldCaspianSkin(CalendarTimeTextField paramCalendarTimeTextField)
  {
    super(paramCalendarTimeTextField, new CalendarTimeTextFieldBehavior(paramCalendarTimeTextField));
    construct();
  }
  
  private void construct()
  {
    createNodes();
    ((CalendarTimeTextField)getSkinnable()).valueProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        CalendarTimeTextFieldCaspianSkin.this.refreshValue();
      }
    });
    refreshValue();
  }
  
  private void refreshValue()
  {
    Calendar localCalendar = ((CalendarTimeTextField)getSkinnable()).getValue();
    String str = CalendarTimePickerSkin.calendarTimeToText(localCalendar);
    this.textField.setText(str);
  }
  
  private void createNodes()
  {
    this.textField = new TextField();
    this.textField.focusedProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        if (!CalendarTimeTextFieldCaspianSkin.this.textField.isFocused()) {
          CalendarTimeTextFieldCaspianSkin.this.parse();
        }
      }
    });
    this.textField.setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        CalendarTimeTextFieldCaspianSkin.this.parse();
      }
    });
    this.textField.setOnKeyPressed(new EventHandler()
    {
      public void handle(KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getCode() == KeyCode.UP) || (paramAnonymousKeyEvent.getCode() == KeyCode.DOWN))
        {
          CalendarTimeTextFieldCaspianSkin.this.parse();
          Calendar localCalendar = (Calendar)((CalendarTimeTextField)CalendarTimeTextFieldCaspianSkin.this.getSkinnable()).getValue().clone();
          if (paramAnonymousKeyEvent.isControlDown()) {
            localCalendar.add(11, paramAnonymousKeyEvent.getCode() == KeyCode.UP ? 1 : -1);
          } else {
            localCalendar.add(12, paramAnonymousKeyEvent.getCode() == KeyCode.UP ? ((CalendarTimeTextField)CalendarTimeTextFieldCaspianSkin.this.getSkinnable()).getMinuteStep().intValue() : -1 * ((CalendarTimeTextField)CalendarTimeTextFieldCaspianSkin.this.getSkinnable()).getMinuteStep().intValue());
          }
          ((CalendarTimeTextField)CalendarTimeTextFieldCaspianSkin.this.getSkinnable()).setValue(CalendarTimePickerSkin.blockMinutesToStep(localCalendar, ((CalendarTimeTextField)CalendarTimeTextFieldCaspianSkin.this.getSkinnable()).getMinuteStep()));
        }
      }
    });
    this.textField.tooltipProperty().bind(((CalendarTimeTextField)getSkinnable()).tooltipProperty());
    ((CalendarTimeTextField)getSkinnable()).setTooltip(new Tooltip("Type a time or use # for now, or +/-<number>[h|m] for delta's (for example: -3m for minus 3 minutes)\nUse cursor up and down plus optional ctrl (hour) for quick keyboard changes."));
    this.textField.promptTextProperty().bind(((CalendarTimeTextField)getSkinnable()).promptTextProperty());
    Image localImage = new Image(getClass().getResourceAsStream(getClass().getSimpleName() + "Icon.png"));
    this.imageView = new ImageView(localImage);
    this.imageView.setPickOnBounds(true);
    this.imageView.setOnMouseClicked(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (CalendarTimeTextFieldCaspianSkin.this.textField.focusedProperty().get() == true) {
          CalendarTimeTextFieldCaspianSkin.this.parse();
        }
        CalendarTimeTextFieldCaspianSkin.this.showPopup(paramAnonymousMouseEvent);
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
          CalendarTimeTextFieldCaspianSkin.this.textField.requestFocus();
        }
      }
    });
    this.TimePicker = new CalendarTimePicker();
    Bindings.bindBidirectional(this.TimePicker.calendarProperty(), ((CalendarTimeTextField)getSkinnable()).valueProperty());
    Bindings.bindBidirectional(this.TimePicker.minuteStepProperty(), ((CalendarTimeTextField)getSkinnable()).minuteStepProperty());
    Bindings.bindBidirectional(this.TimePicker.showLabelsProperty(), ((CalendarTimeTextField)getSkinnable()).showLabelsProperty());
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
        ((CalendarTimeTextField)getSkinnable()).setValue(null);
        return;
      }
      int i;
      int j;
      if ((str.startsWith("-")) || (str.startsWith("+")))
      {
        if (str.startsWith("+")) {
          str = str.substring(1);
        }
        i = 5;
        if (str.toLowerCase().endsWith("m"))
        {
          str = str.substring(0, str.length() - 1);
          i = 12;
        }
        if (str.toLowerCase().endsWith("h"))
        {
          str = str.substring(0, str.length() - 1);
          i = 11;
        }
        j = Integer.parseInt(str);
        Calendar localCalendar1 = (Calendar)((CalendarTimeTextField)getSkinnable()).getValue().clone();
        localCalendar1.add(i, j);
        ((CalendarTimeTextField)getSkinnable()).setValue(CalendarTimePickerSkin.blockMinutesToStep(localCalendar1, ((CalendarTimeTextField)getSkinnable()).getMinuteStep()));
      }
      else if (str.equals("#"))
      {
        ((CalendarTimeTextField)getSkinnable()).setValue(CalendarTimePickerSkin.blockMinutesToStep(Calendar.getInstance(), ((CalendarTimeTextField)getSkinnable()).getMinuteStep()));
      }
      else
      {
        i = str.indexOf(":");
        if (i > 0)
        {
          j = Integer.parseInt(str.substring(0, i));
          int k = Integer.parseInt(str.substring(i + 1));
          Calendar localCalendar2 = ((CalendarTimeTextField)getSkinnable()).getValue() != null ? (Calendar)((CalendarTimeTextField)getSkinnable()).getValue().clone() : Calendar.getInstance();
          localCalendar2.set(11, j);
          localCalendar2.set(12, k);
          ((CalendarTimeTextField)getSkinnable()).setValue(CalendarTimePickerSkin.blockMinutesToStep(localCalendar2, ((CalendarTimeTextField)getSkinnable()).getMinuteStep()));
        }
        else
        {
          refreshValue();
        }
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
      localBorderPane.setCenter(this.TimePicker);
      ImageView localImageView = new ImageView(this.closeIconImage);
      localImageView.setPickOnBounds(true);
      localImageView.setOnMouseClicked(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          CalendarTimeTextFieldCaspianSkin.this.popup.hide();
        }
      });
      localBorderPane.rightProperty().set(localImageView);
      this.popup.getContent().add(localBorderPane);
    }
    this.popup.show(this.textField, NodeUtil.screenX(getSkinnable()), NodeUtil.screenY(getSkinnable()) + this.textField.getHeight());
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/CalendarTimeTextFieldCaspianSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */