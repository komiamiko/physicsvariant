package model

import Delta

data class BoxDelta(
    var vx: Double,
    var vy: Double,
    var ax: Double,
    var ay: Double
): Delta<BoxDelta> {
    override fun copy(): BoxDelta {
        return BoxDelta(vx, vy, ax, ay)
    }

    override fun imul(by: Double) {
        vx *= by
        vy *= by
        ax *= by
        ay *= by
    }

    override fun iadd(by: BoxDelta) {
        vx += by.vx
        vy += by.vy
        ax += by.ax
        ay += by.ay
    }

}
