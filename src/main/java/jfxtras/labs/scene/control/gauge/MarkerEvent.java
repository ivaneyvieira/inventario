package jfxtras.labs.scene.control.gauge;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class MarkerEvent
  extends Event
{
  private final Type TYPE;
  
  public MarkerEvent()
  {
    super(new EventType());
    this.TYPE = Type.OVER_RUN;
  }
  
  public MarkerEvent(Object paramObject, EventTarget paramEventTarget, Type paramType)
  {
    super(paramObject, paramEventTarget, new EventType());
    this.TYPE = paramType;
  }
  
  public Type getType()
  {
    return this.TYPE;
  }
  
  public static enum Type
  {
    OVER_RUN,  UNDER_RUN;
    
    private Type() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/MarkerEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */