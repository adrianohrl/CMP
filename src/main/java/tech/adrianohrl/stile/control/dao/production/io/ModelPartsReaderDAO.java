package tech.adrianohrl.stile.control.dao.production.io;

import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.production.FabricDAO;
import tech.adrianohrl.stile.control.dao.production.ModelDAO;
import tech.adrianohrl.stile.control.dao.production.ModelPartDAO;
import tech.adrianohrl.stile.model.production.Fabric;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPart;
import tech.adrianohrl.stile.model.production.io.ModelPartsReader;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ModelPartsReaderDAO extends ReaderDAO<Model, ModelDAO> {
    
    private final ModelPartsReader modelPartsReader;

    public ModelPartsReaderDAO(EntityManager em) {
        super(em, new ModelDAO(em));
        this.modelPartsReader = new Reader();
        super.setReader(modelPartsReader);
    }
    
    @Override
    protected void register() {
        ModelPartDAO modelPartDAO = new ModelPartDAO(em);
        for (Model model : modelPartsReader) {
            if (dao.isRegistered(model)) {
                for (ModelPart modelPart : model.getParts()) {
                    modelPartDAO.create(modelPart);
                }
                dao.update(model);
                registeredEntities.add(model);
            }
        }
    }
    
    private class Reader extends ModelPartsReader {
        
        @Override
        protected Model getModel(String reference) {
            ModelDAO modelDAO = new ModelDAO(em);
            return modelDAO.find(reference);
        }

        @Override
        protected Fabric getFabric(String name) {
            FabricDAO fabricDAO = new FabricDAO(em);
            return fabricDAO.find(name);
        }

    }
    
}
