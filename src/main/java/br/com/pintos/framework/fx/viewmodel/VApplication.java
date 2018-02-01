package br.com.pintos.framework.fx.viewmodel;

import br.com.pintos.framework.fx.view.ExecMenu;

public abstract class VApplication
{
  private final String icon = icon();
  private final VModulo modulos = modulos();
  private final String titulo = titulo();
  
  protected VModulo comando(String paramString1, String paramString2, Class<? extends ExecMenu> paramClass)
  {
    return new VModulo(paramString1, paramString2, null, paramClass, new VModulo[0]);
  }
  
  protected VModulo comando(String paramString1, String paramString2, String paramString3, Class<? extends ExecMenu> paramClass)
  {
    return new VModulo(paramString1, paramString2, paramString3, paramClass, new VModulo[0]);
  }
  
  public String getIcon()
  {
    return this.icon;
  }
  
  public VModulo getModulos()
  {
    return this.modulos;
  }
  
  public String getTitulo()
  {
    return this.titulo;
  }
  
  protected abstract String icon();
  
  protected VModulo modulo(VModulo... paramVarArgs)
  {
    return new VModulo(titulo(), icon(), null, null, paramVarArgs);
  }
  
  protected abstract VModulo modulos();
  
  protected VModulo subModulo(String paramString, VModulo... paramVarArgs)
  {
    return new VModulo(paramString, null, null, null, paramVarArgs);
  }
  
  protected abstract String titulo();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/viewmodel/VApplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */