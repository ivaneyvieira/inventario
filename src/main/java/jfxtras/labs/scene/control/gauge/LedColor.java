package jfxtras.labs.scene.control.gauge;

import javafx.scene.paint.Color;

public enum LedColor
{
  RED("-fx-red;", Color.rgb(213, 0, 0)),  GREEN("-fx-green;", Color.rgb(0, 148, 0)),  BLUE("-fx-blue;", Color.rgb(0, 120, 220)),  ORANGE("-fx-orange;", Color.rgb(248, 142, 0)),  YELLOW("-fx-yellow;", Color.rgb(210, 204, 0)),  CYAN("-fx-cyan;", Color.rgb(0, 159, 215)),  MAGENTA("-fx-magenta;", Color.rgb(223, 42, 125)),  LILA("-fx-lila;", Color.rgb(71, 0, 255)),  WHITE("-fx-white;", Color.rgb(245, 245, 245)),  GRAY("-fx-gray;", Color.rgb(102, 102, 102)),  BLACK("-fx-black;", Color.rgb(15, 15, 15)),  RAITH("-fx-raith;", Color.rgb(65, 143, 193)),  GREEN_LCD("-fx-green-lcd;", Color.rgb(24, 220, 183)),  JUG_GREEN("-fx-jug-green;", Color.rgb(90, 183, 0)),  CUSTOM("-fx-custom;", Color.rgb(0, 195, 97));
  
  public final String CSS;
  public final Color GLOW_COLOR;
  
  private LedColor(String paramString, Color paramColor)
  {
    this.CSS = ("-fx-led: " + paramString);
    this.GLOW_COLOR = paramColor;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/LedColor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */