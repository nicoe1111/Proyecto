package CasosDeUso;

import Curso.Curso;
import Curso.CursoController;
import Curso.CursoFacade;
import InterfazUtil.SemestreAnioController;
import Rol.Alumno;
import Rol.RolFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("asignarAlumnoCursoCnontroller")
@ViewScoped
public class AsignarAlumnoCursoCnontroller implements Serializable{
    
    @Inject
    private CursoController cursoController;
    
    @EJB
    RolFacade ejbRol;
    
    @EJB
    CursoFacade ejbCurso;
    
    private List<Alumno> alumnosCurso;
    private List<Alumno> allAlumnos;
    
    private Alumno alumnoCursoSelected;
    private Alumno AlumnoSelected;

    public Alumno getAlumnoCursoSelected() {
        return alumnoCursoSelected;
    }

    public void setAlumnoCursoSelected(Alumno alumnoCursoSelected) {
        this.alumnoCursoSelected = alumnoCursoSelected;
    }

    public Alumno getAlumnoSelected() {
        return AlumnoSelected;
    }

    public void setAlumnoSelected(Alumno AlumnoSelected) {
        this.AlumnoSelected = AlumnoSelected;
    }
    
    public List<Alumno> getAlumnosCursoSelected(){
        Curso cursoSelected = cursoController.getSelected();
        alumnosCurso = cursoSelected.getAlumnos();
        return alumnosCurso;
    }
    
    public List<Alumno> getAllAlumnos(){
        allAlumnos = ejbRol.getAlumnos();
        return allAlumnos;
    }
    
    public void agregarAlumnoACurso(){
        if(!existeAlumnoEnLista(AlumnoSelected, alumnosCurso)){
            alumnosCurso.add(AlumnoSelected);
        }
    }
    
    public void quitarAlumnoACurso(){
        if(existeAlumnoEnLista(alumnoCursoSelected, alumnosCurso)){
            alumnosCurso.remove(alumnoCursoSelected);
        }
    }
    
    public void guardarCambios(){
        Curso cursoSelected = cursoController.getSelected();
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