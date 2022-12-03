package game

import Integrator
import State
import model.*

class World(
    val keys: KeyState,
    var level: Level,
    var player: Box,
    var integrator: Integrator,
    val gravity: Gravity,
    val drag: Drag,
    val jump: Jump
    ): State<World, WorldDelta> {

    override fun copy(): World {
        return World(keys, level, player.copy(), integrator, gravity, drag, jump)
    }

    override fun diff(): WorldDelta {
        if(keys.jump && canJump()) {
            player.vy = jump.getStrength()
        }
        return WorldDelta(
            player.diff()
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