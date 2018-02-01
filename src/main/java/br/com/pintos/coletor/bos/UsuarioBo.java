package br.com.pintos.coletor.bos;

import br.com.pintos.framework.dados.AbstractBo;
import br.com.pintos.framework.dados.Query;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.daos.UsuarioDao;
import br.com.pintos.jooq.tables.pojos.Usuario;
import br.com.pintos.jooq.tables.records.UsuarioRecord;
import org.jooq.impl.Factory;

public class UsuarioBo
  extends AbstractBo<UsuarioRecord, Usuario>
{
  public UsuarioBo()
  {
    super(Tables.USUARIO, Usuario.class);
  }
  
  public Usuario getUsuario(final Integer paramInteger)
    throws BOException
  {
    (Usuario)transaction(new Query()
    {
      public Usuario run(Factory paramAnonymousFactory)
      {
        UsuarioDao localUsuarioDao = new UsuarioDao(paramAnonymousFactory);
        return localUsuarioDao.fetchOneByMatricula(paramInteger);
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/bos/UsuarioBo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */