package br.com.pintos.framework.console;

import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;

public class TelaAviso
  extends Tela<Object>
{
  private final String aviso;
  
  public TelaAviso(ITerminal paramITerminal, String paramString)
  {
    super(paramITerminal, "Aviso", null);
    this.aviso = paramString;
  }
  
  public TelaAviso(Tela paramTela, String paramString)
  {
    super(paramTela, "Aviso", null);
    this.aviso = paramString;
  }
  
  public void actionTela(String paramString)
  {
    close();
  }
  
  protected void atualizaTela()
    throws BOException
  {
    beep();
  }
  
  protected void iniciaControles()
  {
    String[] arrayOfString1 = Util.string.wrapText(this.aviso, 20);
    int i = 1 + (9 - arrayOfString1.length) / 2;
    for (String str : arrayOfString1)
    {
      Campo localCampo = addTitulo(Integer.valueOf(i));
      localCampo.setValor(str.trim());
      i += 1;
    }
    addLeitura("<R> OK </R>", Integer.valueOf(9), Integer.valueOf(9), Integer.valueOf(1));
  }
  
  protected void validaLeitura(String paramString)
    throws EValidaLeitura
  {}
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/console/TelaAviso.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */