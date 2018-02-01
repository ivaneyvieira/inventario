package br.com.pintos.framework.fx.controls;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class ControlFX<T>
  extends VBox
{
  private final String label;
  private final Label labelControl;
  private final Node control;
  
  public ControlFX(String paramString)
  {
    this.label = paramString;
    this.labelControl = new Label(this.label);
    this.labelControl.setStyle("-fx-font-weight:bold");
    this.control = createControl();
    setPadding(new Insets(0.0D, 0.0D, 0.0D, 0.0D));
    initControls();
  }
  
  protected abstract Node createControl();
  
  public Node getControl()
  {
    return this.control;
  }
  
  public String getLabel()
  {
    return this.label;
  }
  
  public Label getLabelControl()
  {
    return this.labelControl;
  }
  
  public abstract T getValue();
  
  protected void initControls()
  {
    getChildren().add(this.labelControl);
    getChildren().add(this.control);
  }
  
  public abstract void setValue(T paramT);
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ControlFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */