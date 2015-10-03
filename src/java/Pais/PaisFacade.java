/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pais;

import Usuario.Usuario;
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
public class PaisFacade extends AbstractFacade<Pais> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaisFacade() {
        super(Pais.class);
    }
    
     @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Pais> findNombre(String nombrePais){
        Query query = em.createQuery("SELECT p FROM Pais p WHERE p.nombre = :nombre");
        query.setParameter("nombre", nombrePais);
        List<Pais> paises = query.getResultList();
        return paises;
        
    }
}
