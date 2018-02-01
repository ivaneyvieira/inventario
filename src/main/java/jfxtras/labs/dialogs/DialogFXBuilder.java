package jfxtras.labs.dialogs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;

public class DialogFXBuilder<B extends DialogFXBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<DialogFX>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static DialogFXBuilder create()
  {
    return new DialogFXBuilder();
  }
  
  public final DialogFXBuilder buttons(List<String> paramList)
  {
    this.properties.put("buttonsLabels", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final DialogFXBuilder buttons(List<String> paramList, int paramInt1, int paramInt2)
  {
    this.properties.put("buttonsLabels1", new SimpleObjectProperty(paramList));
    this.properties.put("buttonsDefaultButton", new SimpleIntegerProperty(paramInt1));
    this.properties.put("buttonsCancelButton", new SimpleIntegerProperty(paramInt2));
    return this;
  }
  
  public final DialogFXBuilder type(DialogFX.Type paramType)
  {
    this.properties.put("type", new SimpleObjectProperty(paramType));
    return this;
  }
  
  public final DialogFXBuilder message(String paramString)
  {
    this.properties.put("message", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final DialogFXBuilder modal(boolean paramBoolean)
  {
    this.properties.put("modal", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final DialogFXBuilder titleText(String paramString)
  {
    this.properties.put("titleText", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final DialogFXBuilder stylesheet(String paramString)
  {
    this.properties.put("stylesheet", new SimpleStringProperty(paramString));
    return this;
  }
  
  public DialogFX build()
  {
    DialogFX localDialogFX = new DialogFX();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("buttonsLabels".equals(str)) {
        localDialogFX.addButtons((List)((ObjectProperty)this.properties.get(str)).get());
      } else if ("buttonsLabels1".equals(str)) {
        localDialogFX.addButtons((List)((ObjectProperty)this.properties.get(str)).get(), ((IntegerProperty)this.properties.get("buttonsDefaultButton")).get(), ((IntegerProperty)this.properties.get("buttonsCancelButton")).get());
      } else if ("type".equals(str)) {
        localDialogFX.setType((DialogFX.Type)((ObjectProperty)this.properties.get(str)).get());
      } else if ("message".equals(str)) {
        localDialogFX.setMessage((String)((StringProperty)this.properties.get(str)).get());
      } else if ("modal".equals(str)) {
        localDialogFX.setModal(((BooleanProperty)this.properties.get(str)).get());
      } else if ("titleText".equals(str)) {
        localDialogFX.setTitleText((String)((StringProperty)this.properties.get(str)).get());
      } else if ("stylesheet".equals(str)) {
        localDialogFX.addStylesheet((String)((StringProperty)this.properties.get(str)).get());
      }
    }
    return localDialogFX;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/dialogs/DialogFXBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */