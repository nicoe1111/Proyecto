/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Usuario;

import General.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Agus
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "ProyectoPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean existeUsuario(String nickname) {
        Query query = em.createQuery("SELECT u.nick FROM Usuario u WHERE u.nick = :nick");
        query.setParameter("nick", nickname);
        return (!query.getResultList().isEmpty());
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean validarUsuario(String nick, String password){
        if(existeUsuario(nick)){
            Usuario user = (Usuario)findByNick(nick).get(0);
            if(user.getNick().equals(nick) && user.getPass().equals(password)){
                return true;
            }
        }
        return false;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Usuario> findByNick(String nickname) {
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.nick = :nick");
        query.setParameter("nick", nickname);
        List<Usuario> users = query.getResultList();
        return users;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<String[]> obtenerNicksYNombres() {
        Query query = em.createQuery("SELECT u.primerNombre, u.primerApellido, u.segundoApellido FROM Usuario u");
        List<String[]> users = query.getResultList();
        return users;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean existeCedula(int cedula) {
        Query query = em.createQuery("SELECT u.cedula FROM Usuario u WHERE u.cedula = :cedula");
        query.setParameter("cedula", cedula);
        return (!query.getResultList().isEmpty());
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Usuario existeCedulaUpdate(int cedula) {
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.cedula = :cedula");
        query.setParameter("cedula", cedula);
        if(query.getResultList().size() > 0){
            return (Usuario) query.getResultList().get(0);
        }
        return null;
    }
}
