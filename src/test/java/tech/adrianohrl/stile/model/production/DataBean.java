/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.model.production;

import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class DataBean {
    
    private String part;
    private String size;
    private String program;
    private String observation;  
    private List<SubDataBean> variants;

    public DataBean() {
    }

    public DataBean(String part, String size, String program, String observation, List<SubDataBean> variants) {
        this.part = part;
        this.size = size;
        this.program = program;
        this.observation = observation;
        this.variants = variants;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<SubDataBean> getVariants() {
        return variants;
    }

    public void setVariants(List<SubDataBean> variants) {
        this.variants = variants;
    }
    
}
