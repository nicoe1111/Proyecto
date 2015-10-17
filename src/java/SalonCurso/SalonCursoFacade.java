/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalonCurso;

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
public class SalonCursoFacade extends AbstractFacade<SalonCurso> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalonCursoFacade() {
        super(SalonCurso.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SalonCurso> getSalonCursoAñoSemestre(String DiadelaSemana, int anio, String semestre) {
        Query query = em.createQuery("SELECT sc FROM SalonCurso sc WHERE sc.DiadelaSemana = :DiadelaSemana AND sc.curso.anio = :anio AND sc.curso.materia.semestre = :semestre");
        query.setParameter("DiadelaSemana", DiadelaSemana);
        query.setParameter("anio", anio);
        query.setParameter("semestre", semestre);
        List<SalonCurso> users = query.getResultList();
        return users;
    }
}
