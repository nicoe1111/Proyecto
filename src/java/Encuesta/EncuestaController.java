package Encuesta;

import Curso.Curso;
import Curso.CursoFacade;
import Materia.Materia;
import Materia.MateriaFacade;
import Pregunta.Pregunta;
import Pregunta.PreguntaFacade;
import RespuestaPregunta.RespuestaPregunta;
import RespuestaPregunta.RespuestaPreguntaFacade;
import Rol.Alumno;
import Rol.TipoRol;
import Usuario.Usuario;
import Usuario.UsuarioFacade;
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
import javax.enterprise.context.SessionScoped;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@Named("encuestaController")
@SessionScoped
public class EncuestaController implements Serializable{
    
    @EJB
    private EncuestaFacade ejbEncuesta;
    private List<Encuesta> items = null;
    private Encuesta selected = null;
    private List<Curso> cursosTree = new ArrayList<Curso>();
    private int anioSelected;
    private String actualYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    private List<String> years = loadyears();
    private List<String> semestres=Arrays.asList("Primer Semestre", "Segundo Semestre", "Tercer Semestre", "Cuarto Semestre", "Quinto Semestre", "Sexto Semestre");
    private List<Integer> selectIdsPreguntas = new ArrayList<>();
    String mensaje = "";
    
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
    
    public int getAnioSelected() {
        return anioSelected;
    }
    
    public void setAnioSelected(int anioSelected) {
        this.anioSelected = anioSelected;
    }
    
    public String getActualYear() {
        return actualYear;
    }
    
    public void setActualYear(String actualYear) {
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
            if(anioSelected != 0){
                cursosTree = ejbCurso.getCursosSemestreAnio(semestres.get(i), anioSelected);
            }
            if(cursosTree.size() > 0){
                nodoSemestres = new DefaultTreeNode(semestres.get(i), root);
            }
            for (int j = 0; j < cursosTree.size(); j++) {
                nodoSemestres.getChildren().add(new DefaultTreeNode(cursosTree.get(j).getMateria().getNombre()));
            }
        }
        //cargarAlumnosCurso();
    }
    @EJB
            UsuarioFacade ejbUsuario;
    public void cargarAlumnosCurso(){
        Usuario u1 = new Usuario();
        u1.setCedula(555);
        u1.setNick("555");
        u1.setPass("555");
        Usuario u2 = new Usuario();
        u2.setCedula(666);
        u1.setNick("666");
        u1.setPass("666");
        List<TipoRol> lista1= new ArrayList<>();
        List<TipoRol> lista2= new ArrayList<>();
        Alumno a1 = new Alumno();
        Alumno a2 = new Alumno();
        lista1.add(a1);
        lista2.add(a2);
        u1.setRoles(lista1);
        u2.setRoles(lista2);
        
        a1.setUsuario(u1);
        a2.setUsuario(u2);
        
        List<Alumno> tiporolll= new ArrayList<>();
        Curso c = new Curso();
        tiporolll.add(a1);
        tiporolll.add(a2);
        c.setAlumnos(tiporolll);
        
        for(Alumno a:tiporolll){
            a.getCursos().add(c);
        }
        ejbUsuario.create(u1);
        ejbUsuario.create(u2);
        ejbCurso.edit(c);
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
    private RespuestaPreguntaFacade ejbRespuestaPregunta;
    List<RespuestaPregunta> respuestasPreguntas =new ArrayList<RespuestaPregunta>();
    public void createEncuesta() throws IOException{
        mensaje = "";
        getSelected();
        selected.setFecha(anioSelected);
        selected.setPreguntas(obtenerPreguntas());
        List<Curso> cursos = obtenerCursos();
        selected.setCursos(cursos);
        if(!selected.getPreguntas().isEmpty() && selected.getFecha() != 0){
            for(Curso curso: cursos){
                if(curso.getRespPregunta().isEmpty()){
                    curso.setEncuesta(selected);
                    /// obtengo todos los alumnos de curso para mandarle las respuestasPreguntas
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
                    }
                    curso.setRespPregunta(respuestasPreguntas);
                    if(!curso.getRespPregunta().isEmpty()){
                        ejbCurso.edit(curso);
                        JsfUtil.addSuccessMessage("Se crearon las encuestas correctamente "  + curso.getMateria().getNombre());
                    }else{
                        JsfUtil.addErrorMessage("No se creo las encuestas del curso " + curso.getMateria().getNombre() + " no tiene alumnos asociados");
                    }
                }else{
                    JsfUtil.addErrorMessage("No fue posible crear las encuestas verifique que no exista encuesta asociada al curso " + curso.getMateria().getNombre());
                }
            }
        }else{
            JsfUtil.addErrorMessage("No fue posible crear las encuestas faltan ingresar datos");
        }
    }
    @EJB
    private PreguntaFacade ejbPregunta;
    public List<Pregunta> obtenerPreguntas(){
        List<Pregunta> preguntas = new ArrayList<Pregunta>();
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
        List<Curso> listCursosSemestre = new ArrayList<Curso>();
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
            listCursosSemestre =  mergeListaCursos(listCursosSemestre, ejbCurso.getCursosSemestreAnio(semestres.get(i), anioSelected));
        }
        
        List<Materia> materiaCurso = new ArrayList<Materia>();
        //paso la lista de cursosString a objeto curso pero tengo que traer la materia primero
        for (int i = 0; i < cursosString.size(); i++) {
            materiaCurso = ejbMateria.findByNombreSemestre(cursosString.get(i).get(1), cursosString.get(i).get(0));
            listCursosSemestre =  mergeListaCursos(listCursosSemestre, ejbCurso.getCursosSemestreNombreAnio(cursosString.get(i).get(0), anioSelected, materiaCurso.get(0).getNombre()));
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
        }
        return lineModel1;
    }
    
    private void createLineModels(List<Curso> cursos) {
        
        lineModel1 = initLinearModel(cursos);
        if(!cursos.isEmpty()){
            lineModel1.setTitle("Cursos:  " + cursos.get(0).getAnio());
            lineModel1.setLegendPosition("e");
            Axis yAxis = lineModel1.getAxis(AxisType.Y);
            int Max = 6;
            yAxis.setMin(0);
            yAxis.setMax(Max);
        }
    }
    
    private LineChartModel initLinearModel(List<Curso> cursos) {
        
        for (int i = 0; i < cursos.size(); i++) {
            
            LineChartSeries series = new LineChartSeries();
            series.setLabel(cursos.get(i).getDocente().getUsuario().getPrimerNombre() + " " + cursos.get(i).getDocente().getUsuario().getPrimerApellido()+ " - " +cursos.get(i).getMateria().getNombre());
            int key = 1;
            boolean bool = false;
            List<RespuestaPregunta> listRPreguntas = new ArrayList<RespuestaPregunta>();
            
            for (int j = 0; j < cursos.get(i).getRespPregunta().size(); j++) {
                if(listRPreguntas.size() < cursos.get(i).getRespPregunta().size()){
                    int puntaje = 0;
                    int cantPreg = 0;
                    for (int k = 0; k < cursos.get(i).getRespPregunta().size(); k++) {
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
                            
                        }
                    }
                    series.set(key , (puntaje / cantPreg));
                    key++;
                }
            }
            lineModel1.addSeries(series);
        }
        return lineModel1;
    }
    
    public void onNodeSelectTree(NodeSelectEvent event){
        lineModel1.clear();
        List<Curso> listCurso = filtarCursosEncuesta();
        createLineModels(listCurso);
    }
    
    public List<Curso> filtarCursosEncuesta(){
        List<Curso> listCurso = new ArrayList<Curso>();
        List<Curso> cursos = obtenerCursos();
        for (int i = 0; i < cursos.size(); i++) {
            if(i == 0){
                listCurso.add(cursos.get(i));
            }else if(cursos.get(i).getEncuesta().getIdEncuesta() == cursos.get(i-1).getEncuesta().getIdEncuesta()){
                listCurso.add(cursos.get(i));
            }
        }
        return listCurso;
    }
    
}
