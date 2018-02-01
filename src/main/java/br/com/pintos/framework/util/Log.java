package br.com.pintos.framework.util;

import org.apache.log4j.Logger;

public class Log
{
  private static final Logger log = Logger.getLogger("Ivaney");
  
  public static void error(String paramString)
  {
    log.error(paramString);
  }
  
  public static void error(Throwable paramThrowable)
  {
    log.error(paramThrowable.getMessage(), paramThrowable);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/util/Log.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */