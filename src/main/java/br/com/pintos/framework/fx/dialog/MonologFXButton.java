package br.com.pintos.framework.fx.dialog;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MonologFXButton
{
  private final List<String> defLabels = Arrays.asList(new String[] { "_OK", "_Cancel", "_Abort", "_Retry", "_Ignore", "_Yes", "_No", "Custom_1", "Custom_2", "Custom_3" });
  private final HashMap<Type, String> defaultLabels = new HashMap();
  private Type type = Type.OK;
  private String label = "";
  private Node icon;
  private boolean defaultButton = false;
  private boolean cancelButton = false;
  
  public MonologFXButton()
  {
    int i = 0;
    for (Type localType : Type.values())
    {
      this.defaultLabels.put(localType, this.defLabels.get(i));
      i++;
    }
  }
  
  public Node getIcon()
  {
    return this.icon;
  }
  
  public String getLabel()
  {
    if (!this.label.isEmpty()) {
      return this.label;
    }
    String str = (String)this.defaultLabels.get(getType());
    try
    {
      ResourceBundle localResourceBundle = ResourceBundle.getBundle("org/thehecklers/monologfx/MonologFXButton", Locale.getDefault());
      if (localResourceBundle != null) {
        str = localResourceBundle.getString(str.replaceAll("_", "").toUpperCase());
      }
    }
    catch (Exception localException)
    {
      System.err.println(localException.getMessage());
    }
    return str;
  }
  
  public Type getType()
  {
    return this.type;
  }
  
  public boolean isCancelButton()
  {
    return this.cancelButton;
  }
  
  public boolean isDefaultButton()
  {
    return this.defaultButton;
  }
  
  public void setCancelButton(boolean paramBoolean)
  {
    this.cancelButton = paramBoolean;
  }
  
  public void setDefaultButton(boolean paramBoolean)
  {
    this.defaultButton = paramBoolean;
  }
  
  public void setIcon(String paramString)
  {
    try
    {
      this.icon = new ImageView(new Image(getClass().getResourceAsStream(paramString)));
    }
    catch (Exception localException)
    {
      System.err.println("Exception trying to load button icon:" + localException.getMessage());
    }
  }
  
  public void setLabel(String paramString)
  {
    this.label = paramString;
  }
  
  public void setType(Type paramType)
  {
    this.type = paramType;
  }
  
  public static enum Type
  {
    OK,  CANCEL,  ABORT,  RETRY,  IGNORE,  YES,  NO,  CUSTOM1,  CUSTOM2,  CUSTOM3;
    
    private Type() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/dialog/MonologFXButton.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */