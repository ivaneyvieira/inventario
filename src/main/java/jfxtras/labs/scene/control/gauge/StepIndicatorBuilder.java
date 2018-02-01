package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class StepIndicatorBuilder<B extends StepIndicatorBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<StepIndicator>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final StepIndicatorBuilder create()
  {
    return new StepIndicatorBuilder();
  }
  
  public final StepIndicatorBuilder noOfSteps(int paramInt)
  {
    this.properties.put("noOfSteps", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final StepIndicatorBuilder color(Color paramColor)
  {
    this.properties.put("color", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final StepIndicatorBuilder currentStep(int paramInt)
  {
    this.properties.put("currentStep", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final B prefWidth(double paramDouble)
  {
    this.properties.put("prefWidth", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B prefHeight(double paramDouble)
  {
    this.properties.put("prefHeight", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutX(double paramDouble)
  {
    this.properties.put("layoutX", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutY(double paramDouble)
  {
    this.properties.put("layoutY", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final StepIndicator build()
  {
    StepIndicator localStepIndicator = new StepIndicator();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("noOfSteps".equals(str)) {
        localStepIndicator.setNoOfSteps(((IntegerProperty)this.properties.get(str)).get());
      } else if ("color".equals(str)) {
        localStepIndicator.setColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("currentStep".equals(str)) {
        localStepIndicator.setCurrentStep(((IntegerProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localStepIndicator.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localStepIndicator.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localStepIndicator.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localStepIndicator.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localStepIndicator;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/StepIndicatorBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */