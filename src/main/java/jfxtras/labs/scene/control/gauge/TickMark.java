package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TickMark
{
  private ObjectProperty<Type> type;
  private ObjectProperty<Indicator> indicator;
  private ObjectProperty<Color> indicatorColor;
  private BooleanProperty indicatorVisible;
  private StringProperty label;
  private ObjectProperty<Color> labelColor;
  private BooleanProperty labelVisible;
  private ObjectProperty<Font> labelFont;
  private DoubleProperty labelFontSizeFactor;
  private ObjectProperty<TickLabelOrientation> tickLabelOrientation;
  
  public TickMark()
  {
    this(Type.MINOR, Indicator.LINE, Color.BLACK, "", Color.BLACK, TickLabelOrientation.NORMAL);
  }
  
  public TickMark(Type paramType, Indicator paramIndicator, String paramString)
  {
    this(paramType, paramIndicator, Color.BLACK, paramString, Color.BLACK, TickLabelOrientation.NORMAL);
  }
  
  public TickMark(Type paramType, Indicator paramIndicator, Color paramColor1, String paramString, Color paramColor2, TickLabelOrientation paramTickLabelOrientation)
  {
    this.type = new SimpleObjectProperty(paramType);
    this.indicator = new SimpleObjectProperty(paramIndicator);
    this.indicatorColor = new SimpleObjectProperty(paramColor1);
    this.indicatorVisible = new SimpleBooleanProperty(true);
    this.label = new SimpleStringProperty(paramString);
    this.labelColor = new SimpleObjectProperty(paramColor2);
    this.labelVisible = new SimpleBooleanProperty(paramType != Type.MINOR);
    this.labelFont = new SimpleObjectProperty(Font.font("Verdana", FontWeight.NORMAL, 8.0D));
    this.labelFontSizeFactor = new SimpleDoubleProperty(0.035D);
    this.tickLabelOrientation = new SimpleObjectProperty(paramTickLabelOrientation);
  }
  
  public final Type getType()
  {
    return (Type)this.type.get();
  }
  
  public final void setType(Type paramType)
  {
    this.type.set(paramType);
  }
  
  public final ObjectProperty<Type> typeProperty()
  {
    return this.type;
  }
  
  public final Indicator getIndicator()
  {
    return (Indicator)this.indicator.get();
  }
  
  public final void setIndicator(Indicator paramIndicator)
  {
    this.indicator.set(paramIndicator);
  }
  
  public final ObjectProperty<Indicator> indicatorProperty()
  {
    return this.indicator;
  }
  
  public final Color getIndicatorColor()
  {
    return (Color)this.indicatorColor.get();
  }
  
  public final void setIndicatorColor(Color paramColor)
  {
    this.indicatorColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> indicatorColorProperty()
  {
    return this.indicatorColor;
  }
  
  public final boolean isIndicatorVisible()
  {
    return this.indicatorVisible.get();
  }
  
  public final void setIndicatorVisible(boolean paramBoolean)
  {
    this.indicatorVisible.set(paramBoolean);
  }
  
  public final BooleanProperty indicatorVisibleProperty()
  {
    return this.indicatorVisible;
  }
  
  public final String getLabel()
  {
    return (String)this.label.get();
  }
  
  public final void setLabel(String paramString)
  {
    this.label.set(paramString);
  }
  
  public final StringProperty labelProperty()
  {
    return this.label;
  }
  
  public final Color getLabelColor()
  {
    return (Color)this.labelColor.get();
  }
  
  public final void setLabelColor(Color paramColor)
  {
    this.labelColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> labelColorProperty()
  {
    return this.labelColor;
  }
  
  public final boolean isLabelVisible()
  {
    return this.labelVisible.get();
  }
  
  public final void setLabelVisible(boolean paramBoolean)
  {
    this.labelVisible.set(paramBoolean);
  }
  
  public final BooleanProperty labelVisibleProperty()
  {
    return this.labelVisible;
  }
  
  public final Font getLabelFont()
  {
    return (Font)this.labelFont.get();
  }
  
  public final void setLabelFont(Font paramFont)
  {
    this.labelFont.set(paramFont);
  }
  
  public final ObjectProperty<Font> labelFontProperty()
  {
    return this.labelFont;
  }
  
  public final double getLabelFontSizeFactor()
  {
    return this.labelFontSizeFactor.get();
  }
  
  public final void setLabelFontSizeFactor(double paramDouble)
  {
    this.labelFontSizeFactor.set(paramDouble > 1.0D ? 0.035D : paramDouble < 0.0D ? 0.035D : paramDouble);
  }
  
  public final DoubleProperty labelFontSizeFactorProperty()
  {
    return this.labelFontSizeFactor;
  }
  
  public final TickLabelOrientation getTickLabelOrientation()
  {
    return (TickLabelOrientation)this.tickLabelOrientation.get();
  }
  
  public final void setTickLabelOrientation(TickLabelOrientation paramTickLabelOrientation)
  {
    this.tickLabelOrientation.set(paramTickLabelOrientation);
  }
  
  public final ObjectProperty<TickLabelOrientation> tickLabelOrienationObjectProperty()
  {
    return this.tickLabelOrientation;
  }
  
  public static enum TickLabelOrientation
  {
    NORMAL,  HORIZONTAL,  TANGENT;
    
    private TickLabelOrientation() {}
  }
  
  public static enum Indicator
  {
    LINE,  CIRCLE,  TRIANGLE,  SQUARE;
    
    private Indicator() {}
  }
  
  public static enum Type
  {
    MINOR(0.0015D, 0.3D, 0.0133333333D, 1.0D),  MEDIUM(0.0025D, 0.5D, 0.02D, 1.0D),  MAJOR(0.005D, 1.0D, 0.03D, 1.0D);
    
    public double strokeWidthFactor;
    public double minStrokeWidth;
    public double strokeLengthFactor;
    public double minStrokeLength;
    
    private Type(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
    {
      this.strokeWidthFactor = paramDouble1;
      this.minStrokeWidth = paramDouble2;
      this.strokeLengthFactor = paramDouble3;
      this.minStrokeLength = paramDouble4;
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/TickMark.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */