package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Stop;
import javafx.util.Builder;

public class SimpleBatteryBuilder<B extends SimpleBatteryBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<SimpleBattery>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final SimpleBatteryBuilder create()
  {
    return new SimpleBatteryBuilder();
  }
  
  public final SimpleBatteryBuilder charging(boolean paramBoolean)
  {
    this.properties.put("charging", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SimpleBatteryBuilder chargeIndicator(SimpleBattery.ChargeIndicator paramChargeIndicator)
  {
    this.properties.put("chargeIndicator", new SimpleObjectProperty(paramChargeIndicator));
    return this;
  }
  
  public final SimpleBatteryBuilder chargingLevel(double paramDouble)
  {
    this.properties.put("chargingLevel", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SimpleBatteryBuilder levelColors(Stop[] paramArrayOfStop)
  {
    this.properties.put("levelColors", new SimpleObjectProperty(paramArrayOfStop));
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
  
  public final SimpleBattery build()
  {
    SimpleBattery localSimpleBattery = new SimpleBattery();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("charging".equals(str)) {
        localSimpleBattery.setCharging(((BooleanProperty)this.properties.get(str)).get());
      } else if ("chargeIndicator".equals(str)) {
        localSimpleBattery.setChargeIndicator((SimpleBattery.ChargeIndicator)((ObjectProperty)this.properties.get(str)).get());
      } else if ("chargingLevel".equals(str)) {
        localSimpleBattery.setChargingLevel(((DoubleProperty)this.properties.get(str)).get());
      } else if ("levelColors".equals(str)) {
        localSimpleBattery.setLevelColors((Stop[])((ObjectProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localSimpleBattery.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localSimpleBattery.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localSimpleBattery.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localSimpleBattery.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localSimpleBattery;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SimpleBatteryBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */