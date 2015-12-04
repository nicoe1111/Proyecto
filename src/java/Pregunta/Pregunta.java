/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pregunta;

import Encuesta.Encuesta;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Representa una Pregunta de una Encuesta, contiene N RespuestaPregunta.
 * @author Matias
 */
@Entity
public class Pregunta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idPregunta;
    private String pregunta;
    
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RespuestaPregunta> respuestasPreguntas= new ArrayList<>();
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_encuesta")
    private Encuesta encuesta;
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public Pregunta() {
    }

    public Pregunta(int idPregunta, String pregunta, Encuesta encuesta) {
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.encuesta = encuesta;
    }
    
//+++++++++++++++++++++SETTERS++++++++++++++++++++++++++

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public void setRespuestasPreguntas(List<RespuestaPregunta> respuestasPreguntas) {
        this.respuestasPreguntas = respuestasPreguntas;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }
    
 //+++++++++++++++++++++GETTERS++++++++++++++++++++++++++

    public int getIdPregunta() {
        return idPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public List<RespuestaPregunta> getRespuestasPreguntas() {
        return respuestasPreguntas;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }
    
    
   
   
}
