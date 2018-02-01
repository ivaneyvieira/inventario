package jfxtras.labs.internal.scene.control.behavior;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import jfxtras.labs.scene.control.ListSpinner;

public class ListSpinnerBehavior<T>
  extends BehaviorBase<ListSpinner<T>>
{
  private static final String EVENT_PREVIOUS = "PreviousPressed";
  private static final String EVENT_NEXT = "NextPressed";
  protected static final List<KeyBinding> KEY_BINDINGS = new ArrayList();
  
  public ListSpinnerBehavior(ListSpinner<T> paramListSpinner)
  {
    super(paramListSpinner);
    construct();
  }
  
  private void construct() {}
  
  public void parse(String paramString)
  {
    String str1 = paramString;
    String str2 = ((ListSpinner)getControl()).getPostfix();
    if ((str2.length() > 0) && (str1.endsWith(str2))) {
      str1 = str1.substring(0, str1.length() - str2.length());
    }
    String str3 = ((ListSpinner)getControl()).getPrefix();
    if ((str3.length() > 0) && (str1.startsWith(str3))) {
      str1 = str1.substring(str3.length());
    }
    Object localObject = ((ListSpinner)getControl()).getStringConverter().fromString(str1);
    int i = ((ListSpinner)getControl()).getItems().indexOf(localObject);
    if (i >= 0)
    {
      ((ListSpinner)getControl()).setValue(localObject);
      return;
    }
    Callback localCallback = ((ListSpinner)getControl()).getAddCallback();
    if (localCallback != null)
    {
      Integer localInteger = (Integer)localCallback.call(localObject);
      if (localInteger != null)
      {
        ((ListSpinner)getControl()).setIndex(localInteger);
        return;
      }
    }
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    ListSpinner localListSpinner = (ListSpinner)getControl();
    if ((!localListSpinner.isFocused()) && (localListSpinner.isFocusTraversable())) {
      localListSpinner.requestFocus();
    }
  }
  
  protected List<KeyBinding> createKeyBindings()
  {
    return KEY_BINDINGS;
  }
  
  protected void callAction(String paramString)
  {
    if ("PreviousPressed".equals(paramString)) {
      ((ListSpinner)getControl()).decrement();
    } else if ("NextPressed".equals(paramString)) {
      ((ListSpinner)getControl()).increment();
    } else {
      super.callAction(paramString);
    }
  }
  
  static
  {
    KEY_BINDINGS.add(new KeyBinding(KeyCode.MINUS, "PreviousPressed"));
    KEY_BINDINGS.add(new KeyBinding(KeyCode.PLUS, "NextPressed"));
    KEY_BINDINGS.add(new KeyBinding(KeyCode.SUBTRACT, "PreviousPressed"));
    KEY_BINDINGS.add(new KeyBinding(KeyCode.ADD, "NextPressed"));
    KEY_BINDINGS.add(new KeyBinding(KeyCode.UP, "NextPressed"));
    KEY_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "PreviousPressed"));
    KEY_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "PreviousPressed"));
    KEY_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "NextPressed"));
    KEY_BINDINGS.addAll(TRAVERSAL_BINDINGS);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/behavior/ListSpinnerBehavior.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */