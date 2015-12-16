package InstanciaEvaluacion;

import Curso.Curso;
import Curso.CursoController;
import Curso.CursoFacade;
import InterfazUtil.SemestreAnioController;
import ResultadoInstancia.ResultadoFacade;
import ResultadoInstancia.ResultadoInstancia;
import Rol.Alumno;
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
    private ResultadoFacade ejbResultado;
    
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
        crearResultadosAlumno(selected);
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
        for(ResultadoInstancia ri : selected.getResultadosInstancias()){
            ri.setAlumno(null);
            ri.setInstanciaEvaluacion(null);
            ejbResultado.remove(ri);
        }
        selected.setResultadosInstancias(null);
        ejbInstancia.edit(selected);
    }
    
    private void initItems(){
        items=ejbInstancia.findAll();
    }
    
    private void updateItems(){
        cursoController.setSelected(ejbCurso.find(cursoController.getSelected().getIdCurso()));
        items=cursoController.getSelected().getInstanciasEvaluaciones();
    }
    
    private void crearResultadosAlumno(Evaluacion e){
        for(Alumno a:e.getCurso().getAlumnos()){
            if(!existeResultadoAlumno(e, a)){
                ResultadoInstancia ri = new ResultadoInstancia();
                ri.setAlumno(a);
                e.getResultadosInstancias().add(ri);
                ri.setInstanciaEvaluacion(e);
            }
        }
    }
    
    private boolean existeResultadoAlumno(Evaluacion e, Alumno a){
        for(ResultadoInstancia ri : e.getResultadosInstancias()){
            if(ri.getAlumno().getIdRol()==a.getIdRol()){
                return true;
            }
        }
        return false;
    }
}