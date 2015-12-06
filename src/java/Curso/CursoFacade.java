/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Curso;

import General.AbstractFacade;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author Agus
 */
@Stateful
public class CursoFacade extends AbstractFacade<Curso> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public CursoFacade() {
        super(Curso.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Curso> getCursosSemestreAnio(String semestre, int anio) {
        Query query = em.createQuery("SELECT c FROM Curso c WHERE c.materia.semestre = :semestre AND c.anio= :anio");
        query.setParameter("semestre", semestre);
        query.setParameter("anio", anio);
        List<Curso> users = query.getResultList();
        return users;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Curso> getCursosSemestre(String semestre) {
        Query query = em.createQuery("SELECT c FROM Curso c WHERE c.materia.semestre = :semestre");
        query.setParameter("semestre", semestre);
        List<Curso> users = query.getResultList();
        return users;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Curso> getCursosAnio(int anio) {
        Query query = em.createQuery("SELECT c FROM Curso c WHERE c.anio= :anio");
        query.setParameter("anio", anio);
        List<Curso> users = query.getResultList();
        return users;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Curso> getCursosSemestreNombreAnio(String semestrecurs, int aniocurs, String materiacurs) {
        Query query = em.createQuery("SELECT c FROM Curso c WHERE c.materia.semestre = :semestre AND c.anio = :anio AND c.materia.nombre = :materia");
        query.setParameter("semestre", semestrecurs);
        query.setParameter("anio", aniocurs);
        query.setParameter("materia", materiacurs);
        List<Curso> users = query.getResultList();
        return users;
    }
    
}
