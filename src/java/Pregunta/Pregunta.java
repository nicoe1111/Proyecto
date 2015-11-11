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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class Pregunta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idPregunta;
    private String pregunta;
    
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<RespuestaPregunta> respuestasPreguntas = new ArrayList<RespuestaPregunta>();
    
    @ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    private List<Encuesta> encuestas = new ArrayList<Encuesta>();
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public Pregunta() {
    }

    public Pregunta(int idPregunta, String pregunta, List<Encuesta> encuesta) {
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.encuestas = encuesta;
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

    public void setEncuesta(List<Encuesta> encuesta) {
        this.encuestas = encuesta;
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

    public List<Encuesta> getEncuesta() {
        return encuestas;
    }
    
    
   
   
}
