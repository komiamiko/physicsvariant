interface Delta<Self: Delta<Self>> {
    // Make a copy of self
    fun copy(): Self
    // Multiply in place
    fun imul(by: Double)
    // Add in place
    fun iadd(by: Self)
}