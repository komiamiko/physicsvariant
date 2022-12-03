package model

interface Force {
    fun apply(box: Box): BoxDelta
}