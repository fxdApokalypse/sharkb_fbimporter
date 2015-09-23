package net.sharkfw.apps.fb.core.importplan;

import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.core.importer.FBImporterStep;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Base import plan which builds a execution plan for
 * all {@link FBImporterStep} which could be founded in the
 * Spring {@link ApplicationContext}. Per default all {@link FBImporterStep}
 * will be loaded from the package net.sharkfw.apps.fb.core.importer.
 *
 * Furthermore this ImportPlan tries to build a Directed Acyclic Graph out of all importer steps.
 * This DAG ensures that all importers will be executed in the proper order depending
 * on it's dependencies. When a import step called A depends on a import step called B
 * than the B step will be executed before the import step A.
 *
 */
@Component
public class BaseImportPlan implements ImportPlan, ApplicationContextAware {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseImportPlan.class);

    /**
     * A set of required permission which are necessary in order to execute this import plan.
     */
    protected Set<String> requiredPermissions = null;

    /**
     * Placeholder for the import plan entries
     */
    private Map<String, ImportPlanEntry> importers = null;

    /**
     * The DAG which is the actual execution plan.
     */
    private DirectedAcyclicGraph<ImportPlanEntry, DefaultEdge> executionPlanDAG;

    /**
     * The spring application context in order to retrieve the import steps.
     */
    private ApplicationContext ctx;

    /**
     * The retrieved importers.
     */
    private List<FBImporterStep> retrievedImportSteps;

    /**
     * Creates an empty ImportPlan.
     */
    public BaseImportPlan() {
        importers = new HashMap<>();
        requiredPermissions = new HashSet<>();
    }

    @Override
    public void execute() throws FBImportException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        LOG.info("Building the execution plan - creates DAG of import steps");

        retrievedImportSteps = ctx.getBeansOfType(FBImporterStep.class)
            .entrySet()
            .stream()
            .map((bean) -> {
                FBImporterStep importerStep = bean.getValue();
                requiredPermissions.addAll(importerStep.getRequiredPermissions());
                return  importerStep;
            }).collect(Collectors.toList());

        retrieveImportSteps();
        buildExecutionDAGPlan();

        if (!executionPlanDAG.edgeSet().isEmpty()) {
            LOG.info("Executing import plan");
            Iterator<ImportPlanEntry> it = executionPlanDAG.iterator();
            while (it.hasNext()) {
                ImportPlanEntry importStep = it.next();
                importStep.execute();
            }
        }
        stopWatch.stop();

        long importDurationInMS = stopWatch.getTotalTimeMillis();
        LOG.info(String.format("The duration of the import in ms: %s", importDurationInMS));
    }

    @Override
    public void execute(String importerName) throws FBImportException {
        throw  new NoSuchElementException("Not Yet implemented");
    }

    private void buildExecutionDAGPlan() throws FBImportException {

        executionPlanDAG = new DirectedAcyclicGraph<>(DefaultEdge.class);

        if (importers.isEmpty()) {
            LOG.error("No importer steps to add to the execution plan, please check your importer dependencies and permissions");
            return;
        }

        LOG.info("Adds import steps to the import execution plan");
        importers.values().forEach((importPlanEntry) -> {
            LOG.info("Add the import step: " + importPlanEntry.getName());
            executionPlanDAG.addVertex(importPlanEntry);
        });

        LOG.info("Add import step dependencies");
        for (Map.Entry<String, ImportPlanEntry> importStep :  importers.entrySet()) {
            ImportPlanEntry importPlanEntry = importStep.getValue();

            for (ImportPlanEntry dependOnImporter : importPlanEntry.getDependencies()) {
                LOG.info(String.format("Assign the importer \"%s\" as parent to \"%s\"", dependOnImporter.getName(), importPlanEntry.getName()));
                try {
                    executionPlanDAG.addEdge(dependOnImporter, importPlanEntry);
                } catch (IllegalArgumentException ex) {
                    LOG.error(
                        String.format(
                            "Detected a cyclic dependency between the importers: \"%s\" - \"%s\", cyclic dependencies are forbidden",
                            dependOnImporter.getName(), importPlanEntry.getName())
                    );
                }
            }
        }
        LOG.info("Finished building the execution plan");
    }

    protected void retrieveImportSteps() {
        this.importers.clear();
        retrievedImportSteps.stream()
            .filter(this::includeImporter)
            .forEach(this::add);
    }

    protected boolean includeImporter(FBImporterStep fbImporterStep) {
        return true;
    }

    @Override
    public void add(FBImporterStep importer) {
        this.importers.put(importer.getName(), this.new ImportPlanEntry(importer));
    }

    @Override
    public void remove(FBImporterStep importer) {
        this.importers.remove(importer.getName());
    }

    @Override
    public boolean hasImporter(FBImporterStep importer) {
        return this.importers.containsKey(importer.getName());
    }

    @Override
    public Set<String> getRequiredPermissions() {
        return new HashSet<>(requiredPermissions);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    /**
     * Retrieves the spring application context.
     * @return the application context.
     */
    protected ApplicationContext getCtx() {
        return this.ctx;
    }

    /**
     * A Import plan defines a entry in the import
     * execution plan which encapsulates a import step,
     * memorizes if a step is already executed and
     * provides methods for:
     * <ul>
     *     <li>executing a import step</li>
     *     <li>retrieving the dependencies of a import step</li>
     * </ul>
     *
     */
    private class ImportPlanEntry {

        private FBImporterStep importer = null;
        private boolean isFinished;

        public ImportPlanEntry(FBImporterStep importer) {
            this.importer = importer;
            this.isFinished = false;
        }
        public void markAsFinished() {
            this.isFinished = true;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public FBImporterStep getImporter() {
            return this.importer;
        }

        public String getName() {
            return getImporter().getName();
        }

        public Set<ImportPlanEntry> getDependencies() throws FBImportException {
            Set<ImportPlanEntry> dependencyImporterSteps = new HashSet<>();
            for (String dependencyName : getImporter().getDependentImporters()) {
                ImportPlanEntry dependencyImporterStep = importers.getOrDefault(dependencyName, null);
                if (dependencyImporterStep == null) {
                    FBImportException ex = new FBImportException(
                        String.format("Missing required import step: '%s' which is required by the importer step: '%s'", dependencyName, getName())
                    );
                    LOG.error(ex.getMessage());
                } else {
                    dependencyImporterSteps.add(dependencyImporterStep);
                }
            }
            return dependencyImporterSteps;
        }

        public void execute() throws FBImportException {
            if (! isFinished()) {
                try {
                    LOG.info("Execute the importer: " + getName());
                    importer.performImport();
                    markAsFinished();
                } catch (SharkKBException ex) {
                    throw new FBImportException(ex.getMessage(), ex);
                }
            }
        }

    }
}
