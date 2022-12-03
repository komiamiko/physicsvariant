package model

import State

data class Box(
    var mass: Double,
    var radius: Double,
    var x: Double,
    var y: Double,
    var vx: Double,
    var vy: Double,
    var forces: List<Force>
): State<Box, BoxDelta> {
    override fun copy(): Box {
        return Box(mass, radius, x, y, vx, vy, forces)
    }

    override fun diff(): BoxDelta {
        val delta = BoxDelta(vx, vy, 0.0, 0.0)
        for(force in forces) {
            val fdelta = force.apply(this)
            delta.iadd(fdelta)
        }
        return delta
    }

    override fun iadd(by: BoxDelta) {
        x += by.vx
        y += by.vy
        x += by.ax
        y += by.ay
    }

}
