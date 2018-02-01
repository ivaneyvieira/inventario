package br.com.pintos.coletor.modelo;

import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.ProdutoBo;
import br.com.pintos.jooq.tables.pojos.Leitura;
import br.com.pintos.jooq.tables.pojos.Produto;

public class LeituraModel
  extends Model<Leitura>
{
  public LeituraModel(Leitura paramLeitura)
  {
    super(paramLeitura);
  }
  
  public ProdutoModel getProdutos()
  {
    Produto localProduto = (Produto)Facade.produto.findById(((Leitura)this.bean).getProdutoId());
    return new ProdutoModel(localProduto);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/modelo/LeituraModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */