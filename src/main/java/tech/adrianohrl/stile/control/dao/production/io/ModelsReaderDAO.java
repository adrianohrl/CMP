package tech.adrianohrl.stile.control.dao.production.io;

import tech.adrianohrl.stile.control.dao.production.ModelDAO;
import tech.adrianohrl.stile.control.dao.production.ModelPhaseDAO;
import tech.adrianohrl.stile.model.production.io.ModelsReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPhase;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.dao.production.ChartDAO;
import tech.adrianohrl.stile.control.dao.production.CollectionDAO;
import tech.adrianohrl.stile.control.dao.production.FamilyDAO;
import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Family;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ModelsReaderDAO extends ModelsReader {
    
    private final EntityManager em;
    private final List<Model> registeredModels = new ArrayList<>();

    public ModelsReaderDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        register();
    }

    @Override
    public void readFile(InputStream in) throws IOException {
        super.readFile(in);
        register();
    }
    
    private void register() {
        ModelDAO modelDAO = new ModelDAO(em);
        ModelPhaseDAO phaseDAO = new ModelPhaseDAO(em);
        for (Model model : getReadEntities()) {
            if (!modelDAO.isRegistered(model)) {
                for (ModelPhase phase : model.getPhases()) {
                    phaseDAO.create(phase);
                }
                modelDAO.create(model);
                registeredModels.add(model);
            }
        }
    }
    
    @Override
    protected Family getFamily(String name) {
        FamilyDAO familyDAO = new FamilyDAO(em);
        return familyDAO.find(name);
    }
    
    @Override
    protected Collection getCollection(String name) {
        CollectionDAO collectionDAO = new CollectionDAO(em);
        return collectionDAO.find(name);
    }
    
    @Override
    protected Chart getChart(String name) {
        ChartDAO chartDAO = new ChartDAO(em);
        return chartDAO.find(name);
    }

    public List<Model> getRegisteredModels() {
        return registeredModels;
    }

    @Override
    public Iterator<Model> iterator() {
        return registeredModels.iterator();
    }
    
}
