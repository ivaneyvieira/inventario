package br.com.pintos.framework.dados;

import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.dados.exception.ErroFatal;
import br.com.pintos.framework.dados.exception.ErroInterno;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Database
{
  public static Database instance = new Database(new PropDatabase());
  private final String host;
  private final String database;
  private final String username;
  private final Integer port;
  private final String password;
  private final String url;
  private Connection connection;
  
  private Database(PropDatabase paramPropDatabase)
  {
    this(paramPropDatabase.host(), paramPropDatabase.database(), paramPropDatabase.username(), paramPropDatabase.password(), paramPropDatabase.port());
  }
  
  public Database(String paramString1, String paramString2, String paramString3, String paramString4, Integer paramInteger)
  {
    this.host = paramString1;
    this.database = paramString2;
    this.username = paramString3;
    this.password = paramString4;
    this.port = paramInteger;
    this.url = ("jdbc:mysql://" + paramString1 + ":" + paramInteger + "/" + paramString2 + "?autoReconnect=true");
  }
  
  public void close()
  {
    try
    {
      if (this.connection != null) {
        this.connection.close();
      }
    }
    catch (Throwable localThrowable)
    {
      throw new ErroInterno(localThrowable);
    }
  }
  
  private void closeTransaction()
  {
    try
    {
      this.connection.setAutoCommit(true);
    }
    catch (Throwable localThrowable)
    {
      throw new ErroInterno(localThrowable);
    }
  }
  
  private void commit()
  {
    try
    {
      if (!this.connection.getAutoCommit()) {
        this.connection.commit();
      }
    }
    catch (Throwable localThrowable)
    {
      throw new ErroInterno(localThrowable);
    }
  }
  
  private Boolean connectionValid()
  {
    if (this.connection == null) {
      return Boolean.valueOf(false);
    }
    try
    {
      if (this.connection.isClosed()) {
        return Boolean.valueOf(false);
      }
      this.connection.isValid(0);
      return Boolean.valueOf(true);
    }
    catch (Throwable localThrowable) {}
    return Boolean.valueOf(false);
  }
  
  public List<String> execScript(String paramString)
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      InputStream localInputStream = getClass().getResourceAsStream(paramString);
      InputStreamReader localInputStreamReader = new InputStreamReader(localInputStream);
      String str1 = readFileAsString(localInputStreamReader);
      String[] arrayOfString1 = str1.split(";");
      for (String str2 : arrayOfString1) {
        if ((str2.trim().length() > 0) && (str2 != null)) {
          localArrayList.add(str2);
        }
      }
      return localArrayList;
    }
    catch (IOException localIOException)
    {
      throw new ErroFatal(localIOException);
    }
  }
  
  public String getDatabase()
  {
    return this.database;
  }
  
  public String getHost()
  {
    return this.host;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public Integer getPort()
  {
    return this.port;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public Connection open()
  {
    if (!connectionValid().booleanValue()) {
      try
      {
        this.connection = DriverManager.getConnection(this.url, this.username, this.password);
      }
      catch (Throwable localThrowable)
      {
        throw new ErroInterno(localThrowable);
      }
    }
    return this.connection;
  }
  
  private boolean openTransaction()
  {
    try
    {
      if (this.connection.getAutoCommit() == true)
      {
        this.connection.setAutoCommit(false);
        return true;
      }
      return false;
    }
    catch (Throwable localThrowable)
    {
      throw new ErroInterno(localThrowable);
    }
  }
  
  private String readFileAsString(Reader paramReader)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder(1000);
    BufferedReader localBufferedReader = new BufferedReader(paramReader);
    char[] arrayOfChar = new char['Ѐ'];
    int i = 0;
    while ((i = localBufferedReader.read(arrayOfChar)) != -1)
    {
      String str = String.valueOf(arrayOfChar, 0, i);
      localStringBuilder.append(str);
      arrayOfChar = new char['Ѐ'];
    }
    localBufferedReader.close();
    return localStringBuilder.toString();
  }
  
  private void rollback()
  {
    try
    {
      if (!this.connection.getAutoCommit()) {
        this.connection.rollback();
      }
    }
    catch (Throwable localThrowable)
    {
      throw new ErroInterno(localThrowable);
    }
  }
  
  public <A> A transaction(BlocoQuery<A> paramBlocoQuery)
  {
    open();
    boolean bool = openTransaction();
    try
    {
      Object localObject1 = null;
      localObject1 = paramBlocoQuery.query(this.connection);
      commit();
      Object localObject2 = localObject1;
      return (A)localObject2;
    }
    catch (Throwable localThrowable)
    {
      rollback();
      if ((localThrowable instanceof BOException)) {
        throw ((BOException)localThrowable);
      }
      if ((localThrowable instanceof ErroInterno)) {
        throw ((ErroInterno)localThrowable);
      }
      if ((localThrowable instanceof Error)) {
        throw new ErroFatal(localThrowable);
      }
      throw new ErroInterno(localThrowable);
    }
    finally
    {
      if (bool) {
        closeTransaction();
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/Database.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */