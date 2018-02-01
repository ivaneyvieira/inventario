package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LinearScale
  extends Scale
{
  private BooleanProperty tightScale;
  private BooleanProperty niceScaling;
  private DoubleProperty niceMinValue;
  private DoubleProperty niceMaxValue;
  private DoubleProperty majorTickSpacing;
  private DoubleProperty minorTickSpacing;
  private IntegerProperty maxNoOfMajorTicks;
  private IntegerProperty maxNoOfMinorTicks;
  private DoubleProperty niceRange;
  private BooleanProperty largeNumberScale;
  private double tightScaleOffset;
  
  public LinearScale()
  {
    super(0.0D, 100.0D);
    init();
  }
  
  public LinearScale(double paramDouble1, double paramDouble2)
  {
    super(paramDouble1, paramDouble2);
    init();
  }
  
  private void init()
  {
    this.tightScale = new SimpleBooleanProperty(false);
    this.niceScaling = new SimpleBooleanProperty(true);
    this.niceMinValue = new SimpleDoubleProperty(getMinValue());
    this.niceMaxValue = new SimpleDoubleProperty(getMaxValue());
    this.niceRange = new SimpleDoubleProperty(getRange());
    this.largeNumberScale = new SimpleBooleanProperty(false);
    this.maxNoOfMajorTicks = new SimpleIntegerProperty(10);
    this.maxNoOfMinorTicks = new SimpleIntegerProperty(10);
    this.majorTickSpacing = new SimpleDoubleProperty(10.0D);
    this.minorTickSpacing = new SimpleDoubleProperty(1.0D);
    this.tightScaleOffset = 0.0D;
  }
  
  public final boolean isTightScale()
  {
    return this.tightScale.get();
  }
  
  public final void setTightScale(boolean paramBoolean)
  {
    this.tightScale.set(paramBoolean);
  }
  
  public final BooleanProperty tightScaleProperty()
  {
    return this.tightScale;
  }
  
  public final boolean isNiceScaling()
  {
    return this.niceScaling.get();
  }
  
  public final void setNiceScaling(boolean paramBoolean)
  {
    this.niceScaling.set(paramBoolean);
  }
  
  public final BooleanProperty niceScalingProperty()
  {
    return this.niceScaling;
  }
  
  public final double getNiceMinValue()
  {
    return this.niceMinValue.get();
  }
  
  public final ReadOnlyDoubleProperty niceMinValueProperty()
  {
    return this.niceMinValue;
  }
  
  public final double getNiceMaxValue()
  {
    return this.niceMaxValue.get();
  }
  
  public final ReadOnlyDoubleProperty niceMaxValueProperty()
  {
    return this.niceMaxValue;
  }
  
  public final int getMaxNoOfMajorTicks()
  {
    return this.maxNoOfMajorTicks.get();
  }
  
  public final void setMaxNoOfMajorTicks(int paramInt)
  {
    this.maxNoOfMajorTicks.set(paramInt);
  }
  
  public final IntegerProperty maxNoOfMajorTicksProperty()
  {
    return this.maxNoOfMajorTicks;
  }
  
  public final int getMaxNoOfMinorTicks()
  {
    return this.maxNoOfMinorTicks.get();
  }
  
  public final void setMaxNoOfMinorTicks(int paramInt)
  {
    this.maxNoOfMinorTicks.set(paramInt);
  }
  
  public final IntegerProperty maxNoOfMinorTicksProperty()
  {
    return this.maxNoOfMinorTicks;
  }
  
  public final double getNiceRange()
  {
    return this.niceRange.get();
  }
  
  public final ReadOnlyDoubleProperty niceRangeProperty()
  {
    return this.niceRange;
  }
  
  public final boolean isLargeNumberScale()
  {
    return this.largeNumberScale.get();
  }
  
  public final void setLargeNumberScale(boolean paramBoolean)
  {
    this.largeNumberScale.set(paramBoolean);
  }
  
  public final BooleanProperty largeNumberScaleProperty()
  {
    return this.largeNumberScale;
  }
  
  public final double getMajorTickSpacing()
  {
    return this.majorTickSpacing.get();
  }
  
  public final void setMajorTickSpacing(double paramDouble)
  {
    this.majorTickSpacing.set(paramDouble);
  }
  
  public final DoubleProperty majorTickSpacingProperty()
  {
    return this.majorTickSpacing;
  }
  
  public final double getMinorTickSpacing()
  {
    return this.minorTickSpacing.get();
  }
  
  public final void setMinorTickSpacing(double paramDouble)
  {
    this.minorTickSpacing.set(paramDouble);
  }
  
  public final DoubleProperty minorTickSpacingProperty()
  {
    return this.minorTickSpacing;
  }
  
  public final double getTightScaleOffset()
  {
    return this.tightScaleOffset;
  }
  
  protected void calculateLoose()
  {
    if (isNiceScaling())
    {
      this.niceRange.set(calcNiceNumber(getRange(), false));
      this.majorTickSpacing.set(calcNiceNumber(this.niceRange.doubleValue() / (this.maxNoOfMajorTicks.doubleValue() - 1.0D), true));
      this.niceMinValue.set(Math.floor(getMinValue() / this.majorTickSpacing.doubleValue()) * this.majorTickSpacing.doubleValue());
      this.niceMaxValue.set(Math.ceil(getUncorrectedMaxValue() / this.majorTickSpacing.doubleValue()) * this.majorTickSpacing.doubleValue());
      this.minorTickSpacing.set(calcNiceNumber(this.majorTickSpacing.doubleValue() / (this.maxNoOfMinorTicks.intValue() - 1), true));
      this.niceRange.set(this.niceMaxValue.doubleValue() - this.niceMinValue.doubleValue());
      setMinValue(this.niceMinValue.get());
      setMaxValue(this.niceMaxValue.get());
    }
    else
    {
      this.niceRange.set(getRange());
      this.niceMinValue.set(getMinValue());
      this.niceMaxValue.set(getMaxValue());
    }
  }
  
  protected void calculateTight()
  {
    if (isNiceScaling())
    {
      this.niceRange.set(getUncorrectedMaxValue() - getUncorrectedMinValue());
      this.majorTickSpacing.set(calcNiceNumber(this.niceRange.doubleValue() / (this.maxNoOfMajorTicks.doubleValue() - 1.0D), true));
      this.niceMinValue.set(Math.floor(getUncorrectedMinValue() / this.majorTickSpacing.doubleValue()) * this.majorTickSpacing.doubleValue());
      this.niceMaxValue.set(Math.ceil(getUncorrectedMaxValue() / this.majorTickSpacing.doubleValue()) * this.majorTickSpacing.doubleValue());
      this.minorTickSpacing.set(calcNiceNumber(this.majorTickSpacing.doubleValue() / (this.maxNoOfMinorTicks.doubleValue() - 1.0D), true));
      this.tightScaleOffset = ((int)((getUncorrectedMinValue() - this.niceMinValue.doubleValue() + 1.0D) / this.minorTickSpacing.doubleValue()));
      this.niceMinValue.set(getUncorrectedMinValue());
      this.niceMaxValue.set(getUncorrectedMaxValue());
    }
    else
    {
      this.niceRange.set(getRange());
      this.niceMinValue.set(getMinValue());
      this.niceMaxValue.set(getMaxValue());
    }
  }
  
  private double calcNiceNumber(double paramDouble, boolean paramBoolean)
  {
    double d1 = Math.floor(Math.log10(paramDouble));
    double d2 = paramDouble / Math.pow(10.0D, d1);
    double d3 = d2 % 0.5D;
    double d4;
    if (isLargeNumberScale())
    {
      if (d3 != 0.0D)
      {
        d4 = d2 - d3;
        d4 += 0.5D;
      }
      else
      {
        d4 = d2;
      }
    }
    else if (paramBoolean)
    {
      if (d2 < 1.5D) {
        d4 = 1.0D;
      } else if (d2 < 3.0D) {
        d4 = 2.0D;
      } else if (d2 < 7.0D) {
        d4 = 5.0D;
      } else {
        d4 = 10.0D;
      }
    }
    else if (Double.compare(d2, 1.0D) <= 0) {
      d4 = 1.0D;
    } else if (Double.compare(d2, 2.0D) <= 0) {
      d4 = 2.0D;
    } else if (Double.compare(d2, 5.0D) <= 0) {
      d4 = 5.0D;
    } else {
      d4 = 10.0D;
    }
    return d4 * Math.pow(10.0D, d1);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/LinearScale.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */