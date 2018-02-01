package jfxtras.labs.scene.control.gauge;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Content
{
  private ObjectProperty<MatrixColor> color;
  private ObjectProperty<Type> type;
  private ObjectProperty<Point2D> origin;
  private ObjectProperty<Rectangle> area;
  private ObjectProperty<Effect> effect;
  private ObjectProperty<PostEffect> postEffect;
  private IntegerProperty pause;
  private IntegerProperty lapse;
  private ObjectProperty<RotationOrder> order;
  private BooleanProperty clear;
  private StringProperty bmpName;
  private StringProperty txtContent;
  private ObjectProperty<MatrixFont> matrixFont;
  private ObjectProperty<Gap> fontGap;
  private ObjectProperty<Align> txtAlign;
  
  public Content()
  {
    this(MatrixColor.RED, Type.TEXT, new Point2D(0.0D, 0.0D), new Rectangle(0.0D, 0.0D, 20000.0D, 20000.0D), "", MatrixFont.FF_5x7, Gap.SIMPLE, Align.LEFT, Effect.NONE, PostEffect.STOP, 0, 10, RotationOrder.SINGLE, false);
  }
  
  public Content(MatrixColor paramMatrixColor, Type paramType, Point2D paramPoint2D, Rectangle paramRectangle, String paramString, MatrixFont paramMatrixFont, Gap paramGap, Align paramAlign, Effect paramEffect, PostEffect paramPostEffect, int paramInt1, int paramInt2, RotationOrder paramRotationOrder, boolean paramBoolean)
  {
    this.color = new SimpleObjectProperty(paramMatrixColor);
    this.type = new SimpleObjectProperty(paramType);
    this.origin = new SimpleObjectProperty(paramPoint2D);
    this.area = new SimpleObjectProperty(paramRectangle);
    if (((Type)this.type.get()).equals(Type.IMAGE))
    {
      this.bmpName = new SimpleStringProperty(paramString);
      this.txtContent = new SimpleStringProperty("");
    }
    else if (((Type)this.type.get()).equals(Type.TEXT))
    {
      this.bmpName = new SimpleStringProperty("");
      this.txtContent = new SimpleStringProperty(paramString);
    }
    this.matrixFont = new SimpleObjectProperty(paramMatrixFont);
    this.fontGap = new SimpleObjectProperty(paramGap);
    this.txtAlign = new SimpleObjectProperty(paramAlign);
    this.effect = new SimpleObjectProperty(paramEffect);
    this.postEffect = new SimpleObjectProperty(paramPostEffect);
    this.pause = new SimpleIntegerProperty(paramInt1);
    this.lapse = new SimpleIntegerProperty(paramInt2);
    this.order = new SimpleObjectProperty(paramRotationOrder);
    this.clear = new SimpleBooleanProperty(paramBoolean);
  }
  
  public final MatrixColor getColor()
  {
    return (MatrixColor)this.color.get();
  }
  
  public void setColor(MatrixColor paramMatrixColor)
  {
    this.color.set(paramMatrixColor);
  }
  
  public final ObjectProperty<MatrixColor> colorProperty()
  {
    return this.color;
  }
  
  public final Type getType()
  {
    return (Type)this.type.get();
  }
  
  public void setType(Type paramType)
  {
    this.type.set(paramType);
  }
  
  public final ObjectProperty<Type> typeProperty()
  {
    return this.type;
  }
  
  public final Point2D getOrigin()
  {
    return (Point2D)this.origin.get();
  }
  
  public void setOrigin(Point2D paramPoint2D)
  {
    this.origin.set(paramPoint2D);
  }
  
  public final ObjectProperty<Point2D> originProperty()
  {
    return this.origin;
  }
  
  public final Rectangle getArea()
  {
    return (Rectangle)this.area.get();
  }
  
  public void setArea(Rectangle paramRectangle)
  {
    this.area.set(paramRectangle);
  }
  
  public final ObjectProperty<Rectangle> areaProperty()
  {
    return this.area;
  }
  
  public final String getBmpName()
  {
    return (String)this.bmpName.get();
  }
  
  public void setBmpName(String paramString)
  {
    this.bmpName.set(paramString);
  }
  
  public final StringProperty bmpNameProperty()
  {
    return this.bmpName;
  }
  
  public final String getTxtContent()
  {
    return (String)this.txtContent.get();
  }
  
  public void setTxtContent(String paramString)
  {
    this.txtContent.set(paramString);
  }
  
  public final StringProperty txtContentProperty()
  {
    return this.txtContent;
  }
  
  public final MatrixFont getMatrixFont()
  {
    return (MatrixFont)this.matrixFont.get();
  }
  
  public void setMatrixFont(MatrixFont paramMatrixFont)
  {
    this.matrixFont.set(paramMatrixFont);
  }
  
  public final ObjectProperty<MatrixFont> matrixFontProperty()
  {
    return this.matrixFont;
  }
  
  public final Gap getFontGap()
  {
    return (Gap)this.fontGap.get();
  }
  
  public void setFontGap(Gap paramGap)
  {
    this.fontGap.set(paramGap);
  }
  
  public final ObjectProperty<Gap> fontGapProperty()
  {
    return this.fontGap;
  }
  
  public final Align getTxtAlign()
  {
    return (Align)this.txtAlign.get();
  }
  
  public void setTxtAlign(Align paramAlign)
  {
    this.txtAlign.set(paramAlign);
  }
  
  public final ObjectProperty<Align> txtAlignProperty()
  {
    return this.txtAlign;
  }
  
  public final Effect getEffect()
  {
    return (Effect)this.effect.get();
  }
  
  public void setEffect(Effect paramEffect)
  {
    this.effect.set(paramEffect);
  }
  
  public final ObjectProperty<Effect> effectProperty()
  {
    return this.effect;
  }
  
  public final PostEffect getPostEffect()
  {
    return (PostEffect)this.postEffect.get();
  }
  
  public void setPostEffect(PostEffect paramPostEffect)
  {
    this.postEffect.set(paramPostEffect);
  }
  
  public final ObjectProperty<PostEffect> postEffectProperty()
  {
    return this.postEffect;
  }
  
  public final int getLapse()
  {
    return this.lapse.get();
  }
  
  public void setLapse(int paramInt)
  {
    this.lapse.set(paramInt);
  }
  
  public final IntegerProperty lapseProperty()
  {
    return this.lapse;
  }
  
  public final int getPause()
  {
    return this.pause.get();
  }
  
  public void setPause(int paramInt)
  {
    this.pause.set(paramInt);
  }
  
  public final IntegerProperty pauseProperty()
  {
    return this.pause;
  }
  
  public final RotationOrder getOrder()
  {
    return (RotationOrder)this.order.get();
  }
  
  public void setOrder(RotationOrder paramRotationOrder)
  {
    this.order.set(paramRotationOrder);
  }
  
  public final ObjectProperty<RotationOrder> orderProperty()
  {
    return this.order;
  }
  
  public final boolean getClear()
  {
    return this.clear.get();
  }
  
  public void setClear(boolean paramBoolean)
  {
    this.clear.set(paramBoolean);
  }
  
  public final BooleanProperty clearProperty()
  {
    return this.clear;
  }
  
  public boolean equals(Content paramContent)
  {
    return (paramContent.getType().equals(getType())) && (paramContent.getColor().equals(getColor())) && (paramContent.getMatrixFont().equals(getMatrixFont())) && (paramContent.getOrigin().equals(getOrigin())) && (paramContent.getArea().getBoundsInLocal().equals(getArea().getBoundsInLocal())) && (paramContent.getTxtContent().equals(getTxtContent())) && (paramContent.getBmpName().equals(getBmpName())) && (paramContent.getEffect().equals(getEffect())) && (paramContent.getPostEffect().equals(getPostEffect())) && (paramContent.getPause() == getPause()) && (paramContent.getLapse() == getLapse()) && (paramContent.getOrder().equals(getOrder())) && (paramContent.getClear() == getClear());
  }
  
  public static enum RotationOrder
  {
    SINGLE,  FIRST,  SECOND;
    
    private RotationOrder() {}
  }
  
  public static enum Align
  {
    LEFT,  CENTER,  RIGHT;
    
    private Align() {}
  }
  
  public static enum Gap
  {
    NULL(0),  SIMPLE(1),  DOUBLE(2);
    
    private final int gapWidth;
    
    private Gap(int paramInt)
    {
      this.gapWidth = paramInt;
    }
    
    public int getGapWidth()
    {
      return this.gapWidth;
    }
  }
  
  public static enum MatrixFont
  {
    NONE,  FF_5x7,  FF_7x7,  FF_7x9,  FF_8x14,  FF_10x14,  FF_8x16,  FF_10x16,  FF_15x32;
    
    private MatrixFont() {}
  }
  
  public static enum PostEffect
  {
    STOP,  PAUSE,  REPEAT;
    
    private PostEffect() {}
  }
  
  public static enum Effect
  {
    NONE,  SCROLL_LEFT,  SCROLL_RIGHT,  SCROLL_UP,  SCROLL_DOWN,  MIRROR,  BLINK,  BLINK_4,  BLINK_10,  SPRAY;
    
    private Effect() {}
  }
  
  public static enum Type
  {
    IMAGE,  TEXT;
    
    private Type() {}
  }
  
  public static enum MatrixColor
  {
    RED,  GREEN,  BLUE,  YELLOW,  RGB;
    
    private MatrixColor() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Content.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */