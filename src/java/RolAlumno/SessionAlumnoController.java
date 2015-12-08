package RolAlumno;

import Curso.Curso;
import Rol.Alumno;
import Rol.RolFacade;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    
    public List<Curso> getSelectedCursosOrderByYear(){
        List<Curso> list = selected.getCursos();
        Collections.sort(list, new Comparator<Curso>() {
            @Override 
            public int compare(Curso c1, Curso c2) {
                return c2.getAnio() - c1.getAnio(); // de mayor a menor
            }
        });
        return list;
    }
}