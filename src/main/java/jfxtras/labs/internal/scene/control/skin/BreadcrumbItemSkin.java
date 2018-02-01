package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import jfxtras.labs.scene.control.BreadcrumbBar;
import jfxtras.labs.scene.control.BreadcrumbItem;

public class BreadcrumbItemSkin
  extends SkinBase<BreadcrumbItem, BehaviorBase<BreadcrumbItem>>
{
  public BreadcrumbItemSkin(BreadcrumbItem paramBreadcrumbItem)
  {
    super(paramBreadcrumbItem, new BehaviorBase(paramBreadcrumbItem));
    HBox localHBox1 = new HBox();
    final SVGPath localSVGPath1 = new SVGPath();
    if (!paramBreadcrumbItem.isFirst()) {
      localSVGPath1.setContent("M0 0 L15 0 l0 30 L0 30 l10 -15 Z");
    } else {
      localSVGPath1.setContent("M0 0 L15 0 l0 30 L0 30 Z");
    }
    localSVGPath1.getStyleClass().add("breadcrumbitem-ui");
    final SVGPath localSVGPath2 = new SVGPath();
    localSVGPath2.setContent("M0,0 L5,0 15,15 5,30 0,30 Z");
    localSVGPath2.getStyleClass().add("breadcrumbitem-ui");
    localSVGPath2.setLayoutY(10.0D);
    final StackPane localStackPane = new StackPane();
    localStackPane.getStyleClass().add("breadcrumbitem-ui");
    localStackPane.setAlignment(Pos.CENTER);
    HBox localHBox2 = new HBox(10.0D);
    localHBox2.setAlignment(Pos.CENTER);
    localStackPane.getChildren().add(localHBox2);
    localHBox1.getChildren().add(localSVGPath1);
    localHBox1.getChildren().add(localStackPane);
    final Label localLabel = new Label();
    if (paramBreadcrumbItem != null)
    {
      if (paramBreadcrumbItem.getIcon() != null)
      {
        ImageView localImageView = new ImageView(paramBreadcrumbItem.getIcon());
        localImageView.setPreserveRatio(true);
        localImageView.setFitHeight(20.0D);
        localHBox2.getChildren().add(localImageView);
      }
      if (paramBreadcrumbItem.getSvgIcon() != null) {
        localHBox2.getChildren().add(paramBreadcrumbItem.getSvgIcon());
      }
      if (paramBreadcrumbItem.getText() != null)
      {
        localLabel.setText(paramBreadcrumbItem.getText());
        localLabel.getStyleClass().add("breadcrumbitem-text");
        localHBox2.getChildren().add(localLabel);
      }
    }
    localHBox1.getChildren().add(localSVGPath2);
    localSVGPath1.addEventHandler(MouseEvent.ANY, new BreadcrumbItemMouseHandler(localSVGPath2, localStackPane) {});
    localSVGPath2.addEventHandler(MouseEvent.ANY, new BreadcrumbItemMouseHandler(localSVGPath1, localStackPane) {});
    localStackPane.addEventHandler(MouseEvent.ANY, new BreadcrumbItemMouseHandler(localSVGPath2, localSVGPath1) {});
    localHBox1.setPrefHeight(paramBreadcrumbItem.getBreadcrumbBar().getPrefHeight());
    getChildren().add(localHBox1);
  }
  
  private class BreadcrumbItemMouseHandler
    implements EventHandler<MouseEvent>
  {
    List<Node> nodes = null;
    
    private BreadcrumbItemMouseHandler() {}
    
    public List<Node> getNodes()
    {
      if (this.nodes == null) {
        this.nodes = new ArrayList();
      }
      return this.nodes;
    }
    
    public void handle(MouseEvent paramMouseEvent)
    {
      Iterator localIterator;
      Node localNode;
      if (paramMouseEvent.getEventType().equals(MouseEvent.MOUSE_ENTERED))
      {
        localIterator = getNodes().iterator();
        while (localIterator.hasNext())
        {
          localNode = (Node)localIterator.next();
          localNode.getStyleClass().add("breadcrumbitem-ui-hover");
          localNode.getStyleClass().add("breadcrumbitem-text-hover");
        }
      }
      else if (paramMouseEvent.getEventType().equals(MouseEvent.MOUSE_EXITED))
      {
        localIterator = getNodes().iterator();
        while (localIterator.hasNext())
        {
          localNode = (Node)localIterator.next();
          localNode.getStyleClass().remove("breadcrumbitem-ui-hover");
          localNode.getStyleClass().remove("breadcrumbitem-text-hover");
        }
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/BreadcrumbItemSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */