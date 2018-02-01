package br.com.pintos.framework.dados.exception;

public class ErroInterno
  extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  
  public ErroInterno(String paramString)
  {
    super(paramString);
  }
  
  public ErroInterno(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/exception/ErroInterno.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */