package jfxtras.labs.scene.control.gauge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

public class UtilHex
{
  private byte[] rawData = null;
  
  public static byte[] toBytes(String paramString)
  {
    String[] arrayOfString = paramString.split("\\s");
    return toBytes(arrayOfString);
  }
  
  public static byte[] toBytes(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList();
    for (Object localObject2 : paramArrayOfString)
    {
      int k = 0;
      if ((!((String)localObject2).equals("0")) && (!((String)localObject2).equals("00")))
      {
        k = Byte.parseByte(((String)localObject2).substring(0, 1), 16);
        k = 16 * k + Byte.parseByte(((String)localObject2).substring(1), 16);
      }
      localArrayList.add(new Byte((byte)k));
    }
    ??? = new byte[localArrayList.size()];
    for (??? = 0; ??? < localArrayList.size(); ???++) {
      ???[???] = ((Byte)localArrayList.get(???)).byteValue();
    }
    return (byte[])???;
  }
  
  public static String long2Dword(long paramLong, boolean paramBoolean)
  {
    String str1 = Long.toHexString(paramLong);
    String str2 = "00000000".substring(0, 8 - str1.length()).concat(str1);
    if (paramBoolean) {
      return str2.substring(6, 8).concat(" ").concat(str2.substring(4, 6)).concat(" ").concat(str2.substring(2, 4)).concat(" ").concat(str2.substring(0, 2));
    }
    return str2.substring(0, 2).concat(" ").concat(str2.substring(2, 4)).concat(" ").concat(str2.substring(4, 6)).concat(" ").concat(str2.substring(6, 8));
  }
  
  public static String long2Word(long paramLong, boolean paramBoolean)
  {
    String str1 = Long.toHexString(paramLong);
    String str2 = "0000".substring(0, 4 - str1.length()).concat(str1);
    if (paramBoolean) {
      return str2.substring(2, 4).concat(" ").concat(str2.substring(0, 2));
    }
    return str2.substring(0, 2).concat(" ").concat(str2.substring(2, 4));
  }
  
  public static String dec2hexStr(byte paramByte)
  {
    return Integer.toString((paramByte & 0xFF) + 256, 16).substring(1).toUpperCase();
  }
  
  public static String dec2hexStr(int paramInt)
  {
    return Integer.toString((paramInt & 0xFF) + 256, 16).substring(1).toUpperCase();
  }
  
  public static String bin2Hex(String paramString)
  {
    return Integer.toHexString(Integer.parseInt(paramString, 2));
  }
  
  public static String bytes2String(byte[] paramArrayOfByte)
  {
    String str = "";
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      str = str.concat(dec2hexStr(paramArrayOfByte[i]));
      if (i < paramArrayOfByte.length - 1) {
        str = str.concat(" ");
      }
    }
    return str;
  }
  
  public static int word2Int(String paramString1, String paramString2)
  {
    String str1 = "00".substring(0, 2 - paramString1.length()).concat(paramString1);
    String str2 = str1.concat("00".substring(0, 2 - paramString2.length()).concat(paramString2));
    return Integer.parseInt(str2, 16);
  }
  
  public static long dword2Long(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    String str1 = "00".substring(0, 2 - paramString1.length()).concat(paramString1);
    String str2 = str1.concat("00".substring(0, 2 - paramString2.length()).concat(paramString2));
    String str3 = str2.concat("00".substring(0, 2 - paramString3.length()).concat(paramString3));
    String str4 = str3.concat("00".substring(0, 2 - paramString4.length()).concat(paramString4));
    return Long.parseLong(str4, 16);
  }
  
  public static int hex2Decimal(String paramString)
  {
    int i = Integer.parseInt(paramString, 16);
    return i;
  }
  
  public static String String2Binary(String paramString)
  {
    return Integer.toBinaryString(hex2Decimal(paramString));
  }
  
  public static String hex2bin(String paramString)
  {
    String str1 = String2Binary(paramString);
    String str2 = "00000000".substring(0, 8 - str1.length()).concat(str1);
    return str2;
  }
  
  public boolean convertsBmp(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramInt2 > 255) {
      paramInt2 = 255;
    }
    if (paramInt1 > paramInt2) {
      paramInt1 = paramInt2;
    }
    if (paramInt1 < 0) {
      paramInt1 = 0;
    }
    int[] arrayOfInt1 = { paramInt1, (paramInt2 + paramInt1) / 2, paramInt2 };
    int[] arrayOfInt2 = { paramBoolean1 ? 1 : 0, paramBoolean2 ? 1 : 0, paramBoolean3 ? 1 : 0 };
    String str1 = paramString.endsWith(".bmp") ? paramString : paramString.concat(".bmp");
    InputStream localInputStream = null;
    try
    {
      localInputStream = getClass().getResourceAsStream(str1);
    }
    catch (MissingResourceException localMissingResourceException) {}
    jBMP2Panel localjBMP2Panel = new jBMP2Panel(str1, arrayOfInt2, arrayOfInt1);
    try
    {
      boolean bool = false;
      if (localInputStream != null) {
        bool = localjBMP2Panel.getBMPImageFromStream(localInputStream);
      } else {
        bool = localjBMP2Panel.getBMPImage();
      }
      if (bool)
      {
        String str2 = localjBMP2Panel.BMP2MemoriaGrafica();
        this.rawData = toBytes(str2);
        return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public String getRawData()
  {
    if (this.rawData == null) {
      return null;
    }
    return bytes2String(this.rawData);
  }
  
  private class jBMP2Panel
  {
    private InputStream is;
    private int curPos = 0;
    private int bitmapOffset;
    private int width;
    private int height;
    private int levels = 3;
    private int[] tonos;
    private int colorMask = 1;
    private int numColors = 1;
    private short bitsPerPixel;
    private int compression;
    private int actualSizeOfBitmap;
    private int scanLineSize;
    private int actualColorsUsed;
    private byte[] r;
    private byte[] g;
    private byte[] b;
    private int noOfEntries;
    private byte[] byteData;
    private int[] intData;
    private byte[] m_RawData = null;
    private String m_sFullPath = null;
    
    public jBMP2Panel(String paramString, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    {
      this.m_sFullPath = paramString;
      this.tonos = paramArrayOfInt2;
      this.levels = paramArrayOfInt2.length;
      this.colorMask = (paramArrayOfInt1[0] + 2 * paramArrayOfInt1[1] + 4 * paramArrayOfInt1[2]);
      this.numColors = 3;
    }
    
    public boolean getBMPImage()
      throws Exception
    {
      File localFile = new File(this.m_sFullPath);
      FileInputStream localFileInputStream = null;
      try
      {
        localFileInputStream = new FileInputStream(localFile);
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        System.out.println("File " + this.m_sFullPath + " not found");
        return false;
      }
      try
      {
        read(localFileInputStream);
      }
      catch (IOException localIOException)
      {
        System.out.append("Error reading bmp file");
        return false;
      }
      return true;
    }
    
    public boolean getBMPImageFromStream(InputStream paramInputStream)
      throws Exception
    {
      try
      {
        read(paramInputStream);
      }
      catch (IOException localIOException)
      {
        System.out.append("Error reading bmp file");
        return false;
      }
      return true;
    }
    
    protected void getFileHeader()
      throws IOException, Exception
    {
      int i = 19778;
      int k = 0;
      int m = 0;
      i = readShort();
      if (i != 19778) {
        throw new Exception("Not a BMP file");
      }
      int j = readInt();
      k = readShort();
      m = readShort();
      this.bitmapOffset = readInt();
    }
    
    protected void getBitmapHeader()
      throws IOException
    {
      int i = readInt();
      this.width = readInt();
      this.height = readInt();
      int j = readShort();
      this.bitsPerPixel = readShort();
      this.compression = readInt();
      int k = readInt();
      int m = readInt();
      int n = readInt();
      int i1 = readInt();
      int i2 = readInt();
      int i3 = this.height < 0 ? 1 : 0;
      int i4 = this.width * this.height;
      this.scanLineSize = ((this.width * this.bitsPerPixel + 31) / 32 * 4);
      if (k != 0) {
        this.actualSizeOfBitmap = k;
      } else {
        this.actualSizeOfBitmap = (this.scanLineSize * this.height);
      }
      if (i1 != 0) {
        this.actualColorsUsed = i1;
      } else if (this.bitsPerPixel < 16) {
        this.actualColorsUsed = (1 << this.bitsPerPixel);
      } else {
        this.actualColorsUsed = 0;
      }
    }
    
    protected void getPalette()
      throws IOException
    {
      this.noOfEntries = this.actualColorsUsed;
      if (this.noOfEntries > 0)
      {
        this.r = new byte[this.noOfEntries];
        this.g = new byte[this.noOfEntries];
        this.b = new byte[this.noOfEntries];
        for (int j = 0; j < this.noOfEntries; j++)
        {
          this.b[j] = ((byte)this.is.read());
          this.g[j] = ((byte)this.is.read());
          this.r[j] = ((byte)this.is.read());
          int i = this.is.read();
          this.curPos += 4;
        }
      }
    }
    
    protected void unpack(byte[] paramArrayOfByte, int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3)
    {
      int i = paramInt2;
      int j = paramInt1;
      int k = 255;
      for (int m = 0; m < paramInt3; m++)
      {
        int n = paramArrayOfByte[(j++)] & k;
        int i1 = (paramArrayOfByte[(j++)] & k) << 8;
        int i2 = (paramArrayOfByte[(j++)] & k) << 16;
        paramArrayOfInt[i] = (0xFF000000 | n | i1 | i2);
        i++;
      }
    }
    
    protected void unpack(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4)
      throws Exception
    {
      int i = paramInt3;
      int j = paramInt1;
      int k;
      int m;
      switch (paramInt2)
      {
      case 1: 
        k = 1;
        m = 8;
        break;
      case 4: 
        k = 15;
        m = 2;
        break;
      case 8: 
        k = -1;
        m = 1;
        break;
      default: 
        throw new Exception("Unsupported bits-per-pixel value");
      }
      int n = 0;
      for (;;)
      {
        int i1 = 8 - paramInt2;
        for (int i2 = 0; i2 < m; i2++)
        {
          int i3 = paramArrayOfByte1[j];
          i3 = (byte)(i3 >> i1);
          paramArrayOfByte2[i] = ((byte)(i3 & k));
          i++;
          n++;
          if (n == paramInt4) {
            return;
          }
          i1 -= paramInt2;
        }
        j++;
      }
    }
    
    protected int readScanLine(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = 0;
      int j = paramInt2;
      int k = 0;
      while (paramInt2 > 0)
      {
        i = this.is.read(paramArrayOfByte, paramInt1, paramInt2);
        if (i == -1) {
          return k == 0 ? -1 : k;
        }
        if (i == paramInt2) {
          return j;
        }
        paramInt2 -= i;
        paramInt1 += i;
        k += i;
      }
      return j;
    }
    
    protected void getPixelData()
      throws IOException, Exception
    {
      long l = this.bitmapOffset - this.curPos;
      if (l > 0L)
      {
        this.is.skip(l);
        this.curPos = ((int)(this.curPos + l));
      }
      int i = this.scanLineSize;
      if (this.bitsPerPixel > 8) {
        this.intData = new int[this.width * this.height];
      } else {
        this.byteData = new byte[this.width * this.height];
      }
      this.m_RawData = new byte[this.actualSizeOfBitmap];
      int j = 0;
      int k = (this.height - 1) * this.width;
      for (int m = this.height - 1; m >= 0; m--)
      {
        int n = readScanLine(this.m_RawData, j, i);
        if (n < i) {
          throw new Exception("Scan line ended prematurely after " + n + " bytes");
        }
        if (this.bitsPerPixel > 8) {
          unpack(this.m_RawData, j, this.intData, k, this.width);
        } else {
          unpack(this.m_RawData, j, this.bitsPerPixel, this.byteData, k, this.width);
        }
        j += i;
        k -= this.width;
      }
    }
    
    public String BMP2MemoriaGrafica()
    {
      int i = (this.width / 8 + (this.width % 8 > 0 ? 1 : 0)) * 8;
      int j = (this.width / 16 + (this.width % 16 > 0 ? 1 : 0)) * 16;
      long l = 32L + this.levels * j * this.height * this.numColors / 8;
      String str = "04 DF FF FF FF FF ";
      str = str.concat(UtilHex.long2Word(i, false)).concat(" ").concat(UtilHex.long2Word(this.height, false)).concat(" ");
      str = str.concat(UtilHex.dec2hexStr(this.colorMask)).concat(" ").concat(UtilHex.dec2hexStr(this.levels)).concat(" ");
      str = str.concat("00 00 00 00 00 00 01 00 ").concat(UtilHex.long2Dword(l - 32L, false)).concat(" ");
      str = str.concat(UtilHex.long2Dword(l, false)).concat(" 00 00 00 00");
      for (int k = 0; k < this.levels; k++)
      {
        String[][] arrayOfString = getPanelRawData(this.tonos[k]);
        for (int m = 0; m < this.numColors; m++) {
          for (int n = 0; n < arrayOfString[m].length; n++) {
            str = str.concat(" ").concat(arrayOfString[m][n]);
          }
        }
      }
      return str;
    }
    
    public String[] getMemoriaGrafica(int paramInt)
    {
      String[] arrayOfString = new String[3];
      String[][] arrayOfString1 = getPanelRawData(paramInt);
      for (int i = 0; i < 3; i++)
      {
        arrayOfString[i] = "";
        for (int j = 0; j < arrayOfString1[i].length; j++)
        {
          arrayOfString[i] = arrayOfString[i].concat(arrayOfString1[i][j]);
          if ((j > 0) && ((j + 1) % 8 == 0)) {
            arrayOfString[i] = arrayOfString[i].concat("#");
          } else if (j < arrayOfString1[i].length - 1) {
            arrayOfString[i] = arrayOfString[i].concat(" ");
          }
        }
      }
      return arrayOfString;
    }
    
    private String[][] getPanelRawData(int paramInt)
    {
      int i = 3;
      int j = this.scanLineSize;
      int k = (this.width / 8 + (this.width % 8 > 0 ? 1 : 0)) * 8;
      int m = (this.width / 16 + (this.width % 16 > 0 ? 1 : 0)) * 16;
      String[][] arrayOfString = new String[i][this.height * m / 8];
      int[] arrayOfInt = { 2, 1, 0 };
      for (int n = 0; n < i; n++)
      {
        int i1 = arrayOfInt[n];
        int i2 = 0;
        String str1 = "";
        for (int i3 = this.height - 1; i3 >= 0; i3--)
        {
          int i4 = n;
          while (i4 < m * i + n)
          {
            String str2 = "";
            for (int i5 = 0; i5 < 8; i5++) {
              if (i4 + i5 * i < j)
              {
                if ((this.m_RawData[(i4 + i5 * i + i3 * j)] & 0xFF) >= paramInt)
                {
                  str2 = str2.concat("1");
                  str1 = "1";
                }
                else
                {
                  str2 = str2.concat("0");
                  str1 = "0";
                }
              }
              else if (i4 + i5 * i < k * i) {
                str2 = str2.concat("0");
              } else if (i4 + i5 * i < m * i) {
                str2 = str2.concat("0");
              }
            }
            if (i2 < m * this.height / 8)
            {
              String str3 = UtilHex.bin2Hex(str2);
              String str4 = "00".substring(0, 2 - str3.length()).concat(str3);
              arrayOfString[i1][(i2++)] = str4;
            }
            i4 += i * 8;
          }
        }
      }
      return arrayOfString;
    }
    
    public void read(InputStream paramInputStream)
      throws IOException, Exception
    {
      this.is = paramInputStream;
      getFileHeader();
      getBitmapHeader();
      if (this.compression != 0) {
        throw new Exception("BMP Compression not supported");
      }
      getPalette();
      getPixelData();
    }
    
    protected int readInt()
      throws IOException
    {
      int i = this.is.read();
      int j = this.is.read();
      int k = this.is.read();
      int m = this.is.read();
      this.curPos += 4;
      return (m << 24) + (k << 16) + (j << 8) + (i << 0);
    }
    
    protected short readShort()
      throws IOException
    {
      int i = this.is.read();
      int j = this.is.read();
      this.curPos += 4;
      return (short)((j << 8) + i);
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/jfxtras/labs/scene/control/gauge/UtilHex.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */