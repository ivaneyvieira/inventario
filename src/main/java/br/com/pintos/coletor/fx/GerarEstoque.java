package br.com.pintos.coletor.fx;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.InventarioBO;
import br.com.pintos.coletor.bos.RunTask;
import br.com.pintos.framework.fx.dialog.DialogModal;
import br.com.pintos.framework.fx.dialog.MonologFX;
import br.com.pintos.framework.fx.view.ApplicationFX;
import br.com.pintos.framework.fx.view.ExecMenu;
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;

public class GerarEstoque
  implements ExecMenu
{
  public void run(final ApplicationFX paramApplicationFX)
  {
    final ProgressIndicator localProgressIndicator = new ProgressIndicator(0.0D);
    final DialogModal localDialogModal = DialogModal.indicador(paramApplicationFX.getStage(), localProgressIndicator, "Gerando o estoque");
    Thread local1 = new Thread()
    {
      public void run()
      {
        RunTask local1 = new RunTask()
        {
          public void run(final int paramAnonymous2Int)
          {
            Platform.runLater(new Runnable()
            {
              public void run()
              {
                GerarEstoque.1.this.val$ind.setProgress(paramAnonymous2Int * 1.0D / 100.0D);
              }
            });
          }
        };
        Facade.inventario.geraEstoque(local1);
        Platform.runLater(new Runnable()
        {
          public void run()
          {
            GerarEstoque.1.this.val$dialog.close();
            MonologFX.showAviso(GerarEstoque.1.this.val$application.getStage(), "Operacao realizada com sucesso");
          }
        });
      }
    };
    local1.start();
    localDialogModal.show();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/GerarEstoque.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */