/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.production.Chart;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ChartDAO extends DAO<Chart, String> {

    public ChartDAO(EntityManager em) {
        super(em, Chart.class);
    }

    @Override
    public boolean isRegistered(Chart entity) {
        return super.find(entity.getName()) != null;
    }
    
}
