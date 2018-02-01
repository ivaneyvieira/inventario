package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.LeituraRecord;
import java.sql.Time;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class LeituraDao
  extends DAOImpl<LeituraRecord, br.com.pintos.jooq.tables.pojos.Leitura, Long>
{
  public LeituraDao()
  {
    super(br.com.pintos.jooq.tables.Leitura.LEITURA, br.com.pintos.jooq.tables.pojos.Leitura.class);
  }
  
  public LeituraDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Leitura.LEITURA, br.com.pintos.jooq.tables.pojos.Leitura.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Leitura paramLeitura)
  {
    return paramLeitura.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Leitura> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Leitura.LEITURA.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Leitura fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Leitura)fetchOne(br.com.pintos.jooq.tables.Leitura.LEITURA.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Leitura> fetchByHora(Time... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Leitura.LEITURA.HORA, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Leitura> fetchByLeitura(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Leitura.LEITURA.LEITURA_, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Leitura> fetchByObservacao(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Leitura.LEITURA.OBSERVACAO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Leitura> fetchByQuant(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Leitura.LEITURA.QUANT, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Leitura> fetchByStatus(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Leitura.LEITURA.STATUS, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Leitura> fetchByColetaId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Leitura.LEITURA.COLETA_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Leitura> fetchByProdutoId(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Leitura.LEITURA.PRODUTO_ID, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Leitura> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Leitura.LEITURA.VERSION, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/LeituraDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */