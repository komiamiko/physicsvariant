interface State<Self: State<Self, DeltaType>, DeltaType: Delta<DeltaType>> {
    // Make a copy of self
    fun copy(): Self
    // Differentiate
    fun diff(): DeltaType
    // Add in place with clamping
    fun iadd(by: DeltaType)
}