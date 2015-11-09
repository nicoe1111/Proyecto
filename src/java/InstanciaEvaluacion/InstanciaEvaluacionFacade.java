/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InstanciaEvaluacion;

import Curso.*;
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
public class InstanciaEvaluacionFacade extends AbstractFacade<Evaluacion> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstanciaEvaluacionFacade() {
        super(Evaluacion.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Evaluacion> findInstanciaByCurso(int idCurso) {
        Query query = em.createQuery("SELECT ie FROM InstanciaEvaluacion ie WHERE ie.curso.idCurso = :id");
        query.setParameter("id", idCurso);
        return query.getResultList();
    }
}
