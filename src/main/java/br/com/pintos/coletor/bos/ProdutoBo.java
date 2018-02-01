package br.com.pintos.coletor.bos;

import br.com.pintos.framework.dados.AbstractBo;
import br.com.pintos.framework.dados.Query;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.daos.ProdutoDao;
import br.com.pintos.jooq.tables.pojos.Produto;
import br.com.pintos.jooq.tables.records.ProdutoRecord;
import org.jooq.impl.Factory;

public class ProdutoBo
  extends AbstractBo<ProdutoRecord, Produto>
{
  public ProdutoBo()
  {
    super(Tables.PRODUTO, Produto.class);
  }
  
  public Produto getProduto(final String paramString)
  {
    (Produto)transaction(new Query()
    {
      public Produto run(Factory paramAnonymousFactory)
      {
        ProdutoDao localProdutoDao = new ProdutoDao(paramAnonymousFactory);
        return localProdutoDao.fetchOneByBarcode(paramString);
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/bos/ProdutoBo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */