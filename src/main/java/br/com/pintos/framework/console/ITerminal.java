package br.com.pintos.framework.console;

public abstract class ITerminal
{
  private final char LINECHAR = '=';
  private final Integer colunas;
  private final Integer linhas;
  
  public ITerminal(Integer paramInteger1, Integer paramInteger2)
  {
    this.colunas = paramInteger1;
    this.linhas = paramInteger2;
  }
  
  public abstract void apagaTela();
  
  public abstract void beep();
  
  public abstract void blinkOFF();
  
  public abstract void blinkON();
  
  public abstract void brightOFF();
  
  public abstract void brightON();
  
  public abstract void close();
  
  public abstract void cusorPos(Integer paramInteger1, Integer paramInteger2);
  
  public abstract void echo(String paramString);
  
  public void escreve(String paramString)
  {
    if (paramString != null)
    {
      String str1 = paramString.toUpperCase();
      int i = 0;
      int j = str1.length();
      while (i < j)
      {
        int k = str1.indexOf('<', i);
        if (k >= 0)
        {
          String str2 = str1.substring(i, k);
          String str3 = str1.substring(k);
          echo(str2);
          i += str2.length();
          if (str3.startsWith("<N>"))
          {
            brightON();
            i += 3;
          }
          else if (str3.startsWith("<B>"))
          {
            blinkON();
            i += 3;
          }
          else if (str3.startsWith("<R>"))
          {
            reversoON();
            i += 3;
          }
          else if (str3.startsWith("<S>"))
          {
            sublinhadoON();
            i += 3;
          }
          else if (str3.startsWith("</N>"))
          {
            brightOFF();
            i += 4;
          }
          else if (str3.startsWith("</B>"))
          {
            blinkOFF();
            i += 4;
          }
          else if (str3.startsWith("</R>"))
          {
            reversoOFF();
            i += 4;
          }
          else if (str3.startsWith("</S>"))
          {
            sublinhadoOFF();
            i += 4;
          }
          else if (str3.startsWith("<"))
          {
            echo("<");
            i += 1;
          }
        }
        else
        {
          echo(str1.substring(i));
          i = j;
        }
      }
    }
  }
  
  public void escreveCen(String paramString, Integer paramInteger)
  {
    if (paramString == null) {
      paramString = "";
    }
    if (paramString.length() > paramInteger.intValue()) {
      paramString = paramString.substring(0, paramInteger.intValue());
    }
    int i = (paramInteger.intValue() - paramString.length()) / 2;
    int j = paramInteger.intValue() - i - paramString.length();
    escreve(repeteChar(Integer.valueOf(i), ' '));
    escreve(paramString);
    escreve(repeteChar(Integer.valueOf(j), ' '));
  }
  
  public void escreveDir(String paramString, Integer paramInteger)
  {
    if (paramString == null) {
      paramString = "";
    }
    if (paramString.length() > paramInteger.intValue()) {
      paramString = paramString.substring(0, paramInteger.intValue());
    }
    int i = paramInteger.intValue() - paramString.length();
    escreve(repeteChar(Integer.valueOf(i), ' '));
    escreve(paramString);
  }
  
  public void escreveEsq(String paramString, Integer paramInteger)
  {
    if (paramString == null) {
      paramString = "";
    }
    if (paramString.length() > paramInteger.intValue()) {
      paramString = paramString.substring(0, paramInteger.intValue());
    }
    int i = paramInteger.intValue() - paramString.length();
    escreve(paramString);
    escreve(repeteChar(Integer.valueOf(i), ' '));
  }
  
  public void escreveLinha()
  {
    getClass();
    String str = repeteChar(this.colunas, '=');
    echo(str);
  }
  
  public Integer getColunas()
  {
    return this.colunas;
  }
  
  public Integer getLinhas()
  {
    return this.linhas;
  }
  
  public abstract void normal();
  
  public abstract void open();
  
  public abstract String readLine(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);
  
  public String repeteChar(Integer paramInteger, char paramChar)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    while (localStringBuilder.length() < paramInteger.intValue()) {
      localStringBuilder.append(paramChar);
    }
    return localStringBuilder.toString();
  }
  
  public abstract void reversoOFF();
  
  public abstract void reversoON();
  
  public abstract void sublinhadoOFF();
  
  public abstract void sublinhadoON();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/console/ITerminal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */