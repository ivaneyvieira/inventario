package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.DotMatrixSegmentBehavior;
import jfxtras.labs.scene.control.gauge.DotMatrixSegment;
import jfxtras.labs.scene.control.gauge.DotMatrixSegment.Dot;
import jfxtras.labs.util.Util;

public class DotMatrixSegmentSkin
  extends SkinBase<DotMatrixSegment, DotMatrixSegmentBehavior>
{
  private DotMatrixSegment control;
  private boolean isDirty;
  private boolean initialized;
  private Group dots;
  private Map<Dot, Shape> dotMap;
  
  public DotMatrixSegmentSkin(DotMatrixSegment paramDotMatrixSegment)
  {
    super(paramDotMatrixSegment, new DotMatrixSegmentBehavior(paramDotMatrixSegment));
    this.control = paramDotMatrixSegment;
    this.initialized = false;
    this.isDirty = false;
    this.dots = new Group();
    this.dotMap = new HashMap(17);
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(40.0D, 56.0D);
    }
    createDots();
    updateCharacter();
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
    registerChangeListener(this.control.characterProperty(), "CHARACTER");
    registerChangeListener(this.control.colorProperty(), "COLOR");
    registerChangeListener(this.control.plainColorProperty(), "PLAIN_COLOR");
    registerChangeListener(this.control.customDotMappingProperty(), "CUSTOM_MAPPING");
    registerChangeListener(this.control.dotOnProperty(), "DOT_ON");
    this.initialized = true;
    repaint();
  }
  
  protected void handleControlPropertyChanged(String paramString)
  {
    super.handleControlPropertyChanged(paramString);
    if ("CHARACTER".equals(paramString)) {
      updateCharacter();
    } else if ("COLOR".equals(paramString)) {
      updateCharacter();
    } else if ("PLAIN_COLOR".equals(paramString)) {
      updateCharacter();
    } else if ("CUSTOM_MAPPING".equals(paramString)) {
      updateCharacter();
    } else if ("DOT_ON".equals(paramString)) {
      updateCharacter();
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
      updateCharacter();
      getChildren().setAll(new Node[] { this.dots });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final DotMatrixSegment getSkinnable()
  {
    return this.control;
  }
  
  public final void dispose()
  {
    this.control = null;
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    double d = 40.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getLeft() - getInsets().getRight());
    }
    return super.computePrefWidth(d);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    double d = 56.0D;
    if (paramDouble != -1.0D) {
      d = Math.max(0.0D, paramDouble - getInsets().getTop() - getInsets().getBottom());
    }
    return super.computePrefWidth(d);
  }
  
  public void updateCharacter()
  {
    this.dots.setStyle("-fx-segment-color-on: " + Util.createCssColor(this.control.getColor()) + "-fx-segment-color-off: " + Util.createCssColor(Color.color(this.control.getColor().getRed(), this.control.getColor().getGreen(), this.control.getColor().getBlue(), 0.075D)));
    int i = this.control.getCharacter().isEmpty() ? '\024' : this.control.getCharacter().toUpperCase().charAt(0);
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setRadius(0.05D * this.control.getPrefWidth());
    localInnerShadow.setColor(Color.hsb(this.control.getColor().getHue(), this.control.getColor().getSaturation(), 0.2D));
    String str = this.control.isPlainColor() ? "dot-matrix-segment-plain-on" : "dot-matrix-segment-on";
    Iterator localIterator;
    Dot localDot;
    if (this.control.getCustomDotMapping().isEmpty())
    {
      localIterator = this.dotMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        localDot = (Dot)localIterator.next();
        if (this.control.getDotMapping().containsKey(Integer.valueOf(i)))
        {
          if (((List)this.control.getDotMapping().get(Integer.valueOf(i))).contains(localDot))
          {
            ((Shape)this.dotMap.get(localDot)).getStyleClass().clear();
            ((Shape)this.dotMap.get(localDot)).getStyleClass().add(str);
            ((Shape)this.dotMap.get(localDot)).setEffect(localInnerShadow);
          }
          else
          {
            ((Shape)this.dotMap.get(localDot)).getStyleClass().clear();
            ((Shape)this.dotMap.get(localDot)).getStyleClass().add("dot-matrix-segment-off");
            ((Shape)this.dotMap.get(localDot)).setEffect(null);
          }
        }
        else
        {
          ((Shape)this.dotMap.get(localDot)).getStyleClass().clear();
          ((Shape)this.dotMap.get(localDot)).getStyleClass().add("dot-matrix-segment-off");
          ((Shape)this.dotMap.get(localDot)).setEffect(null);
        }
      }
    }
    else
    {
      localIterator = this.dotMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        localDot = (Dot)localIterator.next();
        if (this.control.getCustomDotMapping().containsKey(Integer.valueOf(i)))
        {
          if (((List)this.control.getCustomDotMapping().get(Integer.valueOf(i))).contains(localDot))
          {
            ((Shape)this.dotMap.get(localDot)).getStyleClass().clear();
            ((Shape)this.dotMap.get(localDot)).getStyleClass().add(str);
            ((Shape)this.dotMap.get(localDot)).setEffect(localInnerShadow);
          }
          else
          {
            ((Shape)this.dotMap.get(localDot)).getStyleClass().clear();
            ((Shape)this.dotMap.get(localDot)).getStyleClass().add("dot-matrix-segment-off");
            ((Shape)this.dotMap.get(localDot)).setEffect(null);
          }
        }
        else
        {
          ((Shape)this.dotMap.get(localDot)).getStyleClass().clear();
          ((Shape)this.dotMap.get(localDot)).getStyleClass().add("dot-matrix-segment-off");
          ((Shape)this.dotMap.get(localDot)).setEffect(null);
        }
      }
    }
  }
  
  public final void createDots()
  {
    double d1 = this.control.getPrefWidth();
    double d2 = this.control.getPrefHeight();
    this.dots.setStyle("-fx-segment-color-on: " + Util.createCssColor(this.control.getColor()) + "-fx-segment-color-off: " + Util.createCssColor(Color.color(this.control.getColor().getRed(), this.control.getColor().getGreen(), this.control.getColor().getBlue(), 0.075D)));
    this.dots.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle.setOpacity(0.0D);
    this.dots.getChildren().add(localRectangle);
    Circle localCircle1 = new Circle(0.8902439024390244D * d1, 0.9210526315789473D * d2, 0.08536585365853659D * d1);
    localCircle1.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D57, localCircle1);
    Circle localCircle2 = new Circle(0.6951219512195121D * d1, 0.9210526315789473D * d2, 0.08536585365853659D * d1);
    localCircle2.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D47, localCircle2);
    Circle localCircle3 = new Circle(0.5D * d1, 0.9210526315789473D * d2, 0.08536585365853659D * d1);
    localCircle3.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D37, localCircle3);
    Circle localCircle4 = new Circle(0.3048780487804878D * d1, 0.9210526315789473D * d2, 0.08536585365853659D * d1);
    localCircle4.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D27, localCircle4);
    Circle localCircle5 = new Circle(0.10975609756097561D * d1, 0.9210526315789473D * d2, 0.08536585365853659D * d1);
    localCircle5.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D17, localCircle5);
    Circle localCircle6 = new Circle(0.8902439024390244D * d1, 0.7807017543859649D * d2, 0.08536585365853659D * d1);
    localCircle6.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D56, localCircle6);
    Circle localCircle7 = new Circle(0.6951219512195121D * d1, 0.7807017543859649D * d2, 0.08536585365853659D * d1);
    localCircle7.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D46, localCircle7);
    Circle localCircle8 = new Circle(0.5D * d1, 0.7807017543859649D * d2, 0.08536585365853659D * d1);
    localCircle8.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D36, localCircle8);
    Circle localCircle9 = new Circle(0.3048780487804878D * d1, 0.7807017543859649D * d2, 0.08536585365853659D * d1);
    localCircle9.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D26, localCircle9);
    Circle localCircle10 = new Circle(0.10975609756097561D * d1, 0.7807017543859649D * d2, 0.08536585365853659D * d1);
    localCircle10.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D16, localCircle10);
    Circle localCircle11 = new Circle(0.8902439024390244D * d1, 0.6403508771929824D * d2, 0.08536585365853659D * d1);
    localCircle11.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D55, localCircle11);
    Circle localCircle12 = new Circle(0.6951219512195121D * d1, 0.6403508771929824D * d2, 0.08536585365853659D * d1);
    localCircle12.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D45, localCircle12);
    Circle localCircle13 = new Circle(0.5D * d1, 0.6403508771929824D * d2, 0.08536585365853659D * d1);
    localCircle13.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D35, localCircle13);
    Circle localCircle14 = new Circle(0.3048780487804878D * d1, 0.6403508771929824D * d2, 0.08536585365853659D * d1);
    localCircle14.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D25, localCircle14);
    Circle localCircle15 = new Circle(0.10975609756097561D * d1, 0.6403508771929824D * d2, 0.08536585365853659D * d1);
    localCircle15.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D15, localCircle15);
    Circle localCircle16 = new Circle(0.8902439024390244D * d1, 0.5D * d2, 0.08536585365853659D * d1);
    localCircle16.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D54, localCircle16);
    Circle localCircle17 = new Circle(0.6951219512195121D * d1, 0.5D * d2, 0.08536585365853659D * d1);
    localCircle17.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D44, localCircle17);
    Circle localCircle18 = new Circle(0.5D * d1, 0.5D * d2, 0.08536585365853659D * d1);
    localCircle18.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D34, localCircle18);
    Circle localCircle19 = new Circle(0.3048780487804878D * d1, 0.5D * d2, 0.08536585365853659D * d1);
    localCircle19.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D24, localCircle19);
    Circle localCircle20 = new Circle(0.10975609756097561D * d1, 0.5D * d2, 0.08536585365853659D * d1);
    localCircle20.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D14, localCircle20);
    Circle localCircle21 = new Circle(0.8902439024390244D * d1, 0.35964912280701755D * d2, 0.08536585365853659D * d1);
    localCircle21.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D53, localCircle21);
    Circle localCircle22 = new Circle(0.6951219512195121D * d1, 0.35964912280701755D * d2, 0.08536585365853659D * d1);
    localCircle22.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D43, localCircle22);
    Circle localCircle23 = new Circle(0.5D * d1, 0.35964912280701755D * d2, 0.08536585365853659D * d1);
    localCircle23.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D33, localCircle23);
    Circle localCircle24 = new Circle(0.3048780487804878D * d1, 0.35964912280701755D * d2, 0.08536585365853659D * d1);
    localCircle24.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D23, localCircle24);
    Circle localCircle25 = new Circle(0.10975609756097561D * d1, 0.35964912280701755D * d2, 0.08536585365853659D * d1);
    localCircle25.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D13, localCircle25);
    Circle localCircle26 = new Circle(0.8902439024390244D * d1, 0.21929824561403508D * d2, 0.08536585365853659D * d1);
    localCircle26.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D52, localCircle26);
    Circle localCircle27 = new Circle(0.6951219512195121D * d1, 0.21929824561403508D * d2, 0.08536585365853659D * d1);
    localCircle27.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D42, localCircle27);
    Circle localCircle28 = new Circle(0.5D * d1, 0.21929824561403508D * d2, 0.08536585365853659D * d1);
    localCircle28.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D32, localCircle28);
    Circle localCircle29 = new Circle(0.3048780487804878D * d1, 0.21929824561403508D * d2, 0.08536585365853659D * d1);
    localCircle29.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D22, localCircle29);
    Circle localCircle30 = new Circle(0.10975609756097561D * d1, 0.21929824561403508D * d2, 0.08536585365853659D * d1);
    localCircle30.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D12, localCircle30);
    Circle localCircle31 = new Circle(0.8902439024390244D * d1, 0.07894736842105263D * d2, 0.08536585365853659D * d1);
    localCircle31.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D51, localCircle31);
    Circle localCircle32 = new Circle(0.6951219512195121D * d1, 0.07894736842105263D * d2, 0.08536585365853659D * d1);
    localCircle32.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D41, localCircle32);
    Circle localCircle33 = new Circle(0.5D * d1, 0.07894736842105263D * d2, 0.08536585365853659D * d1);
    localCircle33.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D31, localCircle33);
    Circle localCircle34 = new Circle(0.3048780487804878D * d1, 0.07894736842105263D * d2, 0.08536585365853659D * d1);
    localCircle34.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D21, localCircle34);
    Circle localCircle35 = new Circle(0.10975609756097561D * d1, 0.07894736842105263D * d2, 0.08536585365853659D * d1);
    localCircle35.getStyleClass().add("dot-matrix-segment-off");
    this.dotMap.put(Dot.D11, localCircle35);
    this.dots.getChildren().addAll(new Node[] { localCircle1, localCircle2, localCircle3, localCircle4, localCircle5, localCircle6, localCircle7, localCircle8, localCircle9, localCircle10, localCircle11, localCircle12, localCircle13, localCircle14, localCircle15, localCircle16, localCircle17, localCircle18, localCircle19, localCircle20, localCircle21, localCircle22, localCircle23, localCircle24, localCircle25, localCircle26, localCircle27, localCircle28, localCircle29, localCircle30, localCircle31, localCircle32, localCircle33, localCircle34, localCircle35 });
    this.dots.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/DotMatrixSegmentSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */