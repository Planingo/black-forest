package solver.engine.proxy;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import utils.ElementList;
import utils.ListenerEngine;

public class SolverModelProxy extends ListenerEngine<SolverModelProxyListener> {
    private Model model;

    SolverModelProxyListener listener;
    public SolverModelProxy(Model model) {
        this.model = model;
    }

    public SolverModelProxy() {
        this(new Model("Choco Generator"));
    }


    private Model getModel() {
        return model;
    }

    public void activate(SolverConstraintProxy constraints) {
        activate(ElementList.from(constraints));
    }

    public void activate(ElementList<SolverConstraintProxy> constraints) {
        constraints.stream().forEach(c -> c.deactivate());
        getListeners().stream().forEach(l -> l.onActivate(ElementList.from(constraints)));
    }

    public void deactivate(SolverConstraintProxy constraints) {
        deactivate(ElementList.from(constraints));
    }

    public void deactivate(ElementList<SolverConstraintProxy> constraints) {
        constraints.stream().forEach(c -> c.deactivate());
        getListeners().stream().forEach(l -> l.onDeactivate(ElementList.from(constraints)));
    }

    protected Boolean unpost(Constraint constraint) {
        Boolean good = false;
        try {
            if ( constraint.getStatus() != Constraint.Status.FREE
                    && ( constraint.getStatus() == Constraint.Status.POSTED
                    || constraint.getStatus() == Constraint.Status.REIFIED ) )
            {
                getModel().unpost(constraint);
                good = true;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            good = false;
        }
        finally
        {
            return good;
        }

    }

    protected Boolean post(Constraint constraint) {
        Boolean good = false;
        try {
            // Si la contrainte n'a pas un status libre, alors on la déconnecte du modèle
            // avant de la reconneter
            if (constraint.getStatus() != Constraint.Status.FREE)
                getModel().unpost(constraint);

            if (constraint.getStatus() == Constraint.Status.REIFIED)
                getModel().unpost(constraint);

            // Si la contrainte n'est pas postée, alors on la post
            if (constraint.getStatus() != Constraint.Status.POSTED) {
                // CalGenerator passait par contrainte.post()
                getModel().post(constraint);
                good = true;
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
            good = false;
        }
        finally
        {
            return good;
        }
    }

    public IntVar intVar(String name, int... intvalues)
    {
        return getModel().intVar(name, intvalues);
    }
}
