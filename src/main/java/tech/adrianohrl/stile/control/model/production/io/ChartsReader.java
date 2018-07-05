package tech.adrianohrl.stile.control.model.production.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ChartsReader extends AbstractReader<Chart> {
    
    /** Column Titles **/
    private final static String NAME_COLUMN_TITLE = PropertyUtil.getChartColumnTitle("Name");
    private final static String ABBREVIATION_COLUMN_TITLE = PropertyUtil.getChartColumnTitle("Abbreviation");

    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(ABBREVIATION_COLUMN_TITLE, true));
        return defaultFields;
    }

    @Override
    protected Chart build(List<Field> fields) throws IOException {
        String chartName = Field.getFieldValue(fields, NAME_COLUMN_TITLE);
        String chartAbbreviation = Field.getFieldValue(fields, ABBREVIATION_COLUMN_TITLE);
        return new Chart(chartName, chartAbbreviation);
    }
    
}
