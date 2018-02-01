package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class SevenSegment
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "seven-segment";
  private ObjectProperty<Color> color;
  private BooleanProperty plainColor;
  private StringProperty character;
  private BooleanProperty dotOn;
  private Map<Integer, List<Segment>> mapping;
  private ObjectProperty<Map<Integer, List<Segment>>> customSegmentMapping;
  
  public SevenSegment()
  {
    this(" ", Color.rgb(255, 126, 18));
  }
  
  public SevenSegment(String paramString)
  {
    this(paramString, Color.rgb(255, 126, 18));
  }
  
  public SevenSegment(String paramString, Color paramColor)
  {
    this.color = new SimpleObjectProperty(paramColor);
    this.plainColor = new SimpleBooleanProperty(false);
    this.character = new SimpleStringProperty(paramString);
    this.dotOn = new SimpleBooleanProperty(false);
    this.mapping = new HashMap(42);
    initMapping();
    getStyleClass().add("seven-segment");
  }
  
  private void initMapping()
  {
    this.mapping.put(Integer.valueOf(20), Arrays.asList(new Segment[0]));
    this.mapping.put(Integer.valueOf(46), Arrays.asList(new Segment[] { Segment.DOT }));
    this.mapping.put(Integer.valueOf(48), Arrays.asList(new Segment[] { Segment.A, Segment.B, Segment.C, Segment.D, Segment.E, Segment.F }));
    this.mapping.put(Integer.valueOf(49), Arrays.asList(new Segment[] { Segment.B, Segment.C }));
    this.mapping.put(Integer.valueOf(50), Arrays.asList(new Segment[] { Segment.A, Segment.B, Segment.D, Segment.E, Segment.G }));
    this.mapping.put(Integer.valueOf(51), Arrays.asList(new Segment[] { Segment.A, Segment.B, Segment.C, Segment.D, Segment.G }));
    this.mapping.put(Integer.valueOf(52), Arrays.asList(new Segment[] { Segment.B, Segment.C, Segment.F, Segment.G }));
    this.mapping.put(Integer.valueOf(53), Arrays.asList(new Segment[] { Segment.A, Segment.C, Segment.D, Segment.F, Segment.G }));
    this.mapping.put(Integer.valueOf(54), Arrays.asList(new Segment[] { Segment.A, Segment.C, Segment.D, Segment.E, Segment.F, Segment.G }));
    this.mapping.put(Integer.valueOf(55), Arrays.asList(new Segment[] { Segment.A, Segment.B, Segment.C }));
    this.mapping.put(Integer.valueOf(56), Arrays.asList(new Segment[] { Segment.A, Segment.B, Segment.C, Segment.D, Segment.E, Segment.F, Segment.G }));
    this.mapping.put(Integer.valueOf(57), Arrays.asList(new Segment[] { Segment.A, Segment.B, Segment.C, Segment.D, Segment.F, Segment.G }));
  }
  
  public final Color getColor()
  {
    return (Color)this.color.get();
  }
  
  public final void setColor(Color paramColor)
  {
    this.color.set(paramColor);
  }
  
  public final ObjectProperty<Color> colorProperty()
  {
    return this.color;
  }
  
  public final boolean isPlainColor()
  {
    return this.plainColor.get();
  }
  
  public final void setPlainColor(boolean paramBoolean)
  {
    this.plainColor.set(paramBoolean);
  }
  
  public final BooleanProperty plainColorProperty()
  {
    return this.plainColor;
  }
  
  public final String getCharacter()
  {
    return (String)this.character.get();
  }
  
  public final void setCharacter(String paramString)
  {
    this.character.set(paramString);
  }
  
  public final void setCharacter(Character paramCharacter)
  {
    this.character.set(String.valueOf(paramCharacter));
  }
  
  public final void setCharacter(int paramInt)
  {
    this.character.set(Integer.toString(paramInt));
  }
  
  public final StringProperty characterProperty()
  {
    return this.character;
  }
  
  public final boolean isDotOn()
  {
    return this.dotOn.get();
  }
  
  public final void setDotOn(boolean paramBoolean)
  {
    this.dotOn.set(paramBoolean);
  }
  
  public final BooleanProperty dotOnProperty()
  {
    return this.dotOn;
  }
  
  public final Map<Integer, List<Segment>> getCustomSegmentMapping()
  {
    if (this.customSegmentMapping == null) {
      this.customSegmentMapping = new SimpleObjectProperty(new HashMap());
    }
    return (Map)this.customSegmentMapping.get();
  }
  
  public final void setCustomSegmentMapping(Map<Integer, List<Segment>> paramMap)
  {
    if (this.customSegmentMapping == null) {
      this.customSegmentMapping = new SimpleObjectProperty(new HashMap());
    }
    ((Map)this.customSegmentMapping.get()).clear();
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      ((Map)this.customSegmentMapping.get()).put(Integer.valueOf(i), paramMap.get(Integer.valueOf(i)));
    }
  }
  
  public final ObjectProperty<Map<Integer, List<Segment>>> customSegmentMappingProperty()
  {
    if (this.customSegmentMapping == null) {
      this.customSegmentMapping = new SimpleObjectProperty(new HashMap());
    }
    return this.customSegmentMapping;
  }
  
  public final Map<Integer, List<Segment>> getSegmentMapping()
  {
    HashMap localHashMap = new HashMap(42);
    Iterator localIterator = this.mapping.keySet().iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      localHashMap.put(Integer.valueOf(i), this.mapping.get(Integer.valueOf(i)));
    }
    return localHashMap;
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1 <= paramDouble2 ? paramDouble1 : paramDouble2 / 1.5D;
    double d2 = paramDouble1 <= paramDouble2 ? paramDouble1 * 1.5D : paramDouble2;
    super.setPrefSize(d1, d2);
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
  
  public static enum Segment
  {
    A,  B,  C,  D,  E,  F,  G,  DOT;
    
    private Segment() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SevenSegment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */