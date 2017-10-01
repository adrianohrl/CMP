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
public enum AttendanceSeriesTypes implements SeriesType<AttendanceSeriesTypes> {
    
    TOTAL_QUANTITY("Total Quantity", true);

    private final String name;
    private final boolean realNumber;

    private AttendanceSeriesTypes(String name, boolean realNumber) {
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
