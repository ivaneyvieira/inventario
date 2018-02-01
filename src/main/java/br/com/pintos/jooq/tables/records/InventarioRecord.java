package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Cl;
import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.Estoque;
import br.com.pintos.jooq.tables.Fornecedor;
import br.com.pintos.jooq.tables.Inventario;
import br.com.pintos.jooq.tables.Loja;
import br.com.pintos.jooq.tables.interfaces.IInventario;
import java.sql.Date;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class InventarioRecord
  extends UpdatableRecordImpl<InventarioRecord>
  implements IInventario
{
  private static final long serialVersionUID = 1366369845L;
  
  public void setId(Long paramLong)
  {
    setValue(Inventario.INVENTARIO.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Inventario.INVENTARIO.ID);
  }
  
  public List<ColetaRecord> fetchColetaList()
  {
    return create().selectFrom(Coleta.COLETA).where(new Condition[] { Coleta.COLETA.INVENTARIO_ID.equal(getValue(Inventario.INVENTARIO.ID)) }).fetch();
  }
  
  public List<EstoqueRecord> fetchEstoqueList()
  {
    return create().selectFrom(Estoque.ESTOQUE).where(new Condition[] { Estoque.ESTOQUE.INVENTARIO_ID.equal(getValue(Inventario.INVENTARIO.ID)) }).fetch();
  }
  
  public void setNumero(Integer paramInteger)
  {
    setValue(Inventario.INVENTARIO.NUMERO, paramInteger);
  }
  
  public Integer getNumero()
  {
    return (Integer)getValue(Inventario.INVENTARIO.NUMERO);
  }
  
  public void setData(Date paramDate)
  {
    setValue(Inventario.INVENTARIO.DATA, paramDate);
  }
  
  public Date getData()
  {
    return (Date)getValue(Inventario.INVENTARIO.DATA);
  }
  
  public void setObservacao(String paramString)
  {
    setValue(Inventario.INVENTARIO.OBSERVACAO, paramString);
  }
  
  public String getObservacao()
  {
    return (String)getValue(Inventario.INVENTARIO.OBSERVACAO);
  }
  
  public void setTipoinventario(String paramString)
  {
    setValue(Inventario.INVENTARIO.TIPOINVENTARIO, paramString);
  }
  
  public String getTipoinventario()
  {
    return (String)getValue(Inventario.INVENTARIO.TIPOINVENTARIO);
  }
  
  public void setStatusinventario(String paramString)
  {
    setValue(Inventario.INVENTARIO.STATUSINVENTARIO, paramString);
  }
  
  public String getStatusinventario()
  {
    return (String)getValue(Inventario.INVENTARIO.STATUSINVENTARIO);
  }
  
  public void setLojaId(Long paramLong)
  {
    setValue(Inventario.INVENTARIO.LOJA_ID, paramLong);
  }
  
  public Long getLojaId()
  {
    return (Long)getValue(Inventario.INVENTARIO.LOJA_ID);
  }
  
  public void setLojaId(LojaRecord paramLojaRecord)
  {
    if (paramLojaRecord == null) {
      setValue(Inventario.INVENTARIO.LOJA_ID, null);
    } else {
      setValue(Inventario.INVENTARIO.LOJA_ID, paramLojaRecord.getValue(Loja.LOJA.ID));
    }
  }
  
  public LojaRecord fetchLoja()
  {
    return (LojaRecord)create().selectFrom(Loja.LOJA).where(new Condition[] { Loja.LOJA.ID.equal(getValue(Inventario.INVENTARIO.LOJA_ID)) }).fetchOne();
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Inventario.INVENTARIO.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Inventario.INVENTARIO.VERSION);
  }
  
  public void setFornecedorId(Long paramLong)
  {
    setValue(Inventario.INVENTARIO.FORNECEDOR_ID, paramLong);
  }
  
  public Long getFornecedorId()
  {
    return (Long)getValue(Inventario.INVENTARIO.FORNECEDOR_ID);
  }
  
  public void setFornecedorId(FornecedorRecord paramFornecedorRecord)
  {
    if (paramFornecedorRecord == null) {
      setValue(Inventario.INVENTARIO.FORNECEDOR_ID, null);
    } else {
      setValue(Inventario.INVENTARIO.FORNECEDOR_ID, paramFornecedorRecord.getValue(Fornecedor.FORNECEDOR.ID));
    }
  }
  
  public FornecedorRecord fetchFornecedor()
  {
    return (FornecedorRecord)create().selectFrom(Fornecedor.FORNECEDOR).where(new Condition[] { Fornecedor.FORNECEDOR.ID.equal(getValue(Inventario.INVENTARIO.FORNECEDOR_ID)) }).fetchOne();
  }
  
  public void setClId(Long paramLong)
  {
    setValue(Inventario.INVENTARIO.CL_ID, paramLong);
  }
  
  public Long getClId()
  {
    return (Long)getValue(Inventario.INVENTARIO.CL_ID);
  }
  
  public void setClId(ClRecord paramClRecord)
  {
    if (paramClRecord == null) {
      setValue(Inventario.INVENTARIO.CL_ID, null);
    } else {
      setValue(Inventario.INVENTARIO.CL_ID, paramClRecord.getValue(Cl.CL.ID));
    }
  }
  
  public ClRecord fetchCl()
  {
    return (ClRecord)create().selectFrom(Cl.CL).where(new Condition[] { Cl.CL.ID.equal(getValue(Inventario.INVENTARIO.CL_ID)) }).fetchOne();
  }
  
  public InventarioRecord()
  {
    super(Inventario.INVENTARIO);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/InventarioRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */