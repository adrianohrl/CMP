package tech.adrianohrl.stile.control.dao.production.io;

import tech.adrianohrl.stile.exceptions.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.dao.production.ChartDAO;
import tech.adrianohrl.stile.control.dao.production.ChartSizeDAO;
import tech.adrianohrl.stile.control.model.production.io.ChartsReader;
import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.model.production.ChartSize;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ChartsReaderDAO extends ChartsReader {
    
    private final EntityManager em;
    private final List<Chart> registeredCharts = new ArrayList<>();

    public ChartsReaderDAO(EntityManager em) {
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
        ChartSizeDAO sizeDAO = new ChartSizeDAO(em);
        for (Chart chart : getReadEntities()) {
            if (!chartDAO.isRegistered(chart)) {
                for (ChartSize size : chart.getSizes()) {
                    sizeDAO.create(size);
                }
                chartDAO.create(chart);
                registeredCharts.add(chart);
            }
        }
    }

    public List<Chart> getRegisteredCharts() {
        return registeredCharts;
    }

    @Override
    public Iterator<Chart> iterator() {
        return registeredCharts.iterator();
    }
    
}
