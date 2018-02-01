package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class SevenSegmentBuilder<B extends SevenSegmentBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<SevenSegment>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final SevenSegmentBuilder create()
  {
    return new SevenSegmentBuilder();
  }
  
  public final SevenSegmentBuilder character(String paramString)
  {
    this.properties.put("character", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final SevenSegmentBuilder character(Character paramCharacter)
  {
    this.properties.put("charCharacter", new SimpleObjectProperty(paramCharacter));
    return this;
  }
  
  public final SevenSegmentBuilder character(int paramInt)
  {
    this.properties.put("intCharacter", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final SevenSegmentBuilder dotOn(boolean paramBoolean)
  {
    this.properties.put("dotOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SevenSegmentBuilder customSegmentMapping(Map<Integer, List<SevenSegment.Segment>> paramMap)
  {
    this.properties.put("customSegmentMapping", new SimpleObjectProperty(paramMap));
    return this;
  }
  
  public final SevenSegmentBuilder color(Color paramColor)
  {
    this.properties.put("color", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SevenSegmentBuilder plainColor(boolean paramBoolean)
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
  
  public final SevenSegment build()
  {
    SevenSegment localSevenSegment = new SevenSegment();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("character".equals(str)) {
        localSevenSegment.setCharacter((String)((StringProperty)this.properties.get(str)).get());
      } else if ("charCharacter".equals(str)) {
        localSevenSegment.setCharacter((Character)((ObjectProperty)this.properties.get(str)).get());
      } else if ("intCharacter".equals(str)) {
        localSevenSegment.setCharacter(((IntegerProperty)this.properties.get(str)).get());
      } else if ("dotOn".equals(str)) {
        localSevenSegment.setDotOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("customSegmentMapping".equals(str)) {
        localSevenSegment.setCustomSegmentMapping((Map)((ObjectProperty)this.properties.get(str)).get());
      } else if ("color".equals(str)) {
        localSevenSegment.setColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("plainColor".equals(str)) {
        localSevenSegment.setPlainColor(((BooleanProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localSevenSegment.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localSevenSegment.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localSevenSegment.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localSevenSegment.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localSevenSegment;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SevenSegmentBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */