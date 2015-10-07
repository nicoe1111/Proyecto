/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rol;

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
public class RolFacade extends AbstractFacade<TipoRol> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(TipoRol.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<TipoRol> findRolUser(int idUser) {
        Query query = em.createQuery("SELECT r FROM TipoRol r WHERE r.usuario.id_user = :id");
        query.setParameter("id", idUser);
        return query.getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Docente> getDocentes() {
        Query query = em.createQuery("SELECT r FROM TipoRol r WHERE TYPE(r) = :tipo");
        query.setParameter("tipo", Docente.class);
        List<? extends TipoRol> docentes =  query.getResultList();
        return  (List<Docente>)docentes;
    }
}
