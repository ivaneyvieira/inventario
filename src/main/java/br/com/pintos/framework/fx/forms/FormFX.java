package br.com.pintos.framework.fx.forms;

public abstract class FormFX<B>
  extends TablePane
{
  private final DialogFX dialog;
  protected final B bean;
  
  public FormFX(DialogFX paramDialogFX, B paramB)
  {
    this.dialog = paramDialogFX;
    this.bean = paramB;
  }
  
  public abstract B getBean();
  
  public DialogFX getDialog()
  {
    return this.dialog;
  }
  
  public abstract void initControls();
  
  public abstract void setBean(B paramB);
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/FormFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */