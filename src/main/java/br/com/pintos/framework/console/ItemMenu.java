package br.com.pintos.framework.console;

import br.com.pintos.framework.util.Command;

public class ItemMenu
{
  private final Integer numero;
  private final String label;
  private final Command command;
  private final Integer colsInfo;
  
  public ItemMenu(Integer paramInteger1, String paramString, Command paramCommand, Integer paramInteger2)
  {
    this.numero = paramInteger1;
    this.label = paramString;
    this.command = paramCommand;
    this.colsInfo = paramInteger2;
  }
  
  public Integer getColsInfo()
  {
    return this.colsInfo;
  }
  
  public Command getCommand()
  {
    return this.command;
  }
  
  public String getLabel()
  {
    return this.label;
  }
  
  public Integer getNumero()
  {
    return this.numero;
  }
  
  public String label()
  {
    return this.numero + " - " + this.label;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/console/ItemMenu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */