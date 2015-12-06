package RolAlumno;

import Rol.Alumno;
import Rol.RolFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("alumnoController")
@ViewScoped
public class AlumnoController implements Serializable{
    
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