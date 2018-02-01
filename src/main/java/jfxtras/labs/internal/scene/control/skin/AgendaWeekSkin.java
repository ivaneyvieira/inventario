package jfxtras.labs.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.SkinBase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import jfxtras.labs.animation.Timer;
import jfxtras.labs.internal.scene.control.behavior.AgendaBehavior;
import jfxtras.labs.scene.control.Agenda;
import jfxtras.labs.scene.control.Agenda.Appointment;
import jfxtras.labs.scene.control.Agenda.AppointmentGroup;
import jfxtras.labs.scene.control.Agenda.CalendarRange;
import jfxtras.labs.scene.control.CalendarTextField;
import jfxtras.labs.util.NodeUtil;

public class AgendaWeekSkin
  extends SkinBase<Agenda, AgendaBehavior>
{
  private SimpleDateFormat dayOfWeekDateFormat = null;
  private SimpleDateFormat dateFormat = null;
  private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
  private Pane dragPane = null;
  private BorderPane borderPane = null;
  private WeekHeaderPane weekHeaderPane = null;
  private ScrollPane weekScrollPane = null;
  private WeekPane weekPane = null;
  private Image closeIconImage = null;
  AbstractAppointmentPane focused = null;
  final Rectangle nowLine = new Rectangle(0.0D, 0.0D, 0.0D, 0.0D);
  Runnable nowUpdateRunnable = new Runnable()
  {
    public void run()
    {
      Calendar localCalendar = Calendar.getInstance();
      int i = 0;
      Iterator localIterator1 = AgendaWeekSkin.this.weekPane.dayPanes.iterator();
      Object localObject1;
      Iterator localIterator2;
      Object localObject2;
      while (localIterator1.hasNext())
      {
        localObject1 = (DayPane)localIterator1.next();
        if (!AgendaWeekSkin.this.isSameDay((Calendar)((DayPane)localObject1).calendarObjectProperty.get(), localCalendar))
        {
          ((DayPane)localObject1).getStyleClass().remove("today");
        }
        else
        {
          if (!((DayPane)localObject1).getStyleClass().contains("today")) {
            ((DayPane)localObject1).getStyleClass().add("today");
          }
          i = 1;
          if (!AgendaWeekSkin.this.weekPane.getChildren().contains(AgendaWeekSkin.this.nowLine))
          {
            AgendaWeekSkin.this.weekPane.getChildren().add(AgendaWeekSkin.this.nowLine);
            AgendaWeekSkin.this.nowLine.xProperty().bind(((DayPane)localObject1).layoutXProperty());
          }
          int j = localCalendar.get(11) * 60 + localCalendar.get(12);
          AgendaWeekSkin.this.nowLine.setY(AgendaWeekSkin.this.dayHeightProperty.get() / 1440.0D * j);
          if (!AgendaWeekSkin.this.nowLine.widthProperty().isBound()) {
            AgendaWeekSkin.this.nowLine.widthProperty().bind(AgendaWeekSkin.this.dayWidthProperty);
          }
        }
        localIterator2 = ((DayPane)localObject1).regularAppointmentPanes.iterator();
        while (localIterator2.hasNext())
        {
          localObject2 = (RegularAppointmentPane)localIterator2.next();
          ((RegularAppointmentPane)localObject2).historicalVisualizer.setVisible(((RegularAppointmentPane)localObject2).start.before(localCalendar));
        }
        localIterator2 = ((DayPane)localObject1).wholedayAppointmentPanes.iterator();
        while (localIterator2.hasNext())
        {
          localObject2 = (WholedayAppointmentPane)localIterator2.next();
          ((WholedayAppointmentPane)localObject2).historicalVisualizer.setVisible(((WholedayAppointmentPane)localObject2).start.before(localCalendar));
        }
      }
      if (i == 0) {
        AgendaWeekSkin.this.weekPane.getChildren().remove(AgendaWeekSkin.this.nowLine);
      }
      localIterator1 = AgendaWeekSkin.this.weekHeaderPane.dayHeaderPanes.iterator();
      while (localIterator1.hasNext())
      {
        localObject1 = (DayHeaderPane)localIterator1.next();
        localIterator2 = ((DayHeaderPane)localObject1).appointmentHeaderPanes.iterator();
        while (localIterator2.hasNext())
        {
          localObject2 = (AppointmentHeaderPane)localIterator2.next();
          ((AppointmentHeaderPane)localObject2).historicalVisualizer.setVisible(((AppointmentHeaderPane)localObject2).appointment.getStartTime().before(localCalendar));
        }
      }
    }
  };
  Timer nowTimer = new Timer(this.nowUpdateRunnable).withCycleDuration(new Duration(60000.0D)).withDelay(new Duration((60 - Calendar.getInstance().get(13)) * 1000)).start();
  private final double padding = 3.0D;
  private final double timeColumnWhitespace = 10.0D;
  private final double wholedayAppointmentWidth = 5.0D;
  private final IntegerProperty highestNumberOfWholedayAppointmentsProperty = new SimpleIntegerProperty(0);
  private final DoubleProperty textHeight1MProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty titleCalendarHeightProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty headerHeightProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty wholedayTitleHeightProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty timeWidthProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty dayFirstColumnXProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty dayWidthProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty dayContentWidthProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty dayHeightProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty durationInMSPerPixelProperty = new SimpleDoubleProperty(0.0D);
  private final DoubleProperty hourHeighProperty = new SimpleDoubleProperty(0.0D);
  
  public AgendaWeekSkin(Agenda paramAgenda)
  {
    super(paramAgenda, new AgendaBehavior(paramAgenda));
    construct();
  }
  
  private void construct()
  {
    createNodes();
    ((Agenda)getSkinnable()).localeProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        AgendaWeekSkin.this.refreshLocale();
      }
    });
    refreshLocale();
    ((Agenda)getSkinnable()).displayedCalendar().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        AgendaWeekSkin.this.assignCalendarToTheDayPanes();
        AgendaWeekSkin.this.setupAppointments();
      }
    });
    assignCalendarToTheDayPanes();
    ((Agenda)getSkinnable()).appointments().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends Appointment> paramAnonymousChange)
      {
        AgendaWeekSkin.this.setupAppointments();
      }
    });
    setupAppointments();
    ((Agenda)getSkinnable()).selectedAppointments().addListener(new ListChangeListener()
    {
      public void onChanged(Change<? extends Appointment> paramAnonymousChange)
      {
        AgendaWeekSkin.this.setOrRemoveSelected();
      }
    });
    setOrRemoveSelected();
  }
  
  private void setOrRemoveSelected()
  {
    Iterator localIterator1 = this.weekPane.dayPanes.iterator();
    while (localIterator1.hasNext())
    {
      DayPane localDayPane = (DayPane)localIterator1.next();
      Iterator localIterator2 = localDayPane.allAbstractAppointmentPanes().iterator();
      while (localIterator2.hasNext())
      {
        AbstractAppointmentPane localAbstractAppointmentPane = (AbstractAppointmentPane)localIterator2.next();
        if ((localAbstractAppointmentPane.getStyleClass().contains("Selected") == true) && (!((Agenda)getSkinnable()).selectedAppointments().contains(localAbstractAppointmentPane.appointment))) {
          localAbstractAppointmentPane.getStyleClass().remove("Selected");
        }
        if ((!localAbstractAppointmentPane.getStyleClass().contains("Selected")) && (((Agenda)getSkinnable()).selectedAppointments().contains(localAbstractAppointmentPane.appointment) == true)) {
          localAbstractAppointmentPane.getStyleClass().add("Selected");
        }
      }
    }
  }
  
  private void assignCalendarToTheDayPanes()
  {
    Calendar localCalendar1 = getFirstDayOfWeekCalendar();
    Calendar localCalendar2 = (Calendar)localCalendar1.clone();
    Calendar localCalendar3 = null;
    for (int i = 0; i < 7; i++)
    {
      DayPane localDayPane = (DayPane)this.weekPane.dayPanes.get(i);
      localDayPane.calendarObjectProperty.set((Calendar)localCalendar1.clone());
      if (i == 6) {
        localCalendar3 = (Calendar)localCalendar1.clone();
      }
      localCalendar1.add(5, 1);
    }
    this.nowUpdateRunnable.run();
    if (((Agenda)getSkinnable()).getCalendarRangeCallback() != null)
    {
      CalendarRange localCalendarRange = new CalendarRange(localCalendar2, localCalendar3);
      ((Agenda)getSkinnable()).getCalendarRangeCallback().call(localCalendarRange);
    }
  }
  
  private void refreshLocale()
  {
    this.dayOfWeekDateFormat = new SimpleDateFormat("E", ((Agenda)getSkinnable()).getLocale());
    this.dateFormat = ((SimpleDateFormat)SimpleDateFormat.getDateInstance(3, ((Agenda)getSkinnable()).getLocale()));
    int i = 0;
    Iterator localIterator = this.weekPane.dayPanes.iterator();
    while (localIterator.hasNext())
    {
      DayPane localDayPane = (DayPane)localIterator.next();
      if (localDayPane.calendarObjectProperty.get() != null) {
        localDayPane.calendarObjectProperty.set((Calendar)((Calendar)localDayPane.calendarObjectProperty.get()).clone());
      }
      String str = isWeekdayWeekend(i) ? "weekend" : "weekday";
      localDayPane.getStyleClass().removeAll(new String[] { "weekend", "weekday" });
      localDayPane.getStyleClass().add(str);
      localDayPane.dayHeaderPane.calendarText.getStyleClass().removeAll(new String[] { "weekend", "weekday" });
      localDayPane.dayHeaderPane.calendarText.getStyleClass().add(str);
      i++;
    }
  }
  
  private void setupAppointments()
  {
    calculateSizes();
    Iterator localIterator = this.weekPane.dayPanes.iterator();
    while (localIterator.hasNext())
    {
      DayPane localDayPane = (DayPane)localIterator.next();
      localDayPane.setupAppointments();
    }
    calculateSizes();
    this.nowUpdateRunnable.run();
  }
  
  private void createNodes()
  {
    this.borderPane = new BorderPane();
    this.weekPane = new WeekPane();
    this.weekScrollPane = ScrollPaneBuilder.create().content(this.weekPane).hbarPolicy(ScrollBarPolicy.NEVER).fitToWidth(true).pannable(false).build();
    this.borderPane.setCenter(this.weekScrollPane);
    this.weekScrollPane.viewportBoundsProperty().addListener(new InvalidationListener()
    {
      public void invalidated(Observable paramAnonymousObservable)
      {
        AgendaWeekSkin.this.calculateSizes();
        AgendaWeekSkin.this.nowUpdateRunnable.run();
      }
    });
    this.weekHeaderPane = new WeekHeaderPane();
    this.weekHeaderPane.prefWidthProperty().bind(this.weekPane.widthProperty());
    this.weekHeaderPane.prefHeightProperty().bind(this.headerHeightProperty);
    this.weekHeaderPane.setTranslateX(1.0D);
    this.borderPane.setTop(this.weekHeaderPane);
    this.dragPane = new Pane();
    this.dragPane.prefWidthProperty().bind(widthProperty());
    this.dragPane.prefHeightProperty().bind(heightProperty());
    this.dragPane.getChildren().add(this.borderPane);
    this.borderPane.prefWidthProperty().bind(this.dragPane.widthProperty());
    this.borderPane.prefHeightProperty().bind(this.dragPane.heightProperty());
    getStyleClass().add(getClass().getSimpleName());
    getChildren().add(this.dragPane);
    this.closeIconImage = new Image(getClass().getResourceAsStream(getClass().getSimpleName() + "PopupCloseWindowIcon.png"));
  }
  
  private void showMenu(MouseEvent paramMouseEvent, final AbstractAppointmentPane paramAbstractAppointmentPane)
  {
    final Popup localPopup = new Popup();
    localPopup.setAutoFix(true);
    localPopup.setAutoHide(true);
    localPopup.setHideOnEscape(true);
    localPopup.setOnHidden(new EventHandler()
    {
      public void handle(WindowEvent paramAnonymousWindowEvent)
      {
        AgendaWeekSkin.this.setupAppointments();
      }
    });
    BorderPane localBorderPane = new BorderPane();
    localBorderPane.getStyleClass().add(getClass().getSimpleName() + "_popup");
    localPopup.getContent().add(localBorderPane);
    ImageView localImageView = new ImageView(this.closeIconImage);
    localImageView.setPickOnBounds(true);
    localImageView.setOnMouseClicked(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        localPopup.hide();
      }
    });
    localBorderPane.setRight(localImageView);
    VBox localVBox = new VBox(3.0D);
    localBorderPane.setCenter(localVBox);
    localVBox.getChildren().add(new Text("Time:"));
    CalendarTextField localCalendarTextField1 = new CalendarTextField().withShowTime(Boolean.valueOf(true));
    localCalendarTextField1.setLocale(((Agenda)getSkinnable()).getLocale());
    localCalendarTextField1.setValue(paramAbstractAppointmentPane.appointment.getStartTime());
    localVBox.getChildren().add(localCalendarTextField1);
    final CalendarTextField localCalendarTextField2 = new CalendarTextField().withShowTime(Boolean.valueOf(true));
    localCalendarTextField2.setLocale(((Agenda)getSkinnable()).getLocale());
    localCalendarTextField2.setValue(paramAbstractAppointmentPane.appointment.getEndTime());
    localVBox.getChildren().add(localCalendarTextField2);
    localCalendarTextField2.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Calendar> paramAnonymousObservableValue, Calendar paramAnonymousCalendar1, Calendar paramAnonymousCalendar2)
      {
        paramAbstractAppointmentPane.appointment.setEndTime(paramAnonymousCalendar2);
      }
    });
    localCalendarTextField2.setVisible(paramAbstractAppointmentPane.appointment.getEndTime() != null);
    CheckBox localCheckBox = new CheckBox("Wholeday");
    localCheckBox.selectedProperty().set(paramAbstractAppointmentPane.appointment.isWholeDay().booleanValue());
    localVBox.getChildren().add(localCheckBox);
    localCheckBox.selectedProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        paramAbstractAppointmentPane.appointment.setWholeDay(paramAnonymousBoolean2);
        if (paramAnonymousBoolean2.booleanValue() == true)
        {
          paramAbstractAppointmentPane.appointment.setEndTime(null);
        }
        else
        {
          Calendar localCalendar = (Calendar)paramAbstractAppointmentPane.appointment.getStartTime().clone();
          localCalendar.add(12, 30);
          paramAbstractAppointmentPane.appointment.setEndTime(localCalendar);
          localCalendarTextField2.setValue(paramAbstractAppointmentPane.appointment.getEndTime());
        }
        localCalendarTextField2.setVisible(paramAbstractAppointmentPane.appointment.getEndTime() != null);
      }
    });
    localCalendarTextField1.valueProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends Calendar> paramAnonymousObservableValue, Calendar paramAnonymousCalendar1, Calendar paramAnonymousCalendar2)
      {
        if (paramAbstractAppointmentPane.appointment.isWholeDay().booleanValue())
        {
          paramAbstractAppointmentPane.appointment.setStartTime(paramAnonymousCalendar2);
        }
        else
        {
          long l = paramAbstractAppointmentPane.appointment.getEndTime().getTimeInMillis() - paramAbstractAppointmentPane.appointment.getStartTime().getTimeInMillis();
          paramAbstractAppointmentPane.appointment.setStartTime(paramAnonymousCalendar2);
          Calendar localCalendar = (Calendar)paramAbstractAppointmentPane.appointment.getStartTime().clone();
          localCalendar.add(14, (int)l);
          paramAbstractAppointmentPane.appointment.setEndTime(localCalendar);
          localCalendarTextField2.setValue(paramAbstractAppointmentPane.appointment.getEndTime());
        }
      }
    });
    localVBox.getChildren().add(new Text("Summary:"));
    TextField localTextField1 = new TextField();
    localTextField1.setText(paramAbstractAppointmentPane.appointment.getSummary());
    localTextField1.textProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends String> paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2)
      {
        paramAbstractAppointmentPane.appointment.setSummary(paramAnonymousString2);
      }
    });
    localVBox.getChildren().add(localTextField1);
    localVBox.getChildren().add(new Text("Location:"));
    TextField localTextField2 = new TextField();
    localTextField2.setText(paramAbstractAppointmentPane.appointment.getLocation() == null ? "" : paramAbstractAppointmentPane.appointment.getLocation());
    localTextField2.textProperty().addListener(new ChangeListener()
    {
      public void changed(ObservableValue<? extends String> paramAnonymousObservableValue, String paramAnonymousString1, String paramAnonymousString2)
      {
        paramAbstractAppointmentPane.appointment.setLocation(paramAnonymousString2);
      }
    });
    localVBox.getChildren().add(localTextField2);
    localVBox.getChildren().add(new Text("Actions:"));
    HBox localHBox = new HBox();
    localVBox.getChildren().add(localHBox);
    Object localObject = new ImageButton(new Image(getClass().getResourceAsStream("jqueryMobileBlack16x16/delete.png")));
    ((ImageButton)localObject).setOnMouseClicked(new EventHandler()
    {
      public void handle(MouseEvent paramAnonymousMouseEvent)
      {
        localPopup.hide();
        ((Agenda)AgendaWeekSkin.this.getSkinnable()).appointments().remove(paramAbstractAppointmentPane.appointment);
      }
    });
    Tooltip.install((Node)localObject, new Tooltip("Delete"));
    localHBox.getChildren().add(localObject);
    localVBox.getChildren().add(new Text("Group:"));
    localObject = new GridPane();
    localVBox.getChildren().add(localObject);
    ((GridPane)localObject).getStyleClass().add("AppointmentGroups");
    ((GridPane)localObject).setHgap(2.0D);
    ((GridPane)localObject).setVgap(2.0D);
    int i = 0;
    Iterator localIterator = ((Agenda)getSkinnable()).appointmentGroups().iterator();
    while (localIterator.hasNext())
    {
      AppointmentGroup localAppointmentGroup1 = (AppointmentGroup)localIterator.next();
      final Pane localPane = new Pane();
      localPane.setPrefSize(15.0D, 15.0D);
      localPane.getStyleClass().addAll(new String[] { "AppointmentGroup", localAppointmentGroup1.getStyleClass() });
      ((GridPane)localObject).add(localPane, i % 10, i / 10);
      i++;
      Tooltip.install(localPane, new Tooltip(localAppointmentGroup1.getDescription()));
      localPane.setOnMouseEntered(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown())
          {
            paramAnonymousMouseEvent.consume();
            localPane.setCursor(Cursor.HAND);
          }
        }
      });
      localPane.setOnMouseExited(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown())
          {
            paramAnonymousMouseEvent.consume();
            localPane.setCursor(Cursor.DEFAULT);
          }
        }
      });
      final AppointmentGroup localAppointmentGroup2 = localAppointmentGroup1;
      localPane.setOnMouseClicked(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          paramAnonymousMouseEvent.consume();
          paramAbstractAppointmentPane.appointment.setAppointmentGroup(localAppointmentGroup2);
          localPopup.hide();
        }
      });
    }
    localPopup.show(paramAbstractAppointmentPane, NodeUtil.screenX(paramAbstractAppointmentPane), NodeUtil.screenY(paramAbstractAppointmentPane.menuIcon) + paramAbstractAppointmentPane.menuIcon.getHeight());
  }
  
  protected boolean isWeekday(int paramInt1, int paramInt2)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(2009, 6, 4 + ((Agenda)getSkinnable()).getDisplayedCalendar().getFirstDayOfWeek());
    localGregorianCalendar.add(5, paramInt1);
    int i = localGregorianCalendar.get(7);
    return i == paramInt2;
  }
  
  protected boolean isWeekdayWeekend(int paramInt)
  {
    return (isWeekday(paramInt, 7)) || (isWeekday(paramInt, 1));
  }
  
  private void calculateSizes()
  {
    double d = new ScrollBar().getWidth();
    this.textHeight1MProperty.set(new Text("X").getBoundsInParent().getHeight());
    this.highestNumberOfWholedayAppointmentsProperty.set(0);
    Iterator localIterator = this.weekPane.dayPanes.iterator();
    while (localIterator.hasNext())
    {
      DayPane localDayPane = (DayPane)localIterator.next();
      if (localDayPane.wholedayAppointmentPanes.size() > this.highestNumberOfWholedayAppointmentsProperty.get()) {
        this.highestNumberOfWholedayAppointmentsProperty.set(localDayPane.wholedayAppointmentPanes.size());
      }
    }
    this.titleCalendarHeightProperty.set(1.5D * this.textHeight1MProperty.get());
    this.wholedayTitleHeightProperty.set(this.textHeight1MProperty.get() + 5.0D);
    this.headerHeightProperty.set(this.titleCalendarHeightProperty.get() + this.highestNumberOfWholedayAppointmentsProperty.get() * this.wholedayTitleHeightProperty.get());
    this.timeWidthProperty.set(new Text("88:88").getBoundsInParent().getWidth() + 10.0D);
    this.dayFirstColumnXProperty.set(this.timeWidthProperty.get());
    if (this.weekScrollPane.viewportBoundsProperty().get() != null) {
      this.dayWidthProperty.set((((Bounds)this.weekScrollPane.viewportBoundsProperty().get()).getWidth() - this.timeWidthProperty.get()) / 7.0D);
    }
    this.dayContentWidthProperty.set(this.dayWidthProperty.get() - 10.0D);
    this.hourHeighProperty.set(2.0D * this.textHeight1MProperty.get() + 10.0D);
    if ((this.weekScrollPane.viewportBoundsProperty().get() != null) && (((Bounds)this.weekScrollPane.viewportBoundsProperty().get()).getHeight() - d > this.hourHeighProperty.get() * 24.0D)) {
      this.hourHeighProperty.set((((Bounds)this.weekScrollPane.viewportBoundsProperty().get()).getHeight() - d) / 24.0D);
    }
    this.dayHeightProperty.set(this.hourHeighProperty.get() * 24.0D);
    this.durationInMSPerPixelProperty.set(8.64E7D / this.dayHeightProperty.get());
  }
  
  protected Calendar getFirstDayOfWeekCalendar()
  {
    Calendar localCalendar1 = Calendar.getInstance(((Agenda)getSkinnable()).getLocale());
    int i = localCalendar1.getFirstDayOfWeek();
    Calendar localCalendar2 = ((Agenda)getSkinnable()).getDisplayedCalendar();
    if (localCalendar2 == null) {
      return null;
    }
    Calendar localCalendar3 = (Calendar)localCalendar2.clone();
    localCalendar3.add(5, i - localCalendar3.get(7));
    while ((localCalendar3.get(1) > localCalendar2.get(1)) || ((localCalendar3.get(1) == localCalendar2.get(1)) && (localCalendar3.get(3) > localCalendar2.get(3)))) {
      localCalendar3.add(5, -7);
    }
    while ((localCalendar3.get(1) < localCalendar2.get(1)) || ((localCalendar3.get(1) == localCalendar2.get(1)) && (localCalendar3.get(3) < localCalendar2.get(3)))) {
      localCalendar3.add(5, 7);
    }
    return localCalendar3;
  }
  
  private boolean isSameDay(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    return (paramCalendar1.get(1) == paramCalendar2.get(1)) && (paramCalendar1.get(2) == paramCalendar2.get(2)) && (paramCalendar1.get(5) == paramCalendar2.get(5));
  }
  
  private int determineTrackWhereAppointmentCanBeAdded(List<List<RegularAppointmentPane>> paramList, RegularAppointmentPane paramRegularAppointmentPane)
  {
    for (int i = 0;; i++)
    {
      if (i == paramList.size()) {
        paramList.add(new ArrayList());
      }
      if (!checkIfTheAppointmentOverlapsAnAppointmentAlreadyInThisTrack(paramList, i, paramRegularAppointmentPane)) {
        return i;
      }
    }
  }
  
  private boolean checkIfTheAppointmentOverlapsAnAppointmentAlreadyInThisTrack(List<List<RegularAppointmentPane>> paramList, int paramInt, RegularAppointmentPane paramRegularAppointmentPane)
  {
    List localList = (List)paramList.get(paramInt);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      RegularAppointmentPane localRegularAppointmentPane = (RegularAppointmentPane)localIterator.next();
      if (((localRegularAppointmentPane.start.equals(paramRegularAppointmentPane.start)) || (localRegularAppointmentPane.start.before(paramRegularAppointmentPane.end))) && ((localRegularAppointmentPane.end.equals(paramRegularAppointmentPane.start)) || (localRegularAppointmentPane.end.after(paramRegularAppointmentPane.start)))) {
        return true;
      }
    }
    return false;
  }
  
  private Calendar setTimeTo0000(Calendar paramCalendar)
  {
    paramCalendar.set(11, 0);
    paramCalendar.set(12, 0);
    paramCalendar.set(13, 0);
    paramCalendar.set(14, 0);
    return paramCalendar;
  }
  
  private Calendar setTimeTo2359(Calendar paramCalendar)
  {
    paramCalendar.set(11, 23);
    paramCalendar.set(12, 59);
    paramCalendar.set(13, 59);
    paramCalendar.set(14, 999);
    return paramCalendar;
  }
  
  private Calendar setTimeToNearestMinutes(Calendar paramCalendar, int paramInt)
  {
    paramCalendar.set(14, 0);
    paramCalendar.set(13, 0);
    int i = paramCalendar.get(12) % paramInt;
    if (i < paramInt / 2) {
      paramCalendar.add(12, -1 * i);
    } else {
      paramCalendar.add(12, paramInt - i);
    }
    return paramCalendar;
  }
  
  private Calendar copyYMD(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    paramCalendar2.set(1, paramCalendar1.get(1));
    paramCalendar2.set(2, paramCalendar1.get(2));
    paramCalendar2.set(5, paramCalendar1.get(5));
    return paramCalendar2;
  }
  
  class ImageButton
    extends ImageView
  {
    public ImageButton(Image paramImage)
    {
      super();
      setPickOnBounds(true);
      setOnMouseEntered(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown()) {
            ImageButton.this.setCursor(Cursor.HAND);
          }
        }
      });
      setOnMouseExited(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown()) {
            ImageButton.this.setCursor(Cursor.DEFAULT);
          }
        }
      });
    }
  }
  
  abstract class AbstractAppointmentPane
    extends Pane
  {
    Appointment appointment = null;
    boolean isFirstAreaOfAppointment = true;
    boolean isLastAreaOfAppointment = true;
    boolean isDraggable = true;
    Rectangle dragRectangle;
    double startX = 0.0D;
    double startY = 0.0D;
    boolean dragEventHasOccurred = false;
    Rectangle menuIcon = null;
    
    public AbstractAppointmentPane(Appointment paramAppointment)
    {
      this.appointment = paramAppointment;
      Tooltip.install(this, new Tooltip(paramAppointment.getSummary()));
      this.menuIcon = new MenuIcon(AgendaWeekSkin.this, this);
      setOnMousePressed(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          paramAnonymousMouseEvent.consume();
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown()) {
            return;
          }
          if (!AbstractAppointmentPane.this.isDraggable) {
            return;
          }
          AbstractAppointmentPane.this.dragEventHasOccurred = (!paramAnonymousMouseEvent.isPrimaryButtonDown());
          if (!AbstractAppointmentPane.this.isFirstAreaOfAppointment) {
            return;
          }
          AbstractAppointmentPane.this.setCursor(Cursor.MOVE);
          double d1 = NodeUtil.screenX(AbstractAppointmentPane.this) - NodeUtil.screenX(AgendaWeekSkin.this.dragPane);
          double d2 = NodeUtil.screenY(AbstractAppointmentPane.this) - NodeUtil.screenY(AgendaWeekSkin.this.dragPane);
          AbstractAppointmentPane.this.dragRectangle = new Rectangle(d1, d2, AbstractAppointmentPane.this.getWidth(), AbstractAppointmentPane.this.appointment.isWholeDay().booleanValue() ? AgendaWeekSkin.this.titleCalendarHeightProperty.get() : AbstractAppointmentPane.this.getHeight());
          AbstractAppointmentPane.this.dragRectangle.getStyleClass().add("GhostRectangle");
          AgendaWeekSkin.this.dragPane.getChildren().add(AbstractAppointmentPane.this.dragRectangle);
          AbstractAppointmentPane.this.startX = paramAnonymousMouseEvent.getScreenX();
          AbstractAppointmentPane.this.startY = paramAnonymousMouseEvent.getScreenY();
        }
      });
      setOnMouseDragged(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          paramAnonymousMouseEvent.consume();
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown()) {
            return;
          }
          AbstractAppointmentPane.this.dragEventHasOccurred = true;
          if (AbstractAppointmentPane.this.dragRectangle == null) {
            return;
          }
          double d1 = paramAnonymousMouseEvent.getScreenX() - AbstractAppointmentPane.this.startX;
          double d2 = paramAnonymousMouseEvent.getScreenY() - AbstractAppointmentPane.this.startY;
          double d3 = NodeUtil.screenX(AbstractAppointmentPane.this) - NodeUtil.screenX(AgendaWeekSkin.this.dragPane) + d1;
          double d4 = NodeUtil.screenY(AbstractAppointmentPane.this) - NodeUtil.screenY(AgendaWeekSkin.this.dragPane) + d2;
          AbstractAppointmentPane.this.dragRectangle.setX(d3);
          AbstractAppointmentPane.this.dragRectangle.setY(d4);
          paramAnonymousMouseEvent.consume();
        }
      });
      setOnMouseReleased(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          paramAnonymousMouseEvent.consume();
          int i = AbstractAppointmentPane.this.dragRectangle != null ? 1 : 0;
          AbstractAppointmentPane.this.setCursor(Cursor.HAND);
          AgendaWeekSkin.this.dragPane.getChildren().remove(AbstractAppointmentPane.this.dragRectangle);
          AbstractAppointmentPane.this.dragRectangle = null;
          if (!AbstractAppointmentPane.this.dragEventHasOccurred)
          {
            if (!paramAnonymousMouseEvent.isShiftDown()) {
              ((Agenda)AgendaWeekSkin.this.getSkinnable()).selectedAppointments().clear();
            }
            if (!((Agenda)AgendaWeekSkin.this.getSkinnable()).selectedAppointments().contains(AbstractAppointmentPane.this.appointment)) {
              ((Agenda)AgendaWeekSkin.this.getSkinnable()).selectedAppointments().add(AbstractAppointmentPane.this.appointment);
            }
            return;
          }
          if (i == 0) {
            return;
          }
          Iterator localIterator = AgendaWeekSkin.this.weekPane.dayPanes.iterator();
          Object localObject;
          double d1;
          double d2;
          Appointment localAppointment;
          Calendar localCalendar1;
          while (localIterator.hasNext())
          {
            localObject = (DayPane)localIterator.next();
            d1 = NodeUtil.screenX((Node)localObject);
            d2 = NodeUtil.screenY((Node)localObject);
            if ((d1 <= paramAnonymousMouseEvent.getScreenX()) && (paramAnonymousMouseEvent.getScreenX() < d1 + ((DayPane)localObject).getWidth()) && (d2 <= paramAnonymousMouseEvent.getScreenY()) && (paramAnonymousMouseEvent.getScreenY() < d2 + ((DayPane)localObject).getHeight()))
            {
              localAppointment = AbstractAppointmentPane.this.appointment;
              localCalendar1 = (Calendar)((DayPane)localObject).calendarObjectProperty.get();
              if (localAppointment.isWholeDay().booleanValue())
              {
                Calendar localCalendar2 = AgendaWeekSkin.this.copyYMD(localCalendar1, (Calendar)localAppointment.getStartTime().clone());
                Calendar localCalendar3 = localAppointment.getEndTime() == null ? AgendaWeekSkin.this.setTimeTo2359((Calendar)localCalendar1.clone()) : AgendaWeekSkin.this.copyYMD(localCalendar1, (Calendar)localAppointment.getEndTime().clone());
                localAppointment.setStartTime(localCalendar2);
                localAppointment.setEndTime(localCalendar3);
                localAppointment.setWholeDay(Boolean.valueOf(false));
              }
              else
              {
                long l = localAppointment.getEndTime().getTimeInMillis() - localAppointment.getStartTime().getTimeInMillis();
                Calendar localCalendar4 = AgendaWeekSkin.this.copyYMD(localCalendar1, (Calendar)localAppointment.getStartTime().clone());
                int j = (int)((paramAnonymousMouseEvent.getScreenY() - AbstractAppointmentPane.this.startY) * AgendaWeekSkin.this.durationInMSPerPixelProperty.get());
                localCalendar4.add(14, j);
                AgendaWeekSkin.this.setTimeToNearestMinutes(localCalendar4, 5);
                while ((!AgendaWeekSkin.this.isSameDay(localCalendar4, localCalendar1)) && (localCalendar4.before(localCalendar1))) {
                  localCalendar4.add(12, 1);
                }
                while ((!AgendaWeekSkin.this.isSameDay(localCalendar4, localCalendar1)) && (localCalendar4.after(localCalendar1))) {
                  localCalendar4.add(12, -1);
                }
                Calendar localCalendar5 = (Calendar)localCalendar4.clone();
                localCalendar5.add(14, (int)l);
                localAppointment.setStartTime(localCalendar4);
                localAppointment.setEndTime(localCalendar5);
              }
            }
          }
          localIterator = AgendaWeekSkin.this.weekHeaderPane.dayHeaderPanes.iterator();
          while (localIterator.hasNext())
          {
            localObject = (DayHeaderPane)localIterator.next();
            d1 = NodeUtil.screenX((Node)localObject);
            d2 = NodeUtil.screenY((Node)localObject);
            if ((d1 <= paramAnonymousMouseEvent.getScreenX()) && (paramAnonymousMouseEvent.getScreenX() < d1 + ((DayHeaderPane)localObject).getWidth()) && (d2 <= paramAnonymousMouseEvent.getScreenY()) && (paramAnonymousMouseEvent.getScreenY() < d2 + ((DayHeaderPane)localObject).getHeight()))
            {
              localAppointment = AbstractAppointmentPane.this.appointment;
              localCalendar1 = AgendaWeekSkin.this.copyYMD((Calendar)((DayHeaderPane)localObject).dayPane.calendarObjectProperty.get(), (Calendar)localAppointment.getStartTime().clone());
              localAppointment.setStartTime(localCalendar1);
              localAppointment.setWholeDay(Boolean.valueOf(true));
            }
          }
          AgendaWeekSkin.this.setupAppointments();
        }
      });
    }
  }
  
  abstract class AbstractDayAppointmentPane
    extends AbstractAppointmentPane
  {
    final DayPane dayPane;
    Rectangle historicalVisualizer = null;
    Calendar start = null;
    String startAsString = null;
    Calendar end = null;
    String endAsString = null;
    long durationInMS = 0L;
    DurationDragger durationDragger = null;
    
    public AbstractDayAppointmentPane(Appointment paramAppointment, DayPane paramDayPane)
    {
      super(paramAppointment);
      this.dayPane = paramDayPane;
      getStyleClass().add("Appointment");
      getStyleClass().add(paramAppointment.getAppointmentGroup().getStyleClass());
    }
  }
  
  class DurationDragger
    extends Rectangle
  {
    final AbstractDayAppointmentPane appointmentPane;
    Rectangle resizeRectangle;
    
    public DurationDragger(AbstractDayAppointmentPane paramAbstractDayAppointmentPane)
    {
      this.appointmentPane = paramAbstractDayAppointmentPane;
      xProperty().bind(paramAbstractDayAppointmentPane.widthProperty().multiply(0.25D));
      yProperty().bind(paramAbstractDayAppointmentPane.heightProperty().subtract(5));
      widthProperty().bind(paramAbstractDayAppointmentPane.widthProperty().multiply(0.5D));
      setHeight(3.0D);
      getStyleClass().add("DurationDragger");
      setOnMouseEntered(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown())
          {
            DurationDragger.this.setCursor(Cursor.HAND);
            paramAnonymousMouseEvent.consume();
          }
        }
      });
      setOnMouseExited(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown())
          {
            DurationDragger.this.setCursor(Cursor.DEFAULT);
            paramAnonymousMouseEvent.consume();
          }
        }
      });
      setOnMousePressed(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          DurationDragger.this.setCursor(Cursor.V_RESIZE);
          DurationDragger.this.resizeRectangle = new Rectangle(DurationDragger.this.appointmentPane.getLayoutX(), DurationDragger.this.appointmentPane.getLayoutY(), DurationDragger.this.appointmentPane.getWidth(), DurationDragger.this.appointmentPane.getHeight());
          DurationDragger.this.resizeRectangle.getStyleClass().add("GhostRectangle");
          DurationDragger.this.appointmentPane.dayPane.getChildren().add(DurationDragger.this.resizeRectangle);
          paramAnonymousMouseEvent.consume();
        }
      });
      setOnMouseDragged(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          double d1 = NodeUtil.screenY(DurationDragger.this.appointmentPane);
          double d2 = paramAnonymousMouseEvent.getScreenY();
          double d3 = d2 - d1;
          if (d3 < 5.0D) {
            d3 = 5.0D;
          }
          DurationDragger.this.resizeRectangle.setHeight(d3);
          paramAnonymousMouseEvent.consume();
        }
      });
      setOnMouseReleased(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          int i = (int)(DurationDragger.this.resizeRectangle.getHeight() * AgendaWeekSkin.this.durationInMSPerPixelProperty.get());
          Calendar localCalendar = (Calendar)DurationDragger.this.appointmentPane.appointment.getStartTime().clone();
          localCalendar.add(14, i);
          AgendaWeekSkin.this.setTimeToNearestMinutes(localCalendar, 5);
          DurationDragger.this.appointmentPane.appointment.setEndTime(localCalendar);
          AgendaWeekSkin.this.setupAppointments();
          DurationDragger.this.setCursor(Cursor.HAND);
          DurationDragger.this.appointmentPane.dayPane.getChildren().remove(DurationDragger.this.resizeRectangle);
          DurationDragger.this.resizeRectangle = null;
          paramAnonymousMouseEvent.consume();
        }
      });
    }
  }
  
  class MenuIcon
    extends Rectangle
  {
    public MenuIcon(final AbstractAppointmentPane paramAbstractAppointmentPane)
    {
      setX(3.0D);
      setY(3.0D);
      setWidth(6.0D);
      setHeight(3.0D);
      getStyleClass().add("MenuIcon");
      setOnMouseEntered(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown())
          {
            MenuIcon.this.setCursor(Cursor.HAND);
            paramAnonymousMouseEvent.consume();
          }
        }
      });
      setOnMouseExited(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (!paramAnonymousMouseEvent.isPrimaryButtonDown())
          {
            MenuIcon.this.setCursor(Cursor.DEFAULT);
            paramAnonymousMouseEvent.consume();
          }
        }
      });
      setOnMousePressed(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          paramAnonymousMouseEvent.consume();
        }
      });
      setOnMouseReleased(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          paramAnonymousMouseEvent.consume();
        }
      });
      setOnMouseClicked(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          paramAnonymousMouseEvent.consume();
          AgendaWeekSkin.this.showMenu(paramAnonymousMouseEvent, paramAbstractAppointmentPane);
        }
      });
    }
  }
  
  class HistoricalVisualizer
    extends Rectangle
  {
    public HistoricalVisualizer(Pane paramPane)
    {
      setMouseTransparent(true);
      xProperty().set(0.0D);
      yProperty().set(0.0D);
      widthProperty().bind(paramPane.prefWidthProperty());
      heightProperty().bind(paramPane.prefHeightProperty());
      setVisible(false);
      getStyleClass().add("History");
    }
  }
  
  class RegularAppointmentPane
    extends AbstractDayAppointmentPane
  {
    List<RegularAppointmentPane> clusterMembers = null;
    List<List<RegularAppointmentPane>> clusterTracks = null;
    RegularAppointmentPane clusterOwner = null;
    int clusterTrackIdx = -1;
    
    public RegularAppointmentPane(Appointment paramAppointment, DayPane paramDayPane)
    {
      super(paramAppointment, paramDayPane);
      Calendar localCalendar1 = AgendaWeekSkin.this.setTimeTo0000((Calendar)((Calendar)paramDayPane.calendarObjectProperty.get()).clone());
      this.start = (paramAppointment.getStartTime().before(localCalendar1) ? localCalendar1 : (Calendar)paramAppointment.getStartTime().clone());
      Calendar localCalendar2 = AgendaWeekSkin.this.setTimeTo2359((Calendar)((Calendar)paramDayPane.calendarObjectProperty.get()).clone());
      this.end = (paramAppointment.getEndTime().after(localCalendar2) ? localCalendar2 : (Calendar)paramAppointment.getEndTime().clone());
      this.durationInMS = (this.end.getTimeInMillis() - this.start.getTimeInMillis());
      this.isFirstAreaOfAppointment = this.start.equals(paramAppointment.getStartTime());
      this.isLastAreaOfAppointment = this.end.equals(paramAppointment.getEndTime());
      this.startAsString = AgendaWeekSkin.timeFormat.format(this.start.getTime());
      this.endAsString = AgendaWeekSkin.timeFormat.format(this.end.getTime());
      Text localText = new Text(this.startAsString + "-" + this.endAsString);
      localText.getStyleClass().add("AppointmentTimeLabel");
      localText.setX(3.0D);
      localText.setY(localText.prefHeight(0.0D));
      Object localObject = new Rectangle(0.0D, 0.0D, 0.0D, 0.0D);
      ((Rectangle)localObject).widthProperty().bind(widthProperty().subtract(3.0D));
      ((Rectangle)localObject).heightProperty().bind(heightProperty());
      localText.setClip((Node)localObject);
      getChildren().add(localText);
      localObject = new Text(paramAppointment.getSummary());
      ((Text)localObject).getStyleClass().add("AppointmentLabel");
      ((Text)localObject).setX(3.0D);
      ((Text)localObject).setY(localText.getY() + AgendaWeekSkin.this.textHeight1MProperty.get());
      ((Text)localObject).wrappingWidthProperty().bind(widthProperty().subtract(3.0D));
      Rectangle localRectangle = new Rectangle(0.0D, 0.0D, 0.0D, 0.0D);
      localRectangle.widthProperty().bind(widthProperty());
      localRectangle.heightProperty().bind(heightProperty().subtract(3.0D));
      ((Text)localObject).setClip(localRectangle);
      getChildren().add(localObject);
      if (this.isLastAreaOfAppointment == true)
      {
        this.durationDragger = new DurationDragger(AgendaWeekSkin.this, this);
        getChildren().add(this.durationDragger);
      }
      if (!paramAppointment.isWholeDay().booleanValue()) {
        getChildren().add(this.menuIcon);
      }
      this.historicalVisualizer = new HistoricalVisualizer(AgendaWeekSkin.this, this);
      getChildren().add(this.historicalVisualizer);
    }
    
    public String toString()
    {
      return super.toString() + ";" + this.startAsString + "-" + this.endAsString + ";" + this.durationInMS + "ms" + ";" + this.appointment.getSummary();
    }
  }
  
  class WholedayAppointmentPane
    extends AbstractDayAppointmentPane
  {
    public WholedayAppointmentPane(Appointment paramAppointment, DayPane paramDayPane)
    {
      super(paramAppointment, paramDayPane);
      this.isDraggable = false;
      this.start = AgendaWeekSkin.this.setTimeTo0000((Calendar)paramAppointment.getStartTime().clone());
      this.end = AgendaWeekSkin.this.setTimeTo2359((Calendar)paramAppointment.getStartTime().clone());
      this.durationInMS = (this.end.getTimeInMillis() - this.start.getTimeInMillis());
      this.startAsString = AgendaWeekSkin.timeFormat.format(this.start.getTime());
      this.endAsString = AgendaWeekSkin.timeFormat.format(this.end.getTime());
      this.historicalVisualizer = new HistoricalVisualizer(AgendaWeekSkin.this, this);
      getChildren().add(this.historicalVisualizer);
    }
    
    public String toString()
    {
      return super.toString() + ";" + this.startAsString + " wholeday" + ";" + this.appointment.getSummary();
    }
  }
  
  class DayPane
    extends Pane
  {
    ObjectProperty<Calendar> calendarObjectProperty = new SimpleObjectProperty(this, "calendar");
    DayHeaderPane dayHeaderPane = null;
    Rectangle resizeRectangle = null;
    boolean dragged = false;
    final List<RegularAppointmentPane> regularAppointmentPanes = new ArrayList();
    final List<WholedayAppointmentPane> wholedayAppointmentPanes = new ArrayList();
    
    public DayPane()
    {
      getStyleClass().add("Day");
      widthProperty().addListener(new InvalidationListener()
      {
        public void invalidated(Observable paramAnonymousObservable)
        {
          DayPane.this.relayout();
        }
      });
      heightProperty().addListener(new InvalidationListener()
      {
        public void invalidated(Observable paramAnonymousObservable)
        {
          DayPane.this.relayout();
        }
      });
      setOnMousePressed(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (((Agenda)AgendaWeekSkin.this.getSkinnable()).createAppointmentCallbackProperty().get() == null) {
            return;
          }
          DayPane.this.setCursor(Cursor.V_RESIZE);
          double d = paramAnonymousMouseEvent.getScreenY() - NodeUtil.screenY(DayPane.this);
          DayPane.this.resizeRectangle = new Rectangle(0.0D, d, AgendaWeekSkin.this.dayWidthProperty.get(), 10.0D);
          DayPane.this.resizeRectangle.getStyleClass().add("GhostRectangle");
          DayPane.this.getChildren().add(DayPane.this.resizeRectangle);
          paramAnonymousMouseEvent.consume();
          DayPane.this.dragged = false;
          ((Agenda)AgendaWeekSkin.this.getSkinnable()).selectedAppointments().clear();
        }
      });
      setOnMouseDragged(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (DayPane.this.resizeRectangle == null) {
            return;
          }
          double d = paramAnonymousMouseEvent.getScreenY() - NodeUtil.screenY(DayPane.this.resizeRectangle);
          if (d < 5.0D) {
            d = 5.0D;
          }
          DayPane.this.resizeRectangle.setHeight(d);
          paramAnonymousMouseEvent.consume();
          DayPane.this.dragged = true;
        }
      });
      setOnMouseReleased(new EventHandler()
      {
        public void handle(MouseEvent paramAnonymousMouseEvent)
        {
          if (DayPane.this.resizeRectangle == null) {
            return;
          }
          paramAnonymousMouseEvent.consume();
          DayPane.this.setCursor(Cursor.HAND);
          DayPane.this.getChildren().remove(DayPane.this.resizeRectangle);
          if (!DayPane.this.dragged) {
            return;
          }
          Calendar localCalendar1 = AgendaWeekSkin.this.setTimeTo0000((Calendar)((Calendar)DayPane.this.calendarObjectProperty.get()).clone());
          localCalendar1.add(14, (int)(DayPane.this.resizeRectangle.getY() * AgendaWeekSkin.this.durationInMSPerPixelProperty.get()));
          AgendaWeekSkin.this.setTimeToNearestMinutes(localCalendar1, 5);
          Calendar localCalendar2 = (Calendar)localCalendar1.clone();
          localCalendar2.add(14, (int)(DayPane.this.resizeRectangle.getHeight() * AgendaWeekSkin.this.durationInMSPerPixelProperty.get()));
          AgendaWeekSkin.this.setTimeToNearestMinutes(localCalendar2, 5);
          DayPane.this.resizeRectangle = null;
          Appointment localAppointment = (Appointment)((Callback)((Agenda)AgendaWeekSkin.this.getSkinnable()).createAppointmentCallbackProperty().get()).call(new CalendarRange(localCalendar1, localCalendar2));
          if (localAppointment != null) {
            ((Agenda)AgendaWeekSkin.this.getSkinnable()).appointments().add(localAppointment);
          }
        }
      });
    }
    
    public List<AbstractAppointmentPane> allAbstractAppointmentPanes()
    {
      ArrayList localArrayList = new ArrayList(this.regularAppointmentPanes);
      localArrayList.addAll(this.wholedayAppointmentPanes);
      localArrayList.addAll(this.dayHeaderPane.appointmentHeaderPanes);
      return localArrayList;
    }
    
    private void relayout()
    {
      int i = 0;
      Iterator localIterator1 = this.wholedayAppointmentPanes.iterator();
      while (localIterator1.hasNext())
      {
        WholedayAppointmentPane localWholedayAppointmentPane = (WholedayAppointmentPane)localIterator1.next();
        localWholedayAppointmentPane.setLayoutX(i * 5.0D);
        localWholedayAppointmentPane.setLayoutY(0.0D);
        localWholedayAppointmentPane.setPrefSize(5.0D, AgendaWeekSkin.this.dayHeightProperty.get());
        i++;
      }
      double d1 = AgendaWeekSkin.this.dayContentWidthProperty.get() - i * 5.0D;
      Iterator localIterator2 = this.regularAppointmentPanes.iterator();
      while (localIterator2.hasNext())
      {
        RegularAppointmentPane localRegularAppointmentPane = (RegularAppointmentPane)localIterator2.next();
        localRegularAppointmentPane.setLayoutX(i * 5.0D + d1 / localRegularAppointmentPane.clusterOwner.clusterTracks.size() * localRegularAppointmentPane.clusterTrackIdx);
        int j = localRegularAppointmentPane.start.get(11) * 60 + localRegularAppointmentPane.start.get(12);
        localRegularAppointmentPane.setLayoutY(AgendaWeekSkin.this.dayHeightProperty.get() / 1440.0D * j);
        double d2 = (AgendaWeekSkin.this.dayContentWidthProperty.get() - this.wholedayAppointmentPanes.size() * 5.0D) * (1.0D / localRegularAppointmentPane.clusterOwner.clusterTracks.size());
        if (localRegularAppointmentPane.clusterTrackIdx < localRegularAppointmentPane.clusterOwner.clusterTracks.size() - 1) {
          d2 *= 1.75D;
        }
        localRegularAppointmentPane.setPrefWidth(d2);
        double d3 = AgendaWeekSkin.this.dayHeightProperty.get() / 1440.0D * (localRegularAppointmentPane.durationInMS / 1000L / 60L);
        if (d3 < 6.0D) {
          d3 = 6.0D;
        }
        localRegularAppointmentPane.setPrefHeight(d3);
      }
    }
    
    public void setupAppointments()
    {
      ArrayList localArrayList1 = new ArrayList(this.regularAppointmentPanes);
      ArrayList localArrayList2 = new ArrayList(this.wholedayAppointmentPanes);
      this.regularAppointmentPanes.clear();
      this.wholedayAppointmentPanes.clear();
      if (this.calendarObjectProperty.get() == null) {
        return;
      }
      Object localObject1 = ((Agenda)AgendaWeekSkin.this.getSkinnable()).appointments().iterator();
      Object localObject3;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Appointment)((Iterator)localObject1).next();
        if (((Appointment)localObject2).isWholeDay().booleanValue())
        {
          if (AgendaWeekSkin.this.isSameDay((Calendar)this.calendarObjectProperty.get(), ((Appointment)localObject2).getStartTime()))
          {
            localObject3 = new WholedayAppointmentPane(AgendaWeekSkin.this, (Appointment)localObject2, this);
            this.wholedayAppointmentPanes.add(localObject3);
          }
        }
        else
        {
          localObject3 = new RegularAppointmentPane(AgendaWeekSkin.this, (Appointment)localObject2, this);
          if ((AgendaWeekSkin.this.isSameDay((Calendar)this.calendarObjectProperty.get(), ((RegularAppointmentPane)localObject3).start)) && (AgendaWeekSkin.this.isSameDay((Calendar)this.calendarObjectProperty.get(), ((RegularAppointmentPane)localObject3).end))) {
            this.regularAppointmentPanes.add(localObject3);
          }
        }
      }
      Collections.sort(this.regularAppointmentPanes, new Comparator()
      {
        public int compare(AbstractDayAppointmentPane paramAnonymousAbstractDayAppointmentPane1, AbstractDayAppointmentPane paramAnonymousAbstractDayAppointmentPane2)
        {
          if (!paramAnonymousAbstractDayAppointmentPane1.startAsString.equals(paramAnonymousAbstractDayAppointmentPane2.startAsString)) {
            return paramAnonymousAbstractDayAppointmentPane1.startAsString.compareTo(paramAnonymousAbstractDayAppointmentPane2.startAsString);
          }
          return paramAnonymousAbstractDayAppointmentPane1.durationInMS > paramAnonymousAbstractDayAppointmentPane2.durationInMS ? -1 : 1;
        }
      });
      localObject1 = null;
      Object localObject2 = this.regularAppointmentPanes.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (RegularAppointmentPane)((Iterator)localObject2).next();
        if (localObject1 == null)
        {
          localObject1 = localObject3;
          ((RegularAppointmentPane)localObject1).clusterTracks = new ArrayList();
        }
        int i = AgendaWeekSkin.this.determineTrackWhereAppointmentCanBeAdded(((RegularAppointmentPane)localObject1).clusterTracks, (RegularAppointmentPane)localObject3);
        if (i == 0)
        {
          boolean bool = false;
          for (int j = 1; (j < ((RegularAppointmentPane)localObject1).clusterTracks.size()) && (!bool); j++) {
            bool = AgendaWeekSkin.this.checkIfTheAppointmentOverlapsAnAppointmentAlreadyInThisTrack(((RegularAppointmentPane)localObject1).clusterTracks, j, (RegularAppointmentPane)localObject3);
          }
          if (!bool)
          {
            localObject1 = localObject3;
            ((RegularAppointmentPane)localObject1).clusterMembers = new ArrayList();
            ((RegularAppointmentPane)localObject1).clusterTracks = new ArrayList();
            ((RegularAppointmentPane)localObject1).clusterTracks.add(new ArrayList());
          }
        }
        ((RegularAppointmentPane)localObject1).clusterMembers.add(localObject3);
        ((List)((RegularAppointmentPane)localObject1).clusterTracks.get(i)).add(localObject3);
        ((RegularAppointmentPane)localObject3).clusterOwner = ((RegularAppointmentPane)localObject1);
        ((RegularAppointmentPane)localObject3).clusterTrackIdx = i;
      }
      relayout();
      getChildren().removeAll(localArrayList1);
      getChildren().removeAll(localArrayList2);
      getChildren().addAll(this.wholedayAppointmentPanes);
      getChildren().addAll(this.regularAppointmentPanes);
      this.dayHeaderPane.setupAppointments();
    }
  }
  
  class WeekPane
    extends Pane
  {
    final List<DayPane> dayPanes = new ArrayList();
    
    public WeekPane()
    {
      getStyleClass().add("WeekPane");
      Object localObject;
      for (int i = 0; i < 24; i++)
      {
        localObject = new Line(0.0D, 10.0D, 100.0D, 10.0D);
        ((Line)localObject).getStyleClass().add("HourLine");
        ((Line)localObject).startXProperty().set(0.0D);
        ((Line)localObject).startYProperty().bind(AgendaWeekSkin.this.hourHeighProperty.multiply(i));
        ((Line)localObject).endXProperty().bind(widthProperty());
        ((Line)localObject).endYProperty().bind(((Line)localObject).startYProperty());
        getChildren().add(localObject);
        localObject = new Line(0.0D, 10.0D, 100.0D, 10.0D);
        ((Line)localObject).getStyleClass().add("HalfHourLine");
        ((Line)localObject).startXProperty().bind(AgendaWeekSkin.this.timeWidthProperty);
        ((Line)localObject).endXProperty().bind(widthProperty());
        ((Line)localObject).startYProperty().bind(AgendaWeekSkin.this.hourHeighProperty.multiply(i + 0.5D));
        ((Line)localObject).endYProperty().bind(((Line)localObject).startYProperty());
        getChildren().add(localObject);
        localObject = new Text(i + ":00");
        ((Text)localObject).xProperty().bind(AgendaWeekSkin.this.timeWidthProperty.subtract(((Text)localObject).getBoundsInParent().getWidth()).subtract(5.0D));
        ((Text)localObject).yProperty().bind(AgendaWeekSkin.this.hourHeighProperty.multiply(i));
        ((Text)localObject).setTranslateY(((Text)localObject).getBoundsInParent().getHeight());
        ((Text)localObject).getStyleClass().add("HourLabel");
        ((Text)localObject).setFontSmoothingType(FontSmoothingType.LCD);
        getChildren().add(localObject);
      }
      for (i = 0; i < 7; i++)
      {
        localObject = new DayPane(AgendaWeekSkin.this);
        ((DayPane)localObject).layoutXProperty().bind(AgendaWeekSkin.this.dayWidthProperty.multiply(i).add(AgendaWeekSkin.this.dayFirstColumnXProperty));
        ((DayPane)localObject).layoutYProperty().set(0.0D);
        ((DayPane)localObject).prefWidthProperty().bind(AgendaWeekSkin.this.dayWidthProperty);
        ((DayPane)localObject).prefHeightProperty().bind(AgendaWeekSkin.this.dayHeightProperty);
        getChildren().add(localObject);
        this.dayPanes.add(localObject);
      }
    }
  }
  
  class AppointmentHeaderPane
    extends AbstractAppointmentPane
  {
    final Rectangle historicalVisualizer;
    
    public AppointmentHeaderPane(Appointment paramAppointment)
    {
      super(paramAppointment);
      getStyleClass().add("Appointment");
      getStyleClass().add(paramAppointment.getAppointmentGroup().getStyleClass());
      Text localText = new Text(paramAppointment.getSummary());
      localText.getStyleClass().add("AppointmentLabel");
      localText.setX(3.0D);
      localText.setY(AgendaWeekSkin.this.textHeight1MProperty.get());
      Rectangle localRectangle = new Rectangle(0.0D, 0.0D, 0.0D, 0.0D);
      localRectangle.widthProperty().bind(widthProperty().subtract(3.0D));
      localRectangle.heightProperty().bind(heightProperty());
      localText.setClip(localRectangle);
      getChildren().add(localText);
      getChildren().add(this.menuIcon);
      this.historicalVisualizer = new HistoricalVisualizer(AgendaWeekSkin.this, this);
      getChildren().add(this.historicalVisualizer);
    }
  }
  
  class DayHeaderPane
    extends Pane
  {
    DayPane dayPane = null;
    Text calendarText = null;
    final List<AppointmentHeaderPane> appointmentHeaderPanes = new ArrayList();
    
    public DayHeaderPane(DayPane paramDayPane)
    {
      getStyleClass().add("DayHeader");
      this.dayPane = paramDayPane;
      paramDayPane.dayHeaderPane = this;
      this.calendarText = new Text("?");
      this.calendarText.getStyleClass().add("Calendar");
      this.calendarText.setX(3.0D);
      this.calendarText.setY(this.calendarText.prefHeight(0.0D));
      Rectangle localRectangle = new Rectangle(0.0D, 0.0D, 0.0D, 0.0D);
      localRectangle.widthProperty().bind(widthProperty().subtract(3.0D));
      localRectangle.heightProperty().bind(heightProperty());
      this.calendarText.setClip(localRectangle);
      getChildren().add(this.calendarText);
      paramDayPane.calendarObjectProperty.addListener(new InvalidationListener()
      {
        public void invalidated(Observable paramAnonymousObservable)
        {
          String str = AgendaWeekSkin.this.dayOfWeekDateFormat.format(((Calendar)DayHeaderPane.this.dayPane.calendarObjectProperty.get()).getTime()) + " " + AgendaWeekSkin.this.dateFormat.format(((Calendar)DayHeaderPane.this.dayPane.calendarObjectProperty.get()).getTime());
          DayHeaderPane.this.calendarText.setText(str);
        }
      });
      widthProperty().addListener(new InvalidationListener()
      {
        public void invalidated(Observable paramAnonymousObservable)
        {
          DayHeaderPane.this.relayout();
        }
      });
      heightProperty().addListener(new InvalidationListener()
      {
        public void invalidated(Observable paramAnonymousObservable)
        {
          DayHeaderPane.this.relayout();
        }
      });
      relayout();
    }
    
    public void relayout()
    {
      int i = AgendaWeekSkin.this.highestNumberOfWholedayAppointmentsProperty.get() - this.appointmentHeaderPanes.size();
      Iterator localIterator = this.appointmentHeaderPanes.iterator();
      while (localIterator.hasNext())
      {
        AppointmentHeaderPane localAppointmentHeaderPane = (AppointmentHeaderPane)localIterator.next();
        int j = this.appointmentHeaderPanes.indexOf(localAppointmentHeaderPane);
        localAppointmentHeaderPane.setLayoutX(j * 5.0D);
        localAppointmentHeaderPane.setLayoutY(AgendaWeekSkin.this.titleCalendarHeightProperty.get() + (j + i) * AgendaWeekSkin.this.wholedayTitleHeightProperty.get());
        localAppointmentHeaderPane.setPrefSize(AgendaWeekSkin.this.dayWidthProperty.get() - j * 5.0D, (this.appointmentHeaderPanes.size() - j) * AgendaWeekSkin.this.wholedayTitleHeightProperty.get());
      }
    }
    
    public void setupAppointments()
    {
      getChildren().removeAll(this.appointmentHeaderPanes);
      this.appointmentHeaderPanes.clear();
      Iterator localIterator = this.dayPane.wholedayAppointmentPanes.iterator();
      while (localIterator.hasNext())
      {
        WholedayAppointmentPane localWholedayAppointmentPane = (WholedayAppointmentPane)localIterator.next();
        AppointmentHeaderPane localAppointmentHeaderPane = new AppointmentHeaderPane(AgendaWeekSkin.this, localWholedayAppointmentPane.appointment);
        getChildren().add(localAppointmentHeaderPane);
        this.appointmentHeaderPanes.add(localAppointmentHeaderPane);
      }
      relayout();
    }
  }
  
  class WeekHeaderPane
    extends Pane
  {
    final List<DayHeaderPane> dayHeaderPanes = new ArrayList();
    
    public WeekHeaderPane()
    {
      for (int i = 0; i < 7; i++)
      {
        DayHeaderPane localDayHeaderPane = new DayHeaderPane(AgendaWeekSkin.this, (DayPane)AgendaWeekSkin.this.weekPane.dayPanes.get(i));
        localDayHeaderPane.layoutXProperty().bind(((DayPane)AgendaWeekSkin.this.weekPane.dayPanes.get(i)).layoutXProperty());
        localDayHeaderPane.layoutYProperty().set(0.0D);
        localDayHeaderPane.prefWidthProperty().bind(((DayPane)AgendaWeekSkin.this.weekPane.dayPanes.get(i)).prefWidthProperty());
        localDayHeaderPane.prefHeightProperty().bind(heightProperty());
        getChildren().add(localDayHeaderPane);
        this.dayHeaderPanes.add(localDayHeaderPane);
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/internal/scene/control/skin/AgendaWeekSkin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */