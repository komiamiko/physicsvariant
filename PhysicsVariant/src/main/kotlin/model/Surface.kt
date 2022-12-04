package model

import java.awt.geom.AffineTransform
import java.awt.geom.Point2D
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

data class Surface(
    val x1: Double,
    val y1: Double,
    val x2: Double,
    val y2: Double,
    var allowJump: Boolean = false
    ) {

    fun isTouching(box: Box): Boolean {
        val dirX = x2 - x1
        val dirY = y2 - y1
        val dirMag = hypot(dirX, dirY)
        val dirMag2 = dirMag * dirMag
        val dotNorm = (dirX * (box.x - x1) + dirY * (box.y - y1)) / dirMag2
        val npfac = min(1.0, max(0.0, dotNorm))
        val nearestX = x1 + npfac * dirX
        val nearestY = y1 + npfac * dirY
        val dist = hypot(box.x - nearestX, box.y - nearestY)
        return dist <= box.radius + 1e-6 // is near tolerance
    }

    fun canJump(box: Box): Boolean {
        return allowJump && isTouching(box)
    }

    fun clamp(box: Box, boxDelta: BoxDelta): CollisionResult? {
        var vmag = hypot(boxDelta.vx, boxDelta.vy)
        if(vmag <= 1e-6) return null
        var transform = AffineTransform(x2 - x1, y2 - y1, -boxDelta.vx, -boxDelta.vy, x1 - box.x, y1 - box.y)
        if(transform.determinant <= 1e-9) return null
        var nearest = Point2D.Double(0.0, 0.0)
        val origin = Point2D.Double(0.0, 0.0)
        transform.inverseTransform(origin, nearest)
        nearest.x = min(1.0, max(0.0, nearest.x))
        nearest.y = min(1.0, max(0.0, nearest.y))
        var nearestPost = Point2D.Double(0.0, 0.0)
        // TODO missing linear algebra, some cases not covered
        transform.transform(nearest, nearestPost)
        if(nearestPost.distance(origin) > box.radius) return null
        // it collides!
        // TODO legit linear algebra to get precise collision point, not this bs
        var lt = 0.0
        var rt = nearest.y
        val dirX = x2 - x1
        val dirY = y2 - y1
        val dirMag = hypot(dirX, dirY)
        val dirMag2 = dirMag * dirMag
        while(rt - lt > Math.ulp(lt) * 2) {
            val mt = lt + (rt - lt) * 0.5
            val x3 = x1 - boxDelta.vx * mt
            val y3 = y1 - boxDelta.vy * mt
            val dotNorm = (dirX * (box.x - x3) + dirY * (box.y - y3)) / dirMag2
            val npfac = min(1.0, max(0.0, dotNorm))
            val nearestX = x3 + npfac * dirX
            val nearestY = y3 + npfac * dirY
            val dist = hypot(box.x - nearestX, box.y - nearestY)
            if(dist <= box.radius) {
                // hits - possibly too high
                rt = mt
            } else {
                // not hits - possibly too low
                lt = mt
            }
        }
        // project original velocity onto surface tangent vector
        val dotv = (boxDelta.vx * dirX + boxDelta.vy * dirY) / dirMag2
        val newVx = dirX * dotv
        val newVy = dirY * dotv
        // ignore collision if moving away
        val x3 = x1 - boxDelta.vx * lt
        val y3 = y1 - boxDelta.vy * lt
        val dotNorm = (dirX * (box.x - x3) + dirY * (box.y - y3)) / dirMag2
        val npfac = min(1.0, max(0.0, dotNorm))
        val nearestX = x3 + npfac * dirX
        val nearestY = y3 + npfac * dirY
        val dotnv = boxDelta.vx * (box.x - nearestX) + boxDelta.vy * (box.y - nearestY)
        if(dotnv >= 0) return null
        return CollisionResult(lt, newVx, newVy)
    }

}
