package solver.engine.proxy;

import utils.ElementList;

public interface SolverModelProxyListener {
    public void onActivate(ElementList<SolverConstraintProxy> constraintProxy);
    public void onDeactivate(ElementList<SolverConstraintProxy> constraintProxy);
}
