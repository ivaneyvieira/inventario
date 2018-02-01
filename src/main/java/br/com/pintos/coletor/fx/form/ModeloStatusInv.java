package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.InventarioBO.Status;
import br.com.pintos.framework.fx.controls.ProviderList;

public class ModeloStatusInv
  extends ProviderList
{
  public ModeloStatusInv()
  {
    super("Status", InventarioBO.Status.values());
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/ModeloStatusInv.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */