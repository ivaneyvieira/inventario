package jfxtras.labs.scene.control;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import jfxtras.labs.util.BreadcrumbBarEventHandler;

public class BreadcrumbBar
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "breadcrumbbar";
  private final ReadOnlyListProperty<BreadcrumbItem> items = new SimpleListProperty(FXCollections.observableArrayList(new ArrayList()));
  private final ObjectProperty<BreadcrumbItem> homeItem = new SimpleObjectProperty();
  private final ObjectProperty<BreadcrumbBarEventHandler> onItemAction = new SimpleObjectProperty();
  
  public BreadcrumbBar()
  {
    getStyleClass().add("breadcrumbbar");
    setPrefHeight(30.0D);
    setPrefWidth(200.0D);
    this.onItemAction.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends BreadcrumbBarEventHandler> paramAnonymousObservableValue, BreadcrumbBarEventHandler paramAnonymousBreadcrumbBarEventHandler1, BreadcrumbBarEventHandler paramAnonymousBreadcrumbBarEventHandler2)
      {
        Iterator localIterator;
        BreadcrumbItem localBreadcrumbItem;
        if (paramAnonymousBreadcrumbBarEventHandler1 != null)
        {
          localIterator = BreadcrumbBar.this.itemsProperty().iterator();
          while (localIterator.hasNext())
          {
            localBreadcrumbItem = (BreadcrumbItem)localIterator.next();
            localBreadcrumbItem.removeEventHandler(MouseEvent.MOUSE_CLICKED, paramAnonymousBreadcrumbBarEventHandler1);
          }
        }
        if (paramAnonymousBreadcrumbBarEventHandler2 != null)
        {
          localIterator = BreadcrumbBar.this.itemsProperty().iterator();
          while (localIterator.hasNext())
          {
            localBreadcrumbItem = (BreadcrumbItem)localIterator.next();
            localBreadcrumbItem.addEventHandler(MouseEvent.MOUSE_CLICKED, paramAnonymousBreadcrumbBarEventHandler2);
          }
        }
      }
    });
    ((ObservableList)this.items.get()).addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends BreadcrumbItem> paramAnonymousChange)
      {
        Object localObject1;
        while (paramAnonymousChange.next()) {
          if ((paramAnonymousChange.wasAdded()) && (BreadcrumbBar.this.onItemAction.get() != null))
          {
            Iterator localIterator = paramAnonymousChange.getAddedSubList().iterator();
            while (localIterator.hasNext())
            {
              localObject1 = (BreadcrumbItem)localIterator.next();
              ((BreadcrumbItem)localObject1).addEventHandler(MouseEvent.MOUSE_CLICKED, BreadcrumbBar.this.getOnItemAction());
            }
          }
        }
        paramAnonymousChange.reset();
        synchronized (BreadcrumbBar.this.itemsProperty())
        {
          localObject1 = BreadcrumbBar.this.itemsProperty().iterator();
          while (((Iterator)localObject1).hasNext())
          {
            BreadcrumbItem localBreadcrumbItem = (BreadcrumbItem)((Iterator)localObject1).next();
            ((SimpleBooleanProperty)localBreadcrumbItem.firstProperty()).set(BreadcrumbBar.this.items.indexOf(localBreadcrumbItem) == 0);
          }
        }
      }
    });
  }
  
  public final ReadOnlyListProperty<BreadcrumbItem> itemsProperty()
  {
    return this.items;
  }
  
  public final ObjectProperty<BreadcrumbItem> homeItemProperty()
  {
    return this.homeItem;
  }
  
  public final BreadcrumbItem getHomeItem()
  {
    return (BreadcrumbItem)homeItemProperty().get();
  }
  
  public final void setHomeItem(BreadcrumbItem paramBreadcrumbItem)
  {
    homeItemProperty().set(paramBreadcrumbItem);
  }
  
  public final ObjectProperty<BreadcrumbBarEventHandler> onItemActionProperty()
  {
    return this.onItemAction;
  }
  
  public final BreadcrumbBarEventHandler getOnItemAction()
  {
    return (BreadcrumbBarEventHandler)onItemActionProperty().get();
  }
  
  public final void setOnItemAction(BreadcrumbBarEventHandler paramBreadcrumbBarEventHandler)
  {
    onItemActionProperty().set(paramBreadcrumbBarEventHandler);
  }
  
  public final BreadcrumbItem addItem(BreadcrumbItem paramBreadcrumbItem)
  {
    if (paramBreadcrumbItem != null) {
      itemsProperty().add(paramBreadcrumbItem);
    }
    return paramBreadcrumbItem;
  }
  
  public final BreadcrumbItem addHome(Node paramNode)
  {
    Image localImage = new Image(getClass().getResource("/jfxtras/labs/internal/scene/control/home.png").toExternalForm());
    BreadcrumbItem localBreadcrumbItem = new BreadcrumbItem(this, "", localImage, paramNode);
    if (getHomeItem() == null) {
      ((SimpleListProperty)itemsProperty()).add(0, localBreadcrumbItem);
    } else {
      ((SimpleListProperty)itemsProperty()).set(0, localBreadcrumbItem);
    }
    setHomeItem(localBreadcrumbItem);
    return localBreadcrumbItem;
  }
  
  public final BreadcrumbItem addItem(String paramString, Image paramImage, Node paramNode)
  {
    BreadcrumbItem localBreadcrumbItem = new BreadcrumbItem(this, paramString, paramImage, paramNode);
    return addItem(localBreadcrumbItem);
  }
  
  public final BreadcrumbItem addItem(String paramString, SVGPath paramSVGPath, Node paramNode)
  {
    BreadcrumbItem localBreadcrumbItem = new BreadcrumbItem(this, paramString, paramSVGPath, paramNode);
    return addItem(localBreadcrumbItem);
  }
  
  public final BreadcrumbItem addItem(String paramString, Node paramNode)
  {
    BreadcrumbItem localBreadcrumbItem = new BreadcrumbItem(this, paramString, (Image)null, paramNode);
    return addItem(localBreadcrumbItem);
  }
  
  public final BreadcrumbItem addItem(Image paramImage, Node paramNode)
  {
    BreadcrumbItem localBreadcrumbItem = new BreadcrumbItem(this, null, paramImage, paramNode);
    return addItem(localBreadcrumbItem);
  }
  
  public final boolean removeItem(BreadcrumbItem paramBreadcrumbItem)
  {
    boolean bool = false;
    if (paramBreadcrumbItem != null)
    {
      int i = this.items.indexOf(paramBreadcrumbItem);
      if (i == this.items.size() - 1)
      {
        bool = ((SimpleListProperty)itemsProperty()).remove(paramBreadcrumbItem);
      }
      else if (i < this.items.size())
      {
        int j = this.items.size();
        ((SimpleListProperty)itemsProperty()).remove(i, this.items.size());
        bool = j > this.items.size();
      }
    }
    return bool;
  }
  
  public final boolean removeItem(int paramInt)
  {
    boolean bool = false;
    if ((paramInt >= 0) && (paramInt < itemsProperty().getSize())) {
      bool = removeItem((BreadcrumbItem)itemsProperty().get(paramInt));
    }
    return bool;
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + getClass().getSimpleName() + ".css").toExternalForm();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/BreadcrumbBar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */