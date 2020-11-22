package model.constraint;

import model.module.Module;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import solver.SolverEngine;
import solver.model.ModelConstraintAbstract;
import solver.engine.proxy.*;
import utils.ElementList;

/**
 * The type Max amplitude formation.
 */
public class MaxAmplitudeFormation extends ModelConstraintAbstract<Integer, Module> {

    // début de l'ensemble des modules
    private ElementList<IntVar> debut;

    private SolverPropagatorProxyListener<IntVar> propagatorListener = new SolverPropagatorProxyListener<IntVar>() {
        private ESat isEntailed;

        // Est appelé lors de la recherche par le solveur
        @Override
        public void propagate(SolverPropagatorProxy<IntVar> propagatorProxy, int evtmask) throws ContradictionException {

            if (isActivated())
            {
                // determine le début du premier cours
                int datePremierModule = debut.stream().mapToInt(d -> d.getValue()).min().getAsInt();
                for (IntVar var : propagatorProxy.getPropagator().getVars()) {
                    if ( ((var.getValue() - datePremierModule) / 7) > getValue()) {
                        var.removeValue(var.getValue(), propagatorProxy.getPropagator());
                        isEntailed = ESat.UNDEFINED;
                    } else {
                        isEntailed = ESat.TRUE;
                    }
                }
            }
            else {
                isEntailed = ESat.TRUE;
            }
        }

        // Est appelé lors de la recherche par le solveur
        @Override
        public ESat isEntailed(SolverPropagatorProxy<IntVar> propagatorProxy) {
            return isEntailed;
        }

    };

    @Override
    public String getConstraintName() {
        return getPriority() + "- MaxAmplitudeFormation " + getValue();
    }


    @Override
    public void createConstraintFor(SolverEngine solverEngine, ElementList<Module> objectInModel) {
        // On récupére l'ensemble des Variable Choco du début de chaque module pour calculer l'amplitude au niveau du propagateur
        debut = ElementList.from(objectInModel.stream().map(m -> m.getDebut()));

        for (Module module: objectInModel) {
            SolverPropagatorProxy<IntVar> prop = new SolverPropagatorProxy<IntVar>(this, module, propagatorListener, module.getFin());
            Constraint constraint = new Constraint(getConstraintName() + " - Module Id " + module.getIdModule(), prop.getPropagator());
            new SolverConstraintProxy(solverEngine,
                    this,
                    module,
                    constraint,
                    new SolverConstraintProxyListener() {

                        @Override
                        public Boolean onCalculatedIsActivated(SolverConstraintProxy constraintProxy) {
                            return prop.isActivated();
                        }

                        @Override
                        public Boolean onCalculatedIsDeactivated(SolverConstraintProxy constraintProxy) {
                            return !prop.isActivated();
                        }

                        @Override
                        public void onActivate(SolverConstraintProxy constraintProxy) {
                            prop.activate();
                        }

                        @Override
                        public void onDeactivate(SolverConstraintProxy constraintProxy) {
                            prop.deactivate();
                        }
                    });
        }
    }

}
