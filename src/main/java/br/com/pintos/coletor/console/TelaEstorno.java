package br.com.pintos.coletor.console;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.LeituraBo;
import br.com.pintos.framework.console.Campo;
import br.com.pintos.framework.console.TelaMenu;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.ShowDialog;
import br.com.pintos.jooq.tables.pojos.Coleta;

public class TelaEstorno
  extends TelaMenu
{
  private final MenuPrincipal menuPrincipal;
  private Campo edtLoja;
  private Campo edtLote;
  private Campo edtResp;
  
  public TelaEstorno(MenuPrincipal paramMenuPrincipal)
  {
    super(paramMenuPrincipal, "Configuracao");
    this.menuPrincipal = paramMenuPrincipal;
  }
  
  protected void atualizaTela()
    throws BOException
  {
    String str1 = this.menuPrincipal.getNumLote();
    String str2 = this.menuPrincipal.getQuantColeta();
    String str3 = this.menuPrincipal.getNomeLoja();
    String str4 = this.menuPrincipal.getNomeUsuario();
    this.edtLoja.setValor(String.format("LJ: %-16s", new Object[] { str3 }));
    this.edtLote.setValor(String.format("LT: %6s  QT: %4s", new Object[] { str1, str2 }));
    this.edtResp.setValor(String.format("US: %-16s", new Object[] { str4 }));
  }
  
  protected void iniciaMenu()
  {
    this.edtLoja = addText();
    this.edtLote = addText();
    this.edtResp = addText();
    addItem("Apagar ultimo", menuApagaUtm(), Integer.valueOf(0));
    addItem("Apagar todos", menuApagaTudo(), Integer.valueOf(0));
    addItem("Voltar", menuVoltar(), Integer.valueOf(0));
  }
  
  public Command menuApagaTudo()
  {
    new Command(this)
    {
      public void run()
      {
        Coleta localColeta = TelaEstorno.this.menuPrincipal.getModelo().getColeta();
        if (localColeta != null) {
          Facade.leitura.apagaTodos(localColeta);
        }
      }
    };
  }
  
  public Command menuApagaUtm()
  {
    new Command(this)
    {
      public void run()
      {
        Coleta localColeta = TelaEstorno.this.menuPrincipal.getModelo().getColeta();
        if (localColeta != null) {
          Facade.leitura.apagaUltimo(localColeta);
        }
      }
    };
  }
  
  public Command menuVoltar()
  {
    new Command(this)
    {
      public void run()
      {
        TelaEstorno.this.close();
      }
    };
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/console/TelaEstorno.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */