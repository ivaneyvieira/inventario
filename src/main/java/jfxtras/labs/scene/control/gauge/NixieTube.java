package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class NixieTube
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "nixie-tube";
  private ObjectProperty<Color> glowColor = new SimpleObjectProperty(Color.color(1.0D, 0.4D, 0.0D, 1.0D));
  private IntegerProperty number = new SimpleIntegerProperty(-1);
  private boolean keepAspect = true;
  
  public NixieTube()
  {
    getStyleClass().add("nixie-tube");
  }
  
  public final Color getGlowColor()
  {
    return (Color)this.glowColor.get();
  }
  
  public final void setGlowColor(Color paramColor)
  {
    this.glowColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> glowColorProperty()
  {
    return this.glowColor;
  }
  
  public final int getNumber()
  {
    return this.number.get();
  }
  
  public final void setNumber(int paramInt)
  {
    if ((paramInt > 9) || (paramInt < 0)) {
      this.number.set(0);
    } else {
      this.number.set(paramInt);
    }
  }
  
  public final void setNumber(String paramString)
  {
    int i = Integer.parseInt(paramString);
    setNumber(i);
  }
  
  public final IntegerProperty numberProperty()
  {
    return this.number;
  }
  
  public String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/NixieTube.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */