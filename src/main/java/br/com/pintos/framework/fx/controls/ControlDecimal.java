package br.com.pintos.framework.fx.controls;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javafx.scene.control.Control;
import jfxtras.labs.scene.control.BigDecimalField;

public class ControlDecimal
  extends ControlFX<Double>
{
  private BigDecimalField control;
  
  public ControlDecimal(String paramString)
  {
    super(paramString);
  }
  
  protected Control createControl()
  {
    this.control = new BigDecimalField();
    DecimalFormat localDecimalFormat = new DecimalFormat(mask());
    this.control.setFormat(localDecimalFormat);
    return this.control;
  }
  
  public Double getValue()
  {
    return Double.valueOf(this.control.getNumber().doubleValue());
  }
  
  protected String mask()
  {
    return "#,##0.0######";
  }
  
  public void setValue(Double paramDouble)
  {
    Double localDouble = paramDouble;
    if (paramDouble == null) {
      localDouble = Double.valueOf(0.0D);
    }
    this.control.setNumber(new BigDecimal(localDouble.doubleValue()));
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ControlDecimal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */