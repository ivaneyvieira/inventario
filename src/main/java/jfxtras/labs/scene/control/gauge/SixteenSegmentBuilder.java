package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class SixteenSegmentBuilder<B extends SixteenSegmentBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<SixteenSegment>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final SixteenSegmentBuilder create()
  {
    return new SixteenSegmentBuilder();
  }
  
  public final SixteenSegmentBuilder character(String paramString)
  {
    this.properties.put("character", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final SixteenSegmentBuilder character(Character paramCharacter)
  {
    this.properties.put("charCharacter", new SimpleObjectProperty(paramCharacter));
    return this;
  }
  
  public final SixteenSegmentBuilder dotOn(boolean paramBoolean)
  {
    this.properties.put("dotOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SixteenSegmentBuilder customSegmentMapping(Map<Integer, List<SixteenSegment.Segment>> paramMap)
  {
    this.properties.put("customSegmentMapping", new SimpleObjectProperty(paramMap));
    return this;
  }
  
  public final SixteenSegmentBuilder color(Color paramColor)
  {
    this.properties.put("color", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SixteenSegmentBuilder plainColor(boolean paramBoolean)
  {
    this.properties.put("plainColor", new SimpleBooleanProperty(paramBoolean));
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
  
  public final SixteenSegment build()
  {
    SixteenSegment localSixteenSegment = new SixteenSegment();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("character".equals(str)) {
        localSixteenSegment.setCharacter((String)((StringProperty)this.properties.get(str)).get());
      } else if ("charCharacter".equals(str)) {
        localSixteenSegment.setCharacter((Character)((ObjectProperty)this.properties.get(str)).get());
      } else if ("dotOn".equals(str)) {
        localSixteenSegment.setDotOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("customSegmentMapping".equals(str)) {
        localSixteenSegment.setCustomSegmentMapping((Map)((ObjectProperty)this.properties.get(str)).get());
      } else if ("color".equals(str)) {
        localSixteenSegment.setColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("plainColor".equals(str)) {
        localSixteenSegment.setPlainColor(((BooleanProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localSixteenSegment.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localSixteenSegment.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localSixteenSegment.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localSixteenSegment.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localSixteenSegment;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SixteenSegmentBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */