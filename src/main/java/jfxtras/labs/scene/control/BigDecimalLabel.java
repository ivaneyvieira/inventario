package jfxtras.labs.scene.control;

import java.math.BigDecimal;
import java.text.NumberFormat;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class BigDecimalLabel
  extends Label
{
  private ObjectProperty<BigDecimal> number = new SimpleObjectProperty();
  private final ObjectProperty<NumberFormat> format = new SimpleObjectProperty();
  
  public final BigDecimal getNumber()
  {
    return (BigDecimal)this.number.get();
  }
  
  public final void setNumber(BigDecimal paramBigDecimal)
  {
    this.number.set(paramBigDecimal);
  }
  
  public ObjectProperty<BigDecimal> numberProperty()
  {
    return this.number;
  }
  
  public NumberFormat getFormat()
  {
    return (NumberFormat)this.format.getValue();
  }
  
  public final void setFormat(NumberFormat paramNumberFormat)
  {
    this.format.set(paramNumberFormat);
  }
  
  public ObjectProperty<NumberFormat> formatProperty()
  {
    return this.format;
  }
  
  public BigDecimalLabel()
  {
    this(BigDecimal.ZERO);
  }
  
  public BigDecimalLabel(BigDecimal paramBigDecimal)
  {
    this(paramBigDecimal, NumberFormat.getInstance());
  }
  
  public BigDecimalLabel(BigDecimal paramBigDecimal, NumberFormat paramNumberFormat)
  {
    setFormat(paramNumberFormat);
    initHandlers();
    setNumber(paramBigDecimal);
  }
  
  private void initHandlers()
  {
    numberProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends BigDecimal> paramAnonymousObservableValue, BigDecimal paramAnonymousBigDecimal1, BigDecimal paramAnonymousBigDecimal2)
      {
        BigDecimalLabel.this.setText(BigDecimalLabel.this.getFormat().format(BigDecimalLabel.this.getNumber()));
      }
    });
    formatProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends NumberFormat> paramAnonymousObservableValue, NumberFormat paramAnonymousNumberFormat1, NumberFormat paramAnonymousNumberFormat2)
      {
        BigDecimalLabel.this.setText(BigDecimalLabel.this.getFormat().format(BigDecimalLabel.this.getNumber()));
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/BigDecimalLabel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */