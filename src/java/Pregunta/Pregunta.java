/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pregunta;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pregunta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idPregunta;
    private String pregunta;
    
//++++++++++++++++++CONSTRUCTORES+++++++++++++++++++++++

    public Pregunta() {
    }

    public Pregunta(int idPregunta, String pregunta) {
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
    }
    
//+++++++++++++++++++++SETTERS++++++++++++++++++++++++++

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    
 //+++++++++++++++++++++GETTERS++++++++++++++++++++++++++

    public int getIdPregunta() {
        return idPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }
}
