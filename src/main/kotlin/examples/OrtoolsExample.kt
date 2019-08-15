package examples

import com.google.ortools.linearsolver.MPSolver

class OrtoolsExample {
    fun runExample() {
        // Create the linear solver with CLP backend.
        val solver = MPSolver(
            "SimpleLpProgram",
            MPSolver.OptimizationProblemType.GLOP_LINEAR_PROGRAMMING
        )

        // Create the variables x and y.
        val x = solver.makeNumVar(0.0, 1.0, "x")
        val y = solver.makeNumVar(0.0, 2.0, "y")
        println("Number of variables: ${solver.numVariables()}")

        // Create a linear constraint, 0 <= x + y <= 2.
        val ct = solver.makeConstraint(0.0, 2.0, "ct")
        ct.setCoefficient(x, 1.0)
        ct.setCoefficient(y, 1.0)
        println("Number of constraints: ${solver.numConstraints()}")

        // Create the objective function, 3x + y
        val objective = solver.objective()
        objective.setCoefficient(x, 3.0)
        objective.setCoefficient(y, 1.0)
        objective.setMaximization()

        // Solve and get solution.
        solver.solve()
        println("Solution:")
        println("Objective value: ${objective.value()}")
        println("x: ${x.solutionValue()}")
        println("y: ${y.solutionValue()}")
        println("constraint dual: ${ct.dualValue()}")
    }

    companion object {
        init {
            System.loadLibrary("jniortools")
        }
    }
}