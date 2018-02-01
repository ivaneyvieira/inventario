package br.com.pintos.framework.dados;

import org.jooq.impl.Factory;

public abstract class Query<R>
{
  protected abstract R run(Factory paramFactory);
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/Query.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */