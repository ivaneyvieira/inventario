package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class MatrixPanelBuilder<B extends MatrixPanelBuilder<B>>
  extends ControlBuilder<B>
  implements Builder<MatrixPanel>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final MatrixPanelBuilder create()
  {
    return new MatrixPanelBuilder();
  }
  
  public final MatrixPanelBuilder ledWidth(int paramInt)
  {
    this.properties.put("ledWidth", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final MatrixPanelBuilder ledHeight(int paramInt)
  {
    this.properties.put("ledHeight", new SimpleIntegerProperty(paramInt));
    return this;
  }
  
  public final MatrixPanelBuilder contents(List<Content> paramList)
  {
    this.properties.put("contentsList", new SimpleObjectProperty(paramList));
    return this;
  }
  
  public final MatrixPanelBuilder contents(Content[] paramArrayOfContent)
  {
    this.properties.put("contentsArray", new SimpleObjectProperty(paramArrayOfContent));
    return this;
  }
  
  public final MatrixPanelBuilder frameDesign(Gauge.FrameDesign paramFrameDesign)
  {
    this.properties.put("frameDesign", new SimpleObjectProperty(paramFrameDesign));
    return this;
  }
  
  public final MatrixPanelBuilder frameBaseColor(Color paramColor)
  {
    this.properties.put("frameBaseColor", new SimpleObjectProperty(paramColor));
    return this;
  }
  
  public final MatrixPanelBuilder frameVisible(boolean paramBoolean)
  {
    this.properties.put("frameVisible", new SimpleBooleanProperty(paramBoolean));
    return this;
  }
  
  public final B prefWidth(double paramDouble)
  {
    this.properties.put("prefWidth", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final B prefHeight(double paramDouble)
  {
    this.properties.put("prefHeight", new SimpleDoubleProperty(paramDouble));
    return this;
  }
  
  public final MatrixPanel build()
  {
    MatrixPanel localMatrixPanel = new MatrixPanel();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("ledWidth".equals(str)) {
        localMatrixPanel.setLedWidth(((IntegerProperty)this.properties.get(str)).get());
      } else if ("ledHeight".equals(str)) {
        localMatrixPanel.setLedHeight(((IntegerProperty)this.properties.get(str)).get());
      } else if ("contentsList".equals(str)) {
        localMatrixPanel.setContents((List)((ObjectProperty)this.properties.get(str)).get());
      } else if ("contentsArray".equals(str)) {
        localMatrixPanel.setContents((Content[])((ObjectProperty)this.properties.get(str)).get());
      } else if ("prefWidth".equals(str)) {
        localMatrixPanel.setPrefWidth(((DoubleProperty)this.properties.get(str)).get());
      } else if ("prefHeight".equals(str)) {
        localMatrixPanel.setPrefHeight(((DoubleProperty)this.properties.get(str)).get());
      } else if ("frameDesign".equals(str)) {
        localMatrixPanel.setFrameDesign((Gauge.FrameDesign)((ObjectProperty)this.properties.get(str)).get());
      } else if ("frameBaseColor".equals(str)) {
        localMatrixPanel.setFrameBaseColor((Color)((ObjectProperty)this.properties.get(str)).get());
      } else if ("frameVisible".equals(str)) {
        localMatrixPanel.setFrameVisible(((BooleanProperty)this.properties.get(str)).get());
      }
    }
    return localMatrixPanel;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/MatrixPanelBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */