package br.com.pintos.framework.fx.controls;

import java.util.HashMap;
import java.util.Map;
import javafx.util.StringConverter;

public class ConvertCombo<T>
  extends StringConverter<T>
{
  private final ProviderModel<T> model;
  Map<String, T> map;
  
  public ConvertCombo(ProviderModel<T> paramProviderModel)
  {
    this.model = paramProviderModel;
    this.map = new HashMap();
  }
  
  public T fromString(String paramString)
  {
    return (T)this.map.get(paramString);
  }
  
  public String toString(T paramT)
  {
    return this.model.getLockupLabel(paramT);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ConvertCombo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */