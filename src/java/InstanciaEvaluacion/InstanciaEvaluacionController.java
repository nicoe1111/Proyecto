package InstanciaEvaluacion;

import Curso.Curso;
import Curso.CursoController;
import Curso.CursoFacade;
import InterfazUtil.SemestreAnioController;
import java.io.Serializable;
import java.util.ArrayList;
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
        if(selected==null){
            selected = new Evaluacion();
        }
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
        comboBoxController.setTipoInstanciaSelected(null);
    }
    
    public void createSelected(){
        Curso c = cursoController.getSelected();
        selected.setCurso(c);
        ejbInstancia.edit(selected);
        updateItems();
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