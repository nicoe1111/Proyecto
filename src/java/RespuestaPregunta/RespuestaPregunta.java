/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RespuestaPregunta;

import Pregunta.Pregunta;
import TipoRol.Alumno;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Representa la respuesta a una Pregunta dada por un Alumno en particular.
 * @author Matias
 */
@Entity
public class RespuestaPregunta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idRespuestaPregunta;
    private int puntaje;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_Alumno")
    private Alumno alumno;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_Pregunta")
    private Pregunta pregunta;

//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public RespuestaPregunta() {
    }

    public RespuestaPregunta(int idRespuestaPregunta, int puntaje, Alumno alumno, Pregunta pregunta) {
        this.idRespuestaPregunta = idRespuestaPregunta;
        this.puntaje = puntaje;
        this.alumno = alumno;
        this.pregunta = pregunta;
    }
    
//++++++++++++++++++++SETTERS+++++++++++++++++++++++++++

    public void setIdRespuestaPregunta(int idRespuestaPregunta) {
        this.idRespuestaPregunta = idRespuestaPregunta;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
    
//++++++++++++++++++++GETTERS+++++++++++++++++++++++++++

    public int getIdRespuestaPregunta() {
        return idRespuestaPregunta;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }
    

    
    
}
