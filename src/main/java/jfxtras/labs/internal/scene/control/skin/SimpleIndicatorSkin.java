package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.SimpleIndicatorBehavior;
import jfxtras.labs.scene.control.gauge.SimpleIndicator;
import jfxtras.labs.util.Util;

public class SimpleIndicatorSkin
  extends SkinBase<SimpleIndicator, SimpleIndicatorBehavior>
{
  private SimpleIndicator control;
  private boolean isDirty;
  private boolean initialized;
  private Group indicator;
  private Circle main;
  private DropShadow mainGlow;
  
  public SimpleIndicatorSkin(SimpleIndicator paramSimpleIndicator)
  {
    super(paramSimpleIndicator, new SimpleIndicatorBehavior(paramSimpleIndicator));
    this.control = paramSimpleIndicator;
    this.initialized = false;
    this.isDirty = false;
    this.indicator = new Group();
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(48.0D, 48.0D);
    }
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    registerChangeListener(this.control.innerColorProperty(), "INNER_COLOR");
    registerChangeListener(this.control.outerColorProperty(), "OUTER_COLOR");
    registerChangeListener(this.control.glowVisibleProperty(), "GLOW_VISIBILITY");
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("INNER_COLOR".equals(paramString)) {
      updateIndicator();
    } else if ("OUTER_COLOR".equals(paramString)) {
      updateIndicator();
    } else if ("GLOW_VISIBILITY".equals(paramString)) {
      repaint();
    } else if ("PREF_WIDTH".equals(paramString)) {
      repaint();
    } else if ("PREF_HEIGHT".equals(paramString)) {
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
      drawIndicator();
      getChildren().setAll(new Node[] { this.indicator });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final SimpleIndicator getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 250.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 250.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  private void updateIndicator()
  {
    this.main.setStyle("-fx-indicator-inner-color: " + Util.createCssColor(this.control.getInnerColor()) + "-fx-indicator-outer-color: " + Util.createCssColor(this.control.getOuterColor()));
    this.mainGlow.setColor(this.control.getInnerColor());
  }
  
  private final void drawIndicator()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.indicator.setStyle("-fx-indicator-inner-color: " + Util.createCssColor(this.control.getInnerColor()) + "-fx-indicator-outer-color: " + Util.createCssColor(this.control.getOuterColor()));
    this.indicator.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    this.indicator.getChildren().add(localRectangle);
    Circle localCircle1 = new Circle(0.5D * d2, 0.5D * d3, 0.496D * d2);
    localCircle1.getStyleClass().add("indicator-outer-frame-fill");
    localCircle1.setStroke(null);
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.05D * localCircle1.getLayoutBounds().getWidth());
    localInnerShadow1.setHeight(0.05D * localCircle1.getLayoutBounds().getHeight());
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setOffsetY(0.0D);
    localInnerShadow1.setRadius(0.05D * localCircle1.getLayoutBounds().getWidth());
    localInnerShadow1.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.9D));
    localInnerShadow1.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow1.inputProperty().set(null);
    localCircle1.setEffect(localInnerShadow1);
    Circle localCircle2 = new Circle(0.5D * d2, 0.5D * d3, 0.4D * d2);
    localCircle2.getStyleClass().add("indicator-inner-frame-fill");
    this.main = new Circle(0.5D * d2, 0.5D * d3, 0.38D * d2);
    this.main.setStyle("-fx-indicator-inner-color: " + Util.createCssColor(this.control.getInnerColor()) + "-fx-indicator-outer-color: " + Util.createCssColor(this.control.getOuterColor()));
    this.main.getStyleClass().add("indicator-main-fill");
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setWidth(0.288D * this.main.getLayoutBounds().getWidth());
    localInnerShadow2.setHeight(0.288D * this.main.getLayoutBounds().getHeight());
    localInnerShadow2.setOffsetX(0.0D);
    localInnerShadow2.setOffsetY(0.0D);
    localInnerShadow2.setRadius(0.288D * this.main.getLayoutBounds().getWidth());
    localInnerShadow2.setColor(Color.BLACK);
    localInnerShadow2.setBlurType(BlurType.GAUSSIAN);
    this.mainGlow = new DropShadow();
    this.mainGlow.setWidth(0.288D * this.main.getLayoutBounds().getWidth());
    this.mainGlow.setHeight(0.288D * this.main.getLayoutBounds().getHeight());
    this.mainGlow.setOffsetX(0.0D);
    this.mainGlow.setOffsetY(0.0D);
    this.mainGlow.setRadius(0.288D * this.main.getLayoutBounds().getWidth());
    this.mainGlow.setColor(this.control.getInnerColor());
    this.mainGlow.setBlurType(BlurType.GAUSSIAN);
    this.mainGlow.inputProperty().set(localInnerShadow2);
    if (this.control.isGlowVisible()) {
      this.main.setEffect(this.mainGlow);
    } else {
      this.main.setEffect(localInnerShadow2);
    }
    Ellipse localEllipse = new Ellipse(0.504D * d2, 0.294D * d3, 0.26D * d2, 0.15D * d3);
    localEllipse.getStyleClass().add("indicator-highlight-fill");
    this.indicator.getChildren().addAll(new Node[] { localCircle1, localCircle2, this.main, localEllipse });
    this.indicator.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/SimpleIndicatorSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */