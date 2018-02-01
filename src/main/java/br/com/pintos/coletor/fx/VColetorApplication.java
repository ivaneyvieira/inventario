package br.com.pintos.coletor.fx;

import br.com.pintos.coletor.fx.form.BrowserInventarioFX;
import br.com.pintos.framework.fx.view.ExecSair;
import br.com.pintos.framework.fx.viewmodel.VApplication;
import br.com.pintos.framework.fx.viewmodel.VModulo;

public class VColetorApplication
  extends VApplication
{
  public String icon()
  {
    return "inv.png";
  }
  
  public VModulo modulos()
  {
    VModulo localVModulo1 = comando("Inventario v 1.5", "inventario.png", "Ctrl+I", BrowserInventarioFX.class);
    VModulo localVModulo2 = comando("Gerar estoque", "estoque.png", "Ctrl+E", GerarEstoque.class);
    VModulo localVModulo3 = comando("Sair", "sair.png", "Ctrl+X", ExecSair.class);
    VModulo localVModulo4 = comando("Estornos", "estornos.png", "Ctrl+T", null);
    VModulo localVModulo5 = subModulo("Planejamento", new VModulo[] { localVModulo1, localVModulo2, localVModulo3 });
    VModulo localVModulo6 = subModulo("Acompanhamento", new VModulo[] { localVModulo4 });
    return modulo(new VModulo[] { localVModulo5, localVModulo6 });
  }
  
  public String titulo()
  {
    return "Controle de Inventário";
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/VColetorApplication.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */