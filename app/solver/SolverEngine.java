package solver;

import solver.engine.ConstraintsEngine;
import solver.engine.DataEngine;
import solver.engine.proxy.*;
import utils.ListenerEngine;

public class SolverEngine extends ListenerEngine<SolverEngineListener> {


    // Attributs
    private final DataEngine data ;


    private final ConstraintsEngine constraintEngine;


    // Proxy Objects
        // RealConstraints
        // Model
        private SolverModelProxy model;
        private SolverModelProxyListener modelListener;


// Constructeurs

    public SolverEngine(SolverModelProxy model) {
        this.model = model;
        this.data = new DataEngine(this);
        this.constraintEngine = new ConstraintsEngine(this);
    }

    public SolverEngine() {
        this(new SolverModelProxy());
    }

    public SolverEngine(SolverModelProxy model, SolverEngineListener listener) {
        this(model);
        addListener(listener);
    }

    public SolverEngine(SolverEngineListener listener) {
        this(new SolverModelProxy(), listener);
    }



// Getter

    public SolverModelProxy getModel() {
        return model;
    }

    public ConstraintsEngine getConstraintEngine() {
        return constraintEngine;
    }

    public DataEngine getData() {
        return data;
    }

}
