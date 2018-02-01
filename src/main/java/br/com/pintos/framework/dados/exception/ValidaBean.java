package br.com.pintos.framework.dados.exception;

public class ValidaBean
  extends BOException
{
  private final Object bean;
  private static final long serialVersionUID = -7014350961859810176L;
  
  public ValidaBean(Object paramObject, String paramString)
  {
    super(paramString);
    this.bean = paramObject;
  }
  
  public Object getBean()
  {
    return this.bean;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/exception/ValidaBean.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */