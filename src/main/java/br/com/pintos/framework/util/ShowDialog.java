package br.com.pintos.framework.util;

import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.dados.exception.ErroFatal;
import br.com.pintos.framework.dados.exception.ErroInterno;

public abstract interface ShowDialog
{
  public abstract void showErro(BOException paramBOException);
  
  public abstract void showErroFatal(ErroFatal paramErroFatal);
  
  public abstract void showErroInterno(ErroInterno paramErroInterno);
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/util/ShowDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */