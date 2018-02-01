package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.ProdutoRecord;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class ProdutoDao
  extends DAOImpl<ProdutoRecord, br.com.pintos.jooq.tables.pojos.Produto, Long>
{
  public ProdutoDao()
  {
    super(br.com.pintos.jooq.tables.Produto.PRODUTO, br.com.pintos.jooq.tables.pojos.Produto.class);
  }
  
  public ProdutoDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Produto.PRODUTO, br.com.pintos.jooq.tables.pojos.Produto.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Produto paramProduto)
  {
    return paramProduto.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Produto fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Produto)fetchOne(br.com.pintos.jooq.tables.Produto.PRODUTO.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByBarcode(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.BARCODE, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Produto fetchOneByBarcode(String paramString)
  {
    return (br.com.pintos.jooq.tables.pojos.Produto)fetchOne(br.com.pintos.jooq.tables.Produto.PRODUTO.BARCODE, paramString);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByCodigo(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.CODIGO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByDescricao(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.DESCRICAO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByDuplicado(Byte... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.DUPLICADO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByForalinha(Byte... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.FORALINHA, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByGrade(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.GRADE, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByUsoconsumo(Byte... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.USOCONSUMO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByClId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.CL_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByFornecedorId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.FORNECEDOR_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Produto.PRODUTO.VERSION, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/ProdutoDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */