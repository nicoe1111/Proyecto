package Rol.RolDocente;

import Rol.Docente;
import Usuario.*;
import Rol.TipoRol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("docenteController")
@ViewScoped
public class DocenteController implements Serializable{
    
    @EJB
    private Rol.RolFacade ejbRol;
    
    @EJB
    private UsuarioFacade ejbUsuario;
    
    @PostConstruct
    private void init(){
        loadDocentes();
    }
    
    private List<Usuario> items = null;
    private Usuario selected = null;
    
    private int idSelected;
    
    public int getIdSelected() {
        return idSelected;
    }
    
    public void setIdSelected(int idDocenteSelected) {
        selected = ejbUsuario.find(idDocenteSelected);
        this.idSelected = idDocenteSelected;
    }

    public Usuario getSelected() {
        if(selected==null){
            selected=new Usuario();
        }
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }
    
    public List<Usuario> getItems() {
        loadDocentes();
        return items;
    }
    
    public void setItems(List<Usuario> Items) {
        this.items = Items;
    }
    
    public void loadDocentes(){
        items=new ArrayList();
        List<Docente> docentesFacade = ejbRol.getDocentes();
        for(TipoRol docente : docentesFacade){
                items.add(docente.getUsuario());
        }
    }
}