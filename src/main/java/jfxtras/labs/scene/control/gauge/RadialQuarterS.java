package jfxtras.labs.scene.control.gauge;

import javafx.collections.ObservableList;

public class RadialQuarterS
  extends Gauge
{
  private static final String DEFAULT_STYLE_CLASS = "radial-quarter-south";
  
  public RadialQuarterS()
  {
    this(new GaugeModel(), new StyleModel());
  }
  
  public RadialQuarterS(GaugeModel paramGaugeModel)
  {
    this(paramGaugeModel, new StyleModel());
  }
  
  public RadialQuarterS(StyleModel paramStyleModel)
  {
    this(new GaugeModel(), paramStyleModel);
  }
  
  public RadialQuarterS(GaugeModel paramGaugeModel, StyleModel paramStyleModel)
  {
    super(paramGaugeModel, paramStyleModel);
    setRadialRange(RadialRange.RADIAL_90S);
    getStyleClass().setAll(new String[] { "radial-quarter-south" });
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
  
  public final void setRadialRange(RadialRange paramRadialRange)
  {
    super.setRadialRange(RadialRange.RADIAL_90S);
  }
  
  public static enum ForegroundType
  {
    TYPE1,  TYPE2,  TYPE3,  TYPE4,  TYPE5;
    
    private ForegroundType() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/RadialQuarterS.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */