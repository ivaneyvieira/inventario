package br.com.pintos.framework.dados.exception;

public class ValidaFieldBean
  extends ValidaBean
{
  private static final long serialVersionUID = 4125104597011613469L;
  private final String fldName;
  private final Object fldValue;
  
  public ValidaFieldBean(Object paramObject1, String paramString1, Object paramObject2, String paramString2)
  {
    super(paramObject1, paramString2);
    this.fldName = paramString1;
    this.fldValue = paramObject2;
  }
  
  public String getFldName()
  {
    return this.fldName;
  }
  
  public Object getFldValue()
  {
    return this.fldValue;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/exception/ValidaFieldBean.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */