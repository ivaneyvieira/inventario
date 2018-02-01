package jfxtras.labs.scene.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public class RadialMenu
  extends Group
{
  protected List<RadialMenuItem> items = new ArrayList();
  protected DoubleProperty innerRadius;
  protected DoubleProperty radius;
  protected DoubleProperty offset;
  protected DoubleProperty initialAngle;
  protected Paint backgroundFill;
  protected Paint backgroundMouseOnFill;
  protected Paint strokeFill;
  protected BooleanProperty clockwise;
  protected BooleanProperty backgroundVisible;
  protected BooleanProperty strokeVisible;
  protected Node graphic;
  
  public Paint getBackgroundFill()
  {
    return this.backgroundFill;
  }
  
  public void setBackgroundFill(Paint paramPaint)
  {
    this.backgroundFill = paramPaint;
  }
  
  public Paint getBackgroundMouseOnFill()
  {
    return this.backgroundMouseOnFill;
  }
  
  public void setBackgroundMouseOnFill(Paint paramPaint)
  {
    this.backgroundMouseOnFill = paramPaint;
  }
  
  public Paint getStrokeFill()
  {
    return this.strokeFill;
  }
  
  public void setStrokeFill(Paint paramPaint)
  {
    this.strokeFill = paramPaint;
  }
  
  public Node getGraphic()
  {
    return this.graphic;
  }
  
  public void setGraphic(Node paramNode)
  {
    this.graphic = paramNode;
  }
  
  public double getInitialAngle()
  {
    return this.initialAngle.get();
  }
  
  public DoubleProperty initialAngleProperty()
  {
    return this.initialAngle;
  }
  
  public double getInnerRadius()
  {
    return this.innerRadius.get();
  }
  
  public DoubleProperty innerRadiusProperty()
  {
    return this.innerRadius;
  }
  
  public double getRadius()
  {
    return this.radius.get();
  }
  
  public DoubleProperty radiusProperty()
  {
    return this.radius;
  }
  
  public double getOffset()
  {
    return this.offset.get();
  }
  
  public DoubleProperty offsetProperty()
  {
    return this.offset;
  }
  
  public boolean isClockwise()
  {
    return this.clockwise.get();
  }
  
  public BooleanProperty clockwiseProperty()
  {
    return this.clockwise;
  }
  
  public boolean isBackgroundVisible()
  {
    return this.backgroundVisible.get();
  }
  
  public BooleanProperty backgroundVisibleProperty()
  {
    return this.backgroundVisible;
  }
  
  public BooleanProperty strokeVisibleProperty()
  {
    return this.strokeVisible;
  }
  
  public boolean isStrokeVisible()
  {
    return this.strokeVisible.get();
  }
  
  public RadialMenu() {}
  
  public RadialMenu(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, Paint paramPaint1, Paint paramPaint2, Paint paramPaint3, boolean paramBoolean)
  {
    this.initialAngle = new SimpleDoubleProperty(paramDouble1);
    this.initialAngle.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        RadialMenu.this.setInitialAngle(((Number)paramAnonymousObservableValue.getValue()).doubleValue());
      }
    });
    this.innerRadius = new SimpleDoubleProperty(paramDouble2);
    this.radius = new SimpleDoubleProperty(paramDouble3);
    this.offset = new SimpleDoubleProperty(paramDouble4);
    this.clockwise = new SimpleBooleanProperty(paramBoolean);
    this.backgroundFill = paramPaint1;
    this.backgroundMouseOnFill = paramPaint2;
    this.strokeFill = paramPaint3;
    this.strokeVisible = new SimpleBooleanProperty(true);
    this.backgroundVisible = new SimpleBooleanProperty(true);
  }
  
  public void setOnMenuItemMouseClicked(EventHandler<? super MouseEvent> paramEventHandler)
  {
    Iterator localIterator = this.items.iterator();
    while (localIterator.hasNext())
    {
      RadialMenuItem localRadialMenuItem = (RadialMenuItem)localIterator.next();
      localRadialMenuItem.setOnMouseClicked(paramEventHandler);
    }
  }
  
  public void setInitialAngle(double paramDouble)
  {
    this.initialAngle.set(paramDouble);
    double d = this.initialAngle.get();
    Iterator localIterator = this.items.iterator();
    while (localIterator.hasNext())
    {
      RadialMenuItem localRadialMenuItem = (RadialMenuItem)localIterator.next();
      localRadialMenuItem.setStartAngle(d);
      d += localRadialMenuItem.getMenuSize();
    }
  }
  
  public void setInnerRadius(double paramDouble)
  {
    this.innerRadius.set(paramDouble);
  }
  
  public void setRadius(double paramDouble)
  {
    this.radius.set(paramDouble);
  }
  
  public void setOffset(double paramDouble)
  {
    this.offset.set(paramDouble);
  }
  
  public void setBackgroundVisible(boolean paramBoolean)
  {
    this.backgroundVisible.set(paramBoolean);
  }
  
  public void setStrokeVisible(boolean paramBoolean)
  {
    this.strokeVisible.set(paramBoolean);
  }
  
  public void setBackgroundColor(Paint paramPaint)
  {
    this.backgroundFill = paramPaint;
    Iterator localIterator = this.items.iterator();
    while (localIterator.hasNext())
    {
      RadialMenuItem localRadialMenuItem = (RadialMenuItem)localIterator.next();
      localRadialMenuItem.setBackgroundColor(paramPaint);
    }
  }
  
  public void setBackgroundMouseOnColor(Paint paramPaint)
  {
    this.backgroundMouseOnFill = paramPaint;
    Iterator localIterator = this.items.iterator();
    while (localIterator.hasNext())
    {
      RadialMenuItem localRadialMenuItem = (RadialMenuItem)localIterator.next();
      localRadialMenuItem.setBackgroundMouseOnColor(paramPaint);
    }
  }
  
  public void setStrokeColor(Paint paramPaint)
  {
    this.strokeFill = paramPaint;
    Iterator localIterator = this.items.iterator();
    while (localIterator.hasNext())
    {
      RadialMenuItem localRadialMenuItem = (RadialMenuItem)localIterator.next();
      localRadialMenuItem.setStrokeColor(paramPaint);
    }
  }
  
  public void setClockwise(boolean paramBoolean)
  {
    this.clockwise.set(paramBoolean);
    Iterator localIterator = this.items.iterator();
    while (localIterator.hasNext())
    {
      RadialMenuItem localRadialMenuItem = (RadialMenuItem)localIterator.next();
      localRadialMenuItem.setClockwise(paramBoolean);
    }
  }
  
  public void addMenuItem(RadialMenuItem paramRadialMenuItem)
  {
    paramRadialMenuItem.setBackgroundColor(this.backgroundFill);
    paramRadialMenuItem.setBackgroundMouseOnColor(this.backgroundMouseOnFill);
    paramRadialMenuItem.innerRadiusProperty().bind(this.innerRadius);
    paramRadialMenuItem.radiusProperty().bind(this.radius);
    paramRadialMenuItem.offsetProperty().bind(this.offset);
    paramRadialMenuItem.setStrokeColor(this.strokeFill);
    paramRadialMenuItem.setClockwise(this.clockwise.get());
    this.items.add(paramRadialMenuItem);
    getChildren().add(paramRadialMenuItem);
    double d = this.initialAngle.get();
    Iterator localIterator = this.items.iterator();
    while (localIterator.hasNext())
    {
      RadialMenuItem localRadialMenuItem = (RadialMenuItem)localIterator.next();
      localRadialMenuItem.setStartAngle(d);
      d += paramRadialMenuItem.getMenuSize();
    }
  }
  
  public List<RadialMenuItem> getMenuItems()
  {
    return this.items;
  }
  
  public void removeMenuItem(RadialMenuItem paramRadialMenuItem)
  {
    this.items.remove(paramRadialMenuItem);
    getChildren().remove(paramRadialMenuItem);
    paramRadialMenuItem.innerRadiusProperty().unbind();
    paramRadialMenuItem.radiusProperty().unbind();
    paramRadialMenuItem.offsetProperty().unbind();
  }
  
  public void removeMenuItem(int paramInt)
  {
    RadialMenuItem localRadialMenuItem = (RadialMenuItem)this.items.remove(paramInt);
    getChildren().remove(localRadialMenuItem);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/RadialMenu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */