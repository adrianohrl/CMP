package tech.adrianohrl.stile.control.dao.production.io;

import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.production.ChartDAO;
import tech.adrianohrl.stile.model.production.io.ChartsReader;
import tech.adrianohrl.stile.model.production.Chart;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ChartsReaderDAO extends ReaderDAO<Chart, ChartDAO> {
    
    public ChartsReaderDAO(EntityManager em) {
        super(em, new ChartDAO(em));
        super.setReader(new ChartsReader());
    } 
    
}
