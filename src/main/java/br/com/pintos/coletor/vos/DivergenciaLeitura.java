package br.com.pintos.coletor.vos;

public class DivergenciaLeitura
{
  private final Long idColeta;
  private final Integer count;
  
  public DivergenciaLeitura(Long paramLong, Integer paramInteger)
  {
    this.idColeta = paramLong;
    this.count = paramInteger;
  }
  
  public Integer getCount()
  {
    return this.count;
  }
  
  public Long getIdColeta()
  {
    return this.idColeta;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/coletor/vos/DivergenciaLeitura.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */