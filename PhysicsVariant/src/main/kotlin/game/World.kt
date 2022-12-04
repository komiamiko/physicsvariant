package game

import Integrator
import State
import model.*
import kotlin.math.min

class World(
    val keys: KeyState,
    var level: Level,
    var player: Box,
    var integrator: Integrator,
    val gravity: Gravity,
    val drag: Drag,
    val jump: Jump,
    val walk: Walk,
    val friction: Friction
    ): State<World, WorldDelta> {

    override fun copy(): World {
        return World(keys, level, player.copy(), integrator, gravity, drag, jump, walk, friction)
    }

    override fun diff(): WorldDelta {
        val canJump = canJump()
        if(keys.jump && canJump) {
            player.vy = jump.getStrength()
        }
        var playerDiff = player.diff()
        if((keys.left xor keys.right) && canJump) {
            // note: for best realism, friction should be implemented as a Force
            val downForce = -gravity.apply(player).vy
            val accel = min(walk.strength, friction.getValue(downForce))
            if(keys.left) {
                playerDiff.ax -= accel
            } else {
                playerDiff.ax += accel
            }
        }
        return WorldDelta(
            playerDiff
        )
    }

    override fun iadd(by: WorldDelta) {
        player.iadd(by.playerDelta)
    }

    fun tick(by: Double): World {
        return integrator.step(this, by)
    }

    fun canJump(): Boolean {
        return level.surfaces.any { it -> it.canJump(player) }
    }
}