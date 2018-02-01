package jfxtras.labs.util;

import java.util.ArrayList;
import java.util.Arrays;
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

public class EllipticalGradient
{
  private List<Stop> sortedStops;
  
  public EllipticalGradient(Stop... paramVarArgs)
  {
    this(Arrays.asList(paramVarArgs));
  }
  
  public EllipticalGradient(List<Stop> paramList)
  {
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
      localLinkedHashMap.put(Double.valueOf(1.0D), localLinkedHashMap.get(((SortedSet)localObject2).last()));
      ((SortedSet)localObject2).add(Double.valueOf(1.0D));
    }
    if (((Double)((SortedSet)localObject2).first()).doubleValue() > 0.0D)
    {
      localLinkedHashMap.put(Double.valueOf(0.0D), localLinkedHashMap.get(((SortedSet)localObject2).first()));
      ((SortedSet)localObject2).add(Double.valueOf(0.0D));
    }
    Object localObject3 = ((SortedSet)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      Double localDouble = (Double)((Iterator)localObject3).next();
      this.sortedStops.add(new Stop(localDouble.doubleValue(), (Color)localLinkedHashMap.get(localDouble)));
    }
  }
  
  public List<Stop> getStops()
  {
    return this.sortedStops;
  }
  
  public Image getImage(double paramDouble1, double paramDouble2)
  {
    return getImage(paramDouble1, paramDouble2, new Point2D(paramDouble1 / 2.0D, paramDouble2 / 2.0D));
  }
  
  public Image getImage(double paramDouble1, double paramDouble2, Point2D paramPoint2D)
  {
    int i = (int)paramDouble1 <= 0 ? 100 : (int)paramDouble1;
    int j = (int)paramDouble2 <= 0 ? 50 : (int)paramDouble2;
    double d1 = paramDouble1 / 2.0D;
    double d2 = d1 * d1;
    double d3 = paramDouble2 / 2.0D;
    double d4 = d3 * d3;
    Color localColor = Color.TRANSPARENT;
    WritableImage localWritableImage = new WritableImage(i, j);
    PixelWriter localPixelWriter = localWritableImage.getPixelWriter();
    for (int k = 0; k < j; k++) {
      for (int m = 0; m < i; m++)
      {
        double d5 = (m - paramPoint2D.getX()) * (m - paramPoint2D.getX()) / d2 + (k - paramPoint2D.getY()) * (k - paramPoint2D.getY()) / d4;
        d5 = d5 > 1.0D ? 1.0D : d5;
        for (int n = 0; n < this.sortedStops.size() - 1; n++)
        {
          double d7 = ((Stop)this.sortedStops.get(n)).getOffset();
          double d8 = ((Stop)this.sortedStops.get(n + 1)).getOffset();
          if ((Double.compare(d5, d7) > 0) && (Double.compare(d5, d8) <= 0))
          {
            double d6 = (d5 - d7) / (d8 - d7);
            localColor = (Color)Interpolator.LINEAR.interpolate(((Stop)this.sortedStops.get(n)).getColor(), ((Stop)this.sortedStops.get(n + 1)).getColor(), d6);
          }
        }
        localPixelWriter.setColor(m, k, localColor);
      }
    }
    return localWritableImage;
  }
  
  public ImagePattern getFill(Shape paramShape)
  {
    return getFill(paramShape, new Point2D(paramShape.getLayoutBounds().getWidth() / 2.0D, paramShape.getLayoutBounds().getHeight() / 2.0D));
  }
  
  public ImagePattern getFill(Shape paramShape, Point2D paramPoint2D)
  {
    double d1 = paramShape.getLayoutBounds().getMinX();
    double d2 = paramShape.getLayoutBounds().getMinY();
    double d3 = paramShape.getLayoutBounds().getWidth();
    double d4 = paramShape.getLayoutBounds().getHeight();
    return new ImagePattern(getImage(d3, d4, paramPoint2D), d1, d2, d3, d4, false);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/util/EllipticalGradient.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */