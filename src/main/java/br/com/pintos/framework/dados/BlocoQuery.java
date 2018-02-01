package br.com.pintos.framework.dados;

import java.sql.Connection;

public abstract class BlocoQuery<A>
{
  protected abstract A query(Connection paramConnection);
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/BlocoQuery.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */