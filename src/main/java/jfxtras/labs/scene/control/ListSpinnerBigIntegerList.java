package jfxtras.labs.scene.control;

import java.math.BigInteger;
import java.util.AbstractList;

public class ListSpinnerBigIntegerList
  extends AbstractList<BigInteger>
{
  private BigInteger from;
  private int size;
  private BigInteger step;
  
  public ListSpinnerBigIntegerList()
  {
    this(BigInteger.valueOf(-1073741824L).add(BigInteger.ONE), BigInteger.valueOf(1073741823L), BigInteger.ONE);
  }
  
  public ListSpinnerBigIntegerList(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    this(paramBigInteger1, paramBigInteger2, paramBigInteger1.compareTo(paramBigInteger2) > 0 ? BigInteger.valueOf(-1L) : BigInteger.ONE);
  }
  
  public ListSpinnerBigIntegerList(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3)
  {
    this.from = paramBigInteger1;
    this.size = paramBigInteger2.subtract(paramBigInteger1).divide(paramBigInteger3).add(BigInteger.ONE).intValue();
    if (this.size < 0) {
      throw new IllegalArgumentException("This results in a negative size: " + paramBigInteger1 + ", " + paramBigInteger2 + "," + paramBigInteger3);
    }
    this.step = paramBigInteger3;
  }
  
  public BigInteger get(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Index cannot be < 0: " + paramInt);
    }
    BigInteger localBigInteger = this.from.add(BigInteger.valueOf(paramInt).multiply(this.step));
    return localBigInteger;
  }
  
  public int indexOf(Object paramObject)
  {
    BigInteger localBigInteger1 = (BigInteger)paramObject;
    BigInteger localBigInteger2 = localBigInteger1.subtract(this.from).divide(this.step);
    int i = localBigInteger2.intValue();
    if (i > this.size) {
      return -1;
    }
    BigInteger localBigInteger3 = get(i);
    if (!paramObject.equals(localBigInteger3)) {
      return -1;
    }
    return i;
  }
  
  public int size()
  {
    return this.size;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/ListSpinnerBigIntegerList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */