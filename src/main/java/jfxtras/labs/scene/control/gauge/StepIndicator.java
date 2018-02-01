package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class StepIndicator
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "step-indicator";
  private ObjectProperty<EventHandler<StepEvent>> onStepEvent = new SimpleObjectProperty();
  private ObjectProperty<Color> color;
  private IntegerProperty noOfSteps;
  private IntegerProperty currentStep;
  private int selectedStep;
  
  public StepIndicator()
  {
    this(Color.rgb(138, 205, 250));
  }
  
  public StepIndicator(Color paramColor)
  {
    this.color = new SimpleObjectProperty(paramColor);
    this.noOfSteps = new SimpleIntegerProperty(5);
    this.currentStep = new SimpleIntegerProperty(0);
    getStyleClass().add("step-indicator");
  }
  
  public final Color getColor()
  {
    return (Color)this.color.get();
  }
  
  public final void setColor(Color paramColor)
  {
    this.color.set(paramColor);
  }
  
  public final ObjectProperty<Color> colorProperty()
  {
    return this.color;
  }
  
  public final int getNoOfSteps()
  {
    return this.noOfSteps.get();
  }
  
  public final void setNoOfSteps(int paramInt)
  {
    this.noOfSteps.set(paramInt > 20 ? 20 : paramInt < 1 ? 1 : paramInt);
  }
  
  public final IntegerProperty noOfStepsProperty()
  {
    return this.noOfSteps;
  }
  
  public final int getCurrentStep()
  {
    return this.currentStep.get();
  }
  
  public final void setCurrentStep(int paramInt)
  {
    this.currentStep.set(paramInt > this.noOfSteps.get() ? this.noOfSteps.get() : paramInt < 0 ? 0 : paramInt);
  }
  
  public final IntegerProperty currentStepProperty()
  {
    return this.currentStep;
  }
  
  public final void next()
  {
    if (this.currentStep.get() < this.noOfSteps.get()) {
      this.currentStep.set(this.currentStep.get() + 1);
    }
  }
  
  public final void back()
  {
    if (this.currentStep.get() > 0) {
      this.currentStep.set(this.currentStep.get() - 1);
    }
  }
  
  public final void setSelectedStep(int paramInt)
  {
    this.selectedStep = paramInt;
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 < paramDouble2 ? paramDouble1 : paramDouble2;
    super.setPrefSize(this.noOfSteps.get() * d, d);
  }
  
  public final ObjectProperty<EventHandler<StepEvent>> onStepEventProperty()
  {
    return this.onStepEvent;
  }
  
  public final void setOnStepEvent(EventHandler<StepEvent> paramEventHandler)
  {
    onStepEventProperty().set(paramEventHandler);
  }
  
  public final EventHandler<StepEvent> getOnStepEvent()
  {
    return (EventHandler)onStepEventProperty().get();
  }
  
  public void fireStepEvent()
  {
    if (this.selectedStep > 0)
    {
      EventHandler localEventHandler = getOnStepEvent();
      if (localEventHandler != null)
      {
        StepEvent localStepEvent = new StepEvent(this.selectedStep);
        localEventHandler.handle(localStepEvent);
      }
    }
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
  
  public class StepEvent
    extends Event
  {
    private final int INDEX;
    
    public StepEvent(int paramInt)
    {
      super();
      this.INDEX = paramInt;
    }
    
    public StepEvent(Object paramObject, EventTarget paramEventTarget, int paramInt)
    {
      super(paramEventTarget, new EventType());
      this.INDEX = paramInt;
    }
    
    public final int getIndex()
    {
      return this.INDEX;
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/StepIndicator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */