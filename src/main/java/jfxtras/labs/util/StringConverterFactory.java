package jfxtras.labs.util;

import javafx.util.StringConverter;

public class StringConverterFactory
{
  public static StringConverter<String> forString()
  {
    new StringConverter()
    {
      public String fromString(String paramAnonymousString)
      {
        return paramAnonymousString;
      }
      
      public String toString(String paramAnonymousString)
      {
        return paramAnonymousString;
      }
    };
  }
  
  public static StringConverter<Integer> forInteger()
  {
    new StringConverter()
    {
      public Integer fromString(String paramAnonymousString)
      {
        return Integer.valueOf(paramAnonymousString);
      }
      
      public String toString(Integer paramAnonymousInteger)
      {
        return "" + paramAnonymousInteger;
      }
    };
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/util/StringConverterFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */