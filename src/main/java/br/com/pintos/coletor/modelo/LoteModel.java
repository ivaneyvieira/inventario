package br.com.pintos.coletor.modelo;

import br.com.pintos.coletor.bos.ColetaBo;
import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.LoteBo;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.pojos.Lote;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SortField;
import org.jooq.TableField;

public class LoteModel
  extends Model<Lote>
{
  public LoteModel(Lote paramLote)
  {
    super(paramLote);
  }
  
  public List<ColetaModel> getColetas(Inventario paramInventario)
  {
    Long localLong1 = ((Lote)this.bean).getId();
    Long localLong2 = paramInventario.getId();
    List localList = Facade.coleta.find(Tables.COLETA.LOTE_ID.eq(localLong1).and(Tables.COLETA.INVENTARIO_ID.eq(localLong2)), new SortField[0]);
    return Model.getList(ColetaModel.class, localList);
  }
  
  public List<ProdutoModel> getProdutos(Inventario paramInventario)
  {
    List localList = Facade.lote.getProdutos((Lote)this.bean, paramInventario);
    return Model.getList(ProdutoModel.class, localList);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/modelo/LoteModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */