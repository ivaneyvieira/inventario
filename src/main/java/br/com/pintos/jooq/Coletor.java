package br.com.pintos.jooq;

import br.com.pintos.jooq.tables.Cl;
import br.com.pintos.jooq.tables.Coleta;
import br.com.pintos.jooq.tables.Estoque;
import br.com.pintos.jooq.tables.Fornecedor;
import br.com.pintos.jooq.tables.Inventario;
import br.com.pintos.jooq.tables.Leitura;
import br.com.pintos.jooq.tables.Loja;
import br.com.pintos.jooq.tables.Lote;
import br.com.pintos.jooq.tables.Produto;
import br.com.pintos.jooq.tables.Usuario;
import br.com.pintos.jooq.tables.Viewcontagens;
import br.com.pintos.jooq.tables.Viewcontagensmodel;
import br.com.pintos.jooq.tables.Viewlotes;
import br.com.pintos.jooq.tables.Viewultleitura;
import java.util.Arrays;
import java.util.List;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

public class Coletor
  extends SchemaImpl
{
  private static final long serialVersionUID = 1753464756L;
  public static final Coletor COLETOR = new Coletor();
  
  private Coletor()
  {
    super("coletor");
  }
  
  public final List<Table<?>> getTables()
  {
    return Arrays.asList(new Table[] { Cl.CL, Coleta.COLETA, Estoque.ESTOQUE, Fornecedor.FORNECEDOR, Inventario.INVENTARIO, Leitura.LEITURA, Loja.LOJA, Lote.LOTE, Produto.PRODUTO, Usuario.USUARIO, Viewcontagens.VIEWCONTAGENS, Viewcontagensmodel.VIEWCONTAGENSMODEL, Viewlotes.VIEWLOTES, Viewultleitura.VIEWULTLEITURA });
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/jooq/Coletor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */