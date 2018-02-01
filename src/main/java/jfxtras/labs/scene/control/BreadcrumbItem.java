package jfxtras.labs.scene.control;

import java.net.URL;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;

public class BreadcrumbItem
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "breadcrumbitem";
  private final ReadOnlyObjectProperty<BreadcrumbBar> breadcrumbBar = new SimpleObjectProperty();
  private final ObjectProperty<Node> content = new SimpleObjectProperty();
  private final StringProperty text = new SimpleStringProperty();
  private final ObjectProperty<Image> icon = new SimpleObjectProperty();
  private final ObjectProperty<SVGPath> svgIcon = new SimpleObjectProperty();
  private final ReadOnlyBooleanProperty first = new SimpleBooleanProperty(false);
  
  private BreadcrumbItem()
  {
    getStyleClass().add("breadcrumbitem");
    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (BreadcrumbItem.this.breadcrumbBarProperty().get() != null)
        {
          int i = ((ObservableList)((BreadcrumbBar)BreadcrumbItem.this.breadcrumbBar.get()).itemsProperty().get()).indexOf(BreadcrumbItem.this);
          try
          {
            BreadcrumbItem localBreadcrumbItem = (BreadcrumbItem)((ObservableList)((BreadcrumbBar)BreadcrumbItem.this.breadcrumbBar.get()).itemsProperty().get()).get(i + 1);
            ((BreadcrumbBar)BreadcrumbItem.this.breadcrumbBar.get()).removeItem(localBreadcrumbItem);
          }
          catch (Exception localException) {}
        }
      }
    });
  }
  
  protected BreadcrumbItem(BreadcrumbBar paramBreadcrumbBar, String paramString, Image paramImage, Node paramNode)
  {
    this();
    ((SimpleObjectProperty)this.breadcrumbBar).set(paramBreadcrumbBar);
    this.content.set(paramNode);
    this.icon.set(paramImage);
    this.text.set(paramString);
  }
  
  protected BreadcrumbItem(BreadcrumbBar paramBreadcrumbBar, String paramString, SVGPath paramSVGPath, Node paramNode)
  {
    this();
    ((SimpleObjectProperty)this.breadcrumbBar).set(paramBreadcrumbBar);
    this.content.set(paramNode);
    svgIconProperty().set(paramSVGPath);
    this.text.set(paramString);
  }
  
  public final ReadOnlyObjectProperty<BreadcrumbBar> breadcrumbBarProperty()
  {
    return this.breadcrumbBar;
  }
  
  public final BreadcrumbBar getBreadcrumbBar()
  {
    return (BreadcrumbBar)breadcrumbBarProperty().get();
  }
  
  public final ObjectProperty<Node> contentProperty()
  {
    return this.content;
  }
  
  public final Node getContent()
  {
    return (Node)contentProperty().get();
  }
  
  public final void setContent(Node paramNode)
  {
    contentProperty().set(paramNode);
  }
  
  public final StringProperty textProperty()
  {
    return this.text;
  }
  
  public final String getText()
  {
    return (String)textProperty().get();
  }
  
  public final void setText(String paramString)
  {
    textProperty().set(paramString);
  }
  
  public final ObjectProperty<Image> iconProperty()
  {
    return this.icon;
  }
  
  public final Image getIcon()
  {
    return (Image)iconProperty().get();
  }
  
  public final void setIcon(Image paramImage)
  {
    iconProperty().set(paramImage);
    svgIconProperty().set(null);
  }
  
  public final ObjectProperty<SVGPath> svgIconProperty()
  {
    return this.svgIcon;
  }
  
  public final SVGPath getSvgIcon()
  {
    return (SVGPath)svgIconProperty().get();
  }
  
  public final void setSvgIcom(SVGPath paramSVGPath)
  {
    svgIconProperty().set(paramSVGPath);
    iconProperty().set(null);
  }
  
  public final ReadOnlyBooleanProperty firstProperty()
  {
    return this.first;
  }
  
  public final boolean isFirst()
  {
    return firstProperty().get();
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + getClass().getSimpleName() + ".css").toExternalForm();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/BreadcrumbItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */