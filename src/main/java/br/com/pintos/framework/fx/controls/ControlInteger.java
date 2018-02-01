package br.com.pintos.framework.fx.controls;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javafx.scene.control.Control;
import jfxtras.labs.scene.control.BigDecimalField;

public class ControlInteger
  extends ControlFX<Integer>
{
  private BigDecimalField control;
  
  public ControlInteger(String paramString)
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
  
  public Integer getValue()
  {
    return Integer.valueOf(this.control.getNumber().intValue());
  }
  
  protected String mask()
  {
    return "#,##0";
  }
  
  public void setValue(Integer paramInteger)
  {
    if (paramInteger == null) {
      this.control.setNumber(new BigDecimal(0));
    } else {
      this.control.setNumber(new BigDecimal(paramInteger.intValue()));
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ControlInteger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */