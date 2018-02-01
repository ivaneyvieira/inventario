package br.com.pintos.framework.fx.controls;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class ControlString
  extends ControlFX<String>
{
  private TextField text;
  
  public ControlString(String paramString)
  {
    super(paramString);
  }
  
  protected Control createControl()
  {
    this.text = new TextField();
    return this.text;
  }
  
  public String getValue()
  {
    String str = this.text.getText();
    if (str == null) {
      return "";
    }
    return str;
  }
  
  public void setValue(String paramString)
  {
    this.text.setText(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ControlString.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */