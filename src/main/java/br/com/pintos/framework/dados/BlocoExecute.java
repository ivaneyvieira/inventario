package br.com.pintos.framework.dados;

import java.sql.Connection;

public abstract class BlocoExecute
  extends BlocoQuery<Object>
{
  protected abstract void execute(Connection paramConnection);
  
  protected Object query(Connection paramConnection)
  {
    execute(paramConnection);
    return null;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/BlocoExecute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */