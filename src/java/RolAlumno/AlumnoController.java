package RolAlumno;

import Curso.Curso;
import Curso.CursoController;
import Curso.CursoFacade;
import Rol.Alumno;
import RolDocente.*;
import Rol.Docente;
import Rol.RolFacade;
import Usuario.*;
import Rol.TipoRol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Named("alumnoController")
@ViewScoped
public class AlumnoController implements Serializable{
    
    @Inject
    private CursoController cursoController;
    
    private List<Alumno> items = null;
    private Alumno selected = null;
    
    private Curso cursoSelected;
    
    
    public void cargarAlumnosCurso() {
        cursoSelected = cursoController.getSelected();
//        Session session = factory.openSession();  
//        UserTransaction userTxn = sessionContext.getUserTransaction();
//        
//        List<Alumno> alumnos=null;
//        try {
//            userTxn.begin();
//            alumnos=cursoSelected.getAlumnos();
//            userTxn.commit();
//        } catch (Throwable e) {
//            userTxn.rollback();
//        }
         items =cursoSelected.getAlumnos();
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
    
    @Inject
    private UsuarioController2 usuarioController;
    
    @EJB
    private CursoFacade ejbCurso;
    
    public void agregarAlumnoACurso(){
        if(!existeAlumnoEnLista(usuarioController.getSelected().getRolAlumno(), cursoSelected.getAlumnos())){
            cursoSelected.getAlumnos().add(usuarioController.getSelected().getRolAlumno());
        }
    }
    
    public void quitarAlumnoACurso(){
        if(existeAlumnoEnLista(selected, cursoSelected.getAlumnos())){
            cursoSelected.getAlumnos().remove(selected);
        }
    }
    
    public void guardarCambios(){
        ejbCurso.edit(cursoSelected);
    }
    
    private boolean existeAlumnoEnLista(Alumno alumno, List<Alumno> lista){
        for(Alumno a: lista){
            if(a.getIdRol()==alumno.getIdRol()){
                return true;
            }
        }
        return false;
    }
}