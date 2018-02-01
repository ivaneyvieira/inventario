package br.com.pintos.coletor.modelo;

import br.com.pintos.coletor.bos.ColetaBo;
import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.InventarioBO;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.pojos.Inventario;
import java.util.List;
import org.jooq.SortField;
import org.jooq.TableField;

public class InventarioModel
  extends Model<Inventario>
{
  public InventarioModel(Inventario paramInventario)
  {
    super(paramInventario);
  }
  
  public List<ColetaModel> getColetas()
  {
    List localList = Facade.coleta.find(Tables.COLETA.INVENTARIO_ID.eq(((Inventario)this.bean).getId()), new SortField[0]);
    return Model.getList(ColetaModel.class, localList);
  }
  
  public List<LoteModel> getLotes()
  {
    List localList = Facade.inventario.getLotes((Inventario)this.bean);
    return Model.getList(LoteModel.class, localList);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/modelo/InventarioModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */