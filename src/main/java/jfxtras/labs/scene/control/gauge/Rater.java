package jfxtras.labs.scene.control.gauge;

import java.net.URL;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

public class Rater
  extends Control
{
  private static final String DEFAULT_STYLE_CLASS = "rater";
  private ObjectProperty<Color> brightColor;
  private ObjectProperty<Color> darkColor;
  private IntegerProperty noOfStars;
  private IntegerProperty rating;
  
  public Rater()
  {
    this(Color.rgb(255, 231, 81), Color.rgb(255, 126, 18));
  }
  
  public Rater(Color paramColor1, Color paramColor2)
  {
    this.brightColor = new SimpleObjectProperty(paramColor1);
    this.darkColor = new SimpleObjectProperty(paramColor2);
    this.noOfStars = new SimpleIntegerProperty(5);
    this.rating = new SimpleIntegerProperty(0);
    getStyleClass().add("rater");
  }
  
  public final Color getDarkColor()
  {
    return (Color)this.darkColor.get();
  }
  
  public final void setDarkColor(Color paramColor)
  {
    this.darkColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> darkColorProperty()
  {
    return this.darkColor;
  }
  
  public final Color getBrightColor()
  {
    return (Color)this.brightColor.get();
  }
  
  public final void setBrightColor(Color paramColor)
  {
    this.brightColor.set(paramColor);
  }
  
  public final ObjectProperty<Color> brightColorProperty()
  {
    return this.brightColor;
  }
  
  public final int getNoOfStars()
  {
    return this.noOfStars.get();
  }
  
  public final void setNoOfStars(int paramInt)
  {
    this.noOfStars.set(paramInt > 10 ? 10 : paramInt < 1 ? 1 : paramInt);
  }
  
  public final IntegerProperty noOfStarsProperty()
  {
    return this.noOfStars;
  }
  
  public final int getRating()
  {
    return this.rating.get();
  }
  
  public final void setRating(int paramInt)
  {
    this.rating.set(paramInt > this.noOfStars.get() ? this.noOfStars.get() : paramInt < 0 ? 0 : paramInt);
  }
  
  public final IntegerProperty ratingProperty()
  {
    return this.rating;
  }
  
  public void setPrefSize(double paramDouble1, double paramDouble2)
  {
    double d = paramDouble1 < paramDouble2 ? paramDouble1 : paramDouble2;
    super.setPrefSize(this.noOfStars.get() * d, d);
  }
  
  protected String getUserAgentStylesheet()
  {
    return getClass().getResource("extras.css").toExternalForm();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/Rater.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */