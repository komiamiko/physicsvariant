package integrators

import Delta
import Integrator
import State

// Explicit midpoint method
class Midpoint : Integrator{
    override fun <StateType : State<StateType, DeltaType>, DeltaType : Delta<DeltaType>> step(
        state: StateType,
        by: Double
    ): StateType {
        val dx0 = state.diff()
        dx0.imul(by / 2)
        val midx = state.copy()
        midx.iadd(dx0)
        val dx = midx.diff()
        dx.imul(by)
        state.iadd(dx)
        return state
    }
}