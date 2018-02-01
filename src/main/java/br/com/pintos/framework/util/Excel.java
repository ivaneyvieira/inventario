package br.com.pintos.framework.util;

import br.com.pintos.framework.dados.exception.ErroFatal;
import br.com.pintos.framework.fx.controls.Coluna;
import br.com.pintos.framework.fx.controls.ProviderModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class Excel
{
  private void adicionaLinhas(Sheet paramSheet, ProviderModel<?> paramProviderModel)
  {
    List localList1 = paramProviderModel.getLista(false);
    List localList2 = paramProviderModel.getColunas();
    int i = 1;
    Iterator localIterator1 = localList1.iterator();
    while (localIterator1.hasNext())
    {
      Object localObject1 = localIterator1.next();
      int j = 0;
      Row localRow = paramSheet.createRow(i);
      Iterator localIterator2 = localList2.iterator();
      while (localIterator2.hasNext())
      {
        Coluna localColuna = (Coluna)localIterator2.next();
        if (!localColuna.equals(Coluna.STATUS))
        {
          Cell localCell = localRow.createCell(j);
          Object localObject2 = Util.bean.get(localObject1, localColuna.getNome());
          if (localObject2 != null)
          {
            Object localObject3;
            if ((localObject2 instanceof Number))
            {
              localObject3 = (Number)localObject2;
              localCell.setCellValue(((Number)localObject3).doubleValue());
              localCell.setCellType(0);
            }
            else if ((localObject2 instanceof Date))
            {
              localObject3 = (Date)localObject2;
              localCell.setCellValue((Date)localObject3);
              localCell.setCellType(0);
            }
            else
            {
              localObject3 = Util.string.format(localObject2);
              localCell.setCellValue((String)localObject3);
              localCell.setCellType(1);
            }
          }
          j++;
        }
      }
      i++;
    }
  }
  
  public void autoSize(Sheet paramSheet, ProviderModel<?> paramProviderModel)
  {
    int i = 0;
    Iterator localIterator = paramProviderModel.getColunas().iterator();
    while (localIterator.hasNext())
    {
      Coluna localColuna = (Coluna)localIterator.next();
      if (!localColuna.equals(Coluna.STATUS)) {
        paramSheet.autoSizeColumn(i++);
      }
    }
  }
  
  private void formataColunas(Sheet paramSheet, List<Coluna> paramList)
  {
    Row localRow = paramSheet.createRow(0);
    CellStyle localCellStyle = paramSheet.getWorkbook().createCellStyle();
    Font localFont = paramSheet.getWorkbook().createFont();
    localFont.setBoldweight((short)700);
    localCellStyle.setFont(localFont);
    int i = 0;
    localRow.setRowStyle(localCellStyle);
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Coluna localColuna = (Coluna)localIterator.next();
      if (!localColuna.equals(Coluna.STATUS))
      {
        Cell localCell = localRow.createCell(i);
        localCell.setCellValue(localColuna.getTitulo());
        localCell.setCellType(1);
        i++;
      }
    }
  }
  
  public void geraExcell(ProviderModel<?> paramProviderModel, String paramString)
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      SXSSFWorkbook localSXSSFWorkbook = new SXSSFWorkbook(100);
      Sheet localSheet = localSXSSFWorkbook.createSheet(paramProviderModel.getTitulo());
      formataColunas(localSheet, paramProviderModel.getColunas());
      autoSize(localSheet, paramProviderModel);
      adicionaLinhas(localSheet, paramProviderModel);
      autoSize(localSheet, paramProviderModel);
      localSXSSFWorkbook.write(localFileOutputStream);
      localFileOutputStream.close();
    }
    catch (IOException localIOException)
    {
      throw new ErroFatal(localIOException);
    }
  }
  
  public void open(String paramString)
  {
    try
    {
      new ProcessBuilder(new String[] { "excel", paramString }).start();
    }
    catch (IOException localIOException1)
    {
      try
      {
        new ProcessBuilder(new String[] { "libreoffice", paramString }).start();
      }
      catch (IOException localIOException2) {}
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/util/Excel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */