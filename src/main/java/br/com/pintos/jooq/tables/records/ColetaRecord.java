package br.com.pintos.jooq.tables.records;

import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.Inventario;
import br.com.pintos.jooq.tables.Leitura;
import br.com.pintos.jooq.tables.Lote;
import br.com.pintos.jooq.tables.Usuario;
import br.com.pintos.jooq.tables.interfaces.IColeta;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SimpleSelectConditionStep;
import org.jooq.SimpleSelectWhereStep;
import org.jooq.TableField;
import org.jooq.impl.Factory;
import org.jooq.impl.UpdatableRecordImpl;

public class ColetaRecord
  extends UpdatableRecordImpl<ColetaRecord>
  implements IColeta
{
  private static final long serialVersionUID = 908948744L;
  
  public void setId(Long paramLong)
  {
    setValue(Coleta.COLETA.ID, paramLong);
  }
  
  public Long getId()
  {
    return (Long)getValue(Coleta.COLETA.ID);
  }
  
  public List<LeituraRecord> fetchLeituraList()
  {
    return create().selectFrom(Leitura.LEITURA).where(new Condition[] { Leitura.LEITURA.COLETA_ID.equal(getValue(Coleta.COLETA.ID)) }).fetch();
  }
  
  public void setNumleitura(Integer paramInteger)
  {
    setValue(Coleta.COLETA.NUMLEITURA, paramInteger);
  }
  
  public Integer getNumleitura()
  {
    return (Integer)getValue(Coleta.COLETA.NUMLEITURA);
  }
  
  public void setInventarioId(Long paramLong)
  {
    setValue(Coleta.COLETA.INVENTARIO_ID, paramLong);
  }
  
  public Long getInventarioId()
  {
    return (Long)getValue(Coleta.COLETA.INVENTARIO_ID);
  }
  
  public void setInventarioId(InventarioRecord paramInventarioRecord)
  {
    if (paramInventarioRecord == null) {
      setValue(Coleta.COLETA.INVENTARIO_ID, null);
    } else {
      setValue(Coleta.COLETA.INVENTARIO_ID, paramInventarioRecord.getValue(Inventario.INVENTARIO.ID));
    }
  }
  
  public InventarioRecord fetchInventario()
  {
    return (InventarioRecord)create().selectFrom(Inventario.INVENTARIO).where(new Condition[] { Inventario.INVENTARIO.ID.equal(getValue(Coleta.COLETA.INVENTARIO_ID)) }).fetchOne();
  }
  
  public void setLoteId(Long paramLong)
  {
    setValue(Coleta.COLETA.LOTE_ID, paramLong);
  }
  
  public Long getLoteId()
  {
    return (Long)getValue(Coleta.COLETA.LOTE_ID);
  }
  
  public void setLoteId(LoteRecord paramLoteRecord)
  {
    if (paramLoteRecord == null) {
      setValue(Coleta.COLETA.LOTE_ID, null);
    } else {
      setValue(Coleta.COLETA.LOTE_ID, paramLoteRecord.getValue(Lote.LOTE.ID));
    }
  }
  
  public LoteRecord fetchLote()
  {
    return (LoteRecord)create().selectFrom(Lote.LOTE).where(new Condition[] { Lote.LOTE.ID.equal(getValue(Coleta.COLETA.LOTE_ID)) }).fetchOne();
  }
  
  public void setUsuarioId(Long paramLong)
  {
    setValue(Coleta.COLETA.USUARIO_ID, paramLong);
  }
  
  public Long getUsuarioId()
  {
    return (Long)getValue(Coleta.COLETA.USUARIO_ID);
  }
  
  public void setUsuarioId(UsuarioRecord paramUsuarioRecord)
  {
    if (paramUsuarioRecord == null) {
      setValue(Coleta.COLETA.USUARIO_ID, null);
    } else {
      setValue(Coleta.COLETA.USUARIO_ID, paramUsuarioRecord.getValue(Usuario.USUARIO.ID));
    }
  }
  
  public UsuarioRecord fetchUsuario()
  {
    return (UsuarioRecord)create().selectFrom(Usuario.USUARIO).where(new Condition[] { Usuario.USUARIO.ID.equal(getValue(Coleta.COLETA.USUARIO_ID)) }).fetchOne();
  }
  
  public void setColetor(Integer paramInteger)
  {
    setValue(Coleta.COLETA.COLETOR, paramInteger);
  }
  
  public Integer getColetor()
  {
    return (Integer)getValue(Coleta.COLETA.COLETOR);
  }
  
  public void setStatus(String paramString)
  {
    setValue(Coleta.COLETA.STATUS, paramString);
  }
  
  public String getStatus()
  {
    return (String)getValue(Coleta.COLETA.STATUS);
  }
  
  public void setVersion(Integer paramInteger)
  {
    setValue(Coleta.COLETA.VERSION, paramInteger);
  }
  
  public Integer getVersion()
  {
    return (Integer)getValue(Coleta.COLETA.VERSION);
  }
  
  public ColetaRecord()
  {
    super(Coleta.COLETA);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/tables/records/ColetaRecord.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */