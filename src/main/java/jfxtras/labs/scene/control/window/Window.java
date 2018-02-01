package jfxtras.labs.scene.control.window;

import javafx.animation.Animation.Status;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import jfxtras.labs.util.NodeUtil;

public class Window
  extends Control
{
  public static final String DEFAULT_STYLE = "/jfxtras/labs/scene/control/window/default.css";
  public static final String DEFAULT_STYLE_CLASS = "window";
  private boolean moveToFront = true;
  private StringProperty titleProperty = new SimpleStringProperty("Title");
  private BooleanProperty minimizeProperty = new SimpleBooleanProperty();
  private Property<Pane> contentPaneProperty = new SimpleObjectProperty();
  private ObservableList<WindowIcon> leftIcons = FXCollections.observableArrayList();
  private ObservableList<WindowIcon> rightIcons = FXCollections.observableArrayList();
  private DoubleProperty resizableBorderWidthProperty = new SimpleDoubleProperty(5.0D);
  private StringProperty titleBarStyleClassProperty = new SimpleStringProperty("window-titlebar");
  private ObjectProperty<EventHandler<ActionEvent>> onCloseActionProperty = new SimpleObjectProperty();
  private ObjectProperty<EventHandler<ActionEvent>> onClosedActionProperty = new SimpleObjectProperty();
  private ObjectProperty<Transition> closeTransitionProperty = new SimpleObjectProperty();
  
  public Window()
  {
    init();
  }
  
  public Window(String paramString)
  {
    setTitle(paramString);
    init();
  }
  
  private void init()
  {
    getStyleClass().setAll(new String[] { "window" });
    setContentPane(new StackPane());
    boundsInParentProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Bounds> paramAnonymousObservableValue, Bounds paramAnonymousBounds1, Bounds paramAnonymousBounds2)
      {
        if (Window.this.getParent() != null)
        {
          if (paramAnonymousBounds2.equals(paramAnonymousBounds1)) {
            return;
          }
          Window.this.getParent().requestLayout();
          double d1 = Math.max(0.0D, Window.this.getLayoutX());
          double d2 = Math.max(0.0D, Window.this.getLayoutY());
          Window.this.setLayoutX(d1);
          Window.this.setLayoutY(d2);
        }
      }
    });
    this.closeTransitionProperty.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Transition> paramAnonymousObservableValue, Transition paramAnonymousTransition1, Transition paramAnonymousTransition2)
      {
        paramAnonymousTransition2.statusProperty().addListener(new ChangeListener()
        {
          public void changed(ObservableValue<? extends Animation.Status> paramAnonymous2ObservableValue, Animation.Status paramAnonymous2Status1, Animation.Status paramAnonymous2Status2)
          {
            if (paramAnonymous2Status2 == Animation.Status.STOPPED)
            {
              if (Window.this.getOnCloseAction() != null) {
                Window.this.getOnCloseAction().handle(new ActionEvent(this, Window.this));
              }
              NodeUtil.removeFromParent(Window.this);
              if (Window.this.getOnClosedAction() != null) {
                Window.this.getOnClosedAction().handle(new ActionEvent(this, Window.this));
              }
            }
          }
        });
      }
    });
    ScaleTransition localScaleTransition = new ScaleTransition();
    localScaleTransition.setNode(this);
    localScaleTransition.setFromX(1.0D);
    localScaleTransition.setFromY(1.0D);
    localScaleTransition.setToX(0.0D);
    localScaleTransition.setToY(0.0D);
    localScaleTransition.setDuration(Duration.seconds(0.2D));
    setCloseTransition(localScaleTransition);
  }
  
  protected String getUserAgentStylesheet()
  {
    return "/jfxtras/labs/scene/control/window/default.css";
  }
  
  public Pane getContentPane()
  {
    return (Pane)this.contentPaneProperty.getValue();
  }
  
  public void setContentPane(Pane paramPane)
  {
    this.contentPaneProperty.setValue(paramPane);
  }
  
  public Property<Pane> contentPaneProperty()
  {
    return this.contentPaneProperty;
  }
  
  public void setMoveToFront(boolean paramBoolean)
  {
    this.moveToFront = paramBoolean;
  }
  
  public boolean isMoveToFront()
  {
    return this.moveToFront;
  }
  
  public final String getTitle()
  {
    return (String)this.titleProperty.get();
  }
  
  public final void setTitle(String paramString)
  {
    this.titleProperty.set(paramString);
  }
  
  public final StringProperty titleProperty()
  {
    return this.titleProperty;
  }
  
  public ObservableList<WindowIcon> getLeftIcons()
  {
    return this.leftIcons;
  }
  
  public ObservableList<WindowIcon> getRightIcons()
  {
    return this.rightIcons;
  }
  
  public void setMinimized(Boolean paramBoolean)
  {
    this.minimizeProperty.set(paramBoolean.booleanValue());
  }
  
  public boolean isMinimized()
  {
    return this.minimizeProperty.get();
  }
  
  public BooleanProperty minimizedProperty()
  {
    return this.minimizeProperty;
  }
  
  public StringProperty titleBarStyleClassProperty()
  {
    return this.titleBarStyleClassProperty;
  }
  
  public void setTitleBarStyleClass(String paramString)
  {
    this.titleBarStyleClassProperty.set(paramString);
  }
  
  public String getTitleBarStyleClass()
  {
    return (String)this.titleBarStyleClassProperty.get();
  }
  
  public DoubleProperty resizableBorderWidthProperty()
  {
    return this.resizableBorderWidthProperty;
  }
  
  public void setResizableBorderWidth(double paramDouble)
  {
    this.resizableBorderWidthProperty.set(paramDouble);
  }
  
  public double getResizableBorderWidth()
  {
    return this.resizableBorderWidthProperty.get();
  }
  
  public void close()
  {
    if (getCloseTransition() != null) {
      getCloseTransition().play();
    } else {
      NodeUtil.removeFromParent(this);
    }
  }
  
  public ObjectProperty<EventHandler<ActionEvent>> onClosedActionProperty()
  {
    return this.onClosedActionProperty;
  }
  
  public void setOnClosedAction(EventHandler<ActionEvent> paramEventHandler)
  {
    this.onClosedActionProperty.set(paramEventHandler);
  }
  
  public EventHandler<ActionEvent> getOnClosedAction()
  {
    return (EventHandler)this.onClosedActionProperty.get();
  }
  
  public ObjectProperty<EventHandler<ActionEvent>> onCloseActionProperty()
  {
    return this.onCloseActionProperty;
  }
  
  public void setOnCloseAction(EventHandler<ActionEvent> paramEventHandler)
  {
    this.onCloseActionProperty.set(paramEventHandler);
  }
  
  public EventHandler<ActionEvent> getOnCloseAction()
  {
    return (EventHandler)this.onCloseActionProperty.get();
  }
  
  public ObjectProperty<Transition> closeTransitionProperty()
  {
    return this.closeTransitionProperty;
  }
  
  public void setCloseTransition(Transition paramTransition)
  {
    this.closeTransitionProperty.set(paramTransition);
  }
  
  public Transition getCloseTransition()
  {
    return (Transition)this.closeTransitionProperty.get();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/window/Window.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */