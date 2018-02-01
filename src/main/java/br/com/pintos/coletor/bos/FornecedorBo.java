package br.com.pintos.coletor.bos;

import br.com.pintos.framework.dados.AbstractBo;
import br.com.pintos.framework.dados.Query;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.daos.FornecedorDao;
import br.com.pintos.jooq.tables.pojos.Fornecedor;
import br.com.pintos.jooq.tables.records.FornecedorRecord;
import org.jooq.impl.Factory;

public class FornecedorBo
  extends AbstractBo<FornecedorRecord, Fornecedor>
{
  public FornecedorBo()
  {
    super(Tables.FORNECEDOR, Fornecedor.class);
  }
  
  public Fornecedor getFornecedor(final Integer paramInteger)
    throws BOException
  {
    (Fornecedor)transaction(new Query()
    {
      public Fornecedor run(Factory paramAnonymousFactory)
      {
        FornecedorDao localFornecedorDao = new FornecedorDao(paramAnonymousFactory);
        return localFornecedorDao.fetchOneByCodigo(paramInteger);
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/bos/FornecedorBo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */