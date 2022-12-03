package integrators

import Delta
import Integrator
import State

// Explicit midpoint method
class RK4 : Integrator{
    override fun <StateType : State<StateType, DeltaType>, DeltaType : Delta<DeltaType>> step(
        state: StateType,
        by: Double
    ): StateType {
        val k1 = state.diff()
        k1.imul(by / 2)
        val x2 = state.copy()
        x2.iadd(k1)
        val k2 = x2.diff()
        k2.imul(by / 2)
        val x3 = state.copy()
        x3.iadd(k2)
        val k3 = x3.diff()
        k3.imul(by)
        val x4 = state.copy()
        x4.iadd(k3)
        val k4 = x4.diff()
        k1.imul(1.0 / 3)
        k2.imul(2.0 / 3)
        k3.imul(1.0 / 3)
        k4.imul(by / 6)
        k1.iadd(k2)
        k1.iadd(k3)
        k1.iadd(k4)
        state.iadd(k1)
        return state
    }
}