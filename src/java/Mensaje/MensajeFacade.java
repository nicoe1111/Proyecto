
package Mensaje;

import General.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MensajeFacade extends AbstractFacade<Mensaje> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public MensajeFacade() {
        super(Mensaje.class);
    }
}
