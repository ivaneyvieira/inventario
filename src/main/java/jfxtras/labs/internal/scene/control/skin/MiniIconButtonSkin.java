package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import jfxtras.labs.scene.control.MiniIconButton;
import jfxtras.labs.scene.control.MiniIconButton.AnimationType;

public class MiniIconButtonSkin
  extends ButtonSkin
{
  private final TranslateTransition jumpTransition = new TranslateTransition();
  private final Timeline blinkTimeline = new Timeline();
  private KeyFrame kf;
  private static final double JUMP_DISTANCE = 4.0D;
  private static final double MARGIN = 6.0D;
  private static final double MINIMUM_OPACITY_FOR_BLINKING = 0.0D;
  
  public MiniIconButtonSkin(MiniIconButton paramMiniIconButton)
  {
    super(paramMiniIconButton);
    setMiniIcon(paramMiniIconButton.getMiniIcon());
    positionMiniIcon(paramMiniIconButton);
    calculateAndSetNewMiniIconSize(paramMiniIconButton);
    defaultConfigJumpingAnimation();
    defaultConfigBlinkingAnimation();
    configureJumping(paramMiniIconButton);
    configureBlinking(paramMiniIconButton);
    startAnimation(paramMiniIconButton);
    addImageViewSizeBindings();
    addChangeListeners();
  }
  
  protected void layoutChildren()
  {
    super.layoutChildren();
    MiniIconButton localMiniIconButton = (MiniIconButton)getSkinnable();
    ImageView localImageView = localMiniIconButton.getMiniIcon();
    double d1 = getWidth();
    double d2 = getHeight();
    double d3 = getBaselineOffset();
    Pos localPos = StackPane.getAlignment(localImageView);
    layoutInArea(localImageView, 0.0D, 0.0D, d1, d2, d3, getMargin(localImageView), localPos != null ? localPos.getHpos() : getAlignment().getHpos(), localPos != null ? localPos.getVpos() : getAlignment().getVpos());
  }
  
  private void addChangeListeners()
  {
    final MiniIconButton localMiniIconButton = (MiniIconButton)getSkinnable();
    localMiniIconButton.animationDurationProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        MiniIconButtonSkin.this.stopAnimation(localMiniIconButton);
        MiniIconButtonSkin.this.configureJumping(localMiniIconButton);
        MiniIconButtonSkin.this.configureBlinking(localMiniIconButton);
        MiniIconButtonSkin.this.startAnimation(localMiniIconButton);
        MiniIconButtonSkin.this.requestLayout();
      }
    });
    localMiniIconButton.animationTypeProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends AnimationType> paramAnonymousObservableValue, AnimationType paramAnonymousAnimationType1, AnimationType paramAnonymousAnimationType2)
      {
        MiniIconButtonSkin.this.startAnimation(localMiniIconButton);
        MiniIconButtonSkin.this.requestLayout();
      }
    });
    localMiniIconButton.miniIconPositionProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Pos> paramAnonymousObservableValue, Pos paramAnonymousPos1, Pos paramAnonymousPos2)
      {
        StackPane.setAlignment(localMiniIconButton.getMiniIcon(), paramAnonymousPos2);
        MiniIconButtonSkin.this.requestLayout();
      }
    });
    localMiniIconButton.miniIconProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends ImageView> paramAnonymousObservableValue, ImageView paramAnonymousImageView1, ImageView paramAnonymousImageView2)
      {
        MiniIconButtonSkin.this.stopAnimation(localMiniIconButton);
        MiniIconButtonSkin.this.changeMiniIcon(paramAnonymousImageView1, paramAnonymousImageView2);
        MiniIconButtonSkin.this.positionMiniIcon(localMiniIconButton);
        MiniIconButtonSkin.this.configureJumping(localMiniIconButton);
        MiniIconButtonSkin.this.configureBlinking(localMiniIconButton);
        MiniIconButtonSkin.this.calculateAndSetNewMiniIconSize(localMiniIconButton);
        MiniIconButtonSkin.this.startAnimation(localMiniIconButton);
        MiniIconButtonSkin.this.requestLayout();
      }
    });
    localMiniIconButton.miniIconRatioProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        MiniIconButtonSkin.this.stopAnimation(localMiniIconButton);
        MiniIconButtonSkin.this.calculateAndSetNewMiniIconSize(localMiniIconButton);
        MiniIconButtonSkin.this.startAnimation(localMiniIconButton);
        MiniIconButtonSkin.this.requestLayout();
      }
    });
  }
  
  private void changeMiniIcon(ImageView paramImageView1, ImageView paramImageView2)
  {
    getChildren().remove(paramImageView1);
    setMiniIcon(paramImageView2);
  }
  
  private void setMiniIcon(ImageView paramImageView)
  {
    getChildren().add(paramImageView);
  }
  
  private void configureBlinking(MiniIconButton paramMiniIconButton)
  {
    this.blinkTimeline.getKeyFrames().remove(this.kf);
    KeyValue localKeyValue = new KeyValue(paramMiniIconButton.getMiniIcon().opacityProperty(), Double.valueOf(0.0D));
    this.kf = new KeyFrame(Duration.millis(paramMiniIconButton.getAnimationDuration()), new KeyValue[] { localKeyValue });
    this.blinkTimeline.getKeyFrames().add(this.kf);
  }
  
  private void configureJumping(MiniIconButton paramMiniIconButton)
  {
    ImageView localImageView = paramMiniIconButton.getMiniIcon();
    this.jumpTransition.setNode(localImageView);
    this.jumpTransition.setDuration(Duration.millis(paramMiniIconButton.getAnimationDuration()));
  }
  
  private void positionMiniIcon(MiniIconButton paramMiniIconButton)
  {
    ImageView localImageView = paramMiniIconButton.getMiniIcon();
    StackPane.setAlignment(localImageView, paramMiniIconButton.getMiniIconPosition());
    StackPane.setMargin(localImageView, new Insets(6.0D, 6.0D, 6.0D, 6.0D));
  }
  
  private void addImageViewSizeBindings()
  {
    final MiniIconButton localMiniIconButton = (MiniIconButton)getSkinnable();
    widthProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue paramAnonymousObservableValue, Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        MiniIconButtonSkin.this.calculateAndSetNewMiniIconSize(localMiniIconButton);
        MiniIconButtonSkin.this.requestLayout();
      }
    });
    heightProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue paramAnonymousObservableValue, Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        MiniIconButtonSkin.this.calculateAndSetNewMiniIconSize(localMiniIconButton);
        MiniIconButtonSkin.this.requestLayout();
      }
    });
  }
  
  private void calculateAndSetNewMiniIconSize(MiniIconButton paramMiniIconButton)
  {
    ImageView localImageView = paramMiniIconButton.getMiniIcon();
    double d1 = getWidth() * paramMiniIconButton.getMiniIconRatio();
    double d2 = getHeight() * paramMiniIconButton.getMiniIconRatio();
    double d3 = d1;
    double d4 = d2;
    if (localImageView.getImage() != null)
    {
      double d5 = localImageView.getImage().getWidth();
      double d6 = localImageView.getImage().getHeight();
      d3 = Math.min(d5, d1);
      d4 = Math.min(d6, d2);
    }
    localImageView.setPreserveRatio(true);
    if (d3 > 0.0D) {
      localImageView.setFitWidth(d3);
    }
    if (d4 > 0.0D) {
      localImageView.setFitHeight(d4);
    }
  }
  
  private void startAnimation(MiniIconButton paramMiniIconButton)
  {
    switch (paramMiniIconButton.getAnimationType())
    {
    case BLINK: 
      this.jumpTransition.stop();
      this.blinkTimeline.play();
      break;
    case JUMP: 
      this.blinkTimeline.stop();
      this.jumpTransition.play();
      break;
    case NONE: 
    default: 
      this.blinkTimeline.stop();
      this.jumpTransition.stop();
    }
  }
  
  private void stopAnimation(MiniIconButton paramMiniIconButton)
  {
    ImageView localImageView = paramMiniIconButton.getMiniIcon();
    this.jumpTransition.stop();
    this.blinkTimeline.stop();
    localImageView.setOpacity(1.0D);
    localImageView.setTranslateY(0.0D);
  }
  
  private void defaultConfigJumpingAnimation()
  {
    this.jumpTransition.setFromY(0.0D);
    this.jumpTransition.setToY(-4.0D);
    this.jumpTransition.setCycleCount(-1);
    this.jumpTransition.setAutoReverse(true);
    this.jumpTransition.setInterpolator(Interpolator.EASE_BOTH);
  }
  
  private void defaultConfigBlinkingAnimation()
  {
    this.blinkTimeline.setCycleCount(-1);
    this.blinkTimeline.setAutoReverse(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/MiniIconButtonSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */