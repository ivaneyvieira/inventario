package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.Iterator;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import jfxtras.labs.scene.control.BreadcrumbBar;
import jfxtras.labs.scene.control.BreadcrumbItem;

public class BreadcrumbBarSkin
  extends SkinBase<BreadcrumbBar, BehaviorBase<BreadcrumbBar>>
{
  private HBox itemsBox = new HBox();
  
  public BreadcrumbBarSkin(BreadcrumbBar paramBreadcrumbBar)
  {
    super(paramBreadcrumbBar, new BehaviorBase(paramBreadcrumbBar));
    ((ObservableList)paramBreadcrumbBar.itemsProperty().get()).addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends BreadcrumbItem> paramAnonymousChange)
      {
        while (paramAnonymousChange.next()) {
          if (paramAnonymousChange.wasRemoved()) {
            BreadcrumbBarSkin.this.itemsBox.getChildren().removeAll(paramAnonymousChange.getRemoved());
          } else if (paramAnonymousChange.wasAdded()) {
            BreadcrumbBarSkin.this.itemsBox.getChildren().addAll(paramAnonymousChange.getAddedSubList());
          }
        }
        paramAnonymousChange.reset();
      }
    });
    this.itemsBox.getStyleClass().add("breadcrumbbar-ui");
    this.itemsBox.setStyle(paramBreadcrumbBar.getStyle());
    this.itemsBox.setSpacing(-10.0D);
    this.itemsBox.setPrefHeight(paramBreadcrumbBar.getPrefHeight());
    this.itemsBox.setPrefWidth(paramBreadcrumbBar.getPrefWidth());
    Iterator localIterator = paramBreadcrumbBar.itemsProperty().iterator();
    while (localIterator.hasNext())
    {
      BreadcrumbItem localBreadcrumbItem = (BreadcrumbItem)localIterator.next();
      this.itemsBox.getChildren().add(localBreadcrumbItem);
    }
    getChildren().add(this.itemsBox);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/BreadcrumbBarSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */