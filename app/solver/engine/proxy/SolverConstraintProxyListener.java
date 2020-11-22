package solver.engine.proxy;

public interface SolverConstraintProxyListener {
    public Boolean onCalculatedIsActivated(SolverConstraintProxy data);
    public Boolean onCalculatedIsDeactivated(SolverConstraintProxy data);
    public void onActivate(SolverConstraintProxy data);
    public void onDeactivate(SolverConstraintProxy data);
}
