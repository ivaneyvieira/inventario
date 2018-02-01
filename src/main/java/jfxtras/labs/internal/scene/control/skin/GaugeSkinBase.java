package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import jfxtras.labs.internal.scene.control.behavior.GaugeBehaviorBase;
import jfxtras.labs.scene.control.gauge.ColorDef;
import jfxtras.labs.scene.control.gauge.Gauge;
import jfxtras.labs.scene.control.gauge.Gauge.BackgroundDesign;
import jfxtras.labs.scene.control.gauge.Gauge.FrameDesign;
import jfxtras.labs.scene.control.gauge.Gauge.KnobColor;
import jfxtras.labs.scene.control.gauge.Gauge.NumberFormat;
import jfxtras.labs.scene.control.gauge.Gauge.PointerType;
import jfxtras.labs.scene.control.gauge.Gauge.RadialRange;
import jfxtras.labs.scene.control.gauge.LcdDesign;
import jfxtras.labs.scene.control.gauge.LedColor;
import jfxtras.labs.scene.control.gauge.Marker;
import jfxtras.labs.scene.control.gauge.MarkerEvent;
import jfxtras.labs.scene.control.gauge.MarkerEvent.Type;
import jfxtras.labs.scene.control.gauge.Section;
import jfxtras.labs.util.ConicalGradient;
import jfxtras.labs.util.Util;

public abstract class GaugeSkinBase<C extends Gauge, B extends GaugeBehaviorBase<C>>
  extends SkinBase<C, B>
{
  private long blinkInterval = 500000000L;
  
  public GaugeSkinBase(C paramC, B paramB)
  {
    super(paramC, paramB);
    registerChangeListener(paramC.widthProperty(), "WIDTH");
    registerChangeListener(paramC.heightProperty(), "HEIGHT");
    registerChangeListener(paramC.prefWidthProperty(), "PREF_WIDTH");
    registerChangeListener(paramC.prefHeightProperty(), "PREF_HEIGHT");
    registerChangeListener(paramC.animationDurationProperty(), "ANIMATION_DURATION");
    registerChangeListener(paramC.radialRangeProperty(), "RADIAL_RANGE");
    registerChangeListener(paramC.frameDesignProperty(), "FRAME_DESIGN");
    registerChangeListener(paramC.backgroundDesignProperty(), "BACKGROUND_DESIGN");
    registerChangeListener(paramC.knobDesignProperty(), "KNOB_DESIGN");
    registerChangeListener(paramC.knobColorProperty(), "KNOB_COLOR");
    registerChangeListener(paramC.pointerTypeProperty(), "POINTER_TYPE");
    registerChangeListener(paramC.valueColorProperty(), "VALUE_COLOR");
    registerChangeListener(paramC.pointerGlowEnabledProperty(), "POINTER_GLOW");
    registerChangeListener(paramC.pointerShadowEnabledProperty(), "POINTER_SHADOW");
    registerChangeListener(paramC.thresholdProperty(), "THRESHOLD");
    registerChangeListener(paramC.thresholdColorProperty(), "THRESHOLD_COLOR");
    registerChangeListener(paramC.foregroundTypeProperty(), "FOREGROUND_TYPE");
    registerChangeListener(paramC.lcdDesignProperty(), "LCD");
    registerChangeListener(paramC.lcdNumberSystemProperty(), "LCD");
    registerChangeListener(paramC.lcdValueFontProperty(), "LCD");
    registerChangeListener(paramC.lcdBackgroundVisibleProperty(), "LCD");
    registerChangeListener(paramC.userLedBlinkingProperty(), "USER_LED_BLINKING");
    registerChangeListener(paramC.ledBlinkingProperty(), "LED_BLINKING");
    registerChangeListener(paramC.glowColorProperty(), "GLOW_COLOR");
    registerChangeListener(paramC.glowVisibleProperty(), "GLOW_VISIBILITY");
    registerChangeListener(paramC.glowOnProperty(), "GLOW_ON");
    registerChangeListener(paramC.pulsatingGlowProperty(), "PULSATING_GLOW");
    registerChangeListener(paramC.minMeasuredValueProperty(), "MIN_MEASURED_VALUE");
    registerChangeListener(paramC.maxMeasuredValueProperty(), "MAX_MEASURED_VALUE");
    registerChangeListener(paramC.trendProperty(), "TREND");
    registerChangeListener(paramC.simpleGradientBaseColorProperty(), "SIMPLE_GRADIENT_BASE");
    registerChangeListener(paramC.gaugeModelProperty(), "GAUGE_MODEL");
    registerChangeListener(paramC.styleModelProperty(), "STYLE_MODEL");
    registerChangeListener(paramC.thresholdExceededProperty(), "THRESHOLD_EXCEEDED");
    registerChangeListener(paramC.rangeProperty(), "TICKMARKS");
    registerChangeListener(paramC.tickmarkGlowEnabledProperty(), "TICKMARKS");
    registerChangeListener(paramC.tickmarkGlowProperty(), "TICKMARKS");
    registerChangeListener(paramC.majorTickmarkColorProperty(), "TICKMARKS");
    registerChangeListener(paramC.majorTickmarkTypeProperty(), "TICKMARKS");
    registerChangeListener(paramC.majorTickSpacingProperty(), "TICKMARKS");
    registerChangeListener(paramC.majorTickmarkColorEnabledProperty(), "TICKMARKS");
    registerChangeListener(paramC.minorTickmarkColorProperty(), "TICKMARKS");
    registerChangeListener(paramC.minorTickSpacingProperty(), "TICKMARKS");
    registerChangeListener(paramC.minorTickmarkColorEnabledProperty(), "TICKMARKS");
    registerChangeListener(paramC.tickLabelNumberFormatProperty(), "TICKMARKS");
    registerChangeListener(paramC.tickLabelOrientationProperty(), "TICKMARKS");
    registerChangeListener(paramC.tickmarksOffsetProperty(), "TICKMARKS");
    registerChangeListener(paramC.niceScalingProperty(), "TICKMARKS");
    registerChangeListener(paramC.tightScaleProperty(), "TICKMARKS");
    registerChangeListener(paramC.largeNumberScaleProperty(), "TICKMARKS");
    registerChangeListener(paramC.areasHighlightingProperty(), "AREAS");
    registerChangeListener(paramC.sectionsHighlightingProperty(), "SECTIONS");
    registerChangeListener(paramC.redrawToleranceProperty(), "REDRAW_TOLERANCE");
  }
  
  protected double computeMinWidth(double paramDouble)
  {
    return ((Gauge)getSkinnable()).prefWidth(paramDouble);
  }
  
  protected double computeMinHeight(double paramDouble)
  {
    return ((Gauge)getSkinnable()).prefHeight(paramDouble);
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    return ((Gauge)getSkinnable()).prefWidth(paramDouble);
  }
  
  protected double computePrefHeight(double paramDouble)
  {
    return ((Gauge)getSkinnable()).prefHeight(paramDouble);
  }
  
  protected double computeMaxWidth(double paramDouble)
  {
    return ((Gauge)getSkinnable()).prefWidth(paramDouble);
  }
  
  protected double computeMaxHeight(double paramDouble)
  {
    return ((Gauge)getSkinnable()).prefHeight(paramDouble);
  }
  
  protected void layoutChildren()
  {
    Insets localInsets = getInsets();
    double d1 = localInsets.getLeft();
    double d2 = localInsets.getTop();
    double d3 = getWidth() - (localInsets.getLeft() + localInsets.getRight());
    double d4 = getHeight() - (localInsets.getTop() + localInsets.getBottom());
  }
  
  protected void checkMarkers(Gauge paramGauge, double paramDouble1, double paramDouble2)
  {
    if ((paramGauge.isMarkersVisible()) && (!paramGauge.getMarkers().isEmpty()))
    {
      Iterator localIterator = paramGauge.getMarkers().iterator();
      while (localIterator.hasNext())
      {
        Marker localMarker = (Marker)localIterator.next();
        if ((paramDouble1 < localMarker.getValue()) && (paramDouble2 > localMarker.getValue())) {
          localMarker.fireMarkerEvent(new MarkerEvent(paramGauge, null, Type.OVER_RUN));
        } else if ((paramDouble1 > localMarker.getValue()) && (paramDouble2 < localMarker.getValue())) {
          localMarker.fireMarkerEvent(new MarkerEvent(paramGauge, null, Type.UNDER_RUN));
        }
      }
    }
  }
  
  protected long getBlinkInterval()
  {
    return this.blinkInterval;
  }
  
  protected void setBlinkInterval(long paramLong)
  {
    this.blinkInterval = (paramLong > 2000000000L ? 2000000000L : paramLong < 50000000L ? 50000000L : paramLong);
  }
  
  protected void drawCircularFrame(Gauge paramGauge, Group paramGroup, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    double d2 = d1;
    double d3 = d1;
    paramGroup.getChildren().clear();
    Point2D localPoint2D = new Point2D(0.5D * d2, 0.5D * d3);
    Circle localCircle1 = new Circle(0.5D * d2, 0.5D * d3, d2 * 0.4158878326D);
    localCircle1.setFill(Color.TRANSPARENT);
    Shape localShape1 = Shape.subtract(new Circle(0.5D * d2, 0.5D * d3, 0.5D * d2), localCircle1);
    localShape1.setFill(Color.color(0.5176470588D, 0.5176470588D, 0.5176470588D, 1.0D));
    localShape1.setStroke(null);
    paramGroup.getChildren().add(localShape1);
    Shape localShape2 = Shape.subtract(new Circle(0.5D * d2, 0.5D * d3, 0.4205607476635514D * d2), localCircle1);
    localShape2.setFill(Color.color(0.6D, 0.6D, 0.6D, 0.8D));
    localShape2.setStroke(null);
    Circle localCircle2 = new Circle(localPoint2D.getX(), localPoint2D.getY(), 0.4953271028037383D * d2);
    localCircle2.setStroke(null);
    switch (paramGauge.getFrameDesign())
    {
    case BLACK_METAL: 
      ConicalGradient localConicalGradient1 = new ConicalGradient(new Point2D(localCircle2.getLayoutBounds().getWidth() / 2.0D, localCircle2.getLayoutBounds().getHeight() / 2.0D), new Stop[] { new Stop(0.0D, Color.rgb(254, 254, 254)), new Stop(0.125D, Color.rgb(0, 0, 0)), new Stop(0.3472D, Color.rgb(153, 153, 153)), new Stop(0.5D, Color.rgb(0, 0, 0)), new Stop(0.6805D, Color.rgb(153, 153, 153)), new Stop(0.875D, Color.rgb(0, 0, 0)), new Stop(1.0D, Color.rgb(254, 254, 254)) });
      localCircle2.setFill(localConicalGradient1.apply(localCircle2));
      localCircle2.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localCircle2, localShape2 });
      break;
    case SHINY_METAL: 
      ConicalGradient localConicalGradient2 = new ConicalGradient(new Point2D(localCircle2.getLayoutBounds().getWidth() / 2.0D, localCircle2.getLayoutBounds().getHeight() / 2.0D), new Stop[] { new Stop(0.0D, Color.rgb(254, 254, 254)), new Stop(0.125D, Util.darker(paramGauge.getFrameBaseColor(), 0.15D)), new Stop(0.25D, paramGauge.getFrameBaseColor().darker()), new Stop(0.3472D, paramGauge.getFrameBaseColor().brighter()), new Stop(0.5D, paramGauge.getFrameBaseColor().darker().darker()), new Stop(0.6527D, paramGauge.getFrameBaseColor().brighter()), new Stop(0.75D, paramGauge.getFrameBaseColor().darker()), new Stop(0.875D, Util.darker(paramGauge.getFrameBaseColor(), 0.15D)), new Stop(1.0D, Color.rgb(254, 254, 254)) });
      localCircle2.setFill(localConicalGradient2.apply(localCircle2));
      localCircle2.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localCircle2, localShape2 });
      break;
    case CHROME: 
      ConicalGradient localConicalGradient3 = new ConicalGradient(new Point2D(localCircle2.getLayoutBounds().getWidth() / 2.0D, localCircle2.getLayoutBounds().getHeight() / 2.0D), new Stop[] { new Stop(0.0D, Color.WHITE), new Stop(0.09D, Color.WHITE), new Stop(0.12D, Color.rgb(136, 136, 138)), new Stop(0.16D, Color.rgb(164, 185, 190)), new Stop(0.25D, Color.rgb(158, 179, 182)), new Stop(0.29D, Color.rgb(112, 112, 112)), new Stop(0.33D, Color.rgb(221, 227, 227)), new Stop(0.38D, Color.rgb(155, 176, 179)), new Stop(0.48D, Color.rgb(156, 176, 177)), new Stop(0.52D, Color.rgb(254, 255, 255)), new Stop(0.63D, Color.WHITE), new Stop(0.68D, Color.rgb(156, 180, 180)), new Stop(0.8D, Color.rgb(198, 209, 211)), new Stop(0.83D, Color.rgb(246, 248, 247)), new Stop(0.87D, Color.rgb(204, 216, 216)), new Stop(0.97D, Color.rgb(164, 188, 190)), new Stop(1.0D, Color.WHITE) });
      localCircle2.setFill(localConicalGradient3.apply(localCircle2));
      localCircle2.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localCircle2, localShape2 });
      break;
    case GLOSSY_METAL: 
      localCircle2.setFill(new RadialGradient(0.0D, 0.0D, localPoint2D.getX(), localPoint2D.getY(), 0.5D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.8117647059D, 0.8117647059D, 0.8117647059D, 1.0D)), new Stop(0.96D, Color.color(0.8039215686D, 0.8D, 0.8039215686D, 1.0D)), new Stop(1.0D, Color.color(0.9568627451D, 0.9568627451D, 0.9568627451D, 1.0D)) }));
      Circle localCircle3 = new Circle(localPoint2D.getX(), localPoint2D.getY(), 0.4859813084D * d2);
      localCircle3.setFill(new LinearGradient(0.0D, localCircle3.getLayoutBounds().getMinY(), 0.0D, localCircle3.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.9764705882D, 0.9764705882D, 0.9764705882D, 1.0D)), new Stop(0.23D, Color.color(0.7843137255D, 0.7647058824D, 0.7490196078D, 1.0D)), new Stop(0.36D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(0.59D, Color.color(0.1137254902D, 0.1137254902D, 0.1137254902D, 1.0D)), new Stop(0.76D, Color.color(0.7843137255D, 0.7607843137D, 0.7529411765D, 1.0D)), new Stop(1.0D, Color.color(0.8196078431D, 0.8196078431D, 0.8196078431D, 1.0D)) }));
      Circle localCircle4 = new Circle(localPoint2D.getX(), localPoint2D.getY(), 0.4345794393D * d2);
      localCircle4.setFill(Color.web("#F6F6F6"));
      Circle localCircle5 = new Circle(localPoint2D.getX(), localPoint2D.getY(), 0.4252336449D * d2);
      localCircle5.setFill(Color.web("#333333"));
      paramGroup.getChildren().addAll(new Node[] { localCircle2, localCircle3, localCircle4, localCircle5 });
      break;
    case DARK_GLOSSY: 
      localCircle2.setFill(new LinearGradient(0.8551401869158879D * d2, 0.14953271028037382D * d3, 0.15794611761513314D * d2, 0.8467267795811287D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.3254901961D, 0.3254901961D, 0.3254901961D, 1.0D)), new Stop(0.08D, Color.color(0.9960784314D, 0.9960784314D, 1.0D, 1.0D)), new Stop(0.52D, Color.color(0.0D, 0.0D, 0.0D, 1.0D)), new Stop(0.55D, Color.color(0.0196078431D, 0.0235294118D, 0.0196078431D, 1.0D)), new Stop(0.84D, Color.color(0.9725490196D, 0.9803921569D, 0.9764705882D, 1.0D)), new Stop(0.99D, Color.color(0.3254901961D, 0.3254901961D, 0.3254901961D, 1.0D)), new Stop(1.0D, Color.color(0.3254901961D, 0.3254901961D, 0.3254901961D, 1.0D)) }));
      Circle localCircle6 = new Circle(localPoint2D.getX(), localPoint2D.getY(), 0.48598130841121495D * d2);
      localCircle6.setFill(new LinearGradient(0.0D, 0.014018691588785047D * d3, 0.0D, 0.985981308411215D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.2588235294D, 0.2588235294D, 0.2588235294D, 1.0D)), new Stop(0.42D, Color.color(0.2588235294D, 0.2588235294D, 0.2588235294D, 1.0D)), new Stop(1.0D, Color.color(0.0509803922D, 0.0509803922D, 0.0509803922D, 1.0D)) }));
      localCircle6.setStroke(null);
      Path localPath = new Path();
      localPath.setFillRule(FillRule.EVEN_ODD);
      localPath.getElements().add(new MoveTo(0.014018691588785047D * d2, 0.5D * d3));
      localPath.getElements().add(new CubicCurveTo(0.014018691588785047D * d2, 0.514018691588785D * d3, 0.014018691588785047D * d2, 0.5233644859813084D * d3, 0.014018691588785047D * d2, 0.5373831775700935D * d3));
      localPath.getElements().add(new CubicCurveTo(0.07009345794392523D * d2, 0.37383177570093457D * d3, 0.26635514018691586D * d2, 0.2570093457943925D * d3, 0.5D * d2, 0.2570093457943925D * d3));
      localPath.getElements().add(new CubicCurveTo(0.7336448598130841D * d2, 0.2570093457943925D * d3, 0.9299065420560748D * d2, 0.37383177570093457D * d3, 0.985981308411215D * d2, 0.5373831775700935D * d3));
      localPath.getElements().add(new CubicCurveTo(0.985981308411215D * d2, 0.5233644859813084D * d3, 0.985981308411215D * d2, 0.514018691588785D * d3, 0.985981308411215D * d2, 0.5D * d3));
      localPath.getElements().add(new CubicCurveTo(0.985981308411215D * d2, 0.2336448598130841D * d3, 0.7663551401869159D * d2, 0.014018691588785047D * d3, 0.5D * d2, 0.014018691588785047D * d3));
      localPath.getElements().add(new CubicCurveTo(0.2336448598130841D * d2, 0.014018691588785047D * d3, 0.014018691588785047D * d2, 0.2336448598130841D * d3, 0.014018691588785047D * d2, 0.5D * d3));
      localPath.getElements().add(new ClosePath());
      localPath.setFill(new LinearGradient(0.0D, 0.014018691588785047D * d3, 0.0D, 0.5280373831775701D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(0.26D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(0.26009998D, Color.color(1.0D, 1.0D, 1.0D, 1.0D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
      localPath.setStroke(null);
      Circle localCircle7 = new Circle(localPoint2D.getX(), localPoint2D.getY(), 0.4392523364485981D * d2);
      localCircle7.setFill(new LinearGradient(0.8037383177570093D * d2, 0.1822429906542056D * d3, 0.18584594354259637D * d2, 0.8001353648686187D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.6745098039D, 0.6745098039D, 0.6784313725D, 1.0D)), new Stop(0.08D, Color.color(0.9960784314D, 0.9960784314D, 1.0D, 1.0D)), new Stop(0.52D, Color.color(0.0D, 0.0D, 0.0D, 1.0D)), new Stop(0.55D, Color.color(0.0196078431D, 0.0235294118D, 0.0196078431D, 1.0D)), new Stop(0.91D, Color.color(0.9725490196D, 0.9803921569D, 0.9764705882D, 1.0D)), new Stop(0.99D, Color.color(0.6980392157D, 0.6980392157D, 0.6980392157D, 1.0D)), new Stop(1.0D, Color.color(0.6980392157D, 0.6980392157D, 0.6980392157D, 1.0D)) }));
      localCircle7.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localCircle2, localCircle6, localPath, localCircle7 });
      break;
    default: 
      localCircle2.getStyleClass().add(paramGauge.getFrameDesign().CSS);
      localCircle2.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localCircle2, localShape2 });
    }
    paramGroup.setCache(true);
  }
  
  protected void drawCircularBackground(Gauge paramGauge, Group paramGroup, Rectangle paramRectangle)
  {
    double d = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    paramGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d, d);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    paramGroup.getChildren().add(localRectangle);
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setWidth(0.2D * d);
    localInnerShadow.setHeight(0.2D * d);
    localInnerShadow.setOffsetY(0.03D * d);
    localInnerShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.7D));
    localInnerShadow.setBlurType(BlurType.GAUSSIAN);
    Circle localCircle1 = new Circle(0.5D * d, 0.5D * d, 0.4158878504672897D * d);
    localCircle1.setStroke(null);
    switch (paramGauge.getBackgroundDesign())
    {
    case STAINLESS: 
      Color localColor1 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      Color localColor2 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#E2E2E2").getBrightness());
      Color localColor3 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#B2B2B4").getBrightness());
      Color localColor4 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#ACACAE").getBrightness());
      Color localColor5 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      Color localColor6 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#6E6E70").getBrightness());
      Color localColor7 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#6E6E70").getBrightness());
      Color localColor8 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      Color localColor9 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#6E6E70").getBrightness());
      Color localColor10 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#6E6E70").getBrightness());
      Color localColor11 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      Color localColor12 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#ACACAE").getBrightness());
      Color localColor13 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#B2B2B4").getBrightness());
      Color localColor14 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#E2E2E2").getBrightness());
      Color localColor15 = Color.hsb(paramGauge.getTextureColor().getHue(), paramGauge.getTextureColor().getSaturation(), Color.web("#FDFDFD").getBrightness());
      ConicalGradient localConicalGradient = new ConicalGradient(new Point2D(d / 2.0D, d / 2.0D), new Stop[] { new Stop(0.0D, localColor1), new Stop(0.03D, localColor2), new Stop(0.1D, localColor3), new Stop(0.14D, localColor4), new Stop(0.24D, localColor5), new Stop(0.33D, localColor6), new Stop(0.38D, localColor7), new Stop(0.5D, localColor8), new Stop(0.62D, localColor9), new Stop(0.67D, localColor10), new Stop(0.76D, localColor11), new Stop(0.81D, localColor12), new Stop(0.85D, localColor13), new Stop(0.97D, localColor14), new Stop(1.0D, localColor15) });
      localCircle1.setFill(localConicalGradient.apply(localCircle1));
      localCircle1.setEffect(localInnerShadow);
      paramGroup.getChildren().addAll(new Node[] { localCircle1 });
      break;
    case CARBON: 
      localCircle1.setFill(Util.createCarbonPattern());
      localCircle1.setStroke(null);
      Circle localCircle2 = new Circle(0.5D * d, 0.5D * d, 0.4158878504672897D * d);
      localCircle2.setFill(new LinearGradient(localCircle2.getLayoutBounds().getMinX(), 0.0D, localCircle2.getLayoutBounds().getMaxX(), 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 0.5D)), new Stop(0.4D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.6D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(1.0D, Color.color(0.0D, 0.0D, 0.0D, 0.5D)) }));
      localCircle2.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localCircle1, localCircle2 });
      break;
    case PUNCHED_SHEET: 
      localCircle1.setFill(Util.createPunchedSheetPattern(paramGauge.getTextureColor()));
      localCircle1.setStroke(null);
      Circle localCircle3 = new Circle(0.5D * d, 0.5D * d, 0.4158878504672897D * d);
      localCircle3.setFill(new LinearGradient(localCircle3.getLayoutBounds().getMinX(), 0.0D, localCircle3.getLayoutBounds().getMaxX(), 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 0.5D)), new Stop(0.4D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.6D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(1.0D, Color.color(0.0D, 0.0D, 0.0D, 0.5D)) }));
      localCircle3.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localCircle1, localCircle3 });
      break;
    case NOISY_PLASTIC: 
      Circle localCircle4 = new Circle(0.5D * d, 0.5D * d, 0.4158878504672897D * d);
      localCircle4.setFill(new LinearGradient(0.0D, localCircle4.getLayoutY(), 0.0D, localCircle4.getLayoutBounds().getHeight(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Util.brighter(paramGauge.getTextureColor(), 0.15D)), new Stop(1.0D, Util.darker(paramGauge.getTextureColor(), 0.15D)) }));
      localCircle4.setStroke(null);
      localCircle4.setEffect(localInnerShadow);
      localCircle1.setFill(Util.applyNoisyBackground(localCircle1, paramGauge.getTextureColor()));
      paramGroup.getChildren().addAll(new Node[] { localCircle4, localCircle1 });
      break;
    case BRUSHED_METAL: 
      localCircle1.setFill(Util.applyBrushedMetalBackground(localCircle1, paramGauge.getTextureColor()));
      localCircle1.setEffect(localInnerShadow);
      paramGroup.getChildren().addAll(new Node[] { localCircle1 });
      break;
    default: 
      localCircle1.setStyle(paramGauge.getSimpleGradientBaseColorString());
      localCircle1.getStyleClass().add(paramGauge.getBackgroundDesign().CSS_BACKGROUND);
      localCircle1.setEffect(localInnerShadow);
      paramGroup.getChildren().addAll(new Node[] { localCircle1 });
    }
    paramGroup.setCache(true);
  }
  
  protected void drawCircularTrend(Gauge paramGauge, Group paramGroup, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth();
    double d2 = paramRectangle.getHeight();
    paramGroup.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    paramGroup.getChildren().add(localRectangle1);
    double d3 = 0.455D * d1;
    double d4 = 0.79D * d1;
    switch (paramGauge.getRadialRange())
    {
    case RADIAL_300: 
      d3 = 0.455D * d1;
      d4 = 0.79D * d1;
      break;
    case RADIAL_270: 
      d3 = 0.6D * d1;
      d4 = 0.72D * d2;
      break;
    case RADIAL_180: 
      d3 = 0.455D * d1;
      d4 = 0.79D * d1;
      break;
    case RADIAL_180N: 
      d3 = 0.6D * d1;
      d4 = 0.45D * d1;
      break;
    case RADIAL_180S: 
      d3 = 0.6D * d1;
      d4 = 0.11D * d1;
      break;
    case RADIAL_90: 
      d3 = 0.455D * d1;
      d4 = 0.79D * d1;
      break;
    case RADIAL_90N: 
      d3 = 0.6D * d1;
      d4 = 0.72D * d1;
      break;
    case RADIAL_90S: 
      d3 = 0.6D * d1;
      d4 = 0.2D * d1;
    }
    Rectangle localRectangle2 = new Rectangle(d3 - 1.0D, d4 - 1.0D, 0.1D * d1, 0.1D * d1);
    localRectangle2.setFill(new LinearGradient(0.0D, d4 - 1.0D, 0.0D, d4 - 1.0D + localRectangle2.getLayoutBounds().getHeight(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.1D, 0.1D, 0.1D, 1.0D)), new Stop(0.1D, Color.color(0.3D, 0.3D, 0.3D, 1.0D)), new Stop(0.93D, Color.color(0.3D, 0.3D, 0.3D, 1.0D)), new Stop(1.0D, Color.color(0.86D, 0.86D, 0.86D, 1.0D)) }));
    localRectangle2.setStroke(null);
    paramGroup.getChildren().add(localRectangle2);
    Rectangle localRectangle3 = new Rectangle(localRectangle2.getX() + 1.0D, localRectangle2.getY() + 1.0D, localRectangle2.getWidth() - 2.0D, localRectangle2.getHeight() - 2.0D);
    localRectangle3.setFill(new LinearGradient(0.0D, d4, 0.0D, d4 + localRectangle2.getLayoutBounds().getHeight(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.3D, 0.3D, 0.3D, 1.0D)), new Stop(1.0D, Color.color(0.1D, 0.1D, 0.1D, 1.0D)) }));
    localRectangle3.setStroke(null);
    Group localGroup = createTrendIndicator(paramGauge, d1 * 0.09D);
    localGroup.setTranslateX(d3 + 1.0D);
    localGroup.setTranslateY(d4 + 1.0D);
    paramGroup.getChildren().addAll(new Node[] { localRectangle3, localGroup });
    paramGroup.setCache(true);
  }
  
  protected void drawCircularSections(Gauge paramGauge, Group paramGroup, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    double d2 = d1;
    double d3 = d1;
    paramGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    paramGroup.getChildren().addAll(new Node[] { localRectangle });
    Iterator localIterator = paramGauge.getSections().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      Shape localShape = localSection.getSectionArea();
      localShape.setFill(localSection.getTransparentColor());
      localShape.setStroke(null);
      paramGroup.getChildren().add(localShape);
    }
  }
  
  protected void drawCircularAreas(Gauge paramGauge, Group paramGroup, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    double d2 = d1;
    double d3 = d1;
    paramGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    paramGroup.getChildren().add(localRectangle);
    Iterator localIterator = paramGauge.getAreas().iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      Shape localShape = localSection.getFilledArea();
      localShape.setFill(localSection.getTransparentColor());
      localShape.setStroke(null);
      paramGroup.getChildren().add(localShape);
    }
  }
  
  protected void drawCircularGlowOff(Group paramGroup, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    double d2 = d1;
    double d3 = d1;
    paramGroup.getChildren().clear();
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.10747663551401869D * d2, 0.5D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.10747663551401869D * d2, 0.2850467289719626D * d3, 0.2850467289719626D * d2, 0.10747663551401869D * d3, 0.5D * d2, 0.10747663551401869D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.7149532710280374D * d2, 0.10747663551401869D * d3, 0.8925233644859814D * d2, 0.2850467289719626D * d3, 0.8925233644859814D * d2, 0.5D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.8925233644859814D * d2, 0.7149532710280374D * d3, 0.7149532710280374D * d2, 0.8925233644859814D * d3, 0.5D * d2, 0.8925233644859814D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.2850467289719626D * d2, 0.8925233644859814D * d3, 0.10747663551401869D * d2, 0.7149532710280374D * d3, 0.10747663551401869D * d2, 0.5D * d3));
    localPath1.getElements().add(new ClosePath());
    localPath1.getElements().add(new MoveTo(0.08411214953271028D * d2, 0.5D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.08411214953271028D * d2, 0.7289719626168224D * d3, 0.27102803738317754D * d2, 0.9158878504672897D * d3, 0.5D * d2, 0.9158878504672897D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.7289719626168224D * d2, 0.9158878504672897D * d3, 0.9158878504672897D * d2, 0.7289719626168224D * d3, 0.9158878504672897D * d2, 0.5D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.9158878504672897D * d2, 0.27102803738317754D * d3, 0.7289719626168224D * d2, 0.08411214953271028D * d3, 0.5D * d2, 0.08411214953271028D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.27102803738317754D * d2, 0.08411214953271028D * d3, 0.08411214953271028D * d2, 0.27102803738317754D * d3, 0.08411214953271028D * d2, 0.5D * d3));
    localPath1.getElements().add(new ClosePath());
    LinearGradient localLinearGradient = new LinearGradient(0.5D * d2, 0.08411214953271028D * d3, 0.5D * d2, 0.9112149532710281D * d3, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.8D, 0.8D, 0.8D, 0.4D)), new Stop(0.17D, Color.color(0.6D, 0.6D, 0.6D, 0.4D)), new Stop(0.33D, Color.color(0.9882352941D, 0.9882352941D, 0.9882352941D, 0.4D)), new Stop(0.34D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)), new Stop(0.63D, Color.color(0.8D, 0.8D, 0.8D, 0.4D)), new Stop(0.64D, Color.color(0.7960784314D, 0.7960784314D, 0.7960784314D, 0.4D)), new Stop(0.83D, Color.color(0.6D, 0.6D, 0.6D, 0.4D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)) });
    localPath1.setFill(localLinearGradient);
    localPath1.setStroke(null);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.8598130841121495D * d2, 0.6588785046728972D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.794392523364486D * d2, 0.8037383177570093D * d3, 0.6588785046728972D * d2, 0.8925233644859814D * d3, 0.5D * d2, 0.8925233644859814D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.5D * d2, 0.8925233644859814D * d3, 0.5D * d2, 0.9158878504672897D * d3, 0.5D * d2, 0.9158878504672897D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.6682242990654206D * d2, 0.9158878504672897D * d3, 0.8084112149532711D * d2, 0.822429906542056D * d3, 0.8785046728971962D * d2, 0.6682242990654206D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.8785046728971962D * d2, 0.6682242990654206D * d3, 0.8598130841121495D * d2, 0.6588785046728972D * d3, 0.8598130841121495D * d2, 0.6588785046728972D * d3));
    localPath2.getElements().add(new ClosePath());
    localPath2.setFill(new RadialGradient(0.0D, 0.0D, 0.7336448598130841D * d2, 0.8364485981308412D * d3, 0.23598130841121495D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.5490196078D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
    localPath2.setStroke(null);
    Path localPath3 = new Path();
    localPath3.setFillRule(FillRule.EVEN_ODD);
    localPath3.getElements().add(new MoveTo(0.14018691588785046D * d2, 0.3411214953271028D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.205607476635514D * d2, 0.19626168224299065D * d3, 0.3411214953271028D * d2, 0.10747663551401869D * d3, 0.5D * d2, 0.10747663551401869D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.5D * d2, 0.10747663551401869D * d3, 0.5D * d2, 0.08411214953271028D * d3, 0.5D * d2, 0.08411214953271028D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.3317757009345794D * d2, 0.08411214953271028D * d3, 0.18691588785046728D * d2, 0.17757009345794392D * d3, 0.12149532710280374D * d2, 0.3317757009345794D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.12149532710280374D * d2, 0.3317757009345794D * d3, 0.14018691588785046D * d2, 0.3411214953271028D * d3, 0.14018691588785046D * d2, 0.3411214953271028D * d3));
    localPath3.getElements().add(new ClosePath());
    localPath3.setFill(new RadialGradient(0.0D, 0.0D, 0.26635514018691586D * d2, 0.16355140186915887D * d3, 0.23598130841121495D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
    localPath3.setStroke(null);
    paramGroup.getChildren().addAll(new Node[] { localPath1, localPath2, localPath3 });
    paramGroup.setCache(true);
  }
  
  protected void drawCircularGlowOn(Gauge paramGauge, Group paramGroup, ArrayList<Color> paramArrayList, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    double d2 = d1;
    double d3 = d1;
    paramGroup.getChildren().clear();
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.10747663551401869D * d2, 0.5D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.10747663551401869D * d2, 0.2850467289719626D * d3, 0.2850467289719626D * d2, 0.10747663551401869D * d3, 0.5D * d2, 0.10747663551401869D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.7149532710280374D * d2, 0.10747663551401869D * d3, 0.8925233644859814D * d2, 0.2850467289719626D * d3, 0.8925233644859814D * d2, 0.5D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.8925233644859814D * d2, 0.7149532710280374D * d3, 0.7149532710280374D * d2, 0.8925233644859814D * d3, 0.5D * d2, 0.8925233644859814D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.2850467289719626D * d2, 0.8925233644859814D * d3, 0.10747663551401869D * d2, 0.7149532710280374D * d3, 0.10747663551401869D * d2, 0.5D * d3));
    localPath1.getElements().add(new ClosePath());
    localPath1.getElements().add(new MoveTo(0.08411214953271028D * d2, 0.5D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.08411214953271028D * d2, 0.7289719626168224D * d3, 0.27102803738317754D * d2, 0.9158878504672897D * d3, 0.5D * d2, 0.9158878504672897D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.7289719626168224D * d2, 0.9158878504672897D * d3, 0.9158878504672897D * d2, 0.7289719626168224D * d3, 0.9158878504672897D * d2, 0.5D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.9158878504672897D * d2, 0.27102803738317754D * d3, 0.7289719626168224D * d2, 0.08411214953271028D * d3, 0.5D * d2, 0.08411214953271028D * d3));
    localPath1.getElements().add(new CubicCurveTo(0.27102803738317754D * d2, 0.08411214953271028D * d3, 0.08411214953271028D * d2, 0.27102803738317754D * d3, 0.08411214953271028D * d2, 0.5D * d3));
    localPath1.getElements().add(new ClosePath());
    RadialGradient localRadialGradient = new RadialGradient(0.0D, 0.0D, 0.5D * d2, 0.5D * d3, 0.4158878504672897D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, (Color)paramArrayList.get(0)), new Stop(0.91D, (Color)paramArrayList.get(1)), new Stop(0.96D, (Color)paramArrayList.get(2)), new Stop(1.0D, (Color)paramArrayList.get(3)) });
    localPath1.setFill(localRadialGradient);
    localPath1.setStroke(null);
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setRadius(0.15D * d2);
    localDropShadow.setBlurType(BlurType.GAUSSIAN);
    if (localDropShadow.colorProperty().isBound()) {
      localDropShadow.colorProperty().unbind();
    }
    localDropShadow.colorProperty().bind(paramGauge.glowColorProperty());
    localPath1.setEffect(localDropShadow);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.8598130841121495D * d2, 0.6588785046728972D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.794392523364486D * d2, 0.8037383177570093D * d3, 0.6588785046728972D * d2, 0.8925233644859814D * d3, 0.5D * d2, 0.8925233644859814D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.5D * d2, 0.8925233644859814D * d3, 0.5D * d2, 0.9158878504672897D * d3, 0.5D * d2, 0.9158878504672897D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.6682242990654206D * d2, 0.9158878504672897D * d3, 0.8084112149532711D * d2, 0.822429906542056D * d3, 0.8785046728971962D * d2, 0.6682242990654206D * d3));
    localPath2.getElements().add(new CubicCurveTo(0.8785046728971962D * d2, 0.6682242990654206D * d3, 0.8598130841121495D * d2, 0.6588785046728972D * d3, 0.8598130841121495D * d2, 0.6588785046728972D * d3));
    localPath2.getElements().add(new ClosePath());
    localPath2.setFill(new RadialGradient(0.0D, 0.0D, 0.7336448598130841D * d2, 0.8364485981308412D * d3, 0.23598130841121495D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.5490196078D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
    localPath2.setStroke(null);
    Path localPath3 = new Path();
    localPath3.setFillRule(FillRule.EVEN_ODD);
    localPath3.getElements().add(new MoveTo(0.14018691588785046D * d2, 0.3411214953271028D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.205607476635514D * d2, 0.19626168224299065D * d3, 0.3411214953271028D * d2, 0.10747663551401869D * d3, 0.5D * d2, 0.10747663551401869D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.5D * d2, 0.10747663551401869D * d3, 0.5D * d2, 0.08411214953271028D * d3, 0.5D * d2, 0.08411214953271028D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.3317757009345794D * d2, 0.08411214953271028D * d3, 0.18691588785046728D * d2, 0.17757009345794392D * d3, 0.12149532710280374D * d2, 0.3317757009345794D * d3));
    localPath3.getElements().add(new CubicCurveTo(0.12149532710280374D * d2, 0.3317757009345794D * d3, 0.14018691588785046D * d2, 0.3411214953271028D * d3, 0.14018691588785046D * d2, 0.3411214953271028D * d3));
    localPath3.getElements().add(new ClosePath());
    localPath3.setFill(new RadialGradient(0.0D, 0.0D, 0.26635514018691586D * d2, 0.16355140186915887D * d3, 0.23598130841121495D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.4D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
    localPath3.setStroke(null);
    paramGroup.getChildren().addAll(new Node[] { localPath1, localPath2, localPath3 });
    paramGroup.setCache(true);
  }
  
  protected void drawCircularIndicators(Gauge paramGauge, Group paramGroup, Point2D paramPoint2D, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    double d2 = d1;
    double d3 = d1;
    paramGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    paramGroup.getChildren().add(localRectangle);
    paramGroup.getTransforms().clear();
    paramGroup.getTransforms().add(Transform.rotate(paramGauge.getRadialRange().ROTATION_OFFSET, paramPoint2D.getX(), paramPoint2D.getY()));
    paramGroup.getTransforms().add(Transform.rotate(-paramGauge.getMinValue() * paramGauge.getAngleStep(), paramPoint2D.getX(), paramPoint2D.getY()));
    Iterator localIterator = paramGauge.getMarkers().iterator();
    while (localIterator.hasNext())
    {
      Marker localMarker = (Marker)localIterator.next();
      if ((Double.compare(localMarker.getValue(), paramGauge.getMinValue()) >= 0) && (Double.compare(localMarker.getValue(), paramGauge.getMaxValue()) <= 0))
      {
        Group localGroup = createIndicator(d1, localMarker, new Point2D(d1 * 0.4813084112D, d1 * 0.0841121495D));
        localGroup.getTransforms().add(Transform.rotate(localMarker.getValue() * paramGauge.getAngleStep(), paramPoint2D.getX(), paramPoint2D.getY()));
        paramGroup.getChildren().add(localGroup);
      }
    }
  }
  
  protected void drawCircularKnobs(Gauge paramGauge, Group paramGroup, Point2D paramPoint2D, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    double d2 = paramRectangle.getWidth();
    double d3 = paramRectangle.getHeight();
    paramGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    paramGroup.getChildren().add(localRectangle);
    double d4;
    Group localGroup1;
    switch (paramGauge.getKnobDesign())
    {
    case BIG: 
      d4 = Math.ceil(d2 * 0.1214953271D);
      localGroup1 = createBigKnob(d4, paramGauge.getKnobColor());
      break;
    case METAL: 
      d4 = Math.ceil(d2 * 0.0841121495D);
      localGroup1 = createMetalKnob(d4, paramGauge.getKnobColor());
      break;
    case PLAIN: 
      d4 = Math.ceil(d2 * 0.0841121495D);
      localGroup1 = createPlainKnob(d4, paramGauge.getKnobColor());
      break;
    case STANDARD: 
    default: 
      d4 = Math.ceil(d2 * 0.0841121495D);
      localGroup1 = createStandardKnob(d4, paramGauge.getKnobColor());
    }
    Point2D localPoint2D1 = new Point2D(paramPoint2D.getX() - d4 / 2.0D, paramPoint2D.getY() - d4 / 2.0D);
    localGroup1.setTranslateX(localPoint2D1.getX());
    localGroup1.setTranslateY(localPoint2D1.getY());
    if ((paramGauge.isPointerGlowEnabled()) && (paramGauge.getPointerType() != PointerType.TYPE9))
    {
      localObject = new DropShadow();
      ((DropShadow)localObject).setWidth(0.1D * d1);
      ((DropShadow)localObject).setHeight(0.1D * d1);
      ((DropShadow)localObject).setOffsetX(0.0D);
      ((DropShadow)localObject).setOffsetY(0.0D);
      ((DropShadow)localObject).setRadius(0.1D * d1);
      ((DropShadow)localObject).setColor(paramGauge.getValueColor().COLOR);
      ((DropShadow)localObject).setBlurType(BlurType.GAUSSIAN);
      localGroup1.setEffect((Effect)localObject);
    }
    Object localObject = createStandardKnob(Math.ceil(d2 * 0.03738316893577576D), paramGauge.getKnobColor());
    Group localGroup2 = createStandardKnob(Math.ceil(d2 * 0.03738316893577576D), paramGauge.getKnobColor());
    Point2D localPoint2D2;
    Point2D localPoint2D3;
    switch (paramGauge.getRadialRange())
    {
    case RADIAL_90N: 
      localPoint2D2 = new Point2D(0.12D, 0.4D);
      localPoint2D3 = new Point2D(0.845D, 0.4D);
      break;
    case RADIAL_90W: 
      localPoint2D2 = new Point2D(0.4D, 0.845D);
      localPoint2D3 = new Point2D(0.4D, 0.12D);
      break;
    case RADIAL_90S: 
      localPoint2D2 = new Point2D(0.12D, 0.56D);
      localPoint2D3 = new Point2D(0.845D, 0.56D);
      break;
    case RADIAL_90E: 
      localPoint2D2 = new Point2D(0.56D, 0.845D);
      localPoint2D3 = new Point2D(0.56D, 0.12D);
      break;
    case RADIAL_90: 
      localPoint2D2 = new Point2D(0.13084112107753754D, 0.514018714427948D);
      localPoint2D3 = new Point2D(0.5233644843101501D, 0.13084112107753754D);
      break;
    case RADIAL_180: 
      localPoint2D2 = new Point2D(0.13084112107753754D, 0.514018714427948D);
      localPoint2D3 = new Point2D(0.8317757248878479D, 0.514018714427948D);
      break;
    case RADIAL_180N: 
      localPoint2D2 = new Point2D(0.13084112107753754D, 0.514018714427948D);
      localPoint2D3 = new Point2D(0.8317757248878479D, 0.514018714427948D);
      break;
    case RADIAL_180S: 
      localPoint2D2 = new Point2D(0.13084112107753754D, 0.1D);
      localPoint2D3 = new Point2D(0.8317757248878479D, 0.1D);
      break;
    case RADIAL_270: 
      localPoint2D2 = new Point2D(0.5233644843101501D, 0.8317757248878479D);
      localPoint2D3 = new Point2D(0.8317757248878479D, 0.514018714427948D);
      break;
    case RADIAL_300: 
    default: 
      localPoint2D2 = new Point2D(0.336448609828949D, 0.8037382960319519D);
      localPoint2D3 = new Point2D(0.6261682510375977D, 0.8037382960319519D);
    }
    ((Group)localObject).setTranslateX(d2 * localPoint2D2.getX());
    ((Group)localObject).setTranslateY(d2 * localPoint2D2.getY());
    localGroup2.setTranslateX(d2 * localPoint2D3.getX());
    localGroup2.setTranslateY(d2 * localPoint2D3.getY());
    if (((Group)localObject).visibleProperty().isBound()) {
      ((Group)localObject).visibleProperty().unbind();
    }
    ((Group)localObject).visibleProperty().bind(paramGauge.knobsVisibleProperty());
    if (localGroup2.visibleProperty().isBound()) {
      localGroup2.visibleProperty().unbind();
    }
    localGroup2.visibleProperty().bind(paramGauge.knobsVisibleProperty());
    paramGroup.getChildren().addAll(new Node[] { localGroup1, localObject, localGroup2 });
  }
  
  protected void drawCircularLed(Gauge paramGauge, Group paramGroup1, Group paramGroup2, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth();
    double d2 = paramRectangle.getHeight();
    paramGroup1.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    Group localGroup1 = createLed(d1 * 0.1D, paramGauge.getLedColor(), false);
    localGroup1.setLayoutX(d1 * paramGauge.getLedPosition().getX());
    localGroup1.setLayoutY(d1 * paramGauge.getLedPosition().getY());
    paramGroup1.getChildren().addAll(new Node[] { localRectangle1, localGroup1 });
    paramGroup1.setCache(true);
    paramGroup2.getChildren().clear();
    Rectangle localRectangle2 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle2.setOpacity(0.0D);
    localRectangle2.setStroke(null);
    Group localGroup2 = createLed(d1 * 0.1D, paramGauge.getLedColor(), true);
    localGroup2.setLayoutX(d1 * paramGauge.getLedPosition().getX());
    localGroup2.setLayoutY(d1 * paramGauge.getLedPosition().getY());
    paramGroup2.getChildren().addAll(new Node[] { localRectangle2, localGroup2 });
    paramGroup2.setCache(true);
  }
  
  protected void drawCircularUserLed(Gauge paramGauge, Group paramGroup1, Group paramGroup2, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth();
    double d2 = paramRectangle.getHeight();
    paramGroup1.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    Group localGroup1 = createLed(d1 * 0.1D, paramGauge.getUserLedColor(), false);
    localGroup1.setLayoutX(d1 * paramGauge.getUserLedPosition().getX());
    localGroup1.setLayoutY(d1 * paramGauge.getUserLedPosition().getY());
    paramGroup1.getChildren().addAll(new Node[] { localRectangle1, localGroup1 });
    paramGroup2.getChildren().clear();
    Rectangle localRectangle2 = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle2.setOpacity(0.0D);
    localRectangle2.setStroke(null);
    Group localGroup2 = createLed(d1 * 0.1D, paramGauge.getUserLedColor(), true);
    localGroup2.setLayoutX(d1 * paramGauge.getUserLedPosition().getX());
    localGroup2.setLayoutY(d1 * paramGauge.getUserLedPosition().getY());
    paramGroup2.getChildren().addAll(new Node[] { localRectangle2, localGroup2 });
  }
  
  protected void drawCircularLcd(Gauge paramGauge, Group paramGroup, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    paramGroup.getChildren().clear();
    Rectangle localRectangle1 = new Rectangle(0.0D, 0.0D, d1, d1);
    localRectangle1.setOpacity(0.0D);
    localRectangle1.setStroke(null);
    paramGroup.getChildren().add(localRectangle1);
    Rectangle localRectangle2 = new Rectangle((d1 - d1 * paramGauge.getRadialRange().LCD_FACTORS.getX()) / 2.0D, d1 * paramGauge.getRadialRange().LCD_FACTORS.getY(), d1 * paramGauge.getRadialRange().LCD_FACTORS.getWidth(), d1 * paramGauge.getRadialRange().LCD_FACTORS.getHeight());
    double d2 = localRectangle2.getWidth() > localRectangle2.getHeight() ? localRectangle2.getHeight() * 0.15D : localRectangle2.getWidth() * 0.15D;
    localRectangle2.arcWidthProperty().set(d2);
    localRectangle2.arcHeightProperty().set(d2);
    LinearGradient localLinearGradient = new LinearGradient(0.0D, localRectangle2.getLayoutBounds().getMinY(), 0.0D, localRectangle2.getLayoutBounds().getMaxY(), false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.1D, 0.1D, 0.1D, 1.0D)), new Stop(0.1D, Color.color(0.3D, 0.3D, 0.3D, 1.0D)), new Stop(0.93D, Color.color(0.3D, 0.3D, 0.3D, 1.0D)), new Stop(1.0D, Color.color(0.86D, 0.86D, 0.86D, 1.0D)) });
    localRectangle2.setFill(localLinearGradient);
    localRectangle2.setStroke(null);
    Rectangle localRectangle3 = new Rectangle(localRectangle2.getX() + 1.0D, localRectangle2.getY() + 1.0D, localRectangle2.getWidth() - 2.0D, localRectangle2.getHeight() - 2.0D);
    double d3 = localRectangle2.getArcWidth() - 1.0D;
    localRectangle3.setArcWidth(d3);
    localRectangle3.setArcHeight(d3);
    localRectangle3.getStyleClass().add("lcd");
    localRectangle3.getStyleClass().add(paramGauge.getLcdDesign().CSS);
    localRectangle3.getStyleClass().add("lcd-main");
    InnerShadow localInnerShadow1 = new InnerShadow();
    localInnerShadow1.setWidth(0.25D * localRectangle2.getHeight());
    localInnerShadow1.setHeight(0.25D * localRectangle2.getHeight());
    localInnerShadow1.setOffsetY(-0.05D * localRectangle2.getHeight());
    localInnerShadow1.setOffsetX(0.0D);
    localInnerShadow1.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.2D));
    InnerShadow localInnerShadow2 = new InnerShadow();
    localInnerShadow2.setInput(localInnerShadow1);
    localInnerShadow2.setWidth(0.15D * localRectangle2.getHeight());
    localInnerShadow2.setHeight(0.075D * localRectangle2.getHeight());
    localInnerShadow2.setOffsetY(0.025D * localRectangle2.getHeight());
    localInnerShadow2.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.65D));
    localRectangle3.setEffect(localInnerShadow2);
    if (!paramGauge.isLcdBackgroundVisible())
    {
      localRectangle2.setVisible(false);
      localRectangle3.setVisible(false);
    }
    paramGroup.getChildren().addAll(new Node[] { localRectangle2, localRectangle3 });
    paramGroup.setCache(true);
  }
  
  protected void drawCircularBargraph(Gauge paramGauge, Group paramGroup, int paramInt, ArrayList<Shape> paramArrayList, boolean paramBoolean1, boolean paramBoolean2, Point2D paramPoint2D, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth();
    double d2 = paramRectangle.getHeight();
    paramGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    paramGroup.getChildren().addAll(new Node[] { localRectangle });
    double d3;
    switch (paramGauge.getRadialRange())
    {
    case RADIAL_90: 
      d3 = 180.0D;
      break;
    case RADIAL_180: 
      d3 = 90.0D;
      break;
    case RADIAL_180N: 
      d3 = 90.0D;
      break;
    case RADIAL_180S: 
      d3 = -90.0D;
      break;
    case RADIAL_270: 
      d3 = 180.0D;
      break;
    case RADIAL_300: 
    default: 
      d3 = 90.0D;
    }
    paramArrayList.clear();
    for (int i = 0; i < paramInt; i++)
    {
      Shape localShape = createBargraphLed(paramRectangle, paramGauge, paramBoolean1);
      localShape.getTransforms().add(Transform.rotate(paramGauge.getRadialRange().SECTIONS_OFFSET - d3 - 2.5D - 5 * i, paramPoint2D.getX(), paramPoint2D.getY()));
      localShape.setVisible(paramBoolean2);
      paramArrayList.add(localShape);
      paramGroup.getChildren().add(localShape);
    }
    paramGroup.setCache(true);
  }
  
  protected void drawCircularForeground(Gauge paramGauge, Group paramGroup, Rectangle paramRectangle)
  {
    double d = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    paramGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d, d);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    paramGroup.getChildren().addAll(new Node[] { localRectangle });
    Path localPath1 = new Path();
    switch (paramGauge.getForegroundType())
    {
    case TYPE2: 
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.13551401869158877D * d, 0.6962616822429907D * d));
      localPath1.getElements().add(new CubicCurveTo(0.21495327102803738D * d, 0.5887850467289719D * d, 0.3177570093457944D * d, 0.5D * d, 0.46261682242990654D * d, 0.4252336448598131D * d));
      localPath1.getElements().add(new CubicCurveTo(0.6121495327102804D * d, 0.34579439252336447D * d, 0.7336448598130841D * d, 0.3177570093457944D * d, 0.8738317757009346D * d, 0.32242990654205606D * d));
      localPath1.getElements().add(new CubicCurveTo(0.7663551401869159D * d, 0.11214953271028037D * d, 0.5280373831775701D * d, 0.02336448598130841D * d, 0.3130841121495327D * d, 0.1308411214953271D * d));
      localPath1.getElements().add(new CubicCurveTo(0.09813084112149532D * d, 0.2383177570093458D * d, 0.028037383177570093D * d, 0.48598130841121495D * d, 0.13551401869158877D * d, 0.6962616822429907D * d));
      localPath1.getElements().add(new ClosePath());
      localPath1.getStyleClass().add("foreground-type2");
      localPath1.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localPath1 });
      break;
    case TYPE3: 
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.08411214953271028D * d, 0.5093457943925234D * d));
      localPath1.getElements().add(new CubicCurveTo(0.2102803738317757D * d, 0.5560747663551402D * d, 0.46261682242990654D * d, 0.5607476635514018D * d, 0.5D * d, 0.5607476635514018D * d));
      localPath1.getElements().add(new CubicCurveTo(0.5373831775700935D * d, 0.5607476635514018D * d, 0.794392523364486D * d, 0.5607476635514018D * d, 0.9158878504672897D * d, 0.5093457943925234D * d));
      localPath1.getElements().add(new CubicCurveTo(0.9158878504672897D * d, 0.2757009345794392D * d, 0.7383177570093458D * d, 0.08411214953271028D * d, 0.5D * d, 0.08411214953271028D * d));
      localPath1.getElements().add(new CubicCurveTo(0.2616822429906542D * d, 0.08411214953271028D * d, 0.08411214953271028D * d, 0.2757009345794392D * d, 0.08411214953271028D * d, 0.5093457943925234D * d));
      localPath1.getElements().add(new ClosePath());
      localPath1.getStyleClass().add("foreground-type3");
      localPath1.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localPath1 });
      break;
    case TYPE4: 
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.677570093457944D * d, 0.24299065420560748D * d));
      localPath1.getElements().add(new CubicCurveTo(0.7710280373831776D * d, 0.308411214953271D * d, 0.822429906542056D * d, 0.411214953271028D * d, 0.8130841121495327D * d, 0.5280373831775701D * d));
      localPath1.getElements().add(new CubicCurveTo(0.7990654205607477D * d, 0.6542056074766355D * d, 0.719626168224299D * d, 0.7570093457943925D * d, 0.5934579439252337D * d, 0.7990654205607477D * d));
      localPath1.getElements().add(new CubicCurveTo(0.48598130841121495D * d, 0.8317757009345794D * d, 0.3691588785046729D * d, 0.8084112149532711D * d, 0.2850467289719626D * d, 0.7289719626168224D * d));
      localPath1.getElements().add(new CubicCurveTo(0.2757009345794392D * d, 0.719626168224299D * d, 0.2523364485981308D * d, 0.7149532710280374D * d, 0.2336448598130841D * d, 0.7289719626168224D * d));
      localPath1.getElements().add(new CubicCurveTo(0.21495327102803738D * d, 0.7476635514018691D * d, 0.21962616822429906D * d, 0.7710280373831776D * d, 0.22897196261682243D * d, 0.7757009345794392D * d));
      localPath1.getElements().add(new CubicCurveTo(0.3317757009345794D * d, 0.8785046728971962D * d, 0.4766355140186916D * d, 0.9158878504672897D * d, 0.616822429906542D * d, 0.8691588785046729D * d));
      localPath1.getElements().add(new CubicCurveTo(0.7710280373831776D * d, 0.822429906542056D * d, 0.8738317757009346D * d, 0.6915887850467289D * d, 0.8878504672897196D * d, 0.5327102803738317D * d));
      localPath1.getElements().add(new CubicCurveTo(0.897196261682243D * d, 0.3878504672897196D * d, 0.8364485981308412D * d, 0.2570093457943925D * d, 0.719626168224299D * d, 0.1822429906542056D * d));
      localPath1.getElements().add(new CubicCurveTo(0.705607476635514D * d, 0.17289719626168223D * d, 0.6822429906542056D * d, 0.16355140186915887D * d, 0.6635514018691588D * d, 0.18691588785046728D * d));
      localPath1.getElements().add(new CubicCurveTo(0.6542056074766355D * d, 0.205607476635514D * d, 0.6682242990654206D * d, 0.2383177570093458D * d, 0.677570093457944D * d, 0.24299065420560748D * d));
      localPath1.getElements().add(new ClosePath());
      localPath1.setFill(new RadialGradient(0.0D, 0.0D, 0.5D * d, 0.5D * d, 0.3878504672897196D * d, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(0.83D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0980392157D)) }));
      localPath1.setStroke(null);
      Path localPath2 = new Path();
      localPath2.setFillRule(FillRule.EVEN_ODD);
      localPath2.getElements().add(new MoveTo(0.2616822429906542D * d, 0.22429906542056074D * d));
      localPath2.getElements().add(new CubicCurveTo(0.2850467289719626D * d, 0.2383177570093458D * d, 0.2523364485981308D * d, 0.2850467289719626D * d, 0.24299065420560748D * d, 0.3177570093457944D * d));
      localPath2.getElements().add(new CubicCurveTo(0.24299065420560748D * d, 0.35046728971962615D * d, 0.27102803738317754D * d, 0.38317757009345793D * d, 0.27102803738317754D * d, 0.397196261682243D * d));
      localPath2.getElements().add(new CubicCurveTo(0.2757009345794392D * d, 0.4158878504672897D * d, 0.2616822429906542D * d, 0.45794392523364486D * d, 0.2383177570093458D * d, 0.5093457943925234D * d));
      localPath2.getElements().add(new CubicCurveTo(0.22429906542056074D * d, 0.5420560747663551D * d, 0.17757009345794392D * d, 0.6121495327102804D * d, 0.1588785046728972D * d, 0.6121495327102804D * d));
      localPath2.getElements().add(new CubicCurveTo(0.14485981308411214D * d, 0.6121495327102804D * d, 0.08878504672897196D * d, 0.5467289719626168D * d, 0.1308411214953271D * d, 0.3691588785046729D * d));
      localPath2.getElements().add(new CubicCurveTo(0.14018691588785046D * d, 0.3364485981308411D * d, 0.21495327102803738D * d, 0.20093457943925233D * d, 0.2616822429906542D * d, 0.22429906542056074D * d));
      localPath2.getElements().add(new ClosePath());
      localPath1.getStyleClass().add("foreground-type4");
      localPath2.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localPath1, localPath2 });
      break;
    case TYPE5: 
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.08411214953271028D * d, 0.5D * d));
      localPath1.getElements().add(new CubicCurveTo(0.08411214953271028D * d, 0.27102803738317754D * d, 0.27102803738317754D * d, 0.08411214953271028D * d, 0.5D * d, 0.08411214953271028D * d));
      localPath1.getElements().add(new CubicCurveTo(0.7009345794392523D * d, 0.08411214953271028D * d, 0.8644859813084113D * d, 0.22429906542056074D * d, 0.9065420560747663D * d, 0.411214953271028D * d));
      localPath1.getElements().add(new CubicCurveTo(0.9112149532710281D * d, 0.4392523364485981D * d, 0.9112149532710281D * d, 0.5186915887850467D * d, 0.8457943925233645D * d, 0.5373831775700935D * d));
      localPath1.getElements().add(new CubicCurveTo(0.794392523364486D * d, 0.5467289719626168D * d, 0.5514018691588785D * d, 0.411214953271028D * d, 0.3925233644859813D * d, 0.45794392523364486D * d));
      localPath1.getElements().add(new CubicCurveTo(0.16822429906542055D * d, 0.5093457943925234D * d, 0.13551401869158877D * d, 0.7757009345794392D * d, 0.09345794392523364D * d, 0.5934579439252337D * d));
      localPath1.getElements().add(new CubicCurveTo(0.08878504672897196D * d, 0.5607476635514018D * d, 0.08411214953271028D * d, 0.5327102803738317D * d, 0.08411214953271028D * d, 0.5D * d));
      localPath1.getElements().add(new ClosePath());
      localPath1.getStyleClass().add("foreground-type5");
      localPath1.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localPath1 });
      break;
    case TYPE1: 
    default: 
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.08411214953271028D * d, 0.5093457943925234D * d));
      localPath1.getElements().add(new CubicCurveTo(0.205607476635514D * d, 0.4485981308411215D * d, 0.3364485981308411D * d, 0.4158878504672897D * d, 0.5D * d, 0.4158878504672897D * d));
      localPath1.getElements().add(new CubicCurveTo(0.6728971962616822D * d, 0.4158878504672897D * d, 0.7897196261682243D * d, 0.4439252336448598D * d, 0.9158878504672897D * d, 0.5093457943925234D * d));
      localPath1.getElements().add(new CubicCurveTo(0.9158878504672897D * d, 0.2757009345794392D * d, 0.7383177570093458D * d, 0.08411214953271028D * d, 0.5D * d, 0.08411214953271028D * d));
      localPath1.getElements().add(new CubicCurveTo(0.2616822429906542D * d, 0.08411214953271028D * d, 0.08411214953271028D * d, 0.2757009345794392D * d, 0.08411214953271028D * d, 0.5093457943925234D * d));
      localPath1.getElements().add(new ClosePath());
      localPath1.getStyleClass().add("foreground-type1");
      localPath1.setStroke(null);
      paramGroup.getChildren().addAll(new Node[] { localPath1 });
    }
    paramGroup.setCache(true);
  }
  
  protected void drawCircularTickmarks(Gauge paramGauge, Group paramGroup, Point2D paramPoint2D, Rectangle paramRectangle)
  {
    double d1 = paramRectangle.getWidth() <= paramRectangle.getHeight() ? paramRectangle.getWidth() : paramRectangle.getHeight();
    double d2 = paramRectangle.getWidth();
    double d3 = paramRectangle.getHeight();
    double d4 = paramGauge.getRadialRange().RADIUS_FACTOR;
    double d5;
    switch (paramGauge.getTickLabelOrientation())
    {
    case TANGENT: 
      d5 = 0.07D;
      break;
    case HORIZONTAL: 
      d5 = 0.08D;
      break;
    case NORMAL: 
    default: 
      d5 = 0.09D;
    }
    paramGroup.getTransforms().clear();
    paramGroup.getChildren().clear();
    paramGroup.getStyleClass().add(paramGauge.getBackgroundDesign().CSS_BACKGROUND);
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d2, d3);
    localRectangle.setOpacity(0.0D);
    paramGroup.getChildren().add(localRectangle);
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.setSmooth(true);
    localPath1.setStrokeType(StrokeType.CENTERED);
    localPath1.setStrokeLineCap(StrokeLineCap.ROUND);
    localPath1.setStrokeLineJoin(StrokeLineJoin.BEVEL);
    if (d2 < 200.0D) {
      localPath1.setStrokeWidth(1.0D);
    } else {
      localPath1.setStrokeWidth(0.005D * d2);
    }
    if (paramGauge.isMajorTickmarkColorEnabled()) {
      switch (paramGauge.getMajorTickmarkType())
      {
      case TRIANGLE: 
        localPath1.setFill(paramGauge.getMajorTickmarkColor());
        localPath1.setStroke(null);
        break;
      default: 
        localPath1.setFill(null);
        localPath1.setStroke(paramGauge.getMajorTickmarkColor());
        break;
      }
    } else {
      switch (paramGauge.getMajorTickmarkType())
      {
      case TRIANGLE: 
        localPath1.getStyleClass().add(paramGauge.getBackgroundDesign().CSS_TEXT);
        break;
      default: 
        localPath1.getStyleClass().add(paramGauge.getBackgroundDesign().CSS_BACKGROUND);
      }
    }
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.setSmooth(true);
    localPath2.setStrokeType(StrokeType.CENTERED);
    localPath2.setStrokeLineCap(StrokeLineCap.ROUND);
    localPath2.setStrokeLineJoin(StrokeLineJoin.BEVEL);
    if (d2 < 200.0D) {
      localPath2.setStrokeWidth(0.5D);
    } else {
      localPath2.setStrokeWidth(0.0025D * d2);
    }
    localPath2.getStyleClass().add(paramGauge.getBackgroundDesign().CSS_BACKGROUND);
    Path localPath3 = new Path();
    localPath3.setFillRule(FillRule.EVEN_ODD);
    localPath3.setSmooth(true);
    localPath3.setStrokeType(StrokeType.CENTERED);
    localPath3.setStrokeLineCap(StrokeLineCap.ROUND);
    localPath3.setStrokeLineJoin(StrokeLineJoin.BEVEL);
    if (d2 < 200.0D) {
      localPath3.setStrokeWidth(0.3D);
    } else {
      localPath3.setStrokeWidth(0.0015D * d2);
    }
    if (paramGauge.isMinorTickmarkColorEnabled())
    {
      localPath3.setFill(null);
      localPath3.setStroke(paramGauge.getMinorTickmarkColor());
    }
    else
    {
      localPath3.getStyleClass().add(paramGauge.getBackgroundDesign().CSS_BACKGROUND);
    }
    double d6;
    if (paramGauge.isBargraph())
    {
      localPath1.setVisible(false);
      localPath2.setVisible(false);
      localPath3.setVisible(false);
      d6 = 0.03D;
    }
    else
    {
      localPath1.setVisible(true);
      localPath2.setVisible(true);
      localPath3.setVisible(true);
      d6 = 0.0D;
    }
    ArrayList localArrayList = new ArrayList();
    NumberFormat localNumberFormat;
    if (paramGauge.getTickLabelNumberFormat() == NumberFormat.AUTO)
    {
      if (Math.abs(paramGauge.getMajorTickSpacing()) > 1000.0D) {
        localNumberFormat = NumberFormat.SCIENTIFIC;
      } else if (paramGauge.getMajorTickSpacing() % 1.0D != 0.0D) {
        localNumberFormat = NumberFormat.FRACTIONAL;
      } else {
        localNumberFormat = NumberFormat.STANDARD;
      }
    }
    else {
      localNumberFormat = paramGauge.getTickLabelNumberFormat();
    }
    Font localFont;
    if (d2 < 250.0D) {
      localFont = Font.font("Verdana", FontWeight.NORMAL, 8.0D);
    } else {
      localFont = Font.font("Verdana", FontWeight.NORMAL, 0.035D * d2);
    }
    double d7 = (d5 + d6) * d2;
    double d8 = 0.0133333333D * d2;
    double d9 = 0.02D * d2;
    double d10 = 0.03D * d2;
    if (paramGauge.getTickmarksOffset() != null)
    {
      paramGroup.translateXProperty().set(paramGauge.getTickmarksOffset().getX());
      paramGroup.translateYProperty().set(paramGauge.getTickmarksOffset().getY());
    }
    double d11 = paramGauge.getRadialRange().ROTATION_OFFSET;
    double d12 = d2 * d4;
    double d13 = paramGauge.getRadialRange().ANGLE_RANGE / ((paramGauge.getMaxValue() - paramGauge.getMinValue()) / paramGauge.getMinorTickSpacing()) * paramGauge.getRadialRange().ANGLE_STEP_SIGN;
    double d14 = paramGauge.isTightScale() ? paramGauge.getMinValue() + paramGauge.getTightScaleOffset() * paramGauge.getMinorTickSpacing() : paramGauge.getMinValue();
    int i = paramGauge.isTightScale() ? paramGauge.getMaxNoOfMinorTicks() - 1 - (int)paramGauge.getTightScaleOffset() : paramGauge.getMaxNoOfMinorTicks() - 1;
    Rotate localRotate = Transform.rotate(d11 - 180.0D, paramPoint2D.getX(), paramPoint2D.getY());
    paramGroup.getTransforms().add(localRotate);
    double d17 = paramGauge.getMinValue();
    double d18 = paramGauge.getMaxValue();
    double d19 = paramGauge.getMinorTickSpacing();
    double d20 = 0.0D;
    for (double d21 = d17; Double.compare(d21, d18) <= 0; d21 += d19)
    {
      double d15 = Math.sin(Math.toRadians(d20));
      double d16 = Math.cos(Math.toRadians(d20));
      i++;
      Point2D localPoint2D2;
      Point2D localPoint2D3;
      if (i == paramGauge.getMaxNoOfMinorTicks())
      {
        localPoint2D2 = new Point2D(paramPoint2D.getX() + (d12 - d10) * d15, paramPoint2D.getY() + (d12 - d10) * d16);
        localPoint2D3 = new Point2D(paramPoint2D.getX() + d12 * d15, paramPoint2D.getY() + d12 * d16);
        Point2D localPoint2D1 = new Point2D(paramPoint2D.getX() + (d12 - d7) * d15, paramPoint2D.getY() + (d12 - d7) * d16);
        Object localObject;
        if ((paramGauge.isTickmarksVisible()) && (paramGauge.isMajorTicksVisible())) {
          switch (paramGauge.getMajorTickmarkType())
          {
          case TRIANGLE: 
            localObject = new Point2D(paramPoint2D.getX() + d12 * Math.sin(Math.toRadians(d20 - 1.2D)), paramPoint2D.getY() + d12 * Math.cos(Math.toRadians(d20 - 1.2D)));
            Point2D localPoint2D4 = new Point2D(paramPoint2D.getX() + d12 * Math.sin(Math.toRadians(d20 + 1.2D)), paramPoint2D.getY() + d12 * Math.cos(Math.toRadians(d20 + 1.2D)));
            localPath1.getElements().add(new MoveTo(localPoint2D2.getX(), localPoint2D2.getY()));
            localPath1.getElements().add(new LineTo(((Point2D)localObject).getX(), ((Point2D)localObject).getY()));
            localPath1.getElements().add(new LineTo(localPoint2D4.getX(), localPoint2D4.getY()));
            localPath1.getElements().add(new ClosePath());
            break;
          default: 
            drawRadialTicks(localPath1, localPoint2D2, localPoint2D3);
          }
        }
        if (paramGauge.isTickLabelsVisible())
        {
          localObject = new Text(localNumberFormat.format(Double.valueOf(d14)));
          ((Text)localObject).setFontSmoothingType(FontSmoothingType.LCD);
          ((Text)localObject).setTextOrigin(VPos.BOTTOM);
          ((Text)localObject).setBoundsType(TextBoundsType.LOGICAL);
          ((Text)localObject).getStyleClass().add(paramGauge.getBackgroundDesign().CSS_TEXT);
          ((Text)localObject).setStroke(null);
          ((Text)localObject).setFont(localFont);
          ((Text)localObject).setX(localPoint2D1.getX() - ((Text)localObject).getLayoutBounds().getWidth() / 2.0D);
          ((Text)localObject).setY(localPoint2D1.getY() + ((Text)localObject).getLayoutBounds().getHeight() / 2.0D);
          switch (paramGauge.getTickLabelOrientation())
          {
          case NORMAL: 
            if (Double.compare(d20, -paramGauge.getRadialRange().TICKLABEL_ORIENATION_CHANGE_ANGLE) > 0) {
              ((Text)localObject).rotateProperty().set(-90.0D - d20);
            } else {
              ((Text)localObject).rotateProperty().set(90.0D - d20);
            }
            break;
          case HORIZONTAL: 
            ((Text)localObject).rotateProperty().set(180.0D - paramGauge.getRadialRange().ROTATION_OFFSET);
            break;
          case TANGENT: 
          default: 
            ((Text)localObject).rotateProperty().set(180.0D - d20 + 0.0D);
          }
          if (Double.compare(d14, d18) != 0) {
            localArrayList.add(localObject);
          } else if ((paramGauge.isLastLabelVisible()) && (paramGauge.getRadialRange() != RadialRange.RADIAL_360)) {
            localArrayList.add(localObject);
          }
        }
        d14 += paramGauge.getMajorTickSpacing();
        i = 0;
      }
      else
      {
        localPoint2D2 = new Point2D(paramPoint2D.getX() + (d12 - d8) * d15, paramPoint2D.getY() + (d12 - d8) * d16);
        localPoint2D3 = new Point2D(paramPoint2D.getX() + d12 * d15, paramPoint2D.getY() + d12 * d16);
        if ((paramGauge.getMaxNoOfMinorTicks() % 2 == 0) && (i == paramGauge.getMaxNoOfMinorTicks() / 2))
        {
          localPoint2D2 = new Point2D(paramPoint2D.getX() + (d12 - d9) * d15, paramPoint2D.getY() + (d12 - d9) * d16);
          localPoint2D3 = new Point2D(paramPoint2D.getX() + d12 * d15, paramPoint2D.getY() + d12 * d16);
          if ((paramGauge.isTickmarksVisible()) && (paramGauge.isMinorTicksVisible())) {
            drawRadialTicks(localPath2, localPoint2D2, localPoint2D3);
          }
        }
        else if ((paramGauge.isTickmarksVisible()) && (paramGauge.isMinorTicksVisible()))
        {
          drawRadialTicks(localPath3, localPoint2D2, localPoint2D3);
        }
      }
      d20 -= d13;
    }
    if (paramGauge.isTickmarkGlowEnabled())
    {
      InnerShadow localInnerShadow = new InnerShadow();
      localInnerShadow.setRadius(0.005D * d1);
      localInnerShadow.setColor(paramGauge.getTickmarkGlowColor());
      localInnerShadow.setBlurType(BlurType.GAUSSIAN);
      DropShadow localDropShadow = new DropShadow();
      localDropShadow.setRadius(0.02D * d1);
      localDropShadow.setColor(paramGauge.getTickmarkGlowColor());
      localDropShadow.setBlurType(BlurType.GAUSSIAN);
      localDropShadow.inputProperty().set(localInnerShadow);
      localPath1.setEffect(localDropShadow);
      localPath2.setEffect(localDropShadow);
      localPath3.setEffect(localDropShadow);
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        Text localText = (Text)localIterator.next();
        localText.setEffect(localDropShadow);
      }
    }
    paramGroup.getChildren().addAll(new Node[] { localPath1, localPath2, localPath3 });
    paramGroup.getChildren().addAll(localArrayList);
    paramGroup.setCache(true);
  }
  
  private static void drawRadialTicks(Path paramPath, Point2D paramPoint2D1, Point2D paramPoint2D2)
  {
    paramPath.getElements().add(new MoveTo(paramPoint2D1.getX(), paramPoint2D1.getY()));
    paramPath.getElements().add(new LineTo(paramPoint2D2.getX(), paramPoint2D2.getY()));
  }
  
  protected Group createLed(double paramDouble, LedColor paramLedColor, boolean paramBoolean)
  {
    Group localGroup = new Group();
    Circle localCircle1 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.25D * paramDouble);
    localCircle1.setFill(new LinearGradient(0.0D, 0.0D, 0.0D, paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 0.9D)), new Stop(0.4D, Color.color(0.2D, 0.2D, 0.2D, 0.96D)), new Stop(1.0D, Color.color(0.6D, 0.6D, 0.6D, 1.0D)) }));
    localCircle1.setStroke(null);
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.blurTypeProperty().set(BlurType.GAUSSIAN);
    localInnerShadow.setRadius(10.0D);
    localInnerShadow.setWidth(0.2D * paramDouble);
    localInnerShadow.setHeight(0.2D * paramDouble);
    localInnerShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 1.0D));
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setInput(localInnerShadow);
    localDropShadow.setSpread(0.6D);
    localDropShadow.setWidth(2.0D * paramDouble);
    localDropShadow.setHeight(2.0D * paramDouble);
    localDropShadow.setBlurType(BlurType.GAUSSIAN);
    localDropShadow.setColor(paramLedColor.GLOW_COLOR);
    Circle localCircle2 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.2261904762D * paramDouble);
    if (paramBoolean)
    {
      localCircle2.getStyleClass().add("root");
      localCircle2.setStyle(paramLedColor.CSS);
      localCircle2.getStyleClass().add("led-on-gradient");
      localCircle2.setStroke(null);
      localCircle2.setEffect(localDropShadow);
    }
    else
    {
      localCircle2.getStyleClass().add("root");
      localCircle2.setStyle(paramLedColor.CSS);
      localCircle2.getStyleClass().add("led-off-gradient");
      localCircle2.setStroke(null);
      localCircle2.effectProperty().set(localInnerShadow);
    }
    Circle localCircle3 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.2261904762D * paramDouble);
    localCircle3.setFill(new RadialGradient(0.0D, 0.0D, 0.47619047619047616D * paramDouble, 0.47619047619047616D * paramDouble, 0.4523809523809524D * paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(0, 0, 0, 0.0D)), new Stop(0.86D, Color.rgb(0, 0, 0, 0.3450980392D)), new Stop(1.0D, Color.rgb(0, 0, 0, 0.4D)) }));
    localCircle3.setStroke(null);
    Ellipse localEllipse = new Ellipse(0.5D * paramDouble, 0.4D * paramDouble, 0.1D * paramDouble, 0.06D * paramDouble);
    localEllipse.setFill(new LinearGradient(0.0D, 0.3D * paramDouble, 0.0D, 0.5D * paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(255, 255, 255, 0.4D)), new Stop(1.0D, Color.rgb(255, 255, 255, 0.0D)) }));
    localEllipse.setStroke(null);
    localGroup.getChildren().addAll(new Node[] { localCircle1, localCircle2, localEllipse });
    return localGroup;
  }
  
  protected Group createIndicator(double paramDouble, Marker paramMarker, Point2D paramPoint2D)
  {
    Group localGroup = new Group();
    double d1 = paramDouble * 0.04D;
    double d2 = paramDouble * 0.1D;
    localGroup.getChildren().add(createIndicatorShape(d1, d2, paramMarker, paramPoint2D));
    return localGroup;
  }
  
  protected Shape createIndicatorShape(double paramDouble1, double paramDouble2, Marker paramMarker, Point2D paramPoint2D)
  {
    Path localPath = new Path();
    localPath.setFillRule(FillRule.EVEN_ODD);
    localPath.getElements().add(new MoveTo(paramDouble1 * 0.1111111111111111D, paramDouble2 * 0.047619047619047616D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.8888888888888888D, paramDouble2 * 0.047619047619047616D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.8888888888888888D, paramDouble2 * 0.3333333333333333D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5D, paramDouble2 * 0.5714285714285714D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.1111111111111111D, paramDouble2 * 0.3333333333333333D));
    localPath.getElements().add(new ClosePath());
    localPath.setStroke(Color.WHITE);
    localPath.setStrokeType(StrokeType.CENTERED);
    localPath.setStrokeLineCap(StrokeLineCap.ROUND);
    localPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
    localPath.setStrokeWidth(0.02D * paramDouble2);
    LinearGradient localLinearGradient = new LinearGradient(localPath.getLayoutX(), 0.0D, localPath.getLayoutX() + localPath.getLayoutBounds().getWidth(), 0.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, paramMarker.getColor().brighter()), new Stop(0.55D, paramMarker.getColor().brighter()), new Stop(0.55D, paramMarker.getColor().darker()), new Stop(1.0D, paramMarker.getColor().darker()) });
    localPath.setFill(localLinearGradient);
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.4D));
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setHeight(0.325D * paramDouble2);
    localDropShadow.setWidth(0.325D * paramDouble2);
    localDropShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.65D));
    localDropShadow.setInput(localInnerShadow);
    localPath.setEffect(localDropShadow);
    localPath.setLayoutX(paramPoint2D.getX());
    localPath.setLayoutY(paramPoint2D.getY());
    localPath.setCache(true);
    return localPath;
  }
  
  protected Group createStandardKnob(double paramDouble, KnobColor paramKnobColor)
  {
    Group localGroup = new Group();
    Stop[] arrayOfStop;
    switch (paramKnobColor)
    {
    case BLACK: 
      arrayOfStop = new Stop[] { new Stop(0.0D, Color.web("#BFBFBF")), new Stop(0.5D, Color.web("#2B2A2F")), new Stop(1.0D, Color.web("#7D7E80")) };
      break;
    case BRASS: 
      arrayOfStop = new Stop[] { new Stop(0.0D, Color.web("#DFD0AE")), new Stop(0.5D, Color.web("#7A5E3E")), new Stop(1.0D, Color.web("#CFBE9D")) };
      break;
    case SILVER: 
    default: 
      arrayOfStop = new Stop[] { new Stop(0.0D, Color.web("#D7D7D7")), new Stop(0.5D, Color.web("#747474")), new Stop(1.0D, Color.web("#D7D7D7")) };
    }
    Circle localCircle1 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.5D * paramDouble);
    localCircle1.setFill(new LinearGradient(0.5D * paramDouble, 0.0D, 0.5D * paramDouble, paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.7058823529D, 0.7058823529D, 0.7058823529D, 1.0D)), new Stop(0.46D, Color.color(0.2470588235D, 0.2470588235D, 0.2470588235D, 1.0D)), new Stop(1.0D, Color.color(0.1568627451D, 0.1568627451D, 0.1568627451D, 1.0D)) }));
    localCircle1.setStroke(null);
    Circle localCircle2 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.3888888888888889D * paramDouble);
    localCircle2.setFill(new LinearGradient(0.5D * paramDouble, 0.1111111111111111D * paramDouble, 0.5D * paramDouble, 0.8888888888888888D * paramDouble, false, CycleMethod.NO_CYCLE, arrayOfStop));
    localCircle2.setStroke(null);
    localGroup.getChildren().addAll(new Node[] { localCircle1, localCircle2 });
    localGroup.setCache(true);
    return localGroup;
  }
  
  protected Group createMetalKnob(double paramDouble, KnobColor paramKnobColor)
  {
    Group localGroup = new Group();
    Circle localCircle1 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.5D * paramDouble);
    localCircle1.setFill(new LinearGradient(0.5D * paramDouble, 0.0D, 0.5D * paramDouble, paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.3607843137D, 0.3725490196D, 0.3960784314D, 1.0D)), new Stop(0.47D, Color.color(0.1803921569D, 0.1921568627D, 0.2078431373D, 1.0D)), new Stop(1.0D, Color.color(0.0862745098D, 0.0901960784D, 0.1019607843D, 1.0D)) }));
    localCircle1.setStroke(null);
    Stop[] arrayOfStop;
    switch (paramKnobColor)
    {
    case BLACK: 
      arrayOfStop = new Stop[] { new Stop(0.0D, Color.web("#2B2A2F")), new Stop(1.0D, Color.web("#1A1B20")) };
      break;
    case BRASS: 
      arrayOfStop = new Stop[] { new Stop(0.0D, Color.web("#966E36")), new Stop(1.0D, Color.web("#7C5F3D")) };
      break;
    case SILVER: 
    default: 
      arrayOfStop = new Stop[] { new Stop(0.0D, Color.color(0.8D, 0.8D, 0.8D, 1.0D)), new Stop(1.0D, Color.color(0.3411764706D, 0.3607843137D, 0.3843137255D, 1.0D)) };
    }
    Circle localCircle2 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.4444444444444444D * paramDouble);
    localCircle2.setFill(new LinearGradient(0.5D * paramDouble, 0.05555555555555555D * paramDouble, 0.5D * paramDouble, 0.9444444444444444D * paramDouble, false, CycleMethod.NO_CYCLE, arrayOfStop));
    localCircle2.setStroke(null);
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.7777777777777778D * paramDouble, 0.8333333333333334D * paramDouble));
    localPath1.getElements().add(new CubicCurveTo(0.7222222222222222D * paramDouble, 0.7222222222222222D * paramDouble, 0.6111111111111112D * paramDouble, 0.6666666666666666D * paramDouble, 0.5D * paramDouble, 0.6666666666666666D * paramDouble));
    localPath1.getElements().add(new CubicCurveTo(0.3888888888888889D * paramDouble, 0.6666666666666666D * paramDouble, 0.2777777777777778D * paramDouble, 0.7222222222222222D * paramDouble, 0.2222222222222222D * paramDouble, 0.8333333333333334D * paramDouble));
    localPath1.getElements().add(new CubicCurveTo(0.2777777777777778D * paramDouble, 0.8888888888888888D * paramDouble, 0.3888888888888889D * paramDouble, 0.9444444444444444D * paramDouble, 0.5D * paramDouble, 0.9444444444444444D * paramDouble));
    localPath1.getElements().add(new CubicCurveTo(0.6111111111111112D * paramDouble, 0.9444444444444444D * paramDouble, 0.7222222222222222D * paramDouble, 0.8888888888888888D * paramDouble, 0.7777777777777778D * paramDouble, 0.8333333333333334D * paramDouble));
    localPath1.getElements().add(new ClosePath());
    localPath1.setFill(new RadialGradient(0.0D, 0.0D, 0.5555555555555556D * paramDouble, 0.9444444444444444D * paramDouble, 0.3888888888888889D * paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.6D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
    localPath1.setStroke(null);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.9444444444444444D * paramDouble, 0.2777777777777778D * paramDouble));
    localPath2.getElements().add(new CubicCurveTo(0.8333333333333334D * paramDouble, 0.1111111111111111D * paramDouble, 0.6666666666666666D * paramDouble, 0.0D, 0.5D * paramDouble, 0.0D));
    localPath2.getElements().add(new CubicCurveTo(0.3333333333333333D * paramDouble, 0.0D, 0.16666666666666666D * paramDouble, 0.1111111111111111D * paramDouble, 0.05555555555555555D * paramDouble, 0.2777777777777778D * paramDouble));
    localPath2.getElements().add(new CubicCurveTo(0.16666666666666666D * paramDouble, 0.3333333333333333D * paramDouble, 0.3333333333333333D * paramDouble, 0.3888888888888889D * paramDouble, 0.5D * paramDouble, 0.3888888888888889D * paramDouble));
    localPath2.getElements().add(new CubicCurveTo(0.6666666666666666D * paramDouble, 0.3888888888888889D * paramDouble, 0.8333333333333334D * paramDouble, 0.3333333333333333D * paramDouble, 0.9444444444444444D * paramDouble, 0.2777777777777778D * paramDouble));
    localPath2.getElements().add(new ClosePath());
    localPath2.setFill(new RadialGradient(0.0D, 0.0D, 0.5D * paramDouble, 0.0D, 0.5833333333333334D * paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.7490196078D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
    localPath2.setStroke(null);
    Circle localCircle3 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.2777777777777778D * paramDouble);
    localCircle3.setFill(new LinearGradient(0.5D * paramDouble, 0.2222222222222222D * paramDouble, 0.5D * paramDouble, 0.7777777777777778D * paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0D, 0.0D, 0.0D, 1.0D)), new Stop(1.0D, Color.color(0.8D, 0.8D, 0.8D, 1.0D)) }));
    localCircle3.setStroke(null);
    Circle localCircle4 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.2222222222222222D * paramDouble);
    localCircle4.setFill(new LinearGradient(0.5D * paramDouble, 0.2777777777777778D * paramDouble, 0.5D * paramDouble, 0.7222222222222222D * paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.0039215686D, 0.0235294118D, 0.0431372549D, 1.0D)), new Stop(1.0D, Color.color(0.1960784314D, 0.2039215686D, 0.2196078431D, 1.0D)) }));
    localCircle4.setStroke(null);
    localGroup.getChildren().addAll(new Node[] { localCircle1, localCircle2, localPath1, localPath2, localCircle3, localCircle4 });
    localGroup.setCache(true);
    return localGroup;
  }
  
  protected Group createPlainKnob(double paramDouble, KnobColor paramKnobColor)
  {
    Group localGroup = new Group();
    Circle localCircle1 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.5D * paramDouble);
    localCircle1.setFill(new LinearGradient(0.5D * paramDouble, 0.0D, 0.5D * paramDouble, paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(0.3607843137D, 0.3725490196D, 0.3960784314D, 1.0D)), new Stop(0.47D, Color.color(0.1803921569D, 0.1921568627D, 0.2078431373D, 1.0D)), new Stop(1.0D, Color.color(0.0862745098D, 0.0901960784D, 0.1019607843D, 1.0D)) }));
    localCircle1.setStroke(null);
    Stop[] arrayOfStop;
    switch (paramKnobColor)
    {
    case BLACK: 
      arrayOfStop = new Stop[] { new Stop(0.0D, Color.web("#2B2A2F")), new Stop(1.0D, Color.web("#1A1B20")) };
      break;
    case BRASS: 
      arrayOfStop = new Stop[] { new Stop(0.0D, Color.web("#966E36")), new Stop(1.0D, Color.web("#7C5F3D")) };
      break;
    case SILVER: 
    default: 
      arrayOfStop = new Stop[] { new Stop(0.0D, Color.color(0.8D, 0.8D, 0.8D, 1.0D)), new Stop(1.0D, Color.color(0.3411764706D, 0.3607843137D, 0.3843137255D, 1.0D)) };
    }
    Circle localCircle2 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.4444444444444444D * paramDouble);
    localCircle2.setFill(new LinearGradient(0.5D * paramDouble, 0.05555555555555555D * paramDouble, 0.5D * paramDouble, 0.9444444444444444D * paramDouble, false, CycleMethod.NO_CYCLE, arrayOfStop));
    localCircle2.setStroke(null);
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    localPath1.getElements().add(new MoveTo(0.7777777777777778D * paramDouble, 0.8333333333333334D * paramDouble));
    localPath1.getElements().add(new CubicCurveTo(0.7222222222222222D * paramDouble, 0.7222222222222222D * paramDouble, 0.6111111111111112D * paramDouble, 0.6666666666666666D * paramDouble, 0.5D * paramDouble, 0.6666666666666666D * paramDouble));
    localPath1.getElements().add(new CubicCurveTo(0.3888888888888889D * paramDouble, 0.6666666666666666D * paramDouble, 0.2777777777777778D * paramDouble, 0.7222222222222222D * paramDouble, 0.2222222222222222D * paramDouble, 0.8333333333333334D * paramDouble));
    localPath1.getElements().add(new CubicCurveTo(0.2777777777777778D * paramDouble, 0.8888888888888888D * paramDouble, 0.3888888888888889D * paramDouble, 0.9444444444444444D * paramDouble, 0.5D * paramDouble, 0.9444444444444444D * paramDouble));
    localPath1.getElements().add(new CubicCurveTo(0.6111111111111112D * paramDouble, 0.9444444444444444D * paramDouble, 0.7222222222222222D * paramDouble, 0.8888888888888888D * paramDouble, 0.7777777777777778D * paramDouble, 0.8333333333333334D * paramDouble));
    localPath1.getElements().add(new ClosePath());
    localPath1.setFill(new RadialGradient(0.0D, 0.0D, 0.5555555555555556D * paramDouble, 0.9444444444444444D * paramDouble, 0.3888888888888889D * paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.2D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
    localPath1.setStroke(null);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    localPath2.getElements().add(new MoveTo(0.9444444444444444D * paramDouble, 0.2777777777777778D * paramDouble));
    localPath2.getElements().add(new CubicCurveTo(0.8333333333333334D * paramDouble, 0.1111111111111111D * paramDouble, 0.6666666666666666D * paramDouble, 0.0D, 0.5D * paramDouble, 0.0D));
    localPath2.getElements().add(new CubicCurveTo(0.3333333333333333D * paramDouble, 0.0D, 0.16666666666666666D * paramDouble, 0.1111111111111111D * paramDouble, 0.05555555555555555D * paramDouble, 0.2777777777777778D * paramDouble));
    localPath2.getElements().add(new CubicCurveTo(0.16666666666666666D * paramDouble, 0.3333333333333333D * paramDouble, 0.3333333333333333D * paramDouble, 0.3888888888888889D * paramDouble, 0.5D * paramDouble, 0.3888888888888889D * paramDouble));
    localPath2.getElements().add(new CubicCurveTo(0.6666666666666666D * paramDouble, 0.3888888888888889D * paramDouble, 0.8333333333333334D * paramDouble, 0.3333333333333333D * paramDouble, 0.9444444444444444D * paramDouble, 0.2777777777777778D * paramDouble));
    localPath2.getElements().add(new ClosePath());
    localPath2.setFill(new RadialGradient(0.0D, 0.0D, 0.5D * paramDouble, 0.0D, 0.5833333333333334D * paramDouble, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.color(1.0D, 1.0D, 1.0D, 0.35D)), new Stop(1.0D, Color.color(1.0D, 1.0D, 1.0D, 0.0D)) }));
    localPath2.setStroke(null);
    localGroup.getChildren().addAll(new Node[] { localCircle1, localCircle2, localPath1, localPath2 });
    localGroup.setCache(true);
    return localGroup;
  }
  
  protected Group createBigKnob(double paramDouble, KnobColor paramKnobColor)
  {
    Group localGroup = new Group();
    Stop[] arrayOfStop1;
    Stop[] arrayOfStop2;
    Stop[] arrayOfStop3;
    Stop[] arrayOfStop4;
    switch (paramKnobColor)
    {
    case BLACK: 
      arrayOfStop1 = new Stop[] { new Stop(0.0D, Color.rgb(129, 133, 136)), new Stop(1.0D, Color.rgb(61, 61, 73)) };
      arrayOfStop2 = new Stop[] { new Stop(0.0D, Color.rgb(26, 27, 32)), new Stop(1.0D, Color.rgb(96, 97, 102)) };
      arrayOfStop3 = new Stop[] { new Stop(0.0D, Color.rgb(191, 191, 191)), new Stop(0.47D, Color.rgb(56, 57, 61)), new Stop(1.0D, Color.rgb(143, 144, 146)) };
      arrayOfStop4 = new Stop[] { new Stop(0.0D, Color.rgb(191, 191, 191)), new Stop(0.21D, Color.rgb(94, 93, 99)), new Stop(0.5D, Color.rgb(43, 42, 47)), new Stop(0.78D, Color.rgb(78, 79, 81)), new Stop(1.0D, Color.rgb(143, 144, 146)) };
      break;
    case BRASS: 
      arrayOfStop1 = new Stop[] { new Stop(0.0D, Color.rgb(143, 117, 80)), new Stop(1.0D, Color.rgb(100, 76, 49)) };
      arrayOfStop2 = new Stop[] { new Stop(0.0D, Color.rgb(98, 75, 49)), new Stop(1.0D, Color.rgb(149, 109, 54)) };
      arrayOfStop3 = new Stop[] { new Stop(0.0D, Color.rgb(147, 108, 54)), new Stop(0.47D, Color.rgb(82, 66, 50)), new Stop(1.0D, Color.rgb(147, 108, 54)) };
      arrayOfStop4 = new Stop[] { new Stop(0.0D, Color.rgb(223, 208, 174)), new Stop(0.21D, Color.rgb(159, 136, 104)), new Stop(0.5D, Color.rgb(122, 94, 62)), new Stop(0.78D, Color.rgb(159, 136, 104)), new Stop(1.0D, Color.rgb(223, 208, 174)) };
      break;
    case SILVER: 
    default: 
      arrayOfStop1 = new Stop[] { new Stop(0.0D, Color.rgb(152, 152, 152)), new Stop(1.0D, Color.rgb(118, 121, 126)) };
      arrayOfStop2 = new Stop[] { new Stop(0.0D, Color.rgb(118, 121, 126)), new Stop(1.0D, Color.rgb(191, 191, 191)) };
      arrayOfStop3 = new Stop[] { new Stop(0.0D, Color.rgb(191, 191, 191)), new Stop(0.47D, Color.rgb(116, 116, 116)), new Stop(1.0D, Color.rgb(143, 144, 146)) };
      arrayOfStop4 = new Stop[] { new Stop(0.0D, Color.rgb(215, 215, 215)), new Stop(0.21D, Color.rgb(139, 142, 145)), new Stop(0.5D, Color.rgb(100, 100, 100)), new Stop(0.78D, Color.rgb(139, 142, 145)), new Stop(1.0D, Color.rgb(215, 215, 215)) };
    }
    Circle localCircle1 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.5D * paramDouble);
    localCircle1.setFill(new LinearGradient(0.5D * paramDouble, 0.0D, 0.5D * paramDouble, paramDouble, false, CycleMethod.NO_CYCLE, arrayOfStop1));
    localCircle1.setStroke(null);
    Circle localCircle2 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.46153846153846156D * paramDouble);
    localCircle2.setFill(new LinearGradient(0.5D * paramDouble, 0.038461538461538464D * paramDouble, 0.5D * paramDouble, 0.9615384615384616D * paramDouble, false, CycleMethod.NO_CYCLE, arrayOfStop2));
    localCircle2.setStroke(null);
    Circle localCircle3 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.38461538461538464D * paramDouble);
    localCircle3.setFill(new LinearGradient(0.5D * paramDouble, 0.11538461538461539D * paramDouble, 0.5D * paramDouble, 0.8846153846153846D * paramDouble, false, CycleMethod.NO_CYCLE, arrayOfStop3));
    localCircle3.setStroke(null);
    Circle localCircle4 = new Circle(0.5D * paramDouble, 0.5D * paramDouble, 0.34615384615384615D * paramDouble);
    localCircle4.setFill(new LinearGradient(0.5D * paramDouble, 0.15384615384615385D * paramDouble, 0.5D * paramDouble, 0.8461538461538461D * paramDouble, false, CycleMethod.NO_CYCLE, arrayOfStop4));
    localCircle4.setStroke(null);
    localGroup.getChildren().addAll(new Node[] { localCircle1, localCircle2, localCircle3, localCircle4 });
    localGroup.setCache(true);
    return localGroup;
  }
  
  protected Path createTriangleShape(double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    Path localPath = new Path();
    localPath.setFillRule(FillRule.EVEN_ODD);
    if (paramBoolean)
    {
      localPath.getElements().add(new MoveTo(0.0D, 0.0D));
      localPath.getElements().add(new LineTo(paramDouble1 * 0.5D, paramDouble2));
      localPath.getElements().add(new LineTo(paramDouble1, 0.0D));
    }
    else
    {
      localPath.getElements().add(new MoveTo(0.5D * paramDouble1, 0.0D));
      localPath.getElements().add(new LineTo(0.0D, paramDouble2));
      localPath.getElements().add(new LineTo(paramDouble1, paramDouble2));
    }
    localPath.getElements().add(new ClosePath());
    return localPath;
  }
  
  protected Shape createBargraphLed(Rectangle paramRectangle, Gauge paramGauge, boolean paramBoolean)
  {
    double d = paramRectangle.getWidth();
    Path localPath = new Path();
    localPath.setFillRule(FillRule.EVEN_ODD);
    localPath.getElements().add(new MoveTo(0.485D * d, 0.185D * d));
    localPath.getElements().add(new CubicCurveTo(0.495D * d, 0.185D * d, 0.505D * d, 0.185D * d, 0.515D * d, 0.185D * d));
    localPath.getElements().add(new CubicCurveTo(0.515D * d, 0.185D * d, 0.515D * d, 0.11D * d, 0.515D * d, 0.11D * d));
    localPath.getElements().add(new CubicCurveTo(0.505D * d, 0.11D * d, 0.495D * d, 0.11D * d, 0.485D * d, 0.11D * d));
    localPath.getElements().add(new CubicCurveTo(0.485D * d, 0.11D * d, 0.485D * d, 0.185D * d, 0.485D * d, 0.185D * d));
    localPath.getElements().add(new ClosePath());
    InnerShadow localInnerShadow = new InnerShadow();
    localInnerShadow.setRadius(0.02D * d);
    localInnerShadow.setColor(Color.BLACK);
    localPath.setEffect(localInnerShadow);
    localPath.getStyleClass().add("root");
    localPath.setStyle("-fx-value: " + paramGauge.getValueColor().CSS);
    if (paramBoolean) {
      localPath.getStyleClass().add("bargraph-on");
    } else {
      localPath.getStyleClass().add("bargraph-off");
    }
    localPath.setStroke(null);
    localPath.setCache(true);
    return localPath;
  }
  
  protected Group createLcdThresholdIndicator(double paramDouble1, double paramDouble2, String paramString)
  {
    Group localGroup = new Group();
    localGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, paramDouble1, paramDouble2);
    localRectangle.setOpacity(0.0D);
    localRectangle.setStroke(null);
    localGroup.getChildren().add(localRectangle);
    Path localPath = new Path();
    localPath.setFillRule(FillRule.EVEN_ODD);
    localPath.getElements().add(new MoveTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.7777777777777778D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5555555555555556D, paramDouble2 * 0.7777777777777778D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5555555555555556D, paramDouble2 * 0.8888888888888888D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.8888888888888888D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.7777777777777778D));
    localPath.getElements().add(new ClosePath());
    localPath.getElements().add(new MoveTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.3333333333333333D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5555555555555556D, paramDouble2 * 0.3333333333333333D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5555555555555556D, paramDouble2 * 0.7222222222222222D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.7222222222222222D));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.4444444444444444D, paramDouble2 * 0.3333333333333333D));
    localPath.getElements().add(new ClosePath());
    localPath.getElements().add(new MoveTo(0.0D, paramDouble2));
    localPath.getElements().add(new LineTo(paramDouble1, paramDouble2));
    localPath.getElements().add(new LineTo(paramDouble1 * 0.5D, 0.0D));
    localPath.getElements().add(new LineTo(0.0D, paramDouble2));
    localPath.getElements().add(new ClosePath());
    localPath.getStyleClass().add("lcd");
    localPath.setStyle(paramString);
    localPath.getStyleClass().add("lcd-foreground");
    localPath.setStroke(null);
    localGroup.getChildren().addAll(new Node[] { localPath });
    localGroup.setCache(true);
    return localGroup;
  }
  
  protected Group createTrendIndicator(Gauge paramGauge, double paramDouble)
  {
    double d1 = paramDouble;
    double d2 = paramDouble;
    Group localGroup = new Group();
    localGroup.getChildren().clear();
    Rectangle localRectangle = new Rectangle(0.0D, 0.0D, d1, d2);
    localRectangle.setOpacity(0.0D);
    localGroup.getChildren().add(localRectangle);
    Path localPath1 = new Path();
    localPath1.setFillRule(FillRule.EVEN_ODD);
    Path localPath2 = new Path();
    localPath2.setFillRule(FillRule.EVEN_ODD);
    InnerShadow localInnerShadow = new InnerShadow();
    LinearGradient localLinearGradient;
    switch (paramGauge.getTrend())
    {
    case UP: 
      localPath1.getElements().add(new MoveTo(0.2777777777777778D * d1, 0.9444444444444444D * d2));
      localPath1.getElements().add(new LineTo(0.2777777777777778D * d1, 0.5555555555555556D * d2));
      localPath1.getElements().add(new LineTo(0.05555555555555555D * d1, 0.5555555555555556D * d2));
      localPath1.getElements().add(new LineTo(0.5D * d1, 0.0D));
      localPath1.getElements().add(new LineTo(d1, 0.5555555555555556D * d2));
      localPath1.getElements().add(new LineTo(0.7777777777777778D * d1, 0.5555555555555556D * d2));
      localPath1.getElements().add(new LineTo(0.7777777777777778D * d1, 0.9444444444444444D * d2));
      localPath1.getElements().add(new ClosePath());
      localPath1.setFill(paramGauge.getTrendUpColor().darker());
      localPath1.setStroke(null);
      localPath2.getElements().add(new MoveTo(0.3333333333333333D * d1, 0.8888888888888888D * d2));
      localPath2.getElements().add(new LineTo(0.3333333333333333D * d1, 0.5D * d2));
      localPath2.getElements().add(new LineTo(0.16666666666666666D * d1, 0.5D * d2));
      localPath2.getElements().add(new LineTo(0.5D * d1, 0.05555555555555555D * d2));
      localPath2.getElements().add(new LineTo(0.8888888888888888D * d1, 0.5D * d2));
      localPath2.getElements().add(new LineTo(0.7222222222222222D * d1, 0.5D * d2));
      localPath2.getElements().add(new LineTo(0.7222222222222222D * d1, 0.8888888888888888D * d2));
      localPath2.getElements().add(new LineTo(0.3333333333333333D * d1, 0.8888888888888888D * d2));
      localPath2.getElements().add(new ClosePath());
      localLinearGradient = new LinearGradient(0.5D * d1, 0.05555555555555555D * d2, 0.6696079958902622D * d1, 0.928113051953479D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, paramGauge.getTrendUpColor().brighter()), new Stop(1.0D, paramGauge.getTrendUpColor()) });
      localPath2.setFill(localLinearGradient);
      localPath2.setStroke(null);
      localInnerShadow.setWidth(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setHeight(0.19999999999999998D * localPath2.getLayoutBounds().getHeight());
      localInnerShadow.setOffsetX(0.1021392590825304D * paramDouble);
      localInnerShadow.setOffsetY(0.0857050146248719D * paramDouble);
      localInnerShadow.setRadius(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.65D));
      localInnerShadow.setBlurType(BlurType.GAUSSIAN);
      localPath2.setEffect(localInnerShadow);
      break;
    case RISING: 
      localPath1.getElements().add(new MoveTo(0.3888888888888889D * d1, 0.9444444444444444D * d2));
      localPath1.getElements().add(new LineTo(0.6666666666666666D * d1, 0.6666666666666666D * d2));
      localPath1.getElements().add(new LineTo(0.8888888888888888D * d1, 0.8888888888888888D * d2));
      localPath1.getElements().add(new LineTo(0.8888888888888888D * d1, 0.1111111111111111D * d2));
      localPath1.getElements().add(new LineTo(0.1111111111111111D * d1, 0.1111111111111111D * d2));
      localPath1.getElements().add(new LineTo(0.3333333333333333D * d1, 0.3333333333333333D * d2));
      localPath1.getElements().add(new LineTo(0.05555555555555555D * d1, 0.6111111111111112D * d2));
      localPath1.getElements().add(new LineTo(0.3888888888888889D * d1, 0.9444444444444444D * d2));
      localPath1.getElements().add(new ClosePath());
      localPath1.setFill(paramGauge.getTrendRisingColor().darker());
      localPath1.setStroke(null);
      localPath2.getElements().add(new MoveTo(0.3888888888888889D * d1, 0.8888888888888888D * d2));
      localPath2.getElements().add(new LineTo(0.6666666666666666D * d1, 0.6111111111111112D * d2));
      localPath2.getElements().add(new LineTo(0.8333333333333334D * d1, 0.7777777777777778D * d2));
      localPath2.getElements().add(new LineTo(0.8333333333333334D * d1, 0.16666666666666666D * d2));
      localPath2.getElements().add(new LineTo(0.2222222222222222D * d1, 0.16666666666666666D * d2));
      localPath2.getElements().add(new LineTo(0.3888888888888889D * d1, 0.3333333333333333D * d2));
      localPath2.getElements().add(new LineTo(0.1111111111111111D * d1, 0.6111111111111112D * d2));
      localPath2.getElements().add(new LineTo(0.3888888888888889D * d1, 0.8888888888888888D * d2));
      localPath2.getElements().add(new ClosePath());
      localLinearGradient = new LinearGradient(0.8333333333333334D * d1, 0.16666666666666666D * d2, 0.33182081403995967D * d1, 0.8321962583727439D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, paramGauge.getTrendRisingColor().brighter()), new Stop(1.0D, paramGauge.getTrendRisingColor()) });
      localPath2.setFill(localLinearGradient);
      localPath2.setStroke(null);
      localInnerShadow.setWidth(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setHeight(0.19999999999999998D * localPath2.getLayoutBounds().getHeight());
      localInnerShadow.setOffsetX(0.045602685776755844D * paramDouble);
      localInnerShadow.setOffsetY(0.1252923494381211D * paramDouble);
      localInnerShadow.setRadius(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.65D));
      localInnerShadow.setBlurType(BlurType.GAUSSIAN);
      localPath2.setEffect(localInnerShadow);
      break;
    case STEADY: 
      localPath1.setFillRule(FillRule.EVEN_ODD);
      localPath1.getElements().add(new MoveTo(0.05555555555555555D * d1, 0.2777777777777778D * d2));
      localPath1.getElements().add(new LineTo(0.4444444444444444D * d1, 0.2777777777777778D * d2));
      localPath1.getElements().add(new LineTo(0.4444444444444444D * d1, 0.05555555555555555D * d2));
      localPath1.getElements().add(new LineTo(d1, 0.5D * d2));
      localPath1.getElements().add(new LineTo(0.4444444444444444D * d1, d2));
      localPath1.getElements().add(new LineTo(0.4444444444444444D * d1, 0.7777777777777778D * d2));
      localPath1.getElements().add(new LineTo(0.05555555555555555D * d1, 0.7777777777777778D * d2));
      localPath1.getElements().add(new ClosePath());
      localPath1.setFill(paramGauge.getTrendSteadyColor().darker());
      localPath1.setStroke(null);
      localPath2.getElements().add(new MoveTo(0.1111111111111111D * d1, 0.3333333333333333D * d2));
      localPath2.getElements().add(new LineTo(0.5D * d1, 0.3333333333333333D * d2));
      localPath2.getElements().add(new LineTo(0.5D * d1, 0.16666666666666666D * d2));
      localPath2.getElements().add(new LineTo(0.8888888888888888D * d1, 0.5D * d2));
      localPath2.getElements().add(new LineTo(0.5D * d1, 0.8888888888888888D * d2));
      localPath2.getElements().add(new LineTo(0.5D * d1, 0.7222222222222222D * d2));
      localPath2.getElements().add(new LineTo(0.1111111111111111D * d1, 0.7222222222222222D * d2));
      localPath2.getElements().add(new LineTo(0.1111111111111111D * d1, 0.3333333333333333D * d2));
      localPath2.getElements().add(new ClosePath());
      localLinearGradient = new LinearGradient(0.5D * d1, 0.1111111111111111D * d2, 0.5D * d1, 0.8888888888888888D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, paramGauge.getTrendSteadyColor().brighter()), new Stop(1.0D, paramGauge.getTrendSteadyColor()) });
      localPath2.setFill(localLinearGradient);
      localPath2.setStroke(null);
      localInnerShadow.setWidth(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setHeight(0.19999999999999998D * localPath2.getLayoutBounds().getHeight());
      localInnerShadow.setOffsetX(8.164311994315688E-18D * paramDouble);
      localInnerShadow.setOffsetY(0.13333333333333333D * paramDouble);
      localInnerShadow.setRadius(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.65D));
      localInnerShadow.setBlurType(BlurType.GAUSSIAN);
      localPath2.setEffect(localInnerShadow);
      break;
    case FALLING: 
      localPath1.getElements().add(new MoveTo(0.3888888888888889D * d1, 0.05555555555555555D * d2));
      localPath1.getElements().add(new LineTo(0.6666666666666666D * d1, 0.3333333333333333D * d2));
      localPath1.getElements().add(new LineTo(0.8888888888888888D * d1, 0.1111111111111111D * d2));
      localPath1.getElements().add(new LineTo(0.8888888888888888D * d1, 0.8888888888888888D * d2));
      localPath1.getElements().add(new LineTo(0.1111111111111111D * d1, 0.8888888888888888D * d2));
      localPath1.getElements().add(new LineTo(0.3333333333333333D * d1, 0.6666666666666666D * d2));
      localPath1.getElements().add(new LineTo(0.05555555555555555D * d1, 0.3888888888888889D * d2));
      localPath1.getElements().add(new LineTo(0.3888888888888889D * d1, 0.05555555555555555D * d2));
      localPath1.getElements().add(new ClosePath());
      localPath1.setFill(paramGauge.getTrendFallingColor().darker());
      localPath1.setStroke(null);
      localPath2.getElements().add(new MoveTo(0.3888888888888889D * d1, 0.1111111111111111D * d2));
      localPath2.getElements().add(new LineTo(0.6666666666666666D * d1, 0.3888888888888889D * d2));
      localPath2.getElements().add(new LineTo(0.8333333333333334D * d1, 0.2222222222222222D * d2));
      localPath2.getElements().add(new LineTo(0.8333333333333334D * d1, 0.8333333333333334D * d2));
      localPath2.getElements().add(new LineTo(0.2222222222222222D * d1, 0.8333333333333334D * d2));
      localPath2.getElements().add(new LineTo(0.3888888888888889D * d1, 0.6666666666666666D * d2));
      localPath2.getElements().add(new LineTo(0.1111111111111111D * d1, 0.3888888888888889D * d2));
      localPath2.getElements().add(new LineTo(0.3888888888888889D * d1, 0.1111111111111111D * d2));
      localPath2.getElements().add(new ClosePath());
      localLinearGradient = new LinearGradient(0.2222222222222222D * d1, 0.2222222222222222D * d2, 0.8507615832769312D * d1, 0.8507615832769311D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, paramGauge.getTrendFallingColor().brighter()), new Stop(1.0D, paramGauge.getTrendFallingColor()) });
      localPath2.setFill(localLinearGradient);
      localPath2.setStroke(null);
      localInnerShadow.setWidth(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setHeight(0.19999999999999998D * localPath2.getLayoutBounds().getHeight());
      localInnerShadow.setOffsetX(0.0D);
      localInnerShadow.setOffsetY(0.13333333333333333D * paramDouble);
      localInnerShadow.setRadius(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.65D));
      localInnerShadow.setBlurType(BlurType.GAUSSIAN);
      localPath2.setEffect(localInnerShadow);
      break;
    case DOWN: 
      localPath1.getElements().add(new MoveTo(0.2777777777777778D * d1, 0.05555555555555555D * d2));
      localPath1.getElements().add(new LineTo(0.2777777777777778D * d1, 0.4444444444444444D * d2));
      localPath1.getElements().add(new LineTo(0.05555555555555555D * d1, 0.4444444444444444D * d2));
      localPath1.getElements().add(new LineTo(0.5D * d1, d2));
      localPath1.getElements().add(new LineTo(d1, 0.4444444444444444D * d2));
      localPath1.getElements().add(new LineTo(0.7777777777777778D * d1, 0.4444444444444444D * d2));
      localPath1.getElements().add(new LineTo(0.7777777777777778D * d1, 0.05555555555555555D * d2));
      localPath1.getElements().add(new ClosePath());
      localPath1.setFill(paramGauge.getTrendDownColor().darker());
      localPath1.setStroke(null);
      localPath2.getElements().add(new MoveTo(0.3333333333333333D * d1, 0.1111111111111111D * d2));
      localPath2.getElements().add(new LineTo(0.3333333333333333D * d1, 0.5D * d2));
      localPath2.getElements().add(new LineTo(0.16666666666666666D * d1, 0.5D * d2));
      localPath2.getElements().add(new LineTo(0.5D * d1, 0.8888888888888888D * d2));
      localPath2.getElements().add(new LineTo(0.8888888888888888D * d1, 0.5D * d2));
      localPath2.getElements().add(new LineTo(0.7222222222222222D * d1, 0.5D * d2));
      localPath2.getElements().add(new LineTo(0.7222222222222222D * d1, 0.1111111111111111D * d2));
      localPath2.getElements().add(new LineTo(0.3333333333333333D * d1, 0.1111111111111111D * d2));
      localPath2.getElements().add(new ClosePath());
      localLinearGradient = new LinearGradient(0.5D * d1, 0.05555555555555555D * d2, 0.5D * d1, 0.9444444444444444D * d2, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, paramGauge.getTrendDownColor().brighter()), new Stop(1.0D, paramGauge.getTrendDownColor()) });
      localPath2.setFill(localLinearGradient);
      localPath2.setStroke(null);
      localInnerShadow.setWidth(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setHeight(0.19999999999999998D * localPath2.getLayoutBounds().getHeight());
      localInnerShadow.setOffsetX(0.06666666666666668D * paramDouble);
      localInnerShadow.setOffsetY(0.11547005383792514D * paramDouble);
      localInnerShadow.setRadius(0.19999999999999998D * localPath2.getLayoutBounds().getWidth());
      localInnerShadow.setColor(Color.color(1.0D, 1.0D, 1.0D, 0.65D));
      localInnerShadow.setBlurType(BlurType.GAUSSIAN);
      localPath2.setEffect(localInnerShadow);
    }
    localGroup.getChildren().addAll(new Node[] { localPath1, localPath2 });
    return localGroup;
  }
  
  public final Canvas createAlertIndicatorCanvas(double paramDouble1, double paramDouble2, Paint paramPaint)
  {
    Canvas localCanvas = new Canvas(paramDouble1, paramDouble2);
    GraphicsContext localGraphicsContext = localCanvas.getGraphicsContext2D();
    localGraphicsContext.save();
    localGraphicsContext.beginPath();
    localGraphicsContext.moveTo(0.45161290322580644D * paramDouble1, 0.8518518518518519D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.45161290322580644D * paramDouble1, 0.8148148148148148D * paramDouble2, 0.4838709677419355D * paramDouble1, 0.7777777777777778D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.7777777777777778D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.5161290322580645D * paramDouble1, 0.7777777777777778D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.8148148148148148D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.8518518518518519D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.5483870967741935D * paramDouble1, 0.8518518518518519D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.8888888888888888D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.8888888888888888D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.4838709677419355D * paramDouble1, 0.8888888888888888D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.8518518518518519D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.8518518518518519D * paramDouble2);
    localGraphicsContext.closePath();
    localGraphicsContext.moveTo(0.45161290322580644D * paramDouble1, 0.3333333333333333D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.45161290322580644D * paramDouble1, 0.2962962962962963D * paramDouble2, 0.4838709677419355D * paramDouble1, 0.25925925925925924D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.25925925925925924D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.5161290322580645D * paramDouble1, 0.25925925925925924D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.2962962962962963D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.3333333333333333D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.5483870967741935D * paramDouble1, 0.3333333333333333D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.6666666666666666D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.6666666666666666D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.5483870967741935D * paramDouble1, 0.6666666666666666D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.7037037037037037D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.7037037037037037D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.4838709677419355D * paramDouble1, 0.7037037037037037D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.6666666666666666D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.6666666666666666D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.45161290322580644D * paramDouble1, 0.6666666666666666D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.3333333333333333D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.3333333333333333D * paramDouble2);
    localGraphicsContext.closePath();
    localGraphicsContext.moveTo(0.41935483870967744D * paramDouble1, 0.1111111111111111D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.41935483870967744D * paramDouble1, 0.1111111111111111D * paramDouble2, 0.06451612903225806D * paramDouble1, 0.8148148148148148D * paramDouble2, 0.06451612903225806D * paramDouble1, 0.8148148148148148D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.03225806451612903D * paramDouble1, 0.8888888888888888D * paramDouble2, 0.06451612903225806D * paramDouble1, 0.9629629629629629D * paramDouble2, 0.16129032258064516D * paramDouble1, 0.9629629629629629D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.16129032258064516D * paramDouble1, 0.9629629629629629D * paramDouble2, 0.8387096774193549D * paramDouble1, 0.9629629629629629D * paramDouble2, 0.8387096774193549D * paramDouble1, 0.9629629629629629D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.9354838709677419D * paramDouble1, 0.9629629629629629D * paramDouble2, 0.967741935483871D * paramDouble1, 0.8888888888888888D * paramDouble2, 0.9032258064516129D * paramDouble1, 0.8148148148148148D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.9032258064516129D * paramDouble1, 0.8148148148148148D * paramDouble2, 0.5806451612903226D * paramDouble1, 0.1111111111111111D * paramDouble2, 0.5806451612903226D * paramDouble1, 0.1111111111111111D * paramDouble2);
    localGraphicsContext.bezierCurveTo(0.5161290322580645D * paramDouble1, 0.037037037037037035D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.037037037037037035D * paramDouble2, 0.41935483870967744D * paramDouble1, 0.1111111111111111D * paramDouble2);
    localGraphicsContext.closePath();
    localGraphicsContext.setFill(paramPaint);
    localGraphicsContext.fill();
    localGraphicsContext.restore();
    return localCanvas;
  }
  
  public final Shape createAlertIndicator(double paramDouble1, double paramDouble2, Paint paramPaint)
  {
    Path localPath = new Path();
    localPath.setFillRule(FillRule.EVEN_ODD);
    localPath.getElements().add(new MoveTo(0.45161290322580644D * paramDouble1, 0.8518518518518519D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.45161290322580644D * paramDouble1, 0.8148148148148148D * paramDouble2, 0.4838709677419355D * paramDouble1, 0.7777777777777778D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.7777777777777778D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.5161290322580645D * paramDouble1, 0.7777777777777778D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.8148148148148148D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.8518518518518519D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.5483870967741935D * paramDouble1, 0.8518518518518519D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.8888888888888888D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.8888888888888888D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.4838709677419355D * paramDouble1, 0.8888888888888888D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.8518518518518519D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.8518518518518519D * paramDouble2));
    localPath.getElements().add(new ClosePath());
    localPath.getElements().add(new MoveTo(0.45161290322580644D * paramDouble1, 0.3333333333333333D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.45161290322580644D * paramDouble1, 0.2962962962962963D * paramDouble2, 0.4838709677419355D * paramDouble1, 0.25925925925925924D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.25925925925925924D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.5161290322580645D * paramDouble1, 0.25925925925925924D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.2962962962962963D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.3333333333333333D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.5483870967741935D * paramDouble1, 0.3333333333333333D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.6666666666666666D * paramDouble2, 0.5483870967741935D * paramDouble1, 0.6666666666666666D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.5483870967741935D * paramDouble1, 0.6666666666666666D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.7037037037037037D * paramDouble2, 0.5161290322580645D * paramDouble1, 0.7037037037037037D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.4838709677419355D * paramDouble1, 0.7037037037037037D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.6666666666666666D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.6666666666666666D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.45161290322580644D * paramDouble1, 0.6666666666666666D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.3333333333333333D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.3333333333333333D * paramDouble2));
    localPath.getElements().add(new ClosePath());
    localPath.getElements().add(new MoveTo(0.41935483870967744D * paramDouble1, 0.1111111111111111D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.41935483870967744D * paramDouble1, 0.1111111111111111D * paramDouble2, 0.06451612903225806D * paramDouble1, 0.8148148148148148D * paramDouble2, 0.06451612903225806D * paramDouble1, 0.8148148148148148D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.03225806451612903D * paramDouble1, 0.8888888888888888D * paramDouble2, 0.06451612903225806D * paramDouble1, 0.9629629629629629D * paramDouble2, 0.16129032258064516D * paramDouble1, 0.9629629629629629D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.16129032258064516D * paramDouble1, 0.9629629629629629D * paramDouble2, 0.8387096774193549D * paramDouble1, 0.9629629629629629D * paramDouble2, 0.8387096774193549D * paramDouble1, 0.9629629629629629D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.9354838709677419D * paramDouble1, 0.9629629629629629D * paramDouble2, 0.967741935483871D * paramDouble1, 0.8888888888888888D * paramDouble2, 0.9032258064516129D * paramDouble1, 0.8148148148148148D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.9032258064516129D * paramDouble1, 0.8148148148148148D * paramDouble2, 0.5806451612903226D * paramDouble1, 0.1111111111111111D * paramDouble2, 0.5806451612903226D * paramDouble1, 0.1111111111111111D * paramDouble2));
    localPath.getElements().add(new CubicCurveTo(0.5161290322580645D * paramDouble1, 0.037037037037037035D * paramDouble2, 0.45161290322580644D * paramDouble1, 0.037037037037037035D * paramDouble2, 0.41935483870967744D * paramDouble1, 0.1111111111111111D * paramDouble2));
    localPath.getElements().add(new ClosePath());
    localPath.setFill(paramPaint);
    localPath.setStroke(null);
    return localPath;
  }
  
  protected void addDropShadow(Control paramControl, Node... paramVarArgs)
  {
    if (paramVarArgs.length == 0) {
      return;
    }
    double d = paramControl.getPrefWidth() < paramControl.getPrefHeight() ? paramControl.getPrefWidth() : paramControl.getPrefHeight();
    Lighting localLighting = new Lighting();
    Light.Distant localDistant = new Light.Distant();
    localDistant.setAzimuth(270.0D);
    localDistant.setElevation(60.0D);
    localLighting.setLight(localDistant);
    DropShadow localDropShadow = new DropShadow();
    localDropShadow.setInput(localLighting);
    localDropShadow.setOffsetY(0.0075D * d);
    localDropShadow.setRadius(0.0075D * d);
    localDropShadow.setBlurType(BlurType.GAUSSIAN);
    localDropShadow.setColor(Color.color(0.0D, 0.0D, 0.0D, 0.45D));
    for (Node localNode : paramVarArgs) {
      localNode.setEffect(localDropShadow);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/GaugeSkinBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */