/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.model.production;

/**
 *
 * @author adrianohrl
 */
public enum Sizes {
    
    XS("PP"),
    S("P"),
    M("M"),
    L("G"),
    XL("GG"),
    U("U");
    
    private final String name;
    
    private Sizes(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
