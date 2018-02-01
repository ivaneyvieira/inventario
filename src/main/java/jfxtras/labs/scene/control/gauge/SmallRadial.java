package jfxtras.labs.scene.control.gauge;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public class SmallRadial
  extends Gauge
{
  private static final String DEFAULT_STYLE_CLASS = "small-radial";
  private ObjectProperty<Color> valueLabelColor;
  private BooleanProperty valueLabelVisible;
  private IntegerProperty noOfDecimals;
  private DoubleProperty timeToValueInMs;
  private ObjectProperty<Color> frameColor;
  private ObjectProperty<Color> backgroundColor;
  private ObjectProperty<Color> tickMarkColor;
  private ObjectProperty<Color> pointerColor;
  private ObjectProperty<Color> centerKnobColor;
  private ObjectProperty<Color> thresholdLedColor;
  private BooleanProperty pointerShadowVisible;
  
  public SmallRadial()
  {
    this(new GaugeModel(), new StyleModel());
  }
  
  public SmallRadial(GaugeModel paramGaugeModel)
  {
    this(paramGaugeModel, new StyleModel());
  }
  
  public SmallRadial(GaugeModel paramGaugeModel, StyleModel paramStyleModel)
  {
    super(paramGaugeModel, paramStyleModel);
    setRadialRange(RadialRange.RADIAL_280);
    this.valueLabelColor = new SimpleObjectProperty(Color.BLACK);
    this.valueLabelVisible = new SimpleBooleanProperty(true);
    this.noOfDecimals = new SimpleIntegerProperty(2);
    this.frameColor = new SimpleObjectProperty(Color.rgb(110, 110, 110));
    this.backgroundColor = new SimpleObjectProperty(Color.rgb(220, 220, 220));
    this.tickMarkColor = new SimpleObjectProperty(Color.BLACK);
    this.pointerColor = new SimpleObjectProperty(Color.RED);
    this.centerKnobColor = new SimpleObjectProperty(Color.rgb(110, 110, 110));
    this.thresholdLedColor = new SimpleObjectProperty(Color.RED);
    this.pointerShadowVisible = new SimpleBooleanProperty(false);
    this.timeToValueInMs = new SimpleDoubleProperty(1500.0D);
    getStyleClass().add("small-radial");
  }
  
  public final RadialRange getRadialRange()
  {
    return RadialRange.RADIAL_280;
  }
  
  public final Color getValueLabelColor()
  {
    return (Color)this.valueLabelColor.get();
  }
  
  public final void setValueLabelColor(Color paramColor)
  {
    this.valueLabelColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> labelColorProperty()
  {
    return this.valueLabelColor;
  }
  
  public final boolean isValueLabelVisible()
  {
    return this.valueLabelVisible.get();
  }
  
  public final void setValueLabelVisible(boolean paramBoolean)
  {
    this.valueLabelVisible.set(paramBoolean);
  }
  
  public final BooleanProperty valueLabelVisibleProperty()
  {
    return this.valueLabelVisible;
  }
  
  public final int getNoOfDecimals()
  {
    return this.noOfDecimals.get();
  }
  
  public final void setNoOfDecimals(int paramInt)
  {
    int i = paramInt > 5 ? 5 : paramInt < 0 ? 0 : paramInt;
    this.noOfDecimals.set(i);
  }
  
  public final ReadOnlyIntegerProperty noOfDecimalsProperty()
  {
    return this.noOfDecimals;
  }
  
  public final void setSections(Section... paramVarArgs)
  {
    getGaugeModel().setSections(paramVarArgs);
  }
  
  public final void setSections(List<Section> paramList)
  {
    getGaugeModel().setSections(paramList);
  }
  
  public final Color getFrameColor()
  {
    return (Color)this.frameColor.get();
  }
  
  public final void setFrameColor(Color paramColor)
  {
    this.frameColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> frameColorProperty()
  {
    return this.frameColor;
  }
  
  public final Color getBackgroundColor()
  {
    return (Color)this.backgroundColor.get();
  }
  
  public final void setBackgroundColor(Color paramColor)
  {
    this.backgroundColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> backgroundColorProperty()
  {
    return this.backgroundColor;
  }
  
  public final Color getTickMarkColor()
  {
    return (Color)this.tickMarkColor.get();
  }
  
  public final void setTickMarkColor(Color paramColor)
  {
    this.tickMarkColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> tickMarkColorProperty()
  {
    return this.tickMarkColor;
  }
  
  public final Color getPointerColor()
  {
    return (Color)this.pointerColor.get();
  }
  
  public final void setPointerColor(Color paramColor)
  {
    this.pointerColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> pointerColorProperty()
  {
    return this.pointerColor;
  }
  
  public final Color getCenterKnobColor()
  {
    return (Color)this.centerKnobColor.get();
  }
  
  public final void setCenterKnobColor(Color paramColor)
  {
    this.centerKnobColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> centerKnobColorProperty()
  {
    return this.centerKnobColor;
  }
  
  public final Color getThresholdLedColor()
  {
    return (Color)this.thresholdLedColor.get();
  }
  
  public final void setThresholdLedColor(Color paramColor)
  {
    this.thresholdLedColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> thresholdLedColorProperty()
  {
    return this.thresholdLedColor;
  }
  
  public final boolean isPointerShadowVisible()
  {
    return this.pointerShadowVisible.get();
  }
  
  public final void setPointerShadowVisible(boolean paramBoolean)
  {
    this.pointerShadowVisible.set(paramBoolean);
  }
  
  public final BooleanProperty pointerShadowVisibleProperty()
  {
    return this.pointerShadowVisible;
  }
  
  public final double getTimeToValueInMs()
  {
    return this.timeToValueInMs.get();
  }
  
  public final void setTimeToValueInMs(double paramDouble)
  {
    double d = paramDouble > 5000.0D ? 5000.0D : paramDouble < 10.0D ? 10.0D : paramDouble;
    this.timeToValueInMs.set(d);
  }
  
  public final DoubleProperty timeToValueInMsProperty()
  {
    return this.timeToValueInMs;
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 < paramDouble2 * 1.0D ? paramDouble1 * 1.0D : paramDouble2;
    super.setPrefSize(d, d);
  }
  
  public void setMinSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 < paramDouble2 * 1.0D ? paramDouble1 * 1.0D : paramDouble2;
    d = d < 30.0D ? 30.0D : d;
    super.setPrefSize(d, d);
  }
  
  public void setMaxSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 < paramDouble2 * 1.0D ? paramDouble1 * 1.0D : paramDouble2;
    d = d > 500.0D ? 500.0D : d;
    super.setPrefSize(d, d);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SmallRadial.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */