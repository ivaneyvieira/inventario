package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.RaterBehavior;
import jfxtras.labs.scene.control.gauge.Rater;
import jfxtras.labs.util.Util;

public class RaterSkin
  extends SkinBase<Rater, RaterBehavior>
{
  private Rater control;
  private boolean isDirty;
  private boolean initialized;
  private int noOfStars;
  private int rating;
  private HBox starContainer;
  private List<Group> stars;
  private int currentIndex;
  private int currentMouseOverIndex;
  
  public RaterSkin(Rater paramRater)
  {
    super(paramRater, new RaterBehavior(paramRater));
    this.control = paramRater;
    this.initialized = false;
    this.isDirty = false;
    this.noOfStars = this.control.getNoOfStars();
    this.rating = this.control.getRating();
    this.starContainer = new HBox();
    this.stars = new ArrayList(this.noOfStars);
    this.currentIndex = 0;
    this.currentMouseOverIndex = 0;
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(this.noOfStars * 32, 32.0D);
    }
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    registerChangeListener(this.control.darkColorProperty(), "DARK_COLOR");
    registerChangeListener(this.control.brightColorProperty(), "BRIGHT_COLOR");
    registerChangeListener(this.control.noOfStarsProperty(), "NO_OF_STARS");
    registerChangeListener(this.control.ratingProperty(), "RATING");
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("NO_OF_STARS".equals(paramString))
    {
      this.noOfStars = this.control.getNoOfStars();
      drawStars();
      repaint();
    }
    else if ("RATING".equals(paramString))
    {
      this.rating = this.control.getRating();
      updateStars();
    }
    else if ("BRIGHT_COLOR".equals(paramString))
    {
      updateStars();
    }
    else if ("DARK_COLOR".equals(paramString))
    {
      updateStars();
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
      drawStars();
      getChildren().setAll(new Node[] { this.starContainer });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final Rater getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 160.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 32.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  public int getCurrentIndex()
  {
    return this.currentIndex;
  }
  
  private void addMouseEventListener(Group paramGroup, final int paramInt)
  {
    paramGroup.setOnMouseEntered(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        RaterSkin.this.currentMouseOverIndex = paramInt;
        RaterSkin.this.highlightStars();
      }
    });
    paramGroup.setOnMousePressed(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        RaterSkin.this.currentIndex = paramInt;
      }
    });
  }
  
  private void drawStars()
  {
    this.starContainer.getChildren().clear();
    this.stars.clear();
    for (int i = 0; i < this.rating; i++) {
      this.stars.add(createStar(true));
    }
    for (i = 0; i < this.noOfStars - this.rating; i++) {
      this.stars.add(createStar(false));
    }
    i = 0;
    Iterator localIterator = this.stars.iterator();
    while (localIterator.hasNext())
    {
      Group localGroup = (Group)localIterator.next();
      this.starContainer.getChildren().add(localGroup);
      addMouseEventListener(localGroup, i + 1);
      i++;
    }
  }
  
  private void updateStars()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    for (int i = 0; i < this.stars.size(); i++)
    {
      localStringBuilder.setLength(0);
      if (i < this.rating)
      {
        localStringBuilder.append("-fx-rater-bright-color: " + Util.createCssColor(this.control.getBrightColor()));
        localStringBuilder.append("-fx-rater-dark-color: " + Util.createCssColor(this.control.getDarkColor()));
        if (i <= this.currentMouseOverIndex - 1) {
          localStringBuilder.append("-fx-rater-stroke: " + Util.createCssColor(this.control.getBrightColor()));
        } else {
          localStringBuilder.append("-fx-rater-stroke: transparent;");
        }
      }
      else
      {
        localStringBuilder.append("-fx-rater-bright-color: white;");
        localStringBuilder.append("-fx-rater-dark-color: rgb(204, 204, 204);");
        if (i <= this.currentMouseOverIndex - 1) {
          localStringBuilder.append("-fx-rater-stroke: " + Util.createCssColor(this.control.getBrightColor()));
        } else {
          localStringBuilder.append("-fx-rater-stroke: transparent;");
        }
      }
      ((Group)this.stars.get(i)).setStyle(localStringBuilder.toString());
    }
  }
  
  public void highlightStars()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    for (int i = 0; i < this.stars.size(); i++)
    {
      localStringBuilder.setLength(0);
      if (i < this.rating)
      {
        localStringBuilder.append("-fx-rater-bright-color: " + Util.createCssColor(this.control.getBrightColor()));
        localStringBuilder.append("-fx-rater-dark-color: " + Util.createCssColor(this.control.getDarkColor()));
      }
      else
      {
        localStringBuilder.append("-fx-rater-bright-color: white;");
        localStringBuilder.append("-fx-rater-dark-color: rgb(204, 204, 204);");
      }
      if (i < this.currentMouseOverIndex) {
        localStringBuilder.append("-fx-rater-stroke: " + Util.createCssColor(this.control.getBrightColor()));
      } else {
        localStringBuilder.append("-fx-rater-stroke: transparent;");
      }
      ((Group)this.stars.get(i)).setStyle(localStringBuilder.toString());
    }
  }
  
  public void deHighlightStars()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    this.currentMouseOverIndex = 0;
    for (int i = 0; i < this.stars.size(); i++)
    {
      localStringBuilder.setLength(0);
      if (i < this.rating)
      {
        localStringBuilder.append("-fx-rater-bright-color: " + Util.createCssColor(this.control.getBrightColor()));
        localStringBuilder.append("-fx-rater-dark-color: " + Util.createCssColor(this.control.getDarkColor()));
      }
      else
      {
        localStringBuilder.append("-fx-rater-bright-color: white;");
        localStringBuilder.append("-fx-rater-dark-color: rgb(204, 204, 204);");
      }
      localStringBuilder.append("-fx-rater-stroke: transparent;");
      ((Group)this.stars.get(i)).setStyle(localStringBuilder.toString());
    }
  }
  
  private final Group createStar(boolean paramBoolean)
  {
    double d1 = this.control.getPrefWidth() < this.control.getPrefHeight() ? this.control.getPrefWidth() : this.control.getPrefHeight();
    double d2 = d1;
    double d3 = d1;
    Group localGroup = new Group();
    if (paramBoolean) {
      localGroup.setStyle("-fx-rater-bright-color: " + Util.createCssColor(this.control.getBrightColor()) + "-fx-rater-dark-color: " + Util.createCssColor(this.control.getDarkColor()) + "-fx-rater-stroke: transparent");
    } else {
      localGroup.setStyle("-fx-rater-bright-color: white;-fx-rater-dark-color: rgb(204, 204, 204);-fx-rater-stroke: transparent");
    }
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localGroup.getChildren().add(localRectangle);
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.5D * d2, 0.04D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.505D * d2, 0.04D * d3, 0.64D * d2, 0.35D * d3, 0.64D * d2, 0.35D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.64D * d2, 0.35D * d3, 0.975D * d2, 0.385D * d3, 0.975D * d2, 0.385D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.975D * d2, 0.39D * d3, 0.725D * d2, 0.615D * d3, 0.725D * d2, 0.615D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.725D * d2, 0.615D * d3, 0.795D * d2, 0.94D * d3, 0.795D * d2, 0.945D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.79D * d2, 0.945D * d3, 0.5D * d2, 0.78D * d3, 0.5D * d2, 0.78D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.5D * d2, 0.78D * d3, 0.21D * d2, 0.945D * d3, 0.205D * d2, 0.945D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.205D * d2, 0.94D * d3, 0.275D * d2, 0.615D * d3, 0.275D * d2, 0.615D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.275D * d2, 0.615D * d3, 0.025D * d2, 0.39D * d3, 0.025D * d2, 0.385D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.025D * d2, 0.385D * d3, 0.36D * d2, 0.35D * d3, 0.36D * d2, 0.35D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.36D * d2, 0.35D * d3, 0.495D * d2, 0.04D * d3, 0.5D * d2, 0.04D * d3));
    localPath1.getElements().add(new ClosePath());
    localPath1.setSmooth(true);
    localPath1.getStyleClass().add("star-fill");
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.2D * localPath1.getLayoutBounds().getWidth());
    localInnerShadow1.setHeight(0.2D * localPath1.getLayoutBounds().getHeight());
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setOffsetY(0.0D);
    localInnerShadow1.setRadius(0.2D * localPath1.getLayoutBounds().getWidth());
    localInnerShadow1.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.65D));
    localInnerShadow1.setBlurType(BlurType.GAUSSIAN);
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setWidth(0.1D * localPath1.getLayoutBounds().getWidth());
    localInnerShadow2.setHeight(0.1D * localPath1.getLayoutBounds().getHeight());
    localInnerShadow2.setOffsetX(0.0D);
    localInnerShadow2.setOffsetY(0.0D);
    localInnerShadow2.setRadius(0.1D * localPath1.getLayoutBounds().getWidth());
    localInnerShadow2.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.65D));
    localInnerShadow2.setBlurType(BlurType.GAUSSIAN);
    localInnerShadow2.inputProperty().set(localInnerShadow1);
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setWidth(0.1D * localPath1.getLayoutBounds().getWidth());
    localDropShadow.setHeight(0.1D * localPath1.getLayoutBounds().getHeight());
    localDropShadow.setOffsetX(0.0D);
    localDropShadow.setOffsetY(0.0D);
    localDropShadow.setRadius(0.1D * localPath1.getLayoutBounds().getWidth());
    localDropShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.65D));
    localDropShadow.setBlurType(BlurType.GAUSSIAN);
    localDropShadow.inputProperty().set(localInnerShadow2);
    localPath1.setEffect(localDropShadow);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.5D * d2, 0.09D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.49D * d2, 0.09D * d3, 0.365D * d2, 0.355D * d3, 0.365D * d2, 0.355D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.365D * d2, 0.355D * d3, 0.05D * d2, 0.39D * d3, 0.045D * d2, 0.395D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.045D * d2, 0.395D * d3, 0.055D * d2, 0.4D * d3, 0.065D * d2, 0.41D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.135D * d2, 0.4D * d3, 0.375D * d2, 0.375D * d3, 0.375D * d2, 0.375D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.375D * d2, 0.375D * d3, 0.495D * d2, 0.155D * d3, 0.5D * d2, 0.155D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.51D * d2, 0.155D * d3, 0.625D * d2, 0.375D * d3, 0.625D * d2, 0.375D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.625D * d2, 0.375D * d3, 0.865D * d2, 0.4D * d3, 0.935D * d2, 0.41D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.945D * d2, 0.4D * d3, 0.955D * d2, 0.395D * d3, 0.955D * d2, 0.395D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.95D * d2, 0.39D * d3, 0.635D * d2, 0.355D * d3, 0.635D * d2, 0.355D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.635D * d2, 0.355D * d3, 0.51D * d2, 0.09D * d3, 0.5D * d2, 0.09D * d3));
    localPath2.getElements().add(new ClosePath());
    localPath2.setSmooth(true);
    localPath2.getStyleClass().add("highlights-inner-fill");
    localPath2.setStroke(null);
    Path localPath3 = new Path();
    localPath3.setFillRule(FillRule.EVEN_ODD);
    localPath3.getElements().add(new MoveTo(0.5D * d2, 0.065D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.495D * d2, 0.065D * d3, 0.365D * d2, 0.355D * d3, 0.365D * d2, 0.355D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.365D * d2, 0.355D * d3, 0.05D * d2, 0.39D * d3, 0.045D * d2, 0.395D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.045D * d2, 0.395D * d3, 0.21D * d2, 0.54D * d3, 0.265D * d2, 0.595D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.415D * d2, 0.485D * d3, 0.655D * d2, 0.415D * d3, 0.93D * d2, 0.415D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.93D * d2, 0.415D * d3, 0.93D * d2, 0.415D * d3, 0.93D * d2, 0.415D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.945D * d2, 0.4D * d3, 0.955D * d2, 0.395D * d3, 0.955D * d2, 0.395D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.95D * d2, 0.39D * d3, 0.635D * d2, 0.355D * d3, 0.635D * d2, 0.355D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.635D * d2, 0.355D * d3, 0.505D * d2, 0.065D * d3, 0.5D * d2, 0.065D * d3));
    localPath3.getElements().add(new ClosePath());
    localPath3.setSmooth(true);
    localPath3.getStyleClass().add("highlights-top-fill");
    localPath3.setStroke(null);
    localGroup.getChildren().addAll(new Node[] { localPath1, localPath2, localPath3 });
    localGroup.setCache(true);
    return localGroup;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/RaterSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */