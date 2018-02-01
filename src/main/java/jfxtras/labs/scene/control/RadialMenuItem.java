package jfxtras.labs.scene.control;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class RadialMenuItem
  extends Group
  implements ChangeListener<Number>
{
  protected double startAngle;
  protected double menuSize;
  protected DoubleProperty innerRadius = new SimpleDoubleProperty();
  protected DoubleProperty radius = new SimpleDoubleProperty();
  protected DoubleProperty offset = new SimpleDoubleProperty();
  protected Paint computedBackgroundFill;
  protected Paint backgroundMouseOnColor;
  protected Paint backgroundColor;
  protected boolean backgroundVisible = true;
  protected boolean strokeVisible = true;
  protected boolean clockWise;
  protected Paint strokeColor;
  protected MoveTo moveTo;
  protected ArcTo arcToInner;
  protected ArcTo arcTo;
  protected LineTo lineTo;
  protected LineTo lineTo2;
  protected double innerStartX;
  protected double innerStartY;
  protected double innerEndX;
  protected double innerEndY;
  protected boolean innerSweep;
  protected double startX;
  protected double startY;
  protected double endX;
  protected double endY;
  protected boolean sweep;
  protected double graphicX;
  protected double graphicY;
  protected double translateX;
  protected double translateY;
  protected boolean mouseOn = false;
  protected Path path;
  protected Node graphic;
  protected String text;
  
  DoubleProperty innerRadiusProperty()
  {
    return this.innerRadius;
  }
  
  DoubleProperty radiusProperty()
  {
    return this.radius;
  }
  
  DoubleProperty offsetProperty()
  {
    return this.offset;
  }
  
  Paint getBackgroundMouseOnColor()
  {
    return this.backgroundMouseOnColor;
  }
  
  void setBackgroundMouseOnColor(Paint paramPaint)
  {
    this.backgroundMouseOnColor = paramPaint;
    redraw();
  }
  
  Paint getBackgroundColor()
  {
    return this.backgroundColor;
  }
  
  void setBackgroundColor(Paint paramPaint)
  {
    this.backgroundColor = paramPaint;
    redraw();
  }
  
  boolean isClockwise()
  {
    return this.clockWise;
  }
  
  void setClockwise(boolean paramBoolean)
  {
    this.clockWise = paramBoolean;
    redraw();
  }
  
  Paint getStrokeColor()
  {
    return this.strokeColor;
  }
  
  void setStrokeColor(Paint paramPaint)
  {
    this.strokeColor = paramPaint;
    redraw();
  }
  
  void setBackgroundVisible(boolean paramBoolean)
  {
    this.backgroundVisible = paramBoolean;
    redraw();
  }
  
  boolean isBackgroundVisible()
  {
    return this.backgroundVisible;
  }
  
  void setStrokeVisible(boolean paramBoolean)
  {
    this.strokeVisible = paramBoolean;
    redraw();
  }
  
  boolean isStrokeVisible()
  {
    return this.strokeVisible;
  }
  
  public Node getGraphic()
  {
    return this.graphic;
  }
  
  public void setStartAngle(double paramDouble)
  {
    this.startAngle = paramDouble;
    redraw();
  }
  
  public void setGraphic(Node paramNode)
  {
    if (this.graphic != null) {
      getChildren().remove(paramNode);
    }
    this.graphic = paramNode;
    if (this.graphic != null) {
      getChildren().add(paramNode);
    }
    redraw();
  }
  
  public void setText(String paramString)
  {
    this.text = paramString;
    redraw();
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public RadialMenuItem()
  {
    this.innerRadius.addListener(this);
    this.radius.addListener(this);
    this.offset.addListener(this);
    this.path = new Path();
    this.moveTo = new MoveTo();
    this.arcToInner = new ArcTo();
    this.arcTo = new ArcTo();
    this.lineTo = new LineTo();
    this.lineTo2 = new LineTo();
    this.path.getElements().add(this.moveTo);
    this.path.getElements().add(this.arcToInner);
    this.path.getElements().add(this.lineTo);
    this.path.getElements().add(this.arcTo);
    this.path.getElements().add(this.lineTo2);
    getChildren().add(this.path);
    setOnMouseEntered(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        RadialMenuItem.this.mouseOn = true;
        RadialMenuItem.this.redraw();
      }
    });
    setOnMouseExited(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        RadialMenuItem.this.mouseOn = false;
        RadialMenuItem.this.redraw();
      }
    });
  }
  
  public RadialMenuItem(double paramDouble, Node paramNode)
  {
    this();
    this.menuSize = paramDouble;
    this.graphic = paramNode;
    if (this.graphic != null) {
      getChildren().add(this.graphic);
    }
    redraw();
  }
  
  public RadialMenuItem(double paramDouble, Node paramNode, final EventHandler<ActionEvent> paramEventHandler)
  {
    this(paramDouble, paramNode);
    setOnMouseClicked(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        paramEventHandler.handle(new ActionEvent(paramAnonymousMouseEvent.getSource(), paramAnonymousMouseEvent.getTarget()));
      }
    });
    redraw();
  }
  
  public RadialMenuItem(double paramDouble, String paramString, Node paramNode, EventHandler<ActionEvent> paramEventHandler)
  {
    this(paramDouble, paramNode, paramEventHandler);
    this.text = paramString;
    redraw();
  }
  
  protected void redraw()
  {
    this.path.setFill(this.backgroundVisible ? this.backgroundColor : (this.mouseOn) && (this.backgroundMouseOnColor != null) ? this.backgroundMouseOnColor : null);
    this.path.setStroke(this.strokeVisible ? this.strokeColor : null);
    this.path.setFillRule(FillRule.EVEN_ODD);
    computeCoordinates();
    update();
  }
  
  protected void update()
  {
    double d1 = this.innerRadius.get();
    double d2 = this.radius.get();
    this.moveTo.setX(this.innerStartX);
    this.moveTo.setY(this.innerStartY);
    this.arcToInner.setX(this.innerEndX);
    this.arcToInner.setY(this.innerEndY);
    this.arcToInner.setSweepFlag(this.innerSweep);
    this.arcToInner.setRadiusX(d1);
    this.arcToInner.setRadiusY(d1);
    this.lineTo.setX(this.startX);
    this.lineTo.setY(this.startY);
    this.arcTo.setX(this.endX);
    this.arcTo.setY(this.endY);
    this.arcTo.setSweepFlag(this.sweep);
    this.arcTo.setRadiusX(d2);
    this.arcTo.setRadiusY(d2);
    this.lineTo2.setX(this.innerStartX);
    this.lineTo2.setY(this.innerStartY);
    if (this.graphic != null)
    {
      this.graphic.setTranslateX(this.graphicX);
      this.graphic.setTranslateY(this.graphicY);
    }
    translateXProperty().set(this.translateX);
    translateYProperty().set(this.translateY);
  }
  
  protected void computeCoordinates()
  {
    double d1 = this.innerRadius.get();
    double d2 = this.startAngle;
    double d3 = d2 + this.menuSize / 2.0D;
    double d4 = this.radius.get();
    double d5 = d1 + (d4 - d1) / 2.0D;
    double d6 = this.offset.get();
    if (!this.clockWise)
    {
      this.innerStartX = (d1 * Math.cos(Math.toRadians(d2)));
      this.innerStartY = (-d1 * Math.sin(Math.toRadians(d2)));
      this.innerEndX = (d1 * Math.cos(Math.toRadians(d2 + this.menuSize)));
      this.innerEndY = (-d1 * Math.sin(Math.toRadians(d2 + this.menuSize)));
      this.innerSweep = false;
      this.startX = (d4 * Math.cos(Math.toRadians(d2 + this.menuSize)));
      this.startY = (-d4 * Math.sin(Math.toRadians(d2 + this.menuSize)));
      this.endX = (d4 * Math.cos(Math.toRadians(d2)));
      this.endY = (-d4 * Math.sin(Math.toRadians(d2)));
      this.sweep = true;
      this.graphicX = (d5 * Math.cos(Math.toRadians(d3)) - this.graphic.getBoundsInParent().getWidth() / 2.0D);
      this.graphicY = (-d5 * Math.sin(Math.toRadians(d3)) - this.graphic.getBoundsInParent().getHeight() / 2.0D);
      this.translateX = (d6 * Math.cos(Math.toRadians(d2 + this.menuSize / 2.0D)));
      this.translateY = (-d6 * Math.sin(Math.toRadians(d2 + this.menuSize / 2.0D)));
    }
    else if (this.clockWise)
    {
      this.innerStartX = (d1 * Math.cos(Math.toRadians(d2)));
      this.innerStartY = (d1 * Math.sin(Math.toRadians(d2)));
      this.innerEndX = (d1 * Math.cos(Math.toRadians(d2 + this.menuSize)));
      this.innerEndY = (d1 * Math.sin(Math.toRadians(d2 + this.menuSize)));
      this.innerSweep = true;
      this.startX = (d4 * Math.cos(Math.toRadians(d2 + this.menuSize)));
      this.startY = (d4 * Math.sin(Math.toRadians(d2 + this.menuSize)));
      this.endX = (d4 * Math.cos(Math.toRadians(d2)));
      this.endY = (d4 * Math.sin(Math.toRadians(d2)));
      this.sweep = false;
      this.graphicX = (d5 * Math.cos(Math.toRadians(d3)) - this.graphic.getBoundsInParent().getWidth() / 2.0D);
      this.graphicY = (d5 * Math.sin(Math.toRadians(d3)) - this.graphic.getBoundsInParent().getHeight() / 2.0D);
      this.translateX = (d6 * Math.cos(Math.toRadians(d2 + this.menuSize / 2.0D)));
      this.translateY = (d6 * Math.sin(Math.toRadians(d2 + this.menuSize / 2.0D)));
    }
  }
  
  public double getMenuSize()
  {
    return this.menuSize;
  }
  
  public void changed(ObservableValue<? extends Number> paramObservableValue, Number paramNumber1, Number paramNumber2)
  {
    redraw();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/RadialMenuItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */