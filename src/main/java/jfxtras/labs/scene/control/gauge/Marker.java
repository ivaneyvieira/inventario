package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class Marker
{
  private DoubleProperty value = new SimpleDoubleProperty();
  private ObjectProperty<Color> color = new SimpleObjectProperty(Color.TRANSPARENT);
  private StringProperty text = new SimpleStringProperty("");
  private BooleanProperty visible = new SimpleBooleanProperty();
  private ObjectProperty<EventHandler<MarkerEvent>> onMarkerEvent = new SimpleObjectProperty();
  
  public Marker() {}
  
  public Marker(double paramDouble, Color paramColor)
  {
    this(paramDouble, paramColor, Double.toString(paramDouble));
  }
  
  public Marker(double paramDouble, Color paramColor, String paramString)
  {
    this(paramDouble, paramColor, paramString, true);
  }
  
  public Marker(double paramDouble, Color paramColor, String paramString, boolean paramBoolean)
  {
    setValue(paramDouble);
    setColor(paramColor);
    setText(paramString);
    setVisible(paramBoolean);
  }
  
  public final ObjectProperty<EventHandler<MarkerEvent>> onMarkerEventProperty()
  {
    return this.onMarkerEvent;
  }
  
  public final void setOnMarkerEvent(EventHandler<MarkerEvent> paramEventHandler)
  {
    onMarkerEventProperty().set(paramEventHandler);
  }
  
  public final EventHandler<MarkerEvent> getOnMarkerEvent()
  {
    return (EventHandler)onMarkerEventProperty().get();
  }
  
  public void fireMarkerEvent(MarkerEvent paramMarkerEvent)
  {
    EventHandler localEventHandler = getOnMarkerEvent();
    if (localEventHandler != null) {
      localEventHandler.handle(paramMarkerEvent);
    }
  }
  
  public final double getValue()
  {
    return this.value.get();
  }
  
  public final void setValue(double paramDouble)
  {
    this.value.set(paramDouble);
  }
  
  public final DoubleProperty valueProperty()
  {
    return this.value;
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
  
  public final String getText()
  {
    return (String)this.text.get();
  }
  
  public final void setText(String paramString)
  {
    this.text.set(paramString);
  }
  
  public final StringProperty textProperty()
  {
    return this.text;
  }
  
  public final boolean isVisible()
  {
    return this.visible.get();
  }
  
  public final void setVisible(boolean paramBoolean)
  {
    this.visible.set(paramBoolean);
  }
  
  public final BooleanProperty visibleProperty()
  {
    return this.visible;
  }
  
  public final boolean equals(Marker paramMarker)
  {
    return (Double.compare(paramMarker.getValue(), getValue()) == 0) && (paramMarker.getColor().equals(getColor())) && (paramMarker.getText().equals(getText()));
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Marker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */