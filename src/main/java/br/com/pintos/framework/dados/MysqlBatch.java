package br.com.pintos.framework.dados;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.jooq.Result;
import org.jooq.impl.Factory;

public class MysqlBatch<T>
{
  private final Map<String, String> parametros = new HashMap();
  private final List<String> comandos = new ArrayList();
  private final String[] arquivoPartes;
  private final Class<T> classResult;
  
  public MysqlBatch(Class<T> paramClass, InputStream paramInputStream)
    throws IOException
  {
    String str = readFile(paramInputStream);
    this.classResult = paramClass;
    this.arquivoPartes = str.split(";");
  }
  
  public void addParametro(String paramString1, String paramString2)
  {
    this.parametros.put(paramString1, paramString2);
  }
  
  protected void adicionaComandos()
  {
    for (String str1 : this.arquivoPartes)
    {
      String str2 = str1.trim();
      if (!str2.equals("")) {
        this.comandos.add(str2);
      }
    }
  }
  
  public List<T> executa(Factory paramFactory)
  {
    Set localSet = this.parametros.entrySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Entry localEntry = (Entry)localIterator.next();
      this.comandos.add(String.format("DO @%s := %s", new Object[] { localEntry.getKey(), localEntry.getValue() }));
    }
    adicionaComandos();
    return executaComandos(paramFactory);
  }
  
  private void executaComando(Factory paramFactory, String paramString)
  {
    paramFactory.execute(paramString);
  }
  
  private List<T> executaComandos(Factory paramFactory)
  {
    int i = this.comandos.size();
    for (int j = 0; j < i - 1; j++)
    {
      String str = (String)this.comandos.get(j);
      executaComando(paramFactory, str);
    }
    return executaQuery(paramFactory, (String)this.comandos.get(i - 1));
  }
  
  private List<T> executaQuery(Factory paramFactory, String paramString)
  {
    return paramFactory.fetch(paramString).into(this.classResult);
  }
  
  private String readFile(InputStream paramInputStream)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    String str = null;
    StringBuilder localStringBuilder = new StringBuilder();
    while ((str = localBufferedReader.readLine()) != null)
    {
      localStringBuilder.append(str);
      localStringBuilder.append(' ');
    }
    return localStringBuilder.toString();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/MysqlBatch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */