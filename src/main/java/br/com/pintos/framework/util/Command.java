package br.com.pintos.framework.util;

import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.dados.exception.ErroFatal;
import br.com.pintos.framework.dados.exception.ErroInterno;

public abstract class Command
{
  private Throwable exception = null;
  private final ShowDialog dialog;
  
  public Command(ShowDialog paramShowDialog)
  {
    this.dialog = paramShowDialog;
  }
  
  public final boolean execute()
  {
    try
    {
      this.exception = null;
      run();
      return true;
    }
    catch (RuntimeException localRuntimeException)
    {
      this.exception = localRuntimeException;
      if ((localRuntimeException instanceof BOException)) {
        this.dialog.showErro((BOException)localRuntimeException);
      } else if ((localRuntimeException instanceof ErroInterno)) {
        this.dialog.showErroInterno((ErroInterno)localRuntimeException);
      } else if ((localRuntimeException instanceof ErroFatal)) {
        this.dialog.showErroFatal((ErroFatal)localRuntimeException);
      }
      Log.error(localRuntimeException);
    }
    return false;
  }
  
  public Throwable getException()
  {
    return this.exception;
  }
  
  protected abstract void run();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/util/Command.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */