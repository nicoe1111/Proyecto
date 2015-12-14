package Materia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import prueba.util.JsfUtil;

@Named("materiaController")
@ViewScoped
public class MateriaController implements Serializable {
    
    @EJB
    private MateriaFacade ejbMateria;
    
    private List<Materia> items;
    private Materia selected;
    private List<String> semestres=Arrays.asList("Primer Semestre", "Segundo Semestre", "Tercer Semestre", "Cuarto Semestre", "Quinto Semestre", "Sexto Semestre");
    
    public List<String> getSemestres() {
        return semestres;
    }
    
    public void setSemestres(List<String> semestres) {
        this.semestres = semestres;
    }
    
    public List<Materia> getItems() {
        items = ejbMateria.findAll();
        return items;
    }
    
    public void setItems(List<Materia> items) {
        this.items = items;
    }
    
    public Materia getSelected() {
        if(selected==null){
            selected = new Materia();
        }
        return selected;
    }
    
    public void setSelected(Materia selected) {
        this.selected = selected;
    }
    
    public void updateSelected(){
        if(!ejbMateria.existeMateria(selected.getNombre(), selected.getIdMateria())){
        ejbMateria.edit(selected);
        updateItems();
        }else JsfUtil.addErrorMessage("Ya existe una materia con ese nombre");
    }
    
    public void update(int id){
        Materia u = ejbMateria.find(id);
        ejbMateria.edit(u);
        updateItems();
    }
    
    public void deleteSelected() {
        ejbMateria.remove(selected);
        updateItems();
        selected = null;
    }
    
    public void delete(int id) {
        Materia u = ejbMateria.find(id);
        ejbMateria.remove(u);
        updateItems();
        selected = null;
    }
    
    public void createSelected(){
        if(!ejbMateria.existeMateria(selected.getNombre())){
            ejbMateria.create(selected);
            updateItems();
            selected = null;
        }else JsfUtil.addErrorMessage("Ya existe una materia con ese nombre");
    }
    
    private void updateItems(){
        items=ejbMateria.findAll();
    }
    
    public void loadSelected(int id){
        selected=ejbMateria.find(id);
    }
    
    //////////////////////////  Arbol  //////////////////////////////////////////
    private TreeNode root;
    
    private TreeNode[] selectedNodes;
    
    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }
    
    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }
    
    public TreeNode getRoot() {
        root = new DefaultTreeNode("Root", null);
        for(String semestre:semestres){
            if(verificarSemestre(semestre)){
                TreeNode semnod = new DefaultTreeNode(semestre, root);
                semnod.setSelectable(false);
                for(Materia mate:getMateriasSemestre(semestre)){
                    TreeNode matenode = new DefaultTreeNode(mate.getNombre(), semnod);
                }
            }
        }
        if(items.isEmpty()||items==null){
            TreeNode nodo = new DefaultTreeNode("No hay materias registradas", root);
            nodo.setSelectable(false);
        }
        return root;
    }
    
    List<Materia> getMateriasSemestre(String Semestre){
        List<Materia> materias = new ArrayList();
        for(Materia mate: getItems()){
            if(mate.getSemestre().equals(Semestre)) materias.add(mate);
        }
        return materias;
    }
    
    boolean verificarSemestre(String Semestre){
        for(Materia mate: getItems()){
            if(mate.getSemestre().equals(Semestre)) return true;
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
        if(selectedNode!=null){
            List<Materia> mats= ejbMateria.findByNombre(selectedNode.getData().toString());
            selected = mats.get(0);
        }
        this.selectedNode = selectedNode;
    }
    
    /////////////////////////////////////////////////////////////////////////////
}
