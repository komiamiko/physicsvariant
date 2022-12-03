package model

class Drag(
    val strength: Double,
    var mode: DragMode
    ): Force {

    override fun apply(box: Box): BoxDelta {
        val x = Math.hypot(box.vx, box.vy)
        val mul = strength * (if(mode == DragMode.NO_DRAG) 0.0 else if(mode == DragMode.LINEAR_DRAG) 1.0 else x)
        return BoxDelta(0.0, 0.0, -box.vx * mul, -box.vy * mul)
    }

}