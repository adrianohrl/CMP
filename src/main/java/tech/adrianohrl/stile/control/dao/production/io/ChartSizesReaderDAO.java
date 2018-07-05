package tech.adrianohrl.stile.control.dao.production.io;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.dao.production.ChartDAO;
import tech.adrianohrl.stile.control.dao.production.ChartSizeDAO;
import tech.adrianohrl.stile.control.model.production.io.ChartSizesReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.model.production.ChartSize;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ChartSizesReaderDAO extends ChartSizesReader {
    
    private final EntityManager em;
    private final List<Chart> registeredCharts = new ArrayList<>();

    public ChartSizesReaderDAO(EntityManager em) {
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
        ChartDAO chartDAO = new ChartDAO(em);
        for (Chart chart : getReadEntities()) {
            chartDAO.update(chart);
            registeredCharts.add(chart);
        }
    }

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

    public List<Chart> getRegisteredCharts() {
        return registeredCharts;
    }

    @Override
    public Iterator<Chart> iterator() {
        return registeredCharts.iterator();
    }
    
}
