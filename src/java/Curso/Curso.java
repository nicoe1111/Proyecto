/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Curso;

import Materia.Materia;
import Rol.Alumno;
import Rol.Docente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Curso implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idCurso;
    private int anio;
    private Date FechaInicio;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_materia")
    private Materia materia;
        
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_docente")
    private Docente docente;
    
    @ManyToMany
    private List<Alumno> alumnos = new ArrayList<>();

    public Curso() {    }

    public Curso(int idCurso, int anio, Date FechaInicio) {
        this.idCurso = idCurso;
        this.anio = anio;
        this.FechaInicio = FechaInicio;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

//    public Docente getDocente() {
//        return docente;
//    }
//
//    public void setDocente(Docente docente) {
//        this.docente = docente;
//    }

//    public List<Alumno> getAlumnos() {
//        return alumnos;
//    }
//
//    public void setAlumnos(List<Alumno> alumnos) {
//        this.alumnos = alumnos;
//    }
    
}
