package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public final class Radial
  extends Gauge
{
  private static final String DEFAULT_STYLE_CLASS = "radial";
  private BooleanProperty histogramVisible = new SimpleBooleanProperty(false);
  private ObjectProperty<Color> histogramColor = new SimpleObjectProperty(Color.AQUAMARINE);
  private DoubleProperty histogramLineWidth = new SimpleDoubleProperty(1.0D);
  private BooleanProperty histogramCreationEnabled = new SimpleBooleanProperty(false);
  private IntegerProperty histogramDataPeriodInMinutes = new SimpleIntegerProperty(5);
  
  public Radial()
  {
    this(new GaugeModel(), new StyleModel());
  }
  
  public Radial(GaugeModel paramGaugeModel)
  {
    this(paramGaugeModel, new StyleModel());
  }
  
  public Radial(StyleModel paramStyleModel)
  {
    this(new GaugeModel(), paramStyleModel);
  }
  
  public Radial(GaugeModel paramGaugeModel, StyleModel paramStyleModel)
  {
    super(paramGaugeModel, paramStyleModel);
    getStyleClass().setAll(new String[] { "radial" });
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 <= paramDouble2 ? paramDouble1 : paramDouble2;
    super.setPrefSize(d, d);
  }
  
  public void setMinSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 <= paramDouble2 ? paramDouble1 : paramDouble2;
    super.setMinSize(d, d);
  }
  
  public void setMaxSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 <= paramDouble2 ? paramDouble1 : paramDouble2;
    super.setMaxSize(d, d);
  }
  
  public final boolean isHistogramVisible()
  {
    return this.histogramVisible.get();
  }
  
  public final void setHistogramVisible(boolean paramBoolean)
  {
    this.histogramVisible.set(paramBoolean);
  }
  
  public final BooleanProperty histogramVisibleProperty()
  {
    return this.histogramVisible;
  }
  
  public final Color getHistogramColor()
  {
    return (Color)this.histogramColor.get();
  }
  
  public final void setHistogramColor(Color paramColor)
  {
    this.histogramColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> histogramColorProperty()
  {
    return this.histogramColor;
  }
  
  public final double getHistogramLineWidth()
  {
    return this.histogramLineWidth.get();
  }
  
  public final void setHistogramLineWidth(double paramDouble)
  {
    double d = paramDouble > 3.0D ? 3.0D : paramDouble < 0.5D ? 0.5D : paramDouble;
    this.histogramLineWidth.set(d);
  }
  
  public final DoubleProperty histogramLineWidthProperty()
  {
    return this.histogramLineWidth;
  }
  
  public final boolean isHistogramCreationEnabled()
  {
    return this.histogramCreationEnabled.get();
  }
  
  public final void setHistogramCreationEnabled(boolean paramBoolean)
  {
    this.histogramCreationEnabled.set(paramBoolean);
  }
  
  public final BooleanProperty histogramCreationEnabledProperty()
  {
    return this.histogramCreationEnabled;
  }
  
  public final int getHistogramDataPeriodInMinutes()
  {
    return this.histogramDataPeriodInMinutes.get();
  }
  
  public final void setHistogramDataPeriodInMinutes(int paramInt)
  {
    this.histogramDataPeriodInMinutes.set(paramInt);
  }
  
  public final IntegerProperty histogramDataPeriodInMinutesProperty()
  {
    return this.histogramDataPeriodInMinutes;
  }
  
  public static enum ForegroundType
  {
    TYPE1,  TYPE2,  TYPE3,  TYPE4,  TYPE5;
    
    private ForegroundType() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Radial.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */