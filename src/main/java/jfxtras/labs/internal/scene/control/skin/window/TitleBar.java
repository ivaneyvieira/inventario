package jfxtras.labs.internal.scene.control.skin.window;

import java.util.Iterator;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import jfxtras.labs.scene.control.window.Window;

class TitleBar
  extends HBox
{
  public static final String DEFAULT_STYLE_CLASS = "window-titlebar";
  private Pane leftIconPane;
  private Pane rightIconPane;
  private Text label = new Text();
  private double iconSpacing = 3.0D;
  Window control;
  private double offset = 40.0D;
  private double originalTitleWidth;
  
  public TitleBar(Window paramWindow)
  {
    this.control = paramWindow;
    setManaged(false);
    getStylesheets().setAll(paramWindow.getStylesheets());
    getStyleClass().setAll(new String[] { "window-titlebar" });
    setSpacing(8.0D);
    this.leftIconPane = new IconPane();
    this.rightIconPane = new IconPane();
    getChildren().add(this.leftIconPane);
    getChildren().add(this.label);
    getChildren().add(this.rightIconPane);
    this.control.boundsInParentProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Bounds> paramAnonymousObservableValue, Bounds paramAnonymousBounds1, Bounds paramAnonymousBounds2)
      {
        if ((TitleBar.this.control.getTitle() == null) || (TitleBar.this.getLabel().getText() == null) || (TitleBar.this.getLabel().getText().isEmpty())) {
          return;
        }
        double d = Math.max(TitleBar.this.leftIconPane.getWidth(), TitleBar.this.rightIconPane.getWidth());
        if (!TitleBar.this.control.getTitle().equals(TitleBar.this.getLabel().getText()))
        {
          if (TitleBar.this.originalTitleWidth + d * 2.0D + TitleBar.this.offset < TitleBar.this.getWidth()) {
            TitleBar.this.getLabel().setText(TitleBar.this.control.getTitle());
          }
        }
        else if ((!"...".equals(TitleBar.this.getLabel().getText())) && (TitleBar.this.originalTitleWidth + d * 2.0D + TitleBar.this.offset >= TitleBar.this.getWidth())) {
          TitleBar.this.getLabel().setText("...");
        }
      }
    });
  }
  
  public void setTitle(String paramString)
  {
    getLabel().setText(paramString);
    this.originalTitleWidth = getLabel().getBoundsInParent().getWidth();
    double d = Math.max(this.leftIconPane.getWidth(), this.rightIconPane.getWidth());
    if (this.originalTitleWidth + d * 2.0D + this.offset >= getWidth()) {
      getLabel().setText("...");
    }
  }
  
  public String getTitle()
  {
    return getLabel().getText();
  }
  
  public void addLeftIcon(Node paramNode)
  {
    this.leftIconPane.getChildren().add(paramNode);
  }
  
  public void addRightIcon(Node paramNode)
  {
    this.rightIconPane.getChildren().add(paramNode);
  }
  
  public void removeLeftIcon(Node paramNode)
  {
    this.leftIconPane.getChildren().remove(paramNode);
  }
  
  public void removeRightIcon(Node paramNode)
  {
    this.rightIconPane.getChildren().remove(paramNode);
  }
  
  protected double computeMinWidth(double paramDouble)
  {
    double d1 = super.computeMinWidth(paramDouble);
    double d2 = Math.max(this.leftIconPane.prefWidth(paramDouble), this.rightIconPane.prefWidth(paramDouble)) * 2.0D;
    d1 = Math.max(d1, d2 + getInsets().getLeft() + getInsets().getRight());
    return d1 + this.iconSpacing * 2.0D + this.offset;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    return computeMinWidth(paramDouble);
  }
  
  protected void layoutChildren()
  {
    super.layoutChildren();
    this.leftIconPane.resizeRelocate(getInsets().getLeft(), getInsets().getTop(), this.leftIconPane.prefWidth(Double.NEGATIVE_INFINITY), getHeight() - getInsets().getTop() - getInsets().getBottom());
    this.rightIconPane.resize(this.rightIconPane.prefWidth(Double.NEGATIVE_INFINITY), getHeight() - getInsets().getTop() - getInsets().getBottom());
    this.rightIconPane.relocate(getWidth() - this.rightIconPane.getWidth() - getInsets().getRight(), getInsets().getTop());
  }
  
  public Text getLabel()
  {
    return this.label;
  }
  
  private static class IconPane
    extends Pane
  {
    private double spacing = 2.0D;
    
    public IconPane()
    {
      setManaged(false);
      setPrefWidth(-1.0D);
      setMinWidth(-1.0D);
    }
    
    protected void layoutChildren()
    {
      int i = 0;
      double d1 = getHeight();
      double d2 = getHeight();
      Iterator localIterator = getManagedChildren().iterator();
      while (localIterator.hasNext())
      {
        Node localNode = (Node)localIterator.next();
        double d3 = (d1 + this.spacing) * i;
        localNode.resizeRelocate(d3, 0.0D, d1, d2);
        i++;
      }
    }
    
    protected double computeMinWidth(double paramDouble)
    {
      return getHeight() * getChildren().size() + this.spacing * (getChildren().size() - 1);
    }
    
    protected double computeMaxWidth(double paramDouble)
    {
      return computeMinWidth(paramDouble);
    }
    
    protected double computePrefWidth(double paramDouble)
    {
      return computeMinWidth(paramDouble);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/window/TitleBar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */