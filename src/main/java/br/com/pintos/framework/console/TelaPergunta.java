package br.com.pintos.framework.console;

import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.ShowDialog;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;

public class TelaPergunta
  extends TelaMenu
{
  private final String pergunta;
  
  public TelaPergunta(Tela paramTela, String paramString)
  {
    super(paramTela, "Pergunta:");
    this.pergunta = paramString;
  }
  
  protected void atualizaTela()
    throws BOException
  {}
  
  protected void iniciaMenu()
  {
    String[] arrayOfString = Util.string.wrapText(this.pergunta, 20);
    for (Object localObject2 : arrayOfString) {
      addText(((String)localObject2).trim());
    }
    ??? = new Command(this)
    {
      public void run()
      {
        TelaPergunta.this.close();
      }
    };
    addItem("Sim", (Command)???, Integer.valueOf(0));
    addItem("Nao", (Command)???, Integer.valueOf(0));
  }
  
  public Boolean onExit()
  {
    return Boolean.valueOf(getResult() != null);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/console/TelaPergunta.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */