/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Curso;

import Materia.Materia;
import Materia.MateriaController;
import RolDocente.DocenteController;
import Usuario.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named("cursoController")
@ViewScoped
public class CursoController implements Serializable {
    
    @EJB
    private CursoFacade ejbCurso;
    
    private List<Curso> items;
    private Curso selected;
    
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
    
    public void updateSelected(){
        beforCreate();
        if(!itemsContainsMateria()){
            ejbCurso.edit(selected);
            JsfUtil.addSuccessMessage("El curso se ha editado correctamente");
        }else {
            JsfUtil.addErrorMessage("Ya existe un curso con ese nombre y ese año");
        }
        updateItems();
    }
    
    public void update(int id){
        Curso u = ejbCurso.find(id);
        ejbCurso.edit(u);
        updateItems();
    }
    
    public void deleteSelected() {
        beforeDelete();
        ejbCurso.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        Curso u = ejbCurso.find(id);
        ejbCurso.remove(u);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        beforCreate();
        if(!itemsContainsMateria()){
            ejbCurso.create(selected);
            updateItems();
            JsfUtil.addSuccessMessage("El curso se ha creado correctamente");
        }else {
            JsfUtil.addErrorMessage("Ya existe un curso con ese nombre y ese año");
        }
        vaciarControllersSelecteds();
    }
    
    private void updateItems(){
        items=ejbCurso.findAll();
    }
    
    public void loadSelected(int id){
        selected=ejbCurso.find(id);
    }
    
    private boolean itemsContainsMateria(){
        for(Curso c: items){
            if(c.getMateria().getNombre().equals(selected.getMateria().getNombre()) && c.getAnio() == selected.getAnio() && selected.getIdCurso() != c.getIdCurso()){
                return true;
            }
        }
        return false;
    }
    
    @Inject
    private DocenteController docenteController;
    
    @Inject
    private MateriaController materiaController;
    
    private void beforeDelete(){
        selected.setAlumnos(new ArrayList());
        selected.setEncuesta(null);
        selected.setSalonesCurso(new ArrayList());
        selected.setInstanciasEvaluaciones(new ArrayList());
        selected.setClasesDadas(new ArrayList());
        selected.setDocente(null);
        selected.setMateria(null);
        ejbCurso.edit(selected);
    }
    
    public void beforCreate(){
        selected.setAnio(Calendar.getInstance().get(Calendar.YEAR));
        selected.setDocente(docenteController.getSelected().getRolDocente());
        selected.setMateria(materiaController.getSelected());
    }
    
    public void vaciarControllersSelecteds(){
        setSelected(null);
        docenteController.setSelected(null);
        docenteController.setIdSelected(0);
        materiaController.setSelected(null);
        materiaController.setSelectedNode(null);
    }
    
    public void cargarControllersSelecteds(){
        docenteController.setIdSelected(selected.getDocente().getIdRol());
        materiaController.setSelected(selected.getMateria());
    }
    
    private int CursoSelectedID;
    
    public int getCursoSelectedID() {
        return CursoSelectedID;
    }
    
    public void setCursoSelectedID(int CursoSelectedID) {
        Curso c = ejbCurso.find(CursoSelectedID);
        setSelected(c);
        this.CursoSelectedID = CursoSelectedID;
    }
    
    
    //arbol
    private List<String> semestres=Arrays.asList("Primer Semestre", "Segundo Semestre", "Tercer Semestre", "Cuarto Semestre", "Quinto Semestre", "Sexto Semestre");
    
    private TreeNode root;
    
    private TreeNode[] selectedNodes;
    
    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }
    
    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }
    
    public TreeNode getRoot() {
        
        root = new DefaultTreeNode(crearChanchada("root"), null);
        for(String semestre:semestres){
            if(verificarSemestre(semestre)){
                TreeNode semnod = new DefaultTreeNode(crearChanchada(semestre), root);
                semnod.setSelectable(false);
                for(Curso curso:getMateriasSemestre(semestre)){
                    TreeNode matenode = new DefaultTreeNode(curso, semnod);
                }
            }
        }
        if(items.isEmpty()||items==null){
            TreeNode nodo = new DefaultTreeNode("No hay materias registradas", root);
            nodo.setSelectable(false);
        }
        return root;
    }
    
    private Curso crearChanchada(String nombre){
        Materia m =new Materia();
        m.setNombre(nombre);
        Curso nuevo=new Curso();
        nuevo.setMateria(m);
        return nuevo;
    }
    
    List<Curso> getMateriasSemestre(String Semestre){
        List<Curso> materias = new ArrayList();
        for(Curso curso: getItems()){
            if(curso.getMateria().getSemestre().equals(Semestre)) materias.add(curso);
        }
        return materias;
    }
    
    boolean verificarSemestre(String Semestre){
        for(Curso curso: getItems()){
            if(curso.getMateria().getSemestre().equals(Semestre)) return true;
        }
        return false;
    }
    
    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    public String verSelectedNodes(){
        if(selectedNodes!=null){
            String result="";
            for(TreeNode nodo:selectedNodes){
                result = result + " " + nodo.getData().toString();
            }
            return result;
        }
        return"";
    }
    
    private String ver;
    
    public String getVer() {
        ver=verSelectedNodes();
        return ver;
    }
    
    public void setVer(String ver) {
        this.ver = ver;
    }
    
    private TreeNode selectedNode;
    
    public TreeNode getSelectedNode() {
        return selectedNode;
    }
    
    public void setSelectedNode(TreeNode selectedNode) {
        selected = (Curso) selectedNode.getData();
        this.selectedNode = selectedNode;
    }
    
    public boolean verificarNodoInsancia(TreeNode nodo){
        if (nodo instanceof Curso) {
            return true;
        }
        return false;
    }
    ///////////////////////////////
    
}
