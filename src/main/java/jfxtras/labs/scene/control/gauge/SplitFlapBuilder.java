package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class SplitFlapBuilder<B extends SplitFlapBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<SplitFlap>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final SplitFlapBuilder create()
  {
    return new SplitFlapBuilder();
  }
  
  public final SplitFlapBuilder textColor(Color paramColor)
  {
    this.properties.put("textColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SplitFlapBuilder color(Color paramColor)
  {
    this.properties.put("color", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final SplitFlapBuilder text(String paramString)
  {
    this.properties.put("text", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final SplitFlapBuilder selection(String[] paramArrayOfString)
  {
    this.properties.put("selection", new SimpleObjectProperty(paramArrayOfString));
    return this;
  }
  
  public final SplitFlapBuilder soundOn(boolean paramBoolean)
  {
    this.properties.put("soundOn", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SplitFlapBuilder sound(SplitFlap.Sound paramSound)
  {
    this.properties.put("sound", new SimpleObjectProperty(paramSound));
    return this;
  }
  
  public final SplitFlapBuilder frameVisible(boolean paramBoolean)
  {
    this.properties.put("frameVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SplitFlapBuilder backgroundVisible(boolean paramBoolean)
  {
    this.properties.put("backgroundVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SplitFlapBuilder interactive(boolean paramBoolean)
  {
    this.properties.put("interactive", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SplitFlapBuilder flipTimeInMs(long paramLong)
  {
    this.properties.put("flipTimeInMs", new SimpleLongProperty(paramLong));
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
  
  public final SplitFlap build()
  {
    SplitFlap localSplitFlap = new SplitFlap();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("textColor".equals(str)) {
        localSplitFlap.setTextColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("color".equals(str)) {
        localSplitFlap.setColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("text".equals(str)) {
        localSplitFlap.setText((String)((StringProperty)this.properties.get(str)).get());
      } else if ("selection".equals(str)) {
        localSplitFlap.setSelection((String[])((SimpleObjectProperty)this.properties.get(str)).get());
      } else if ("soundOn".equals(str)) {
        localSplitFlap.setSoundOn(((BooleanProperty)this.properties.get(str)).get());
      } else if ("sound".equals(str)) {
        localSplitFlap.setSound((SplitFlap.Sound)((ObjectProperty)this.properties.get(str)).get());
      } else if ("frameVisible".equals(str)) {
        localSplitFlap.setFrameVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("backgroundVisible".equals(str)) {
        localSplitFlap.setBackgroundVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("interactive".equals(str)) {
        localSplitFlap.setInteractive(((BooleanProperty)this.properties.get(str)).get());
      } else if ("flipTimeInMs".equals(str)) {
        localSplitFlap.setFlipTimeInMs(((LongProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localSplitFlap.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localSplitFlap.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutX".equals(str)) {
        localSplitFlap.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("layoutY".equals(str)) {
        localSplitFlap.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      }
    }
    return localSplitFlap;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SplitFlapBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */