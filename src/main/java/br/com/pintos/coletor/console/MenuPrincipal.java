package br.com.pintos.coletor.console;

import br.com.pintos.coletor.bos.ColetaBo;
import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.InventarioBO;
import br.com.pintos.coletor.bos.LojaBo;
import br.com.pintos.framework.console.Campo;
import br.com.pintos.framework.console.ITerminal;
import br.com.pintos.framework.console.TelaMenu;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.util.Command;
import br.com.pintos.framework.util.ShowDialog;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;
import br.com.pintos.jooq.tables.pojos.Coleta;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.pojos.Loja;
import br.com.pintos.jooq.tables.pojos.Lote;
import br.com.pintos.jooq.tables.pojos.Usuario;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class MenuPrincipal
  extends TelaMenu
{
  private final ModeloColetor modelo;
  private Campo info1;
  private Campo info2;
  private Campo infoLote;
  private Campo infoColeta;
  private Campo info3;
  
  public MenuPrincipal(ITerminal paramITerminal, Usuario paramUsuario, Integer paramInteger)
    throws BOException
  {
    super(paramITerminal, "Inventario");
    Inventario localInventario = selecionaInventario();
    this.modelo = new ModeloColetor(localInventario, paramUsuario, paramInteger);
    if (this.modelo.getColeta() == null)
    {
      Lote localLote = selecionaLote();
      this.modelo.setLote(localLote);
    }
  }
  
  protected void actionEnter() {}
  
  protected void atualizaTela()
  {
    if (this.modelo.getColeta() != null) {
      menu03Coleta().execute();
    }
    String str1 = getNomeUsuario();
    String str2 = getNomeLoja();
    String str3 = getNumLote();
    String str4 = getDataInventario();
    String str5 = getQuantColeta();
    this.info1.setValor(str2);
    this.info2.setValor(str4);
    this.info3.setValor(str1);
    this.infoLote.setValor(str3);
    this.infoColeta.setValor(str5);
  }
  
  String getDataInventario()
  {
    Inventario localInventario = this.modelo.getInventario();
    Date localDate = localInventario.getData();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return localSimpleDateFormat.format(localDate);
  }
  
  public ModeloColetor getModelo()
  {
    return this.modelo;
  }
  
  String getNomeLoja()
  {
    Inventario localInventario = this.modelo.getInventario();
    Long localLong = localInventario.getLojaId();
    Loja localLoja = (Loja)Facade.loja.findById(localLong);
    return localLoja.getNome();
  }
  
  String getNomeUsuario()
  {
    Usuario localUsuario = this.modelo.getUsuario();
    String str1 = localUsuario.getApelido();
    String str2 = Util.string.format(this.modelo.getColetor(), "00");
    return String.format("%-16s%4s", new Object[] { str1, "C" + str2 });
  }
  
  String getNumLote()
  {
    Coleta localColeta = this.modelo.getColeta();
    if (localColeta == null) {
      return "---/--";
    }
    Lote localLote = this.modelo.getLote();
    Integer localInteger1 = localLote.getNumero();
    Integer localInteger2 = localColeta.getNumleitura();
    String str1 = Util.string.format(localInteger1, "000");
    String str2 = Util.string.format(localInteger2, "00");
    return str1 + "/" + str2;
  }
  
  String getQuantColeta()
    throws BOException
  {
    Coleta localColeta = this.modelo.getColeta();
    if (localColeta == null) {
      return "----";
    }
    String str = Util.string.format(Facade.coleta.contaColeta(localColeta), "0000");
    return str;
  }
  
  protected void iniciaMenu()
  {
    this.info1 = addText("");
    this.info2 = addText("");
    this.info3 = addText("");
    this.infoLote = addItem("Lote   ", menu02Lote(), Integer.valueOf(11));
    this.infoColeta = addItem("Coleta ", menu03Coleta(), Integer.valueOf(11));
    addItem("Apaga  ", menu04Estornos(), Integer.valueOf(0));
    addItem("Sair   ", menu05Sair(), Integer.valueOf(0));
  }
  
  public Command menu02Lote()
  {
    new Command(this)
    {
      public void run()
      {
        Lote localLote = MenuPrincipal.this.telaLote();
        if (localLote != null) {
          MenuPrincipal.this.modelo.setLote(localLote);
        }
      }
    };
  }
  
  public Command menu03Coleta()
  {
    new Command(this)
    {
      public void run()
      {
        if (MenuPrincipal.this.modelo.getColeta() != null)
        {
          TelaColetor localTelaColetor = new TelaColetor(MenuPrincipal.this);
          localTelaColetor.showModal();
          if (MenuPrincipal.this.pergunta("Deseja fechar o lote " + MenuPrincipal.this.getNumLote())) {
            MenuPrincipal.this.modelo.fechaColeta();
          }
        }
      }
    };
  }
  
  public Command menu04Estornos()
  {
    new Command(this)
    {
      public void run()
      {
        TelaEstorno localTelaEstorno = new TelaEstorno(MenuPrincipal.this);
        localTelaEstorno.showModal();
      }
    };
  }
  
  public Command menu05Sair()
  {
    new Command(this)
    {
      public void run()
      {
        MenuPrincipal.this.close();
      }
    };
  }
  
  private Inventario selecionaInventario()
  {
    List localList = Facade.inventario.findAberto();
    if (localList.size() == 0) {
      throw new BOException("Nao ha nenhum inventario aberto");
    }
    if (localList.size() == 1) {
      return (Inventario)localList.get(0);
    }
    for (Inventario localInventario = null; localInventario == null; localInventario = telaInv()) {}
    return localInventario;
  }
  
  private Lote selecionaLote()
  {
    for (Lote localLote = null; localLote == null; localLote = telaLote()) {}
    return localLote;
  }
  
  private Inventario telaInv()
  {
    TelaSelInv localTelaSelInv = new TelaSelInv(this, null);
    return (Inventario)localTelaSelInv.showModal();
  }
  
  private Lote telaLote()
  {
    Lote localLote = this.modelo.getLote();
    TelaLote localTelaLote = new TelaLote(this, localLote);
    return (Lote)localTelaLote.showModal();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/console/MenuPrincipal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */