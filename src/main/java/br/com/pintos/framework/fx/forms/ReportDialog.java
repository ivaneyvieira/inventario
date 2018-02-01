package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.dados.exception.ErroFatal;
import br.com.pintos.framework.fx.controls.ControlCombo;
import br.com.pintos.framework.fx.controls.ProviderList;
import br.com.pintos.framework.report.Report;
import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.Printer;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ReportDialog<B>
  extends DialogFX
{
  private ControlCombo<String> cmbPrinter;
  private final Report<B> report;
  
  public ReportDialog(String paramString, Report<B> paramReport)
  {
    super(paramString);
    this.report = paramReport;
  }
  
  private String fileUrl(String paramString)
  {
    try
    {
      File localFile = new File(paramString);
      String str = localFile.toURI().toURL().toString();
      return str;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw new ErroFatal(localMalformedURLException);
    }
  }
  
  private Command imprimir()
  {
    return null;
  }
  
  public void initControls()
  {
    setCenter(viewReport());
    super.initControls();
  }
  
  private String tempFileHtml()
  {
    String str1 = System.getProperty("java.io.tmpdir");
    String str2 = Util.string.format(Double.valueOf(Math.random() * 9999.0D), "0000");
    return str1 + File.separator + "tmp" + str2 + ".html";
  }
  
  protected ToolBar toolBar()
  {
    ToolBar localToolBar = new ToolBar();
    this.cmbPrinter = new ControlCombo("Impressora", new ProviderList("Impressora", Util.printer.getPrinters()));
    localToolBar.getItems().add(this.cmbPrinter);
    localToolBar.getItems().add(createButton("Imprimir", "printer.png", imprimir()));
    localToolBar.getItems().add(createButtonFechar());
    return localToolBar;
  }
  
  private Node viewReport()
  {
    String str1 = tempFileHtml();
    this.report.exportReportHtml(str1);
    WebView localWebView = new WebView();
    String str2 = fileUrl(str1);
    localWebView.getEngine().load(str2);
    return localWebView;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/ReportDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */