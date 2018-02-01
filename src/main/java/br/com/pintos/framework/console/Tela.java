package br.com.pintos.framework.console;

import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.dados.exception.ErroFatal;
import br.com.pintos.framework.dados.exception.ErroInterno;
import br.com.pintos.framework.util.ShowDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Tela<T>
  implements ShowDialog
{
  private static final String ESCS = "\033";
  private T result;
  private final ITerminal terminal;
  private final Tela telaPai;
  private Boolean sair = Boolean.valueOf(false);
  private final List<Campo> campos = new ArrayList();
  private Campo campoLeitura;
  private final Campo campoTitulo;
  private T resultInicial;
  
  public Tela(ITerminal paramITerminal, String paramString, T paramT)
  {
    this(paramITerminal, null, paramString, paramT);
  }
  
  public Tela(ITerminal paramITerminal, Tela paramTela, String paramString, T paramT)
  {
    this.result = paramT;
    this.resultInicial = paramT;
    this.terminal = paramITerminal;
    this.telaPai = paramTela;
    this.campoTitulo = addTitulo(Integer.valueOf(1));
    this.campoTitulo.setValor(String.format("%-15s%5s", new Object[] { paramString, "v 1.2" }));
  }
  
  public Tela(Tela paramTela, String paramString, T paramT)
  {
    this(paramTela.getTerminal(), paramTela, paramString, paramT);
  }
  
  protected void actionEnter()
  {
    close();
  }
  
  protected void actionEsc()
  {
    if (this.telaPai != null)
    {
      setResult(this.resultInicial);
      close();
    }
  }
  
  protected abstract void actionTela(String paramString)
    throws BOException;
  
  private Campo addCampo(Campo paramCampo)
  {
    this.campos.add(paramCampo);
    return paramCampo;
  }
  
  public Campo addErro(Integer paramInteger)
  {
    return addCampo(new Campo(this, ETipo.error, Integer.valueOf(1), paramInteger, this.terminal.getColunas(), EAlin.cen));
  }
  
  public Campo addLabel(Integer paramInteger1, Integer paramInteger2, String paramString)
  {
    Campo localCampo = addCampo(new Campo(this, ETipo.label, paramInteger1, paramInteger2, Integer.valueOf(paramString.length()), EAlin.esq));
    localCampo.setValor(paramString);
    return localCampo;
  }
  
  public void addLeitura(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
  {
    this.campoLeitura = addTexto(paramString, paramInteger1, paramInteger2, paramInteger3);
  }
  
  public Campo addTexto(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
  {
    return addTexto("", paramInteger1, paramInteger2, paramInteger3);
  }
  
  public Campo addTexto(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
  {
    Integer localInteger = Integer.valueOf(paramString.length());
    if (localInteger.intValue() > 0) {
      addLabel(paramInteger1, paramInteger2, paramString);
    }
    return addCampo(new Campo(this, ETipo.field, Integer.valueOf(paramInteger1.intValue() + localInteger.intValue()), paramInteger2, paramInteger3, EAlin.esq));
  }
  
  public Campo addTitulo(Integer paramInteger)
  {
    return addCampo(new Campo(this, ETipo.titulo, Integer.valueOf(1), paramInteger, this.terminal.getColunas(), EAlin.esq));
  }
  
  protected abstract void atualizaTela();
  
  public void beep()
  {
    this.terminal.beep();
  }
  
  public void close()
  {
    this.sair = Boolean.valueOf(true);
  }
  
  public void desenhaTela()
  {
    this.terminal.apagaTela();
    Iterator localIterator = this.campos.iterator();
    while (localIterator.hasNext())
    {
      Campo localCampo = (Campo)localIterator.next();
      localCampo.desenha();
    }
  }
  
  public void exibeErro(String paramString)
  {
    TelaAviso localTelaAviso = new TelaAviso(this, paramString);
    localTelaAviso.showModal();
  }
  
  private void finaliza()
  {
    if (this.telaPai == null) {
      this.terminal.apagaTela();
    } else {
      this.telaPai.desenhaTela();
    }
  }
  
  public Campo getCampoTitulo()
  {
    return this.campoTitulo;
  }
  
  public T getResult()
  {
    return (T)this.result;
  }
  
  public Tela getTelaPai()
  {
    return this.telaPai;
  }
  
  public ITerminal getTerminal()
  {
    return this.terminal;
  }
  
  public String getTitulo()
  {
    return this.campoTitulo.getValor();
  }
  
  protected abstract void iniciaControles();
  
  public Boolean onEnter()
  {
    return Boolean.valueOf(true);
  }
  
  public Boolean onExit()
  {
    return Boolean.valueOf(true);
  }
  
  protected boolean pergunta(String paramString)
  {
    TelaPergunta localTelaPergunta = new TelaPergunta(this, paramString);
    return ((Integer)localTelaPergunta.showModal()).intValue() == 1;
  }
  
  protected void sairSistema(String paramString)
  {
    close();
    throw new BOException(paramString);
  }
  
  public void setResult(T paramT)
  {
    this.result = paramT;
  }
  
  public void showErro(BOException paramBOException)
  {
    exibeErro(paramBOException.getMessage());
  }
  
  public void showErroFatal(ErroFatal paramErroFatal)
  {
    exibeErro("Erro Fatal no sistema\nFavor Entrar em contato com o Administrador do sistema");
    System.exit(0);
  }
  
  public void showErroInterno(ErroInterno paramErroInterno)
  {
    exibeErro("Erro Interno no sistema\nFavor Entrar em contato com o Administrador do sistema");
  }
  
  public T showModal()
  {
    iniciaControles();
    desenhaTela();
    if (onEnter().booleanValue())
    {
      this.sair = Boolean.valueOf(false);
      while (!this.sair.booleanValue()) {
        try
        {
          atualizaTela();
          String str = this.campoLeitura.executaLeitura();
          if (str.equals(""))
          {
            actionEnter();
          }
          else if (str.equals("\033"))
          {
            actionEsc();
          }
          else
          {
            validaLeitura(str);
            actionTela(str);
          }
          if (this.sair.booleanValue() == true) {
            this.sair = onExit();
          }
        }
        catch (BOException localBOException)
        {
          exibeErro(localBOException.getMessage());
        }
      }
    }
    finaliza();
    return (T)this.result;
  }
  
  protected abstract void validaLeitura(String paramString)
    throws EValidaLeitura;
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/console/Tela.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */