package tech.adrianohrl.stile.control.model.production.reports;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public enum AttendanceSeriesTypes implements SeriesType<AttendanceSeriesTypes> {
    
    TOTAL_QUANTITY("Total Quantity");

    private final String name;

    private AttendanceSeriesTypes(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(String name) {
        return this.name.equalsIgnoreCase(name);
    }
    
}
