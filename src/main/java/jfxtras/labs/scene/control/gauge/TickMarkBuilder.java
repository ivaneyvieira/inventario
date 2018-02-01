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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Builder;

public class TickMarkBuilder<B extends TickMarkBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<TickMark>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final TickMarkBuilder create()
  {
    return new TickMarkBuilder();
  }
  
  public final TickMarkBuilder type(TickMark.Type paramType)
  {
    this.properties.put("type", new SimpleObjectProperty(paramType));
    return this;
  }
  
  public final TickMarkBuilder indicator(TickMark.Indicator paramIndicator)
  {
    this.properties.put("indicator", new SimpleObjectProperty(paramIndicator));
    return this;
  }
  
  public final TickMarkBuilder indicatorColor(Color paramColor)
  {
    this.properties.put("indicatorColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final TickMarkBuilder indicatorVisible(boolean paramBoolean)
  {
    this.properties.put("indicatorVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final TickMarkBuilder label(String paramString)
  {
    this.properties.put("label", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final TickMarkBuilder labelColor(Color paramColor)
  {
    this.properties.put("labelColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final TickMarkBuilder labelVisible(boolean paramBoolean)
  {
    this.properties.put("labelVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final TickMarkBuilder labelFont(Font paramFont)
  {
    this.properties.put("labelFont", new SimpleObjectProperty(paramFont));
    return this;
  }
  
  public final TickMarkBuilder labelFontSizeFactor(double paramDouble)
  {
    this.properties.put("labelFontSizeFactor", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final TickMarkBuilder tickLabelOrientation(TickMark.TickLabelOrientation paramTickLabelOrientation)
  {
    this.properties.put("tickLabelOrientation", new SimpleObjectProperty(paramTickLabelOrientation));
    return this;
  }
  
  public TickMark build()
  {
    TickMark localTickMark = new TickMark();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("type".equals(str)) {
        localTickMark.setType((TickMark.Type)((ObjectProperty)this.properties.get(str)).get());
      } else if ("indicator".equals(str)) {
        localTickMark.setIndicator((TickMark.Indicator)((ObjectProperty)this.properties.get(str)).get());
      } else if ("indicatorColor".equals(str)) {
        localTickMark.setIndicatorColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("indicatorVisible".equals(str)) {
        localTickMark.setIndicatorVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("label".equals(str)) {
        localTickMark.setLabel((String)((StringProperty)this.properties.get(str)).get());
      } else if ("labelColor".equals(str)) {
        localTickMark.setLabelColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("labelVisible".equals(str)) {
        localTickMark.setLabelVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("labelFont".equals(str)) {
        localTickMark.setLabelFont((Font)((ObjectProperty)this.properties.get(str)).get());
      } else if ("labelFontSizeFactor".equals(str)) {
        localTickMark.setLabelFontSizeFactor(((DoubleProperty)this.properties.get(str)).get());
      } else if ("tickLabelOrientation".equals(str)) {
        localTickMark.setTickLabelOrientation((TickMark.TickLabelOrientation)((ObjectProperty)this.properties.get(str)).get());
      }
    }
    return localTickMark;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/TickMarkBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */