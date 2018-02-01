package br.com.pintos.framework.fx.dialog;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class DialogModal
  extends Stage
{
  public static DialogModal indicador(Window paramWindow, ProgressIndicator paramProgressIndicator, String paramString)
  {
    DialogModal localDialogModal = new DialogModal(paramWindow);
    localDialogModal.setTitle("Processamento");
    GridPane localGridPane = new GridPane();
    localGridPane.setHgap(4.0D);
    localGridPane.setVgap(4.0D);
    localGridPane.setPadding(new Insets(4.0D, 4.0D, 4.0D, 4.0D));
    localGridPane.add(paramProgressIndicator, 0, 0);
    localGridPane.add(new Label(paramString), 1, 0);
    localDialogModal.setScene(new Scene(localGridPane));
    return localDialogModal;
  }
  
  public DialogModal(Window paramWindow)
  {
    super(StageStyle.UTILITY);
    initModality(Modality.WINDOW_MODAL);
    initOwner(paramWindow);
    setResizable(false);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/dialog/DialogModal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */