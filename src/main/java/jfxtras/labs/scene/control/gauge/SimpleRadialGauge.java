package jfxtras.labs.scene.control.gauge;

import javafx.collections.ObservableList;

public class SimpleRadialGauge
  extends SimpleGauge
{
  private static final String DEFAULT_STYLE_CLASS = "simple-radial-gauge";
  
  public SimpleRadialGauge()
  {
    this(new GaugeModel());
  }
  
  public SimpleRadialGauge(GaugeModel paramGaugeModel)
  {
    super(paramGaugeModel);
    getStyleClass().setAll(new String[] { "simple-radial-gauge" });
  }
  
  public final RadialRange getRadialRange()
  {
    return RadialRange.RADIAL_300;
  }
  
  public final void setRadialRange(RadialRange paramRadialRange)
  {
    super.setRadialRange(RadialRange.RADIAL_300);
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 < paramDouble2 ? paramDouble1 : paramDouble2;
    super.setPrefSize(d, d);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SimpleRadialGauge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */