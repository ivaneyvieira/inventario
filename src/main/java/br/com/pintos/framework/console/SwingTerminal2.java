package br.com.pintos.framework.console;

import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.Terminal.SGR;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.swing.TerminalAppearance;

public class SwingTerminal2
  extends SwingTerminal
{
  public SwingTerminal2() {}
  
  public SwingTerminal2(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
  }
  
  public SwingTerminal2(TerminalAppearance paramTerminalAppearance)
  {
    super(paramTerminalAppearance);
  }
  
  public SwingTerminal2(TerminalAppearance paramTerminalAppearance, int paramInt1, int paramInt2)
  {
    super(paramTerminalAppearance, paramInt1, paramInt2);
  }
  
  public SwingTerminal2(TerminalSize paramTerminalSize)
  {
    super(paramTerminalSize);
  }
  
  public void applySGR(Terminal.SGR... paramVarArgs)
  {
    super.applySGR(paramVarArgs);
    for (Terminal.SGR localSGR : paramVarArgs) {
      if (localSGR == Terminal.SGR.ENTER_REVERSE)
      {
        applyForegroundColor(Terminal.Color.BLACK);
        applyBackgroundColor(Terminal.Color.WHITE);
      }
      else if (localSGR == Terminal.SGR.EXIT_REVERSE)
      {
        applyForegroundColor(Terminal.Color.WHITE);
        applyBackgroundColor(Terminal.Color.BLACK);
      }
    }
  }
}


/* Location:              /home/ivaneyvieira/git/inventario/Inventario.jar!/br/com/pintos/framework/console/SwingTerminal2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */