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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class NixieTubeBuilder<B extends NixieTubeBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<NixieTube>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final NixieTubeBuilder create()
  {
    return new NixieTubeBuilder();
  }
  
  public final NixieTubeBuilder glowColor(Color paramColor)
  {
    this.properties.put("glowColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final NixieTubeBuilder number(String paramString)
  {
    this.properties.put("number", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final NixieTubeBuilder number(int paramInt)
  {
    this.properties.put("intNumber", new SimpleIntegerProperty(paramInt));
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
  
  public final NixieTube build()
  {
    NixieTube localNixieTube = new NixieTube();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("glowColor".equals(str)) {
        localNixieTube.setGlowColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("number".equals(str)) {
        localNixieTube.setNumber((String)((StringProperty)this.properties.get(str)).get());
      } else if ("intNumber".equals(str)) {
        localNixieTube.setNumber(((IntegerProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localNixieTube.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localNixieTube.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localNixieTube.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localNixieTube.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localNixieTube;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/NixieTubeBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */