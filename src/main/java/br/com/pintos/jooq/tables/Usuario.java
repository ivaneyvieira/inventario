package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.Keys;
import br.com.pintos.jooq.tables.records.UsuarioRecord;
import java.util.Arrays;
import java.util.List;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.UpdatableTableImpl;

public class Usuario
  extends UpdatableTableImpl<UsuarioRecord>
{
  private static final long serialVersionUID = 1917022371L;
  public static final Usuario USUARIO = new Usuario();
  public final TableField<UsuarioRecord, Long> ID = createField("id", SQLDataType.BIGINT, this);
  public final TableField<UsuarioRecord, Integer> MATRICULA = createField("matricula", SQLDataType.INTEGER, this);
  public final TableField<UsuarioRecord, String> NOME = createField("nome", SQLDataType.VARCHAR, this);
  public final TableField<UsuarioRecord, String> SENHA = createField("senha", SQLDataType.VARCHAR, this);
  public final TableField<UsuarioRecord, String> APELIDO = createField("apelido", SQLDataType.VARCHAR, this);
  public final TableField<UsuarioRecord, Integer> VERSION = createField("version", SQLDataType.INTEGER, this);
  
  public Class<UsuarioRecord> getRecordType()
  {
    return UsuarioRecord.class;
  }
  
  public Usuario()
  {
    super("usuario", Coletor.COLETOR);
  }
  
  public Usuario(String paramString)
  {
    super(paramString, Coletor.COLETOR, USUARIO);
  }
  
  public Identity<UsuarioRecord, Long> getIdentity()
  {
    return Keys.IDENTITY_USUARIO;
  }
  
  public UniqueKey<UsuarioRecord> getMainKey()
  {
    return Keys.KEY_USUARIO_PRIMARY;
  }
  
  public List<UniqueKey<UsuarioRecord>> getKeys()
  {
    return Arrays.asList(new UniqueKey[] { Keys.KEY_USUARIO_PRIMARY, Keys.KEY_USUARIO_MATRICULA });
  }
  
  public Usuario as(String paramString)
  {
    return new Usuario(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Usuario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */