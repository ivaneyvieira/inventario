package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import jfxtras.labs.animation.Timer;
import jfxtras.labs.internal.scene.control.behavior.ListSpinnerBehavior;
import jfxtras.labs.scene.control.ListSpinner;
import jfxtras.labs.scene.control.ListSpinner.ArrowDirection;
import jfxtras.labs.scene.control.ListSpinner.ArrowPosition;

public class ListSpinnerCaspianSkin<T>
  extends SkinBase<ListSpinner<T>, ListSpinnerBehavior<T>>
{
  private Region decrementArrow = null;
  private Region incrementArrow = null;
  private GridPane gridPane = null;
  private BorderPane valueGroup;
  private final Timer unclickTimer = new Timer(new Runnable()
  {
    public void run()
    {
      ListSpinnerCaspianSkin.this.unclickArrows();
    }
  }).withDelay(Duration.millis(100.0D)).withRepeats(false);
  private final Timer repeatDecrementClickTimer = new Timer(new Runnable()
  {
    public void run()
    {
      ((ListSpinner)ListSpinnerCaspianSkin.this.getSkinnable()).decrement();
    }
  }).withDelay(Duration.millis(500.0D)).withCycleDuration(Duration.millis(50.0D));
  private final Timer repeatIncrementClickTimer = new Timer(new Runnable()
  {
    public void run()
    {
      ((ListSpinner)ListSpinnerCaspianSkin.this.getSkinnable()).increment();
    }
  }).withDelay(Duration.millis(500.0D)).withCycleDuration(Duration.millis(50.0D));
  private TextField textField = null;
  
  public ListSpinnerCaspianSkin(ListSpinner<T> paramListSpinner)
  {
    super(paramListSpinner, new ListSpinnerBehavior(paramListSpinner));
    construct();
  }
  
  private void construct()
  {
    createNodes();
    ((ListSpinner)getSkinnable()).editableProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        ListSpinnerCaspianSkin.this.replaceValueNode();
      }
    });
    replaceValueNode();
    ((ListSpinner)getSkinnable()).valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends T> paramAnonymousObservableValue, T paramAnonymousT1, T paramAnonymousT2)
      {
        ListSpinnerCaspianSkin.this.refreshValue();
      }
    });
    refreshValue();
    ((ListSpinner)getSkinnable()).arrowDirectionProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends ArrowDirection> paramAnonymousObservableValue, ArrowDirection paramAnonymousArrowDirection1, ArrowDirection paramAnonymousArrowDirection2)
      {
        ListSpinnerCaspianSkin.this.setArrowCSS();
        ListSpinnerCaspianSkin.this.layoutGridPane();
      }
    });
    setArrowCSS();
    layoutGridPane();
    ((ListSpinner)getSkinnable()).alignmentProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Pos> paramAnonymousObservableValue, Pos paramAnonymousPos1, Pos paramAnonymousPos2)
      {
        ListSpinnerCaspianSkin.this.alignValue();
      }
    });
    alignValue();
  }
  
  private void refreshValue()
  {
    Object localObject;
    if (((ListSpinner)getSkinnable()).isEditable().booleanValue() == true)
    {
      localObject = ((ListSpinner)getSkinnable()).getValue();
      this.textField.setText(((ListSpinner)getSkinnable()).getPrefix() + ((ListSpinner)getSkinnable()).getStringConverter().toString(localObject) + ((ListSpinner)getSkinnable()).getPostfix());
    }
    else
    {
      localObject = (Node)((ListSpinner)getSkinnable()).getCellFactory().call(getSkinnable());
    }
  }
  
  private void createNodes()
  {
    this.decrementArrow = new Region();
    this.decrementArrow.getStyleClass().add("idle");
    this.valueGroup = new BorderPane();
    this.valueGroup.getStyleClass().add("value");
    this.incrementArrow = new Region();
    this.incrementArrow.getStyleClass().add("idle");
    this.gridPane = new GridPane();
    this.gridPane.setOnMouseClicked(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (ListSpinnerCaspianSkin.this.mouseEventOverArrow(paramAnonymousMouseEvent, ListSpinnerCaspianSkin.this.decrementArrow))
        {
          ListSpinnerCaspianSkin.this.unclickArrows();
          ListSpinnerCaspianSkin.this.decrementArrow.getStyleClass().add("clicked");
          ((ListSpinner)ListSpinnerCaspianSkin.this.getSkinnable()).decrement();
          ListSpinnerCaspianSkin.this.unclickTimer.restart();
          return;
        }
        if (ListSpinnerCaspianSkin.this.mouseEventOverArrow(paramAnonymousMouseEvent, ListSpinnerCaspianSkin.this.incrementArrow))
        {
          ListSpinnerCaspianSkin.this.unclickArrows();
          ListSpinnerCaspianSkin.this.incrementArrow.getStyleClass().add("clicked");
          ((ListSpinner)ListSpinnerCaspianSkin.this.getSkinnable()).increment();
          ListSpinnerCaspianSkin.this.unclickTimer.restart();
          return;
        }
      }
    });
    this.gridPane.setOnMousePressed(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (ListSpinnerCaspianSkin.this.mouseEventOverArrow(paramAnonymousMouseEvent, ListSpinnerCaspianSkin.this.decrementArrow))
        {
          ListSpinnerCaspianSkin.this.decrementArrow.getStyleClass().add("clicked");
          ListSpinnerCaspianSkin.this.repeatDecrementClickTimer.restart();
          return;
        }
        if (ListSpinnerCaspianSkin.this.mouseEventOverArrow(paramAnonymousMouseEvent, ListSpinnerCaspianSkin.this.incrementArrow))
        {
          ListSpinnerCaspianSkin.this.incrementArrow.getStyleClass().add("clicked");
          ListSpinnerCaspianSkin.this.repeatIncrementClickTimer.restart();
          return;
        }
      }
    });
    this.gridPane.setOnMouseReleased(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        ListSpinnerCaspianSkin.this.unclickArrows();
        ListSpinnerCaspianSkin.this.repeatDecrementClickTimer.stop();
        ListSpinnerCaspianSkin.this.repeatIncrementClickTimer.stop();
      }
    });
    this.gridPane.setOnMouseExited(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        ListSpinnerCaspianSkin.this.unclickArrows();
        ListSpinnerCaspianSkin.this.repeatDecrementClickTimer.stop();
        ListSpinnerCaspianSkin.this.repeatIncrementClickTimer.stop();
      }
    });
    this.gridPane.setOnScroll(new EventHandler()
    {
      public void handle(ScrollEvent paramAnonymousScrollEvent)
      {
        if ((paramAnonymousScrollEvent.getDeltaY() < 0.0D) || (paramAnonymousScrollEvent.getDeltaX() < 0.0D))
        {
          ListSpinnerCaspianSkin.this.unclickArrows();
          ListSpinnerCaspianSkin.this.decrementArrow.getStyleClass().add("clicked");
          ((ListSpinner)ListSpinnerCaspianSkin.this.getSkinnable()).decrement();
          ListSpinnerCaspianSkin.this.unclickTimer.restart();
          return;
        }
        if ((paramAnonymousScrollEvent.getDeltaY() > 0.0D) || (paramAnonymousScrollEvent.getDeltaX() > 0.0D))
        {
          ListSpinnerCaspianSkin.this.unclickArrows();
          ListSpinnerCaspianSkin.this.incrementArrow.getStyleClass().add("clicked");
          ((ListSpinner)ListSpinnerCaspianSkin.this.getSkinnable()).increment();
          ListSpinnerCaspianSkin.this.unclickTimer.restart();
          return;
        }
      }
    });
    getStyleClass().add(getClass().getSimpleName());
    getChildren().add(this.gridPane);
  }
  
  private boolean mouseEventOverArrow(MouseEvent paramMouseEvent, Region paramRegion)
  {
    Point2D localPoint2D = paramRegion.sceneToLocal(paramMouseEvent.getSceneX(), paramMouseEvent.getSceneY());
    return (localPoint2D.getX() >= 0.0D) && (localPoint2D.getX() <= paramRegion.getWidth()) && (localPoint2D.getY() >= 0.0D) && (localPoint2D.getY() <= paramRegion.getHeight());
  }
  
  private void unclickArrows()
  {
    this.decrementArrow.getStyleClass().remove("clicked");
    this.incrementArrow.getStyleClass().remove("clicked");
  }
  
  private void replaceValueNode()
  {
    this.valueGroup.getChildren().clear();
    if (!((ListSpinner)getSkinnable()).isEditable().booleanValue())
    {
      Node localNode = (Node)((ListSpinner)getSkinnable()).getCellFactory().call(getSkinnable());
      this.valueGroup.setCenter(localNode);
    }
    else
    {
      if (this.textField == null)
      {
        this.textField = new TextField();
        this.textField.focusedProperty().addListener(new InvalidationListener()
        {
          public void invalidated(Observable paramAnonymousObservable)
          {
            if (!ListSpinnerCaspianSkin.this.textField.isFocused()) {
              ListSpinnerCaspianSkin.this.parse(ListSpinnerCaspianSkin.this.textField);
            }
          }
        });
        this.textField.setOnAction(new EventHandler()
        {
          public void handle(ActionEvent paramAnonymousActionEvent)
          {
            ListSpinnerCaspianSkin.this.parse(ListSpinnerCaspianSkin.this.textField);
          }
        });
        this.textField.setOnKeyPressed(new EventHandler()
        {
          public void handle(KeyEvent paramAnonymousKeyEvent)
          {
            if (paramAnonymousKeyEvent.getCode() == KeyCode.ESCAPE) {
              ListSpinnerCaspianSkin.this.refreshValue();
            }
          }
        });
        this.textField.alignmentProperty().bind(((ListSpinner)getSkinnable()).alignmentProperty());
      }
      this.valueGroup.setCenter(this.textField);
    }
    alignValue();
  }
  
  private void alignValue()
  {
    BorderPane.setAlignment((Node)this.valueGroup.getChildren().get(0), (Pos)((ListSpinner)getSkinnable()).alignmentProperty().getValue());
  }
  
  protected void parse(TextField paramTextField)
  {
    String str = paramTextField.getText();
    ((ListSpinnerBehavior)getBehavior()).parse(str);
    refreshValue();
  }
  
  private void layoutGridPane()
  {
    ArrowDirection localArrowDirection = ((ListSpinner)getSkinnable()).getArrowDirection();
    ArrowPosition localArrowPosition = ((ListSpinner)getSkinnable()).getArrowPosition();
    ColumnConstraints localColumnConstraints1 = new ColumnConstraints(this.valueGroup.getMinWidth(), this.valueGroup.getPrefWidth(), Double.MAX_VALUE);
    localColumnConstraints1.setHgrow(Priority.ALWAYS);
    ColumnConstraints localColumnConstraints2 = new ColumnConstraints(10.0D);
    RowConstraints localRowConstraints1 = new RowConstraints(this.valueGroup.getMinHeight(), this.valueGroup.getPrefHeight(), Double.MAX_VALUE);
    localRowConstraints1.setVgrow(Priority.ALWAYS);
    RowConstraints localRowConstraints2 = new RowConstraints(10.0D);
    this.gridPane.getChildren().clear();
    this.gridPane.getColumnConstraints().clear();
    this.gridPane.getRowConstraints().clear();
    if (localArrowDirection == ArrowDirection.HORIZONTAL)
    {
      if (localArrowPosition == ArrowPosition.LEADING)
      {
        this.gridPane.setHgap(3.0D);
        this.gridPane.setVgap(0.0D);
        this.gridPane.add(this.decrementArrow, 0, 0);
        this.gridPane.add(this.incrementArrow, 1, 0);
        this.gridPane.add(this.valueGroup, 2, 0);
        this.gridPane.getColumnConstraints().addAll(new ColumnConstraints[] { localColumnConstraints2, localColumnConstraints2, localColumnConstraints1 });
      }
      if (localArrowPosition == ArrowPosition.TRAILING)
      {
        this.gridPane.setHgap(3.0D);
        this.gridPane.setVgap(0.0D);
        this.gridPane.add(this.valueGroup, 0, 0);
        this.gridPane.add(this.decrementArrow, 1, 0);
        this.gridPane.add(this.incrementArrow, 2, 0);
        this.gridPane.getColumnConstraints().addAll(new ColumnConstraints[] { localColumnConstraints1, localColumnConstraints2, localColumnConstraints2 });
      }
      if (localArrowPosition == ArrowPosition.SPLIT)
      {
        this.gridPane.setHgap(3.0D);
        this.gridPane.setVgap(0.0D);
        this.gridPane.add(this.decrementArrow, 0, 0);
        this.gridPane.add(this.valueGroup, 1, 0);
        this.gridPane.add(this.incrementArrow, 2, 0);
        this.gridPane.getColumnConstraints().addAll(new ColumnConstraints[] { localColumnConstraints2, localColumnConstraints1, localColumnConstraints2 });
      }
    }
    if (localArrowDirection == ArrowDirection.VERTICAL)
    {
      if (localArrowPosition == ArrowPosition.LEADING)
      {
        this.gridPane.setHgap(3.0D);
        this.gridPane.setVgap(0.0D);
        this.gridPane.add(this.incrementArrow, 0, 0);
        this.gridPane.add(this.decrementArrow, 0, 1);
        this.gridPane.add(this.valueGroup, 1, 0, 1, 2);
        this.gridPane.getColumnConstraints().addAll(new ColumnConstraints[] { localColumnConstraints2, localColumnConstraints1 });
        this.gridPane.getRowConstraints().addAll(new RowConstraints[] { localRowConstraints2, localRowConstraints2 });
      }
      if (localArrowPosition == ArrowPosition.TRAILING)
      {
        this.gridPane.setHgap(3.0D);
        this.gridPane.setVgap(0.0D);
        this.gridPane.add(this.valueGroup, 0, 0, 1, 2);
        this.gridPane.add(this.incrementArrow, 1, 0);
        this.gridPane.add(this.decrementArrow, 1, 1);
        this.gridPane.getColumnConstraints().addAll(new ColumnConstraints[] { localColumnConstraints1, localColumnConstraints2 });
        this.gridPane.getRowConstraints().addAll(new RowConstraints[] { localRowConstraints2, localRowConstraints2 });
      }
      if (localArrowPosition == ArrowPosition.SPLIT)
      {
        this.gridPane.setHgap(3.0D);
        this.gridPane.setVgap(0.0D);
        this.gridPane.add(this.incrementArrow, 0, 0);
        this.gridPane.add(this.valueGroup, 0, 1);
        this.gridPane.add(this.decrementArrow, 0, 2);
        this.gridPane.getColumnConstraints().addAll(new ColumnConstraints[] { localColumnConstraints1 });
        this.gridPane.getRowConstraints().addAll(new RowConstraints[] { localRowConstraints2, localRowConstraints1, localRowConstraints2 });
      }
    }
  }
  
  private void setArrowCSS()
  {
    if (((ListSpinner)getSkinnable()).getArrowDirection().equals(ArrowDirection.HORIZONTAL))
    {
      this.decrementArrow.getStyleClass().add("left-arrow");
      this.incrementArrow.getStyleClass().add("right-arrow");
    }
    else
    {
      this.decrementArrow.getStyleClass().add("down-arrow");
      this.incrementArrow.getStyleClass().add("up-arrow");
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/ListSpinnerCaspianSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */