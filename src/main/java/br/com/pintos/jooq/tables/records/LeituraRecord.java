package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.Leitura;
import br.com.pintos.jooq.tables.Produto;
import br.com.pintos.jooq.tables.interfaces.ILeitura;
import java.sql.Time;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class LeituraRecord
  extends UpdatableRecordImpl<LeituraRecord>
  implements ILeitura
{
  private static final long serialVersionUID = -1966541391L;
  
  public void setId(Long paramLong)
  {
    setValue(Leitura.LEITURA.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Leitura.LEITURA.ID);
  }
  
  public void setHora(Time paramTime)
  {
    setValue(Leitura.LEITURA.HORA, paramTime);
  }
  
  public Time getHora()
  {
    return (Time)getValue(Leitura.LEITURA.HORA);
  }
  
  public void setLeitura(String paramString)
  {
    setValue(Leitura.LEITURA.LEITURA_, paramString);
  }
  
  public String getLeitura()
  {
    return (String)getValue(Leitura.LEITURA.LEITURA_);
  }
  
  public void setObservacao(String paramString)
  {
    setValue(Leitura.LEITURA.OBSERVACAO, paramString);
  }
  
  public String getObservacao()
  {
    return (String)getValue(Leitura.LEITURA.OBSERVACAO);
  }
  
  public void setQuant(Integer paramInteger)
  {
    setValue(Leitura.LEITURA.QUANT, paramInteger);
  }
  
  public Integer getQuant()
  {
    return (Integer)getValue(Leitura.LEITURA.QUANT);
  }
  
  public void setStatus(String paramString)
  {
    setValue(Leitura.LEITURA.STATUS, paramString);
  }
  
  public String getStatus()
  {
    return (String)getValue(Leitura.LEITURA.STATUS);
  }
  
  public void setColetaId(Long paramLong)
  {
    setValue(Leitura.LEITURA.COLETA_ID, paramLong);
  }
  
  public Long getColetaId()
  {
    return (Long)getValue(Leitura.LEITURA.COLETA_ID);
  }
  
  public void setColetaId(ColetaRecord paramColetaRecord)
  {
    if (paramColetaRecord == null) {
      setValue(Leitura.LEITURA.COLETA_ID, null);
    } else {
      setValue(Leitura.LEITURA.COLETA_ID, paramColetaRecord.getValue(Coleta.COLETA.ID));
    }
  }
  
  public ColetaRecord fetchColeta()
  {
    return (ColetaRecord)create().selectFrom(Coleta.COLETA).where(new Condition[] { Coleta.COLETA.ID.equal(getValue(Leitura.LEITURA.COLETA_ID)) }).fetchOne();
  }
  
  public void setProdutoId(Long paramLong)
  {
    setValue(Leitura.LEITURA.PRODUTO_ID, paramLong);
  }
  
  public Long getProdutoId()
  {
    return (Long)getValue(Leitura.LEITURA.PRODUTO_ID);
  }
  
  public void setProdutoId(ProdutoRecord paramProdutoRecord)
  {
    if (paramProdutoRecord == null) {
      setValue(Leitura.LEITURA.PRODUTO_ID, null);
    } else {
      setValue(Leitura.LEITURA.PRODUTO_ID, paramProdutoRecord.getValue(Produto.PRODUTO.ID));
    }
  }
  
  public ProdutoRecord fetchProduto()
  {
    return (ProdutoRecord)create().selectFrom(Produto.PRODUTO).where(new Condition[] { Produto.PRODUTO.ID.equal(getValue(Leitura.LEITURA.PRODUTO_ID)) }).fetchOne();
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Leitura.LEITURA.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Leitura.LEITURA.VERSION);
  }
  
  public LeituraRecord()
  {
    super(Leitura.LEITURA);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/LeituraRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */