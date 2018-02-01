package br.com.pintos.framework.util;

import java.util.Calendar;
import java.util.Date;

public class Convert
{
  public Calendar toCalendar(Date paramDate)
  {
    if (paramDate == null) {
      return null;
    }
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar;
  }
  
  public Date toDate(Calendar paramCalendar)
  {
    return paramCalendar.getTime();
  }
  
  public Integer toInteger(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (paramString.matches("^[0-9]+$")) {
      return Integer.valueOf(paramString);
    }
    return null;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/util/Convert.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */