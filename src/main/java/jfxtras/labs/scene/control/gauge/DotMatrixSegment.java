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

public class DotMatrixSegment
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "dot-matrix-segment";
  private ObjectProperty<Color> color;
  private BooleanProperty plainColor;
  private StringProperty character;
  private BooleanProperty dotOn;
  private Map<Integer, List<Dot>> mapping;
  private ObjectProperty<Map<Integer, List<Dot>>> customDotMapping;
  
  public DotMatrixSegment()
  {
    this(" ", Color.rgb(255, 126, 18));
  }
  
  public DotMatrixSegment(String paramString)
  {
    this(paramString, Color.rgb(255, 126, 18));
  }
  
  public DotMatrixSegment(String paramString, Color paramColor)
  {
    this.color = new SimpleObjectProperty(paramColor);
    this.plainColor = new SimpleBooleanProperty(false);
    this.character = new SimpleStringProperty(paramString);
    this.dotOn = new SimpleBooleanProperty(false);
    this.mapping = new HashMap(72);
    initMapping();
    getStyleClass().add("dot-matrix-segment");
  }
  
  private void initMapping()
  {
    this.mapping.put(Integer.valueOf(20), Arrays.asList(new Dot[0]));
    this.mapping.put(Integer.valueOf(42), Arrays.asList(new Dot[] { Dot.D32, Dot.D13, Dot.D33, Dot.D53, Dot.D24, Dot.D34, Dot.D44, Dot.D15, Dot.D35, Dot.D55, Dot.D36 }));
    this.mapping.put(Integer.valueOf(43), Arrays.asList(new Dot[] { Dot.D32, Dot.D33, Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D54, Dot.D35, Dot.D36 }));
    this.mapping.put(Integer.valueOf(44), Arrays.asList(new Dot[] { Dot.D25, Dot.D35, Dot.D36, Dot.D27 }));
    this.mapping.put(Integer.valueOf(45), Arrays.asList(new Dot[] { Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D54 }));
    this.mapping.put(Integer.valueOf(46), Arrays.asList(new Dot[] { Dot.D35, Dot.D36, Dot.D45, Dot.D46 }));
    this.mapping.put(Integer.valueOf(47), Arrays.asList(new Dot[] { Dot.D52, Dot.D43, Dot.D34, Dot.D25, Dot.D16 }));
    this.mapping.put(Integer.valueOf(58), Arrays.asList(new Dot[] { Dot.D22, Dot.D32, Dot.D23, Dot.D33, Dot.D25, Dot.D35, Dot.D26, Dot.D36 }));
    this.mapping.put(Integer.valueOf(59), Arrays.asList(new Dot[] { Dot.D22, Dot.D32, Dot.D23, Dot.D33, Dot.D25, Dot.D35, Dot.D36, Dot.D27 }));
    this.mapping.put(Integer.valueOf(61), Arrays.asList(new Dot[] { Dot.D13, Dot.D23, Dot.D33, Dot.D43, Dot.D53, Dot.D15, Dot.D25, Dot.D35, Dot.D45, Dot.D55 }));
    this.mapping.put(Integer.valueOf(92), Arrays.asList(new Dot[] { Dot.D12, Dot.D23, Dot.D34, Dot.D45, Dot.D56 }));
    this.mapping.put(Integer.valueOf(95), Arrays.asList(new Dot[] { Dot.D17, Dot.D27, Dot.D37, Dot.D47, Dot.D57 }));
    this.mapping.put(Integer.valueOf(60), Arrays.asList(new Dot[] { Dot.D41, Dot.D32, Dot.D23, Dot.D14, Dot.D25, Dot.D36, Dot.D47 }));
    this.mapping.put(Integer.valueOf(62), Arrays.asList(new Dot[] { Dot.D21, Dot.D32, Dot.D43, Dot.D54, Dot.D45, Dot.D36, Dot.D27 }));
    this.mapping.put(Integer.valueOf(35), Arrays.asList(new Dot[] { Dot.D21, Dot.D41, Dot.D22, Dot.D42, Dot.D13, Dot.D23, Dot.D33, Dot.D43, Dot.D53, Dot.D24, Dot.D44, Dot.D15, Dot.D25, Dot.D35, Dot.D45, Dot.D55, Dot.D26, Dot.D46, Dot.D27, Dot.D47 }));
    this.mapping.put(Integer.valueOf(34), Arrays.asList(new Dot[] { Dot.D21, Dot.D41, Dot.D22, Dot.D42, Dot.D23, Dot.D43 }));
    this.mapping.put(Integer.valueOf(48), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D33, Dot.D53, Dot.D14, Dot.D34, Dot.D54, Dot.D15, Dot.D35, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(49), Arrays.asList(new Dot[] { Dot.D31, Dot.D22, Dot.D32, Dot.D33, Dot.D34, Dot.D35, Dot.D36, Dot.D17, Dot.D27, Dot.D37, Dot.D47, Dot.D57 }));
    this.mapping.put(Integer.valueOf(50), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D53, Dot.D44, Dot.D35, Dot.D26, Dot.D17, Dot.D27, Dot.D37, Dot.D47, Dot.D57 }));
    this.mapping.put(Integer.valueOf(51), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D53, Dot.D34, Dot.D44, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(52), Arrays.asList(new Dot[] { Dot.D14, Dot.D32, Dot.D42, Dot.D23, Dot.D41, Dot.D43, Dot.D44, Dot.D15, Dot.D25, Dot.D35, Dot.D45, Dot.D55, Dot.D46, Dot.D47 }));
    this.mapping.put(Integer.valueOf(53), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D51, Dot.D12, Dot.D13, Dot.D23, Dot.D33, Dot.D43, Dot.D54, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(54), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(55), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D51, Dot.D52, Dot.D43, Dot.D34, Dot.D35, Dot.D36, Dot.D37 }));
    this.mapping.put(Integer.valueOf(56), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D24, Dot.D34, Dot.D44, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(57), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D24, Dot.D34, Dot.D44, Dot.D54, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(63), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D53, Dot.D34, Dot.D44, Dot.D35, Dot.D37 }));
    this.mapping.put(Integer.valueOf(33), Arrays.asList(new Dot[] { Dot.D31, Dot.D32, Dot.D33, Dot.D34, Dot.D35, Dot.D37 }));
    this.mapping.put(Integer.valueOf(37), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D12, Dot.D22, Dot.D52, Dot.D43, Dot.D34, Dot.D25, Dot.D16, Dot.D46, Dot.D56, Dot.D47, Dot.D57 }));
    this.mapping.put(Integer.valueOf(36), Arrays.asList(new Dot[] { Dot.D31, Dot.D22, Dot.D32, Dot.D42, Dot.D52, Dot.D13, Dot.D33, Dot.D24, Dot.D34, Dot.D44, Dot.D35, Dot.D55, Dot.D16, Dot.D26, Dot.D36, Dot.D46, Dot.D37 }));
    this.mapping.put(Integer.valueOf(91), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D42, Dot.D43, Dot.D44, Dot.D45, Dot.D46, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(93), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D22, Dot.D23, Dot.D24, Dot.D25, Dot.D26, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(40), Arrays.asList(new Dot[] { Dot.D41, Dot.D32, Dot.D23, Dot.D24, Dot.D25, Dot.D36, Dot.D47 }));
    this.mapping.put(Integer.valueOf(41), Arrays.asList(new Dot[] { Dot.D21, Dot.D32, Dot.D43, Dot.D44, Dot.D45, Dot.D36, Dot.D27 }));
    this.mapping.put(Integer.valueOf(123), Arrays.asList(new Dot[] { Dot.D31, Dot.D41, Dot.D22, Dot.D23, Dot.D14, Dot.D25, Dot.D26, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(125), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D42, Dot.D43, Dot.D54, Dot.D45, Dot.D46, Dot.D27, Dot.D37 }));
    this.mapping.put(Integer.valueOf(65), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D54, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D17, Dot.D57 }));
    this.mapping.put(Integer.valueOf(66), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D17, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(67), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D51, Dot.D12, Dot.D13, Dot.D14, Dot.D15, Dot.D16, Dot.D27, Dot.D37, Dot.D47, Dot.D57 }));
    this.mapping.put(Integer.valueOf(68), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D54, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D17, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(69), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D51, Dot.D12, Dot.D13, Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D15, Dot.D16, Dot.D17, Dot.D27, Dot.D37, Dot.D47, Dot.D57 }));
    this.mapping.put(Integer.valueOf(70), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D51, Dot.D12, Dot.D13, Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D15, Dot.D16, Dot.D17 }));
    this.mapping.put(Integer.valueOf(71), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D51, Dot.D12, Dot.D13, Dot.D14, Dot.D34, Dot.D44, Dot.D54, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(72), Arrays.asList(new Dot[] { Dot.D11, Dot.D51, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D54, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D17, Dot.D57 }));
    this.mapping.put(Integer.valueOf(73), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D51, Dot.D32, Dot.D33, Dot.D34, Dot.D35, Dot.D36, Dot.D17, Dot.D27, Dot.D37, Dot.D47, Dot.D57 }));
    this.mapping.put(Integer.valueOf(74), Arrays.asList(new Dot[] { Dot.D41, Dot.D42, Dot.D43, Dot.D44, Dot.D15, Dot.D45, Dot.D16, Dot.D46, Dot.D27, Dot.D37 }));
    this.mapping.put(Integer.valueOf(75), Arrays.asList(new Dot[] { Dot.D11, Dot.D51, Dot.D12, Dot.D42, Dot.D13, Dot.D33, Dot.D14, Dot.D24, Dot.D15, Dot.D35, Dot.D16, Dot.D46, Dot.D17, Dot.D57 }));
    this.mapping.put(Integer.valueOf(76), Arrays.asList(new Dot[] { Dot.D11, Dot.D12, Dot.D13, Dot.D14, Dot.D15, Dot.D16, Dot.D17, Dot.D27, Dot.D37, Dot.D47, Dot.D57 }));
    this.mapping.put(Integer.valueOf(77), Arrays.asList(new Dot[] { Dot.D11, Dot.D51, Dot.D12, Dot.D22, Dot.D42, Dot.D52, Dot.D13, Dot.D33, Dot.D53, Dot.D14, Dot.D34, Dot.D54, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D17, Dot.D57 }));
    this.mapping.put(Integer.valueOf(78), Arrays.asList(new Dot[] { Dot.D11, Dot.D51, Dot.D12, Dot.D52, Dot.D13, Dot.D23, Dot.D53, Dot.D14, Dot.D34, Dot.D54, Dot.D15, Dot.D45, Dot.D55, Dot.D16, Dot.D56, Dot.D17, Dot.D57 }));
    this.mapping.put(Integer.valueOf(79), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D54, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(80), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D15, Dot.D16, Dot.D17 }));
    this.mapping.put(Integer.valueOf(81), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D54, Dot.D15, Dot.D35, Dot.D55, Dot.D16, Dot.D46, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(82), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D24, Dot.D34, Dot.D44, Dot.D15, Dot.D35, Dot.D16, Dot.D46, Dot.D17, Dot.D57 }));
    this.mapping.put(Integer.valueOf(83), Arrays.asList(new Dot[] { Dot.D21, Dot.D31, Dot.D41, Dot.D12, Dot.D52, Dot.D13, Dot.D24, Dot.D34, Dot.D44, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(84), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D51, Dot.D32, Dot.D33, Dot.D34, Dot.D35, Dot.D36, Dot.D37 }));
    this.mapping.put(Integer.valueOf(85), Arrays.asList(new Dot[] { Dot.D11, Dot.D51, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D54, Dot.D15, Dot.D55, Dot.D16, Dot.D56, Dot.D27, Dot.D37, Dot.D47 }));
    this.mapping.put(Integer.valueOf(86), Arrays.asList(new Dot[] { Dot.D11, Dot.D51, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D54, Dot.D15, Dot.D55, Dot.D26, Dot.D46, Dot.D37 }));
    this.mapping.put(Integer.valueOf(87), Arrays.asList(new Dot[] { Dot.D11, Dot.D51, Dot.D12, Dot.D52, Dot.D13, Dot.D53, Dot.D14, Dot.D34, Dot.D54, Dot.D15, Dot.D35, Dot.D55, Dot.D16, Dot.D26, Dot.D46, Dot.D56, Dot.D17, Dot.D57 }));
    this.mapping.put(Integer.valueOf(88), Arrays.asList(new Dot[] { Dot.D11, Dot.D51, Dot.D12, Dot.D52, Dot.D23, Dot.D43, Dot.D34, Dot.D25, Dot.D45, Dot.D16, Dot.D56, Dot.D17, Dot.D57 }));
    this.mapping.put(Integer.valueOf(89), Arrays.asList(new Dot[] { Dot.D11, Dot.D51, Dot.D12, Dot.D52, Dot.D23, Dot.D43, Dot.D34, Dot.D35, Dot.D36, Dot.D37 }));
    this.mapping.put(Integer.valueOf(90), Arrays.asList(new Dot[] { Dot.D11, Dot.D21, Dot.D31, Dot.D41, Dot.D51, Dot.D52, Dot.D43, Dot.D34, Dot.D25, Dot.D16, Dot.D17, Dot.D27, Dot.D37, Dot.D47, Dot.D57 }));
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
  
  public final Map<Integer, List<Dot>> getCustomDotMapping()
  {
    if (this.customDotMapping == null) {
      this.customDotMapping = new SimpleObjectProperty(new HashMap());
    }
    return (Map)this.customDotMapping.get();
  }
  
  public final void setCustomDotMapping(Map<Integer, List<Dot>> paramMap)
  {
    if (this.customDotMapping == null) {
      this.customDotMapping = new SimpleObjectProperty(new HashMap());
    }
    ((Map)this.customDotMapping.get()).clear();
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      ((Map)this.customDotMapping.get()).put(Integer.valueOf(i), paramMap.get(Integer.valueOf(i)));
    }
  }
  
  public final ObjectProperty<Map<Integer, List<Dot>>> customDotMappingProperty()
  {
    if (this.customDotMapping == null) {
      this.customDotMapping = new SimpleObjectProperty(new HashMap());
    }
    return this.customDotMapping;
  }
  
  public final Map<Integer, List<Dot>> getDotMapping()
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
    double d1 = paramDouble1 < paramDouble2 * 0.7192982456140351D ? paramDouble1 * 1.3902439024390243D : paramDouble2;
    double d2 = d1 * 0.7192982456140351D;
    super.setPrefSize(d2, d1);
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
  
  public static enum Dot
  {
    D11,  D21,  D31,  D41,  D51,  D12,  D22,  D32,  D42,  D52,  D13,  D23,  D33,  D43,  D53,  D14,  D24,  D34,  D44,  D54,  D15,  D25,  D35,  D45,  D55,  D16,  D26,  D36,  D46,  D56,  D17,  D27,  D37,  D47,  D57;
    
    private Dot() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/DotMatrixSegment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */