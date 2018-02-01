package jfxtras.labs.scene.control.gauge;

import javafx.collections.ObservableList;

public class RadialHalfS
  extends Gauge
{
  private static final String DEFAULT_STYLE_CLASS = "radial-half-south";
  
  public RadialHalfS()
  {
    this(new GaugeModel(), new StyleModel());
  }
  
  public RadialHalfS(GaugeModel paramGaugeModel)
  {
    this(paramGaugeModel, new StyleModel());
  }
  
  public RadialHalfS(StyleModel paramStyleModel)
  {
    this(new GaugeModel(), paramStyleModel);
  }
  
  public RadialHalfS(GaugeModel paramGaugeModel, StyleModel paramStyleModel)
  {
    super(paramGaugeModel, paramStyleModel);
    setRadialRange(RadialRange.RADIAL_180S);
    getStyleClass().setAll(new String[] { "radial-half-south" });
  }
  
  public final void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 == 0.0D ? 200.0D : paramDouble1;
    super.setPrefSize(d, d / 1.5384615385D);
  }
  
  public final void setRadialRange(RadialRange paramRadialRange)
  {
    super.setRadialRange(RadialRange.RADIAL_180S);
  }
  
  public static enum ForegroundType
  {
    TYPE1,  TYPE2,  TYPE3,  TYPE4,  TYPE5;
    
    private ForegroundType() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/RadialHalfS.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */