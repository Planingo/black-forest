package solver.engine.proxy;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.util.ESat;
import solver.model.ModelConstraintAbstract;
import solver.model.ModelDataAbstract;
import utils.ListenerEngine;

public class SolverPropagatorProxy<T extends Variable> extends ListenerEngine<SolverPropagatorProxyListener<T>> {
    private ModelDataAbstract dataLinked;

    private ModelConstraintAbstract parent;

    private Propagator<T> propagator;

    private boolean activated;


    public SolverPropagatorProxy(ModelConstraintAbstract parent, ModelDataAbstract dataLinked, T... vars) {
        this.dataLinked = dataLinked;
        this.parent = parent;
        this.propagator = new Propagator<T>(vars) {
            @Override
            public void propagate(int evtmask) throws ContradictionException {
                for (SolverPropagatorProxyListener<T> l : getListeners()) {
                    l.propagate(SolverPropagatorProxy.this, evtmask);
                }
            }

            @Override
            public ESat isEntailed() {
                ESat returnValue = null;
                for (SolverPropagatorProxyListener<T> l : getListeners()) {
                    returnValue = l.isEntailed(SolverPropagatorProxy.this);
                }
                return returnValue;
            }
        };
    }

    public SolverPropagatorProxy(ModelConstraintAbstract parent, ModelDataAbstract dataLinked, SolverPropagatorProxyListener<T> listener, T... vars) {
        this(parent, dataLinked, vars);
        addListener(listener);
    }


    public ModelDataAbstract getDataLinked() {
        return dataLinked;
    }

    public ModelConstraintAbstract getParent() {
        return parent;
    }

    public Propagator<T> getPropagator() {
        return propagator;
    }

    public void activate() {
        activated = true;
    }

    public void deactivate() {
        activated = false;
    }

    public boolean getActivated()
    {
        return activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isDeactivated() {
        return !activated;
    }

}
