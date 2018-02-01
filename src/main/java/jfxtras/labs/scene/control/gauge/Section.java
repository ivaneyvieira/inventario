package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Shape;
import jfxtras.labs.util.Util;

public class Section
{
  private DoubleProperty start;
  private DoubleProperty stop;
  private ObjectProperty<Color> color;
  private StringProperty cssColor;
  private ObjectProperty<Color> transparentColor;
  private ObjectProperty<Color> highlightColor;
  private ObjectProperty<Color> transparentHighlightColor;
  private ObjectProperty<Paint> paint;
  private ObjectProperty<Shape> sectionArea;
  private ObjectProperty<Shape> filledArea;
  private StringProperty text;
  
  public Section()
  {
    this(-1.0D, -1.0D, Color.RED, "");
  }
  
  public Section(double paramDouble1, double paramDouble2, Color paramColor)
  {
    this(paramDouble1, paramDouble2, paramColor, "");
  }
  
  public Section(double paramDouble1, double paramDouble2, Color paramColor, String paramString)
  {
    this(paramDouble1, paramDouble2, paramColor, paramColor.brighter().brighter(), null, null, paramString);
  }
  
  public Section(double paramDouble1, double paramDouble2, Color paramColor1, Color paramColor2)
  {
    this(paramDouble1, paramDouble2, paramColor1, paramColor2, null, null, "");
  }
  
  public Section(double paramDouble1, double paramDouble2, Color paramColor1, Color paramColor2, String paramString)
  {
    this(paramDouble1, paramDouble2, paramColor1, paramColor2, null, null, paramString);
  }
  
  public Section(double paramDouble1, double paramDouble2, Color paramColor, Arc paramArc)
  {
    this(paramDouble1, paramDouble2, paramColor, null, paramArc);
  }
  
  public Section(double paramDouble1, double paramDouble2, Color paramColor, Shape paramShape, Arc paramArc)
  {
    this(paramDouble1, paramDouble2, paramColor, paramColor.brighter().brighter(), paramShape, paramArc, "");
  }
  
  public Section(double paramDouble1, double paramDouble2, Color paramColor1, Color paramColor2, Shape paramShape1, Shape paramShape2, String paramString)
  {
    this.start = new SimpleDoubleProperty(paramDouble1);
    this.stop = new SimpleDoubleProperty(paramDouble2);
    this.color = new SimpleObjectProperty(paramColor1);
    this.transparentColor = new SimpleObjectProperty(Color.color(paramColor1.getRed(), paramColor1.getGreen(), paramColor1.getBlue(), 0.3D));
    this.highlightColor = new SimpleObjectProperty(paramColor2);
    this.transparentHighlightColor = new SimpleObjectProperty(Color.color(paramColor1.getRed(), paramColor1.getGreen(), paramColor1.getBlue(), 0.5D));
    this.sectionArea = new SimpleObjectProperty(paramShape1);
    this.filledArea = new SimpleObjectProperty(paramShape2);
    this.paint = new SimpleObjectProperty(paramColor1);
    this.text = new SimpleStringProperty(paramString);
    this.cssColor = new SimpleStringProperty(Util.createCssColor(paramColor1));
  }
  
  public final double getStart()
  {
    return this.start.get();
  }
  
  public final void setStart(double paramDouble)
  {
    this.start.set(paramDouble);
  }
  
  public final DoubleProperty startProperty()
  {
    return this.start;
  }
  
  public final double getStop()
  {
    return this.stop.get();
  }
  
  public final void setStop(double paramDouble)
  {
    this.stop.set(paramDouble);
  }
  
  public final DoubleProperty stopProperty()
  {
    return this.stop;
  }
  
  public final Color getColor()
  {
    return (Color)this.color.get();
  }
  
  public final void setColor(Color paramColor)
  {
    this.color.set(paramColor);
    this.transparentColor.set(Color.color(paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue(), 0.25D));
    this.cssColor.set(Util.createCssColor(paramColor));
  }
  
  public final ObjectProperty<Color> colorProperty()
  {
    return this.color;
  }
  
  public final Color getTransparentColor()
  {
    return (Color)this.transparentColor.get();
  }
  
  public final ObjectProperty<Color> transparentColorProperty()
  {
    return this.transparentColor;
  }
  
  public final Color getHighlightColor()
  {
    return (Color)this.highlightColor.get();
  }
  
  public final void setHighlightColor(Color paramColor)
  {
    this.highlightColor.set(paramColor);
    this.transparentHighlightColor.set(Color.color(paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue(), 0.5D));
  }
  
  public final ObjectProperty<Color> highlightColorProperty()
  {
    return this.highlightColor;
  }
  
  public final Color getTransparentHighlightColor()
  {
    return (Color)this.transparentHighlightColor.get();
  }
  
  public final ObjectProperty<Color> transparentHighlightColorProperty()
  {
    return this.transparentHighlightColor;
  }
  
  public final String getCssColor()
  {
    return (String)this.cssColor.get();
  }
  
  public final StringProperty cssColorProperty()
  {
    return this.cssColor;
  }
  
  public final Shape getSectionArea()
  {
    return (Shape)this.sectionArea.get();
  }
  
  public final void setSectionArea(Shape paramShape)
  {
    this.sectionArea.set(paramShape);
  }
  
  public final ObjectProperty<Shape> sectionAreaProperty()
  {
    return this.sectionArea;
  }
  
  public final Shape getFilledArea()
  {
    return (Shape)this.filledArea.get();
  }
  
  public final void setFilledArea(Shape paramShape)
  {
    this.filledArea.set(paramShape);
  }
  
  public final ObjectProperty<Shape> filledAreaProperty()
  {
    return this.filledArea;
  }
  
  public final String getText()
  {
    return (String)this.text.get();
  }
  
  public final void setText(String paramString)
  {
    this.text.set(paramString);
  }
  
  public final StringProperty textProperty()
  {
    return this.text;
  }
  
  public final Paint getPaint()
  {
    return (Paint)this.paint.get();
  }
  
  public final void setPaint(Paint paramPaint)
  {
    this.paint.set(paramPaint);
  }
  
  public final ObjectProperty<Paint> paintProperty()
  {
    return this.paint;
  }
  
  public boolean contains(double paramDouble)
  {
    return (Double.compare(paramDouble, this.start.get()) >= 0) && (Double.compare(paramDouble, this.stop.get()) <= 0);
  }
  
  public boolean equals(Section paramSection)
  {
    return (Double.compare(paramSection.getStart(), getStart()) == 0) && (Double.compare(paramSection.getStop(), getStop()) == 0) && (paramSection.getColor().equals(getColor())) && (paramSection.getText().equals(getText()));
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Section: ").append("\n");
    localStringBuilder.append("startValue: ").append(this.start.get()).append("\n");
    localStringBuilder.append("stopValue : ").append(this.stop.get()).append("\n");
    localStringBuilder.append("color     : ").append(this.color.toString());
    return localStringBuilder.toString();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Section.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */