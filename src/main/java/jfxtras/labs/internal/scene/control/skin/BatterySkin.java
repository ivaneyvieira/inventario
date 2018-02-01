package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.BatteryBehavior;
import jfxtras.labs.scene.control.gauge.Battery;
import jfxtras.labs.scene.control.gauge.Battery.ChargeIndicator;
import jfxtras.labs.scene.control.gauge.GradientLookup;

public class BatterySkin
  extends SkinBase<Battery, BatteryBehavior>
{
  private Battery control;
  private boolean isDirty;
  private boolean initialized;
  private Group background;
  private Group main;
  private Group foreground;
  private Path plug;
  private Path flashFrame;
  private Path flashMain;
  private Rectangle fluid;
  private Rectangle fluidHighlight;
  private GradientLookup lookup;
  private Color currentLevelColor;
  
  public BatterySkin(Battery paramBattery)
  {
    super(paramBattery, new BatteryBehavior(paramBattery));
    this.control = paramBattery;
    this.initialized = false;
    this.isDirty = false;
    this.background = new Group();
    this.main = new Group();
    this.foreground = new Group();
    this.plug = new Path();
    this.flashFrame = new Path();
    this.flashMain = new Path();
    this.fluid = new Rectangle();
    this.fluidHighlight = new Rectangle();
    this.lookup = new GradientLookup(this.control.getLevelColors());
    this.currentLevelColor = Color.RED;
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(120.0D, 255.0D);
    }
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    registerChangeListener(this.control.chargingProperty(), "CHARGING");
    registerChangeListener(this.control.chargeIndicatorProperty(), "CHARGE_INDICATOR");
    registerChangeListener(this.control.chargingLevelProperty(), "CHARGE_LEVEL");
    registerChangeListener(this.control.levelColorsProperty(), "LEVEL_COLORS");
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("CHARGING".equals(paramString))
    {
      this.plug.setVisible(this.control.isCharging());
      this.flashFrame.setVisible(this.control.isCharging());
      this.flashMain.setVisible(this.control.isCharging());
    }
    else if ("CHARGE_INDICATOR".equals(paramString))
    {
      if (this.control.getChargeIndicator() == ChargeIndicator.PLUG)
      {
        this.plug.setOpacity(1.0D);
        this.flashFrame.setOpacity(0.0D);
        this.flashMain.setOpacity(0.0D);
      }
      else
      {
        this.plug.setOpacity(0.0D);
        this.flashFrame.setOpacity(1.0D);
        this.flashMain.setOpacity(1.0D);
      }
    }
    else if ("CHARGE_LEVEL".equals(paramString))
    {
      this.currentLevelColor = this.lookup.getColorAt(this.control.getChargingLevel());
      updateFluid();
    }
    else if ("LEVEL_COLORS".equals(paramString))
    {
      this.lookup = new GradientLookup(this.control.getLevelColors());
      updateFluid();
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
  
  public void repaint()
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
      drawMain();
      drawForeground();
      getChildren().setAll(new Node[] { this.background, this.main, this.foreground });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final Battery getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 255.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 255.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  public final void drawBackground()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.background.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    this.background.getChildren().add(localRectangle1);
    Path localPath = new Path();
    localPath.setFillRule(FillRule.EVEN_ODD);
    localPath.getElements().add(new MoveTo(0.27450980392156865D * d2, 0.9372549019607843D * d3));
    localPath.getElements().add(new CubicCurveTo(0.27450980392156865D * d2, 0.9294117647058824D * d3, 0.3764705882352941D * d2, 0.9215686274509803D * d3, 0.5019607843137255D * d2, 0.9215686274509803D * d3));
    localPath.getElements().add(new CubicCurveTo(0.6274509803921569D * d2, 0.9215686274509803D * d3, 0.7294117647058823D * d2, 0.9294117647058824D * d3, 0.7294117647058823D * d2, 0.9372549019607843D * d3));
    localPath.getElements().add(new CubicCurveTo(0.7294117647058823D * d2, 0.9450980392156862D * d3, 0.6274509803921569D * d2, 0.9529411764705882D * d3, 0.5019607843137255D * d2, 0.9529411764705882D * d3));
    localPath.getElements().add(new CubicCurveTo(0.3764705882352941D * d2, 0.9529411764705882D * d3, 0.27450980392156865D * d2, 0.9450980392156862D * d3, 0.27450980392156865D * d2, 0.9372549019607843D * d3));
    localPath.getElements().add(new ClosePath());
    Color localColor = Color.color(0.2D, 0.2D, 0.2D, 1.0D);
    localPath.setFill(localColor);
    localPath.setStroke(null);
    Rectangle localRectangle2 = new Rectangle(0.2745098039D * d2, 0.1137254902D * d3, 0.4549019608D * d2, 0.8235294118D * d3);
    LinearGradient localLinearGradient = new LinearGradient(0.2745098039D * d2, 0.0D, 0.2745098039D * d2 + 0.4549019608D * d2, 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.2D, 0.2D, 0.2D, 0.15D)), new Stop(0.23D, Color.color(0.4D, 0.4D, 0.4D, 0.13D)), new Stop(0.48D, Color.color(0.9764705882D, 0.9764705882D, 0.9764705882D, 0.1D)), new Stop(0.49D, Color.color(1.0D, 1.0D, 1.0D, 0.1D)), new Stop(0.81D, Color.color(0.6D, 0.6D, 0.6D, 0.13D)), new Stop(1.0D, Color.color(0.2D, 0.2D, 0.2D, 0.15D)) });
    localRectangle2.setFill(localLinearGradient);
    localRectangle2.setStroke(null);
    this.background.getChildren().addAll(new Node[] { localPath, localRectangle2 });
    this.background.setCache(true);
  }
  
  public final void drawMain()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.main.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    this.main.getChildren().add(localRectangle);
    this.fluid = new Rectangle(0.2745098039D * d2, 0.1137254902D * d3, 0.4549019608D * d2, 0.8235294118D * d3);
    LinearGradient localLinearGradient1 = new LinearGradient(0.2745098039D * d2, 0.0D, 0.2745098039D * d2 + 0.4549019608D * d2, 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.1411764706D, 0.2666666667D, 0.137254902D, 1.0D)), new Stop(0.23D, Color.color(0.1647058824D, 0.5450980392D, 0.0D, 1.0D)), new Stop(0.49D, Color.color(0.4666666667D, 0.8588235294D, 0.0D, 1.0D)), new Stop(0.81D, Color.color(0.1647058824D, 0.5450980392D, 0.0D, 1.0D)), new Stop(1.0D, Color.color(0.1411764706D, 0.2666666667D, 0.137254902D, 1.0D)) });
    this.fluid.setFill(localLinearGradient1);
    this.fluid.setStroke(null);
    if (Double.compare(this.control.getChargingLevel(), 0.0D) == 0) {
      this.fluid.setVisible(false);
    }
    this.fluidHighlight = new Rectangle(0.2745098039D * d2, 0.1137254902D * d3, 0.4549019608D * d2, 0.0078431373D * d3);
    LinearGradient localLinearGradient2 = new LinearGradient(0.2745098039D * d2, 0.0D, 0.2745098039D * d2 + 0.4549019608D * d2, 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4980392157D)), new Stop(0.35D, Color.color(1.0D, 1.0D, 1.0D, 0.7764705882D)), new Stop(0.63D, Color.color(1.0D, 1.0D, 1.0D, 0.7843137255D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4980392157D)) });
    this.fluidHighlight.setFill(localLinearGradient2);
    this.fluidHighlight.setStroke(null);
    this.plug = new Path();
    this.plug.setFillRule(FillRule.EVEN_ODD);
    this.plug.getElements().add(new MoveTo(0.48627450980392156D * d2, 0.4196078431372549D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.48627450980392156D * d2, 0.4196078431372549D * d3, 0.48627450980392156D * d2, 0.34509803921568627D * d3, 0.48627450980392156D * d2, 0.34509803921568627D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.48627450980392156D * d2, 0.3411764705882353D * d3, 0.47843137254901963D * d2, 0.33725490196078434D * d3, 0.4745098039215686D * d2, 0.33725490196078434D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.4745098039215686D * d2, 0.33725490196078434D * d3, 0.47058823529411764D * d2, 0.33725490196078434D * d3, 0.47058823529411764D * d2, 0.33725490196078434D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.4627450980392157D * d2, 0.33725490196078434D * d3, 0.4588235294117647D * d2, 0.3411764705882353D * d3, 0.4588235294117647D * d2, 0.34901960784313724D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.4588235294117647D * d2, 0.34901960784313724D * d3, 0.4588235294117647D * d2, 0.4196078431372549D * d3, 0.4588235294117647D * d2, 0.4196078431372549D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.43137254901960786D * d2, 0.4196078431372549D * d3, 0.41568627450980394D * d2, 0.4235294117647059D * d3, 0.41568627450980394D * d2, 0.4235294117647059D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.41568627450980394D * d2, 0.5137254901960784D * d3, 0.44313725490196076D * d2, 0.5686274509803921D * d3, 0.48627450980392156D * d2, 0.5803921568627451D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.48627450980392156D * d2, 0.5803921568627451D * d3, 0.48627450980392156D * d2, 0.6588235294117647D * d3, 0.48627450980392156D * d2, 0.6588235294117647D * d3));
    this.plug.getElements().add(new LineTo(0.5294117647058824D * d2, 0.6588235294117647D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5294117647058824D * d2, 0.6588235294117647D * d3, 0.5294117647058824D * d2, 0.5843137254901961D * d3, 0.5294117647058824D * d2, 0.5843137254901961D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5725490196078431D * d2, 0.5725490196078431D * d3, 0.6039215686274509D * d2, 0.5294117647058824D * d3, 0.6039215686274509D * d2, 0.4235294117647059D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.6039215686274509D * d2, 0.4235294117647059D * d3, 0.5882352941176471D * d2, 0.4235294117647059D * d3, 0.5607843137254902D * d2, 0.4196078431372549D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5607843137254902D * d2, 0.4196078431372549D * d3, 0.5607843137254902D * d2, 0.34901960784313724D * d3, 0.5607843137254902D * d2, 0.34901960784313724D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5607843137254902D * d2, 0.3411764705882353D * d3, 0.5568627450980392D * d2, 0.33725490196078434D * d3, 0.5490196078431373D * d2, 0.33725490196078434D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5490196078431373D * d2, 0.33725490196078434D * d3, 0.5411764705882353D * d2, 0.33725490196078434D * d3, 0.5411764705882353D * d2, 0.33725490196078434D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5333333333333333D * d2, 0.33725490196078434D * d3, 0.5294117647058824D * d2, 0.3411764705882353D * d3, 0.5294117647058824D * d2, 0.34901960784313724D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5294117647058824D * d2, 0.34901960784313724D * d3, 0.5294117647058824D * d2, 0.4196078431372549D * d3, 0.5294117647058824D * d2, 0.4196078431372549D * d3));
    this.plug.getElements().add(new LineTo(0.48627450980392156D * d2, 0.4196078431372549D * d3));
    this.plug.getElements().add(new ClosePath());
    this.plug.setFill(Color.rgb(75, 75, 75));
    this.plug.setStroke(null);
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.01411764705882353D * this.plug.getLayoutBounds().getWidth());
    localInnerShadow1.setHeight(0.01411764705882353D * this.plug.getLayoutBounds().getHeight());
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setOffsetY(-0.0D);
    localInnerShadow1.setRadius(0.01411764705882353D * this.plug.getLayoutBounds().getWidth());
    localInnerShadow1.setColor(Color.BLACK);
    localInnerShadow1.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow1.inputProperty().set(null);
    this.plug.setEffect(localInnerShadow1);
    if (this.control.getChargeIndicator() == ChargeIndicator.PLUG) {
      this.plug.setOpacity(1.0D);
    } else {
      this.plug.setOpacity(0.0D);
    }
    if (!this.control.isCharging()) {
      this.plug.setVisible(false);
    }
    this.flashFrame = new Path();
    this.flashFrame.setFillRule(FillRule.EVEN_ODD);
    this.flashFrame.getElements().add(new MoveTo(0.3568627450980392D * d2, 0.37254901960784315D * d3));
    this.flashFrame.getElements().add(new LineTo(0.5372549019607843D * d2, 0.6274509803921569D * d3));
    this.flashFrame.getElements().add(new LineTo(0.5372549019607843D * d2, 0.5254901960784314D * d3));
    this.flashFrame.getElements().add(new LineTo(0.6392156862745098D * d2, 0.615686274509804D * d3));
    this.flashFrame.getElements().add(new LineTo(0.49411764705882355D * d2, 0.3843137254901961D * d3));
    this.flashFrame.getElements().add(new LineTo(0.49411764705882355D * d2, 0.5019607843137255D * d3));
    this.flashFrame.getElements().add(new LineTo(0.3568627450980392D * d2, 0.37254901960784315D * d3));
    this.flashFrame.getElements().add(new ClosePath());
    this.flashFrame.setFill(Color.WHITE);
    this.flashFrame.setStroke(null);
    if (this.control.getChargeIndicator() == ChargeIndicator.FLASH) {
      this.flashFrame.setOpacity(1.0D);
    } else {
      this.flashFrame.setOpacity(0.0D);
    }
    if (!this.control.isCharging()) {
      this.flashFrame.setVisible(false);
    }
    this.flashMain = new Path();
    this.flashMain.setFillRule(FillRule.EVEN_ODD);
    this.flashMain.getElements().add(new MoveTo(0.3843137254901961D * d2, 0.403921568627451D * d3));
    this.flashMain.getElements().add(new LineTo(0.5333333333333333D * d2, 0.611764705882353D * d3));
    this.flashMain.getElements().add(new LineTo(0.5333333333333333D * d2, 0.5137254901960784D * d3));
    this.flashMain.getElements().add(new LineTo(0.6196078431372549D * d2, 0.592156862745098D * d3));
    this.flashMain.getElements().add(new LineTo(0.4980392156862745D * d2, 0.4D * d3));
    this.flashMain.getElements().add(new LineTo(0.4980392156862745D * d2, 0.5137254901960784D * d3));
    this.flashMain.getElements().add(new LineTo(0.3843137254901961D * d2, 0.403921568627451D * d3));
    this.flashMain.getElements().add(new ClosePath());
    this.flashMain.setFill(Color.color(0.9960784314D, 0.9215686275D, 0.0D, 1.0D));
    this.flashMain.setStroke(null);
    if (this.control.getChargeIndicator() == ChargeIndicator.FLASH) {
      this.flashMain.setOpacity(1.0D);
    } else {
      this.flashMain.setOpacity(0.0D);
    }
    if (!this.control.isCharging()) {
      this.flashMain.setVisible(false);
    }
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setWidth(0.08470588235294117D * this.flashMain.getLayoutBounds().getWidth());
    localInnerShadow2.setHeight(0.08470588235294117D * this.flashMain.getLayoutBounds().getHeight());
    localInnerShadow2.setOffsetX(0.0D);
    localInnerShadow2.setOffsetY(0.0D);
    localInnerShadow2.setRadius(0.08470588235294117D * this.flashMain.getLayoutBounds().getWidth());
    localInnerShadow2.setColor(Color.color(0.8509803922D, 0.5294117647D, 0.0D, 1.0D));
    localInnerShadow2.setBlurType(BlurType.GAUSSIAN);
    this.flashMain.setEffect(localInnerShadow2);
    this.main.getChildren().addAll(new Node[] { this.fluid, this.fluidHighlight, this.plug, this.flashFrame, this.flashMain });
  }
  
  private final void updateFluid()
  {
    Platform.runLater(new Runnable()
    {
      public void run()
      {
        if (Double.compare(BatterySkin.this.control.getChargingLevel(), 0.0D) == 0) {
          BatterySkin.this.fluid.setVisible(false);
        } else {
          BatterySkin.this.fluid.setVisible(true);
        }
        BatterySkin.this.fluid.setHeight(BatterySkin.this.control.getChargingLevel() * 0.8235294118D * BatterySkin.this.control.getPrefHeight());
        BatterySkin.this.fluid.setY(0.1137254902D * BatterySkin.this.control.getPrefHeight() + (0.8235294118D * BatterySkin.this.control.getPrefHeight() - BatterySkin.this.fluid.getHeight()));
        BatterySkin.this.fluid.setFill(new LinearGradient(0.0166666667D * BatterySkin.this.control.getPrefWidth(), 0.0D, 0.0166666667D * BatterySkin.this.control.getPrefWidth() + 0.9666666667D * BatterySkin.this.control.getPrefWidth(), 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.hsb(BatterySkin.this.currentLevelColor.getHue(), BatterySkin.this.currentLevelColor.getSaturation(), 0.1D)), new Stop(0.2D, Color.hsb(BatterySkin.this.currentLevelColor.getHue(), BatterySkin.this.currentLevelColor.getSaturation(), 0.4D)), new Stop(0.5D, BatterySkin.this.currentLevelColor), new Stop(0.8D, Color.hsb(BatterySkin.this.currentLevelColor.getHue(), BatterySkin.this.currentLevelColor.getSaturation(), 0.4D)), new Stop(1.0D, Color.hsb(BatterySkin.this.currentLevelColor.getHue(), BatterySkin.this.currentLevelColor.getSaturation(), 0.1D)) }));
        BatterySkin.this.fluidHighlight.setY(0.1137254902D * BatterySkin.this.control.getPrefHeight() + (0.8235294118D * BatterySkin.this.control.getPrefHeight() - BatterySkin.this.fluid.getHeight()));
      }
    });
  }
  
  public final void drawForeground()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.foreground.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle1.setOpacity(0.0D);
    this.foreground.getChildren().add(localRectangle1);
    Rectangle localRectangle2 = new Rectangle(0.2705882353D * d2, 0.1137254902D * d3, 0.462745098D * d2, 0.8235294118D * d3);
    LinearGradient localLinearGradient1 = new LinearGradient(0.2705882353D * d2, 0.0D, 0.2705882353D * d2 + 0.462745098D * d2, 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2862745098D)), new Stop(0.04D, Color.color(1.0D, 1.0D, 1.0D, 0.1176470588D)), new Stop(0.05D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.11D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.13D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.15D, Color.color(1.0D, 1.0D, 1.0D, 0.168627451D)), new Stop(0.151D, Color.color(1.0D, 1.0D, 1.0D, 0.2D)), new Stop(0.39D, Color.color(1.0D, 1.0D, 1.0D, 0.031372549D)), new Stop(0.43D, Color.color(1.0D, 1.0D, 1.0D, 0.0392156863D)), new Stop(0.44D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.55D, Color.color(1.0D, 1.0D, 1.0D, 0.0901960784D)), new Stop(0.56D, Color.color(1.0D, 1.0D, 1.0D, 0.0980392157D)), new Stop(0.69D, Color.color(1.0D, 1.0D, 1.0D, 0.0078431373D)), new Stop(0.7D, Color.color(1.0D, 1.0D, 1.0D, 0.0352941176D)), new Stop(0.78D, Color.color(1.0D, 1.0D, 1.0D, 0.0862745098D)), new Stop(0.79D, Color.color(1.0D, 1.0D, 1.0D, 0.0980392157D)), new Stop(0.8D, Color.color(1.0D, 1.0D, 1.0D, 0.1137254902D)), new Stop(0.81D, Color.color(1.0D, 1.0D, 1.0D, 0.1490196078D)), new Stop(0.89D, Color.color(1.0D, 1.0D, 1.0D, 0.1960784314D)), new Stop(0.891D, Color.color(1.0D, 1.0D, 1.0D, 0.2D)), new Stop(0.92D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.93D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.96D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.97D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.98D, Color.color(1.0D, 1.0D, 1.0D, 0.2235294118D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4980392157D)) });
    localRectangle2.setFill(localLinearGradient1);
    localRectangle2.setStroke(null);
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.27058823529411763D * d2, 0.11372549019607843D * d3));
    localPath1.getElements().add(new LineTo(0.7333333333333333D * d2, 0.11372549019607843D * d3));
    localPath1.getElements().add(new LineTo(0.7333333333333333D * d2, 0.3176470588235294D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.7333333333333333D * d2, 0.3176470588235294D * d3, 0.6705882352941176D * d2, 0.3411764705882353D * d3, 0.5843137254901961D * d2, 0.41568627450980394D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.4980392156862745D * d2, 0.47843137254901963D * d3, 0.396078431372549D * d2, 0.48627450980392156D * d3, 0.396078431372549D * d2, 0.48627450980392156D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.34509803921568627D * d2, 0.48627450980392156D * d3, 0.27058823529411763D * d2, 0.47058823529411764D * d3, 0.27058823529411763D * d2, 0.47058823529411764D * d3));
    localPath1.getElements().add(new LineTo(0.27058823529411763D * d2, 0.11372549019607843D * d3));
    localPath1.getElements().add(new ClosePath());
    LinearGradient localLinearGradient2 = new LinearGradient(0.2705882353D * d2, 0.0D, 0.2705882353D * d2 + 0.462745098D * d2, 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2862745098D)), new Stop(0.04D, Color.color(1.0D, 1.0D, 1.0D, 0.1176470588D)), new Stop(0.05D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.11D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.13D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.15D, Color.color(1.0D, 1.0D, 1.0D, 0.168627451D)), new Stop(0.151D, Color.color(1.0D, 1.0D, 1.0D, 0.2D)), new Stop(0.39D, Color.color(1.0D, 1.0D, 1.0D, 0.031372549D)), new Stop(0.43D, Color.color(1.0D, 1.0D, 1.0D, 0.0392156863D)), new Stop(0.44D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.55D, Color.color(1.0D, 1.0D, 1.0D, 0.0901960784D)), new Stop(0.56D, Color.color(1.0D, 1.0D, 1.0D, 0.0980392157D)), new Stop(0.69D, Color.color(1.0D, 1.0D, 1.0D, 0.0078431373D)), new Stop(0.7D, Color.color(1.0D, 1.0D, 1.0D, 0.0352941176D)), new Stop(0.78D, Color.color(1.0D, 1.0D, 1.0D, 0.0862745098D)), new Stop(0.79D, Color.color(1.0D, 1.0D, 1.0D, 0.0980392157D)), new Stop(0.8D, Color.color(1.0D, 1.0D, 1.0D, 0.1137254902D)), new Stop(0.81D, Color.color(1.0D, 1.0D, 1.0D, 0.1490196078D)), new Stop(0.89D, Color.color(1.0D, 1.0D, 1.0D, 0.1960784314D)), new Stop(0.891D, Color.color(1.0D, 1.0D, 1.0D, 0.2D)), new Stop(0.92D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(0.93D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0470588235D)) });
    localPath1.setFill(localLinearGradient2);
    localPath1.setStroke(null);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.26666666666666666D * d2, 0.9490196078431372D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.26666666666666666D * d2, 0.9411764705882353D * d3, 0.27058823529411763D * d2, 0.9333333333333333D * d3, 0.2784313725490196D * d2, 0.9333333333333333D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.2784313725490196D * d2, 0.9333333333333333D * d3, 0.3568627450980392D * d2, 0.9372549019607843D * d3, 0.5019607843137255D * d2, 0.9372549019607843D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.6431372549019608D * d2, 0.9372549019607843D * d3, 0.7254901960784313D * d2, 0.9333333333333333D * d3, 0.7254901960784313D * d2, 0.9333333333333333D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.7333333333333333D * d2, 0.9333333333333333D * d3, 0.7372549019607844D * d2, 0.9411764705882353D * d3, 0.7372549019607844D * d2, 0.9490196078431372D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.7372549019607844D * d2, 0.9490196078431372D * d3, 0.7372549019607844D * d2, 0.9764705882352941D * d3, 0.7372549019607844D * d2, 0.9764705882352941D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.7372549019607844D * d2, 0.984313725490196D * d3, 0.7333333333333333D * d2, 0.9921568627450981D * d3, 0.7254901960784313D * d2, 0.9921568627450981D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.7254901960784313D * d2, 0.9921568627450981D * d3, 0.6823529411764706D * d2, 0.996078431372549D * d3, 0.5019607843137255D * d2, 0.996078431372549D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.3254901960784314D * d2, 0.996078431372549D * d3, 0.2784313725490196D * d2, 0.9921568627450981D * d3, 0.2784313725490196D * d2, 0.9921568627450981D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.27058823529411763D * d2, 0.9921568627450981D * d3, 0.26666666666666666D * d2, 0.984313725490196D * d3, 0.26666666666666666D * d2, 0.9764705882352941D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.26666666666666666D * d2, 0.9764705882352941D * d3, 0.26666666666666666D * d2, 0.9490196078431372D * d3, 0.26666666666666666D * d2, 0.9490196078431372D * d3));
    localPath2.getElements().add(new ClosePath());
    LinearGradient localLinearGradient3 = new LinearGradient(0.26666666666666666D * d2, 0.9607843137254902D * d3, 0.7372549019607844D * d2, 0.9607843137254902D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.368627451D, 0.3725490196D, 0.3803921569D, 1.0D)), new Stop(0.13D, Color.color(0.2D, 0.2D, 0.2D, 1.0D)), new Stop(0.15D, Color.color(0.9254901961D, 0.9254901961D, 0.9333333333D, 1.0D)), new Stop(0.4D, Color.color(0.6156862745D, 0.6196078431D, 0.6274509804D, 1.0D)), new Stop(0.44D, Color.BLACK), new Stop(0.78D, Color.color(0.0862745098D, 0.0784313725D, 0.0901960784D, 1.0D)), new Stop(0.89D, Color.color(0.9294117647D, 0.9294117647D, 0.9294117647D, 1.0D)), new Stop(0.92D, Color.color(0.0980392157D, 0.0901960784D, 0.1058823529D, 1.0D)), new Stop(0.97D, Color.BLACK), new Stop(1.0D, Color.color(0.3803921569D, 0.3921568627D, 0.4117647059D, 1.0D)) });
    localPath2.setFill(localLinearGradient3);
    localPath2.setStroke(null);
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.07058823529411765D * localPath2.getLayoutBounds().getWidth());
    localInnerShadow1.setHeight(0.07058823529411765D * localPath2.getLayoutBounds().getHeight());
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setOffsetY(0.0D);
    localInnerShadow1.setRadius(0.07058823529411765D * localPath2.getLayoutBounds().getWidth());
    localInnerShadow1.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.6470588235D));
    localInnerShadow1.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow1.inputProperty().set(null);
    localPath2.setEffect(localInnerShadow1);
    Path localPath3 = new Path();
    localPath3.setFillRule(FillRule.EVEN_ODD);
    localPath3.getElements().add(new MoveTo(0.26666666666666666D * d2, 0.07058823529411765D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.26666666666666666D * d2, 0.06274509803921569D * d3, 0.27058823529411763D * d2, 0.054901960784313725D * d3, 0.2784313725490196D * d2, 0.054901960784313725D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.2784313725490196D * d2, 0.054901960784313725D * d3, 0.3568627450980392D * d2, 0.058823529411764705D * d3, 0.5019607843137255D * d2, 0.058823529411764705D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.6431372549019608D * d2, 0.058823529411764705D * d3, 0.7254901960784313D * d2, 0.054901960784313725D * d3, 0.7254901960784313D * d2, 0.054901960784313725D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.7333333333333333D * d2, 0.054901960784313725D * d3, 0.7372549019607844D * d2, 0.06274509803921569D * d3, 0.7372549019607844D * d2, 0.07058823529411765D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.7372549019607844D * d2, 0.07058823529411765D * d3, 0.7372549019607844D * d2, 0.09803921568627451D * d3, 0.7372549019607844D * d2, 0.09803921568627451D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.7372549019607844D * d2, 0.10588235294117647D * d3, 0.7333333333333333D * d2, 0.11372549019607843D * d3, 0.7254901960784313D * d2, 0.11372549019607843D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.7254901960784313D * d2, 0.11372549019607843D * d3, 0.6823529411764706D * d2, 0.11764705882352941D * d3, 0.5019607843137255D * d2, 0.11764705882352941D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.3254901960784314D * d2, 0.12156862745098039D * d3, 0.2784313725490196D * d2, 0.11372549019607843D * d3, 0.2784313725490196D * d2, 0.11372549019607843D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.27058823529411763D * d2, 0.11372549019607843D * d3, 0.26666666666666666D * d2, 0.10588235294117647D * d3, 0.26666666666666666D * d2, 0.09803921568627451D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.26666666666666666D * d2, 0.09803921568627451D * d3, 0.26666666666666666D * d2, 0.07058823529411765D * d3, 0.26666666666666666D * d2, 0.07058823529411765D * d3));
    localPath3.getElements().add(new ClosePath());
    LinearGradient localLinearGradient4 = new LinearGradient(0.26666666666666666D * d2, 0.08235294117647059D * d3, 0.7372549019607844D * d2, 0.08235294117647059D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.368627451D, 0.3725490196D, 0.3803921569D, 1.0D)), new Stop(0.13D, Color.color(0.2D, 0.2D, 0.2D, 1.0D)), new Stop(0.15D, Color.color(0.9254901961D, 0.9254901961D, 0.9333333333D, 1.0D)), new Stop(0.4D, Color.color(0.6156862745D, 0.6196078431D, 0.6274509804D, 1.0D)), new Stop(0.44D, Color.BLACK), new Stop(0.78D, Color.color(0.0862745098D, 0.0784313725D, 0.0901960784D, 1.0D)), new Stop(0.89D, Color.color(0.9294117647D, 0.9294117647D, 0.9294117647D, 1.0D)), new Stop(0.92D, Color.color(0.0980392157D, 0.0901960784D, 0.1058823529D, 1.0D)), new Stop(0.97D, Color.BLACK), new Stop(1.0D, Color.color(0.3803921569D, 0.3921568627D, 0.4117647059D, 1.0D)) });
    localPath3.setFill(localLinearGradient4);
    localPath3.setStroke(null);
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setWidth(0.07058823529411765D * localPath3.getLayoutBounds().getWidth());
    localInnerShadow2.setHeight(0.07058823529411765D * localPath3.getLayoutBounds().getHeight());
    localInnerShadow2.setOffsetX(0.0D);
    localInnerShadow2.setOffsetY(0.0D);
    localInnerShadow2.setRadius(0.07058823529411765D * localPath3.getLayoutBounds().getWidth());
    localInnerShadow2.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.6470588235D));
    localInnerShadow2.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow2.inputProperty().set(null);
    localPath3.setEffect(localInnerShadow2);
    Path localPath4 = new Path();
    localPath4.setFillRule(FillRule.EVEN_ODD);
    localPath4.getElements().add(new MoveTo(0.3843137254901961D * d2, 0.058823529411764705D * d3));
    localPath4.getElements().add(new CubicCurveTo(0.3843137254901961D * d2, 0.058823529411764705D * d3, 0.2784313725490196D * d2, 0.054901960784313725D * d3, 0.2784313725490196D * d2, 0.054901960784313725D * d3));
    localPath4.getElements().add(new CubicCurveTo(0.28627450980392155D * d2, 0.07058823529411765D * d3, 0.30980392156862746D * d2, 0.08235294117647059D * d3, 0.3333333333333333D * d2, 0.08235294117647059D * d3));
    localPath4.getElements().add(new CubicCurveTo(0.3568627450980392D * d2, 0.08235294117647059D * d3, 0.3803921568627451D * d2, 0.07450980392156863D * d3, 0.3843137254901961D * d2, 0.058823529411764705D * d3));
    localPath4.getElements().add(new ClosePath());
    RadialGradient localRadialGradient1 = new RadialGradient(0.0D, 0.0D, 0.3333333333333333D * d2, 0.047058823529411764D * d3, 0.049019607843137254D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.WHITE), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) });
    localPath4.setFill(localRadialGradient1);
    localPath4.setStroke(null);
    Path localPath5 = new Path();
    localPath5.setFillRule(FillRule.EVEN_ODD);
    localPath5.getElements().add(new MoveTo(0.3803921568627451D * d2, 0.011764705882352941D * d3));
    localPath5.getElements().add(new CubicCurveTo(0.3803921568627451D * d2, 0.00784313725490196D * d3, 0.3843137254901961D * d2, 0.00392156862745098D * d3, 0.38823529411764707D * d2, 0.00392156862745098D * d3));
    localPath5.getElements().add(new CubicCurveTo(0.38823529411764707D * d2, 0.00392156862745098D * d3, 0.41568627450980394D * d2, 0.0D, 0.41568627450980394D * d2, 0.0D));
    localPath5.getElements().add(new LineTo(0.5725490196078431D * d2, 0.0D));
    localPath5.getElements().add(new CubicCurveTo(0.5725490196078431D * d2, 0.0D, 0.6078431372549019D * d2, 0.00392156862745098D * d3, 0.6078431372549019D * d2, 0.00392156862745098D * d3));
    localPath5.getElements().add(new CubicCurveTo(0.6078431372549019D * d2, 0.00392156862745098D * d3, 0.611764705882353D * d2, 0.00784313725490196D * d3, 0.611764705882353D * d2, 0.011764705882352941D * d3));
    localPath5.getElements().add(new CubicCurveTo(0.611764705882353D * d2, 0.011764705882352941D * d3, 0.611764705882353D * d2, 0.050980392156862744D * d3, 0.611764705882353D * d2, 0.050980392156862744D * d3));
    localPath5.getElements().add(new CubicCurveTo(0.611764705882353D * d2, 0.054901960784313725D * d3, 0.6078431372549019D * d2, 0.058823529411764705D * d3, 0.6078431372549019D * d2, 0.058823529411764705D * d3));
    localPath5.getElements().add(new CubicCurveTo(0.6078431372549019D * d2, 0.058823529411764705D * d3, 0.38823529411764707D * d2, 0.058823529411764705D * d3, 0.38823529411764707D * d2, 0.058823529411764705D * d3));
    localPath5.getElements().add(new CubicCurveTo(0.3843137254901961D * d2, 0.058823529411764705D * d3, 0.3803921568627451D * d2, 0.054901960784313725D * d3, 0.3803921568627451D * d2, 0.050980392156862744D * d3));
    localPath5.getElements().add(new CubicCurveTo(0.3803921568627451D * d2, 0.050980392156862744D * d3, 0.3803921568627451D * d2, 0.011764705882352941D * d3, 0.3803921568627451D * d2, 0.011764705882352941D * d3));
    localPath5.getElements().add(new ClosePath());
    LinearGradient localLinearGradient5 = new LinearGradient(0.3803921568627451D * d2, 0.03137254901960784D * d3, 0.6078431372549019D * d2, 0.03137254901960784D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.8784313725D, 0.8588235294D, 0.8666666667D, 1.0D)), new Stop(1.0D, Color.color(0.1647058824D, 0.1450980392D, 0.1921568627D, 1.0D)) });
    localPath5.setFill(localLinearGradient5);
    localPath5.setStroke(null);
    Path localPath6 = new Path();
    localPath6.setFillRule(FillRule.EVEN_ODD);
    localPath6.getElements().add(new MoveTo(0.3843137254901961D * d2, 0.01568627450980392D * d3));
    localPath6.getElements().add(new CubicCurveTo(0.3843137254901961D * d2, 0.011764705882352941D * d3, 0.38823529411764707D * d2, 0.00784313725490196D * d3, 0.39215686274509803D * d2, 0.00784313725490196D * d3));
    localPath6.getElements().add(new CubicCurveTo(0.39215686274509803D * d2, 0.00784313725490196D * d3, 0.4117647058823529D * d2, 0.00392156862745098D * d3, 0.4117647058823529D * d2, 0.00392156862745098D * d3));
    localPath6.getElements().add(new LineTo(0.5803921568627451D * d2, 0.00392156862745098D * d3));
    localPath6.getElements().add(new CubicCurveTo(0.5803921568627451D * d2, 0.00392156862745098D * d3, 0.6039215686274509D * d2, 0.00784313725490196D * d3, 0.6039215686274509D * d2, 0.00784313725490196D * d3));
    localPath6.getElements().add(new CubicCurveTo(0.6039215686274509D * d2, 0.00784313725490196D * d3, 0.6078431372549019D * d2, 0.011764705882352941D * d3, 0.6078431372549019D * d2, 0.01568627450980392D * d3));
    localPath6.getElements().add(new CubicCurveTo(0.6078431372549019D * d2, 0.01568627450980392D * d3, 0.6078431372549019D * d2, 0.047058823529411764D * d3, 0.6078431372549019D * d2, 0.047058823529411764D * d3));
    localPath6.getElements().add(new CubicCurveTo(0.6078431372549019D * d2, 0.050980392156862744D * d3, 0.6039215686274509D * d2, 0.054901960784313725D * d3, 0.6039215686274509D * d2, 0.054901960784313725D * d3));
    localPath6.getElements().add(new CubicCurveTo(0.6039215686274509D * d2, 0.054901960784313725D * d3, 0.39215686274509803D * d2, 0.054901960784313725D * d3, 0.39215686274509803D * d2, 0.054901960784313725D * d3));
    localPath6.getElements().add(new CubicCurveTo(0.38823529411764707D * d2, 0.054901960784313725D * d3, 0.3843137254901961D * d2, 0.050980392156862744D * d3, 0.3843137254901961D * d2, 0.047058823529411764D * d3));
    localPath6.getElements().add(new CubicCurveTo(0.3843137254901961D * d2, 0.047058823529411764D * d3, 0.3843137254901961D * d2, 0.01568627450980392D * d3, 0.3843137254901961D * d2, 0.01568627450980392D * d3));
    localPath6.getElements().add(new ClosePath());
    LinearGradient localLinearGradient6 = new LinearGradient(0.3843137254901961D * d2, 0.027450980392156862D * d3, 0.6039215686274509D * d2, 0.027450980392156862D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.8156862745D, 0.8196078431D, 0.8470588235D, 1.0D)), new Stop(0.11D, Color.color(0.2D, 0.2D, 0.2D, 1.0D)), new Stop(0.13D, Color.color(0.9254901961D, 0.9254901961D, 0.9333333333D, 1.0D)), new Stop(0.38D, Color.color(0.6156862745D, 0.6196078431D, 0.6274509804D, 1.0D)), new Stop(0.45D, Color.BLACK), new Stop(0.78D, Color.color(0.0862745098D, 0.0784313725D, 0.0901960784D, 1.0D)), new Stop(0.92D, Color.color(0.9294117647D, 0.9294117647D, 0.9294117647D, 1.0D)), new Stop(0.95D, Color.color(0.0980392157D, 0.0901960784D, 0.1058823529D, 1.0D)), new Stop(0.98D, Color.BLACK), new Stop(1.0D, Color.color(0.3803921569D, 0.3921568627D, 0.4117647059D, 1.0D)) });
    localPath6.setFill(localLinearGradient6);
    localPath6.setStroke(null);
    Path localPath7 = new Path();
    localPath7.setFillRule(FillRule.EVEN_ODD);
    localPath7.getElements().add(new MoveTo(0.4980392156862745D * d2, 0.0D));
    localPath7.getElements().add(new CubicCurveTo(0.4980392156862745D * d2, 0.0D, 0.4196078431372549D * d2, 0.0D, 0.4196078431372549D * d2, 0.0D));
    localPath7.getElements().add(new CubicCurveTo(0.42745098039215684D * d2, 0.01568627450980392D * d3, 0.4392156862745098D * d2, 0.03529411764705882D * d3, 0.4588235294117647D * d2, 0.03529411764705882D * d3));
    localPath7.getElements().add(new CubicCurveTo(0.4823529411764706D * d2, 0.03529411764705882D * d3, 0.49411764705882355D * d2, 0.01568627450980392D * d3, 0.4980392156862745D * d2, 0.0D));
    localPath7.getElements().add(new ClosePath());
    RadialGradient localRadialGradient2 = new RadialGradient(0.0D, 0.0D, 0.4588235294117647D * d2, 0.0D, 0.03333333333333333D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.WHITE), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) });
    localPath7.setFill(localRadialGradient2);
    localPath7.setStroke(null);
    this.foreground.getChildren().addAll(new Node[] { localRectangle2, localPath1, localPath2, localPath3, localPath4, localPath5, localPath6, localPath7 });
    this.foreground.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/BatterySkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */