fun String.padCenter(sizePad : Int) : String{
  val tamanho = length
  val dif = sizePad - tamanho
  return if(dif > 0) {
    val meio: Int = dif / 2
    padStart(tamanho + meio ).padEnd(sizePad)
  }else
    this
}