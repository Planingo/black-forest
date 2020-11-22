package solver.engine.proxy;

import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.util.ESat;

public interface SolverPropagatorProxyListener<T extends Variable> {
    public void propagate(SolverPropagatorProxy<T> propagatorProxy, int evtmask) throws ContradictionException ;
    public ESat isEntailed(SolverPropagatorProxy<T> propagatorProxy) ;
}
