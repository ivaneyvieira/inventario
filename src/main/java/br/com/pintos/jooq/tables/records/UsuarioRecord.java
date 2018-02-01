package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.Usuario;
import br.com.pintos.jooq.tables.interfaces.IUsuario;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class UsuarioRecord
  extends UpdatableRecordImpl<UsuarioRecord>
  implements IUsuario
{
  private static final long serialVersionUID = 575034886L;
  
  public void setId(Long paramLong)
  {
    setValue(Usuario.USUARIO.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Usuario.USUARIO.ID);
  }
  
  public List<ColetaRecord> fetchColetaList()
  {
    return create().selectFrom(Coleta.COLETA).where(new Condition[] { Coleta.COLETA.USUARIO_ID.equal(getValue(Usuario.USUARIO.ID)) }).fetch();
  }
  
  public void setMatricula(Integer paramInteger)
  {
    setValue(Usuario.USUARIO.MATRICULA, paramInteger);
  }
  
  public Integer getMatricula()
  {
    return (Integer)getValue(Usuario.USUARIO.MATRICULA);
  }
  
  public void setNome(String paramString)
  {
    setValue(Usuario.USUARIO.NOME, paramString);
  }
  
  public String getNome()
  {
    return (String)getValue(Usuario.USUARIO.NOME);
  }
  
  public void setSenha(String paramString)
  {
    setValue(Usuario.USUARIO.SENHA, paramString);
  }
  
  public String getSenha()
  {
    return (String)getValue(Usuario.USUARIO.SENHA);
  }
  
  public void setApelido(String paramString)
  {
    setValue(Usuario.USUARIO.APELIDO, paramString);
  }
  
  public String getApelido()
  {
    return (String)getValue(Usuario.USUARIO.APELIDO);
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Usuario.USUARIO.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Usuario.USUARIO.VERSION);
  }
  
  public UsuarioRecord()
  {
    super(Usuario.USUARIO);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/UsuarioRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */