package examples

import lpsolve.*

fun runLpSolveExample() {
    // Create a problem with 4 variables and 0 constraints.
    val lpSolver = LpSolve.makeLp(0, 4)

    // Add constraints.
    lpSolver.strAddConstraint("3 2 2 1", LpSolve.LE, 4.0)
    lpSolver.addConstraint(doubleArrayOf(0.0, 4.0, 3.0, 1.0), LpSolve.GE, 3.0)

    // Set objective function.
    lpSolver.setObjFn(doubleArrayOf(2.0, 3.0, -2.0, 3.0))

    // Solve the problem.
    lpSolver.solve()

    // Print solution.
    println("objective$: ${lpSolver.objective}")
    val solution = lpSolver.ptrVariables
    for ( i in solution.indices) {
        println("value of variable $i is ${solution[i]}")
    }

    // Delete the problem and free memory.
    lpSolver.deleteLp()
}

