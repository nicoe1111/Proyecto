package Usuario;

import Rol.Docente;
import Rol.TipoRol;
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
    
    /////////  Usuario Docente     ////////////////////////////////////////////////
    
    private List<Usuario> docentes = null;
    private int idDocenteSelected;
    
    public int getIdDocenteSelected() {
        return idDocenteSelected;
    }
    
    public void setIdDocenteSelected(int idDocenteSelected) {
        selected = ejbUsuario.find(idDocenteSelected);
        this.idDocenteSelected = idDocenteSelected;
    }
    
    public List<Usuario> getDocentes() {
        loadDocentes();
        return docentes;
    }
    
    public void loadDocentes(){
        docentes=new ArrayList();
        for(Usuario user : getItems()){
            if(isUserDocente(user)){
                docentes.add(user);
            }
        }
    }
    
    public boolean isUserDocente(Usuario user){
        if(user!=null){
            if(user.getRoles()!=null && !user.getRoles().isEmpty()){
                for(TipoRol rol:user.getRoles()){
                    if(rol instanceof Docente){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void setDocentes(List<Usuario> docentes) {
        this.docentes = docentes;
    }
    
    /////////////////////////////////////////////////////////////////////////////////
}