/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

/**
 *
 * @author adrianohrl
 */
public enum EfficiencySeriesTypes implements SeriesType<EfficiencySeriesTypes> {
    
    EFFECTIVE_DURATION("Effective Duration", true),
    EXPECTED_DURATION("Expected Duration", true),
    FREE_DURATION("Free Duration", true),
    TOTAL_DURATION("Total Duration", true),
    PRODUCED_QUANTITY("Produced Quantity", false),
    RETURNED_QUANTITY("Returned Quantity", false),
    EFFECTIVE_EFFICIENCY("Effective Efficiency", true),
    TOTAL_EFFICIENCY("Total Efficiency", true);

    private final String name;
    private final boolean realNumber;

    private EfficiencySeriesTypes(String name, boolean realNumber) {
        this.name = name;
        this.realNumber = realNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isRealNumber() {
        return realNumber;
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
