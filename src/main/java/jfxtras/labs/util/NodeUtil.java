package jfxtras.labs.util;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Window;

public class NodeUtil
{
  public static double screenX(Node paramNode)
  {
    return paramNode.localToScene(paramNode.getBoundsInLocal()).getMinX() + paramNode.getScene().getX() + paramNode.getScene().getWindow().getX();
  }
  
  public static double screenY(Node paramNode)
  {
    return paramNode.localToScene(paramNode.getBoundsInLocal()).getMinY() + paramNode.getScene().getY() + paramNode.getScene().getWindow().getY();
  }
  
  public static void removeFromParent(Node paramNode)
  {
    if ((paramNode.getParent() instanceof Group)) {
      ((Group)paramNode.getParent()).getChildren().remove(paramNode);
    } else if ((paramNode.getParent() instanceof Region)) {
      ((Pane)paramNode.getParent()).getChildren().remove(paramNode);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/util/NodeUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */