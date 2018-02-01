package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.EstoqueRecord;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class EstoqueDao
  extends DAOImpl<EstoqueRecord, br.com.pintos.jooq.tables.pojos.Estoque, Long>
{
  public EstoqueDao()
  {
    super(br.com.pintos.jooq.tables.Estoque.ESTOQUE, br.com.pintos.jooq.tables.pojos.Estoque.class);
  }
  
  public EstoqueDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Estoque.ESTOQUE, br.com.pintos.jooq.tables.pojos.Estoque.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Estoque paramEstoque)
  {
    return paramEstoque.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Estoque> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Estoque.ESTOQUE.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Estoque fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Estoque)fetchOne(br.com.pintos.jooq.tables.Estoque.ESTOQUE.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Estoque> fetchByQuant(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Estoque.ESTOQUE.QUANT, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Estoque> fetchByInventarioId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Estoque.ESTOQUE.INVENTARIO_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Estoque> fetchByProdutoId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Estoque.ESTOQUE.PRODUTO_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Estoque> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Estoque.ESTOQUE.VERSION, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/EstoqueDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */