package br.com.pintos.framework.fx.controls;

import br.com.pintos.framework.util.Beans;
import br.com.pintos.framework.util.Util;
import java.util.ArrayList;
import java.util.List;

public abstract class ProviderModel<T>
{
  private final List<Coluna> colunas = new ArrayList();
  private final List<T> lista = new ArrayList();
  private final String titulo;
  private final Class<T> classe;
  
  public ProviderModel(String paramString, Class<T> paramClass)
  {
    this.titulo = paramString;
    this.classe = paramClass;
  }
  
  public void addColuna(String paramString1, String paramString2)
  {
    addColuna(paramString1, paramString2, 0, "");
  }
  
  public void addColuna(String paramString1, String paramString2, int paramInt)
  {
    addColuna(paramString1, paramString2, paramInt, "");
  }
  
  public void addColuna(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    Class localClass = Util.bean.getTipo(this.classe, paramString1);
    Coluna.EAlinh localEAlinh = Coluna.alinhaTipo(localClass);
    this.colunas.add(new Coluna(paramString1, paramString2, paramInt, localClass, paramString3, localEAlinh));
  }
  
  public void addColuna(String paramString1, String paramString2, String paramString3)
  {
    addColuna(paramString1, paramString2, 0, paramString3);
  }
  
  public void addColunaSTATUS()
  {
    this.colunas.add(Coluna.STATUS);
  }
  
  public void atualiza()
  {
    this.lista.clear();
    this.lista.addAll(dadosNovos());
  }
  
  protected abstract List<T> dadosNovos();
  
  public Class<T> getClasse()
  {
    return this.classe;
  }
  
  public final List<Coluna> getColunas()
  {
    return this.colunas;
  }
  
  public final List<T> getLista(boolean paramBoolean)
  {
    if (paramBoolean) {
      atualiza();
    }
    return this.lista;
  }
  
  protected T getLockupBean(String paramString)
  {
    return null;
  }
  
  public String getLockupField(T paramT)
  {
    return null;
  }
  
  public String getLockupLabel(T paramT)
  {
    return null;
  }
  
  public ESemaforo getStatus(T paramT)
  {
    return null;
  }
  
  public String getStyle(T paramT)
  {
    return null;
  }
  
  public String getTitulo()
  {
    return this.titulo;
  }
  
  public static enum ESemaforo
  {
    verde,  amarelo,  vermelho;
    
    private ESemaforo() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/ProviderModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */