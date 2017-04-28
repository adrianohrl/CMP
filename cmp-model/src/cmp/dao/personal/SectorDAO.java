/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.personal;

import cmp.dao.DAO;
import cmp.model.personal.Sector;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class SectorDAO extends DAO<Sector, String> {

    public SectorDAO(EntityManager em) {
        super(em, Sector.class);
    }

    @Override
    public boolean isRegistered(Sector entity) {
        return super.find(entity.getName()) != null;
    }
    
    public boolean isSectorSupervisor(String sectorName, String supervisorName) {
        long counter = (long) em.createQuery("SELECT COUNT(*) "
                + "FROM Sector s JOIN s.supervisor sup "
                + "WHERE s.name = '" + sectorName +"' "
                + "AND sup.name = '" + supervisorName + "'").getSingleResult();
        return counter > 0;
    }
    
}
