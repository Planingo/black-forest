package solver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import solver.engine.proxy.SolverConstraintProxy;
import solver.SolverEngine;
import utils.ElementList;

public abstract class ModelDataAbstract {

    @JsonIgnore
    private ElementList<SolverConstraintProxy> constraints = new ElementList<SolverConstraintProxy>();

    public ModelDataAbstract() {
    }

    public void addConstraint(SolverConstraintProxy solverConstraintProxy) {
        constraints.add(solverConstraintProxy);
    }

    public ElementList<SolverConstraintProxy> getConstraints() {
        return constraints;
    }

    public void setConstraints(ElementList<SolverConstraintProxy> constraints) {
        this.constraints = constraints;
    }

    // createObjectInModel doit se charger de créer et de retourner une liste de DataCompiler
    // Un dataCompiler peut en cacher un autre (ex: les cours séparés en deux périodes)
    public abstract ElementList<ModelDataAbstract> createObjectInModel(SolverEngine modelProxy);

}

