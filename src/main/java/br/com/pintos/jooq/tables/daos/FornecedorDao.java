package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.FornecedorRecord;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class FornecedorDao
  extends DAOImpl<FornecedorRecord, br.com.pintos.jooq.tables.pojos.Fornecedor, Long>
{
  public FornecedorDao()
  {
    super(br.com.pintos.jooq.tables.Fornecedor.FORNECEDOR, br.com.pintos.jooq.tables.pojos.Fornecedor.class);
  }
  
  public FornecedorDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Fornecedor.FORNECEDOR, br.com.pintos.jooq.tables.pojos.Fornecedor.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Fornecedor paramFornecedor)
  {
    return paramFornecedor.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Fornecedor> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Fornecedor.FORNECEDOR.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Fornecedor fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Fornecedor)fetchOne(br.com.pintos.jooq.tables.Fornecedor.FORNECEDOR.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Fornecedor> fetchByCodigo(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Fornecedor.FORNECEDOR.CODIGO, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Fornecedor fetchOneByCodigo(Integer paramInteger)
  {
    return (br.com.pintos.jooq.tables.pojos.Fornecedor)fetchOne(br.com.pintos.jooq.tables.Fornecedor.FORNECEDOR.CODIGO, paramInteger);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Fornecedor> fetchByFantazia(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Fornecedor.FORNECEDOR.FANTAZIA, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Fornecedor> fetchByRazao(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Fornecedor.FORNECEDOR.RAZAO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Fornecedor> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Fornecedor.FORNECEDOR.VERSION, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/FornecedorDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */