package InstanciaEvaluacion;

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
    private InstanciaEvaluacionFacade ejbInstancia;
    
    private List<InstanciaEvaluacion> items=new ArrayList();
    private InstanciaEvaluacion selected;
    
    @PostConstruct
    private void init(){
        items=ejbInstancia.findAll();
    }
    
    public List<InstanciaEvaluacion> getItems() {
        return items;
    }
    
    public void setItems(List<InstanciaEvaluacion> items) {
        this.items = items;
    }
    
    public InstanciaEvaluacion getSelected() {
        if(selected==null){
            selected=new InstanciaEvaluacion();
        }
        return selected;
    }
    
    public void setSelected(InstanciaEvaluacion selected) {
        this.selected = selected;
    }
    
    @Inject
    private Curso.CursoController cursoController;
    
    @Inject
    private InterfazUtil.SemestreAnioController comboBoxController;
    
    public void cargarInstanciasDelCurso(){
        items = ejbInstancia.findInstanciaByCurso(cursoController.getSelected().getIdCurso());
    }
    
    public void createSelected(){
        selected.setCurso(cursoController.getSelected());
        combertirInstanciaEnChild();
        
    }
    
    private void combertirInstanciaEnChild(){
        if(comboBoxController.getTipoInstanciaSelected().equals(Examen.class.getName())){
            selected = (Examen)selected;
        }
        if(comboBoxController.getTipoInstanciaSelected().equals(Laboratorio.class.getName())){
            selected = (Laboratorio)selected;
        }
        if(comboBoxController.getTipoInstanciaSelected().equals(Parcial.class.getName())){
            selected = (Parcial)selected;
        }
    }
    
}