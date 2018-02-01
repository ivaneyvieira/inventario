package jfxtras.labs.scene.control.gauge;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public abstract class SimpleGauge
  extends Gauge
{
  private ObjectProperty<Color> barFrameColor = new SimpleObjectProperty(Color.rgb(100, 100, 100));
  private ObjectProperty<Color> barBackgroundColor = new SimpleObjectProperty(Color.rgb(234, 234, 234));
  private ObjectProperty<Color> barColor = new SimpleObjectProperty(Color.rgb(178, 177, 212));
  private DoubleProperty barWidth = new SimpleDoubleProperty(20.0D);
  private DoubleProperty valueLabelFontSize = new SimpleDoubleProperty(36.0D);
  private DoubleProperty unitLabelFontSize = new SimpleDoubleProperty(20.0D);
  private ObjectProperty<Color> valueLabelColor = new SimpleObjectProperty(Color.BLACK);
  private ObjectProperty<Color> unitLabelColor = new SimpleObjectProperty(Color.BLACK);
  private BooleanProperty valueLabelVisible = new SimpleBooleanProperty(true);
  private BooleanProperty unitLabelVisible = new SimpleBooleanProperty(true);
  private IntegerProperty noOfDecimals = new SimpleIntegerProperty(2);
  private BooleanProperty minLabelVisible = new SimpleBooleanProperty(false);
  private BooleanProperty maxLabelVisible = new SimpleBooleanProperty(false);
  private DoubleProperty minMaxLabelFontSize = new SimpleDoubleProperty(10.0D);
  private ObjectProperty<Color> minLabelColor = new SimpleObjectProperty(Color.BLACK);
  private ObjectProperty<Color> maxLabelColor = new SimpleObjectProperty(Color.BLACK);
  private BooleanProperty roundedBar = new SimpleBooleanProperty(true);
  private DoubleProperty timeToValueInMs = new SimpleDoubleProperty(1500.0D);
  private StringProperty unit;
  private BooleanProperty canvasMode;
  
  protected SimpleGauge()
  {
    this(new GaugeModel(), new StyleModel());
  }
  
  protected SimpleGauge(GaugeModel paramGaugeModel)
  {
    this(paramGaugeModel, new StyleModel());
  }
  
  protected SimpleGauge(GaugeModel paramGaugeModel, StyleModel paramStyleModel)
  {
    super(paramGaugeModel, paramStyleModel);
    this.unit = new SimpleStringProperty(paramGaugeModel.getUnit());
    this.canvasMode = new SimpleBooleanProperty(false);
  }
  
  public final Color getBarColor()
  {
    return (Color)this.barColor.get();
  }
  
  public final void setValueColor(Color paramColor)
  {
    this.barColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> barColorProperty()
  {
    return this.barColor;
  }
  
  public final Color getBarFrameColor()
  {
    return (Color)this.barFrameColor.get();
  }
  
  public final void setBarFrameColor(Color paramColor)
  {
    this.barFrameColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> barFrameColorProperty()
  {
    return this.barFrameColor;
  }
  
  public final Color getBarBackgroundColor()
  {
    return (Color)this.barBackgroundColor.get();
  }
  
  public final void setBarBackgroundColor(Color paramColor)
  {
    this.barBackgroundColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> barBackgroundColorProperty()
  {
    return this.barBackgroundColor;
  }
  
  public final double getBarWidth()
  {
    return this.barWidth.get();
  }
  
  public final void setBarWidth(double paramDouble)
  {
    double d = paramDouble > 100.0D ? 100.0D : paramDouble < 1.0D ? 1.0D : paramDouble;
    this.barWidth.set(d);
  }
  
  public final DoubleProperty barWidthProperty()
  {
    return this.barWidth;
  }
  
  public final double getValueLabelFontSize()
  {
    return this.valueLabelFontSize.get();
  }
  
  public final void setValueLabelFontSize(double paramDouble)
  {
    double d = paramDouble > 52.0D ? 52.0D : paramDouble < 8.0D ? 8.0D : paramDouble;
    this.valueLabelFontSize.set(d);
  }
  
  public final ReadOnlyDoubleProperty valueLabelFontSizeProperty()
  {
    return this.valueLabelFontSize;
  }
  
  public final double getUnitLabelFontSize()
  {
    return this.unitLabelFontSize.get();
  }
  
  public final void setUnitLabelFontSize(double paramDouble)
  {
    double d = paramDouble > 52.0D ? 52.0D : paramDouble < 8.0D ? 8.0D : paramDouble;
    this.unitLabelFontSize.set(d);
  }
  
  public final DoubleProperty unitLabelFontSizeProperty()
  {
    return this.unitLabelFontSize;
  }
  
  public final Color getValueLabelColor()
  {
    return (Color)this.valueLabelColor.get();
  }
  
  public final void setValueLabelColor(Color paramColor)
  {
    this.valueLabelColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> valueLabelColorProperty()
  {
    return this.valueLabelColor;
  }
  
  public final Color getUnitLabelColor()
  {
    return (Color)this.unitLabelColor.get();
  }
  
  public final void setUnitLabelColor(Color paramColor)
  {
    this.unitLabelColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> unitLabelColorProperty()
  {
    return this.unitLabelColor;
  }
  
  public final boolean isValueLabelVisible()
  {
    return this.valueLabelVisible.get();
  }
  
  public final void setValueLabelVisible(boolean paramBoolean)
  {
    this.valueLabelVisible.set(paramBoolean);
  }
  
  public final BooleanProperty valueLabelVisibleProperty()
  {
    return this.valueLabelVisible;
  }
  
  public final boolean isUnitLabelVisible()
  {
    return this.unitLabelVisible.get();
  }
  
  public final void setUnitLabelVisible(boolean paramBoolean)
  {
    this.unitLabelVisible.set(paramBoolean);
  }
  
  public final BooleanProperty unitLabelVisibleProperty()
  {
    return this.unitLabelVisible;
  }
  
  public final int getNoOfDecimals()
  {
    return this.noOfDecimals.get();
  }
  
  public final void setNoOfDecimals(int paramInt)
  {
    int i = paramInt > 5 ? 5 : paramInt < 0 ? 0 : paramInt;
    this.noOfDecimals.set(i);
  }
  
  public final ReadOnlyIntegerProperty noOfDecimalsProperty()
  {
    return this.noOfDecimals;
  }
  
  public final void setSections(Section... paramVarArgs)
  {
    getGaugeModel().setSections(paramVarArgs);
  }
  
  public final void setSections(List<Section> paramList)
  {
    getGaugeModel().setSections(paramList);
  }
  
  public final boolean isMinLabelVisible()
  {
    return this.minLabelVisible.get();
  }
  
  public final void setMinLabelVisible(boolean paramBoolean)
  {
    this.minLabelVisible.set(paramBoolean);
  }
  
  public final BooleanProperty minLabelVisibleProperty()
  {
    return this.minLabelVisible;
  }
  
  public final boolean isMaxLabelVisible()
  {
    return this.maxLabelVisible.get();
  }
  
  public final void setMaxLabelVisible(boolean paramBoolean)
  {
    this.maxLabelVisible.set(paramBoolean);
  }
  
  public final BooleanProperty maxLabelVisibleProperty()
  {
    return this.maxLabelVisible;
  }
  
  public final double getMinMaxLabelFontSize()
  {
    return this.minMaxLabelFontSize.get();
  }
  
  public final void setMinMaxLabelFontSize(double paramDouble)
  {
    double d = paramDouble > 52.0D ? 52.0D : paramDouble < 8.0D ? 8.0D : paramDouble;
    this.minMaxLabelFontSize.set(d);
  }
  
  public final DoubleProperty minMaxLabelFontSizeProperty()
  {
    return this.minMaxLabelFontSize;
  }
  
  public final Color getMinLabelColor()
  {
    return (Color)this.minLabelColor.get();
  }
  
  public final void setMinLabelColor(Color paramColor)
  {
    this.minLabelColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> minLabelColorProperty()
  {
    return this.minLabelColor;
  }
  
  public final Color getMaxLabelColor()
  {
    return (Color)this.maxLabelColor.get();
  }
  
  public final void setMaxLabelColor(Color paramColor)
  {
    this.maxLabelColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> maxLabelColorProperty()
  {
    return this.maxLabelColor;
  }
  
  public final boolean isRoundedBar()
  {
    return this.roundedBar.get();
  }
  
  public final void setRoundedBar(boolean paramBoolean)
  {
    this.roundedBar.set(paramBoolean);
  }
  
  public final BooleanProperty roundedBarProperty()
  {
    return this.roundedBar;
  }
  
  public final double getTimeToValueInMs()
  {
    return this.timeToValueInMs.get();
  }
  
  public final void setTimeToValueInMs(double paramDouble)
  {
    double d = paramDouble > 5000.0D ? 5000.0D : paramDouble < 10.0D ? 10.0D : paramDouble;
    this.timeToValueInMs.set(d);
  }
  
  public final DoubleProperty timeToValueInMsProperty()
  {
    return this.timeToValueInMs;
  }
  
  public final boolean isCanvasMode()
  {
    return this.canvasMode.get();
  }
  
  public final void setCanvasMode(boolean paramBoolean)
  {
    this.canvasMode.set(paramBoolean);
  }
  
  public final BooleanProperty canvasModeProperty()
  {
    return this.canvasMode;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SimpleGauge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */