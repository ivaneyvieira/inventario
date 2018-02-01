package br.com.pintos.framework.util;

import java.util.ArrayList;
import java.util.List;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class Printer
{
  public List<String> getPrinters()
  {
    PrintService[] arrayOfPrintService1 = PrintServiceLookup.lookupPrintServices(null, null);
    ArrayList localArrayList = new ArrayList();
    for (PrintService localPrintService : arrayOfPrintService1) {
      localArrayList.add(localPrintService.getName());
    }
    return localArrayList;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/util/Printer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */