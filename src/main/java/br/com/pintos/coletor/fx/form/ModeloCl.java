package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.ClBo;
import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.util.Convert;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;
import br.com.pintos.jooq.tables.pojos.Cl;
import java.util.List;

public class ModeloCl
  extends ProviderModel<Cl>
{
  public ModeloCl()
  {
    super("Centro de lucro", Cl.class);
    addColuna("clno", "Número");
    addColuna("grupo", "Grupo");
    addColuna("departamento", "Dpto");
    addColuna("secao", "Secção");
  }
  
  protected List<Cl> dadosNovos()
  {
    return Facade.cl.findAll();
  }
  
  public Cl getLockupBean(String paramString)
  {
    Integer localInteger = Util.convert.toInteger(paramString);
    return Facade.cl.getCl(localInteger);
  }
  
  public String getLockupField(Cl paramCl)
  {
    if (paramCl == null) {
      return null;
    }
    return Util.string.format(paramCl.getClno(), "000000");
  }
  
  public String getLockupLabel(Cl paramCl)
  {
    if (paramCl == null) {
      return "";
    }
    return paramCl.getGrupo() + "/" + paramCl.getDepartamento() + "/" + paramCl.getSecao();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/ModeloCl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */