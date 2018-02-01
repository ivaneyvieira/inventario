package br.com.pintos.framework.fx.forms;

import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.fx.controls.ProviderModel;
import br.com.pintos.framework.report.Report;
import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.ShowDialog;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class BrowserFX<B, F>
  extends DialogFX
{
  protected final FiltroFX<F> formFiltro = newFormFiltro();
  protected final TableFX<B> tableBean = newTableFX();
  protected final ProviderModel<B> providerTable = newProviderTable();
  
  public BrowserFX(String paramString)
  {
    super(paramString);
  }
  
  private Command actionDelete()
  {
    new Command(this)
    {
      public void run()
      {
        Object localObject = BrowserFX.this.tableBean.selecionado();
        if (localObject != null)
        {
          final FormBean localFormBean = BrowserFX.this.newFormBean(localObject);
          localFormBean.setBean(localObject);
          localFormBean.initControls();
          final DialogBean localDialogBean = new DialogBean("Remove", localFormBean);
          localDialogBean.setActionConfirma(new Command(BrowserFX.this)
          {
            public void run()
              throws BOException
            {
              Object localObject = localFormBean.getBean();
              BrowserFX.this.delete(localObject);
              BrowserFX.this.atualizaTabela();
              localDialogBean.close();
            }
          });
          localDialogBean.run(BrowserFX.this.application);
        }
      }
    };
  }
  
  private Command actionEdit()
  {
    new Command(this)
    {
      public void run()
      {
        Object localObject = BrowserFX.this.tableBean.selecionado();
        if (localObject != null)
        {
          final FormBean localFormBean = BrowserFX.this.newFormBean(localObject);
          localFormBean.setBean(localObject);
          localFormBean.initControls();
          final DialogBean localDialogBean = new DialogBean("Edita", localFormBean);
          localDialogBean.setActionConfirma(new Command(BrowserFX.this)
          {
            public void run()
              throws BOException
            {
              Object localObject = localFormBean.getBean();
              BrowserFX.this.update(localObject);
              BrowserFX.this.atualizaTabela();
              localDialogBean.close();
            }
          });
          localDialogBean.run(BrowserFX.this.application);
        }
      }
    };
  }
  
  private Command actionNovo()
  {
    new Command(this)
    {
      public void run()
      {
        Object localObject = BrowserFX.this.newBean();
        final FormBean localFormBean = BrowserFX.this.newFormBean(localObject);
        localFormBean.setBean(localObject);
        localFormBean.initControls();
        final DialogBean localDialogBean = new DialogBean("Novo", localFormBean);
        localDialogBean.setActionConfirma(new Command(BrowserFX.this)
        {
          public void run()
          {
            Object localObject = localFormBean.getBean();
            BrowserFX.this.insert(localObject);
            BrowserFX.this.atualizaTabela();
            localDialogBean.close();
          }
        });
        localDialogBean.run(BrowserFX.this.application);
      }
    };
  }
  
  private Command actionPrint()
  {
    new Command(this)
    {
      protected void run()
      {
        ProviderModel localProviderModel = BrowserFX.this.tableBean.getProvider();
        Report localReport = new Report(localProviderModel);
        ReportDialog localReportDialog = new ReportDialog(localProviderModel.getTitulo(), localReport);
        localReportDialog.run(BrowserFX.this.application);
      }
    };
  }
  
  protected void atualizaTabela()
  {
    this.tableBean.atualiza();
  }
  
  protected abstract void delete(B paramB)
    throws BOException;
  
  public ProviderModel<B> getProviderTable()
  {
    return this.providerTable;
  }
  
  public void initControls()
  {
    super.initControls();
    this.formFiltro.initControls();
    setTop(this.formFiltro);
    setCenter(this.tableBean);
    TableView localTableView = this.tableBean.getTable();
    localTableView.setOnKeyPressed(new EventHandler()
    {
      public void handle(KeyEvent paramAnonymousKeyEvent)
      {
        if (paramAnonymousKeyEvent.getCode() == KeyCode.ENTER) {
          BrowserFX.this.actionEdit().execute();
        }
      }
    });
    localTableView.setOnMouseClicked(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        if (paramAnonymousMouseEvent.getClickCount() == 2) {
          BrowserFX.this.actionEdit().execute();
        }
      }
    });
  }
  
  protected abstract void insert(B paramB)
    throws BOException;
  
  protected abstract B newBean();
  
  protected abstract FormBean<B> newFormBean(B paramB);
  
  protected abstract FiltroFX<F> newFormFiltro();
  
  protected abstract ProviderModel<B> newProviderTable();
  
  protected TableFX<B> newTableFX()
  {
    return new TableFX(this.providerTable);
  }
  
  protected ToolBar toolBar()
  {
    ToolBar localToolBar = new ToolBar();
    ObservableList localObservableList = localToolBar.getItems();
    localObservableList.add(createButton("Novo", "add.png", actionNovo()));
    localObservableList.add(createButton("Modificar", "update.png", actionEdit()));
    localObservableList.add(createButton("Remover", "delete.png", actionDelete()));
    localObservableList.add(new Separator());
    localObservableList.add(createButtonFechar());
    return localToolBar;
  }
  
  protected abstract void update(B paramB)
    throws BOException;
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/BrowserFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */