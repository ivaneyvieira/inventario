package br.com.pintos.framework.fx.controls;

import java.util.Calendar;
import java.util.Date;

public class Coluna
{
  private final String nome;
  private final String mask;
  private final String titulo;
  private final EAlinh alinhamento;
  private final Class tipo;
  private final int largura;
  public static final Coluna STATUS = new Coluna("STATUS", "STATUS", 0, String.class, "", EAlinh.right);
  
  public static EAlinh alinhaTipo(Class<?> paramClass)
  {
    if ((Number.class.isAssignableFrom(paramClass)) || (Date.class.isAssignableFrom(paramClass)) || (Calendar.class.isAssignableFrom(paramClass))) {
      return EAlinh.right;
    }
    return EAlinh.left;
  }
  
  public Coluna(String paramString1, String paramString2, int paramInt, Class paramClass, String paramString3, EAlinh paramEAlinh)
  {
    this.nome = paramString1;
    this.titulo = paramString2;
    this.mask = paramString3;
    this.alinhamento = paramEAlinh;
    this.tipo = paramClass;
    this.largura = paramInt;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (getClass() != paramObject.getClass()) {
      return false;
    }
    Coluna localColuna = (Coluna)paramObject;
    if (this.alinhamento != localColuna.alinhamento) {
      return false;
    }
    if (this.mask == null)
    {
      if (localColuna.mask != null) {
        return false;
      }
    }
    else if (!this.mask.equals(localColuna.mask)) {
      return false;
    }
    if (this.nome == null)
    {
      if (localColuna.nome != null) {
        return false;
      }
    }
    else if (!this.nome.equals(localColuna.nome)) {
      return false;
    }
    if (this.titulo == null)
    {
      if (localColuna.titulo != null) {
        return false;
      }
    }
    else if (!this.titulo.equals(localColuna.titulo)) {
      return false;
    }
    return true;
  }
  
  public EAlinh getAlinhamento()
  {
    return this.alinhamento;
  }
  
  public int getLargura()
  {
    return this.largura;
  }
  
  public String getMask()
  {
    return this.mask;
  }
  
  public String getNome()
  {
    return this.nome;
  }
  
  public Class getTipo()
  {
    return this.tipo;
  }
  
  public String getTitulo()
  {
    return this.titulo;
  }
  
  public int hashCode()
  {
    int i = 1;
    i = 31 * i + (this.alinhamento == null ? 0 : this.alinhamento.hashCode());
    i = 31 * i + (this.mask == null ? 0 : this.mask.hashCode());
    i = 31 * i + (this.nome == null ? 0 : this.nome.hashCode());
    i = 31 * i + (this.titulo == null ? 0 : this.titulo.hashCode());
    return i;
  }
  
  public static enum EAlinh
  {
    right,  center,  left;
    
    private EAlinh() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/controls/Coluna.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */