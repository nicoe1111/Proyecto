package ResultadoInstancia;

import Curso.Curso;
import Curso.CursoController;
import Curso.CursoFacade;
import InstanciaEvaluacion.Evaluacion;
import InstanciaEvaluacion.EvaluacionFacade;
import InstanciaEvaluacion.Examen;
import InstanciaEvaluacion.InstanciaEvaluacionController;
import InstanciaEvaluacion.Laboratorio;
import InstanciaEvaluacion.Parcial;
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

@Named("resultadosEvaluacionController")
@ViewScoped
public class ResultadosEvaluacionController implements Serializable{
    
    @EJB
    private EvaluacionFacade ejbInstancia;
    
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
        items = instanciaEvaluacionController.getSelected().getResultadosInstancias();
    }
    
    public void guardar(){
        ejbInstancia.edit(items.get(0).getInstanciaEvaluacion());
    }
}