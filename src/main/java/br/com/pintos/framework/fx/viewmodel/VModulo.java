package br.com.pintos.framework.fx.viewmodel;

import br.com.pintos.framework.fx.view.ExecMenu;

public class VModulo
{
  private final String descricao;
  private final String icon;
  private final String atalho;
  private final VModulo[] childreAction;
  private final Class<? extends ExecMenu> execClass;
  
  public VModulo(String paramString1, String paramString2, String paramString3, Class<? extends ExecMenu> paramClass, VModulo... paramVarArgs)
  {
    this.descricao = paramString1;
    this.icon = paramString2;
    this.atalho = paramString3;
    this.execClass = paramClass;
    this.childreAction = paramVarArgs;
  }
  
  public String getAtalho()
  {
    return this.atalho;
  }
  
  public VModulo[] getChildreAction()
  {
    return this.childreAction;
  }
  
  public String getDescricao()
  {
    return this.descricao;
  }
  
  public Boolean getFolha()
  {
    return Boolean.valueOf((this.childreAction == null) || (this.childreAction.length == 0));
  }
  
  public String getIcon()
  {
    return this.icon;
  }
  
  public ExecMenu makeExec()
  {
    try
    {
      if (this.execClass == null) {
        return null;
      }
      return (ExecMenu)this.execClass.newInstance();
    }
    catch (InstantiationException|IllegalAccessException localInstantiationException)
    {
      throw new Error(localInstantiationException);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/viewmodel/VModulo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */