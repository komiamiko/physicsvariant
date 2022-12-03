interface Integrator {
    // Perform one time step of an explicit method
    // It is allowed to destroy the original
    // Returned object has the state after
    // Returned object may be the same as the original
    fun <StateType: State<StateType, DeltaType>, DeltaType: Delta<DeltaType>> step(state: StateType, by: Double): StateType
}