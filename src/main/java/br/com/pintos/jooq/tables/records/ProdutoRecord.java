package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Cl;
import br.com.pintos.jooq.tables.Estoque;
import br.com.pintos.jooq.tables.Fornecedor;
import br.com.pintos.jooq.tables.Leitura;
import br.com.pintos.jooq.tables.Produto;
import br.com.pintos.jooq.tables.interfaces.IProduto;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class ProdutoRecord
  extends UpdatableRecordImpl<ProdutoRecord>
  implements IProduto
{
  private static final long serialVersionUID = 1053902175L;
  
  public void setId(Long paramLong)
  {
    setValue(Produto.PRODUTO.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Produto.PRODUTO.ID);
  }
  
  public List<EstoqueRecord> fetchEstoqueList()
  {
    return create().selectFrom(Estoque.ESTOQUE).where(new Condition[] { Estoque.ESTOQUE.PRODUTO_ID.equal(getValue(Produto.PRODUTO.ID)) }).fetch();
  }
  
  public List<LeituraRecord> fetchLeituraList()
  {
    return create().selectFrom(Leitura.LEITURA).where(new Condition[] { Leitura.LEITURA.PRODUTO_ID.equal(getValue(Produto.PRODUTO.ID)) }).fetch();
  }
  
  public void setBarcode(String paramString)
  {
    setValue(Produto.PRODUTO.BARCODE, paramString);
  }
  
  public String getBarcode()
  {
    return (String)getValue(Produto.PRODUTO.BARCODE);
  }
  
  public void setCodigo(String paramString)
  {
    setValue(Produto.PRODUTO.CODIGO, paramString);
  }
  
  public String getCodigo()
  {
    return (String)getValue(Produto.PRODUTO.CODIGO);
  }
  
  public void setDescricao(String paramString)
  {
    setValue(Produto.PRODUTO.DESCRICAO, paramString);
  }
  
  public String getDescricao()
  {
    return (String)getValue(Produto.PRODUTO.DESCRICAO);
  }
  
  public void setDuplicado(Byte paramByte)
  {
    setValue(Produto.PRODUTO.DUPLICADO, paramByte);
  }
  
  public Byte getDuplicado()
  {
    return (Byte)getValue(Produto.PRODUTO.DUPLICADO);
  }
  
  public void setForalinha(Byte paramByte)
  {
    setValue(Produto.PRODUTO.FORALINHA, paramByte);
  }
  
  public Byte getForalinha()
  {
    return (Byte)getValue(Produto.PRODUTO.FORALINHA);
  }
  
  public void setGrade(String paramString)
  {
    setValue(Produto.PRODUTO.GRADE, paramString);
  }
  
  public String getGrade()
  {
    return (String)getValue(Produto.PRODUTO.GRADE);
  }
  
  public void setUsoconsumo(Byte paramByte)
  {
    setValue(Produto.PRODUTO.USOCONSUMO, paramByte);
  }
  
  public Byte getUsoconsumo()
  {
    return (Byte)getValue(Produto.PRODUTO.USOCONSUMO);
  }
  
  public void setClId(Long paramLong)
  {
    setValue(Produto.PRODUTO.CL_ID, paramLong);
  }
  
  public Long getClId()
  {
    return (Long)getValue(Produto.PRODUTO.CL_ID);
  }
  
  public void setClId(ClRecord paramClRecord)
  {
    if (paramClRecord == null) {
      setValue(Produto.PRODUTO.CL_ID, null);
    } else {
      setValue(Produto.PRODUTO.CL_ID, paramClRecord.getValue(Cl.CL.ID));
    }
  }
  
  public ClRecord fetchCl()
  {
    return (ClRecord)create().selectFrom(Cl.CL).where(new Condition[] { Cl.CL.ID.equal(getValue(Produto.PRODUTO.CL_ID)) }).fetchOne();
  }
  
  public void setFornecedorId(Long paramLong)
  {
    setValue(Produto.PRODUTO.FORNECEDOR_ID, paramLong);
  }
  
  public Long getFornecedorId()
  {
    return (Long)getValue(Produto.PRODUTO.FORNECEDOR_ID);
  }
  
  public void setFornecedorId(FornecedorRecord paramFornecedorRecord)
  {
    if (paramFornecedorRecord == null) {
      setValue(Produto.PRODUTO.FORNECEDOR_ID, null);
    } else {
      setValue(Produto.PRODUTO.FORNECEDOR_ID, paramFornecedorRecord.getValue(Fornecedor.FORNECEDOR.ID));
    }
  }
  
  public FornecedorRecord fetchFornecedor()
  {
    return (FornecedorRecord)create().selectFrom(Fornecedor.FORNECEDOR).where(new Condition[] { Fornecedor.FORNECEDOR.ID.equal(getValue(Produto.PRODUTO.FORNECEDOR_ID)) }).fetchOne();
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Produto.PRODUTO.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Produto.PRODUTO.VERSION);
  }
  
  public ProdutoRecord()
  {
    super(Produto.PRODUTO);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/ProdutoRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */