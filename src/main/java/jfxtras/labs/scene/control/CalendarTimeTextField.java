package jfxtras.labs.scene.control;

import java.net.URL;
import java.util.Calendar;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;

public class CalendarTimeTextField
  extends Control
{
  private final ObjectProperty<Calendar> valueObjectProperty = new SimpleObjectProperty(this, "value", null);
  private final SimpleObjectProperty<Integer> minuteStepProperty = new SimpleObjectProperty(this, "minuteStep", Integer.valueOf(1));
  private final SimpleObjectProperty<Boolean> showLabelsProperty = new SimpleObjectProperty(this, "showLabels", Boolean.valueOf(true));
  private final ObjectProperty<String> promptTextObjectProperty = new SimpleObjectProperty(this, "promptText", null);
  
  public CalendarTimeTextField()
  {
    construct();
  }
  
  private void construct()
  {
    getStyleClass().add(getClass().getSimpleName());
    setFocusTraversable(false);
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + getClass().getSimpleName() + ".css").toString();
  }
  
  public ObjectProperty<Calendar> valueProperty()
  {
    return this.valueObjectProperty;
  }
  
  public Calendar getValue()
  {
    return (Calendar)this.valueObjectProperty.getValue();
  }
  
  public void setValue(Calendar paramCalendar)
  {
    this.valueObjectProperty.setValue(paramCalendar);
  }
  
  public CalendarTimeTextField withValue(Calendar paramCalendar)
  {
    setValue(paramCalendar);
    return this;
  }
  
  public ObjectProperty<Integer> minuteStepProperty()
  {
    return this.minuteStepProperty;
  }
  
  public Integer getMinuteStep()
  {
    return (Integer)this.minuteStepProperty.getValue();
  }
  
  public void setMinuteStep(Integer paramInteger)
  {
    this.minuteStepProperty.setValue(paramInteger);
  }
  
  public CalendarTimeTextField withMinuteStep(Integer paramInteger)
  {
    setMinuteStep(paramInteger);
    return this;
  }
  
  public ObjectProperty<Boolean> showLabelsProperty()
  {
    return this.showLabelsProperty;
  }
  
  public Boolean getShowLabels()
  {
    return (Boolean)this.showLabelsProperty.getValue();
  }
  
  public void setShowLabels(Boolean paramBoolean)
  {
    this.showLabelsProperty.setValue(paramBoolean);
  }
  
  public CalendarTimeTextField withShowLabels(Boolean paramBoolean)
  {
    setShowLabels(paramBoolean);
    return this;
  }
  
  public ObjectProperty<String> promptTextProperty()
  {
    return this.promptTextObjectProperty;
  }
  
  public String getPromptText()
  {
    return (String)this.promptTextObjectProperty.get();
  }
  
  public void setPromptText(String paramString)
  {
    this.promptTextObjectProperty.set(paramString);
  }
  
  public CalendarTimeTextField withPromptText(String paramString)
  {
    setPromptText(paramString);
    return this;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/CalendarTimeTextField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */