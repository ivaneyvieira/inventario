package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Builder;

public class SectionBuilder
  implements Builder<Section>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final SectionBuilder create()
  {
    return new SectionBuilder();
  }
  
  public final SectionBuilder start(double paramDouble)
  {
    this.properties.put("start", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SectionBuilder stop(double paramDouble)
  {
    this.properties.put("stop", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SectionBuilder color(Color paramColor)
  {
    this.properties.put("color", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SectionBuilder highlightColor(Color paramColor)
  {
    this.properties.put("highlightColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SectionBuilder sectionArea(Shape paramShape)
  {
    this.properties.put("sectionArea", new SimpleObjectProperty(paramShape));
    return this;
  }
  
  public final SectionBuilder filledArea(Shape paramShape)
  {
    this.properties.put("filledArea", new SimpleObjectProperty(paramShape));
    return this;
  }
  
  public final SectionBuilder text(String paramString)
  {
    this.properties.put("text", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final Section build()
  {
    Section localSection = new Section();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("start".equals(str)) {
        localSection.setStart(((DoubleProperty)this.properties.get(str)).get());
      } else if ("stop".equals(str)) {
        localSection.setStop(((DoubleProperty)this.properties.get(str)).get());
      } else if ("color".equals(str)) {
        localSection.setColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("highlightColor".equals(str)) {
        localSection.setHighlightColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("sectionArea".equals(str)) {
        localSection.setSectionArea((Shape)((ObjectProperty)this.properties.get(str)).get());
      } else if ("filledArea".equals(str)) {
        localSection.setFilledArea((Shape)((ObjectProperty)this.properties.get(str)).get());
      } else if ("text".equals(str)) {
        localSection.setText((String)((StringProperty)this.properties.get(str)).get());
      }
    }
    return localSection;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SectionBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */