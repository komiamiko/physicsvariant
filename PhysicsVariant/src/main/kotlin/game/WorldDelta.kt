package game

import Delta
import model.BoxDelta

class WorldDelta(
    var playerDelta: BoxDelta
): Delta<WorldDelta> {
    override fun copy(): WorldDelta {
        return WorldDelta(playerDelta.copy())
    }

    override fun imul(by: Double) {
        playerDelta.imul(by)
    }

    override fun iadd(by: WorldDelta) {
        playerDelta.iadd(by.playerDelta)
    }
}