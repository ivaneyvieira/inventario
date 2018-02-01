package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.Loja;
import br.com.pintos.jooq.tables.Lote;
import br.com.pintos.jooq.tables.interfaces.ILote;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class LoteRecord
  extends UpdatableRecordImpl<LoteRecord>
  implements ILote
{
  private static final long serialVersionUID = -1099450431L;
  
  public void setId(Long paramLong)
  {
    setValue(Lote.LOTE.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Lote.LOTE.ID);
  }
  
  public List<ColetaRecord> fetchColetaList()
  {
    return create().selectFrom(Coleta.COLETA).where(new Condition[] { Coleta.COLETA.LOTE_ID.equal(getValue(Lote.LOTE.ID)) }).fetch();
  }
  
  public void setDescricao(String paramString)
  {
    setValue(Lote.LOTE.DESCRICAO, paramString);
  }
  
  public String getDescricao()
  {
    return (String)getValue(Lote.LOTE.DESCRICAO);
  }
  
  public void setLoteavulso(Byte paramByte)
  {
    setValue(Lote.LOTE.LOTEAVULSO, paramByte);
  }
  
  public Byte getLoteavulso()
  {
    return (Byte)getValue(Lote.LOTE.LOTEAVULSO);
  }
  
  public void setNumero(Integer paramInteger)
  {
    setValue(Lote.LOTE.NUMERO, paramInteger);
  }
  
  public Integer getNumero()
  {
    return (Integer)getValue(Lote.LOTE.NUMERO);
  }
  
  public void setLojaId(Long paramLong)
  {
    setValue(Lote.LOTE.LOJA_ID, paramLong);
  }
  
  public Long getLojaId()
  {
    return (Long)getValue(Lote.LOTE.LOJA_ID);
  }
  
  public void setLojaId(LojaRecord paramLojaRecord)
  {
    if (paramLojaRecord == null) {
      setValue(Lote.LOTE.LOJA_ID, null);
    } else {
      setValue(Lote.LOTE.LOJA_ID, paramLojaRecord.getValue(Loja.LOJA.ID));
    }
  }
  
  public LojaRecord fetchLoja()
  {
    return (LojaRecord)create().selectFrom(Loja.LOJA).where(new Condition[] { Loja.LOJA.ID.equal(getValue(Lote.LOTE.LOJA_ID)) }).fetchOne();
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Lote.LOTE.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Lote.LOTE.VERSION);
  }
  
  public LoteRecord()
  {
    super(Lote.LOTE);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/LoteRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */