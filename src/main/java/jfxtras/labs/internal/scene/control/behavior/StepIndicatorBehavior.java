package jfxtras.labs.internal.scene.control.behavior;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.input.MouseEvent;
import jfxtras.labs.scene.control.gauge.StepIndicator;

public class StepIndicatorBehavior
  extends BehaviorBase<StepIndicator>
{
  public StepIndicatorBehavior(StepIndicator paramStepIndicator)
  {
    super(paramStepIndicator);
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    super.mousePressed(paramMouseEvent);
    ((StepIndicator)getControl()).fireStepEvent();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/behavior/StepIndicatorBehavior.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */