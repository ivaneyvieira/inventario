package br.com.pintos.coletor.fx.teste;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFX_MyDialog
  extends Application
{
  public static void main(String[] paramArrayOfString)
  {
    launch(paramArrayOfString);
  }
  
  public void start(Stage paramStage)
  {
    paramStage.setTitle("http://java-buddy.blogspot.com/");
    Button localButton = new Button();
    localButton.setText("Open Dialog");
    localButton.setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        final Stage localStage = new Stage();
        localStage.initModality(Modality.APPLICATION_MODAL);
        Button localButton = new Button("CLOSE");
        localButton.setOnAction(new EventHandler()
        {
          public void handle(ActionEvent paramAnonymous2ActionEvent)
          {
            localStage.close();
          }
        });
        Scene localScene = new Scene(((VBoxBuilder)((VBoxBuilder)VBoxBuilder.create().children(new Node[] { new Text("Hello! it's My Dialog."), localButton })).alignment(Pos.CENTER).padding(new Insets(10.0D))).build());
        localStage.setScene(localScene);
        localStage.show();
      }
    });
    StackPane localStackPane = new StackPane();
    localStackPane.getChildren().add(localButton);
    paramStage.setScene(new Scene(localStackPane, 300.0D, 250.0D));
    paramStage.show();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/teste/JavaFX_MyDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */