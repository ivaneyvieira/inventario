package br.com.pintos.framework.dados;

import java.io.IOException;
import java.util.Properties;

public class PropDatabase
{
  private final String propFile = "/database.property";
  private final Properties prop = new Properties();
  
  public PropDatabase()
  {
    try
    {
      getClass();
      this.prop.load(getClass().getResourceAsStream("/database.property"));
    }
    catch (IOException localIOException)
    {
      throw new Error(localIOException);
    }
  }
  
  public String database()
  {
    return this.prop.getProperty("database", "");
  }
  
  public String host()
  {
    return this.prop.getProperty("host", "localhost");
  }
  
  public String password()
  {
    return this.prop.getProperty("password");
  }
  
  public Integer port()
  {
    String str = this.prop.getProperty("port");
    return Integer.valueOf(str);
  }
  
  public String username()
  {
    return this.prop.getProperty("username");
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/PropDatabase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */