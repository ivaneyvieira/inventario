package br.com.pintos.coletor.bos;

import br.com.pintos.framework.dados.AbstractBo;
import br.com.pintos.framework.dados.Database;
import br.com.pintos.framework.dados.Execute;
import br.com.pintos.framework.dados.Query;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.framework.dados.exception.ValidaKeyDuplicate;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.pojos.Cl;
import br.com.pintos.jooq.tables.pojos.Loja;
import br.com.pintos.jooq.tables.pojos.Produto;
import br.com.pintos.jooq.tables.records.InventarioRecord;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitStep;
import org.jooq.SelectSelectStep;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.SortField;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnStep;
import org.jooq.impl.Factory;

public class InventarioBO
  extends AbstractBo<InventarioRecord, br.com.pintos.jooq.tables.pojos.Inventario>
{
  public InventarioBO()
  {
    super(Tables.INVENTARIO, br.com.pintos.jooq.tables.pojos.Inventario.class);
  }
  
  public Date dataIncial()
    throws BOException
  {
    (Date)transaction(new Query()
    {
      public Date run(Factory paramAnonymousFactory)
      {
        return (Date)paramAnonymousFactory.select(new Field[] { Factory.min(Tables.INVENTARIO.DATA) }).from(new TableLike[] { Tables.INVENTARIO }).fetchOne(0, Date.class);
      }
    });
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> filtro(final Date paramDate1, final Date paramDate2, final Loja paramLoja)
    throws BOException
  {
    (List)transaction(new Query()
    {
      public List<br.com.pintos.jooq.tables.pojos.Inventario> run(Factory paramAnonymousFactory)
      {
        SelectConditionStep localSelectConditionStep = paramAnonymousFactory.select(new Field[0]).from(new TableLike[] { Tables.INVENTARIO }).where(new Condition[] { Tables.INVENTARIO.DATA.between(InventarioBO.this.date(paramDate1), InventarioBO.this.date(paramDate2)).and(Tables.INVENTARIO.LOJA_ID.equal(paramLoja.getId())) });
        List localList = localSelectConditionStep.fetchInto(br.com.pintos.jooq.tables.pojos.Inventario.class);
        return localList;
      }
    });
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Inventario> findAberto()
  {
    (List)transaction(new Query()
    {
      protected List<br.com.pintos.jooq.tables.pojos.Inventario> run(Factory paramAnonymousFactory)
        throws BOException
      {
        return paramAnonymousFactory.selectFrom(Tables.INVENTARIO).where(new Condition[] { Tables.INVENTARIO.STATUSINVENTARIO.eq(
            Status.ABERTO.toString()) }).fetchInto(br.com.pintos.jooq.tables.pojos.Inventario.class);
      }
    });
  }
  
  public br.com.pintos.jooq.tables.pojos.Inventario findByNum(final Integer paramInteger)
  {
    (br.com.pintos.jooq.tables.pojos.Inventario)transaction(new Query()
    {
      protected br.com.pintos.jooq.tables.pojos.Inventario run(Factory paramAnonymousFactory)
        throws BOException
      {
        List localList = paramAnonymousFactory.selectFrom(Tables.INVENTARIO).where(new Condition[] { Tables.INVENTARIO.NUMERO.eq(paramInteger) }).fetchInto(br.com.pintos.jooq.tables.pojos.Inventario.class);
        if (localList.size() > 0) {
          return (br.com.pintos.jooq.tables.pojos.Inventario)localList.get(0);
        }
        return null;
      }
    });
  }
  
  public void geraEstoque(final RunTask paramRunTask)
  {
    final List localList = Database.instance.execScript("/sql/dadossaci.sql");
    transaction(new Execute()
    {
      protected void run(Factory paramAnonymousFactory)
      {
        int i = localList.size();
        int j = 0;
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          System.out.print(str);
          paramAnonymousFactory.execute(str);
          j += 1;
          int k = j * 100 / i;
          paramRunTask.run(k);
        }
      }
    });
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Lote> getLotes(final br.com.pintos.jooq.tables.pojos.Inventario paramInventario)
  {
    (List)transaction(new Query()
    {
      protected List<br.com.pintos.jooq.tables.pojos.Lote> run(Factory paramAnonymousFactory)
      {
        return paramAnonymousFactory.selectDistinct(Tables.LOTE.getFields()).from(new TableLike[] { Tables.COLETA.join(Tables.LOTE).on(new Condition[] { Tables.COLETA.LOTE_ID.eq(Tables.LOTE.ID) }) }).where(new Condition[] { Tables.COLETA.INVENTARIO_ID.eq(paramInventario.getId()) }).fetchInto(br.com.pintos.jooq.tables.pojos.Lote.class);
      }
    });
  }
  
  public br.com.pintos.jooq.tables.pojos.Inventario inventarioAtual()
    throws BOException
  {
    br.com.pintos.jooq.tables.pojos.Inventario localInventario = (br.com.pintos.jooq.tables.pojos.Inventario)transaction(new Query()
    {
      public br.com.pintos.jooq.tables.pojos.Inventario run(Factory paramAnonymousFactory)
      {
        String str = Status.ABERTO.toString();
        SelectLimitStep localSelectLimitStep = paramAnonymousFactory.select(new Field[0]).from(new TableLike[] { Tables.INVENTARIO }).where(new Condition[] { Tables.INVENTARIO.STATUSINVENTARIO.eq(str) }).orderBy(new SortField[] { Tables.INVENTARIO.DATA.desc() });
        List localList = localSelectLimitStep.fetchInto(br.com.pintos.jooq.tables.pojos.Inventario.class);
        if (localList.size() > 0) {
          return (br.com.pintos.jooq.tables.pojos.Inventario)localList.get(0);
        }
        return null;
      }
    });
    if (localInventario == null) {
      throw new BOException("Nao ha nenhum inventario configurado");
    }
    return localInventario;
  }
  
  public Integer proximoNumero()
  {
    (Integer)transaction(new Query()
    {
      protected Integer run(Factory paramAnonymousFactory)
        throws BOException
      {
        Integer localInteger = (Integer)paramAnonymousFactory.select(new Field[] { Factory.max(Tables.INVENTARIO.NUMERO) }).from(new TableLike[] { Tables.INVENTARIO }).fetchOne(0, Integer.class);
        return Integer.valueOf(localInteger.intValue() + 1);
      }
    });
  }
  
  public boolean validaCl(final long paramLong, Produto paramProduto)
  {
    ((Boolean)transaction(new Query()
    {
      public Boolean run(Factory paramAnonymousFactory)
        throws BOException
      {
        br.com.pintos.jooq.tables.pojos.Inventario localInventario = (br.com.pintos.jooq.tables.pojos.Inventario)InventarioBO.this.findById(Long.valueOf(paramLong));
        if (localInventario.getClId() == null) {
          return Boolean.valueOf(true);
        }
        Cl localCl1 = (Cl)Facade.cl.findById(localInventario.getClId());
        List localList = Facade.cl.findClCompativeis(localCl1);
        Cl localCl2 = (Cl)Facade.cl.findById(this.val$produto.getClId());
        return Boolean.valueOf(localList.contains(localCl2));
      }
    })).booleanValue();
  }
  
  public boolean validaFornecedor(final long paramLong, Produto paramProduto)
  {
    ((Boolean)transaction(new Query()
    {
      public Boolean run(Factory paramAnonymousFactory)
        throws BOException
      {
        br.com.pintos.jooq.tables.pojos.Inventario localInventario = (br.com.pintos.jooq.tables.pojos.Inventario)InventarioBO.this.findById(Long.valueOf(paramLong));
        if (localInventario.getFornecedorId() == null) {
          return Boolean.valueOf(true);
        }
        return Boolean.valueOf(this.val$produto.getFornecedorId().equals(localInventario.getFornecedorId()));
      }
    })).booleanValue();
  }
  
  public void validaInsert(br.com.pintos.jooq.tables.pojos.Inventario paramInventario)
    throws BOException
  {
    super.validaInsert(paramInventario);
    validaUpdate(paramInventario);
    br.com.pintos.jooq.tables.pojos.Inventario localInventario = findByNum(paramInventario.getNumero());
    if (localInventario != null) {
      throw new ValidaKeyDuplicate(paramInventario, "Numero", paramInventario.getNumero());
    }
  }
  
  public void validaUpdate(br.com.pintos.jooq.tables.pojos.Inventario paramInventario)
    throws BOException
  {
    super.validaUpdate(paramInventario);
    validaNulo(paramInventario, "Numero", paramInventario.getNumero());
    validaNulo(paramInventario, "Data", paramInventario.getData());
    validaNulo(paramInventario, "Observação", paramInventario.getObservacao());
    validaNulo(paramInventario, "Tipo", paramInventario.getTipoinventario());
    validaNulo(paramInventario, "Status", paramInventario.getStatusinventario());
    validaNulo(paramInventario, "Loja", paramInventario.getLojaId());
  }
  
  public static abstract enum Tipo
  {
    SIMPLES,  DIVERGENCIA;
    
    private Tipo() {}
    
    public abstract String toString();
  }
  
  public static abstract enum Status
  {
    ABERTO,  FECHADA;
    
    private Status() {}
    
    public abstract String toString();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/bos/InventarioBO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */