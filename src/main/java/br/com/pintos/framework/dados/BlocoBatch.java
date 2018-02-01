package br.com.pintos.framework.dados;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

public class BlocoBatch
  extends BlocoExecute
{
  private final List<BlocoExecute> blocos;
  
  public BlocoBatch(List<BlocoExecute> paramList)
  {
    this.blocos = paramList;
  }
  
  protected void execute(Connection paramConnection)
  {
    Iterator localIterator = this.blocos.iterator();
    while (localIterator.hasNext())
    {
      BlocoExecute localBlocoExecute = (BlocoExecute)localIterator.next();
      localBlocoExecute.execute(paramConnection);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/BlocoBatch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */