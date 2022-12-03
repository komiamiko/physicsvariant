package model

data class CollisionResult(
    val fracMoved: Double,
    val newVx: Double,
    val newVy: Double
): Comparable<CollisionResult> {

    override fun compareTo(other: CollisionResult): Int {
        return fracMoved.compareTo(other.fracMoved)
    }

}