package game

import model.Box

data class Hitbox(
    val x1: Double,
    val y1: Double,
    val x2: Double,
    val y2: Double
    ) {

    fun touches(box: Box): Boolean {
        return box.x + box.radius >= x1
                && box.x - box.radius <= x2
                && box.y + box.radius >= y1
                && box.y - box.radius <= y2
    }

    fun contains(box: Box): Boolean {
        return box.x - box.radius >= x1
                && box.x + box.radius <= x2
                && box.y - box.radius >= y1
                && box.y + box.radius <= y2
    }

}
