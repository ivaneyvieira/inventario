package br.com.pintos.coletor.fx.teste;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

class MyDialog
  extends Stage
{
  public MyDialog(Stage paramStage)
  {
    initOwner(paramStage);
    setTitle("title");
    initModality(Modality.APPLICATION_MODAL);
    Group localGroup = new Group();
    Scene localScene = new Scene(localGroup, 250.0D, 150.0D, Color.WHITE);
    setScene(localScene);
    GridPane localGridPane = new GridPane();
    localGridPane.setPadding(new Insets(5.0D));
    localGridPane.setHgap(5.0D);
    localGridPane.setVgap(5.0D);
    Label localLabel1 = new Label("User Name: ");
    localGridPane.add(localLabel1, 0, 1);
    Label localLabel2 = new Label("Password: ");
    localGridPane.add(localLabel2, 0, 2);
    TextField localTextField = new TextField("Admin");
    localGridPane.add(localTextField, 1, 1);
    PasswordField localPasswordField = new PasswordField();
    localPasswordField.setText("password");
    localGridPane.add(localPasswordField, 1, 2);
    Button localButton = new Button("Change");
    localButton.setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        MyDialog.this.close();
      }
    });
    localGridPane.add(localButton, 1, 3);
    GridPane.setHalignment(localButton, HPos.RIGHT);
    localGroup.getChildren().add(localGridPane);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/teste/MyDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */