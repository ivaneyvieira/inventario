package jfxtras.labs.internal.scene.control.behavior;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import jfxtras.labs.scene.control.gauge.XYControl;

public class XYControlBehavior
  extends BehaviorBase<XYControl>
{
  private XYControl control;
  
  public XYControlBehavior(XYControl paramXYControl)
  {
    super(paramXYControl);
    this.control = paramXYControl;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/behavior/XYControlBehavior.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */