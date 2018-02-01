package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Inventario;
import br.com.pintos.jooq.tables.Loja;
import br.com.pintos.jooq.tables.Lote;
import br.com.pintos.jooq.tables.interfaces.ILoja;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class LojaRecord
  extends UpdatableRecordImpl<LojaRecord>
  implements ILoja
{
  private static final long serialVersionUID = -128670748L;
  
  public void setId(Long paramLong)
  {
    setValue(Loja.LOJA.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Loja.LOJA.ID);
  }
  
  public List<InventarioRecord> fetchInventarioList()
  {
    return create().selectFrom(Inventario.INVENTARIO).where(new Condition[] { Inventario.INVENTARIO.LOJA_ID.equal(getValue(Loja.LOJA.ID)) }).fetch();
  }
  
  public List<LoteRecord> fetchLoteList()
  {
    return create().selectFrom(Lote.LOTE).where(new Condition[] { Lote.LOTE.LOJA_ID.equal(getValue(Loja.LOJA.ID)) }).fetch();
  }
  
  public void setEndereco(String paramString)
  {
    setValue(Loja.LOJA.ENDERECO, paramString);
  }
  
  public String getEndereco()
  {
    return (String)getValue(Loja.LOJA.ENDERECO);
  }
  
  public void setNome(String paramString)
  {
    setValue(Loja.LOJA.NOME, paramString);
  }
  
  public String getNome()
  {
    return (String)getValue(Loja.LOJA.NOME);
  }
  
  public void setSigla(String paramString)
  {
    setValue(Loja.LOJA.SIGLA, paramString);
  }
  
  public String getSigla()
  {
    return (String)getValue(Loja.LOJA.SIGLA);
  }
  
  public void setStoreno(Integer paramInteger)
  {
    setValue(Loja.LOJA.STORENO, paramInteger);
  }
  
  public Integer getStoreno()
  {
    return (Integer)getValue(Loja.LOJA.STORENO);
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Loja.LOJA.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Loja.LOJA.VERSION);
  }
  
  public LojaRecord()
  {
    super(Loja.LOJA);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/LojaRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */