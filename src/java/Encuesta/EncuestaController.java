package Encuesta;

import Curso.Curso;
import Curso.CursoFacade;
import Materia.Materia;
import Materia.MateriaFacade;
import Pregunta.Pregunta;
import Pregunta.PreguntaFacade;
import RespuestaPregunta.RespuestaPregunta;
import RespuestaPregunta.RespuestaPreguntaFacade;
import Usuario.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@Named("encuestaController")
@ViewScoped
public class EncuestaController implements Serializable{
    
    @EJB
    private EncuestaFacade ejbEncuesta;
    private List<Encuesta> items = null;
    private Encuesta selected = null;
    private List<Curso> cursosTree = new ArrayList<Curso>();
    private int actualYear = Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    private List<String> years = loadyears();
    private List<String> semestres=Arrays.asList("Primer Semestre", "Segundo Semestre", "Tercer Semestre", "Cuarto Semestre", "Quinto Semestre", "Sexto Semestre");
    private List<Integer> selectIdsPreguntas = new ArrayList<>();
    private int fechaSeleccionada;
    String mensaje = "";
    
    public int getFechaSeleccionada() {
        return fechaSeleccionada;
    }
    
    public void setFechaSeleccionada(int fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public List<String> getYears() {
        return years;
    }
    
    public void setYears(List<String> years) {
        this.years = years;
    }
    
    public List<String> getSemestres() {
        return semestres;
    }
    
    public void setSemestres(List<String> semestres) {
        this.semestres = semestres;
    }
    
    public int getActualYear() {
        return actualYear;
    }
    
    public void setActualYear(int actualYear) {
        this.actualYear = actualYear;
    }
    
    public List<Curso> getCursosTree() {
        return cursosTree;
    }
    
    public void setCursosTree(List<Curso> cursosTree) {
        this.cursosTree = cursosTree;
    }
    
    public List<Integer> getSelectIdsPreguntas() {
        return selectIdsPreguntas;
    }
    
    public void setSelectIdsPreguntas(List<Integer> selectIdsPreguntas) {
        this.selectIdsPreguntas = selectIdsPreguntas;
    }
    
    public List<Encuesta> getItems() {
        items = ejbEncuesta.findAll();
        return items;
    }
    
    private List<String> loadyears(){
        List<String> years = new ArrayList();
        for(int i=(Integer.valueOf(actualYear)-5); i<=(Integer.valueOf(actualYear)+5) ;i++){
            years.add(String.valueOf(i));
        }
        return years;
    }
    public void setItems(List<Encuesta> items) {
        this.items = items;
    }
    
    public Encuesta getSelected() {
        if(selected==null){
            selected = new Encuesta();
        }
        return selected;
    }
    
    public void setSelected(Encuesta selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        ejbEncuesta.edit(selected);
        updateItems();
        selected = null;
    }
    
    public void update(int id){
        Encuesta e = ejbEncuesta.find(id);
        ejbEncuesta.edit(e);
        updateItems();
        selected = null;
    }
    
    public void deleteSelected() {
        ejbEncuesta.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        Encuesta p = ejbEncuesta.find(id);
        ejbEncuesta.remove(p);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        ejbEncuesta.create(selected);
        updateItems();
        selected = null;
    }
    
    private void updateItems(){
        items = ejbEncuesta.findAll();
    }
    
    /////filtrossssss/////////
    
    private List<Encuesta> filteredEncuesta;
    
    public List<Encuesta> getFilteredEncuesta() {
        return filteredEncuesta;
    }
    
    public void setFilteredEncuestas(List<Encuesta> filteredEncuesta) {
        this.filteredEncuesta = filteredEncuesta;
    }
    
    //////////////////////////
    
    public void loadSelected(int id){
        selected=ejbEncuesta.find(id);
    }
    
    private TreeNode[] selectedNode;
    private TreeNode root;
    @EJB
    private CursoFacade ejbCurso;
    
    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Root", null);
        TreeNode nodoSemestres = null;
        for (int i = 0; i < semestres.size(); i++) {
            cursosTree = ejbCurso.getCursosSemestreAnio(semestres.get(i), actualYear);
            if(cursosTree.size() > 0){
                nodoSemestres = new DefaultTreeNode(semestres.get(i), root);
            }
            for (int j = 0; j < cursosTree.size(); j++) {
                nodoSemestres.getChildren().add(new DefaultTreeNode(cursosTree.get(j).getMateria().getNombre()));
            }
        }
    }
    
    public void cursosAnioTree() {
        root = new DefaultTreeNode("Root", null);
        TreeNode nodoSemestres = null;
        for (int i = 0; i < semestres.size(); i++) {
            cursosTree = ejbCurso.getCursosSemestreAnio(semestres.get(i), fechaSeleccionada);
            if(cursosTree.size() > 0){
                nodoSemestres = new DefaultTreeNode(semestres.get(i), root);
            }
            for (int j = 0; j < cursosTree.size(); j++) {
                nodoSemestres.getChildren().add(new DefaultTreeNode(cursosTree.get(j).getMateria().getNombre()));
            }
        }
    }
    
    public TreeNode getRoot() {
        return root;
    }
    
    public TreeNode[] getSelectedNode() {
        return selectedNode;
    }
    
    public void setSelectedNode(TreeNode[] selectedNode) {
        this.selectedNode = selectedNode;
    }
    
    public void addIdsPregunta(int idPregunta){
        
        selectIdsPreguntas.add(idPregunta);
    }
    
    public void DeleteIdsPregunta(int idPregunta){
        for (int i = 0; i < selectIdsPreguntas.size(); i++) {
            if(selectIdsPreguntas.get(i) == idPregunta){
                selectIdsPreguntas.remove(i);
            }
        }
    }
    
    public Boolean preguntaSelect(int idPregunta){
        for (int i = 0; i < selectIdsPreguntas.size(); i++) {
            if(selectIdsPreguntas.get(i) == idPregunta){
                return true;
            }
        }
        return false;
    }
    @EJB
            RespuestaPreguntaFacade ejbRespuestaPregunta;
    public String createEncuesta() throws IOException{
        List<RespuestaPregunta> respuestasPreguntas =new ArrayList<>();
        mensaje = "";
        getSelected();
        selected.setFecha(actualYear);
        selected.setPreguntas(obtenerPreguntas());
        List<Curso> cursos = obtenerCursos();
        if(!cursos.isEmpty()){
            if(!selected.getPreguntas().isEmpty() && selected.getFecha() != 0){
                selected.setCursos(cursos);
                for(Curso curso: cursos){
                    if(curso.getRespPregunta().isEmpty()){
                        /// obtengo todos los alumnos de curso para mandarle las respuestasPreguntas
                        if(curso.getAlumnos().isEmpty()){
                            JsfUtil.addErrorMessage("No se creo las encuestas el curso "+ curso.getMateria().getNombre() + " no tiene alumnos asociados");
                        }
                        curso.setEncuesta(selected);
                        for (int i = 0; i < curso.getAlumnos().size(); i++) {
                            for (int j = 0; j < selected.getPreguntas().size(); j++) {
                                RespuestaPregunta respuestaPregunta = new RespuestaPregunta();
                                respuestaPregunta.setCurso(curso);
                                respuestaPregunta.setAlumno(curso.getAlumnos().get(i));
                                respuestaPregunta.setEncuesta(selected);
                                respuestaPregunta.setContesto(false);
                                respuestaPregunta.setPregunta(selected.getPreguntas().get(j));
                                respuestasPreguntas.add(respuestaPregunta);
                            }
                            //curso.setRespPregunta(respuestasPreguntas);
                        }
                    }else{
                        JsfUtil.addErrorMessage("No fue posible crear las encuestas verifique que no exista encuesta asociada al curso " + curso.getMateria().getNombre());
                    }
                    selected.setRespPregunta(respuestasPreguntas);
                }
                if(!selected.getRespPregunta().isEmpty()){
                    ejbEncuesta.edit(selected);
                    JsfUtil.addSuccessMessage("Se creo la encuesta correctamente");
                    return "ContenedorEncuesta.xhtml";
                }
            }else{
                JsfUtil.addErrorMessage("No  fue posible realizar la encuesta verifique los datos ingresados");
                return "ContenedorEncuesta.xhtml";
            }
        }else{
            JsfUtil.addErrorMessage("No fue posible crear las encuestas faltan ingresar datos");
            return "ContenedorEncuesta.xhtml";
        }
        return "ContenedorEncuesta.xhtml";
    }
    @EJB
    private PreguntaFacade ejbPregunta;
    public List<Pregunta> obtenerPreguntas(){
        List<Pregunta> preguntas = new ArrayList<>();
        List<Pregunta> listPregunta = ejbPregunta.findAll();
        for (int i = 0; i < selectIdsPreguntas.size(); i++) {
            for (int j = 0; j < listPregunta.size(); j++) {
                if(listPregunta.get(j).getIdPregunta() == selectIdsPreguntas.get(i)){
                    preguntas.add(listPregunta.get(j));
                }
            }
        }
        return preguntas;
    }
    
    @EJB
    private MateriaFacade ejbMateria;
    
    public List<Curso> obtenerCursos(){
        List<String> semestres = new ArrayList<>();
        //List<String> cursos = new ArrayList<>();
        List<Curso> listCursosSemestre = new ArrayList<>();
        ArrayList<ArrayList<String>> cursosString = new ArrayList<ArrayList<String>>();
        ArrayList<String> datosCursosString = null;
        //separo lo que es curso de semestre
        for (int i = 0; i < selectedNode.length; i++) {
            if(!selectedNode[i].getData().toString().equals("Root")){
                if(selectedNode[i].getChildCount() > 0){
                    semestres.add(selectedNode[i].getData().toString());
                }else{
                    datosCursosString = new ArrayList<>();
                    datosCursosString.add(selectedNode[i].getParent().getData().toString());// lugar 0
                    datosCursosString.add(selectedNode[i].getData().toString());              //lugar 1
                    cursosString.add(datosCursosString);
                }
            }
        }
        //elimino los cursos que estan en los semestres seleccionados y anio
        for (int i = 0; i < semestres.size(); i++) {
            for (int j = 0; j < cursosString.size(); j++) {
                if(semestres.get(i).equals(cursosString.get(j).get(0))){
                    cursosString.remove(cursosString.get(j));
                }
            }
        }
        
        //paso los semestres a lista de objeto cursos
        for (int i = 0; i < semestres.size(); i++) {
            //recorro todos los hijos y se los agrego a la lista cursosString
            listCursosSemestre =  mergeListaCursos(listCursosSemestre, ejbCurso.getCursosSemestreAnio(semestres.get(i), actualYear));
        }
        
        List<Materia> materiaCurso = new ArrayList<>();
        //paso la lista de cursosString a objeto curso pero tengo que traer la materia primero
        for (int i = 0; i < cursosString.size(); i++) {
            materiaCurso = ejbMateria.findByNombreSemestre(cursosString.get(i).get(1), cursosString.get(i).get(0));
            listCursosSemestre =  mergeListaCursos(listCursosSemestre, ejbCurso.getCursosSemestreNombreAnio(cursosString.get(i).get(0), actualYear, materiaCurso.get(0).getNombre()));
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
    
    /////////////////////////////////GRAFICA/////////////////////////////////////////////////////
    private LineChartModel lineModel1;
    
    public LineChartModel getLineModel1() {
        if(lineModel1 == null){
            
            lineModel1 = new LineChartModel();
            LineChartSeries series = new LineChartSeries();
            series.setLabel("");
            series.set(1 , 1);
            lineModel1.addSeries(series);
        }
        return lineModel1;
    }
    
    private void createLineModels(List<Curso> cursos) {
        if(!cursos.isEmpty()){
            lineModel1 = initLinearModel(cursos);
            lineModel1.setTitle("Cursos:  " + cursos.get(0).getAnio());
            lineModel1.setLegendPosition("e");
            Axis yAxis = lineModel1.getAxis(AxisType.Y);
            int Max_y = 6;
            yAxis.setMin(0);
            yAxis.setMax((int)Max_y);
            yAxis.setLabel("PUNTAJES");
            Axis XAxis = lineModel1.getAxis(AxisType.X);
            XAxis.setLabel("PREGUNTAS");
        }
    }
    
    int n = 0;
    private LineChartModel initLinearModel(List<Curso> cursos) {
        Boolean entro = false;
        List<String> alumnosPendiente = new ArrayList<>();
        graficaPreguntas.clear();
        alumnosPendientesEncuesta.clear();
        List<Pregunta> listPreguntas = new ArrayList<>();
        for (int i = 0; i < cursos.size(); i++) {
            listPreguntas.clear();
            if(i == 0){
                n = i;
            }else{
                n = i-1;
            }
            if(cursos.get(i).getEncuesta() != null){
                
                if(cursos.get(i).getEncuesta().getIdEncuesta() == cursos.get(n).getEncuesta().getIdEncuesta()){
                    
                    LineChartSeries series = new LineChartSeries();
                    series.setLabel(cursos.get(i).getDocente().getUsuario().getPrimerNombre() + " " + cursos.get(i).getDocente().getUsuario().getPrimerApellido()+ " - " +cursos.get(i).getMateria().getNombre());
                    int key = 1;
                    boolean bool;
                    List<RespuestaPregunta> listRPreguntas = new ArrayList<>();
                    
                    for (int j = 0; j < cursos.get(i).getRespPregunta().size(); j++) {
                        if(cursos.get(i).getRespPregunta().get(j).isContesto()){
                            if(listRPreguntas.size() < cursos.get(i).getRespPregunta().size()){
                                float puntaje = 0;
                                int cantPreg = 0;
                                for (int k = 0; k < cursos.get(i).getRespPregunta().size(); k++) {
                                    bool =  false;
                                    if(cursos.get(i).getRespPregunta().get(k).isContesto()){
                                        if(cursos.get(i).getRespPregunta().get(j).getPregunta().getIdPregunta() == cursos.get(i).getRespPregunta().get(k).getPregunta().getIdPregunta()){
                                            
                                            for (RespuestaPregunta listRPregunta : listRPreguntas) {
                                                if(listRPregunta.getIdRespuestaPregunta() == cursos.get(i).getRespPregunta().get(k).getIdRespuestaPregunta()){
                                                    bool= true;
                                                }
                                            }
                                            if(!bool){
                                                puntaje = puntaje + cursos.get(i).getRespPregunta().get(k).getPuntaje();
                                                cantPreg++;
                                                listRPreguntas.add(cursos.get(i).getRespPregunta().get(k));
                                            }
                                            if(!graficaPreguntas.contains(cursos.get(i).getRespPregunta().get(j).getPregunta().getPregunta())){
                                                graficaPreguntas.add(cursos.get(i).getRespPregunta().get(j).getPregunta().getPregunta());
                                            }
                                        }
                                    }
                                    
                                }
                                if(!listPreguntas.contains(cursos.get(i).getRespPregunta().get(j).getPregunta())){
                                    listPreguntas.add(cursos.get(i).getRespPregunta().get(j).getPregunta());
                                    series.set(key, (puntaje / cantPreg));
                                    key++;
                                    entro = true;
                                }
                                
                            }
                        }else{
                            if(!alumnosPendiente.contains(cursos.get(i)+ "" + cursos.get(i).getRespPregunta().get(j).getAlumno().getIdRol())){
                                alumnosPendientesEncuesta.add("Curso: " + cursos.get(i).getMateria().getNombre()+ " - " +
                                        cursos.get(i).getRespPregunta().get(j).getAlumno().getUsuario().getPrimerNombre() +
                                        " " + cursos.get(i).getRespPregunta().get(j).getAlumno().getUsuario().getPrimerApellido());
                                alumnosPendiente.add(cursos.get(i)+ "" + cursos.get(i).getRespPregunta().get(j).getAlumno().getIdRol());
                            }
                        }
                    }
                    lineModel1.addSeries(series);
                }
            }else{
                LineChartSeries series = new LineChartSeries();
                series.setLabel( cursos.get(i).getMateria().getNombre() + "  Sin encuesta");
                series.set(1 , 1);
                lineModel1.addSeries(series);
                entro = true;
            }
        }
        if(!entro){
            lineModel1.clear();
            LineChartSeries series = new LineChartSeries();
            series.setLabel("Ningun alumno ha contestado");
            series.set(1 , 1);
            lineModel1.addSeries(series);
        }
        return lineModel1;
    }
    
    private List<String> alumnosPendientesEncuesta = new ArrayList<>();
    
    public List<String> getAlumnosPendientesEncuesta() {
        return alumnosPendientesEncuesta;
    }
    
    public void setAlumnosPendientesEncuesta(List<String> alumnosPendientesEncuesta) {
        this.alumnosPendientesEncuesta = alumnosPendientesEncuesta;
    }
    
    
    public void onNodeSelectTree(NodeSelectEvent event){
        List<Curso> listCurso = obtenerCursos();
        if(listCurso.size() > 0){
            lineModel1.clear();
            createLineModels(listCurso);
        }
    }
    
    private List<String> graficaPreguntas = new ArrayList<>();
    
    public List<String> getGraficaPreguntas() {
        return graficaPreguntas;
    }
}
