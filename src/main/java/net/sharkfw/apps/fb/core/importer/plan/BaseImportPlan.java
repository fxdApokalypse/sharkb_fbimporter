package net.sharkfw.apps.fb.core.importer.plan;

import net.sharkfw.apps.fb.core.importer.FBImportException;
import net.sharkfw.apps.fb.core.importer.FBImporter;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BaseImportPlan implements ImportPlan {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseImportPlan.class);

    public class ImportPlanEntry {

        protected FBImporter importer = null;
        protected boolean isFinished;

        public ImportPlanEntry(FBImporter importer) {
            this.importer = importer;
            this.isFinished = false;
        }

        public void markAsFinished() {
            this.isFinished = true;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public FBImporter getImporter() {
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
                        String.format("Missing required importer: '%s' which is required by the importer: '%s'", dependencyName, getName())
                    );
                    LOG.error(ex.getMessage());
                }
                dependencyImporterSteps.add(dependencyImporterStep);
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


    private Map<String, ImportPlanEntry> importers = null;
    private DirectedAcyclicGraph<ImportPlanEntry, DefaultEdge> executionPlanDAG;

    public BaseImportPlan() {
        importers = new HashMap<>();
    }

    @Override
    public void execute() throws FBImportException {
        buildExecutionDAGPlan();
        LOG.info("Executing import plan");
        Iterator<ImportPlanEntry> it = executionPlanDAG.iterator();
        while (it.hasNext()) {
            ImportPlanEntry importStep = it.next();
            importStep.execute();
        }
    }

    @Override
    public void execute(String importerName) throws FBImportException {
        buildExecutionDAGPlan();
        LOG.info(String.format("Try to execute the importer \"%s\"", importerName));

        if (! importers.containsKey(importerName) ) {
            String errorMsg = String.format("The FBImporter \"%s\" doesn't exists", importerName);
            LOG.error(errorMsg);
            throw new FBImportException(errorMsg);
        }


        ImportPlanEntry importStep = importers.get(importerName);
        throw  new NoSuchElementException("Not Yet implemented");
    }

    private void buildExecutionDAGPlan() throws FBImportException {
        LOG.info("Building the execution plan: Creates DAG of import execution steps");
        executionPlanDAG = new DirectedAcyclicGraph<>(DefaultEdge.class);

        LOG.info("Add the import steps to the import execution plan");
        importers.values().forEach(executionPlanDAG::addVertex);

        LOG.info("Add ImporterStep Dependencies");
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

    @Override
    public void add(FBImporter importer) {
        this.importers.put(importer.getName(), this.new ImportPlanEntry(importer));
    }

    @Override
    public void remove(FBImporter importer) {
        this.importers.remove(importer.getName());
    }

    @Override
    public boolean hasImporter(FBImporter importer) {
        return this.importers.containsKey(importer.getName());
    }
}
