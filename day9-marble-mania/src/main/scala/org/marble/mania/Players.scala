package org.marble.mania

final case class Players(pointsPerPlayer: Map[Int, Int] = Map.empty, currentPlayer: Int = 1) {
  def initialize(maxPlayers: Int): Players = {
    val result = for (i <- 1 to maxPlayers) yield {
      (i, 0)
    }
    Players(result.toMap, currentPlayer)
  }

  def nextPlayer(): Players =
    if (currentPlayer == pointsPerPlayer.size) {
      Players(pointsPerPlayer)
    } else {
      val nextPlayer = currentPlayer + 1
      Players(pointsPerPlayer, nextPlayer)
    }

  def addPoints(points: Int): Players = {
    val currentPoints      = pointsPerPlayer(currentPlayer)
    val newPoints          = currentPoints + points
    val newPointsPerPlayer = pointsPerPlayer + (currentPlayer -> newPoints)
    Players(newPointsPerPlayer, currentPlayer)
  }

  def printScore(currentMarbleNumber: Int): Unit = {
    println(s"----Score after Marble number: $currentMarbleNumber")
    pointsPerPlayer.foreach(entry => println(s"Player ${entry._1}  ---  Score ${entry._2}"))
    println()
  }

  def highScore(): Int =
    pointsPerPlayer.values.max
}
