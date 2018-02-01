package jfxtras.labs.scene.control.gauge;

import javafx.collections.ObservableList;

public class Linear
  extends Gauge
{
  private static final String DEFAULT_STYLE_CLASS = "linear";
  
  public Linear()
  {
    this(new GaugeModel(), new StyleModel());
  }
  
  public Linear(GaugeModel paramGaugeModel)
  {
    this(paramGaugeModel, new StyleModel());
  }
  
  public Linear(StyleModel paramStyleModel)
  {
    this(new GaugeModel(), paramStyleModel);
  }
  
  public Linear(GaugeModel paramGaugeModel, StyleModel paramStyleModel)
  {
    super(paramGaugeModel, paramStyleModel);
    getStyleClass().setAll(new String[] { "linear" });
  }
  
  public final void setPrefSize(double paramDouble1, double paramDouble2)
  {
    super.setPrefSize(paramDouble1, paramDouble2);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Linear.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */