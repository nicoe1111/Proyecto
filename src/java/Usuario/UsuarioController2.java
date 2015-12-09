package Usuario;

import Rol.Alumno;
import Rol.RolFacade;
import Rol.TipoRol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("usuarioController2")
@ViewScoped
public class UsuarioController2 implements Serializable{
    
    @EJB
    private UsuarioFacade ejbUsuario;
    @EJB
    private RolFacade ejbRol;
    private List<Usuario> items = null;
    private Usuario selected = null;
    
    
    
    public List<Usuario> getItems() {
        return items;
    }
    
    public List<String> obtenerNicks(String query){
        List<Usuario> users = ejbUsuario.findAll();
        List<String> nicks=new ArrayList<>();
        for(Usuario u:users){
            if(u.getNick().toLowerCase().startsWith(query) || u.getNick().toUpperCase().startsWith(query)){
                nicks.add(u.getNick());
            }
        }
        return nicks;
    }
    
    public List<String> obtenerNicksYNombres(String query){
        List<String> datos = obtenerNicksNobresConcatenados();
        List<String> result= new ArrayList<>();
        for(String dato : datos){
        if(dato.toLowerCase().startsWith(query) || dato.toUpperCase().startsWith(query)){
                result.add(dato);
            }
        }
        return result;
    }
        
    private List<String> obtenerNicksNobresConcatenados(){
        List<String[]> users = ejbUsuario.obtenerNicksYNombres();
        List<String> datos=new ArrayList<>();
        for(int i=0 ; i<users.size() ; i++){
            String nombre = (((Object[]) users.get(i))[0]).toString();
            String pellido1 = (((Object[]) users.get(i))[1]).toString();
            String apellido2 = (((Object[]) users.get(i))[2]).toString();
//            datos.add(nombre+ " " + pellido1 + " " +apellido2);
            datos.add(nombre+ " " + pellido1);
        }
        return datos;
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
    
    public void loadSelected(int id){
        selected=ejbUsuario.find(id);
    }
    
    public void loadAlumnos(){
        items=new ArrayList();
        List<Alumno> alumnosFacade = ejbRol.getAlumnos();
        for(TipoRol alumno : alumnosFacade){
                items.add(alumno.getUsuario());
        }
    }
    
}