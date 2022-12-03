package model

class Gravity(
    var normalStrength: Double,
    var reducedStrength: Double,
    var watchKeys: KeyState,
    var mode: GravityMode
): Force {

    override fun apply(box: Box): BoxDelta {
        val less = mode == GravityMode.LESS_IF_HOLDING_UP && watchKeys.up
                || mode == GravityMode.LESS_IF_HOLDING_UP_AND_MOVING_UP && watchKeys.up && box.vy > 0
        val strength = if(less) reducedStrength else normalStrength
        return BoxDelta(0.0, 0.0, 0.0, -strength)
    }

}