package solver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import solver.engine.proxy.SolverConstraintProxy;
import solver.SolverEngine;
import utils.ElementList;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * The type Constraint abstract.
 *
 * @param <T> the type parameter
 * @param <B> the type parameter
 */
public abstract class ModelConstraintAbstract<T, B extends ModelDataAbstract> {

    /**
     * The Priority.
     */
    @JsonProperty(value = "Priority", defaultValue = "-1")
    protected Integer priority = -1;

    /**
     * The Id.
     */
    @JsonProperty(value = "Id")
    protected String ID = "";

    /**
     * The Value.
     */
    @JsonProperty(value = "Value")
    protected T value;

    /**
     * The Respected.
     */
    @JsonProperty(value ="IsRespected", defaultValue = "null")
    protected Boolean respected = null;


    /**
     * The Real constraints.
     */
    @JsonIgnore
    protected ElementList<SolverConstraintProxy> realConstraints = new ElementList<>();

    @JsonIgnore
    private ElementList<ModelConstraintAbstract<T,B>> linkedEvoluatedConstraints = new ElementList<ModelConstraintAbstract<T,B>>();

    /**
     * The Parent.
     */
    @JsonIgnore
    protected ModelConstraintAbstract<T,B> parent = null;
    private Predicate<ModelDataAbstract> dataCompilerPredicate;

    /**
     * Instantiates a new Evoluated constraint abstract.
     */
    public ModelConstraintAbstract() {}

    /**
     * Instantiates a new Evoluated constraint abstract.
     *
     * @param value the value
     */
    public ModelConstraintAbstract(T value)
    {
        this(-1, UUID.randomUUID().toString(), value);
    }

    /**
     * Instantiates a new Evoluated constraint abstract.
     *
     * @param priority the priority
     * @param value    the value
     */
    public ModelConstraintAbstract(Integer priority, T value)
    {
        this(priority, UUID.randomUUID().toString(), value);
    }

    /**
     * Instantiates a new Evoluated constraint abstract.
     *
     * @param priority the priority
     * @param ID       the id
     * @param value    the value
     */
    public ModelConstraintAbstract(Integer priority, String ID, T value)
    {
        this.priority = priority;
        this.ID = ID;
        this.value = value;
    }

    /**
     * Instantiates a new Constraint abstract.
     *
     * @param priority       the priority
     * @param ID             the id
     * @param value          the value
     * @param objectsInModel the objects in model
     */
    public ModelConstraintAbstract(Integer priority, String ID, T value, ElementList<B> objectsInModel) {
        this.priority = priority;
        this.ID = ID;
        this.value = value;
    }

    /**
     * Gets priority.
     *
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Sets priority.
     *
     * @param priority the priority, the lower is a highest priority keep in the model
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets id.
     *
     * @param ID the id
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Gets respected.
     *
     * @return the respected, calculated only if the model was changed
     */
    public Boolean getRespected() {
        return respected == null ? isActivated() : respected;
    }

    /**
     * Sets respected.
     *
     * @param respected the respected
     */
    public void setRespected(Boolean respected) {
        this.respected = respected;
    }

    /**
     * Is off boolean.
     *
     * @return the boolean
     */
    public Boolean isDeactivated()
    {
        return getRealConstraints().stream().allMatch(constraint -> constraint.isDeactivated());
    }

    /**
     * Is on boolean.
     *
     * @return the boolean
     */
    public Boolean isActivated()
    {
        return getRealConstraints().stream().allMatch(constraint -> constraint.isActivated());
    }

    public void addConstraint(SolverConstraintProxy constraintProxy) {
        realConstraints.add(constraintProxy);
    }

    /**
     * Gets real constraints.
     *
     * @return the real constraints
     */
    public ElementList<SolverConstraintProxy> getRealConstraints() {
        return ElementList.from(realConstraints).join(getLinkedEvoluatedConstraints().stream().flatMap(m-> m.getRealConstraints().stream()));
    }

    /**
     * Sets real constraints.
     *
     * @param realConstraints the real constraints
     */
    protected void setRealConstraints(ElementList<SolverConstraintProxy> realConstraints) {
        this.realConstraints = realConstraints;
    }

    /**
     * Gets linked evoluated constraints.
     *
     * @return the linked evoluated constraints
     */
    public ElementList<ModelConstraintAbstract<T,B>> getLinkedEvoluatedConstraints() {
        return linkedEvoluatedConstraints;
    }

    /**
     * Sets linked evoluated constraints.
     *
     * @param linkedEvoluatedConstraints the linked evoluated constraints
     */
    protected void setLinkedEvoluatedConstraints(ElementList<ModelConstraintAbstract<T,B>> linkedEvoluatedConstraints) {
        this.linkedEvoluatedConstraints = linkedEvoluatedConstraints;
    }

    /**
     * Gets parent.
     *
     * @return the parent
     */
    public ModelConstraintAbstract<T,B> getParent() {
        return parent;
    }

    /**
     * Sets parent.
     *
     * @param parent the parent
     */
    protected void setParent(ModelConstraintAbstract<T,B> parent) {
        this.parent = parent;
    }

    /**
     * Gets constraint name.
     *
     * @return the constraint name
     */
    public abstract String getConstraintName();

    /**
     * Add constraint element list.
     *
     * @param objectInModel the object in model
     * @return the element list, can be the Global object in model for a global constraint or any object extended DataCompiler known by the model
     */
    public abstract void createConstraintFor(SolverEngine solverEngine, ElementList<B> objectInModel);

}
