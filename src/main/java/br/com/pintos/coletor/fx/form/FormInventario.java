package br.com.pintos.coletor.fx.form;

import br.com.pintos.coletor.bos.ClBo;
import br.com.pintos.coletor.bos.Facade;
import br.com.pintos.coletor.bos.FornecedorBo;
import br.com.pintos.coletor.bos.LojaBo;
import br.com.pintos.framework.fx.controls.ControlCombo;
import br.com.pintos.framework.fx.controls.ControlDate;
import br.com.pintos.framework.fx.controls.ControlInteger;
import br.com.pintos.framework.fx.controls.ControlLockup;
import br.com.pintos.framework.fx.controls.ControlString;
import br.com.pintos.framework.fx.forms.FormBean;
import br.com.pintos.jooq.tables.pojos.Cl;
import br.com.pintos.jooq.tables.pojos.Fornecedor;
import br.com.pintos.jooq.tables.pojos.Inventario;
import br.com.pintos.jooq.tables.pojos.Loja;

public class FormInventario
  extends FormBean<Inventario>
{
  private final ControlInteger numero = new ControlInteger("Numero");
  private final ControlDate data = new ControlDate("Data Inventario");
  private final ControlString observacao = new ControlString("Observacao");
  private final ControlCombo<String> tipo = new ControlCombo("Tipo", new ModeloTipoInv());
  private final ControlLockup<Cl> centroLuclo = new ControlLockup("Centro de Lucro", new ModeloCl());
  private final ControlLockup<Fornecedor> fornecedor = new ControlLockup("Fornecedor", new ModeloFor());
  private final ControlLockup<Loja> cmbLoja = new ControlLockup("Loja", new ModeloLoja());
  private final ControlCombo<String> cmbStatus = new ControlCombo("Situacao", new ModeloStatusInv());
  
  public FormInventario(BrowserInventarioFX paramBrowserInventarioFX, Inventario paramInventario)
  {
    super(paramBrowserInventarioFX, paramInventario);
  }
  
  public Inventario getBean()
  {
    ((Inventario)this.bean).setNumero(this.numero.getValue());
    ((Inventario)this.bean).setData(this.data.dataSql());
    ((Inventario)this.bean).setObservacao(this.observacao.getValue());
    ((Inventario)this.bean).setTipoinventario((String)this.tipo.getValue());
    ((Inventario)this.bean).setStatusinventario((String)this.cmbStatus.getValue());
    Long localLong1 = getLojaId();
    ((Inventario)this.bean).setLojaId(localLong1);
    ((Inventario)this.bean).setVersion(Integer.valueOf(0));
    Long localLong2 = getFornecedorId();
    ((Inventario)this.bean).setFornecedorId(localLong2);
    Long localLong3 = getCiId();
    ((Inventario)this.bean).setClId(localLong3);
    return (Inventario)this.bean;
  }
  
  private Long getCiId()
  {
    Cl localCl = (Cl)this.centroLuclo.getValue();
    if (localCl == null) {
      return null;
    }
    return localCl.getId();
  }
  
  private Long getFornecedorId()
  {
    Fornecedor localFornecedor = (Fornecedor)this.fornecedor.getValue();
    if (localFornecedor == null) {
      return null;
    }
    return localFornecedor.getId();
  }
  
  private Long getLojaId()
  {
    Loja localLoja = (Loja)this.cmbLoja.getValue();
    if (localLoja == null) {
      return null;
    }
    return localLoja.getId();
  }
  
  public void initControls()
  {
    layout(this.numero, 0, 0);
    layout(this.cmbStatus, 1, 0);
    layout(this.tipo, 2, 0);
    layout(this.data, 0, 1);
    layout(this.cmbLoja, 1, 1, 3);
    layout(this.observacao, 0, 2, 4);
    layout(this.centroLuclo, 0, 3, 2);
    layout(this.fornecedor, 2, 3, 2);
  }
  
  public void setBean(Inventario paramInventario)
  {
    this.numero.setValue(paramInventario.getNumero());
    this.data.setValue(paramInventario.getData());
    this.observacao.setValue(paramInventario.getObservacao());
    this.tipo.setValue(paramInventario.getTipoinventario());
    Cl localCl = (Cl)Facade.cl.findById(paramInventario.getClId());
    this.centroLuclo.setValue(localCl);
    Fornecedor localFornecedor = (Fornecedor)Facade.fornecedor.findById(paramInventario.getFornecedorId());
    this.fornecedor.setValue(localFornecedor);
    Loja localLoja = (Loja)Facade.loja.findById(paramInventario.getLojaId());
    this.cmbLoja.setValue(localLoja);
    this.cmbStatus.setValue(paramInventario.getStatusinventario());
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/fx/form/FormInventario.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */