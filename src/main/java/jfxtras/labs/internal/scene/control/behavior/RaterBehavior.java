package jfxtras.labs.internal.scene.control.behavior;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.input.MouseEvent;
import jfxtras.labs.internal.scene.control.skin.RaterSkin;
import jfxtras.labs.scene.control.gauge.Rater;

public class RaterBehavior
  extends BehaviorBase<Rater>
{
  public RaterBehavior(Rater paramRater)
  {
    super(paramRater);
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    Rater localRater = (Rater)getControl();
    RaterSkin localRaterSkin = (RaterSkin)((Rater)getControl()).getSkin();
    if ((localRater.getRating() == 1) && (localRaterSkin.getCurrentIndex() == 1)) {
      localRater.setRating(0);
    } else {
      localRater.setRating(localRaterSkin.getCurrentIndex());
    }
  }
  
  public void mouseExited(MouseEvent paramMouseEvent)
  {
    RaterSkin localRaterSkin = (RaterSkin)((Rater)getControl()).getSkin();
    localRaterSkin.deHighlightStars();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/behavior/RaterBehavior.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */