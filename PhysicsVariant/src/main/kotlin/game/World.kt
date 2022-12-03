package game

import Integrator
import State
import model.Box
import model.Drag
import model.Gravity
import model.KeyState

class World(
    val keys: KeyState,
    var level: Level,
    var player: Box,
    var integrator: Integrator,
    val gravity: Gravity,
    val drag: Drag
    ): State<World, WorldDelta> {

    override fun copy(): World {
        return World(keys, level, player.copy(), integrator, gravity, drag)
    }

    override fun diff(): WorldDelta {
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
}