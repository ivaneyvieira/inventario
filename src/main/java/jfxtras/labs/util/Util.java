package jfxtras.labs.util;

import java.util.Random;
import javafx.animation.Interpolator;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotParametersBuilder;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import jfxtras.labs.scene.control.gauge.GradientLookup;

public class Util
{
  private static final SnapshotParameters SNAPSHOT_PARAMETER = SnapshotParametersBuilder.create().fill(Color.TRANSPARENT).build();
  
  public static String createCssColor(Color paramColor)
  {
    StringBuilder localStringBuilder = new StringBuilder(19);
    localStringBuilder.append("rgba(");
    localStringBuilder.append((int)(paramColor.getRed() * 255.0D));
    localStringBuilder.append(", ");
    localStringBuilder.append((int)(paramColor.getGreen() * 255.0D));
    localStringBuilder.append(", ");
    localStringBuilder.append((int)(paramColor.getBlue() * 255.0D));
    localStringBuilder.append(", ");
    localStringBuilder.append(paramColor.getOpacity());
    localStringBuilder.append(");");
    return localStringBuilder.toString();
  }
  
  public static String createWebColor(Color paramColor)
  {
    String str1 = Integer.toHexString((int)(paramColor.getRed() * 255.0D));
    if (str1.length() == 1) {
      str1 = "0" + str1;
    }
    String str2 = Integer.toHexString((int)(paramColor.getGreen() * 255.0D));
    if (str2.length() == 1) {
      str2 = "0" + str2;
    }
    String str3 = Integer.toHexString((int)(paramColor.getBlue() * 255.0D));
    if (str3.length() == 1) {
      str3 = "0" + str3;
    }
    return "#" + str1 + str2 + str3;
  }
  
  public static Color biLinearInterpolateColor(Color paramColor1, Color paramColor2, Color paramColor3, Color paramColor4, float paramFloat1, float paramFloat2)
  {
    Color localColor1 = (Color)Interpolator.LINEAR.interpolate(paramColor1, paramColor2, paramFloat1);
    Color localColor2 = (Color)Interpolator.LINEAR.interpolate(paramColor3, paramColor4, paramFloat1);
    return (Color)Interpolator.LINEAR.interpolate(localColor1, localColor2, paramFloat2);
  }
  
  public static Color darker(Color paramColor, double paramDouble)
  {
    double d1 = clamp(0.0D, 1.0D, paramColor.getRed() * (1.0D - paramDouble));
    double d2 = clamp(0.0D, 1.0D, paramColor.getGreen() * (1.0D - paramDouble));
    double d3 = clamp(0.0D, 1.0D, paramColor.getBlue() * (1.0D - paramDouble));
    return new Color(d1, d2, d3, paramColor.getOpacity());
  }
  
  public static Color brighter(Color paramColor, double paramDouble)
  {
    double d1 = clamp(0.0D, 1.0D, paramColor.getRed() * (1.0D + paramDouble));
    double d2 = clamp(0.0D, 1.0D, paramColor.getGreen() * (1.0D + paramDouble));
    double d3 = clamp(0.0D, 1.0D, paramColor.getBlue() * (1.0D + paramDouble));
    return new Color(d1, d2, d3, paramColor.getOpacity());
  }
  
  public static double clamp(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return paramDouble3 > paramDouble2 ? paramDouble2 : paramDouble3 < paramDouble1 ? paramDouble1 : paramDouble3;
  }
  
  public static double colorDistance(Color paramColor1, Color paramColor2)
  {
    double d1 = paramColor2.getRed() - paramColor1.getRed();
    double d2 = paramColor2.getGreen() - paramColor1.getGreen();
    double d3 = paramColor2.getBlue() - paramColor1.getBlue();
    return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
  }
  
  public static boolean isDark(Color paramColor)
  {
    double d1 = colorDistance(paramColor, Color.WHITE);
    double d2 = colorDistance(paramColor, Color.BLACK);
    return d2 < d1;
  }
  
  public static boolean isBright(Color paramColor)
  {
    return !isDark(paramColor);
  }
  
  public static Image takeSnapshot(Node paramNode)
  {
    WritableImage localWritableImage = new WritableImage((int)paramNode.getLayoutBounds().getWidth(), (int)paramNode.getLayoutBounds().getHeight());
    return paramNode.snapshot(SNAPSHOT_PARAMETER, localWritableImage);
  }
  
  public static Canvas createConicalGradient(Shape paramShape, Stop[] paramArrayOfStop, double paramDouble)
  {
    Canvas localCanvas = new Canvas(paramShape.getLayoutBounds().getWidth(), paramShape.getLayoutBounds().getHeight());
    createConicalGradient(localCanvas, paramShape, paramArrayOfStop, paramDouble);
    return localCanvas;
  }
  
  public static void createConicalGradient(Canvas paramCanvas, Shape paramShape, Stop[] paramArrayOfStop, double paramDouble)
  {
    if (paramCanvas.getLayoutBounds().getWidth() < paramShape.getLayoutBounds().getWidth()) {
      paramCanvas.setWidth(paramShape.getLayoutBounds().getWidth());
    }
    if (paramCanvas.getLayoutBounds().getHeight() < paramShape.getLayoutBounds().getHeight()) {
      paramCanvas.setHeight(paramShape.getLayoutBounds().getHeight());
    }
    Shape localShape = paramShape;
    localShape.setTranslateX(-paramShape.getLayoutBounds().getMinX());
    localShape.setTranslateY(-paramShape.getLayoutBounds().getMinY());
    paramCanvas.setLayoutX(paramShape.getLayoutBounds().getMinX());
    paramCanvas.setLayoutY(paramShape.getLayoutBounds().getMinY());
    paramCanvas.setClip(localShape);
    GraphicsContext localGraphicsContext = paramCanvas.getGraphicsContext2D();
    Bounds localBounds = paramShape.getLayoutBounds();
    Point2D localPoint2D = new Point2D(localBounds.getWidth() / 2.0D, localBounds.getHeight() / 2.0D);
    double d1 = Math.sqrt(localBounds.getWidth() * localBounds.getWidth() + localBounds.getHeight() * localBounds.getHeight()) / 2.0D;
    GradientLookup localGradientLookup = new GradientLookup(paramArrayOfStop);
    localGraphicsContext.translate(localPoint2D.getX(), localPoint2D.getY());
    localGraphicsContext.rotate(-90.0D + paramDouble);
    localGraphicsContext.translate(-localPoint2D.getX(), -localPoint2D.getY());
    int i = 0;
    int j = paramArrayOfStop.length - 1;
    while (i < j)
    {
      for (double d2 = paramArrayOfStop[i].getOffset() * 360.0D; Double.compare(d2, paramArrayOfStop[(i + 1)].getOffset() * 360.0D) <= 0; d2 += 0.1D)
      {
        localGraphicsContext.beginPath();
        localGraphicsContext.moveTo(localPoint2D.getX() - d1, localPoint2D.getY() - d1);
        localGraphicsContext.setFill(localGradientLookup.getColorAt(d2 / 360.0D));
        if (d1 > 0.0D) {
          localGraphicsContext.fillArc(localPoint2D.getX() - d1, localPoint2D.getY() - d1, 2.0D * d1, 2.0D * d1, d2, 0.1D, ArcType.ROUND);
        } else {
          localGraphicsContext.moveTo(localPoint2D.getX() - d1, localPoint2D.getY() - d1);
        }
        localGraphicsContext.fill();
      }
      i++;
    }
  }
  
  public static ImagePattern createCarbonPattern()
  {
    Canvas localCanvas = new Canvas(12.0D, 12.0D);
    GraphicsContext localGraphicsContext = localCanvas.getGraphicsContext2D();
    double d = 0.0D;
    localGraphicsContext.beginPath();
    localGraphicsContext.rect(0.0D, 0.0D, 6.0D, 6.0D);
    localGraphicsContext.closePath();
    localGraphicsContext.setFill(new LinearGradient(0.0D, d * 12.0D, 0.0D, 6.0D + d * 12.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(35, 35, 35)), new Stop(1.0D, Color.rgb(23, 23, 23)) }));
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.rect(0.9999960000000001D, 0.0D, 3.999996D, 4.999992D);
    localGraphicsContext.closePath();
    d = 0.0D;
    localGraphicsContext.setFill(new LinearGradient(0.0D, d * 12.0D, 0.0D, 4.999992D + d * 12.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(38, 38, 38)), new Stop(1.0D, Color.rgb(30, 30, 30)) }));
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.rect(6.0D, 6.0D, 6.0D, 6.0D);
    localGraphicsContext.closePath();
    d = 0.5D;
    localGraphicsContext.setFill(new LinearGradient(0.0D, d * 12.0D, 0.0D, 6.0D + d * 12.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(35, 35, 35)), new Stop(1.0D, Color.rgb(23, 23, 23)) }));
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.rect(6.999995999999999D, 6.0D, 3.999996D, 4.999992D);
    localGraphicsContext.closePath();
    d = 0.5D;
    localGraphicsContext.setFill(new LinearGradient(0.0D, d * 12.0D, 0.0D, 4.999992D + d * 12.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(38, 38, 38)), new Stop(1.0D, Color.rgb(30, 30, 30)) }));
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.rect(6.0D, 0.0D, 6.0D, 6.0D);
    localGraphicsContext.closePath();
    d = 0.0D;
    localGraphicsContext.setFill(new LinearGradient(0.0D, d * 12.0D, 0.0D, 6.0D + d * 12.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(48, 48, 48)), new Stop(1.0D, Color.rgb(40, 40, 40)) }));
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.rect(6.999995999999999D, 0.9999960000000001D, 3.999996D, 4.999992D);
    localGraphicsContext.closePath();
    d = 0.083333D;
    localGraphicsContext.setFill(new LinearGradient(0.0D, d * 12.0D, 0.0D, 4.999992D + d * 12.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(53, 53, 53)), new Stop(1.0D, Color.rgb(45, 45, 45)) }));
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.rect(0.0D, 6.0D, 6.0D, 6.0D);
    localGraphicsContext.closePath();
    d = 0.5D;
    localGraphicsContext.setFill(new LinearGradient(0.0D, d * 12.0D, 0.0D, 6.0D + d * 12.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(48, 48, 48)), new Stop(1.0D, Color.rgb(40, 40, 40)) }));
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.rect(0.9999960000000001D, 6.999995999999999D, 3.999996D, 4.999992D);
    localGraphicsContext.closePath();
    d = 0.583333D;
    localGraphicsContext.setFill(new LinearGradient(0.0D, d * 12.0D, 0.0D, 4.999992D + d * 12.0D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(53, 53, 53)), new Stop(1.0D, Color.rgb(45, 45, 45)) }));
    localGraphicsContext.fill();
    WritableImage localWritableImage = localCanvas.snapshot(SNAPSHOT_PARAMETER, null);
    ImagePattern localImagePattern = new ImagePattern(localWritableImage, 0.0D, 0.0D, 12.0D, 12.0D, false);
    return localImagePattern;
  }
  
  public static ImagePattern createPunchedSheetPattern(Color paramColor)
  {
    Canvas localCanvas = new Canvas(15.0D, 15.0D);
    GraphicsContext localGraphicsContext = localCanvas.getGraphicsContext2D();
    localGraphicsContext.beginPath();
    localGraphicsContext.rect(0.0D, 0.0D, 15.0D, 15.0D);
    localGraphicsContext.closePath();
    localGraphicsContext.setFill(paramColor);
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.moveTo(0.0D, 3.9999900000000004D);
    localGraphicsContext.bezierCurveTo(0.0D, 6.0D, 0.99999D, 6.99999D, 3.0D, 6.99999D);
    localGraphicsContext.bezierCurveTo(4.999995D, 6.99999D, 6.0D, 6.0D, 6.0D, 3.9999900000000004D);
    localGraphicsContext.bezierCurveTo(6.0D, 1.9999950000000002D, 4.999995D, 0.99999D, 3.0D, 0.99999D);
    localGraphicsContext.bezierCurveTo(0.99999D, 0.99999D, 0.0D, 1.9999950000000002D, 0.0D, 3.9999900000000004D);
    localGraphicsContext.closePath();
    localGraphicsContext.setFill(new LinearGradient(0.0D, 0.99999D, 0.0D, 6.99999D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(0, 0, 0)), new Stop(1.0D, Color.rgb(68, 68, 68)) }));
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.moveTo(0.0D, 3.0D);
    localGraphicsContext.bezierCurveTo(0.0D, 4.999995D, 0.99999D, 6.0D, 3.0D, 6.0D);
    localGraphicsContext.bezierCurveTo(4.999995D, 6.0D, 6.0D, 4.999995D, 6.0D, 3.0D);
    localGraphicsContext.bezierCurveTo(6.0D, 0.99999D, 4.999995D, 0.0D, 3.0D, 0.0D);
    localGraphicsContext.bezierCurveTo(0.99999D, 0.0D, 0.0D, 0.99999D, 0.0D, 3.0D);
    localGraphicsContext.closePath();
    localGraphicsContext.setFill(paramColor.darker().darker());
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.moveTo(6.99999D, 10.999995D);
    localGraphicsContext.bezierCurveTo(6.99999D, 12.99999D, 7.999994999999999D, 13.999995D, 9.99999D, 13.999995D);
    localGraphicsContext.bezierCurveTo(12.0D, 13.999995D, 12.99999D, 12.99999D, 12.99999D, 10.999995D);
    localGraphicsContext.bezierCurveTo(12.99999D, 9.0D, 12.0D, 7.999994999999999D, 9.99999D, 7.999994999999999D);
    localGraphicsContext.bezierCurveTo(7.999994999999999D, 7.999994999999999D, 6.99999D, 9.0D, 6.99999D, 10.999995D);
    localGraphicsContext.closePath();
    localGraphicsContext.setFill(new LinearGradient(0.0D, 7.999994999999999D, 0.0D, 13.999995D, false, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(0, 0, 0)), new Stop(1.0D, Color.rgb(68, 68, 68)) }));
    localGraphicsContext.fill();
    localGraphicsContext.beginPath();
    localGraphicsContext.moveTo(6.99999D, 9.99999D);
    localGraphicsContext.bezierCurveTo(6.99999D, 12.0D, 7.999994999999999D, 12.99999D, 9.99999D, 12.99999D);
    localGraphicsContext.bezierCurveTo(12.0D, 12.99999D, 12.99999D, 12.0D, 12.99999D, 9.99999D);
    localGraphicsContext.bezierCurveTo(12.99999D, 7.999994999999999D, 12.0D, 6.99999D, 9.99999D, 6.99999D);
    localGraphicsContext.bezierCurveTo(7.999994999999999D, 6.99999D, 6.99999D, 7.999994999999999D, 6.99999D, 9.99999D);
    localGraphicsContext.closePath();
    localGraphicsContext.setFill(paramColor.darker().darker());
    localGraphicsContext.fill();
    WritableImage localWritableImage = localCanvas.snapshot(SNAPSHOT_PARAMETER, null);
    ImagePattern localImagePattern = new ImagePattern(localWritableImage, 0.0D, 0.0D, 15.0D, 15.0D, false);
    return localImagePattern;
  }
  
  public static Image createNoiseImage(double paramDouble1, double paramDouble2, Color paramColor)
  {
    return createNoiseImage(paramDouble1, paramDouble2, paramColor.darker(), paramColor.brighter(), 30.0D);
  }
  
  public static Image createNoiseImage(double paramDouble1, double paramDouble2, Color paramColor1, Color paramColor2, double paramDouble3)
  {
    if ((paramDouble1 <= 0.0D) || (paramDouble2 <= 0.0D)) {
      return null;
    }
    double d1 = clamp(0.0D, 100.0D, paramDouble3);
    WritableImage localWritableImage = new WritableImage((int)paramDouble1, (int)paramDouble2);
    PixelWriter localPixelWriter = localWritableImage.getPixelWriter();
    Random localRandom1 = new Random();
    Random localRandom2 = new Random();
    double d2 = d1 / 100.0D / 2.0D;
    double d3 = d1 / 100.0D;
    for (int i = 0; i < paramDouble2; i++) {
      for (int j = 0; j < paramDouble1; j++)
      {
        Color localColor;
        if (localRandom1.nextBoolean()) {
          localColor = paramColor2;
        } else {
          localColor = paramColor1;
        }
        double d4 = clamp(0.0D, 1.0D, d2 + localRandom2.nextDouble() * d3);
        localPixelWriter.setColor(j, i, Color.color(localColor.getRed(), localColor.getGreen(), localColor.getBlue(), d4));
      }
    }
    return localWritableImage;
  }
  
  public static Paint applyNoisyBackground(Shape paramShape, Color paramColor)
  {
    int i = (int)paramShape.getLayoutBounds().getWidth();
    int j = (int)paramShape.getLayoutBounds().getHeight();
    WritableImage localWritableImage = new WritableImage(i, j);
    PixelWriter localPixelWriter = localWritableImage.getPixelWriter();
    Random localRandom1 = new Random();
    Random localRandom2 = new Random();
    for (int k = 0; k < j; k++) {
      for (int m = 0; m < i; m++)
      {
        Color localColor;
        if (localRandom1.nextBoolean()) {
          localColor = paramColor.brighter();
        } else {
          localColor = paramColor.darker();
        }
        double d1 = clamp(0.0D, 1.0D, 0.045D + localRandom2.nextDouble() * 0.09D);
        localPixelWriter.setColor(m, k, Color.color(localColor.getRed(), localColor.getGreen(), localColor.getBlue(), d1));
      }
    }
    double d2 = paramShape.getLayoutBounds().getMinX();
    double d3 = paramShape.getLayoutBounds().getMinY();
    double d4 = paramShape.getLayoutBounds().getWidth();
    double d5 = paramShape.getLayoutBounds().getHeight();
    return new ImagePattern(localWritableImage, d2, d3, d4, d5, false);
  }
  
  public static Image createBrushedMetalImage(double paramDouble1, double paramDouble2, Color paramColor)
  {
    BrushedMetalPaint localBrushedMetalPaint = new BrushedMetalPaint(paramColor);
    return localBrushedMetalPaint.getImage(paramDouble1, paramDouble2);
  }
  
  public static Paint applyBrushedMetalBackground(Shape paramShape, Color paramColor)
  {
    BrushedMetalPaint localBrushedMetalPaint = new BrushedMetalPaint(paramColor);
    return localBrushedMetalPaint.apply(paramShape);
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/util/Util.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */