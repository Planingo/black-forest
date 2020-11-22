package solver.engine.proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.chocosolver.solver.constraints.Constraint;
import solver.SolverEngine;
import solver.model.ModelConstraintAbstract;
import solver.model.ModelDataAbstract;
import utils.ListenerEngine;


/**
 * The type Constraint proxy.
 */
public class SolverConstraintProxy extends ListenerEngine<SolverConstraintProxyListener> {
    @JsonIgnore
    private ModelConstraintAbstract parent;
    @JsonIgnore
    private Constraint constraint;
    @JsonIgnore
    private SolverEngine solverEngine;
    @JsonIgnore
    private ModelDataAbstract dataLinked = null;


    /**
     * Instantiates a new Constraint origin.
     *
     * @param parent     the parent
     * @param constraint the constraint
     * @param dataLinked the data linked
     * @param model      the model
     */
    public SolverConstraintProxy(SolverEngine model, ModelConstraintAbstract parent, ModelDataAbstract dataLinked, Constraint constraint) {
        this.parent = parent;
        this.constraint = constraint;
        this.solverEngine = model;
        this.dataLinked = dataLinked;
        getSolverEngine().getConstraintEngine().addSolverConstraint(this);
        getParent().addConstraint(this);
        dataLinked.addConstraint(this);
    }

    public SolverConstraintProxy(SolverEngine model, ModelConstraintAbstract parent, ModelDataAbstract dataLinked, Constraint constraint, SolverConstraintProxyListener listener) {
        this(model, parent, dataLinked, constraint);
        addListener(listener);
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public SolverEngine getSolverEngine() {
        return solverEngine;
    }

    /**
     * Sets model.
     *
     * @param solverEngine the model
     */
    public void setSolverEngine(SolverEngine solverEngine) {
        this.solverEngine = solverEngine;
    }

    /**
     * Gets optional data linked.
     *
     * @return the optional data linked
     */
    public ModelDataAbstract getDataLinked() {
        return dataLinked;
    }

    /**
     * Sets optional data linked.
     *
     * @param dataLinked the optional data linked
     */
    public void setDataLinked(ModelDataAbstract dataLinked) {

        this.dataLinked = dataLinked;
        getDataLinked().addConstraint(this);
    }


    /**
     * Gets parent.
     *
     * @return the parent
     */
    public ModelConstraintAbstract getParent() {
        return parent;
    }

    /**
     * Gets priority.
     *
     * @return the priority
     */
    public Integer getPriority()
    {
        return getParent().getPriority();
    }

    /**
     * Gets constraint.
     *
     * @return the constraint
     */
    private Constraint getConstraint() {
        return constraint;
    }

    /**
     * Sets constraint.
     *
     * @param constraint the constraint
     */
    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }


    /**
     * Off boolean.
     *
     * @return the boolean
     */
    protected Boolean deactivate() {
        Boolean good = getSolverEngine().getModel().unpost(getConstraint());
        getListeners().stream().forEach(l -> l.onDeactivate(this));

        return good;
    }

    /**
     * On boolean.
     *
     * @return the boolean
     */
    protected Boolean activate() {
        getListeners().stream().forEach(l -> l.onActivate(this));
        Boolean good = getSolverEngine().getModel().post(getConstraint());

        return good;
    }

    /**
     * Is off boolean.
     *
     * @return the boolean
     */
    public Boolean isDeactivated() {
        Boolean isOff = null;
        isOff = getListeners().stream().anyMatch(l -> l.onCalculatedIsDeactivated(this));
        if (isOff == null)
        {
            isOff = getStatus() != Constraint.Status.POSTED;
        }
        return isOff;
    }

    /**
     * Is on boolean.
     *
     * @return the boolean
     */
    public Boolean isActivated() {
        Boolean isOn = null;
        isOn = getListeners().stream().anyMatch(l -> l.onCalculatedIsActivated(this));
        if (isOn == null)
        {
            isOn = !isDeactivated();
        }
        return isOn;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Constraint.Status getStatus() {
        return constraint.getStatus();
    }
}
