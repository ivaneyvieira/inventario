package jfxtras.labs.scene.control;

import java.math.BigDecimal;
import java.net.URL;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;

public class BigDecimalField
  extends Control
{
  private final ObjectProperty<BigDecimal> number;
  private final ObjectProperty<BigDecimal> stepwidth;
  private final ObjectProperty<NumberFormat> format;
  private final StringProperty promptText;
  private final ObjectProperty<BigDecimal> maxValue;
  private final ObjectProperty<BigDecimal> minValue;
  
  public BigDecimalField()
  {
    setStyle(null);
    getStyleClass().add("big-decimal-field");
    this.number = new SimpleObjectProperty(this, "number");
    this.stepwidth = new SimpleObjectProperty(this, "stepwidth", BigDecimal.ONE);
    this.maxValue = new SimpleObjectProperty(this, "maxValue");
    this.minValue = new SimpleObjectProperty(this, "minValue");
    this.format = new SimpleObjectProperty(this, "format", NumberFormat.getNumberInstance());
    this.promptText = new SimpleStringProperty(this, "promptText", "");
    setFocusTraversable(false);
  }
  
  public BigDecimalField(BigDecimal paramBigDecimal)
  {
    this();
    setNumber(paramBigDecimal);
  }
  
  public BigDecimalField(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, NumberFormat paramNumberFormat)
  {
    this();
    this.number.set(paramBigDecimal1);
    this.stepwidth.set(paramBigDecimal2);
    this.format.set(paramNumberFormat);
  }
  
  public String getText()
  {
    if (this.number.getValue() != null) {
      return getFormat().format(this.number.getValue());
    }
    return null;
  }
  
  public void setText(String paramString)
  {
    try
    {
      Number localNumber = getFormat().parse(paramString);
      setNumber(new BigDecimal(localNumber.toString()));
    }
    catch (ParseException localParseException)
    {
      Logger.getLogger(BigDecimalField.class.getName()).log(Level.INFO, null, localParseException);
    }
  }
  
  public void increment()
  {
    if ((getNumber() != null) && (getStepwidth() != null))
    {
      BigDecimal localBigDecimal = getNumber().add(getStepwidth());
      if (!checkBounds(localBigDecimal)) {
        return;
      }
      setNumber(localBigDecimal);
    }
  }
  
  public void decrement()
  {
    if ((getNumber() != null) && (getStepwidth() != null))
    {
      BigDecimal localBigDecimal = getNumber().subtract(getStepwidth());
      if (!checkBounds(localBigDecimal)) {
        return;
      }
      setNumber(localBigDecimal);
    }
  }
  
  public BigDecimal getNumber()
  {
    return (BigDecimal)this.number.getValue();
  }
  
  public void setNumber(BigDecimal paramBigDecimal)
  {
    if (!checkBounds(paramBigDecimal))
    {
      String str = MessageFormat.format("number {0} is out of bounds({1}, {2})", new Object[] { paramBigDecimal, this.minValue.get(), this.maxValue.get() });
      throw new IllegalArgumentException(str);
    }
    this.number.set(paramBigDecimal);
  }
  
  private boolean checkBounds(BigDecimal paramBigDecimal)
  {
    if ((paramBigDecimal != null) && (getMaxValue() != null) && (paramBigDecimal.compareTo(getMaxValue()) > 0)) {
      return false;
    }
    return (paramBigDecimal == null) || (getMinValue() == null) || (paramBigDecimal.compareTo(getMinValue()) >= 0);
  }
  
  public ObjectProperty<BigDecimal> numberProperty()
  {
    return this.number;
  }
  
  public BigDecimal getStepwidth()
  {
    return (BigDecimal)this.stepwidth.getValue();
  }
  
  public void setStepwidth(BigDecimal paramBigDecimal)
  {
    this.stepwidth.set(paramBigDecimal);
  }
  
  public ObjectProperty<BigDecimal> stepwidthProperty()
  {
    return this.stepwidth;
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
  
  public String getPromptText()
  {
    return this.promptText.getValue();
  }
  
  public final void setPromptText(String paramString)
  {
    this.promptText.setValue(paramString);
  }
  
  public StringProperty promptTextProperty()
  {
    return this.promptText;
  }
  
  public BigDecimal getMaxValue()
  {
    return (BigDecimal)this.maxValue.getValue();
  }
  
  public void setMaxValue(BigDecimal paramBigDecimal)
  {
    this.maxValue.set(paramBigDecimal);
  }
  
  public ObjectProperty<BigDecimal> maxValueProperty()
  {
    return this.maxValue;
  }
  
  public BigDecimal getMinValue()
  {
    return (BigDecimal)this.minValue.getValue();
  }
  
  public void setMinValue(BigDecimal paramBigDecimal)
  {
    this.minValue.set(paramBigDecimal);
  }
  
  public ObjectProperty<BigDecimal> minValueProperty()
  {
    return this.minValue;
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + getClass().getSimpleName() + ".css").toExternalForm();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/BigDecimalField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */