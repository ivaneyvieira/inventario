package br.com.pintos.coletor.fx.teste;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalDialog
  extends Stage
{
  Stage owner;
  Stage stage = this;
  BorderPane root = new BorderPane();
  
  public ModalDialog(Stage paramStage, String paramString)
  {
    this.owner = paramStage;
    initModality(Modality.APPLICATION_MODAL);
    initOwner(paramStage);
    initStyle(StageStyle.UTILITY);
    setTitle(paramString);
    setContents();
  }
  
  public void setContents()
  {
    Scene localScene = new Scene(this.root, 150.0D, 150.0D);
    setScene(localScene);
    Group localGroup = new Group();
    localGroup.getChildren().add(new Label("Really Exit ?"));
    this.root.setCenter(localGroup);
    Button localButton1 = new Button("Yes");
    localButton1.setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        ModalDialog.this.stage.close();
        ModalDialog.this.owner.close();
      }
    });
    Button localButton2 = new Button("No");
    localButton2.setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        ModalDialog.this.stage.close();
      }
    });
    HBox localHBox = new HBox();
    localHBox.setSpacing(10.0D);
    localHBox.getChildren().addAll(new Node[] { localButton1, localButton2 });
    this.root.setBottom(localHBox);
    this.stage.show();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/teste/ModalDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */