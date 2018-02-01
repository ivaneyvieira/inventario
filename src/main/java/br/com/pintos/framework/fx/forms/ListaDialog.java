package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.fx.view.ApplicationFX;
import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.ShowDialog;
import java.io.File;
import javafx.collections.ObservableList;
import javafx.scene.control.ToolBar;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ListaDialog<B>
  extends DialogFX
{
  private final ProviderModel<B> model;
  protected TableFX<B> table;
  
  public ListaDialog(String paramString, ProviderModel<B> paramProviderModel)
  {
    super(paramString);
    this.model = paramProviderModel;
  }
  
  private Command actionAtualizar()
  {
    new Command(this)
    {
      protected void run()
      {
        ListaDialog.this.table.atualiza();
      }
    };
  }
  
  private Command actionExcel()
  {
    new Command(this)
    {
      protected void run()
      {
        String str1 = ".xlsx";
        FileChooser localFileChooser = new FileChooser();
        ExtensionFilter localExtensionFilter = new ExtensionFilter("Excel (*.xlsx)", new String[] { "*" + str1 });
        String str2 = System.getProperty("user.home");
        localFileChooser.getExtensionFilters().add(localExtensionFilter);
        localFileChooser.setInitialDirectory(new File(str2));
        localFileChooser.setTitle("Salvar excel");
        File localFile = localFileChooser.showSaveDialog(ListaDialog.this.application.getStage());
        if (localFile != null)
        {
          String str3 = localFile.getAbsolutePath();
          if (!str3.endsWith(str1)) {
            str3 = str3 + str1;
          }
          ListaDialog.this.table.geraExcel(str3);
        }
      }
    };
  }
  
  public void close()
  {
    super.close();
  }
  
  public void initControls()
  {
    super.initControls();
    this.table = new TableFX(this.model);
    setCenter(this.table);
  }
  
  protected ToolBar toolBar()
  {
    ToolBar localToolBar = new ToolBar();
    ObservableList localObservableList = localToolBar.getItems();
    localObservableList.add(createButton("Atualizar", "refresh.png", actionAtualizar()));
    localObservableList.add(createButton("Excel", "excel.png", actionExcel()));
    localObservableList.add(createButtonFechar());
    return localToolBar;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/ListaDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */