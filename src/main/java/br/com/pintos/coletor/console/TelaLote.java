package br.com.pintos.coletor.console;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.LoteBo;
import br.com.pintos.framework.console.Campo;
import br.com.pintos.framework.console.EValidaLeitura;
import br.com.pintos.framework.console.Tela;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.pojos.Lote;

public class TelaLote
  extends Tela<Lote>
{
  private Campo edtLoja;
  private final MenuPrincipal menuPrincipal;
  private Campo edtLote;
  private Campo edtResp;
  
  public TelaLote(MenuPrincipal paramMenuPrincipal, Lote paramLote)
  {
    super(paramMenuPrincipal, "Selecao de Lote", paramLote);
    this.menuPrincipal = paramMenuPrincipal;
  }
  
  public void actionTela(String paramString)
  {
    Integer localInteger = parserLote(paramString);
    Lote localLote = findLote(localInteger);
    setResult(localLote);
    close();
  }
  
  protected void atualizaTela()
    throws BOException
  {
    String str1 = this.menuPrincipal.getNumLote();
    String str2 = this.menuPrincipal.getQuantColeta();
    String str3 = this.menuPrincipal.getNomeLoja();
    String str4 = this.menuPrincipal.getNomeUsuario();
    this.edtLoja.setValor(String.format("LJ: %-16s", new Object[] { str3 }));
    this.edtLote.setValor(String.format("LT: %6s  QT: %4s", new Object[] { str1, str2 }));
    this.edtResp.setValor(String.format("US: %-16s", new Object[] { str4 }));
  }
  
  private Lote findLote(Integer paramInteger)
  {
    Long localLong = this.menuPrincipal.getModelo().getInventario().getLojaId();
    Lote localLote = Facade.lote.findLote(localLong, paramInteger);
    return localLote;
  }
  
  protected void iniciaControles()
  {
    this.edtLoja = addTitulo(Integer.valueOf(2));
    this.edtLote = addTitulo(Integer.valueOf(3));
    this.edtResp = addTitulo(Integer.valueOf(4));
    addLeitura("Lote: ", Integer.valueOf(1), Integer.valueOf(9), Integer.valueOf(10));
  }
  
  private Integer parserLote(String paramString)
  {
    Integer localInteger = null;
    if (paramString.matches("^LOT[0-9]+$"))
    {
      String str = paramString.substring(3);
      localInteger = Integer.valueOf(str);
    }
    else if (paramString.matches("^[0-9]+$"))
    {
      localInteger = Integer.valueOf(paramString);
    }
    return localInteger;
  }
  
  protected void validaLeitura(String paramString)
    throws EValidaLeitura
  {
    if (!paramString.matches("[0-9]+")) {
      beep();
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/console/TelaLote.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */