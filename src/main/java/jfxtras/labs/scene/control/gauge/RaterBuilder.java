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

public class RaterBuilder<B extends RaterBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<Rater>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final RaterBuilder create()
  {
    return new RaterBuilder();
  }
  
  public final RaterBuilder noOfStars(int paramInt)
  {
    this.properties.put("noOfStars", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final RaterBuilder brightColor(Color paramColor)
  {
    this.properties.put("brightColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final RaterBuilder darkColor(Color paramColor)
  {
    this.properties.put("darkColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final RaterBuilder rating(int paramInt)
  {
    this.properties.put("rating", new SimpleIntegerProperty(paramInt));
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
  
  public final Rater build()
  {
    Rater localRater = new Rater();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("noOfStars".equals(str)) {
        localRater.setNoOfStars(((IntegerProperty)this.properties.get(str)).get());
      } else if ("brightColor".equals(str)) {
        localRater.setBrightColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("darkColor".equals(str)) {
        localRater.setDarkColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("rating".equals(str)) {
        localRater.setRating(((IntegerProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localRater.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localRater.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localRater.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localRater.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localRater;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/RaterBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */