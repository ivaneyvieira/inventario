package br.com.pintos.coletor.lanterna

import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.SGR.REVERSE
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.bundle.LanternaThemes
import com.googlecode.lanterna.graphics.PropertyTheme
import com.googlecode.lanterna.gui2.MultiWindowTextGUI
import com.googlecode.lanterna.gui2.Window
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorColorConfiguration
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorPalette
import java.awt.Color
import java.io.Closeable
import java.util.*

class Terminal(val window: Window) : Closeable {
  override fun close() {
    screen.stopScreen()
  }

  fun startScreen() {
    val terminalFactory = DefaultTerminalFactory()
    terminalFactory.setInitialTerminalSize(TerminalSize(20, 9))

//    val palete = TerminalEmulatorPalette(Color.white, Color.white, Color.black, Color.black, Color.black, Color.black,
//                                         Color.black, Color.white, Color.white, Color.black, Color.black, Color.black,
//                                         Color.black, Color.black, Color.black, Color.black, Color.black, Color.white,
//                                         Color.white)
//    terminalFactory.setTerminalEmulatorColorConfiguration(TerminalEmulatorColorConfiguration.newInstance(palete))

    screen = terminalFactory.createScreen()
    SGR.values().forEach { screen.terminal.disableSGR(it) }
    screen.terminal.enableSGR(REVERSE)
    screen.startScreen()
    textGUI = MultiWindowTextGUI(screen)
    screen.clear()
    val prop = Properties()
    prop.load(Terminal::class.java.getResourceAsStream("/mono-theme.properties"))
    textGUI.theme = PropertyTheme(prop)

    textGUI.addWindow(window)

    textGUI.waitForWindowToClose(window)
  }

  companion object {
    lateinit var textGUI: MultiWindowTextGUI
    lateinit var screen: TerminalScreen
  }
}

