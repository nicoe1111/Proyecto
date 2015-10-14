
package Mensaje;

import General.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Mensaje> getMensajesRecividos(String desde) {
        Query query = em.createQuery("SELECT m FROM Mensaje m WHERE m.desde = :desde");
        query.setParameter("desde", desde);
        List<Mensaje> msjs = query.getResultList();
        return msjs;
    }
    
    public List<Mensaje> getMensajesEnviados(String para) {
        Query query = em.createQuery("SELECT m FROM Mensaje m WHERE m.para = :para");
        query.setParameter("para", para);
        List<Mensaje> msjs = query.getResultList();
        return msjs;
    }
}
