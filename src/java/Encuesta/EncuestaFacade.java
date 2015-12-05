/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encuesta;

import General.AbstractFacade;
import java.util.Calendar;
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
public class EncuestaFacade extends AbstractFacade<Encuesta> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EncuestaFacade() {
        super(Encuesta.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean crearEncuesta() {
        int fecha = Calendar.getInstance().get(Calendar.YEAR);
        Query query = em.createQuery("INSERT encuesta (fecha) VALUES (:fecha)");
        query.setParameter("fecha", fecha);
        return (!query.getResultList().isEmpty());
    }
}
