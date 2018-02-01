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
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import jfxtras.labs.internal.scene.control.behavior.SevenSegmentBehavior;
import jfxtras.labs.scene.control.gauge.SevenSegment;
import jfxtras.labs.scene.control.gauge.SevenSegment.Segment;
import jfxtras.labs.util.Util;

public class SevenSegmentSkin
  extends SkinBase<SevenSegment, SevenSegmentBehavior>
{
  private SevenSegment control;
  private boolean isDirty;
  private boolean initialized;
  private Group segments;
  private Map<Segment, Shape> segmentMap;
  
  public SevenSegmentSkin(SevenSegment paramSevenSegment)
  {
    super(paramSevenSegment, new SevenSegmentBehavior(paramSevenSegment));
    this.control = paramSevenSegment;
    this.initialized = false;
    this.isDirty = false;
    this.segments = new Group();
    this.segmentMap = new HashMap(17);
    init();
  }
  
  private void init()
  {
    if (((this.control.getPrefWidth() < 0.0D ? 1 : 0) | (this.control.getPrefHeight() < 0.0D ? 1 : 0)) != 0) {
      this.control.setPrefSize(40.0D, 56.0D);
    }
    createSegments();
    updateCharacter();
    registerChangeListener(this.control.characterProperty(), "CHARACTER");
    registerChangeListener(this.control.colorProperty(), "COLOR");
    registerChangeListener(this.control.plainColorProperty(), "PLAIN_COLOR");
    registerChangeListener(this.control.customSegmentMappingProperty(), "CUSTOM_MAPPING");
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
      getChildren().setAll(new Node[] { this.segments });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final SevenSegment getSkinnable()
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
    this.segments.setStyle("-fx-segment-color-on: " + Util.createCssColor(this.control.getColor()) + "-fx-segment-color-off: " + Util.createCssColor(Color.color(this.control.getColor().getRed(), this.control.getColor().getGreen(), this.control.getColor().getBlue(), 0.075D)));
    int i = this.control.getCharacter().isEmpty() ? '\024' : this.control.getCharacter().toUpperCase().charAt(0);
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setRadius(0.05D * this.control.getPrefWidth());
    localInnerShadow.setColor(Color.hsb(this.control.getColor().getHue(), this.control.getColor().getSaturation(), 0.2D));
    String str = this.control.isPlainColor() ? "seven-segment-plain-on" : "seven-segment-on";
    Iterator localIterator;
    Segment localSegment;
    if (this.control.getCustomSegmentMapping().isEmpty())
    {
      localIterator = this.segmentMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        localSegment = (Segment)localIterator.next();
        if (this.control.getSegmentMapping().containsKey(Integer.valueOf(i)))
        {
          if (((List)this.control.getSegmentMapping().get(Integer.valueOf(i))).contains(localSegment))
          {
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().clear();
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add(str);
            ((Shape)this.segmentMap.get(localSegment)).setEffect(localInnerShadow);
          }
          else
          {
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().clear();
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add("seven-segment-off");
            ((Shape)this.segmentMap.get(localSegment)).setEffect(null);
          }
        }
        else
        {
          ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add("seven-segment-off");
          ((Shape)this.segmentMap.get(localSegment)).setEffect(null);
        }
      }
    }
    else
    {
      localIterator = this.segmentMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        localSegment = (Segment)localIterator.next();
        if (this.control.getCustomSegmentMapping().containsKey(Integer.valueOf(i)))
        {
          if (((List)this.control.getCustomSegmentMapping().get(Integer.valueOf(i))).contains(localSegment))
          {
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().clear();
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add(str);
            ((Shape)this.segmentMap.get(localSegment)).setEffect(localInnerShadow);
          }
          else
          {
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().clear();
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add("seven-segment-off");
            ((Shape)this.segmentMap.get(localSegment)).setEffect(null);
          }
        }
        else
        {
          ((Shape)this.segmentMap.get(localSegment)).getStyleClass().clear();
          ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add("seven-segment-off");
          ((Shape)this.segmentMap.get(localSegment)).setEffect(null);
        }
      }
    }
    if (this.control.isDotOn())
    {
      ((Shape)this.segmentMap.get(Segment.DOT)).getStyleClass().clear();
      ((Shape)this.segmentMap.get(Segment.DOT)).getStyleClass().add(str);
      ((Shape)this.segmentMap.get(Segment.DOT)).setEffect(localInnerShadow);
    }
  }
  
  public void createSegments()
  {
    double d1 = this.control.getPrefWidth();
    double d2 = this.control.getPrefHeight();
    this.segments.setStyle("-fx-segment-color-on: " + Util.createCssColor(this.control.getColor()) + "-fx-segment-color-off: " + Util.createCssColor(Color.color(this.control.getColor().getRed(), this.control.getColor().getGreen(), this.control.getColor().getBlue(), 0.075D)));
    this.segments.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle.setOpacity(0.0D);
    this.segments.getChildren().add(localRectangle);
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.11790393013100436D * d1, 0.014925373134328358D * d2));
    localPath1.getElements().add(new LineTo(0.11790393013100436D * d1, 0.01791044776119403D * d2));
    localPath1.getElements().add(new LineTo(0.1965065502183406D * d1, 0.07164179104477612D * d2));
    localPath1.getElements().add(new LineTo(0.8122270742358079D * d1, 0.07164179104477612D * d2));
    localPath1.getElements().add(new LineTo(0.8864628820960698D * d1, 0.020895522388059702D * d2));
    localPath1.getElements().add(new LineTo(0.8864628820960698D * d1, 0.01791044776119403D * d2));
    localPath1.getElements().add(new LineTo(0.8602620087336245D * d1, 0.0D));
    localPath1.getElements().add(new LineTo(0.13973799126637554D * d1, 0.0D));
    localPath1.getElements().add(new LineTo(0.11790393013100436D * d1, 0.014925373134328358D * d2));
    localPath1.getElements().add(new ClosePath());
    localPath1.getStyleClass().add("seven-segment-off");
    this.segmentMap.put(Segment.A, localPath1);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.8951965065502183D * d1, 0.023880597014925373D * d2));
    localPath2.getElements().add(new LineTo(0.9213973799126638D * d1, 0.04477611940298507D * d2));
    localPath2.getElements().add(new LineTo(0.9213973799126638D * d1, 0.08358208955223881D * d2));
    localPath2.getElements().add(new LineTo(0.8820960698689956D * d1, 0.4626865671641791D * d2));
    localPath2.getElements().add(new LineTo(0.8296943231441049D * d1, 0.49850746268656715D * d2));
    localPath2.getElements().add(new LineTo(0.777292576419214D * d1, 0.4626865671641791D * d2));
    localPath2.getElements().add(new LineTo(0.8209606986899564D * d1, 0.07462686567164178D * d2));
    localPath2.getElements().add(new LineTo(0.8951965065502183D * d1, 0.023880597014925373D * d2));
    localPath2.getElements().add(new ClosePath());
    localPath2.getStyleClass().add("seven-segment-off");
    this.segmentMap.put(Segment.B, localPath2);
    Path localPath3 = new Path();
    localPath3.setFillRule(FillRule.EVEN_ODD);
    localPath3.getElements().add(new MoveTo(0.8296943231441049D * d1, 0.5014925373134328D * d2));
    localPath3.getElements().add(new LineTo(0.8777292576419214D * d1, 0.5343283582089552D * d2));
    localPath3.getElements().add(new LineTo(0.8296943231441049D * d1, 0.9671641791044776D * d2));
    localPath3.getElements().add(new LineTo(0.8078602620087336D * d1, 0.982089552238806D * d2));
    localPath3.getElements().add(new LineTo(0.7292576419213974D * d1, 0.9253731343283582D * d2));
    localPath3.getElements().add(new LineTo(0.7685589519650655D * d1, 0.5432835820895522D * d2));
    localPath3.getElements().add(new LineTo(0.8296943231441049D * d1, 0.5014925373134328D * d2));
    localPath3.getElements().add(new ClosePath());
    localPath3.getStyleClass().add("seven-segment-off");
    this.segmentMap.put(Segment.C, localPath3);
    Path localPath4 = new Path();
    localPath4.setFillRule(FillRule.EVEN_ODD);
    localPath4.getElements().add(new MoveTo(0.7205240174672489D * d1, 0.9283582089552239D * d2));
    localPath4.getElements().add(new LineTo(0.1091703056768559D * d1, 0.9283582089552239D * d2));
    localPath4.getElements().add(new LineTo(0.039301310043668124D * d1, 0.9761194029850746D * d2));
    localPath4.getElements().add(new LineTo(0.039301310043668124D * d1, 0.982089552238806D * d2));
    localPath4.getElements().add(new LineTo(0.06550218340611354D * d1, d2));
    localPath4.getElements().add(new LineTo(0.7816593886462883D * d1, d2));
    localPath4.getElements().add(new LineTo(0.7991266375545851D * d1, 0.9880597014925373D * d2));
    localPath4.getElements().add(new LineTo(0.7991266375545851D * d1, 0.982089552238806D * d2));
    localPath4.getElements().add(new LineTo(0.7205240174672489D * d1, 0.9283582089552239D * d2));
    localPath4.getElements().add(new ClosePath());
    localPath4.getStyleClass().add("seven-segment-off");
    this.segmentMap.put(Segment.D, localPath4);
    Path localPath5 = new Path();
    localPath5.setFillRule(FillRule.EVEN_ODD);
    localPath5.getElements().add(new MoveTo(0.03056768558951965D * d1, 0.9761194029850746D * d2));
    localPath5.getElements().add(new LineTo(0.0D, 0.9552238805970149D * d2));
    localPath5.getElements().add(new LineTo(0.0D, 0.9164179104477612D * d2));
    localPath5.getElements().add(new LineTo(0.043668122270742356D * d1, 0.5373134328358209D * d2));
    localPath5.getElements().add(new LineTo(0.09606986899563319D * d1, 0.5014925373134328D * d2));
    localPath5.getElements().add(new LineTo(0.14410480349344978D * d1, 0.5373134328358209D * d2));
    localPath5.getElements().add(new LineTo(0.10043668122270742D * d1, 0.9253731343283582D * d2));
    localPath5.getElements().add(new LineTo(0.03056768558951965D * d1, 0.9761194029850746D * d2));
    localPath5.getElements().add(new ClosePath());
    localPath5.getStyleClass().add("seven-segment-off");
    this.segmentMap.put(Segment.E, localPath5);
    Path localPath6 = new Path();
    localPath6.setFillRule(FillRule.EVEN_ODD);
    localPath6.getElements().add(new MoveTo(0.1091703056768559D * d1, 0.01791044776119403D * d2));
    localPath6.getElements().add(new LineTo(0.18777292576419213D * d1, 0.07462686567164178D * d2));
    localPath6.getElements().add(new LineTo(0.15283842794759825D * d1, 0.45671641791044776D * d2));
    localPath6.getElements().add(new LineTo(0.09170305676855896D * d1, 0.49850746268656715D * d2));
    localPath6.getElements().add(new LineTo(0.043668122270742356D * d1, 0.4626865671641791D * d2));
    localPath6.getElements().add(new LineTo(0.08733624454148471D * d1, 0.03283582089552239D * d2));
    localPath6.getElements().add(new LineTo(0.1091703056768559D * d1, 0.01791044776119403D * d2));
    localPath6.getElements().add(new ClosePath());
    localPath6.getStyleClass().add("seven-segment-off");
    this.segmentMap.put(Segment.F, localPath6);
    Path localPath7 = new Path();
    localPath7.setFillRule(FillRule.EVEN_ODD);
    localPath7.getElements().add(new MoveTo(0.7729257641921398D * d1, 0.5373134328358209D * d2));
    localPath7.getElements().add(new LineTo(0.8253275109170306D * d1, 0.49850746268656715D * d2));
    localPath7.getElements().add(new LineTo(0.7685589519650655D * d1, 0.4626865671641791D * d2));
    localPath7.getElements().add(new LineTo(0.1572052401746725D * d1, 0.4626865671641791D * d2));
    localPath7.getElements().add(new LineTo(0.10043668122270742D * d1, 0.49850746268656715D * d2));
    localPath7.getElements().add(new LineTo(0.1572052401746725D * d1, 0.5373134328358209D * d2));
    localPath7.getElements().add(new LineTo(0.7729257641921398D * d1, 0.5373134328358209D * d2));
    localPath7.getElements().add(new ClosePath());
    localPath7.getStyleClass().add("seven-segment-off");
    this.segmentMap.put(Segment.G, localPath7);
    Circle localCircle = new Circle(0.9301310043668122D * d1, 0.9522388059701492D * d2, 0.06986899563318777D * d1);
    localCircle.getStyleClass().add("seven-segment-off");
    this.segmentMap.put(Segment.DOT, localCircle);
    this.segments.getChildren().addAll(new Node[] { localPath1, localPath2, localPath3, localPath4, localPath5, localPath6, localPath7, localCircle });
    this.segments.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/SevenSegmentSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */