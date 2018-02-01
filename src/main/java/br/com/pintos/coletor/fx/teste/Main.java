package br.com.pintos.coletor.fx.teste;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main
  extends Application
{
  public static void main(String[] paramArrayOfString)
  {
    Application.launch(paramArrayOfString);
  }
  
  public void start(Stage paramStage)
  {
    paramStage.setTitle("Dialog");
    Group localGroup = new Group();
    Scene localScene = new Scene(localGroup, 400.0D, 300.0D, Color.WHITE);
    paramStage.setScene(localScene);
    paramStage.show();
    MyDialog localMyDialog = new MyDialog(paramStage);
    localMyDialog.sizeToScene();
    localMyDialog.show();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/teste/Main.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */