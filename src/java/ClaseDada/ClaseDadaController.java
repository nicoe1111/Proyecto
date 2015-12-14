/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ClaseDada;

import Asistencia.Asistencia;
import Asistencia.AsistenciaFacade;
import Curso.Curso;
import Curso.CursoFacade;
import Materia.Materia;
import Materia.MateriaFacade;
import Rol.Alumno;
import Rol.RolFacade;
import Rol.TipoRol;
import Session.LoginMB;
import Usuario.Usuario;
import Usuario.util.JsfUtil;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named("claseDadaController")
@ViewScoped
public class ClaseDadaController implements Serializable{
    
    private ClaseDada claseDada = null;
    private List<Curso> cursos = new ArrayList<>();
    private List<Asistencia> asistencias = new ArrayList<>();
    private Asistencia asistencia;
    private String temaDado;
    private Boolean isAsistio;
    private Date fecha;
    private ClaseDada selected;
    private int fechaSeleccionada;
    private List<String> years;
    private List<String> semestres=Arrays.asList("Primer Semestre", "Segundo Semestre", "Tercer Semestre", "Cuarto Semestre", "Quinto Semestre", "Sexto Semestre");
    private List<Curso> cursosTree = new ArrayList<Curso>();
    private int actualYear = Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    private List<Curso> cursosSeleccionados = new ArrayList<>();
    
    @EJB
            ClaseDadaFacade ejbClaseDada;
    @EJB
            CursoFacade ejbCurso;
    
    public List<Curso> getCursosSeleccionados() {
        return cursosSeleccionados;
    }
    
    public void setCursosSeleccionados(List<Curso> cursosSeleccionados) {
        this.cursosSeleccionados = cursosSeleccionados;
    }
    
    public ClaseDada getClaseDada() {
        if(claseDada == null){
            claseDada = new ClaseDada();
        }
        return claseDada;
    }
    
    public ClaseDada getSelected() {
        return selected;
    }
    
    public void setSelected(ClaseDada selected) {
        this.selected = selected;
    }
    
    public void setClaseDada(ClaseDada claseDada) {
        this.claseDada = claseDada;
    }
    
    public int getFechaSeleccionada() {
        return fechaSeleccionada;
    }
    
    
    public void setFechaSeleccionada(int fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }
    
    public List<String> getYears() {
        years = loadyears();
        return years;
    }
    
    public void setYears(List<String> years) {
        this.years = years;
    }
    
    public int getActualYear() {
        return actualYear;
    }
    
    public void setActualYear(int actualYear) {
        this.actualYear = actualYear;
    }
    
    private List<String> loadyears(){
        List<String> years = new ArrayList();
        for(int i=(Integer.valueOf(actualYear)-5); i<=(Integer.valueOf(actualYear)+5) ;i++){
            years.add(String.valueOf(i));
        }
        return years;
    }
    
    public List<Curso> getCursos() {
        if(getUserLog().hasRol("Administrativo") || getUserLog().hasRol("Administrador")){
            return ejbCurso.findAll();
        }else if(getUserLog().hasRol("Docente")){
            return ejbCurso.getCursoDocente(getUserLog().getRolDocente().getIdRol());
        }
        return new ArrayList<>();
    }
    
    public List<ClaseDada>obtenerCasesDadas(Curso cursoSelec){
        List<ClaseDada> clasesDadasCursos = new ArrayList<>();
        if(cursoSelec != null){
            clasesDadasCursos = ejbClaseDada.obtenerClasesDadasIdCurso(cursoSelec.getIdCurso());
            Collections.sort(clasesDadasCursos, new Comparator<ClaseDada>() {
                public int compare(ClaseDada o1, ClaseDada o2) {
                    return o2.getFecha().compareTo(o1.getFecha());
                }
            });
        }
        return clasesDadasCursos;
    }
    
    public List<Alumno> obtenerAlumnosClaseDada(){
        return claseDada.getCurso().getAlumnos();
    }
    
    private Usuario userLog;
    
    public void getUserSession(){
        LoginMB login = new LoginMB();
        userLog = new Usuario();
        userLog = login.getUsuarioLogeado();
    }
    
    public Usuario getUserLog() {
        getUserSession();
        return userLog;
    }
    
    
    @EJB
            AsistenciaFacade ejbAsistencia;
    public List<Asistencia> asistenciasClaseDada(ClaseDada claseDada){
        List<Asistencia> listAsistencia = new ArrayList<>();
        if(claseDada != null){
            listAsistencia = ejbAsistencia.getAsistenciaClaseDada(claseDada.getIdClaseDada());
        }
        return listAsistencia;
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
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        seteoAsistenciasFalse();
        claseDada.setAsistencias(asistencias);
        claseDada.setTemaDado(temaDado);
        claseDada.setFecha(fecha);
        if(claseDada.getFecha() == null || claseDada.getAsistencias().isEmpty()){
            JsfUtil.addErrorMessage("No fue posible crear la clase faltan ingresar datos");
        }else{
            ejbClaseDada.create(claseDada);
            JsfUtil.addSuccessMessage("Se creo la clase y sus asistencias correctamente");
        }
        return "ClaseDada.xhtml";
    }
    
    public void seteoAsistenciasFalse(){
        Boolean atributoFalse;
        List<Asistencia> listAsistenciaFalse = new ArrayList<>();
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
            for (Asistencia listAsistenciaFalse1 : listAsistenciaFalse) {
                asistencias.add(listAsistenciaFalse1);
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
    
    public void setModificarAsistencia(Asistencia asistencia, Boolean asistio){
        asistencia.setIsPresente(asistio);
    }
    
    public void updateClaseDada(){
        if(selected == null){
            JsfUtil.addErrorMessage("No fue posible modificar la clase faltan ingresar datos");
        }else{
            ejbClaseDada.edit(selected);
            JsfUtil.addSuccessMessage("Se modifico la clase y sus asistencias correctamente");
        }
        
    }
    
    private TreeNode selectedNode;
    private TreeNode root;
    @EJB
    private MateriaFacade ejbMateria;
    
    public TreeNode getSelectedNode() {
        return selectedNode;
    }
    
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    
    public TreeNode getRoot() {
        return root;
    }
    
    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    public void onNodeSelectTree(NodeSelectEvent event){
        cursosSeleccionados.clear();
        cursosSeleccionados = obtenerCursos();
    }
    
    public List<Alumno> obtenerAlumnosCurso(Curso curso){
       return curso.getAlumnos();
    }
    
    public String getAsistencias(Alumno alumnoSelec){
        alumnoSelec.getAsistencias();
        String texto = "";
        int cantClasesDadas = 0;//cursosSeleccionados.get(0).getClasesDadas().size();
       List<Asistencia> asistencias =  ejbClaseDada.obtenerAsistenciaAlumno(alumnoSelec.getIdRol());
       int asistio = 0;
        for (Asistencia asistencia : asistencias) {
            if(asistencia.isIsPresente() && asistencia.getAlumno().getIdRol() == alumnoSelec.getIdRol()){
                asistio++;
                cantClasesDadas++;
            }
        }
        int noAsistio = cantClasesDadas - asistio;
        texto = "Cantidad de clases dadas: " + cantClasesDadas + "    Asistencias: " + asistio + "    Inasistencias: " + noAsistio;
        return texto;
    }
    
    public List<Curso> obtenerCursos(){
        List<String> semestres = new ArrayList<>();
        //List<String> cursos = new ArrayList<>();
        List<Curso> listCursosSemestre = new ArrayList<>();
        ArrayList<ArrayList<String>> cursosString = new ArrayList<ArrayList<String>>();
        ArrayList<String> datosCursosString = null;
            if(!selectedNode.getData().toString().equals("Root")){
                if(selectedNode.getChildCount() > 0){
                    JsfUtil.addErrorMessage("Seleccione una materia");
                    cursosSeleccionados.clear();
                    return cursosSeleccionados;
                }else{
                    datosCursosString = new ArrayList<>();
                    datosCursosString.add(selectedNode.getParent().getData().toString());// lugar 0
                    datosCursosString.add(selectedNode.getData().toString());              //lugar 1
                    cursosString.add(datosCursosString);
                }
            }
        
        List<Materia> materiaCurso = new ArrayList<>();
        //paso la lista de cursosString a objeto curso pero tengo que traer la materia primero
        for (int i = 0; i < cursosString.size(); i++) {
            materiaCurso = ejbMateria.findByNombreSemestre(cursosString.get(i).get(1), cursosString.get(i).get(0));
            if(getUserLog().hasRol("Administrativo") || getUserLog().hasRol("Administrador")){
                listCursosSemestre =  mergeListaCursos(listCursosSemestre, ejbCurso.getCursosSemestreNombreAnio(cursosString.get(i).get(0), fechaSeleccionada, materiaCurso.get(0).getNombre()));
            }else if(getUserLog().hasRol("Docente")){
                listCursosSemestre =  mergeListaCursos(listCursosSemestre, ejbCurso.getCursosSemestreNombreAnioDocente(cursosString.get(i).get(0), fechaSeleccionada, materiaCurso.get(0).getNombre(), userLog.getRolDocente().getIdRol()));
            }
        }
        return listCursosSemestre;
    }
    
    public List<Curso> mergeListaCursos(List<Curso> cursosSemestreTotal, List<Curso> cursosSemestre){
        if(cursosSemestre != null){
            for (int i = 0; i < cursosSemestre.size(); i++) {
                cursosSemestreTotal.add(cursosSemestre.get(i));
            }
        }
        return cursosSemestreTotal;
    }
    
    @PostConstruct
    public void init() {
        fechaSeleccionada = actualYear;
        root = new DefaultTreeNode("Root", null);
        TreeNode nodoSemestres = null;
        for (int i = 0; i < semestres.size(); i++) {
            if(getUserLog().hasRol("Administrativo") || getUserLog().hasRol("Administrador")){
                cursosTree = ejbCurso.getCursosSemestreAnio(semestres.get(i), actualYear);
            }else if(getUserLog().hasRol("Docente")){
                cursosTree = ejbCurso.getCursosSemestreAnioDocente(semestres.get(i), actualYear, userLog.getRolDocente().getIdRol());
            }
            if(cursosTree.size() > 0){
                nodoSemestres = new DefaultTreeNode(semestres.get(i), root);
            }
            for (int j = 0; j < cursosTree.size(); j++) {
                nodoSemestres.getChildren().add(new DefaultTreeNode(cursosTree.get(j).getMateria().getNombre()));
            }
        }
    }
    
    public void cursosAnioTree() {
        cursosSeleccionados.clear();
        root = new DefaultTreeNode("Root", null);
        TreeNode nodoSemestres = null;
        for (int i = 0; i < semestres.size(); i++) {
            if(getUserLog().hasRol("Administrativo") || getUserLog().hasRol("Administrador")){
                cursosTree = ejbCurso.getCursosSemestreAnio(semestres.get(i), fechaSeleccionada);
            }else if(getUserLog().hasRol("Docente")){
                cursosTree = ejbCurso.getCursosSemestreAnioDocente(semestres.get(i), fechaSeleccionada, userLog.getRolDocente().getIdRol());
            }
            if(cursosTree.size() > 0){
                nodoSemestres = new DefaultTreeNode(semestres.get(i), root);
            }
            for (int j = 0; j < cursosTree.size(); j++) {
                nodoSemestres.getChildren().add(new DefaultTreeNode(cursosTree.get(j).getMateria().getNombre()));
            }
        }
    }
}
