package br.com.pintos.framework.fx.controls;

import br.com.pintos.framework.util.Convert;
import br.com.pintos.framework.util.Util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.scene.control.Control;
import jfxtras.labs.scene.control.CalendarTextField;

public class ControlDate
  extends ControlFX<java.util.Date>
{
  private CalendarTextField control;
  
  public ControlDate(String paramString)
  {
    super(paramString);
  }
  
  protected Control createControl()
  {
    this.control = new CalendarTextField();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(mask());
    this.control.setDateFormat(localSimpleDateFormat);
    return this.control;
  }
  
  public java.util.Date getValue()
  {
    return this.control.getValue().getTime();
  }
  
  public java.sql.Date dataSql()
  {
    long l = getValue().getTime();
    return new java.sql.Date(l);
  }
  
  private String mask()
  {
    return "dd/MM/yyyy";
  }
  
  public void setValue(java.util.Date paramDate)
  {
    Calendar localCalendar = Util.convert.toCalendar(paramDate);
    this.control.setValue(localCalendar);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ControlDate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */