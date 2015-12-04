/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asistencia;

import ClaseDada.ClaseDada;
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
public class Asistencia implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAsistencia;
    private boolean isPresente;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_ClaseDada")
    private ClaseDada claseDada;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_Alumno")
    private Alumno alumno;

    
//++++++++++++++CONSTRUCTORES+++++++++++++++++
    public Asistencia() {
    }

    public Asistencia(int idAsistencia, boolean isPresente, ClaseDada claseDada, Alumno alumno) {
        this.idAsistencia = idAsistencia;
        this.isPresente = isPresente;
        this.claseDada = claseDada;
        this.alumno = alumno;
    }
 
//+++++++++++++SETTERS++++++++++++++++++++++++

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public void setIsPresente(boolean isPresente) {
        this.isPresente = isPresente;
    }

    public void setClaseDada(ClaseDada claseDada) {
        this.claseDada = claseDada;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    
//++++++++++++++GETTERS+++++++++++++++++++++++

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public boolean isIsPresente() {
        return isPresente;
    }

    public ClaseDada getClaseDada() {
        return claseDada;
    }

    public Alumno getAlumno() {
        return alumno;
    }
    
    
    
}
