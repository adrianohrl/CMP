package tech.adrianohrl.stile.control.model.production.io;

import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.model.production.ChartSize;
import tech.adrianohrl.stile.util.PropertyUtil;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl@tech)
 */
public class ChartSizesReader extends AbstractReader<Chart> {
    
    /** Column Titles **/
    private final static String CHART_COLUMN_TITLE = PropertyUtil.getChartSizeColumnTitle("Chart");
    private final static String SIZE_COLUMN_TITLE = PropertyUtil.getChartSizeColumnTitle("Size");

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(CHART_COLUMN_TITLE, true));
        defaultFields.add(new StringField(SIZE_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Chart build(List<Field> fields) throws IOException {
        String chartName = Field.getFieldValue(fields, CHART_COLUMN_TITLE);
        boolean containsChart = contains(chartName);
        String sizeName = Field.getFieldValue(fields, SIZE_COLUMN_TITLE);
        Chart chart = !containsChart ? getChart(chartName) : get(chartName);
        if (chart == null) {
            throw new IOException(chartName + " chart is not registered yet!!!");
        }
        ChartSize size = getSize(sizeName, chart.getSizes().size());
        if (chart.getSizes().contains(size)) {
            return null;
        }
        chart.getSizes().add(size);
        return !containsChart ? chart : null;
    }
    
    protected Chart getChart(String name) {
        return new Chart(name, name);
    }
    
    protected ChartSize getSize(String name, int cardinal) {
        return new ChartSize(name, cardinal);
    }
    
    protected boolean contains(String name) {
        for (Chart chart : getReadEntities()) {
            if (name.equalsIgnoreCase(chart.getName())) {
                return true;
            }
        }
        return false;
    }
    
    protected Chart get(String name) {
        for (Chart chart : getReadEntities()) {
            if (name.equalsIgnoreCase(chart.getName())) {
                return chart;
            }
        }
        return null;
    }
    
}
