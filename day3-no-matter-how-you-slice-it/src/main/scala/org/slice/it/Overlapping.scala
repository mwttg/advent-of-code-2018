package org.slice.it
import org.common.FileReader

class Overlapping {

  private def hasContent(input: String): Boolean = if (input.isEmpty) false else true

  def extract(input: String): Array[String] = input.split("(#| |@|,|:|x)").filter(item => hasContent(item))

  def createDefinitions(lines: Seq[String]): Seq[Array[String]] =
    for (line <- lines) yield extract(line)

  def createRectangles(definitions: Seq[Array[String]]): Seq[Rectangle] =
    for (definition <- definitions) yield Rectangle.from(definition)

  def getMaxXY(rectangles: Seq[Rectangle]): (Int, Int) = {
    var maxX = 0
    var maxY = 0
    for (rect <- rectangles) {
      val x = if (rect.left > rect.right) rect.left else rect.right
      val y = if (rect.top > rect.bottom) rect.top else rect.bottom

      if (x > maxX) maxX = x
      if (y > maxY) maxY = y
    }
    (maxX, maxY)
  }

  def createField(maxXY: (Int, Int)): Array[Array[Int]] = Array.ofDim[Int](maxXY._1, maxXY._2)

  def fillFieldWith(rectangle: Rectangle, field: Array[Array[Int]]): Array[Array[Int]] = {
    val result = field
    for (x <- rectangle.left until rectangle.right) {
      for (y <- rectangle.top until rectangle.bottom) {
        val value = result(x)(y)
        if (value == 0) result(x)(y) = 1 else result(x)(y) = 9
      }
    }
    result
  }

  def fill(rectangles: Seq[Rectangle], field: Array[Array[Int]]): Array[Array[Int]] = {
    var result = field
    for (rect <- rectangles) {
      result = fillFieldWith(rect, result)
    }
    result
  }

  def count(field: Array[Array[Int]], max: (Int, Int)): Int = {
    var count = 0
    for (x <- 0 until max._1) {
      for (y <- 0 until max._2) {
        if (field(x)(y) == 9)
          count = count + 1
      }
    }
    count
  }

  def startPuzzle1(filename: String): Unit = {
    val lines       = FileReader.read(filename).get
    val definitions = createDefinitions(lines)
    val rectangles  = createRectangles(definitions)
    val max         = getMaxXY(rectangles)
    val field       = createField(max)
    val resultField = fill(rectangles, field)
    val result      = count(resultField, max)

    println(s"There are `$result` overlapping claims")
  }

}
