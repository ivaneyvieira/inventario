package br.com.pintos.jooq.tables;

import br.com.pintos.jooq.Coletor;
import br.com.pintos.jooq.tables.records.ViewcontagensmodelRecord;
import java.math.BigInteger;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Viewcontagensmodel
  extends TableImpl<ViewcontagensmodelRecord>
{
  private static final long serialVersionUID = -1012480779L;
  public static final Viewcontagensmodel VIEWCONTAGENSMODEL = new Viewcontagensmodel();
  public final TableField<ViewcontagensmodelRecord, Long> INVENTARIO_ID = createField("Inventario_id", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensmodelRecord, Long> LOTE_ID = createField("lote_id", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensmodelRecord, Long> PRODUTO_ID = createField("produto_id", SQLDataType.BIGINT, this);
  public final TableField<ViewcontagensmodelRecord, Integer> ULTLEITURA = createField("ultLeitura", SQLDataType.INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, String> CODIGO = createField("codigo", SQLDataType.VARCHAR, this);
  public final TableField<ViewcontagensmodelRecord, String> DESCRICAO = createField("descricao", SQLDataType.VARCHAR, this);
  public final TableField<ViewcontagensmodelRecord, String> GRADE = createField("grade", SQLDataType.VARCHAR, this);
  public final TableField<ViewcontagensmodelRecord, Integer> NUMLOTE = createField("numLote", SQLDataType.INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, String> DESCLOTE = createField("descLote", SQLDataType.CLOB, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> QUANT = createField("quant", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> L1 = createField("l1", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> L2 = createField("l2", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> L3 = createField("l3", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> L4 = createField("l4", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> L5 = createField("l5", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> L6 = createField("l6", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> L7 = createField("l7", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> L8 = createField("l8", SQLDataType.DECIMAL_INTEGER, this);
  public final TableField<ViewcontagensmodelRecord, BigInteger> L9 = createField("l9", SQLDataType.DECIMAL_INTEGER, this);
  
  public Class<ViewcontagensmodelRecord> getRecordType()
  {
    return ViewcontagensmodelRecord.class;
  }
  
  public Viewcontagensmodel()
  {
    super("viewContagensModel", Coletor.COLETOR);
  }
  
  public Viewcontagensmodel(String paramString)
  {
    super(paramString, Coletor.COLETOR, VIEWCONTAGENSMODEL);
  }
  
  public Viewcontagensmodel as(String paramString)
  {
    return new Viewcontagensmodel(paramString);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/Viewcontagensmodel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */