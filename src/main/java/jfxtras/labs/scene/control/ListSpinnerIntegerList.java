package jfxtras.labs.scene.control;

import java.util.AbstractList;

public class ListSpinnerIntegerList
  extends AbstractList<Integer>
{
  private int from;
  private int size;
  private int step;
  
  public ListSpinnerIntegerList()
  {
    this(-1073741823, 1073741823, 1);
  }
  
  public ListSpinnerIntegerList(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, paramInt1 > paramInt2 ? -1 : 1);
  }
  
  public ListSpinnerIntegerList(int paramInt1, int paramInt2, int paramInt3)
  {
    this.from = paramInt1;
    this.size = ((paramInt2 - paramInt1) / paramInt3 + 1);
    if (this.size < 0) {
      throw new IllegalArgumentException("This results in a negative size: " + paramInt1 + ", " + paramInt2 + "," + paramInt3);
    }
    this.step = paramInt3;
  }
  
  public Integer get(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Index cannot be < 0: " + paramInt);
    }
    int i = this.from + paramInt * this.step;
    return Integer.valueOf(i);
  }
  
  public int indexOf(Object paramObject)
  {
    int i = ((Integer)paramObject).intValue();
    int j = (i - this.from) / this.step;
    if ((j < 0) || (j > this.size)) {
      return -1;
    }
    Integer localInteger = get(j);
    if (!paramObject.equals(localInteger)) {
      return -1;
    }
    return j;
  }
  
  public int size()
  {
    return this.size;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/ListSpinnerIntegerList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */