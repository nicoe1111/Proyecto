/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ClaseDada;

import Asistencia.Asistencia;
import Curso.Curso;
import Curso.CursoFacade;
import Rol.Alumno;
import Rol.RolFacade;
import Rol.TipoRol;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named("claseDadaController")
@ViewScoped
public class ClaseDadaController implements Serializable{
    
    private ClaseDada claseDada = null;
    private List<Curso> cursos = new ArrayList<>();
    private List<Asistencia> asistencias = new ArrayList<Asistencia>();
    private Asistencia asistencia;
    private String temaDado;
    private Boolean isAsistio;
    private Date fecha;
    
    @EJB
            ClaseDadaFacade ejbClaseDada;
    @EJB
            CursoFacade ejbCurso;
    
    public ClaseDada getClaseDada() {
        if(claseDada == null){
            claseDada = new ClaseDada();
        }
        return claseDada;
    }
    
    public void setClaseDada(ClaseDada claseDada) {
        this.claseDada = claseDada;
    }
    
    public List<Curso> getCursos() {
        cursos = ejbCurso.findAll();
        return cursos;
    }
    
    public List<Alumno>obtenerAlumnos(int id){
        Curso curso = ejbCurso.find(id);
        List<ClaseDada> clasesDadasCursos = ejbClaseDada.obtenerClasesDadasIdCurso(curso.getIdCurso());
        return curso.getAlumnos();
        
    }
    
    private int cursoSeleccionado;
    private String fechaString;
    
    public void setCursoSeleccionado(int id){
        cursoSeleccionado = id;
        claseDada.setCurso(ejbCurso.find(id));
    }
    
    public int getCursoSeleccionado() {
        return cursoSeleccionado;
    }
    
    public void onDateSelect(SelectEvent event) throws ParseException {
        Date fecha = ((Date)event.getObject());
        setFecha(fecha);
        DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        setFechaString(formatter.format(fecha));
    }
    
    public Boolean getIsAsistio() {
        return isAsistio;
    }
    
    public void setIsAsistio(Boolean isAsistio) {
        this.isAsistio = isAsistio;
    }
    @EJB
    RolFacade ejbRol;
    public void setAlumnoAsistio(int id){
        if(isAsistio){
            asistencia = new Asistencia();
            TipoRol alumno = new Alumno();
            alumno = ejbRol.find(id);
            asistencia.setAlumno((Alumno)alumno);
            asistencia.setClaseDada(claseDada);
            asistencia.setIsPresente(true);
            asistencias.add(asistencia);
        }else{
            for (int i = 0; i < asistencias.size(); i++) {
                if(asistencias.get(i).getAlumno().getIdRol() == id){
                    asistencias.remove(asistencias.get(i));
                }
            }
        }
    }
    
    public String getFechaString() {
        return fechaString;
    }
    
    public void setFechaString(String fechaSeleccionada) {
        this.fechaString = fechaSeleccionada;
    }
    
    public String crearClaseDada() throws ParseException{
        DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        seteoAsistenciasFalse();
        claseDada.setAsistencias(asistencias);
        claseDada.setTemaDado(temaDado);
        claseDada.setFecha(fecha);
        ejbClaseDada.create(claseDada);
        return "ClaseDada.xhtml";
    }
    
    public void seteoAsistenciasFalse(){
        Boolean atributoFalse;
        List<Asistencia> listAsistenciaFalse = new ArrayList<Asistencia>();
        if(claseDada != null){
            for (int i = 0; i < claseDada.getCurso().getAlumnos().size(); i++) {
                atributoFalse = false;
                for (int j = 0; j < asistencias.size(); j++) {
                    if(claseDada.getCurso().getAlumnos().get(i).getIdRol() == asistencias.get(j).getAlumno().getIdRol()){
                        atributoFalse = false;
                        j = asistencias.size();
                    }else{
                        atributoFalse = true;
                    }
                }
                if(atributoFalse || asistencias.isEmpty()){
                    asistencia = new Asistencia();
                    asistencia.setAlumno(claseDada.getCurso().getAlumnos().get(i));
                    asistencia.setIsPresente(false);
                    asistencia.setClaseDada(claseDada);
                    listAsistenciaFalse.add(asistencia);
                }
            }
            ///agrego a la lista de asistencias true las asistencias en false////
            for (int i = 0; i < listAsistenciaFalse.size(); i++) {
                asistencias.add(listAsistenciaFalse.get(i));
            }
        }
    }
    
    public Asistencia getAsistencia() {
        if(asistencia == null){
            asistencia = new Asistencia();
        }
        return asistencia;
    }
    
    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }
    
    public List<Asistencia> getAsistencias() {
        return asistencias;
    }
    
    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
    
    public String getTemaDado() {
        return temaDado;
    }
    
    public void setTemaDado(String temaDado) {
        this.temaDado = temaDado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
