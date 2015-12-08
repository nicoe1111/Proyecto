package InstanciaEvaluacion;

import Curso.Curso;
import ResultadoInstancia.ResultadoInstancia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Evaluacion implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEvaluacion;
    private String Tipo;
    private Date fecha;
    
    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Curso")
    private Curso curso;
    
    @OneToMany(mappedBy = "instanciaEvaluacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ResultadoInstancia> resultadosInstancias= new ArrayList<>();
    
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public Evaluacion() {
    }

    

//++++++++++++++++++++SETTERS+++++++++++++++++++++++++++

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setResultadosInstancias(List<ResultadoInstancia> resultadosInstancias) {
        this.resultadosInstancias = resultadosInstancias;
    }

    

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
//++++++++++++++++++++GETTERS+++++++++++++++++++++++++++

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<ResultadoInstancia> getResultadosInstancias() {
        return resultadosInstancias;
    }
    
    public String getTipo() {
        return Tipo;
    }}

