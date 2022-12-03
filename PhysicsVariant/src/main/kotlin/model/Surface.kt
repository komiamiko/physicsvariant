package model

data class Surface(
    val x1: Double,
    val y1: Double,
    val x2: Double,
    val y2: Double,
    var allowJump: Boolean = false
    ) {

    fun isTouching(box: Box): Boolean {
        TODO()
    }

    fun canJump(box: Box): Boolean {
        return allowJump && isTouching(box)
    }

    fun clamp(box: Box, boxDelta: BoxDelta): CollisionResult? {
        TODO()
    }

}
