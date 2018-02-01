package jfxtras.labs.scene.control;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Builder;

public class BigDecimalFieldBuilder<B extends BigDecimalFieldBuilder<B>>
  implements Builder<BigDecimalField>
{
  private static final String NUMBER = "number";
  private static final String NUMBER_FORMAT = "numberFormat";
  private static final String STEPWIDTH = "stepwidth";
  private static final String PROMPT_TEXT = "promptText";
  private static final String MIN_VALUE = "minValue";
  private static final String MAX_VALUE = "maxValue";
  private HashMap<String, Property> properties = new HashMap();
  
  public static final BigDecimalFieldBuilder create()
  {
    return new BigDecimalFieldBuilder();
  }
  
  public final BigDecimalFieldBuilder number(BigDecimal paramBigDecimal)
  {
    this.properties.put("number", new SimpleObjectProperty(paramBigDecimal));
    return this;
  }
  
  public final BigDecimalFieldBuilder format(NumberFormat paramNumberFormat)
  {
    this.properties.put("numberFormat", new SimpleObjectProperty(paramNumberFormat));
    return this;
  }
  
  public final BigDecimalFieldBuilder stepwidth(BigDecimal paramBigDecimal)
  {
    this.properties.put("stepwidth", new SimpleObjectProperty(paramBigDecimal));
    return this;
  }
  
  public final BigDecimalFieldBuilder promptText(String paramString)
  {
    this.properties.put("promptText", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final BigDecimalFieldBuilder minValue(BigDecimal paramBigDecimal)
  {
    this.properties.put("minValue", new SimpleObjectProperty(paramBigDecimal));
    return this;
  }
  
  public final BigDecimalFieldBuilder maxValue(BigDecimal paramBigDecimal)
  {
    this.properties.put("maxValue", new SimpleObjectProperty(paramBigDecimal));
    return this;
  }
  
  public BigDecimalField build()
  {
    BigDecimalField localBigDecimalField = new BigDecimalField();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("maxValue".equals(str)) {
        localBigDecimalField.setMaxValue((BigDecimal)((SimpleObjectProperty)this.properties.get(str)).get());
      } else if ("minValue".equals(str)) {
        localBigDecimalField.setMinValue((BigDecimal)((SimpleObjectProperty)this.properties.get(str)).get());
      } else if ("number".equals(str)) {
        localBigDecimalField.setNumber((BigDecimal)((SimpleObjectProperty)this.properties.get(str)).get());
      } else if ("numberFormat".equals(str)) {
        localBigDecimalField.setFormat((NumberFormat)((SimpleObjectProperty)this.properties.get(str)).get());
      } else if ("promptText".equals(str)) {
        localBigDecimalField.setPromptText(((SimpleStringProperty)this.properties.get(str)).get());
      } else if ("stepwidth".equals(str)) {
        localBigDecimalField.setStepwidth((BigDecimal)((SimpleObjectProperty)this.properties.get(str)).get());
      }
    }
    return localBigDecimalField;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/BigDecimalFieldBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */