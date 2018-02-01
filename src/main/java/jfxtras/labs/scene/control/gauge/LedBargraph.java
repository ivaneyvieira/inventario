package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import java.util.LinkedList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class LedBargraph
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "bargraph";
  private ObjectProperty<Led.Type> ledType = new SimpleObjectProperty(Led.Type.ROUND);
  private BooleanProperty frameVisible = new SimpleBooleanProperty(true);
  private DoubleProperty ledSize = new SimpleDoubleProperty(16.0D);
  private ObjectProperty<Orientation> orientation = new SimpleObjectProperty(Orientation.HORIZONTAL);
  private IntegerProperty noOfLeds = new SimpleIntegerProperty(16);
  private ObjectProperty<LinkedList<Color>> ledColors = new SimpleObjectProperty(new LinkedList());
  private BooleanProperty peakValueVisible = new SimpleBooleanProperty(false);
  private DoubleProperty value = new SimpleDoubleProperty(0.0D);
  
  public LedBargraph()
  {
    for (int i = 0; i < this.noOfLeds.get(); i++) {
      if (i < 11) {
        ((LinkedList)this.ledColors.get()).add(Color.LIME);
      } else if ((i > 10) && (i < 13)) {
        ((LinkedList)this.ledColors.get()).add(Color.YELLOW);
      } else {
        ((LinkedList)this.ledColors.get()).add(Color.RED);
      }
    }
    getStyleClass().add("bargraph");
  }
  
  public final Led.Type getLedType()
  {
    return (Led.Type)this.ledType.get();
  }
  
  public final void setLedType(Led.Type paramType)
  {
    this.ledType.set(paramType);
  }
  
  public final ObjectProperty<Led.Type> ledTypeProperty()
  {
    return this.ledType;
  }
  
  public final boolean isFrameVisible()
  {
    return this.frameVisible.get();
  }
  
  public final void setFrameVisible(boolean paramBoolean)
  {
    this.frameVisible.set(paramBoolean);
  }
  
  public final BooleanProperty frameVisibleProperty()
  {
    return this.frameVisible;
  }
  
  public final double getLedSize()
  {
    return this.ledSize.get();
  }
  
  public final void setLedSize(double paramDouble)
  {
    double d = paramDouble > 50.0D ? 50.0D : paramDouble < 10.0D ? 10.0D : paramDouble;
    this.ledSize.set(d);
  }
  
  public final DoubleProperty ledSizeProperty()
  {
    return this.ledSize;
  }
  
  public final Orientation getOrientation()
  {
    return (Orientation)this.orientation.get();
  }
  
  public final void setOrientation(Orientation paramOrientation)
  {
    this.orientation.set(paramOrientation);
  }
  
  public final ObjectProperty<Orientation> orientationProperty()
  {
    return this.orientation;
  }
  
  public final int getNoOfLeds()
  {
    return this.noOfLeds.get();
  }
  
  public final void setNoOfLeds(int paramInt)
  {
    int i = paramInt < 5 ? 5 : paramInt;
    if (i > this.noOfLeds.get()) {
      for (int j = 0; j < i - this.noOfLeds.get(); j++) {
        ((LinkedList)this.ledColors.get()).add(Color.RED);
      }
    }
    this.noOfLeds.set(i);
  }
  
  public final IntegerProperty noOfLedsProperty()
  {
    return this.noOfLeds;
  }
  
  public final LinkedList<Color> getLedColors()
  {
    return (LinkedList)this.ledColors.get();
  }
  
  public final void setLedColors(LinkedList<Color> paramLinkedList)
  {
    this.ledColors.set(paramLinkedList);
  }
  
  public final ObjectProperty<LinkedList<Color>> ledColorsProperty()
  {
    return this.ledColors;
  }
  
  public final Color getLedColor(int paramInt)
  {
    Color localColor;
    if (paramInt < 0) {
      localColor = (Color)((LinkedList)this.ledColors.get()).get(0);
    } else if (paramInt > this.noOfLeds.get() - 1) {
      localColor = (Color)((LinkedList)this.ledColors.get()).get(this.noOfLeds.get() - 1);
    } else {
      localColor = (Color)((LinkedList)this.ledColors.get()).get(paramInt);
    }
    return localColor;
  }
  
  public final void setLedColor(int paramInt, Color paramColor)
  {
    int i = paramInt - 1;
    if (i < 0) {
      ((LinkedList)this.ledColors.get()).set(0, paramColor);
    } else if (i > this.noOfLeds.get() - 1) {
      ((LinkedList)this.ledColors.get()).set(this.noOfLeds.get() - 1, paramColor);
    } else {
      ((LinkedList)this.ledColors.get()).set(i, paramColor);
    }
  }
  
  public final boolean isPeakValueVisible()
  {
    return this.peakValueVisible.get();
  }
  
  public final void setPeakValueVisible(boolean paramBoolean)
  {
    this.peakValueVisible.set(paramBoolean);
  }
  
  public final BooleanProperty peakValueVisibleProperty()
  {
    return this.peakValueVisible;
  }
  
  public final double getValue()
  {
    return this.value.get();
  }
  
  public final void setValue(double paramDouble)
  {
    double d = paramDouble > 1.0D ? 1.0D : paramDouble < 0.0D ? 0.0D : paramDouble;
    this.value.set(d);
  }
  
  public final DoubleProperty valueProperty()
  {
    return this.value;
  }
  
  public String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/LedBargraph.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */