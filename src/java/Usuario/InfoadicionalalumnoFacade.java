/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Agus
 */
@Stateless
public class InfoadicionalalumnoFacade extends AbstractFacade<Infoadicionalalumno> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InfoadicionalalumnoFacade() {
        super(Infoadicionalalumno.class);
    }
    
}
