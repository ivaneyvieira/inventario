package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import jfxtras.labs.internal.scene.control.behavior.OdometerBehavior;
import jfxtras.labs.scene.control.gauge.Odometer;

public class OdometerSkin
  extends SkinBase<Odometer, OdometerBehavior>
{
  private Odometer control;
  private boolean isDirty;
  private boolean initialized;
  private Group foreground;
  private List<Dial> listOfDials;
  private Group background;
  private Font font;
  
  public OdometerSkin(Odometer paramOdometer)
  {
    super(paramOdometer, new OdometerBehavior(paramOdometer));
    this.control = paramOdometer;
    this.initialized = false;
    this.isDirty = false;
    this.foreground = new Group();
    this.listOfDials = new LinkedList();
    this.background = new Group();
    init();
  }
  
  private void init()
  {
    double d1 = this.control.getPrefWidth();
    double d2 = this.control.getPrefHeight();
    if (d1 <= 0.0D)
    {
      if (d2 <= 0.0D) {
        d2 = 40.0D;
      }
      d1 = 0.5925925925925926D * d2 * (this.control.getNoOfDigits() + this.control.getNoOfDecimals());
    }
    if (d2 <= 0.0D)
    {
      if (d1 <= 0.0D) {
        d1 = 23.703703703703702D * (this.control.getNoOfDigits() + this.control.getNoOfDecimals());
      }
      d2 = d1 / (this.control.getNoOfDigits() + this.control.getNoOfDecimals()) * 1.6875D;
    }
    this.control.setPrefSize(d1, d2);
    registerChangeListener(this.control.rotationsProperty(), "ROTATION");
    registerChangeListener(this.control.rotationPresetProperty(), "ROTATION_PRESET");
    registerChangeListener(this.control.noOfDigitsProperty(), "NO_OF_DIGITS");
    registerChangeListener(this.control.noOfDecimalsProperty(), "NO_OF_DECIMALS");
    registerChangeListener(this.control.decimalColorProperty(), "DECIMAL_COLOR");
    registerChangeListener(this.control.colorProperty(), "COLOR");
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("ROTATION_PRESET".equals(paramString))
    {
      String str = Integer.toString(this.control.getRotations());
      int j = this.control.getNoOfDigits() + this.control.getNoOfDecimals();
      if (str.length() > j)
      {
        str = str.substring(str.length() - j);
      }
      else if (str.length() < j)
      {
        StringBuilder localStringBuilder = new StringBuilder(j);
        for (int m = 0; m < j - str.length(); m++) {
          localStringBuilder.append("0");
        }
        localStringBuilder.append(str);
        str = localStringBuilder.toString();
      }
      int k = this.listOfDials.size() - 1;
      for (char c : str.toCharArray())
      {
        ((Dial)this.listOfDials.get(k)).setToNumber(Integer.parseInt(String.valueOf(c)));
        k--;
      }
    }
    else if ("ROTATION".equals(paramString))
    {
      for (int i = 1; i < this.control.getNoOfDigits() + this.control.getNoOfDecimals() + 1; i++) {
        if (this.control.getRotations() == 0) {
          ((Dial)this.listOfDials.get(i - 1)).reset();
        } else {
          ((Dial)this.listOfDials.get(i - 1)).setNumber(this.control.getDialPosition(i));
        }
      }
    }
    else if ("NO_OF_DIGITS".equals(paramString))
    {
      this.control.setPrefSize(0.5925925925925926D * getPrefHeight() * (this.control.getNoOfDigits() + this.control.getNoOfDecimals()), getPrefHeight());
      repaint();
    }
    else if ("NO_OF_DECIMALS".equals(paramString))
    {
      this.control.setPrefSize(0.5925925925925926D * getPrefHeight() * (this.control.getNoOfDigits() + this.control.getNoOfDecimals()), getPrefHeight());
      repaint();
    }
    else if ("DECIMAL_COLOR".equals(paramString))
    {
      repaint();
    }
    else if ("COLOR".equals(paramString))
    {
      repaint();
    }
    else
    {
      double d;
      if ("PREF_WIDTH".equals(paramString))
      {
        d = this.control.getPrefWidth() / (this.control.getNoOfDigits() + this.control.getNoOfDecimals()) * 1.6875D;
        if (Double.compare(this.control.getPrefHeight(), d) != 0) {
          this.control.setPrefHeight(d);
        }
        repaint();
      }
      else if ("PREF_HEIGHT".equals(paramString))
      {
        d = this.control.getPrefHeight() * 0.5925925925925926D * (this.control.getNoOfDigits() + this.control.getNoOfDecimals());
        if (Double.compare(this.control.getPrefWidth(), d) != 0) {
          this.control.setPrefWidth(d);
        }
        repaint();
      }
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
      this.font = Font.loadFont(getClass().getResourceAsStream("/jfxtras/labs/scene/control/gauge/droidsansmono.ttf"), 0.85D * this.control.getPrefHeight());
      setClip(new Rectangle(0.0D, 1.0D, this.control.getPrefWidth(), this.control.getPrefHeight()));
      getChildren().clear();
      drawBackground();
      setupDials();
      drawForeground();
      getChildren().add(this.background);
      Iterator localIterator = this.listOfDials.iterator();
      while (localIterator.hasNext())
      {
        Dial localDial = (Dial)localIterator.next();
        getChildren().addAll(new Node[] { localDial.getNextNumberGroup(), localDial.getCurrentNumberGroup() });
      }
      getChildren().add(this.foreground);
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final Odometer getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  public final void drawBackground()
  {
    double d1 = this.control.getPrefWidth();
    double d2 = this.control.getPrefHeight();
    double d3 = this.control.getPrefHeight() * 0.5925925925925926D;
    this.background.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle1.setFill(this.control.getColor());
    localRectangle1.setStroke(null);
    this.background.getChildren().add(localRectangle1);
    Rectangle localRectangle2 = new Rectangle(0.0D, 0.0D, this.control.getNoOfDecimals() * d3, d2);
    localRectangle2.setFill(this.control.getDecimalColor());
    localRectangle2.setStroke(null);
    localRectangle2.setTranslateX(d1 - localRectangle2.getLayoutBounds().getWidth());
    this.background.getChildren().add(localRectangle2);
    for (int i = 0; i < this.control.getNoOfDigits(); i++)
    {
      Path localPath2 = new Path();
      localPath2.setFillRule(FillRule.EVEN_ODD);
      localPath2.getElements().add(new MoveTo(0.0D, 0.0D));
      localPath2.getElements().add(new LineTo(0.0D, d2));
      localPath2.getElements().add(new MoveTo(d3, 0.0D));
      localPath2.getElements().add(new LineTo(d3, d2));
      localPath2.setStrokeWidth(0.5D);
      localPath2.setStrokeType(StrokeType.CENTERED);
      localPath2.setTranslateX(i * d3);
      localPath2.setStroke(Color.color(0.0D, 0.0D, 0.0D, 0.8D));
      this.background.getChildren().add(localPath2);
    }
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(d1, 0.0D));
    localPath1.getElements().add(new LineTo(d1, d2));
    localPath1.setStrokeWidth(0.5D);
    localPath1.setStrokeType(StrokeType.CENTERED);
    localPath1.setStroke(Color.color(0.0D, 0.0D, 0.0D, 0.8D));
    this.background.getChildren().add(localPath1);
    this.background.setCache(true);
  }
  
  public final void drawForeground()
  {
    double d1 = this.control.getPrefWidth();
    double d2 = this.control.getPrefHeight();
    this.foreground.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d1, d2);
    LinearGradient localLinearGradient = new LinearGradient(0.0D, 0.0D, 0.0D, d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 1.0D)), new Stop(0.15D, Color.color(0.0D, 0.0D, 0.0D, 0.4D)), new Stop(0.33D, Color.color(1.0D, 1.0D, 1.0D, 0.45D)), new Stop(0.46D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.85D, Color.color(0.0D, 0.0D, 0.0D, 0.4D)), new Stop(1.0D, Color.color(0.0D, 0.0D, 0.0D, 1.0D)) });
    localRectangle.setFill(localLinearGradient);
    localRectangle.setStroke(null);
    this.foreground.getChildren().add(localRectangle);
  }
  
  public final void setupDials()
  {
    double d1 = this.control.getPrefHeight() * 0.5925925925925926D;
    double d2 = this.control.getPrefWidth();
    double d3 = this.control.getPrefHeight();
    for (int i = 0; i < this.control.getNoOfDigits() + this.control.getNoOfDecimals(); i++)
    {
      double d4 = d2 / 2.0D - (i + 1) * d1 + d1 / 2.0D;
      Color localColor = i < this.control.getNoOfDecimals() ? this.control.getNumberDecimalColor() : this.control.getNumberColor();
      Dial localDial = new Dial(localColor, d4, d3);
      this.listOfDials.add(localDial);
    }
  }
  
  private class Dial
  {
    private Group nextNumberGroup;
    private Text nextNumber = new Text();
    private Group currentNumberGroup;
    private Text currentNumber;
    private TranslateTransition next;
    private TranslateTransition current;
    private ParallelTransition parallel;
    
    protected Dial(Color paramColor, double paramDouble1, double paramDouble2)
    {
      this.nextNumberGroup = createGroup(paramColor, this.nextNumber, 1, paramDouble1, -paramDouble2);
      this.currentNumber = new Text();
      this.currentNumberGroup = createGroup(paramColor, this.currentNumber, 0, paramDouble1, 0.0D);
      this.next = new TranslateTransition(Duration.millis(OdometerSkin.this.control.getInterval()), this.nextNumberGroup);
      this.current = new TranslateTransition(Duration.millis(OdometerSkin.this.control.getInterval()), this.currentNumberGroup);
      this.next.setFromY(-OdometerSkin.this.control.getPrefHeight());
      this.next.setToY(0.0D);
      this.next.setInterpolator(Interpolator.LINEAR);
      this.current.setFromY(0.0D);
      this.current.setToY(OdometerSkin.this.control.getPrefHeight());
      this.current.setInterpolator(Interpolator.LINEAR);
      this.current.setDelay(Duration.ZERO);
      this.parallel = new ParallelTransition(new Animation[] { this.next, this.current });
    }
    
    protected void setNumber(int paramInt)
    {
      if (this.parallel.getStatus() == Status.RUNNING)
      {
        this.parallel.stop();
        increaseNumbers();
      }
      if (!Integer.toString(paramInt).equals(this.currentNumber.getText()))
      {
        this.parallel.play();
        this.parallel.setOnFinished(new EventHandler()
        {
          public void handle(ActionEvent paramAnonymousActionEvent)
          {
            Dial.this.increaseNumbers();
          }
        });
      }
    }
    
    protected void reset()
    {
      this.parallel.stop();
      this.nextNumberGroup.setTranslateY(-OdometerSkin.this.control.getPrefHeight());
      this.nextNumber.setText("1");
      this.currentNumberGroup.setTranslateY(0.0D);
      this.currentNumber.setText("0");
    }
    
    protected void setToNumber(int paramInt)
    {
      int i = paramInt > 9 ? 9 : paramInt < 0 ? 0 : paramInt;
      int j = i + 1 > 9 ? 0 : i + 1;
      this.parallel.stop();
      this.nextNumberGroup.setTranslateY(-OdometerSkin.this.control.getPrefHeight());
      this.nextNumber.setText(Integer.toString(j));
      this.currentNumberGroup.setTranslateY(0.0D);
      this.currentNumber.setText(Integer.toString(i));
    }
    
    protected Group getNextNumberGroup()
    {
      return this.nextNumberGroup;
    }
    
    protected Group getCurrentNumberGroup()
    {
      return this.currentNumberGroup;
    }
    
    private Group createGroup(Color paramColor, Text paramText, int paramInt, double paramDouble1, double paramDouble2)
    {
      Group localGroup = new Group();
      paramText.setText(Integer.toString(paramInt));
      paramText.setTextAlignment(TextAlignment.CENTER);
      paramText.setTextOrigin(VPos.CENTER);
      paramText.setFont(OdometerSkin.this.font);
      paramText.setFill(paramColor);
      paramText.setStroke(null);
      paramText.setFontSmoothingType(FontSmoothingType.LCD);
      localGroup.getChildren().addAll(new Node[] { paramText });
      localGroup.setTranslateX(paramDouble1);
      localGroup.setTranslateY(paramDouble2);
      return localGroup;
    }
    
    private void increaseNumbers()
    {
      int i = Integer.parseInt(this.nextNumber.getText());
      this.nextNumberGroup.setTranslateY(-OdometerSkin.this.control.getPrefHeight());
      this.nextNumber.setText(i == 9 ? "0" : Integer.toString(i + 1));
      this.currentNumberGroup.setTranslateY(0.0D);
      this.currentNumber.setText(Integer.toString(i));
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/OdometerSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */