package jfxtras.labs.scene.control;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ListSpinner<T>
  extends Control
{
  private ListChangeListener<T> listChangeListener = new ListChangeListener()
  {
    public void onChanged(Change<? extends T> paramAnonymousChange)
    {
      int i = ListSpinner.this.getIndex().intValue();
      if (i >= ListSpinner.this.getItems().size())
      {
        i = ListSpinner.this.getItems().size() - 1;
        ListSpinner.this.setIndex(Integer.valueOf(i));
        return;
      }
      ListSpinner.this.valueObjectProperty.setValue(ListSpinner.this.getItems().get(i));
    }
  };
  private final ObjectProperty<T> valueObjectProperty = new SimpleObjectProperty(this, "value", null)
  {
    public void set(T paramAnonymousT)
    {
      if (ListSpinner.this.getItems().indexOf(paramAnonymousT) < 0) {
        throw new IllegalArgumentException("Value does not exist in the list: " + paramAnonymousT);
      }
      super.set(paramAnonymousT);
    }
  };
  private final ObjectProperty<Integer> indexObjectProperty = new SimpleObjectProperty(this, "index", null)
  {
    public void set(Integer paramAnonymousInteger)
    {
      if (paramAnonymousInteger == null) {
        throw new NullPointerException("Null not allowed as the value for index");
      }
      if (paramAnonymousInteger.intValue() >= ListSpinner.this.getItems().size()) {
        throw new IllegalArgumentException("Index out of bounds: " + paramAnonymousInteger + ", valid values are 0-" + (ListSpinner.this.getItems().size() - 1));
      }
      super.set(paramAnonymousInteger);
    }
  };
  private final ObjectProperty<Boolean> cyclicObjectProperty = new SimpleObjectProperty(this, "cyclic", Boolean.valueOf(false))
  {
    public void set(Boolean paramAnonymousBoolean)
    {
      if (paramAnonymousBoolean == null) {
        throw new NullPointerException("Null not allowed as the value for cyclic");
      }
      super.set(paramAnonymousBoolean);
    }
  };
  private final ObjectProperty<Boolean> editableObjectProperty = new SimpleObjectProperty(this, "editable", Boolean.valueOf(false))
  {
    public void set(Boolean paramAnonymousBoolean)
    {
      if (paramAnonymousBoolean == null) {
        throw new NullPointerException("Null not allowed as the value for editable");
      }
      super.set(paramAnonymousBoolean);
    }
  };
  private final ObjectProperty<String> postfixObjectProperty = new SimpleObjectProperty(this, "postfix", "");
  private final ObjectProperty<String> prefixObjectProperty = new SimpleObjectProperty(this, "prefix", "");
  private final ObjectProperty<ObservableList<T>> itemsObjectProperty = new SimpleObjectProperty(this, "items", null)
  {
    public void set(ObservableList<T> paramAnonymousObservableList)
    {
      if (paramAnonymousObservableList == null) {
        throw new NullPointerException("Null not allowed as the value for items");
      }
      super.set(paramAnonymousObservableList);
    }
  };
  private final ObjectProperty<Callback<ListSpinner<T>, Node>> cellFactoryObjectProperty = new SimpleObjectProperty(this, "cellFactory", new DefaultCellFactory());
  private final ObjectProperty<StringConverter<T>> stringConverterObjectProperty = new SimpleObjectProperty(this, "stringConverter", new DefaultStringConverter());
  private final ObjectProperty<ArrowDirection> arrowDirectionObjectProperty = new SimpleObjectProperty(this, "arrowDirection", ArrowDirection.HORIZONTAL)
  {
    public void set(ArrowDirection paramAnonymousArrowDirection)
    {
      if (paramAnonymousArrowDirection == null) {
        throw new NullPointerException("Null not allowed as the value for arrowDirection");
      }
      super.set(paramAnonymousArrowDirection);
    }
  };
  private final ObjectProperty<ArrowPosition> arrowPositionObjectProperty = new SimpleObjectProperty(this, "arrowPosition", ArrowPosition.TRAILING)
  {
    public void set(ArrowPosition paramAnonymousArrowPosition)
    {
      if (paramAnonymousArrowPosition == null) {
        throw new NullPointerException("Null not allowed as the value for arrowPosition");
      }
      super.set(paramAnonymousArrowPosition);
    }
  };
  private final ObjectProperty<Pos> alignmentObjectProperty = new SimpleObjectProperty(this, "alignment", Pos.CENTER_LEFT)
  {
    public void set(Pos paramAnonymousPos)
    {
      if (paramAnonymousPos == null) {
        throw new NullPointerException("Null not allowed as the value for alignment");
      }
      super.set(paramAnonymousPos);
    }
  };
  private final ObjectProperty<Callback<T, Integer>> addCallbackObjectProperty = new SimpleObjectProperty(this, "addCallback", null);
  private final ObjectProperty<EventHandler<CycleEvent>> iOnCycleObjectProperty = new SimpleObjectProperty(null);
  public static final String ONCYCLE_PROPERTY_ID = "onCycle";
  
  public ListSpinner()
  {
    construct();
  }
  
  public ListSpinner(ObservableList<T> paramObservableList)
  {
    construct();
    setItems(paramObservableList);
    first();
  }
  
  public ListSpinner(ObservableList<T> paramObservableList, T paramT)
  {
    construct();
    setItems(paramObservableList);
    setValue(paramT);
  }
  
  public ListSpinner(List<T> paramList)
  {
    this(FXCollections.observableList(paramList));
  }
  
  public ListSpinner(T... paramVarArgs)
  {
    this(Arrays.asList(paramVarArgs));
  }
  
  public ListSpinner(int paramInt1, int paramInt2)
  {
    this(new ListSpinnerIntegerList(paramInt1, paramInt2));
  }
  
  public ListSpinner(int paramInt1, int paramInt2, int paramInt3)
  {
    this(new ListSpinnerIntegerList(paramInt1, paramInt2, paramInt3));
  }
  
  private void construct()
  {
    getStyleClass().add(getClass().getSimpleName());
    this.valueObjectProperty.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends T> paramAnonymousObservableValue, T paramAnonymousT1, T paramAnonymousT2)
      {
        int i = ListSpinner.this.getItems().indexOf(paramAnonymousT2);
        if (!ListSpinner.equals(ListSpinner.this.indexObjectProperty.getValue(), Integer.valueOf(i))) {
          ListSpinner.this.indexObjectProperty.setValue(Integer.valueOf(i));
        }
      }
    });
    this.indexObjectProperty.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Integer> paramAnonymousObservableValue, Integer paramAnonymousInteger1, Integer paramAnonymousInteger2)
      {
        Object localObject = paramAnonymousInteger2.intValue() < 0 ? null : ListSpinner.this.getItems().get(paramAnonymousInteger2.intValue());
        if (!ListSpinner.equals(ListSpinner.this.valueObjectProperty.getValue(), localObject)) {
          ListSpinner.this.valueObjectProperty.setValue(localObject);
        }
      }
    });
    this.itemsObjectProperty.addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends ObservableList<T>> paramAnonymousObservableValue, ObservableList<T> paramAnonymousObservableList1, ObservableList<T> paramAnonymousObservableList2)
      {
        if (paramAnonymousObservableList1 != null) {
          paramAnonymousObservableList1.removeListener(ListSpinner.this.listChangeListener);
        }
        if (paramAnonymousObservableList2 != null) {
          paramAnonymousObservableList2.addListener(ListSpinner.this.listChangeListener);
        }
      }
    });
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("/jfxtras/labs/internal/scene/control/" + getClass().getSimpleName() + ".css").toString();
  }
  
  public ObjectProperty<T> valueProperty()
  {
    return this.valueObjectProperty;
  }
  
  public T getValue()
  {
    return (T)this.valueObjectProperty.getValue();
  }
  
  public void setValue(T paramT)
  {
    this.valueObjectProperty.setValue(paramT);
  }
  
  public ListSpinner<T> withValue(T paramT)
  {
    setValue(paramT);
    return this;
  }
  
  public ObjectProperty<Integer> indexProperty()
  {
    return this.indexObjectProperty;
  }
  
  public Integer getIndex()
  {
    return (Integer)this.indexObjectProperty.getValue();
  }
  
  public void setIndex(Integer paramInteger)
  {
    this.indexObjectProperty.setValue(paramInteger);
  }
  
  public ListSpinner<T> withIndex(Integer paramInteger)
  {
    setIndex(paramInteger);
    return this;
  }
  
  public ObjectProperty<Boolean> cyclicProperty()
  {
    return this.cyclicObjectProperty;
  }
  
  public Boolean isCyclic()
  {
    return (Boolean)this.cyclicObjectProperty.getValue();
  }
  
  public void setCyclic(Boolean paramBoolean)
  {
    this.cyclicObjectProperty.setValue(paramBoolean);
  }
  
  public ListSpinner<T> withCyclic(Boolean paramBoolean)
  {
    setCyclic(paramBoolean);
    return this;
  }
  
  public ObjectProperty<Boolean> editableProperty()
  {
    return this.editableObjectProperty;
  }
  
  public Boolean isEditable()
  {
    return (Boolean)this.editableObjectProperty.getValue();
  }
  
  public void setEditable(Boolean paramBoolean)
  {
    this.editableObjectProperty.setValue(paramBoolean);
  }
  
  public ListSpinner<T> withEditable(Boolean paramBoolean)
  {
    setEditable(paramBoolean);
    return this;
  }
  
  public ObjectProperty<String> postfixProperty()
  {
    return this.postfixObjectProperty;
  }
  
  public String getPostfix()
  {
    return (String)this.postfixObjectProperty.getValue();
  }
  
  public void setPostfix(String paramString)
  {
    this.postfixObjectProperty.setValue(paramString);
  }
  
  public ListSpinner<T> withPostfix(String paramString)
  {
    setPostfix(paramString);
    return this;
  }
  
  public ObjectProperty<String> prefixProperty()
  {
    return this.prefixObjectProperty;
  }
  
  public String getPrefix()
  {
    return (String)this.prefixObjectProperty.getValue();
  }
  
  public void setPrefix(String paramString)
  {
    this.prefixObjectProperty.setValue(paramString);
  }
  
  public ListSpinner<T> withPrefix(String paramString)
  {
    setPrefix(paramString);
    return this;
  }
  
  public ObjectProperty<ObservableList<T>> itemsProperty()
  {
    return this.itemsObjectProperty;
  }
  
  public ObservableList<T> getItems()
  {
    return (ObservableList)this.itemsObjectProperty.getValue();
  }
  
  public void setItems(ObservableList<T> paramObservableList)
  {
    this.itemsObjectProperty.setValue(paramObservableList);
  }
  
  public ListSpinner<T> withItems(ObservableList<T> paramObservableList)
  {
    setItems(paramObservableList);
    return this;
  }
  
  public ObjectProperty<Callback<ListSpinner<T>, Node>> cellFactoryProperty()
  {
    return this.cellFactoryObjectProperty;
  }
  
  public Callback<ListSpinner<T>, Node> getCellFactory()
  {
    return (Callback)this.cellFactoryObjectProperty.getValue();
  }
  
  public void setCellFactory(Callback<ListSpinner<T>, Node> paramCallback)
  {
    this.cellFactoryObjectProperty.setValue(paramCallback);
  }
  
  public ListSpinner<T> withCellFactory(Callback<ListSpinner<T>, Node> paramCallback)
  {
    setCellFactory(paramCallback);
    return this;
  }
  
  public ObjectProperty<StringConverter<T>> stringConverterProperty()
  {
    return this.stringConverterObjectProperty;
  }
  
  public StringConverter<T> getStringConverter()
  {
    return (StringConverter)this.stringConverterObjectProperty.getValue();
  }
  
  public void setStringConverter(StringConverter<T> paramStringConverter)
  {
    this.stringConverterObjectProperty.setValue(paramStringConverter);
  }
  
  public ListSpinner<T> withStringConverter(StringConverter<T> paramStringConverter)
  {
    setStringConverter(paramStringConverter);
    return this;
  }
  
  public ObjectProperty<ArrowDirection> arrowDirectionProperty()
  {
    return this.arrowDirectionObjectProperty;
  }
  
  public ArrowDirection getArrowDirection()
  {
    return (ArrowDirection)this.arrowDirectionObjectProperty.getValue();
  }
  
  public void setArrowDirection(ArrowDirection paramArrowDirection)
  {
    this.arrowDirectionObjectProperty.setValue(paramArrowDirection);
  }
  
  public ListSpinner<T> withArrowDirection(ArrowDirection paramArrowDirection)
  {
    setArrowDirection(paramArrowDirection);
    return this;
  }
  
  public ObjectProperty<ArrowPosition> arrowPositionProperty()
  {
    return this.arrowPositionObjectProperty;
  }
  
  public ArrowPosition getArrowPosition()
  {
    return (ArrowPosition)this.arrowPositionObjectProperty.getValue();
  }
  
  public void setArrowPosition(ArrowPosition paramArrowPosition)
  {
    this.arrowPositionObjectProperty.setValue(paramArrowPosition);
  }
  
  public ListSpinner<T> withArrowPosition(ArrowPosition paramArrowPosition)
  {
    setArrowPosition(paramArrowPosition);
    return this;
  }
  
  public ObjectProperty<Pos> alignmentProperty()
  {
    return this.alignmentObjectProperty;
  }
  
  public Pos isAlignment()
  {
    return (Pos)this.alignmentObjectProperty.getValue();
  }
  
  public void setAlignment(Pos paramPos)
  {
    this.alignmentObjectProperty.setValue(paramPos);
  }
  
  public ListSpinner<T> withAlignment(Pos paramPos)
  {
    setAlignment(paramPos);
    return this;
  }
  
  public ObjectProperty<Callback<T, Integer>> addCallbackProperty()
  {
    return this.addCallbackObjectProperty;
  }
  
  public Callback<T, Integer> getAddCallback()
  {
    return (Callback)this.addCallbackObjectProperty.getValue();
  }
  
  public void setAddCallback(Callback<T, Integer> paramCallback)
  {
    this.addCallbackObjectProperty.setValue(paramCallback);
  }
  
  public ListSpinner<T> withAddCallback(Callback<T, Integer> paramCallback)
  {
    setAddCallback(paramCallback);
    return this;
  }
  
  public ObjectProperty<EventHandler<CycleEvent>> onCycleProperty()
  {
    return this.iOnCycleObjectProperty;
  }
  
  public EventHandler<CycleEvent> getOnCycle()
  {
    return (EventHandler)this.iOnCycleObjectProperty.getValue();
  }
  
  public void setOnCycle(EventHandler<CycleEvent> paramEventHandler)
  {
    this.iOnCycleObjectProperty.setValue(paramEventHandler);
  }
  
  public ListSpinner<T> withOnCycle(EventHandler<CycleEvent> paramEventHandler)
  {
    setOnCycle(paramEventHandler);
    return this;
  }
  
  public void fireCycleEvent(CycleDirection paramCycleDirection)
  {
    EventHandler localEventHandler = getOnCycle();
    if (localEventHandler != null)
    {
      CycleEvent localCycleEvent = new CycleEvent();
      localCycleEvent.cycleDirection = paramCycleDirection;
      localEventHandler.handle(localCycleEvent);
    }
  }
  
  public void first()
  {
    if ((getItems() == null) || (getItems().size() == 0)) {
      return;
    }
    this.indexObjectProperty.setValue(Integer.valueOf(0));
  }
  
  public void decrement()
  {
    if ((getItems() == null) || (getItems().size() == 0)) {
      return;
    }
    int i = ((Integer)this.indexObjectProperty.getValue()).intValue();
    int j = i - 1;
    if (j < 0)
    {
      if ((isCyclic() != null) && (!isCyclic().booleanValue())) {
        return;
      }
      j = getItems().size() - 1;
      fireCycleEvent(CycleDirection.BOTTOM_TO_TOP);
    }
    this.indexObjectProperty.setValue(Integer.valueOf(j));
  }
  
  public void increment()
  {
    if ((getItems() == null) || (getItems().size() == 0)) {
      return;
    }
    int i = ((Integer)this.indexObjectProperty.getValue()).intValue();
    int j = i + 1;
    if (j >= getItems().size())
    {
      if ((isCyclic() != null) && (!isCyclic().booleanValue())) {
        return;
      }
      j = 0;
      fireCycleEvent(CycleDirection.TOP_TO_BOTTOM);
    }
    this.indexObjectProperty.setValue(Integer.valueOf(j));
  }
  
  public void last()
  {
    if ((getItems() == null) || (getItems().size() == 0)) {
      return;
    }
    this.indexObjectProperty.setValue(Integer.valueOf(getItems().size() - 1));
  }
  
  public static boolean equals(Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 == null) && (paramObject2 == null)) {
      return true;
    }
    if ((paramObject1 != null) && (paramObject2 == null)) {
      return false;
    }
    if ((paramObject1 == null) && (paramObject2 != null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public static enum CycleDirection
  {
    TOP_TO_BOTTOM,  BOTTOM_TO_TOP;
    
    private CycleDirection() {}
  }
  
  public static class CycleEvent
    extends Event
  {
    private Object oldIdx;
    private Object newIdx;
    CycleDirection cycleDirection;
    
    public CycleEvent()
    {
      super();
    }
    
    public CycleEvent(Object paramObject, EventTarget paramEventTarget)
    {
      super(paramEventTarget, new EventType());
    }
    
    public Object getOldIdx()
    {
      return this.oldIdx;
    }
    
    public Object getNewIdx()
    {
      return this.newIdx;
    }
    
    public boolean cycledDown()
    {
      return this.cycleDirection == CycleDirection.TOP_TO_BOTTOM;
    }
    
    public boolean cycledUp()
    {
      return this.cycleDirection == CycleDirection.BOTTOM_TO_TOP;
    }
  }
  
  class DefaultCellFactory
    implements Callback<ListSpinner<T>, Node>
  {
    private Label label = null;
    
    DefaultCellFactory() {}
    
    public Node call(ListSpinner<T> paramListSpinner)
    {
      Object localObject = paramListSpinner.getValue();
      if (this.label == null) {
        this.label = new Label();
      }
      this.label.setText(paramListSpinner.getPrefix() + ListSpinner.this.getStringConverter().toString(localObject) + paramListSpinner.getPostfix());
      return this.label;
    }
  }
  
  class DefaultStringConverter
    extends StringConverter<T>
  {
    DefaultStringConverter() {}
    
    public T fromString(String paramString)
    {
      throw new IllegalStateException("No StringConverter is set. An editable Spinner must have a StringConverter to be able to render and parse the value.");
    }
    
    public String toString(T paramT)
    {
      return paramT == null ? "" : paramT.toString();
    }
  }
  
  public static enum ArrowPosition
  {
    LEADING,  TRAILING,  SPLIT;
    
    private ArrowPosition() {}
  }
  
  public static enum ArrowDirection
  {
    VERTICAL,  HORIZONTAL;
    
    private ArrowDirection() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/ListSpinner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */