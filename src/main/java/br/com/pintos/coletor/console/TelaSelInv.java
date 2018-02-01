package br.com.pintos.coletor.console;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.InventarioBO;
import br.com.pintos.coletor.bos.InventarioBO.Status;
import br.com.pintos.framework.console.Campo;
import br.com.pintos.framework.console.EValidaLeitura;
import br.com.pintos.framework.console.Tela;
import br.com.pintos.framework.dados.exception.ValidaBean;
import br.com.pintos.framework.util.Convert;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;
import br.com.pintos.jooq.tables.pojos.Inventario;

public class TelaSelInv
  extends Tela<Inventario>
{
  private Campo edtNum;
  
  public TelaSelInv(MenuPrincipal paramMenuPrincipal, Inventario paramInventario)
  {
    super(paramMenuPrincipal, "Selecao de Lote", paramInventario);
  }
  
  protected void actionTela(String paramString)
  {
    Inventario localInventario = getInventario(paramString);
    setResult(localInventario);
    close();
  }
  
  protected void atualizaTela()
  {
    Inventario localInventario = (Inventario)getResult();
    if (localInventario == null) {
      this.edtNum.setValor("");
    } else {
      this.edtNum.setValor(Util.string.format(localInventario.getNumero(), "0000"));
    }
  }
  
  private Inventario getInventario(String paramString)
  {
    Integer localInteger = Util.convert.toInteger(paramString);
    Inventario localInventario = Facade.inventario.findByNum(localInteger);
    return localInventario;
  }
  
  protected void iniciaControles()
  {
    addLabel(Integer.valueOf(1), Integer.valueOf(2), "Numero do Inventario");
    this.edtNum = addTexto("Numero :", Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(4));
    addLeitura("Numero : ", Integer.valueOf(1), Integer.valueOf(9), Integer.valueOf(4));
  }
  
  protected void validaLeitura(String paramString)
    throws EValidaLeitura
  {
    if (!paramString.matches("[0-9]+")) {
      throw new EValidaLeitura("Numero Invalido: " + paramString);
    }
    Inventario localInventario = getInventario(paramString);
    if (localInventario != null)
    {
      String str = localInventario.getStatusinventario();
      if (Status.FECHADA.toString().equals(str)) {
        throw new ValidaBean(localInventario, "O inventario esta fechado");
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/console/TelaSelInv.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */