package InstanciaEvaluacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("instanciaEvaluacionController")
@ViewScoped
public class InstanciaEvaluacionController implements Serializable{
    
    @EJB
    private InstanciaEvaluacionFacade ejbInstancia;
    
    private List<Evaluacion> items=new ArrayList();
    private Evaluacion selected;
    
//    private String nombreEv;
//    private Date fechEv;
    
    @PostConstruct
    private void init(){
        items=ejbInstancia.findAll();
    }
    
    public List<Evaluacion> getItems() {
        return items;
    }
    
    public void setItems(List<Evaluacion> items) {
        this.items = items;
    }
    
    public Evaluacion getSelected() {
        if(selected==null){
            selected= new Evaluacion();
        }
        return selected;
    }
    
    public void setSelected(Evaluacion selected) {
        this.selected = selected;
    }
    
    @Inject
    private Curso.CursoController cursoController;
    
    @Inject
    private InterfazUtil.SemestreAnioController comboBoxController;
    
    public void cargarInstanciasDelCurso(){
        items = ejbInstancia.findInstanciaByCurso(cursoController.getSelected().getIdCurso());
    }
    
//    public void prepareToCreate(){
//        selected = null;
//        nombreEv = null;
//        fechEv = null;
//        comboBoxController.setTipoInstanciaSelected(null);
//    }
//    
//    public void prepareToUpdate(){
//        nombreEv = selected.getNombre();
//        fechEv = selected.getFecha();
//        comboBoxController.setTipoInstanciaSelected(null);
//    }
    
    public void createSelected(){
        InicializarSelectedInstanciaChild();
//        selected.setFecha(fechEv);
//        selected.setNombre(nombreEv);
        selected.setCurso(cursoController.getSelected());
        ejbInstancia.edit(selected);
    }
    
    public void updateSelected(){
        InicializarSelectedInstanciaChild();
//        selected.setFecha(fechEv);
//        selected.setNombre(nombreEv);
        selected.setCurso(cursoController.getSelected());
        ejbInstancia.edit(selected);
    }
    
    private void InicializarSelectedInstanciaChild(){
        if(comboBoxController.getTipoInstanciaSelected().equals("Examen")){
//            selected = new Examen();
            selected= (Examen)selected;
        }
        if(comboBoxController.getTipoInstanciaSelected().equals("Laboratorio")){
//            selected = new Laboratorio();
            selected= (Laboratorio)selected;
        }
        if(comboBoxController.getTipoInstanciaSelected().equals("Parcial")){
//            selected = new Parcial();
            selected= (Parcial)selected;
        }
    }
    
    public void deleteSelected() {
        beforeDelete();
        ejbInstancia.remove(selected);
        updateItems();
        selected = null;
    }
    
    private void beforeDelete(){
        selected.setCurso(null);
        selected.setResultadosInstancias(new ArrayList());
        ejbInstancia.edit(selected);
    }
    
    private void updateItems(){
        items=ejbInstancia.findAll();
    }
    
     public void cargarControllersSelecteds(){
        comboBoxController.setTipoInstanciaSelected(selected.getClass().toString());
        //cursoController.setSelected(selected.getCurso());
    }
}