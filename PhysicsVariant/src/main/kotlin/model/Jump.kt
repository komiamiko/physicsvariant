package model

class Jump(
    val normalStrength: Double,
    val increasedStrength: Double,
    var watchKeys: KeyState,
    var mode: JumpMode
) {

    fun getStrength(): Double {
        return if(mode == JumpMode.STRONGER_IF_HOLDING_UP && watchKeys.up) increasedStrength else normalStrength
    }

}