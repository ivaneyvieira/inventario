package jfxtras.labs.scene.control.gauge;

import javafx.collections.ObservableList;

public class SimpleLinearGauge
  extends SimpleGauge
{
  private static final String DEFAULT_STYLE_CLASS = "simple-linear-gauge";
  
  public SimpleLinearGauge()
  {
    this(new GaugeModel());
  }
  
  public SimpleLinearGauge(GaugeModel paramGaugeModel)
  {
    super(paramGaugeModel);
    getStyleClass().setAll(new String[] { "simple-linear-gauge" });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SimpleLinearGauge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */