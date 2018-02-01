package jfxtras.labs.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javafx.animation.Interpolator;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Shape;

public class ConicalGradient
{
  private Point2D center;
  private List<Stop> sortedStops;
  
  public ConicalGradient(Stop... paramVarArgs)
  {
    this(null, Arrays.asList(paramVarArgs));
  }
  
  public ConicalGradient(List<Stop> paramList)
  {
    this(null, paramList);
  }
  
  public ConicalGradient(Point2D paramPoint2D, Stop... paramVarArgs)
  {
    this(paramPoint2D, 0.0D, Arrays.asList(paramVarArgs));
  }
  
  public ConicalGradient(Point2D paramPoint2D, List<Stop> paramList)
  {
    this(paramPoint2D, 0.0D, paramList);
  }
  
  public ConicalGradient(Point2D paramPoint2D, double paramDouble, Stop... paramVarArgs)
  {
    this(paramPoint2D, paramDouble, Arrays.asList(paramVarArgs));
  }
  
  public ConicalGradient(Point2D paramPoint2D, double paramDouble, List<Stop> paramList)
  {
    double d = Util.clamp(0.0D, 1.0D, paramDouble);
    this.center = paramPoint2D;
    Object localObject1;
    if ((paramList == null) || (paramList.isEmpty()))
    {
      localObject1 = new ArrayList();
      ((List)localObject1).add(new Stop(0.0D, Color.TRANSPARENT));
      ((List)localObject1).add(new Stop(1.0D, Color.TRANSPARENT));
    }
    else
    {
      localObject1 = paramList;
    }
    LinkedHashMap localLinkedHashMap = new LinkedHashMap(((List)localObject1).size());
    Object localObject2 = ((List)localObject1).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Stop)((Iterator)localObject2).next();
      localLinkedHashMap.put(Double.valueOf(((Stop)localObject3).getOffset()), ((Stop)localObject3).getColor());
    }
    this.sortedStops = new LinkedList();
    localObject2 = new TreeSet(localLinkedHashMap.keySet());
    if (((Double)((SortedSet)localObject2).last()).doubleValue() < 1.0D)
    {
      localLinkedHashMap.put(Double.valueOf(1.0D), localLinkedHashMap.get(((SortedSet)localObject2).first()));
      ((SortedSet)localObject2).add(Double.valueOf(1.0D));
    }
    if (((Double)((SortedSet)localObject2).first()).doubleValue() > 0.0D)
    {
      localLinkedHashMap.put(Double.valueOf(0.0D), localLinkedHashMap.get(((SortedSet)localObject2).last()));
      ((SortedSet)localObject2).add(Double.valueOf(0.0D));
    }
    Object localObject3 = ((SortedSet)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      Double localDouble = (Double)((Iterator)localObject3).next();
      this.sortedStops.add(new Stop(localDouble.doubleValue(), (Color)localLinkedHashMap.get(localDouble)));
    }
    if (d > 0.0D) {
      recalculate(d);
    }
  }
  
  private void recalculate(double paramDouble)
  {
    paramDouble = Util.clamp(0.0D, 1.0D, paramDouble);
    ArrayList localArrayList = new ArrayList(this.sortedStops.size());
    Object localObject1 = null;
    Object localObject2 = this.sortedStops.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Stop)((Iterator)localObject2).next();
      double d = ((Stop)localObject3).getOffset() + paramDouble;
      if (d > 1.0D)
      {
        d -= 1.000001D;
        if (localObject1 != null)
        {
          localArrayList.add(new Stop(0.0D, (Color)Interpolator.LINEAR.interpolate(((Stop)localObject1).getColor(), ((Stop)localObject3).getColor(), 1.0D - paramDouble)));
          localArrayList.add(new Stop(1.0D, (Color)Interpolator.LINEAR.interpolate(((Stop)localObject1).getColor(), ((Stop)localObject3).getColor(), 1.0D - paramDouble)));
        }
      }
      else
      {
        localArrayList.add(new Stop(d, ((Stop)localObject3).getColor()));
        localObject1 = localObject3;
      }
    }
    localObject2 = new LinkedHashMap(localArrayList.size());
    Object localObject3 = localArrayList.iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject4 = (Stop)((Iterator)localObject3).next();
      ((HashMap)localObject2).put(Double.valueOf(((Stop)localObject4).getOffset()), ((Stop)localObject4).getColor());
    }
    localObject3 = new LinkedList();
    Object localObject4 = new TreeSet(((HashMap)localObject2).keySet());
    if (((Double)((SortedSet)localObject4).last()).doubleValue() < 1.0D)
    {
      ((HashMap)localObject2).put(Double.valueOf(1.0D), ((HashMap)localObject2).get(((SortedSet)localObject4).first()));
      ((SortedSet)localObject4).add(Double.valueOf(1.0D));
    }
    if (((Double)((SortedSet)localObject4).first()).doubleValue() > 0.0D)
    {
      ((HashMap)localObject2).put(Double.valueOf(0.0D), ((HashMap)localObject2).get(((SortedSet)localObject4).last()));
      ((SortedSet)localObject4).add(Double.valueOf(0.0D));
    }
    Iterator localIterator = ((SortedSet)localObject4).iterator();
    while (localIterator.hasNext())
    {
      Double localDouble = (Double)localIterator.next();
      ((List)localObject3).add(new Stop(localDouble.doubleValue(), (Color)((HashMap)localObject2).get(localDouble)));
    }
    this.sortedStops.clear();
    this.sortedStops.addAll((Collection)localObject3);
  }
  
  public List<Stop> getStops()
  {
    return this.sortedStops;
  }
  
  public Point2D getCenter()
  {
    return this.center;
  }
  
  public Image getImage(double paramDouble1, double paramDouble2)
  {
    int i = (int)paramDouble1 <= 0 ? 100 : (int)paramDouble1;
    int j = (int)paramDouble2 <= 0 ? 100 : (int)paramDouble2;
    WritableImage localWritableImage = new WritableImage(i, j);
    PixelWriter localPixelWriter = localWritableImage.getPixelWriter();
    Color localColor = Color.TRANSPARENT;
    if (this.center == null) {
      this.center = new Point2D(i / 2, j / 2);
    }
    for (int k = 0; k < j; k++) {
      for (int m = 0; m < i; m++)
      {
        double d1 = m - this.center.getX();
        double d2 = k - this.center.getY();
        double d3 = Math.sqrt(d1 * d1 + d2 * d2);
        d3 = Double.compare(d3, 0.0D) == 0 ? 1.0D : d3;
        double d4 = Math.abs(Math.toDegrees(Math.acos(d1 / d3)));
        if ((d1 >= 0.0D) && (d2 <= 0.0D)) {
          d4 = 90.0D - d4;
        } else if ((d1 >= 0.0D) && (d2 >= 0.0D)) {
          d4 += 90.0D;
        } else if ((d1 <= 0.0D) && (d2 >= 0.0D)) {
          d4 += 90.0D;
        } else if ((d1 <= 0.0D) && (d2 <= 0.0D)) {
          d4 = 450.0D - d4;
        }
        for (int n = 0; n < this.sortedStops.size() - 1; n++) {
          if ((d4 >= ((Stop)this.sortedStops.get(n)).getOffset() * 360.0D) && (d4 < ((Stop)this.sortedStops.get(n + 1)).getOffset() * 360.0D))
          {
            double d5 = (d4 - ((Stop)this.sortedStops.get(n)).getOffset() * 360.0D) / ((((Stop)this.sortedStops.get(n + 1)).getOffset() - ((Stop)this.sortedStops.get(n)).getOffset()) * 360.0D);
            localColor = (Color)Interpolator.LINEAR.interpolate(((Stop)this.sortedStops.get(n)).getColor(), ((Stop)this.sortedStops.get(n + 1)).getColor(), d5);
          }
        }
        localPixelWriter.setColor(m, k, localColor);
      }
    }
    return localWritableImage;
  }
  
  public ImagePattern apply(Shape paramShape)
  {
    double d1 = paramShape.getLayoutBounds().getMinX();
    double d2 = paramShape.getLayoutBounds().getMinY();
    double d3 = paramShape.getLayoutBounds().getWidth();
    double d4 = paramShape.getLayoutBounds().getHeight();
    this.center = new Point2D(d3 / 2.0D, d3 / 2.0D);
    return new ImagePattern(getImage(d3, d4), d1, d2, d3, d4, false);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/util/ConicalGradient.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */