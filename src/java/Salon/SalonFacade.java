/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Salon;

import Usuario.*;
import General.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Agus
 */
@Stateless
public class SalonFacade extends AbstractFacade<Salon> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalonFacade() {
        super(Salon.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Salon> findByNombre(String nickname) {
        Query query = em.createQuery("SELECT s FROM Salon s WHERE s.nombreNumero = :nick");
        query.setParameter("nick", nickname);
        List<Salon> users = query.getResultList();
        return users;
    }
}
