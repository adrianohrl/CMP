package tech.adrianohrl.stile.control.dao.production.io;

import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.production.VariantDAO;
import tech.adrianohrl.stile.model.production.Variant;
import tech.adrianohrl.stile.model.production.io.VariantsReader;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class VariantsReaderDAO extends ReaderDAO<Variant, VariantDAO> {
    
    public VariantsReaderDAO(EntityManager em) {
        super(em, new VariantDAO(em));
        super.setReader(new VariantsReader());
    }
    
}
