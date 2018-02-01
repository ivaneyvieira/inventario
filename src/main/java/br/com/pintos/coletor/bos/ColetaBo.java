package br.com.pintos.coletor.bos;

import br.com.pintos.framework.dados.AbstractBo;
import br.com.pintos.framework.dados.Query;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.Leitura;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.pojos.Lote;
import br.com.pintos.jooq.tables.pojos.Usuario;
import br.com.pintos.jooq.tables.records.ColetaRecord;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.impl.Factory;

public class ColetaBo
  extends AbstractBo<ColetaRecord, br.com.pintos.jooq.tables.pojos.Coleta>
{
  public ColetaBo()
  {
    super(Tables.COLETA, br.com.pintos.jooq.tables.pojos.Coleta.class);
  }
  
  public Integer contaColeta(final br.com.pintos.jooq.tables.pojos.Coleta paramColeta)
    throws BOException
  {
    Integer localInteger = (Integer)transaction(new Query()
    {
      public Integer run(Factory paramAnonymousFactory)
      {
        Integer localInteger = (Integer)paramAnonymousFactory.select(new Field[] { Factory.sum(Tables.LEITURA.QUANT) }).from(new TableLike[] { Tables.LEITURA }).where(new Condition[] { Tables.LEITURA.COLETA_ID.eq(paramColeta.getId()) }).fetchOne(0, Integer.class);
        return localInteger;
      }
    });
    if (localInteger == null) {
      return Integer.valueOf(0);
    }
    return localInteger;
  }
  
  public br.com.pintos.jooq.tables.pojos.Coleta createColeta(Inventario paramInventario, Usuario paramUsuario, Lote paramLote, Integer paramInteger)
    throws BOException
  {
    if ((paramInventario == null) || (paramUsuario == null) || (paramLote == null)) {
      return null;
    }
    Long localLong1 = paramInventario.getId();
    Long localLong2 = paramLote.getId();
    Long localLong3 = paramUsuario.getId();
    br.com.pintos.jooq.tables.pojos.Coleta localColeta = novaColeta(localLong1, localLong3, localLong2, paramInteger);
    return localColeta;
  }
  
  public void fechaColeta(br.com.pintos.jooq.tables.pojos.Coleta paramColeta)
    throws BOException
  {
    if (paramColeta != null)
    {
      Integer localInteger = contaColeta(paramColeta);
      if (localInteger.intValue() == 0)
      {
        delete(paramColeta);
      }
      else
      {
        paramColeta.setStatus(Status.FECHADO.toString());
        update(paramColeta);
      }
    }
  }
  
  public br.com.pintos.jooq.tables.pojos.Coleta findColeta(final Inventario paramInventario, final Usuario paramUsuario)
  {
    if ((paramInventario == null) || (paramUsuario == null)) {
      return null;
    }
    List localList = (List)transaction(new Query()
    {
      public List<br.com.pintos.jooq.tables.pojos.Coleta> run(Factory paramAnonymousFactory)
      {
        return paramAnonymousFactory.selectFrom(Tables.COLETA).where(new Condition[] { Tables.COLETA.INVENTARIO_ID.eq(paramInventario.getId()).and(Tables.COLETA.USUARIO_ID.eq(paramUsuario.getId()).and(Tables.COLETA.STATUS.eq(
            Status.ABERTO.toString()))) }).fetchInto(br.com.pintos.jooq.tables.pojos.Coleta.class);
      }
    });
    if (localList.size() > 0) {
      return (br.com.pintos.jooq.tables.pojos.Coleta)localList.get(0);
    }
    return null;
  }
  
  public br.com.pintos.jooq.tables.pojos.Coleta findColeta(final Inventario paramInventario, final Usuario paramUsuario, final Lote paramLote)
    throws BOException
  {
    if ((paramInventario == null) || (paramUsuario == null)) {
      return null;
    }
    List localList = (List)transaction(new Query()
    {
      public List<br.com.pintos.jooq.tables.pojos.Coleta> run(Factory paramAnonymousFactory)
      {
        return paramAnonymousFactory.selectFrom(Tables.COLETA).where(new Condition[] { Tables.COLETA.INVENTARIO_ID.eq(paramInventario.getId()).and(Tables.COLETA.USUARIO_ID.eq(paramUsuario.getId()).and(Tables.COLETA.STATUS.eq(
            Status.ABERTO.toString()).and(Tables.COLETA.LOTE_ID.eq(paramLote.getId())))) }).fetchInto(br.com.pintos.jooq.tables.pojos.Coleta.class);
      }
    });
    if (localList.size() > 0) {
      return (br.com.pintos.jooq.tables.pojos.Coleta)localList.get(0);
    }
    return null;
  }
  
  public br.com.pintos.jooq.tables.pojos.Coleta findColetaAberta(Inventario paramInventario, Usuario paramUsuario, Lote paramLote)
    throws BOException
  {
    if ((paramInventario == null) || (paramUsuario == null) || (paramLote == null)) {
      return null;
    }
    br.com.pintos.jooq.tables.pojos.Coleta localColeta = findColeta(paramInventario, paramUsuario, paramLote);
    return localColeta;
  }
  
  private Integer maxNumero(final Long paramLong1, final Long paramLong2)
    throws BOException
  {
    Integer localInteger = (Integer)transaction(new Query()
    {
      public Integer run(Factory paramAnonymousFactory)
      {
        return (Integer)paramAnonymousFactory.select(new Field[] { Factory.max(Tables.COLETA.NUMLEITURA) }).from(new TableLike[] { Tables.COLETA }).where(new Condition[] { Tables.COLETA.INVENTARIO_ID.eq(paramLong1).and(Tables.COLETA.LOTE_ID.eq(paramLong2)) }).fetchOne(0, Integer.class);
      }
    });
    if (localInteger == null) {
      return Integer.valueOf(0);
    }
    return localInteger;
  }
  
  public br.com.pintos.jooq.tables.pojos.Coleta mudaLote(final br.com.pintos.jooq.tables.pojos.Coleta paramColeta, final Lote paramLote, final Inventario paramInventario, final Usuario paramUsuario, final Integer paramInteger)
    throws BOException
  {
    (br.com.pintos.jooq.tables.pojos.Coleta)transaction(new Query()
    {
      public br.com.pintos.jooq.tables.pojos.Coleta run(Factory paramAnonymousFactory)
        throws BOException
      {
        ColetaBo.this.fechaColeta(paramColeta);
        br.com.pintos.jooq.tables.pojos.Coleta localColeta1 = ColetaBo.this.findColetaAberta(paramInventario, paramUsuario, paramLote);
        if (localColeta1 != null) {
          return localColeta1;
        }
        br.com.pintos.jooq.tables.pojos.Coleta localColeta2 = ColetaBo.this.createColeta(paramInventario, paramUsuario, paramLote, paramInteger);
        return localColeta2;
      }
    });
  }
  
  private br.com.pintos.jooq.tables.pojos.Coleta novaColeta(Long paramLong1, Long paramLong2, Long paramLong3, Integer paramInteger)
    throws BOException
  {
    Integer localInteger = maxNumero(paramLong1, paramLong3);
    br.com.pintos.jooq.tables.pojos.Coleta localColeta = new br.com.pintos.jooq.tables.pojos.Coleta();
    localColeta.setNumleitura(Integer.valueOf(localInteger.intValue() + 1));
    localColeta.setInventarioId(paramLong1);
    localColeta.setLoteId(paramLong3);
    localColeta.setUsuarioId(paramLong2);
    localColeta.setStatus(Status.ABERTO.toString());
    localColeta.setColetor(paramInteger);
    insert(localColeta);
    return localColeta;
  }
  
  public static enum Status
  {
    ABERTO,  FECHADO;
    
    private Status() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/bos/ColetaBo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */