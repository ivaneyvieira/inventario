package jfxtras.labs.util;

import javafx.animation.Interpolator;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

public class BiLinearGradient
{
  private final Color COLOR_00;
  private final Color COLOR_10;
  private final Color COLOR_01;
  private final Color COLOR_11;
  
  public BiLinearGradient(Color paramColor1, Color paramColor2, Color paramColor3, Color paramColor4)
  {
    this.COLOR_00 = paramColor1;
    this.COLOR_10 = paramColor2;
    this.COLOR_01 = paramColor3;
    this.COLOR_11 = paramColor4;
  }
  
  private Color biLinearInterpolateColor(Color paramColor1, Color paramColor2, Color paramColor3, Color paramColor4, double paramDouble1, double paramDouble2)
  {
    Color localColor1 = (Color)Interpolator.LINEAR.interpolate(paramColor1, paramColor2, paramDouble1);
    Color localColor2 = (Color)Interpolator.LINEAR.interpolate(paramColor3, paramColor4, paramDouble1);
    return (Color)Interpolator.LINEAR.interpolate(localColor1, localColor2, paramDouble2);
  }
  
  public Image getImage(double paramDouble1, double paramDouble2)
  {
    int i = (int)paramDouble1 <= 0 ? 100 : (int)paramDouble1;
    int j = (int)paramDouble2 <= 0 ? 100 : (int)paramDouble2;
    WritableImage localWritableImage = new WritableImage(i, j);
    PixelWriter localPixelWriter = localWritableImage.getPixelWriter();
    double d1 = 1.0D / (paramDouble1 - 1.0D);
    double d2 = 1.0D / (paramDouble2 - 1.0D);
    double d3 = 0.0D;
    double d4 = 0.0D;
    for (int k = 0; k < paramDouble2; k++)
    {
      for (int m = 0; m < paramDouble1; m++)
      {
        localPixelWriter.setColor(m, k, biLinearInterpolateColor(this.COLOR_00, this.COLOR_10, this.COLOR_01, this.COLOR_11, d3, d4));
        d3 += d1;
        d3 = d3 > 1.0D ? 1.0D : d3;
      }
      d4 += d2;
      d4 = d4 > 1.0D ? 1.0D : d4;
      d3 = 0.0D;
    }
    return localWritableImage;
  }
  
  public ImagePattern apply(Shape paramShape)
  {
    double d1 = paramShape.getLayoutBounds().getMinX();
    double d2 = paramShape.getLayoutBounds().getMinY();
    double d3 = paramShape.getLayoutBounds().getWidth();
    double d4 = paramShape.getLayoutBounds().getHeight();
    return new ImagePattern(getImage(d3, d4), d1, d2, d3, d4, false);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/util/BiLinearGradient.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */