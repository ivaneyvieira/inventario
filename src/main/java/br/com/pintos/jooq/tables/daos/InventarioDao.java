package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.InventarioRecord;
import java.sql.Date;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class InventarioDao
  extends DAOImpl<InventarioRecord, br.com.pintos.jooq.tables.pojos.Inventario, Long>
{
  public InventarioDao()
  {
    super(br.com.pintos.jooq.tables.Inventario.INVENTARIO, br.com.pintos.jooq.tables.pojos.Inventario.class);
  }
  
  public InventarioDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Inventario.INVENTARIO, br.com.pintos.jooq.tables.pojos.Inventario.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Inventario paramInventario)
  {
    return paramInventario.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Inventario fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Inventario)fetchOne(br.com.pintos.jooq.tables.Inventario.INVENTARIO.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchByNumero(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.NUMERO, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Inventario fetchOneByNumero(Integer paramInteger)
  {
    return (br.com.pintos.jooq.tables.pojos.Inventario)fetchOne(br.com.pintos.jooq.tables.Inventario.INVENTARIO.NUMERO, paramInteger);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchByData(Date... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.DATA, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchByObservacao(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.OBSERVACAO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchByTipoinventario(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.TIPOINVENTARIO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchByStatusinventario(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.STATUSINVENTARIO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchByLojaId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.LOJA_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.VERSION, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchByFornecedorId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.FORNECEDOR_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> fetchByClId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Inventario.INVENTARIO.CL_ID, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/InventarioDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */