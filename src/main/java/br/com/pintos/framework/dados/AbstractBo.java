package br.com.pintos.framework.dados;

import br.com.pintos.framework.dados.exception.ErroInterno;
import br.com.pintos.framework.dados.exception.ValidaFieldNotNull;
import br.com.pintos.framework.util.Beans;
import br.com.pintos.framework.util.Util;
import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitStep;
import org.jooq.SelectOnStep;
import org.jooq.SelectSelectStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.UpdatableRecord;
import org.jooq.UpdatableTable;
import org.jooq.conf.Settings;
import org.jooq.impl.Factory;

public abstract class AbstractBo<R extends UpdatableRecord<R>, P>
{
  protected final UpdatableTable<R> table;
  private final Class<P> pojoClass;
  
  public AbstractBo(UpdatableTable<R> paramUpdatableTable, Class<P> paramClass)
  {
    this.table = paramUpdatableTable;
    this.pojoClass = paramClass;
  }
  
  protected Time agora()
  {
    long l = new java.util.Date().getTime();
    return new Time(l);
  }
  
  public Factory createFactory(Connection paramConnection)
  {
    Settings localSettings = new Settings();
    localSettings.setExecuteLogging(Boolean.valueOf(true));
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("br.com.pintos.framework.dados.PrettyPrinter");
    localSettings.setExecuteListeners(localArrayList);
    Factory localFactory = new Factory(paramConnection, SQLDialect.MYSQL, localSettings);
    return localFactory;
  }
  
  protected java.sql.Date date(java.util.Date paramDate)
  {
    return new java.sql.Date(paramDate.getTime());
  }
  
  public Integer delete(final P paramP)
  {
    (Integer)transaction(new Query()
    {
      public Integer run(Factory paramAnonymousFactory)
      {
        AbstractBo.this.validaDelete(paramP);
        UpdatableRecord localUpdatableRecord = (UpdatableRecord)paramAnonymousFactory.newRecord(AbstractBo.this.table, paramP);
        return Integer.valueOf(paramAnonymousFactory.executeDelete(localUpdatableRecord));
      }
    });
  }
  
  public void executeQuery(Connection paramConnection, Execute paramExecute)
  {
    Factory localFactory = createFactory(paramConnection);
    paramExecute.run(localFactory);
  }
  
  public <A> A executeQuery(Connection paramConnection, Query<A> paramQuery)
  {
    Factory localFactory = createFactory(paramConnection);
    return (A)paramQuery.run(localFactory);
  }
  
  public List<P> find(Condition paramCondition, SortField<?>... paramVarArgs)
  {
    ResultQuery localResultQuery = select(paramCondition, paramVarArgs);
    return localResultQuery.fetchInto(this.pojoClass);
  }
  
  public List<P> findAll()
  {
    (List)transaction(new Query()
    {
      public List<P> run(Factory paramAnonymousFactory)
      {
        return paramAnonymousFactory.selectFrom(AbstractBo.this.table).fetchInto(AbstractBo.this.pojoClass);
      }
    });
  }
  
  public P findById(final Long paramLong)
  {
    if (paramLong == null) {
      return null;
    }
    (P)transaction(new Query()
    {
      public P run(Factory paramAnonymousFactory)
      {
        Field localField = AbstractBo.this.table.getField("id");
        UpdatableRecord localUpdatableRecord = (UpdatableRecord)paramAnonymousFactory.fetchOne(AbstractBo.this.table, localField.eq(paramLong));
        Object localObject = localUpdatableRecord.into(AbstractBo.this.pojoClass);
        return (P)localObject;
      }
    });
  }
  
  public <A> List<A> getRelation(final Class<A> paramClass, final Table<?> paramTable)
  {
    (List)transaction(new Query()
    {
      protected List<A> run(Factory paramAnonymousFactory)
      {
        return paramAnonymousFactory.select(paramTable.getFields()).from(new TableLike[] { AbstractBo.this.table }).join(paramTable).onKey().fetchInto(paramClass);
      }
    });
  }
  
  public Integer insert(final P paramP)
  {
    (Integer)transaction(new Query()
    {
      public Integer run(Factory paramAnonymousFactory)
      {
        AbstractBo.this.validaInsert(paramP);
        UpdatableRecord localUpdatableRecord = (UpdatableRecord)paramAnonymousFactory.newRecord(AbstractBo.this.table, paramP);
        Integer localInteger = Integer.valueOf(localUpdatableRecord.store());
        Object localObject = localUpdatableRecord.getValue("id");
        Util.bean.set(paramP, "id", localObject);
        return localInteger;
      }
    });
  }
  
  private ResultQuery<R> select(final Condition paramCondition, final SortField<?>... paramVarArgs)
  {
    ResultQuery localResultQuery = (ResultQuery)transaction(new Query()
    {
      public ResultQuery<R> run(Factory paramAnonymousFactory)
      {
        SelectLimitStep localSelectLimitStep = paramAnonymousFactory.select(new Field[0]).from(new TableLike[] { AbstractBo.this.table }).where(new Condition[] { paramCondition }).orderBy(paramVarArgs);
        return localSelectLimitStep;
      }
    });
    return localResultQuery;
  }
  
  protected void transaction(final Execute paramExecute)
  {
    Database.instance.transaction(new BlocoExecute()
    {
      public void execute(Connection paramAnonymousConnection)
      {
        AbstractBo.this.executeQuery(paramAnonymousConnection, paramExecute);
      }
    });
  }
  
  protected <A> A transaction(final Query<A> paramQuery)
  {
    (A)Database.instance.transaction(new BlocoQuery()
    {
      public A query(Connection paramAnonymousConnection)
      {
        return (A)AbstractBo.this.executeQuery(paramAnonymousConnection, paramQuery);
      }
    });
  }
  
  public Integer update(final P paramP)
  {
    (Integer)transaction(new Query()
    {
      public Integer run(Factory paramAnonymousFactory)
      {
        AbstractBo.this.validaUpdate(paramP);
        UpdatableRecord localUpdatableRecord = (UpdatableRecord)paramAnonymousFactory.newRecord(AbstractBo.this.table, paramP);
        return Integer.valueOf(paramAnonymousFactory.executeUpdate(localUpdatableRecord));
      }
    });
  }
  
  public void validaDelete(P paramP)
  {
    validaPojo(paramP);
  }
  
  public void validaInsert(P paramP)
  {
    validaPojo(paramP);
  }
  
  protected void validaNulo(P paramP, String paramString, Object paramObject)
  {
    if (paramObject == null) {
      throw new ValidaFieldNotNull(paramP, paramString, paramObject);
    }
    if ((paramObject instanceof String))
    {
      String str = (String)paramObject;
      if (str.trim().length() == 0) {
        throw new ValidaFieldNotNull(paramP, paramString, paramObject);
      }
    }
  }
  
  public void validaPojo(P paramP)
  {
    if (paramP == null) {
      throw new ErroInterno("Bean com valor nulo");
    }
  }
  
  public void validaUpdate(P paramP)
  {
    validaPojo(paramP);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/dados/AbstractBo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */