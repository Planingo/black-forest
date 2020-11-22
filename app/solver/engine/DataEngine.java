package solver.engine;

import solver.SolverEngine;
import solver.model.ModelDataAbstract;
import utils.ElementList;

public class DataEngine {

// Attributs
    private final SolverEngine engine;
    // Données
    private ElementList<ModelDataAbstract> allDatas = new ElementList<>();


// Constructeurs
    public DataEngine(SolverEngine engine)
    {
        this.engine = engine;
    }

// Getter
    public SolverEngine getEngine() {
        return engine;
    }

    public ElementList<ModelDataAbstract> getModelDatas() {
        return allDatas;
    }

// Ajout de données dans le moteur
    public void addModelData(ModelDataAbstract data)
    {
        // Une donnée peut en cacher une autre (exemple: les cours séparés en deux périodes)
        // On passe donc par la méthode createObjectInModel qui se chargera de les créer et de retourner une liste de DataCompiler
        allDatas.addAll(data.createObjectInModel(getEngine()));
    }

    public void addModelData(ElementList<ModelDataAbstract> data)
    {
        // Ajoute n'importe quel donnée
        data.stream().forEach(this::addModelData);
    }

}
