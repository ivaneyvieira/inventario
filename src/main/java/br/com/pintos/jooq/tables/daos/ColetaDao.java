package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.ColetaRecord;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class ColetaDao
  extends DAOImpl<ColetaRecord, br.com.pintos.jooq.tables.pojos.Coleta, Long>
{
  public ColetaDao()
  {
    super(br.com.pintos.jooq.tables.Coleta.COLETA, br.com.pintos.jooq.tables.pojos.Coleta.class);
  }
  
  public ColetaDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Coleta.COLETA, br.com.pintos.jooq.tables.pojos.Coleta.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Coleta paramColeta)
  {
    return paramColeta.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Coleta> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Coleta.COLETA.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Coleta fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Coleta)fetchOne(br.com.pintos.jooq.tables.Coleta.COLETA.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Coleta> fetchByNumleitura(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Coleta.COLETA.NUMLEITURA, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Coleta> fetchByInventarioId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Coleta.COLETA.INVENTARIO_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Coleta> fetchByLoteId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Coleta.COLETA.LOTE_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Coleta> fetchByUsuarioId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Coleta.COLETA.USUARIO_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Coleta> fetchByColetor(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Coleta.COLETA.COLETOR, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Coleta> fetchByStatus(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Coleta.COLETA.STATUS, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Coleta> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Coleta.COLETA.VERSION, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/ColetaDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */