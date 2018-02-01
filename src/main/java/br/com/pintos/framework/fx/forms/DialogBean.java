package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.ShowDialog;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class DialogBean
  extends DialogFX
{
  private final Node node;
  private Button btnOk;
  private Button btnCancel;
  private Command actionConfirma;
  private Command actionCancela;
  
  public DialogBean(String paramString, Node paramNode)
  {
    super(paramString);
    this.node = paramNode;
  }
  
  private Command cancela()
  {
    new Command(this)
    {
      protected void run()
      {
        DialogBean.this.actionCancela.execute();
      }
    };
  }
  
  private Command confirma()
  {
    new Command(this)
    {
      protected void run()
      {
        DialogBean.this.actionConfirma.execute();
      }
    };
  }
  
  public void initControls()
  {
    setCenter(this.node);
    setActionCancela(new Command(this)
    {
      protected void run()
      {
        DialogBean.this.close();
      }
    });
    super.initControls();
  }
  
  public void setActionCancela(Command paramCommand)
  {
    this.actionCancela = paramCommand;
  }
  
  public void setActionConfirma(Command paramCommand)
  {
    this.actionConfirma = paramCommand;
  }
  
  protected ToolBar toolBar()
  {
    ToolBar localToolBar = new ToolBar();
    ObservableList localObservableList = localToolBar.getItems();
    this.btnOk = createButton("Confirmar", "confirma.png", confirma());
    this.btnOk.setDefaultButton(true);
    localObservableList.add(this.btnOk);
    this.btnCancel = createButton("Cancelar", "cancel.png", cancela());
    this.btnCancel.setCancelButton(true);
    localObservableList.add(this.btnCancel);
    return localToolBar;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/DialogBean.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */