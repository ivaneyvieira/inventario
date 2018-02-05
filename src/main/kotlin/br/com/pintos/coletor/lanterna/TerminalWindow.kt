package br.com.pintos.coletor.lanterna

import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.SGR.REVERSE
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.terminal.ansi.UnixTerminal

class TerminalWindow : UnixTerminal() {

  override fun disableSGR(sgr: SGR?) {
    if(sgr == REVERSE)
      super.disableSGR(sgr)
  }

  override fun enableSGR(sgr: SGR?) {
    if(sgr == REVERSE)
      super.enableSGR(sgr)
  }

  override fun setBackgroundColor(color: TextColor?) {
    //super.setBackgroundColor(color)
  }

  override fun resetColorAndSGR() {
    super.resetColorAndSGR()
  }

  override fun setForegroundColor(color: TextColor?) {
   // super.setForegroundColor(color)
  }
}