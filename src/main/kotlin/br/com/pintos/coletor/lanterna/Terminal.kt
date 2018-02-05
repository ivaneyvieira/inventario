package br.com.pintos.coletor.lanterna

import br.com.pintos.coletor.lanterna.util.open
import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.SGR.REVERSE
import com.googlecode.lanterna.graphics.PropertyTheme
import com.googlecode.lanterna.gui2.BasicWindow
import com.googlecode.lanterna.gui2.MultiWindowTextGUI
import com.googlecode.lanterna.gui2.TextBox
import com.googlecode.lanterna.gui2.Window
import com.googlecode.lanterna.gui2.Window.Hint
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.ansi.UnixTerminal
import terminal.NativeGNULinuxTerminal
import java.io.Closeable
import java.util.*

class Terminal(val window: Window) : Closeable {
  override fun close() {
    screen.stopScreen()
  }

  fun startScreen() {
    val terminalFactory = DefaultTerminalFactory()
    //terminalFactory.setInitialTerminalSize(TerminalSize(20, 9))

    //    val palete = TerminalEmulatorPalette(Color.white, Color.white, Color.black, Color.black, Color.black, Color.black,
    //                                         Color.black, Color.white, Color.white, Color.black, Color.black, Color.black,
    //                                         Color.black, Color.black, Color.black, Color.black, Color.black, Color.white,
    //                                         Color.white)
    //    terminalFactory.setTerminalEmulatorColorConfiguration(TerminalEmulatorColorConfiguration.newInstance(palete))

    screen = TerminalScreen(TerminalWindow())
    SGR.values().forEach { screen.terminal.disableSGR(it) }
    screen.terminal.enableSGR(REVERSE)
    screen.startScreen()
    textGUI = MultiWindowTextGUI(screen)
    val prop = Properties()
    prop.load(Terminal::class.java.getResourceAsStream("/mono-theme.properties"))
    textGUI.theme = PropertyTheme(prop)
    textGUI.theme = null
    textGUI.updateScreen()
    screen.clear()

    textGUI.addWindow(window)

    window.waitUntilClosed()
  }


  companion object {
    lateinit var textGUI: MultiWindowTextGUI
    lateinit var screen: TerminalScreen
  }
}

class MainWindow : BasicWindow() {
  init {
    setHints(
      Arrays.asList(
        Window.Hint.FIXED_POSITION,
        Window.Hint.FIXED_SIZE,
        Window.Hint.NO_DECORATIONS,
        Window.Hint.NO_POST_RENDERING,
        Window.Hint.MODAL,
        Window.Hint.FIT_TERMINAL_WINDOW
                   )
            )
    component =TextBox()
   // telaLogin.open()
  }
}