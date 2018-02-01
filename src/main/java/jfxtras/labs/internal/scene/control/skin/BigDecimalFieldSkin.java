package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import jfxtras.labs.internal.scene.control.behavior.BigDecimalFieldBehaviour;
import jfxtras.labs.scene.control.BigDecimalField;

public class BigDecimalFieldSkin
  extends SkinBase<BigDecimalField, BigDecimalFieldBehaviour>
{
  private BigDecimalField CONTROL;
  private NumberTextField textField;
  private StackPane btnUp;
  private StackPane btnDown;
  private Path arrowUp;
  private Path arrowDown;
  private final double ARROW_SIZE = 4.0D;
  private final double ARROW_HEIGHT = 0.7D;
  
  public BigDecimalFieldSkin(BigDecimalField paramBigDecimalField)
  {
    super(paramBigDecimalField, new BigDecimalFieldBehaviour(paramBigDecimalField));
    this.CONTROL = paramBigDecimalField;
    createNodes();
    initFocusSimulation();
    requestLayout();
  }
  
  public BigDecimalField getSkinnable()
  {
    return this.CONTROL;
  }
  
  private void createNodes()
  {
    this.textField = new NumberTextField();
    this.textField.promptTextProperty().bind(this.CONTROL.promptTextProperty());
    this.btnUp = new StackPane();
    this.btnUp.getStyleClass().add("arrow-button");
    this.arrowUp = new Path();
    this.arrowUp.getStyleClass().add("spinner-arrow");
    this.arrowUp.getElements().addAll(new PathElement[] { new MoveTo(-4.0D, 0.0D), new LineTo(0.0D, -2.8D), new LineTo(4.0D, 0.0D) });
    this.btnUp.getChildren().add(this.arrowUp);
    this.btnDown = new StackPane();
    this.btnDown.getStyleClass().add("arrow-button");
    this.arrowDown = new Path();
    this.arrowDown.getStyleClass().add("spinner-arrow");
    this.arrowDown.getElements().addAll(new PathElement[] { new MoveTo(-4.0D, 0.0D), new LineTo(0.0D, 2.8D), new LineTo(4.0D, 0.0D) });
    this.btnDown.getChildren().add(this.arrowDown);
    getChildren().addAll(new Node[] { this.textField, this.btnUp, this.btnDown });
    this.btnUp.setOnMouseClicked(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        BigDecimalFieldSkin.this.CONTROL.increment();
      }
    });
    this.btnDown.setOnMouseClicked(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        BigDecimalFieldSkin.this.CONTROL.decrement();
      }
    });
  }
  
  protected void layoutChildren()
  {
    super.layoutChildren();
    Insets localInsets = getInsets();
    double d1 = localInsets.getLeft();
    double d2 = localInsets.getTop();
    double d3 = getHeight() - localInsets.getTop() - localInsets.getBottom();
    double d4 = this.textField.prefHeight(-1.0D);
    double d5 = getWidth() - localInsets.getLeft() - localInsets.getRight() - d4;
    layoutInArea(this.textField, d1, d2, d5, d3, Double.NEGATIVE_INFINITY, HPos.LEFT, VPos.TOP);
    layoutInArea(this.btnUp, d1 + d5, d2, d4, d3 / 2.0D, Double.NEGATIVE_INFINITY, HPos.LEFT, VPos.TOP);
    layoutInArea(this.btnDown, d1 + d5, d2 + d3 / 2.0D, d4, d3 / 2.0D, Double.NEGATIVE_INFINITY, HPos.LEFT, VPos.TOP);
  }
  
  protected double computePrefWidth(double paramDouble)
  {
    super.computePrefWidth(paramDouble);
    double d = getInsets().getLeft() + this.textField.prefWidth(paramDouble) + this.textField.prefHeight(paramDouble) + getInsets().getRight();
    return d;
  }
  
  private void initFocusSimulation()
  {
    this.textField.focusedProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        if (paramAnonymousBoolean2.booleanValue()) {
          BigDecimalFieldSkin.this.CONTROL.getStyleClass().add("big-decimal-field-focused");
        } else {
          BigDecimalFieldSkin.this.CONTROL.getStyleClass().remove("big-decimal-field-focused");
        }
      }
    });
  }
  
  public class NumberTextField
    extends TextField
  {
    public NumberTextField()
    {
      getStyleClass().add("number-text-field");
      initHandlers();
      setText(BigDecimalFieldSkin.this.CONTROL.getText());
      BigDecimalFieldSkin.this.CONTROL.disableProperty().bindBidirectional(disableProperty());
    }
    
    private void initHandlers()
    {
      setOnAction(new EventHandler()
      {
        public void handle(ActionEvent paramAnonymousActionEvent)
        {
          NumberTextField.this.parseAndFormatInput();
        }
      });
      focusedProperty().addListener(new ChangeListener()
      {
        public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
        {
          if (!paramAnonymousBoolean2.booleanValue()) {
            NumberTextField.this.parseAndFormatInput();
          }
        }
      });
      BigDecimalFieldSkin.this.CONTROL.numberProperty().addListener(new InvalidationListener()
      {
        public void invalidated(Observable paramAnonymousObservable)
        {
          NumberTextField.this.setText(BigDecimalFieldSkin.this.CONTROL.getText());
        }
      });
      addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler()
      {
        public void handle(KeyEvent paramAnonymousKeyEvent)
        {
          if (paramAnonymousKeyEvent.getCode() == KeyCode.DOWN)
          {
            BigDecimalFieldSkin.this.CONTROL.decrement();
            paramAnonymousKeyEvent.consume();
          }
          if (paramAnonymousKeyEvent.getCode() == KeyCode.UP)
          {
            BigDecimalFieldSkin.this.CONTROL.increment();
            paramAnonymousKeyEvent.consume();
          }
        }
      });
    }
    
    private void parseAndFormatInput()
    {
      try
      {
        String str = getText();
        if ((str == null) || (str.length() == 0))
        {
          BigDecimalFieldSkin.this.CONTROL.setNumber(null);
          return;
        }
        Number localNumber = BigDecimalFieldSkin.this.CONTROL.getFormat().parse(str);
        BigDecimal localBigDecimal = new BigDecimal(localNumber.toString());
        BigDecimalFieldSkin.this.CONTROL.setNumber(localBigDecimal);
        selectAll();
      }
      catch (ParseException localParseException)
      {
        setText(BigDecimalFieldSkin.this.CONTROL.getText());
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        setText(BigDecimalFieldSkin.this.CONTROL.getText());
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/BigDecimalFieldSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */