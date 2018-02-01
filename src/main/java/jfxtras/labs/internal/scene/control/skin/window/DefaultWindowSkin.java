package jfxtras.labs.internal.scene.control.skin.window;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.Iterator;
import java.util.List;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.util.Duration;
import jfxtras.labs.scene.control.window.Window;
import jfxtras.labs.scene.control.window.WindowIcon;

public class DefaultWindowSkin
  extends SkinBase<Window, BehaviorBase<Window>>
{
  private double mouseX;
  private double mouseY;
  private double nodeX = 0.0D;
  private double nodeY = 0.0D;
  private boolean dragging = false;
  private boolean zoomable = true;
  private double minScale = 0.1D;
  private double maxScale = 10.0D;
  private double scaleIncrement = 0.001D;
  private ResizeMode resizeMode;
  private boolean RESIZE_TOP;
  private boolean RESIZE_LEFT;
  private boolean RESIZE_BOTTOM;
  private boolean RESIZE_RIGHT;
  private TitleBar titleBar;
  private Window control;
  private Pane root = new Pane();
  private double contentScale = 1.0D;
  private double oldHeight;
  private Timeline minimizeTimeLine;
  
  public DefaultWindowSkin(Window paramWindow)
  {
    super(paramWindow, new BehaviorBase(paramWindow));
    this.control = paramWindow;
    this.titleBar = new TitleBar(this.control);
    this.titleBar.setTitle("");
    init();
  }
  
  private void init()
  {
    getChildren().add(this.root);
    this.root.getChildren().add(this.titleBar);
    Iterator localIterator = this.control.getLeftIcons().iterator();
    WindowIcon localWindowIcon;
    while (localIterator.hasNext())
    {
      localWindowIcon = (WindowIcon)localIterator.next();
      this.titleBar.addLeftIcon(localWindowIcon);
    }
    localIterator = this.control.getRightIcons().iterator();
    while (localIterator.hasNext())
    {
      localWindowIcon = (WindowIcon)localIterator.next();
      this.titleBar.addRightIcon(localWindowIcon);
    }
    this.control.getLeftIcons().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends WindowIcon> paramAnonymousChange)
      {
        while (paramAnonymousChange.next()) {
          if (paramAnonymousChange.wasPermutated())
          {
            for (int i = paramAnonymousChange.getFrom(); i < paramAnonymousChange.getTo(); i++) {}
          }
          else if (!paramAnonymousChange.wasUpdated())
          {
            Iterator localIterator;
            WindowIcon localWindowIcon;
            if (paramAnonymousChange.wasRemoved())
            {
              localIterator = paramAnonymousChange.getRemoved().iterator();
              while (localIterator.hasNext())
              {
                localWindowIcon = (WindowIcon)localIterator.next();
                DefaultWindowSkin.this.titleBar.removeLeftIcon(localWindowIcon);
              }
            }
            else if (paramAnonymousChange.wasAdded())
            {
              localIterator = paramAnonymousChange.getAddedSubList().iterator();
              while (localIterator.hasNext())
              {
                localWindowIcon = (WindowIcon)localIterator.next();
                DefaultWindowSkin.this.titleBar.addLeftIcon(localWindowIcon);
              }
            }
          }
        }
      }
    });
    this.control.getRightIcons().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends WindowIcon> paramAnonymousChange)
      {
        while (paramAnonymousChange.next()) {
          if (paramAnonymousChange.wasPermutated())
          {
            for (int i = paramAnonymousChange.getFrom(); i < paramAnonymousChange.getTo(); i++) {}
          }
          else if (!paramAnonymousChange.wasUpdated())
          {
            Iterator localIterator;
            WindowIcon localWindowIcon;
            if (paramAnonymousChange.wasRemoved())
            {
              localIterator = paramAnonymousChange.getRemoved().iterator();
              while (localIterator.hasNext())
              {
                localWindowIcon = (WindowIcon)localIterator.next();
                DefaultWindowSkin.this.titleBar.removeRightIcon(localWindowIcon);
              }
            }
            else if (paramAnonymousChange.wasAdded())
            {
              localIterator = paramAnonymousChange.getAddedSubList().iterator();
              while (localIterator.hasNext())
              {
                localWindowIcon = (WindowIcon)localIterator.next();
                DefaultWindowSkin.this.titleBar.addRightIcon(localWindowIcon);
              }
            }
          }
        }
      }
    });
    this.control.minimizedProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, final Boolean paramAnonymousBoolean2)
      {
        if (paramAnonymousBoolean1 == paramAnonymousBoolean2) {
          return;
        }
        int i = (DefaultWindowSkin.this.minimizeTimeLine == null) && (paramAnonymousBoolean2.booleanValue()) ? 1 : 0;
        if (DefaultWindowSkin.this.minimizeTimeLine != null)
        {
          DefaultWindowSkin.this.minimizeTimeLine.stop();
          DefaultWindowSkin.this.minimizeTimeLine = null;
        }
        double d;
        if (paramAnonymousBoolean2.booleanValue()) {
          d = DefaultWindowSkin.this.titleBar.getHeight();
        } else {
          d = DefaultWindowSkin.this.oldHeight;
        }
        if (i != 0) {
          DefaultWindowSkin.this.oldHeight = DefaultWindowSkin.this.control.getPrefHeight();
        }
        DefaultWindowSkin.this.minimizeTimeLine = new Timeline(new KeyFrame[] { new KeyFrame(Duration.ZERO, new KeyValue[] { new KeyValue(DefaultWindowSkin.this.control.prefHeightProperty(), Double.valueOf(DefaultWindowSkin.this.control.getPrefHeight())) }), new KeyFrame(Duration.seconds(0.2D), new KeyValue[] { new KeyValue(DefaultWindowSkin.this.control.prefHeightProperty(), Double.valueOf(d)) }) });
        DefaultWindowSkin.this.minimizeTimeLine.statusProperty().addListener(new ChangeListener()
        {
          public void changed(ObservableValue<? extends Animation.Status> paramAnonymous2ObservableValue, Animation.Status paramAnonymous2Status1, Animation.Status paramAnonymous2Status2)
          {
            if (paramAnonymous2Status2 == Animation.Status.STOPPED)
            {
              DefaultWindowSkin.this.minimizeTimeLine = null;
              if (paramAnonymousBoolean2.booleanValue()) {
                DefaultWindowSkin.this.control.getContentPane().setVisible(false);
              }
            }
          }
        });
        DefaultWindowSkin.this.minimizeTimeLine.play();
      }
    });
    this.control.prefHeightProperty().addListener(new MinimizeHeightListener(this.control, this.titleBar));
    initMouseEventHandlers();
    this.titleBar.setTitle(this.control.getTitle());
    this.control.titleProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends String> paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2)
      {
        DefaultWindowSkin.this.titleBar.setTitle(paramAnonymousString2);
        DefaultWindowSkin.this.control.autosize();
      }
    });
    this.root.getChildren().add(this.control.getContentPane());
    this.control.getContentPane().setManaged(false);
    this.control.contentPaneProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Pane> paramAnonymousObservableValue, Pane paramAnonymousPane1, Pane paramAnonymousPane2)
      {
        DefaultWindowSkin.this.root.getChildren().remove(paramAnonymousPane1);
        DefaultWindowSkin.this.root.getChildren().add(paramAnonymousPane2);
        paramAnonymousPane2.setManaged(false);
      }
    });
    this.titleBar.setStyle(this.control.getStyle());
    this.control.styleProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends String> paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2)
      {
        DefaultWindowSkin.this.titleBar.setStyle(paramAnonymousString2);
      }
    });
    this.titleBar.getStyleClass().setAll(new String[] { this.control.getTitleBarStyleClass() });
    this.titleBar.getLabel().getStyleClass().setAll(new String[] { this.control.getTitleBarStyleClass() });
    this.control.titleBarStyleClassProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends String> paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2)
      {
        DefaultWindowSkin.this.titleBar.getStyleClass().setAll(new String[] { paramAnonymousString2 });
        DefaultWindowSkin.this.titleBar.getLabel().getStyleClass().setAll(new String[] { paramAnonymousString2 });
      }
    });
    this.titleBar.getStylesheets().setAll(this.control.getStylesheets());
    this.control.getStylesheets().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends String> paramAnonymousChange)
      {
        while (paramAnonymousChange.next()) {
          if (paramAnonymousChange.wasPermutated())
          {
            for (int i = paramAnonymousChange.getFrom(); i < paramAnonymousChange.getTo(); i++) {}
          }
          else if (!paramAnonymousChange.wasUpdated())
          {
            Iterator localIterator;
            String str;
            if (paramAnonymousChange.wasRemoved())
            {
              localIterator = paramAnonymousChange.getRemoved().iterator();
              while (localIterator.hasNext())
              {
                str = (String)localIterator.next();
                DefaultWindowSkin.this.titleBar.getStylesheets().remove(str);
              }
            }
            else if (paramAnonymousChange.wasAdded())
            {
              localIterator = paramAnonymousChange.getAddedSubList().iterator();
              while (localIterator.hasNext())
              {
                str = (String)localIterator.next();
                DefaultWindowSkin.this.titleBar.getStylesheets().add(str);
              }
            }
          }
        }
      }
    });
  }
  
  private void initMouseEventHandlers()
  {
    this.control.onMousePressedProperty().set(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        Window localWindow = DefaultWindowSkin.this.control;
        double d1 = ((Transform)localWindow.getParent().localToSceneTransformProperty().getValue()).getMxx();
        double d2 = ((Transform)localWindow.getParent().localToSceneTransformProperty().getValue()).getMyy();
        DefaultWindowSkin.this.mouseX = paramAnonymousMouseEvent.getSceneX();
        DefaultWindowSkin.this.mouseY = paramAnonymousMouseEvent.getSceneY();
        DefaultWindowSkin.this.nodeX = (localWindow.getLayoutX() * d1);
        DefaultWindowSkin.this.nodeY = (localWindow.getLayoutY() * d2);
        if (DefaultWindowSkin.this.control.isMoveToFront()) {
          DefaultWindowSkin.this.control.toFront();
        }
      }
    });
    this.control.onMouseDraggedProperty().set(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        Window localWindow = DefaultWindowSkin.this.control;
        double d1 = ((Transform)localWindow.getParent().localToSceneTransformProperty().getValue()).getMxx();
        double d2 = ((Transform)localWindow.getParent().localToSceneTransformProperty().getValue()).getMyy();
        double d3 = ((Transform)localWindow.localToSceneTransformProperty().getValue()).getMxx();
        double d4 = ((Transform)localWindow.localToSceneTransformProperty().getValue()).getMyy();
        Bounds localBounds = DefaultWindowSkin.this.control.localToScene(DefaultWindowSkin.this.control.getBoundsInLocal());
        double d5 = localBounds.getMinX();
        double d6 = localBounds.getMinY();
        double d7 = paramAnonymousMouseEvent.getSceneX() - DefaultWindowSkin.this.mouseX;
        double d8 = paramAnonymousMouseEvent.getSceneY() - DefaultWindowSkin.this.mouseY;
        double d9;
        double d10;
        if (DefaultWindowSkin.this.resizeMode == ResizeMode.NONE)
        {
          DefaultWindowSkin.access$718(DefaultWindowSkin.this, d7);
          DefaultWindowSkin.access$818(DefaultWindowSkin.this, d8);
          d9 = DefaultWindowSkin.this.nodeX * 1.0D / d1;
          d10 = DefaultWindowSkin.this.nodeY * 1.0D / d2;
          localWindow.setLayoutX(d9);
          localWindow.setLayoutY(d10);
          DefaultWindowSkin.this.dragging = true;
        }
        else
        {
          d9 = localWindow.getBoundsInLocal().getMaxX() - localWindow.getBoundsInLocal().getMinX();
          d10 = localWindow.getBoundsInLocal().getMaxY() - localWindow.getBoundsInLocal().getMinY();
          double d11;
          double d12;
          double d13;
          if (DefaultWindowSkin.this.RESIZE_TOP)
          {
            d11 = DefaultWindowSkin.this.getInsets().getTop() / 2.0D;
            d12 = d6 / d2 + d11 - paramAnonymousMouseEvent.getSceneY() / d2;
            d13 = DefaultWindowSkin.this.control.getPrefHeight() + d12;
            if (d13 > DefaultWindowSkin.this.control.minHeight(0.0D))
            {
              DefaultWindowSkin.this.control.setLayoutY(DefaultWindowSkin.this.control.getLayoutY() - d12);
              DefaultWindowSkin.this.control.setPrefHeight(d13);
            }
          }
          if (DefaultWindowSkin.this.RESIZE_LEFT)
          {
            d11 = DefaultWindowSkin.this.getInsets().getLeft() / 2.0D;
            d12 = d5 / d1 + d11 - paramAnonymousMouseEvent.getSceneX() / d1;
            d13 = DefaultWindowSkin.this.control.getPrefWidth() + d12;
            if (d13 > DefaultWindowSkin.this.control.minWidth(0.0D))
            {
              DefaultWindowSkin.this.control.setLayoutX(DefaultWindowSkin.this.control.getLayoutX() - d12);
              DefaultWindowSkin.this.control.setPrefWidth(d13);
            }
          }
          if (DefaultWindowSkin.this.RESIZE_BOTTOM)
          {
            d11 = DefaultWindowSkin.this.getInsets().getBottom() / 2.0D;
            d12 = paramAnonymousMouseEvent.getSceneY() / d2 - d6 / d2 - d11;
            d13 = d12;
            d13 = Math.max(d13, DefaultWindowSkin.this.control.minHeight(0.0D));
            if (d13 < DefaultWindowSkin.this.control.maxHeight(0.0D)) {
              DefaultWindowSkin.this.control.setPrefHeight(d13);
            }
          }
          if (DefaultWindowSkin.this.RESIZE_RIGHT)
          {
            d11 = DefaultWindowSkin.this.getInsets().getRight() / 2.0D;
            d12 = paramAnonymousMouseEvent.getSceneX() / d1 - d5 / d2 - d11;
            d13 = d12;
            d13 = Math.max(d13, DefaultWindowSkin.this.control.minWidth(0.0D));
            if (d13 < DefaultWindowSkin.this.control.maxWidth(0.0D)) {
              DefaultWindowSkin.this.control.setPrefWidth(d13);
            }
          }
        }
        DefaultWindowSkin.this.mouseX = paramAnonymousMouseEvent.getSceneX();
        DefaultWindowSkin.this.mouseY = paramAnonymousMouseEvent.getSceneY();
        paramAnonymousMouseEvent.consume();
      }
    });
    this.control.onMouseClickedProperty().set(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        DefaultWindowSkin.this.dragging = false;
      }
    });
    this.control.onMouseMovedProperty().set(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (DefaultWindowSkin.this.control.isMinimized())
        {
          DefaultWindowSkin.this.RESIZE_TOP = false;
          DefaultWindowSkin.this.RESIZE_LEFT = false;
          DefaultWindowSkin.this.RESIZE_BOTTOM = false;
          DefaultWindowSkin.this.RESIZE_RIGHT = false;
          DefaultWindowSkin.this.resizeMode = ResizeMode.NONE;
          return;
        }
        Window localWindow = DefaultWindowSkin.this.control;
        double d1 = ((Transform)localWindow.getParent().localToSceneTransformProperty().getValue()).getMxx();
        double d2 = ((Transform)localWindow.getParent().localToSceneTransformProperty().getValue()).getMyy();
        double d3 = ((Transform)localWindow.localToSceneTransformProperty().getValue()).getMxx();
        double d4 = ((Transform)localWindow.localToSceneTransformProperty().getValue()).getMyy();
        double d5 = DefaultWindowSkin.this.control.getResizableBorderWidth() * d3;
        double d6 = Math.abs(localWindow.getLayoutBounds().getMinX() - paramAnonymousMouseEvent.getX() + DefaultWindowSkin.this.getInsets().getLeft());
        double d7 = Math.abs(localWindow.getLayoutBounds().getMinY() - paramAnonymousMouseEvent.getY() + DefaultWindowSkin.this.getInsets().getTop());
        double d8 = Math.abs(localWindow.getLayoutBounds().getMaxX() - paramAnonymousMouseEvent.getX() - DefaultWindowSkin.this.getInsets().getRight());
        double d9 = Math.abs(localWindow.getLayoutBounds().getMaxY() - paramAnonymousMouseEvent.getY() - DefaultWindowSkin.this.getInsets().getBottom());
        int i = d6 * d3 < Math.max(d5, DefaultWindowSkin.this.getInsets().getLeft() / 2.0D * d3) ? 1 : 0;
        int j = d7 * d4 < Math.max(d5, DefaultWindowSkin.this.getInsets().getTop() / 2.0D * d4) ? 1 : 0;
        int k = d8 * d3 < Math.max(d5, DefaultWindowSkin.this.getInsets().getRight() / 2.0D * d3) ? 1 : 0;
        int m = d9 * d4 < Math.max(d5, DefaultWindowSkin.this.getInsets().getBottom() / 2.0D * d4) ? 1 : 0;
        DefaultWindowSkin.this.RESIZE_TOP = false;
        DefaultWindowSkin.this.RESIZE_LEFT = false;
        DefaultWindowSkin.this.RESIZE_BOTTOM = false;
        DefaultWindowSkin.this.RESIZE_RIGHT = false;
        if ((i != 0) && (j == 0) && (m == 0))
        {
          localWindow.setCursor(Cursor.W_RESIZE);
          DefaultWindowSkin.this.resizeMode = ResizeMode.LEFT;
          DefaultWindowSkin.this.RESIZE_LEFT = true;
        }
        else if ((i != 0) && (j != 0) && (m == 0))
        {
          localWindow.setCursor(Cursor.NW_RESIZE);
          DefaultWindowSkin.this.resizeMode = ResizeMode.TOP_LEFT;
          DefaultWindowSkin.this.RESIZE_LEFT = true;
          DefaultWindowSkin.this.RESIZE_TOP = true;
        }
        else if ((i != 0) && (j == 0) && (m != 0))
        {
          localWindow.setCursor(Cursor.SW_RESIZE);
          DefaultWindowSkin.this.resizeMode = ResizeMode.BOTTOM_LEFT;
          DefaultWindowSkin.this.RESIZE_LEFT = true;
          DefaultWindowSkin.this.RESIZE_BOTTOM = true;
        }
        else if ((k != 0) && (j == 0) && (m == 0))
        {
          localWindow.setCursor(Cursor.E_RESIZE);
          DefaultWindowSkin.this.resizeMode = ResizeMode.RIGHT;
          DefaultWindowSkin.this.RESIZE_RIGHT = true;
        }
        else if ((k != 0) && (j != 0) && (m == 0))
        {
          localWindow.setCursor(Cursor.NE_RESIZE);
          DefaultWindowSkin.this.resizeMode = ResizeMode.TOP_RIGHT;
          DefaultWindowSkin.this.RESIZE_RIGHT = true;
          DefaultWindowSkin.this.RESIZE_TOP = true;
        }
        else if ((k != 0) && (j == 0) && (m != 0))
        {
          localWindow.setCursor(Cursor.SE_RESIZE);
          DefaultWindowSkin.this.resizeMode = ResizeMode.BOTTOM_RIGHT;
          DefaultWindowSkin.this.RESIZE_RIGHT = true;
          DefaultWindowSkin.this.RESIZE_BOTTOM = true;
        }
        else if ((j != 0) && (i == 0) && (k == 0))
        {
          localWindow.setCursor(Cursor.N_RESIZE);
          DefaultWindowSkin.this.resizeMode = ResizeMode.TOP;
          DefaultWindowSkin.this.RESIZE_TOP = true;
        }
        else if ((m != 0) && (i == 0) && (k == 0))
        {
          localWindow.setCursor(Cursor.S_RESIZE);
          DefaultWindowSkin.this.resizeMode = ResizeMode.BOTTOM;
          DefaultWindowSkin.this.RESIZE_BOTTOM = true;
        }
        else
        {
          localWindow.setCursor(Cursor.DEFAULT);
          DefaultWindowSkin.this.resizeMode = ResizeMode.NONE;
        }
        DefaultWindowSkin.this.control.autosize();
      }
    });
  }
  
  public boolean isZoomable()
  {
    return this.zoomable;
  }
  
  public void setZoomable(boolean paramBoolean)
  {
    this.zoomable = paramBoolean;
  }
  
  protected boolean isDragging()
  {
    return this.dragging;
  }
  
  public void removeNode(Node paramNode)
  {
    getChildren().remove(paramNode);
  }
  
  public double getMinScale()
  {
    return this.minScale;
  }
  
  public void setMinScale(double paramDouble)
  {
    this.minScale = paramDouble;
  }
  
  public double getMaxScale()
  {
    return this.maxScale;
  }
  
  public void setMaxScale(double paramDouble)
  {
    this.maxScale = paramDouble;
  }
  
  public double getScaleIncrement()
  {
    return this.scaleIncrement;
  }
  
  public void setScaleIncrement(double paramDouble)
  {
    this.scaleIncrement = paramDouble;
  }
  
  protected void layoutChildren()
  {
    super.layoutChildren();
    this.root.relocate(0.0D, 0.0D);
    this.root.resize(this.root.getWidth() + getInsets().getLeft() + getInsets().getRight(), this.root.getHeight() + getInsets().getTop() + getInsets().getBottom());
    this.titleBar.relocate(0.0D, 0.0D);
    double d1 = this.titleBar.prefWidth(0.0D);
    double d2 = this.root.getWidth();
    if (d1 > d2) {
      setWidth(d1);
    }
    double d3 = Math.max(d1, d2);
    this.titleBar.resize(d3, this.titleBar.prefHeight(0.0D));
    double d4 = getInsets().getLeft() + getInsets().getRight();
    double d5 = getInsets().getTop() + getInsets().getBottom();
    this.control.getContentPane().relocate(getInsets().getLeft(), this.titleBar.prefHeight(0.0D));
    this.control.getContentPane().resize(this.root.getWidth() - d4, this.root.getHeight() - getInsets().getBottom() - this.titleBar.prefHeight(0.0D));
    this.titleBar.layoutChildren();
  }
  
  protected double computeMinWidth(double paramDouble)
  {
    double d = this.root.minWidth(paramDouble);
    d = Math.max(d, this.titleBar.prefWidth(paramDouble));
    return d;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    return computeMinWidth(paramDouble);
  }
  
  protected double computeMinHeight(double paramDouble)
  {
    double d1 = this.root.minHeight(paramDouble);
    double d2 = this.titleBar.prefHeight(paramDouble);
    if ((!this.control.isMinimized()) && (this.control.getContentPane().isVisible())) {
      d2 += this.control.getContentPane().minHeight(paramDouble) + getInsets().getBottom();
    }
    d1 = Math.max(d1, d2);
    return d1;
  }
  
  static class MinimizeHeightListener
    implements ChangeListener<Number>
  {
    private Window control;
    private TitleBar titleBar;
    
    public MinimizeHeightListener(Window paramWindow, TitleBar paramTitleBar)
    {
      this.control = paramWindow;
      this.titleBar = paramTitleBar;
    }
    
    public void changed(ObservableValue<? extends Number> paramObservableValue, Number paramNumber1, Number paramNumber2)
    {
      if ((this.control.isMinimized()) && (this.control.getPrefHeight() < this.titleBar.minHeight(0.0D) + this.control.getContentPane().minHeight(0.0D))) {
        this.control.getContentPane().setVisible(false);
      } else if ((!this.control.isMinimized()) && (this.control.getPrefHeight() >= this.titleBar.minHeight(0.0D) + this.control.getContentPane().minHeight(0.0D))) {
        this.control.getContentPane().setVisible(true);
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/window/DefaultWindowSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */