package br.com.pintos.framework.console;

import br.com.pintos.framework.util.Command;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class TelaMenu
  extends Tela<Integer>
{
  private final Map<Integer, ItemMenu> itens = new LinkedHashMap();
  private Integer numero = Integer.valueOf(1);
  private Integer linhaMenu = Integer.valueOf(2);
  
  public TelaMenu(ITerminal paramITerminal, String paramString)
  {
    this(paramITerminal, null, paramString);
  }
  
  public TelaMenu(ITerminal paramITerminal, Tela paramTela, String paramString)
  {
    super(paramITerminal, paramTela, paramString, null);
  }
  
  public TelaMenu(Tela paramTela, String paramString)
  {
    this(paramTela.getTerminal(), paramTela, paramString);
  }
  
  protected void actionTela(String paramString)
  {
    if (paramString.matches("[0-9]+"))
    {
      Integer localInteger = Integer.valueOf(paramString);
      ItemMenu localItemMenu = (ItemMenu)this.itens.get(localInteger);
      if (localItemMenu == null)
      {
        setResult(null);
        beep();
      }
      else
      {
        setResult(localInteger);
        Command localCommand = localItemMenu.getCommand();
        if (localCommand != null) {
          localCommand.execute();
        }
      }
    }
  }
  
  private Campo addItem(ItemMenu paramItemMenu)
  {
    this.itens.put(paramItemMenu.getNumero(), paramItemMenu);
    String str = paramItemMenu.getNumero() + "." + paramItemMenu.getLabel();
    Integer localInteger1 = paramItemMenu.getColsInfo();
    Campo localCampo;
    if (localInteger1.intValue() == 0) {
      localCampo = addLabel(Integer.valueOf(1), this.linhaMenu, str);
    } else {
      localCampo = addTexto(str, Integer.valueOf(1), this.linhaMenu, localInteger1);
    }
    TelaMenu localTelaMenu = this;
    Integer localInteger2 = localTelaMenu.linhaMenu;
    Integer localInteger3 = localTelaMenu.linhaMenu = Integer.valueOf(localTelaMenu.linhaMenu.intValue() + 1);
    return localCampo;
  }
  
  public Campo addItem(String paramString, Command paramCommand, Integer paramInteger)
  {
    Campo localCampo = addItem(new ItemMenu(this.numero, paramString, paramCommand, paramInteger));
    TelaMenu localTelaMenu = this;
    Integer localInteger1 = localTelaMenu.numero;
    Integer localInteger2 = localTelaMenu.numero = Integer.valueOf(localTelaMenu.numero.intValue() + 1);
    return localCampo;
  }
  
  public Campo addText()
  {
    return addText("");
  }
  
  public Campo addText(String paramString)
  {
    Campo localCampo = addTitulo(this.linhaMenu);
    localCampo.setValor(paramString);
    TelaMenu localTelaMenu = this;
    Integer localInteger1 = localTelaMenu.linhaMenu;
    Integer localInteger2 = localTelaMenu.linhaMenu = Integer.valueOf(localTelaMenu.linhaMenu.intValue() + 1);
    return localCampo;
  }
  
  protected void iniciaControles()
  {
    iniciaMenu();
    Integer localInteger = getTerminal().getLinhas();
    addLeitura("Opcao: ", Integer.valueOf(1), localInteger, Integer.valueOf(1));
  }
  
  protected abstract void iniciaMenu();
  
  protected void validaLeitura(String paramString)
    throws EValidaLeitura
  {
    if (!paramString.matches("[0-9]")) {
      beep();
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/console/TelaMenu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */