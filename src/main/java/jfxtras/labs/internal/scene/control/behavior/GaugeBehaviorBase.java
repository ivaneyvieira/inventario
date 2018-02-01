package jfxtras.labs.internal.scene.control.behavior;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.input.MouseEvent;
import jfxtras.labs.scene.control.gauge.Gauge;

public class GaugeBehaviorBase<C extends Gauge>
  extends BehaviorBase<C>
{
  public GaugeBehaviorBase(C paramC)
  {
    super(paramC);
  }
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseDragged(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/behavior/GaugeBehaviorBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */