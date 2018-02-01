package br.com.pintos.framework.fx.view;

import br.com.pintos.framework.fx.forms.DialogFX;
import br.com.pintos.framework.fx.viewmodel.VApplication;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ApplicationFX
{
  private final VApplication application;
  private MenuBarApplication menubar;
  private ToolBarApplication toolBar;
  private TabPaneApplication tabPane;
  private Stage stage;
  
  public ApplicationFX(VApplication paramVApplication)
  {
    this.application = paramVApplication;
  }
  
  public void closeAll()
  {
    ObservableList localObservableList = this.tabPane.getTabs();
    while (localObservableList.size() > 0) {
      closeDialog((Tab)localObservableList.get(0), null);
    }
  }
  
  public void closeDialog(Tab paramTab1, Tab paramTab2)
  {
    this.tabPane.getTabs().removeAll(new Tab[] { paramTab1 });
    if (paramTab2 != null) {
      this.tabPane.getSelectionModel().select(paramTab2);
    }
  }
  
  public void execute(ExecMenu paramExecMenu)
  {
    paramExecMenu.run(this);
  }
  
  public VApplication getApplication()
  {
    return this.application;
  }
  
  public Stage getStage()
  {
    return this.stage;
  }
  
  protected StackPane makeRoot()
  {
    this.menubar = new MenuBarApplication(this);
    this.toolBar = new ToolBarApplication(this);
    this.tabPane = new TabPaneApplication();
    StackPane localStackPane = new StackPane();
    BorderPane localBorderPane = new BorderPane();
    localStackPane.getChildren().add(localBorderPane);
    VBox localVBox = new VBox();
    localBorderPane.setTop(localVBox);
    localVBox.getChildren().addAll(new Node[] { this.menubar, this.toolBar });
    localBorderPane.setCenter(this.tabPane);
    return localStackPane;
  }
  
  private void maximizer(Stage paramStage)
  {
    Screen localScreen = Screen.getPrimary();
    Rectangle2D localRectangle2D = localScreen.getVisualBounds();
    paramStage.setX(localRectangle2D.getMinX());
    paramStage.setY(localRectangle2D.getMinY());
    paramStage.setWidth(localRectangle2D.getMaxX());
    paramStage.setHeight(localRectangle2D.getMaxY());
  }
  
  public void openDialog(DialogFX paramDialogFX)
  {
    Tab localTab1 = (Tab)this.tabPane.getSelectionModel().getSelectedItem();
    paramDialogFX.setOldTab(localTab1);
    Tab localTab2 = this.tabPane.addNode(paramDialogFX.getTitle(), paramDialogFX);
    this.tabPane.getSelectionModel().select(localTab2);
    paramDialogFX.setTab(localTab2);
  }
  
  public void start(Stage paramStage)
  {
    this.stage = paramStage;
    paramStage.getIcons().add(Icon.image(this.application.getIcon(), Integer.valueOf(32)));
    paramStage.setTitle(this.application.getTitulo());
    maximizer(paramStage);
    StackPane localStackPane = makeRoot();
    Scene localScene = new Scene(localStackPane);
    paramStage.setScene(localScene);
    paramStage.show();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/view/ApplicationFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */