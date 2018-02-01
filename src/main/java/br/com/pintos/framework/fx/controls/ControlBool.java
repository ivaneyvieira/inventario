package br.com.pintos.framework.fx.controls;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;

public class ControlBool
  extends ControlFX<Boolean>
{
  private CheckBox control;
  
  public ControlBool(String paramString)
  {
    super(paramString);
  }
  
  protected Control createControl()
  {
    getLabelControl().setText("");
    this.control = new CheckBox();
    this.control.setText(getLabel());
    return this.control;
  }
  
  public Boolean getValue()
  {
    return Boolean.valueOf(this.control.selectedProperty().get());
  }
  
  public void setValue(Boolean paramBoolean)
  {
    this.control.selectedProperty().set(paramBoolean.booleanValue());
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ControlBool.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */