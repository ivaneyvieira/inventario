package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Estoque;
import br.com.pintos.jooq.tables.Inventario;
import br.com.pintos.jooq.tables.Produto;
import br.com.pintos.jooq.tables.interfaces.IEstoque;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class EstoqueRecord
  extends UpdatableRecordImpl<EstoqueRecord>
  implements IEstoque
{
  private static final long serialVersionUID = 1052400606L;
  
  public void setId(Long paramLong)
  {
    setValue(Estoque.ESTOQUE.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Estoque.ESTOQUE.ID);
  }
  
  public void setQuant(Integer paramInteger)
  {
    setValue(Estoque.ESTOQUE.QUANT, paramInteger);
  }
  
  public Integer getQuant()
  {
    return (Integer)getValue(Estoque.ESTOQUE.QUANT);
  }
  
  public void setInventarioId(Long paramLong)
  {
    setValue(Estoque.ESTOQUE.INVENTARIO_ID, paramLong);
  }
  
  public Long getInventarioId()
  {
    return (Long)getValue(Estoque.ESTOQUE.INVENTARIO_ID);
  }
  
  public void setInventarioId(InventarioRecord paramInventarioRecord)
  {
    if (paramInventarioRecord == null) {
      setValue(Estoque.ESTOQUE.INVENTARIO_ID, null);
    } else {
      setValue(Estoque.ESTOQUE.INVENTARIO_ID, paramInventarioRecord.getValue(Inventario.INVENTARIO.ID));
    }
  }
  
  public InventarioRecord fetchInventario()
  {
    return (InventarioRecord)create().selectFrom(Inventario.INVENTARIO).where(new Condition[] { Inventario.INVENTARIO.ID.equal(getValue(Estoque.ESTOQUE.INVENTARIO_ID)) }).fetchOne();
  }
  
  public void setProdutoId(Long paramLong)
  {
    setValue(Estoque.ESTOQUE.PRODUTO_ID, paramLong);
  }
  
  public Long getProdutoId()
  {
    return (Long)getValue(Estoque.ESTOQUE.PRODUTO_ID);
  }
  
  public void setProdutoId(ProdutoRecord paramProdutoRecord)
  {
    if (paramProdutoRecord == null) {
      setValue(Estoque.ESTOQUE.PRODUTO_ID, null);
    } else {
      setValue(Estoque.ESTOQUE.PRODUTO_ID, paramProdutoRecord.getValue(Produto.PRODUTO.ID));
    }
  }
  
  public ProdutoRecord fetchProduto()
  {
    return (ProdutoRecord)create().selectFrom(Produto.PRODUTO).where(new Condition[] { Produto.PRODUTO.ID.equal(getValue(Estoque.ESTOQUE.PRODUTO_ID)) }).fetchOne();
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Estoque.ESTOQUE.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Estoque.ESTOQUE.VERSION);
  }
  
  public EstoqueRecord()
  {
    super(Estoque.ESTOQUE);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/EstoqueRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */