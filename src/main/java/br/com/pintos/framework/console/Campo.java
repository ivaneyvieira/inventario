package br.com.pintos.framework.console;

public class Campo
{
  private final Tela tela;
  private final ETipo tipo;
  private final Integer col;
  private final Integer lin;
  private String valor;
  private final Integer tamanho;
  private final EAlin alinhamento;
  
  public Campo(Tela paramTela, ETipo paramETipo, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, EAlin paramEAlin)
  {
    this.tela = paramTela;
    this.tipo = paramETipo;
    this.col = paramInteger1;
    this.lin = paramInteger2;
    this.valor = "";
    this.tamanho = paramInteger3;
    this.alinhamento = paramEAlin;
  }
  
  public void desenha()
  {
    ITerminal localITerminal = this.tela.getTerminal();
    localITerminal.cusorPos(this.col, this.lin);
    setAtributo();
    escreve();
    localITerminal.normal();
  }
  
  public void escreve()
  {
    ITerminal localITerminal = this.tela.getTerminal();
    switch (this.alinhamento)
    {
    case cen: 
      localITerminal.escreveCen(this.valor, this.tamanho);
      break;
    case esq: 
      localITerminal.escreveEsq(this.valor, this.tamanho);
      break;
    case dir: 
      localITerminal.escreveDir(this.valor, this.tamanho);
    }
  }
  
  public String executaLeitura()
  {
    ITerminal localITerminal = this.tela.getTerminal();
    setValor("");
    String str = localITerminal.readLine(this.col, this.lin, this.tamanho);
    setValor(str);
    return this.valor;
  }
  
  public EAlin getAlinhamento()
  {
    return this.alinhamento;
  }
  
  public Integer getCol()
  {
    return this.col;
  }
  
  public Integer getLin()
  {
    return this.lin;
  }
  
  public Integer getTamanho()
  {
    return this.tamanho;
  }
  
  public Tela getTela()
  {
    return this.tela;
  }
  
  public ETipo getTipo()
  {
    return this.tipo;
  }
  
  public String getValor()
  {
    return this.valor;
  }
  
  private void setAtributo()
  {
    ITerminal localITerminal = this.tela.getTerminal();
    switch (this.tipo)
    {
    case label: 
      localITerminal.brightON();
      break;
    case field: 
      localITerminal.sublinhadoON();
      break;
    case error: 
      localITerminal.blinkON();
      break;
    case titulo: 
      localITerminal.brightON();
      localITerminal.reversoON();
    }
  }
  
  public void setValor(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    if (!paramString.equals(this.valor)) {
      this.valor = paramString;
    }
    desenha();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/console/Campo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */