package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Cl;
import br.com.pintos.jooq.tables.Inventario;
import br.com.pintos.jooq.tables.Produto;
import br.com.pintos.jooq.tables.interfaces.ICl;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class ClRecord
  extends UpdatableRecordImpl<ClRecord>
  implements ICl
{
  private static final long serialVersionUID = -2049534591L;
  
  public void setId(Long paramLong)
  {
    setValue(Cl.CL.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Cl.CL.ID);
  }
  
  public List<InventarioRecord> fetchInventarioList()
  {
    return create().selectFrom(Inventario.INVENTARIO).where(new Condition[] { Inventario.INVENTARIO.CL_ID.equal(getValue(Cl.CL.ID)) }).fetch();
  }
  
  public List<ProdutoRecord> fetchProdutoList()
  {
    return create().selectFrom(Produto.PRODUTO).where(new Condition[] { Produto.PRODUTO.CL_ID.equal(getValue(Cl.CL.ID)) }).fetch();
  }
  
  public void setClno(Integer paramInteger)
  {
    setValue(Cl.CL.CLNO, paramInteger);
  }
  
  public Integer getClno()
  {
    return (Integer)getValue(Cl.CL.CLNO);
  }
  
  public void setDepartamento(String paramString)
  {
    setValue(Cl.CL.DEPARTAMENTO, paramString);
  }
  
  public String getDepartamento()
  {
    return (String)getValue(Cl.CL.DEPARTAMENTO);
  }
  
  public void setGrupo(String paramString)
  {
    setValue(Cl.CL.GRUPO, paramString);
  }
  
  public String getGrupo()
  {
    return (String)getValue(Cl.CL.GRUPO);
  }
  
  public void setSecao(String paramString)
  {
    setValue(Cl.CL.SECAO, paramString);
  }
  
  public String getSecao()
  {
    return (String)getValue(Cl.CL.SECAO);
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Cl.CL.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Cl.CL.VERSION);
  }
  
  public ClRecord()
  {
    super(Cl.CL);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/ClRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */