package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Lcd
  extends Gauge
{
  private static final String DEFAULT_STYLE_CLASS = "lcd";
  private BooleanProperty lcdMinMeasuredValueVisible = new SimpleBooleanProperty(false);
  private BooleanProperty lcdMaxMeasuredValueVisible = new SimpleBooleanProperty(false);
  private BooleanProperty lcdFormerValueVisible = new SimpleBooleanProperty(false);
  private IntegerProperty lcdMinMeasuredValueDecimals;
  private IntegerProperty lcdMaxMeasuredValueDecimals;
  private BooleanProperty bargraphVisible;
  private BooleanProperty clockMode;
  private BooleanProperty clockSecondsVisible;
  
  public Lcd()
  {
    this(new GaugeModel(), new StyleModel());
  }
  
  public Lcd(GaugeModel paramGaugeModel)
  {
    this(paramGaugeModel, new StyleModel());
  }
  
  public Lcd(StyleModel paramStyleModel)
  {
    this(new GaugeModel(), paramStyleModel);
  }
  
  public Lcd(GaugeModel paramGaugeModel, StyleModel paramStyleModel)
  {
    super(paramGaugeModel, paramStyleModel);
    this.lcdMinMeasuredValueDecimals = new SimpleIntegerProperty(paramStyleModel.getLcdDecimals());
    this.lcdMaxMeasuredValueDecimals = new SimpleIntegerProperty(paramStyleModel.getLcdDecimals());
    this.bargraphVisible = new SimpleBooleanProperty(false);
    this.clockMode = new SimpleBooleanProperty(false);
    this.clockSecondsVisible = new SimpleBooleanProperty(true);
    setValueAnimationEnabled(false);
    getStyleClass().setAll(new String[] { "lcd" });
  }
  
  public final void setPrefSize(double paramDouble1, double paramDouble2)
  {
    super.setPrefSize(paramDouble1, paramDouble2);
  }
  
  public final boolean isLcdMinMeasuredValueVisible()
  {
    return this.lcdMinMeasuredValueVisible.get();
  }
  
  public final void setLcdMinMeasuredValueVisible(boolean paramBoolean)
  {
    this.lcdMinMeasuredValueVisible.set(paramBoolean);
  }
  
  public final BooleanProperty lcdMinMeasuredValueVisibleProperty()
  {
    return this.lcdMinMeasuredValueVisible;
  }
  
  public final boolean isLcdMaxMeasuredValueVisible()
  {
    return this.lcdMaxMeasuredValueVisible.get();
  }
  
  public final void setLcdMaxMeasuredValueVisible(boolean paramBoolean)
  {
    this.lcdMaxMeasuredValueVisible.set(paramBoolean);
  }
  
  public final BooleanProperty lcdMaxMeasuredValueVisibleProperty()
  {
    return this.lcdMaxMeasuredValueVisible;
  }
  
  public final boolean isLcdFormerValueVisible()
  {
    return this.lcdFormerValueVisible.get();
  }
  
  public final void setLcdFormerValueVisible(boolean paramBoolean)
  {
    this.lcdFormerValueVisible.set(paramBoolean);
  }
  
  public final BooleanProperty lcdFormerValueVisibleProperty()
  {
    return this.lcdFormerValueVisible;
  }
  
  public final int getLcdMinMeasuredValueDecimals()
  {
    return this.lcdMinMeasuredValueDecimals.get();
  }
  
  public final void setLcdMinMeasuredValueDecimals(int paramInt)
  {
    int i = paramInt < 0 ? 0 : paramInt > 5 ? 5 : paramInt;
    this.lcdMinMeasuredValueDecimals.set(i);
  }
  
  public final IntegerProperty lcdMinMeasuredValueDecimalsProperty()
  {
    return this.lcdMaxMeasuredValueDecimals;
  }
  
  public final int getLcdMaxMeasuredValueDecimals()
  {
    return this.lcdMaxMeasuredValueDecimals.get();
  }
  
  public final void setLcdMaxMeasuredValueDecimals(int paramInt)
  {
    int i = paramInt < 0 ? 0 : paramInt > 5 ? 5 : paramInt;
    this.lcdMaxMeasuredValueDecimals.set(i);
  }
  
  public final IntegerProperty lcdMaxMeasuredValueDecimalsProperty()
  {
    return this.lcdMaxMeasuredValueDecimals;
  }
  
  public final boolean isBargraphVisible()
  {
    return this.bargraphVisible.get();
  }
  
  public final void setBargraphVisible(boolean paramBoolean)
  {
    this.bargraphVisible.set(paramBoolean);
  }
  
  public final BooleanProperty bargraphVisibleProperty()
  {
    return this.bargraphVisible;
  }
  
  public final boolean isClockMode()
  {
    return this.clockMode.get();
  }
  
  public final void setClockMode(boolean paramBoolean)
  {
    this.clockMode.set(paramBoolean);
  }
  
  public final BooleanProperty clockModeProperty()
  {
    return this.clockMode;
  }
  
  public final boolean isClockSecondsVisible()
  {
    return this.clockSecondsVisible.get();
  }
  
  public final void setClockSecondsVisible(boolean paramBoolean)
  {
    this.clockSecondsVisible.set(paramBoolean);
  }
  
  public final BooleanProperty clockSecondsVisibleProperty()
  {
    return this.clockSecondsVisible;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Lcd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */