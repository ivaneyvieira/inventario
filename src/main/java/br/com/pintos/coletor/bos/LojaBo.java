package br.com.pintos.coletor.bos;

import br.com.pintos.framework.dados.AbstractBo;
import br.com.pintos.framework.dados.Query;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.daos.LojaDao;
import br.com.pintos.jooq.tables.pojos.Loja;
import br.com.pintos.jooq.tables.records.LojaRecord;
import org.jooq.impl.Factory;

public class LojaBo
  extends AbstractBo<LojaRecord, Loja>
{
  public LojaBo()
  {
    super(Tables.LOJA, Loja.class);
  }
  
  public Loja getLoja(final Integer paramInteger)
    throws BOException
  {
    (Loja)transaction(new Query()
    {
      public Loja run(Factory paramAnonymousFactory)
      {
        LojaDao localLojaDao = new LojaDao(paramAnonymousFactory);
        return localLojaDao.fetchOneByStoreno(paramInteger);
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/bos/LojaBo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */