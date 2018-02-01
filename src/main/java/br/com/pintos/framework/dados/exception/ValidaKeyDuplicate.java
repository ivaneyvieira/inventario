package br.com.pintos.framework.dados.exception;

public class ValidaKeyDuplicate
  extends ValidaFieldBean
{
  private static final long serialVersionUID = -6050763029973064158L;
  
  public ValidaKeyDuplicate(Object paramObject1, String paramString, Object paramObject2)
  {
    super(paramObject1, paramString, paramObject2, String.format("O campo '%s' está duplicado", new Object[] { paramString }));
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/exception/ValidaKeyDuplicate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */