package br.com.pintos.coletor.console;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.LeituraBo;
import br.com.pintos.coletor.bos.ProdutoBo;
import br.com.pintos.framework.console.Campo;
import br.com.pintos.framework.console.EValidaLeitura;
import br.com.pintos.framework.console.Tela;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.tables.pojos.Coleta;
import br.com.pintos.jooq.tables.pojos.Leitura;
import br.com.pintos.jooq.tables.pojos.Produto;

public class TelaColetor
  extends Tela<Leitura>
{
  private final MenuPrincipal menuPrincipal;
  private Campo loja;
  private Campo lote;
  private Campo resp;
  private Campo barra;
  private Campo codigo;
  private Campo grade;
  private Campo nome1;
  private Campo nome2;
  
  public TelaColetor(MenuPrincipal paramMenuPrincipal)
  {
    super(paramMenuPrincipal, "COLETA DE DADOS", Facade.leitura.ultimaLeitura(paramMenuPrincipal.getModelo().getColeta()));
    this.menuPrincipal = paramMenuPrincipal;
  }
  
  protected void actionEnter() {}
  
  public void actionTela(String paramString)
  {
    try
    {
      if (paramString.matches("^LOT[0-9]+$")) {
        lerLote(paramString.substring(3));
      } else if (paramString.matches("^[0-9]+$")) {
        lerProduto(paramString);
      } else {
        beep();
      }
    }
    catch (BOException localBOException)
    {
      exibeErro(localBOException.getMessage());
    }
  }
  
  public void atualizaTela()
    throws BOException
  {
    String str1 = this.menuPrincipal.getNumLote();
    String str2 = this.menuPrincipal.getQuantColeta();
    String str3 = this.menuPrincipal.getNomeLoja();
    String str4 = this.menuPrincipal.getNomeUsuario();
    this.loja.setValor(String.format("LJ: %-16s", new Object[] { str3 }));
    this.lote.setValor(String.format("LT: %6s  QT: %4s", new Object[] { str1, str2 }));
    this.resp.setValor(String.format("US: %-16s", new Object[] { str4 }));
    Leitura localLeitura = (Leitura)getResult();
    if (localLeitura == null)
    {
      this.barra.setValor("");
      this.codigo.setValor("");
      this.grade.setValor("");
      setNomeProduto("");
    }
    else
    {
      Long localLong = localLeitura.getProdutoId();
      String str5 = localLeitura.getObservacao();
      if (localLong == null)
      {
        this.barra.setValor(localLeitura.getLeitura());
        this.codigo.setValor("");
        this.grade.setValor("");
        setNomeProduto(str5);
      }
      else
      {
        Produto localProduto = (Produto)Facade.produto.findById(localLong);
        this.barra.setValor(localProduto.getBarcode().trim());
        this.codigo.setValor(localProduto.getCodigo().trim());
        this.grade.setValor(localProduto.getGrade().trim());
        if (str5.equals("")) {
          setNomeProduto(localProduto.getDescricao());
        } else {
          setNomeProduto(str5);
        }
      }
    }
  }
  
  protected void iniciaControles()
  {
    this.loja = addTitulo(Integer.valueOf(2));
    this.lote = addTitulo(Integer.valueOf(3));
    this.resp = addTitulo(Integer.valueOf(4));
    this.barra = addTexto("BAR: ", Integer.valueOf(1), Integer.valueOf(5), Integer.valueOf(15));
    this.codigo = addTexto("C/G: ", Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(6));
    this.grade = addTexto("-", Integer.valueOf(12), Integer.valueOf(6), Integer.valueOf(8));
    this.nome1 = addTexto(Integer.valueOf(1), Integer.valueOf(7), Integer.valueOf(20));
    this.nome2 = addTexto(Integer.valueOf(1), Integer.valueOf(8), Integer.valueOf(20));
    addLeitura("LEIT: ", Integer.valueOf(1), Integer.valueOf(9), Integer.valueOf(14));
  }
  
  private void lerLote(String paramString)
    throws BOException
  {
    try
    {
      ModeloColetor localModeloColetor = this.menuPrincipal.getModelo();
      Integer localInteger = Integer.valueOf(paramString);
      localModeloColetor.setLote(localInteger);
      setResult(null);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      beep();
    }
  }
  
  private void lerProduto(String paramString)
    throws BOException
  {
    ModeloColetor localModeloColetor = this.menuPrincipal.getModelo();
    try
    {
      localColeta = localModeloColetor.getColeta();
      Facade.leitura.validaLeitura(paramString, localColeta);
      Leitura localLeitura1 = Facade.leitura.adicionaLeitura(paramString, localColeta);
      setResult(localLeitura1);
    }
    catch (BOException localBOException)
    {
      Coleta localColeta = localModeloColetor.getColeta();
      String str = localBOException.getMessage();
      Leitura localLeitura2 = Facade.leitura.adicionaErro(paramString, localColeta, str);
      setResult(localLeitura2);
      beep();
    }
  }
  
  private void setNomeProduto(String paramString)
  {
    if (paramString.length() <= 20)
    {
      this.nome1.setValor(paramString);
      this.nome2.setValor("");
    }
    else
    {
      this.nome1.setValor(paramString.substring(0, 20));
      this.nome2.setValor(paramString.substring(20));
    }
  }
  
  protected void validaLeitura(String paramString)
    throws EValidaLeitura
  {}
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/console/TelaColetor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */