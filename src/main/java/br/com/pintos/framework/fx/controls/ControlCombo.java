package br.com.pintos.framework.fx.controls;

import br.com.pintos.framework.util.Beans;
import br.com.pintos.framework.util.Util;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;

public class ControlCombo<T>
  extends ControlFX<T>
{
  private ComboBox<T> combo;
  private final ProviderModel<T> model;
  
  public ControlCombo(String paramString, ProviderModel<T> paramProviderModel)
  {
    super(paramString);
    this.model = paramProviderModel;
    if (Util.bean.isBean(paramProviderModel.getClasse())) {
      this.combo.setConverter(new ConvertCombo(paramProviderModel));
    }
    addItens();
  }
  
  private void addItens()
  {
    List localList = this.model.getLista(true);
    this.combo.getItems().addAll(localList);
    if (localList.size() > 0) {
      this.combo.valueProperty().set(localList.get(0));
    }
  }
  
  protected Control createControl()
  {
    this.combo = new ComboBox();
    return this.combo;
  }
  
  public T getValue()
  {
    return (T)this.combo.getValue();
  }
  
  public void setValue(T paramT)
  {
    this.combo.setValue(paramT);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ControlCombo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */