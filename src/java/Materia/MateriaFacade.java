/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Materia;


import General.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MateriaFacade extends AbstractFacade<Materia> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public MateriaFacade() {
        super(Materia.class);
    }
    
    public List<Materia> findByNombre(String nombremat) {
        Query query = em.createQuery("SELECT m FROM Materia m WHERE m.nombre = :nombre");
        query.setParameter("nombre", nombremat);
        List<Materia> mats = query.getResultList();
        return mats;
    }
    
    public boolean existeMateria(String nombremat) {
        Query query = em.createQuery("SELECT m FROM Materia m WHERE m.nombre = :nombre");
        query.setParameter("nombre", nombremat);
        return query.getResultList().size() > 0;
    }
    
    public boolean existeMateria(String nombremat, int id) {
        Query query = em.createQuery("SELECT m FROM Materia m WHERE m.nombre = :nombre and m.idMateria != :id");
        query.setParameter("nombre", nombremat);
        query.setParameter("id", id);
        return query.getResultList().size() > 0;
    }
    
    public List<String> getAllNombres() {
        Query query = em.createQuery("SELECT m.nombre FROM Materia m");
        return query.getResultList();
    }
    
    public List<Materia> findByNombreSemestre(String nombremat, String semestremat) {
        Query query = em.createQuery("SELECT m FROM Materia m WHERE m.nombre = :nombre AND m.semestre = :semestre");
        query.setParameter("nombre", nombremat);
        query.setParameter("semestre", semestremat);
        List<Materia> mats = query.getResultList();
        return mats;
    }
}
