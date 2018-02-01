package br.com.pintos.coletor.console;

import br.com.pintos.framework.console.ITerminal;
import br.com.pintos.framework.console.Lanterna;
import br.com.pintos.framework.console.SwingTerminal2;
import br.com.pintos.framework.console.TelaAviso;
import br.com.pintos.framework.console.TelaMenu;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.tables.pojos.Usuario;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

public class Console
{
  private static Integer col = Integer.valueOf(20);
  private static Integer lin = Integer.valueOf(9);
  
  private static Integer getColetor(String[] paramArrayOfString)
  {
    if (paramArrayOfString.length > 0)
    {
      String str = paramArrayOfString[0];
      if (str.matches("^[0-9]+$")) {
        return Integer.valueOf(str);
      }
    }
    return null;
  }
  
  private static ITerminal getTerminal(String[] paramArrayOfString)
  {
    String str;
    if (paramArrayOfString.length > 0) {
      str = paramArrayOfString[(paramArrayOfString.length - 1)];
    } else {
      str = "TEXT";
    }
    Lanterna localLanterna = null;
    Object localObject;
    if (str.equals("SWING"))
    {
      localObject = new SwingTerminal2(col.intValue(), lin.intValue());
      localLanterna = new Lanterna((Terminal)localObject, col, lin);
    }
    else
    {
      localObject = TerminalFacade.createTextTerminal();
      localLanterna = new Lanterna((Terminal)localObject, col, lin);
    }
    return localLanterna;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    ITerminal localITerminal = getTerminal(paramArrayOfString);
    Integer localInteger = getColetor(paramArrayOfString);
    localITerminal.open();
    Usuario localUsuario = telaLogin(localITerminal);
    try
    {
      MenuPrincipal localMenuPrincipal = new MenuPrincipal(localITerminal, localUsuario, localInteger);
      localMenuPrincipal.showModal();
    }
    catch (BOException localBOException)
    {
      String str = localBOException.getMessage();
      TelaAviso localTelaAviso = new TelaAviso(localITerminal, str);
      localTelaAviso.showModal();
    }
    localITerminal.close();
  }
  
  public static Usuario telaLogin(ITerminal paramITerminal)
  {
    TelaLogin localTelaLogin = new TelaLogin(paramITerminal);
    return (Usuario)localTelaLogin.showModal();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/console/Console.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */