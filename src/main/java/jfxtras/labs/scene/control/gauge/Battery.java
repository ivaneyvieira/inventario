package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

public class Battery
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "battery";
  private DoubleProperty chargingLevel = new SimpleDoubleProperty(0.0D);
  private BooleanProperty charging = new SimpleBooleanProperty(false);
  private ObjectProperty<ChargeCondition> chargeCondition = new SimpleObjectProperty(ChargeCondition.EMPTY);
  private ObjectProperty<ChargeIndicator> chargeIndicator = new SimpleObjectProperty(ChargeIndicator.PLUG);
  private ObjectProperty<Stop[]> levelColors = new SimpleObjectProperty(new Stop[] { new Stop(0.0D, Color.RED), new Stop(0.55D, Color.YELLOW), new Stop(1.0D, Color.hsb(102.0D, 1.0D, 0.85D)) });
  
  public Battery()
  {
    getStyleClass().add("battery");
  }
  
  public final double getChargingLevel()
  {
    return this.chargingLevel.get();
  }
  
  public final void setChargingLevel(double paramDouble)
  {
    this.chargingLevel.set(paramDouble > 1.0D ? 1.0D : paramDouble < 0.0D ? 0.0D : paramDouble);
  }
  
  public final DoubleProperty chargingLevelProperty()
  {
    return this.chargingLevel;
  }
  
  public final boolean isCharging()
  {
    return this.charging.get();
  }
  
  public final void setCharging(boolean paramBoolean)
  {
    this.charging.set(paramBoolean);
  }
  
  public final ChargeCondition getChargeCondition()
  {
    return (ChargeCondition)this.chargeCondition.get();
  }
  
  public final void setChargeCondition(ChargeCondition paramChargeCondition)
  {
    this.chargeCondition.set(paramChargeCondition);
  }
  
  public final ObjectProperty<ChargeCondition> chargeConditionProperty()
  {
    return this.chargeCondition;
  }
  
  public final ChargeIndicator getChargeIndicator()
  {
    return (ChargeIndicator)this.chargeIndicator.get();
  }
  
  public final void setChargeIndicator(ChargeIndicator paramChargeIndicator)
  {
    this.chargeIndicator.set(paramChargeIndicator);
  }
  
  public final ObjectProperty<ChargeIndicator> chargeIndicatorProperty()
  {
    return this.chargeIndicator;
  }
  
  public final BooleanProperty chargingProperty()
  {
    return this.charging;
  }
  
  public final Stop[] getLevelColors()
  {
    return (Stop[])this.levelColors.get();
  }
  
  public final void setLevelColors(Stop[] paramArrayOfStop)
  {
    if (paramArrayOfStop.length == 0) {
      this.levelColors.set(new Stop[] { new Stop(0.0D, Color.RED), new Stop(0.55D, Color.YELLOW), new Stop(1.0D, Color.hsb(102.0D, 1.0D, 0.85D)) });
    } else {
      this.levelColors.set(paramArrayOfStop);
    }
  }
  
  public final ObjectProperty<Stop[]> levelColorsProperty()
  {
    return this.levelColors;
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 <= paramDouble2 ? paramDouble1 : paramDouble2;
    super.setPrefSize(d, d);
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
  
  public static enum ChargeIndicator
  {
    PLUG,  FLASH;
    
    private ChargeIndicator() {}
  }
  
  public static enum ChargeCondition
  {
    EMPTY,  PARTLY_CHARGED,  CHARGED;
    
    private ChargeCondition() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Battery.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */