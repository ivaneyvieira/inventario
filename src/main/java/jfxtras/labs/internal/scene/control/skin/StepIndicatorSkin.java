package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jfxtras.labs.internal.scene.control.behavior.StepIndicatorBehavior;
import jfxtras.labs.scene.control.gauge.StepIndicator;
import jfxtras.labs.util.Util;

public class StepIndicatorSkin
  extends SkinBase<StepIndicator, StepIndicatorBehavior>
{
  private StepIndicator control;
  private boolean isDirty;
  private boolean initialized;
  private int noOfCircles;
  private Group circles;
  private Group selectedCircles;
  
  public StepIndicatorSkin(StepIndicator paramStepIndicator)
  {
    super(paramStepIndicator, new StepIndicatorBehavior(paramStepIndicator));
    this.control = paramStepIndicator;
    this.initialized = false;
    this.isDirty = false;
    this.noOfCircles = this.control.getNoOfSteps();
    this.circles = new Group();
    this.selectedCircles = new Group();
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(this.noOfCircles * 60 + (this.noOfCircles - 1) * 20, 60.0D);
    }
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    registerChangeListener(this.control.colorProperty(), "COLOR");
    registerChangeListener(this.control.noOfStepsProperty(), "NO_OF_CIRCLES");
    registerChangeListener(this.control.currentStepProperty(), "SELECTION");
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("NO_OF_CIRCLES".equals(paramString))
    {
      this.noOfCircles = this.control.getNoOfSteps();
      repaint();
    }
    else if ("SELECTION".equals(paramString))
    {
      drawSelectedCircles();
    }
    else if ("COLOR".equals(paramString))
    {
      drawSelectedCircles();
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
      drawCircles();
      drawSelectedCircles();
      getChildren().setAll(new Node[] { this.circles, this.selectedCircles });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final StepIndicator getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 200.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 60.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  private void addMouseEventListener(Shape paramShape, final int paramInt)
  {
    paramShape.setOnMouseEntered(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        StepIndicatorSkin.this.control.setSelectedStep(paramInt);
      }
    });
    paramShape.setOnMouseExited(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        StepIndicatorSkin.this.control.setSelectedStep(-1);
      }
    });
  }
  
  private final void drawCircles()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.circles.getChildren().clear();
    this.circles.setStyle("-fx-step-indicator-selected-inner-frame-fill: " + Util.createCssColor(this.control.getColor().darker()) + "-fx-step-indicator-selected-inner-background-fill: " + Util.createCssColor(this.control.getColor()) + "-fx-step-indicator-selected-text-fill: " + Util.createCssColor(this.control.getColor().darker().darker()) + "-fx-step-indicator-inner-frame-fill: rgb(158, 158, 158);" + "-fx-step-indicator-inner-background-fill: rgb(244, 244, 244);" + "-fx-step-indicator-stroke: transparent");
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.1933333333D * d3);
    localInnerShadow1.setHeight(0.1933333333D * d3);
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setOffsetY(0.04D * d1);
    localInnerShadow1.setRadius(0.1933333333D * d3);
    localInnerShadow1.setColor(Color.color(0.6D, 0.6D, 0.6D, 0.65D));
    localInnerShadow1.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow1.inputProperty().set(null);
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setWidth(0.048D * d3);
    localDropShadow.setHeight(0.048D * d3);
    localDropShadow.setOffsetX(0.0D);
    localDropShadow.setOffsetY(0.02D * d1);
    localDropShadow.setRadius(0.048D * d3);
    localDropShadow.setColor(Color.color(0.4D, 0.4D, 0.4D, 0.65D));
    localDropShadow.setBlurType(BlurType.GAUSSIAN);
    localDropShadow.inputProperty().set(null);
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setWidth(0.092D * d3);
    localInnerShadow2.setHeight(0.092D * d3);
    localInnerShadow2.setOffsetX(0.0D);
    localInnerShadow2.setOffsetY(0.0D);
    localInnerShadow2.setRadius(0.092D * d3);
    localInnerShadow2.setColor(Color.WHITE);
    localInnerShadow2.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow2.inputProperty().set(null);
    double d4 = 1.3333333333D * d2;
    Object localObject1 = new Circle(0.5D * d2, 0.5D * d3, 0.5D * d2);
    Object localObject2 = new Circle(0.5D * d2, 0.5D * d3, 0.48333333333333334D * d2);
    Object localObject5;
    Object localObject6;
    for (int i = 1; i < this.control.getNoOfSteps(); i++)
    {
      localObject4 = new Rectangle(0.5D * d2 + (i - 1) * d4, 0.3166666667D * d3, 1.3333333333D * d2, 0.3666666667D * d3);
      localObject1 = Shape.union((Shape)localObject1, (Shape)localObject4);
      Circle localCircle = new Circle(0.5D * d2 + i * d4, 0.5D * d3, 0.5D * d2);
      localObject1 = Shape.union((Shape)localObject1, localCircle);
      localObject5 = new Rectangle(0.5D * d2 + (i - 1) * d4, 0.3333333333D * d3, 1.3333333333D * d2, 0.3333333333D * d3);
      localObject2 = Shape.union((Shape)localObject2, (Shape)localObject5);
      localObject6 = new Circle(0.5D * d2 + i * d4, 0.5D * d3, 0.48333333333333334D * d2);
      localObject2 = Shape.union((Shape)localObject2, (Shape)localObject6);
    }
    ((Shape)localObject1).getStyleClass().add("step-indicator-outer-frame");
    ((Shape)localObject2).getStyleClass().add("step-indicator-outer-background");
    ((Shape)localObject1).setFill(Color.rgb(201, 201, 201));
    ((Shape)localObject2).setFill(Color.WHITE);
    ((Shape)localObject2).setEffect(localInnerShadow1);
    Object localObject3 = new Circle(0.5D * d2, 0.5D * d3, 0.4D * d2);
    Object localObject4 = new Circle(0.5D * d2, 0.5D * d3, 0.38333333333333336D * d2);
    for (int j = 1; j < this.control.getNoOfSteps(); j++)
    {
      if (j != 0)
      {
        localObject5 = new Rectangle(0.5D * d2 + (j - 1) * d4, 0.4333333333D * d3, 1.3333333333D * d2, 0.1333333333D * d3);
        localObject3 = Shape.union((Shape)localObject3, (Shape)localObject5);
      }
      localObject5 = new Circle(0.5D * d2 + j * d4, 0.5D * d3, 0.4D * d2);
      localObject3 = Shape.union((Shape)localObject3, (Shape)localObject5);
      if (j != 0)
      {
        localObject6 = new Rectangle(0.5D * d2 + (j - 1) * d4, 0.45D * d3, 1.3333333333D * d2, 0.1D * d3);
        localObject4 = Shape.union((Shape)localObject4, (Shape)localObject6);
      }
      localObject6 = new Circle(0.5D * d2 + j * d4, 0.5D * d3, 0.38333333333333336D * d2);
      localObject4 = Shape.union((Shape)localObject4, (Shape)localObject6);
    }
    ((Shape)localObject3).getStyleClass().add("step-indicator-inner-frame");
    ((Shape)localObject4).getStyleClass().add("step-indicator-inner-background");
    ((Shape)localObject3).setFill(Color.rgb(158, 158, 158));
    ((Shape)localObject4).setFill(Color.rgb(244, 244, 244));
    ((Shape)localObject3).setEffect(localDropShadow);
    ((Shape)localObject4).setEffect(localInnerShadow2);
    this.circles.getChildren().addAll(new Node[] { localObject1, localObject2, localObject3, localObject4 });
    this.circles.setCache(true);
  }
  
  private final void drawSelectedCircles()
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    this.selectedCircles.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, this.control.getNoOfSteps() * d2 + (this.control.getNoOfSteps() - 1) * 0.3333333333D * d3, d3);
    localRectangle1.setOpacity(0.0D);
    this.selectedCircles.getChildren().add(localRectangle1);
    this.selectedCircles.setStyle("-fx-step-indicator-selected-inner-frame-fill: " + Util.createCssColor(this.control.getColor().darker()) + "-fx-step-indicator-selected-inner-background-fill: " + Util.createCssColor(this.control.getColor()) + "-fx-step-indicator-selected-text-fill: " + Util.createCssColor(this.control.getColor().darker().darker()) + "-fx-step-indicator-inner-frame-fill: rgb(158, 158, 158);" + "-fx-step-indicator-inner-background-fill: rgb(244, 244, 244);" + "-fx-step-indicator-stroke: transparent");
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.092D * d3);
    localInnerShadow.setHeight(0.092D * d3);
    localInnerShadow.setOffsetX(0.0D);
    localInnerShadow.setOffsetY(0.0D);
    localInnerShadow.setRadius(0.092D * d3);
    localInnerShadow.setColor(Color.WHITE);
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow.inputProperty().set(null);
    double d4 = 1.3333333333D * d2;
    Object localObject1 = new Circle(0.5D * d2, 0.5D * d3, 0.4D * d2);
    Object localObject2 = new Circle(0.5D * d2, 0.5D * d3, 0.38333333333333336D * d2);
    Object localObject3;
    for (int i = 1; i < this.control.getCurrentStep(); i++)
    {
      Rectangle localRectangle2 = new Rectangle(0.5D * d2 + (i - 1) * d4, 0.4333333333D * d3, 1.3333333333D * d2, 0.1333333333D * d3);
      localObject1 = Shape.union((Shape)localObject1, localRectangle2);
      localObject3 = new Circle(0.5D * d2 + i * d4, 0.5D * d3, 0.4D * d2);
      localObject1 = Shape.union((Shape)localObject1, (Shape)localObject3);
      Rectangle localRectangle3 = new Rectangle(0.5D * d2 + (i - 1) * d4, 0.45D * d3, 1.3333333333D * d2, 0.1D * d3);
      localObject2 = Shape.union((Shape)localObject2, localRectangle3);
      Circle localCircle = new Circle(0.5D * d2 + i * d4, 0.5D * d3, 0.38333333333333336D * d2);
      localObject2 = Shape.union((Shape)localObject2, localCircle);
    }
    ((Shape)localObject1).getStyleClass().add("step-indicator-inner-frame");
    ((Shape)localObject2).getStyleClass().add("step-indicator-inner-background");
    ((Shape)localObject1).setFill(this.control.getColor().darker());
    ((Shape)localObject2).setFill(this.control.getColor());
    ((Shape)localObject2).setEffect(localInnerShadow);
    if (this.control.getCurrentStep() == 0)
    {
      ((Shape)localObject1).setVisible(false);
      ((Shape)localObject2).setVisible(false);
    }
    this.selectedCircles.getChildren().addAll(new Node[] { localObject1, localObject2 });
    Font localFont = Font.font("Arial", FontWeight.BOLD, 0.4D * d3);
    for (int j = 0; j < this.control.getNoOfSteps(); j++)
    {
      localObject3 = new Text(Integer.toString(j + 1));
      ((Text)localObject3).setTextOrigin(VPos.CENTER);
      ((Text)localObject3).setTextAlignment(TextAlignment.CENTER);
      ((Text)localObject3).setFontSmoothingType(FontSmoothingType.LCD);
      ((Text)localObject3).setFont(localFont);
      if (j < this.control.getCurrentStep()) {
        ((Text)localObject3).getStyleClass().add("step-indicator-selected-text");
      } else {
        ((Text)localObject3).getStyleClass().add("step-indicator-text");
      }
      ((Text)localObject3).setTranslateX((d2 - ((Text)localObject3).getLayoutBounds().getWidth()) / 2.0D + j * d4);
      ((Text)localObject3).setTranslateY(0.5D * d3);
      this.selectedCircles.getChildren().add(localObject3);
    }
    for (j = 0; j < this.control.getCurrentStep(); j++)
    {
      localObject3 = new Circle(0.5D * d2 + j * d4, 0.5D * d3, 0.4D * d2);
      ((Circle)localObject3).setFill(Color.TRANSPARENT);
      ((Circle)localObject3).setStroke(Color.TRANSPARENT);
      this.selectedCircles.getChildren().add(localObject3);
      addMouseEventListener((Shape)localObject3, j + 1);
    }
    this.selectedCircles.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/StepIndicatorSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */