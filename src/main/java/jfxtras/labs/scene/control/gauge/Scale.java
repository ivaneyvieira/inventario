package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Scale
{
  private DoubleProperty minValue;
  private DoubleProperty maxValue;
  private DoubleProperty range;
  private DoubleProperty uncorrectedMinValue;
  private DoubleProperty uncorrectedMaxValue;
  private BooleanProperty lastLabelVisible;
  
  public Scale()
  {
    this(0.0D, 100.0D);
  }
  
  public Scale(double paramDouble1, double paramDouble2)
  {
    this.minValue = new SimpleDoubleProperty(paramDouble1);
    this.maxValue = new SimpleDoubleProperty(paramDouble2);
    this.range = new SimpleDoubleProperty();
    this.range.bind(this.maxValue.subtract(this.minValue));
    this.uncorrectedMinValue = new SimpleDoubleProperty(paramDouble1);
    this.uncorrectedMaxValue = new SimpleDoubleProperty(paramDouble2);
    this.lastLabelVisible = new SimpleBooleanProperty(true);
  }
  
  public final double getMinValue()
  {
    return this.minValue.get();
  }
  
  public final void setMinValue(double paramDouble)
  {
    this.minValue.set(paramDouble);
  }
  
  public final ReadOnlyDoubleProperty minValueProperty()
  {
    return this.minValue;
  }
  
  public final double getMaxValue()
  {
    return this.maxValue.get();
  }
  
  public final void setMaxValue(double paramDouble)
  {
    this.maxValue.set(paramDouble);
  }
  
  public final ReadOnlyDoubleProperty maxValueProperty()
  {
    return this.maxValue;
  }
  
  public final double getRange()
  {
    return this.range.get();
  }
  
  public final ReadOnlyDoubleProperty rangeProperty()
  {
    return this.range;
  }
  
  public final double getUncorrectedMinValue()
  {
    return this.uncorrectedMinValue.get();
  }
  
  public final void setUncorrectedMinValue(double paramDouble)
  {
    this.minValue.set(paramDouble);
    this.uncorrectedMinValue.set(paramDouble);
  }
  
  public final ReadOnlyDoubleProperty uncorrectedMinValueProperty()
  {
    return this.uncorrectedMinValue;
  }
  
  public final double getUncorrectedMaxValue()
  {
    return this.uncorrectedMaxValue.get();
  }
  
  public final void setUncorrectedMaxValue(double paramDouble)
  {
    this.maxValue.set(paramDouble);
    this.uncorrectedMaxValue.set(paramDouble);
  }
  
  public final ReadOnlyDoubleProperty uncorrectedMaxValueProperty()
  {
    return this.uncorrectedMaxValue;
  }
  
  public final boolean isLastLabelVisible()
  {
    return this.lastLabelVisible.get();
  }
  
  public final void setLastLabelVisible(boolean paramBoolean)
  {
    this.lastLabelVisible.set(paramBoolean);
  }
  
  public final BooleanProperty lastLabelVisibleProperty()
  {
    return this.lastLabelVisible;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Scale.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */