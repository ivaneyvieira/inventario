package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.dados.exception.ErroFatal;
import br.com.pintos.framework.dados.exception.ErroInterno;
import br.com.pintos.framework.fx.dialog.MonologFX;
import br.com.pintos.framework.fx.view.ApplicationFX;
import br.com.pintos.framework.fx.view.ExecMenu;
import br.com.pintos.framework.fx.view.Icon;
import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.ShowDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

public abstract class DialogFX
  extends BorderPane
  implements ExecMenu, ShowDialog
{
  private final ToolBar toolBar;
  private final String title;
  protected ApplicationFX application;
  private Tab oldTab;
  private Tab tab;
  
  public DialogFX(String paramString)
  {
    this.title = paramString;
    this.toolBar = toolBar();
  }
  
  protected Command actionFechar()
  {
    new Command(this)
    {
      protected void run()
      {
        DialogFX.this.close();
      }
    };
  }
  
  public void close()
  {
    this.application.closeDialog(this.tab, this.oldTab);
  }
  
  protected Button createButton(String paramString1, String paramString2, final Command paramCommand)
  {
    Button localButton = new Button("", Icon.icon(paramString2, Integer.valueOf(32)));
    localButton.setTooltip(new Tooltip(paramString1));
    if (paramCommand != null) {
      localButton.setOnAction(new EventHandler()
      {
        public void handle(ActionEvent paramAnonymousActionEvent)
        {
          paramCommand.execute();
        }
      });
    }
    return localButton;
  }
  
  protected Button createButtonFechar()
  {
    return createButton("Fechar", "exit.png", actionFechar());
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public ToolBar getToolBar()
  {
    return this.toolBar;
  }
  
  public void initControls()
  {
    setBottom(this.toolBar);
  }
  
  public final void run(ApplicationFX paramApplicationFX)
  {
    initControls();
    this.application = paramApplicationFX;
    paramApplicationFX.openDialog(this);
  }
  
  public void setOldTab(Tab paramTab)
  {
    this.oldTab = paramTab;
  }
  
  public void setTab(Tab paramTab)
  {
    this.tab = paramTab;
  }
  
  public void showErro(BOException paramBOException)
  {
    showErro(paramBOException.getMessage());
  }
  
  private void showErro(String paramString)
  {
    MonologFX.showError(this.application.getStage(), "Erro", paramString);
  }
  
  public void showErroFatal(ErroFatal paramErroFatal)
  {
    showErro("Erro Fatal no sistema\nFavor Entrar em contato com o Administrador do sistema");
    Platform.exit();
  }
  
  public void showErroInterno(ErroInterno paramErroInterno)
  {
    showErro("Erro Interno no sistema\nFavor Entrar em contato com o Administrador do sistema");
  }
  
  protected abstract ToolBar toolBar();
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/DialogFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */