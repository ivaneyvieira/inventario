package br.com.pintos.framework.util;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.beans.property.Property;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class Beans
{
  public Object get(Object paramObject, String paramString)
  {
    try
    {
      return PropertyUtils.getProperty(paramObject, paramString);
    }
    catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException localIllegalAccessException)
    {
      throw new RuntimeException(localIllegalAccessException);
    }
  }
  
  public <A extends Annotation> A getAnnotation(Property paramProperty, Class<A> paramClass)
  {
    try
    {
      Object localObject = paramProperty.getBean();
      String str = paramProperty.getName();
      Class localClass = localObject.getClass();
      Field localField = localClass.getField(str);
      return localField.getAnnotation(paramClass);
    }
    catch (NoSuchFieldException|SecurityException localNoSuchFieldException)
    {
      throw new RuntimeException(localNoSuchFieldException);
    }
  }
  
  public Class getTipo(Class paramClass, String paramString)
  {
    PropertyDescriptor[] arrayOfPropertyDescriptor1 = PropertyUtils.getPropertyDescriptors(paramClass);
    for (PropertyDescriptor localPropertyDescriptor : arrayOfPropertyDescriptor1) {
      if (localPropertyDescriptor.getName().equals(paramString)) {
        return localPropertyDescriptor.getPropertyType();
      }
    }
    throw new RuntimeException("Propriedade não encontrada");
  }
  
  public boolean isBean(Class paramClass)
  {
    return ((String.class.isAssignableFrom(paramClass)) || (Number.class.isAssignableFrom(paramClass)) || (Date.class.isAssignableFrom(paramClass)) || (Calendar.class.isAssignableFrom(paramClass)) ? 1 : 0) == 0;
  }
  
  public List<String> props(Object paramObject)
  {
    Class localClass = paramObject.getClass();
    Field[] arrayOfField1 = localClass.getDeclaredFields();
    ArrayList localArrayList = new ArrayList();
    for (Field localField : arrayOfField1) {
      localArrayList.add(localField.getName());
    }
    return localArrayList;
  }
  
  public void set(Object paramObject1, String paramString, Object paramObject2)
  {
    try
    {
      BeanUtils.setProperty(paramObject1, paramString, paramObject2);
    }
    catch (IllegalAccessException|InvocationTargetException localIllegalAccessException)
    {
      throw new RuntimeException(localIllegalAccessException);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/util/Beans.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */