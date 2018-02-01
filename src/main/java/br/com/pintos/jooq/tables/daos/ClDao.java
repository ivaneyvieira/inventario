package br.com.pintos.jooq.tables.daos;

import br.com.pintos.jooq.tables.records.ClRecord;
import java.util.List;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.Factory;

public class ClDao
  extends DAOImpl<ClRecord, br.com.pintos.jooq.tables.pojos.Cl, Long>
{
  public ClDao()
  {
    super(br.com.pintos.jooq.tables.Cl.CL, br.com.pintos.jooq.tables.pojos.Cl.class);
  }
  
  public ClDao(Factory paramFactory)
  {
    super(br.com.pintos.jooq.tables.Cl.CL, br.com.pintos.jooq.tables.pojos.Cl.class, paramFactory);
  }
  
  protected Long getId(br.com.pintos.jooq.tables.pojos.Cl paramCl)
  {
    return paramCl.getId();
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Cl> fetchById(Long... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Cl.CL.ID, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Cl fetchOneById(Long paramLong)
  {
    return (br.com.pintos.jooq.tables.pojos.Cl)fetchOne(br.com.pintos.jooq.tables.Cl.CL.ID, paramLong);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Cl> fetchByClno(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Cl.CL.CLNO, paramVarArgs);
  }
  
  public br.com.pintos.jooq.tables.pojos.Cl fetchOneByClno(Integer paramInteger)
  {
    return (br.com.pintos.jooq.tables.pojos.Cl)fetchOne(br.com.pintos.jooq.tables.Cl.CL.CLNO, paramInteger);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Cl> fetchByDepartamento(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Cl.CL.DEPARTAMENTO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Cl> fetchByGrupo(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Cl.CL.GRUPO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Cl> fetchBySecao(String... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Cl.CL.SECAO, paramVarArgs);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Cl> fetchByVersion(Integer... paramVarArgs)
  {
    return fetch(br.com.pintos.jooq.tables.Cl.CL.VERSION, paramVarArgs);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/daos/ClDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */