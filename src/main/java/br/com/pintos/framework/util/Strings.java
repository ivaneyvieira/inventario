package br.com.pintos.framework.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.text.MaskFormatter;

public class Strings
{
  public String format(Number paramNumber, String paramString)
  {
    if (paramNumber == null) {
      return "";
    }
    DecimalFormat localDecimalFormat = new DecimalFormat(paramString);
    return localDecimalFormat.format(paramNumber);
  }
  
  public String format(Object paramObject)
  {
    return format(paramObject, "");
  }
  
  public String format(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return "";
    }
    if ((paramObject instanceof Date)) {
      return formatDate((Date)paramObject, paramString);
    }
    if ((paramObject instanceof Calendar))
    {
      Calendar localCalendar = (Calendar)paramObject;
      return formatDate(localCalendar.getTime(), paramString);
    }
    if ((paramObject instanceof Number)) {
      return formatNumber((Number)paramObject, paramString);
    }
    if ((paramObject instanceof String)) {
      return formatString((String)paramObject, paramString);
    }
    return paramObject.toString();
  }
  
  public String formatDate(Date paramDate)
  {
    return formatDate(paramDate, "");
  }
  
  public String formatDate(Date paramDate, String paramString)
  {
    if (paramString.equals("")) {
      paramString = "dd/MM/yyyy";
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
    return localSimpleDateFormat.format(paramDate);
  }
  
  public String formatNumber(Number paramNumber, String paramString)
  {
    if (paramString.equals("")) {
      paramString = "#,###,##0.###";
    }
    DecimalFormat localDecimalFormat = new DecimalFormat(paramString);
    return localDecimalFormat.format(paramNumber);
  }
  
  public String formatString(String paramString)
  {
    return formatString(paramString, "");
  }
  
  public String formatString(String paramString1, String paramString2)
  {
    try
    {
      if (paramString2.equals("")) {
        return paramString1;
      }
      MaskFormatter localMaskFormatter = new MaskFormatter(paramString2);
      return localMaskFormatter.valueToString(paramString1);
    }
    catch (ParseException localParseException) {}
    return paramString1;
  }
  
  public String lpad(String paramString, int paramInt)
  {
    return String.format("%1$" + paramInt + "s", new Object[] { paramString });
  }
  
  public String rpad(String paramString, int paramInt)
  {
    return String.format("%1$-" + paramInt + "s", new Object[] { paramString });
  }
  
  public String[] wrapText(String paramString, int paramInt)
  {
    if (paramString == null) {
      return new String[0];
    }
    if (paramInt <= 0) {
      return new String[] { paramString };
    }
    if (paramString.length() <= paramInt) {
      return new String[] { paramString };
    }
    char[] arrayOfChar = paramString.toCharArray();
    Vector localVector = new Vector();
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    for (int i = 0; i < arrayOfChar.length; i++)
    {
      localStringBuffer2.append(arrayOfChar[i]);
      if (arrayOfChar[i] == ' ')
      {
        if (localStringBuffer1.length() + localStringBuffer2.length() > paramInt)
        {
          localVector.add(localStringBuffer1.toString());
          localStringBuffer1.delete(0, localStringBuffer1.length());
        }
        localStringBuffer1.append(localStringBuffer2);
        localStringBuffer2.delete(0, localStringBuffer2.length());
      }
    }
    if (localStringBuffer2.length() > 0)
    {
      if (localStringBuffer1.length() + localStringBuffer2.length() > paramInt)
      {
        localVector.add(localStringBuffer1.toString());
        localStringBuffer1.delete(0, localStringBuffer1.length());
      }
      localStringBuffer1.append(localStringBuffer2);
    }
    if (localStringBuffer1.length() > 0) {
      localVector.add(localStringBuffer1.toString());
    }
    String[] arrayOfString = new String[localVector.size()];
    int j = 0;
    Enumeration localEnumeration = localVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      arrayOfString[j] = ((String)localEnumeration.nextElement());
      j++;
    }
    return arrayOfString;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/util/Strings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */