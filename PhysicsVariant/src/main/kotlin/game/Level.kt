package game

import model.Box
import model.Surface

data class Level(
    val spawn: Box,
    val goal: Hitbox,
    val surfaces: List<Surface>
)
