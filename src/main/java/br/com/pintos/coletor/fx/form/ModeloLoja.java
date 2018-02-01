package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.LojaBo;
import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.util.Convert;
import br.com.pintos.framework.util.Util;
import br.com.pintos.jooq.tables.pojos.Loja;
import java.util.List;

public class ModeloLoja
  extends ProviderModel<Loja>
{
  public ModeloLoja()
  {
    super("Lojas", Loja.class);
    addColuna("storeno", "Código");
    addColuna("sigla", "Sigla");
    addColuna("nome", "Nome");
  }
  
  protected List<Loja> dadosNovos()
  {
    return Facade.loja.findAll();
  }
  
  public Loja getLockupBean(String paramString)
  {
    Integer localInteger = Util.convert.toInteger(paramString);
    return Facade.loja.getLoja(localInteger);
  }
  
  public String getLockupField(Loja paramLoja)
  {
    if (paramLoja == null) {
      return null;
    }
    return paramLoja.getStoreno().toString();
  }
  
  public String getLockupLabel(Loja paramLoja)
  {
    if (paramLoja == null) {
      return null;
    }
    return paramLoja.getNome();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/ModeloLoja.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */