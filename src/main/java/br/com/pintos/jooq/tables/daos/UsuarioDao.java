package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.UsuarioRecord;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class UsuarioDao
  extends DAOImpl<UsuarioRecord, br.com.pintos.jooq.tables.pojos.Usuario, Long>
{
  public UsuarioDao()
  {
    super(br.com.pintos.jooq.tables.Usuario.USUARIO, br.com.pintos.jooq.tables.pojos.Usuario.class);
  }
  
  public UsuarioDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Usuario.USUARIO, br.com.pintos.jooq.tables.pojos.Usuario.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Usuario paramUsuario)
  {
    return paramUsuario.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Usuario> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Usuario.USUARIO.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Usuario fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Usuario)fetchOne(br.com.pintos.jooq.tables.Usuario.USUARIO.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Usuario> fetchByMatricula(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Usuario.USUARIO.MATRICULA, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Usuario fetchOneByMatricula(Integer paramInteger)
  {
    return (br.com.pintos.jooq.tables.pojos.Usuario)fetchOne(br.com.pintos.jooq.tables.Usuario.USUARIO.MATRICULA, paramInteger);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Usuario> fetchByNome(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Usuario.USUARIO.NOME, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Usuario> fetchBySenha(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Usuario.USUARIO.SENHA, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Usuario> fetchByApelido(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Usuario.USUARIO.APELIDO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Usuario> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Usuario.USUARIO.VERSION, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/UsuarioDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */