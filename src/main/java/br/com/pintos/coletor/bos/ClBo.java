package br.com.pintos.coletor.bos;

import br.com.pintos.framework.dados.AbstractBo;
import br.com.pintos.framework.dados.Query;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.daos.ClDao;
import br.com.pintos.jooq.tables.records.ClRecord;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.SortField;
import org.jooq.TableField;
import org.jooq.impl.Factory;

public class ClBo
  extends AbstractBo<ClRecord, br.com.pintos.jooq.tables.pojos.Cl>
{
  public ClBo()
  {
    super(Tables.CL, br.com.pintos.jooq.tables.pojos.Cl.class);
  }
  
  public br.com.pintos.jooq.tables.pojos.Cl findCl(final String paramString)
  {
    try
    {
      (br.com.pintos.jooq.tables.pojos.Cl)transaction(new Query()
      {
        protected br.com.pintos.jooq.tables.pojos.Cl run(Factory paramAnonymousFactory)
          throws BOException
        {
          int i = Integer.valueOf(paramString).intValue();
          List localList = paramAnonymousFactory.selectFrom(Tables.CL).where(new Condition[] { Tables.CL.CLNO.eq(Integer.valueOf(i)) }).fetchInto(br.com.pintos.jooq.tables.pojos.Cl.class);
          if (localList.size() > 0) {
            return (br.com.pintos.jooq.tables.pojos.Cl)localList.get(0);
          }
          return null;
        }
      });
    }
    catch (BOException localBOException) {}
    return null;
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Cl> findClCompativeis(br.com.pintos.jooq.tables.pojos.Cl paramCl)
  {
    String str = paramCl.getClno().toString();
    if (str.endsWith("0000")) {
      str = str.substring(0, 2) + "%";
    } else if (str.endsWith("00")) {
      str = str.substring(0, 4) + "%";
    }
    return find(Tables.CL.CLNO.like(str), new SortField[0]);
  }
  
  public br.com.pintos.jooq.tables.pojos.Cl getCl(final Integer paramInteger)
  {
    try
    {
      (br.com.pintos.jooq.tables.pojos.Cl)transaction(new Query()
      {
        public br.com.pintos.jooq.tables.pojos.Cl run(Factory paramAnonymousFactory)
        {
          ClDao localClDao = new ClDao(paramAnonymousFactory);
          return localClDao.fetchOneByClno(paramInteger);
        }
      });
    }
    catch (BOException localBOException) {}
    return null;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/bos/ClBo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */