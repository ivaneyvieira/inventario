package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.LedBargraphBehavior;
import jfxtras.labs.scene.control.gauge.Led;
import jfxtras.labs.scene.control.gauge.LedBargraph;

public class LedBargraphSkin
  extends SkinBase<LedBargraph, LedBargraphBehavior>
{
  public static final long PEAK_TIMEOUT = 1500000000L;
  private LedBargraph control;
  private boolean isDirty;
  private boolean initialized;
  private Group bargraph;
  private List<Led> ledList;
  private long lastTimerCall;
  private DoubleProperty stepSize;
  private int peakLedIndex;
  private AnimationTimer timer;
  
  public LedBargraphSkin(LedBargraph paramLedBargraph)
  {
    super(paramLedBargraph, new LedBargraphBehavior(paramLedBargraph));
    this.control = paramLedBargraph;
    this.initialized = false;
    this.isDirty = false;
    this.bargraph = new Group();
    this.ledList = new ArrayList(this.control.getNoOfLeds());
    for (int i = 0; i < this.control.getNoOfLeds(); i++)
    {
      Led localLed = new Led();
      localLed.setPrefSize(this.control.getLedSize(), this.control.getLedSize());
      this.ledList.add(localLed);
    }
    this.lastTimerCall = 0L;
    this.stepSize = new SimpleDoubleProperty(1.0D / this.control.getNoOfLeds());
    this.peakLedIndex = 0;
    this.timer = new AnimationTimer()
    {
      public void handle(long paramAnonymousLong)
      {
        long l = System.nanoTime();
        if (l > LedBargraphSkin.this.lastTimerCall + 1500000000L)
        {
          ((Led)LedBargraphSkin.this.ledList.get(LedBargraphSkin.this.peakLedIndex)).setOn(false);
          LedBargraphSkin.this.peakLedIndex = 0;
          LedBargraphSkin.this.timer.stop();
        }
      }
    };
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(16.0D, 16.0D);
    }
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    registerChangeListener(this.control.ledTypeProperty(), "LED_TYPE");
    registerChangeListener(this.control.frameVisibleProperty(), "FRAME_VISIBLE");
    registerChangeListener(this.control.ledSizeProperty(), "LED_SIZE");
    registerChangeListener(this.control.orientationProperty(), "ORIENTATION");
    registerChangeListener(this.control.noOfLedsProperty(), "LED_NUMBER");
    registerChangeListener(this.control.ledColorsProperty(), "LED_COLOR");
    if (this.control.getValue() > 0.0D) {
      for (int i = 0; i < this.control.getNoOfLeds(); i++) {
        if (Double.compare(i * this.stepSize.doubleValue(), this.control.getValue()) <= 0) {
          ((Led)this.ledList.get(i)).setOn(true);
        } else {
          ((Led)this.ledList.get(i)).setOn(false);
        }
      }
    }
    this.control.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        int i = 0;
        for (int j = 0; j < LedBargraphSkin.this.control.getNoOfLeds(); j++)
        {
          if (Double.compare(j * LedBargraphSkin.this.stepSize.doubleValue(), paramAnonymousNumber2.doubleValue()) <= 0)
          {
            ((Led)LedBargraphSkin.this.ledList.get(j)).setOn(true);
            i = j;
          }
          else
          {
            ((Led)LedBargraphSkin.this.ledList.get(j)).setOn(false);
          }
          ((Led)LedBargraphSkin.this.ledList.get(LedBargraphSkin.this.peakLedIndex)).setOn(true);
        }
        if ((LedBargraphSkin.this.control.isPeakValueVisible()) && (i > LedBargraphSkin.this.peakLedIndex))
        {
          LedBargraphSkin.this.peakLedIndex = i;
          LedBargraphSkin.this.timer.stop();
          LedBargraphSkin.this.lastTimerCall = System.nanoTime();
          LedBargraphSkin.this.timer.start();
        }
      }
    });
    setLedColors();
    setLedTypes();
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    Led localLed;
    if ("FRAME_VISIBLE".equals(paramString))
    {
      Iterator localIterator = this.ledList.iterator();
      while (localIterator.hasNext())
      {
        localLed = (Led)localIterator.next();
        localLed.setFrameVisible(this.control.isFrameVisible());
      }
      repaint();
    }
    else if ("LED_SIZE".equals(paramString))
    {
      this.ledList.clear();
      for (int i = 0; i < this.control.getNoOfLeds(); i++)
      {
        localLed = new Led();
        localLed.setPrefSize(this.control.getLedSize(), this.control.getLedSize());
        this.ledList.add(localLed);
      }
      setLedColors();
      repaint();
    }
    else if ("ORIENTATION".equals(paramString))
    {
      repaint();
    }
    else if ("LED_NUMBER".equals(paramString))
    {
      this.stepSize.set(1.0D / this.control.getNoOfLeds());
    }
    else if ("LED_COLOR".equals(paramString))
    {
      setLedColors();
      repaint();
    }
    else if ("LED_TYPE".equals(paramString))
    {
      setLedTypes();
      repaint();
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
      drawLed();
      getChildren().setAll(new Node[] { this.bargraph });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final LedBargraph getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 16.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 16.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  private final void setLedColors()
  {
    for (int i = 0; i < this.control.getNoOfLeds(); i++) {
      ((Led)this.ledList.get(i)).setColor(this.control.getLedColor(i));
    }
  }
  
  private final void setLedTypes()
  {
    for (int i = 0; i < this.control.getNoOfLeds(); i++) {
      ((Led)this.ledList.get(i)).setType(this.control.getLedType());
    }
  }
  
  private final void drawLed()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.bargraph.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    this.bargraph.getChildren().add(localRectangle);
    int i = this.control.getNoOfLeds();
    Object localObject;
    int j;
    if (this.control.getOrientation() == Orientation.VERTICAL)
    {
      localObject = new VBox();
      ((VBox)localObject).setSpacing(0.0D);
      ((VBox)localObject).setPadding(new Insets(0.0D, 0.0D, 0.0D, 0.0D));
      for (j = 0; j < i; j++) {
        ((VBox)localObject).getChildren().add(j, this.ledList.get(i - 1 - j));
      }
      this.bargraph.getChildren().add(localObject);
    }
    else
    {
      localObject = new HBox();
      ((HBox)localObject).setSpacing(0.0D);
      ((HBox)localObject).setPadding(new Insets(0.0D, 0.0D, 0.0D, 0.0D));
      for (j = 0; j < i; j++) {
        ((HBox)localObject).getChildren().add(j, this.ledList.get(j));
      }
      this.bargraph.getChildren().add(localObject);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/LedBargraphSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */