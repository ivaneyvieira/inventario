package br.com.pintos.framework.dados.exception;

public class ErroFatal
  extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  
  public ErroFatal(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/exception/ErroFatal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */