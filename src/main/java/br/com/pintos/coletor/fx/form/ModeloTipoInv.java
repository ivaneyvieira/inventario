package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.InventarioBO.Tipo;
import br.com.pintos.framework.fx.controls.ProviderList;

public class ModeloTipoInv
  extends ProviderList
{
  public ModeloTipoInv()
  {
    super("Tipo", InventarioBO.Tipo.values());
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/ModeloTipoInv.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */