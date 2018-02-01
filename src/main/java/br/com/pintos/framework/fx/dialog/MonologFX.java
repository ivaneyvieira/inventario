package br.com.pintos.framework.fx.dialog;

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
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class MonologFX
  extends Stage
{
  private Type type;
  private final BorderPane pane = new BorderPane();
  private final ImageView icon = new ImageView();
  private final Label message = new Label();
  private final HBox buttonBox = new HBox(10.0D);
  private final List<MonologFXButton> buttons = new ArrayList();
  private int buttonCancel = -1;
  private int buttonSelected = -1;
  private ButtonAlignment buttonAlignment = ButtonAlignment.CENTER;
  private final List<String> stylesheets = new ArrayList();
  
  public static void showAviso(Window paramWindow, String paramString)
  {
    MonologFX localMonologFX = new MonologFX(paramWindow);
    localMonologFX.setModal(true);
    localMonologFX.setType(Type.INFO);
    localMonologFX.setMessage(paramString);
    localMonologFX.setTitleText("Aviso");
    localMonologFX.setTitle("Aviso");
    localMonologFX.setButtonAlignment(ButtonAlignment.CENTER);
    localMonologFX.showDialog();
  }
  
  public static void showError(Window paramWindow, String paramString1, String paramString2)
  {
    MonologFX localMonologFX = new MonologFX(paramWindow);
    localMonologFX.setModal(true);
    localMonologFX.setType(Type.ERROR);
    localMonologFX.setMessage(paramString2);
    localMonologFX.setTitleText(paramString1);
    localMonologFX.setTitle(paramString1);
    localMonologFX.setButtonAlignment(ButtonAlignment.CENTER);
    localMonologFX.showDialog();
  }
  
  public MonologFX(Window paramWindow)
  {
    this(paramWindow, Type.INFO);
  }
  
  public MonologFX(Window paramWindow, Type paramType)
  {
    super(StageStyle.UTILITY);
    initModality(Modality.WINDOW_MODAL);
    initOwner(paramWindow);
    setResizable(false);
    initDialog(paramType);
  }
  
  public void addButton(MonologFXButton paramMonologFXButton)
  {
    this.buttons.add(paramMonologFXButton);
    final Button localButton = new Button();
    localButton.setMnemonicParsing(true);
    localButton.setText(paramMonologFXButton.getLabel());
    if (paramMonologFXButton.getIcon() != null) {
      localButton.setGraphic(paramMonologFXButton.getIcon());
    }
    localButton.setDefaultButton(paramMonologFXButton.isDefaultButton());
    if (paramMonologFXButton.isCancelButton())
    {
      localButton.setCancelButton(true);
      this.buttonCancel = (this.buttons.size() - 1);
    }
    if (localButton.isDefaultButton()) {
      Platform.runLater(new Runnable()
      {
        public void run()
        {
          localButton.requestFocus();
        }
      });
    }
    localButton.setOnAction(new EventHandler()
    {
      public void handle(ActionEvent paramAnonymousActionEvent)
      {
        for (int i = 0; i < MonologFX.this.buttons.size(); i++) {
          if (((MonologFXButton)MonologFX.this.buttons.get(i)).getLabel().equalsIgnoreCase(((Button)paramAnonymousActionEvent.getSource()).getText()))
          {
            MonologFX.this.buttonSelected = i;
            break;
          }
        }
        MonologFX.this.close();
      }
    });
    this.buttonBox.getChildren().add(localButton);
  }
  
  private void addOKButton()
  {
    MonologFXButton localMonologFXButton = new MonologFXButton();
    localMonologFXButton.setType(MonologFXButton.Type.OK);
    localMonologFXButton.setLabel("_OK");
    localMonologFXButton.setCancelButton(true);
    localMonologFXButton.setDefaultButton(true);
    addButton(localMonologFXButton);
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
  
  private void addYesNoButtons()
  {
    MonologFXButton localMonologFXButton1 = new MonologFXButton();
    localMonologFXButton1.setType(MonologFXButton.Type.YES);
    localMonologFXButton1.setLabel("_Yes");
    localMonologFXButton1.setCancelButton(false);
    localMonologFXButton1.setDefaultButton(false);
    addButton(localMonologFXButton1);
    MonologFXButton localMonologFXButton2 = new MonologFXButton();
    localMonologFXButton2.setType(MonologFXButton.Type.NO);
    localMonologFXButton2.setLabel("_No");
    localMonologFXButton2.setCancelButton(false);
    localMonologFXButton2.setDefaultButton(false);
    addButton(localMonologFXButton2);
  }
  
  private void initDialog(Type paramType)
  {
    setType(paramType);
    initModality(Modality.APPLICATION_MODAL);
    setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2.0D);
  }
  
  private void loadIconFromResource(String paramString)
  {
    Image localImage = new Image(getClass().getResourceAsStream(paramString));
    this.icon.setPreserveRatio(true);
    this.icon.setFitHeight(48.0D);
    this.icon.setImage(localImage);
  }
  
  private void populateStage()
  {
    String str;
    switch (this.type)
    {
    case ACCEPT: 
      str = "Dialog-accept.jpg";
      if (this.buttons.size() == 0) {
        addOKButton();
      }
      break;
    case ERROR: 
      str = "Dialog-error.jpg";
      if (this.buttons.size() == 0) {
        addOKButton();
      }
      break;
    case INFO: 
      str = "Dialog-info.jpg";
      if (this.buttons.size() == 0) {
        addOKButton();
      }
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
    BorderPane.setAlignment(this.icon, Pos.CENTER_LEFT);
    BorderPane.setMargin(this.icon, new Insets(5.0D));
    this.pane.setLeft(this.icon);
    BorderPane.setAlignment(this.message, Pos.CENTER);
    BorderPane.setMargin(this.message, new Insets(5.0D));
    this.pane.setCenter(this.message);
    switch (this.buttonAlignment)
    {
    case LEFT: 
      this.buttonBox.setAlignment(Pos.CENTER_LEFT);
      break;
    case CENTER: 
      this.buttonBox.setAlignment(Pos.CENTER);
      break;
    case RIGHT: 
      this.buttonBox.setAlignment(Pos.CENTER_RIGHT);
    }
    BorderPane.setMargin(this.buttonBox, new Insets(5.0D));
    this.pane.setBottom(this.buttonBox);
    Scene localScene = new Scene(this.pane);
    for (int i = 0; i < this.stylesheets.size(); i++) {
      try
      {
        localScene.getStylesheets().add(this.stylesheets.get(i));
      }
      catch (Exception localException2)
      {
        System.err.println("Unable to load specified stylesheet: " + (String)this.stylesheets.get(i));
        System.err.println(localException2.getMessage());
      }
    }
    setScene(localScene);
  }
  
  public void setButtonAlignment(ButtonAlignment paramButtonAlignment)
  {
    this.buttonAlignment = paramButtonAlignment;
  }
  
  public void setMessage(String paramString)
  {
    this.message.setText(paramString);
    this.message.setWrapText(true);
  }
  
  public void setModal(boolean paramBoolean)
  {
    initModality(paramBoolean ? Modality.WINDOW_MODAL : Modality.NONE);
  }
  
  public void setTitleText(String paramString)
  {
    setTitle(paramString);
  }
  
  public void setType(Type paramType)
  {
    this.type = paramType;
  }
  
  public MonologFXButton.Type showDialog()
  {
    populateStage();
    if ((this.type == Type.QUESTION) && (this.buttons.size() == 0)) {
      addYesNoButtons();
    }
    setResizable(false);
    sizeToScene();
    centerOnScreen();
    showAndWait();
    if (this.buttonSelected == -1) {
      return this.buttonCancel == -1 ? MonologFXButton.Type.CANCEL : ((MonologFXButton)this.buttons.get(this.buttonCancel)).getType();
    }
    return ((MonologFXButton)this.buttons.get(this.buttonSelected)).getType();
  }
  
  public static enum Type
  {
    ACCEPT,  ERROR,  INFO,  QUESTION;
    
    private Type() {}
  }
  
  public static enum ButtonAlignment
  {
    LEFT,  RIGHT,  CENTER;
    
    private ButtonAlignment() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/dialog/MonologFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */