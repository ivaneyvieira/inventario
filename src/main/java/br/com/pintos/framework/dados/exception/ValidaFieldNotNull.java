package br.com.pintos.framework.dados.exception;

public class ValidaFieldNotNull
  extends ValidaFieldBean
{
  private static final long serialVersionUID = -6050763029973064158L;
  
  public ValidaFieldNotNull(Object paramObject1, String paramString, Object paramObject2)
  {
    super(paramObject1, paramString, paramObject2, String.format("O campo '%s' está vazio", new Object[] { paramString }));
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/exception/ValidaFieldNotNull.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */