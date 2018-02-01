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

public class DotMatrixSegmentBuilder<B extends DotMatrixSegmentBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<DotMatrixSegment>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final DotMatrixSegmentBuilder create()
  {
    return new DotMatrixSegmentBuilder();
  }
  
  public final DotMatrixSegmentBuilder character(String paramString)
  {
    this.properties.put("character", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final DotMatrixSegmentBuilder character(Character paramCharacter)
  {
    this.properties.put("charCharacter", new SimpleObjectProperty(paramCharacter));
    return this;
  }
  
  public final DotMatrixSegmentBuilder dotOn(boolean paramBoolean)
  {
    this.properties.put("dotOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final DotMatrixSegmentBuilder customSegmentMapping(Map<Integer, List<DotMatrixSegment.Dot>> paramMap)
  {
    this.properties.put("customSegmentMapping", new SimpleObjectProperty(paramMap));
    return this;
  }
  
  public final DotMatrixSegmentBuilder color(Color paramColor)
  {
    this.properties.put("color", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final DotMatrixSegmentBuilder plainColor(boolean paramBoolean)
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
  
  public final DotMatrixSegment build()
  {
    DotMatrixSegment localDotMatrixSegment = new DotMatrixSegment();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("character".equals(str)) {
        localDotMatrixSegment.setCharacter((String)((StringProperty)this.properties.get(str)).get());
      } else if ("charCharacter".equals(str)) {
        localDotMatrixSegment.setCharacter((Character)((ObjectProperty)this.properties.get(str)).get());
      } else if ("dotOn".equals(str)) {
        localDotMatrixSegment.setDotOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("customSegmentMapping".equals(str)) {
        localDotMatrixSegment.setCustomDotMapping((Map)((ObjectProperty)this.properties.get(str)).get());
      } else if ("color".equals(str)) {
        localDotMatrixSegment.setColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("plainColor".equals(str)) {
        localDotMatrixSegment.setPlainColor(((BooleanProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localDotMatrixSegment.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localDotMatrixSegment.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localDotMatrixSegment.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localDotMatrixSegment.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localDotMatrixSegment;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/DotMatrixSegmentBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */