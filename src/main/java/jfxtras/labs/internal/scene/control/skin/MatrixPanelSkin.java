package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.MatrixPanelBehavior;
import jfxtras.labs.scene.control.gauge.Content;
import jfxtras.labs.scene.control.gauge.Content.Align;
import jfxtras.labs.scene.control.gauge.Content.Effect;
import jfxtras.labs.scene.control.gauge.Content.Gap;
import jfxtras.labs.scene.control.gauge.Content.MatrixColor;
import jfxtras.labs.scene.control.gauge.Content.PostEffect;
import jfxtras.labs.scene.control.gauge.Content.RotationOrder;
import jfxtras.labs.scene.control.gauge.Content.Type;
import jfxtras.labs.scene.control.gauge.Gauge.FrameDesign;
import jfxtras.labs.scene.control.gauge.MatrixPanel;
import jfxtras.labs.scene.control.gauge.MatrixPanel.DotFont;
import jfxtras.labs.scene.control.gauge.UtilHex;
import jfxtras.labs.util.ConicalGradient;
import jfxtras.labs.util.Util;

public class MatrixPanelSkin
  extends SkinBase<MatrixPanel, MatrixPanelBehavior>
{
  private static final Rectangle PREF_SIZE = new Rectangle(170.0D, 350.0D);
  private MatrixPanel control;
  private Rectangle gaugeBounds;
  private Point2D framelessOffset;
  private Group frame;
  private Group background;
  private Group matrix;
  private Group foreground;
  private boolean isDirty;
  private boolean initialized;
  private IntegerProperty ledWidth;
  private IntegerProperty ledHeight;
  private Group dots;
  private ObservableList<Content> contents;
  private Map<Integer, Shape> dotMap;
  private BooleanProperty[] visibleContent = null;
  private final int toneScale = 85;
  private final Color COLOR_OFF = Color.rgb(39, 39, 39, 0.25D);
  private double radio = 0.0D;
  private final int LED_COLUMN = 0;
  private final int LED_ROW = 1;
  private final int LED_INTENSITY = 2;
  private ArrayList<int[][]> fullAreas = null;
  private Rectangle[] visibleArea = null;
  private ArrayList<ContentPair> pairs = null;
  private ArrayList<Animation> Anim = null;
  
  public MatrixPanelSkin(MatrixPanel paramMatrixPanel)
  {
    super(paramMatrixPanel, new MatrixPanelBehavior(paramMatrixPanel));
    this.control = paramMatrixPanel;
    this.gaugeBounds = new Rectangle(800.0D, 600.0D);
    this.framelessOffset = new Point2D(0.0D, 0.0D);
    this.frame = new Group();
    this.background = new Group();
    this.matrix = new Group();
    this.foreground = new Group();
    this.ledWidth = new SimpleIntegerProperty(0);
    this.ledHeight = new SimpleIntegerProperty(0);
    this.dots = new Group();
    this.isDirty = false;
    this.initialized = false;
    init();
  }
  
  private void init()
  {
    if ((this.control.getPrefWidth() < 0.0D) || (this.control.getPrefHeight() < 0.0D)) {
      this.control.setPrefSize(PREF_SIZE.getWidth(), PREF_SIZE.getHeight());
    }
    this.ledWidth.bind(this.control.ledWidthProperty());
    this.ledHeight.bind(this.control.ledHeightProperty());
    this.contents = this.control.getContents();
    addBindings();
    addListeners();
    this.initialized = true;
    paint();
  }
  
  private void addBindings()
  {
    if (this.frame.visibleProperty().isBound()) {
      this.frame.visibleProperty().unbind();
    }
    this.frame.visibleProperty().bind(this.control.frameVisibleProperty());
  }
  
  private void addListeners()
  {
    this.control.prefWidthProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        MatrixPanelSkin.this.isDirty = true;
      }
    });
    this.control.prefHeightProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        MatrixPanelSkin.this.isDirty = true;
      }
    });
    this.ledHeight.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        MatrixPanelSkin.this.isDirty = true;
      }
    });
    this.ledWidth.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
      {
        MatrixPanelSkin.this.isDirty = true;
      }
    });
    this.contents.addListener(new ListChangeListener()
    {
      public void onChanged(Change paramAnonymousChange)
      {
        MatrixPanelSkin.this.updatePanel();
      }
    });
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("FRAME_DESIGN".equals(paramString)) {
      drawFrame();
    } else if ("SIMPLE_GRADIENT_BASE".equals(paramString)) {
      this.isDirty = true;
    }
  }
  
  public void paint()
  {
    if (!this.initialized) {
      init();
    }
    calcGaugeBounds();
    setTranslateX(this.framelessOffset.getX());
    setTranslateY(this.framelessOffset.getY());
    getChildren().clear();
    drawFrame();
    drawBackground();
    drawPanel();
    drawForeground();
    getChildren().addAll(new Node[] { this.frame, this.background, this.matrix, this.foreground });
    updatePanel();
    this.isDirty = false;
  }
  
  public void layoutChildren()
  {
    if (this.isDirty) {
      paint();
    }
    super.layoutChildren();
  }
  
  public MatrixPanel getSkinnable()
  {
    return this.control;
  }
  
  public void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = PREF_SIZE.getWidth();
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = PREF_SIZE.getHeight();
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefHeight(d);
  }
  
  private void calcGaugeBounds()
  {
    if (this.control.getPrefWidth() == 0.0D) {
      this.control.setPrefWidth(800.0D);
    }
    if (this.control.getPrefHeight() == 0.0D) {
      this.control.setPrefHeight(600.0D);
    }
    if ((this.control.getLedHeight() > 0) && (this.control.getLedWidth() > 0))
    {
      double d = Math.min(this.control.getPrefWidth() / this.control.getLedWidth(), this.control.getPrefHeight() / this.control.getLedHeight());
      this.control.setPrefWidth(d * this.control.getLedWidth());
      this.control.setPrefHeight(d * this.control.getLedHeight());
    }
    this.gaugeBounds.setWidth(this.control.getPrefWidth());
    this.gaugeBounds.setHeight(this.control.getPrefHeight());
    this.framelessOffset = new Point2D(0.0D, 0.0D);
  }
  
  public void drawFrame()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.frame.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
    localRectangle1.setArcWidth(0.05D * d1);
    localRectangle1.setArcHeight(0.05D * d1);
    Rectangle localRectangle2 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle2.setArcWidth(0.09333333333333334D * d1);
    localRectangle2.setArcHeight(0.09333333333333334D * d1);
    localRectangle2.setFill(Color.color(0.5176470588D, 0.5176470588D, 0.5176470588D, 1.0D));
    localRectangle2.setStroke(null);
    this.frame.getChildren().add(localRectangle2);
    Rectangle localRectangle3 = new Rectangle(1.0D, 1.0D, d2 - 2.0D, d3 - 2.0D);
    localRectangle3.setArcWidth(0.08D * d1);
    localRectangle3.setArcHeight(0.08D * d1);
    localRectangle3.setStroke(null);
    Rectangle localRectangle4 = new Rectangle(0.0841121495D * d1, 0.0841121495D * d1, d2 - 0.168224299D * d1, d3 - 0.168224299D * d1);
    localRectangle4.setArcWidth(0.05D * d1);
    localRectangle4.setArcHeight(0.05D * d1);
    localRectangle4.setFill(Color.color(0.6D, 0.6D, 0.6D, 0.8D));
    localRectangle4.setStroke(null);
    switch (this.control.getFrameDesign())
    {
    case BLACK_METAL: 
      ConicalGradient localConicalGradient1 = new ConicalGradient(new Point2D(localRectangle3.getLayoutBounds().getWidth() / 2.0D, localRectangle3.getLayoutBounds().getHeight() / 2.0D), new Stop[] { new Stop(0.0D, Color.rgb(254, 254, 254)), new Stop(0.125D, Color.rgb(0, 0, 0)), new Stop(0.3472D, Color.rgb(153, 153, 153)), new Stop(0.5D, Color.rgb(0, 0, 0)), new Stop(0.6805D, Color.rgb(153, 153, 153)), new Stop(0.875D, Color.rgb(0, 0, 0)), new Stop(1.0D, Color.rgb(254, 254, 254)) });
      localRectangle3.setFill(localConicalGradient1.apply(localRectangle3));
      localRectangle3.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle4 });
      break;
    case SHINY_METAL: 
      ConicalGradient localConicalGradient2 = new ConicalGradient(new Point2D(localRectangle3.getLayoutBounds().getWidth() / 2.0D, localRectangle3.getLayoutBounds().getHeight() / 2.0D), new Stop[] { new Stop(0.0D, Color.rgb(254, 254, 254)), new Stop(0.125D, Util.darker(this.control.getFrameBaseColor(), 0.15D)), new Stop(0.25D, this.control.getFrameBaseColor().darker()), new Stop(0.3472D, this.control.getFrameBaseColor().brighter()), new Stop(0.5D, this.control.getFrameBaseColor().darker().darker()), new Stop(0.6527D, this.control.getFrameBaseColor().brighter()), new Stop(0.75D, this.control.getFrameBaseColor().darker()), new Stop(0.875D, Util.darker(this.control.getFrameBaseColor(), 0.15D)), new Stop(1.0D, Color.rgb(254, 254, 254)) });
      localRectangle3.setFill(localConicalGradient2.apply(localRectangle3));
      localRectangle3.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle4 });
      break;
    case CHROME: 
      ConicalGradient localConicalGradient3 = new ConicalGradient(new Point2D(localRectangle3.getLayoutBounds().getWidth() / 2.0D, localRectangle3.getLayoutBounds().getHeight() / 2.0D), new Stop[] { new Stop(0.0D, Color.WHITE), new Stop(0.09D, Color.WHITE), new Stop(0.12D, Color.rgb(136, 136, 138)), new Stop(0.16D, Color.rgb(164, 185, 190)), new Stop(0.25D, Color.rgb(158, 179, 182)), new Stop(0.29D, Color.rgb(112, 112, 112)), new Stop(0.33D, Color.rgb(221, 227, 227)), new Stop(0.38D, Color.rgb(155, 176, 179)), new Stop(0.48D, Color.rgb(156, 176, 177)), new Stop(0.52D, Color.rgb(254, 255, 255)), new Stop(0.63D, Color.WHITE), new Stop(0.68D, Color.rgb(156, 180, 180)), new Stop(0.8D, Color.rgb(198, 209, 211)), new Stop(0.83D, Color.rgb(246, 248, 247)), new Stop(0.87D, Color.rgb(204, 216, 216)), new Stop(0.97D, Color.rgb(164, 188, 190)), new Stop(1.0D, Color.WHITE) });
      localRectangle3.setFill(localConicalGradient3.apply(localRectangle3));
      localRectangle3.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle4 });
      break;
    case GLOSSY_METAL: 
      localRectangle3.setFill(new LinearGradient(0.4714285714285714D * d2, 0.014285714285714285D * d3, 0.47142857142857153D * d2, 0.9785714285714285D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.9764705882D, 0.9764705882D, 0.9764705882D, 1.0D)), new Stop(0.1D, Color.color(0.7843137255D, 0.7647058824D, 0.7490196078D, 1.0D)), new Stop(0.26D, Color.WHITE), new Stop(0.73D, Color.color(0.1137254902D, 0.1137254902D, 0.1137254902D, 1.0D)), new Stop(1.0D, Color.color(0.8196078431D, 0.8196078431D, 0.8196078431D, 1.0D)) }));
      Rectangle localRectangle5 = new Rectangle(0.08571428571428572D * d2, 0.08571428571428572D * d3, 0.8285714285714286D * d2, 0.8285714285714286D * d3);
      localRectangle5.setArcWidth(0.05714285714285714D * d1);
      localRectangle5.setArcHeight(0.05714285714285714D * d1);
      localRectangle5.setFill(new LinearGradient(0.0D, localRectangle5.getLayoutBounds().getMinY(), 0.0D, localRectangle5.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.9764705882D, 0.9764705882D, 0.9764705882D, 1.0D)), new Stop(0.23D, Color.color(0.7843137255D, 0.7647058824D, 0.7490196078D, 1.0D)), new Stop(0.36D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(0.59D, Color.color(0.1137254902D, 0.1137254902D, 0.1137254902D, 1.0D)), new Stop(0.76D, Color.color(0.7843137255D, 0.7607843137D, 0.7529411765D, 1.0D)), new Stop(1.0D, Color.color(0.8196078431D, 0.8196078431D, 0.8196078431D, 1.0D)) }));
      Rectangle localRectangle6 = new Rectangle(localRectangle4.getX() - 2.0D, localRectangle4.getY() - 2.0D, localRectangle4.getWidth() + 4.0D, localRectangle4.getHeight() + 4.0D);
      localRectangle6.setArcWidth(localRectangle4.getArcWidth() + 1.0D);
      localRectangle6.setArcHeight(localRectangle4.getArcHeight() + 1.0D);
      localRectangle6.setFill(Color.web("#F6F6F6"));
      Rectangle localRectangle7 = new Rectangle(localRectangle6.getX() + 2.0D, localRectangle6.getY() + 2.0D, localRectangle6.getWidth() - 4.0D, localRectangle6.getHeight() - 4.0D);
      localRectangle7.setArcWidth(localRectangle6.getArcWidth() - 1.0D);
      localRectangle7.setArcHeight(localRectangle6.getArcHeight() - 1.0D);
      localRectangle7.setFill(Color.web("#333333"));
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle5, localRectangle6, localRectangle7 });
      break;
    case DARK_GLOSSY: 
      localRectangle3.setFill(new LinearGradient(0.8551401869158879D * d2, 0.14953271028037382D * d3, 0.15794611761513314D * d2, 0.8467267795811287D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.3254901961D, 0.3254901961D, 0.3254901961D, 1.0D)), new Stop(0.08D, Color.color(0.9960784314D, 0.9960784314D, 1.0D, 1.0D)), new Stop(0.52D, Color.color(0.0D, 0.0D, 0.0D, 1.0D)), new Stop(0.55D, Color.color(0.0196078431D, 0.0235294118D, 0.0196078431D, 1.0D)), new Stop(0.84D, Color.color(0.9725490196D, 0.9803921569D, 0.9764705882D, 1.0D)), new Stop(0.99D, Color.color(0.3254901961D, 0.3254901961D, 0.3254901961D, 1.0D)), new Stop(1.0D, Color.color(0.3254901961D, 0.3254901961D, 0.3254901961D, 1.0D)) }));
      Rectangle localRectangle8 = new Rectangle(0.08571428571428572D * d2, 0.08571428571428572D * d3, 0.8285714285714286D * d2, 0.8285714285714286D * d3);
      localRectangle8.setArcWidth(0.05714285714285714D * d1);
      localRectangle8.setArcHeight(0.05714285714285714D * d1);
      localRectangle8.setFill(new LinearGradient(0.5D * d2, 0.014018691588785047D * d3, 0.5D * d2, 0.985981308411215D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.2588235294D, 0.2588235294D, 0.2588235294D, 1.0D)), new Stop(0.42D, Color.color(0.2588235294D, 0.2588235294D, 0.2588235294D, 1.0D)), new Stop(1.0D, Color.color(0.0509803922D, 0.0509803922D, 0.0509803922D, 1.0D)) }));
      localRectangle8.setStroke(null);
      Rectangle localRectangle9 = new Rectangle(localRectangle3.getX(), localRectangle3.getY(), localRectangle3.getWidth(), localRectangle3.getHeight() * 0.5D);
      localRectangle9.setArcWidth(localRectangle3.getArcWidth());
      localRectangle9.setArcHeight(localRectangle3.getArcHeight());
      localRectangle9.setFill(new LinearGradient(0.5D * d2, 0.014018691588785047D * d3, 0.5D * d2, 0.5280373831775701D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(0.26D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(0.26009998D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
      localRectangle9.setStroke(null);
      Rectangle localRectangle10 = new Rectangle(localRectangle4.getX() - 2.0D, localRectangle4.getY() - 2.0D, localRectangle4.getWidth() + 4.0D, localRectangle4.getHeight() + 4.0D);
      localRectangle10.setArcWidth(localRectangle4.getArcWidth() + 1.0D);
      localRectangle10.setArcHeight(localRectangle4.getArcHeight() + 1.0D);
      localRectangle10.setFill(new LinearGradient(0.8037383177570093D * d2, 0.1822429906542056D * d3, 0.18584594354259637D * d2, 0.8001353648686187D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.6745098039D, 0.6745098039D, 0.6784313725D, 1.0D)), new Stop(0.08D, Color.color(0.9960784314D, 0.9960784314D, 1.0D, 1.0D)), new Stop(0.52D, Color.color(0.0D, 0.0D, 0.0D, 1.0D)), new Stop(0.55D, Color.color(0.0196078431D, 0.0235294118D, 0.0196078431D, 1.0D)), new Stop(0.91D, Color.color(0.9725490196D, 0.9803921569D, 0.9764705882D, 1.0D)), new Stop(0.99D, Color.color(0.6980392157D, 0.6980392157D, 0.6980392157D, 1.0D)), new Stop(1.0D, Color.color(0.6980392157D, 0.6980392157D, 0.6980392157D, 1.0D)) }));
      localRectangle10.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle8, localRectangle9, localRectangle10 });
      break;
    default: 
      ImageView localImageView = new ImageView();
      localImageView.setVisible(false);
      localRectangle3.getStyleClass().add(this.control.getFrameDesign().CSS);
      localRectangle3.setStroke(null);
      this.frame.getChildren().addAll(new Node[] { localRectangle3, localRectangle4 });
    }
    this.frame.setCache(true);
  }
  
  public void drawBackground()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.background.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    this.background.getChildren().add(localRectangle1);
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.2D * d1);
    localInnerShadow.setHeight(0.2D * d1);
    localInnerShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 1.0D));
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    LinearGradient localLinearGradient = new LinearGradient(0.0D, 0.0D, d2, 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 0.6D)), new Stop(0.4D, Color.color(0.0D, 0.0D, 0.0D, 0.0D)), new Stop(0.6D, Color.color(0.0D, 0.0D, 0.0D, 0.0D)), new Stop(1.0D, Color.color(0.0D, 0.0D, 0.0D, 0.6D)) });
    Rectangle localRectangle2 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
    localRectangle2.setArcWidth(0.05D * d1);
    localRectangle2.setArcHeight(0.05D * d1);
    localRectangle2.setStroke(null);
    Rectangle localRectangle3 = new Rectangle(0.0841121495D * d1 + 1.0D, 0.0841121495D * d1 + 1.0D, d2 - 0.168224299D * d1 - 2.0D, d3 - 0.168224299D * d1 - 2.0D);
    localRectangle3.setArcWidth(0.05D * d1);
    localRectangle3.setArcHeight(0.05D * d1);
    localRectangle2.setEffect(localInnerShadow);
    this.background.getChildren().addAll(new Node[] { localRectangle2 });
    this.background.setCache(true);
  }
  
  public void drawForeground()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth();
    double d3 = this.gaugeBounds.getHeight();
    this.foreground.getChildren().clear();
    Insets localInsets = new Insets(0.0841121495D * d1 + 2.0D, d2 - 0.0841121495D * d1 - 2.0D, d3 - 0.0841121495D * d1 - 2.0D, 0.0841121495D * d1 + 2.0D);
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.foreground.getChildren().addAll(new Node[] { localRectangle });
    Path localPath = new Path();
    Point2D localPoint2D1;
    Point2D localPoint2D2;
    if (d2 >= d3)
    {
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(localInsets.getLeft(), localInsets.getBottom()));
      localPath.getElements().add(new LineTo(localInsets.getRight(), localInsets.getBottom()));
      localPath.getElements().add(new CubicCurveTo(localInsets.getRight(), localInsets.getBottom(), localInsets.getRight() - 13.0D, 0.7D * d3, localInsets.getRight() - 13.0D, 0.5D * d3));
      localPath.getElements().add(new CubicCurveTo(localInsets.getRight() - 13.0D, 0.3D * d3, localInsets.getRight(), localInsets.getTop(), localInsets.getRight(), localInsets.getTop()));
      localPath.getElements().add(new LineTo(localInsets.getLeft(), localInsets.getTop()));
      localPath.getElements().add(new CubicCurveTo(localInsets.getLeft(), localInsets.getTop(), localInsets.getLeft() + 13.0D, 0.3D * d3, localInsets.getLeft() + 13.0D, 0.5D * d3));
      localPath.getElements().add(new CubicCurveTo(localInsets.getLeft() + 13.0D, 0.7D * d3, localInsets.getLeft(), localInsets.getBottom(), localInsets.getLeft(), localInsets.getBottom()));
      localPath.getElements().add(new ClosePath());
      localPoint2D1 = new Point2D(0.0D, localPath.getLayoutBounds().getMaxY());
      localPoint2D2 = new Point2D(0.0D, localPath.getLayoutBounds().getMinY());
    }
    else
    {
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(localInsets.getLeft(), localInsets.getTop()));
      localPath.getElements().add(new LineTo(localInsets.getLeft(), localInsets.getBottom()));
      localPath.getElements().add(new CubicCurveTo(localInsets.getLeft(), localInsets.getBottom(), 0.3D * d2, localInsets.getBottom() - 13.0D, 0.5D * d2, localInsets.getBottom() - 13.0D));
      localPath.getElements().add(new CubicCurveTo(0.7D * d2, localInsets.getBottom() - 13.0D, localInsets.getRight(), localInsets.getBottom(), localInsets.getRight(), localInsets.getBottom()));
      localPath.getElements().add(new LineTo(localInsets.getRight(), localInsets.getTop()));
      localPath.getElements().add(new CubicCurveTo(localInsets.getRight(), localInsets.getTop(), 0.7D * d2, localInsets.getTop() + 13.0D, 0.5D * d2, localInsets.getTop() + 13.0D));
      localPath.getElements().add(new CubicCurveTo(0.3D * d2, localInsets.getTop() + 13.0D, localInsets.getLeft(), localInsets.getTop(), localInsets.getLeft(), localInsets.getTop()));
      localPath.getElements().add(new ClosePath());
      localPoint2D1 = new Point2D(localPath.getLayoutBounds().getMinX(), 0.0D);
      localPoint2D2 = new Point2D(localPath.getLayoutBounds().getMaxX(), 0.0D);
    }
    LinearGradient localLinearGradient = new LinearGradient(localPoint2D1.getX(), localPoint2D1.getY(), localPoint2D2.getX(), localPoint2D2.getY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.06D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.07D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.12D, Color.color(1.0D, 1.0D, 1.0D, 0.05D)), new Stop(0.17D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.18D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.23D, Color.color(1.0D, 1.0D, 1.0D, 0.02D)), new Stop(0.3D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.8D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.84D, Color.color(1.0D, 1.0D, 1.0D, 0.08D)), new Stop(0.93D, Color.color(1.0D, 1.0D, 1.0D, 0.18D)), new Stop(0.94D, Color.color(1.0D, 1.0D, 1.0D, 0.2D)), new Stop(0.96D, Color.color(1.0D, 1.0D, 1.0D, 0.1D)), new Stop(0.97D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) });
    localPath.setFill(localLinearGradient);
    localPath.setStroke(null);
    this.foreground.getChildren().addAll(new Node[] { localPath });
    this.foreground.setCache(true);
  }
  
  public void drawPanel()
  {
    double d1 = this.gaugeBounds.getWidth() <= this.gaugeBounds.getHeight() ? this.gaugeBounds.getWidth() : this.gaugeBounds.getHeight();
    double d2 = this.gaugeBounds.getWidth() - 2.0D * (0.0841121495D * d1 + 5.0D);
    double d3 = this.gaugeBounds.getHeight() - 2.0D * (0.0841121495D * d1 + 5.0D);
    this.matrix.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0841121495D * d1 + 5.0D, 0.0841121495D * d1 + 5.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    this.matrix.getChildren().add(localRectangle);
    this.radio = (d2 / (3.0D * this.ledWidth.doubleValue() + 1.0D));
    double d4 = this.radio;
    double d5 = (d3 - 2.0D * this.radio * this.ledHeight.doubleValue()) / (this.ledHeight.doubleValue() + 1.0D);
    this.dots.getChildren().clear();
    this.dotMap = new HashMap(this.ledWidth.intValue() * this.ledHeight.intValue());
    for (int i = 0; i < this.ledHeight.intValue(); i++) {
      for (int j = 0; j < this.ledWidth.intValue(); j++)
      {
        Circle localCircle = new Circle(0.0841121495D * d1 + 5.0D + d4 + this.radio + j * (d4 + 2.0D * this.radio), 0.0841121495D * d1 + 5.0D + d5 + this.radio + i * (d5 + 2.0D * this.radio), this.radio, Color.DARKGREY);
        localCircle.setFill(this.COLOR_OFF);
        this.dotMap.put(new Integer(j + i * this.ledWidth.intValue()), localCircle);
        this.dots.getChildren().add(localCircle);
      }
    }
    this.dots.setCache(true);
    this.matrix.getChildren().add(this.dots);
  }
  
  public void updatePanel()
  {
    if (this.contents == null) {
      return;
    }
    stop();
    Platform.runLater(new Runnable()
    {
      public void run()
      {
        int i = 0;
        MatrixPanelSkin.this.fullAreas = new ArrayList();
        MatrixPanelSkin.this.pairs = new ArrayList();
        MatrixPanelSkin.this.visibleArea = new Rectangle[MatrixPanelSkin.this.contents.size()];
        Iterator localIterator = MatrixPanelSkin.this.contents.iterator();
        Object localObject1;
        int j;
        int n;
        Object localObject3;
        while (localIterator.hasNext())
        {
          localObject1 = (Content)localIterator.next();
          j = (int)((Content)localObject1).getOrigin().getX() + (int)((Content)localObject1).getArea().getX();
          int k = (int)((Content)localObject1).getOrigin().getY() + (int)((Content)localObject1).getArea().getY();
          int m = Math.min((int)((Content)localObject1).getArea().getWidth(), MatrixPanelSkin.this.ledWidth.intValue());
          n = Math.min((int)((Content)localObject1).getArea().getHeight(), MatrixPanelSkin.this.ledHeight.intValue());
          MatrixPanelSkin.this.visibleArea[i] = new Rectangle(Math.max(j, 0), Math.max(k, 0), m, n);
          Object localObject4;
          int i2;
          int i3;
          if (((Content)localObject1).getType().equals(Type.IMAGE))
          {
            localObject3 = new UtilHex();
            ((UtilHex)localObject3).convertsBmp(((Content)localObject1).getBmpName(), 65, 190, true, true, true);
            localObject4 = ((UtilHex)localObject3).getRawData();
            if (localObject4 != null)
            {
              String[] arrayOfString = ((String)localObject4).split("\\s");
              i2 = UtilHex.word2Int(arrayOfString[8], arrayOfString[9]);
              i3 = (int)(UtilHex.dword2Long(arrayOfString[20], arrayOfString[21], arrayOfString[22], arrayOfString[23]) / i2 / 3L / 3L);
              int i4 = 32;
              int[][] arrayOfInt2 = new int[i2][i3 * 8];
              int[] arrayOfInt = {(((Content)localObject1).getColor().equals(MatrixColor.RED)) || (((Content)localObject1).getColor().equals(
                  MatrixColor.YELLOW)) || (((Content)localObject1).getColor().equals(MatrixColor.RGB)) ? 1 : 0, (((Content)localObject1).getColor().equals(
                  MatrixColor.GREEN)) || (((Content)localObject1).getColor().equals(MatrixColor.YELLOW)) || (((Content)localObject1).getColor().equals(
                  MatrixColor.RGB)) ? 1 : 0, (((Content)localObject1).getColor().equals(MatrixColor.BLUE)) || (((Content)localObject1).getColor().equals(
                  MatrixColor.RGB)) ? 1 : 0 };
              for (int i5 = 0; i5 < 3; i5++) {
                for (int i6 = 0; i6 < 3; i6++) {
                  for (int i7 = 0; i7 < i2; i7++) {
                    for (int i8 = 0; i8 < i3; i8++)
                    {
                      String str = UtilHex.hex2bin(arrayOfString[(i4++)]);
                      for (int i9 = 0; i9 < 8; i9++)
                      {
                        int tmp545_544 = (i8 * 8 + i9);
                        int[] tmp545_536 = arrayOfInt2[i7];
                        tmp545_536[tmp545_544] = ((int)(tmp545_536[tmp545_544] + (str.substring(i9, i9 + 1).equalsIgnoreCase("1") ? 1 : 0) * Math.pow(10.0D, i6) * arrayOfInt[i6]));
                      }
                    }
                  }
                }
              }
              MatrixPanelSkin.this.fullAreas.add(i, arrayOfInt2);
            }
            else
            {
              MatrixPanelSkin.this.fullAreas.add(i, null);
            }
          }
          else if (((Content)localObject1).getType().equals(Type.TEXT))
          {
            localObject3 = new DotFont(((Content)localObject1).getTxtContent(), ((Content)localObject1).getMatrixFont(), ((Content)localObject1).getFontGap().getGapWidth());
            localObject4 = ((DotFont)localObject3).getDotString();
            if (localObject4 != null)
            {
              int i1 = ((Content)localObject1).getColor().equals(MatrixColor.YELLOW) ? 33 : ((Content)localObject1).getColor().equals(
                  MatrixColor.BLUE) ? 300 : ((Content)localObject1).getColor().equals(MatrixColor.GREEN) ? 30 : ((Content)localObject1).getColor().equals(
                  MatrixColor.RED) ? 3 : 333;
              int[][] arrayOfInt1 = new int[localObject4.length][localObject4[0].length];
              for (i2 = 0; i2 < localObject4.length; i2++) {
                for (i3 = 0; i3 < localObject4[i2].length; i3++) {
                  arrayOfInt1[i2][i3] = (localObject4[i2][i3] != 0 ? i1 : 0);
                }
              }
              MatrixPanelSkin.this.fullAreas.add(i, arrayOfInt1);
            }
            else
            {
              MatrixPanelSkin.this.fullAreas.add(i, null);
            }
          }
          i++;
        }
        localIterator = MatrixPanelSkin.this.contents.iterator();
        Object localObject2;
        while (localIterator.hasNext())
        {
          localObject1 = (Content)localIterator.next();
          if (((Content)localObject1).getOrder().equals(RotationOrder.FIRST))
          {
            j = MatrixPanelSkin.this.contents.indexOf(localObject1);
            localObject2 = MatrixPanelSkin.this.contents.iterator();
            while (((Iterator)localObject2).hasNext())
            {
              Content localContent = (Content)((Iterator)localObject2).next();
              n = MatrixPanelSkin.this.contents.indexOf(localContent);
              if ((localContent.getOrder().equals(RotationOrder.SECOND)) && (((Content)localObject1).getArea().getBoundsInLocal().equals(localContent.getArea().getBoundsInLocal())))
              {
                localObject3 = new ContentPair(j, n);
                MatrixPanelSkin.this.pairs.add(localObject3);
                break;
              }
            }
          }
        }
        MatrixPanelSkin.this.visibleContent = new SimpleBooleanProperty[MatrixPanelSkin.this.contents.size()];
        localIterator = MatrixPanelSkin.this.dotMap.values().iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (Shape)localIterator.next();
          ((Circle)localObject1).setFill(MatrixPanelSkin.this.COLOR_OFF);
        }
        MatrixPanelSkin.this.Anim = new ArrayList();
        localIterator = MatrixPanelSkin.this.contents.iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (Content)localIterator.next();
          j = MatrixPanelSkin.this.contents.indexOf(localObject1);
          if (MatrixPanelSkin.this.fullAreas.get(j) != null)
          {
            localObject2 = new Animation(MatrixPanelSkin.this, j, (Content)localObject1);
            ((Animation)localObject2).initAnimation();
            MatrixPanelSkin.this.Anim.add(localObject2);
          }
        }
        localIterator = MatrixPanelSkin.this.Anim.iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (Animation)localIterator.next();
          ((Animation)localObject1).start();
        }
      }
    });
  }
  
  public void stop()
  {
    if (this.Anim != null)
    {
      Iterator localIterator = this.Anim.iterator();
      while (localIterator.hasNext())
      {
        Animation localAnimation = (Animation)localIterator.next();
        localAnimation.stop();
      }
      this.Anim.clear();
      this.Anim = null;
    }
  }
  
  private static class ContentPair
  {
    private int indexFirst;
    private int indexSecond;
    private boolean bVisibleFirst;
    
    public ContentPair(int paramInt1, int paramInt2)
    {
      this.indexFirst = paramInt1;
      this.bVisibleFirst = true;
      this.indexSecond = paramInt2;
    }
    
    public void setFirstIndex(int paramInt)
    {
      this.indexFirst = paramInt;
      this.bVisibleFirst = true;
    }
    
    public void setSecondIndex(int paramInt)
    {
      this.indexSecond = paramInt;
    }
    
    public void changeIndex()
    {
      this.bVisibleFirst = (!this.bVisibleFirst);
    }
    
    public int getFirstIndex()
    {
      return this.indexFirst;
    }
    
    public int getSecondIndex()
    {
      return this.indexSecond;
    }
    
    public boolean isVisibleFirst()
    {
      return this.bVisibleFirst;
    }
    
    public boolean isVisibleSecond()
    {
      return !this.bVisibleFirst;
    }
    
    private boolean isInPair(int paramInt)
    {
      return (this.indexFirst == paramInt) || (this.indexSecond == paramInt);
    }
  }
  
  private class Animation
    extends AnimationTimer
  {
    private long lastUpdate = 0L;
    private boolean bBlink = false;
    private int contBlink = 0;
    private int iter = 0;
    private int iContent;
    private Content content = null;
    private int oriX;
    private int oriY;
    private int endX;
    private int endY;
    private int areaWidth;
    private int areaHeight;
    private int contentWidth;
    private int contentHeight;
    private IntegerProperty posX;
    private IntegerProperty posY;
    private IntegerProperty posXIni;
    private IntegerProperty posYIni;
    private int[][] contentArea = (int[][])null;
    private int realLapse;
    private int advance;
    private int limX;
    private int limitBlink;
    private int iterLeds;
    private boolean isBlinkEffect;
    private LinkedHashMap<Integer, int[]> brightLeds = null;
    private ArrayList<int[]> arrBrightLeds = null;
    private IntegerProperty incrPos = null;
    
    public Animation(int paramInt, Content paramContent)
    {
      this.iContent = paramInt;
      this.content = paramContent;
      this.incrPos = new SimpleIntegerProperty(1);
      MatrixPanelSkin.this.visibleContent[paramInt] = new SimpleBooleanProperty(true);
      if (this.content.getOrder().equals(RotationOrder.SECOND)) {
        MatrixPanelSkin.this.visibleContent[paramInt].setValue(Boolean.valueOf(false));
      }
    }
    
    public void initAnimation()
    {
      this.contentArea = ((int[][])MatrixPanelSkin.this.fullAreas.get(this.iContent));
      this.oriX = ((int)MatrixPanelSkin.this.visibleArea[this.iContent].getX());
      this.oriY = ((int)MatrixPanelSkin.this.visibleArea[this.iContent].getY());
      this.endX = ((int)MatrixPanelSkin.this.visibleArea[this.iContent].getWidth());
      this.endY = ((int)MatrixPanelSkin.this.visibleArea[this.iContent].getHeight());
      this.areaWidth = (this.endX - this.oriX);
      this.areaHeight = (this.endY - this.oriY);
      this.contentWidth = this.contentArea[0].length;
      this.contentHeight = this.contentArea.length;
      this.posXIni = new SimpleIntegerProperty(0);
      this.posYIni = new SimpleIntegerProperty(0);
      this.posYIni.set(0);
      if (this.content.getTxtAlign().equals(Align.LEFT))
      {
        this.posXIni.set(0);
        this.limX = 0;
      }
      else if (this.content.getTxtAlign().equals(Align.CENTER))
      {
        this.posXIni.set(this.contentWidth / 2 - this.areaWidth / 2);
        this.limX = (-this.areaWidth / 2 + this.contentWidth / 2);
      }
      else if (this.content.getTxtAlign().equals(Align.RIGHT))
      {
        this.posXIni.set(this.contentWidth - this.areaWidth);
        this.limX = (this.contentWidth - this.areaWidth);
      }
      if (this.content.getEffect().equals(Effect.SCROLL_RIGHT)) {
        this.posXIni.set(this.contentWidth);
      } else if (this.content.getEffect().equals(Effect.SCROLL_LEFT)) {
        this.posXIni.set(-this.areaWidth);
      } else if (this.content.getEffect().equals(Effect.SCROLL_UP)) {
        this.posYIni.set(-this.areaHeight);
      } else if (this.content.getEffect().equals(Effect.SCROLL_DOWN)) {
        this.posYIni.set(this.contentHeight);
      } else if (this.content.getEffect().equals(Effect.MIRROR)) {
        if (this.content.getTxtAlign().equals(Align.LEFT)) {
          this.posXIni.set(-this.contentWidth / 2);
        } else if (this.content.getTxtAlign().equals(Align.CENTER)) {
          this.posXIni.set(0 - this.areaWidth / 2);
        } else if (this.content.getTxtAlign().equals(Align.RIGHT)) {
          this.posXIni.set(this.contentWidth / 2 - this.areaWidth);
        }
      }
      this.posX = new SimpleIntegerProperty(this.posXIni.get());
      this.posY = new SimpleIntegerProperty(this.posYIni.get());
      this.realLapse = (this.content.getLapse() >= 250 ? this.content.getLapse() : 250);
      if (this.content.getLapse() > 0)
      {
        this.advance = (this.realLapse / this.content.getLapse());
        this.realLapse = (this.advance * this.content.getLapse());
      }
      else
      {
        this.advance = 10;
      }
      this.isBlinkEffect = ((this.content.getEffect().equals(Effect.BLINK)) || (this.content.getEffect().equals(
          Effect.BLINK_4)) || (this.content.getEffect().equals(Effect.BLINK_10)));
      this.limitBlink = (this.content.getEffect().equals(Effect.BLINK_10) ? 19 : this.content.getEffect().equals(
          Effect.BLINK_4) ? 7 : this.content.getEffect().equals(Effect.BLINK) ? 10000 : 0);
      if (this.content.getEffect().equals(Effect.SPRAY))
      {
        this.brightLeds = new LinkedHashMap();
        this.arrBrightLeds = new ArrayList();
        for (int i = this.oriY; i < this.endY; i++) {
          for (j = this.oriX; j < this.endX; j++)
          {
            Integer localInteger = new Integer(j + i * MatrixPanelSkin.this.ledWidth.intValue());
            if (MatrixPanelSkin.this.dotMap.get(localInteger) != null)
            {
              int k = 0;
              if ((j + this.posX.intValue() >= this.oriX) && (j + this.posX.intValue() < this.contentWidth + this.oriX) && (i + this.posY.intValue() >= this.oriY) && (i + this.posY.intValue() < this.contentHeight + this.oriY))
              {
                k = this.contentArea[(i + this.posY.intValue() - this.oriY)][(j + this.posX.intValue() - this.oriX)];
                if (k > 0)
                {
                  int[] arrayOfInt = { j, i, k };
                  this.arrBrightLeds.add(arrayOfInt);
                }
              }
            }
          }
        }
        Collections.shuffle(this.arrBrightLeds);
        Iterator localIterator = this.arrBrightLeds.iterator();
        for (int j = 0; j < this.arrBrightLeds.size(); j++) {
          this.brightLeds.put(Integer.valueOf(j), localIterator.next());
        }
        this.arrBrightLeds.clear();
        this.iterLeds = (this.brightLeds.size() / this.advance);
      }
    }
    
    public void handle(long paramLong)
    {
      if ((paramLong > this.lastUpdate + this.realLapse * 1000000) && (MatrixPanelSkin.this.visibleContent[this.iContent].getValue().booleanValue()) && (this.incrPos.intValue() == 1))
      {
        int m;
        int n;
        if (this.content.getEffect().equals(Effect.SPRAY)) {
          for (i = 0; i < this.iterLeds; i++)
          {
            int[] arrayOfInt = (int[])this.brightLeds.get(Integer.valueOf(this.brightLeds.size() - this.iter - 1));
            int k = arrayOfInt[2] / 100;
            m = (arrayOfInt[2] - k * 100) / 10;
            n = arrayOfInt[2] - k * 100 - m * 10;
            Integer localInteger2 = new Integer(arrayOfInt[0] + arrayOfInt[1] * MatrixPanelSkin.this.ledWidth.intValue());
            ((Circle)MatrixPanelSkin.this.dotMap.get(localInteger2)).setFill(Color.rgb(85 * n, 85 * m, 85 * k));
            this.iter = (this.iter < this.brightLeds.size() - 1 ? this.iter + 1 : this.iter);
          }
        } else {
          for (i = this.oriX; i < this.endX; i++) {
            for (int j = this.oriY; j < this.endY; j++)
            {
              Integer localInteger1 = new Integer(i + j * MatrixPanelSkin.this.ledWidth.intValue());
              if (MatrixPanelSkin.this.dotMap.get(localInteger1) != null)
              {
                m = this.posX.intValue();
                if (this.content.getEffect().equals(Effect.MIRROR)) {
                  if ((this.content.getTxtAlign().equals(Align.LEFT)) && (i - this.oriX > this.contentWidth / 2)) {
                    m = -m;
                  } else if ((this.content.getTxtAlign().equals(Align.CENTER)) && (i - this.oriX > this.areaWidth / 2.0D)) {
                    m = -m - this.areaWidth + this.contentWidth;
                  } else if ((this.content.getTxtAlign().equals(Align.RIGHT)) && (i - this.oriX > -this.contentWidth / 2 + this.areaWidth)) {
                    m = -m + 2 * (this.contentWidth - this.areaWidth);
                  }
                }
                n = 0;
                if ((i + m >= this.oriX) && (i + m < this.contentWidth + this.oriX) && (j + this.posY.intValue() >= this.oriY) && (j + this.posY.intValue() < this.contentHeight + this.oriY)) {
                  n = this.contentArea[(j + this.posY.intValue() - this.oriY)][(i + m - this.oriX)];
                }
                if (((n > 0) && (!this.isBlinkEffect)) || ((n > 0) && (this.isBlinkEffect) && (this.bBlink)))
                {
                  int i1 = n / 100;
                  int i2 = (n - i1 * 100) / 10;
                  int i3 = n - i1 * 100 - i2 * 10;
                  ((Circle)MatrixPanelSkin.this.dotMap.get(localInteger1)).setFill(Color.rgb(85 * i3, 85 * i2, 85 * i1));
                }
                else
                {
                  ((Circle)MatrixPanelSkin.this.dotMap.get(localInteger1)).setFill(MatrixPanelSkin.this.COLOR_OFF);
                }
              }
            }
          }
        }
        int i = 0;
        if (this.content.getEffect().equals(Effect.NONE))
        {
          i = 1;
        }
        else if (this.content.getEffect().equals(Effect.SCROLL_RIGHT))
        {
          i = this.posX.intValue() <= this.limX ? 1 : 0;
          if (this.posX.intValue() - this.advance * this.incrPos.getValue().intValue() <= this.limX) {
            this.posX.set(this.limX);
          } else {
            this.posX.set(this.posX.intValue() - this.advance * this.incrPos.getValue().intValue());
          }
        }
        else if ((this.content.getEffect().equals(Effect.SCROLL_LEFT)) || (this.content.getEffect().equals(
            Effect.MIRROR)))
        {
          i = this.posX.intValue() >= this.limX ? 1 : 0;
          if (this.posX.intValue() + this.advance * this.incrPos.getValue().intValue() >= this.limX) {
            this.posX.set(this.limX);
          } else {
            this.posX.set(this.posX.intValue() + this.advance * this.incrPos.getValue().intValue());
          }
        }
        else if (this.content.getEffect().equals(Effect.SCROLL_DOWN))
        {
          this.posY.set(this.posY.intValue() - this.incrPos.getValue().intValue());
          i = this.posY.intValue() < 0 ? 1 : 0;
        }
        else if (this.content.getEffect().equals(Effect.SCROLL_UP))
        {
          this.posY.set(this.posY.intValue() + this.incrPos.getValue().intValue());
          i = this.posY.intValue() > 0 ? 1 : 0;
        }
        else if (this.isBlinkEffect)
        {
          if (this.contBlink == this.limitBlink)
          {
            i = 1;
            this.contBlink = -1;
          }
          else if (this.incrPos.getValue().intValue() == 1)
          {
            i = 0;
            this.contBlink += 1;
            this.bBlink = (!this.bBlink);
          }
        }
        else if (this.content.getEffect().equals(Effect.SPRAY))
        {
          if (this.iter >= this.brightLeds.size() - 1)
          {
            i = 1;
            this.iter = 0;
          }
          else
          {
            i = 0;
          }
        }
        if (i != 0) {
          if (this.content.getPostEffect().equals(PostEffect.STOP))
          {
            stop();
          }
          else if ((this.content.getPostEffect().equals(PostEffect.REPEAT)) || (this.content.getPostEffect().equals(
              PostEffect.PAUSE)))
          {
            this.posX.set(this.posXIni.get());
            this.posY.set(this.posYIni.get());
            this.incrPos.setValue(Integer.valueOf(0));
            PauseTransition localPauseTransition = new PauseTransition();
            if (this.content.getPostEffect().equals(PostEffect.REPEAT)) {
              localPauseTransition.setDuration(Duration.millis(10.0D));
            } else {
              localPauseTransition.setDuration(Duration.millis(this.content.getPause()));
            }
            localPauseTransition.setOnFinished(new EventHandler()
            {
              public void handle(ActionEvent paramAnonymousActionEvent)
              {
                Animation.this.incrPos.setValue(Integer.valueOf(1));
                if ((Animation.this.content.getClear()) || (Animation.this.content.getEffect().equals(
                    Effect.SPRAY))) {
                  for (int i = Animation.this.oriY; i < Animation.this.endY; i++) {
                    for (int j = Animation.this.oriX; j < Animation.this.endX; j++)
                    {
                      Integer localInteger = new Integer(j + i * MatrixPanelSkin.this.ledWidth.intValue());
                      ((Circle)MatrixPanelSkin.this.dotMap.get(localInteger)).setFill(MatrixPanelSkin.this.COLOR_OFF);
                    }
                  }
                }
                if (!Animation.this.content.getOrder().equals(RotationOrder.SINGLE))
                {
                  Iterator localIterator = MatrixPanelSkin.this.pairs.iterator();
                  while (localIterator.hasNext())
                  {
                    ContentPair localContentPair = (ContentPair)localIterator.next();
                    if (localContentPair.isInPair(Animation.this.iContent))
                    {
                      MatrixPanelSkin.this.visibleContent[localContentPair.getFirstIndex()].setValue(Boolean.valueOf(!localContentPair.isVisibleFirst()));
                      MatrixPanelSkin.this.visibleContent[localContentPair.getSecondIndex()].setValue(Boolean.valueOf(!localContentPair.isVisibleSecond()));
                      ((ContentPair)MatrixPanelSkin.this.pairs.get(MatrixPanelSkin.this.pairs.indexOf(localContentPair))).changeIndex();
                      break;
                    }
                  }
                }
              }
            });
            localPauseTransition.playFromStart();
          }
        }
        this.lastUpdate = paramLong;
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/MatrixPanelSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */