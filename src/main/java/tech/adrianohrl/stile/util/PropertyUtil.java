package tech.adrianohrl.stile.util;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class PropertyUtil {
    
    private static final String COLUMN_TITLE_PREFIX = "import.column.title";
    
    public static String getPersonnelColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("personnel", defaultColumnTitleName);
    }
    
    public static String getMachineColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("machine", defaultColumnTitleName);
    }
    
    public static String getSectorColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("sector", defaultColumnTitleName);
    }
    
    public static String getSubordinateColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("subordinate", defaultColumnTitleName);
    }
    
    public static String getSupervisorColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("supervisor", defaultColumnTitleName);
    }
    
    public static String getChartColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("chart", defaultColumnTitleName);
    }
    
    public static String getChartSizeColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("size", defaultColumnTitleName);
    }
    
    public static String getCollectionColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("collection", defaultColumnTitleName);
    }
    
    public static String getFabricColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("fabric", defaultColumnTitleName);
    }
    
    public static String getFamilyColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("family", defaultColumnTitleName);
    }
    
    public static String getPhaseColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("phase", defaultColumnTitleName);
    }
    
    public static String getModelColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("model", defaultColumnTitleName);
    }
    
    public static String getModelPhaseColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("model.phase", defaultColumnTitleName);
    }
    
    public static String getModelPartColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("model.part", defaultColumnTitleName);
    }
    
    public static String getVariantColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("variant", defaultColumnTitleName);
    }
    
    public static String getModelVariantColumnTitle(String defaultColumnTitleName) {
        return getColumnTitle("model.variant", defaultColumnTitleName);
    }
    
    private static String getColumnTitle(String clazz, String defaultColumnTitleName) {
        String propertyKey = COLUMN_TITLE_PREFIX + "." + clazz + "." + defaultColumnTitleName.toLowerCase();
        return tech.adrianohrl.util.PropertyUtil.getProperty(propertyKey);
    }
    
}
