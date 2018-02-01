package br.com.pintos.coletor.fx.teste;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WindowEvent06
  extends Application
{
  public static void main(String[] paramArrayOfString)
  {
    launch(paramArrayOfString);
  }
  
  public void start(final Stage paramStage)
  {
    Group localGroup = new Group();
    Scene localScene = new Scene(localGroup);
    paramStage.setScene(localScene);
    paramStage.setTitle("WindowEvent06");
    paramStage.setOnCloseRequest(new EventHandler()
    {
      public void handle(WindowEvent paramAnonymousWindowEvent)
      {
        paramAnonymousWindowEvent.consume();
        new ModalDialog(paramStage, "Question");
      }
    });
    paramStage.show();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/teste/WindowEvent06.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */