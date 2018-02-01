package br.com.pintos.framework.fx.view;

import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icon
{
  public static ImageView icon(String paramString, Integer paramInteger)
  {
    ImageView localImageView = new ImageView();
    InputStream localInputStream = Icon.class.getResourceAsStream(filename(paramString, paramInteger));
    if (localInputStream == null) {
      return null;
    }
    localImageView.setImage(new Image(localInputStream));
    return localImageView;
  }
  
  public static Image image(String paramString, Integer paramInteger)
  {
    String str = filename(paramString, paramInteger);
    return new Image(str);
  }
  
  private static String filename(String paramString, Integer paramInteger)
  {
    return "/img/" + paramInteger + "/" + paramString;
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/fx/view/Icon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */