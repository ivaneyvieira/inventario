package jfxtras.labs.scene.control.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Builder;

public class ContentBuilder
  implements Builder<Content>
{
  private HashMap<String, Property> properties = new HashMap();
  
  public static final ContentBuilder create()
  {
    return new ContentBuilder();
  }
  
  public final ContentBuilder color(Content.MatrixColor paramMatrixColor)
  {
    this.properties.put("color", new SimpleObjectProperty(paramMatrixColor));
    return this;
  }
  
  public final ContentBuilder type(Content.Type paramType)
  {
    this.properties.put("type", new SimpleObjectProperty(paramType));
    return this;
  }
  
  public final ContentBuilder origin(Point2D paramPoint2D)
  {
    this.properties.put("origin", new SimpleObjectProperty(paramPoint2D));
    return this;
  }
  
  public final ContentBuilder origin(int paramInt1, int paramInt2)
  {
    this.properties.put("origin", new SimpleObjectProperty(new Point2D(paramInt1, paramInt2)));
    return this;
  }
  
  public final ContentBuilder area(Rectangle paramRectangle)
  {
    Rectangle localRectangle = new Rectangle(paramRectangle.getX(), paramRectangle.getY(), paramRectangle.getX() + paramRectangle.getWidth(), paramRectangle.getY() + paramRectangle.getHeight());
    this.properties.put("area", new SimpleObjectProperty(localRectangle));
    return this;
  }
  
  public final ContentBuilder area(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.properties.put("area", new SimpleObjectProperty(new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4)));
    return this;
  }
  
  public final ContentBuilder bmpName(String paramString)
  {
    this.properties.put("bmpName", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final ContentBuilder txtContent(String paramString)
  {
    this.properties.put("txtContent", new SimpleStringProperty(paramString));
    return this;
  }
  
  public final ContentBuilder font(Content.MatrixFont paramMatrixFont)
  {
    this.properties.put("matrixFont", new SimpleObjectProperty(paramMatrixFont));
    return this;
  }
  
  public final ContentBuilder fontGap(Content.Gap paramGap)
  {
    this.properties.put("fontGap", new SimpleObjectProperty(paramGap));
    return this;
  }
  
  public final ContentBuilder align(Content.Align paramAlign)
  {
    this.properties.put("align", new SimpleObjectProperty(paramAlign));
    return this;
  }
  
  public final ContentBuilder effect(Content.Effect paramEffect)
  {
    this.properties.put("effect", new SimpleObjectProperty(paramEffect));
    return this;
  }
  
  public final ContentBuilder postEffect(Content.PostEffect paramPostEffect)
  {
    this.properties.put("postEffect", new SimpleObjectProperty(paramPostEffect));
    return this;
  }
  
  public final ContentBuilder pause(Integer paramInteger)
  {
    this.properties.put("pause", new SimpleIntegerProperty(paramInteger.intValue()));
    return this;
  }
  
  public final ContentBuilder lapse(Integer paramInteger)
  {
    this.properties.put("lapse", new SimpleIntegerProperty(paramInteger.intValue()));
    return this;
  }
  
  public final ContentBuilder order(Content.RotationOrder paramRotationOrder)
  {
    this.properties.put("order", new SimpleObjectProperty(paramRotationOrder));
    return this;
  }
  
  public final ContentBuilder clear(Boolean paramBoolean)
  {
    this.properties.put("clear", new SimpleBooleanProperty(paramBoolean.booleanValue()));
    return this;
  }
  
  public Content build()
  {
    Content localContent = new Content();
    Iterator localIterator = this.properties.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ("color".equals(str)) {
        localContent.setColor((Content.MatrixColor)((ObjectProperty)this.properties.get(str)).get());
      } else if ("type".equals(str)) {
        localContent.setType((Content.Type)((ObjectProperty)this.properties.get(str)).get());
      } else if ("origin".equals(str)) {
        localContent.setOrigin((Point2D)((ObjectProperty)this.properties.get(str)).get());
      } else if ("area".equals(str)) {
        localContent.setArea((Rectangle)((ObjectProperty)this.properties.get(str)).get());
      } else if ("bmpName".equals(str)) {
        localContent.setBmpName((String)((StringProperty)this.properties.get(str)).get());
      } else if ("txtContent".equals(str)) {
        localContent.setTxtContent((String)((StringProperty)this.properties.get(str)).get());
      } else if ("matrixFont".equals(str)) {
        localContent.setMatrixFont((Content.MatrixFont)((ObjectProperty)this.properties.get(str)).get());
      } else if ("fontGap".equals(str)) {
        localContent.setFontGap((Content.Gap)((ObjectProperty)this.properties.get(str)).get());
      } else if ("align".equals(str)) {
        localContent.setTxtAlign((Content.Align)((ObjectProperty)this.properties.get(str)).get());
      } else if ("effect".equals(str)) {
        localContent.setEffect((Content.Effect)((ObjectProperty)this.properties.get(str)).get());
      } else if ("postEffect".equals(str)) {
        localContent.setPostEffect((Content.PostEffect)((ObjectProperty)this.properties.get(str)).get());
      } else if ("pause".equals(str)) {
        localContent.setPause(((IntegerProperty)this.properties.get(str)).get());
      } else if ("lapse".equals(str)) {
        localContent.setLapse(((IntegerProperty)this.properties.get(str)).get());
      } else if ("order".equals(str)) {
        localContent.setOrder((Content.RotationOrder)((ObjectProperty)this.properties.get(str)).get());
      } else if ("clear".equals(str)) {
        localContent.setClear(((BooleanProperty)this.properties.get(str)).get());
      }
    }
    return localContent;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/ContentBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */