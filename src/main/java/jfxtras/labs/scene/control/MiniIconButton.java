package jfxtras.labs.scene.control;

import java.net.URL;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;

public class MiniIconButton
  extends Button
{
  private ObjectProperty<ImageView> miniIcon;
  private ObjectProperty<AnimationType> animationType = new SimpleObjectProperty(this, "animationType", AnimationType.NONE);
  private DoubleProperty animationDuration = new SimpleDoubleProperty(this, "animationDuration", 500.0D);
  private ObjectProperty<Pos> miniIconPosition = new SimpleObjectProperty(this, "miniIconPosition", Pos.TOP_RIGHT);
  private DoubleProperty miniIconRatio = new SimpleDoubleProperty(this, "miniIconRatio", 0.25D);
  
  public MiniIconButton()
  {
    init();
  }
  
  public MiniIconButton(ImageView paramImageView)
  {
    setMiniIcon(paramImageView);
    init();
  }
  
  public MiniIconButton(String paramString, ImageView paramImageView)
  {
    super(paramString);
    setMiniIcon(paramImageView);
    init();
  }
  
  public MiniIconButton(Node paramNode, ImageView paramImageView)
  {
    super(null, paramNode);
    setMiniIcon(paramImageView);
    init();
  }
  
  public MiniIconButton(String paramString, Node paramNode, ImageView paramImageView)
  {
    super(paramString, paramNode);
    setMiniIcon(paramImageView);
    init();
  }
  
  public final ObjectProperty<ImageView> miniIconProperty()
  {
    if (this.miniIcon == null) {
      this.miniIcon = new ObjectPropertyBase()
      {
        public Object getBean()
        {
          return MiniIconButton.this;
        }
        
        public String getName()
        {
          return "miniIcon";
        }
      };
    }
    return this.miniIcon;
  }
  
  public void setMiniIcon(ImageView paramImageView)
  {
    miniIconProperty().setValue(paramImageView);
  }
  
  public ImageView getMiniIcon()
  {
    return this.miniIcon == null ? null : (ImageView)this.miniIcon.getValue();
  }
  
  public final ObjectProperty<AnimationType> animationTypeProperty()
  {
    return this.animationType;
  }
  
  public AnimationType getAnimationType()
  {
    return (AnimationType)this.animationType.getValue();
  }
  
  public void setAnimationType(AnimationType paramAnimationType)
  {
    this.animationType.setValue(paramAnimationType);
  }
  
  public final ObjectProperty<Pos> miniIconPositionProperty()
  {
    return this.miniIconPosition;
  }
  
  public final void setMiniIconPosition(Pos paramPos)
  {
    this.miniIconPosition.set(paramPos);
  }
  
  public final Pos getMiniIconPosition()
  {
    return (Pos)this.miniIconPosition.getValue();
  }
  
  public final DoubleProperty miniIconRatioProperty()
  {
    return this.miniIconRatio;
  }
  
  public final void setMiniIconRatio(double paramDouble)
  {
    this.miniIconRatio.set(paramDouble);
  }
  
  public final double getMiniIconRatio()
  {
    return this.miniIconRatio.getValue().doubleValue();
  }
  
  public final DoubleProperty animationDurationProperty()
  {
    return this.animationDuration;
  }
  
  public final void setAnimationDuration(double paramDouble)
  {
    this.animationDuration.set(paramDouble);
  }
  
  public final double getAnimationDuration()
  {
    return this.animationDuration.getValue().doubleValue();
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + getClass().getSimpleName() + ".css").toString();
  }
  
  private void init()
  {
    setStyle(null);
    getStyleClass().add("mini-icon-button");
    setContentDisplay(ContentDisplay.TOP);
    setMiniIconRatioRange();
  }
  
  private void setMiniIconRatioRange()
  {
    this.miniIconRatio.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        if (paramAnonymousNumber2.doubleValue() > 1.0D) {
          MiniIconButton.this.miniIconRatio.set(1.0D);
        } else if (paramAnonymousNumber2.doubleValue() < 0.01D) {
          MiniIconButton.this.miniIconRatio.set(0.01D);
        }
      }
    });
  }
  
  public static enum AnimationType
  {
    NONE,  JUMP,  BLINK;
    
    private AnimationType() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/MiniIconButton.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */