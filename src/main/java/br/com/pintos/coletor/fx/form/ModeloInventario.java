package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.InventarioBO;
import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.util.Convert;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;
import br.com.pintos.jooq.tables.pojos.Inventario;
import java.util.List;

public class ModeloInventario
  extends ProviderModel<Inventario>
{
  public ModeloInventario()
  {
    super("Inventário", Inventario.class);
    addColuna("numero", "Número");
    addColuna("data", "Data");
    addColuna("observacao", "OBS");
    addColuna("statusinventario", "Situação");
  }
  
  public List<Inventario> dadosNovos()
  {
    return Facade.inventario.findAll();
  }
  
  public Inventario getLockupBean(String paramString)
  {
    Integer localInteger = Util.convert.toInteger(paramString);
    return Facade.inventario.findByNum(localInteger);
  }
  
  public String getLockupField(Inventario paramInventario)
  {
    if (paramInventario == null) {
      return null;
    }
    return Util.string.format(paramInventario.getNumero(), "00000");
  }
  
  public String getLockupLabel(Inventario paramInventario)
  {
    if (paramInventario == null) {
      return null;
    }
    return paramInventario.getObservacao();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/ModeloInventario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */