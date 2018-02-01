package jfxtras.labs.scene.control;

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
import javafx.scene.paint.Paint;
import javafx.util.Builder;

public class SlideLockBuilder<B extends SlideLockBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<SlideLock>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final SlideLockBuilder create()
  {
    return new SlideLockBuilder();
  }
  
  public final SlideLockBuilder backgroundVisible(boolean paramBoolean)
  {
    this.properties.put("BACKGROUND_VISIBLE", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SlideLockBuilder locked(boolean paramBoolean)
  {
    this.properties.put("LOCKED", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final SlideLockBuilder text(String paramString)
  {
    this.properties.put("TEXT", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final SlideLockBuilder textOpacity(double paramDouble)
  {
    this.properties.put("TEXT_OPACITY", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SlideLockBuilder buttonArrowBackgroundColor(Paint paramPaint)
  {
    this.properties.put("BUTTON_ARROW_BG_COLOR", new SimpleObjectProperty(paramPaint));
    return this;
  }
  
  public final SlideLockBuilder buttonColor(Paint paramPaint)
  {
    this.properties.put("BUTTON_COLOR", new SimpleObjectProperty(paramPaint));
    return this;
  }
  
  public final SlideLockBuilder buttonGlareVisible(boolean paramBoolean)
  {
    this.properties.put("BUTTON_GLARE", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final B prefWidth(double paramDouble)
  {
    this.properties.put("PREF_WIDTH", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B prefHeight(double paramDouble)
  {
    this.properties.put("PREF_HEIGHT", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutX(double paramDouble)
  {
    this.properties.put("LAYOUT_X", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B layoutY(double paramDouble)
  {
    this.properties.put("LAYOUT_Y", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final SlideLock build()
  {
    SlideLock localSlideLock = new SlideLock();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("LOCKED".equals(str)) {
        localSlideLock.setLocked(((BooleanProperty)this.properties.get(str)).get());
      } else if ("BACKGROUND_VISIBLE".equals(str)) {
        localSlideLock.setBackgroundVisible(((BooleanProperty)this.properties.get(str)).get());
      } else if ("TEXT".equals(str)) {
        localSlideLock.setText((String)((StringProperty)this.properties.get(str)).get());
      } else if ("TEXT_OPACITY".equals(str)) {
        localSlideLock.setTextOpacity(((DoubleProperty)this.properties.get(str)).get());
      } else if ("PREF_WIDTH".equals(str)) {
        localSlideLock.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("PREF_HEIGHT".equals(str)) {
        localSlideLock.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("LAYOUT_X".equals(str)) {
        localSlideLock.setLayoutX(((DoubleProperty)this.properties.get(str)).get());
      } else if ("LAYOUT_Y".equals(str)) {
        localSlideLock.setLayoutY(((DoubleProperty)this.properties.get(str)).get());
      } else if ("BUTTON_ARROW_BG_COLOR".equals(str)) {
        localSlideLock.setButtonArrowBackgroundColor((Paint)((ObjectProperty)this.properties.get(str)).get());
      } else if ("BUTTON_COLOR".equals(str)) {
        localSlideLock.setButtonColor((Paint)((ObjectProperty)this.properties.get(str)).get());
      } else if ("BUTTON_GLARE".equals(str)) {
        localSlideLock.setButtonGlareVisible(((BooleanProperty)this.properties.get(str)).get());
      }
    }
    return localSlideLock;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/SlideLockBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */