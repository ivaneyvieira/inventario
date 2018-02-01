package jfxtras.labs.util;

import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;

public class BrushedMetalPaint
{
  private int radius;
  private double amount;
  private int color = getIntFromColor(paramColor);
  private double shine;
  private boolean monochrome;
  private Random randomNumbers;
  
  public BrushedMetalPaint()
  {
    this(Color.rgb(136, 136, 136), 5, 0.1D, true, 0.3D);
  }
  
  public BrushedMetalPaint(Color paramColor)
  {
    this(paramColor, 5, 0.1D, true, 0.3D);
  }
  
  public BrushedMetalPaint(Color paramColor, int paramInt, double paramDouble1, boolean paramBoolean, double paramDouble2)
  {
    this.radius = paramInt;
    this.amount = paramDouble1;
    this.monochrome = paramBoolean;
    this.shine = paramDouble2;
  }
  
  public Image getImage(double paramDouble1, double paramDouble2)
  {
    int i = (int)paramDouble1;
    int j = (int)paramDouble2;
    WritableImage localWritableImage = new WritableImage(i, j);
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = new int[i];
    this.randomNumbers = new Random(0L);
    int k = this.color & 0xFF000000;
    int m = this.color >> 16 & 0xFF;
    int n = this.color >> 8 & 0xFF;
    int i1 = this.color & 0xFF;
    for (int i2 = 0; i2 < j; i2++)
    {
      for (int i3 = 0; i3 < i; i3++)
      {
        int i4 = m;
        int i5 = n;
        int i6 = i1;
        int i7;
        if (this.shine != 0.0D)
        {
          i7 = (int)(255.0D * this.shine * Math.sin(i3 / i * 3.141592653589793D));
          i4 += i7;
          i5 += i7;
          i6 += i7;
        }
        if (this.monochrome)
        {
          i7 = (int)(255.0F * (2.0F * this.randomNumbers.nextFloat() - 1.0F) * this.amount);
          arrayOfInt1[i3] = (k | clamp(i4 + i7) << 16 | clamp(i5 + i7) << 8 | clamp(i6 + i7));
        }
        else
        {
          arrayOfInt1[i3] = (k | random(i4) << 16 | random(i5) << 8 | random(i6));
        }
      }
      if (this.radius != 0)
      {
        blur(arrayOfInt1, arrayOfInt2, i, this.radius);
        setRGB(localWritableImage, 0, i2, arrayOfInt2);
      }
      else
      {
        setRGB(localWritableImage, 0, i2, arrayOfInt1);
      }
    }
    return localWritableImage;
  }
  
  public ImageView getImageView(double paramDouble1, double paramDouble2, Shape paramShape)
  {
    Image localImage = getImage(paramDouble1, paramDouble2);
    ImageView localImageView = new ImageView(localImage);
    localImageView.setClip(paramShape);
    return localImageView;
  }
  
  public ImagePattern apply(Shape paramShape)
  {
    double d1 = paramShape.getLayoutBounds().getMinX();
    double d2 = paramShape.getLayoutBounds().getMinY();
    double d3 = paramShape.getLayoutBounds().getWidth();
    double d4 = paramShape.getLayoutBounds().getHeight();
    return new ImagePattern(getImage(d3, d4), d1, d2, d3, d4, false);
  }
  
  public void blur(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2)
  {
    int i = paramInt1 - 1;
    int j = 2 * paramInt2 + 1;
    int k = 0;
    int m = 0;
    int n = 0;
    int i2;
    for (int i1 = -paramInt2; i1 <= paramInt2; i1++)
    {
      i2 = paramArrayOfInt1[mod(i1, paramInt1)];
      k += (i2 >> 16 & 0xFF);
      m += (i2 >> 8 & 0xFF);
      n += (i2 & 0xFF);
    }
    for (i1 = 0; i1 < paramInt1; i1++)
    {
      paramArrayOfInt2[i1] = (0xFF000000 | k / j << 16 | m / j << 8 | n / j);
      i2 = i1 + paramInt2 + 1;
      if (i2 > i) {
        i2 = mod(i2, paramInt1);
      }
      int i3 = i1 - paramInt2;
      if (i3 < 0) {
        i3 = mod(i3, paramInt1);
      }
      int i4 = paramArrayOfInt1[i2];
      int i5 = paramArrayOfInt1[i3];
      k += ((i4 & 0xFF0000) - (i5 & 0xFF0000) >> 16);
      m += ((i4 & 0xFF00) - (i5 & 0xFF00) >> 8);
      n += (i4 & 0xFF) - (i5 & 0xFF);
    }
  }
  
  public void setRadius(int paramInt)
  {
    this.radius = paramInt;
  }
  
  public int getRadius()
  {
    return this.radius;
  }
  
  public void setAmount(double paramDouble)
  {
    this.amount = paramDouble;
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public void setColor(int paramInt)
  {
    this.color = paramInt;
  }
  
  public int getColor()
  {
    return this.color;
  }
  
  public void setMonochrome(boolean paramBoolean)
  {
    this.monochrome = paramBoolean;
  }
  
  public boolean isMonochrome()
  {
    return this.monochrome;
  }
  
  public void setShine(double paramDouble)
  {
    this.shine = paramDouble;
  }
  
  public double getShine()
  {
    return this.shine;
  }
  
  private int random(int paramInt)
  {
    paramInt += (int)(255.0F * (2.0F * this.randomNumbers.nextFloat() - 1.0F) * this.amount);
    if (paramInt < 0) {
      paramInt = 0;
    } else if (paramInt > 255) {
      paramInt = 255;
    }
    return paramInt;
  }
  
  private int clamp(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 0) {
      i = 0;
    }
    if (paramInt > 255) {
      i = 255;
    }
    return i;
  }
  
  private int mod(int paramInt1, int paramInt2)
  {
    int i = paramInt1 / paramInt2;
    paramInt1 -= i * paramInt2;
    if (paramInt1 < 0) {
      return paramInt1 + paramInt2;
    }
    return paramInt1;
  }
  
  private void setRGB(WritableImage paramWritableImage, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    PixelWriter localPixelWriter = paramWritableImage.getPixelWriter();
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      localPixelWriter.setColor(paramInt1 + i, paramInt2, Color.rgb(paramArrayOfInt[i] >> 16 & 0xFF, paramArrayOfInt[i] >> 8 & 0xFF, paramArrayOfInt[i] & 0xFF));
    }
  }
  
  private int getIntFromColor(Color paramColor)
  {
    String str = paramColor.toString();
    StringBuilder localStringBuilder = new StringBuilder(10);
    localStringBuilder.append(str.substring(8, 10));
    localStringBuilder.append(str.substring(2, 8));
    return (int)Long.parseLong(localStringBuilder.toString(), 16);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/util/BrushedMetalPaint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */