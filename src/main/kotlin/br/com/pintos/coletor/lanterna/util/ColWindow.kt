package br.com.pintos.coletor.lanterna.util

import br.com.pintos.coletor.lanterna.Terminal
import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.BasicWindow
import com.googlecode.lanterna.gui2.Button
import com.googlecode.lanterna.gui2.Component
import com.googlecode.lanterna.gui2.Direction
import com.googlecode.lanterna.gui2.Direction.HORIZONTAL
import com.googlecode.lanterna.gui2.Direction.VERTICAL
import com.googlecode.lanterna.gui2.Label
import com.googlecode.lanterna.gui2.LinearLayout
import com.googlecode.lanterna.gui2.LinearLayout.Alignment.Center
import com.googlecode.lanterna.gui2.Panel
import com.googlecode.lanterna.gui2.TextBox
import com.googlecode.lanterna.gui2.Window
import padCenter
import java.util.*

abstract class ColWindow(
  titulo: String,
  val cols: Int = 20,
  val rows: Int = 9
                        ) : BasicWindow("") {
  val sizeLinha = TerminalSize(cols, 1)
  val sizeArea = TerminalSize(cols, rows - 2)

  val panelTitle = Panel(LinearLayout(Direction.VERTICAL))
  val areaPanel = Panel()
  val panelButton = Panel(LinearLayout(Direction.HORIZONTAL))

  init {
    setHints(
      Arrays.asList(
        Window.Hint.FIXED_POSITION,
        Window.Hint.FIXED_SIZE,
        Window.Hint.NO_DECORATIONS,
       Window.Hint.NO_POST_RENDERING,
        Window.Hint.MODAL
                   )
            )
    size = TerminalSize(cols, rows)
    position = TerminalPosition.TOP_LEFT_CORNER
    val label = Label(titulo.padCenter(cols))

    label.setSizeComp(sizeLinha)
    label.addStyle(SGR.REVERSE)
    label.addStyle(SGR.BOLD)
    panelTitle.addComponent(label)

    areaPanel.setSizeComp(sizeArea)
    panelTitle.addComponent(areaPanel)
    panelButton.setSizeComp(sizeLinha)
    panelTitle.addComponent(panelButton)
    initComp()
    component = panelTitle
  }

  abstract fun initComp()

  fun addButton(
    caption: String,
    action: () -> Unit
               ) {
    val button = Button(caption, action)
    panelButton.addComponent(button, LinearLayout.createLayoutData(Center))
  }
}

fun Component.setSizeComp(value: TerminalSize) {
  size = value
  preferredSize = value
}

fun text(size: Int): TextBox {
  val text = TextBox(TerminalSize(size, 1), "")
  return text
}

fun text(col : Int, row : Int, msg : String): TextBox {
  return TextBox(TerminalSize(col, row), msg)
}

fun text(text: String): TextBox {
  return TextBox(TerminalSize(text.length, 1), text)
}

fun label(text: String): Label {
  return Label(text)
}

fun horizontal(vararg comps: Component): Panel {
  val layout = Panel(LinearLayout(HORIZONTAL))
  comps.forEach { layout.addComponent(it) }
  return layout
}

fun vertical(vararg comps: Component): Panel {
  val layout = Panel(LinearLayout(VERTICAL))
  comps.forEach { layout.addComponent(it) }
  return layout
}

fun Panel.addComponent(label: String) {
  addComponent(Label(label))
}

fun messageDialog(title : String, msg : String) : ColWindow{
  return MessageDialog(title, msg)
}

fun Window.open() {
  Terminal.textGUI.addWindow(this)
}

class MessageDialog(title : String, val msg : String) : ColWindow(title){
  override fun initComp() {
    val labelMsg = Label(msg)
    areaPanel.addComponent(labelMsg)
    addButton(" OK "){
      close()
    }
  }
}