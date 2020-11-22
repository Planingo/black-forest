package model.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.chocosolver.solver.variables.IntVar;
import solver.SolverEngine;
import solver.model.ModelDataAbstract;
import utils.DateTimeHelper;
import utils.ElementList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Module extends ModelDataAbstract
{

    private Integer idModule;
    private Integer nbWeekOfModule;
    private Integer nbHourOfModule;
    private List<Integer> listIdModulePrerequisite;
    private List<Integer> listIdModuleOptional;
    private List<Cours> cours;

    @JsonIgnore
    private IntVar debut;
    @JsonIgnore
    private IntVar fin;
    @JsonIgnore
    private IntVar lieux;
    @JsonIgnore
    private IntVar coursIdentifier;
    @JsonIgnore
    private IntVar id;
    @JsonIgnore
    private IntVar coursId;
    @JsonIgnore
    private IntVar modulesDuration;
    @JsonIgnore
    private IntVar modulesWorkingDayDuration;
    @JsonIgnore
    private IntVar nbSemaine;
    @JsonIgnore
    private IntVar nbHeure;
    @JsonIgnore
    private Integer occurence;

    public Module()
    {
    }

    public Module(Module module, Integer occurence)
    {
        this.occurence = occurence;
        setIdModule(module.getIdModule());
    }

    @Override
    public ElementList<ModelDataAbstract> createObjectInModel(SolverEngine engine) {
        // Initialisation des variables
        cours.sort(Comparator.comparing(o -> DateTimeHelper.toDays(o.getPeriod().getStart())));
        setNbHourOfModule(cours.stream().mapToInt(c -> c.getRealDuration()).max().getAsInt());

        IntStream.range(0, cours.size()).forEach(i -> cours.get(i).setIdCours(i));

        debut = engine.getModel().intVar("Debut " + getIdModule() + " occurence ", cours.stream().mapToInt(c -> c.getDebut()).toArray());
        fin = engine.getModel().intVar("Fin " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getFin()).toArray());
        lieux = engine.getModel().intVar("Lieu " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getLieu()).toArray());
        coursIdentifier = engine.getModel().intVar("Module " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getCoursIdentifier()).toArray());
        id = engine.getModel().intVar("ID module " + getIdModule() + " occurence " + occurence, getIdModule());
        coursId = engine.getModel().intVar("ID cours " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getIdCours()).toArray());
        occurenceVar = engine.getModel().intVar("Occurence cours " + getIdModule() + " occurence " + occurence, occurence);

        modulesDuration = engine.getModel().intVar("Duration " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getDuration()).toArray());

        modulesWorkingDayDuration =  engine.getModel().intVar("Working day Duration " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getWorkingDuration()).toArray());

        nbSemaine = engine.getModel().intVar("Nb Semaine " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getNbSemaine()).toArray());

        nbHeure = engine.getModel().intVar("Nb Heure " + getIdModule() + " occurence " + occurence, coursDuModule.stream().mapToInt(c -> c.getNbHeure()).toArray());

    }

    public Module(Integer idModule, List<Integer> listIdModulePrerequisite, List<Integer> listIdModuleOptional, List<Cours> listClasses, Integer nbSemainePrevu, Integer nbHourOfModule)
    {
        this.idModule = idModule;
        this.listIdModulePrerequisite = listIdModulePrerequisite;
        this.listIdModuleOptional = listIdModuleOptional;
        this.nbWeekOfModule = nbSemainePrevu;
        this.nbHourOfModule = nbHourOfModule;
    }


    public Integer getIdModule() {
        return idModule;
    }

    public void setIdModule(Integer idModule) {
        this.idModule = idModule;
    }

    public Integer getNbWeekOfModule() {
        return nbWeekOfModule;
    }

    public void setNbWeekOfModule(Integer nbWeekOfModule) {
        this.nbWeekOfModule = nbWeekOfModule;
    }

    public Integer getNbHourOfModule() {
        return nbHourOfModule;
    }

    public void setNbHourOfModule(Integer nbHourOfModule) {
        this.nbHourOfModule = nbHourOfModule;
    }

    public List<Integer> getListIdModulePrerequisite() {
        return listIdModulePrerequisite;
    }

    public void setListIdModulePrerequisite(List<Integer> listIdModulePrerequisite) {
        this.listIdModulePrerequisite = listIdModulePrerequisite;
    }

    public List<Integer> getListIdModuleOptional() {
        return listIdModuleOptional;
    }

    public void setListIdModuleOptional(List<Integer> listIdModuleOptional) {
        this.listIdModuleOptional = listIdModuleOptional;
    }

    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    public IntVar getDebut() {
        return debut;
    }

    public void setDebut(IntVar debut) {
        this.debut = debut;
    }

    public IntVar getFin() {
        return fin;
    }

    public void setFin(IntVar fin) {
        this.fin = fin;
    }

    public IntVar getLieux() {
        return lieux;
    }

    public void setLieux(IntVar lieux) {
        this.lieux = lieux;
    }

    public IntVar getCoursIdentifier() {
        return coursIdentifier;
    }

    public void setCoursIdentifier(IntVar coursIdentifier) {
        this.coursIdentifier = coursIdentifier;
    }

    public IntVar getId() {
        return id;
    }

    public void setId(IntVar id) {
        this.id = id;
    }

    public IntVar getCoursId() {
        return coursId;
    }

    public void setCoursId(IntVar coursId) {
        this.coursId = coursId;
    }

    public IntVar getModulesDuration() {
        return modulesDuration;
    }

    public void setModulesDuration(IntVar modulesDuration) {
        this.modulesDuration = modulesDuration;
    }

    public IntVar getModulesWorkingDayDuration() {
        return modulesWorkingDayDuration;
    }

    public void setModulesWorkingDayDuration(IntVar modulesWorkingDayDuration) {
        this.modulesWorkingDayDuration = modulesWorkingDayDuration;
    }

    public IntVar getNbSemaine() {
        return nbSemaine;
    }

    public void setNbSemaine(IntVar nbSemaine) {
        this.nbSemaine = nbSemaine;
    }

    public IntVar getNbHeure() {
        return nbHeure;
    }

    public void setNbHeure(IntVar nbHeure) {
        this.nbHeure = nbHeure;
    }

    public List<Module> getIdModuleRequis() {
        return idModuleRequis;
    }

    public void setIdModuleRequis(List<Module> idModuleRequis) {
        this.idModuleRequis = idModuleRequis;
    }

    public List<Module> getIdModuleFacultatif() {
        return idModuleFacultatif;
    }

    public void setIdModuleFacultatif(List<Module> idModuleFacultatif) {
        this.idModuleFacultatif = idModuleFacultatif;
    }
}
