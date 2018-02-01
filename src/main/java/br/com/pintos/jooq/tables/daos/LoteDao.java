package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.LoteRecord;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class LoteDao
  extends DAOImpl<LoteRecord, br.com.pintos.jooq.tables.pojos.Lote, Long>
{
  public LoteDao()
  {
    super(br.com.pintos.jooq.tables.Lote.LOTE, br.com.pintos.jooq.tables.pojos.Lote.class);
  }
  
  public LoteDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Lote.LOTE, br.com.pintos.jooq.tables.pojos.Lote.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Lote paramLote)
  {
    return paramLote.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Lote> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Lote.LOTE.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Lote fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Lote)fetchOne(br.com.pintos.jooq.tables.Lote.LOTE.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Lote> fetchByDescricao(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Lote.LOTE.DESCRICAO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Lote> fetchByLoteavulso(Byte... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Lote.LOTE.LOTEAVULSO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Lote> fetchByNumero(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Lote.LOTE.NUMERO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Lote> fetchByLojaId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Lote.LOTE.LOJA_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Lote> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Lote.LOTE.VERSION, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/LoteDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */