package br.com.pintos.coletor.modelo;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.InventarioBO;
import br.com.pintos.coletor.bos.LeituraBo;
import br.com.pintos.coletor.bos.LoteBo;
import br.com.pintos.coletor.bos.UsuarioBo;
import br.com.pintos.jooq.Tables;
import br.com.pintos.jooq.tables.pojos.Coleta;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.pojos.Lote;
import br.com.pintos.jooq.tables.pojos.Produto;
import br.com.pintos.jooq.tables.pojos.Usuario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.SortField;
import org.jooq.TableField;

public class ColetaModel
  extends Model<Coleta>
{
  public ColetaModel(Coleta paramColeta)
  {
    super(paramColeta);
  }
  
  public Integer contaProduto(Produto paramProduto)
  {
    Integer localInteger = Integer.valueOf(0);
    List localList = getLeituras(paramProduto);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      LeituraModel localLeituraModel = (LeituraModel)localIterator.next();
      localInteger = Integer.valueOf(localInteger.intValue() + ((br.com.pintos.jooq.tables.pojos.Leitura)localLeituraModel.getBean()).getQuant().intValue());
    }
    return localInteger;
  }
  
  public List<LeituraModel> getColetas()
  {
    List localList = Facade.leitura.find(Tables.LEITURA.COLETA_ID.eq(((Coleta)this.bean).getId()), new SortField[0]);
    return Model.getList(LeituraModel.class, localList);
  }
  
  public InventarioModel getIntentario()
  {
    Inventario localInventario = (Inventario)Facade.inventario.findById(((Coleta)this.bean).getInventarioId());
    return new InventarioModel(localInventario);
  }
  
  public List<LeituraModel> getLeituras()
  {
    List localList = Facade.leitura.find(Tables.LEITURA.COLETA_ID.eq(((Coleta)this.bean).getId()), new SortField[0]);
    return Model.getList(LeituraModel.class, localList);
  }
  
  private List<LeituraModel> getLeituras(Produto paramProduto)
  {
    List localList = Facade.leitura.find(Tables.LEITURA.COLETA_ID.eq(((Coleta)this.bean).getId()).and(Tables.LEITURA.PRODUTO_ID.eq(paramProduto.getId())), new SortField[0]);
    return Model.getList(LeituraModel.class, localList);
  }
  
  public LoteModel getLote()
  {
    Lote localLote = (Lote)Facade.lote.findById(((Coleta)this.bean).getLoteId());
    return new LoteModel(localLote);
  }
  
  public List<ProdutoModel> getProdutos()
  {
    ArrayList localArrayList = new ArrayList();
    List localList = getLeituras();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      LeituraModel localLeituraModel = (LeituraModel)localIterator.next();
      ProdutoModel localProdutoModel = localLeituraModel.getProdutos();
      if (!localArrayList.contains(localProdutoModel)) {
        localArrayList.add(localProdutoModel);
      }
    }
    return localArrayList;
  }
  
  public UsuarioModel getUsuario()
  {
    Usuario localUsuario = (Usuario)Facade.usuario.findById(((Coleta)this.bean).getUsuarioId());
    return new UsuarioModel(localUsuario);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/modelo/ColetaModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */