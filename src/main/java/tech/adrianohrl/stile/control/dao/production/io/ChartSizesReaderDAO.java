package tech.adrianohrl.stile.control.dao.production.io;

import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.production.ChartDAO;
import tech.adrianohrl.stile.control.dao.production.ChartSizeDAO;
import tech.adrianohrl.stile.model.production.io.ChartSizesReader;
import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.model.production.ChartSize;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ChartSizesReaderDAO extends ReaderDAO<Chart, ChartDAO> {

    private final ChartSizesReader chartSizesReader;
    
    public ChartSizesReaderDAO(EntityManager em) {
        super(em, new ChartDAO(em));
        this.chartSizesReader = new Reader();
        super.setReader(chartSizesReader);
    } 
    
    @Override
    protected void register() {
        for (Chart chart : chartSizesReader) {
            dao.update(chart);
            registeredEntities.add(chart);
        }
    }

    private class Reader extends ChartSizesReader {

        @Override
        protected ChartSize getSize(String name, int cardinal) {
            ChartSizeDAO sizeDAO = new ChartSizeDAO(em);
            ChartSize size = new ChartSize(name, cardinal);
            sizeDAO.create(size);
            return size;
        }   

        @Override
        protected Chart getChart(String chartName) {
            ChartDAO chartDAO = new ChartDAO(em);
            return chartDAO.find(chartName);
        }
        
    }
    
}
