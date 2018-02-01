package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.fx.controls.Coluna;
import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.fx.controls.ProviderModel.ESemaforo;
import br.com.pintos.framework.util.Excel;
import br.com.pintos.framework.util.Util;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.StackPane;

public class TableFX<B>
  extends StackPane
{
  private final TableView<B> table;
  private final ObservableList<B> data;
  private final ProviderModel<B> provider;
  
  public TableFX(ProviderModel<B> paramProviderModel)
  {
    this.provider = paramProviderModel;
    this.table = new TableView();
    this.table.setRowFactory(new RowFactoryTable(paramProviderModel));
    this.data = FXCollections.observableArrayList();
    this.data.addAll(paramProviderModel.getLista(true));
    initControls();
  }
  
  public void atualiza()
  {
    Platform.runLater(new Runnable()
    {
      public void run()
      {
        List localList = TableFX.this.provider.getLista(true);
        ObservableList localObservableList = TableFX.this.table.getItems();
        localObservableList.clear();
        localObservableList.addAll(localList);
      }
    });
  }
  
  public void geraExcel(String paramString)
  {
    Util.excel.geraExcell(this.provider, paramString);
  }
  
  public ObservableList<B> getData()
  {
    return this.data;
  }
  
  public ProviderModel<B> getProvider()
  {
    return this.provider;
  }
  
  public TableView<B> getTable()
  {
    return this.table;
  }
  
  private void initControls()
  {
    this.table.setEditable(false);
    Iterator localIterator = this.provider.getColunas().iterator();
    while (localIterator.hasNext())
    {
      Coluna localColuna = (Coluna)localIterator.next();
      TableColumn localTableColumn;
      if (localColuna.equals(Coluna.STATUS))
      {
        localTableColumn = newColunaStatus();
        this.table.getColumns().add(localTableColumn);
      }
      else
      {
        localTableColumn = newColunaFx(localColuna);
        this.table.getColumns().add(localTableColumn);
      }
    }
    this.table.setItems(this.data);
    getChildren().add(this.table);
  }
  
  private TableColumn<B, String> newColunaFx(Coluna paramColuna)
  {
    TableColumn localTableColumn = new TableColumn(paramColuna.getTitulo());
    localTableColumn.setCellValueFactory(new PropertyFormatFactory(paramColuna));
    localTableColumn.setMinWidth(paramColuna.getTitulo().length() * 14);
    localTableColumn.setCellFactory(new CellColunaFactory(paramColuna));
    return localTableColumn;
  }
  
  private TableColumn<B, ProviderModel.ESemaforo> newColunaStatus()
  {
    TableColumn localTableColumn = new TableColumn("S");
    localTableColumn.setCellValueFactory(new PropertyStatusFactory(this.provider));
    localTableColumn.setCellFactory(new CellStatusFactory());
    return localTableColumn;
  }
  
  public B selecionado()
  {
    Object localObject = this.table.getSelectionModel().getSelectedItem();
    return (B)localObject;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/TableFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */