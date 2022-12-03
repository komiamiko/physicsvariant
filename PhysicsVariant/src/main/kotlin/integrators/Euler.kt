package integrators

import Delta
import Integrator
import State

// Forward Euler method
class Euler : Integrator{
    override fun <StateType : State<StateType, DeltaType>, DeltaType : Delta<DeltaType>> step(
        state: StateType,
        by: Double
    ): StateType {
        val dx = state.diff()
        dx.imul(by)
        state.iadd(dx)
        return state
    }
}