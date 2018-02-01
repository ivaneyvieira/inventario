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

public class BatteryBuilder<B extends BatteryBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<Battery>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final BatteryBuilder create()
  {
    return new BatteryBuilder();
  }
  
  public final BatteryBuilder charging(boolean paramBoolean)
  {
    this.properties.put("charging", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final BatteryBuilder chargeIndicator(Battery.ChargeIndicator paramChargeIndicator)
  {
    this.properties.put("chargeIndicator", new SimpleObjectProperty(paramChargeIndicator));
    return this;
  }
  
  public final BatteryBuilder chargingLevel(double paramDouble)
  {
    this.properties.put("chargingLevel", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final BatteryBuilder levelColors(Stop[] paramArrayOfStop)
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
  
  public final Battery build()
  {
    Battery localBattery = new Battery();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("charging".equals(str)) {
        localBattery.setCharging(((BooleanProperty)this.properties.get(str)).get());
      } else if ("chargeIndicator".equals(str)) {
        localBattery.setChargeIndicator((Battery.ChargeIndicator)((ObjectProperty)this.properties.get(str)).get());
      } else if ("chargingLevel".equals(str)) {
        localBattery.setChargingLevel(((DoubleProperty)this.properties.get(str)).get());
      } else if ("levelColors".equals(str)) {
        localBattery.setLevelColors((Stop[])((ObjectProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localBattery.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localBattery.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localBattery.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localBattery.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localBattery;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/BatteryBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */