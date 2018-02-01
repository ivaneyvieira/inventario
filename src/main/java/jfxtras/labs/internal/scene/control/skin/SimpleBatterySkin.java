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
import javafx.scene.paint.Stop;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.SimpleBatteryBehavior;
import jfxtras.labs.scene.control.gauge.GradientLookup;
import jfxtras.labs.scene.control.gauge.SimpleBattery;
import jfxtras.labs.scene.control.gauge.SimpleBattery.ChargeIndicator;

public class SimpleBatterySkin
  extends SkinBase<SimpleBattery, SimpleBatteryBehavior>
{
  private SimpleBattery control;
  private boolean isDirty;
  private boolean initialized;
  private Group background;
  private Group main;
  private Group foreground;
  private Path plug;
  private Path flashFrame;
  private Path flashMain;
  private Rectangle fluid;
  private GradientLookup lookup;
  private Color currentLevelColor;
  
  public SimpleBatterySkin(SimpleBattery paramSimpleBattery)
  {
    super(paramSimpleBattery, new SimpleBatteryBehavior(paramSimpleBattery));
    this.control = paramSimpleBattery;
    this.initialized = false;
    this.isDirty = false;
    this.background = new Group();
    this.main = new Group();
    this.foreground = new Group();
    this.plug = new Path();
    this.flashFrame = new Path();
    this.flashMain = new Path();
    this.fluid = new Rectangle();
    this.lookup = new GradientLookup(this.control.getLevelColors());
    this.currentLevelColor = Color.RED;
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(128.0D, 128.0D);
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
      drawMain();
      drawForeground();
      getChildren().setAll(new Node[] { this.background, this.main, this.foreground });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final SimpleBattery getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 128.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 128.0D;
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
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    this.background.getChildren().add(localRectangle);
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.0703125D * d2, 0.3203125D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.0703125D * d2, 0.296875D * d3, 0.0703125D * d2, 0.296875D * d3, 0.09375D * d2, 0.296875D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.09375D * d2, 0.296875D * d3, 0.8359375D * d2, 0.296875D * d3, 0.8359375D * d2, 0.296875D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.859375D * d2, 0.296875D * d3, 0.859375D * d2, 0.296875D * d3, 0.859375D * d2, 0.3203125D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.859375D * d2, 0.3203125D * d3, 0.859375D * d2, 0.6796875D * d3, 0.859375D * d2, 0.6796875D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.859375D * d2, 0.703125D * d3, 0.859375D * d2, 0.703125D * d3, 0.8359375D * d2, 0.703125D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.8359375D * d2, 0.703125D * d3, 0.09375D * d2, 0.703125D * d3, 0.09375D * d2, 0.703125D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.0703125D * d2, 0.703125D * d3, 0.0703125D * d2, 0.703125D * d3, 0.0703125D * d2, 0.6796875D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.0703125D * d2, 0.6796875D * d3, 0.0703125D * d2, 0.3203125D * d3, 0.0703125D * d2, 0.3203125D * d3));
    localPath1.getElements().add(new ClosePath());
    localPath1.getElements().add(new MoveTo(0.0546875D * d2, 0.3203125D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.0546875D * d2, 0.3203125D * d3, 0.0546875D * d2, 0.6796875D * d3, 0.0546875D * d2, 0.6796875D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.0546875D * d2, 0.7109375D * d3, 0.0625D * d2, 0.71875D * d3, 0.09375D * d2, 0.71875D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.09375D * d2, 0.71875D * d3, 0.8359375D * d2, 0.71875D * d3, 0.8359375D * d2, 0.71875D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.8671875D * d2, 0.71875D * d3, 0.875D * d2, 0.7109375D * d3, 0.875D * d2, 0.6796875D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.875D * d2, 0.6796875D * d3, 0.875D * d2, 0.59375D * d3, 0.875D * d2, 0.59375D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.875D * d2, 0.59375D * d3, 0.9375D * d2, 0.59375D * d3, 0.9375D * d2, 0.59375D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.9453125D * d2, 0.59375D * d3, 0.953125D * d2, 0.5859375D * d3, 0.953125D * d2, 0.578125D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.953125D * d2, 0.578125D * d3, 0.953125D * d2, 0.4296875D * d3, 0.953125D * d2, 0.4296875D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.953125D * d2, 0.4140625D * d3, 0.9453125D * d2, 0.40625D * d3, 0.9375D * d2, 0.40625D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.9375D * d2, 0.40625D * d3, 0.875D * d2, 0.40625D * d3, 0.875D * d2, 0.40625D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.875D * d2, 0.40625D * d3, 0.875D * d2, 0.3203125D * d3, 0.875D * d2, 0.3203125D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.875D * d2, 0.2890625D * d3, 0.8671875D * d2, 0.28125D * d3, 0.8359375D * d2, 0.28125D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.8359375D * d2, 0.28125D * d3, 0.09375D * d2, 0.28125D * d3, 0.09375D * d2, 0.28125D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.0625D * d2, 0.28125D * d3, 0.0546875D * d2, 0.2890625D * d3, 0.0546875D * d2, 0.3203125D * d3));
    localPath1.getElements().add(new ClosePath());
    localPath1.getStyleClass().add("simple-battery-body");
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.875D * d2, 0.40625D * d3));
    localPath2.getElements().add(new LineTo(0.875D * d2, 0.40625D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.875D * d2, 0.40625D * d3, 0.9375D * d2, 0.40625D * d3, 0.9375D * d2, 0.40625D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.9453125D * d2, 0.40625D * d3, 0.953125D * d2, 0.4140625D * d3, 0.953125D * d2, 0.4296875D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.953125D * d2, 0.4296875D * d3, 0.953125D * d2, 0.578125D * d3, 0.953125D * d2, 0.578125D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.953125D * d2, 0.5859375D * d3, 0.9453125D * d2, 0.59375D * d3, 0.9375D * d2, 0.59375D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.9375D * d2, 0.59375D * d3, 0.875D * d2, 0.59375D * d3, 0.875D * d2, 0.59375D * d3));
    localPath2.getElements().add(new LineTo(0.875D * d2, 0.59375D * d3));
    localPath2.getElements().add(new LineTo(0.875D * d2, 0.40625D * d3));
    localPath2.getElements().add(new ClosePath());
    localPath2.getStyleClass().add("simple-battery-connector");
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.05625D * localPath2.getLayoutBounds().getWidth());
    localInnerShadow.setHeight(0.05625D * localPath2.getLayoutBounds().getHeight());
    localInnerShadow.setOffsetX(0.0D);
    localInnerShadow.setOffsetY(0.0D);
    localInnerShadow.setRadius(0.05625D * localPath2.getLayoutBounds().getWidth());
    localInnerShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.6470588235D));
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow.inputProperty().set(null);
    localPath2.setEffect(localInnerShadow);
    this.background.getChildren().addAll(new Node[] { localPath1, localPath2 });
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
    this.fluid = new Rectangle(0.0703125D * d2, 0.296875D * d3, 0.7890625D * d2, 0.40625D * d3);
    this.fluid.setArcWidth(0.025D * d2);
    this.fluid.setArcHeight(0.025D * d3);
    LinearGradient localLinearGradient = new LinearGradient(0.0D, 0.296875D * d3, 0.0D, 0.703125D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.1647058824D, 0.5450980392D, 0.0D, 1.0D)), new Stop(0.32D, Color.color(0.1647058824D, 0.5450980392D, 0.0D, 1.0D)), new Stop(1.0D, Color.color(0.4666666667D, 0.8588235294D, 0.0D, 1.0D)) });
    this.fluid.setFill(localLinearGradient);
    this.fluid.setStroke(null);
    if (Double.compare(this.control.getChargingLevel(), 0.0D) == 0) {
      this.fluid.setVisible(false);
    }
    this.flashFrame = new Path();
    this.flashFrame.setFillRule(FillRule.EVEN_ODD);
    this.flashFrame.getElements().add(new MoveTo(0.59375D * d2, 0.3671875D * d3));
    this.flashFrame.getElements().add(new LineTo(0.34375D * d2, 0.546875D * d3));
    this.flashFrame.getElements().add(new LineTo(0.4453125D * d2, 0.546875D * d3));
    this.flashFrame.getElements().add(new LineTo(0.3515625D * d2, 0.6484375D * d3));
    this.flashFrame.getElements().add(new LineTo(0.5859375D * d2, 0.5D * d3));
    this.flashFrame.getElements().add(new LineTo(0.46875D * d2, 0.5D * d3));
    this.flashFrame.getElements().add(new LineTo(0.59375D * d2, 0.3671875D * d3));
    this.flashFrame.getElements().add(new ClosePath());
    Color localColor1 = Color.WHITE;
    this.flashFrame.setFill(localColor1);
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
    this.flashMain.getElements().add(new MoveTo(0.5625D * d2, 0.390625D * d3));
    this.flashMain.getElements().add(new LineTo(0.359375D * d2, 0.5390625D * d3));
    this.flashMain.getElements().add(new LineTo(0.453125D * d2, 0.5390625D * d3));
    this.flashMain.getElements().add(new LineTo(0.375D * d2, 0.625D * d3));
    this.flashMain.getElements().add(new LineTo(0.5703125D * d2, 0.5078125D * d3));
    this.flashMain.getElements().add(new LineTo(0.453125D * d2, 0.5078125D * d3));
    this.flashMain.getElements().add(new LineTo(0.5625D * d2, 0.390625D * d3));
    this.flashMain.getElements().add(new ClosePath());
    Color localColor2 = Color.color(0.9960784314D, 0.9215686275D, 0.0D, 1.0D);
    this.flashMain.setFill(localColor2);
    this.flashMain.setStroke(null);
    if (this.control.getChargeIndicator() == ChargeIndicator.FLASH) {
      this.flashMain.setOpacity(1.0D);
    } else {
      this.flashMain.setOpacity(0.0D);
    }
    if (!this.control.isCharging()) {
      this.flashMain.setVisible(false);
    }
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.084375D * this.flashMain.getLayoutBounds().getWidth());
    localInnerShadow.setHeight(0.084375D * this.flashMain.getLayoutBounds().getHeight());
    localInnerShadow.setOffsetX(0.0D);
    localInnerShadow.setOffsetY(0.0D);
    localInnerShadow.setRadius(0.084375D * this.flashMain.getLayoutBounds().getWidth());
    localInnerShadow.setColor(Color.color(0.8509803922D, 0.5294117647D, 0.0D, 1.0D));
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow.inputProperty().set(null);
    this.flashMain.setEffect(localInnerShadow);
    this.plug = new Path();
    this.plug.setFillRule(FillRule.EVEN_ODD);
    this.plug.getElements().add(new MoveTo(0.5390625D * d2, 0.484375D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5390625D * d2, 0.484375D * d3, 0.609375D * d2, 0.484375D * d3, 0.609375D * d2, 0.484375D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.6171875D * d2, 0.484375D * d3, 0.625D * d2, 0.4765625D * d3, 0.625D * d2, 0.46875D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.625D * d2, 0.46875D * d3, 0.625D * d2, 0.4609375D * d3, 0.625D * d2, 0.4609375D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.625D * d2, 0.4609375D * d3, 0.6171875D * d2, 0.453125D * d3, 0.609375D * d2, 0.453125D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.609375D * d2, 0.453125D * d3, 0.5390625D * d2, 0.453125D * d3, 0.5390625D * d2, 0.453125D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5390625D * d2, 0.4296875D * d3, 0.5390625D * d2, 0.40625D * d3, 0.5390625D * d2, 0.40625D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.4453125D * d2, 0.40625D * d3, 0.390625D * d2, 0.4453125D * d3, 0.375D * d2, 0.484375D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.375D * d2, 0.484375D * d3, 0.3046875D * d2, 0.484375D * d3, 0.3046875D * d2, 0.484375D * d3));
    this.plug.getElements().add(new LineTo(0.3046875D * d2, 0.5234375D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.3046875D * d2, 0.5234375D * d3, 0.3828125D * d2, 0.5234375D * d3, 0.3828125D * d2, 0.5234375D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.390625D * d2, 0.5625D * d3, 0.4296875D * d2, 0.59375D * d3, 0.5390625D * d2, 0.59375D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5390625D * d2, 0.59375D * d3, 0.5390625D * d2, 0.578125D * d3, 0.5390625D * d2, 0.5546875D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.5390625D * d2, 0.5546875D * d3, 0.6171875D * d2, 0.5546875D * d3, 0.6171875D * d2, 0.5546875D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.6171875D * d2, 0.5546875D * d3, 0.625D * d2, 0.546875D * d3, 0.625D * d2, 0.546875D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.625D * d2, 0.546875D * d3, 0.625D * d2, 0.53125D * d3, 0.625D * d2, 0.53125D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.625D * d2, 0.53125D * d3, 0.6171875D * d2, 0.5234375D * d3, 0.6171875D * d2, 0.5234375D * d3));
    this.plug.getElements().add(new CubicCurveTo(0.6171875D * d2, 0.5234375D * d3, 0.5390625D * d2, 0.5234375D * d3, 0.5390625D * d2, 0.5234375D * d3));
    this.plug.getElements().add(new LineTo(0.5390625D * d2, 0.484375D * d3));
    this.plug.getElements().add(new ClosePath());
    this.plug.setFill(Color.rgb(51, 51, 51));
    this.plug.setStroke(null);
    if (this.control.getChargeIndicator() == ChargeIndicator.PLUG) {
      this.plug.setOpacity(1.0D);
    } else {
      this.plug.setOpacity(0.0D);
    }
    if (!this.control.isCharging()) {
      this.plug.setVisible(false);
    }
    this.main.getChildren().addAll(new Node[] { this.fluid, this.flashFrame, this.flashMain, this.plug });
  }
  
  private final void updateFluid()
  {
    Platform.runLater(new Runnable()
    {
      public void run()
      {
        if (Double.compare(SimpleBatterySkin.this.control.getChargingLevel(), 0.0D) == 0) {
          SimpleBatterySkin.this.fluid.setVisible(false);
        } else {
          SimpleBatterySkin.this.fluid.setVisible(true);
        }
        SimpleBatterySkin.this.fluid.setWidth(SimpleBatterySkin.this.control.getChargingLevel() * 0.7890625D * SimpleBatterySkin.this.control.getPrefWidth());
        SimpleBatterySkin.this.fluid.setFill(new LinearGradient(0.0D, 0.296875D * SimpleBatterySkin.this.control.getPrefHeight(), 0.0D, 0.703125D * SimpleBatterySkin.this.control.getPrefHeight(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.hsb(SimpleBatterySkin.this.currentLevelColor.getHue(), SimpleBatterySkin.this.currentLevelColor.getSaturation(), 0.5D)), new Stop(0.32D, Color.hsb(SimpleBatterySkin.this.currentLevelColor.getHue(), SimpleBatterySkin.this.currentLevelColor.getSaturation(), 0.5D)), new Stop(1.0D, SimpleBatterySkin.this.currentLevelColor) }));
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
    Rectangle localRectangle2 = new Rectangle(0.0703125D * d2, 0.296875D * d3, 0.7890625D * d2, 0.40625D * d3);
    localRectangle2.setArcWidth(0.025D * d2);
    localRectangle2.setArcHeight(0.025D * d3);
    localRectangle2.getStyleClass().add("simple-battery-reflection");
    this.foreground.getChildren().addAll(new Node[] { localRectangle2 });
    this.foreground.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/SimpleBatterySkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */