package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.FornecedorBo;
import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.util.Convert;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;
import br.com.pintos.jooq.tables.pojos.Fornecedor;
import java.util.List;

public class ModeloFor
  extends ProviderModel<Fornecedor>
{
  public ModeloFor()
  {
    super("Fornecedore", Fornecedor.class);
    addColuna("codigo", "Código");
    addColuna("fantazia", "Nome");
  }
  
  protected List<Fornecedor> dadosNovos()
  {
    return Facade.fornecedor.findAll();
  }
  
  public Fornecedor getLockupBean(String paramString)
  {
    Integer localInteger = Util.convert.toInteger(paramString);
    return Facade.fornecedor.getFornecedor(localInteger);
  }
  
  public String getLockupField(Fornecedor paramFornecedor)
  {
    if (paramFornecedor == null) {
      return null;
    }
    return Util.string.format(paramFornecedor.getCodigo(), "0000");
  }
  
  public String getLockupLabel(Fornecedor paramFornecedor)
  {
    if (paramFornecedor == null) {
      return null;
    }
    return paramFornecedor.getRazao();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/ModeloFor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */