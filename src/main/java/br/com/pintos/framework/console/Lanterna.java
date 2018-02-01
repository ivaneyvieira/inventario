package br.com.pintos.framework.console;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.SGR;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

public class Lanterna
  extends ITerminal
{
  private final Terminal terminal;
  private final char VT_ESC = '\033';
  private final boolean debug;
  
  public Lanterna(Terminal paramTerminal, Integer paramInteger1, Integer paramInteger2)
  {
    super(paramInteger1, paramInteger2);
    this.debug = (paramTerminal instanceof SwingTerminal);
    this.terminal = paramTerminal;
  }
  
  public void apagaTela()
  {
    this.terminal.clearScreen();
  }
  
  public void beep()
  {
    char c = '\007';
    this.terminal.putCharacter(c);
    if (!this.debug)
    {
      getClass();
      echo('\033' + "[4;10]");
    }
    this.terminal.flush();
  }
  
  public void blinkOFF()
  {
    this.terminal.applySGR(new Terminal.SGR[] { Terminal.SGR.EXIT_BLINK });
    this.terminal.flush();
  }
  
  public void blinkON()
  {
    this.terminal.applySGR(new Terminal.SGR[] { Terminal.SGR.ENTER_BLINK });
    this.terminal.flush();
  }
  
  public void brightOFF()
  {
    this.terminal.applySGR(new Terminal.SGR[] { Terminal.SGR.EXIT_BOLD });
    this.terminal.flush();
  }
  
  public void brightON()
  {
    this.terminal.applySGR(new Terminal.SGR[] { Terminal.SGR.ENTER_BOLD });
    this.terminal.flush();
  }
  
  public void close()
  {
    this.terminal.exitPrivateMode();
  }
  
  public void cusorPos(Integer paramInteger1, Integer paramInteger2)
  {
    this.terminal.moveCursor(paramInteger1.intValue() - 1, paramInteger2.intValue() - 1);
    this.terminal.flush();
  }
  
  public void echo(String paramString)
  {
    for (char c : paramString.toCharArray()) {
      this.terminal.putCharacter(c);
    }
    this.terminal.flush();
  }
  
  private void esperaKey()
  {
    try
    {
      Thread.sleep(250L);
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  public void normal()
  {
    this.terminal.applySGR(new Terminal.SGR[] { Terminal.SGR.RESET_ALL });
    this.terminal.flush();
  }
  
  public void open()
  {
    this.terminal.enterPrivateMode();
  }
  
  public Key readKey()
  {
    Key localKey = null;
    while (localKey == null)
    {
      localKey = this.terminal.readInput();
      if (localKey == null) {
        esperaKey();
      }
    }
    return localKey;
  }
  
  public String readLine(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
  {
    cusorPos(paramInteger1, paramInteger2);
    StringBuilder localStringBuilder = new StringBuilder();
    for (;;)
    {
      Key localKey = readKey();
      Key.Kind localKind = localKey.getKind();
      if (localKind == Key.Kind.Enter) {
        break;
      }
      if (localKind == Key.Kind.Escape)
      {
        localStringBuilder.delete(0, localStringBuilder.length());
        localStringBuilder.append('\033');
      }
      else
      {
        sublinhadoON();
        if ((localKind == Key.Kind.NormalKey) && (localStringBuilder.length() <= paramInteger3.intValue()))
        {
          char c = localKey.getCharacter();
          localStringBuilder.append(c);
          this.terminal.putCharacter(c);
        }
        if ((localKind == Key.Kind.Backspace) && (localStringBuilder.length() > 0))
        {
          Integer localInteger = Integer.valueOf(localStringBuilder.length() - 1);
          localStringBuilder.deleteCharAt(localInteger.intValue());
          cusorPos(Integer.valueOf(paramInteger1.intValue() + localStringBuilder.length()), paramInteger2);
          this.terminal.putCharacter(' ');
          cusorPos(Integer.valueOf(paramInteger1.intValue() + localStringBuilder.length()), paramInteger2);
        }
        normal();
        if (paramInteger3.intValue() == 1) {
          break;
        }
      }
    }
    return localStringBuilder.toString();
  }
  
  public void reversoOFF()
  {
    this.terminal.applySGR(new Terminal.SGR[] { Terminal.SGR.EXIT_REVERSE });
    this.terminal.flush();
  }
  
  public void reversoON()
  {
    this.terminal.applySGR(new Terminal.SGR[] { Terminal.SGR.ENTER_REVERSE });
    this.terminal.flush();
  }
  
  public void sublinhadoOFF()
  {
    this.terminal.applySGR(new Terminal.SGR[] { Terminal.SGR.EXIT_UNDERLINE });
    this.terminal.flush();
  }
  
  public void sublinhadoON()
  {
    this.terminal.applySGR(new Terminal.SGR[] { Terminal.SGR.ENTER_UNDERLINE });
    this.terminal.flush();
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/console/Lanterna.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */