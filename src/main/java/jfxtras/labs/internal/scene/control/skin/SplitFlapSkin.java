package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.CacheHint;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import jfxtras.labs.internal.scene.control.behavior.SplitFlapBehavior;
import jfxtras.labs.scene.control.gauge.SplitFlap;

public class SplitFlapSkin
  extends SkinBase<SplitFlap, SplitFlapBehavior>
{
  private SplitFlap control;
  private static double MIN_FLIP_TIME = 1.66666666666667E7D;
  private final AudioClip SOUND1 = new AudioClip(getClass().getResource("/jfxtras/labs/scene/control/gauge/flap.mp3").toExternalForm());
  private final AudioClip SOUND2 = new AudioClip(getClass().getResource("/jfxtras/labs/scene/control/gauge/flap1.mp3").toExternalForm());
  private final AudioClip SOUND3 = new AudioClip(getClass().getResource("/jfxtras/labs/scene/control/gauge/flap2.mp3").toExternalForm());
  private boolean isDirty;
  private boolean initialized;
  private Group background;
  private Group fixture;
  private Group flip;
  private Group frame;
  private Shape upper;
  private Text upperText;
  private Shape upperNext;
  private Text upperNextText;
  private Shape lower;
  private Text lowerText;
  private Text lowerNextText;
  private ArrayList<String> selectedSet;
  private int currentSelectionIndex;
  private int nextSelectionIndex;
  private Rotate rotate;
  private Rotate lowerFlipVert;
  private double angleStep;
  private double currentAngle;
  private boolean flipping;
  private int lastFlapDirection;
  private AnimationTimer timer;
  
  public SplitFlapSkin(SplitFlap paramSplitFlap)
  {
    super(paramSplitFlap, new SplitFlapBehavior(paramSplitFlap));
    this.control = paramSplitFlap;
    this.initialized = false;
    this.isDirty = false;
    this.background = new Group();
    this.fixture = new Group();
    this.flip = new Group();
    this.frame = new Group();
    this.upperText = new Text(this.control.getText());
    this.lowerText = new Text(this.control.getText());
    this.upperNextText = new Text(this.control.getNextText());
    this.lowerNextText = new Text(this.control.getNextText());
    this.selectedSet = new ArrayList(64);
    this.currentSelectionIndex = 0;
    this.nextSelectionIndex = 1;
    this.rotate = new Rotate();
    this.angleStep = (180.0D / (this.control.getFlipTimeInMs() * 1000000L / MIN_FLIP_TIME));
    this.currentAngle = 0.0D;
    this.flipping = false;
    this.lastFlapDirection = 0;
    this.timer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        if (SplitFlapSkin.this.initialized) {
          if (!SplitFlapSkin.this.control.isCountdownMode()) {
            SplitFlapSkin.this.flipForward(SplitFlapSkin.this.angleStep);
          } else {
            SplitFlapSkin.this.flipBackward(SplitFlapSkin.this.angleStep);
          }
        }
      }
    };
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(132.0D, 227.0D);
    }
    this.rotate.setAxis(Rotate.X_AXIS);
    this.rotate.setPivotY(this.control.getPrefHeight() * 0.4625550661D);
    this.lowerFlipVert = new Rotate();
    this.selectedSet.clear();
    this.selectedSet.addAll(this.control.getSelectedSet());
    this.upperText.setVisible(!this.control.isImageMode());
    this.upperNextText.setVisible(!this.control.isImageMode());
    this.lowerText.setVisible(!this.control.isImageMode());
    this.lowerNextText.setVisible(!this.control.isImageMode());
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    registerChangeListener(this.control.colorProperty(), "COLOR");
    registerChangeListener(this.control.textColorProperty(), "TEXT_COLOR");
    registerChangeListener(this.control.textProperty(), "TEXT");
    registerChangeListener(this.control.flipTimeInMsProperty(), "FLIP_TIME");
    registerChangeListener(this.control.frameVisibleProperty(), "FRAME_VISIBILITY");
    registerChangeListener(this.control.backgroundVisibleProperty(), "BACKGROUND_VISIBILITY");
    registerChangeListener(this.control.countdownModeProperty(), "COUNTDOWN_MODE");
    registerChangeListener(this.control.imageModeProperty(), "IMAGE_MODE");
    this.control.selectionProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends String[]> paramAnonymousObservableValue, String[] paramAnonymousArrayOfString1, String[] paramAnonymousArrayOfString2)
      {
        SplitFlapSkin.this.selectedSet.clear();
        for (String str : paramAnonymousArrayOfString2) {
          SplitFlapSkin.this.selectedSet.add(str);
        }
      }
    });
    this.frame.setVisible(this.control.isFrameVisible());
    this.background.setVisible(this.control.isBackgroundVisible());
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("COLOR".equals(paramString))
    {
      repaint();
    }
    else if ("TEXT_COLOR".equals(paramString))
    {
      repaint();
    }
    else if ("TEXT".equals(paramString))
    {
      if (this.control.getText() != this.selectedSet.get(this.currentSelectionIndex))
      {
        this.timer.stop();
        this.flipping = true;
        this.timer.start();
      }
    }
    else if ("FLIP_TIME".equals(paramString))
    {
      this.angleStep = (180.0D / (this.control.getFlipTimeInMs() * 1000000L / MIN_FLIP_TIME));
    }
    else if ("FRAME_VISIBILITY".equals(paramString))
    {
      this.frame.setVisible(this.control.isFrameVisible());
    }
    else if ("BACKGROUND_VISIBILITY".equals(paramString))
    {
      this.background.setVisible(this.control.isBackgroundVisible());
    }
    else if ("COUNDOWN_MODE".equals(paramString))
    {
      this.currentAngle = 180.0D;
    }
    else if ("SELECTION".equals(paramString))
    {
      this.selectedSet.clear();
      this.selectedSet.addAll(this.control.getSelectedSet());
    }
    else if ("IMAGE_MODE".equals(paramString))
    {
      this.upperText.setVisible(!this.control.isImageMode());
      this.upperNextText.setVisible(!this.control.isImageMode());
      this.lowerText.setVisible(!this.control.isImageMode());
      this.lowerNextText.setVisible(!this.control.isImageMode());
    }
    else if ("PREF_WIDTH".equals(paramString))
    {
      repaint();
    }
    else if ("PREF_HEIGHT".equals(paramString))
    {
      repaint();
    }
  }
  
  public final void repaint()
  {
    this.isDirty = true;
    requestLayout();
  }
  
  public void layoutChildren()
  {
    if (!this.isDirty) {
      return;
    }
    if (!this.initialized) {
      init();
    }
    if (this.control.getScene() != null)
    {
      drawBackground();
      drawFixture();
      drawFlip();
      drawFrame();
      getChildren().setAll(new Node[] { this.background, this.fixture, this.flip, this.frame });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final SplitFlap getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 132.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 227.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  private void flipForward(double paramDouble)
  {
    this.currentAngle += paramDouble;
    if (Double.compare(this.currentAngle, 180.0D) >= 0)
    {
      if (this.control.isSoundOn()) {
        switch (this.control.getSound())
        {
        case SOUND1: 
          this.SOUND1.play();
          break;
        case SOUND2: 
          this.SOUND2.play();
          break;
        case SOUND3: 
          this.SOUND3.play();
        }
      }
      this.currentAngle = 0.0D;
      this.upper.getTransforms().clear();
      this.upperText.getTransforms().clear();
      this.lowerNextText.getTransforms().clear();
      this.lowerNextText.setVisible(false);
      this.lowerFlipVert.setAxis(Rotate.X_AXIS);
      this.lowerFlipVert.setPivotY(this.control.getPrefHeight() * 0.4625550661D);
      this.lowerFlipVert.setAngle(180.0D);
      this.lowerNextText.getTransforms().add(this.lowerFlipVert);
      this.upperText.setVisible(true);
      this.currentSelectionIndex += 1;
      if (this.currentSelectionIndex >= this.selectedSet.size()) {
        this.currentSelectionIndex = 0;
      }
      this.nextSelectionIndex = (this.currentSelectionIndex + 1);
      if (this.nextSelectionIndex >= this.selectedSet.size()) {
        this.nextSelectionIndex = 0;
      }
      if (((String)this.selectedSet.get(this.currentSelectionIndex)).equals(this.control.getText()))
      {
        this.timer.stop();
        this.flipping = false;
      }
      this.upperText.setText((String)this.selectedSet.get(this.currentSelectionIndex));
      this.lowerText.setText((String)this.selectedSet.get(this.currentSelectionIndex));
      this.upperNextText.setText((String)this.selectedSet.get(this.nextSelectionIndex));
      this.lowerNextText.setText((String)this.selectedSet.get(this.nextSelectionIndex));
      double d;
      if (((String)this.selectedSet.get(this.currentSelectionIndex)).length() > 1)
      {
        d = 0.105D * this.frame.getLayoutBounds().getHeight();
        this.upperText.setX(d);
        this.lowerText.setX(d);
      }
      if (((String)this.selectedSet.get(this.nextSelectionIndex)).length() > 1)
      {
        d = 0.105D * this.frame.getLayoutBounds().getHeight();
        this.upperNextText.setX(d);
        this.lowerNextText.setX(d);
      }
    }
    if (this.currentAngle > 90.0D)
    {
      this.upperText.setVisible(false);
      this.lowerNextText.setVisible(true);
    }
    if (this.flipping)
    {
      this.upper.getTransforms().remove(this.rotate);
      this.upperText.getTransforms().remove(this.rotate);
      this.lowerNextText.getTransforms().remove(this.rotate);
      this.rotate.setAngle(this.currentAngle);
      this.upper.getTransforms().add(this.rotate);
      this.upperText.getTransforms().add(this.rotate);
      this.lowerNextText.getTransforms().add(this.rotate);
    }
  }
  
  private void flipBackward(double paramDouble)
  {
    this.currentAngle -= paramDouble;
    if (Double.compare(this.currentAngle, 0.0D) <= 0)
    {
      if (this.control.isSoundOn()) {
        switch (this.control.getSound())
        {
        case SOUND1: 
          this.SOUND1.play();
          break;
        case SOUND2: 
          this.SOUND2.play();
          break;
        case SOUND3: 
          this.SOUND3.play();
        }
      }
      this.currentAngle = 180.0D;
      this.upper.getTransforms().clear();
      this.upperText.getTransforms().clear();
      this.lowerNextText.getTransforms().clear();
      this.lowerNextText.setVisible(true);
      this.lowerFlipVert.setAxis(Rotate.X_AXIS);
      this.lowerFlipVert.setPivotY(this.control.getPrefHeight() * 0.4625550661D);
      this.lowerFlipVert.setAngle(180.0D);
      this.lowerNextText.getTransforms().add(this.lowerFlipVert);
      this.upperText.setVisible(false);
      this.currentSelectionIndex -= 1;
      if (this.currentSelectionIndex < 0) {
        this.currentSelectionIndex = (this.selectedSet.size() - 1);
      }
      this.nextSelectionIndex = (this.currentSelectionIndex - 1);
      if (this.nextSelectionIndex < 0) {
        this.nextSelectionIndex = (this.selectedSet.size() - 1);
      }
      if (((String)this.selectedSet.get(this.currentSelectionIndex)).equals(this.control.getText()))
      {
        this.timer.stop();
        this.flipping = false;
      }
      this.upperText.setText((String)this.selectedSet.get(this.nextSelectionIndex));
      this.lowerText.setText((String)this.selectedSet.get(this.nextSelectionIndex));
      this.upperNextText.setText((String)this.selectedSet.get(this.currentSelectionIndex));
      this.lowerNextText.setText((String)this.selectedSet.get(this.currentSelectionIndex));
      if (((String)this.selectedSet.get(this.currentSelectionIndex)).length() > 1)
      {
        double d = 0.1057268722D * getPrefHeight();
        this.upperText.setX(d);
        this.lowerText.setX(d);
        this.upperNextText.setX(d);
        this.lowerNextText.setX(d);
      }
      this.rotate.setAngle(this.currentAngle);
      this.upper.getTransforms().add(this.rotate);
      this.upperText.getTransforms().add(this.rotate);
      this.lowerNextText.getTransforms().add(this.rotate);
    }
    if (this.currentAngle < 90.0D)
    {
      this.upperText.setVisible(true);
      this.lowerNextText.setVisible(false);
    }
    if (this.flipping)
    {
      this.upper.getTransforms().remove(this.rotate);
      this.upperText.getTransforms().remove(this.rotate);
      this.lowerNextText.getTransforms().remove(this.rotate);
      this.rotate.setAngle(this.currentAngle);
      this.upper.getTransforms().add(this.rotate);
      this.upperText.getTransforms().add(this.rotate);
      this.lowerNextText.getTransforms().add(this.rotate);
    }
  }
  
  private void addMouseEventListener(Shape paramShape, final int paramInt)
  {
    paramShape.setOnMousePressed(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        switch (paramInt)
        {
        case 1: 
          SplitFlapSkin.this.currentAngle = 0.0D;
          SplitFlapSkin.this.checkLastFlapDirection(paramInt);
          SplitFlapSkin.this.control.flipForward();
          SplitFlapSkin.this.lastFlapDirection = paramInt;
          break;
        case -1: 
          SplitFlapSkin.this.currentAngle = 180.0D;
          SplitFlapSkin.this.checkLastFlapDirection(paramInt);
          SplitFlapSkin.this.control.flipBackward();
          SplitFlapSkin.this.lastFlapDirection = paramInt;
        }
      }
    });
  }
  
  private void checkLastFlapDirection(int paramInt)
  {
    if ((paramInt == 1) && (this.lastFlapDirection == -1))
    {
      System.out.println("changed from backward to forward");
    }
    else if ((paramInt == -1) && (this.lastFlapDirection == 1))
    {
      this.rotate.setAngle(this.currentAngle);
      this.upper.getTransforms().add(this.rotate);
      this.upperText.getTransforms().add(this.rotate);
      this.lowerNextText.getTransforms().add(this.rotate);
      System.out.println("changed from forward to backward");
    }
  }
  
  public final void drawBackground()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = this.control.getPrefWidth();
    double d3 = this.control.getPrefHeight();
    this.background.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    this.background.getChildren().add(localRectangle1);
    Rectangle localRectangle2 = new Rectangle(0.0352422907D * d3, 0.0352422907D * d3, d2 - 0.0352422907D * d3, 0.9207048458D * d3);
    LinearGradient localLinearGradient1 = new LinearGradient(0.0D, 0.0352422907D * d3, 0.0D, 0.9559471366D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.BLACK), new Stop(1.0D, Color.rgb(20, 20, 20)) });
    localRectangle2.setFill(localLinearGradient1);
    localRectangle2.setStroke(null);
    double d4 = 0.04405286343612335D * d3;
    double d5 = d4;
    Rectangle localRectangle3 = new Rectangle(d5, 0.8149779736D * d3, d2 - 2.0D * d4, 0.1321585903D * d3);
    localRectangle3.setArcWidth(0.05286343612334802D * d3);
    localRectangle3.setArcHeight(0.05286343612334802D * d3);
    LinearGradient localLinearGradient2 = new LinearGradient(0.0D, 0.8149779736D * d3, 0.0D, 0.8149779736D * d3 + 0.1321585903D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getLowerFlapTopColor()), new Stop(1.0D, this.control.getLowerFlapBottomColor()) });
    localRectangle3.setFill(localLinearGradient2);
    localRectangle3.setStroke(null);
    Rectangle localRectangle4 = new Rectangle(d5, 0.7973568282D * d3, d2 - 2.0D * d4, 0.1321585903D * d3);
    localRectangle4.setArcWidth(0.05286343612334802D * d3);
    localRectangle4.setArcHeight(0.05286343612334802D * d3);
    LinearGradient localLinearGradient3 = new LinearGradient(0.0D, 0.7973568282D * d3, 0.0D, 0.7973568282D * d3 + 0.1321585903D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getLowerFlapTopColor()), new Stop(1.0D, this.control.getLowerFlapBottomColor()) });
    localRectangle4.setFill(localLinearGradient3);
    localRectangle4.setStroke(null);
    Rectangle localRectangle5 = new Rectangle(d5, 0.7797356828D * d3, d2 - 2.0D * d4, 0.1321585903D * d3);
    localRectangle5.setArcWidth(0.05286343612334802D * d3);
    localRectangle5.setArcHeight(0.05286343612334802D * d3);
    LinearGradient localLinearGradient4 = new LinearGradient(0.0D, 0.7797356828D * d3, 0.0D, 0.7797356828D * d3 + 0.1321585903D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getLowerFlapTopColor()), new Stop(1.0D, this.control.getLowerFlapBottomColor()) });
    localRectangle5.setFill(localLinearGradient4);
    localRectangle5.setStroke(null);
    Rectangle localRectangle6 = new Rectangle(d5, 0.7621145374D * d3, d2 - 2.0D * d4, 0.1321585903D * d3);
    localRectangle6.setArcWidth(0.05286343612334802D * d3);
    localRectangle6.setArcHeight(0.05286343612334802D * d3);
    LinearGradient localLinearGradient5 = new LinearGradient(0.0D, 0.7621145374D * d3, 0.0D, 0.7621145374D * d3 + 0.1321585903D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getLowerFlapTopColor()), new Stop(1.0D, this.control.getLowerFlapBottomColor()) });
    localRectangle6.setFill(localLinearGradient5);
    localRectangle6.setStroke(null);
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.01D * localRectangle3.getLayoutBounds().getHeight());
    localInnerShadow1.setHeight(0.01D * localRectangle3.getLayoutBounds().getHeight());
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setOffsetY(-0.066D * localRectangle3.getLayoutBounds().getHeight());
    localInnerShadow1.setRadius(0.001D * localRectangle3.getLayoutBounds().getHeight());
    localInnerShadow1.setColor(Color.BLACK);
    localInnerShadow1.setBlurType(BlurType.GAUSSIAN);
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setWidth(0.04D * localRectangle3.getLayoutBounds().getHeight());
    localInnerShadow2.setHeight(0.04D * localRectangle3.getLayoutBounds().getHeight());
    localInnerShadow2.setOffsetX(0.0D);
    localInnerShadow2.setOffsetY(0.01D * d1);
    localInnerShadow2.setRadius(0.04D * localRectangle3.getLayoutBounds().getHeight());
    localInnerShadow2.setColor(Color.WHITE);
    localInnerShadow2.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow2.inputProperty().set(localInnerShadow1);
    localRectangle3.setEffect(localInnerShadow1);
    localRectangle4.setEffect(localInnerShadow1);
    localRectangle5.setEffect(localInnerShadow1);
    localRectangle6.setEffect(localInnerShadow1);
    this.background.getChildren().addAll(new Node[] { localRectangle2, localRectangle3, localRectangle4, localRectangle5, localRectangle6 });
    this.background.setCache(true);
  }
  
  public void drawFixture()
  {
    double d1 = this.control.getPrefWidth();
    double d2 = this.control.getPrefHeight();
    this.fixture.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle1.setOpacity(0.0D);
    this.fixture.getChildren().add(localRectangle1);
    double d3 = 0.0396475771D * d2;
    double d4 = 0.13656387665198239D * d2;
    double d5 = 0.0308370044D * d2;
    double d6 = 0.1277533039647577D * d2;
    double d7 = 0.04405286343612335D * d2;
    double d8 = d7;
    double d9 = 0.0484581498D * d2;
    double d10 = d1 - d7 - d3;
    double d11 = d1 - d9 - d5;
    Rectangle localRectangle2 = new Rectangle(d10, 0.3920704845814978D * d2, d3, d4);
    Rectangle localRectangle3 = new Rectangle(d11, 0.3964757709251101D * d2, d5, d6);
    LinearGradient localLinearGradient1;
    LinearGradient localLinearGradient2;
    if (this.control.isDarkFixtureEnabled())
    {
      localLinearGradient1 = new LinearGradient(0.0D, 0.3920704845814978D * d2, 0.0D, 0.5286343612334802D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(26, 26, 26)), new Stop(0.18D, Color.rgb(86, 86, 86)), new Stop(0.65D, Color.rgb(17, 17, 17)), new Stop(0.89D, Color.rgb(24, 24, 24)), new Stop(1.0D, Color.rgb(25, 24, 24)) });
      localLinearGradient2 = new LinearGradient(0.0D, 0.3964757709251101D * d2, 0.0D * d1, 0.5242290748898678D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(66, 66, 66)), new Stop(0.13D, Color.rgb(153, 153, 153)), new Stop(0.66D, Color.rgb(6, 6, 6)), new Stop(0.73D, Color.rgb(14, 14, 14)), new Stop(0.9D, Color.rgb(39, 39, 39)), new Stop(1.0D, Color.rgb(23, 23, 23)) });
    }
    else
    {
      localLinearGradient1 = new LinearGradient(0.0D, 0.3920704845814978D * d2, 0.0D, 0.5286343612334802D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(56, 56, 56)), new Stop(0.18D, Color.rgb(156, 156, 156)), new Stop(0.65D, Color.rgb(47, 47, 47)), new Stop(0.89D, Color.rgb(84, 84, 84)), new Stop(1.0D, Color.rgb(55, 55, 55)) });
      localLinearGradient2 = new LinearGradient(0.0D, 0.3964757709251101D * d2, 0.0D * d1, 0.5242290748898678D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(116, 116, 116)), new Stop(0.13D, Color.rgb(213, 213, 213)), new Stop(0.66D, Color.rgb(56, 56, 56)), new Stop(0.73D, Color.rgb(64, 64, 64)), new Stop(0.9D, Color.rgb(109, 109, 109)), new Stop(1.0D, Color.rgb(83, 83, 83)) });
    }
    localRectangle2.setFill(localLinearGradient1);
    localRectangle2.setStroke(null);
    localRectangle3.setFill(localLinearGradient2);
    localRectangle3.setStroke(null);
    Rectangle localRectangle4 = new Rectangle(d8, 0.3920704845814978D * d2, d3, d4);
    Rectangle localRectangle5 = new Rectangle(d9, 0.3964757709251101D * d2, d5, d6);
    LinearGradient localLinearGradient3;
    LinearGradient localLinearGradient4;
    if (this.control.isDarkFixtureEnabled())
    {
      localLinearGradient3 = new LinearGradient(0.0D, 0.3920704845814978D * d2, 0.0D, 0.5286343612334802D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(26, 26, 26)), new Stop(0.18D, Color.rgb(86, 86, 86)), new Stop(0.65D, Color.rgb(17, 17, 17)), new Stop(0.89D, Color.rgb(24, 24, 24)), new Stop(1.0D, Color.rgb(25, 24, 24)) });
      localLinearGradient4 = new LinearGradient(0.0D, 0.3964757709251101D * d2, 0.0D, 0.5242290748898678D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(66, 66, 66)), new Stop(0.13D, Color.rgb(153, 153, 153)), new Stop(0.66D, Color.rgb(6, 6, 6)), new Stop(0.73D, Color.rgb(14, 14, 14)), new Stop(0.9D, Color.rgb(39, 39, 39)), new Stop(1.0D, Color.rgb(23, 23, 23)) });
    }
    else
    {
      localLinearGradient3 = new LinearGradient(0.0D, 0.3920704845814978D * d2, 0.0D, 0.5286343612334802D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(56, 56, 56)), new Stop(0.18D, Color.rgb(156, 156, 156)), new Stop(0.65D, Color.rgb(47, 47, 47)), new Stop(0.89D, Color.rgb(84, 84, 84)), new Stop(1.0D, Color.rgb(55, 55, 55)) });
      localLinearGradient4 = new LinearGradient(0.0D, 0.3964757709251101D * d2, 0.0D, 0.5242290748898678D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(116, 116, 116)), new Stop(0.13D, Color.rgb(213, 213, 213)), new Stop(0.66D, Color.rgb(56, 56, 56)), new Stop(0.73D, Color.rgb(64, 64, 64)), new Stop(0.9D, Color.rgb(109, 109, 109)), new Stop(1.0D, Color.rgb(83, 83, 83)) });
    }
    localRectangle4.setFill(localLinearGradient3);
    localRectangle4.setStroke(null);
    localRectangle5.setFill(localLinearGradient4);
    localRectangle5.setStroke(null);
    this.fixture.getChildren().addAll(new Node[] { localRectangle2, localRectangle3, localRectangle4, localRectangle5 });
    this.fixture.setCache(true);
  }
  
  public final void drawFlip()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = this.control.getPrefWidth();
    double d3 = this.control.getPrefHeight();
    this.flip.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    this.flip.getChildren().add(localRectangle1);
    double d4 = 0.04405286343612335D * d3;
    double d5 = d4;
    double d6 = d2 - d4;
    double d7 = 0.0484581498D * d3;
    double d8 = 0.0748898678D * d3;
    double d9 = d2 - 2.0D * d4;
    double d10 = 0.4140969163D * d3;
    double d11 = this.control.getFlapCornerRadius() / 227.0D * d3;
    Rectangle localRectangle2 = new Rectangle(d5, 0.3832599118942731D * d3, d7, d8);
    Rectangle localRectangle3 = new Rectangle(d6 - d7, 0.3832599118942731D * d3, d7, d8);
    Rectangle localRectangle4 = new Rectangle(d5, 0.4625550661D * d3, d7, d8);
    Rectangle localRectangle5 = new Rectangle(d6 - d7, 0.4625550661D * d3, d7, d8);
    Rectangle localRectangle6 = new Rectangle(d5, d5, d9, d10);
    localRectangle6.setArcWidth(d11);
    localRectangle6.setArcHeight(d11);
    Rectangle localRectangle7 = new Rectangle(d5, 0.4625550661D * d3, d9, d10);
    localRectangle7.setArcWidth(d11);
    localRectangle7.setArcHeight(d11);
    this.upper = Shape.subtract(Shape.subtract(localRectangle6, localRectangle2), localRectangle3);
    this.lower = Shape.subtract(Shape.subtract(localRectangle7, localRectangle4), localRectangle5);
    LinearGradient localLinearGradient1 = new LinearGradient(0.0D, this.lower.getLayoutBounds().getMinY(), 0.0D, this.lower.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getLowerFlapTopColor().brighter().brighter()), new Stop(0.05D, this.control.getLowerFlapTopColor()), new Stop(0.99D, this.control.getLowerFlapBottomColor()), new Stop(0.0D, this.control.getLowerFlapBottomColor().darker().darker()) });
    this.lower.setFill(localLinearGradient1);
    this.lower.setStroke(null);
    this.lower.setCache(true);
    this.lower.setCacheHint(CacheHint.QUALITY);
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.01D * this.lower.getLayoutBounds().getHeight());
    localInnerShadow1.setHeight(0.01D * this.lower.getLayoutBounds().getHeight());
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setOffsetY(-0.022D * this.lower.getLayoutBounds().getHeight());
    localInnerShadow1.setRadius(0.01D * this.lower.getLayoutBounds().getHeight());
    localInnerShadow1.setColor(Color.BLACK);
    localInnerShadow1.setBlurType(BlurType.GAUSSIAN);
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setWidth(0.04D * this.lower.getLayoutBounds().getHeight());
    localInnerShadow2.setHeight(0.04D * this.lower.getLayoutBounds().getHeight());
    localInnerShadow2.setOffsetX(0.0D);
    localInnerShadow2.setOffsetY(0.015D * this.lower.getLayoutBounds().getHeight());
    localInnerShadow2.setRadius(0.04D * this.lower.getLayoutBounds().getHeight());
    localInnerShadow2.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.8D));
    localInnerShadow2.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow2.inputProperty().set(localInnerShadow1);
    if (this.control.isLowerFlapHighlightEnabled()) {
      this.lower.setEffect(localInnerShadow2);
    } else {
      this.lower.setEffect(localInnerShadow1);
    }
    LinearGradient localLinearGradient2 = new LinearGradient(0.0D, this.upper.getLayoutBounds().getMinY(), 0.0D, this.upper.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getUpperFlapTopColor().darker()), new Stop(0.01D, this.control.getUpperFlapTopColor()), new Stop(0.95D, this.control.getUpperFlapBottomColor()), new Stop(1.0D, this.control.getUpperFlapBottomColor().darker()) });
    this.upper.setFill(localLinearGradient2);
    this.upper.setStroke(null);
    InnerShadow localInnerShadow3 = new InnerShadow();
    localInnerShadow3.setWidth(0.02D * this.upper.getLayoutBounds().getHeight());
    localInnerShadow3.setHeight(0.02D * this.upper.getLayoutBounds().getHeight());
    localInnerShadow3.setOffsetX(0.0D);
    localInnerShadow3.setOffsetY(0.022D * this.upper.getLayoutBounds().getHeight());
    localInnerShadow3.setRadius(0.02D * this.upper.getLayoutBounds().getHeight());
    localInnerShadow3.setColor(Color.BLACK);
    localInnerShadow3.setBlurType(BlurType.GAUSSIAN);
    InnerShadow localInnerShadow4 = new InnerShadow();
    localInnerShadow4.setWidth(0.04D * this.upper.getLayoutBounds().getHeight());
    localInnerShadow4.setHeight(0.04D * this.upper.getLayoutBounds().getHeight());
    localInnerShadow4.setOffsetX(0.0D);
    localInnerShadow4.setOffsetY(0.01D * this.upper.getLayoutBounds().getHeight());
    localInnerShadow4.setRadius(0.04D * this.upper.getLayoutBounds().getHeight());
    localInnerShadow4.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.8D));
    localInnerShadow4.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow4.inputProperty().set(localInnerShadow3);
    if (this.control.isUpperFlapHighlightEnabled()) {
      this.upper.setEffect(localInnerShadow4);
    } else {
      this.upper.setEffect(localInnerShadow3);
    }
    this.upper.setCache(true);
    this.upper.setCacheHint(CacheHint.SPEED);
    Font localFont = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/droidsansmono.ttf"), 0.704845815D * d3);
    Rectangle localRectangle8 = new Rectangle(0.0D, this.upper.getLayoutBounds().getMinY(), d2, this.upper.getLayoutBounds().getHeight());
    this.upperText.setTextOrigin(VPos.BOTTOM);
    this.upperText.setFont(localFont);
    this.upperText.setFontSmoothingType(FontSmoothingType.LCD);
    this.upperText.setText(this.control.getText());
    this.upperText.setX((d2 - this.upperText.getLayoutBounds().getWidth()) / 2.0D);
    this.upperText.setY(d3 * 0.04D + this.upperText.getLayoutBounds().getHeight());
    this.upperText.setClip(localRectangle8);
    LinearGradient localLinearGradient3 = new LinearGradient(0.0D, this.upperText.getLayoutBounds().getMinY(), 0.0D, this.upperText.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getTextUpperFlapColor()), new Stop(0.49D, this.control.getTextColor()), new Stop(0.5D, this.control.getTextColor().darker()) });
    this.upperText.setFill(localLinearGradient3);
    this.upperText.setStroke(null);
    Rectangle localRectangle9 = new Rectangle(0.0D, this.lower.getLayoutBounds().getMinY(), d2, this.lower.getLayoutBounds().getHeight());
    this.lowerText.setTextOrigin(VPos.BOTTOM);
    this.lowerText.setFont(localFont);
    this.lowerText.setFontSmoothingType(FontSmoothingType.LCD);
    this.lowerText.setText(this.control.getText());
    this.lowerText.setX((d2 - this.upperText.getLayoutBounds().getWidth()) / 2.0D);
    this.lowerText.setY(d3 * 0.04D + this.upperText.getLayoutBounds().getHeight());
    this.lowerText.setClip(localRectangle9);
    LinearGradient localLinearGradient4 = new LinearGradient(0.0D, this.lower.getLayoutBounds().getMinY(), 0.0D, this.lowerText.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getTextColor().darker()), new Stop(0.05D, this.control.getTextLowerFlapColor()), new Stop(1.0D, this.control.getTextColor()) });
    this.lowerText.setFill(localLinearGradient4);
    this.lowerText.setStroke(null);
    this.upperNext = Shape.subtract(Shape.subtract(localRectangle6, localRectangle2), localRectangle3);
    LinearGradient localLinearGradient5 = new LinearGradient(0.0D, this.upperNext.getLayoutBounds().getMinY(), 0.0D, this.upperNext.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getUpperFlapTopColor().darker()), new Stop(0.01D, this.control.getUpperFlapTopColor()), new Stop(0.95D, this.control.getUpperFlapBottomColor()), new Stop(1.0D, this.control.getUpperFlapBottomColor().darker()) });
    this.upperNext.setFill(localLinearGradient5);
    this.upperNext.setStroke(null);
    this.upperNext.setCache(true);
    this.upperNext.setCacheHint(CacheHint.SPEED);
    Rectangle localRectangle10 = new Rectangle(0.0D, this.upper.getLayoutBounds().getMinY(), d2, this.upper.getLayoutBounds().getHeight());
    this.upperNextText.setTextOrigin(VPos.BOTTOM);
    this.upperNextText.setFont(localFont);
    this.upperNextText.setFontSmoothingType(FontSmoothingType.LCD);
    this.upperNextText.setText(this.control.getNextText());
    this.upperNextText.setX((d2 - this.upperNextText.getLayoutBounds().getWidth()) / 2.0D);
    this.upperNextText.setY(d3 * 0.04D + this.upperNextText.getLayoutBounds().getHeight());
    this.upperNextText.setClip(localRectangle10);
    LinearGradient localLinearGradient6 = new LinearGradient(0.0D, this.upperNextText.getLayoutBounds().getMinY(), 0.0D, this.upperNextText.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getTextUpperFlapColor()), new Stop(0.47D, this.control.getTextColor()), new Stop(0.5D, this.control.getTextColor().darker()) });
    this.upperNextText.setFill(localLinearGradient6);
    this.upperNextText.setStroke(null);
    Rectangle localRectangle11 = new Rectangle(0.0D, this.lower.getLayoutBounds().getMinY(), d2, this.lower.getLayoutBounds().getHeight());
    this.lowerNextText.setTextOrigin(VPos.BOTTOM);
    this.lowerNextText.setFont(localFont);
    this.lowerNextText.setFontSmoothingType(FontSmoothingType.LCD);
    this.lowerNextText.setText(this.control.getNextText());
    this.lowerNextText.setX((d2 - this.lowerNextText.getLayoutBounds().getWidth()) / 2.0D);
    this.lowerNextText.setY(d3 * 0.04D + this.lowerNextText.getLayoutBounds().getHeight());
    this.lowerNextText.setClip(localRectangle11);
    LinearGradient localLinearGradient7 = new LinearGradient(0.0D, this.lowerNextText.getLayoutBounds().getMinY(), 0.0D, this.lowerNextText.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.5D, this.control.getTextColor().brighter()), new Stop(0.53D, this.control.getTextLowerFlapColor()), new Stop(1.0D, this.control.getTextColor()) });
    this.lowerNextText.setFill(localLinearGradient7);
    this.lowerNextText.setStroke(null);
    this.lowerNextText.setVisible(false);
    this.lowerFlipVert = new Rotate();
    this.lowerFlipVert.setAxis(Rotate.X_AXIS);
    this.lowerFlipVert.setPivotY(d3 * 0.4625550661D);
    this.lowerFlipVert.setAngle(180.0D);
    this.lowerNextText.getTransforms().add(this.lowerFlipVert);
    double d12;
    if (((String)this.selectedSet.get(this.currentSelectionIndex)).length() > 1)
    {
      d12 = 0.1057268722D * d3;
      this.upperText.setX(d12);
      this.lowerText.setX(d12);
    }
    if (((String)this.selectedSet.get(this.nextSelectionIndex)).length() > 1)
    {
      d12 = 0.1057268722D * d3;
      this.upperNextText.setX(d12);
      this.lowerNextText.setX(d12);
    }
    if (this.control.isInteractive())
    {
      addMouseEventListener(this.upperText, 1);
      addMouseEventListener(this.upperNextText, 1);
      addMouseEventListener(this.lowerText, -1);
      addMouseEventListener(this.lowerNextText, -1);
    }
    this.flip.setDepthTest(DepthTest.ENABLE);
    this.flip.getChildren().addAll(new Node[] { this.lower, this.lowerText, this.upperNext, this.upperNextText, this.upper, this.upperText, this.lowerNextText });
  }
  
  public final void drawFrame()
  {
    double d1 = this.control.getPrefWidth();
    double d2 = this.control.getPrefHeight();
    this.frame.getChildren().clear();
    Shape localShape = Shape.subtract(new Rectangle(0.0D, 0.0D, d1, d2), new Rectangle(0.0352422907D * d2, 0.0352422907D * d2, d1 - 0.0704845814D * d2, 0.9207048458D * d2));
    LinearGradient localLinearGradient = new LinearGradient(0.0D, 0.0D, 0.0D, d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, this.control.getFrameTopColor()), new Stop(1.0D, this.control.getFrameBottomColor()) });
    localShape.setFill(localLinearGradient);
    localShape.setStroke(null);
    this.frame.getChildren().addAll(new Node[] { localShape });
    this.frame.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/SplitFlapSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */