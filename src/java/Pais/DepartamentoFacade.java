/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pais;

import Departamento.Departamento;
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
public class DepartamentoFacade extends AbstractFacade<Departamento> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentoFacade() {
        super(Departamento.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Departamento> findNombre(String nombrePais){
        Query query = em.createQuery("SELECT d FROM Departamento d WHERE d.nombre = :nombre");
        query.setParameter("nombre", nombrePais);
        List<Departamento> depto = query.getResultList();
        return depto;
        
    }
    
}
