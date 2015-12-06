package ResultadoInstancia;

import InstanciaEvaluacion.Evaluacion;
import Rol.Alumno;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ResultadoInstancia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idResultadoInstancia;
    private int calificacion;
    private boolean isPresento;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_Evaluacion")
    private Evaluacion instanciaEvaluacion;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_Alumno")
    private Alumno alumno;
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public ResultadoInstancia() {
    }

    public ResultadoInstancia(int idResultadoInstancia, int calificacion, boolean isPresento, Evaluacion instanciaEvaluacion, Alumno alumno) {
        this.idResultadoInstancia = idResultadoInstancia;
        this.calificacion = calificacion;
        this.isPresento = isPresento;
        this.instanciaEvaluacion = instanciaEvaluacion;
        this.alumno = alumno;
    }
    
//++++++++++++++++++++SETTERS+++++++++++++++++++++++++++

    public void setIdResultadoInstancia(int idResultadoInstancia) {
        this.idResultadoInstancia = idResultadoInstancia;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public void setIsPresento(boolean isPresento) {
        this.isPresento = isPresento;
    }

    public void setInstanciaEvaluacion(Evaluacion instanciaEvaluacion) {
        this.instanciaEvaluacion = instanciaEvaluacion;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    
//++++++++++++++++++++GETTERS+++++++++++++++++++++++++++

    public int getIdResultadoInstancia() {
        return idResultadoInstancia;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public boolean isIsPresento() {
        return isPresento;
    }

    public Evaluacion getInstanciaEvaluacion() {
        return instanciaEvaluacion;
    }

    public Alumno getAlumno() {
        return alumno;
    }
    
}
