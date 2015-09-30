/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encuesta;

import Curso.Curso;
import Pregunta.Pregunta;
import RespuestaPregunta.RespuestaPregunta;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Representa una cuesta de un Curso dado, contiene N Pregunta.
 * @author Matias
 */
@Entity
public class Encuesta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idEncuesta;
    private Date fecha;
    
    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pregunta> preguntas= new ArrayList<>();
    
    @OneToOne(mappedBy = "encuesta")
    @PrimaryKeyJoinColumn
    private Curso curso;
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public Encuesta() {
    }

    public Encuesta(int idEncuesta, Date fecha, Curso curso) {
        this.idEncuesta = idEncuesta;
        this.fecha = fecha;
        this.curso = curso;
    }
    
//+++++++++++++++++++++SETTERS++++++++++++++++++++++++++

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
//+++++++++++++++++++++GETTERS++++++++++++++++++++++++++

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public Date getFecha() {
        return fecha;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public Curso getCurso() {
        return curso;
    }
    
}
