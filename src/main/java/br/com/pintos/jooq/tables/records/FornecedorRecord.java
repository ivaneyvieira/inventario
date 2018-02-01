package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Fornecedor;
import br.com.pintos.jooq.tables.Inventario;
import br.com.pintos.jooq.tables.Produto;
import br.com.pintos.jooq.tables.interfaces.IFornecedor;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class FornecedorRecord
  extends UpdatableRecordImpl<FornecedorRecord>
  implements IFornecedor
{
  private static final long serialVersionUID = 1905986183L;
  
  public void setId(Long paramLong)
  {
    setValue(Fornecedor.FORNECEDOR.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Fornecedor.FORNECEDOR.ID);
  }
  
  public List<InventarioRecord> fetchInventarioList()
  {
    return create().selectFrom(Inventario.INVENTARIO).where(new Condition[] { Inventario.INVENTARIO.FORNECEDOR_ID.equal(getValue(Fornecedor.FORNECEDOR.ID)) }).fetch();
  }
  
  public List<ProdutoRecord> fetchProdutoList()
  {
    return create().selectFrom(Produto.PRODUTO).where(new Condition[] { Produto.PRODUTO.FORNECEDOR_ID.equal(getValue(Fornecedor.FORNECEDOR.ID)) }).fetch();
  }
  
  public void setCodigo(Integer paramInteger)
  {
    setValue(Fornecedor.FORNECEDOR.CODIGO, paramInteger);
  }
  
  public Integer getCodigo()
  {
    return (Integer)getValue(Fornecedor.FORNECEDOR.CODIGO);
  }
  
  public void setFantazia(String paramString)
  {
    setValue(Fornecedor.FORNECEDOR.FANTAZIA, paramString);
  }
  
  public String getFantazia()
  {
    return (String)getValue(Fornecedor.FORNECEDOR.FANTAZIA);
  }
  
  public void setRazao(String paramString)
  {
    setValue(Fornecedor.FORNECEDOR.RAZAO, paramString);
  }
  
  public String getRazao()
  {
    return (String)getValue(Fornecedor.FORNECEDOR.RAZAO);
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Fornecedor.FORNECEDOR.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Fornecedor.FORNECEDOR.VERSION);
  }
  
  public FornecedorRecord()
  {
    super(Fornecedor.FORNECEDOR);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/FornecedorRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */