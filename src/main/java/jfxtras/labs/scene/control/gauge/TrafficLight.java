package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;

public class TrafficLight
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "trafficlight";
  private BooleanProperty greenBlinking = new SimpleBooleanProperty(false);
  private BooleanProperty greenOn = new SimpleBooleanProperty(false);
  private BooleanProperty redOn = new SimpleBooleanProperty(false);
  private BooleanProperty redBlinking = new SimpleBooleanProperty(false);
  private BooleanProperty yellowOn = new SimpleBooleanProperty(false);
  private BooleanProperty yellowBlinking = new SimpleBooleanProperty(false);
  private BooleanProperty darkBackground = new SimpleBooleanProperty(false);
  
  public TrafficLight()
  {
    getStyleClass().add("trafficlight");
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1 < paramDouble2 * 0.4D ? paramDouble1 * 2.5D : paramDouble2;
    double d2 = d1 * 0.4D;
    super.setPrefSize(d2, d1);
  }
  
  public void setMinSize(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1 < paramDouble2 * 0.4D ? paramDouble1 * 2.5D : paramDouble2;
    double d2 = d1 * 0.4D;
    super.setMinSize(d2, d1);
  }
  
  public void setMaxSize(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1 < paramDouble2 * 0.4D ? paramDouble1 * 2.5D : paramDouble2;
    double d2 = d1 * 0.4D;
    super.setMaxSize(d2, d1);
  }
  
  public final boolean isRedOn()
  {
    return this.redOn.get();
  }
  
  public final void setRedOn(boolean paramBoolean)
  {
    this.redOn.set(paramBoolean);
  }
  
  public final BooleanProperty redOnProperty()
  {
    return this.redOn;
  }
  
  public final boolean isRedBlinking()
  {
    return this.redBlinking.get();
  }
  
  public final void setRedBlinking(boolean paramBoolean)
  {
    this.redBlinking.set(paramBoolean);
  }
  
  public final BooleanProperty redBlinkingProperty()
  {
    return this.redBlinking;
  }
  
  public final boolean isYellowOn()
  {
    return this.yellowOn.get();
  }
  
  public final void setYellowOn(boolean paramBoolean)
  {
    this.yellowOn.set(paramBoolean);
  }
  
  public final BooleanProperty yellowOnProperty()
  {
    return this.yellowOn;
  }
  
  public final boolean isYellowBlinking()
  {
    return this.yellowBlinking.get();
  }
  
  public final void setYellowBlinking(boolean paramBoolean)
  {
    this.yellowBlinking.set(paramBoolean);
  }
  
  public final BooleanProperty yellowBlinkingProperty()
  {
    return this.yellowBlinking;
  }
  
  public final boolean isGreenOn()
  {
    return this.greenOn.get();
  }
  
  public final void setGreenOn(boolean paramBoolean)
  {
    this.greenOn.set(paramBoolean);
  }
  
  public final BooleanProperty greenOnProperty()
  {
    return this.greenOn;
  }
  
  public final boolean isGreenBlinking()
  {
    return this.greenBlinking.get();
  }
  
  public final void setGreenBlinking(boolean paramBoolean)
  {
    this.greenBlinking.set(paramBoolean);
  }
  
  public final BooleanProperty greenBlinkingProperty()
  {
    return this.greenBlinking;
  }
  
  public final boolean isDarkBackground()
  {
    return this.darkBackground.get();
  }
  
  public final void setDarkBackground(boolean paramBoolean)
  {
    this.darkBackground.set(paramBoolean);
  }
  
  public final BooleanProperty darkBackgroundProperty()
  {
    return this.darkBackground;
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/TrafficLight.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */