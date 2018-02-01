package br.com.pintos.framework.fx.forms;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class TablePane
  extends GridPane
{
  private int uCol = 0;
  private int uLin = 0;
  
  public TablePane()
  {
    setHgap(3.0D);
    setVgap(3.0D);
    setPadding(new Insets(3.0D, 3.0D, 3.0D, 3.0D));
    setGridLinesVisible(false);
  }
  
  private void addColumn(String paramString)
  {
    ObservableList localObservableList = getColumnConstraints();
    if (paramString.matches("^[0-9]+$"))
    {
      int i = Integer.valueOf(paramString).intValue();
      localObservableList.add(new ColumnConstraints(i * 11));
    }
    else if (paramString.equals("F"))
    {
      ColumnConstraints localColumnConstraints = new ColumnConstraints();
      localColumnConstraints.setFillWidth(true);
      localObservableList.add(localColumnConstraints);
    }
  }
  
  public void addColumns(String... paramVarArgs)
  {
    for (String str : paramVarArgs) {
      addColumn(str);
    }
  }
  
  public void addColumns(String paramString)
  {
    String[] arrayOfString = paramString.split("\\s*,\\s*");
    addColumns(arrayOfString);
  }
  
  public void layout(Node paramNode)
  {
    layout(paramNode, this.uCol, this.uLin);
    this.uCol += 1;
  }
  
  public void layout(Node paramNode, int paramInt)
  {
    layout(paramNode, paramInt, this.uLin, 1);
  }
  
  public void layout(Node paramNode, int paramInt1, int paramInt2)
  {
    layout(paramNode, paramInt1, paramInt2, 1);
  }
  
  public void layout(Node paramNode, int paramInt1, int paramInt2, int paramInt3)
  {
    layout(paramNode, paramInt1, paramInt2, paramInt3, 1);
  }
  
  public void layout(Node paramNode, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    layout(paramNode, paramInt1, paramInt2, paramInt3, paramInt4, HPos.LEFT);
  }
  
  public void layout(Node paramNode, int paramInt1, int paramInt2, int paramInt3, int paramInt4, HPos paramHPos)
  {
    layout(paramNode, paramInt1, paramInt2, paramInt3, paramInt4, paramHPos, VPos.TOP);
  }
  
  public void layout(Node paramNode, int paramInt1, int paramInt2, int paramInt3, int paramInt4, HPos paramHPos, VPos paramVPos)
  {
    add(paramNode, paramInt1, paramInt2);
    setConstraints(paramNode, paramInt1, paramInt2, paramInt3, paramInt4, paramHPos, paramVPos);
    this.uCol = paramInt1;
    this.uLin = paramInt2;
    if ((paramNode instanceof Control))
    {
      Control localControl = (Control)paramNode;
      localControl.setMaxWidth(Double.MAX_VALUE);
      localControl.setMaxHeight(Double.MAX_VALUE);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/forms/TablePane.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */