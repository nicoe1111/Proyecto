/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RespuestaPregunta;

import Curso.Curso;
import Encuesta.Encuesta;
import Pregunta.Pregunta;
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
public class RespuestaPregunta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idRespuestaPregunta;
    private int puntaje;
    private boolean contesto;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "id_Alumno")
    private Alumno alumno;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idPregunta")
    private Pregunta pregunta;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idEncuesta")
    private Encuesta encuesta;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idCurso")
    private Curso curso;

//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public RespuestaPregunta() {    }
    
//++++++++++++++++++++SETTERS+++++++++++++++++++++++++++

    public boolean isContesto() {
        return contesto;
    }

    public void setContesto(boolean contesto) {
        this.contesto = contesto;
    }

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

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
//++++++++++++++++++++GETTERS+++++++++++++++++++++++++++

    public Curso getCurso() {
        return curso;
    }

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

    public Encuesta getEncuesta() {
        return encuesta;
    }
    
}
