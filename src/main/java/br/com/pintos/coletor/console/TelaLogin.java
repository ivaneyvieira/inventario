package br.com.pintos.coletor.console;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.UsuarioBo;
import br.com.pintos.framework.console.Campo;
import br.com.pintos.framework.console.EValidaLeitura;
import br.com.pintos.framework.console.ITerminal;
import br.com.pintos.framework.console.Tela;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.util.Strings;
import br.com.pintos.framework.util.Util;
import br.com.pintos.jooq.tables.pojos.Usuario;

public class TelaLogin
  extends Tela<Usuario>
{
  private Campo edtMat;
  private Campo edtNom;
  
  public TelaLogin(ITerminal paramITerminal)
  {
    super(paramITerminal, "Login", null);
  }
  
  public void actionTela(String paramString)
    throws BOException
  {
    Integer localInteger = Integer.valueOf(paramString);
    setMatricula(localInteger);
    close();
  }
  
  public void atualizaTela()
  {
    Usuario localUsuario = (Usuario)getResult();
    if (localUsuario == null)
    {
      this.edtNom.setValor("");
      this.edtMat.setValor("");
    }
    else
    {
      String str = localUsuario.getApelido();
      Integer localInteger = localUsuario.getMatricula();
      this.edtNom.setValor(str);
      this.edtMat.setValor(Util.string.format(localInteger, "000000"));
    }
  }
  
  protected void iniciaControles()
  {
    this.edtMat = addTexto("Matricula  : ", Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(7));
    this.edtNom = addTexto(Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(20));
    addLeitura("Matricula  : ", Integer.valueOf(1), Integer.valueOf(9), Integer.valueOf(7));
  }
  
  private void setMatricula(Integer paramInteger)
    throws BOException
  {
    Usuario localUsuario = Facade.usuario.getUsuario(paramInteger);
    setResult(localUsuario);
    if (localUsuario == null) {
      throw new BOException("Usuario nao encontrado");
    }
  }
  
  protected void validaLeitura(String paramString)
    throws EValidaLeitura
  {
    if (!paramString.matches("[0-9]+")) {
      throw new EValidaLeitura("Matricula Invalida: " + paramString);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/console/TelaLogin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */