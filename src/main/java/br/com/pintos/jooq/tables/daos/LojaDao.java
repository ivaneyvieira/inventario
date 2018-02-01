package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.LojaRecord;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class LojaDao
  extends DAOImpl<LojaRecord, br.com.pintos.jooq.tables.pojos.Loja, Long>
{
  public LojaDao()
  {
    super(br.com.pintos.jooq.tables.Loja.LOJA, br.com.pintos.jooq.tables.pojos.Loja.class);
  }
  
  public LojaDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Loja.LOJA, br.com.pintos.jooq.tables.pojos.Loja.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Loja paramLoja)
  {
    return paramLoja.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Loja> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Loja.LOJA.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Loja fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Loja)fetchOne(br.com.pintos.jooq.tables.Loja.LOJA.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Loja> fetchByEndereco(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Loja.LOJA.ENDERECO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Loja> fetchByNome(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Loja.LOJA.NOME, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Loja> fetchBySigla(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Loja.LOJA.SIGLA, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Loja> fetchByStoreno(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Loja.LOJA.STORENO, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Loja fetchOneByStoreno(Integer paramInteger)
  {
    return (br.com.pintos.jooq.tables.pojos.Loja)fetchOne(br.com.pintos.jooq.tables.Loja.LOJA.STORENO, paramInteger);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Loja> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Loja.LOJA.VERSION, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/LojaDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */