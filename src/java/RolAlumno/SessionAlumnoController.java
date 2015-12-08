package RolAlumno;

import Rol.Alumno;
import Rol.RolFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;

@Named("sessionAlumnoController")
@SessionScoped
public class SessionAlumnoController implements Serializable{
    
    private List<Alumno> items = null;
    private Alumno selected = null;
    
    @EJB
    RolFacade ejbRol;
    
    @PostConstruct
    private void init(){
        items = ejbRol.getAlumnos();
    }
    
    public Alumno getSelected() {
         if(selected==null){
            selected=new Alumno();
        }
        return selected;
    }
    
    public void setSelected(Alumno selected) {
        this.selected = selected;
    }
    
    public List<Alumno> getItems() {
        return items;
    }
    
    public void setItems(List<Alumno> Items) {
        this.items = Items;
    }
}