/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

/**
 *
 * @author adrianohrl
 * @param <T>
 */
public interface ReportSeriesEnum<T extends Enum<T>> extends Comparable<T> {
    
    public abstract String getName();
    
    @Override
    public abstract String toString();
    
    public abstract boolean equals(String name);
    
}
