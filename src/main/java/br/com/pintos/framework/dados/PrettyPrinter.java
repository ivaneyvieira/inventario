package br.com.pintos.framework.dados;

import java.io.PrintStream;
import org.jooq.ExecuteContext;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.impl.Factory;
import org.jooq.tools.StringUtils;

public class PrettyPrinter
  extends DefaultExecuteListener
{
  public void executeStart(ExecuteContext paramExecuteContext)
  {
    Factory localFactory = new Factory(paramExecuteContext.getDialect(), new Settings().withRenderFormatted(Boolean.valueOf(true)));
    String str = "";
    if (paramExecuteContext.query() != null) {
      str = localFactory.renderInlined(paramExecuteContext.query());
    } else if (paramExecuteContext.routine() != null) {
      str = localFactory.renderInlined(paramExecuteContext.routine());
    } else if (!StringUtils.isBlank(paramExecuteContext.sql())) {
      str = paramExecuteContext.sql();
    }
    if (!StringUtils.isBlank(str))
    {
      System.out.println(str + ";");
      System.out.println();
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/PrettyPrinter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */