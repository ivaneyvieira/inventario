package br.com.pintos.coletor.modelo;

import br.com.pintos.framework.dados.exception.ErroFatal;
import br.com.pintos.framework.util.Beans;
import br.com.pintos.framework.util.Util;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Model<B>
{
  protected final B bean;
  
  public static <T, M extends Model<T>> List<M> getList(Class<M> paramClass, List<T> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      try
      {
        Model localModel = (Model)paramClass.getConstructor(new Class[] { localObject.getClass() }).newInstance(new Object[] { localObject });
        localArrayList.add(localModel);
      }
      catch (InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException localInstantiationException)
      {
        throw new ErroFatal(localInstantiationException);
      }
    }
    return localArrayList;
  }
  
  public Model(B paramB)
  {
    this.bean = paramB;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if ((paramObject instanceof Model))
    {
      Model localModel = (Model)paramObject;
      Object localObject1 = localModel.getBean();
      Object localObject2 = Util.bean.get(localObject1, "id");
      Object localObject3 = Util.bean.get(this.bean, "id");
      if (localObject3 == null) {
        return false;
      }
      return localObject3.equals(localObject2);
    }
    return false;
  }
  
  public B getBean()
  {
    return (B)this.bean;
  }
  
  public int hashCode()
  {
    return this.bean.hashCode();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/modelo/Model.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */