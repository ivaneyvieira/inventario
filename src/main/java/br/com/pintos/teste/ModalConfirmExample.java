package br.com.pintos.teste;

import br.com.pintos.framework.fx.dialog.DialogModal;
import java.io.PrintStream;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ModalConfirmExample
  extends Application
{
  public static void main(String[] paramArrayOfString)
  {
    launch(paramArrayOfString);
  }
  
  public void start(final Stage paramStage)
  {
    paramStage.setTitle("Modal Confirm Example");
    final WebView localWebView = new WebView();
    localWebView.getEngine().load("http://docs.oracle.com/javafx/");
    paramStage.setScene(new Scene(localWebView));
    paramStage.show();
    final DialogModal localDialogModal = new DialogModal(paramStage);
    localDialogModal.setScene(new Scene(((HBoxBuilder)((HBoxBuilder)HBoxBuilder.create().styleClass(new String[] { "modal-dialog" })).children(new Node[] { ((LabelBuilder)LabelBuilder.create().text("Will you like this page?")).build(), ((ButtonBuilder)((ButtonBuilder)ButtonBuilder.create().text("Yes")).defaultButton(true).onAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        System.out.println("Liked: " + localWebView.getEngine().getTitle());
        paramStage.getScene().getRoot().setEffect(null);
        localDialogModal.close();
      }
    })).build(), ((ButtonBuilder)((ButtonBuilder)ButtonBuilder.create().text("No")).cancelButton(true).onAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        System.out.println("Disliked: " + localWebView.getEngine().getTitle());
        paramStage.getScene().getRoot().setEffect(null);
        localDialogModal.close();
      }
    })).build() })).build(), Color.TRANSPARENT));
    localWebView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends State> paramAnonymousObservableValue, State paramAnonymousState1, State paramAnonymousState2)
      {
        if (paramAnonymousState2.equals(State.SUCCEEDED))
        {
          paramStage.getScene().getRoot().setEffect(new BoxBlur());
          localDialogModal.show();
        }
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/teste/ModalConfirmExample.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */