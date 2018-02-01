package jfxtras.labs.scene.control.gauge;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

public class GradientLookup
{
  private Map<Double, Stop> stops = new TreeMap();
  
  public GradientLookup(Stop... paramVarArgs)
  {
    for (Stop localStop : paramVarArgs) {
      this.stops.put(Double.valueOf(localStop.getOffset()), localStop);
    }
    init();
  }
  
  private void init()
  {
    double d1 = 1.0D;
    double d2 = 0.0D;
    Iterator localIterator = this.stops.keySet().iterator();
    while (localIterator.hasNext())
    {
      Double localDouble = (Double)localIterator.next();
      d1 = Math.min(localDouble.doubleValue(), d1);
      d2 = Math.max(localDouble.doubleValue(), d2);
    }
    if (d1 > 0.0D) {
      this.stops.put(Double.valueOf(0.0D), new Stop(0.0D, ((Stop)this.stops.get(Double.valueOf(d1))).getColor()));
    }
    if (d2 < 1.0D) {
      this.stops.put(Double.valueOf(1.0D), new Stop(1.0D, ((Stop)this.stops.get(Double.valueOf(d2))).getColor()));
    }
  }
  
  public Color getColorAt(double paramDouble)
  {
    double d = paramDouble > 1.0D ? 1.0D : paramDouble < 0.0D ? 0.0D : paramDouble;
    Object localObject;
    Color localColor;
    if (this.stops.size() == 1)
    {
      localObject = (Map)this.stops.entrySet().iterator().next();
      localColor = ((Stop)this.stops.get(((Map)localObject).keySet().iterator().next())).getColor();
    }
    else
    {
      localObject = (Stop)this.stops.get(Double.valueOf(0.0D));
      Stop localStop = (Stop)this.stops.get(Double.valueOf(1.0D));
      Iterator localIterator = this.stops.keySet().iterator();
      while (localIterator.hasNext())
      {
        Double localDouble = (Double)localIterator.next();
        if (Double.compare(localDouble.doubleValue(), d) < 0) {
          localObject = (Stop)this.stops.get(localDouble);
        }
        if (Double.compare(localDouble.doubleValue(), d) > 0)
        {
          localStop = (Stop)this.stops.get(localDouble);
          break;
        }
      }
      localColor = interpolateColor((Stop)localObject, localStop, d);
    }
    return localColor;
  }
  
  private Color interpolateColor(Stop paramStop1, Stop paramStop2, double paramDouble)
  {
    double d1 = (paramDouble - paramStop1.getOffset()) / (paramStop2.getOffset() - paramStop1.getOffset());
    double d2 = (paramStop2.getColor().getRed() - paramStop1.getColor().getRed()) * d1;
    double d3 = (paramStop2.getColor().getGreen() - paramStop1.getColor().getGreen()) * d1;
    double d4 = (paramStop2.getColor().getBlue() - paramStop1.getColor().getBlue()) * d1;
    double d5 = (paramStop2.getColor().getOpacity() - paramStop1.getColor().getOpacity()) * d1;
    double d6 = paramStop1.getColor().getRed() + d2;
    double d7 = paramStop1.getColor().getGreen() + d3;
    double d8 = paramStop1.getColor().getBlue() + d4;
    double d9 = paramStop1.getColor().getOpacity() + d5;
    d6 = d6 > 1.0D ? 1.0D : d6 < 0.0D ? 0.0D : d6;
    d7 = d7 > 1.0D ? 1.0D : d7 < 0.0D ? 0.0D : d7;
    d8 = d8 > 1.0D ? 1.0D : d8 < 0.0D ? 0.0D : d8;
    d9 = d9 > 1.0D ? 1.0D : d9 < 0.0D ? 0.0D : d9;
    return Color.color(d6, d7, d8, d9);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/GradientLookup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */