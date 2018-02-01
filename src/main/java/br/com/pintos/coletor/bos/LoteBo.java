package br.com.pintos.coletor.bos;

import br.com.pintos.framework.dados.AbstractBo;
import br.com.pintos.framework.dados.Query;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.Leitura;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.records.LoteRecord;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectLimitStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnConditionStep;
import org.jooq.TableOnStep;
import org.jooq.impl.Factory;

public class LoteBo
  extends AbstractBo<LoteRecord, br.com.pintos.jooq.tables.pojos.Lote>
{
  public LoteBo()
  {
    super(Tables.LOTE, br.com.pintos.jooq.tables.pojos.Lote.class);
  }
  
  public br.com.pintos.jooq.tables.pojos.Lote findLote(final Long paramLong, final Integer paramInteger)
  {
    (br.com.pintos.jooq.tables.pojos.Lote)transaction(new Query()
    {
      public br.com.pintos.jooq.tables.pojos.Lote run(Factory paramAnonymousFactory)
      {
        SelectConditionStep localSelectConditionStep = paramAnonymousFactory.select(new Field[0]).from(new TableLike[] { Tables.LOTE }).where(new Condition[] { Tables.LOTE.LOJA_ID.eq(paramLong).and(Tables.LOTE.NUMERO.eq(paramInteger)) });
        List localList = localSelectConditionStep.fetchInto(br.com.pintos.jooq.tables.pojos.Lote.class);
        if (localList.size() > 0) {
          return (br.com.pintos.jooq.tables.pojos.Lote)localList.get(0);
        }
        return null;
      }
    });
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Viewlotes> getLotes(final Inventario paramInventario)
  {
    (List)transaction(new Query()
    {
      protected List<br.com.pintos.jooq.tables.pojos.Viewlotes> run(Factory paramAnonymousFactory)
        throws BOException
      {
        Inventario localInventario = paramInventario;
        Long localLong = localInventario.getId();
        return paramAnonymousFactory.selectFrom(Tables.VIEWLOTES).where(new Condition[] { Tables.VIEWLOTES.INVENTARIO_ID.eq(localLong) }).orderBy(new Field[] { Tables.VIEWLOTES.NUMLOTE }).fetchInto(br.com.pintos.jooq.tables.pojos.Viewlotes.class);
      }
    });
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Produto> getProdutos(final br.com.pintos.jooq.tables.pojos.Lote paramLote, final Inventario paramInventario)
  {
    (List)transaction(new Query()
    {
      protected List<br.com.pintos.jooq.tables.pojos.Produto> run(Factory paramAnonymousFactory)
        throws BOException
      {
        return paramAnonymousFactory.selectDistinct(Tables.PRODUTO.getFields()).from(new TableLike[] { Tables.COLETA.join(Tables.LEITURA).on(new Condition[] { Tables.COLETA.ID.eq(Tables.LEITURA.COLETA_ID) }).join(Tables.PRODUTO).on(new Condition[] { Tables.PRODUTO.ID.eq(Tables.LEITURA.PRODUTO_ID) }) }).where(new Condition[] { Tables.COLETA.LOTE_ID.eq(paramLote.getId()).and(Tables.COLETA.INVENTARIO_ID.eq(paramInventario.getId())) }).fetchInto(br.com.pintos.jooq.tables.pojos.Produto.class);
      }
    });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/bos/LoteBo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */