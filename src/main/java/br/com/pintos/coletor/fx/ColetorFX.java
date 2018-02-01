package br.com.pintos.coletor.fx;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.LojaBo;
import br.com.pintos.framework.dados.Database;
import br.com.pintos.framework.fx.view.ApplicationFX;
import javafx.application.Application;
import javafx.stage.Stage;

public class ColetorFX
  extends Application
{
  private ApplicationFX applicationFX;
  private VColetorApplication application;
  private Stage stage;
  
  public static void main(String[] paramArrayOfString)
  {
    Facade.loja.findAll();
    launch(ColetorFX.class, paramArrayOfString);
  }
  
  private VColetorApplication application()
  {
    return new VColetorApplication();
  }
  
  public void start(Stage paramStage)
  {
    this.application = application();
    this.stage = paramStage;
    this.applicationFX = new ApplicationFX(this.application);
    this.applicationFX.start(paramStage);
  }
  
  public void stop()
    throws Exception
  {
    Database.instance.close();
    this.applicationFX.closeAll();
    this.stage.close();
    super.stop();
    System.gc();
    System.exit(0);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/ColetorFX.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */