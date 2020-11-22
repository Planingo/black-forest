package solver.engine;

import solver.SolverEngine;
import solver.engine.proxy.SolverConstraintProxy;
import solver.model.ModelConstraintAbstract;
import solver.model.ModelDataAbstract;
import utils.ElementList;

import java.lang.reflect.ParameterizedType;

public class ConstraintsEngine {

// Attributs
    private final SolverEngine engine;
    // Contraintes
    private ElementList<ModelConstraintAbstract> modelConstraints = new ElementList<>();
    private ElementList<SolverConstraintProxy> solverConstraints = new ElementList<>();

// Constructeurs
    public ConstraintsEngine(SolverEngine engine)
    {
        this.engine = engine;
    }

// Getter
    public SolverEngine getEngine() {
        return engine;
    }

    public ElementList<ModelConstraintAbstract> getModelConstraints() {
        return modelConstraints;
    }

    public ElementList<SolverConstraintProxy> getSolverConstraints() {
        return solverConstraints;
    }


    public ElementList<SolverConstraintProxy> getSolverConstraintsFor(ModelDataAbstract data){
        return ElementList.from(solverConstraints.stream().filter(c -> c.getDataLinked() == data));
    }

// Ajout de données dans le moteur
    public void addModelConstraints(ModelConstraintAbstract constraints)
    {
        // Ajoute n'importe quel contrainte
        // On lui transmet les données dont il peut s'occuper
        Class<?> tType = (Class<?>) ((ParameterizedType)constraints.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        // le tType contient la classe demandé par la contrainte
        // ex: Module ou Cours
        constraints.createConstraintFor(getEngine(), getEngine().getData().getModelDatas().filter(tType));
        modelConstraints.add(constraints);
    }

    public void addModelConstraints(ElementList<ModelConstraintAbstract> constraints)
    {
        constraints.stream().forEach(this::addModelConstraints);
    }

    public void addSolverConstraint(SolverConstraintProxy constraintProxy) {
        solverConstraints.add(constraintProxy);
    }

}
