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
