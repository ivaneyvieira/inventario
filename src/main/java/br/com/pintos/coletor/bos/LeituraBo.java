package br.com.pintos.coletor.bos;

import br.com.pintos.framework.dados.AbstractBo;
import br.com.pintos.framework.dados.Execute;
import br.com.pintos.framework.dados.Query;
import br.com.pintos.framework.dados.exception.BOException;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.pojos.Coleta;
import br.com.pintos.jooq.tables.pojos.Produto;
import br.com.pintos.jooq.tables.records.LeituraRecord;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectLimitStep;
import org.jooq.SimpleSelectOffsetStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.SortField;
import org.jooq.TableField;
import org.jooq.impl.Factory;

public class LeituraBo
  extends AbstractBo<LeituraRecord, br.com.pintos.jooq.tables.pojos.Leitura>
{
  public LeituraBo()
  {
    super(Tables.LEITURA, br.com.pintos.jooq.tables.pojos.Leitura.class);
  }
  
  private br.com.pintos.jooq.tables.pojos.Leitura addLeitura(final String paramString1, final Coleta paramColeta, final int paramInt, final EStatusLeitura paramEStatusLeitura, final String paramString2)
    throws BOException
  {
    (br.com.pintos.jooq.tables.pojos.Leitura)transaction(new Query()
    {
      public br.com.pintos.jooq.tables.pojos.Leitura run(Factory paramAnonymousFactory)
        throws BOException
      {
        Produto localProduto = Facade.produto.getProduto(paramString1);
        br.com.pintos.jooq.tables.pojos.Leitura localLeitura = new br.com.pintos.jooq.tables.pojos.Leitura();
        localLeitura.setHora(LeituraBo.this.agora());
        localLeitura.setLeitura(paramString1);
        localLeitura.setObservacao(paramString2);
        localLeitura.setQuant(Integer.valueOf(paramInt));
        localLeitura.setStatus(paramEStatusLeitura.toString());
        localLeitura.setColetaId(paramColeta.getId());
        if (localProduto == null) {
          localLeitura.setProdutoId(null);
        } else {
          localLeitura.setProdutoId(localProduto.getId());
        }
        LeituraBo.this.insert(localLeitura);
        return localLeitura;
      }
    });
  }
  
  public br.com.pintos.jooq.tables.pojos.Leitura adicionaErro(String paramString1, Coleta paramColeta, String paramString2)
    throws BOException
  {
    return addLeitura(paramString1, paramColeta, 0, EStatusLeitura.ERRO, paramString2);
  }
  
  public br.com.pintos.jooq.tables.pojos.Leitura adicionaEstorno(String paramString1, Coleta paramColeta, String paramString2)
    throws BOException
  {
    return addLeitura(paramString1, paramColeta, -1, EStatusLeitura.ERRO, paramString2);
  }
  
  public br.com.pintos.jooq.tables.pojos.Leitura adicionaLeitura(String paramString, Coleta paramColeta)
    throws BOException
  {
    return addLeitura(paramString, paramColeta, 1, EStatusLeitura.SUCESSO, "");
  }
  
  public void apagaTodos(final Coleta paramColeta)
    throws BOException
  {
    transaction(new Execute()
    {
      public void run(Factory paramAnonymousFactory)
        throws BOException
      {
        List localList = LeituraBo.this.find(Tables.LEITURA.COLETA_ID.eq(paramColeta.getId()), new SortField[0]);
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          br.com.pintos.jooq.tables.pojos.Leitura localLeitura = (br.com.pintos.jooq.tables.pojos.Leitura)localIterator.next();
          LeituraBo.this.delete(localLeitura);
        }
      }
    });
  }
  
  public void apagaUltimo(Coleta paramColeta)
    throws BOException
  {
    br.com.pintos.jooq.tables.pojos.Leitura localLeitura = ultimaLeitura(paramColeta);
    delete(localLeitura);
  }
  
  public List<br.com.pintos.jooq.tables.pojos.Viewcontagensmodel> getDados(final Long paramLong1, final Long paramLong2)
  {
    (List)transaction(new Query()
    {
      protected List<br.com.pintos.jooq.tables.pojos.Viewcontagensmodel> run(Factory paramAnonymousFactory)
        throws BOException
      {
        if (paramLong2 == null) {
          return paramAnonymousFactory.selectFrom(Tables.VIEWCONTAGENSMODEL).where(new Condition[] { Tables.VIEWCONTAGENSMODEL.INVENTARIO_ID.eq(paramLong1) }).fetchInto(br.com.pintos.jooq.tables.pojos.Viewcontagensmodel.class);
        }
        return paramAnonymousFactory.selectFrom(Tables.VIEWCONTAGENSMODEL).where(new Condition[] { Tables.VIEWCONTAGENSMODEL.INVENTARIO_ID.eq(paramLong1).and(Tables.VIEWCONTAGENSMODEL.LOTE_ID.eq(paramLong2)) }).fetchInto(br.com.pintos.jooq.tables.pojos.Viewcontagensmodel.class);
      }
    });
  }
  
  public br.com.pintos.jooq.tables.pojos.Leitura ultimaLeitura(final Coleta paramColeta)
  {
    (br.com.pintos.jooq.tables.pojos.Leitura)transaction(new Query()
    {
      public br.com.pintos.jooq.tables.pojos.Leitura run(Factory paramAnonymousFactory)
      {
        List localList = paramAnonymousFactory.selectFrom(Tables.LEITURA).where(new Condition[] { Tables.LEITURA.COLETA_ID.eq(paramColeta.getId()) }).orderBy(new SortField[] { Tables.LEITURA.ID.desc() }).limit(1).fetchInto(br.com.pintos.jooq.tables.pojos.Leitura.class);
        if (localList.size() > 0) {
          return (br.com.pintos.jooq.tables.pojos.Leitura)localList.get(0);
        }
        return null;
      }
    });
  }
  
  public void validaLeitura(String paramString, Coleta paramColeta)
  {
    String str = "";
    Produto localProduto = Facade.produto.getProduto(paramString);
    if (localProduto == null) {
      str = "Produto nao encontrado";
    } else if (localProduto.getUsoconsumo().byteValue() == 1) {
      str = "Produto uso e consumo";
    } else if (localProduto.getDuplicado().byteValue() == 1) {
      str = "Grade duplicada";
    } else if (localProduto.getForalinha().byteValue() == 1) {
      str = "Produto fora de linha";
    } else if (!Facade.inventario.validaFornecedor(paramColeta.getInventarioId().longValue(), localProduto)) {
      str = "Fornecedor invalido";
    } else if (!Facade.inventario.validaCl(paramColeta.getInventarioId().longValue(), localProduto)) {
      str = "Centro de lucro invalido";
    }
    if (!str.equals("")) {
      throw new BOException(str);
    }
  }
  
  public static enum EStatusLeitura
  {
    SUCESSO,  ERRO,  ESTORNO;
    
    private EStatusLeitura() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/bos/LeituraBo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */