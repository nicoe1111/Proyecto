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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Encuesta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEncuesta;
    private int fecha;
    
    @ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
    private List<Pregunta> preguntas= new ArrayList<>();
    
    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Curso> cursos;
    
    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RespuestaPregunta> respPregunta;
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public Encuesta() { }

    public Encuesta(int idEncuesta, int fecha, List<Curso> cursos) {
        this.idEncuesta = idEncuesta;
        this.fecha = fecha;
        this.cursos = cursos;
    }
    
//+++++++++++++++++++++SETTERS++++++++++++++++++++++++++

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public void setRespPregunta(List<RespuestaPregunta> respPregunta) {
        this.respPregunta = respPregunta;
    }
    
//+++++++++++++++++++++GETTERS++++++++++++++++++++++++++

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public int getFecha() {
        return fecha;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public List<RespuestaPregunta> getRespPregunta() {
        return respPregunta;
    }
    
}
