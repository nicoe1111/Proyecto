package Curso;

import RolAlumno.AlumnoController;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("sessionCursoController")
@SessionScoped
public class SessionCursoController implements Serializable{
    
    private List<Curso> items = null;
    private Curso selected = null;
    
    @EJB
    CursoFacade ejbCurso;
    
    @PostConstruct
    private void init(){
        items = ejbCurso.findAll();
    }

    public List<Curso> getItems() {
        return items;
    }

    public void setItems(List<Curso> items) {
        this.items = items;
    }

    public Curso getSelected() {
        if(selected==null){
            selected = new Curso();
        }
        return selected;
    }

    public void setSelected(Curso selected) {
        this.selected = selected;
    }
}