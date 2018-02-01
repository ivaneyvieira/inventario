package jfxtras.labs.scene.control.gauge;

import java.util.Iterator;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class GaugeModel
{
  private DoubleProperty value = new SimpleDoubleProperty(0.0D);
  private DoubleProperty realValue = new SimpleDoubleProperty(0.0D);
  private BooleanProperty valueAnimationEnabled = new SimpleBooleanProperty(true);
  private DoubleProperty animationDuration = new SimpleDoubleProperty(800.0D);
  private DoubleProperty redrawTolerance = new SimpleDoubleProperty(0.0D);
  private DoubleProperty minMeasuredValue = new SimpleDoubleProperty(100.0D);
  private DoubleProperty maxMeasuredValue = new SimpleDoubleProperty(0.0D);
  private DoubleProperty threshold = new SimpleDoubleProperty(50.0D);
  private BooleanProperty thresholdBehaviorInverted = new SimpleBooleanProperty(false);
  private BooleanProperty thresholdExceeded = new SimpleBooleanProperty(false);
  private StringProperty title = new SimpleStringProperty("");
  private StringProperty unit = new SimpleStringProperty("");
  private DoubleProperty lcdValue = new SimpleDoubleProperty(0.0D);
  private BooleanProperty lcdValueCoupled = new SimpleBooleanProperty(true);
  private DoubleProperty lcdThreshold = new SimpleDoubleProperty(50.0D);
  private BooleanProperty lcdThresholdBehaviorInverted = new SimpleBooleanProperty(false);
  private StringProperty lcdUnit = new SimpleStringProperty("");
  private ObjectProperty<Gauge.NumberSystem> lcdNumberSystem = new SimpleObjectProperty(Gauge.NumberSystem.DECIMAL);
  private ObjectProperty<LinearScale> scale = new SimpleObjectProperty(new LinearScale(0.0D, 100.0D));
  private ObjectProperty<Gauge.Trend> trend = new SimpleObjectProperty(Gauge.Trend.UNKNOWN);
  private ObservableList<Section> sections = FXCollections.observableArrayList();
  private ObservableList<Section> areas = FXCollections.observableArrayList();
  private ObservableList<Section> tickMarkSections = FXCollections.observableArrayList();
  private ObservableList<Marker> markers = FXCollections.observableArrayList();
  private BooleanProperty endlessMode = new SimpleBooleanProperty(false);
  private ObjectProperty<EventHandler<GaugeModelEvent>> onGaugeModelEvent = new SimpleObjectProperty();
  
  public GaugeModel()
  {
    this.sections.addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        GaugeModel.this.fireGaugeModelEvent();
      }
    });
    this.areas.addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        GaugeModel.this.fireGaugeModelEvent();
      }
    });
    this.tickMarkSections.addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        GaugeModel.this.fireGaugeModelEvent();
      }
    });
    this.markers.addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        GaugeModel.this.fireGaugeModelEvent();
      }
    });
  }
  
  public final ObjectProperty<EventHandler<GaugeModelEvent>> onGaugeModelEventProperty()
  {
    return this.onGaugeModelEvent;
  }
  
  public final void setOnGaugeModelEvent(EventHandler<GaugeModelEvent> paramEventHandler)
  {
    onGaugeModelEventProperty().set(paramEventHandler);
  }
  
  public final EventHandler<GaugeModelEvent> getOnGaugeModelEvent()
  {
    return (EventHandler)onGaugeModelEventProperty().get();
  }
  
  public void fireGaugeModelEvent()
  {
    EventHandler localEventHandler = getOnGaugeModelEvent();
    if (localEventHandler != null)
    {
      GaugeModelEvent localGaugeModelEvent = new GaugeModelEvent();
      localEventHandler.handle(localGaugeModelEvent);
    }
  }
  
  public final double getValue()
  {
    return this.value.get();
  }
  
  public final void setValue(double paramDouble)
  {
    if (isEndlessMode())
    {
      this.value.set(paramDouble % getRange());
      this.realValue.set(paramDouble);
    }
    else
    {
      this.value.set(clamp(((LinearScale)this.scale.get()).getNiceMinValue(), ((LinearScale)this.scale.get()).getNiceMaxValue(), paramDouble));
      this.realValue.set(this.value.get());
    }
    fireGaugeModelEvent();
  }
  
  public final DoubleProperty valueProperty()
  {
    return this.value;
  }
  
  public final double getRealValue()
  {
    return this.realValue.get();
  }
  
  public final ReadOnlyDoubleProperty realValueProperty()
  {
    return this.realValue;
  }
  
  public final boolean isValueAnimationEnabled()
  {
    return this.valueAnimationEnabled.get();
  }
  
  public final void setValueAnimationEnabled(boolean paramBoolean)
  {
    this.valueAnimationEnabled.set(paramBoolean);
    fireGaugeModelEvent();
  }
  
  public final BooleanProperty valueAnimationEnabledProperty()
  {
    return this.valueAnimationEnabled;
  }
  
  public final double getAnimationDuration()
  {
    return this.animationDuration.get();
  }
  
  public final void setAnimationDuration(double paramDouble)
  {
    this.animationDuration.set(paramDouble);
    fireGaugeModelEvent();
  }
  
  public final DoubleProperty animationDurationProperty()
  {
    return this.animationDuration;
  }
  
  public final double getRedrawTolerance()
  {
    return this.redrawTolerance.get();
  }
  
  public final void setRedrawTolerance(double paramDouble)
  {
    this.redrawTolerance.set(clamp(0.0D, 1.0D, paramDouble));
  }
  
  public final DoubleProperty redrawToleranceProperty()
  {
    return this.redrawTolerance;
  }
  
  public final double getRedrawToleranceValue()
  {
    return redrawToleranceProperty().multiply(rangeProperty()).doubleValue();
  }
  
  public final double getMinValue()
  {
    return ((LinearScale)this.scale.get()).getMinValue();
  }
  
  public final void setMinValue(double paramDouble)
  {
    ((LinearScale)this.scale.get()).setMinValue(paramDouble);
    ((LinearScale)this.scale.get()).setUncorrectedMinValue(paramDouble);
    fireGaugeModelEvent();
  }
  
  public final ReadOnlyDoubleProperty minValueProperty()
  {
    return ((LinearScale)this.scale.get()).minValueProperty();
  }
  
  public final double getUncorrectedMinValue()
  {
    return ((LinearScale)this.scale.get()).getUncorrectedMinValue();
  }
  
  public final double getMaxValue()
  {
    return ((LinearScale)this.scale.get()).getMaxValue();
  }
  
  public final void setMaxValue(double paramDouble)
  {
    ((LinearScale)this.scale.get()).setMaxValue(paramDouble);
    ((LinearScale)this.scale.get()).setUncorrectedMaxValue(paramDouble);
    fireGaugeModelEvent();
  }
  
  public final ReadOnlyDoubleProperty maxValueProperty()
  {
    return ((LinearScale)this.scale.get()).maxValueProperty();
  }
  
  public final double getUncorrectedMaxValue()
  {
    return ((LinearScale)this.scale.get()).getUncorrectedMaxValue();
  }
  
  public final double getRange()
  {
    return ((LinearScale)this.scale.get()).getRange();
  }
  
  public final ReadOnlyDoubleProperty rangeProperty()
  {
    return ((LinearScale)this.scale.get()).rangeProperty();
  }
  
  public final double getMinMeasuredValue()
  {
    return this.minMeasuredValue.get();
  }
  
  public final void setMinMeasuredValue(double paramDouble)
  {
    this.minMeasuredValue.set(paramDouble);
    fireGaugeModelEvent();
  }
  
  public final DoubleProperty minMeasuredValueProperty()
  {
    return this.minMeasuredValue;
  }
  
  public final void resetMinMeasuredValue()
  {
    setMinMeasuredValue(getValue());
  }
  
  public final double getMaxMeasuredValue()
  {
    return this.maxMeasuredValue.get();
  }
  
  public final void setMaxMeasuredValue(double paramDouble)
  {
    this.maxMeasuredValue.set(paramDouble);
    fireGaugeModelEvent();
  }
  
  public final DoubleProperty maxMeasuredValueProperty()
  {
    return this.maxMeasuredValue;
  }
  
  public final void resetMaxMeasuredValue()
  {
    setMaxMeasuredValue(getValue());
  }
  
  public final void resetMinMaxMeasuredValue()
  {
    setMinMeasuredValue(getValue());
    setMaxMeasuredValue(getValue());
  }
  
  public final double getThreshold()
  {
    return this.threshold.get();
  }
  
  public final void setThreshold(double paramDouble)
  {
    this.threshold.set(Double.compare(paramDouble, ((LinearScale)this.scale.get()).getNiceMaxValue()) > 0 ? ((LinearScale)this.scale.get()).getNiceMaxValue() : Double.compare(paramDouble, ((LinearScale)this.scale.get()).getNiceMinValue()) < 0 ? ((LinearScale)this.scale.get()).getNiceMinValue() : paramDouble);
    fireGaugeModelEvent();
  }
  
  public final DoubleProperty thresholdProperty()
  {
    return this.threshold;
  }
  
  public final boolean isThresholdBehaviorInverted()
  {
    return this.thresholdBehaviorInverted.get();
  }
  
  public final void setThresholdBehaviorInverted(boolean paramBoolean)
  {
    this.thresholdBehaviorInverted.set(paramBoolean);
    fireGaugeModelEvent();
  }
  
  public final BooleanProperty thresholdBehaviorInvertedProperty()
  {
    return this.thresholdBehaviorInverted;
  }
  
  public final boolean isThresholdExceeded()
  {
    return this.thresholdExceeded.get();
  }
  
  public final void setThresholdExceeded(boolean paramBoolean)
  {
    this.thresholdExceeded.set(paramBoolean);
    fireGaugeModelEvent();
  }
  
  public final BooleanProperty thresholdExceededProperty()
  {
    return this.thresholdExceeded;
  }
  
  public final String getTitle()
  {
    return (String)this.title.get();
  }
  
  public final void setTitle(String paramString)
  {
    this.title.set(paramString);
    fireGaugeModelEvent();
  }
  
  public final StringProperty titleProperty()
  {
    return this.title;
  }
  
  public final String getUnit()
  {
    return (String)this.unit.get();
  }
  
  public final void setUnit(String paramString)
  {
    this.unit.set(paramString);
    fireGaugeModelEvent();
  }
  
  public final StringProperty unitProperty()
  {
    return this.unit;
  }
  
  public final double getLcdValue()
  {
    return this.lcdValue.get();
  }
  
  public final void setLcdValue(double paramDouble)
  {
    this.lcdValue.set(paramDouble);
    fireGaugeModelEvent();
  }
  
  public final DoubleProperty lcdValueProperty()
  {
    return this.lcdValue;
  }
  
  public final boolean isLcdValueCoupled()
  {
    return this.lcdValueCoupled.get();
  }
  
  public final void setLcdValueCoupled(boolean paramBoolean)
  {
    this.lcdValueCoupled.set(paramBoolean);
    fireGaugeModelEvent();
  }
  
  public final BooleanProperty lcdValueCoupledProperty()
  {
    return this.lcdValueCoupled;
  }
  
  public final double getLcdThreshold()
  {
    return this.lcdThreshold.get();
  }
  
  public final void setLcdThreshold(double paramDouble)
  {
    this.lcdThreshold.set(paramDouble);
    fireGaugeModelEvent();
  }
  
  public final DoubleProperty lcdThresholdProperty()
  {
    return this.lcdThreshold;
  }
  
  public final boolean isLcdThresholdBehaviorInverted()
  {
    return this.lcdThresholdBehaviorInverted.get();
  }
  
  public final void setLcdThresholdBehaviorInverted(boolean paramBoolean)
  {
    this.lcdThresholdBehaviorInverted.set(paramBoolean);
    fireGaugeModelEvent();
  }
  
  public final BooleanProperty lcdThresholdBehaviorInvertedProperty()
  {
    return this.lcdThresholdBehaviorInverted;
  }
  
  public final String getLcdUnit()
  {
    return (String)this.lcdUnit.get();
  }
  
  public final void setLcdUnit(String paramString)
  {
    this.lcdUnit.set(paramString);
    fireGaugeModelEvent();
  }
  
  public final StringProperty lcdUnitProperty()
  {
    return this.lcdUnit;
  }
  
  public final Gauge.NumberSystem getLcdNumberSystem()
  {
    return (Gauge.NumberSystem)this.lcdNumberSystem.get();
  }
  
  public final void setLcdNumberSystem(Gauge.NumberSystem paramNumberSystem)
  {
    this.lcdNumberSystem.set(paramNumberSystem);
    fireGaugeModelEvent();
  }
  
  public final ObjectProperty lcdNumberSystemProperty()
  {
    return this.lcdNumberSystem;
  }
  
  public final int getMaxNoOfMajorTicks()
  {
    return ((LinearScale)this.scale.get()).getMaxNoOfMajorTicks();
  }
  
  public final void setMaxNoOfMajorTicks(int paramInt)
  {
    ((LinearScale)this.scale.get()).setMaxNoOfMajorTicks(paramInt);
    fireGaugeModelEvent();
  }
  
  public final IntegerProperty maxNoOfMajorTicksProperty()
  {
    return ((LinearScale)this.scale.get()).maxNoOfMajorTicksProperty();
  }
  
  public final int getMaxNoOfMinorTicks()
  {
    return ((LinearScale)this.scale.get()).getMaxNoOfMinorTicks();
  }
  
  public final void setMaxNoOfMinorTicks(int paramInt)
  {
    ((LinearScale)this.scale.get()).setMaxNoOfMinorTicks(paramInt);
    fireGaugeModelEvent();
  }
  
  public final IntegerProperty maxNoOfMinorTicksProperty()
  {
    return ((LinearScale)this.scale.get()).maxNoOfMinorTicksProperty();
  }
  
  public final double getMajorTickSpacing()
  {
    return ((LinearScale)this.scale.get()).getMajorTickSpacing();
  }
  
  public final void setMajorTickSpacing(double paramDouble)
  {
    ((LinearScale)this.scale.get()).setMajorTickSpacing(paramDouble);
  }
  
  public final DoubleProperty majorTickSpacingProperty()
  {
    return ((LinearScale)this.scale.get()).majorTickSpacingProperty();
  }
  
  public final double getMinorTickSpacing()
  {
    return ((LinearScale)this.scale.get()).getMinorTickSpacing();
  }
  
  public final void setMinorTickSpacing(double paramDouble)
  {
    ((LinearScale)this.scale.get()).setMinorTickSpacing(paramDouble);
  }
  
  public final DoubleProperty minorTickSpacingProperty()
  {
    return ((LinearScale)this.scale.get()).minorTickSpacingProperty();
  }
  
  public final Gauge.Trend getTrend()
  {
    return (Gauge.Trend)this.trend.get();
  }
  
  public final void setTrend(Gauge.Trend paramTrend)
  {
    this.trend.set(paramTrend);
    fireGaugeModelEvent();
  }
  
  public final ObjectProperty<Gauge.Trend> trendProperty()
  {
    return this.trend;
  }
  
  public final boolean isNiceScaling()
  {
    return ((LinearScale)this.scale.get()).isNiceScaling();
  }
  
  public final void setNiceScaling(boolean paramBoolean)
  {
    ((LinearScale)this.scale.get()).setNiceScaling(paramBoolean);
    fireGaugeModelEvent();
  }
  
  public final BooleanProperty niceScalingProperty()
  {
    return ((LinearScale)this.scale.get()).niceScalingProperty();
  }
  
  public final boolean isTightScale()
  {
    return ((LinearScale)this.scale.get()).isTightScale();
  }
  
  public final void setTightScale(boolean paramBoolean)
  {
    ((LinearScale)this.scale.get()).setTightScale(paramBoolean);
  }
  
  public final BooleanProperty tightScaleProperty()
  {
    return ((LinearScale)this.scale.get()).tightScaleProperty();
  }
  
  public final double getTightScaleOffset()
  {
    return ((LinearScale)this.scale.get()).getTightScaleOffset();
  }
  
  public final boolean isLargeNumberScale()
  {
    return ((LinearScale)this.scale.get()).isLargeNumberScale();
  }
  
  public final void setLargeNumberScale(boolean paramBoolean)
  {
    ((LinearScale)this.scale.get()).setLargeNumberScale(paramBoolean);
  }
  
  public final BooleanProperty largeNumberScaleProperty()
  {
    return ((LinearScale)this.scale.get()).largeNumberScaleProperty();
  }
  
  public final boolean isLastLabelVisible()
  {
    return ((LinearScale)this.scale.get()).isLastLabelVisible();
  }
  
  public final void setLastLabelVisible(boolean paramBoolean)
  {
    ((LinearScale)this.scale.get()).setLastLabelVisible(paramBoolean);
  }
  
  public final BooleanProperty lastLabelVisibleProperty()
  {
    return ((LinearScale)this.scale.get()).lastLabelVisibleProperty();
  }
  
  public final ObservableList<Section> getSections()
  {
    return this.sections;
  }
  
  public final void setSections(Section... paramVarArgs)
  {
    this.sections.setAll(paramVarArgs);
    fireGaugeModelEvent();
  }
  
  public final void setSections(List<Section> paramList)
  {
    this.sections.setAll(paramList);
    fireGaugeModelEvent();
  }
  
  public final void addSection(Section paramSection)
  {
    this.sections.add(new Section(paramSection.getStart(), paramSection.getStop(), paramSection.getColor(), paramSection.getText()));
    fireGaugeModelEvent();
  }
  
  public final void removeSection(Section paramSection)
  {
    Iterator localIterator = this.sections.iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      if (localSection.equals(paramSection))
      {
        this.sections.remove(localSection);
        break;
      }
    }
    fireGaugeModelEvent();
  }
  
  public final void resetSections()
  {
    this.sections.clear();
    fireGaugeModelEvent();
  }
  
  public final ObservableList<Section> getAreas()
  {
    return this.areas;
  }
  
  public final void setAreas(Section... paramVarArgs)
  {
    this.areas.setAll(paramVarArgs);
    fireGaugeModelEvent();
  }
  
  public final void setAreas(List<Section> paramList)
  {
    this.areas.setAll(paramList);
    fireGaugeModelEvent();
  }
  
  public final void addArea(Section paramSection)
  {
    this.areas.add(new Section(paramSection.getStart(), paramSection.getStop(), paramSection.getColor(), paramSection.getText()));
    fireGaugeModelEvent();
  }
  
  public final void removeArea(Section paramSection)
  {
    Iterator localIterator = this.areas.iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      if (localSection.equals(paramSection))
      {
        this.areas.remove(localSection);
        break;
      }
    }
    fireGaugeModelEvent();
  }
  
  public final void resetAreas()
  {
    this.areas.clear();
    fireGaugeModelEvent();
  }
  
  public final ObservableList<Section> getTickMarkSections()
  {
    return this.tickMarkSections;
  }
  
  public final void setTickMarkSections(Section... paramVarArgs)
  {
    this.tickMarkSections.setAll(paramVarArgs);
    fireGaugeModelEvent();
  }
  
  public final void setTickMarkSections(List<Section> paramList)
  {
    this.tickMarkSections.setAll(paramList);
    fireGaugeModelEvent();
  }
  
  public final void addTickMarkSection(Section paramSection)
  {
    this.tickMarkSections.add(new Section(paramSection.getStart(), paramSection.getStop(), paramSection.getColor(), paramSection.getText()));
    fireGaugeModelEvent();
  }
  
  public final void removeTickMarkSection(Section paramSection)
  {
    Iterator localIterator = this.tickMarkSections.iterator();
    while (localIterator.hasNext())
    {
      Section localSection = (Section)localIterator.next();
      if (localSection.equals(paramSection))
      {
        this.tickMarkSections.remove(localSection);
        break;
      }
    }
    fireGaugeModelEvent();
  }
  
  public final void resetTickMarkSections()
  {
    this.tickMarkSections.clear();
    fireGaugeModelEvent();
  }
  
  public final ObservableList<Marker> getMarkers()
  {
    return this.markers;
  }
  
  public final void setMarkers(Marker... paramVarArgs)
  {
    this.markers.setAll(paramVarArgs);
    fireGaugeModelEvent();
  }
  
  public final void setMarkers(List<Marker> paramList)
  {
    this.markers.setAll(paramList);
    fireGaugeModelEvent();
  }
  
  public final void addMarker(Marker paramMarker)
  {
    this.markers.add(new Marker(paramMarker.getValue(), paramMarker.getColor(), paramMarker.getText(), paramMarker.isVisible()));
    fireGaugeModelEvent();
  }
  
  public final void removeMarker(Marker paramMarker)
  {
    Iterator localIterator = this.markers.iterator();
    while (localIterator.hasNext())
    {
      Marker localMarker = (Marker)localIterator.next();
      if (localMarker.equals(paramMarker))
      {
        this.markers.remove(localMarker);
        break;
      }
    }
    fireGaugeModelEvent();
  }
  
  public final void resetMarkers()
  {
    this.markers.clear();
    fireGaugeModelEvent();
  }
  
  public final boolean isEndlessMode()
  {
    return this.endlessMode.get();
  }
  
  public final void setEndlessMode(boolean paramBoolean)
  {
    this.endlessMode.set(paramBoolean);
  }
  
  public final BooleanProperty endlessModeProperty()
  {
    return this.endlessMode;
  }
  
  private double clamp(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return paramDouble3 > paramDouble2 ? paramDouble2 : paramDouble3 < paramDouble1 ? paramDouble1 : paramDouble3;
  }
  
  protected void calcRange()
  {
    if (getMinValue() < getMaxValue()) {
      if (((LinearScale)this.scale.get()).isTightScale()) {
        ((LinearScale)this.scale.get()).calculateTight();
      } else {
        ((LinearScale)this.scale.get()).calculateLoose();
      }
    }
  }
  
  public class GaugeModelEvent
    extends Event
  {
    public GaugeModelEvent()
    {
      super();
    }
    
    public GaugeModelEvent(Object paramObject, EventTarget paramEventTarget)
    {
      super(paramEventTarget, new EventType());
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/GaugeModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */