package br.com.pintos.framework.report;

import br.com.pintos.framework.dados.exception.ErroFatal;
import br.com.pintos.framework.fx.controls.Coluna;
import br.com.pintos.framework.fx.controls.ProviderModel;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.ColumnBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JasperReport;

public class Report<T>
{
  private final ProviderModel<T> model;
  protected JasperReport jr;
  
  public Report(ProviderModel<T> paramProviderModel)
  {
    this.model = paramProviderModel;
  }
  
  private JasperReportBuilder buildReport()
  {
    JasperReportBuilder localJasperReportBuilder = DynamicReports.report();
    StyleBuilder localStyleBuilder1 = (StyleBuilder)DynamicReports.stl.style().bold();
    StyleBuilder localStyleBuilder2 = (StyleBuilder)DynamicReports.stl.style(localStyleBuilder1).setHorizontalAlignment(HorizontalAlignment.CENTER);
    StyleBuilder localStyleBuilder3 = (StyleBuilder)((StyleBuilder)DynamicReports.stl.style(localStyleBuilder2).setBorder(DynamicReports.stl.pen1Point())).setBackgroundColor(Color.LIGHT_GRAY);
    ((JasperReportBuilder)localJasperReportBuilder.setColumnTitleStyle(localStyleBuilder3)).highlightDetailEvenRows();
    localJasperReportBuilder.setTemplate(Templates.reportTemplate);
    localJasperReportBuilder.addTitle(new ComponentBuilder[] { Components.text(this.model.getTitulo()).setStyle(localStyleBuilder2) });
    Iterator localIterator = this.model.getColunas().iterator();
    while (localIterator.hasNext())
    {
      Coluna localColuna = (Coluna)localIterator.next();
      if (!localColuna.equals(Coluna.STATUS))
      {
        TextColumnBuilder localTextColumnBuilder = createColuna(localColuna);
        localJasperReportBuilder.addColumn(new ColumnBuilder[] { localTextColumnBuilder });
      }
    }
    localJasperReportBuilder.setDataSource(this.model.getLista(true));
    return localJasperReportBuilder;
  }
  
  private TextColumnBuilder createColuna(Coluna paramColuna)
  {
    TextColumnBuilder localTextColumnBuilder = Columns.column(paramColuna.getTitulo(), paramColuna.getNome(), paramColuna.getTipo());
    if (paramColuna.getLargura() > 0) {
      localTextColumnBuilder.setFixedWidth(Integer.valueOf(12 * paramColuna.getLargura()));
    }
    String str = paramColuna.getMask();
    if (!str.equals("")) {
      localTextColumnBuilder.setPattern(str);
    }
    return localTextColumnBuilder;
  }
  
  public void exportReportHtml(String paramString)
  {
    JasperReportBuilder localJasperReportBuilder = buildReport();
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      localJasperReportBuilder.toXhtml(localFileOutputStream);
      localFileOutputStream.close();
    }
    catch (DRException|IOException localDRException)
    {
      throw new ErroFatal(localDRException);
    }
  }
  
  public void exportReportPdf(String paramString)
  {
    JasperReportBuilder localJasperReportBuilder = buildReport();
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      localJasperReportBuilder.toPdf(localFileOutputStream);
      localFileOutputStream.close();
    }
    catch (DRException|IOException localDRException)
    {
      throw new ErroFatal(localDRException);
    }
  }
  
  public void exportReportXls(String paramString)
  {
    JasperReportBuilder localJasperReportBuilder = buildReport();
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      localJasperReportBuilder.toXlsx(localFileOutputStream);
      localFileOutputStream.close();
    }
    catch (DRException|IOException localDRException)
    {
      throw new ErroFatal(localDRException);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/report/Report.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */