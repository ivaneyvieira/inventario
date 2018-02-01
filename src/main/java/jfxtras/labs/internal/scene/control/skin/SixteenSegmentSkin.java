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
import jfxtras.labs.internal.scene.control.behavior.SixteenSegmentBehavior;
import jfxtras.labs.scene.control.gauge.SixteenSegment;
import jfxtras.labs.scene.control.gauge.SixteenSegment.Segment;
import jfxtras.labs.util.Util;

public class SixteenSegmentSkin
  extends SkinBase<SixteenSegment, SixteenSegmentBehavior>
{
  private SixteenSegment control;
  private boolean isDirty;
  private boolean initialized;
  private Group segments;
  private Map<Segment, Shape> segmentMap;
  
  public SixteenSegmentSkin(SixteenSegment paramSixteenSegment)
  {
    super(paramSixteenSegment, new SixteenSegmentBehavior(paramSixteenSegment));
    this.control = paramSixteenSegment;
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
    registerChangeListener(this.control.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(this.control.prefHeightProperty(), "PREF_HEIGHT");
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
      getChildren().setAll(new Node[] { this.segments });
    }
    this.isDirty = false;
    super.layoutChildren();
  }
  
  public final SixteenSegment getSkinnable()
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
    String str = this.control.isPlainColor() ? "sixteen-segment-plain-on" : "sixteen-segment-on";
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
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add("sixteen-segment-off");
            ((Shape)this.segmentMap.get(localSegment)).setEffect(null);
          }
        }
        else
        {
          ((Shape)this.segmentMap.get(localSegment)).getStyleClass().clear();
          ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add("sixteen-segment-off");
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
            ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add("sixteen-segment-off");
            ((Shape)this.segmentMap.get(localSegment)).setEffect(null);
          }
        }
        else
        {
          ((Shape)this.segmentMap.get(localSegment)).getStyleClass().clear();
          ((Shape)this.segmentMap.get(localSegment)).getStyleClass().add("sixteen-segment-off");
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
    localPath1.getElements().add(new MoveTo(0.13389121338912133D * d1, 0.028985507246376812D * d2));
    localPath1.getElements().add(new LineTo(0.15481171548117154D * d1, 0.014492753623188406D * d2));
    localPath1.getElements().add(new LineTo(0.45188284518828453D * d1, 0.014492753623188406D * d2));
    localPath1.getElements().add(new LineTo(0.497907949790795D * d1, 0.043478260869565216D * d2));
    localPath1.getElements().add(new LineTo(0.497907949790795D * d1, 0.04927536231884058D * d2));
    localPath1.getElements().add(new LineTo(0.4435146443514644D * d1, 0.08405797101449275D * d2));
    localPath1.getElements().add(new LineTo(0.20920502092050208D * d1, 0.08405797101449275D * d2));
    localPath1.getElements().add(new LineTo(0.13389121338912133D * d1, 0.03188405797101449D * d2));
    localPath1.getElements().add(new LineTo(0.13389121338912133D * d1, 0.028985507246376812D * d2));
    localPath1.getElements().add(new ClosePath());
    localPath1.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.A1, localPath1);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.8702928870292888D * d1, 0.03188405797101449D * d2));
    localPath2.getElements().add(new LineTo(0.8451882845188284D * d1, 0.014492753623188406D * d2));
    localPath2.getElements().add(new LineTo(0.5523012552301255D * d1, 0.014492753623188406D * d2));
    localPath2.getElements().add(new LineTo(0.502092050209205D * d1, 0.043478260869565216D * d2));
    localPath2.getElements().add(new LineTo(0.502092050209205D * d1, 0.0463768115942029D * d2));
    localPath2.getElements().add(new LineTo(0.5564853556485355D * d1, 0.08405797101449275D * d2));
    localPath2.getElements().add(new LineTo(0.799163179916318D * d1, 0.08405797101449275D * d2));
    localPath2.getElements().add(new LineTo(0.8702928870292888D * d1, 0.034782608695652174D * d2));
    localPath2.getElements().add(new LineTo(0.8702928870292888D * d1, 0.03188405797101449D * d2));
    localPath2.getElements().add(new ClosePath());
    localPath2.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.A2, localPath2);
    Path localPath3 = new Path();
    localPath3.setFillRule(FillRule.EVEN_ODD);
    localPath3.getElements().add(new MoveTo(0.8786610878661087D * d1, 0.03768115942028986D * d2));
    localPath3.getElements().add(new LineTo(0.9037656903765691D * d1, 0.057971014492753624D * d2));
    localPath3.getElements().add(new LineTo(0.9037656903765691D * d1, 0.09565217391304348D * d2));
    localPath3.getElements().add(new LineTo(0.8661087866108786D * d1, 0.463768115942029D * d2));
    localPath3.getElements().add(new LineTo(0.8158995815899581D * d1, 0.4985507246376812D * d2));
    localPath3.getElements().add(new LineTo(0.7656903765690377D * d1, 0.463768115942029D * d2));
    localPath3.getElements().add(new LineTo(0.8075313807531381D * d1, 0.08695652173913043D * d2));
    localPath3.getElements().add(new LineTo(0.8786610878661087D * d1, 0.03768115942028986D * d2));
    localPath3.getElements().add(new ClosePath());
    localPath3.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.B, localPath3);
    Path localPath4 = new Path();
    localPath4.setFillRule(FillRule.EVEN_ODD);
    localPath4.getElements().add(new MoveTo(0.8158995815899581D * d1, 0.5014492753623189D * d2));
    localPath4.getElements().add(new LineTo(0.8619246861924686D * d1, 0.5333333333333333D * d2));
    localPath4.getElements().add(new LineTo(0.8158995815899581D * d1, 0.9536231884057971D * d2));
    localPath4.getElements().add(new LineTo(0.7949790794979079D * d1, 0.9681159420289855D * d2));
    localPath4.getElements().add(new LineTo(0.7196652719665272D * d1, 0.9130434782608695D * d2));
    localPath4.getElements().add(new LineTo(0.7573221757322176D * d1, 0.5420289855072464D * d2));
    localPath4.getElements().add(new LineTo(0.8158995815899581D * d1, 0.5014492753623189D * d2));
    localPath4.getElements().add(new ClosePath());
    localPath4.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.C, localPath4);
    Path localPath5 = new Path();
    localPath5.setFillRule(FillRule.EVEN_ODD);
    localPath5.getElements().add(new MoveTo(0.7112970711297071D * d1, 0.9159420289855073D * d2));
    localPath5.getElements().add(new LineTo(0.7866108786610879D * d1, 0.9681159420289855D * d2));
    localPath5.getElements().add(new LineTo(0.7866108786610879D * d1, 0.9739130434782609D * d2));
    localPath5.getElements().add(new LineTo(0.7698744769874477D * d1, 0.9855072463768116D * d2));
    localPath5.getElements().add(new LineTo(0.47280334728033474D * d1, 0.9855072463768116D * d2));
    localPath5.getElements().add(new LineTo(0.4309623430962343D * d1, 0.9565217391304348D * d2));
    localPath5.getElements().add(new LineTo(0.4309623430962343D * d1, 0.9507246376811594D * d2));
    localPath5.getElements().add(new LineTo(0.4811715481171548D * d1, 0.9159420289855073D * d2));
    localPath5.getElements().add(new LineTo(0.7112970711297071D * d1, 0.9159420289855073D * d2));
    localPath5.getElements().add(new ClosePath());
    localPath5.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.D2, localPath5);
    Path localPath6 = new Path();
    localPath6.setFillRule(FillRule.EVEN_ODD);
    localPath6.getElements().add(new MoveTo(0.3682008368200837D * d1, 0.9159420289855073D * d2));
    localPath6.getElements().add(new LineTo(0.41841004184100417D * d1, 0.9507246376811594D * d2));
    localPath6.getElements().add(new LineTo(0.41841004184100417D * d1, 0.9565217391304348D * d2));
    localPath6.getElements().add(new LineTo(0.3723849372384937D * d1, 0.9855072463768116D * d2));
    localPath6.getElements().add(new LineTo(0.08368200836820083D * d1, 0.9855072463768116D * d2));
    localPath6.getElements().add(new LineTo(0.058577405857740586D * d1, 0.9681159420289855D * d2));
    localPath6.getElements().add(new LineTo(0.058577405857740586D * d1, 0.9623188405797102D * d2));
    localPath6.getElements().add(new LineTo(0.12552301255230125D * d1, 0.9159420289855073D * d2));
    localPath6.getElements().add(new LineTo(0.3682008368200837D * d1, 0.9159420289855073D * d2));
    localPath6.getElements().add(new ClosePath());
    localPath6.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.D1, localPath6);
    Path localPath7 = new Path();
    localPath7.setFillRule(FillRule.EVEN_ODD);
    localPath7.getElements().add(new MoveTo(0.0502092050209205D * d1, 0.9623188405797102D * d2));
    localPath7.getElements().add(new LineTo(0.02092050209205021D * d1, 0.9420289855072463D * d2));
    localPath7.getElements().add(new LineTo(0.02092050209205021D * d1, 0.9043478260869565D * d2));
    localPath7.getElements().add(new LineTo(0.06276150627615062D * d1, 0.5362318840579711D * d2));
    localPath7.getElements().add(new LineTo(0.11297071129707113D * d1, 0.5014492753623189D * d2));
    localPath7.getElements().add(new LineTo(0.1589958158995816D * d1, 0.5362318840579711D * d2));
    localPath7.getElements().add(new LineTo(0.11715481171548117D * d1, 0.9130434782608695D * d2));
    localPath7.getElements().add(new LineTo(0.0502092050209205D * d1, 0.9623188405797102D * d2));
    localPath7.getElements().add(new ClosePath());
    localPath7.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.E, localPath7);
    Path localPath8 = new Path();
    localPath8.setFillRule(FillRule.EVEN_ODD);
    localPath8.getElements().add(new MoveTo(0.12552301255230125D * d1, 0.03188405797101449D * d2));
    localPath8.getElements().add(new LineTo(0.200836820083682D * d1, 0.08695652173913043D * d2));
    localPath8.getElements().add(new LineTo(0.16736401673640167D * d1, 0.4579710144927536D * d2));
    localPath8.getElements().add(new LineTo(0.1087866108786611D * d1, 0.4985507246376812D * d2));
    localPath8.getElements().add(new LineTo(0.06276150627615062D * d1, 0.463768115942029D * d2));
    localPath8.getElements().add(new LineTo(0.10460251046025104D * d1, 0.0463768115942029D * d2));
    localPath8.getElements().add(new LineTo(0.12552301255230125D * d1, 0.03188405797101449D * d2));
    localPath8.getElements().add(new ClosePath());
    localPath8.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.F, localPath8);
    Path localPath9 = new Path();
    localPath9.setFillRule(FillRule.EVEN_ODD);
    localPath9.getElements().add(new MoveTo(0.20920502092050208D * d1, 0.08695652173913043D * d2));
    localPath9.getElements().add(new LineTo(0.2510460251046025D * d1, 0.1246376811594203D * d2));
    localPath9.getElements().add(new LineTo(0.41422594142259417D * d1, 0.3536231884057971D * d2));
    localPath9.getElements().add(new LineTo(0.45188284518828453D * d1, 0.48695652173913045D * d2));
    localPath9.getElements().add(new LineTo(0.3472803347280335D * d1, 0.41739130434782606D * d2));
    localPath9.getElements().add(new LineTo(0.19665271966527198D * d1, 0.20579710144927535D * d2));
    localPath9.getElements().add(new LineTo(0.20920502092050208D * d1, 0.08695652173913043D * d2));
    localPath9.getElements().add(new ClosePath());
    localPath9.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.G, localPath9);
    Path localPath10 = new Path();
    localPath10.setFillRule(FillRule.EVEN_ODD);
    localPath10.getElements().add(new MoveTo(0.502092050209205D * d1, 0.05507246376811594D * d2));
    localPath10.getElements().add(new LineTo(0.5481171548117155D * d1, 0.08695652173913043D * d2));
    localPath10.getElements().add(new LineTo(0.5397489539748954D * d1, 0.1565217391304348D * d2));
    localPath10.getElements().add(new LineTo(0.5188284518828452D * d1, 0.34782608695652173D * d2));
    localPath10.getElements().add(new LineTo(0.4602510460251046D * d1, 0.4956521739130435D * d2));
    localPath10.getElements().add(new LineTo(0.41841004184100417D * d1, 0.3536231884057971D * d2));
    localPath10.getElements().add(new LineTo(0.4476987447698745D * d1, 0.08695652173913043D * d2));
    localPath10.getElements().add(new LineTo(0.502092050209205D * d1, 0.05507246376811594D * d2));
    localPath10.getElements().add(new ClosePath());
    localPath10.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.H, localPath10);
    Path localPath11 = new Path();
    localPath11.setFillRule(FillRule.EVEN_ODD);
    localPath11.getElements().add(new MoveTo(0.799163179916318D * d1, 0.08695652173913043D * d2));
    localPath11.getElements().add(new LineTo(0.7907949790794979D * d1, 0.19710144927536233D * d2));
    localPath11.getElements().add(new LineTo(0.5941422594142259D * d1, 0.4028985507246377D * d2));
    localPath11.getElements().add(new LineTo(0.47280334728033474D * d1, 0.48695652173913045D * d2));
    localPath11.getElements().add(new LineTo(0.5313807531380753D * d1, 0.3391304347826087D * d2));
    localPath11.getElements().add(new LineTo(0.7280334728033473D * d1, 0.13333333333333333D * d2));
    localPath11.getElements().add(new LineTo(0.799163179916318D * d1, 0.08695652173913043D * d2));
    localPath11.getElements().add(new ClosePath());
    localPath11.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.J, localPath11);
    Path localPath12 = new Path();
    localPath12.setFillRule(FillRule.EVEN_ODD);
    localPath12.getElements().add(new MoveTo(0.46443514644351463D * d1, 0.4985507246376812D * d2));
    localPath12.getElements().add(new LineTo(0.5146443514644351D * d1, 0.463768115942029D * d2));
    localPath12.getElements().add(new LineTo(0.7573221757322176D * d1, 0.463768115942029D * d2));
    localPath12.getElements().add(new LineTo(0.8117154811715481D * d1, 0.4985507246376812D * d2));
    localPath12.getElements().add(new LineTo(0.7615062761506276D * d1, 0.5362318840579711D * d2));
    localPath12.getElements().add(new LineTo(0.5188284518828452D * d1, 0.5362318840579711D * d2));
    localPath12.getElements().add(new LineTo(0.46443514644351463D * d1, 0.4985507246376812D * d2));
    localPath12.getElements().add(new ClosePath());
    localPath12.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.K, localPath12);
    Path localPath13 = new Path();
    localPath13.setFillRule(FillRule.EVEN_ODD);
    localPath13.getElements().add(new MoveTo(0.7154811715481172D * d1, 0.9130434782608695D * d2));
    localPath13.getElements().add(new LineTo(0.7154811715481172D * d1, 0.8985507246376812D * d2));
    localPath13.getElements().add(new LineTo(0.7238493723849372D * d1, 0.8144927536231884D * d2));
    localPath13.getElements().add(new LineTo(0.7238493723849372D * d1, 0.7913043478260869D * d2));
    localPath13.getElements().add(new LineTo(0.5732217573221757D * d1, 0.5826086956521739D * d2));
    localPath13.getElements().add(new LineTo(0.47280334728033474D * d1, 0.5130434782608696D * d2));
    localPath13.getElements().add(new LineTo(0.5062761506276151D * d1, 0.6405797101449275D * d2));
    localPath13.getElements().add(new LineTo(0.6778242677824268D * d1, 0.8753623188405797D * d2));
    localPath13.getElements().add(new LineTo(0.7154811715481172D * d1, 0.9130434782608695D * d2));
    localPath13.getElements().add(new ClosePath());
    localPath13.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.L, localPath13);
    Path localPath14 = new Path();
    localPath14.setFillRule(FillRule.EVEN_ODD);
    localPath14.getElements().add(new MoveTo(0.4225941422594142D * d1, 0.9478260869565217D * d2));
    localPath14.getElements().add(new LineTo(0.37656903765690375D * d1, 0.9159420289855073D * d2));
    localPath14.getElements().add(new LineTo(0.37656903765690375D * d1, 0.881159420289855D * d2));
    localPath14.getElements().add(new LineTo(0.401673640167364D * d1, 0.6521739130434783D * d2));
    localPath14.getElements().add(new LineTo(0.46443514644351463D * d1, 0.5043478260869565D * d2));
    localPath14.getElements().add(new LineTo(0.5062761506276151D * d1, 0.6434782608695652D * d2));
    localPath14.getElements().add(new LineTo(0.4769874476987448D * d1, 0.9130434782608695D * d2));
    localPath14.getElements().add(new LineTo(0.4225941422594142D * d1, 0.9478260869565217D * d2));
    localPath14.getElements().add(new ClosePath());
    localPath14.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.M, localPath14);
    Path localPath15 = new Path();
    localPath15.setFillRule(FillRule.EVEN_ODD);
    localPath15.getElements().add(new MoveTo(0.12552301255230125D * d1, 0.9130434782608695D * d2));
    localPath15.getElements().add(new LineTo(0.13389121338912133D * d1, 0.8D * d2));
    localPath15.getElements().add(new LineTo(0.24267782426778242D * d1, 0.6898550724637681D * d2));
    localPath15.getElements().add(new LineTo(0.3263598326359833D * d1, 0.5971014492753624D * d2));
    localPath15.getElements().add(new LineTo(0.45188284518828453D * d1, 0.5130434782608696D * d2));
    localPath15.getElements().add(new LineTo(0.39748953974895396D * d1, 0.6463768115942029D * d2));
    localPath15.getElements().add(new LineTo(0.38493723849372385D * d1, 0.6666666666666666D * d2));
    localPath15.getElements().add(new LineTo(0.19665271966527198D * d1, 0.8666666666666667D * d2));
    localPath15.getElements().add(new LineTo(0.12552301255230125D * d1, 0.9130434782608695D * d2));
    localPath15.getElements().add(new ClosePath());
    localPath15.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.N, localPath15);
    Path localPath16 = new Path();
    localPath16.setFillRule(FillRule.EVEN_ODD);
    localPath16.getElements().add(new MoveTo(0.11715481171548117D * d1, 0.4985507246376812D * d2));
    localPath16.getElements().add(new LineTo(0.17154811715481172D * d1, 0.463768115942029D * d2));
    localPath16.getElements().add(new LineTo(0.4100418410041841D * d1, 0.463768115942029D * d2));
    localPath16.getElements().add(new LineTo(0.4602510460251046D * d1, 0.4985507246376812D * d2));
    localPath16.getElements().add(new LineTo(0.40585774058577406D * d1, 0.5362318840579711D * d2));
    localPath16.getElements().add(new LineTo(0.17154811715481172D * d1, 0.5362318840579711D * d2));
    localPath16.getElements().add(new LineTo(0.11715481171548117D * d1, 0.4985507246376812D * d2));
    localPath16.getElements().add(new ClosePath());
    localPath16.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.P, localPath16);
    Circle localCircle = new Circle(0.9121338912133892D * d1, 0.9391304347826087D * d2, 0.06694560669456066D * d1);
    localCircle.getStyleClass().add("sixteen-segment-off");
    this.segmentMap.put(Segment.DOT, localCircle);
    this.segments.getChildren().addAll(new Node[] { localPath1, localPath2, localPath3, localPath4, localPath5, localPath6, localPath7, localPath8, localPath9, localPath10, localPath11, localPath12, localPath13, localPath14, localPath15, localPath16, localCircle });
    this.segments.setCache(true);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/SixteenSegmentSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */