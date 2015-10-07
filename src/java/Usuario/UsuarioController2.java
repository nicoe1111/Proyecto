package Usuario;

import TipoRol.Docente;
import TipoRol.RolFacade;
import TipoRol.TipoRol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("usuarioController2")
@SessionScoped
public class UsuarioController2 implements Serializable{
    
    @EJB
    private UsuarioFacade ejbUsuario;
    @EJB
    private RolFacade ejbRol;
    private List<Usuario> items = null;
    private Usuario selected = null;
    
    public List<Usuario> getItems() {
        items = ejbUsuario.findAll();
        return items;
    }
    
    public List<String> obtenerNicks(String query){
        List<Usuario> users = ejbUsuario.findAll();
        List<String> nicks=new ArrayList<>();
        for(Usuario u:users){
            if(u.getNick().toLowerCase().startsWith(query)){
                nicks.add(u.getNick());
            }
        }
        return nicks;
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
        selected = null;
    }
    
    public void update(int id){
        Usuario u = ejbUsuario.find(id);
        ejbUsuario.edit(u);
        updateItems();
        selected = null;
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
    
    /////filtrossssss/////////
    
    private List<Usuario> filteredUsers;
    
    public List<Usuario> getFilteredUsers() {
        return filteredUsers;
    }
    
    public void setFilteredUsers(List<Usuario> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }
    
    //////////////////////////
    
    public void loadSelected(int id){
        selected=ejbUsuario.find(id);
    }
    
}