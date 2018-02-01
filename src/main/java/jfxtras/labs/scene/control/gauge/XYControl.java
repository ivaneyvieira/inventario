package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class XYControl
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "xy-control";
  private DoubleProperty xValue = new SimpleDoubleProperty(0.0D);
  private DoubleProperty yValue = new SimpleDoubleProperty(0.0D);
  private StringProperty xAxisLabel = new SimpleStringProperty("x");
  private StringProperty yAxisLabel = new SimpleStringProperty("y");
  private BooleanProperty xAxisLabelVisible = new SimpleBooleanProperty(false);
  private BooleanProperty yAxisLabelVisible = new SimpleBooleanProperty(false);
  private ObjectProperty<Sensitivity> sensitivity = new SimpleObjectProperty(Sensitivity.COARSE);
  private ObjectProperty<Point2D> position = new SimpleObjectProperty(new Point2D(this.xValue.get(), this.yValue.get()));
  
  public XYControl()
  {
    getStyleClass().add("xy-control");
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 < paramDouble2 ? paramDouble1 : paramDouble2;
    super.setPrefSize(d, d);
  }
  
  public final double getXValue()
  {
    return this.xValue.get();
  }
  
  public final void setXValue(double paramDouble)
  {
    validateValue(paramDouble, this.xValue);
  }
  
  public final DoubleProperty xValueProperty()
  {
    return this.xValue;
  }
  
  public final double getYValue()
  {
    return this.yValue.get();
  }
  
  public final void setYValue(double paramDouble)
  {
    validateValue(paramDouble, this.yValue);
  }
  
  public final DoubleProperty yValueProperty()
  {
    return this.yValue;
  }
  
  public final String getXAxisLabel()
  {
    return (String)this.xAxisLabel.get();
  }
  
  public final void setXAxisLabel(String paramString)
  {
    this.xAxisLabel.set(paramString);
  }
  
  public final StringProperty xAxisLabelProperty()
  {
    return this.xAxisLabel;
  }
  
  public final String getYAxisLabel()
  {
    return (String)this.yAxisLabel.get();
  }
  
  public final void setYAxisLabel(String paramString)
  {
    this.yAxisLabel.set(paramString);
  }
  
  public final StringProperty yAxisLabelProperty()
  {
    return this.yAxisLabel;
  }
  
  public final boolean isXAxisLabelVisible()
  {
    return this.xAxisLabelVisible.get();
  }
  
  public final void setXAxisLabelVisible(boolean paramBoolean)
  {
    this.xAxisLabelVisible.set(paramBoolean);
  }
  
  public final BooleanProperty xAxisLabelVisibleProperty()
  {
    return this.xAxisLabelVisible;
  }
  
  public final boolean isYAxisLabelVisible()
  {
    return this.yAxisLabelVisible.get();
  }
  
  public final void setYAxisLabelVisible(boolean paramBoolean)
  {
    this.yAxisLabelVisible.set(paramBoolean);
  }
  
  public final BooleanProperty yAxisLabelVisibleProperty()
  {
    return this.yAxisLabelVisible;
  }
  
  public final Sensitivity getSensitivity()
  {
    return (Sensitivity)this.sensitivity.get();
  }
  
  public final void setSensitivity(Sensitivity paramSensitivity)
  {
    this.sensitivity.set(paramSensitivity);
  }
  
  public final ObjectProperty<Sensitivity> sensitivityProperty()
  {
    return this.sensitivity;
  }
  
  public final Point2D getPosition()
  {
    return (Point2D)this.position.get();
  }
  
  public final void setPosition(double paramDouble1, double paramDouble2)
  {
    setXValue(paramDouble1);
    setYValue(paramDouble2);
  }
  
  public final void setPosition(Point2D paramPoint2D)
  {
    setXValue(paramPoint2D.getX());
    setYValue(paramPoint2D.getY());
    this.position.set(paramPoint2D);
  }
  
  public final ObjectProperty<Point2D> positionProperty()
  {
    return this.position;
  }
  
  public final void incrementX()
  {
    setXValue(getXValue() + ((Sensitivity)this.sensitivity.get()).STEP_SIZE);
  }
  
  public final void decrementX()
  {
    setXValue(getXValue() - ((Sensitivity)this.sensitivity.get()).STEP_SIZE);
  }
  
  public final void incrementY()
  {
    setYValue(getYValue() + ((Sensitivity)this.sensitivity.get()).STEP_SIZE);
  }
  
  public final void decrementY()
  {
    setYValue(getYValue() - ((Sensitivity)this.sensitivity.get()).STEP_SIZE);
  }
  
  public final void reset()
  {
    setXValue(0.0D);
    setYValue(0.0D);
  }
  
  private void validateValue(double paramDouble, DoubleProperty paramDoubleProperty)
  {
    double d;
    if (paramDouble < -1.0D) {
      d = -1.0D;
    } else if (paramDouble > 1.0D) {
      d = 1.0D;
    } else {
      d = paramDouble;
    }
    paramDoubleProperty.set(d);
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
  
  public static enum Sensitivity
  {
    COARSE(0.1D, Color.RED),  MEDIUM(0.025D, Color.rgb(255, 191, 0)),  FINE(0.005D, Color.LIME);
    
    public final double STEP_SIZE;
    public final Color COLOR;
    
    private Sensitivity(double paramDouble, Color paramColor)
    {
      this.STEP_SIZE = paramDouble;
      this.COLOR = paramColor;
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/XYControl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */