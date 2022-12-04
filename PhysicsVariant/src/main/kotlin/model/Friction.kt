package model

// only non-realistic friction implemented
class Friction(val strength: Double) {

    fun getValue(downForce: Double): Double {
        return strength
    }

}