package br.com.pintos.framework.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class ReportExporter
{
  public static void exportReport(JasperPrint paramJasperPrint, String paramString)
    throws JRException, FileNotFoundException
  {
    JRPdfExporter localJRPdfExporter = new JRPdfExporter();
    File localFile1 = new File(paramString);
    File localFile2 = localFile1.getParentFile();
    if (localFile2 != null) {
      localFile2.mkdirs();
    }
    FileOutputStream localFileOutputStream = new FileOutputStream(localFile1);
    localJRPdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, paramJasperPrint);
    localJRPdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, localFileOutputStream);
    localJRPdfExporter.exportReport();
  }
  
  public static void exportReportHtml(JasperPrint paramJasperPrint, String paramString)
    throws JRException, FileNotFoundException
  {
    JRHtmlExporter localJRHtmlExporter = new JRHtmlExporter();
    File localFile1 = new File(paramString);
    File localFile2 = localFile1.getParentFile();
    if (localFile2 != null) {
      localFile2.mkdirs();
    }
    FileOutputStream localFileOutputStream = new FileOutputStream(localFile1);
    localJRHtmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, paramJasperPrint);
    localJRHtmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, localFileOutputStream);
    localJRHtmlExporter.exportReport();
  }
  
  public static void exportReportPlainXls(JasperPrint paramJasperPrint, String paramString)
    throws JRException, FileNotFoundException
  {
    JExcelApiExporter localJExcelApiExporter = new JExcelApiExporter();
    File localFile1 = new File(paramString);
    File localFile2 = localFile1.getParentFile();
    if (localFile2 != null) {
      localFile2.mkdirs();
    }
    FileOutputStream localFileOutputStream = new FileOutputStream(localFile1);
    localJExcelApiExporter.setParameter(JRExporterParameter.JASPER_PRINT, paramJasperPrint);
    localJExcelApiExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, localFileOutputStream);
    localJExcelApiExporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    localJExcelApiExporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    localJExcelApiExporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    localJExcelApiExporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
    localJExcelApiExporter.exportReport();
  }
  
  public static void exportReportXls(JasperPrint paramJasperPrint, String paramString)
    throws JRException, FileNotFoundException
  {
    JRXlsExporter localJRXlsExporter = new JRXlsExporter();
    File localFile1 = new File(paramString);
    File localFile2 = localFile1.getParentFile();
    if (localFile2 != null) {
      localFile2.mkdirs();
    }
    FileOutputStream localFileOutputStream = new FileOutputStream(localFile1);
    localJRXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, paramJasperPrint);
    localJRXlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, localFileOutputStream);
    localJRXlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
    localJRXlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    localJRXlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
    localJRXlsExporter.exportReport();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/report/ReportExporter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */