package model

import State

data class Box(
    var mass: Double,
    var radius: Double,
    var x: Double,
    var y: Double,
    var vx: Double,
    var vy: Double,
    var forces: List<Force>,
    var surfaces: List<Surface>
): State<Box, BoxDelta> {
    override fun copy(): Box {
        return Box(mass, radius, x, y, vx, vy, forces, surfaces)
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
        var collide: CollisionResult? = null
        for(surface in surfaces) {
            var icollide = surface.clamp(this, by)
            if(icollide != null && (collide == null || icollide < collide)) {
                collide = icollide
            }
        }
        if(collide != null) {
            x += by.vx * collide.fracMoved + (1 - collide.fracMoved) * collide.newVx
            y += by.vy * collide.fracMoved + (1 - collide.fracMoved) * collide.newVy
            vx = collide.newVx
            vy = collide.newVy
        } else {
            x += by.vx
            y += by.vy
            vx += by.ax
            vy += by.ay
        }
    }

}
