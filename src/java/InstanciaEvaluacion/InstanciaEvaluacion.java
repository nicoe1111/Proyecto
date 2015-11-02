/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InstanciaEvaluacion;

import Curso.Curso;
import ResultadoInstancia.ResultadoInstancia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Representa instancias de evaluacion de un Curso dado, y contiene N ResultadoInstancia.
 * Tiene como clases hijas Laboratorio, Parcial y Examen.
 * @author Matias
 */
@Entity
public class InstanciaEvaluacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected int idInstanciaEvaluacion;
    protected Date fecha;
    protected String nombre;
    
    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Curso")
    private Curso curso;
    
    @OneToMany(mappedBy = "instanciaEvaluacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ResultadoInstancia> resultadosInstancias= new ArrayList<>();
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public InstanciaEvaluacion() {
    }

    public InstanciaEvaluacion(int idInstanciaEvaluacion, Date fecha, String nombre, Curso curso) {
        this.idInstanciaEvaluacion = idInstanciaEvaluacion;
        this.fecha = fecha;
        this.nombre = nombre;
        this.curso = curso;
    }
    

//++++++++++++++++++++SETTERS+++++++++++++++++++++++++++

    public void setIdInstanciaEvaluacion(int idInstanciaEvaluacion) {
        this.idInstanciaEvaluacion = idInstanciaEvaluacion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setResultadosInstancias(List<ResultadoInstancia> resultadosInstancias) {
        this.resultadosInstancias = resultadosInstancias;
    }

//++++++++++++++++++++GETTERS+++++++++++++++++++++++++++

    public int getIdInstanciaEvaluacion() {
        return idInstanciaEvaluacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<ResultadoInstancia> getResultadosInstancias() {
        return resultadosInstancias;
    }
    
    
}
