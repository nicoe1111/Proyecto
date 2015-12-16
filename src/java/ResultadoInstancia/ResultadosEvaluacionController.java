package ResultadoInstancia;

import Curso.Curso;
import Curso.CursoController;
import Curso.CursoFacade;
import InstanciaEvaluacion.Evaluacion;
import InstanciaEvaluacion.EvaluacionFacade;
import InstanciaEvaluacion.InstanciaEvaluacionController;
import InterfazUtil.SemestreAnioController;
import Rol.Alumno;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("resultadosEvaluacionController")
@ViewScoped
public class ResultadosEvaluacionController implements Serializable{
    
    @EJB
    private EvaluacionFacade ejbInstancia;
    
    @EJB
    private ResultadoFacade ejbResultado;
    
    private List<ResultadoInstancia> items=new ArrayList();

    public List<ResultadoInstancia> getItems() {
        return items;
    }

    public void setItems(List<ResultadoInstancia> items) {
        this.items = items;
    }
    
    @Inject
    private InstanciaEvaluacionController instanciaEvaluacionController;
    
    public void cargarResultadosDeEvaluacion(){
        Evaluacion e = instanciaEvaluacionController.getSelected();
        items = e.getResultadosInstancias();
    }
    
    public void guardar(){
        Evaluacion e = instanciaEvaluacionController.getSelected();
        e.setResultadosInstancias(items);
        ejbInstancia.edit(e);
    }
    
    public ResultadoInstancia getResultadoAlumno(Evaluacion e, Alumno a){
       return ejbResultado.findResultadoEvalAlumno(e.getIdEvaluacion(), a.getIdRol());
    }
}