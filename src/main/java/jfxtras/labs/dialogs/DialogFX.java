package jfxtras.labs.dialogs;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public final class DialogFX
  extends Stage
{
  private Type type;
  private Stage stage;
  private Scene scene;
  private BorderPane pane = new BorderPane();
  private ImageView icon = new ImageView();
  private Label message = new Label();
  private HBox buttonBox = new HBox(10.0D);
  private List<String> buttonLabels;
  private int buttonCancel = -1;
  private int buttonCount = 0;
  private int buttonSelected = -1;
  private List<String> stylesheets = new ArrayList();
  
  public DialogFX()
  {
    initDialog(Type.INFO);
  }
  
  public DialogFX(Type paramType)
  {
    initDialog(paramType);
  }
  
  public void addButtons(List<String> paramList)
  {
    addButtons(paramList, -1, -1);
  }
  
  public void addButtons(List<String> paramList, int paramInt1, int paramInt2)
  {
    this.buttonLabels = paramList;
    for (int i = 0; i < paramList.size(); i++)
    {
      final Button localButton = new Button((String)paramList.get(i));
      localButton.setDefaultButton(i == paramInt1);
      localButton.setCancelButton(i == paramInt2);
      if (i == paramInt1) {
        Platform.runLater(new Runnable()
        {
          public void run()
          {
            localButton.requestFocus();
          }
        });
      }
      this.buttonCancel = paramInt2;
      localButton.setOnAction(new EventHandler()
      {
        public void handle(ActionEvent paramAnonymousActionEvent)
        {
          DialogFX.this.buttonSelected = DialogFX.this.buttonLabels.indexOf(((Button)paramAnonymousActionEvent.getSource()).getText());
          DialogFX.this.stage.close();
        }
      });
      this.buttonBox.getChildren().add(localButton);
    }
    this.buttonBox.setAlignment(Pos.CENTER);
    BorderPane.setAlignment(this.buttonBox, Pos.CENTER);
    BorderPane.setMargin(this.buttonBox, new Insets(5.0D, 5.0D, 5.0D, 5.0D));
    this.pane.setBottom(this.buttonBox);
    this.buttonCount = paramList.size();
  }
  
  private void addOKButton()
  {
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add("OK");
    addButtons(localArrayList, 0, 0);
  }
  
  private void addYesNoButtons()
  {
    ArrayList localArrayList = new ArrayList(2);
    localArrayList.add("Yes");
    localArrayList.add("No");
    addButtons(localArrayList);
  }
  
  public void addStylesheet(String paramString)
  {
    try
    {
      String str = getClass().getResource(paramString).toExternalForm();
      this.stylesheets.add(str);
    }
    catch (Exception localException)
    {
      System.err.println("Unable to find specified stylesheet: " + paramString);
      System.err.println("Error message: " + localException.getMessage());
    }
  }
  
  private void initDialog(Type paramType)
  {
    this.stage = new Stage();
    setType(paramType);
    this.stage.initModality(Modality.APPLICATION_MODAL);
    this.stage.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2.0D);
  }
  
  private void loadIconFromResource(String paramString)
  {
    Image localImage = new Image(getClass().getResourceAsStream(paramString));
    this.icon.setPreserveRatio(true);
    this.icon.setFitHeight(48.0D);
    this.icon.setImage(localImage);
  }
  
  public void setMessage(String paramString)
  {
    this.message.setText(paramString);
    this.message.setWrapText(true);
  }
  
  public void setModal(boolean paramBoolean)
  {
    this.stage.initModality(paramBoolean ? Modality.APPLICATION_MODAL : Modality.NONE);
  }
  
  public void setTitleText(String paramString)
  {
    this.stage.setTitle(paramString);
  }
  
  public void setType(Type paramType)
  {
    this.type = paramType;
  }
  
  private void populateStage()
  {
    String str;
    switch (this.type)
    {
    case ACCEPT: 
      str = "Dialog-accept.jpg";
      addOKButton();
      break;
    case ERROR: 
      str = "Dialog-error.jpg";
      addOKButton();
      break;
    case INFO: 
      str = "Dialog-info.jpg";
      addOKButton();
      break;
    case QUESTION: 
      str = "Dialog-question.jpg";
      break;
    default: 
      str = "Dialog-info.jpg";
    }
    try
    {
      loadIconFromResource(str);
    }
    catch (Exception localException1)
    {
      System.err.println("Exception trying to load icon file: " + localException1.getMessage());
    }
    BorderPane.setAlignment(this.icon, Pos.CENTER);
    BorderPane.setMargin(this.icon, new Insets(5.0D, 5.0D, 5.0D, 5.0D));
    this.pane.setLeft(this.icon);
    BorderPane.setAlignment(this.message, Pos.CENTER);
    BorderPane.setMargin(this.message, new Insets(5.0D, 5.0D, 5.0D, 5.0D));
    this.pane.setCenter(this.message);
    this.scene = new Scene(this.pane);
    for (int i = 0; i < this.stylesheets.size(); i++) {
      try
      {
        this.scene.getStylesheets().add(this.stylesheets.get(i));
      }
      catch (Exception localException2)
      {
        System.err.println("Unable to load specified stylesheet: " + (String)this.stylesheets.get(i));
        System.err.println(localException2.getMessage());
      }
    }
    this.stage.setScene(this.scene);
  }
  
  public int showDialog()
  {
    populateStage();
    if ((this.type == Type.QUESTION) && (this.buttonCount == 0)) {
      addYesNoButtons();
    }
    this.stage.setResizable(false);
    this.stage.sizeToScene();
    this.stage.centerOnScreen();
    this.stage.showAndWait();
    return this.buttonSelected == -1 ? this.buttonCancel : this.buttonSelected;
  }
  
  public static enum Type
  {
    ACCEPT,  ERROR,  INFO,  QUESTION;
    
    private Type() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/dialogs/DialogFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */