package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class SplitFlap
  extends Control
{
  public static final String[] TIME_0_TO_5 = { "1", "2", "3", "4", "5", "0" };
  public static final String[] TIME_0_TO_9 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
  public static final String[] NUMERIC = { " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
  public static final String[] ALPHANUMERIC = { " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
  public static final String[] EXTENDED = { " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "-", "/", ":", ",", ".", ";", "@", "#", "+", "?", "!", "%", "$", "=", "<", ">" };
  private static final String DEFAULT_STYLE_CLASS = "split-flap";
  private ObjectProperty<Color> color = new SimpleObjectProperty(Color.rgb(56, 56, 56));
  private ObjectProperty<Color> upperFlapTopColor = new SimpleObjectProperty(Color.rgb(43, 44, 39));
  private ObjectProperty<Color> upperFlapBottomColor = new SimpleObjectProperty(Color.rgb(59, 58, 53));
  private ObjectProperty<Color> lowerFlapTopColor = new SimpleObjectProperty(Color.rgb(59, 58, 53));
  private ObjectProperty<Color> lowerFlapBottomColor = new SimpleObjectProperty(Color.rgb(40, 41, 35));
  private ObjectProperty<Color> textColor = new SimpleObjectProperty(Color.WHITE);
  private ObjectProperty<Color> textUpperFlapColor = new SimpleObjectProperty(Color.rgb(255, 255, 255));
  private ObjectProperty<Color> textLowerFlapColor = new SimpleObjectProperty(Color.rgb(248, 248, 248));
  private DoubleProperty flapCornerRadius = new SimpleDoubleProperty(6.0D);
  private BooleanProperty upperFlapHighlightEnabled = new SimpleBooleanProperty(false);
  private BooleanProperty lowerFlapeHighlightEnabled = new SimpleBooleanProperty(false);
  private BooleanProperty darkFixtureEnabled = new SimpleBooleanProperty(true);
  private ObjectProperty<String[]> selection;
  private ArrayList<String> selectedSet;
  private BooleanProperty imageMode;
  private int currentSelectionIndex;
  private int nextSelectionIndex;
  private int previousSelectionIndex;
  private BooleanProperty interactive;
  private StringProperty text;
  private LongProperty flipTimeInMs;
  private BooleanProperty countdownMode;
  private BooleanProperty soundOn;
  private ObjectProperty<Sound> sound;
  private BooleanProperty frameVisible;
  private ObjectProperty<Color> frameTopColor;
  private ObjectProperty<Color> frameBottomColor;
  private BooleanProperty backgroundVisible;
  private boolean keepAspect;
  
  public SplitFlap()
  {
    this(EXTENDED, " ");
  }
  
  public SplitFlap(String[] paramArrayOfString)
  {
    this(paramArrayOfString, paramArrayOfString[0]);
  }
  
  public SplitFlap(String[] paramArrayOfString, String paramString)
  {
    this.selection = new SimpleObjectProperty(paramArrayOfString.length == 0 ? EXTENDED : paramArrayOfString);
    this.selectedSet = new ArrayList(64);
    this.imageMode = new SimpleBooleanProperty(false);
    this.currentSelectionIndex = 0;
    this.nextSelectionIndex = 1;
    this.previousSelectionIndex = (((String[])this.selection.get()).length - 1);
    this.interactive = new SimpleBooleanProperty(false);
    this.text = new SimpleStringProperty(paramString);
    this.flipTimeInMs = new SimpleLongProperty(200L);
    this.countdownMode = new SimpleBooleanProperty(false);
    this.soundOn = new SimpleBooleanProperty(false);
    this.sound = new SimpleObjectProperty(Sound.SOUND2);
    this.frameVisible = new SimpleBooleanProperty(true);
    this.frameTopColor = new SimpleObjectProperty(Color.rgb(52, 53, 43));
    this.frameBottomColor = new SimpleObjectProperty(Color.rgb(61, 61, 55));
    this.backgroundVisible = new SimpleBooleanProperty(true);
    this.keepAspect = false;
    getStyleClass().add("split-flap");
    this.selectedSet.addAll(Arrays.asList(EXTENDED));
  }
  
  public final Color getColor()
  {
    return (Color)this.color.get();
  }
  
  public final void setColor(Color paramColor)
  {
    this.lowerFlapTopColor.set(paramColor.brighter());
    this.lowerFlapBottomColor.set(paramColor);
    this.upperFlapTopColor.set(paramColor.darker());
    this.upperFlapBottomColor.set(paramColor);
    this.color.set(paramColor);
  }
  
  public final ObjectProperty<Color> colorProperty()
  {
    return this.color;
  }
  
  public final Color getUpperFlapTopColor()
  {
    return (Color)this.upperFlapTopColor.get();
  }
  
  public final void setUpperFlapTopColor(Color paramColor)
  {
    this.upperFlapTopColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> upperFlapTopColorProperty()
  {
    return this.upperFlapTopColor;
  }
  
  public final Color getUpperFlapBottomColor()
  {
    return (Color)this.upperFlapBottomColor.get();
  }
  
  public final void setUpperFlapBottomColor(Color paramColor)
  {
    this.upperFlapBottomColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> upperFlapBottomColorProperty()
  {
    return this.upperFlapBottomColor;
  }
  
  public final Color getLowerFlapTopColor()
  {
    return (Color)this.lowerFlapTopColor.get();
  }
  
  public final void setLowerFlapTopColor(Color paramColor)
  {
    this.lowerFlapTopColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> lowerFlapTopColorProperty()
  {
    return this.lowerFlapTopColor;
  }
  
  public final Color getLowerFlapBottomColor()
  {
    return (Color)this.lowerFlapBottomColor.get();
  }
  
  public final void setLowerFlapBottomColor(Color paramColor)
  {
    this.lowerFlapBottomColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> lowerFlapBottomColorProperty()
  {
    return this.lowerFlapBottomColor;
  }
  
  public final Color getTextColor()
  {
    return (Color)this.textColor.get();
  }
  
  public final void setTextColor(Color paramColor)
  {
    this.textUpperFlapColor.set(paramColor.darker());
    this.textLowerFlapColor.set(paramColor.brighter());
    this.textColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> textColorProperty()
  {
    return this.textColor;
  }
  
  public final Color getTextUpperFlapColor()
  {
    return (Color)this.textUpperFlapColor.get();
  }
  
  public final void setTextUpperFlapColor(Color paramColor)
  {
    this.textUpperFlapColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> textUpperFlapColorProperty()
  {
    return this.textUpperFlapColor;
  }
  
  public final Color getTextLowerFlapColor()
  {
    return (Color)this.textLowerFlapColor.get();
  }
  
  public final void setTextLowerFlapColor(Color paramColor)
  {
    this.textLowerFlapColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> textLowerFlapColorProperty()
  {
    return this.textLowerFlapColor;
  }
  
  public final double getFlapCornerRadius()
  {
    return this.flapCornerRadius.get();
  }
  
  public final void setFlapCornerRadius(double paramDouble)
  {
    double d = paramDouble > 20.0D ? 20.0D : paramDouble < 0.0D ? 0.0D : paramDouble;
    this.flapCornerRadius.set(d);
  }
  
  public final DoubleProperty flapCornerRadiusProperty()
  {
    return this.flapCornerRadius;
  }
  
  public final boolean isUpperFlapHighlightEnabled()
  {
    return this.upperFlapHighlightEnabled.get();
  }
  
  public final void setUpperFlapHighlightEnabled(boolean paramBoolean)
  {
    this.upperFlapHighlightEnabled.set(paramBoolean);
  }
  
  public final BooleanProperty upperFlapHighlightEnabledProperty()
  {
    return this.upperFlapHighlightEnabled;
  }
  
  public final boolean isLowerFlapHighlightEnabled()
  {
    return this.lowerFlapeHighlightEnabled.get();
  }
  
  public final void setLowerFlapHighlightEnabled(boolean paramBoolean)
  {
    this.lowerFlapeHighlightEnabled.set(paramBoolean);
  }
  
  public final BooleanProperty lowerFlapHighlightEnabledProperty()
  {
    return this.lowerFlapeHighlightEnabled;
  }
  
  public final boolean isDarkFixtureEnabled()
  {
    return this.darkFixtureEnabled.get();
  }
  
  public final void setDarkFixtureEnabled(boolean paramBoolean)
  {
    this.darkFixtureEnabled.set(paramBoolean);
  }
  
  public final BooleanProperty darkFixtureEnabledProperty()
  {
    return this.darkFixtureEnabled;
  }
  
  public final String[] getSelection()
  {
    return (String[])this.selection.get();
  }
  
  public final void setSelection(String[] paramArrayOfString)
  {
    this.selectedSet.clear();
    if (paramArrayOfString.length == 0) {
      this.selectedSet.addAll(Arrays.asList(EXTENDED));
    } else {
      this.selectedSet.addAll(Arrays.asList(paramArrayOfString));
    }
    this.selection.set(paramArrayOfString);
  }
  
  public final ObjectProperty<String[]> selectionProperty()
  {
    return this.selection;
  }
  
  public final ArrayList<String> getSelectedSet()
  {
    return this.selectedSet;
  }
  
  public final boolean isImageMode()
  {
    return this.imageMode.get();
  }
  
  public final void setImageMode(boolean paramBoolean)
  {
    this.imageMode.set(paramBoolean);
  }
  
  public final BooleanProperty imageModeProperty()
  {
    return this.imageMode;
  }
  
  public final boolean isInteractive()
  {
    return this.interactive.get();
  }
  
  public final void setInteractive(boolean paramBoolean)
  {
    this.interactive.set(paramBoolean);
  }
  
  public final BooleanProperty interactiveProperty()
  {
    return this.interactive;
  }
  
  public final String getText()
  {
    return (String)this.text.get();
  }
  
  public final void setText(String paramString)
  {
    if ((!paramString.isEmpty()) || (this.selectedSet.contains(paramString))) {
      this.text.set(paramString);
    } else {
      this.text.set(this.selectedSet.get(0));
    }
  }
  
  public final StringProperty textProperty()
  {
    return this.text;
  }
  
  public final String getNextText()
  {
    return (String)this.selectedSet.get(this.nextSelectionIndex);
  }
  
  public final String getPreviousText()
  {
    return (String)this.selectedSet.get(this.previousSelectionIndex);
  }
  
  public final long getFlipTimeInMs()
  {
    return this.flipTimeInMs.get();
  }
  
  public final void setFlipTimeInMs(long paramLong)
  {
    this.flipTimeInMs.set(paramLong > 3000L ? 3000L : paramLong < 16L ? 16L : paramLong);
  }
  
  public final LongProperty flipTimeInMsProperty()
  {
    return this.flipTimeInMs;
  }
  
  public final boolean isCountdownMode()
  {
    return this.countdownMode.get();
  }
  
  public final void setCountdownMode(boolean paramBoolean)
  {
    this.countdownMode.set(paramBoolean);
  }
  
  public final BooleanProperty countdownModeProperty()
  {
    return this.countdownMode;
  }
  
  public final boolean isSoundOn()
  {
    return this.soundOn.get();
  }
  
  public final void setSoundOn(boolean paramBoolean)
  {
    this.soundOn.set(paramBoolean);
  }
  
  public final BooleanProperty soundOnProperty()
  {
    return this.soundOn;
  }
  
  public final Sound getSound()
  {
    return (Sound)this.sound.get();
  }
  
  public final void setSound(Sound paramSound)
  {
    this.sound.set(paramSound);
  }
  
  public final ObjectProperty<Sound> soundProperty()
  {
    return this.sound;
  }
  
  public final boolean isBackgroundVisible()
  {
    return this.backgroundVisible.get();
  }
  
  public final void setBackgroundVisible(boolean paramBoolean)
  {
    this.backgroundVisible.set(paramBoolean);
  }
  
  public final BooleanProperty backgroundVisibleProperty()
  {
    return this.backgroundVisible;
  }
  
  public final boolean isFrameVisible()
  {
    return this.frameVisible.get();
  }
  
  public final void setFrameVisible(boolean paramBoolean)
  {
    this.frameVisible.set(paramBoolean);
  }
  
  public final BooleanProperty frameVisibleProperty()
  {
    return this.frameVisible;
  }
  
  public final Color getFrameTopColor()
  {
    return (Color)this.frameTopColor.get();
  }
  
  public final void setFrameTopColor(Color paramColor)
  {
    this.frameTopColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> frameTopColorProperty()
  {
    return this.frameTopColor;
  }
  
  public final Color getFrameBottomColor()
  {
    return (Color)this.frameBottomColor.get();
  }
  
  public final void setFrameBottomColor(Color paramColor)
  {
    this.frameBottomColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> frameBottomColorProperty()
  {
    return this.frameBottomColor;
  }
  
  public final void flipForward()
  {
    setCountdownMode(false);
    this.previousSelectionIndex = this.currentSelectionIndex;
    this.currentSelectionIndex += 1;
    if (this.currentSelectionIndex >= this.selectedSet.size()) {
      this.currentSelectionIndex = 0;
    }
    this.nextSelectionIndex = (this.currentSelectionIndex + 1);
    if (this.nextSelectionIndex >= this.selectedSet.size()) {
      this.nextSelectionIndex = 0;
    }
    setText((String)this.selectedSet.get(this.currentSelectionIndex));
  }
  
  public final void flipBackward()
  {
    setCountdownMode(true);
    this.previousSelectionIndex = this.currentSelectionIndex;
    this.currentSelectionIndex -= 1;
    if (this.currentSelectionIndex < 0) {
      this.currentSelectionIndex = (this.selectedSet.size() - 1);
    }
    this.nextSelectionIndex = (this.currentSelectionIndex - 1);
    if (this.nextSelectionIndex < 0) {
      this.nextSelectionIndex = (this.selectedSet.size() - 1);
    }
    setText((String)this.selectedSet.get(this.currentSelectionIndex));
  }
  
  public final boolean isKeepAspect()
  {
    return this.keepAspect;
  }
  
  public final void setKeepAspect(boolean paramBoolean)
  {
    this.keepAspect = paramBoolean;
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1 < paramDouble2 * 0.5814977974D ? paramDouble1 * 1.7196969697D : paramDouble2;
    double d2 = d1 * 0.5814977974D;
    if (this.keepAspect) {
      super.setPrefSize(d2, d1);
    } else {
      super.setPrefSize(paramDouble1, paramDouble2);
    }
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
  
  public static enum Sound
  {
    SOUND1,  SOUND2,  SOUND3;
    
    private Sound() {}
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/SplitFlap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */