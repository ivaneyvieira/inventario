package br.com.pintos.framework.fx.controls;

import java.util.ArrayList;
import java.util.List;

public class ProviderList
  extends ProviderModel<String>
{
  private final List<String> list = new ArrayList();
  
  public ProviderList(String paramString, List paramList)
  {
    this(paramString, paramList.toArray());
  }
  
  public ProviderList(String paramString, Object[] paramArrayOfObject)
  {
    super(paramString, String.class);
    for (Object localObject : paramArrayOfObject) {
      if (localObject != null) {
        this.list.add(localObject.toString());
      }
    }
  }
  
  protected List<String> dadosNovos()
  {
    return this.list;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ProviderList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */