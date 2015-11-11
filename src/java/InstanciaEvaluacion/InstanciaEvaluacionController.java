package InstanciaEvaluacion;

import Curso.Curso;
import Curso.CursoController;
import Curso.CursoFacade;
import InterfazUtil.SemestreAnioController;
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
    private EvaluacionFacade ejbInstancia;
    
    @EJB
    private CursoFacade ejbCurso;
    
    private List<Evaluacion> items=new ArrayList();
    private Evaluacion selected;
    
    private String nombreEv;
    private Date fechEv;
    
    public String getNombreEv() {
        return nombreEv;
    }
    
    public void setNombreEv(String nombreEv) {
        this.nombreEv = nombreEv;
    }
    
    public Date getFechEv() {
        return fechEv;
    }
    
    public void setFechEv(Date fechEv) {
        this.fechEv = fechEv;
    }
    
    @PostConstruct
    private void init(){
        initItems();
    }
    
    public List<Evaluacion> getItems() {
        return items;
    }
    
    public void setItems(List<Evaluacion> items) {
        this.items = items;
    }
    
    public Evaluacion getSelected() {
        return selected;
    }
    
    public void setSelected(Evaluacion selected) {
        this.selected = selected;
    }
    
    @Inject
    private CursoController cursoController;
    
    @Inject
    private SemestreAnioController comboBoxController;
    
    public void cargarInstanciasDelCurso(){
        items = ejbInstancia.findInstanciaByCurso(cursoController.getSelected().getIdCurso());
    }
    
    public void prepareToCreate(){
        selected = null;
        nombreEv = null;
        fechEv = null;
        comboBoxController.setTipoInstanciaSelected(null);
    }
    
    public void createSelected(){
        InicializarSelectedInstanciaChild();
        selected.setFecha(fechEv);
        selected.setNombre(nombreEv);
        Curso c = cursoController.getSelected();
        selected.setCurso(c);
        ejbInstancia.edit(selected);
        updateItems();
    }
    
    private void InicializarSelectedInstanciaChild(){
        if(comboBoxController.getTipoInstanciaSelected().equals("Examen")){
            selected = new Examen();
        }
        if(comboBoxController.getTipoInstanciaSelected().equals("Laboratorio")){
            selected = new Laboratorio();
        }
        if(comboBoxController.getTipoInstanciaSelected().equals("Parcial")){
            selected = new Parcial();
        }
    }
    
    public void deleteSelected() {
        beforeDelete();
        ejbInstancia.remove(selected);
        updateItems();
        selected = null;
    }
    
    private void beforeDelete(){
        selected.getCurso().getInstanciasEvaluaciones().remove(selected);
        selected.setCurso(null);
        
        selected.setResultadosInstancias(new ArrayList());
        ejbInstancia.edit(selected);
    }
    
    private void initItems(){
        items=ejbInstancia.findAll();
    }
    
    private void updateItems(){
        cursoController.setSelected(ejbCurso.find(cursoController.getSelected().getIdCurso()));
        items=cursoController.getSelected().getInstanciasEvaluaciones();
    }
}