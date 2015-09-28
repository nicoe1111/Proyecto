package Usuario;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;


@Named("usuarioController2")
@SessionScoped
public class UsuarioController2 implements Serializable {
    
    @EJB
    private UsuarioFacade ejbUsuario;
    @EJB
    private Rol.RolFacade ejbRol;
    private List<Usuario> items = null;
    private Usuario selected = null;
    
    public List<Usuario> getItems() {
        items = ejbUsuario.findAll();
        return items;
    }
    
    public void setItems(List<Usuario> items) {
        this.items = items;
    }
    
    public Usuario getSelected() {
        if(selected==null){
            selected = new Usuario();
        }
        return selected;
    }
    
    public void setSelected(Usuario selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        ejbUsuario.edit(selected);
        updateItems();
    }
    
    public void update(int id){
        Usuario u = ejbUsuario.find(id);
        ejbUsuario.edit(u);
        updateItems();
    }
    
    public void deleteSelected() {
        ejbUsuario.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        Usuario u = ejbUsuario.find(id);
        ejbUsuario.remove(u);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        ejbUsuario.create(selected);
        updateItems();
        selected = null;
    }
    
    private void updateItems(){
        items=ejbUsuario.findAll();
    }
    
}