package br.com.pintos.framework.fx.controls;

import br.com.pintos.framework.fx.forms.TablePane;
import br.com.pintos.framework.util.Beans;
import br.com.pintos.framework.util.Util;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControlLockup<T>
  extends ControlFX<T>
{
  private final ProviderModel<T> model;
  private TextField textField;
  private Label labelField;
  private final SimpleObjectProperty<T> value = new SimpleObjectProperty();
  private ConvertCombo<T> converter;
  
  public ControlLockup(String paramString, ProviderModel<T> paramProviderModel)
  {
    super(paramString);
    this.model = paramProviderModel;
    if (Util.bean.isBean(paramProviderModel.getClasse())) {
      this.converter = new ConvertCombo(paramProviderModel);
    } else {
      this.converter = null;
    }
  }
  
  protected Node createControl()
  {
    TablePane localTablePane = new TablePane();
    localTablePane.addColumns(new String[] { "7", "25" });
    this.textField = new TextField();
    this.labelField = new Label();
    this.labelField.setStyle("-fx-border-style: solid;-fx-border-color: black;");
    localTablePane.layout(this.textField);
    localTablePane.layout(this.labelField);
    this.labelField.setPrefWidth(275.0D);
    this.textField.textProperty().addListener(onChangeText());
    return localTablePane;
  }
  
  public T getValue()
  {
    return (T)this.value.getValue();
  }
  
  private ChangeListener<? super String> onChangeText()
  {
    new ChangeListener()
    {
      public void changed(ObservableValue<? extends String> paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2)
      {
        Object localObject = ControlLockup.this.model.getLockupBean(paramAnonymousString2);
        ControlLockup.this.value.set(localObject);
        String str = "";
        if (localObject == null) {
          str = "";
        } else if (ControlLockup.this.converter == null) {
          str = localObject.toString();
        } else {
          str = ControlLockup.this.converter.toString(localObject);
        }
        ControlLockup.this.labelField.setText(str);
      }
    };
  }
  
  public void setValue(T paramT)
  {
    this.value.set(paramT);
    String str = this.model.getLockupField(paramT);
    this.textField.setText(str);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ControlLockup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */